package org.jevemon.view.skilltree;

import org.jevemon.view.skillbrowser.SkillBrowser;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestSkillTree extends QTreeView {

	public static void main(String[] args) {
		QApplication.initialize(args);
		
		// Create the tree view.
		SkillBrowser view = new SkillBrowser();
		view.show();
		
		// Spin the event loop
		QApplication.exec();
	}

}
