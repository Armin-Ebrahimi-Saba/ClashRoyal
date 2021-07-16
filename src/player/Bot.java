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
            try {Thread.sleep(4000);
            } catch (InterruptedException e) {e.printStackTrace();}
            Point2D lPoint = new Point2D(70, 60);
            Point2D rPoint = new Point2D(270, 60);
            AliveTroop troop = null;
            while (isConnectedToGame) {
                try {Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}
                int rSumDifference = Math.abs(sumOfRightSideTroops(getStatus().getAliveAllyTroops()) - sumOfRightSideTroops(getStatus().getAliveEnemyTroops()));
                int lSumDifference = Math.abs(sumOfLeftSideTroops(getStatus().getAliveAllyTroops()) - sumOfLeftSideTroops(getStatus().getAliveEnemyTroops()));
                ArrayList<Card> chosenCards = new ArrayList<>();
                for (Card card : getStatus().getCardsDeskInUse().subList(4, 7)) {
                    if (card.getCost() <= getStatus().getElixirs())
                        chosenCards.add(card);
                }
                if (rSumDifference > lSumDifference) {
                    var card = getClosestChoice(chosenCards, lSumDifference, true);
                    if (card != null)
                        troop = new AliveTroop(card, lPoint);
                } else {
                    var card = getClosestChoice(chosenCards, rSumDifference, false);
                    if (card != null)
                        troop = new AliveTroop(card, rPoint);
                }
                if (troop != null) {
                    var card = troop.getCard();
                    var point = troop.getLocation();
                    if (card != null)
                        addCardToWaitingList(point, card);
                }
                troop = null;
            }
        });
        play.start();
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
                        var card = chosenCard.get();
                        addCardToWaitingList(randomPoint, card);
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
     * this method get the card closest choice for smart bot
     * @param chosenCards is the list of choices for card
     * @param sumDifference is an effective parameter
     * @param isLeft if the troop is going to be on left or right
     * @return the best card for playing
     */
    private Card getClosestChoice(ArrayList<Card> chosenCards, int sumDifference, boolean isLeft) {
        Card bestCard = null;
        int difference = 1000;
        for (Card card : chosenCards) {
            if (Math.abs(card.getCost() - sumDifference) < difference) {
                bestCard = card;
                difference = Math.abs(card.getCost() - sumDifference);
            } else if (Math.abs(card.getCost() - sumDifference) == difference) {
                boolean isBabyDragon = false;
                for (var troop : getStatus().getAliveEnemyTroops()) {
                    if (troop.getLocation().getX() < (isLeft ? 172 : 330) &&
                            troop.getLocation().getX() > (isLeft ? 0 : 172) &&
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
            if (troop.getLocation().getX() < 172) {
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
            if (troop.getLocation().getX() > 172) {
                sum.addAndGet(troop.getCard().getCost());
            }
        });
        return sum.get();
    }

    /**
     * this method add troop to waiting list
     * @param point is the point of the initial location of the troop
     * @param card is the card which is assigned to the troop
     */
    private void addCardToWaitingList(Point2D point, Card card) {
        getStatus().getCardsDeskInUse().remove(card);
        getStatus().getCardsDeskInUse().add(0, card);
        getStatus().decreaseElixirs(card.getCost());
        for (int i = 0; i < (card instanceof Troop ? ((Troop) card).getCount() : 1); i++) {
            if (i % 2 == 0)
                getStatus().getTroopsInWaitingList().add(new AliveTroop(card,
                        getStatus().getEnemyRelativePoint(new Point2D(point.getX() + 20 * i, point.getY()))));
            if (i % 2 != 0)
                getStatus().getTroopsInWaitingList().add(new AliveTroop(card,
                        getStatus().getEnemyRelativePoint(new Point2D(point.getX(), point.getY() + 20 * i))));
        }
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
