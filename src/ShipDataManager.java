import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Jan 4, 2008
 * Time: 10:17:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShipDataManager {
    private static Ship[] ships;
    private static HashMap<String,String> mappings = new HashMap<String,String>();

//    static {
//        mappings.put("Windrider","Sail Boat");
//        mappings.put("Albatross","Assault Gunboat");
//        mappings.put("Enterprise", "Trade Vessel");
//        mappings.put("Manta", "Scout Cutter");
//        mappings.put("Aegis", "Defense Corvette");
//        mappings.put("Vanguard", "Imperial Corvette");
//        mappings.put("Hammerhead", "Smuggler Sloop");
//        mappings.put("Merchant O' War", "Merchant Galleon");
//        mappings.put("Obsidian Destroyer", "Vanguard Frigate");
//        mappings.put("Trident", "Trident Ironclad");
//        mappings.put("Endeavor", "Heavy Freighter");
//        mappings.put("Ballista", "Missile Frigate");
//        mappings.put("Goliath", "Obsidian Destroyer");
//        mappings.put("Katana", "Brigantine");
//        mappings.put("Claymore", "Royo Destroyer");
//        mappings.put("Guardian 35", "Aegis Heavy Cruiser");
//        mappings.put("Murakamo DX", "Caramusal");
//        mappings.put("Penetrator 3000", "Assault Destroyer");
//    }
    public static Ship retrieve(String name) {
        String newName = mappings.get(name);
        if (newName != null) {
            name = newName;
        }
        if (ships == null) {
            retrieveAll();
        }
        for (int i = 0; i < ships.length; i++) {
            if (ships[i].getName().equals(name)) {
                return (Ship) ships[i].clone();
            }
        }
        return null;
    }

    public static Ship[] retrieveAll() {
        if (ships != null) {
            return ships;
        }
        HashMap map = new HashMap();
        ArrayList all = new ArrayList();
        Map[] arr = GenericDataManager.retrieveAll("ships2");
        for (int i = 0; i < arr.length; ++i) {
            Ship s = (Ship) map.get(arr[i].get(Ship.NAME));
          
            if (s == null) {
                s = new Ship(arr[i]);
                map.put(s.getName(), s);
                all.add(s);
            } else {
                s.addData(arr[i]);
            }
        }

        ships = (Ship[]) all.toArray(new Ship[all.size()]);
        Arrays.sort(ships, new Comparator() {

            public int compare(Object o1, Object o2) {
                Ship s1 = (Ship)o1;
                Ship s2 = (Ship)o2;
                int l1 = Integer.parseInt(s1.getLevel().getRequiredLevel());
                int l2 = Integer.parseInt(s2.getLevel().getRequiredLevel());
                if (l1 == l2) {
                    return s1.getName().compareTo(s2.getName());
                }
                return l2 - l1;
            }
        });
        return ships;
    }

}
