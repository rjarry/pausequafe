package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.business.Skill;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QFont;

/**
 * This class implements a tree node. It's used by the TreeModel to access the
 * data from a MarketGroup instance
 * 
 * @author Gobi
 */
public class MarketGroupElement extends GroupElement {

	protected MarketGroup marketGroup;

	public MarketGroupElement(MarketGroup marketGroup) {
		super();
		this.marketGroup = marketGroup;
	}

	public int childCount() {
		return marketGroup.childCount();
	}

	public ItemTreeElement childAt(int position) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		ItemTreeElement child = null;
		Integer childId = marketGroup.childAt(position);

		if (!marketGroup.isItemContainer()) {
			MarketGroup group = MarketGroupDAO.getInstance().findMarketGroupById(childId);
			child = new MarketGroupElement(group);
		} else {
			Item item = ItemDAO.getInstance().findItemById(childId);
			if (Skill.class.isInstance(item)) {
				child = new SkillElement((Skill) item);
			} else {
				child = new ItemElement(item);
			}
		}

		return child;
	}

	public QFont getFont() {
		QFont font = new QFont();
		font.setBold(true);
		return font;
	}

	public String getName() {
		return marketGroup.getGroupName();
	}

	public String toString() {
		return marketGroup.getGroupName();
	}

}
