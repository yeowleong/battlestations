import org.jdesktop.swingx.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.io.File;

public class BuildSimulatorForm {
    // private static String[] headers = {"Weapon/Part", "Gold", "Wood", "Ore", "Prock", "Port"};

    public JPanel mainPanel;
    private JComboBox shipCombo;
    private JTextField speedText;
    private JTextField capacityText;
    private JTextField usedCapacityText;
    private JComboBox categoryCombo;
    private JComboBox weaponCombo;
    private JTextField minDamageText;
    private JTextField minRangeText;
    private JTextField maxDamageText;
    private JTextField maxRangeText;
    private JButton addButton;
    private JComboBox figureheadCombo;
    private JComboBox stabilizerCombo;
    private JComboBox sailCombo;
    private JComboBox hullCombo;
    private JComboBox engineCombo;
    //    private JTextField gunneryText;
    //    private JTextField navigationText;
    //    private JTextField craftText;
    private JTextField hpText;
    private JButton clearButton;
    //    private JButton clearAllButton;
    private JButton weaponButton1;
    private JButton weaponButton2;
    private JButton weaponButton3;
    private JButton weaponButton4;
    private JButton weaponButton5;
    private JButton weaponButton6;
    private JComboBox upgradeCombo;
    private JTextField impliedHPText;
    private JTextField avgDamageText;
    private JTextField armourText;
    private JButton clearAllButton;
    private JComboBox buildCombo;
    //    private JButton modifyButton;
    //    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameText;
    private JButton saveButton;
    private JLabel statusLabel;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTextField goldText;
    private JTextField woodText;
    private JTextField oreText;
    private JTextField prockText;
    private JComboBox classCombo;
    private JTextField allocatedGunneryText;
    private JTextField allocatedCraftText;
    private JTextField freeText;
    private JTextField allocatedNavigationText;
    private JSpinner craftSpinner;
    private JSpinner gunnerySpinner;
    private JSpinner navigationSpinner;
    private JTextField totalText;
    private JSpinner levelSpinner;
    private JButton weaponButton7;
    private JTable table2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTable table3;
    private JButton yahooButton;
    private JButton thecoffeespoonNetButton;
    private JButton CCCListButton;
    private JButton thecoffeespoonNetButton1;
    private JComboBox shopShipCombo;
    private JTable shipTable;
    private JButton weaponButton8;
    private JCheckBox defenderCheckBox;
    private JTextField remainingCapacityText;
    private JTextField assignedText;
    private JComboBox comboBox3;
    private JButton photoButton;
    private JButton weaponButton9;
    private JButton weaponButton10;
    private JLabel limitLabel;
    private JButton weaponButton11;
    private JButton weaponButton12;
//    private JScrollPane altShipTable;
    private JComboBox altShopShipCombo;
    private JTable altShipTable;
    private JButton cccShipButton;
    private JButton tcsShipButton;
    private JTable craftingTable;

    private ArrayList<PlayerShip> builds;
    private JButton[] weaponCheckBoxes = {};
    private PlayerShip playerShip = new PlayerShip();
    private JButton[] weaponSlots = {weaponButton1, weaponButton2,
            weaponButton3, weaponButton4,
            weaponButton5, weaponButton6,
            weaponButton7, weaponButton8,
            weaponButton9, weaponButton10,weaponButton11,weaponButton12};
    private PartTableModel partTableModel;
    private WeaponTableModel weaponTableModel;
    private ShopTableModel shopTableModel;
    private ShipTableModel shipTableModel;
    private ShipTableModel altShipTableModel;
//    private JXGlassBox gb = new JXGlassBox();
//    private JLabel lbl;

