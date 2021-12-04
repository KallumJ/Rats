/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.geometry.Pos;
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
 *
 * @author YIMING LI
 */
class Player_LBM{
    private String name; 
    private Integer level;
    private Integer leve_score;
    
    public Player_LBM(String name, Integer level, Integer leve_score) {
        this.name = name;
        this.level = level;
        this.leve_score = leve_score;
    }
    
    
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setLevel(Integer level){
        this.level = level;
    }
    
    public Integer getLevel(){
        return level;
    }

    public Integer getLeve_score() {
        return leve_score;
    }

    public void setLeve_score(Integer leve_score) {
        this.leve_score = leve_score;
    }

    @Override
    public String toString() {
        return "Player_LBM{" + "name=" + name + ", level=" + level + ", leve_score=" + leve_score + '}';
    }
}

/**
 *
 * @author YIMING LI
 */
public class LeaderBoardMenu extends GameMenu {

    /**
     *
     */
    public static final String MENU_TITLE = "User Not Found";
    public static ArrayList<Player_LBM> players_LBM;
    
    /**
     * A method to build a LeaderBoardMenu to find top ten players
     * @return the Node containing the menu items
     */
    @Override
    public Parent buildMenu() {
        List<Player> getAllPlayer=PlayerProfileManager.getAllPlayers();
        EventHandler<Event> backHandler = event ->
                    GameMenu.stage.setScene(new Scene(new MainMenu().buildMenu()));
        System.out.println(getAllPlayer.get(0).getPlayerName());
        if(!getAllPlayer.isEmpty()){
            players_LBM = returnScore(getAllPlayer);
            Collections.sort(players_LBM, new SortPlayerByScore());
            // If an EventHandler for a back button is provided, add one
            Button backButton = new Button("Back");
            backButton.setOnMousePressed(backHandler);
            backButton.setTranslateX(10);
            backButton.setTranslateY(10);
            VBox v=new VBox();
            v.setMinSize(100, 100);
            v.setAlignment(Pos.CENTER);
            v.setTranslateX(-180);
            v.setStyle(
                    "-fx-font-size: 36;"+
                    "-fx-font-family: 'consolas';"+
                    "-fx-font-weight: bold;"+
                    "-fx-font-style: oblique;"
            );
            for(int i = 0; i < players_LBM.size(); i++){
                if(i < 10){
                    Label label=new Label();
                    label.setTextFill(Paint.valueOf("#fee140"));
                    label.setText(players_LBM.get(i).getName()+
                            "       "+
                            players_LBM.get(i).getLeve_score().toString());
                    v.getChildren().addAll(label);
                }
            }
            
            BorderPane leaderBoardView=buildBlank(new MenuTitle(MENU_TITLE), backHandler);
            // Get and set the background
            try (InputStream is =
                         Files.newInputStream(Paths.get("resources/rats bg.jpeg"))
            ) {
                ImageView img = new ImageView(new Image(is));
                img.setFitWidth(860);
                img.setFitHeight(600);
                leaderBoardView.getChildren().add(img);
            } catch (IOException e) {
                throw new RuntimeException("Image Failed Load.");
            }
            leaderBoardView.setLeft(backButton);
            leaderBoardView.setCenter(v);
            return leaderBoardView;
        } else{
            return buildBlank(new MenuTitle(MENU_TITLE), backHandler);
        }
    }
    
    public ArrayList<Player_LBM> returnScore(List<Player> players){
        players_LBM = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            for(Integer key : players.get(i).getHighScores().keySet()){
                    Player_LBM playInLadderBoard=new Player_LBM(
                    players.get(i).getPlayerName(),
                    key,
                    Integer.parseInt(players.get(i).getHighScores().get(key).toString())
                );
                players_LBM.add(playInLadderBoard);
            }
        }
        return players_LBM;
    }
}

/**
 *
 * @author YIMING LI
 */
 class SortPlayerByScore implements Comparator<Player_LBM> {
    @Override
    public int compare(Player_LBM a, Player_LBM b){
        return b.getLeve_score() - a.getLeve_score();
    }
}