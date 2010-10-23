import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Apr 13, 2008
 * Time: 11:55:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShipUpgrade implements Payable {
    private Map data;

    public ShipUpgrade(Map data) {
        this.data = data;
    }

    public String getLevel() {
        return (String) data.get(Ship.UPGRADE_LEVEL);
    }

    public String getRequiredLevel() {
        return (String) data.get(Ship.LEVEL_REQUIRED);
    }

    public String getSpeed() {
        return (String) data.get(Ship.SPEED);
    }

    public String getHP() {
        return (String) data.get(Ship.HIT_POINT);
    }

    public String getCapacity() {
        return (String) data.get(Ship.CAPACITY);
    }

    public String getNumWeaponSlot() {
        return (String) data.get(Ship.NUM_SLOT);
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

    public String getName() {
        return data.get("#Ship") + " [ L" + data.get(Ship.LEVEL_REQUIRED)
                + " - " + data.get(Ship.UPGRADE_LEVEL) + " ]";
    }

    public String getNumMedal() {
        return (String)data.get(Ship.MEDAL);
    }

    public String getPort() {
        if ("0".equalsIgnoreCase((String) data.get(Ship.UPGRADE_LEVEL)) || "Base".equalsIgnoreCase((String) data.get(Ship.UPGRADE_LEVEL))) {
            return (String) data.get(Ship.PORT);
        } else {
            return "";
        }
    }
}
