package game;

import com.sun.javafx.geom.Point2D;
import gameUtil.*;
import player.Status;

import java.util.ArrayList;

import static gameUtil.Target.GROUND;

public class GameModel {
    // is the minimum distance between two enemies until they recognize each other
    // head and tail of left and right bridge
    // status of the both players
    private static final float MINIMUM_DISTANCE = 6.0f;
    private final int BRIDGE_LENGTH = 10;
    private final Point2D LEFT_BRIDGE_HEAD;
    private final Point2D LEFT_BRIDGE_TAIL;
    private final Point2D RIGHT_BRIDGE_HEAD;
    private final Point2D RIGHT_BRIDGE_TAIL;
    private final Status statusPlayer1;
    private final Status statusPlayer2;

    /**
     * this is a constructor
     * @param statusPlayer1 is the first players' status
     * @param statusPlayer2 is the second players' status
     */
    public GameModel(Status statusPlayer1, Status statusPlayer2) {
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
        for (var troop : allyStatus.getAliveTroops()) {
            if (troop.getCard() instanceof Troop) {
                AliveTroop targetTroop = determineTargetInSight(troop, enemyStatus);
                determineVelocity(troop, targetTroop, enemyStatus);
                AliveTroop inRangeEnemy = determineTargetsInRange(troop, enemyStatus);
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
        troopsToBeRemoved.forEach(troop -> statusPlayer1.getAliveTroops().remove(troop));
    }

    private void throwThrowableSpell(Status enemyStatus, AliveTroop throwableSpell) {
        for (var troop: enemyStatus.getAliveTroops()) {
            if (troop.getTroopLocation().distance(throwableSpell.getTroopLocation()) <
                throwableSpell.getCard().getRange())
            {
                troop.reduceHP((int) throwableSpell.getDamage());
            }
        }
    }

    private boolean dropRage(Status allyStatus, AliveTroop rageSpell) {
        for (var troop: allyStatus.getAliveTroops()) {
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
                for (var enemyTroop : enemyStatus.getAliveTroops()) {
                    if ((distance = enemyTroop.getTroopLocation().distance(troop.getTroopLocation())) <
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
                for (var enemyTroop : enemyStatus.getAliveTroops()) {
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
                for (var enemyTroop : targetStatus.getAliveTroops()) {
                    if ((distance = enemyTroop.getTroopLocation().distance(troop.getTroopLocation())) <
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
                for (var enemyTroop : targetStatus.getAliveTroops()) {
                    if (enemyTroop.getCard() instanceof Building &&
                        enemyTroop.getTroopLocation().distance(troop.getTroopLocation()) <
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
        if (targetTroop == null && troopLocation.y > LEFT_BRIDGE_HEAD.y) {
            if (troopLocation.distance(LEFT_BRIDGE_HEAD) >
                troopLocation.distance(RIGHT_BRIDGE_HEAD))
            {
                float cosx = (-troopLocation.x + LEFT_BRIDGE_HEAD.x) /
                        troopLocation.distance(LEFT_BRIDGE_HEAD);
                float sinx = (-troopLocation.y + LEFT_BRIDGE_HEAD.y) /
                        troopLocation.distance(LEFT_BRIDGE_HEAD);

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            } else {
                float cosx = (-troopLocation.x + RIGHT_BRIDGE_HEAD.x) /
                        troopLocation.distance(RIGHT_BRIDGE_HEAD);
                float sinx = (-troopLocation.y + RIGHT_BRIDGE_HEAD.y) /
                        troopLocation.distance(RIGHT_BRIDGE_HEAD);

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            }
        } else if (targetTroop == null && troopLocation.y > LEFT_BRIDGE_TAIL.y) {
            troop.setTroopVelocity(new Point2D(0, speed));
        } else {
            var enemyTower = makeQueryForClosestTower(targetStatus, troop);
            var enemyTowerLocation = enemyTower.getTroopLocation();
            Point2D targetLocation;
            if (targetTroop == null) {
                float cosx = (-troopLocation.x + enemyTowerLocation.x) /
                               troopLocation.distance(enemyTowerLocation);
                float sinx = (-troopLocation.y + enemyTowerLocation.y) /
                               troopLocation.distance(enemyTowerLocation);

                troop.setTroopVelocity(new Point2D(speed * cosx, speed * sinx));
            } else {
                targetLocation = targetTroop.getTroopLocation();
                float cosx = (-troopLocation.x + targetLocation.x) /
                               troopLocation.distance(targetLocation);
                float sinx = (-troopLocation.y + targetLocation.y) /
                               troopLocation.distance(targetLocation);

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
        for (var enemyTroop : targetStatus.getAliveTroops()) {
            if (enemyTroop.getCard() instanceof Building &&
                enemyTroop.getCard().equals(BuildingName.TOWER) &&
                enemyTroop.getTroopLocation().distance(troop.getTroopLocation()) < minimumDistance)
            {
                closestTower = enemyTroop;
                minimumDistance = enemyTroop.getTroopLocation().distance(troop.getTroopLocation());
            }
        }
        return closestTower;
    }

    public Status[] getPlayersStatus() {
        return new Status[]{statusPlayer1, statusPlayer2};
    }
}
