package org.pausequafe.gui.view.browsers;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.gui.model.browsers.ItemElement;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QContextMenuEvent;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTreeModel;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class ItemTreeView extends QTreeView {

	public ItemTreeView() {
		super();
	}

	public ItemTreeView(QWidget parent) {
		super(parent);
	}
	
	@Override
	protected void contextMenuEvent(QContextMenuEvent evt) {
		// get left clicked stuff
		QModelIndex index = indexAt(evt.pos());
		Object value = null;
		if(model() instanceof QTreeModel){
			value = ((QTreeModel)model()).indexToValue(index);
		} else if (model() instanceof ItemTreeSortFilterProxyModel){
			value = ((ItemTreeSortFilterProxyModel)model()).indexToValue(index);
		}
		
		// create contextual menu
		if(value instanceof ItemElement){
			Item item = ((ItemElement)value).getItem();
			QMenu menu = new QMenu(this);
			QMenu itemNameMenu = new QMenu(tr("Train ")+item.getTypeName(),this);
			
			MonitoredCharactersAndSkillPlansModel charModel;
			try {
				charModel = MonitoredCharactersAndSkillPlansModel.getInstance();
			} catch (PQException e) {
				// should never go there
				e.printStackTrace();
				return;
			}
			
			for(int i=0;i<charModel.characterCount();++i){
				MonitoredCharacter character = charModel.getCharacterAt(i);
				QMenu charMenu = new QMenu(character.getApi().getCharacterName(),this);
				for(int j=0;j<character.skillPlanCount();++j){
					SkillPlan skillPlan = character.getSkillPlanAt(j);
					QAction spAction = new QAction(skillPlan.getName(),this);
					spAction.setData(skillPlan);
					charMenu.addAction(spAction);
				}
				QAction newAction = new QAction(tr("New SkillPlan ..."),this);
				newAction.setData(null);
				charMenu.addSeparator();
				charMenu.addAction(newAction);
				itemNameMenu.addMenu(charMenu);
				
			}
			menu.addMenu(itemNameMenu);

			//exec menu analyse user choice
			QAction selectedAction = menu.exec(evt.globalPos());
			if(selectedAction!=null){
				SkillPlan sp = (SkillPlan) selectedAction.data();
				if(sp==null){
					// create new Skill Plan
					
					//add skills to skill plan
				} else {
					// open plan view
					MainWindow.getInstance().openPlanView(charModel.findCharacter(sp.getCharacterID()), sp);
					
					// add skills to skill plan
				}
				System.out.println(sp);
			}
		}
	}

}
