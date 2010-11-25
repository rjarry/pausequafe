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

package org.pausequafe.data.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe a market group A market group is an element of the market that contains either other
 * market groups or eve items
 * 
 * @author Gobi
 */
public class MarketGroup {

    // ////////////////////
    // protected fields //
    // ////////////////////
    private int groupID;
    private String groupName;
    private boolean itemContainer;
    private List<Integer> children = new ArrayList<Integer>();

    // ///////////////
    // constructor //
    // ///////////////
    public MarketGroup() {
        super();
    }

    public MarketGroup(int groupID, String groupName, boolean containsItem) {
        super();
        this.groupID = groupID;
        this.groupName = groupName;
        this.itemContainer = containsItem;
    }

    // //////////////////
    // public methods //
    // //////////////////
    public Integer childAt(int position) {
        return children.get(position);
    }

    public int childCount() {
        return children.size();
    }

    public void addChild(Integer child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public void addChildren(List<Integer> newChildren) {
        for (Integer child : newChildren) {
            addChild(child);
        }
    }

    public void removeChild(Integer child) {
        children.remove(child);
    }

    public void removeChildAt(int position) {
        children.remove(position);
    }

    public void clearChildren() {
        children.clear();
    }

    @Override
    public String toString() {
        String result = "";
        result += groupID + " : " + groupName + " : ";
        for (Integer child : children) {
            result += child + ";";
        }
        return result;
    }

    // ///////////
    // getters //
    // ///////////
    public boolean isItemContainer() {
        return itemContainer;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public List<Integer> getChildren() {
        return children;
    }

    // ///////////
    // setters //
    // ///////////
    public void setItemContainer(boolean containsItem) {
        this.itemContainer = containsItem;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

}
