package display;

import javafx.scene.Parent;

public class MainMenu extends GameMenu {
    public Parent buildMainMenu() {

        MenuBox menuBox = new MenuBox(
                new LeaderboardMenuItem(),
                new PlayMenuItem(),
                new ExitMenuItem(),
                new HelpMenuItem(),
                new ContinueMenuItem(),
                new LoadMenuItem());

        return buildMenu(menuBox);
    }
}
