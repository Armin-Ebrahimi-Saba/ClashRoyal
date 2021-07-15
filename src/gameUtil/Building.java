package gameUtil;

import java.io.Serializable;

public class Building extends Card implements Serializable {
    //HP hp of this building
    //lifeTime lifetime of this building
    //hitSpeed hit speed of this building
    //damage is damage of this building
    //maxDamage is maximum damage of this building which is used for inferno tower
    //range range of this building in which it can attack an enemy
    //buildingName is the name of this building
    private final int HP;
    private final int lifeTime;
    private final double hitSpeed;
    private final double range;
    private int damage;
    private int maxDamage;
    private BuildingName buildingName;

    /**
     * this is a constructor
     * @param cost cost of this card
     * @param target is the type of targets which this building can hit
     * @param cardImageAddress asset address
     * @param soundAddress asset address
     * @param functionality special functionality of this building
     * @param HP hp of this building
     * @param lifeTime lifetime of this building
     * @param hitSpeed hit speed of this building
     * @param damage is damage of this building
     * @param range range of this building in which it can attack an enemy
     * @param buildingName is the name of this building
     */
    public Building(int cost,
                    Target target,
                    String cardImageAddress,
                    String soundAddress,
                    Function functionality,
                    int HP,
                    int lifeTime,
                    int damage,
                    double hitSpeed,
                    double range,
                    BuildingName buildingName,
                    String characterImageAddress)
    {
        super(cost,
              target,
              cardImageAddress,
              soundAddress,
              functionality,
              characterImageAddress);
        this.buildingName = buildingName;
        this.HP = HP;
        this.lifeTime = lifeTime;
        this.hitSpeed = hitSpeed;
        this.range = range;
        this.damage = damage;
    }

    /**
     * this is a getter
     * @return type of the building
     */
    @Override
    public BuildingName getName() {
        return buildingName;
    }

    /**
     * this is a getter
     * @return Initial hp of the card
     */
    public int getHP() {
        return HP;
    }

    /**
     * this method is a getter
     * @return damage of this card
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * this method is a getter
     * @return range of this card
     */
    @Override
    public double getRange() {
        return range * 12;
    }

    /**
     * this method checks if troop is area splash
     * @return true if it is area splash else false
     */
    @Override
    public boolean isAreaSplash() {
        return false;
    }

    /**
     * this method is a getter
     * @return lifetime of this building
     */
    public int getLifeTime() {
        return lifeTime;
    }

    /**
     * this method is a getter
     * @return speed of this building which is 0
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * this is a getter
     * @return name of this building
     */
    public BuildingName getBuildingName() {
        return buildingName;
    }

    /**
     * this method is a setter
     * @param buildingName is the name of the building
     */
    public void setBuildingName(BuildingName buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * this method return the maximum damage of thid building
     * @return maximum damage of this building
     */
    public int getMaxDamage() {
        return maxDamage;
    }

    /**
     * this method is a setter
     * @param maxDamage is the maximum damage of this building
     */
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
