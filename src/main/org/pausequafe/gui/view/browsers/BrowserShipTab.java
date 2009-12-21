/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.view.browsers;

import static org.pausequafe.misc.util.Constants.ARMOR_ICON;
import static org.pausequafe.misc.util.Constants.CAPA_ICON;
import static org.pausequafe.misc.util.Constants.CAPA_RECHARGE_ICON;
import static org.pausequafe.misc.util.Constants.CARGO_ICON;
import static org.pausequafe.misc.util.Constants.CPU_ICON;
import static org.pausequafe.misc.util.Constants.DRONES_ICON;
import static org.pausequafe.misc.util.Constants.EM_ICON;
import static org.pausequafe.misc.util.Constants.EVE_ICONS_PATH;
import static org.pausequafe.misc.util.Constants.EXPLO_ICON;
import static org.pausequafe.misc.util.Constants.GUN_ICON;
import static org.pausequafe.misc.util.Constants.HI_SLOT_ICON;
import static org.pausequafe.misc.util.Constants.INERTIA_ICON;
import static org.pausequafe.misc.util.Constants.KINETIC_ICON;
import static org.pausequafe.misc.util.Constants.LOW_SLOT_ICON;
import static org.pausequafe.misc.util.Constants.MASS_ICON;
import static org.pausequafe.misc.util.Constants.MED_SLOT_ICON;
import static org.pausequafe.misc.util.Constants.METAGROUP_ICONS_TAG;
import static org.pausequafe.misc.util.Constants.MISSILE_ICON;
import static org.pausequafe.misc.util.Constants.NO_ITEM_SELECTED_ICON;
import static org.pausequafe.misc.util.Constants.PWG_ICON;
import static org.pausequafe.misc.util.Constants.RADIUS_ICON;
import static org.pausequafe.misc.util.Constants.RANGE_ICON;
import static org.pausequafe.misc.util.Constants.RIG_SLOT_ICON;
import static org.pausequafe.misc.util.Constants.SCAN_RES_ICON;
import static org.pausequafe.misc.util.Constants.SENSOR_GRAVI_ICON;
import static org.pausequafe.misc.util.Constants.SENSOR_LADAR_ICON;
import static org.pausequafe.misc.util.Constants.SENSOR_MAGNETO_ICON;
import static org.pausequafe.misc.util.Constants.SENSOR_RADAR_ICON;
import static org.pausequafe.misc.util.Constants.SHIELD_ICON;
import static org.pausequafe.misc.util.Constants.SHIELD_RECHARGE_ICON;
import static org.pausequafe.misc.util.Constants.STRUCTURE_ICON;
import static org.pausequafe.misc.util.Constants.TARGET_COUNT_ICON;
import static org.pausequafe.misc.util.Constants.THERMIC_ICON;
import static org.pausequafe.misc.util.Constants.VELOCITY_ICON;
import static org.pausequafe.misc.util.Constants.VOLUME_ICON;
import static org.pausequafe.misc.util.Constants.WARP_ICON;
import static org.pausequafe.misc.util.SQLConstants.AGILITY_ATTID;
import static org.pausequafe.misc.util.SQLConstants.ARMOR_EM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.ARMOR_EXPLO_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.ARMOR_HP_ATTID;
import static org.pausequafe.misc.util.SQLConstants.ARMOR_KINE_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.ARMOR_THERM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.CALIBRATION_PONTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.CAPACITOR_CAPACITY_ATTID;
import static org.pausequafe.misc.util.SQLConstants.CAPACITOR_RECHARGE_TIME_ATTID;
import static org.pausequafe.misc.util.SQLConstants.CAPACITY_ATTID;
import static org.pausequafe.misc.util.SQLConstants.CPU_OUTPUT_ATTID;
import static org.pausequafe.misc.util.SQLConstants.DRONEBAY_CAPACITY_ATTID;
import static org.pausequafe.misc.util.SQLConstants.DRONE_BANDWIDTH;
import static org.pausequafe.misc.util.SQLConstants.GUN_HARDPOINTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.HI_SLOTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.LAUNCHER_HARDPOINTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.LOW_SLOTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.MASS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.MAX_VELOCITY_ATTID;
import static org.pausequafe.misc.util.SQLConstants.MED_SLOTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.POWERGRID_OUTPUT_ATTID;
import static org.pausequafe.misc.util.SQLConstants.RADIUS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.RIG_SLOTS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SCAN_RESOLUTION_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SENSOR_GRAVI_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SENSOR_LADAR_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SENSOR_MAGNETO_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SENSOR_RADAR_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_EM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_EXPLO_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_HP_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_KINE_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_RECHARGE_TIME_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SHIELD_THERM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.SIGNATURE_RADIUS_ATTID;
import static org.pausequafe.misc.util.SQLConstants.STRUCT_EM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.STRUCT_EXPLO_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.STRUCT_HP_ATTID;
import static org.pausequafe.misc.util.SQLConstants.STRUCT_KINE_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.STRUCT_THERM_RESIST_ATTID;
import static org.pausequafe.misc.util.SQLConstants.TARGETING_RANGE_ATTID;
import static org.pausequafe.misc.util.SQLConstants.TARGET_COUNT_ATTID;
import static org.pausequafe.misc.util.SQLConstants.VOLUME_ATTID;
import static org.pausequafe.misc.util.SQLConstants.WARP_SPEED_BASE;
import static org.pausequafe.misc.util.SQLConstants.WARP_SPEED_MULTUPLIER_ATTID;

