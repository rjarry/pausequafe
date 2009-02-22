package org.jevemon.misc.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;

public class Formater {
	
	public static String printDouble(double number) {
		StringBuffer buff = new StringBuffer("");

		DecimalFormat form = new DecimalFormat();
		form.setGroupingSize(3);
		form.setMaximumFractionDigits(2);
		form.setMinimumFractionDigits(2);

		form.format(number, buff, new FieldPosition(1));

		return buff.toString();
	}
	
	public static String printLong(long number) {
		StringBuffer buff = new StringBuffer("");

		DecimalFormat form = new DecimalFormat();
		form.setGroupingSize(3);
		form.setMaximumFractionDigits(0);
		form.setMinimumFractionDigits(0);

		form.format(number, buff, new FieldPosition(1));

		return buff.toString();
	}
}
