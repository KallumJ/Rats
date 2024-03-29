package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import level.LevelUtils;
import players.PlayerProfileManager;
import players.scores.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a LeaderBoardMenu.
 *
 * @author YIMING LI
 * @date 2022.02.20
 *
 */
public class LeaderBoardMenu extends GameMenu {
    private static final String PLAYER_HEADER = "Player               ";
    private static final String SCORE_HEADER = "Score       ";
    private static final String SCORE_ENTRY = "%s%s";
    private static final String LEADERBOARD_HEADER =
            PLAYER_HEADER + SCORE_HEADER;
    private final String levelId;
    private static final int SCORE_CONTAINER_MIN_SIZE = 100;
    private static final int SCORE_CONTAINER_X = 90;
    private static final int SCORE_CONTAINER_Y = 80;
    private static final int MAX_NUM_IN_LEADERBOARD = 10;

    /**
     * Constructs a LeaderBoardMenu for the provided level.
     *
     * @param id the id of the level to show scores for.
     */
    public LeaderBoardMenu(String id) {
        this.levelId = id;
    }

    /**
     * A method to match the 2 provided string lengths.
     *
     * @param stringToMatch the string to lengthen.
     * @param masterString  the string whose length we should match.
     * @return the stringToMatch, with however many required spaces.
     */
    private static String matchStringLengths(String stringToMatch,
                                             String masterString) {
        StringBuilder text = new StringBuilder();
        text.append(stringToMatch);
        while (text.length() < masterString.length()) {
            text.append(" ");
        }
        return text.toString();
    }

    /**
     * A method to build a LeaderBoardMenu to find top ten players level.
     *
     * @return the Node containing the menu items.
     */
    @Override
    public Parent buildMenu() {

        // Create the blank menu
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new LeaderboardLevelSelectMenu().buildMenu()));

        BorderPane menu = buildBlank(null, backHandler);

        List<Player> getAllPlayer = PlayerProfileManager.getAllPlayers();

        // If there are players
        if (!getAllPlayer.isEmpty()) {
            // Convert from a map type to an ordered list.
            ArrayList<PlayerInLeaderboard> playersInLeaderboard =
                    convertPlayersToPlayerInLeaderboard(getAllPlayer);
            playersInLeaderboard.sort(new PlayerInLeaderboard.SortPlayerByScore());

            // Create score container
            VBox scoreContainer = new VBox();
            scoreContainer.setMinSize(SCORE_CONTAINER_MIN_SIZE, SCORE_CONTAINER_MIN_SIZE);
            scoreContainer.setTranslateX(SCORE_CONTAINER_X);
            scoreContainer.setTranslateY(SCORE_CONTAINER_Y);

            // Set font style of the leaderboard.
            scoreContainer.setStyle("-fx-font-size: 24;" + "-fx-font-family: "
                    + "'consolas';" + "-fx-font-weight: bold;" + "-fx-font-style" + ": oblique;");

            // Add header to leaderboard
            Label label = new Label();
            label.setTextFill(Color.LIGHTGRAY);
            label.setText(LEADERBOARD_HEADER);
            scoreContainer.getChildren().addAll(label);

            // Add top ten players and their information into VBox.
            int numInLeaderboard = 0;
            for (PlayerInLeaderboard playerInLeaderboard : playersInLeaderboard) {
                if (numInLeaderboard < MAX_NUM_IN_LEADERBOARD && playerInLeaderboard.getLevel().equals(levelId)
                        && LevelUtils.isIdNotForCustomLevel(levelId)) {
                    numInLeaderboard++;
                    Label playerLabel = new Label();
                    playerLabel.setTextFill(Color.YELLOW);

                    // Place user information on the label.
                    String nameStr =
                            matchStringLengths(playerInLeaderboard.getName(),
                                    PLAYER_HEADER);
                    String scoreStr =
                            matchStringLengths(String.valueOf(playerInLeaderboard.getScore()), SCORE_HEADER);

                    playerLabel.setText(String.format(SCORE_ENTRY, nameStr,
                            scoreStr));
                    scoreContainer.getChildren().addAll(playerLabel);
                }

            }

            //Build leaderboard menu.
            getCenter().getChildren().add(scoreContainer);
        }

        return menu;
    }

    /**
     * A method to convert the list of players to PlayerInLeaderboard's.
     *
     * @param players the list of players to convert.
     * @return a List of PlayerInLeaderboards.
     */
    public ArrayList<PlayerInLeaderboard> convertPlayersToPlayerInLeaderboard(List<Player> players) {
        ArrayList<PlayerInLeaderboard> playersInLeaderboard =
                new ArrayList<>();
        for (Player player : players) {

            // Get level and scores of level from the map.
            for (String level : player.getHighScores().keySet()) {

                //Build an instance of PlayerInLeaderboard
                PlayerInLeaderboard playInLadderBoard =
                        new PlayerInLeaderboard(player.getPlayerName(), level,
                                player.getHighScores()
                                        .get(level));

                //Add players in list.
                playersInLeaderboard.add(playInLadderBoard);
            }
        }
        return playersInLeaderboard;
    }
}

