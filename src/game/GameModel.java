package game;

import javafx.geometry.Point2D;
import gameUtil.*;
import javafx.scene.control.Button;
import player.Status;

import java.util.ArrayList;

import static gameUtil.Target.GROUND;

public class GameModel {
    // is the minimum distance between two enemies until they recognize each other
    // middle point of the map
    // head and tail of left and right bridge
    // status of the both players
    // this button is stacked for some purposes (changing its image)
    // this card is stacked which might be transferred to AliveTroop
    private static final float MINIMUM_DISTANCE = 6.0f;
    private static final Point2D MIDDLE_SECOND_LAYER = new Point2D(213.5, 272.5);
    private final int BRIDGE_LENGTH = 10;
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
        LEFT_BRIDGE_HEAD = new Point2D(50, 150);
        LEFT_BRIDGE_TAIL = new Point2D(50, 200);
        RIGHT_BRIDGE_HEAD = new Point2D(250, 150);
        RIGHT_BRIDGE_TAIL = new Point2D(250, 200);
        this.statusPlayer1 = statusPlayer1;
        this.statusPlayer2 = statusPlayer2;
    }

    /**
     * this player takes a step
     */
    protected void step() {
        handlePlayer(statusPlayer1, statusPlayer2);
        handlePlayer(statusPlayer2, statusPlayer1);
    }

    /**
     * handle the players step
     * @param allyStatus is the allys' status
     * @param enemyStatus is the enemy's status
     */
    private void handlePlayer(Status allyStatus, Status enemyStatus) {
        ArrayList<AliveTroop> troopsToBeRemoved = new ArrayList<>();
        for (var troop : allyStatus.getAliveAllyTroops()) {
            if (troop.getCard() instanceof Troop) {
                AliveTroop targetTroop = determineTargetInSight(troop, enemyStatus);
                determineVelocity(troop, targetTroop, enemyStatus);
                AliveTroop inRangeEnemy = determineTargetsInRange(troop, enemyStatus);
                if (inRangeEnemy != null)
                    attackEnemiesInRange(inRangeEnemy , troop, enemyStatus);
                troop.move();
            } else if (troop.getCard() instanceof Spell) {
                var card = troop.getCard();
                if (card.getName().equals(SpellName.RAGE)) {
                    if (dropRage(allyStatus, troop))
                        troopsToBeRemoved.add(troop);
                } else if (card.getName().equals(SpellName.FIRE_BALL)) {
                    throwThrowableSpell(enemyStatus, troop);
                    troopsToBeRemoved.add(troop);
                } else {
                    throwThrowableSpell(enemyStatus, troop);
                    troopsToBeRemoved.add(troop);
                }
            }
        }
        troopsToBeRemoved.forEach(troop -> statusPlayer1.getAliveAllyTroops().remove(troop));
    }

    /**
     * this method throw a throwable spell
     * @param enemyStatus is the enemy status
     * @param throwableSpell is a spell with can be thrown
     */
    private void throwThrowableSpell(Status enemyStatus, AliveTroop throwableSpell) {
        for (var troop: enemyStatus.getAliveAllyTroops()) {
            if (getEnemyPoint(troop.getTroopLocation()).distance(throwableSpell.getTroopLocation()) <
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
     * @param enemyStatus is the status of the enemy
     */
    private AliveTroop determineTargetsInRange(AliveTroop troop, Status enemyStatus) {
        float minimumDistance = Integer.MAX_VALUE;
        float distance = 0;
        AliveTroop closestEnemy = null;
        if (troop.getCard() instanceof Troop) {
            if (!((Troop) troop.getCard()).getName().equals(TroopName.GIANT)) {
                for (var enemyTroop : enemyStatus.getAliveAllyTroops()) {
                    if ((distance = (float) getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation())) <
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
                for (var enemyTroop : enemyStatus.getAliveAllyTroops()) {
                    if (enemyTroop.getCard() instanceof Building &&
                        getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation()) <
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
     * @param statusPlayer2 is the status of the player which owns the damaged troop
     */
    private void attackEnemiesInRange(AliveTroop targetTroop, AliveTroop troop, Status statusPlayer2) {
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
     * @param targetStatus is the status of the enemy
     * @return targeted enemy troop
     */
    private AliveTroop determineTargetInSight(AliveTroop troop, Status targetStatus) {
        float minimumDistance = Integer.MAX_VALUE;
        float distance = 0;
        AliveTroop closestEnemy = null;
        if (troop.getCard() instanceof Troop) {
            if (!((Troop) troop.getCard()).getName().equals(TroopName.GIANT)) {
                for (var enemyTroop : targetStatus.getAliveAllyTroops()) {
                    if ((distance = (float) getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation())) <
                            MINIMUM_DISTANCE)
                    {
                        if (!(enemyTroop.getCard() instanceof Spell) &&
                              enemyTroop.getCard() instanceof Troop)
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
                for (var enemyTroop : targetStatus.getAliveAllyTroops()) {
                    if (enemyTroop.getCard() instanceof Building &&
                        getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation()) <
                        MINIMUM_DISTANCE)
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
     * @param targetStatus is the targets' status
     */
    private void determineVelocity(AliveTroop troop, AliveTroop targetTroop, Status targetStatus) {
        var troopLocation = troop.getTroopLocation();
        int speed = ((Troop) troop.getCard()).getSpeed();
        if (targetTroop == null && troopLocation.getY() > LEFT_BRIDGE_HEAD.getY()) {
            if (troopLocation.distance(LEFT_BRIDGE_HEAD) >
                troopLocation.distance(RIGHT_BRIDGE_HEAD))
            {
                float cosx = (float) ((-troopLocation.getX() + LEFT_BRIDGE_HEAD.getX()) /
                                        troopLocation.distance(LEFT_BRIDGE_HEAD));
                float sinx = (float) ((-troopLocation.getY() + LEFT_BRIDGE_HEAD.getY()) /
                                        troopLocation.distance(LEFT_BRIDGE_HEAD));

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            } else {
                float cosx = (float) ((-troopLocation.getX() + RIGHT_BRIDGE_HEAD.getX()) /
                                        troopLocation.distance(RIGHT_BRIDGE_HEAD));
                float sinx = (float) ((-troopLocation.getY() + RIGHT_BRIDGE_HEAD.getY()) /
                                        troopLocation.distance(RIGHT_BRIDGE_HEAD));

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            }
        } else if (targetTroop == null && troopLocation.getY() > LEFT_BRIDGE_TAIL.getY()) {
            troop.setTroopVelocity(new Point2D(0, speed));
        } else {
            var enemyTower = makeQueryForClosestTower(targetStatus, troop);
            var enemyTowerLocation = enemyTower.getTroopLocation();
            Point2D targetLocation;
            if (targetTroop == null) {
                float cosx = (float) ((-troopLocation.getX() + enemyTowerLocation.getX()) /
                                               troopLocation.distance(enemyTowerLocation));
                float sinx = (float) ((-troopLocation.getY() + enemyTowerLocation.getY()) /
                                               troopLocation.distance(enemyTowerLocation));

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            } else {
                targetLocation = getEnemyPoint(targetTroop.getTroopLocation());
                float cosx = (float) ((-troopLocation.getX() + getEnemyPoint(targetLocation).getX()) /
                                               troopLocation.distance(getEnemyPoint(targetLocation)));
                float sinx = (float) ((-troopLocation.getY() + getEnemyPoint(targetLocation).getY()) /
                                               troopLocation.distance(getEnemyPoint(targetLocation)));

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            }
        }
    }

    /**
     * this method look fo the closest tower to the ally
     * @param targetStatus is the targets' status
     * @param troop is the troops status
     * @return a Tower for being targeted
     */
    private AliveTroop makeQueryForClosestTower(Status targetStatus, AliveTroop troop) {
        AliveTroop closestTower = null;
        float minimumDistance = Integer.MAX_VALUE;
        for (var enemyTroop : targetStatus.getAliveAllyTroops()) {
            if (enemyTroop.getCard() instanceof Building &&
                (enemyTroop.getCard().equals(BuildingName.ARCHER_TOWER) ||
                enemyTroop.getCard().equals(BuildingName.KING_TOWER)) &&
                getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation()) < minimumDistance)
            {
                closestTower = enemyTroop;
                minimumDistance = (float) getEnemyPoint(enemyTroop.getTroopLocation()).distance(troop.getTroopLocation());
            }
        }
        return closestTower;
    }

    /**
     * this is a getter
     * @return an array of status of the both players
     */
    public Status[] getPlayersStatus() {
        return new Status[]{statusPlayer1, statusPlayer2};
    }

    /**
     * this method returns the enemies respective position
     * @return enemies respective position
     */
    private Point2D getEnemyPoint(Point2D point) {
        return new Point2D (point.getX(), 2 * MIDDLE_SECOND_LAYER.getY() - point.getY());
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
    public boolean  isValidCoordination(Point2D point2D) {
        return true;
    }
}
