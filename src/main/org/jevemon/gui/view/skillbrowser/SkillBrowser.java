package org.jevemon.gui.view.skillbrowser;

import org.jevemon.data.business.CharacterSheet;
import org.jevemon.gui.model.items.skilltreemodel.TreeModel;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.gui.view.skillbrowser.Ui_SkillBrowser;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class SkillBrowser extends QWidget {

	private static final QSize SKILL_ICON_SIZE = new QSize(21,14);
	
	/////////////////////
	// private members //
	/////////////////////
    Ui_SkillBrowser ui = new Ui_SkillBrowser();
    
    QTreeView treeView;
    
    QLineEdit searchField;
    QComboBox filterComboBox;
    QFrame skillTree;

    //////////////////
    // constructors //
    //////////////////
    public SkillBrowser(){
        this(null);
    }

    public SkillBrowser(QWidget parent){
        super(parent);
        this.setupUi();
    }
    
    
    //////////////
    // UI setup //
    //////////////
    private void setupUi() {
    	ui.setupUi(this);

    	skillTree = ((QFrame) this.findChild(QFrame.class, "skillTree"));
    	QVBoxLayout treeLayout = new QVBoxLayout(skillTree);
    	treeLayout.setContentsMargins(0,0,0,0);
    	treeLayout.setSpacing(0);
    	    	
    	treeView = new QTreeView();
    	treeView.setHeaderHidden(true);
    	treeView.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
    	treeView.setIndentation(15);
    	treeView.setIconSize(SKILL_ICON_SIZE);
    	treeView.expanded.connect(this, "oneGroupAtATime(QModelIndex)");
    	treeLayout.addWidget(treeView);
    }
    
    ////////////////////
    // public methods //
    ////////////////////
    /**
     * Loads the skill tree with the SkillTreeModel. 
     * 
     * @param sheet
     * 			The character whose skills are to be displayed. 
     * 			The sheet may be <code>null</code>.
     */
    public void loadTree(CharacterSheet sheet) throws JEVEMonException{
    	TreeModel model = new TreeModel(treeView, sheet);
    	treeView.setModel(model);
    	treeView.collapsed.connect(model, "releaseChildren(QModelIndex)");
    }
    
    

	// TEST
    public static void main(String[] args) throws JEVEMonException{
    	QApplication.initialize(args);
    	SkillBrowser browser = new SkillBrowser();
    	browser.loadTree(null);
    	browser.show();
		QApplication.exec();
    }
    
    
    ///////////
    // slots //
    ///////////
    @SuppressWarnings("unused")
	private void oneGroupAtATime(QModelIndex index){
    	treeView.collapseAll();
    	treeView.expand(index);
    }
    
 
}
