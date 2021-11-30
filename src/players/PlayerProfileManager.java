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
    public static final List<Player> allPlayers;
    public static final String HIGH_SCORE_SEPARATOR = ", ";

    // Load the players XMLNode
    static {
        XMLFileReader xmlfileReader = new XMLFileReader(new File(PLAYERS_FILE));
        playersRoot = xmlfileReader.getAsXMLNode();

        allPlayers = new ArrayList<>();
        if (playersRoot.hasChildren()) {
            for (XMLNode playerNode : playersRoot.getChildren()) {

                String name = playerNode.getChildByElementName("name").getNodeValue();
                int maxLevel = Integer.parseInt(playerNode.getChildByElementName("maxLevel").getNodeValue());

                Player player = new Player(name, maxLevel);
                allPlayers.add(player);

                // If there are high scores, add them
                if (playerNode.getChildren().size() > 2) {
                    List<XMLNode> highScores = playerNode.getChildrenByElementName("highScore");

                    for (XMLNode highScore : highScores) {
                        String highScoreValue = highScore.getNodeValue();
                        String[] highScoreValueSplit = highScoreValue.split(HIGH_SCORE_SEPARATOR);

                        player.getHighScores().put(Integer.parseInt(highScoreValueSplit[0]), Integer.parseInt(highScoreValueSplit[1]));
                    }
                }
            }
        }
    }

    /**
     * Get the player by their name
     * @param playerName the players name
     * @return the found Player object, null if they do not exist
     */
    public static Player getPlayer(String playerName) {
        for (Player player : allPlayers) {
            if (player.getPlayerName().equals(playerName)) {
                return player;
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
        allPlayers.add(new Player(playerName, 1));
        savePlayersFile();
    }

    /**
     * Save the players file with the current root node
     */
    public static void savePlayersFile() {
        XMLFileWriter xmlFileWriter = new XMLFileWriter(new File(PLAYERS_FILE), playersRoot.getNodeName());

        for (Player player : allPlayers) {
            xmlFileWriter.writeNode(player.getAsXMLNode());
        }

        xmlFileWriter.saveAndClose();
    }

    /**
     * A method to return the currently logged in player
     * @return the currently logged in player
     */
    public static Player getCurrentlyLoggedInPlayer() {
        return currentlyLoggedIn;
    }

    /**
     * A method to return all the players currently logged by the system
     * @return a List of all Players
     */
    public static List<Player> getAllPlayers() {
        return allPlayers;
    }

}
