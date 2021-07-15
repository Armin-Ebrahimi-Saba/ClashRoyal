package gameUtil;

import java.io.Serializable;

public class Troop extends Card implements Serializable {
    private boolean isAreaSplash;
    private int range;
    private int count;
    private int HP;
    private int damage;
    private int speed;
    private double hitSpeed;
    private TroopName troopName;

    public Troop(
                 int cost,
                 Target target,
                 String cardImageAddress,
                 String soundAddress,
                 Function functionality,
                 boolean isAreaSplash,
                 String characterImageAddress,
                 int range,
                 int count,
                 int Hp,
                 int damage,
                 int speed,
                 double hitSpeed)
    {
        super(  cost,
                target,
                cardImageAddress,
                soundAddress,
                functionality,
                characterImageAddress);
        this.isAreaSplash = isAreaSplash;
        this.range = range;
        this.count = count;
        this.HP = Hp;
        this.damage = damage;
        this.speed = speed;
        this.hitSpeed = hitSpeed;
    }

    /**
     * this is a getter
     * @return speed state of the troop
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * this is a getter
     * @return name of the troop
     */
    @Override
    public TroopName getName() {
        return troopName;
    }

    /**
     * this is a getter
     * @return Initial hp of the card
     */
    @Override
    public int getHP() {
        return HP;
    }

    /**
     * this is a getter
     * @return damage power of the troop
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * this is a getter
     * @return damage range of the troop
     */
    @Override
    public double getRange() {
        return range * 14;
    }

    /**
     * this method checks if troop is area splash
     * @return true if it is area splash else false
     */
    @Override
    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    /**
     * this method is a getter
     * @return hit speed of the troop
     */
    @Override
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * this method is a setter
     * @param troopName this is name of the troop
     */
    public void setTroopName(TroopName troopName) {
        this.troopName = troopName;
    }

    /**
     * this method is a getter
     * @return count of this type troop
     */
    public int getCount() {
        return count;
    }
}
