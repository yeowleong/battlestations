import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ShopTableModel extends AbstractTableModel {

    private ArrayList weapons = new ArrayList();

    public int getRowCount() {
        return weapons.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setWeapons(ArrayList weapons) {
        this.weapons = weapons;
    }

    public int getColumnCount() {
        return HEADERS.length;
    }

    private static final String[] HEADERS =
            {"Name", "Level", "Gold", "Wood", "Ore", "Prock", "Port"};

    public String getColumnName(int column) {
        return HEADERS[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
//        System.out.println(super.getValueAt(rowIndex, columnIndex));

        Payable p = (Payable) weapons.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return p.getName();
            case 1:
                return p.getRequiredLevel();
            case 2:
                return p.getGold();
            case 3:
                return p.getWood();
            case 4:
                return p.getOre();
            case 5:
                return p.getPlasmaRock();
            case 6:
                return p.getPort();
        }

        throw new IllegalStateException();
    }
}