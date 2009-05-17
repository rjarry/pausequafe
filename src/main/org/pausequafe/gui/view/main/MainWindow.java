package org.pausequafe.gui.view.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.pausequafe.StyleSheetEditor;
import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.ServerStatus;
import org.pausequafe.data.dao.MonitoredCharacterDAO;
import org.pausequafe.gui.view.browsers.BrowsersWindow;
import org.pausequafe.gui.view.character.CharacterTab;
import org.pausequafe.gui.view.misc.AboutPQ;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;
import org.pausequafe.misc.util.ServerStatusRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMovie;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QFrame.Shape;

public class MainWindow extends QMainWindow {

    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private Ui_MainWindow ui = new Ui_MainWindow();
	
    private QTabWidget tabWidget;
    private QLabel eveTimeLabel;
    private QTimer eveTimeTimer;

    private QLabel apiActivityIcon;
    private int requestCount = 0;
    
    private QLabel serverStatusIndicator;
    private QTimer serverStatusTimer;
    
    private BrowsersWindow browsers;
	
    private MonitoredCharacterList characterList;
	


    //////////////////
    // constructors //
    //////////////////
    public MainWindow(){
        this(null);
    }

    public MainWindow(QWidget parent) {
        super(parent,Qt.WindowType.Window);
        ui.setupUi(this);
        setupUi();
        updateTime();
        requestServerStatus();
        
        characterList = new MonitoredCharacterList();
        
		try {
			List<APIData> list = MonitoredCharacterDAO.getInstance().getMonitoredCharacters();
			for(APIData data : list){
				MonitoredCharacter character = new MonitoredCharacter(data);
				characterList.addCharacter(character);
				addTab(character);
			}
		} catch (PQSQLDriverNotFoundException e) {
			ErrorMessage error = new ErrorMessage(this,tr(Constants.DRIVER_NOT_FOUND_ERROR));
			error.exec();
		} catch (PQUserDatabaseFileCorrupted e) {
			ErrorQuestion error = new ErrorQuestion(this,tr(Constants.USER_DB_CORRUPTED_ERROR));
			error.exec();
			if(error.result() == QDialog.DialogCode.Accepted.value()){
				File userDb = new File(SQLConstants.USER_DATABASE_FILE);
				userDb.delete();
			}
		}
        
        
    }

	private void setupUi() {
		// init icon & window name
		this.setWindowIcon(new QIcon(Constants.WINDOW_ICON));
		this.setWindowTitle("Pause Quafé");
		
		// Init menu actions
		ui.actionQuit.triggered.connect(QApplication.instance(), "quit()");
		ui.actionQuit.setShortcut("Ctrl+Q");
		
		ui.actionAdd_Character.triggered.connect(this, "addCharacterDialog()");
    	ui.actionAdd_Character.setShortcut("Ctrl+N");
    	
    	ui.actionReorder_Characters.triggered.connect(this, "reorderCharactersDialog()");
    	
    	ui.actionDelete_Current_Character.triggered.connect(this, "deleteCharacterDialog()");
    	ui.actionDelete_Current_Character.setShortcut("Ctrl+W");

    	ui.actionOpen_Browsers.triggered.connect(this, "openBrowsers()");
    	ui.actionOpen_Browsers.setShortcut("Ctrl+B");
    	
    	ui.action_Settings.triggered.connect(this, "openSettingsWindow()");
    	ui.action_Settings.setShortcut("Ctrl+P");
    	
    	ui.action_Stylesheet_editor.triggered.connect(this, "openStylesheetEditor()");
    	
    	ui.actionAbout_PQ.triggered.connect(this, "aboutPQ()");
    	
    	
    	// Init central widget
    	tabWidget = new QTabWidget();
    	
    	QWidget centralWidget = (QWidget) this.findChild(QWidget.class, "centralWidget");
    	QVBoxLayout centralLayout = new QVBoxLayout();
    	centralWidget.setLayout(centralLayout);
    	centralLayout.setContentsMargins(0,0,0,0);
    	centralLayout.setSpacing(0);
    	
    	centralLayout.addWidget(tabWidget);
    	
    	// Init status bar
    	
    	serverStatusIndicator = new QLabel();
    	serverStatusIndicator.setFrameShape(Shape.NoFrame);
    	serverStatusTimer = new QTimer();
    	serverStatusTimer.timeout.connect(this, "requestServerStatus()");
    	serverStatusTimer.start(5 * Constants.MINUTE);
    	updateServerStatus(new ServerStatus());
    	
    	eveTimeLabel = new QLabel();
    	eveTimeLabel.setFrameShape(Shape.NoFrame);
    	TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    	eveTimeTimer = new QTimer(this);
    	eveTimeTimer.timeout.connect(this, "updateTime()");
    	eveTimeTimer.start(Constants.MINUTE);
    	
    	apiActivityIcon = new QLabel();
    	apiActivityIcon.setFrameShape(Shape.NoFrame);
		apiActivityIcon.setPixmap(new QPixmap(Constants.IDLE_ICON));
		
		ui.statusBar.addPermanentWidget(serverStatusIndicator, 1);
		ui.statusBar.addPermanentWidget(eveTimeLabel,1);
		ui.statusBar.addPermanentWidget(new QWidget(),100);
		ui.statusBar.addPermanentWidget(apiActivityIcon,1);
		
		this.resize(300, 700);

	}
	