    public BuildSimulatorForm() {
        
//        AutoCompleteDecorator.decorate(weaponCombo);
        
//        AutoCompleteJComboBoxer b = new AutoCompleteJComboBoxer(  weaponCombo);
        Ship[] shipArr = ShipDataManager.retrieveAll();
        for (Ship s : shipArr) {
            shopShipCombo.addItem(s);
            altShopShipCombo.addItem(s);
        }

//
        craftSpinner.setModel(new SpinnerNumberModel(0, 0, 900, 1));
        navigationSpinner.setModel(new SpinnerNumberModel(0, 0, 900, 1));
        gunnerySpinner.setModel(new SpinnerNumberModel(0, 0, 900, 1));
        levelSpinner.setModel(new SpinnerNumberModel(0, 0, 220, 1));
        setSpinnerAlignment();

        populateShip();
        populateFigurehead();
        populateHull();
        populateStabilizer();
        populateSail();
        populateEngine();
        categoryCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                populateWeapon((String) categoryCombo.getSelectedItem());
            }
        });
        categoryCombo.setSelectedIndex(0);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limitLabel.setText("");
                for (int i = 0; i < weaponCheckBoxes.length; i++) {
                    JButton button = weaponCheckBoxes[i];
                    if (button.getText().equals("Empty")) {
                        Weapon w = (Weapon) weaponCombo.getSelectedItem();
                        if (playerShip.addWeapon(w)) {
                            button.setText(w.toString());
                            if (w.isRare()) {
                                button.setForeground(Color.BLUE);
                            }
                            refresh();
                        } else {
                            limitLabel.setForeground(Color.RED);                            
                            limitLabel.setText("Limit exceeded.");
                        }

                        break;
                    }
                }
            }
        });

        shipCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = shipCombo.getSelectedItem();
                if (obj instanceof Ship) {
                    Ship s = (Ship) obj;
                    playerShip.setShip(s);
                    Object o = upgradeCombo.getSelectedItem();
                    Level l = (Level) o;
                    if (l != null) {
                        playerShip.setLevel(l);
                    }
                    refreshUpgradeCombo();
                    refreshWeaponSlots();
                    refresh();
                } else {
                    playerShip.removeShip();
                }

            }
        });
        figureheadCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = figureheadCombo.getSelectedItem();
                if (obj instanceof Weapon) {
                    playerShip.setFigureHead((Weapon) obj);
                } else {
                    playerShip.removeFigureHead();
                }
                refresh();
            }
        });
        sailCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = sailCombo.getSelectedItem();
                if (obj instanceof Weapon) {
                    playerShip.setSail((Weapon) obj);
                } else {
                    playerShip.removeSail();
                }
                refresh();
            }
        });
        stabilizerCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = stabilizerCombo.getSelectedItem();
                if (obj instanceof Weapon) {
                    playerShip.setStabilizer((Weapon) obj);
                } else {
                    playerShip.removeStabilizer();
                }

                refresh();
            }
        });
        hullCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = hullCombo.getSelectedItem();
                if (obj instanceof Weapon) {
                    playerShip.setHull((Weapon) obj);
                } else {
                    playerShip.removeHull();
                }
                refresh();
            }
        });
        engineCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = engineCombo.getSelectedItem();
                if (obj instanceof Weapon) {
                    playerShip.setEngine((Weapon) obj);
                } else {
                    playerShip.removeEngine();
                }
                refresh();
            }
        });
        clearAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAllWeapons();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearParts();
            }
        });
        upgradeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Level l = (Level) upgradeCombo.getSelectedItem();
                if (l != null) {
                    playerShip.setLevel(l);
                    refreshWeaponSlots();
                    refresh();
                }
            }
        });

        ActionListener weaponListener = new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();

                playerShip.removeWeapon(btn.getText());
                btn.setText("Empty");
                btn.setSelected(false);
                btn.setForeground(Color.BLACK);
                refresh();
            }
        };
        weaponButton1.addActionListener(weaponListener);
        weaponButton2.addActionListener(weaponListener);
        weaponButton3.addActionListener(weaponListener);
        weaponButton4.addActionListener(weaponListener);
        weaponButton5.addActionListener(weaponListener);
        weaponButton6.addActionListener(weaponListener);
        weaponButton7.addActionListener(weaponListener);
        weaponButton8.addActionListener(weaponListener);
        weaponButton9.addActionListener(weaponListener);
        weaponButton10.addActionListener(weaponListener);
        weaponButton11.addActionListener(weaponListener);
        weaponButton12.addActionListener(weaponListener);


//        clearAllButton1.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                removeAllWeapons();
//            }
//        });

        builds = PlayerShipDataManager.getBuilds();
        populateBuildCombo();

        buildCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = buildCombo.getSelectedItem();
                clearAll();
                if (obj instanceof PlayerShip) {
                    playerShip = (PlayerShip) ((PlayerShip) buildCombo.getSelectedItem()).clone();
                    loadPlayerShip();
                    refresh();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (buildCombo.getSelectedItem() instanceof PlayerShip) {
                    PlayerShip pShip = (PlayerShip) buildCombo.getSelectedItem();

                    for (Iterator iter = builds.iterator(); iter.hasNext();) {
                        PlayerShip p = (PlayerShip) iter.next();
                        if (p.getName().equals(pShip.getName())) {
                            iter.remove();
                        }
                    }

                    PlayerShipDataManager.saveBuilds(builds);
                    builds = PlayerShipDataManager.getBuilds();
//                    Object obj = buildCombo.getSelectedItem();
//                    buildCombo.setSelectedItem(pShip);
                    playerShip = new PlayerShip();

                    loadPlayerShip();
//                    clearWeaponSlots();
//                    populateWeaponSlots();
//                    refresh();
//                            clearAll();
//                    populateBuildCombo();
                    populateBuildCombo();
                    buildCombo.setSelectedItem("");
                    statusLabel.setText("Build deleted!");
                    refresh();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameText.getText().equals("")) {
                    return;
                }
                Object obj = buildCombo.getSelectedItem();
                boolean save = false;
                if (obj instanceof PlayerShip) {
                    for (Iterator iter = builds.iterator(); iter.hasNext();) {
                        PlayerShip s = (PlayerShip) iter.next();
                        if (s.getName().equals(nameText.getText())) {
                            iter.remove();
                            save = true;
                        }
                    }
                }
                //pShip = playerShip;
                PlayerShip p = (PlayerShip) playerShip.clone();     //cleared in clearAll

                p.setName(nameText.getText());
                p.setCharacterType((String) classCombo.getSelectedItem());
                builds.add(p);
                PlayerShipDataManager.saveBuilds(builds);

                populateBuildCombo();
                buildCombo.setSelectedItem(p);
                if (!save)
                    statusLabel.setText("Added!");
                else
                    statusLabel.setText("Saved!");
            }
        });

