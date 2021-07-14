package player;

import game.GameModel;
import gameUtil.AliveTroop;
import javafx.geometry.Point2D;

import java.util.Random;

public class Bot extends Player {
    private Status status;
    private boolean isSmart;
    private boolean isConnectedToGame = false;

    /**
     * this is a constructor
     * @param status is the Status of the bot player
     */
    public Bot(Status status, boolean isSmart) {
        this.status = status;
        this.isSmart = isSmart;
        if (isSmart)
            playSmart();
        else
            playNormally();
    }

    /**
     * this method runs the bot to play smart
     */
    private void playSmart() {

    }

    /**
     * this method runs the bot to play normal
     */
    private void playNormally() {
        Thread play = new Thread(() -> {
            while (isConnectedToGame) {
                Random random = new Random();
                var randomPoint = new Point2D(random.nextInt(400), random.nextInt(330));
                if (GameModel.isValidCoordination(randomPoint)) {
                    status.getCardsDeskInUse().forEach(card -> {
                        if (card.getCost() <= status.getElixirs()) {
                            status.getAliveAllyTroops().add(new AliveTroop(card, randomPoint));
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * this acknowledge that bot was connected from the game
     */
    public void connectToGame() {
        isConnectedToGame = true;
    }

    /**
     * this acknowledge that bot was disconnected from the game
     */
    public void disconnectFromGame() {
        isConnectedToGame = false;
    }
}
