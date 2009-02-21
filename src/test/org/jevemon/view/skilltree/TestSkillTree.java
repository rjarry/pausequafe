package org.jevemon.view.skilltree;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.qtrelated.skilltree.SkillTreeModel;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class TestSkillTree extends QTreeView {

	public TestSkillTree() {
		// TODO Auto-generated constructor stub
	}

	public TestSkillTree(QWidget parent) throws JEVEMonException {
		// Hide my header...
		header().hide();
		
		// Create our jambi file model
		SkillTreeModel model = new SkillTreeModel(this);
		setModel(model);
		show();
		
		// The QTreeModel does some caching of datastructures
		// behind the scenes. To avoid memory pooling of this
		// datastructure we can call the releaseChildren method to
		// release parts of this pool when its no longer needed. One
		// such case is when the view collapses a subtree and it is no
		// longer visible.
		collapsed.connect(model, "releaseChildren(QModelIndex)");
		setWindowTitle("Skill Tree");
		setWindowIcon(new QIcon("resources/EVEMon-all.ico"));
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		// Create the tree view.
		TestSkillTree view = new TestSkillTree();
		view.show();
		
		// Spin the event loop
		QApplication.exec();
	}

}