//        model = new AbstractTableModel() {
//            public int getRowCount() {
//                return playerShip.getAllItems().length;
//            }
//
//            public int getColumnCount() {
//                return 6;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            public String getColumnName(int columnIndex) {
//                return headers[columnIndex];
//            }
//
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return false;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                Weapon[] weapons = playerShip.getAllItems();
//
//                for (Weapon p : weapons) {
//                    System.out.println(p);
//                }
//                System.out.println(rowIndex);
////                if (rowIndex >= weapons.length) {
////                               return "";
////                }
//
//                switch (columnIndex) {
//                    case 0:
//                        return weapons[rowIndex].getName();
//                    case 1:
//                        return weapons[rowIndex].getGold();
//                    case 2:
//                        return weapons[rowIndex].getWood();
//                    case 3:
//                        return weapons[rowIndex].getOre();
//                    case 4:
//                        return weapons[rowIndex].getPlasmaRock();
//                    case 5:
//                        return weapons[rowIndex].getPort();
//                    default:
//                        return 0;
//                }
//            }
//
//        };
//
//         SortedTableHelper helper = new SortedTableHelper (table1);
//         helper.prepareTable();

//        table1 = new SortableTable(model);
//        table1.setModel(model);
        table1.setRowSelectionAllowed(true);


        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {


                int[] arr = table1.getSelectedRows();

                int gold = 0;
                int wood = 0;
                int ore = 0;
                int prock = 0;
                for (int i = 0; i < arr.length; i++) {
                    int val = ((JXTable) table1).convertRowIndexToModel(arr[i]);
                    gold += (Integer) table1.getModel().getValueAt(val, 2);
                    wood += (Integer) table1.getModel().getValueAt(val, 3);
                    ore += (Integer) table1.getModel().getValueAt(val, 4);
                    prock += (Integer) table1.getModel().getValueAt(val, 5);
                }
                goldText.setText("" + gold);
                woodText.setText("" + wood);
                oreText.setText("" + ore);
                prockText.setText("" + prock);

            }
        }
        );

        TableColumn column = null;
        int count = table1.getModel().getColumnCount();
        for (int i = 0; i < count; i++) {
            column = table1.getColumnModel().getColumn(i);
            if (i == 0 || i == count - 1) {
                column.setPreferredWidth(150); //third column is bigger
            } else {
                column.setPreferredWidth(30);
            }
        }

//        classCombo.addItem("");
//        classCombo.addItem(new CharacterType(CharacterType.COMMANDER));
//        classCombo.addItem(new CharacterType(CharacterType.ENGINEER));
//        classCombo.addItem(new CharacterType(CharacterType.EXPLORER));
//        classCombo.addItem(new CharacterType(CharacterType.PIRATE));
//        classCombo.addItem(new CharacterType(CharacterType.TRADER));
        craftSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int craft = (Integer) craftSpinner.getValue();
                playerShip.setCraft(craft);
                refresh();
            }
        });
        gunnerySpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int gunnery = (Integer) gunnerySpinner.getValue();
                playerShip.setGunnery(gunnery);
                refresh();
            }
        });

        navigationSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int navigation = (Integer) navigationSpinner.getValue();
                playerShip.setNavigation(navigation);
                refresh();
            }
        });
        levelSpinner.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                int level = (Integer) levelSpinner.getValue();
                String clazz = (String) classCombo.getSelectedItem();
                if (!"".equals(clazz) && level > 0) {
                    playerShip.setCharacterType(clazz);
                    try {
                        // playerShip.setCharacterLevel(level);

                        playerShip.modifyLevel(level);

                        refresh();
                        setMinimumLevelForSpinners();
                        craftSpinner.setValue(playerShip.getCraft());
                        gunnerySpinner.setValue(playerShip.getGunnery());
                        navigationSpinner.setValue(playerShip.getNavigation());

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }


                }
            }
        });
        classCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int level = (Integer) levelSpinner.getValue();
                String clazz = (String) classCombo.getSelectedItem();
                if (!"".equals(clazz) && level > 0) {
                    playerShip.setCharacterType(clazz);
                    try {
                        playerShip.setCharacterLevel(level);
                        setMinimumLevelForSpinners();
                        refresh();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table2.clearSelection();
//                WeaponTableModel m = new WeaponTableModel();
//                m.setType((String) comboBox1.getSelectedItem());
//                table2.setModel(m);
                weaponTableModel.setType((String) comboBox1.getSelectedItem());
                weaponTableModel.fireTableStructureChanged();
                ((JXTable) table2).packAll();
                table2.updateUI();

//                     weaponList.clear();
//                weaponList.addAll(Arrays.asList(WeaponDataManager.retrieveAll((String)comboBox1.getSelectedItem())));
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table3.clearSelection();
                partTableModel.setType((String) comboBox2.getSelectedItem());
                partTableModel.fireTableStructureChanged();
                ((JXTable) table3).packAll();
                table3.updateUI();
            }
        });
        yahooButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://staff.tpjcian.net/chu_chee_chin/weapons.html");
            }
        });
        thecoffeespoonNetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://thecoffeespoon.net/battlestations/weapons");
            }
        });
        CCCListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://staff.tpjcian.net/chu_chee_chin/parts.html");
            }
        });


        thecoffeespoonNetButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://thecoffeespoon.net/battlestations/parts");
            }
        });



        cccShipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://staff.tpjcian.net/chu_chee_chin/ships.html");
            }
        });


        tcsShipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL("http://thecoffeespoon.net/battlestations/ships:list");
            }
        });


        shopShipCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shipTable.clearSelection();
