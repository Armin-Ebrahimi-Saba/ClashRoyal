package player;

import game.GameModel;
import gameUtil.AliveTroop;
import gameUtil.Card;
import gameUtil.CardsCollection;
import javafx.geometry.Point2D;

import java.io.Serializable;
import java.util.ArrayList;

public class Status implements Serializable {
    private ArrayList<Card> cards;
    private ArrayList<Card> cardsDeskInUse;
    transient private ArrayList<AliveTroop> aliveAllyTroops;
    transient private ArrayList<AliveTroop> aliveEnemyTroops;
    transient private ArrayList<AliveTroop> troopsInWaitingList;
    transient private Status enemyStatus;
    private final String username;
    private int trophy;
    private int level;
    private int XP;
    transient private int elixirs;
    private final ArrayList<String> history;

    /**
     * this is a constructor
     * @param username is the username of client
     */
    public Status(String username) {
        this.username = username;
        this.trophy = 0;
        this.elixirs = 5;
        this.level = 1;
        this.XP = 0;
        this.history = new ArrayList<>();
        cards = CardsCollection.getCardSet(1);
        cardsDeskInUse = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            cardsDeskInUse.add(cards.get(i));
        resetLists();
    }

    /**
     * this method is a getter
     * @return list of all alive ally troops
     */
    public ArrayList<AliveTroop> getAliveAllyTroops() {
        return aliveAllyTroops;
    }

    /**
     * this method prepares the status for a 2 on 2 game
     */
    public void resetListsFor2On2() {
        this.elixirs = 5;
        aliveAllyTroops = new ArrayList<>();
        aliveEnemyTroops = new ArrayList<>();
        troopsInWaitingList = new ArrayList<>();
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(33.0, 550)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(324.0, 550)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getKingTowers().get(level - 1), new Point2D(125.0, 600)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getKingTowers().get(level - 1), new Point2D(235.0, 600)));
    }

    /**
     * this method reset towers in the list
     */
    public void resetLists() {
        this.elixirs = 5;
        aliveAllyTroops = new ArrayList<>();
        aliveEnemyTroops = new ArrayList<>();
        troopsInWaitingList = new ArrayList<>();
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(33.0, 550)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(324.0, 550)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getKingTowers().get(level - 1), new Point2D(174.0, 600)));
    }

    /**
     * increase number of trophies
     */
    public void increaseTrophy(int num) {
        trophy += num;
    }

    /**
     * this method increase xp of this client
     * @param xp is the xp of this client
     */
    public void increaseXP(int xp) {
        this.XP += xp;
        if (level != getLevel()) {
            cards = CardsCollection.getCardSet(getLevel());
            cardsDeskInUse = new ArrayList<>();
            for (int i = 4; i < 12; i++) {
                cardsDeskInUse.add(CardsCollection.getCardSet(getLevel()).get(i));
            }
            level = getLevel();
        }
    }

    /**
     * this is a getter
     * return amount of xp of the player
     */
    public double getXP() {
        if (XP <= 300)
            return XP/300.0;
        else if (XP <= 800)
            return (XP - 300)/500.0;
        else if (XP <= 1700)
            return (XP - 800)/900.0;
        else if (XP <= 3400)
            return (XP - 1700)/1700.0;
        else if (XP <= 5900)
            return (XP - 3400)/2500.0;
        else
            return 1;
    }

    /**
     * this method is a getter
     * @return number of trophies
     */
    public int getTrophy() {
        return trophy;
    }

    /**
     * this method is a getter
     * @return number of elixirs
     */
    public int getLevel() {
        if (XP <= 300)
            level = 1;
        else if (XP <= 800)
            level = 2;
        else if (XP <= 1700)
            level = 3;
        else if (XP <= 3400)
            level = 4;
        else if (XP <= 5900)
            level = 5;
        else
            level = 6;
        return level;
    }

    /**
     * this method is a getter
     * @return number of elixirs
     */
    public int getElixirs() {
        return elixirs;
    }

    /**
     * this method increase number of elixirs
     */
    public void increaseElixirs() {
        elixirs++;
    }

    /**
     * this method is a getter
     * @return username of the client
     */
    public String getUsername() { return username; }

    /**
     * this method is a getter
     * @return list of cards in the main desk
     */
    public ArrayList<Card> getCardsDeskInUse() {
        return cardsDeskInUse;
    }

    /**
     * this method is a getter
     * @return complete list of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * this method is a setter
     * @param enemyStatus is the status of this enemy
     */
    public void setRelativeEnemyStatus(Status enemyStatus) {
        this.enemyStatus = enemyStatus;
        enemyStatus.getAliveAllyTroops().forEach(troop -> {
            if (!aliveEnemyTroops.contains(troop)) {
                troop.setTroopLocation(getEnemyRelativePoint(troop.getLocation()));
                aliveEnemyTroops.add(troop);
            }
        });
    }

    /**
     * this method is a setter
     * @param enemyStatus is the status of the enemy
     */
    public void setEnemyStatus(Status enemyStatus) {
        if (enemyStatus == null) {
            this.enemyStatus = null;
            return;
        }
        this.aliveEnemyTroops = enemyStatus.getAliveAllyTroops();
    }

    /**
     * this method is a getter
     * @return list of alive troops of enemy
     */
    public ArrayList<AliveTroop> getAliveEnemyTroops() {
        return aliveEnemyTroops;
    }

    /**
     * this method is a getter
     * @return status of the enemy
     */
    public Status getEnemyStatus() {
        return enemyStatus;
    }

    /**
     * this method returns the enemies respective position
     * @return enemies respective position
     */
    public Point2D getEnemyRelativePoint(Point2D point) {
        return new Point2D (point.getX(), 2 * GameModel.MIDDLE_SECOND_LAYER.getY() - point.getY());
    }

    /**
     * this method decrease number of elixirs of this player
     * @param cost is the cost of the card which was used
     */
    public void decreaseElixirs(int cost) {
        elixirs -= cost;
    }

    /**
     * this method add a record to the history
     * @param record is the new record which will be added to the history
     */
    public void addRecord(String record) {
        history.add(record);
    }

    /**
     * this method is a getter
     * @return troops in waiting list
     */
    public ArrayList<AliveTroop> getTroopsInWaitingList() {
        return troopsInWaitingList;
    }

    /**
     * this is a getter
     * @return list of all games with their results
     */
    public ArrayList<String> getHistory() {
        return history;
    }
}




