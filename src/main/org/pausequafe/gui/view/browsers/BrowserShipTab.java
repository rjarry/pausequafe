package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSizePolicy;
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
    	
    	// we have to do designer work here
    	// because this bastard can't create
    	// a custom ItemTreeView
    	ui.itemTree.dispose();
    	ui.itemTree = new ItemTreeView(ui.rightArea);
    	itemTree=ui.itemTree;
        itemTree.setObjectName("itemTree");
        QSizePolicy sizePolicy6 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy6.setHorizontalStretch((byte)5);
        sizePolicy6.setVerticalStretch((byte)0);
        sizePolicy6.setHeightForWidth(itemTree.sizePolicy().hasHeightForWidth());
        itemTree.setSizePolicy(sizePolicy6);
        itemTree.setMinimumSize(new QSize(0, 200));
        itemTree.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);
        itemTree.setHorizontalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        itemTree.setProperty("showDropIndicator", false);
        itemTree.setDragEnabled(true);
        itemTree.setDragDropMode(com.trolltech.qt.gui.QAbstractItemView.DragDropMode.DragOnly);
        itemTree.setIndentation(20);
        itemTree.setUniformRowHeights(true);
        itemTree.setSortingEnabled(true);
        itemTree.setHeaderHidden(true);

    	ui.verticalLayout.addWidget(ui.itemTree);
    	// end designer work

    	itemDescription = ui.itemDescription;
    	prereqTree = ui.prereqTree;
    	filterLineEdit = ui.serachLineEdit;
    	
		ui.itemDescription.setAcceptRichText(true);
		
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
		
		initIcons();
		ui.stackedWidget.widget(0).setEnabled(false);
		
		ui.attributesTable.verticalHeader().setVisible(false);
    }

    private void initIcons(){
    	ui.armorIcon.setPixmap(new QPixmap(Constants.ARMOR_ICON));
    	ui.capaIcon.setPixmap(new QPixmap(Constants.CAPA_ICON));
    	ui.capaRechargeIcon.setPixmap(new QPixmap(Constants.CAPA_RECHARGE_ICON));
    	ui.cargoIcon.setPixmap(new QPixmap(Constants.CARGO_ICON));
    	ui.cpuIcon.setPixmap(new QPixmap(Constants.CPU_ICON));
    	ui.dronesIcon.setPixmap(new QPixmap(Constants.DRONES_ICON));
    	ui.emIcon.setPixmap(new QPixmap(Constants.EM_ICON));
    	ui.exploIcon.setPixmap(new QPixmap(Constants.EXPLO_ICON));
    	ui.gunIcon.setPixmap(new QPixmap(Constants.GUN_ICON));
    	ui.hiSlotIcon.setPixmap(new QPixmap(Constants.HI_SLOT_ICON));
    	ui.inertiaIcon.setPixmap(new QPixmap(Constants.INERTIA_ICON));
    	ui.kineIcon.setPixmap(new QPixmap(Constants.KINETIC_ICON));
    	ui.lowSlotIcon.setPixmap(new QPixmap(Constants.LOW_SLOT_ICON));
    	ui.massIcon.setPixmap(new QPixmap(Constants.MASS_ICON));
    	ui.medSlotIcon.setPixmap(new QPixmap(Constants.MED_SLOT_ICON));
    	ui.missileIcon.setPixmap(new QPixmap(Constants.MISSILE_ICON));
    	ui.pwgIcon.setPixmap(new QPixmap(Constants.PWG_ICON));
    	ui.radiusIcon.setPixmap(new QPixmap(Constants.RADIUS_ICON));
    	ui.rangeIcon.setPixmap(new QPixmap(Constants.RANGE_ICON));
    	ui.resolutionIcon.setPixmap(new QPixmap(Constants.SCAN_RES_ICON));
    	ui.rigIcon.setPixmap(new QPixmap(Constants.RIG_SLOT_ICON));
    	ui.sensorIcon.setPixmap(new QPixmap(Constants.SENSOR_MAGNETO_ICON));
    	ui.sizeIcon.setPixmap(new QPixmap(Constants.VOLUME_ICON));
    	ui.shieldIcon.setPixmap(new QPixmap(Constants.SHIELD_ICON));
    	ui.shieldRechargeIcon.setPixmap(new QPixmap(Constants.SHIELD_RECHARGE_ICON));
    	ui.structureIcon.setPixmap(new QPixmap(Constants.STRUCTURE_ICON));
    	ui.targetIcon.setPixmap(new QPixmap(Constants.TARGET_COUNT_ICON));
    	ui.thermIcon.setPixmap(new QPixmap(Constants.THERMIC_ICON));
    	ui.velocityIcon.setPixmap(new QPixmap(Constants.VELOCITY_ICON));
    	ui.volumeIcon.setPixmap(new QPixmap(Constants.VOLUME_ICON));
    	ui.warpIcon.setPixmap(new QPixmap(Constants.WARP_ICON));
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
			ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");

			String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
			File iconFile = new File(icon);
			if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
			ui.iconLabel.setPixmap(new QPixmap(icon));

			String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
			ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
			ui.metaGroupIcon.raise();

			itemDescription.setText(currentItemSelected.getDescription());

			ItemTreeElement root = new ItemPrerequisiteElement(currentItemSelected);
			prereqModel = new ItemTreeModel(root);
			prereqModel.setSheet(sheet);
			prereqTree.setModel(prereqModel);

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
		double warpSpeed = SQLConstants.WARP_SPEED_BASE;
		
		for(ItemAttribute att : currentItemSelected.getAttributeList()){
			switch(att.getAttributeID()){
			case SQLConstants.AGILITY_ATTID :
				ui.inertiaModifier.setText("" + att.getValue());
				break;
			case SQLConstants.ARMOR_EM_RESIST_ATTID :
				ui.armorEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.ARMOR_EXPLO_RESIST_ATTID :
				ui.armorEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.ARMOR_HP_ATTID :
				ui.armorHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
				break;
			case SQLConstants.ARMOR_KINE_RESIST_ATTID :
				ui.armorKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.ARMOR_THERM_RESIST_ATTID :
				ui.armorTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.CALIBRATION_PONTS_ATTID :
				ui.calibrationPoints.setText("<i>("+ Math.round(att.getValue()) +" points)</i>");
				break;
			case SQLConstants.CAPACITOR_CAPACITY_ATTID :
				capaCapa = att.getValue();
				ui.capa.setText(Formater.printLong((long) capaCapa) + " GJ");
				break;
			case SQLConstants.CAPACITOR_RECHARGE_TIME_ATTID :
				capaRechTime = att.getValue() / 1000;
				ui.capaRechargeTime.setText("<i>(" + (int) capaRechTime + " sec.)</i>");
				break;
			case SQLConstants.CAPACITY_ATTID :
				ui.cargoLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case SQLConstants.CPU_OUTPUT_ATTID :
				ui.cpu.setText(Formater.printLong((long) att.getValue()) + " TF");
				break;
			case SQLConstants.DRONE_BANDWIDTH :
				ui.droneBandwidth.setText(Formater.printLong((long) att.getValue()) + " MBit/sec.");
				break;
			case SQLConstants.DRONEBAY_CAPACITY_ATTID :
				ui.droneCapacity.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case SQLConstants.GUN_HARDPOINTS_ATTID :
				ui.turretHardpoints.setText("" + (int) att.getValue());
				break;
			case SQLConstants.HI_SLOTS_ATTID :
				ui.hiSlots.setText("" + (int) att.getValue());
				break;
			case SQLConstants.LAUNCHER_HARDPOINTS_ATTID :
				ui.launcherHardpoints.setText("" + (int) att.getValue());
				break;
			case SQLConstants.LOW_SLOTS_ATTID :
				ui.lowSlots.setText("" + (int) att.getValue());
				break;
			case SQLConstants.MASS_ATTID :
				ui.mass.setText(Formater.printLong((long) att.getValue()/1000) + " T");
				break;
			case SQLConstants.MAX_VELOCITY_ATTID :
				ui.velocity.setText(Formater.printLong((long) att.getValue()) + " m/s");
				break;
			case SQLConstants.MED_SLOTS_ATTID :
				ui.medSlots.setText("" + (int) att.getValue());
				break;
			case SQLConstants.POWERGRID_OUTPUT_ATTID :
				ui.powergrid.setText(Formater.printLong((long) att.getValue()) + " MW");
				break;
			case SQLConstants.RIG_SLOTS_ATTID :
				ui.rigSlots.setText("" + (int) att.getValue());
				break;
			case SQLConstants.SCAN_RESOLUTION_ATTID :
				ui.scanResolution.setText((long) att.getValue() + " mm");
				break;
			case SQLConstants.SENSOR_GRAVI_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("Gravimetric Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(Constants.SENSOR_GRAVI_ICON));
				}
				break;
			case SQLConstants.SENSOR_LADAR_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("LADAR Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(Constants.SENSOR_LADAR_ICON));
				}
				break;
			case SQLConstants.SENSOR_MAGNETO_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("Magnetometric Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(Constants.SENSOR_MAGNETO_ICON));
				}
				break;
			case SQLConstants.SENSOR_RADAR_ATTID :
				if(att.getValue() > sensor){
					sensor = att.getValue();
					ui.sensorStrength.setText((int) sensor + " points");
					ui.sensorStrength.setToolTip("RADAR Sensor Strength");
					ui.sensorIcon.setPixmap(new QPixmap(Constants.SENSOR_RADAR_ICON));
				}
				break;
			case SQLConstants.SHIELD_EM_RESIST_ATTID :
				ui.shieldEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.SHIELD_EXPLO_RESIST_ATTID :
				ui.shieldEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.SHIELD_HP_ATTID :
				shieldHP = att.getValue();
				ui.shieldHPLabel.setText(Formater.printLong((long) shieldHP) + " HP");
				break;
			case SQLConstants.SHIELD_KINE_RESIST_ATTID :
				ui.shieldKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.SHIELD_RECHARGE_TIME_ATTID :
				shieldRechTime = att.getValue() / 1000;
				ui.shieldRechargeTime.setText("<i>(" + (int) shieldRechTime + " sec.)</i>");
				break;
			case SQLConstants.SHIELD_THERM_RESIST_ATTID :
				ui.shieldTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.RADIUS_ATTID :
				ui.size.setText(Formater.printLong((long) att.getValue()*2) + " m");
				break;
			case SQLConstants.SIGNATURE_RADIUS_ATTID :
				ui.signatureRadius.setText(Formater.printLong((long) att.getValue()) + " m");
				break;
			case SQLConstants.STRUCT_EM_RESIST_ATTID :
				ui.structEMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.STRUCT_EXPLO_RESIST_ATTID :
				ui.structEXPLOResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.STRUCT_HP_ATTID :
				ui.structureHPLabel.setText(Formater.printLong((long) att.getValue()) + " HP");
				break;
			case SQLConstants.STRUCT_KINE_RESIST_ATTID :
				ui.structKINEResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.STRUCT_THERM_RESIST_ATTID :
				ui.structTHERMResist.setText(Math.round((1.0 - att.getValue()) * 100.0) + "%");
				break;
			case SQLConstants.TARGET_COUNT_ATTID :
				ui.targetCount.setText((int) att.getValue() + " targets");
				break;
			case SQLConstants.TARGETING_RANGE_ATTID :
				ui.targetRange.setText((int) att.getValue()/1000 + " Km");
				break;
			case SQLConstants.VOLUME_ATTID :
				ui.volumeLabel.setText(Formater.printLong((long) att.getValue()) + " m3");
				break;
			case SQLConstants.WARP_SPEED_MULTUPLIER_ATTID :
				warpSpeed = att.getValue() * SQLConstants.WARP_SPEED_BASE;
				break;
			}
		}
		ui.warpSpeed.setText(Formater.printDouble(warpSpeed) + " AU/s");
		ui.capaRechargeRate.setText(Formater.printDouble(capaCapa/capaRechTime) + " GJ/s");
		ui.shieldRechargeRate.setText(Formater.printDouble(shieldHP/shieldRechTime) + " HP/s");
	}

}