//                System.out.println(shopShipCombo.getSelectedItem());
                shipTableModel.setShip((Ship) shopShipCombo.getSelectedItem());
                ((JXTable) shipTable).packAll();
                shipTable.updateUI();
            }
        });
          shopShipCombo.setSelectedItem(shipArr[0]);

//        newRadioButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                PlayerShip.isOld = false;//To change body of implemented methods use File | Settings | File Templates.
//                oldRadioButton.setSelected(false);
//                 refresh();
//            }
//        });
//        oldRadioButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                PlayerShip.isOld = true;
//                newRadioButton.setSelected(false);
//                 refresh();
//            }
//        });
        figureheadCombo.addMouseMotionListener(new MouseMotionAdapter() {
//            public void


            public void mouseMoved(MouseEvent e) {
                if (figureheadCombo.getSelectedItem() != null) {
                    Weapon w = (Weapon) figureheadCombo.getSelectedItem();
                    if (w != null) {
//                        JFrame fr = (JFrame)mainPanel.getParent().getParent().getParent();
//                        Container glassPane = (Container)fr.getGlassPane();
                        // Only allow one message box to be visible at a time so
                        // pop down any visible message boxes
//                             Component glassPaneChildren[] = glassPane.getComponents();
//                             for(int i = 0; i < glassPaneChildren.length; i++) {
//                                 glassPaneChildren[i].setVisible(false);
//                                 glassPane.remove(glassPaneChildren[i]);
//                             }
//                             Point p = SwingUtilities.convertPoint(glassPane, figureheadCombo.getX(), figureheadCombo.getY(), figureheadCombo);
                        //jXGlassBox1.setBorder(new DropShadowBorder());
                        //jXGlassBox1.showOnGlassPane(glassPane, p.x, p.y);

//                         lbl.setText("test\n yry");
//                        gb.setBorder(new DropShadowBorder());
//                        gb.showOnGlassPane(glassPane,figureheadCombo.getX(),figureheadCombo.getY());
//                        System.out.println(p);
//                        gb.setVisible(true);
//                        PopupFactory factory = PopupFactory.getSharedInstance();
//                Popup popup = factory.getPopup(glassPane, gb, p.x,p.y);
//                popup.show();

                        figureheadCombo.setToolTipText("Speed:" + w.getSpeed() + "\n | HP:" + w.getHP());
                    }
                }
            }
        });
        sailCombo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (sailCombo.getSelectedItem() != null) {
                    Weapon w = (Weapon) sailCombo.getSelectedItem();
                    if (w != null) {
                        sailCombo.setToolTipText("Speed:" + w.getSpeed());
                    }
                }
            }
        });
        hullCombo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (hullCombo.getSelectedItem() != null) {
                    Weapon w = (Weapon) hullCombo.getSelectedItem();
                    if (w != null) {
                        hullCombo.setToolTipText("Speed:" + w.getSpeed() + " | HP:" + w.getHP() + " | Capacity:" + w.getCapacity());
                    }
                }
            }
        });
        engineCombo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (engineCombo.getSelectedItem() != null) {
                    Weapon w = engineCombo.getSelectedItem() != null ? (Weapon) engineCombo.getSelectedItem() : null;
                    if (w != null) {
                        engineCombo.setToolTipText("Speed:" + w.getSpeed());
                    }
                }
            }
        });
        stabilizerCombo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (stabilizerCombo.getSelectedItem() != null) {
                    Weapon w = (Weapon) stabilizerCombo.getSelectedItem();
                    if (w != null) {
                        stabilizerCombo.setToolTipText("Speed: " + w.getSpeed());
                    }
                }

            }
        });

        MouseMotionListener mmListener = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                JButton btn = (JButton)e.getSource();
                String name = btn.getText();
                Weapon w = WeaponDataManager.retrieve(
                        name.substring(name.indexOf("-") + 2).trim());
                if (w != null) {
                    btn.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
                            + " | Range:" + w.getRange() + " | Weight:" + w.getWeight());
                }
            }
        };

        weaponButton1.addMouseMotionListener(mmListener);
        weaponButton2.addMouseMotionListener(mmListener);
        weaponButton3.addMouseMotionListener(mmListener);
        weaponButton4.addMouseMotionListener(mmListener);
        weaponButton5.addMouseMotionListener(mmListener);
        weaponButton6.addMouseMotionListener(mmListener);
        weaponButton7.addMouseMotionListener(mmListener);
        weaponButton8.addMouseMotionListener(mmListener);
        weaponButton9.addMouseMotionListener(mmListener);
        weaponButton10.addMouseMotionListener(mmListener);
        weaponButton11.addMouseMotionListener(mmListener);
        weaponButton12.addMouseMotionListener(mmListener);


