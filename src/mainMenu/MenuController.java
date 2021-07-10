package mainMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button normalBotButton;

    public void initialize() {
        EventHandler<ActionEvent> normalBotButtonEvent = new EventHandler<>() {
            public void handle(ActionEvent e) {
                try {
                    Stage playerStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = null;
                    root = loader.load(getClass().getResource("/game/game.fxml").openStream());
                    MenuController gameController = loader.getController();
                    Scene scene = new Scene(root);
                    playerStage.setScene(scene);
                    playerStage.setTitle("Clash Royal");
                    playerStage.setResizable(false);
                    playerStage.show();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        normalBotButton.setOnAction(normalBotButtonEvent);
    }
}
