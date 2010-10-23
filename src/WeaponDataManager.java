import java.util.*;

public class WeaponDataManager {
    private static HashMap map = new HashMap();

    static {
        WeaponDataManager.retrieveAll("hulls");
        WeaponDataManager.retrieveAll("engines");
        WeaponDataManager.retrieveAll("figureheads");
        WeaponDataManager.retrieveAll("stabilizers");
        WeaponDataManager.retrieveAll("sails");
        WeaponDataManager.retrieveAll("cannons");
        WeaponDataManager.retrieveAll("subcannons");
        WeaponDataManager.retrieveAll("melee");
        WeaponDataManager.retrieveAll("missiles");
    }

    public static Weapon[] retrieveAll(String category) {
        Weapon[] weapons = (Weapon[]) map.get(category);
        if (weapons != null) {
            return weapons;
        }
        boolean isMelee = false;
        boolean isMissile = false;
        boolean isSubcannons = false;
        if (category.equals("melee")) {
            isMelee = true;
        }
        if (category.equals("missiles")) {
            isMissile = true;
        }
        if (category.equals("subcannons")) {
            isSubcannons = true;
        }
        List all = new ArrayList();
        Map[] arr = GenericDataManager.retrieveAll(category);
        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
            Weapon s = new Weapon(arr[i]);
            s.setMelee(isMelee);
            s.setMissile(isMissile);
            s.setSubcannon(isSubcannons);
            all.add(s);
        }
        weapons = (Weapon[]) all.toArray(new Weapon[all.size()]);
        map.put(category, weapons);
        return weapons;
    }

    public static Weapon retrieve(String name,String level) {

        for (Iterator iter = map.values().iterator(); iter.hasNext();) {
            Weapon[] all = (Weapon[]) iter.next();
            for (int i = 0; i < all.length; i++) {
                if (all[i].getName().trim().equals(name.trim())
                        &&  all[i].getRefineLevel().trim().equals(level.trim())) {
                    return all[i];
                }
            }
        }
        return null;
    }

    public static Weapon retrieve(String name) {
        for (Iterator iter = map.values().iterator(); iter.hasNext();) {
            Weapon[] all = (Weapon[]) iter.next();
            for (int i = 0; i < all.length; i++) {
                if (all[i].getName().trim().equals(name.trim())
                        && all[i].getRefineLevel().equals("")) {
                    return all[i];
                }
            }
        }
        return null;
    }
}