//        weaponButton2.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton2.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton2.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//        weaponButton3.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton3.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton3.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//        weaponButton4.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton4.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton4.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//        weaponButton5.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton5.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton5.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//        weaponButton6.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton6.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton6.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//
//        weaponButton7.addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseMoved(MouseEvent e) {
//                String name = weaponButton7.getText();
//                Weapon w = WeaponDataManager.retrieve(
//                        name.substring(name.indexOf("-") + 2).trim());
//                if (w != null) {
//                    weaponButton7.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
//                            + " | Range:" + w.getRange());
//                }
//            }
//        });
//
//        weaponButton8.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                playerShip.removeWeapon(weaponButton8.getText());
//                weaponButton8.setText("Empty");
//                weaponButton8.setSelected(false);
//                refresh();
//            }
//        });

        weaponCombo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                Weapon w = (Weapon) weaponCombo.getSelectedItem();
                if (w != null) {
                    weaponCombo.setToolTipText("Damage:" + w.getMinDamage() + "-" + w.getMaxDamage()
                            + " | Range:" + w.getRange());
                }
            }
        });
        defenderCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerShip.setDefender(defenderCheckBox.isSelected());
                refresh();
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox3.getSelectedItem().equals("Mine")) {
                    speedText.setText("" + playerShip.getSpeed());
                } else if (comboBox3.getSelectedItem().equals("* 1.38")) {
                    speedText.setText("" + ((int) (playerShip.getSpeed() * 1.38)));
                } else {
                    speedText.setText("" + ((int) (playerShip.getSpeed() / 1.38)));
                }
            }
        });
        photoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Robot robot = null;
                try {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Dimension screenSize = toolkit.getScreenSize();
                    JFrame fr = (JFrame) mainPanel.getParent().getParent().getParent();
                    Rectangle rect = new Rectangle(fr.getX(), fr.getY(), fr.getWidth(), fr.getHeight());
                    robot = new Robot();
                    BufferedImage image = robot.createScreenCapture(rect);
                    JFileChooser fc = new JFileChooser();


                    int returnVal = fc.showSaveDialog(mainPanel);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        if (file.getName().endsWith(".jpg")) {
                            ImageIO.write(image, "jpg", file);
                        } else {
                            ImageIO.write(image, "jpg", new File(file.getAbsolutePath() + ".jpg"));
                        }
                    }

//                    File file = new File("c:\\screen.jpg");

                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        });

//         lbl = new JLabel("test");
//                         lbl.setBackground(new java.awt.Color(255, 255, 255));
//
//                         lbl.setOpaque(true);
//                         gb.add(lbl);

//        weaponButton9.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                playerShip.removeWeapon(weaponButton9.getText());
//                weaponButton9.setText("Empty");
//                weaponButton9.setSelected(false);
//                refresh();
//            }
//        });
//        weaponButton10.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                playerShip.removeWeapon(weaponButton10.getText());
//                weaponButton10.setText("Empty");
//                weaponButton10.setSelected(false);
//                refresh();
//            }
//
//        });
//
//         weaponButton11.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                playerShip.removeWeapon(weaponButton11.getText());
//                weaponButton11.setText("Empty");
//                weaponButton11.setSelected(false);
//                refresh();
//            }
//        });
//        weaponButton12.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                playerShip.removeWeapon(weaponButton12.getText());
//                weaponButton12.setText("Empty");
//                weaponButton12.setSelected(false);
//                refresh();
//            }
//        });


        altShopShipCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                altShipTable.clearSelection();
