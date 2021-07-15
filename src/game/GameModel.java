package game;

import  javafx.geometry.Point2D;
import gameUtil.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import player.Player;
import player.Status;
import server.Server;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static gameUtil.Target.GROUND;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class GameModel {
    // is the minimum distance between two enemies until they recognize each other
    // middle point of the map
    // head and tail of left and right bridge
    // status of the both players
    // this button is stacked for some purposes (changing its image)
    // this card is stacked which might be transferred to AliveTroop
    private static final float MINIMUM_DISTANCE = 45.0f;
    public static final Point2D MIDDLE_SECOND_LAYER = new Point2D(213.5,300);
    private Point2D LEFT_BRIDGE_HEAD;
    private Point2D LEFT_BRIDGE_TAIL;
    private Point2D RIGHT_BRIDGE_HEAD;
    private Point2D RIGHT_BRIDGE_TAIL;
    private Status statusPlayer1;
    private Status statusPlayer2;
    private Button stackedButton;
    private Card stackedCard;


    /**
     * this is a constructor
     */
    public GameModel() {
        stackedCard = null;
        stackedCard = null;
    }

    /**
     * this method put stackedButton and stackedCard into null state
     */
    public void resetStack() {
        stackedCard = null;
        stackedButton = null;
    }

    /**
     * this method initialize the gameModel
     * @param status1 is player1 status
     * @param status2 is player2 status
     */
    public void initialize(Status status1 , Status status2) {
        LEFT_BRIDGE_HEAD = new Point2D(37, 295);
        LEFT_BRIDGE_TAIL = new Point2D(37, 325);
        RIGHT_BRIDGE_HEAD = new Point2D(335, 295);
        RIGHT_BRIDGE_TAIL = new Point2D(335, 325);
        this.statusPlayer1 = status1;
        this.statusPlayer2 = status2;
    }

    /**
     * this player takes a step
     */
    protected void step() {
        statusPlayer1.setRelativeEnemyStatus(statusPlayer2);
        handleGame(statusPlayer1, statusPlayer1.getAliveEnemyTroops());
        statusPlayer2.setEnemyStatus(statusPlayer1);
        handleGame(statusPlayer2, statusPlayer2.getAliveEnemyTroops());
        removeDeadTroops(statusPlayer1.getAliveAllyTroops());
        removeDeadTroops(statusPlayer1.getAliveEnemyTroops());
        removeDeadTroops(statusPlayer2.getAliveAllyTroops());
        removeDeadTroops(statusPlayer2.getAliveEnemyTroops());
        statusPlayer2.getAliveAllyTroops().forEach(AliveTroop::updateReloadTimer);
        statusPlayer1.getAliveAllyTroops().forEach(AliveTroop::updateReloadTimer);
    }

    /**
     * this method remove dead players
     */
    private void removeDeadTroops(ArrayList<AliveTroop> aliveTroops) {
        ArrayList<AliveTroop> troopsToBeRemoved = new ArrayList<>();
        aliveTroops.forEach(troop -> {
            if (!troop.isAlive())
                troopsToBeRemoved.add(troop);
        });
        aliveTroops.removeAll(troopsToBeRemoved);
    }

    /**
     * handle the players step
     * @param allyStatus is the allys' status
     * @param aliveEnemyTroops is the enemy's status
     */
    private void handleGame(Status allyStatus, ArrayList<AliveTroop> aliveEnemyTroops) {
        ArrayList<AliveTroop> troopsToBeRemoved = new ArrayList<>();
        for (var troop : allyStatus.getAliveAllyTroops()) {
            if (!(troop.getCard() instanceof Spell)) {
                if (!troop.isEngaged() || troop.getCard().isAreaSplash()) {
                    AliveTroop targetTroop = determineTargetInSight(troop, aliveEnemyTroops);
                    determineVelocityDirection(troop, targetTroop, aliveEnemyTroops);
                    ArrayList<AliveTroop> inRangeEnemy = determineTargetsInRange(troop, aliveEnemyTroops);
                    troop.setInRangeEnemies(inRangeEnemy);
                }
                if (troop.getInRangeEnemies() != null)
                        troop.getInRangeEnemies().forEach(enemyTroop ->
                                attackEnemiesInRange(enemyTroop, troop));
                removeDeadTroops(troop.getInRangeEnemies());
                troop.move();
            } else {
                var card = troop.getCard();
                if (card.getName().equals(SpellName.RAGE)) {
                    if (dropRage(allyStatus, troop))
                        troopsToBeRemoved.add(troop);
                } else if (card.getName().equals(SpellName.FIRE_BALL)) {
                    throwThrowableSpell(aliveEnemyTroops, troop);
                    troopsToBeRemoved.add(troop);
                } else {
                    throwThrowableSpell(aliveEnemyTroops, troop);
                    troopsToBeRemoved.add(troop);
                }
            }
        }
        allyStatus.getAliveAllyTroops().forEach(troop -> troop.getInRangeEnemies().forEach(enemy -> System.out.println(troop.getCard().getName() + " target is : " + troop)));
        System.out.println(allyStatus.getAliveAllyTroops());
        troopsToBeRemoved.forEach(troop -> allyStatus.getAliveAllyTroops().remove(troop));
    }

    /**
     * this method throw a throwable spell
     * @param aliveEnemyTroops is the enemy status
     * @param throwableSpell is a spell with can be thrown
     */
    private void throwThrowableSpell(ArrayList<AliveTroop> aliveEnemyTroops, AliveTroop throwableSpell) {
        for (var troop: aliveEnemyTroops) {
            if (troop.getTroopLocation().distance(throwableSpell.getTroopLocation()) <
                throwableSpell.getCard().getRange())
            {
                troop.reduceHP((int) throwableSpell.getDamage());
            }
        }
    }

    /**
     * drop the rage spell
     * @param allyStatus is status of ally
     * @param rageSpell is the rage spell troop
     * @return true if its duration is finished
     */
    private boolean dropRage(Status allyStatus, AliveTroop rageSpell) {
        for (var troop: allyStatus.getAliveAllyTroops()) {
            if (troop.getTroopLocation().distance(rageSpell.getTroopLocation()) <
                    rageSpell.getCard().getRange())
            {
                troop.boost(rageSpell);
            }
        }
        return rageSpell.isDurationFinished();
    }

    /**
     * this method find the enemies in attack range
     * @return enemy in range for being under attack
     * @param troop is the allys' troop
     * @param aliveEnemyTroops is the status of the enemy
     */
    private ArrayList<AliveTroop> determineTargetsInRange(AliveTroop troop, ArrayList<AliveTroop> aliveEnemyTroops) {
        float minimumDistance = Integer.MAX_VALUE;
        float distance = 0;
        ArrayList<AliveTroop> inRangeEnemies = new ArrayList<>();
        if (troop.getCard() instanceof Troop) {
            if (!((Troop) troop.getCard()).getName().equals(TroopName.GIANT)) {
                for (var enemyTroop : aliveEnemyTroops) {
                    int size = 40;
                    if (enemyTroop.getCard() instanceof Building && (
                        enemyTroop.getCard().getName().equals(BuildingName.KING_TOWER) ||
                        enemyTroop.getCard().getName().equals(BuildingName.ARCHER_TOWER)))
                        size = 60;
                    if (isIntersected(troop.getTroopLocation(), size, size, enemyTroop.getTroopLocation(), troop.getCard().getRange())
                            && !(troop.getCard() instanceof Spell))
                    {
                        distance = (float) enemyTroop.getTroopLocation().distance(
                                new Point2D(troop.getTroopLocation().getX() + size/2.0, troop.getTroopLocation().getY() + size/2.0));
                        if (enemyTroop.getCard() instanceof Troop)
                        {
                            if (((Troop) enemyTroop.getCard()).getName().equals(TroopName.BABY_DRAGON)
                                    && troop.getCard().getTarget().equals(GROUND))
                                continue;
                        }
                        if (distance < minimumDistance) {
                            if (troop.getCard().isAreaSplash())
                                inRangeEnemies.add(enemyTroop);
                            else {
                                if (inRangeEnemies.size() == 0)
                                    inRangeEnemies.add(enemyTroop);
                                else inRangeEnemies.set(0, enemyTroop);
                            }
                            minimumDistance = distance;
                        }
                    }
                }
            } else {
                for (var enemyTroop : aliveEnemyTroops) {
                    int size = 40;
                    if (enemyTroop.getCard() instanceof Building && (
                            enemyTroop.getCard().getName().equals(BuildingName.KING_TOWER) ||
                            enemyTroop.getCard().getName().equals(BuildingName.ARCHER_TOWER)))
                        size = 60;
                    if (enemyTroop.getCard() instanceof Building &&
                        isIntersected(troop.getTroopLocation(), size, size, enemyTroop.getTroopLocation(), troop.getCard().getRange()))
                    {
                        if (distance < minimumDistance) {
                            if (inRangeEnemies.size() == 0)
                                inRangeEnemies.add(enemyTroop);
                            else inRangeEnemies.set(0, enemyTroop);

                            minimumDistance = distance;
                        }
                    }
                }
            }
        }
        return inRangeEnemies;
    }

    /**
     * this method damage the enemy in range
     * @param targetTroop is the targeted enemy
     * @param troop is the attacker
     */
    private void attackEnemiesInRange(AliveTroop targetTroop, AliveTroop troop) {
        if(targetTroop != null && troop.isReadyForDamaging()) {
            targetTroop.reduceHP(troop.getDamage());
        }
    }

    /**
     * find the closest enemy to the ally troop
     * @param troop is the ally troop
     * @param aliveEnemyTroops the status of the enemy
     * @return targeted enemy troop
     */
    private AliveTroop determineTargetInSight(AliveTroop troop, ArrayList<AliveTroop> aliveEnemyTroops) {
        float minimumDistance = Integer.MAX_VALUE;
        float distance = 0;
        AliveTroop closestEnemy = null;
        if (troop.getCard() instanceof Troop) {
            if (!((Troop) troop.getCard()).getName().equals(TroopName.GIANT)) {
                for (var enemyTroop : aliveEnemyTroops) {
                    if ((distance = (float) enemyTroop.getTroopLocation().distance(troop.getTroopLocation())) <
                            MINIMUM_DISTANCE)
                    {
                        if (enemyTroop.getCard() instanceof Building ||
                            enemyTroop.getCard() instanceof Troop)
                        {
                            if (enemyTroop.getCard() instanceof Troop &&
                                ((Troop) enemyTroop.getCard()).getName().equals(TroopName.BABY_DRAGON) &&
                                troop.getCard().getTarget().equals(GROUND)) continue;
                            else if (distance < minimumDistance) {
                                closestEnemy = enemyTroop;
                                minimumDistance = distance;
                            }
                        }
                    }
                }
            } else {
                for (var enemyTroop : aliveEnemyTroops) {
                    if (enemyTroop.getCard() instanceof Building &&
                       (distance = (float)enemyTroop.getTroopLocation().distance(troop.getTroopLocation())) < MINIMUM_DISTANCE)
                    {
                        if (distance < minimumDistance) {
                            closestEnemy = enemyTroop;
                            minimumDistance = distance;
                        }
                    }
                }
            }
        }
        return closestEnemy;
    }

    /**
     * this method determines the velocity of a troop
     * @param troop is the ally troop
     * @param targetTroop is the targeted troop
     * @param aliveEnemyTroops is the targets' status
     */
    private void determineVelocityDirection(AliveTroop troop, AliveTroop targetTroop, ArrayList<AliveTroop> aliveEnemyTroops) {
        var troopLocation = troop.getTroopLocation();
        if (troop.getCard().getName().equals(BuildingName.ARCHER_TOWER) ||
            troop.getCard().getName().equals(BuildingName.KING_TOWER))
        {
            if (troop.getTroopLocation().getY() > LEFT_BRIDGE_HEAD.getY())
                troop.setTroopVelocityDirection(new Point2D(0, -1));
            else
                troop.setTroopVelocityDirection(new Point2D(0, 1));
            return;
        }
        if (targetTroop == null && troopLocation.getY() >= LEFT_BRIDGE_TAIL.getY()) {
            double x = 0;
            if (!troop.getCard().getName().equals(TroopName.BABY_DRAGON)) {
                if (troopLocation.distance(LEFT_BRIDGE_TAIL) <
                        troopLocation.distance(RIGHT_BRIDGE_TAIL)) {
                    x = (LEFT_BRIDGE_TAIL.getX() > troopLocation.getX() ? 1 : -1) * LEFT_BRIDGE_TAIL.subtract(troopLocation).angle(new Point2D(0, -1));
                } else {
                    x = (RIGHT_BRIDGE_TAIL.getX() > troopLocation.getX() ? 1 : -1) * RIGHT_BRIDGE_TAIL.subtract(troopLocation).angle(new Point2D(0, -1));
                }
            } else {
                x = 0;
            }
            troop.setTroopVelocityDirection(new Point2D(sin(x/180 * Math.PI), -cos(x/180 * Math.PI)));
        } else if (targetTroop == null &&
                   troopLocation.getY() < LEFT_BRIDGE_TAIL.getY() &&
                   troopLocation.getY() >= LEFT_BRIDGE_HEAD.getY() &&
                  (Math.abs(troopLocation.getX() - LEFT_BRIDGE_HEAD.getX()) < 5 ||
                   Math.abs(troopLocation.getX() - RIGHT_BRIDGE_HEAD.getX()) < 5))
        {
            troop.setTroopVelocityDirection(new Point2D(0, -1));
        } else {
            Point2D targetLocation;
            if (targetTroop != null)
                targetLocation = targetTroop.getTroopLocation();
            else
                targetLocation = makeQueryForClosestTower(aliveEnemyTroops, troop);

            double x = ((targetLocation != null ? targetLocation.getX() : 0) > troopLocation.getX() ? 1:-1) * (targetLocation != null ?
                       targetLocation.subtract(troopLocation).angle(new Point2D(0, -1)) : 0);
            troop.setTroopVelocityDirection(new Point2D(sin(x/180 * Math.PI), -cos(x/180 * Math.PI)));
        }
    }

    /**
     * this method look fo the closest tower to the ally
     * @param aliveEnemyTroops is the targets' status
     * @param troop is the troops status
     * @return a Tower for being targeted
     */
    private Point2D makeQueryForClosestTower(ArrayList<AliveTroop> aliveEnemyTroops, AliveTroop troop) {
        AliveTroop closestTower = null;
        float minimumDistance = Integer.MAX_VALUE;
        for (var enemyTroop : aliveEnemyTroops) {
            if (enemyTroop.getCard() instanceof Building &&
                (enemyTroop.getCard().getName().equals(BuildingName.ARCHER_TOWER) ||
                enemyTroop.getCard().getName().equals(BuildingName.KING_TOWER)) &&
                enemyTroop.getTroopLocation().distance(troop.getTroopLocation()) < minimumDistance)
            {
                closestTower = enemyTroop;
                minimumDistance = (float) enemyTroop.getTroopLocation().distance(troop.getTroopLocation());
            }
        }
        return closestTower != null ? closestTower.getTroopLocation() : null;
    }

    /**
     * this is a getter
     * @return an array of status of the both players
     */
    public Status[] getPlayersStatus() {
        return new Status[]{statusPlayer1, statusPlayer2};
    }

    /**
     * this method put stackedButton and stackedCard into a specific state
     * @param button is the button which will be stacked
     * @param card is the card which will be stacked
     */
    public void setStack(Button button, Card card) {
        this.stackedCard = card;
        this.stackedButton = button;
    }

    /**
     * this is a getter
     * @return a button which was stacked
     */
    public Button getStackedButton() {
        return stackedButton;
    }

    /**
     * this is a getter
     * @return a card which was stacked
     */
    public Card getStackedCard() {
        return stackedCard;
    }

    /**
     * this method checks if the click point is a valid point for putting soldier
     * @param point2D is the coordination of that point
     */
    public static boolean  isValidCoordination(Point2D point2D, ArrayList<AliveTroop> enemyAliveTroops) {
        AtomicBoolean isLeftTowerAlive = new AtomicBoolean(false);
        AtomicBoolean isRightTowerAlive = new AtomicBoolean(false);
        enemyAliveTroops.forEach(troop -> {
            if (troop.getCard().getName().equals(BuildingName.ARCHER_TOWER)) {
                if (troop.getTroopLocation().getX() < 150)
                    isLeftTowerAlive.set(true);
                else
                    isRightTowerAlive.set(true);
            }
        });
        if (isLeftTowerAlive.get() &&
            ShapeHolder.getLeftRectangle().contains(point2D))
            return false;
        if (isRightTowerAlive.get() &&
            ShapeHolder.getRightRectangle().contains(point2D))
            return false;
        if (ShapeHolder.getTopRectangle().contains(point2D))
            return false;
        return true;
    }

    /**
     * this method checks for the intersection
     * @param point1 is the top left of the troop which has gotten offended
     * @param width width of the  troop
     * @param height height of the troop
     * @param point2 is the top left of the offender
     * @param radius is the range of the offender
     * @return if offender will attack defender
     */
    private boolean isIntersected(Point2D point1, int width, int height, Point2D point2, double radius) {
        Circle circle = new Circle(point1.getX() + 25, point1.getY() + 25, radius);
        Rectangle rectangle = new Rectangle(point2.getX(), point2.getY(), width, height);
        return !Shape.intersect(circle, rectangle).getBoundsInLocal().isEmpty();
    }
}

/* this class hold shapes related to this games restricted ares. */
class ShapeHolder {
    private static double Y1 = 200;
    private static double X1 = 213.5;
    private static double height= 130;
    private static Rectangle topRectangle = new Rectangle(0, 0, 2 * X1, Y1); // this area is always restricted.
    private static Rectangle leftRectangle = new Rectangle(0, Y1, X1, height); // this area is not always restricted.
    private static Rectangle rightRectangle = new Rectangle(X1, Y1, X1, height); // this area is not always restricted.
    private static final Color color = new Color(255, 0, 0, (float) 0.4); // this color is for indicating restricted areas.

    static {
        topRectangle.setFill(color);
        leftRectangle.setFill(color);
        rightRectangle.setFill(color);
    }

    /**
     * this is a getter
     * @return top rectangle shape
     */
    public static Rectangle getTopRectangle() {
        return topRectangle;
    }

    /**
     * this is a getter
     * @return left rectangle shape
     */
    public static Rectangle getLeftRectangle() {
        return leftRectangle;
    }

    /**
     * this is a getter
     * @return right rectangle shape
     */
    public static Rectangle getRightRectangle() {
        return rightRectangle;
    }
}
//TODO babydragon movement
//TODO infernoTower damaging