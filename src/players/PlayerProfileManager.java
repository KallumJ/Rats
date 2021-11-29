package players;

import io.XMLFileReader;
import io.XMLFileWriter;
import io.XMLNode;
import players.scores.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerProfileManager {
    private static final String PLAYERS_FILE = "players/players.xml";
    private static final XMLNode playersRoot;
    private static Player currentlyLoggedIn;

    static {
        XMLFileReader xmlfileReader = new XMLFileReader(new File(PLAYERS_FILE));
        playersRoot = xmlfileReader.getAsXMLNode();
    }

    public static Player getPlayer(String playerName) {
        for (XMLNode child : playersRoot.getChildren()) {
            String currentChildName = child.getChildByElementName("name").getNodeValue();

            if (currentChildName.equals(playerName)) {
                return new Player(playerName);
            }
        }

        return null;
    }

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

    private static void createPlayerProfile(String playerName) {
        XMLNode name = new XMLNode("name", playerName, null, null);
        XMLNode maxLevel = new XMLNode("maxLevel", "1", null, null);

        List<XMLNode> children = new ArrayList<>();
        children.add(name);
        children.add(maxLevel);

        playersRoot.getChildren().add(new XMLNode("player", null, null, children));
        savePlayersFile();
    }

    private static void savePlayersFile() {
        XMLFileWriter xmlFileWriter = new XMLFileWriter(new File(PLAYERS_FILE), playersRoot.getNodeName());

        xmlFileWriter.writeNodeAsRoot(playersRoot);
        xmlFileWriter.saveAndClose();
    }

    public static Player getCurrentlyLoggedInPlayer() {
        return currentlyLoggedIn;
    }
}
