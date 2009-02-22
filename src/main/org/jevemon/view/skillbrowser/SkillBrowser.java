package org.jevemon.view.skillbrowser;

import java.util.LinkedList;
import java.util.List;

import org.jevemon.misc.util.Constants;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QWidget;

public class SkillBrowser extends QWidget {

    Ui_SkillBrowser ui = new Ui_SkillBrowser();

    QTreeWidget treeWidget;
    
    
    public static void main(String[] args) {
        QApplication.initialize(args);

        SkillBrowser testSkillBrowser = new SkillBrowser();
        testSkillBrowser.show();

        QApplication.exec();
    }

    public SkillBrowser() {
        this(null);
    }

    public SkillBrowser(QWidget parent) {
        super(parent);
        ui.setupUi(this);
        ((QTreeWidget) this.findChild(QTreeView.class, "treeWidget")).setHeaderItem(null);
        
        
        List<String> groups = new LinkedList<String>();
        
        for(String groupName : Constants.SKILL_GROUP_NAMES){
        	groups.add(groupName);
        }
        
       
    }
}
