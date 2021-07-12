package gameUtil;

import javafx.geometry.Point2D;
import game.GameController;

import java.io.Serializable;

public class AliveTroop implements Serializable {
    // indicates the location of the troop
    // indicates the velocity of the troop
    // is the card which this troop is related to
    // is the current hp of the troop
    // is the time left for the spell of rage or a building like cannon or inferno tower
    // is the booster of this troop which is a rage troop
    private Point2D troopLocation;
    private Point2D troopVelocity;
    private final Card card;
    private int HP;
    private boolean isEngaged;
    private AliveTroop engagedEnemy;
    private double timeLeft;
    private AliveTroop booster;

    /**
     * this is constructor
     * @param card  is the card which is indicated to this alive troop
     */
    public AliveTroop(Card card, Point2D initialLocation) {
        this.card = card;
        this.HP = card.getHP();
        this.timeLeft = card.getDamage() * 100;
        this.troopLocation = initialLocation;
    }

    /**
     * this is a setter
     * @param troopLocation is the location of troops
     */
    public void setTroopLocation(Point2D troopLocation) {
        this.troopLocation = troopLocation;
    }

    /**
     * this is a setter
     * @param troopVelocity is the velocity of troops
     */
    public void setTroopVelocity(Point2D troopVelocity) {
        this.troopVelocity = troopVelocity;
    }

    /**
     * this is a getter which returns location of the troop
     */
    public Point2D getTroopLocation() {
        return troopLocation;
    }

    /**
     * this is a getter which returns velocity of the troop
     */
    public Point2D getTroopVelocity() {
        return troopVelocity;
    }

    /**
     * this is a getter
     * @return card of the troop
     */
    public Card getCard() {
        return card;
    }

    /**
     * this method reduce the HP of the troop
     * @param damage is the damage done to the troop
     */
    public void reduceHP(int damage) {
        HP -= damage;
    }

    /**
     * this method is a getter
     * @return true if this troop is engaged with another one
     */
    public boolean isEngaged() {
        return isEngaged;
    }

    /**
     * this is a getter
     * @return the troop which this troop is engaged with
     */
    public AliveTroop getEngagedEnemy() {
        return engagedEnemy;
    }

    /**
     * this method is a setter
     * @param engagedEnemy set the engaged enemy
     */
    public void setEngagedEnemy(AliveTroop engagedEnemy) {
        this.engagedEnemy = engagedEnemy;
        switchAttackCondition();
    }

    /**
     * change condition
     */
    public void switchAttackCondition() {
        isEngaged = !isEngaged;
    }

    /**
     * checks if the troop is alive
     * @return true if it is alive else false
     */
    public boolean isAlive() {
        return HP > 0;
    }

    /**
     * check if the duration of spell is finished
     * @return true if it is finished else false
     */
    public boolean isDurationFinished() {
        timeLeft -= GameController.frameTimeInMilliseconds;
        return timeLeft <= 0;
    }

    /**
     * this method move the alive troop
     */
    public void move() {
        troopLocation.add(troopVelocity);
    }

    /**
     * this method boost the troop
     * @param booster is the rage troop
     */
    public void boost(AliveTroop booster) {
        this.booster = booster;
    }

    /**
     * this method is a getter
     * @return damage of the troop
     */
    public double getDamage() {
        if (booster != null && booster.isDurationFinished()) {
            return card.getDamage();
        }
        return card.getDamage() * 1.4;
    }
}
