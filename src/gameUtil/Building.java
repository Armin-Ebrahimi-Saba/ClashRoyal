package gameUtil;

public class Building extends Card{
    private final int HP;
    private final int lifeTime;
    private final double hitSpeed;
    private final double range;
    private int damage;
    private final BuildingName buildingName;


    /**
     * this is a constructor
     * @param cost
     * @param target
     * @param cardImageAddress
     * @param soundAddress
     * @param functionality
     * @param HP
     * @param lifeTime
     * @param hitSpeed
     * @param range
     * @param buildingName
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

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getRange() {
        return range;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public BuildingName getBuildingName() {
        return buildingName;
    }
}
