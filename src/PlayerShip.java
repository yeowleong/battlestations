import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerShip {
    private Ship ship = new NullShip();
    private Weapon figureHead = new NullWeapon();
    private Weapon hull = new NullWeapon();
    private Weapon stabilizer = new NullWeapon();
    private Weapon sail = new NullWeapon();
    private Weapon engine = new NullWeapon();
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private int gunnery;
    private int navigation;
    private int craft;
    private String name;
    private int characterLevel;
    private String characterType = "";
    public static boolean isOld = true;

//    public boolean isDefender() {
//        return isDefender;
//    }

    public void setDefender(boolean defender) {
        isDefender = defender;
    }

    private boolean isDefender;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public double getArmourReduction() {
       return  (getArmour() * 0.02)/(1.95+getArmour() * 0.06) *1.2;

       // return ((50 / (50.0 / getArmour() + 1)) / 100);
       //return (getArmour()*0.01)/(1+getArmour()*0.01);
    }

    public int getImpliedHP() {
        //return (int) (getArmourReduction() * getHP()) + getHP();
        //HP/(100%-armour %)
        return  (int) (getHP()/(1 - getArmourReduction()));
    }

    public double getArmour() {
        double armour = getRealCraft() / 10.0 + getRealNavigation() / 25.0; 
        if (isDefender) {
            armour += 30;
        }
        return new BigDecimal(armour).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public int getCraft() {
        return craft;
    }

    public void setCraft(int craft) {
        this.craft = craft;
    }

    public int getGunnery() {
        return gunnery;
    }

    public void setGunnery(int gunnery) {
        this.gunnery = gunnery;
    }

    public int getNavigation() {
        return navigation;
    }

    public void setNavigation(int navigation) {
        this.navigation = navigation;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        if (ship != null) {
            this.ship = ship;
        }
    }

    public boolean addWeapon(Weapon weapon) {
        if (weapon != null) {
            int count = 0;
            for (Weapon w : weapons) {
                if  (w.getName().equals(weapon.getName())) {
                    count++;
                }
            }
            if (count < weapon.getLimit()) {
                weapons.add(weapon);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Payable> getAllItems() {
        ArrayList al = new ArrayList();

        al.addAll(weapons);
        if (!(figureHead instanceof NullWeapon)) {
            al.add(figureHead);
        }
        if (!(hull instanceof NullWeapon)) {
            al.add(hull);
        }
        if (!(stabilizer instanceof NullWeapon)) {
            al.add(stabilizer);
        }
        if (!(sail instanceof NullWeapon)) {
            al.add(sail);
        }
        if (!(engine instanceof NullWeapon)) {
            al.add(engine);
        }
        try {
            al.addAll(ship.getAllLevelLowerThan(Integer.parseInt(ship.getLevel().getRequiredLevel())));
        } catch (Exception e) {
        }
//            return (Weapon[]) al.toArray(new Weapon[al.size()]);
        return al;
    }

    public Weapon[] getAllWeapons() {
        return  weapons.toArray(new Weapon[weapons.size()]);
    }

    public void removeWeapon(String name) {
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            if (w.toString().equals(name)) {
                iter.remove();
                return;
            }
        }
    }


    public Weapon getFigureHead() {
        return figureHead;
    }

    public void setFigureHead(Weapon figureHead) {
        if (figureHead != null) {
            this.figureHead = figureHead;
        }
    }

    public Weapon getHull() {
        return hull;
    }

    public void setHull(Weapon hull) {
        if (hull != null) {
            this.hull = hull;
        }
    }

    public Weapon getStabilizer() {
        return stabilizer;
    }

    public void setStabilizer(Weapon stabilizer) {
        if (stabilizer != null) {
            this.stabilizer = stabilizer;
        }
    }

    public Weapon getSail() {
        return sail;
    }

    public void setSail(Weapon sail) {
        if (sail != null) {
            this.sail = sail;
        }
    }

    public Weapon getEngine() {
        return engine;
    }

    public void setEngine(Weapon engine) {
        if (engine != null) {
            this.engine = engine;
        }
    }

    public int getAverageDamagePerVolley() {
        return (getMinDamagePerVolley() + getMaxDamagePerVolley()) / 2;
    }

    
    public int getMinDamagePerVolley() {
        double total = 0;
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            int dmg = w.getMinDamage();
            total += dmg;
            if (w.isMelee()) {
                    total += 0.005 * getRealNavigation() * dmg;
            } else if (w.isMissile()) {
                    total += Math.max(0.005 * getRealGunnery() + 0.0025 * getRealNavigation(), 0.0035 * getRealNavigation()) * dmg;
            } else if (w.isSubcannon()) {
                    total += 0.004 * getRealGunnery() * dmg;
            } else {
                    total += 0.005 * getRealGunnery() * dmg;
            }
        }
        return (int) total;
    }


    public int getMaxDamagePerVolley() {
        double total = 0;
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            int dmg = w.getMaxDamage();
            total += dmg;
            if (w.isMelee()) {
                    total += 0.005 * getRealNavigation() * dmg;
            } else if (w.isMissile()) {
                    total += Math.max(0.005 * getRealGunnery() + 0.0025 * getRealNavigation(), 0.0035 * getRealNavigation()) * dmg;
            } else if (w.isSubcannon()) {
                    total += 0.004 * getRealGunnery() * dmg;
            } else {
                    total += 0.005 * getRealGunnery() * dmg;
            }
        }
        return (int) total;
    }

    public int getMinRange() {
        int min = 0;
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            if (min == 0) {
                min = w.getRange();
            } else {
                min = min > w.getRange() ? w.getRange() : min;
            }
        }
        return min;
    }

    public int getMaxRange() {
        int max = 0;
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            max = max < w.getRange() ? w.getRange() : max;
        }
        return max;
    }

    public int getUsedCapacity() {
        int total = 0;
        for (Iterator iter = weapons.iterator(); iter.hasNext();) {
            Weapon w = (Weapon) iter.next();
            total += w.getWeight();
        }
        return total + figureHead.getWeight()
                + hull.getWeight() + stabilizer.getWeight()
                + sail.getWeight() + engine.getWeight();
    }

    public int getSpeed() {
        return Math.max(50, ship.getSpeed() + figureHead.getSpeed()
                + hull.getSpeed() + stabilizer.getSpeed()
                + sail.getSpeed() + engine.getSpeed() + navigation);
    }

    public int getCapacity() {
        return ship.getCapacity() + figureHead.getCapacity()
                + hull.getCapacity() + stabilizer.getCapacity()
                + sail.getCapacity() + engine.getCapacity();
    }

    public int getHP() {
        double hp = ship.getHP() + figureHead.getHP() + hull.getHP()
                + stabilizer.getHP()
                + sail.getHP() + engine.getHP();
        if (isOld) {
            hp += getRealCraft() * 0.01 * ship.getHP();
        } else {
            hp += getRealCraft() * 0.5 / (100 + getRealCraft() * 0.5) * ship.getHP();
//            hp += (5 * (getRealCraft() / 10) / (100 + getRealCraft() / 10)) * ship.getHP();
        }
        return (int) Math.round(hp);
    }

    public void removeFigureHead() {
        figureHead = new NullWeapon();
    }

    public void removeStabilizer() {
        stabilizer = new NullWeapon();
    }

    public void removeSail() {
        sail = new NullWeapon();
    }

    public void removeHull() {
        hull = new NullWeapon();
    }

    private static final NullWeapon NULL_WEAPON = new NullWeapon();
    public void removeEngine() {
        engine = NULL_WEAPON; //new NullWeapon();
    }

    public void setLevel(Level level) {
        if (level != null) {
            ship.setLevel(level);
        }
    }

    public Level getLevel() {
        return ship.getLevel();
    }

    public void removeShip() {
        ship = new NullShip();
    }

    public String getRequiredLevel() {
        return ship.getRequiredLevel();
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object another) {
        if (another instanceof PlayerShip) {
            PlayerShip w = (PlayerShip) another;
            return w.getName().equals(getName());
        }
        return false;
    }

    public Object clone() {
        PlayerShip p = new PlayerShip();
        //p.setName(getName());
        p.setShip((Ship) ship.clone());
        p.setFigureHead(figureHead);
        p.setHull(hull);
        p.setStabilizer(stabilizer);
        p.setSail(sail);
        p.setEngine(engine);
        p.weapons.addAll(weapons);
        p.gunnery = gunnery;
        p.navigation = navigation;
        p.craft = craft;
        p.name = name;
        p.characterLevel = characterLevel;
        p.characterType = characterType;
        return p;

    }

    public void modifyLevel(int level) {
        int incr = level - characterLevel;
        characterLevel += incr;
        if (CharacterType.COMMANDER.equals(characterType)) {
            gunnery += incr;
            craft += incr;
            navigation += incr;
        } else if (CharacterType.ENGINEER.equals(characterType)) {
            craft += incr;
            if ((incr == 1 && characterLevel % 2 != 0)
                || (incr == -1 && characterLevel % 2 == 0)) {
                craft += incr;
            }
     
        } else if (CharacterType.PIRATE.equals(characterType)) {
            gunnery += incr;
            if ((incr == 1 && characterLevel % 2 != 0)
                || (incr == -1 && characterLevel % 2 == 0)) {
                gunnery += incr;
            }
        } else if (CharacterType.TRADER.equals(characterType)) {
            navigation += incr;
            if ((incr == 1 && characterLevel % 2 != 0)
                || (incr == -1 && characterLevel % 2 == 0)) {
                craft += incr;
            }
        }   else if (CharacterType.EXPLORER.equals(characterType)) {
            navigation += incr;
            if ((incr == 1 && characterLevel % 2 != 0)
                || (incr == -1 && characterLevel % 2 == 0)) {
                navigation += incr;
            }
        }
    }

