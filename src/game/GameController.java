package game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import player.Client;

import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent> {
    @FXML private GameView gameView;
    private GameModel gameModel;
    private Client client1;
    private Client client2;
    private Timer timer;
    private final int FRAMES_PER_SECOND;
    public static long frameTimeInMilliseconds;

    /**
     * this is a constructor
     */
    public GameController() {
        FRAMES_PER_SECOND = 5;
    }

    /**
     * this method initialize the gui
     */
    public void initialize() {
        gameModel = new GameModel(client1.getStatus(), client2.getStatus());
        startTimer();
    }

    /**
     * this method handles all key events
     * @param keyEvent is a key event
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        //TODO
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
