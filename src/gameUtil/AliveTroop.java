package gameUtil;

import javafx.geometry.Point2D;
import game.GameController;

import java.io.Serializable;
import java.util.ArrayList;

public class AliveTroop implements Serializable {
    // indicates the location of the troop
    // indicates the velocity of the troop
    // is the card which this troop is related to
    // is the current hp of the troop
    // is the time left for the spell of rage or a building like cannon or inferno tower
    // is the booster of this troop which is a rage troop
    // is the amount of time which this troop was engaged with another troop
    private Point2D troopLocation;
    private Point2D troopVelocityDirection;
    private final Card card;
    private int HP;
    private ArrayList<AliveTroop> inRangeEnemies = new ArrayList<>();
    private double timeLeft;
    private AliveTroop booster;
    private double hitTimer;
    private double engagedTime;

    /**
     * this is constructor
     * @param card  is the card which is indicated to this alive troop
     */
    public AliveTroop(Card card, Point2D initialLocation) {
        this.engagedTime = 0;
        this.hitTimer = 0;
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
     * @param troopVelocityDirection is the velocity of troops
     */
    public void setTroopVelocityDirection(Point2D troopVelocityDirection) {
        this.troopVelocityDirection = troopVelocityDirection;
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
    public Point2D getTroopVelocityDirection() {
        return troopVelocityDirection;
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
    public void reduceHP(double damage) {
        HP -= damage;
    }

    /**
     * this method is a getter
     * @return true if this troop is engaged with another one
     */
    public boolean isEngaged() {
        if (inRangeEnemies.size() == 0) {
            engagedTime = 0;
            return false;
        } else {
            engagedTime += 0.2;
            return true;
        }

    }

    /**
     * this is a getter
     * @return the troop which this troop is engaged with
     */
    public ArrayList<AliveTroop> getInRangeEnemies() {
        return inRangeEnemies;
    }

    /**
     * this method is a setter
     * @param inRangeEnemies set the engaged enemy
     */
    public void setInRangeEnemies(ArrayList<AliveTroop> inRangeEnemies) {
        this.inRangeEnemies = inRangeEnemies;
//        inRangeEnemies.forEach(troop -> {
//            if (!this.inRangeEnemies.contains(troop))
//                this.inRangeEnemies.add(troop);
//        });
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
        if (card instanceof Troop && !isEngaged()) {
            double x = troopLocation.getX();
            double y = troopLocation.getY();
            var speed = troopVelocityDirection.multiply(((Troop) card).getSpeed() * 0.8 * (booster != null ? 1.4:1));
            booster = null;
            troopLocation = new Point2D(x + speed.getX(), y + speed.getY());
        }
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
        if (booster != null && !booster.isDurationFinished()) {
            if (card.getName().equals(BuildingName.INFERNO_TOWER))
                return (card.getDamage() + ((((Building)card).getMaxDamage() - card.getDamage())/3.0) * engagedTime) * 1.4;
            return card.getDamage() * 1.4;
        }
        if (card.getName().equals(BuildingName.INFERNO_TOWER))
            return card.getDamage() + ((((Building)card).getMaxDamage() - card.getDamage())/3.0) * engagedTime;
        return card.getDamage();
    }

    /**
     * this method is a getter
     * @return hp of this troop
     */
    public int getHP(){
        return HP;
    }

    /**
     * this method add to the hitTimer
     */
    public void updateReloadTimer() {
        if(hitTimer > 0)
            hitTimer -= 0.2;
    }

    /**
     * this method indicates weather troop is ready for attacking or not
     * @return true if it is ready for attacking otherwise false
     */
    public boolean isReadyForDamaging() {
        if (hitTimer <= 0) {
            hitTimer = 1/card.getHitSpeed();
            return true;
        }
        return false;
    }
}
