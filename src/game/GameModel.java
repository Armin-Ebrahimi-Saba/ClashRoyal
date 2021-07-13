package game;

import  javafx.geometry.Point2D;
import gameUtil.*;
import javafx.scene.control.Button;
import player.Status;

import java.util.ArrayList;

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
    private static final float MINIMUM_DISTANCE = 20.0f;
    public static final Point2D MIDDLE_SECOND_LAYER = new Point2D(213.5, 272.5);
    private final int BRIDGE_LENGTH = 14;
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
     * @param statusPlayer1 is player1 status
     * @param statusPlayer2 is player2 status
     */
    public void initialize(Status statusPlayer1, Status statusPlayer2) {
        LEFT_BRIDGE_HEAD = new Point2D(20, 292);
        LEFT_BRIDGE_TAIL = new Point2D(20, 308);
        RIGHT_BRIDGE_HEAD = new Point2D(330, 292);
        RIGHT_BRIDGE_TAIL = new Point2D(330, 308);
        this.statusPlayer1 = statusPlayer1;
        this.statusPlayer2 = statusPlayer2;
    }

    /**
     * this player takes a step
     */
    protected void step() {
        statusPlayer1.setRelativeEnemyStatus(statusPlayer2);
        handlePlayer(statusPlayer1, statusPlayer1.getAliveEnemyTroops());
        statusPlayer2.setEnemyStatus(statusPlayer1);
        handlePlayer(statusPlayer2, statusPlayer2.getAliveEnemyTroops());
    }

    /**
     * handle the players step
     * @param allyStatus is the allys' status
     * @param aliveEnemyTroops is the enemy's status
     */
    private void handlePlayer(Status allyStatus, ArrayList<AliveTroop> aliveEnemyTroops) {
        ArrayList<AliveTroop> troopsToBeRemoved = new ArrayList<>();
        for (var troop : allyStatus.getAliveAllyTroops()) {
            if (!(troop.getCard() instanceof Spell)) {
                AliveTroop targetTroop = determineTargetInSight(troop, aliveEnemyTroops);
                determineVelocityDirection(troop, targetTroop, aliveEnemyTroops);
                AliveTroop inRangeEnemy = determineTargetsInRange(troop, aliveEnemyTroops);
                if (inRangeEnemy != null)
                        attackEnemiesInRange(inRangeEnemy , troop);
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
    private AliveTroop determineTargetsInRange(AliveTroop troop, ArrayList<AliveTroop> aliveEnemyTroops) {
        float minimumDistance = Integer.MAX_VALUE;
        float distance = 0;
        AliveTroop closestEnemy = null;
        if (troop.getCard() instanceof Troop) {
            if (!((Troop) troop.getCard()).getName().equals(TroopName.GIANT)) {
                for (var enemyTroop : aliveEnemyTroops) {
                    if ((distance = (float) enemyTroop.getTroopLocation().distance(troop.getTroopLocation())) <
                            troop.getCard().getRange() && !(troop.getCard() instanceof Spell))
                    {
                        if (enemyTroop.getCard() instanceof Troop)
                        {
                            if (((Troop) enemyTroop.getCard()).getName().equals(TroopName.BABY_DRAGON)
                                    && troop.getCard().getTarget().equals(GROUND))
                                continue;
                        }
                        if (distance < minimumDistance) {
                            closestEnemy = enemyTroop;
                            minimumDistance = distance;
                        }
                    }
                }
            } else {
                for (var enemyTroop : aliveEnemyTroops) {
                    if (enemyTroop.getCard() instanceof Building &&
                        enemyTroop.getTroopLocation().distance(troop.getTroopLocation()) <
                                    troop.getCard().getRange())
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
     * this method damage the enemy in range
     * @param targetTroop is the targeted enemy
     * @param troop is the attacker
     */
    private void attackEnemiesInRange(AliveTroop targetTroop, AliveTroop troop) {
        if (!troop.isEngaged()) {
            targetTroop.reduceHP((int) troop.getDamage());
            troop.setEngagedEnemy(targetTroop);
        } else
            troop.getEngagedEnemy().reduceHP((int) troop.getDamage());

        if (troop.getEngagedEnemy() != null &&
           !troop.getEngagedEnemy().isAlive())
            troop.setEngagedEnemy(null);
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
                            if (((Troop) enemyTroop.getCard()).getName().equals(TroopName.BABY_DRAGON)
                                  && troop.getCard().getTarget().equals(GROUND));

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
        if (targetTroop == null && troopLocation.getY() > LEFT_BRIDGE_TAIL.getY()) {
            double x = 0;
            if (troopLocation.distance(LEFT_BRIDGE_TAIL) <
                troopLocation.distance(RIGHT_BRIDGE_TAIL))
                x = LEFT_BRIDGE_TAIL.subtract(troopLocation).angle(new Point2D(0, -1));
            else
                x = RIGHT_BRIDGE_TAIL.subtract(troopLocation).angle(new Point2D(0, -1));

            troop.setTroopVelocityDirection(new Point2D(sin(x), cos(x)));
        } else if (targetTroop == null &&
                   troopLocation.getY() < LEFT_BRIDGE_TAIL.getY() &&
                   troopLocation.getY() > LEFT_BRIDGE_HEAD.getY())
        {
            troop.setTroopVelocityDirection(new Point2D(0, -1));
        } else {
            Point2D targetLocation;
            if (targetTroop != null)
                targetLocation = targetTroop.getTroopLocation();
            else
                targetLocation = makeQueryForClosestTower(aliveEnemyTroops, troop);

            double x = targetLocation != null ?
                       targetLocation.subtract(troopLocation).angle(new Point2D(0, -1)) : 0;
            troop.setTroopVelocityDirection(new Point2D(sin(x), cos(x)));
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
    public static boolean  isValidCoordination(Point2D point2D) {
        return true;
    }
}