//    public void decreLevel() {
//
//    }
    
    public void setCharacterLevel(int level) {
        this.characterLevel = level;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterType(String type) {
        this.characterType = type;
    }

    public String getCharacterType() {
        return characterType;
    }

    public double getRealGunnery() {
        double realGunnery = gunnery;
        if (CharacterType.PIRATE.equals(characterType)
                && characterLevel % 2 != 0) {
            realGunnery -= 0.5;
        }
        return realGunnery;
    }

    public int getAllocatedGunnery() {
        if (CharacterType.COMMANDER.equals(characterType)) {
            return characterLevel;
        } else if (CharacterType.PIRATE.equals(characterType)) {
            return (int) Math.round(characterLevel * 1.5);
        }
        return 0;
    }

    public double getRealCraft() {
        double realCraft = craft;
        if (CharacterType.ENGINEER.equals(characterType) || CharacterType.TRADER.equals(characterType)) {
            if (characterLevel % 2 != 0) {
                realCraft -= 0.5;
            }
        }
        return realCraft;
    }

    public int getAllocatedCraft() {
        if (CharacterType.COMMANDER.equals(characterType)) {
            return characterLevel;
        } else if (CharacterType.ENGINEER.equals(characterType)) {
            return (int) Math.round(characterLevel * 1.5);
        } else if (CharacterType.TRADER.equals(characterType)) {
            return (int) Math.round(characterLevel * 0.5);
        }
        return 0;
    }

    public double getRealNavigation() {
        double realNavigation = navigation;
        if (CharacterType.EXPLORER.equals(characterType)
                && characterLevel % 2 != 0) {
            realNavigation -= 0.5;
        }
        return realNavigation;
    }

    public int getAllocatedNavigation() {
        if (CharacterType.TRADER.equals(characterType)) {
            return characterLevel;
        } else if (CharacterType.COMMANDER.equals(characterType)) {
            return characterLevel;
        } else if (CharacterType.EXPLORER.equals(characterType)) {
            return (int) Math.round(characterLevel * 1.5);
        }
        return 0;
    }

    public int getFree() {
        if (CharacterType.COMMANDER.equals(characterType)) {
            return characterLevel * 2;
        }
        return characterLevel * 3;
    }

    public int getTotal() {
        return getFree() + getAllocatedNavigation() + getAllocatedCraft()
                + getAllocatedGunnery();
    }

    public int getAvailable() {
        return getFree()
                - Math.max(0, getCraft() - getAllocatedCraft())
                - Math.max(0, getGunnery() - getAllocatedGunnery())
                - Math.max(0, getNavigation() - getAllocatedNavigation());
    }

//    public double getTotalPoint() {
//        return getCraft() + getNavigation() + getGunnery();
//    }
}
