package player;

import game.GameModel;
import gameUtil.*;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Bot extends Player {
    private boolean isSmart;
    private boolean isConnectedToGame = false;

    /**
     * this is a constructor
     * @param status is the Status of the bot player
     */
    public Bot(Status status, boolean isSmart) {
        setStatus(status);
        this.isSmart = isSmart;
    }

    /**
     * this method runs the bot to play smart
     */
    public void playSmart() {
        Thread play = new Thread(() -> {
            Point2D lPoint = new Point2D(144, 22);
            Point2D rPoint = new Point2D(208, 22);
            AliveTroop troop;
            while (isConnectedToGame) {
                int rSumDifference = Math.abs(sumOfRightSideTroops(getStatus().getAliveAllyTroops()) - sumOfRightSideTroops(getStatus().getAliveEnemyTroops()));
                int lSumDifference = Math.abs(sumOfLeftSideTroops(getStatus().getAliveAllyTroops()) - sumOfLeftSideTroops(getStatus().getAliveEnemyTroops()));
                ArrayList<Card> chosenCards = new ArrayList<>();
                for (Card card : getStatus().getCardsDeskInUse()) {
                    if (card.getCost() <= getStatus().getElixirs())
                        chosenCards.add(card);
                }
                if (rSumDifference > lSumDifference) {
                    troop = new AliveTroop(getClosestChoice(chosenCards, lSumDifference, true), lPoint);
                } else {
                    troop = new AliveTroop(getClosestChoice(chosenCards, rSumDifference, false), rPoint);
                }
                getStatus().getCardsDeskInUse().remove(troop.getCard());
                getStatus().getCardsDeskInUse().add(0, troop.getCard());
                getStatus().decreaseElixirs(troop.getCard().getCost());
                for (int i = 0; i < (troop.getCard() instanceof Troop ? ((Troop) troop.getCard()).getCount() : 1); i++) {
                    if (i % 2 == 0)
                        getStatus().getTroopsInWaitingList().add(troop);
                    if (i % 2 != 0)
                        getStatus().getTroopsInWaitingList().add(troop);
                }
            }
        });
        play.start();
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
                for (var troop : getStatus().getAliveEnemyTroops()) {
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
    public void playNormally() {
        Thread play = new Thread(() -> {
            try {Thread.sleep(4000);
            } catch (InterruptedException e) {e.printStackTrace();}
            while (isConnectedToGame) {
                try {Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}
                Random random = new Random();
                var randomPoint = new Point2D(random.nextInt(455), random.nextInt(280));
                if (!GameModel.isValidCoordination(randomPoint, getStatus().getAliveEnemyTroops())) {
                    AtomicReference<Card> chosenCard = new AtomicReference<>();
                    getStatus().getCardsDeskInUse().forEach(card -> {
                        if (card.getCost() <= getStatus().getElixirs() &&
                            getStatus().getCardsDeskInUse().indexOf(card) > 3)
                        {
                            chosenCard.set(card);
                        }
                    });
                    if (chosenCard.get() != null) {
                        var troop = new AliveTroop(chosenCard.get(), getStatus().getEnemyRelativePoint(randomPoint));
                        getStatus().getCardsDeskInUse().remove(troop.getCard());
                        getStatus().getCardsDeskInUse().add(0, troop.getCard());
                        getStatus().decreaseElixirs(troop.getCard().getCost());
                        for (int i = 0; i < (troop.getCard() instanceof Troop ? ((Troop) troop.getCard()).getCount() : 1); i++) {
                            if (i % 2 == 0)
                                getStatus().getTroopsInWaitingList().add(troop);
                            if (i % 2 != 0)
                                getStatus().getTroopsInWaitingList().add(troop);
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}
            }
        });
        play.start();
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

    /* @return true if it is smart else false. */
    public boolean isSmart() {
        return isSmart;
    }
}
