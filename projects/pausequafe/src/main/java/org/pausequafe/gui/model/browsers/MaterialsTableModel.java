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

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.item.BPRequiredMaterial;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.gui.QAbstractTableModel;

public class MaterialsTableModel extends QAbstractTableModel {

    private static final int PROD_EFF_TYPEID = 3388;

    private final List<BPRequiredMaterial> materials = new ArrayList<BPRequiredMaterial>();
    private int me = 0;
    private CharacterSheet character = null;
    private boolean ignoreSkills = true;
    private int waste = 10;

    public MaterialsTableModel() {
        this(null);
    }

    public MaterialsTableModel(QObject parent) {
        super(parent);
    }

    @Override
    public int columnCount(QModelIndex parent) {
        return 3;
    }

    @Override
    public Object data(QModelIndex index, int role) {
        Object result = null;

        switch (role) {
        // text to display
        case ItemDataRole.DisplayRole:
            switch (index.column()) {
            case 0:
                result = printName(materials.get(index.row()));
                break;
            case 1:
                result = Formater.printLong(calculate(materials.get(index.row()).getQuantity()));
                break;
            case 2:
                result = Formater.printPercent(materials.get(index.row()).getDamagePerJob()) + "%";
            }
            break;

        case ItemDataRole.TextAlignmentRole:
            if (index.column() > 0) {
                result = Qt.AlignmentFlag.AlignRight;
            }
            break;

        default:
            result = null;
        }
        return result;
    }

    private long calculate(int base) {
        double value = base;
        if (!ignoreSkills) {
            if (character != null && character.getSkills().containsKey(PROD_EFF_TYPEID)) {
                int level = character.getSkill(PROD_EFF_TYPEID).getLevel();
                value = value * (1.25 - level * 0.05);
            } else {
                value = value * 1.25;
            }
        }
        if (me < 0) {
            value = value * (1.0 - ((me - 1) * (waste * 0.01)));
        } else {
            value = value * (1.0 + ((waste * 0.01) / (1.0 + me)));
        }

        return Math.round(value);
    }

    private String printName(BPRequiredMaterial bpRequiredMaterial) {
        String name;
        try {
            name = ItemDAO.getInstance().findItemById(bpRequiredMaterial.getTypeID()).getTypeName();
        } catch (Exception e) {
            name = String.valueOf(bpRequiredMaterial.getTypeID());
        }
        return name;
    }

    @Override
    public int rowCount(QModelIndex parent) {
        return materials.size();
    }

    @Override
    public Object headerData(int section, Orientation orientation, int role) {
        if (role == Qt.ItemDataRole.DisplayRole) {
            switch (section) {
            case 0:
                return "Item";
            case 1:
                return "Quantity";
            case 2:
                return "Damage Per Job";
            default:
                return null;
            }
        } else {
            return null;
        }
    }

    public void setMe(Integer me) {
        this.me = me;
        this.reset();
    }

    public void setCharacter(CharacterSheet character) {
        this.character = character;
        this.reset();
    }

    public void setIgnoreSkills(Boolean ignoreSkills) {
        this.ignoreSkills = ignoreSkills;
        this.reset();
    }

    public void setWaste(Integer waste) {
        this.waste = waste;
    }

    public void setMaterials(List<BPRequiredMaterial> list) {
        materials.clear();
        for (BPRequiredMaterial mat : list) {
            if (mat.getQuantity() > 0) {
                materials.add(mat);
            }
        }
        this.reset();
    }
}
