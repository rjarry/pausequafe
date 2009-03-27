package org.pausequafe.gui.view.browsers;

import com.trolltech.qt.gui.*;

public class BrowsersWindow extends QWidget {

    Ui_BrowsersWindow ui = new Ui_BrowsersWindow();
    
    BrowserItemTab skillBrowser;
    BrowserItemTab shipBrowser;
    BrowserItemTab moduleBrowser;
    
    QTabWidget tabs;
    
    public QFrame frame;

    public static void main(String[] args) {
        QApplication.initialize(args);

        BrowsersWindow testBrowsersWindow = new BrowsersWindow();
        testBrowsersWindow.show();

        QApplication.exec();
    }

    public BrowsersWindow() {
        this(null);
    }

    public BrowsersWindow(QWidget parent) {
        super(parent);
        setupUi();
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	frame = (QFrame) this.findChild(QFrame.class, "frame");
    	tabs = new QTabWidget(this);
    	
    	QVBoxLayout layout = new QVBoxLayout();
    	layout.setContentsMargins(0, 0, 0, 0);
    	layout.setSpacing(0);
    	frame.setLayout(layout);
    	
    	layout.addWidget(tabs);
    	
    	skillBrowser = new BrowserItemTab(this, 150);
    	shipBrowser = new BrowserItemTab(this, 4);
    	moduleBrowser = new BrowserItemTab(this, 9);
    	
    	tabs.addTab(skillBrowser, "Skills");
    	tabs.addTab(shipBrowser, "Ships");
    	tabs.addTab(moduleBrowser, "Ship Equipement");
    }
}
