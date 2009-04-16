package org.pausequafe.gui.model;

import org.pausequafe.gui.view.browsers.BrowserItemTab;

import com.trolltech.qt.gui.QApplication;

public class TestBrowserItem {
	public static void main(String[] args){
		QApplication.initialize(args);
		
		BrowserItemTab moduleBrowser = new BrowserItemTab(9);
		moduleBrowser.show();
		
		QApplication.exec();
	}
}
