import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

public class ShipTableModel extends AbstractTableModel {
    private String name;
    //    private ArrayList weapons = new ArrayList();
    private DecimalFormat fmt = new DecimalFormat("0.00");
    private Ship s;
    Map m;

    public int getRowCount() {
        return m.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setShip(Ship s) {
        this.s = s;
        m = s.getAllLevelAsUpgrade();
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    private static final String[] HEADERS =
            {"L", "Required Level", "Speed", "HP", "Capacity", "Weapon Slots", "Gold", "Wood", "Ore", "Prock", "Medal", "Port"};

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
//        System.out.println("ABC get = " + rowIndex);
        Set set = m.keySet();
//        System.out.println(set);
        ShipUpgrade upgrade = (ShipUpgrade) m.get("" + rowIndex);
        if (upgrade == null) {
            upgrade = (ShipUpgrade) m.get("0");
        }
//        System.out.println("ABC = " + upgrade.getPort());
        switch (columnIndex) {
            case 0:
                return upgrade.getLevel();
            case 1:
                return upgrade.getRequiredLevel();
            case 2:
                return upgrade.getSpeed();
            case 3:
                return upgrade.getHP();
            case 4:
                return upgrade.getCapacity();
            case 5:
                return upgrade.getNumWeaponSlot();
            case 6:
                return upgrade.getGold();
            case 7:
                return upgrade.getWood();
            case 8:
                return upgrade.getOre();
            case 9:
                return upgrade.getPlasmaRock();
            case 10:
                return upgrade.getNumMedal();
            case 11:
                return s.getPort();
        }

        throw new IllegalStateException();
    }
}