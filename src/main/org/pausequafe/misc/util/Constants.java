/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
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

package org.pausequafe.misc.util;

public class Constants {
    // /////////////////////////////////////////////
    public static final String PQ_VERSION = "0.0.2";
    // /////////////////////////////////////////////
    public static final String API_VERSION = "2";
    // /////////////////////////////////////////////

    // misc constants
    public static final int PORTRAIT_FILE_SIZE = 256;
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;
    public static final int[] SKILL_LEVEL_REQS = { 0, 250, 1414, 8000, 45255, 256000 };

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

    // //////////////////
    // Meta Groups ID //
    // //////////////////
    public static final int TECH1_METAGROUP = 0;
    public static final int NAMED_METAGROUP = 1;
    public static final int TECH2_METAGROUP = 2;
    public static final int STORYLINE_METAGROUP = 3;
    public static final int FACTION_METAGROUP = 4;
    public static final int OFFICER_METAGROUP = 5;
    public static final int DEADSPACE_METAGROUP = 6;
    public static final int TECH3_METAGROUP = 7;

    // /////////
    // paths //
    // /////////
    public static final String CHAR_SHEET_PATH = "cache/CharacterSheets/";
    public static final String EVE_ICONS_PATH = "resources/icons/EVE/";
    public static final String PROXY_CONFIG_FILE_PATH = "settings/proxy.configuration";

    // /////////
    // icons //
    // /////////

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
    public static final String WARNING_SMALL_ICON = "resources/icons/PauseQuafe/warning_small.png";

    public static final String ARMOR_ICON = "resources/icons/PauseQuafe/ShipAttributes/armor.png";
    public static final String CAPA_ICON = "resources/icons/PauseQuafe/ShipAttributes/capacitor.png";
    public static final String CAPA_RECHARGE_ICON = "resources/icons/PauseQuafe/ShipAttributes/capa_recharge.png";
    public static final String CARGO_ICON = "resources/icons/PauseQuafe/ShipAttributes/cargo.png";
    public static final String CPU_ICON = "resources/icons/PauseQuafe/ShipAttributes/CPU.png";
    public static final String DRONES_ICON = "resources/icons/PauseQuafe/ShipAttributes/drones.png";
    public static final String EM_ICON = "resources/icons/PauseQuafe/ShipAttributes/EM_resistance.png";
    public static final String EXPLO_ICON = "resources/icons/PauseQuafe/ShipAttributes/explosive_resistance.png";
    public static final String GUN_ICON = "resources/icons/PauseQuafe/ShipAttributes/gun_hardpoint.png";
    public static final String HI_SLOT_ICON = "resources/icons/PauseQuafe/ShipAttributes/hi_slots.png";
    public static final String INERTIA_ICON = "resources/icons/PauseQuafe/ShipAttributes/inertia.png";
    public static final String KINETIC_ICON = "resources/icons/PauseQuafe/ShipAttributes/kinetic_resistance.png";
    public static final String LOW_SLOT_ICON = "resources/icons/PauseQuafe/ShipAttributes/low_slots.png";
    public static final String MASS_ICON = "resources/icons/PauseQuafe/ShipAttributes/mass.png";
    public static final String MED_SLOT_ICON = "resources/icons/PauseQuafe/ShipAttributes/med_slots.png";
    public static final String MISSILE_ICON = "resources/icons/PauseQuafe/ShipAttributes/launcher_hardpoint.png";
    public static final String PWG_ICON = "resources/icons/PauseQuafe/ShipAttributes/powergrid.png";
    public static final String RADIUS_ICON = "resources/icons/PauseQuafe/ShipAttributes/signature_radius.png";
    public static final String RANGE_ICON = "resources/icons/PauseQuafe/ShipAttributes/range.png";
    public static final String RIG_SLOT_ICON = "resources/icons/PauseQuafe/ShipAttributes/rig_slot.png";
    public static final String SCAN_RES_ICON = "resources/icons/PauseQuafe/ShipAttributes/resolution.png";
    public static final String SENSOR_MAGNETO_ICON = "resources/icons/PauseQuafe/ShipAttributes/sensor_magneto.png";
    public static final String SENSOR_RADAR_ICON = "resources/icons/PauseQuafe/ShipAttributes/sensor_radar.png";
    public static final String SENSOR_LADAR_ICON = "resources/icons/PauseQuafe/ShipAttributes/sensor_ladar.png";
    public static final String SENSOR_GRAVI_ICON = "resources/icons/PauseQuafe/ShipAttributes/sensor_gravi.png";
    public static final String SHIELD_ICON = "resources/icons/PauseQuafe/ShipAttributes/shield.png";
    public static final String SHIELD_RECHARGE_ICON = "resources/icons/PauseQuafe/ShipAttributes/shield_recharge.png";
    public static final String STRUCTURE_ICON = "resources/icons/PauseQuafe/ShipAttributes/structure.png";
    public static final String TARGET_COUNT_ICON = "resources/icons/PauseQuafe/ShipAttributes/target_count.png";
    public static final String THERMIC_ICON = "resources/icons/PauseQuafe/ShipAttributes/thermal_resistance.png";
    public static final String VELOCITY_ICON = "resources/icons/PauseQuafe/ShipAttributes/velocity.png";
    public static final String VOLUME_ICON = "resources/icons/PauseQuafe/ShipAttributes/volume.png";
    public static final String WARP_ICON = "resources/icons/PauseQuafe/ShipAttributes/warp_velocity.png";

