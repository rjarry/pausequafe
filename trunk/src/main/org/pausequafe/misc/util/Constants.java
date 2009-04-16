package org.pausequafe.misc.util;




public class Constants {
	///////////////////////////////////////////////
	public static final String PQ_VERSION = "0.0";
	///////////////////////////////////////////////
	public static final String API_VERSION = "2";
	///////////////////////////////////////////////
	
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

    ////////////////////
	// Meta Groups ID //
	////////////////////
	public static final int TECH1_METAGROUP = 0;
	public static final int NAMED_METAGROUP = 1;
	public static final int TECH2_METAGROUP = 2;
	public static final int STORYLINE_METAGROUP = 3;
	public static final int FACTION_METAGROUP = 4;
	public static final int OFFICER_METAGROUP = 5;
	public static final int DEADSPACE_METAGROUP = 6;
	public static final int TECH3_METAGROUP = 7;

	///////////
	// paths //
	///////////
	public static final String CHAR_SHEET_PATH = "cache/CharacterSheets/";
	public static final String EVE_ICONS_PATH = "resources/icons/EVE/";
	public static final String PROXY_CONFIG_FILE_PATH = "settings/proxy.configuration";
	
	///////////
	// icons //
	///////////
	
	public static final String BLANK_PORTRAIT = "resources/icons/PauseQuafe/blank_portrait.jpg";
	public static final String WINDOW_ICON = "resources/icons/PauseQuafe/pauseQuafe_windowIcon.png";
	
	public static final String SPLASH_SCREEN = "resources/icons/PauseQuafe/PauseQuafe_Splash.jpg";
	
	public static final String ERROR_ICON_FILE = "resources/icons/PauseQuafe/error.png";
	public static final String QUESTION_ICON_FILE = "resources/icons/PauseQuafe/question.png";
	public static final String DOWNLOADING_ICON = "resources/icons/PauseQuafe/QuafeCan_active.mng";
	public static final String IDLE_ICON = "resources/icons/PauseQuafe/QuafeCan_idle.png";
	public static final String SERVERSTATUS_ONLINE_ICON = "resources/icons/PauseQuafe/serverStatus_online.png";
	public static final String SERVERSTATUS_OFFLINE_ICON = "resources/icons/PauseQuafe/serverStatus_offline.png";
	public static final String SERVERSTATUS_UNKONWN_ICON = "resources/icons/PauseQuafe/serverStatus_unknown.png";
	
	// prerequisites
	public static final String SKILL_OK = "resources/icons/PauseQuafe/skill_ok.png";
	public static final String SKILL_KNOWN = "resources/icons/PauseQuafe/skill_known.png";
	public static final String SKILL_NOT_KNOWN = "resources/icons/PauseQuafe/skill_not_known.png";
	
	// skill tree
	public static final String[] SKILL_LEVEL_ICON = {"resources/icons/PauseQuafe/skill_level_0.png",
													"resources/icons/PauseQuafe/skill_level_1.png",
													"resources/icons/PauseQuafe/skill_level_2.png",
													"resources/icons/PauseQuafe/skill_level_3.png",
													"resources/icons/PauseQuafe/skill_level_4.png",
													"resources/icons/PauseQuafe/skill_level_5.png"};
	
	public static final String SKILL_TRAINABLE = "resources/icons/PauseQuafe/skill_trainable.png";
	public static final String SKILL_NOT_TRAINABLE = "resources/icons/PauseQuafe/skill_not_trainable.png";
	
	// item tree
	public static final String NO_ITEM_SELECTED_ICON = "resources/icons/PauseQuafe/no_item_selected.png";
	
	
	public static final String[] METAGROUP_ICONS_TAG = {"resources/icons/PauseQuafe/tech1_no_tag.png",
														"resources/icons/PauseQuafe/tech1_no_tag.png",
														"resources/icons/PauseQuafe/tag_tech2.png",
														"resources/icons/PauseQuafe/tag_storyline",
														"resources/icons/PauseQuafe/tag_faction.png",
														"resources/icons/PauseQuafe/tag_officer.png",
														"resources/icons/PauseQuafe/tag_deadspace.png",
														"resources/icons/PauseQuafe/tag_tech3.png"};
	public static final String[] METAGROUP_ICONS_SMALL = {"resources/icons/PauseQuafe/tag_tech1_small.png",
														"resources/icons/PauseQuafe/tag_named_small.png",
														"resources/icons/PauseQuafe/tag_tech2_small.png",
														"resources/icons/PauseQuafe/tag_storyline_small.png",
														"resources/icons/PauseQuafe/tag_faction_small.png",
														"resources/icons/PauseQuafe/tag_officer_small.png",
														"resources/icons/PauseQuafe/tag_deadspace_small.png",
														"resources/icons/PauseQuafe/tag_tech3_small.png"};
	
	
	////////////////////
	// error messages //
	////////////////////
	public static final String DRIVER_NOT_FOUND_ERROR = "File \"/lib/sqlitejdbc-v054.jar\" not found.\nPlease check your installation.";
	public static final String EVE_DB_CORRUPTED_ERROR = "File \"/resources/eve-online.db\" corrupted.\nPlease check your installation.";
	public static final String USER_DB_CORRUPTED_ERROR = "File \"/resources/user.db\" corrupted.\nReset user settings?";
	
	///////////////////
	// misc messages //
	///////////////////
	public static final String ABOUT_TEXT = "<style>a { color: #ff66e0; font-weight : bold; } a:visited { color: #ff66e0; font-weight : bold; } </style>" +
											"<b>Pause Quafé®</b><br>" +
											"<i>version " + PQ_VERSION + " - Copyright © 2009</i><br>" +
											"This program is free software. You can redistribute it and/or modify it<br>" +
											"under the terms of version 2 of the <u><a href=\"http://www.gnu.org/copyleft/gpl.html\">GNU General Public Licence</a></u>.<br><br>";
													
											
	public static final String SUGGESTIONS_TEXT = "Please enjoy this software. Any suggestion and/or bug repport is encouraged.<br>" +
													"Donations in ISK are of course welcome \\o/<br><br>" +
													"<i>Pause Quafé®</i> developped by <b>diabeteman</b> and <b>Kios Askoner</b>";
	


}
