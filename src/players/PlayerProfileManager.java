package players;

import io.XMLFileReader;
import io.XMLFileWriter;
import io.XMLNode;
import players.scores.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage player profiles
 */
public class PlayerProfileManager {
    private static final String PLAYERS_FILE = "players/players.xml";
    private static final XMLNode playersRoot;
    private static Player currentlyLoggedIn;

    // Load the players XMLNode
    static {
        XMLFileReader xmlfileReader = new XMLFileReader(new File(PLAYERS_FILE));
        playersRoot = xmlfileReader.getAsXMLNode();
    }

    /**
     * Get the player by their name
     * @param playerName the players name
     * @return the found Player object, null if they do not exist
     */
    public static Player getPlayer(String playerName) {
        for (XMLNode child : playersRoot.getChildren()) {
            String currentChildName = child.getChildByElementName("name").getNodeValue();

            if (currentChildName.equals(playerName)) {
                return new Player(playerName);
            }
        }

        return null;
    }

    /**
     * A method to log in the player with the provided name, or create a profile for them if they don't exist
     * @param playerName the players name
     */
    public static void loginPlayer(String playerName) {
        Player player = PlayerProfileManager.getPlayer(playerName);

        // If the player already exists, log them in, if not, create new account
        if (player != null) {
            currentlyLoggedIn = player;
        } else {
            createPlayerProfile(playerName);
            currentlyLoggedIn = PlayerProfileManager.getPlayer(playerName);
        }
    }

    /**
     * A method to create a Player profile in the file
     * @param playerName the players name
     */
    private static void createPlayerProfile(String playerName) {
        XMLNode name = new XMLNode("name", playerName, null, null);
        XMLNode maxLevel = new XMLNode("maxLevel", "1", null, null);

        List<XMLNode> children = new ArrayList<>();
        children.add(name);
        children.add(maxLevel);

        playersRoot.getChildren().add(new XMLNode("player", null, null, children));
        savePlayersFile();
    }

    /**
     * Save the players file with the current root node
     */
    private static void savePlayersFile() {
        XMLFileWriter xmlFileWriter = new XMLFileWriter(new File(PLAYERS_FILE), playersRoot.getNodeName());

        xmlFileWriter.writeNodeAsRoot(playersRoot);
        xmlFileWriter.saveAndClose();
    }

    /**
     * A method to return the currently logged in player
     * @return the currently logged in player
     */
    public static Player getCurrentlyLoggedInPlayer() {
        return currentlyLoggedIn;
    }
}
