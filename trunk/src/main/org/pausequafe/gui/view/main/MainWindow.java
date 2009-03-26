package org.pausequafe.gui.view.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.ServerStatus;
import org.pausequafe.data.dao.SessionDAO;
import org.pausequafe.gui.view.character.CharacterTab;
import org.pausequafe.gui.view.misc.AboutPQ;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;
import org.pausequafe.misc.util.ServerStatusRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMovie;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class MainWindow extends QMainWindow {

    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
    private QStatusBar statusBar;
    private QTabWidget tabWidget;
	
    private QAction actionAdd_Character;
    private QAction actionDelete_Current_Character;
    private QAction actionQuit;
    private QAction actionOpen_Browsers;
    private QAction actionAbout_PQ;

    private QLabel eveTimeLabel;
    private QTimer eveTimeTimer;
    
    private QLabel apiActivityIcon;
    private int requestCount = 0;

    private QLabel serverStatusIndicator;
    private QTimer serverStatusTimer;
	
    public Signal1<CharacterSheet> tabChanged = new Signal1<CharacterSheet>();
	
    private Ui_MainWindow ui = new Ui_MainWindow();

    //////////////////
    // constructors //
    //////////////////
    public MainWindow(){
        this(null);
    }

    public MainWindow(QWidget parent) {
        super(parent);
        ui.setupUi(this);
        setupUi();
        this.show();
        updateTime();
        requestServerStatus();

        
		try {
			List<APIData> list = SessionDAO.getInstance().getMonitoredCharacters();
			for(APIData data : list){
				addTab(data);
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
		actionQuit = (QAction) this.findChild(QAction.class, "actionQuit");
		actionQuit.triggered.connect(QApplication.instance(), "quit()");
		actionQuit.setShortcut("Ctrl+Q");
		
    	actionAdd_Character = (QAction) this.findChild(QAction.class, "actionAdd_Character");
    	actionAdd_Character.triggered.connect(this, "addCharacterDialog()");
    	actionAdd_Character.setShortcut("Ctrl+N");
    	
    	actionDelete_Current_Character = (QAction) this.findChild(QAction.class, "actionDelete_Current_Character");
    	actionDelete_Current_Character.triggered.connect(this, "deleteCharacterDialog()");
    	actionDelete_Current_Character.setShortcut("Ctrl+W");

    	actionOpen_Browsers = (QAction) this.findChild(QAction.class, "actionOpen_Browsers");
    	actionOpen_Browsers.triggered.connect(this, "openBrowsers()");
    	actionOpen_Browsers.setShortcut("Ctrl+B");
    	
    	actionAbout_PQ = (QAction) this.findChild(QAction.class, "actionAbout_PQ");
    	actionAbout_PQ.triggered.connect(this, "aboutPQ()");
    	
    	
    	// Init central widget
    	tabWidget = new QTabWidget();
    	tabWidget.currentChanged.connect(this, "currentTabChanged(Integer)");
    	
    	QWidget centralWidget = (QWidget) this.findChild(QWidget.class, "centralWidget");
    	QVBoxLayout centralLayout = new QVBoxLayout();
    	centralWidget.setLayout(centralLayout);
    	centralLayout.setContentsMargins(0,0,0,0);
    	centralLayout.setSpacing(0);
    	
    	centralLayout.addWidget(tabWidget);
    	
    	// Init status bar
    	statusBar = (QStatusBar) this.findChild(QStatusBar.class, "statusBar");

    	serverStatusIndicator = new QLabel();
    	serverStatusTimer = new QTimer();
    	serverStatusTimer.timeout.connect(this, "requestServerStatus()");
    	serverStatusTimer.start(5 * Constants.MINUTE);
    	updateServerStatus(new ServerStatus());
    	
    	eveTimeLabel = new QLabel();
    	TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    	eveTimeTimer = new QTimer(this);
    	eveTimeTimer.timeout.connect(this, "updateTime()");
    	eveTimeTimer.start(Constants.MINUTE);
    	
    	apiActivityIcon = new QLabel();
		apiActivityIcon.setPixmap(new QPixmap(Constants.IDLE_ICON));
		
		statusBar.addPermanentWidget(serverStatusIndicator, 1);
    	statusBar.addPermanentWidget(eveTimeLabel,1);
    	statusBar.addPermanentWidget(new QWidget(),100);
    	statusBar.addPermanentWidget(apiActivityIcon,1);

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
	 * Invoqued by the <code>ServerStatusRequest.finished</code> signal
	 * 
	 * @param status 
	 * 			the server's status
	 */
	private void updateServerStatus(ServerStatus status){
		if(status.isOnLine()){
			serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_ONLINE_ICON));
			serverStatusIndicator.setToolTip("Tranquility Server Online (" + status.getPlayerCount() + " pilots)");
		} else {
			serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_OFFLINE_ICON));
			serverStatusIndicator.setToolTip("Tranquility Server Offline");
		}
		if(status.isUnknown()){
			serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_UNKONWN_ICON));
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
			apiActivityIcon.setPixmap(null);
			apiActivityIcon.setMovie(movie);
			movie.start();
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
				addTab(data);
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
	
	
	private void addTab(APIData data) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		CharacterTab tab = new CharacterTab(data);
		tab.requestStarted.connect(this, "incrementRequestCount()");
		tab.requestFinished.connect(this, "decrementRequestCount()");
		tabWidget.addTab(tab, data.getCharacterName());
		
		SessionDAO.getInstance().addMonitoredCharacter(data);
	}
	
	
	@SuppressWarnings("unused")
	private void deleteCharacterDialog() throws PQException, IOException{
		DeleteCharacterDialog delCharDialog = 
			new DeleteCharacterDialog(this, tabWidget.tabText(tabWidget.currentIndex()));
		delCharDialog.exec();
		
		if(delCharDialog.result() == QDialog.DialogCode.Accepted.value()){
			removeTab();
		}
	}
	
	
	private void removeTab() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		int characterID = ((CharacterTab) tabWidget.currentWidget()).getSheet().getCharacterID();
			SessionDAO.getInstance().removeMonitoredCharacter(characterID);
		tabWidget.removeTab(tabWidget.currentIndex());
	}
    
    @SuppressWarnings("unused")
	private void currentTabChanged(Integer tabIndex){
    	tabChanged.emit(((CharacterTab) tabWidget.widget(tabIndex)).getSheet());
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
    	
    }
    
    
}
