package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import static org.pausequafe.misc.util.Constants.*;
import org.pausequafe.misc.util.Formater;
import static org.pausequafe.misc.util.SQLConstants.*;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserShipTab extends AbstractBrowserTab {

	////////////////////
	// private fields //
	////////////////////
	private QTreeView prereqTree;
	private Ui_BrowserShipTab ui;

	////////////////////
	// constructors   //
	////////////////////	
    public BrowserShipTab(int marketGroupID) {
    	super(marketGroupID);
    }

    public BrowserShipTab(QWidget parent, int marketGroupID) {
        super(parent,marketGroupID);
    }
    
	//////////////////
	// widget setup //
	//////////////////
    protected void setupUi(){
    	ui = new Ui_BrowserShipTab();
    	ui.setupUi(this);
    	
    	itemTree=ui.itemTree;
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

    private void initIcons(){
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


	///////////
	// slots //
	///////////
    protected void sort() {
    	return;    	
    }
    
	public void currentItemSelected(QModelIndex index) {
		super.currentItemSelected(index);
		
		if(currentItemSelected != null){
			ui.prereqFrame.setEnabled(true);
			ui.descriptionFrame.setEnabled(true);
			ui.attributesTable.setEnabled(true);
			ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");

			String icon = EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
			File iconFile = new File(icon);
			if(!iconFile.exists()) icon = NO_ITEM_SELECTED_ICON;
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
	
	private void fillShipInfo(){
		double capaCapa = 0;
		double capaRechTime = 1;
		double shieldHP = 0;
		double shieldRechTime = 1;
		double sensor = 0;
		double warpSpeed = WARP_SPEED_BASE;
		
		for(ItemAttribute att : currentItemSelected.getAttributeList()){
			switch(att.getAttributeID()){
			case AGILITY_ATTID :
				ui.inertiaModifier.setText("" + att.getValue());
				break;
			case ARMOR_EM_RESIST_ATTID :
				ui.armorEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case ARMOR_EXPLO_RESIST_ATTID :
				ui.armorEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case ARMOR_HP_ATTID :
				ui.armorHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
				break;
			case ARMOR_KINE_RESIST_ATTID :
				ui.armorKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case ARMOR_THERM_RESIST_ATTID :
				ui.armorTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case CALIBRATION_PONTS_ATTID :
				ui.calibrationPoints.setText("<i>("+ Math.round(att.getValue()) +" points)</i>");
				break;
			case CAPACITOR_CAPACITY_ATTID :
				capaCapa = att.getValue();
				ui.capa.setText(Formater.printLong((long) capaCapa) + " GJ");
				break;
			case CAPACITOR_RECHARGE_TIME_ATTID :
				capaRechTime = att.getValue() / 1000;
				ui.capaRechargeTime.setText("<i>(" + (int) capaRechTime + " sec.)</i>");
				break;
			case CAPACITY_ATTID :
				ui.cargoLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case CPU_OUTPUT_ATTID :
				ui.cpu.setText(Formater.printLong((long) att.getValue()) + " TF");
				break;
			case DRONE_BANDWIDTH :
				ui.droneBandwidth.setText(Formater.printLong((long) att.getValue()) + " MBit/sec.");
				break;
			case DRONEBAY_CAPACITY_ATTID :
				ui.droneCapacity.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case GUN_HARDPOINTS_ATTID :
				ui.turretHardpoints.setText("" + (int) att.getValue());
				break;
			case HI_SLOTS_ATTID :
				ui.hiSlots.setText("" + (int) att.getValue());
				break;
			case LAUNCHER_HARDPOINTS_ATTID :
				ui.launcherHardpoints.setText("" + (int) att.getValue());
				break;
			case LOW_SLOTS_ATTID :
				ui.lowSlots.setText("" + (int) att.getValue());
				break;
			case MASS_ATTID :
				ui.mass.setText(Formater.printLong((long) att.getValue()/1000) + " T");
				break;
			case MAX_VELOCITY_ATTID :
				ui.velocity.setText(Formater.printLong((long) att.getValue()) + " m/s");
				break;
			case MED_SLOTS_ATTID :
				ui.medSlots.setText("" + (int) att.getValue());
				break;
			case POWERGRID_OUTPUT_ATTID :
				ui.powergrid.setText(Formater.printLong((long) att.getValue()) + " MW");
				break;
			case RIG_SLOTS_ATTID :
				ui.rigSlots.setText("" + (int) att.getValue());
				break;
			case SCAN_RESOLUTION_ATTID :
				ui.scanResolution.setText((long) att.getValue() + " mm");
				break;
			case SENSOR_GRAVI_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("Gravimetric Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(SENSOR_GRAVI_ICON));
				}
				break;
			case SENSOR_LADAR_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("LADAR Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(SENSOR_LADAR_ICON));
				}
				break;
			case SENSOR_MAGNETO_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("Magnetometric Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(SENSOR_MAGNETO_ICON));
				}
				break;
			case SENSOR_RADAR_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("RADAR Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(SENSOR_RADAR_ICON));
				}
				break;
			case SHIELD_EM_RESIST_ATTID :
				ui.shieldEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SHIELD_EXPLO_RESIST_ATTID :
				ui.shieldEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SHIELD_HP_ATTID :
				shieldHP = att.getValue();
				ui.shieldHPLabel.setText(Formater.printLong((long) shieldHP) + " HP");
				break;
			case SHIELD_KINE_RESIST_ATTID :
				ui.shieldKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SHIELD_RECHARGE_TIME_ATTID :
				shieldRechTime = att.getValue() / 1000;
				ui.shieldRechargeTime.setText("<i>(" + (int) shieldRechTime + " sec.)</i>");
				break;
			case SHIELD_THERM_RESIST_ATTID :
				ui.shieldTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case RADIUS_ATTID :
				ui.size.setText(Formater.printLong((long) att.getValue()*2) + " m");
				break;
			case SIGNATURE_RADIUS_ATTID :
				ui.signatureRadius.setText(Formater.printLong((long) att.getValue()) + " m");
				break;
			case STRUCT_EM_RESIST_ATTID :
				ui.structEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case STRUCT_EXPLO_RESIST_ATTID :
				ui.structEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case STRUCT_HP_ATTID :
				ui.structureHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
				break;
			case STRUCT_KINE_RESIST_ATTID :
				ui.structKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case STRUCT_THERM_RESIST_ATTID :
				ui.structTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case TARGET_COUNT_ATTID :
				ui.targetCount.setText((int) att.getValue() + " targets");
				break;
			case TARGETING_RANGE_ATTID :
				ui.targetRange.setText((int) att.getValue()/1000 + " Km");
				break;
			case VOLUME_ATTID :
				ui.volumeLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case WARP_SPEED_MULTUPLIER_ATTID :
				warpSpeed = att.getValue() * WARP_SPEED_BASE;
				break;
			}
		}
		ui.warpSpeed.setText(Formater.printDouble(warpSpeed) + " AU/s");
		ui.capaRechargeRate.setText(Formater.printDouble(capaCapa/capaRechTime) + " GJ/s");
		ui.shieldRechargeRate.setText(Formater.printDouble(shieldHP/shieldRechTime) + " HP/s");
	}

}