import java.io.File;

import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserShipTab extends AbstractBrowserTab {

    // /////////////////
    // private fields //
    // /////////////////
    private QTreeView prereqTree;
    private Ui_BrowserShipTab ui;

    // ///////////////
    // constructors //
    // ///////////////
    public BrowserShipTab(int marketGroupID) {
        super(marketGroupID);
    }

    public BrowserShipTab(QWidget parent, int marketGroupID) {
        super(parent, marketGroupID);
    }

    // ///////////////
    // widget setup //
    // ///////////////
    protected void setupUi() {
        ui = new Ui_BrowserShipTab();
        ui.setupUi(this);

        // we have to do designer work here
        // because this bastard can't create
        // a custom ItemTreeView
        ui.itemTree.dispose();
        ui.itemTree = new ItemTreeView(ui.rightArea);
        itemTree=ui.itemTree;
        itemTree.setObjectName("itemTree");
        QSizePolicy sizePolicy12 = new QSizePolicy(QSizePolicy.Policy.Preferred, 
                                                   QSizePolicy.Policy.Expanding);
        sizePolicy12.setHorizontalStretch((byte)5);
        sizePolicy12.setVerticalStretch((byte)0);
        sizePolicy12.setHeightForWidth(itemTree.sizePolicy().hasHeightForWidth());
        itemTree.setSizePolicy(sizePolicy12);
        itemTree.setMinimumSize(new QSize(0, 200));
        itemTree.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);
        itemTree.setHorizontalScrollBarPolicy(
                com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAlwaysOff);
        itemTree.setProperty("showDropIndicator", false);
        itemTree.setDragEnabled(true);
        itemTree.setDragDropMode(com.trolltech.qt.gui.QAbstractItemView.DragDropMode.DragOnly);
        itemTree.setIndentation(20);
        itemTree.setUniformRowHeights(true);
        itemTree.setSortingEnabled(true);
        itemTree.setHeaderHidden(true);
        ui.verticalLayout.addWidget(itemTree);
        // end designer work
        
        itemDescription = ui.itemDescription;
        prereqTree = ui.prereqTree;
        filterLineEdit = ui.serachLineEdit;

        ui.itemDescription.setAcceptRichText(true);

        ui.iconLabel.setPixmap(new QPixmap(NO_ITEM_SELECTED_ICON));
        ui.itemNameLabel.setText("<font size=5>No item selected...</font>");

        initIcons();
        ui.stackedWidget.widget(0).setEnabled(false);

        ui.attributesTable.verticalHeader().setVisible(false);

        QAction enterFilterArea = new QAction(this);
        enterFilterArea.triggered.connect(filterLineEdit, "setFocus()");
        enterFilterArea.setShortcut("Ctrl+F");
    }

    private void initIcons() {
        ui.armorIcon.setPixmap(new QPixmap(ARMOR_ICON));
        ui.capaIcon.setPixmap(new QPixmap(CAPA_ICON));
        ui.capaRechargeIcon.setPixmap(new QPixmap(CAPA_RECHARGE_ICON));
        ui.cargoIcon.setPixmap(new QPixmap(CARGO_ICON));
        ui.cpuIcon.setPixmap(new QPixmap(CPU_ICON));
        ui.dronesIcon.setPixmap(new QPixmap(DRONES_ICON));
        ui.emIcon.setPixmap(new QPixmap(EM_ICON));
        ui.exploIcon.setPixmap(new QPixmap(EXPLO_ICON));
        ui.gunIcon.setPixmap(new QPixmap(GUN_ICON));
        ui.hiSlotIcon.setPixmap(new QPixmap(HI_SLOT_ICON));
        ui.inertiaIcon.setPixmap(new QPixmap(INERTIA_ICON));
        ui.kineIcon.setPixmap(new QPixmap(KINETIC_ICON));
        ui.lowSlotIcon.setPixmap(new QPixmap(LOW_SLOT_ICON));
        ui.massIcon.setPixmap(new QPixmap(MASS_ICON));
        ui.medSlotIcon.setPixmap(new QPixmap(MED_SLOT_ICON));
        ui.missileIcon.setPixmap(new QPixmap(MISSILE_ICON));
        ui.pwgIcon.setPixmap(new QPixmap(PWG_ICON));
        ui.radiusIcon.setPixmap(new QPixmap(RADIUS_ICON));
        ui.rangeIcon.setPixmap(new QPixmap(RANGE_ICON));
        ui.resolutionIcon.setPixmap(new QPixmap(SCAN_RES_ICON));
        ui.rigIcon.setPixmap(new QPixmap(RIG_SLOT_ICON));
        ui.sensorIcon.setPixmap(new QPixmap(SENSOR_MAGNETO_ICON));
        ui.sizeIcon.setPixmap(new QPixmap(VOLUME_ICON));
        ui.shieldIcon.setPixmap(new QPixmap(SHIELD_ICON));
        ui.shieldRechargeIcon.setPixmap(new QPixmap(SHIELD_RECHARGE_ICON));
        ui.structureIcon.setPixmap(new QPixmap(STRUCTURE_ICON));
        ui.targetIcon.setPixmap(new QPixmap(TARGET_COUNT_ICON));
        ui.thermIcon.setPixmap(new QPixmap(THERMIC_ICON));
        ui.velocityIcon.setPixmap(new QPixmap(VELOCITY_ICON));
        ui.volumeIcon.setPixmap(new QPixmap(VOLUME_ICON));
        ui.warpIcon.setPixmap(new QPixmap(WARP_ICON));
    }

    protected void initConnection() {
        super.initConnection();

    }

    // ////////
    // slots //
    // ////////
    protected void sort() {
        return;
    }

    public void currentItemSelected(QModelIndex index) {
        super.currentItemSelected(index);

        if (currentItemSelected != null) {
            ui.prereqFrame.setEnabled(true);
            ui.descriptionFrame.setEnabled(true);
            ui.attributesTable.setEnabled(true);
            ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName()
                    + "</font>");

            String icon = EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
            File iconFile = new File(icon);
            if (!iconFile.exists())
                icon = NO_ITEM_SELECTED_ICON;
            ui.iconLabel.setPixmap(new QPixmap(icon));

            String tagIconFile = METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
            ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
            ui.metaGroupIcon.raise();

            itemDescription.setText(currentItemSelected.getDescription());

            ItemTreeElement root = new ItemPrerequisiteElement(currentItemSelected);
            prereqModel = new ItemTreeModel(root);
            prereqModel.setSheet(sheet);
            prereqTree.setModel(prereqModel);
            prereqTree.expandAll();

            AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
            ui.attributesTable.setModel(tableModel);
            ui.attributesTable.resizeColumnsToContents();
            ui.attributesTable.resizeRowsToContents();
            fillShipInfo();

            ui.stackedWidget.widget(0).setEnabled(true);
        }

    }

    private void fillShipInfo() {
        double capaCapa = 0;
        double capaRechTime = 1;
        double shieldHP = 0;
        double shieldRechTime = 1;
        double sensor = 0;
        double warpSpeed = WARP_SPEED_BASE;

        for (ItemAttribute att : currentItemSelected.getAttributeList()) {
            switch (att.getAttributeID()) {
            case AGILITY_ATTID:
                ui.inertiaModifier.setText("" + att.getValue());
                break;
            case ARMOR_EM_RESIST_ATTID:
                ui.armorEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case ARMOR_EXPLO_RESIST_ATTID:
                ui.armorEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case ARMOR_HP_ATTID:
                ui.armorHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
                break;
            case ARMOR_KINE_RESIST_ATTID:
                ui.armorKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case ARMOR_THERM_RESIST_ATTID:
                ui.armorTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case CALIBRATION_PONTS_ATTID:
                ui.calibrationPoints.setText("<i>(" + Math.round(att.getValue()) + " points)</i>");
                break;
            case CAPACITOR_CAPACITY_ATTID:
                capaCapa = att.getValue();
                ui.capa.setText(Formater.printLong((long) capaCapa) + " GJ");
                break;
            case CAPACITOR_RECHARGE_TIME_ATTID:
                capaRechTime = att.getValue() / 1000;
                ui.capaRechargeTime.setText("<i>(" + (int) capaRechTime + " sec.)</i>");
                break;
            case CAPACITY_ATTID:
                ui.cargoLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
                break;
            case CPU_OUTPUT_ATTID:
                ui.cpu.setText(Formater.printLong((long) att.getValue()) + " TF");
                break;
            case DRONE_BANDWIDTH:
                ui.droneBandwidth.setText(Formater.printLong((long) att.getValue()) + " MBit/sec.");
                break;
            case DRONEBAY_CAPACITY_ATTID:
                ui.droneCapacity.setText(Formater.printLong((long) att.getValue()) + " m3");
                break;
            case GUN_HARDPOINTS_ATTID:
                ui.turretHardpoints.setText("" + (int) att.getValue());
                break;
            case HI_SLOTS_ATTID:
                ui.hiSlots.setText("" + (int) att.getValue());
                break;
            case LAUNCHER_HARDPOINTS_ATTID:
                ui.launcherHardpoints.setText("" + (int) att.getValue());
                break;
            case LOW_SLOTS_ATTID:
                ui.lowSlots.setText("" + (int) att.getValue());
                break;
            case MASS_ATTID:
                ui.mass.setText(Formater.printLong((long) att.getValue() / 1000) + " T");
                break;
            case MAX_VELOCITY_ATTID:
                ui.velocity.setText(Formater.printLong((long) att.getValue()) + " m/s");
                break;
            case MED_SLOTS_ATTID:
                ui.medSlots.setText("" + (int) att.getValue());
                break;
            case POWERGRID_OUTPUT_ATTID:
                ui.powergrid.setText(Formater.printLong((long) att.getValue()) + " MW");
                break;
            case RIG_SLOTS_ATTID:
                ui.rigSlots.setText("" + (int) att.getValue());
                break;
            case SCAN_RESOLUTION_ATTID:
                ui.scanResolution.setText((long) att.getValue() + " mm");
                break;
            case SENSOR_GRAVI_ATTID:
                if (att.getValue() > sensor) {
                    sensor = att.getValue();
                    ui.sensorStrength.setText((int) sensor + " points");
                    ui.sensorStrength.setToolTip("Gravimetric Sensor Strength");
                    ui.sensorIcon.setPixmap(new QPixmap(SENSOR_GRAVI_ICON));
                }
                break;
            case SENSOR_LADAR_ATTID:
                if (att.getValue() > sensor) {
                    sensor = att.getValue();
                    ui.sensorStrength.setText((int) sensor + " points");
                    ui.sensorStrength.setToolTip("LADAR Sensor Strength");
                    ui.sensorIcon.setPixmap(new QPixmap(SENSOR_LADAR_ICON));
                }
                break;
            case SENSOR_MAGNETO_ATTID:
                if (att.getValue() > sensor) {
                    sensor = att.getValue();
                    ui.sensorStrength.setText((int) sensor + " points");
                    ui.sensorStrength.setToolTip("Magnetometric Sensor Strength");
                    ui.sensorIcon.setPixmap(new QPixmap(SENSOR_MAGNETO_ICON));
                }
                break;
            case SENSOR_RADAR_ATTID:
                if (att.getValue() > sensor) {
                    sensor = att.getValue();
                    ui.sensorStrength.setText((int) sensor + " points");
                    ui.sensorStrength.setToolTip("RADAR Sensor Strength");
                    ui.sensorIcon.setPixmap(new QPixmap(SENSOR_RADAR_ICON));
                }
                break;
            case SHIELD_EM_RESIST_ATTID:
                ui.shieldEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case SHIELD_EXPLO_RESIST_ATTID:
                ui.shieldEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case SHIELD_HP_ATTID:
                shieldHP = att.getValue();
                ui.shieldHPLabel.setText(Formater.printLong((long) shieldHP) + " HP");
                break;
            case SHIELD_KINE_RESIST_ATTID:
                ui.shieldKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case SHIELD_RECHARGE_TIME_ATTID:
                shieldRechTime = att.getValue() / 1000;
                ui.shieldRechargeTime.setText("<i>(" + (int) shieldRechTime + " sec.)</i>");
                break;
            case SHIELD_THERM_RESIST_ATTID:
                ui.shieldTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case RADIUS_ATTID:
                ui.size.setText(Formater.printLong((long) att.getValue() * 2) + " m");
                break;
            case SIGNATURE_RADIUS_ATTID:
                ui.signatureRadius.setText(Formater.printLong((long) att.getValue()) + " m");
                break;
            case STRUCT_EM_RESIST_ATTID:
                ui.structEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case STRUCT_EXPLO_RESIST_ATTID:
                ui.structEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case STRUCT_HP_ATTID:
                ui.structureHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
                break;
            case STRUCT_KINE_RESIST_ATTID:
                ui.structKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case STRUCT_THERM_RESIST_ATTID:
                ui.structTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
                break;
            case TARGET_COUNT_ATTID:
                ui.targetCount.setText((int) att.getValue() + " targets");
                break;
            case TARGETING_RANGE_ATTID:
                ui.targetRange.setText((int) att.getValue() / 1000 + " Km");
                break;
            case VOLUME_ATTID:
                ui.volumeLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
                break;
            case WARP_SPEED_MULTUPLIER_ATTID:
                warpSpeed = att.getValue() * WARP_SPEED_BASE;
                break;
            }
        }
        ui.warpSpeed.setText(Formater.printDouble(warpSpeed) + " AU/s");
        ui.capaRechargeRate.setText(Formater.printDouble(capaCapa / capaRechTime) + " GJ/s");
        ui.shieldRechargeRate.setText(Formater.printDouble(shieldHP / shieldRechTime) + " HP/s");
    }

}