    public static final String PRICE_ICON = "resources/icons/PauseQuafe/bp/isk.png";
    public static final String PROD_ICON = "resources/icons/PauseQuafe/bp/production";
    public static final String PE_ICON = "resources/icons/PauseQuafe/bp/PE.png";
    public static final String ME_ICON = "resources/icons/PauseQuafe/bp/PE.png";
    public static final String COPY_ICON = "resources/icons/PauseQuafe/bp/copy.png";
    public static final String INVENTION_ICON = "resources/icons/PauseQuafe/bp/invention.png";
    public static final String MAXRUNS_ICON = "resources/icons/PauseQuafe/bp/maxruns.png";
    public static final String BATCH_ICON = "resources/icons/PauseQuafe/bp/batch.png";
    public static final String WASTE_ICON = "resources/icons/PauseQuafe/bp/waste.png";

    public static final String ADD_ICON = "resources/icons/PauseQuafe/add.png";
    public static final String MINUS_ICON = "resources/icons/PauseQuafe/minus.png";

    // prerequisites
    public static final String SKILL_OK = "resources/icons/PauseQuafe/skill_ok.png";
    public static final String SKILL_KNOWN = "resources/icons/PauseQuafe/skill_known.png";
    public static final String SKILL_NOT_KNOWN = "resources/icons/PauseQuafe/skill_not_known.png";

    // skill tree
    public static final String[] SKILL_LEVEL_ICON = {
            "resources/icons/PauseQuafe/skill_level_0.png",
            "resources/icons/PauseQuafe/skill_level_1.png",
            "resources/icons/PauseQuafe/skill_level_2.png",
            "resources/icons/PauseQuafe/skill_level_3.png",
            "resources/icons/PauseQuafe/skill_level_4.png",
            "resources/icons/PauseQuafe/skill_level_5.png" };

    public static final String SKILL_TRAINABLE = "resources/icons/PauseQuafe/skill_trainable.png";
    public static final String SKILL_NOT_TRAINABLE = "resources/icons/PauseQuafe/skill_not_trainable.png";

    // item tree
    public static final String NO_ITEM_SELECTED_ICON = "resources/icons/PauseQuafe/no_item_selected.png";
    public static final String BLUEPRINTBG_ICON = "resources/icons/PauseQuafe/blueprint_bg.png";

    public static final String[] METAGROUP_ICONS_TAG = {
            "resources/icons/PauseQuafe/tech1_no_tag.png",
            "resources/icons/PauseQuafe/tech1_no_tag.png",
            "resources/icons/PauseQuafe/tag_tech2.png", "resources/icons/PauseQuafe/tag_storyline",
            "resources/icons/PauseQuafe/tag_faction.png",
            "resources/icons/PauseQuafe/tag_officer.png",
            "resources/icons/PauseQuafe/tag_deadspace.png",
            "resources/icons/PauseQuafe/tag_tech3.png" };
    public static final String[] METAGROUP_ICONS_SMALL = {
            "resources/icons/PauseQuafe/tag_tech1_small.png",
            "resources/icons/PauseQuafe/tag_named_small.png",
            "resources/icons/PauseQuafe/tag_tech2_small.png",
            "resources/icons/PauseQuafe/tag_storyline_small.png",
            "resources/icons/PauseQuafe/tag_faction_small.png",
            "resources/icons/PauseQuafe/tag_officer_small.png",
            "resources/icons/PauseQuafe/tag_deadspace_small.png",
            "resources/icons/PauseQuafe/tag_tech3_small.png" };

    // //////////////////
    // error messages //
    // //////////////////
    public static final String DRIVER_NOT_FOUND_ERROR = "File \"/lib/sqlitejdbc-v054.jar\" not found.\n"
                                                        + "Please check your installation.";
    public static final String EVE_DB_CORRUPTED_ERROR = "File \"/resources/eve-online.db\" corrupted.\n"
                                                        + "Please check your installation.";
    public static final String EVE_DB_NOT_FOUND_ERROR = "File not found.";
    public static final String USER_DB_CORRUPTED_ERROR = "File \"/settings/user.db\" corrupted.\n"
                                                        + "Reset user settings?";
    public static final String API_AUTHENTICATION_ERROR = "The API details you provided for ? are incorrect.";
    public static final String CONNECTION_ERROR = "Unable to connect to the API server. "
                                        + "Please Check your network connection and proxy settings.";
    // /////////////////
    // misc messages //
    // /////////////////
    public static final String ABOUT_TEXT = "<style>a { color: #ff66e0; font-weight : bold; } "
            + "a:visited { color: #ff66e0; font-weight : bold; } </style>"
            + "<b>Pause Quafé®</b><br>"
            + "<i>version "
            + PQ_VERSION
            + " - Copyright © 2009</i><br>"
            + "This program is free software. You can redistribute it and/or modify it<br>"
            + "under the terms of version 2 of the "
            + "<u><a href=\"http://www.gnu.org/copyleft/gpl.html\">GNU General Public Licence</a></u>.<br><br>";

    public static final String SUGGESTIONS_TEXT = "Please enjoy this software. "
            + "Any suggestion and/or bug repport is encouraged.<br>"
            + "Donations in ISK are of course welcome \\o/<br><br>"
            + "<i>Pause Quafé®</i> developped by <b>diabeteman</b> and <b>Kios Askoner</b>";

}
