
package display;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import level.LevelData;
import objects.Bomb;
import objects.GameObject;
import tile.Tile;
import tile.TileType;

/**
 *
 * @author YIMING LI
 */
public class Inventory  {
    //used to refill bomb
    //private ImageView bomb=null;
    private int bombPlusOne=0;
    private int gasPlusOne=0;
    private List<Tile> tiles;
    private LevelData levelData;
   
    
    
   public Inventory (LevelData levelData) {
       
       this.levelData = levelData;
       tiles = levelData.getTileSet().getAllTiles();
       
   }
    
   
    
    public BorderPane buildInventoryGUI(){
        
        //Initialize the border pane
        BorderPane inventoryBoard = new BorderPane();
        inventoryBoard.setMinWidth(150);
        inventoryBoard.setMaxWidth(150);
        
        inventoryBoard.setPadding(new Insets(10, 10, 10, 10));
        inventoryBoard.setStyle("-fx-background-image: url(file:resources/inventorySkin.png);");
        //Create a grid pane to show each item row
        //In the center of borderPane 
        GridPane itemRows = new GridPane();
        
        
        //Simulate start game
  
        
        //Will start refilling after the game start 1 second. 
        //Bomb row, store 4 items
        HBox bombRow = new HBox(8);
        Timeline timeline1=new Timeline(); 
        KeyFrame key1=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(bombRow, "file:resources/bomb.png");
                bombPlusOne++;
            }
        });
        
        //GAS row, store 4 items
        HBox gasRow = new HBox(8);
        Timeline timeline2=new Timeline();
        KeyFrame key2=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(gasRow, "file:resources/GAS.png");
                
            }
        });
        
        //femaleChangeRow row, store 4 items
        HBox sterilistationRow = new HBox(8);
        Timeline timeline3=new Timeline();  
        KeyFrame key3=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(sterilistationRow, "file:resources/sterilistation.png");
                
            }
        });
        
        
        //femaleChangeRow row, store 4 items
        HBox poisonRow = new HBox(8);
        Timeline timeline4=new Timeline();  
        KeyFrame key4=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(poisonRow, "file:resources/poison.png");
                
            }
        });
        
        
        //femaleChangeRow row, store 4 items
        HBox maleChangeRow = new HBox(8);
        Timeline timeline5=new Timeline();  
        KeyFrame key5=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(maleChangeRow, "file:resources/maleChange.png");
                
            }
        });
        
        
        //femaleChangeRow row, store 4 items
        HBox femaleChangeRow = new HBox(8);
        Timeline timeline6=new Timeline();  
        KeyFrame key6=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(femaleChangeRow, "file:resources/femaleChange.png");
               
            }
        });
        
        
        //femaleChangeRow row, store 4 items
        HBox noEntrySignRow = new HBox(8);
        Timeline timeline7=new Timeline();  
        KeyFrame key7=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(noEntrySignRow,  "file:resources/noEntrySign.png");
            }
        });
        
        //femaleChangeRow row, store 4 items
        HBox deathRatsRow = new HBox(8);
        Timeline timeline8=new Timeline();  
        KeyFrame key8=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refillItems(deathRatsRow,  "file:resources/deathRats.png");
                bombPlusOne++;
            }
        });
        
        timeline1.getKeyFrames().addAll(key1);
        timeline1.setCycleCount(4);
        AnimationTimer at1 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline1.play();
            }
        };
        
        timeline2.getKeyFrames().addAll(key2);
        timeline2.setCycleCount(4);
        AnimationTimer at2 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline2.play();
            }
        };
        
        timeline3.getKeyFrames().addAll(key3);
        timeline3.setCycleCount(4);
        AnimationTimer at3 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline3.play();
            }
        };
        
        timeline4.getKeyFrames().addAll(key4);
        timeline4.setCycleCount(4);
        AnimationTimer at4 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline4.play();
            }
        };
        
        timeline5.getKeyFrames().addAll(key5);
        timeline5.setCycleCount(4);
        AnimationTimer at5 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline5.play();
            }
        };
        
        timeline6.getKeyFrames().addAll(key6);
        timeline6.setCycleCount(4);
        AnimationTimer at6 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline6.play();
            }
        };
        
        timeline7.getKeyFrames().addAll(key7);
        timeline7.setCycleCount(4);
        AnimationTimer at7 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline7.play();
            }
        };
        
        timeline8.getKeyFrames().addAll(key8);
        timeline8.setCycleCount(4);
        AnimationTimer at8 = new AnimationTimer(){
            @Override
            public void handle(long now){
                timeline8.play();
            }
        };
        
        //Refill bomb animation
      
            at1.start();
            at2.start();
            at3.start();
            at4.start();
            at5.start();
            at6.start();
            at7.start();
            at8.start();
        
        
        bombRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at1.stop();
                timeline1.pause();
            } else{
                System.out.println("Children " + children.size());
                at1.start();
                timeline1.play();
            }
        });
        
        gasRow.widthProperty().addListener(e->{
            ObservableList<Node> children = gasRow.getChildren();
            if(children.size()>3){
                at2.stop();
                timeline2.pause();
            } else{
                at2.start();
                timeline2.play();
            }
        });
        
        sterilistationRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at3.stop();
                timeline3.pause();
            } else{
                at3.start();
                timeline3.play();
            }
        });
        
        poisonRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at4.stop();
                timeline4.pause();
            } else{
                at4.start();
                timeline4.play();
            }
        });
        
        maleChangeRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at5.stop();
                timeline5.pause();
            } else{
                at5.start();
                timeline5.play();
            }
        });
        
        femaleChangeRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at6.stop();
                timeline6.pause();
            } else{
                at6.start();
                timeline6.play();
            }
        });
        
        noEntrySignRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at7.stop();
                timeline7.pause();
            } else{
                at7.start();
                timeline7.play();
            }
        });
        
        deathRatsRow.widthProperty().addListener(e->{
            ObservableList<Node> children = bombRow.getChildren();
            if(children.size()>3){
                at8.stop();
                timeline8.pause();
                while(children.size()<4){
                    deathRatsRow.getChildren().removeAll();
                }
            } else{
                at8.start();
                timeline8.play();
            }
        });
        //eight placeholder
        ImageView placeholder1=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder2=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder3=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder4=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder5=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder6=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder7=new ImageView(new Image("file:resources/empty.png"));
        ImageView placeholder8=new ImageView(new Image("file:resources/empty.png"));
        
        itemRows.add(bombRow, 0, 0);
        itemRows.add(placeholder1, 5, 0);
        
        itemRows.add(gasRow, 0, 1);
        itemRows.add(placeholder2, 5, 1);
        
        itemRows.add(sterilistationRow, 0, 3);
        itemRows.add(placeholder3, 5, 3);
        
        itemRows.add(poisonRow, 0, 4);
        itemRows.add(placeholder4, 5, 4);
        
        itemRows.add(maleChangeRow, 0, 5);
        itemRows.add(placeholder5, 5, 5);
        
        itemRows.add(femaleChangeRow, 0, 6);
        itemRows.add(placeholder6, 5, 6);
        
        itemRows.add(noEntrySignRow, 0, 7);
        itemRows.add(placeholder7, 5, 7);
        
        
        itemRows.add(deathRatsRow, 0, 8);
        itemRows.add(placeholder8, 5, 8);
        
        
