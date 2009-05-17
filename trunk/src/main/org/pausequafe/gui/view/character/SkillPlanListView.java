package org.pausequafe.gui.view.character;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QWidget;

public class SkillPlanListView extends QWidget {

    Ui_SkillPlanList ui = new Ui_SkillPlanList();

     public SkillPlanListView() {
        this(null);
    }

    public SkillPlanListView(QWidget parent) {
        super(parent);
        setupUi();
    }

	private void setupUi() {
		ui.setupUi(this);

		QIcon iconAdd = new QIcon(Constants.ADD_ICON);
		QIcon iconDel = new QIcon(Constants.MINUS_ICON);
		
		ui.addPlanButton.setIcon(iconAdd);
		ui.removePlanButton.setIcon(iconDel);
	}
}
