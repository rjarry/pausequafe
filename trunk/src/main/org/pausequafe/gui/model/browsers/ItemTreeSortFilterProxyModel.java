/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QSortFilterProxyModel;
import com.trolltech.qt.gui.QTreeModel;

public class ItemTreeSortFilterProxyModel extends QSortFilterProxyModel {

    // /////////////
    // constants //
    // /////////////
    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_META_LEVEL = 1;
    public static final int SORT_BY_REMAINING_TRAINING_TIME = 2;

    // //////////////////
    // private fields //
    // //////////////////
    private ItemTreeModel sourceModel;
    private int sortMode = SORT_BY_NAME;

    private boolean tech1Shown = true;
    private boolean tech2Shown = true;
    private boolean tech3Shown = true;
    private boolean namedShown = true;
    private boolean factionShown = true;
    private boolean storylineShown = true;
    private boolean deadspaceShown = true;
    private boolean officerShown = true;

    private boolean unknownShown = true;

    // //////////////////
    // constructors //
    // //////////////////
    public ItemTreeSortFilterProxyModel() {
        super();
        setFilterCaseSensitivity(Qt.CaseSensitivity.CaseInsensitive);
    }

    // //////////////////
    // public methods //
    // //////////////////
    public ItemTreeElement indexToValue(QModelIndex index) {
        return (ItemTreeElement) sourceModel.indexToValue(mapToSource(index));
    }

    public QTreeModel getSourceModel() {
        return sourceModel;
    }

    public void setSheet(CharacterSheet sheet) {
        sourceModel.setSheet(sheet);
    }

    public void setSourceModel(ItemTreeModel sourceModel) {
        this.sourceModel = sourceModel;
        sourceModel.dataChanged.connect(this.dataChanged);
        super.setSourceModel(sourceModel);
    }

    public void setSortMode(int sortMode) {
        this.sortMode = sortMode;
        setSortRole(sortMode); // sort role has to be change in order to trigger the sorting
        sort(0); // trigger sorting
    }

    public void setFilterString(String pattern) {
        super.setFilterFixedString(pattern);
        invalidateFilter();
    }

    public void setTech1Shown(boolean tech1Shown) {
        this.tech1Shown = tech1Shown;
        invalidateFilter();
    }

    public void setTech2Shown(boolean tech2Shown) {
        this.tech2Shown = tech2Shown;
        invalidateFilter();
    }

    public void setTech3Shown(boolean tech3Shown) {
        this.tech3Shown = tech3Shown;
        invalidateFilter();
    }

    public void setNamedShown(boolean namedShown) {
        this.namedShown = namedShown;
        invalidateFilter();
    }

    public void setFactionShown(boolean factionShown) {
        this.factionShown = factionShown;
        invalidateFilter();
    }

    public void setStorylineShown(boolean storylineShown) {
        this.storylineShown = storylineShown;
        invalidateFilter();
    }

    public void setDeadspaceShown(boolean deadspaceShown) {
        this.deadspaceShown = deadspaceShown;
        invalidateFilter();
    }

    public void setOfficerShown(boolean officerShown) {
        this.officerShown = officerShown;
        invalidateFilter();
    }

    public void setUnknownShown(boolean unknownShown) {
        this.unknownShown = unknownShown;
        invalidateFilter();
    }

    // ////////////////////////////////////////////////
    // overridden method from QSortFilterProxyModel //
    // ////////////////////////////////////////////////
    @Override
    protected boolean lessThan(QModelIndex left, QModelIndex right) {
        boolean result = true;
        ItemTreeElement leftElement = (ItemTreeElement) sourceModel.indexToValue(left);
        ItemTreeElement rightElement = (ItemTreeElement) sourceModel.indexToValue(right);

        switch (sortMode) {
        case SORT_BY_META_LEVEL:
            if (leftElement instanceof ItemElement && rightElement instanceof ItemElement) {
                // items are ordered by Meta level or by name if meta levels are
                // equal
                result = (leftElement.getItem().getMetaLevel() < rightElement.getItem().getMetaLevel())
                      || (leftElement.getItem().getMetaLevel() == rightElement.getItem().getMetaLevel() 
                      && leftElement.getName().compareTo(rightElement.getName()) < 0);
            } else {
                // other TreeElement like market groups are ordered by name
                result = leftElement.getName().compareTo(rightElement.getName()) < 0;
            }
            break;
        case SORT_BY_REMAINING_TRAINING_TIME:
            if (leftElement instanceof ItemElement && rightElement instanceof ItemElement) {
                result = leftElement.getItem().getRemainingTrainingTime(sourceModel.getSheet()) 
                      < rightElement.getItem().getRemainingTrainingTime(sourceModel.getSheet());
            } else {
                result = leftElement.getName().compareTo(rightElement.getName()) < 0;
            }
            break;
        case SORT_BY_NAME:
            // always order by name in this case
            result = leftElement.getName().compareTo(rightElement.getName()) < 0;
            break;
        default:
            // should not go here
            result = leftElement.getName().compareTo(rightElement.getName()) < 0;
            break;
        }
        return result;
    }

    @Override
    protected boolean filterAcceptsRow(int source_row, QModelIndex source_parent) {
        boolean result = false;
        ItemTreeElement element = sourceModel.child(sourceModel.indexToValue(source_parent),
                source_row);
        QModelIndex elementIndex = sourceModel.index(source_row, 0, source_parent);

        if (element instanceof ItemElement) {
            result = super.filterAcceptsRow(source_row, source_parent);
            switch (element.getItem().getMetaGroupID()) {
            case Constants.TECH1_METAGROUP:
                result = result && tech1Shown;
                break;
            case Constants.NAMED_METAGROUP:
                result = result && namedShown;
                break;
            case Constants.TECH2_METAGROUP:
                result = result && tech2Shown;
                break;
            case Constants.STORYLINE_METAGROUP:
                result = result && storylineShown;
                break;
            case Constants.FACTION_METAGROUP:
                result = result && factionShown;
                break;
            case Constants.OFFICER_METAGROUP:
                result = result && officerShown;
                break;
            case Constants.DEADSPACE_METAGROUP:
                result = result && deadspaceShown;
                break;
            case Constants.TECH3_METAGROUP:
                result = result && tech3Shown;
                break;
            default:
                break;
            }
        } else {
            // result = super.filterAcceptsRow(source_row, source_parent);
            for (int row = 0; row < sourceModel.childCount(element); row++) {
                result = result || filterAcceptsRow(row, elementIndex);
            }
        }

        // hide unknown skills if filter unknownShown is false
        if (element instanceof SkillElement && !unknownShown) {
            CharacterSheet sheet = sourceModel.getSheet();
            if (sheet != null) {
                boolean isSkillKnown = (sheet.getSkills()
                        .containsKey(element.getItem().getTypeID()));
                result = result && isSkillKnown;
            }
        }

        return result;
    }

}
