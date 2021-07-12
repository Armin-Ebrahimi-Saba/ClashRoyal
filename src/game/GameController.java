package game;

import gameUtil.AliveTroop;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import player.Client;
import player.Status;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<MouseEvent> {
    private GameModel gameModel;
    private Client client1;
    private Client client2;
    private ArrayList<Button> listedButtons;
    private Timer timer;
    private final int FRAMES_PER_SECOND = 5;
    public static long frameTimeInMilliseconds;
    private GameView gameView;
    @FXML private Button card4Button;
    @FXML private Button card3Button;
    @FXML private Button card2Button;
    @FXML private Button card1Button;
    @FXML private AnchorPane firstLayer;
    @FXML private AnchorPane secondLayerPane;
    @FXML private Button tower1;
    @FXML private Button tower2;
    @FXML private Button tower3;
    @FXML private Button tower4;
    @FXML private Button king1;
    @FXML private Button king2;

    /**
     * this is a constructor
     */
    public GameController() {
    }

    /**
     * this method initialize the gui
     */
    public void initialize() {
    }

    /**
     * this method initialize game
     */
    public void initialize(Status status1, Status status2) {
        secondLayerPane = new AnchorPane();
        secondLayerPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0)");
        gameModel = new GameModel();
        gameModel.initialize(status1, status2);
        gameView = new GameView();
        gameView.initialize(gameModel);
        secondLayerPane.getChildren().add(gameView);
        listedButtons = new ArrayList<>();
        listedButtons.add(card1Button);
        listedButtons.add(card2Button);
        listedButtons.add(card3Button);
        listedButtons.add(card4Button);
        for (int i = 0; i < 4; i++)
            setButtonImage(listedButtons.get(i), gameModel.getPlayersStatus()[0].getCardsDeskInUse().get(4 + i).getCardAddress());
        client1 = new Client(status1);
        client2 = new Client(status2);
        firstLayer.getChildren().add(gameView);
        startTimer();

        EventHandler<ActionEvent> cardButtonAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameModel.setStack((Button)actionEvent.getTarget(),
                gameModel.getPlayersStatus()[0].getCardsDeskInUse().get(4 + listedButtons.indexOf((Button)actionEvent.getTarget())));
            }
        };
        listedButtons.forEach(button -> button.setOnAction(cardButtonAction));
    }

    /**
     * this method set an image for a button
     * @param cardButton is the card linked to the button
     * @param imageAddress is address of the image of the card
     */
    private void setButtonImage(Button cardButton, String imageAddress) {
        Image image = new Image(imageAddress);
        ImageView view = new ImageView(image);
        view.setFitHeight(100);
        view.setFitWidth(100);
        cardButton.setGraphic(view);
    }

    /**
     * this method handles all key events
     * @param mouseEvent is a mouse event
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        Point2D mouseLocation = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        System.out.println("mouse clicked first");
        System.out.println(gameModel.getStackedButton());
        System.out.println(gameModel.getStackedCard());
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (gameModel.getStackedButton() != null &&
                gameModel.getStackedCard() != null &&
                gameModel.isValidCoordination(mouseLocation))
            {
                var cardDeskInUse = gameModel.getPlayersStatus()[0].getCardsDeskInUse();
                var aliveAllyTroops = gameModel.getPlayersStatus()[0].getAliveAllyTroops();
                aliveAllyTroops.add(new AliveTroop(gameModel.getStackedCard(), mouseLocation));
                cardDeskInUse.remove(gameModel.getStackedCard());
                cardDeskInUse.add(0, gameModel.getStackedCard());
                for (int i = 0; i < 4; i++)
                    setButtonImage(listedButtons.get(i), cardDeskInUse.get(4 + i).getCardAddress());
                gameModel.resetStack();
                System.out.println("mouse clicked second");
            }
        }
        mouseEvent.consume();
    }

    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(() -> update());
            }
        };
        GameController.frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * updates the game
     */
    private void update() {
        gameModel.step();
        gameView.update(gameModel);
    }

    /**
     * this method sets the game players
     */
    public void setClients(Client client1, Client client2) {
        this.client1 = client1;
        this.client2 = client2;
    }
}
