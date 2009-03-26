package org.pausequafe.misc.util;




public class Constants {
	///////////////////////////////////////////////
	private static final String PQ_VERSION = "0.0";
	///////////////////////////////////////////////
	
	public static final String API_VERSION = "2";
	
	// misc constants
	public static final int PORTRAIT_FILE_SIZE = 256;
	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;
	public static final int[] SKILL_LEVEL_REQS = {0 , 250, 1414, 8000, 45255, 256000};

	// learning skills typeIDs
	public static final int ANALYTICAL_MIND = 3377;
	public static final int CLARITY = 12387;
	public static final int EDEITIC_MEMORY = 12385;
	public static final int EMPATHY = 3376;
	public static final int FOCUS = 12386;
	public static final int INSTANT_RECALL = 3378;
	public static final int IRON_WILL = 3375;
	public static final int LEARNING = 3374;
	public static final int LOGIC = 12376;
	public static final int PRESENCE = 12383;
	public static final int SPATIAL_AWARENESS = 3379;

	///////////
	// paths //
	///////////
	public static final String CHAR_SHEET_PATH = "cache/CharacterSheets/";
	public static final String EVE_ICONS_PATH = "resources/icons/EVE/";
	
	///////////
	// icons //
	///////////
	
	public static final String BLANK_PORTRAIT = "resources/icons/JEVEMon/blank_portrait.jpg";
	public static final String WINDOW_ICON = "resources/icons/JEVEMon/pauseQuafe_windowIcon.png";
	
	public static final String SPLASH_SCREEN = "resources/icons/JEVEMon/PauseQuafe_Splash.jpg";
	
	public static final String ERROR_ICON_FILE = "resources/icons/JEVEMon/error.png";
	public static final String QUESTION_ICON_FILE = "resources/icons/JEVEMon/question.png";
	public static final String DOWNLOADING_ICON = "resources/icons/JEVEMon/rosace.gif";
	public static final String IDLE_ICON = "resources/icons/JEVEMon/rosace.png";
	public static final String SERVERSTATUS_ONLINE_ICON = "resources/icons/JEVEMon/serverStatus_online.png";
	public static final String SERVERSTATUS_OFFLINE_ICON = "resources/icons/JEVEMon/serverStatus_offline.png";
	public static final String SERVERSTATUS_UNKONWN_ICON = "resources/icons/JEVEMon/serverStatus_unknown.png";
	
	// prerequisites
	public static final String SKILL_OK = "resources/icons/JEVEMon/skill_ok.png";
	public static final String SKILL_KNOWN = "resources/icons/JEVEMon/skill_known.png";
	public static final String SKILL_NOT_KNOWN = "resources/icons/JEVEMon/skill_not_known.png";
	
	// skill tree
	public static final String[] SKILL_LEVEL_ICON = {"resources/icons/JEVEMon/skill_level_0.png",
													"resources/icons/JEVEMon/skill_level_1.png",
													"resources/icons/JEVEMon/skill_level_2.png",
													"resources/icons/JEVEMon/skill_level_3.png",
													"resources/icons/JEVEMon/skill_level_4.png",
													"resources/icons/JEVEMon/skill_level_5.png"};
	
	public static final String SKILL_TRAINABLE = "resources/icons/JEVEMon/skill_trainable.png";
	public static final String SKILL_NOT_TRAINABLE = "resources/icons/JEVEMon/skill_not_trainable.png";
	
	// item tree
	public static final String NO_ITEM_SELECTED_ICON = "resources/icons/JEVEMon/no_item_selected.png";
	public static final String[] METAGROUP_ICONS_TAG = {"resources/icons/JEVEMon/tech1_no_tag.png",
														"resources/icons/JEVEMon/tech1_no_tag.png",
														"resources/icons/JEVEMon/tag_tech2.png",
														"resources/icons/JEVEMon/tag_storyline",
														"resources/icons/JEVEMon/tag_faction.png",
														"resources/icons/JEVEMon/tag_officer.png",
														"resources/icons/JEVEMon/tag_deadspace.png",
														"resources/icons/JEVEMon/tag_tech3.png"};
	public static final String[] METAGROUP_ICONS_SMALL = {"resources/icons/JEVEMon/tag_tech1_small.png",
														"resources/icons/JEVEMon/tag_named_small.png",
														"resources/icons/JEVEMon/tag_tech2_small.png",
														"resources/icons/JEVEMon/tag_storyline_small.png",
														"resources/icons/JEVEMon/tag_faction_small.png",
														"resources/icons/JEVEMon/tag_officer_small.png",
														"resources/icons/JEVEMon/tag_deadspace_small.png",
														"resources/icons/JEVEMon/tag_tech3_small.png"};
	
	
	////////////////////
	// error messages //
	////////////////////
	public static final String DRIVER_NOT_FOUND_ERROR = "File \"/lib/sqlitejdbc-v054.jar\" not found.\nPlease check your installation.";
	public static final String EVE_DB_CORRUPTED_ERROR = "File \"/resources/eve-online.db\" corrupted.\nPlease check your installation.";
	public static final String USER_DB_CORRUPTED_ERROR = "File \"/resources/user.db\" corrupted.\nReset user settings?";
	
	///////////////////
	// misc messages //
	///////////////////
	public static final String ABOUT_TEXT = "<i>Pause Quafé®</i>  developped by <b>diabeteman</b> and <b>Kios Askoner</b><br>" +
											"version " + PQ_VERSION + " - March 2009<br>";
											
	public static final String SUGGESTIONS_TEXT = "please enjoy this software, any suggestion and/or bug repport is encouraged<br>" +
													"Donations in ISK are of course welcome \\o/";
	


}
