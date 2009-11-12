package org.pausequafe.gui.view.character;

import org.pausequafe.data.business.MonitoredCharacter;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;



public class AddSkillPlanDialog extends QDialog {
    
	Ui_AddSkillPlanDialog ui = new Ui_AddSkillPlanDialog();
    
    private MonitoredCharacter character;
    private String skillPlanName;
    
    public AddSkillPlanDialog(MonitoredCharacter character) {
        this(character,null);
    }

    public AddSkillPlanDialog(MonitoredCharacter character, QWidget parent) {
        super(parent);
        this.character=character;
        setupUi();
    }

	private void setupUi(){
    	ui.setupUi(this);
    	this.setWindowTitle("Create New Skill Plan for "+character.getApi().getCharacterName());
      	
    	ui.cancelButton.clicked.connect(this, "reject()");
      	ui.createButton.clicked.connect(this, "accept()");
      	ui.skillPlanNameLineEdit.textChanged.connect(this,"nameChanged(String)");
      	
      	ui.createButton.setEnabled(false);
    }
    
    @SuppressWarnings("unused")
	private void nameChanged(String name){
    	skillPlanName=name;
    	boolean isValid = isValid(name);
    	ui.createButton.setEnabled(isValid );
    }
    
    private boolean isValid(String name) {
		boolean result = !("".equals(name));
		for(int i = 0;i<character.skillPlanCount() && result ;i++){
			result = result && !(character.getSkillPlanAt(i).getName().equals(name));	
		}
		return result;
	}

	public String getSkillPlanName() {
    	return skillPlanName;
    }
}
