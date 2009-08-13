package org.pausequafe.gui.view.browsers;

import java.util.List;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;

public class BrowsersWindow extends QWidget {

    Ui_BrowsersWindow ui = new Ui_BrowsersWindow();
    
    ////////////////////
    // private fields //
    ////////////////////
    private BrowserSkillTab skillBrowser;
    private BrowserShipTab shipBrowser;
    private BrowserItemTab moduleBrowser;
    
    private QToolBar toolBar;
    private QComboBox sheetCombo;
    
    
    //////////////////
    // constructors //
    //////////////////
    public BrowsersWindow() {
        this(null);
    }

    public BrowsersWindow(QWidget parent) {
        super(parent);
        setupUi();
    }
    
    //////////////////
    // widget setup //
    //////////////////
    private void setupUi(){
    	ui.setupUi(this);

    	this.setWindowTitle("Browsers");
    	
    	toolBar = new QToolBar(this);
    	ui.verticalLayout.insertWidget(0,toolBar);
    	
    	sheetCombo = new QComboBox(this);
    	toolBar.addWidget(new QLabel(" Active Character : "));
    	toolBar.addWidget(sheetCombo);
    	
    	sheetCombo.addItem("no character");
    	sheetCombo.currentIndexChanged.connect(this, "changeCurrentCharacter(int)");
    	
    	skillBrowser = new BrowserSkillTab(this, 150);
    	shipBrowser = new BrowserShipTab(this, 4);
    	moduleBrowser = new BrowserItemTab(this, 9);
    	
    	ui.tabWidget.removeTab(0);
    	
    	ui.tabWidget.addTab(skillBrowser, "Skills");
    	ui.tabWidget.addTab(shipBrowser, "Ships");
    	ui.tabWidget.addTab(moduleBrowser, "Ship Equipement");
    	
    	this.resize(1100, 700);
    }

    ///////////
    // slots //
    ///////////
    @SuppressWarnings("unused")
	private void changeCurrentCharacter(int index){
    	int i = index;
    	skillBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
    	shipBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
    	moduleBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
    }
    
	public void setSheetList(List<MonitoredCharacter> list) {
		sheetCombo.clear();
		for(MonitoredCharacter character : list){
			sheetCombo.addItem(character.getSheet().getName(), character.getSheet());
		}
	}


}