//                System.out.println(shopShipCombo.getSelectedItem());
                altShipTableModel.setShip((Ship) altShopShipCombo.getSelectedItem());
                ((JXTable) altShipTable).packAll();
                altShipTable.updateUI();
            }
        });


    }

    private void setMinimumLevelForSpinners() {
        int craft = playerShip.getCraft();
        int allocatedCraft = playerShip.getAllocatedCraft();
        SpinnerNumberModel m = (SpinnerNumberModel) craftSpinner.getModel();
        m.setValue(Math.max(craft, allocatedCraft));
        m.setMinimum(allocatedCraft);

        int gunnery = playerShip.getGunnery();
        int allocatedGunnery = playerShip.getAllocatedGunnery();
        m = (SpinnerNumberModel) gunnerySpinner.getModel();
        m.setValue(Math.max(gunnery, allocatedGunnery));
        m.setMinimum(allocatedGunnery);


        int navigation = (Integer) playerShip.getNavigation();
        int allocatedNavigation = playerShip.getAllocatedNavigation();
        m = (SpinnerNumberModel) navigationSpinner.getModel();
        m.setMinimum(allocatedNavigation);
        m.setValue(Math.max(navigation, allocatedNavigation));

    }

    private void setSpinnerAlignment() {
        JFormattedTextField tf = ((JSpinner.DefaultEditor) craftSpinner.getEditor()).getTextField();
        tf.setHorizontalAlignment(JFormattedTextField.CENTER);

        tf = ((JSpinner.DefaultEditor) navigationSpinner.getEditor()).getTextField();
        tf.setHorizontalAlignment(JFormattedTextField.CENTER);

        tf = ((JSpinner.DefaultEditor) gunnerySpinner.getEditor()).getTextField();
        tf.setHorizontalAlignment(JFormattedTextField.CENTER);

        tf = ((JSpinner.DefaultEditor) levelSpinner.getEditor()).getTextField();
        tf.setHorizontalAlignment(JFormattedTextField.LEFT);
    }

    private void clearParts() {
        stabilizerCombo.setSelectedItem(null);
        figureheadCombo.setSelectedItem(null);
        sailCombo.setSelectedItem(null);
        hullCombo.setSelectedItem(null);
        engineCombo.setSelectedItem(null);
    }

    private void clearAll() {
        levelSpinner.getModel().setValue(0);
        classCombo.setSelectedItem("");
//        levelText.setText("");
        statusLabel.setText(" ");
        playerShip = new PlayerShip();

        refreshWeaponSlots();
        clearParts();
        craftSpinner.setValue(0);
        gunnerySpinner.setValue(0);
        navigationSpinner.setValue(0);
        freeText.setText("");
        freeText.setBackground(capacityText.getBackground());
        usedCapacityText.setBackground(capacityText.getBackground());
        shipCombo.setSelectedItem("");
        upgradeCombo.removeAllItems();
        nameText.setText("");
        this.defenderCheckBox.setSelected(false);
    }

    private void refreshUpgradeCombo() {
        Level[] levels = playerShip.getShip().getAllLevel();
        upgradeCombo.removeAllItems();
        for (int i = 0; i < levels.length; i++) {
            upgradeCombo.addItem(levels[i]);
        }
    }

    private void populateBuildCombo() {
        buildCombo.removeAllItems();
        buildCombo.addItem("");
        Collections.sort(builds, new Comparator() {
            public int compare(Object o1, Object o2) {
                PlayerShip s0 = (PlayerShip) o1;
                PlayerShip s1 = (PlayerShip) o2;
                return s0.getName().compareTo(s1.getName());
            }
        });

        for (int i = 0; i < builds.size(); i++) {
            buildCombo.addItem(builds.get(i));
        }
    }

//    private void populateBuildCombo() {
//         builds = PlayerShipDataManager.getBuilds();
//        buildCombo.removeAllItems();
//        for (int i = 0 ; i <  builds.size() ; i++) {
//            buildCombo.addItem(builds.get(i));
//            //shipCombo.setSelectedItem(pShip.getShip());
//        }

    //    }

    private void loadPlayerShip() {
         System.out.println(playerShip.getCharacterType());
        classCombo.setSelectedItem(playerShip.getCharacterType());

        levelSpinner.getModel().setValue(playerShip.getCharacterLevel());
        nameText.setText(playerShip.getName());

        Level l = playerShip.getLevel();
        shipCombo.setSelectedItem(playerShip.getShip());
        refreshUpgradeCombo();
        upgradeCombo.setSelectedItem(l);

        figureheadCombo.setSelectedItem(removeNull(playerShip.getFigureHead()));
        sailCombo.setSelectedItem(removeNull(playerShip.getSail()));
        stabilizerCombo.setSelectedItem(removeNull(playerShip.getStabilizer()));
        hullCombo.setSelectedItem(removeNull(playerShip.getHull()));
        engineCombo.setSelectedItem(removeNull(playerShip.getEngine()));
        craftSpinner.setValue(playerShip.getCraft());
        gunnerySpinner.setValue(playerShip.getGunnery());
        navigationSpinner.setValue(playerShip.getNavigation());

        clearWeaponSlots();
        Weapon[] weapons = playerShip.getAllWeapons();

        for (int i = 0; i < weapons.length; i++) {
            weaponSlots[i].setText(weapons[i].toString());
            if (weapons[i].isRare()) {
                weaponSlots[i].setForeground(Color.BLUE);
            } else {
                weaponSlots[i].setForeground(Color.BLACK);
            }
        }

    }

    private Object removeNull(Weapon w) {
        if (w == null || w instanceof NullWeapon) {
            return "";
        }
        return w;
    }

    private void removeAllWeapons() {
        for (int i = 0; i < weaponSlots.length; i++) {
            playerShip.removeWeapon(weaponSlots[i].getText());
            weaponSlots[i].setText("Empty");
            weaponSlots[i].setSelected(false);
            refresh();

        }
    }