//        itemRows.add(btn, 0, 9);
        
//        itemRows.add(femaleChangeRow, 0, 2);
        inventoryBoard.setCenter(itemRows);
        return inventoryBoard;
    }
    
    /**
    *refillItems will store each item;
 bombPlusOne is the item position in each list
 Whole function like for loop 
    **/
    public void refillItems(HBox bombRow, String URL){
//        ArrayList<ImageView> refillBomb_iv = new ArrayList<>();
        //Create a new item.
        Image item_image=new Image(URL);
        
        ImageView item_iv = new ImageView();
        //Set size to adjust the board view.
        fitIamgeView(item_iv, item_image.getWidth()/2, item_image.getWidth()/2);
        item_iv.setImage(item_image);
        
         //Drag Item.
        item_iv.setOnMouseDragged(event -> dragged(event, item_iv));
           
        item_iv.setOnMouseReleased(event -> released(event, item_iv,bombRow));
            
        
    
        bombRow.getChildren().addAll(item_iv);
    }
    
    
   
    //Set size to adjust the board view.
    public void fitIamgeView(ImageView iv, double w, double h){
        //Set size to adjust the board view.
        iv.setFitWidth(w);
        iv.setFitHeight(h);
    }
    
 
    
    public static int setItemAmouut(){
        return 4;
    }
    
    public void dragged(MouseEvent event,ImageView  c){
        c.setTranslateX(c.getTranslateX()+ event.getX());
        c.setTranslateY(c.getTranslateY()+ event.getY());
        
         
        
        
    }
    public int squareSize =50;
    public void released(MouseEvent event, ImageView c, HBox bombRow){
        int gridX = (int)c.getTranslateX() / squareSize;
        int gridY = (int)c.getTranslateY() / squareSize;
       
        int X1 = (squareSize / 2 + squareSize * gridX);
        int Y1 = (squareSize / 2 + squareSize * gridY);
       
//        c.setTranslateX(squareSize / 2 + squareSize * gridX);
//        c.setTranslateY(squareSize / 2 + squareSize * gridY);
        
        for (int i = 0; i < tiles.size(); i++) {
            //if ((tiles.get(i).getTopLeftX() == X1) && (tiles.get(i).getTopLeftY() == Y1)) {

                if (tiles.get(i).getTileType().equals(TileType.PATH)) {

                    Bomb newGameObject = new Bomb(tiles.get(20), 6, false);

                    GameObject.getBoard().addObject(newGameObject);
                    
                    bombRow.getChildren().remove(c);

                }
           // }
        }

    }

}
    

