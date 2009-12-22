/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
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

package org.pausequafe.gui.view.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.pausequafe.core.threads.ApiRequest;
import org.pausequafe.core.threads.ServerStatusRequest;
import org.pausequafe.data.character.APIData;
import org.pausequafe.data.character.MonitoredCharacter;
import org.pausequafe.data.character.SkillPlan;
import org.pausequafe.data.misc.ServerStatus;
import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.gui.view.browsers.BrowsersWindow;
import org.pausequafe.gui.view.character.CharacterTab;
import org.pausequafe.gui.view.misc.AboutPQ;
import org.pausequafe.gui.view.misc.ErrorAPICorrupted;
import org.pausequafe.gui.view.misc.Errors;
import org.pausequafe.gui.view.misc.StyleSheetEditor;
import org.pausequafe.gui.view.skillplans.SkillPlanView;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMovie;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QSystemTrayIcon.ActivationReason;

public class MainWindow extends QMainWindow {

    private static MainWindow instance;

    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private Ui_MainWindow ui = new Ui_MainWindow();

    private QTabWidget tabWidget;
    private QLabel eveTimeLabel;
    private QTimer eveTimeTimer;

    private QLabel apiActivityIcon;
    private int requestCount;

    private QLabel serverStatusIndicator;
    private QTimer serverStatusTimer;
    private ServerStatus status;

    // used to close properly the system tray icon when the application quits
    public Signal0 aboutToQuit = new Signal0();
    // used to update the system tray icon's tooltip
    public Signal1<ServerStatus> dataUpdated = new Signal1<ServerStatus>();;

