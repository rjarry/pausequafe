package org.pausequafe.gui.view.character;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.gui.model.characters.CharaterSkillPlansProxyModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QWidget;

public class SkillPlanListView extends QWidget {

    Ui_SkillPlanList ui = new Ui_SkillPlanList();
    MonitoredCharacter monChar;
    CharaterSkillPlansProxyModel skillPlansModel;

     public SkillPlanListView(MonitoredCharacter monitoredCharacter) {
        this(null,monitoredCharacter);
    }

    public SkillPlanListView(QWidget parent, MonitoredCharacter monitoredCharacter) {
        super(parent);
        monChar = monitoredCharacter;
        skillPlansModel = new CharaterSkillPlansProxyModel(monChar);
        setupUi();
    }

	private void setupUi() {
		ui.setupUi(this);

		QIcon iconAdd = new QIcon(Constants.ADD_ICON);
		QIcon iconDel = new QIcon(Constants.MINUS_ICON);
		
		ui.addPlanButton.setIcon(iconAdd);
		ui.removePlanButton.setIcon(iconDel);
		ui.skillPlanList.setModel(skillPlansModel);
		
		ui.addPlanButton.clicked.connect(this, "createSkillPlan()");
		ui.removePlanButton.clicked.connect(this, "deleteSkillPlan()");
	}
	
	@SuppressWarnings("unused")
	private void createSkillPlan(){
		// TODO use a dialog box to call the following method
		// + add a parameters to method
		String spName = "Toto";
		skillPlansModel.createSkillPlan(spName);
	}
	
	@SuppressWarnings("unused")
	private void deleteSkillPlan(){
		// TODO use a dialog box to call the following method
		// + add a parameters to method
		skillPlansModel.deleteSkillPlan();
	}
}
