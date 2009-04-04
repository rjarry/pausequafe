package org.pausequafe.gui.view.character;

import com.trolltech.qt.gui.QWidget;

public class SkillPlanList extends QWidget {

    Ui_SkillPlanList ui = new Ui_SkillPlanList();

     public SkillPlanList() {
        this(null);
    }

    public SkillPlanList(QWidget parent) {
        super(parent);
        ui.setupUi(this);
    }
}