//    private void removeWeapons() {
//        for (int i = 0; i < weaponCheckBoxes.length; i++) {
//            if (weaponCheckBoxes[i].isSelected()) {
//                playerShip.removeWeapon(weaponCheckBoxes[i].getText());
//                weaponCheckBoxes[i].setText("Empty");
//                weaponCheckBoxes[i].setSelected(false);
//                refresh();
//            }
//        }
//    }

    private void clearWeaponSlots() {
        for (int i = 0; i < weaponCheckBoxes.length; i++) {
            weaponCheckBoxes[i].setText("Empty");
            weaponCheckBoxes[i].setSelected(false);
        }
    }

    private void refreshWeaponSlots() {
        for (int i = playerShip.getShip().getNumWeaponSlots(); i < weaponCheckBoxes.length; i++) {
            playerShip.removeWeapon(weaponCheckBoxes[i].getText());
            weaponCheckBoxes[i].setText("Empty");
            weaponCheckBoxes[i].setSelected(false);
        }

        int numSlots = playerShip.getShip().getNumWeaponSlots();
        for (int i = 0; i < weaponSlots.length; i++) {
            weaponSlots[i].setEnabled(false);
        }
        weaponCheckBoxes = new JButton[numSlots];
        for (int i = 0; i < numSlots; i++) {
            weaponCheckBoxes[i] = weaponSlots[i];
            weaponCheckBoxes[i].setEnabled(true);
        }
    }

    private void refresh() {

        int total = playerShip.getTotal();
        int available = playerShip.getAvailable();
        if (!classCombo.getSelectedItem().equals("")) {
            allocatedGunneryText.setText("" + playerShip.getAllocatedGunnery());
            allocatedNavigationText.setText("" + playerShip.getAllocatedNavigation());
            allocatedCraftText.setText("" + playerShip.getAllocatedCraft());


            totalText.setText("" + total);

            int level = (Integer) levelSpinner.getValue();
            if (level > 0) {
                freeText.setText("" + available);


                if (available < 0) {
                    freeText.setBackground(Color.RED);
                } else {
                    freeText.setBackground(capacityText.getBackground());
                }
            }
        }

        //  classCombo.setSelectedItem(playerShip.getCharacterType());
//        levelText.setText("" + playerShip.getCharacterLevel());
//        table1.clearSelection();
//        weapons = playerShip.getAllItems();
//        issuesEventList.clear();
//        issuesEventList.addAll(weapons);
        shopTableModel.setWeapons(playerShip.getAllItems());
        table1.clearSelection();
        ((JXTable) table1).packAll();
        table1.updateUI();

//        table1.tableChanged(new TableModelEvent(model));
        //model.sortByColumn(0, true);


        DecimalFormat fmt = new DecimalFormat("0.#%");
        armourText.setText("" + (int) playerShip.getArmour() + "(" + fmt.format(playerShip.getArmourReduction()) + ")");
//        textRequiredLevel.setText(playerShip.getRequiredLevel());
        impliedHPText.setText("" + playerShip.getImpliedHP());
        speedText.setText("" + playerShip.getSpeed());
        minDamageText.setText("" + playerShip.getMinDamagePerVolley());
        avgDamageText.setText("" + playerShip.getAverageDamagePerVolley());
        maxDamageText.setText("" + playerShip.getMaxDamagePerVolley());
        minRangeText.setText("" + playerShip.getMinRange());
        maxRangeText.setText("" + playerShip.getMaxRange());
        hpText.setText("" + playerShip.getHP());
        int capacity = playerShip.getCapacity();
        int usedCapacity = playerShip.getUsedCapacity();
        if (usedCapacity > capacity) {
            usedCapacityText.setBackground(Color.RED);
        } else {
            usedCapacityText.setBackground(capacityText.getBackground());
        }
        capacityText.setText("" + capacity);
        usedCapacityText.setText("" + usedCapacity);
        assignedText.setText("" + (total - available));
        remainingCapacityText.setText("" + (capacity - usedCapacity));

//        weaponButton1.setForeground(Color.BLACK);
//        weaponButton2.setForeground(Color.BLACK);
//        weaponButton3.setForeground(Color.BLACK);
//        weaponButton4.setForeground(Color.BLACK);
//        weaponButton5.setForeground(Color.BLACK);
//        weaponButton6.setForeground(Color.BLACK);
//        weaponButton7.setForeground(Color.BLACK);
//        weaponButton8.setForeground(Color.BLACK);
//        weaponButton9.setForeground(Color.BLACK);
//        weaponButton10.setForeground(Color.BLACK);
//        weaponButton11.setForeground(Color.BLACK);
//         weaponButton12.setForeground(Color.BLACK);

    }

    private void populateHull() {
        Weapon[] arr = WeaponDataManager.retrieveAll("hulls");
        hullCombo.removeAllItems();
        hullCombo.addItem(null);
        for (int i = 0; i < arr.length; i++) {
            hullCombo.addItem(arr[i]);
        }
    }

    private void populateEngine() {
        Weapon[] arr = WeaponDataManager.retrieveAll("engines");
        engineCombo.removeAllItems();
        engineCombo.addItem(null);

        for (int i = 0; i < arr.length; i++) {
            engineCombo.addItem(arr[i]);
        }
    }

    private void populateFigurehead() {
        Weapon[] arr = WeaponDataManager.retrieveAll("figureheads");
        figureheadCombo.removeAllItems();
        figureheadCombo.addItem(null);
        for (int i = 0; i < arr.length; i++) {
            figureheadCombo.addItem(arr[i]);
        }
    }

    private void populateStabilizer() {
        Weapon[] arr = WeaponDataManager.retrieveAll("stabilizers");
        stabilizerCombo.removeAllItems();
        stabilizerCombo.addItem(null);
        for (int i = 0; i < arr.length; i++) {
            stabilizerCombo.addItem(arr[i]);
        }
    }

    private void populateSail() {
        Weapon[] arr = WeaponDataManager.retrieveAll("sails");
        sailCombo.removeAllItems();
        sailCombo.addItem(null);
        for (int i = 0; i < arr.length; i++) {
            sailCombo.addItem(arr[i]);
        }
    }

    private void populateWeapon(String category) {
        Weapon[] arr = WeaponDataManager.retrieveAll(category);
        weaponCombo.removeAllItems();
        for (int i = 0; i < arr.length; i++) {
            weaponCombo.addItem(arr[i]);
        }
    }

    public void populateShip() {
        Ship[] arr = ShipDataManager.retrieveAll();
        shipCombo.addItem("");
        for (int i = 0; i < arr.length; i++) {
            shipCombo.addItem(arr[i]);
        }
    }


    private void createUIComponents() {
//        System.out.println("created");
//        craftingTable = new JXTreeTable();
//        ((JXTreeTable)craftingTable).setTreeTableModel(new CraftingTreeTableModel());
//        ((JXTable) craftingTable).setColumnControlVisible(true);
        table1 = new JXTable();
        ((JXTable) table1).setColumnControlVisible(true);

        shopTableModel = new ShopTableModel();
        table1.setModel(shopTableModel);

//        issuesEventList = new BasicEventList();
//        weapons = new ArrayList<Payable>();
//        issuesEventList.addAll(weapons);
//        SortedList sortedIssues = new SortedList(issuesEventList, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                Payable w1 = (Payable) o1;
//                Payable w2 = (Payable) o2;
//                return w1.getName().compareTo(w2.getName());
//            }
//
//        });
//        EventTableModel issuesTableModel = new EventTableModel(sortedIssues, new PurchaseTableFormat());
//        table1 = new JTable(issuesTableModel);

//        TableComparatorChooser tableSorter = new TableComparatorChooser(table1, sortedIssues, false);
//        loadWeaponPartList();
        loadWeaponList();
        loadPartList();
        loadShipList();
    }