	void connard(Integer moncul){
		System.out.println("mon cul sur la commode");
	}
	
	
	
	
	/**
	 * Updates the eve time label
	 */
	private void updateTime(){
		Date gmt = Calendar.getInstance().getTime();
		eveTimeLabel.setText("Current EVE Time : " + TIME_FORMAT.format(gmt));
	}
	
	/**
	 * Requests Tranquility server status from eve API
	 */
	private void requestServerStatus(){
		ServerStatusRequest request = new ServerStatusRequest();
		QThread thread = new QThread(request);
		request.finished.connect(this, "updateServerStatus(ServerStatus)");
		thread.start();
	}
	
	/**
	 * Updates server status icon
	 * Invoqued by the <code>serverStatusRequest.finished</code> signal
	 * 
	 * @param status 
	 * 			the server's status
	 */
	private void updateServerStatus(ServerStatus status){
		if(status.isOnLine()){
			serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_ONLINE_ICON));
			serverStatusIndicator.setToolTip("Tranquility Server Online (" + status.getPlayerCount() + " pilots)");
		} else {
			if(status.isUnknown()){
				serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_UNKONWN_ICON));
				serverStatusIndicator.setToolTip("Server Status Unknown");
			} else {
				serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_OFFLINE_ICON));
				serverStatusIndicator.setToolTip("Tranquility Server Offline");
			}
		}
	}
	
	/**
	 * Updates fetching indicator
	 * Invoked by the <code>CharacterTab.requestStarted</code> signal
	 */
	@SuppressWarnings("unused")
	private synchronized void incrementRequestCount(){
		if(requestCount==0){
			QMovie movie = new QMovie(Constants.DOWNLOADING_ICON);
			movie.setSpeed(90);
			apiActivityIcon.setPixmap(null);
			apiActivityIcon.setMovie(movie);
			movie.start();
			apiActivityIcon.setToolTip("Accessing data from EVE-Online API server");
		}
		requestCount++;
	}
	
	/**
	 * Updates fetching indicator
	 * Invoqued by the <code>CharacterTab.requestFinished</code> signal
	 */
	@SuppressWarnings("unused")
	private synchronized void decrementRequestCount(){
		requestCount--;
		if(requestCount==0){
			QPixmap pixmap = new QPixmap(Constants.IDLE_ICON);
			apiActivityIcon.setMovie(null);
			apiActivityIcon.setPixmap(pixmap);
			apiActivityIcon.setToolTip("API activity : <b>idle</b> Right click to refresh all characters.");
		}
	}
	
	/**
	 * Invoked by the menu action "Add Character..."
	 * 
	 * @throws PQException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void addCharacterDialog(){
		AddCharacterDialog addCharDialog = new AddCharacterDialog(this);
		addCharDialog.exec();
		if(addCharDialog.result() == QDialog.DialogCode.Accepted.value()){
			APIData data = addCharDialog.getChosenCharacter();
			try {
				MonitoredCharacter character = new MonitoredCharacter(data);
				characterList.addCharacter(character);
				addTab(character);
			} catch (PQSQLDriverNotFoundException e) {
				ErrorMessage error = new ErrorMessage(this,tr(Constants.DRIVER_NOT_FOUND_ERROR));
				error.exec();
			} catch (PQUserDatabaseFileCorrupted e) {
				ErrorQuestion error = new ErrorQuestion(this,tr(Constants.USER_DB_CORRUPTED_ERROR));
				error.exec();
				if(error.result() == QDialog.DialogCode.Accepted.value()){
					File userDb = new File(SQLConstants.USER_DATABASE_FILE);
					userDb.delete();
				}
			}
		}
	}
	
	
	private void addTab(MonitoredCharacter character){
		CharacterTab tab = new CharacterTab(character);
		tab.requestStarted.connect(this, "incrementRequestCount()");
		tab.requestFinished.connect(this, "decrementRequestCount()");
		tabWidget.addTab(tab, character.getApi().getCharacterName());
	}
	
	/**
	 * Invoked by the menu action "Reorder Characters..."
	 * 
	 * @throws PQException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void reorderCharactersDialog(){
		
	}
	
	@SuppressWarnings("unused")
	private void deleteCharacterDialog() throws PQException, IOException{
		DeleteCharacterDialog delCharDialog = 
			new DeleteCharacterDialog(this, tabWidget.tabText(tabWidget.currentIndex()));
		delCharDialog.exec();
		
		if(delCharDialog.result() == QDialog.DialogCode.Accepted.value()){
			characterList.removeCharacter(tabWidget.currentIndex());
			tabWidget.removeTab(tabWidget.currentIndex());
		}
	}
	
    /**
	 * Invoked by the menu action "About Pause Quafé..."
	 * 
	 */
    @SuppressWarnings("unused")
	private void aboutPQ(){
    	AboutPQ about = new AboutPQ(this);
    	about.exec();
    }
    
    /**
	 * Invoked by the menu action "About Pause Quafé..."
	 * 
	 */
    @SuppressWarnings("unused")
	private void openBrowsers(){
    	if(browsers == null){
    		browsers = new BrowsersWindow();
    	}
    	browsers.setWindowIcon(this.windowIcon());
    	browsers.setStyleSheet(this.styleSheet());
    	browsers.show();
    	browsers.activateWindow();

    	if(requestCount == 0){
	    	List<CharacterSheet> list = new ArrayList<CharacterSheet>();
	    	for(int i=0 ; i<tabWidget.count() ; i++){
	    		list.add(((CharacterTab) tabWidget.widget(i)).getCharacter().getSheet());
	    	}
	    	browsers.setSheetList(list);
    	}
    }
    
    
    @SuppressWarnings("unused")
	private void refreshAllTabs(){
    	for(int i=0 ; i < tabWidget.count() ; i++){
    		((CharacterTab) tabWidget.widget(i)).requestInfo();
    	}
    }
    
    
    @SuppressWarnings("unused")
	private void openSettingsWindow(){
    	SettingsDialog settings = new SettingsDialog(this);
    	settings.show();
    }
    
    @SuppressWarnings("unused")
	private void openStylesheetEditor(){
    	StyleSheetEditor editor = new StyleSheetEditor(this);
    	editor.show();
    }
    
}
