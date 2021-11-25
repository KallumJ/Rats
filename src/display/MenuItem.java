package display;


import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public abstract class MenuItem extends StackPane {
    public MenuItem(String name) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.DARKVIOLET)
        });

        Rectangle bg = new Rectangle(200, 30);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.DARKVIOLET);
        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });


    }
}

class PlayMenuItem extends MenuItem {
    public PlayMenuItem() {
        super("PLAY");
    }
}

class LeaderboardMenuItem extends MenuItem {
    public LeaderboardMenuItem() {
        super("LEADERBOARD");
    }
}
class ExitMenuItem extends MenuItem {
    public ExitMenuItem() {
        super("EXIT");
    }
}

class HelpMenuItem extends MenuItem {
    public HelpMenuItem() {
        super("HELP");
    }
}

class ContinueMenuItem extends MenuItem {

    public ContinueMenuItem() {
        super("CONTINUE");
    }
}

class LoadMenuItem extends MenuItem {

    public LoadMenuItem() {
        super("LOAD");
    }
}