    // CONSTRUCTORS ///////////////////////////////////////////////////////////////////////////////
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        this(null);
    }

    private MainWindow(QWidget parent) {
        super(parent, Qt.WindowType.Window);
        ui.setupUi(this);
        setupUi();
        updateTime();
        requestServerStatus();
        try {
            MonitoredCharactersAndSkillPlansModel characters = MonitoredCharactersAndSkillPlansModel
                    .getInstance();
            for (int i = 0; i < characters.characterCount(); i++) {
                addTab(characters.getCharacterAt(i));
            }
        } catch (PQSQLDriverNotFoundException e) {
            Errors.popSQLDriverError(this, e);
        } catch (PQUserDatabaseFileCorrupted e) {
            Errors.popUserDBCorrupt(this, e);
        }

    }

    private void setupUi() {
        // window name
        this.setWindowTitle("Pause Quafé");
    
        // Init menu actions
        ui.actionQuit.triggered.connect(this, "quit()");
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
    
        tabWidget = ui.tabWidget;
        tabWidget.removeTab(0); // parce que le ui creator met un tab par defaut ce con.
    
        // Init status bar
        serverStatusIndicator = new QLabel();
        serverStatusTimer = new QTimer();
        serverStatusTimer.timeout.connect(this, "requestServerStatus()");
        serverStatusTimer.start(5 * Constants.MINUTE);
        status = new ServerStatus();
    
        eveTimeLabel = new QLabel();
        TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        eveTimeTimer = new QTimer(this);
        eveTimeTimer.timeout.connect(this, "updateTime()");
        eveTimeTimer.start(Constants.MINUTE);
    
        apiActivityIcon = new QLabel();
        apiActivityIcon.setPixmap(new QPixmap(Constants.IDLE_ICON));
    
        ui.statusBar.addPermanentWidget(serverStatusIndicator, 1);
        ui.statusBar.addPermanentWidget(eveTimeLabel, 1);
        ui.statusBar.addPermanentWidget(new QWidget(), 100);
        ui.statusBar.addPermanentWidget(apiActivityIcon, 1);
    
        requestCount = 1; // pour que le premier appel ne passe pas le request count à -1.
        updateServerStatus(status);
    
        this.resize(300, 700);
    }

    // PUBLIC METHODS /////////////////////////////////////////////////////////////////////////////
    public void showWindow(ActivationReason reason) {
        if (reason == ActivationReason.DoubleClick) {
            this.show();
        }
    }

    public void openPlanView(MonitoredCharacter character, SkillPlan plan) {
        SkillPlanView skillPlanView = new SkillPlanView();
        skillPlanView.setStyleSheet(this.styleSheet());
        skillPlanView.show();
        skillPlanView.activateWindow();
        
        skillPlanView.changeSelectedCharacter(character);
        skillPlanView.changeSelectedSkillPlan(plan);
    }

    // SLOTS //////////////////////////////////////////////////////////////////////////////////////
    /**
     * Updates the eve time label
     */
    private void updateTime() {
        Date gmt = Calendar.getInstance().getTime();
        eveTimeLabel.setText("Current EVE Time : " + TIME_FORMAT.format(gmt));
    }

    /**
     * Requests Tranquility server status from eve API
     */
    private void requestServerStatus() {
        ServerStatusRequest request = new ServerStatusRequest();
        QThread thread = new QThread(request);
        request.requestStarted.connect(this, "incrementRequestCount()");
        request.requestFinished.connect(this, "updateServerStatus(ServerStatus)");
        thread.start();
    }

    /**
     * Updates server status icon Invoked by the <code>serverStatusRequest.finished</code> signal
     * 
     * @param status
     *            the server's status
     */
    private void updateServerStatus(ServerStatus status) {
        int requestCode = ApiRequest.OK;
        if (status == null) {
            requestCode = ApiRequest.CONNECTION_ERROR;
            this.status = new ServerStatus();
        } else {
            this.status = status;
        }
        if (this.status.isOnLine()) {
            serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_ONLINE_ICON));
            serverStatusIndicator.setToolTip("Tranquility Server Online ("
                    + this.status.getPlayerCount() + " pilots)");
        } else {
            if (this.status.isUnknown()) {
                serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_UNKONWN_ICON));
                serverStatusIndicator.setToolTip("Server Status Unknown");
            } else {
                serverStatusIndicator.setPixmap(new QPixmap(Constants.SERVERSTATUS_OFFLINE_ICON));
                serverStatusIndicator.setToolTip("Tranquility Server Offline");
            }
        }
        decrementRequestCount(requestCode, null);
    }

    /**
     * Updates fetching indicator Invoked by the <code>CharacterTab.requestStarted</code> signal
     */
    @SuppressWarnings("unused")
    private synchronized void incrementRequestCount() {
        if (requestCount == 0) {
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
     * Updates fetching indicator Invoked by the <code>CharacterTab.requestFinished</code> signal
     * 
     */
    private synchronized void decrementRequestCount(Integer returnCode, MonitoredCharacter monChar) {
        requestCount--;
        if (requestCount == 0) {
            QPixmap pixmap = new QPixmap(Constants.IDLE_ICON);
            apiActivityIcon.setMovie(null);
            apiActivityIcon.setPixmap(pixmap);
            apiActivityIcon
                    .setToolTip("API activity : <b>idle</b> Right click to refresh all characters.");
            dataUpdated.emit(status);
        }
        if (returnCode == ApiRequest.AUTHENTICATION_ERROR) {
            popApiError(monChar);
        } else if (returnCode == ApiRequest.CONNECTION_ERROR) {
            Errors.popConnectionError(this);
        }
    }

    /**
     * Invoked by the menu action "Add Character..."
     */
    @SuppressWarnings("unused")
    private void addCharacterDialog() {
        AddCharacterDialog addCharDialog = new AddCharacterDialog(this);
        addCharDialog.exec();
        if (addCharDialog.result() == QDialog.DialogCode.Accepted.value()) {
            APIData data = addCharDialog.getChosenCharacter();
            try {
                MonitoredCharacter character = new MonitoredCharacter(data);
                MonitoredCharactersAndSkillPlansModel.getInstance().addCharacter(character);
                addTab(character);
            } catch (PQSQLDriverNotFoundException e) {
                Errors.popSQLDriverError(this, e);
            } catch (PQUserDatabaseFileCorrupted e) {
                Errors.popUserDBCorrupt(this, e);
            }
        }
    }

    private void addTab(MonitoredCharacter character) {
        CharacterTab tab = new CharacterTab(character);
        tab.requestStarted.connect(this, "incrementRequestCount()");
        tab.requestFinished.connect(this, "decrementRequestCount(Integer,MonitoredCharacter)");
        tabWidget.addTab(tab, character.getApi().getCharacterName());
    }

    /**
     * Invoked by the menu action "Reorder Characters..."
     */
    @SuppressWarnings("unused")
    private void reorderCharactersDialog() {

    }

    @SuppressWarnings("unused")
    private void deleteCharacterDialog() throws PQException, IOException {
        DeleteCharacterDialog delCharDialog = new DeleteCharacterDialog(this, tabWidget
                .tabText(tabWidget.currentIndex()));
        delCharDialog.exec();

        if (delCharDialog.result() == QDialog.DialogCode.Accepted.value()) {
            MonitoredCharactersAndSkillPlansModel.getInstance().removeCharacter(
                    tabWidget.currentIndex());
            tabWidget.removeTab(tabWidget.currentIndex());
        }
    }

    /**
     * Invoked by the menu action "About Pause Quafé..."
     * 
     */
    @SuppressWarnings("unused")
    private void aboutPQ() {
        AboutPQ about = new AboutPQ(this);
        about.exec();
    }

    /**
     * Invoked by the menu action "Open Browsers"
     */
    @SuppressWarnings("unused")
    private void openBrowsers() {
        BrowsersWindow browsers = new BrowsersWindow();
        browsers.setStyleSheet(this.styleSheet());
        browsers.show();
        browsers.activateWindow();
    }


    @SuppressWarnings("unused")
    private void openSettingsWindow() {
        SettingsDialog settings = new SettingsDialog(this);
        settings.show();
    }

    @SuppressWarnings("unused")
    private void openStylesheetEditor() {
        StyleSheetEditor editor = new StyleSheetEditor(this);
        editor.show();
    }

    @SuppressWarnings("unused")
    private void quit() {
        aboutToQuit.emit();
    }

    // ERROR DIALOGS //////////////////////////////////////////////////////////////////////////////

    private void popApiError(MonitoredCharacter monChar) {
        ErrorAPICorrupted error = new ErrorAPICorrupted(this, monChar);
        error.exec();
        if (error.result() == QDialog.DialogCode.Accepted.value()) {
            for (int i = 0; i < tabWidget.count(); i++) {
                CharacterTab tab = ((CharacterTab) tabWidget.widget(i));
                if (tab.getCharacter().getApi().getCharacterID() == monChar.getApi()
                        .getCharacterID()) {
                    tab.requestInfo();
                }
            }
        }
    }

}
