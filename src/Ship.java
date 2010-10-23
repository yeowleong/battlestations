import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Ship {
    public static final String NAME = "#Ship";
    public static final String HIT_POINT = "HP";
    public static final String SPEED = "Speed";
    public static final String NUM_SLOT = "Slots";
    public static final String CAPACITY = "Capacity";
    public static final String LEVEL_REQUIRED = "Level Required";
    public static final String UPGRADE_LEVEL = "Upgrade Level";
    public static final String MEDAL = "Medal";
    public static final String PORT = "Port";

    private Map data = new HashMap();
    private Map current;
    private ArrayList dataList = new ArrayList();

    public Ship() {
    }

    public Ship(Map m) {
        addData(m);
        current = m;
    }

    public void addData(Map m) {
        if (m != null) {
            dataList.add(m);
            data.put(m.get(UPGRADE_LEVEL), m);
        }
    }

    public Level[] getAllLevel() {
        ArrayList all = new ArrayList();
        for (Iterator iter = dataList.listIterator(); iter.hasNext();) {
            Map m = (Map) iter.next();
            String upgradeLevel = (String) m.get(UPGRADE_LEVEL);
            String requiredLevel = (String) m.get(LEVEL_REQUIRED);
            Level l = new Level(upgradeLevel, requiredLevel);
            all.add(l);
        }

        return (Level[]) all.toArray(new Level[all.size()]);
    }

    public Map getAllLevelAsUpgrade() {
        Map all = new HashMap();
        for (Iterator iter = dataList.listIterator(); iter.hasNext();) {
            Map m = (Map) iter.next();
            ShipUpgrade upgrade = new ShipUpgrade(m);
            String level = (String) m.get(UPGRADE_LEVEL);
//            System.out.println("XYZ ="+ level);
            all.put(level, upgrade);
        }
        return all;
    }

    public ArrayList getAllLevelLowerThan(int level) {
        ArrayList all = new ArrayList();
        for (Iterator iter = dataList.listIterator(); iter.hasNext();) {
            Map m = (Map) iter.next();
            int requiredLevel = Integer.parseInt((String) m.get(LEVEL_REQUIRED));
            if (requiredLevel <= level) {
                ShipUpgrade upgrade = new ShipUpgrade(m);
                all.add(upgrade);
            }
        }
        return all;
    }

    public Level getLevel() {
        String upgradeLevel = (String) current.get(UPGRADE_LEVEL);
        String requiredLevel = (String) current.get(LEVEL_REQUIRED);
        return new Level(upgradeLevel, requiredLevel);
    }

    public void setLevel(Level level) {
        current = (Map) data.get(level.getUpgradeLevel());
    }

    public String getName() {
        return (String) current.get(NAME);
    }

    public int getHP() {
        return Integer.parseInt((String) current.get(HIT_POINT));
    }

    public int getSpeed() {
        return Integer.parseInt((String) current.get(SPEED));
    }

    public int getNumWeaponSlots() {
        return Integer.parseInt((String) current.get(NUM_SLOT));
    }

    public int getCapacity() {
        return Integer.parseInt((String) current.get(CAPACITY));
    }

    public String getRequiredLevel() {
        return (String) current.get(LEVEL_REQUIRED);
    }

    public String getPort() {
        return (String) current.get(PORT);
    }
    public boolean equals(Object another) {
        if (another instanceof Ship) {
            Ship s = (Ship) another;
            return s.getName().equals(getName());
        }
        return false;
    }


    public Object clone() {
        Ship s = new Ship(current); //necessary???
        s.dataList = dataList;
        s.data = data;
        return s;
    }

    public String toString() {
        Map base = (Map) data.get("0");
//        System.out.println(getName() + "-" + base);
        String level = (String) base.get(LEVEL_REQUIRED);
        return "L" + level + " - " + getName().trim();
    }
}
