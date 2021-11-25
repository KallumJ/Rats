/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rats_yiming;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author YIMING LI
 */
public class Rats_YIMING extends Application {
    //used to refill bomb
    //private ImageView bomb=null;
    private int bombPlusOne=0;
//    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) {
        //The game interface.
        BorderPane board = new BorderPane();
        
        //Canvas in the center of board.
        Canvas canvas = new Canvas(200, 200);
        
        //Inventory of game board.
        //In the right side of board.
        BorderPane showInventory=showInventory(canvas);
        //Set width, height.
        showInventory.setMinWidth(180);
        
        // Get the Graphic Context of the canvas.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //Set the background.
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //Set position.
        board.setRight(showInventory);
        board.setCenter(canvas);
        Scene scene = new Scene(board, 650, 450);
        
        primaryStage.setTitle("Rats");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public BorderPane showInventory(Canvas canvas){
        //Initialize the border pane
        BorderPane inventoryBoard = new BorderPane();
        inventoryBoard.setPadding(new Insets(10, 10, 10, 10));
        
        //Create a grid pane to show each item row
        //In the center of borderPane 
        GridPane itemRows = new GridPane();
        
        //Bomb row, store 5 items
        HBox bombRow = new HBox(8);
        Timeline timeline=new Timeline(); 
        ArrayList<ImageView> refillBomb = new ArrayList<>();
        //Simulate start game
        Button btn=new Button("start");
        
        //The bomb will start refilling after the game start 1 second.        
        KeyFrame key1=new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                refill(refillBomb, bombRow, canvas, bombPlusOne, "imgItem/bomb.png");
                bombPlusOne++;
            }
        });
        timeline.getKeyFrames().addAll(key1);
        timeline.setCycleCount(5);
        //Refill bomb animation
        btn.setOnMouseClicked(e->{
            timeline.play();
        });
        
        itemRows.add(bombRow, 0, 0);
        inventoryBoard.setCenter(itemRows);
        inventoryBoard.setBottom(btn);
        return inventoryBoard;
    }
    
    public void refill(ArrayList<ImageView> refillBomb, HBox bombRow, Canvas canvas, int bombPlusOne, String itemURL){
        //Create a new bomb
        Image image=new Image(itemURL);
        ImageView iv = new ImageView();
        iv.setImage(image);
        //Set size to adjust the board view.
        double fitItemWidth = image.getWidth()/2;
        double fitItemHeight = image.getWidth()/2;
        iv.setFitWidth(fitItemWidth);
        iv.setFitHeight(fitItemHeight);
        
        //Drag Item.
        iv.setOnDragDetected(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                // Mark the drag as started.
                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                //Set sign while dragging.
                db.setDragView(image);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello");
                db.setContent(content);
                event.consume();
            }
        });
        
        canvas.setOnDragOver(new EventHandler <DragEvent>(){
            public void handle(DragEvent event){
                if(event.getGestureSource() == iv){
                    System.out.println("IN");
                
                    // Mark the drag event as acceptable by the canvas.
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
            }
        });
        
        canvas.setOnDragDropped(new EventHandler<DragEvent>(){
            public void handle(DragEvent event){
                Dragboard db=event.getDragboard();
                useItem(event, fitItemWidth, fitItemHeight, image, canvas);
                // Consume the event. This means we mark it as dealt with.
            	event.consume();
            }
        });
        refillBomb.add(iv);
        //Add new bomb in Hbox
        bombRow.getChildren().addAll(refillBomb.get(bombPlusOne));
    }
    
    /**
    * React when an object is dragged onto the canvas.
    **/
    public void useItem(DragEvent event, double w, double h, Image image, Canvas canvas){
        double x = event.getX();
        double y = event.getY();
        GraphicsContext gc= canvas.getGraphicsContext2D();
        gc.drawImage(image, w, h);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
