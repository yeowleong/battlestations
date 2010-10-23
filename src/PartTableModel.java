import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: May 1, 2008
 * Time: 4:33:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class PartTableModel extends AbstractTableModel {
    private List parts = new ArrayList();
    DecimalFormat fmt = new DecimalFormat("0.00");

    private String type;

    public PartTableModel() {

    }

    public void setType(String type) {
        this.type = type;
        parts.clear();
        parts.addAll(Arrays.asList(WeaponDataManager.retrieveAll(type)));
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getRowCount() {
        return parts.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final String[] HEADERS =
            {"L", "Name", "Speed", "HP", "HP/Wt", "Spd/Wt", "Wt", "Gold", "Wood", "Ore", "Prock", "Port"};

    private static final String[] HULL_HEADERS =
            {"L", "Name", "Speed", "HP", "HP/Wt", "Spd/Wt", "Wt", "Cap", "Gold", "Wood", "Ore", "Prock", "Port"};

    public String getColumnName(int columnIndex) {
//          if (type.equals("hulls") || type.equals("stabilizers") || type.equals("engines")) {
            return HULL_HEADERS[columnIndex];
//        } else {
//            return HEADERS[columnIndex];  //To change body of implemented methods use File | Settings | File Templates.
//        }
    }

    public int getColumnCount() {
//        if (type.equals("hulls") || type.equals("stabilizers") || type.equals("engines")) {
            return HULL_HEADERS.length;
//        } else {
//            return HEADERS.length;  //To change body of implemented methods use File | Settings | File Templates.
//        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Weapon p = (Weapon) parts.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getLevel();
            case 1:
                return p.getName();
            case 2:
                return p.getSpeed();
            case 3:
                return p.getHP();
            case 4:
                return fmt.format(p.getHPPerWeight());
            case 5:
                return fmt.format(p.getSpeedPerWeight());
            case 6:
                return p.getWeight();
            case 7:
//                return type.equals("hulls") ? p.getCapacity() : p.getGold();
                return p.getCapacity();
            case 8:
//                return type.equals("hulls") ? p.getGold() : p.getWood();
                return p.getGold();
            case 9:
//                return type.equals("hulls") ? p.getWood() : p.getOre();
                return p.getWood();
            case 10:
//                return type.equals("hulls") ? p.getOre() : p.getPlasmaRock();
                return p.getOre();
            case 11:
//                return type.equals("hulls") ? p.getPlasmaRock() : p.getPort();
                return p.getPlasmaRock();
            case 12:
                return p.getPort();
        }
        return "";
    }
}
