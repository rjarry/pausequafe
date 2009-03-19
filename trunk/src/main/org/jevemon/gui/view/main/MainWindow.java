package org.jevemon.gui.view.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.jevemon.data.business.APIData;
import org.jevemon.data.dao.SessionDAO;
import org.jevemon.misc.exceptions.JEVEMonException;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class MainWindow extends QMainWindow {

    
//    private QWidget centralwidget;
//    private QMenuBar menuBar;
//    private QMenu menuFile;
//    private QMenu menuPlan;
//    private QMenu menu_Options;
//    private QStatusBar statusBar;
    private QTabWidget tabWidget;
	
//    private QAction actionNew_skill_plan;
//    private QAction actionManage_Skill_plans;
    private QAction actionAdd_character;
    private QAction actionDelete_current_character;
//    private QAction action_Export_Settings;
//    private QAction action_Import_Settings;
//    private QAction actionExport_Character_Info;
    private QAction action_Quit;
//    private QAction actionPreferences;
	
	
    private Ui_MainWindow ui = new Ui_MainWindow();

    public static void main(String[] args) {
        QApplication.initialize(args);

        MainWindow testMainWindow = null;
		try {
			testMainWindow = new MainWindow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JEVEMonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        testMainWindow.baseSize();

        QApplication.exec();
    }

    public MainWindow() throws SQLException, IOException, JEVEMonException {
        this(null);
    }

    public MainWindow(QWidget parent) throws SQLException, IOException, JEVEMonException {
        super(parent);
        ui.setupUi(this);
        setupUi();
        this.show();

        List<APIData> list = SessionDAO.getInstance().getMonitoredCharacters();
        for(APIData data : list){
        	CharacterTab tab = new CharacterTab();
			tabWidget.addTab(tab, data.getCharacterName());
			tab.updateCharacterInfo(data);
        }
        
        
    }

	private void setupUi() {
		action_Quit = (QAction) this.findChild(QAction.class, "action_Quit");
		action_Quit.triggered.connect(QApplication.instance(), "quit()");
		action_Quit.setShortcut("Ctrl+Q");
		tabWidget = new QTabWidget();
		
		QVBoxLayout treeLayout = new QVBoxLayout((QWidget) this.findChild(QWidget.class, "centralWidget"));
    	treeLayout.setContentsMargins(0,0,0,0);
    	treeLayout.setSpacing(0);
    	
    	treeLayout.addWidget(tabWidget);
    	
    	actionAdd_character = (QAction) this.findChild(QAction.class, "actionAdd_character");
    	actionAdd_character.triggered.connect(this, "addCharacter()");
    	
    	actionDelete_current_character = (QAction) this.findChild(QAction.class, "actionDelete_current_character");
    	actionDelete_current_character.triggered.connect(this, "deleteCharacterDialog()");
    	actionDelete_current_character.setShortcut("Ctrl+W");
	}
	
	@SuppressWarnings("unused")
	private void addCharacter() throws JEVEMonException, IOException{
		AddCharacterDialog addCharDialog = new AddCharacterDialog(this);
		addCharDialog.exec();
		if(addCharDialog.result() == QDialog.DialogCode.Accepted.value()){
			APIData data = addCharDialog.getChosenCharacter();
			CharacterTab tab = new CharacterTab();
			tabWidget.addTab(tab, data.getCharacterName());
			tab.updateCharacterInfo(data);
			try {
				SessionDAO.getInstance().addMonitoredCharacter(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void deleteCharacterDialog() throws JEVEMonException, IOException{
		DeleteCharacterDialog delCharDialog = 
			new DeleteCharacterDialog(this, tabWidget.tabText(tabWidget.currentIndex()));
		delCharDialog.accepted.connect(this, "removeTab()");
		delCharDialog.exec();
	}
	
	@SuppressWarnings("unused")
	private void removeTab(){
		int characterID = ((CharacterTab) tabWidget.currentWidget()).getSheet().getCharacterID();
		try {
			SessionDAO.getInstance().removeMonitoredCharacter(characterID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabWidget.removeTab(tabWidget.currentIndex());
	}
    
    
    
    
    
}
