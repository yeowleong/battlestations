import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Feb 29, 2008
 * Time: 9:44:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerShipDataManager {
    public static String getWeaponString(Weapon p) {
        if (p.getRefineLevel().equals("")) {
            return p.getName();
        } else {
            return p.getName() + "|" + p.getRefineLevel();
        }
    }

    public static void saveBuilds(ArrayList builds) {
        PrintWriter out = null;
        try {
            File f = new File("conf");
            f.mkdir();
            out = new PrintWriter(new FileWriter("conf/build.csv"));
            for (int i = 0; i < builds.size(); i++) {
                PlayerShip pShip = (PlayerShip) builds.get(i);
                out.print("2,");
                out.print(pShip.getCharacterType() + ",");
                out.print(pShip.getCharacterLevel() + ",");
                out.print(pShip.getName() + ",");
                out.print(pShip.getShip().getName() + ",");
                Level l = pShip.getLevel();
//                System.out.println("level is =" + l);
                out.print(l.getUpgradeLevel() + ",");
                out.print(l.getRequiredLevel() + ",");
                out.print(pShip.getCraft() + ",");
                out.print(pShip.getGunnery() + ",");
                out.print(pShip.getNavigation() + ",");
                out.print(pShip.getFigureHead().getName() + ",");
                out.print(pShip.getSail().getName() + ",");
                out.print(pShip.getStabilizer().getName() + ",");
                out.print(pShip.getHull().getName() + ",");
                out.print(pShip.getEngine().getName() + ",");
                Weapon[] weapons = pShip.getAllWeapons();
                for (int j = 0; j < weapons.length; j++) {
                    if (weapons[j].getRefineLevel().equals("")) {
                        out.print(weapons[j].getName());
                    } else {
                        out.print(weapons[j].getName() + "|" + weapons[j].getRefineLevel());
                    }
                    if (j + 1 != weapons.length) {
                        out.print(",");
                    }
                }
                out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static ArrayList<PlayerShip> getBuilds() {
        ArrayList builds = new ArrayList();
        try {

            BufferedReader br = new BufferedReader(new FileReader("conf/build.csv"));
            String str;
            while ((str = br.readLine()) != null) {

                String[] values = str.split(",", -1);
//                System.out.println(str);
                // config format V1
                int pos = 0;
                PlayerShip pShip = new PlayerShip();
                if (values[pos++].equals("2")) {

                    Map<String, String> conversion = new HashMap<String, String>();
                    conversion.put("Commander", "Soldier");
                    conversion.put("Engineer", "Mechanics");
                    conversion.put("Explorer", "Scout");
                    conversion.put("Pirate", "Pirate");
                    String charType = values[pos++];

                    if (conversion.get(charType) != null) {
                        charType = conversion.get(charType);
                    }
                    pShip.setCharacterType(charType);

                    pShip.setCharacterLevel(Integer.parseInt(values[pos++]));
                }
                String name = values[pos++];
                pShip.setName(name);
                Ship ship = ShipDataManager.retrieve(values[pos++]);
                if (ship == null) {
                    ship = new NullShip();
                }
                pShip.setShip(ship);
                String upgradeLevel = values[pos++];
                if (upgradeLevel.equals("Base")) {
                    upgradeLevel = "0";
                }
                Level l = new Level(upgradeLevel, values[pos++]);
                pShip.setLevel(l);
                pShip.setCraft(Integer.parseInt(values[pos++]));
                pShip.setGunnery(Integer.parseInt(values[pos++]));
                pShip.setNavigation(Integer.parseInt(values[pos++]));
                Weapon figureHead = WeaponDataManager.retrieve(values[pos++]);
                Weapon sale = WeaponDataManager.retrieve(values[pos++]);
                Weapon stabilizer = WeaponDataManager.retrieve(values[pos++]);
                Weapon hull = WeaponDataManager.retrieve(values[pos++]);
                Weapon engine = WeaponDataManager.retrieve(values[pos++]);
                pShip.setFigureHead(figureHead);
                pShip.setSail(sale);
                pShip.setStabilizer(stabilizer);
                pShip.setHull(hull);
                pShip.setEngine(engine);
                for (int i = pos; i < values.length; i++) {
                    if (values[i].indexOf('|') != -1) {
                        String[] arr = values[i].split("\\|");
                        pShip.addWeapon(WeaponDataManager.retrieve(arr[0], arr[1]));
                    } else {
                        pShip.addWeapon(WeaponDataManager.retrieve(values[i]));
                    }

                }
                builds.add(pShip);
            }

        } catch (Exception e) {

//            e.printStackTrace();
        }
        return builds;
    }
}
