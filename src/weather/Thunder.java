package weather;

import display.TileCanvas;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *Add thunder weather
 * 
 * @author YIMINGLI
 */
public class Thunder {
    
    /**
     *Generate an instance of thunder weather.
     */
    public Thunder(){} 
    
    /**
     *Put thunder on a new laywer of canvas.
     */
    public Canvas thunderLayer(){
        Canvas layer=new Canvas(650.0, 500.0);
        GraphicsContext gc=layer.getGraphicsContext2D();
        drawLighting(gc);
        layer.setTranslateX(280);
        return layer;
    } 
    
    
    /**
     * Draw the shape of thunder on canvas.
     * 
     * @param gc  Draw the shape of thunder.
     */
    private void drawLighting(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillPolygon(new double[]{10, 70, 10, 50},
                       new double[]{250, 10, 440, 270}, 4);
    }
    
    /**
     * The thunder will randomly occur while user playing.
     * 
     * @param layer1 Get the weather layer.
     * @param layer2 Get the gamebord layer.
     */
    public void thunderStart(Canvas layer1, Canvas layer2) {
        Timeline timeline=new Timeline();
        EventHandler<ActionEvent> handler1=(ActionEvent e) -> {
            layer1.toFront();
        };
        EventHandler<ActionEvent> handler2=(ActionEvent e) -> {
            layer2.toFront();
        };
        
        KeyFrame k1=new KeyFrame(Duration.seconds(10), handler1);
        KeyFrame k2=new KeyFrame(Duration.seconds(1*Math.random()), handler2);
        timeline.getKeyFrames().add(k1);
        timeline.getKeyFrames().add(k2);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Used to close thunder weather in level Editor.
     */
    public void thunderSwitch(String turnOn){
        if(turnOn.equals("turnOff")){
            
        } else {
            
        }
    }
}