//    private void loadWeaponPartList() {
//        weaponList = new BasicEventList();
//        ArrayList<Payable> parts = new ArrayList<Payable>();
//        System.out.println(WeaponDataManager.retrieveAll("melee").length);
//        parts.addAll(Arrays.asList(WeaponDataManager.retrieveAll("melee")));
//        weaponList.addAll(parts);
//        System.out.println(parts.size());
//        SortedList sortedIssues = new SortedList(weaponList, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                Payable w1 = (Payable) o1;
//                Payable w2 = (Payable) o2;
//                return w1.getName().compareTo(w2.getName());
//            }
//
//        });
//        EventTableModel issuesTableModel = new EventTableModel(sortedIssues, new ShopTableFormat());
//        table2 = new JTable(issuesTableModel);
//
//
//        TableComparatorChooser tableSorter = new TableComparatorChooser(table2, sortedIssues, false);
//
//       table2.getColumnModel().getColumn(0).setPreferredWidth(100);   //L
//        table2.getColumnModel().getColumn(1).setPreferredWidth(800);  //name
//        table2.getColumnModel().getColumn(2).setPreferredWidth(300);  //wt
//        table2.getColumnModel().getColumn(3).setPreferredWidth(300);  //min dmg
//        table2.getColumnModel().getColumn(4).setPreferredWidth(300);  //avg
//        table2.getColumnModel().getColumn(5).setPreferredWidth(300);  //max
//        table2.getColumnModel().getColumn(6).setPreferredWidth(300);  //avg dmg/wt
//        table2.getColumnModel().getColumn(7).setPreferredWidth(200); //gold
//        table2.getColumnModel().getColumn(8).setPreferredWidth(200); //wood
//        table2.getColumnModel().getColumn(9).setPreferredWidth(200);  //ore
//        table2.getColumnModel().getColumn(10).setPreferredWidth(100); //prock
//        table2.getColumnModel().getColumn(11).setPreferredWidth(1000);
//    }


    private void loadWeaponList() {
        table2 = new JXTable();
        ((JXTable) table2).setColumnControlVisible(true);

        weaponTableModel = new WeaponTableModel();
        weaponTableModel.setType("cannons");

        table2.setModel(weaponTableModel);
        ((JXTable) table2).packAll();
    }

    private void loadPartList() {
        table3 = new JXTable();
        ((JXTable) table3).setColumnControlVisible(true);

        partTableModel = new PartTableModel();
        partTableModel.setType("engines");
        table3.setModel(partTableModel);
        ((JXTable) table3).packAll();
    }

    private void loadShipList() {
        shipTable = new JXTable();
        ((JXTable) shipTable).setColumnControlVisible(true);

        altShipTable = new JXTable();
        ((JXTable) shipTable).setColumnControlVisible(true);

        shipTableModel = new ShipTableModel();

        Ship s = ShipDataManager.retrieve("Windrider");
        if (s == null) {
            s = ShipDataManager.retrieve("Sail Boat");
        }

        altShipTableModel = new ShipTableModel();

        shipTableModel.setShip(s);
        altShipTableModel.setShip(s);
        shipTable.setModel(shipTableModel);
        altShipTable.setModel(altShipTableModel);
        ((JXTable) shipTable).packAll();
        ((JXTable) altShipTable).packAll();





    }


}
