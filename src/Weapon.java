import java.util.Map;

public class Weapon implements Payable {
    private Map data;
    private boolean isMelee;
    private boolean isMissile;
    private boolean isSubcannon;
    private static final String NAME = "#Name";
    private static final String LEVEL_REQUIRED = "Level Required";
    private static final String HIT_POINT = "HP";
    private static final String CAPACITY = "Capacity";
    private static final String MIN_DAMAGE = "Min Damage";
    private static final String MAX_DAMAGE = "Max Damage";
    private static final String RANGE = "Range";
    private static final String WEIGHT = "Weight";
    private static final String SPEED = "Speed";
    private static final String SLOW_DOWN = "Slow";
    private static final String LIMIT = "Limit";
    private static final String REFINE = "Refine";

    public Weapon() {
    }

    public Weapon(Map data) {
        this.data = data;
    }

    public String getName() {
        String name = ((String) data.get(NAME));
//        String level = (String) data.get(LEVEL_REQUIRED);
//        return "L" + level + " - " + name;
        return name;
    }

    public int getHP() {
        return Integer.parseInt((String) data.get(HIT_POINT));
    }

    public int getCapacity() {
        return Integer.parseInt((String) data.get(CAPACITY));
    }

    public int getMinDamage() {
        return Integer.parseInt((String) data.get(MIN_DAMAGE));
    }

    public int getAverageDamage() {
        return (getMinDamage() + getMaxDamage()) / 2;
    }

    public int getMaxDamage() {
        return Integer.parseInt((String) data.get(MAX_DAMAGE));
    }

    public int getRange() {
        return Integer.parseInt((String) data.get(RANGE));
    }

    public int getWeight() {
        return Integer.parseInt((String) data.get(WEIGHT));
    }

    public int getSpeed() {
        return Integer.parseInt((String) data.get(SPEED));
    }

    public int getGold() {
        return Integer.parseInt((String) data.get("Gold"));
    }

    public int getWood() {
        return Integer.parseInt((String) data.get("Wood"));

    }

    public int getOre() {
        return Integer.parseInt((String) data.get("Ore"));
    }

    public int getPlasmaRock() {
        return Integer.parseInt((String) data.get("Plasma Rock"));
    }

    public String getPort() {
        return (String) data.get("Port");
    }

    public String getRequiredLevel() {
        return "" + getLevel();    
    }

    public int getLevel() {
        return Integer.parseInt((String) data.get(LEVEL_REQUIRED));

    }

    public String getSlowdown() {
        return (String)data.get(SLOW_DOWN);
    }

    public int getLimit() {
        return Integer.parseInt((String)data.get(LIMIT));
    }

    public String getRefineLevel() {
        String level = (String)data.get(REFINE);

        if (level == null || (level != null && level.trim().equals("0"))) {
            return "";
        }
        return "+" + level;
    }
    public String toString() {
        String name = (String) data.get(NAME);
        String level = (String) data.get(LEVEL_REQUIRED);
        return "L" + level + " - " + name + getRefineLevel();
    }

    public void setMelee(boolean isMelee) {
        this.isMelee = isMelee;
    }

    public boolean isMelee() {
        return isMelee;
    }

    public boolean isMissile() {
        return isMissile;
    }

    public boolean isSubcannon() {
        return isSubcannon;
    }

    public void setSubcannon(boolean isSubcannon) {
        this.isSubcannon = isSubcannon;
    }
    public void setMissile(boolean isMissile) {
        this.isMissile = isMissile;
    }

    public boolean equals(Object another) {
        if (another instanceof Weapon) {
            Weapon w = (Weapon) another;
//            return w.toString().equals(toString());
            return w.getName().trim().equals(getName().trim()) && w.getRefineLevel().equals(getRefineLevel());
        }
        return false;
    }


    public double getAverageDamagePerWeight() {
        return (double) getAverageDamage() / getWeight();
    }

    public double getHPPerWeight() {
        if (getWeight() == 0) {
            return 0;
        } else
            return (double) getHP() / getWeight();
    }

    public double getSpeedPerWeight() {
        if (getWeight() == 0) {
            return 0;
        } else {
            return (double) getSpeed() / getWeight();
        }
    }

//    public Object getDamagePerWeight() {
//        if (getWeight() == 0) {
//            return 0;
//        }
//        return (double)getA
//
//    }
}