package gameUtil;

import java.io.Serializable;

public class Spell extends Card implements Serializable {
    private double range;
    private int damage;
    private SpellName spellName;


    public Spell(int cost,
                 Target target,
                 String cardImageAddress,
                 String soundAddress,
                 String characterImageAddress,
                 Function functionality,
                 SpellName spellName,
                 double range,
                 int damage)
    {
        super(cost,
              target,
              cardImageAddress,
              soundAddress,
              functionality,
              characterImageAddress);
        this.damage = damage;
        this.range = range;
        this.spellName = spellName;
    }

    /**
     * this is a getter
     * @return name of this spell
     */
    @Override
    public Object getName() {
        return null;
    }

    /**
     * this is a getter
     * @return hp of this spell
     */
    @Override
    public int getHP() {
        return Integer.MAX_VALUE;
    }

    /**
     * this is a getter
     * @return damage of this spell
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * this is a getter
     * @return range of this spell
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

    @Override
    public double getHitSpeed() {
        return Integer.MAX_VALUE;
    }

    /**
     * this method is a setter
     * @param spellName is the spellName which will be assign to this spell
     */
    public void setSpellName(SpellName spellName) {
        this.spellName = spellName;
    }
}
