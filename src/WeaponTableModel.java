import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class WeaponTableModel extends AbstractTableModel {
    private String type;
    private ArrayList weapons = new ArrayList();
    private DecimalFormat fmt = new DecimalFormat("0.00");

    public int getRowCount() {
        return weapons.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setType(String type) {
        this.type = type;
        weapons = new ArrayList();
        weapons.clear();
        weapons.addAll(Arrays.asList(WeaponDataManager.retrieveAll(type)));
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    private static final String[] HEADERS =
            {"L", "Name", "Weight", "Range", "Min Dmg", "Max Dmg", "Dmg/Wt", "Slow", "Limit", "Gold", "Wood", "Ore", "Prock", "Port"};

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Weapon p = (Weapon) weapons.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return p.getLevel();
            case 1:
                return p.getName();
            case 2:
                return p.getWeight();
            case 3:
                return p.getRange();
            case 4:
                return p.getMinDamage();
//                "Max Dmg", "Dmg/Wt", "Gold", "Wood", "Ore", "Prock", "Port"
            case 5:
                return p.getMaxDamage();
            case 6:
                return fmt.format(p.getAverageDamagePerWeight());
            case 7:
                return p.getSlowdown();
            case 8:
                return p.getLimit();
            case 9:
                return p.getGold();
            case 10:
                return p.getWood();
            case 11:
                return p.getOre();
            case 12:
                return p.getPlasmaRock();
            case 13:
                return p.getPort();
        }

        throw new IllegalStateException();
    }
}