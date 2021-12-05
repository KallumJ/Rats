package display.menus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import players.PlayerProfileManager;
import players.scores.Player;

/**
 * A cl
 *
 * @author YIMING LI
 */
public class LeaderBoardMenu extends GameMenu {
    private final static String PLAYER_HEADER = "Player               ";
    private final static String LEVEL_HEADER = "Level       ";
    private final static String SCORE_HEADER = "Score       ";
    private final static String SCORE_ENTRY = "%s%s%s";
    private final static String LEADERBOARD_HEADER =
            PLAYER_HEADER + LEVEL_HEADER + SCORE_HEADER;
    private final int levelId;

    /**
     * Constructs a LeaderBoardMenu for the provided level
     *
     * @param id the id of the level to show scores for
     */
    public LeaderBoardMenu(String id) {
        this.levelId = Integer.parseInt(id);
    }

    /**
     * A method to build a LeaderBoardMenu to find top ten players scores for a level.
     *
     * @return the Node containing the menu items
     */
    @Override
    public Parent buildMenu() {

        // Create the blank menu
        EventHandler<Event> backHandler = event ->
                GameMenu.getStage().setScene(
                        new Scene(new LeaderboardLevelSelectMenu().buildMenu())
                );

        BorderPane menu = buildBlank(null, backHandler);

        List<Player> getAllPlayer = PlayerProfileManager.getAllPlayers();

        // If there are players
        if (!getAllPlayer.isEmpty()) {
            // Convert from a map type to an ordered list.
            ArrayList<PlayerInLeaderboard> playersInLeaderboard = convertPlayersToPlayerInLeaderboard(getAllPlayer);
            playersInLeaderboard.sort(new SortPlayerByScore());

            // Create score container
            VBox scoreContainer = new VBox();
            scoreContainer.setMinSize(100, 100);
            scoreContainer.setTranslateX(90);
            scoreContainer.setTranslateY(80);

            // Set font style of the leaderboard.
            scoreContainer.setStyle(
                    "-fx-font-size: 24;" +
                            "-fx-font-family: 'consolas';" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: oblique;"
            );

            // Add header to leaderboard
            Label label = new Label();
            label.setTextFill(Color.LIGHTGRAY);
            label.setText(LEADERBOARD_HEADER);
            scoreContainer.getChildren().addAll(label);

            // Add top ten players and their information into VBox.
            for (int i = 0; i < playersInLeaderboard.size(); i++) {
                PlayerInLeaderboard playerInLeaderboard = playersInLeaderboard.get(i);

                if (i <= 10 && playerInLeaderboard.getLevel() == levelId) {
                    Label playerLabel = new Label();
                    playerLabel.setTextFill(Color.YELLOW);

                    // Place user information on the label.
                    String nameStr = matchStringLengths(playerInLeaderboard.getName(), PLAYER_HEADER);
                    String levelStr = matchStringLengths(
                            String.valueOf(playerInLeaderboard.getLevel()), LEVEL_HEADER
                    );
                    String scoreStr = matchStringLengths(
                            String.valueOf(playerInLeaderboard.getScore()), SCORE_HEADER
                    );

                    playerLabel.setText(String.format(SCORE_ENTRY,
                            nameStr,
                            levelStr,
                            scoreStr
                    ));
                    scoreContainer.getChildren().addAll(playerLabel);
                }

            }

            //Build leaderboard menu.
            getCenter().getChildren().add(scoreContainer);
        }

        return menu;
    }

    /**
     * A method to match the 2 provided string lengths
     *
     * @param stringToMatch the string to lengthen
     * @param masterString  the string whose length we should match
     * @return the stringToMatch, with however many required spaces.
     */
    private static String matchStringLengths(String stringToMatch, String masterString) {
        StringBuilder text = new StringBuilder();
        text.append(stringToMatch);
        while (text.length() < masterString.length()) {
            text.append(" ");
        }
        return text.toString();
    }

    /**
     * A method to convert the list of players to PlayerInLeaderboard's
     *
     * @param players The list of players to convert
     * @return A List of PlayerInLeaderboards.
     */
    public ArrayList<PlayerInLeaderboard> convertPlayersToPlayerInLeaderboard(List<Player> players) {
        ArrayList<PlayerInLeaderboard> playersInLeaderboard = new ArrayList<>();
        for (Player player : players) {

            // Get level and scores of level from the map.
            for (Integer level : player.getHighScores().keySet()) {

                //Build an instance of PlayerInLeaderboard
                PlayerInLeaderboard playInLadderBoard = new PlayerInLeaderboard(
                        player.getPlayerName(),
                        level,
                        player.getHighScores().get(level)
                );

                //Add players in list.
                playersInLeaderboard.add(playInLadderBoard);
            }
        }
        return playersInLeaderboard;
    }
}

/**
 * The class Sort the list by scores.
 *
 * @author YIMING LI
 */
class SortPlayerByScore implements Comparator<PlayerInLeaderboard> {
    /**
     * Sort in descending order.
     */
    @Override
    public int compare(PlayerInLeaderboard a, PlayerInLeaderboard b) {
        return b.getScore() - a.getScore();
    }
}

/**
 * This class collect player information needed for leaderboard
 *
 * @author YIMING LI
 */
class PlayerInLeaderboard {
    private final String name;
    private final Integer level;
    private final Integer score;

    public PlayerInLeaderboard(String name, Integer level, Integer score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }

    /**
     * Get the player name.
     *
     * @return The player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the level of map of this player.
     *
     * @return The level of map.
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Get the score of level of this player.
     *
     * @return The score of level.
     */
    public Integer getScore() {
        return score;
    }
}
