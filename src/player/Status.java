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
    final transient private ArrayList<AliveTroop> aliveAllyTroops;
    transient private ArrayList<AliveTroop> aliveEnemyTroops;
    transient private String enemyUsername;
    private final String username;
    private int trophy;
    private int level;
    private int XP;
    private int elixirs;

    /**
     * this is a constructor
     * @param username is the username of client
     */
    public Status(String username) {
        this.username = username;
        this.trophy = 0;
        this.elixirs = 0;
        this.level = 1;
        this.XP = 0;
        aliveAllyTroops = new ArrayList<>();
        cards = CardsCollection.getCardSet(1);
        cardsDeskInUse = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            cardsDeskInUse.add(cards.get(i));
        aliveEnemyTroops = new ArrayList<>();
        resetTowers();
    }

    public ArrayList<AliveTroop> getAliveAllyTroops() {
        return aliveAllyTroops;
    }

    /**
     * this method reset towers in the list
     */
    public void resetTowers() {
        aliveAllyTroops.clear();
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(21.0, 473)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getArcherTowers().get(level - 1), new Point2D(324.0, 473)));
        aliveAllyTroops.add(new AliveTroop(CardsCollection.getKingTowers().get(level - 1), new Point2D(166.0, 567)));
    }

    /**
     * increase number of trophies
     */
    public void increaseTrophy(int num) {
        trophy += num;
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
     * this method increase xp of this client
     * @param xp is the xp of this client
     */
    public void increaseXP(int xp) {
        this.XP += xp;
    }

    /**
     * this method increase level of this client
     */
    public void increaseLevel() {
        XP = 0;
        level += 1;
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
        enemyStatus.getAliveAllyTroops().forEach(troop -> {
            troop.setTroopLocation(getEnemyRelativePoint(troop.getTroopLocation()));
            aliveEnemyTroops.clear();
            aliveEnemyTroops.add(troop);
        });
        enemyUsername = enemyStatus.getUsername();
    }

    /**
     * this method is a setter
     * @param enemyStatus is the status of the enemy
     */
    public void setEnemyStatus(Status enemyStatus) {
        this.aliveEnemyTroops = enemyStatus.getAliveAllyTroops();
        this.enemyUsername = enemyStatus.getUsername();
    }

    /**
     * this method is a getter
     * @return list of alive troops of enemy
     */
    public ArrayList<AliveTroop> getAliveEnemyTroops() {
        return aliveEnemyTroops;
    }

    /**
     * this is a getter
     * @return username of the enemy
     */
    public String getEnemyUsername() {
        return enemyUsername;
    }

    /**
     * this method returns the enemies respective position
     * @return enemies respective position
     */
    private Point2D getEnemyRelativePoint(Point2D point) {
        return new Point2D (point.getX(), 2 * GameModel.MIDDLE_SECOND_LAYER.getY() - point.getY());
    }

//    /**
//     * get a clone from this status
//     */
//    public Status clone() {
//        return new Status()
//    }
}




