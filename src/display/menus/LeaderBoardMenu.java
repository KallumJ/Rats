package display.menus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import players.PlayerProfileManager;
import players.scores.Player;

/**
 * This class collect player information needed for leaderboard 
 * @author YIMING LI
 */
class Player_LBM{
    private String name; 
    private Integer level;
    private Integer level_score;
    
    public Player_LBM(String name, Integer level, Integer level_score) {
        this.name = name;
        this.level = level;
        this.level_score = level_score;
    }

/**
* Get the player name.
* @return The player name.
*/    
    public String getName(){
        return name;
    }

/**
* Get the level of map of this player.
* @return The level of map.
*/    
    public Integer getLevel(){
        return level;
    }

/**
* Get the score of level of this player.
* @return The score of level.
*/ 
    public Integer getLevel_score() {
        return level_score;
    }
}

/**
 *
 * @author YIMING LI
 */
public class LeaderBoardMenu extends GameMenu {
    private static final String MENU_TITLE = "User Not Found";
    private static final String NO_BACKGROUND = "Could not load the menu background image file";
    private static ArrayList<Player_LBM> players_LBM;
    
    /**
     * A method to build a LeaderBoardMenu to find top ten players.
     * @return the Node containing the menu items
     */
    @Override
    public Parent buildMenu() {
        List<Player> getAllPlayer=PlayerProfileManager.getAllPlayers();
        //An EventHandler for a back button
        EventHandler<Event> backHandler = event ->
                    GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
        
        if(!getAllPlayer.isEmpty()){
            //Covert from a map type to a ordered list.
            players_LBM = normalisation(getAllPlayer);
            Collections.sort(players_LBM, new SortPlayerByScore());
            
            //A button back to main menu.
            Button backButton = new Button("Back");
            backButton.setOnMousePressed(backHandler);
            backButton.setTranslateX(10);
            backButton.setTranslateY(10);
            
            VBox v=new VBox();
            v.setMinSize(100, 100);
            v.setTranslateX(90);
            v.setTranslateY(80);
            
            //Set font style of the leaderboard.
            v.setStyle(
                    "-fx-font-size: 36;"+
                    "-fx-font-family: 'consolas';"+
                    "-fx-font-weight: bold;"+
                    "-fx-font-style: oblique;"
            );
            
            //Add top ten players and their information into VBox.
            for(int i = 0; i < players_LBM.size(); i++){
                if(i < 10){
                    Label label=new Label();
                    label.setTextFill(Paint.valueOf("#fee140"));
                    
                    //Place user information on the label.
                    label.setText(players_LBM.get(i).getName()+
                                    "       "+
                                    players_LBM.get(i).getLevel_score().toString());
                    v.getChildren().addAll(label);
                }
            }
            
            //Build leaderboard menu.
            BorderPane leaderBoardView=buildBlank(new MenuTitle(MENU_TITLE), backHandler);
            
            // Get and set the background.
            try (InputStream is =
                         Files.newInputStream(Paths.get("resources/ratsBG.jpeg"))
            ) {
                ImageView img = new ImageView(new Image(is));
                img.setFitWidth(860);
                img.setFitHeight(600);
                leaderBoardView.getChildren().add(img);
            } catch (IOException e) {
                throw new RuntimeException(NO_BACKGROUND);
            }
            
            //Place VBox and back button on this menu.
            leaderBoardView.setLeft(backButton);
            leaderBoardView.setCenter(v);
            return leaderBoardView;
        } 
        //Return the default menu if the list empty
        else{
            return buildBlank(new MenuTitle(MENU_TITLE), backHandler);
        }
    }
    
/**
 * 
 * @param players
 * @return A normalisation list of players.
 */    
    public ArrayList<Player_LBM> normalisation(List<Player> players){
        players_LBM = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            
            //Get level and scores of level from the map.
            for(Integer key : players.get(i).getHighScores().keySet()){
                    
                    //Build an instance of player
                    Player_LBM playInLadderBoard=new Player_LBM(
                        players.get(i).getPlayerName(),
                        key,
                        Integer.parseInt(players.get(i).getHighScores().get(key).toString())
                );
                    
                //Add players in list.
                players_LBM.add(playInLadderBoard);
            }
        }
        return players_LBM;
    }
}

/**
 * The class Sort the list by scores. 
 * @author YIMING LI
 */
 class SortPlayerByScore implements Comparator<Player_LBM> {
    @Override

/**
 * Sort in descending order.
 */
    public int compare(Player_LBM a, Player_LBM b){
        return b.getLevel_score() - a.getLevel_score();
    }
}