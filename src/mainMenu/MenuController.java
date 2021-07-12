package mainMenu;

import game.GameController;
import gameUtil.BuildingName;
import gameUtil.Card;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import player.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MenuController {
    private MenuModel menuModel;
    private HashMap<Button, Card> linkedButtons;

    @FXML private Button normalBotButton;
    @FXML private TabPane mainMenuTabPane;
    @FXML private ButtonBar buttonBar1;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Button button5;
    @FXML private Button button6;
    @FXML private Button button7;
    @FXML private Button button8;
    @FXML private Button button9;
    @FXML private Button button10;
    @FXML private Button button11;
    @FXML private Button button12;
    @FXML private Label usernameLabel;
    @FXML private Label trophyLabel;
    @FXML private Label levelLabel;

    /**
     * this is a constructor
     */
    public MenuController() {

    }

    /**
     * this method initialize the model and controller and view of the gui for the user
     */
    public void initialize() {
    }

    /**
     * this method initialize the menu controller
     * @param data is the status of the player
     * @throws IOException is the exception
     */
    public void initialize(Status data) throws IOException {
        ArrayList<Button> buttonsList = new ArrayList<>();
        Collections.addAll(buttonsList, button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12);
        linkedButtons = new HashMap<>();
        menuModel = new MenuModel();
        menuModel.setStatus(data);
        usernameLabel.setText(menuModel.getStatus().getUsername());
        trophyLabel.setText(String.valueOf(menuModel.getStatus().getTrophy()));
        levelLabel.setText(String.valueOf(menuModel.getStatus().getLevel()));

        ArrayList<Card> cardsNotInUse = (ArrayList<Card>) menuModel.getStatus().getCards().clone();
        cardsNotInUse.removeAll(menuModel.getStatus().getCardsDeskInUse());
        int i = 0;
        for (var card : menuModel.getStatus().getCardsDeskInUse())
            linkedButtons.put(buttonsList.get(i++), card);
        for (var card : cardsNotInUse)
            linkedButtons.put(buttonsList.get(i++), card);
        for (var button : linkedButtons.keySet()) {
            setButtonImage(linkedButtons.get(button).getCardAddress(), button);
        }

        EventHandler<ActionEvent> normalBotButtonEvent = new EventHandler<>() {
            public void handle(ActionEvent e) {
                try {
                    menuModel.getStatus().getCardsDeskInUse().clear();
                    linkedButtons.remove(button9);
                    linkedButtons.remove(button10);
                    linkedButtons.remove(button11);
                    linkedButtons.remove(button12);
                    menuModel.getStatus().getCardsDeskInUse().addAll(linkedButtons.values());
                    Stage playerStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("/game/game.fxml").openStream());
                    GameController gameController = loader.getController();
                    gameController.initialize(menuModel.getStatus(), menuModel.getStatus());
                    Scene scene = new Scene(root);
                    playerStage.setScene(scene);
                    playerStage.setTitle("Clash Royal");
                    root.setOnMouseClicked(gameController);
                    playerStage.setResizable(false);
                    playerStage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        normalBotButton.setOnAction(normalBotButtonEvent);

        mainMenuTabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                menuModel.resetStacked();
            }
        });

        EventHandler<ActionEvent> cardButtonsAction = new EventHandler<>() {
            public void handle(ActionEvent e) {
                if (menuModel.getStackedCard() == null) {
                    menuModel.setStackedCard(linkedButtons.get((Button) e.getTarget()));
                    menuModel.setStackedButton((Button)e.getTarget());
                } else {
                    Button clicked = (Button)e.getTarget();
                    Card card = linkedButtons.get((Button)e.getTarget());
                    linkedButtons.put(menuModel.getStackedButton(), card);
                    linkedButtons.put(clicked, menuModel.getStackedCard());
                    setButtonImage(menuModel.getStackedCard().getCardAddress(), clicked);
                    setButtonImage(card.getCardAddress(), menuModel.getStackedButton());
                    menuModel.resetStacked();
                }
            }
        };
        buttonsList.forEach(btn -> btn.setOnAction(cardButtonsAction));
    }

    /**
     * this method set an image for a button
     * @param imageAddress is the address of the image which will be put on the button
     * @param button is the button which will be covered  with that image
     */
    private void setButtonImage(String imageAddress, Button button) {
        Image image = new Image(imageAddress);
        ImageView view = new ImageView(image);
        view.setFitHeight(140);
        view.setFitWidth(140);
        button.setGraphic(view);
    }
}
