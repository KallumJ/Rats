
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class NewClass extends Application {
    
    int squareSize = 50;
     List<Tile> tiles = new ArrayList<>();
    
    public void pressed(MouseEvent event, Circle c){
        c.setFill(Color.BLUE);
        
    }
    
    public void dragged(MouseEvent event, Circle c){
        c.setTranslateX(c.getTranslateX()+ event.getX());
        c.setTranslateY(c.getTranslateY()+ event.getY());
        
         
        
        
    }
    
    public void released(MouseEvent event, Circle c){
        int gridX = (int)c.getTranslateX() / squareSize;
        int gridY = (int)c.getTranslateY() / squareSize;
       
        c.setTranslateX(squareSize / 2 + squareSize * gridX);
        c.setTranslateY(squareSize / 2 + squareSize * gridY);
        c.setFill(Color.RED);
    }
    
    
    private Parent createContent(){
        
        Group root = new Group();
        Canvas canvas = new Canvas(900, 900);
	GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.RED);
        g.fillOval( 105, 55, 40, 40 );
        
        
        
        
        
        Circle c = new Circle();
        c.setFill(Color.RED);
        c.setRadius(20);
        c.setTranslateX(525);
        c.setTranslateY(50);
        c.setOnMousePressed(event -> pressed(event, c));
        c.setOnMouseDragged(event -> dragged(event, c));
        c.setOnMouseReleased(event -> released(event, c));
        
        
        
        
        
       // Pane root = new Pane();
        //root.setPrefSize(800, 800);
        
        
         
         
            
         
        int[][] maze
             = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

                };
         
         
        for (int row = 0; row < maze.length; row++) {
            
            for (int col = 0; col < maze[0].length; col++) {
                
                tiles.add(new Tile(maze [row] [col]));
            }
        }
               
               
               
               
               
               

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(50 * (i / maze[0].length));
           
            tile.setTranslateY(50 * (i % maze[0].length));

            root.getChildren().add(tile);
            

        }
        root.getChildren().add(canvas);
        
        root.getChildren().add(c);
        return root;
    }

    
    

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();   
    }
    
    
    private class Tile extends StackPane {
        
        
        private String color;
        public Tile(int number){
          
            Rectangle border = new Rectangle(squareSize,squareSize);
            border.setStroke(Color.BLACK);
            
            switch (number) {
            case 1:
                border.setFill(Color.GREEN);
                color = "Green";
            break;
                case 2:
                border.setFill(Color.WHEAT);
            break;
            default:
                border.setFill(Color.GRAY);
                color = "Gray";
                break;     
                
            
            
        }
            
            
            
            
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(border);
            
        }
        
       
        

       
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
