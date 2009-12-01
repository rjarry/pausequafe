package org.pausequafe.data.business;

import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.util.Formater;

public class BPRequiredMaterial {
	
	// /////////
	// FIELDS //
	// /////////
	private int typeID;
	private int quantity;
	private double damagePerJob;

	// //////////////
	// CONSTRUCTOR //
	// //////////////
	public BPRequiredMaterial(int typeID, int quantity, double damagePerJob) {
		this.typeID = typeID;
		this.quantity = quantity;
		this.damagePerJob = damagePerJob;
	}

	// ////////////
	// ACCESSORS //
	// ////////////
	public int getTypeID() {
		return typeID;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getDamagePerJob() {
		return damagePerJob;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDamagePerJob(double damagePerJob) {
		this.damagePerJob = damagePerJob;
	}

	@Override
	public String toString() {
		String name;
		try {
			name = ItemDAO.getInstance().findItemById(typeID).getTypeName();
		} catch (Exception e) {
			name = String.valueOf(typeID);
		}
		
		return name + " x" + quantity + " (" + Formater.printPercent(damagePerJob) + ")";
	}
	
	
}
