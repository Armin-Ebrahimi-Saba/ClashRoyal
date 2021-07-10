package gameUtil;

public class Spell extends Card{
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

    @Override
    public Object getName() {
        return null;
    }

    @Override
    public int getHP() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getRange() {
        return range;
    }
}
