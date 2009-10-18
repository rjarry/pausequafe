package org.pausequafe.data.dao;

import java.io.IOException;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.SkillQueue;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

import be.fomp.jeve.core.config.Configuration;

public class TestSkillQueue {

	public static void main(String[] args){
		
		APIData data = new APIData(1151804801, "diabetegirl", 3373023, "E279369E075F4C92A8D79C6D1BF9C64A5383BB0FF17F4FD7B5725A2AACC11DA8");
		Configuration.setConfigurationFilePath(Constants.PROXY_CONFIG_FILE_PATH);
		SkillQueue queue = null;
		try {
			queue = SkillQueueFactory.getSkillQueue(data);
		} catch (PQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(queue);
	}
}
