package org.jevemon.model.skillintraining;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;

public class SkillInTrainingParser {

	public static SkillInTraining parse(Document doc, boolean isCached) throws JEVEMonException {
		
		Element root = doc.getRootElement();
		
		if (!root.getAttributeValue("version").equals(Constants.API_VERSION)){
			throw new JEVEMonException("wrong API version");
		}
		
		SkillInTraining inTraining = new SkillInTraining();
		
		// if the sheet was retrieved from a cached file
		inTraining.setCached(isCached);
		
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date cachedDate = null;
		try {
			cachedDate = dateFormat.parse(root.getChild("currentTime").getValue());
		} catch (ParseException e) {
			// if there's an error during parsing the cached date we take the current Eve time
			cachedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime();
		}
		cal.setTime(cachedDate);
		inTraining.setCurrentTime(cal.getTimeInMillis());
		
		try {
			cachedDate = dateFormat.parse(root.getChild("cachedUntil").getValue());
		} catch (ParseException e) {
			// if there's an error during parsing the cached date we take the current Eve time
			cachedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime();
		}
		cal.setTime(cachedDate);
		inTraining.setCachedUntil(cal.getTimeInMillis());
				
		Element result = root.getChild("result");
		
		try {
			cal.setTime(dateFormat.parse(result.getChild("currentTQTime").getValue()));
			inTraining.setCurrentTQTime(cal.getTimeInMillis());
			cal.setTime(dateFormat.parse(result.getChild("trainingEndTime").getValue()));
			inTraining.setTrainingEndTime(cal.getTimeInMillis());
			cal.setTime(dateFormat.parse(result.getChild("trainingStartTime").getValue()));
			inTraining.setTrainingStartTime(cal.getTimeInMillis());
		} catch (ParseException e) {
			throw new JEVEMonException("date parsing error");
		}
		
		inTraining.setTrainingTypeID(Integer.parseInt(result.getChild("trainingTypeID").getValue()));
		inTraining.setTrainingStartSP(Integer.parseInt(result.getChild("trainingStartSP").getValue()));
		inTraining.setTrainingDestinationSP(Integer.parseInt(result.getChild("trainingDestinationSP").getValue()));
		inTraining.setTrainingToLevel(Integer.parseInt(result.getChild("trainingToLevel").getValue()));
		inTraining.setSkillInTraining(Integer.parseInt(result.getChild("skillInTraining").getValue()));
		
		return inTraining;
	}

}
