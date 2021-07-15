package player;

import game.GameModel;
import gameUtil.*;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        Thread play = new Thread(() -> {
            Point2D lPoint = new Point2D(144, 22);
            Point2D rPoint = new Point2D(208, 22);
            AliveTroop troop;
            while (isConnectedToGame) {
                int rSumDifference = Math.abs(sumOfRightSideTroops(status.getAliveAllyTroops()) - sumOfRightSideTroops(status.getAliveEnemyTroops()));
                int lSumDifference = Math.abs(sumOfLeftSideTroops(status.getAliveAllyTroops()) - sumOfLeftSideTroops(status.getAliveEnemyTroops()));
                ArrayList<Card> chosenCards = new ArrayList<>();
                for (Card card : status.getCardsDeskInUse()) {
                    if (card.getCost() <= status.getElixirs())
                        chosenCards.add(card);
                }
                if (rSumDifference > lSumDifference) {
                    troop = new AliveTroop(getClosestChoice(chosenCards, lSumDifference, true), lPoint);
                } else {
                    troop = new AliveTroop(getClosestChoice(chosenCards, rSumDifference, false), rPoint);
                }
                status.getAliveAllyTroops().add(troop);
            }
        });
    }

    private Card getClosestChoice(ArrayList<Card> chosenCards, int lSumDifference, boolean isLeft) {
        Card bestCard = null;
        int difference = 1000;
        for (Card card : chosenCards) {
            if (Math.abs(card.getCost() - lSumDifference) < difference) {
                bestCard = card;
                difference = Math.abs(card.getCost() - lSumDifference);
            } else if (Math.abs(card.getCost() - lSumDifference) == difference) {
                boolean isBabyDragon = false;
                for (var troop : status.getAliveEnemyTroops()) {
                    if (troop.getTroopLocation().getX() < (isLeft ? 172 : 500) &&
                        troop.getTroopLocation().getX() > (isLeft ? 0 : 172) &&
                        troop.getCard().getName().equals(TroopName.BABY_DRAGON))
                    {
                        isBabyDragon = true;
                        break;
                    }
                }
                if (isBabyDragon &&
                    card.getTarget().equals(Target.ANY) && !bestCard.getTarget().equals(Target.ANY)) {
                    bestCard = card;
                } else if (card.getCost() < bestCard.getCost()) {
                    bestCard = card;
                }
            }
        }
        return bestCard;
    }

    /**
     * this method return sum of cost of all left side of area troops
     * @param troops is the list of those troops
     * @return sum of those troops
     */
    private int sumOfLeftSideTroops(ArrayList<AliveTroop> troops) {
        AtomicInteger sum = new AtomicInteger();
        troops.forEach(troop -> {
            if (troop.getTroopLocation().getX() < 172) {
                sum.addAndGet(troop.getCard().getCost());
            }
        });
        return sum.get();
    }

    /**
     * this method return sum of cost of all right side of area troops
     * @param troops is the list of those troops
     * @return sum of those troops
     */
    private int sumOfRightSideTroops(ArrayList<AliveTroop> troops) {
        AtomicInteger sum = new AtomicInteger();
        troops.forEach(troop -> {
            if (troop.getTroopLocation().getX() > 172) {
                sum.addAndGet(troop.getCard().getCost());
            }
        });
        return sum.get();
    }

    /**
     * this method runs the bot to play normal
     */
    private void playNormally() {
        Thread play = new Thread(() -> {
            while (isConnectedToGame) {
                Random random = new Random();
                var randomPoint = new Point2D(random.nextInt(475), random.nextInt(330));
                if (!GameModel.isValidCoordination(randomPoint, status.getAliveEnemyTroops())) {
                    AtomicReference<Card> chosenCard = new AtomicReference<>();
                    status.getCardsDeskInUse().forEach(card -> {
                        if (card.getCost() <= status.getElixirs() &&
                            status.getCardsDeskInUse().indexOf(card) > 3)
                        {
                            status.getAliveAllyTroops().add(new AliveTroop(card, randomPoint));
                            chosenCard.set(card);
                        }
                    });
                    if (chosenCard.get() != null) {
                        status.getCardsDeskInUse().remove(chosenCard.get());
                        status.getCardsDeskInUse().add(0, chosenCard.get());
                        status.getAliveAllyTroops().add(new AliveTroop(chosenCard.get(), status.getEnemyRelativePoint(randomPoint)));
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}
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
