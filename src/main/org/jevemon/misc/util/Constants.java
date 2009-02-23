package org.jevemon.misc.util;


public class Constants {
	
	public static final int[] SKILL_LEVEL_REQS = {0 , 250, 1414, 8000, 45255, 256000};
	public static final String API_VERSION = "2";
	
	public static final Integer ANALYTICAL_MIND = Integer.valueOf(3377);
	public static final Integer CLARITY = Integer.valueOf(12387);
	public static final Integer EDEITIC_MEMORY = Integer.valueOf(12385);
	public static final Integer EMPATHY = Integer.valueOf(3376);
	public static final Integer FOCUS = Integer.valueOf(12386);
	public static final Integer INSTANT_RECALL = Integer.valueOf(3378);
	public static final Integer IRON_WILL = Integer.valueOf(3375);
	public static final Integer LEARNING = Integer.valueOf(3374);
	public static final Integer LOGIC = Integer.valueOf(12376);
	public static final Integer PRESENCE = Integer.valueOf(12383);
	public static final Integer SPATIAL_AWARENESS = Integer.valueOf(3379);
	
	public static final String[] SKILL_GROUP_NAMES = {"Corporation Management", 
		"Drones", "Electronics","Engineering", "Gunnery", "Industry", "Leadership", 
		"Learning", "Mechanic", "Missile Launcher Operation","Navigation", "Science", 
		"Social", "Spaceship Command", "Trade"};
	
	public static final String CHAR_SHEET_PATH = "cache/CharacterSheets/";
	public static final String SKILL_TREE_FILE = "resources/SkillTree.xml";
	
	public static final int PORTRAIT_FILE_SIZE = 256;
	public static final int PORTRAIT_GUI_SIZE = 120;
	
	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;
}
