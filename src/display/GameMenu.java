package display;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameMenu {

    public Parent buildMainMenu() {
        Pane root = new Pane();

        root.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("resources/rats bg.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title(" R A T S ");
        title.setTranslateX(75);
        title.setTranslateY(200);

        MenuBox vbox = new MenuBox(
                new LeaderboardMenuItem(),
                new PlayMenuItem(),
                new ExitMenuItem(),
                new HelpMenuItem(),
                new ContinueMenuItem(),
                new LoadMenuItem());
        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        root.getChildren().addAll(title, vbox);

        return root;
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(250, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }
}
