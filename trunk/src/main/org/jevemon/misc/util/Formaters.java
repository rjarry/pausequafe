package org.jevemon.misc.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;

public class Formaters {
	
	public static String printMoney(double number) {
		StringBuffer buff = new StringBuffer("");

		DecimalFormat form = new DecimalFormat();
		form.setGroupingSize(3);
		form.setMaximumFractionDigits(2);
		form.setMinimumFractionDigits(2);

		form.format(number, buff, new FieldPosition(1));

		return buff.toString();
	}
}
