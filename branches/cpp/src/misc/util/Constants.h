/*****************************************************************************
 * This file is part of Pause Quafe.                                         *
 *                                                                           *
 * Pause Quafe - An Eve-Online(TM) character assistance application          *
 * Copyright (c) 2009  diabeteman & Kios Askoner                             *
 *                                                                           *
 * Pause Quafe is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafe is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#ifndef CONSTANTS_H
#define CONSTANTS_H

#include <QString>


///////////////////////////////////////////////
#define PQ_VERSION    "0.0.2"
///////////////////////////////////////////////
#define API_VERSION   "2"
///////////////////////////////////////////////

// misc constants
#define PORTRAIT_FILE_SIZE    256
#define SECOND                  1
#define MINUTE       (60 * SECOND)
#define HOUR         (60 * MINUTE)
#define DAY            (24 * HOUR)

static const unsigned SKILL_LEVEL_REQS[] = { 0, 250, 1414, 8000, 45255, 256000 };

// learning skills typeIDs
#define ANALYTICAL_MIND    3377
#define CLARITY           12387
#define EDEITIC_MEMORY    12385
#define EMPATHY            3376
#define FOCUS             12386
#define INSTANT_RECALL     3378
#define IRON_WILL          3375
#define LEARNING           3374
#define LOGIC             12376
#define PRESENCE          12383
#define SPATIAL_AWARENESS  3379

////////////////////
// Meta Groups ID //
////////////////////
#define TECH1_METAGROUP       0
#define NAMED_METAGROUP       1
#define TECH2_METAGROUP       2
#define STORYLINE_METAGROUP   3
#define FACTION_METAGROUP     4
#define OFFICER_METAGROUP     5
#define DEADSPACE_METAGROUP   6
#define TECH3_METAGROUP       7

#define WARP_SPEED_BASE                 3.0

/////////////////////
// item categories //
/////////////////////
#define BLUEPRINT_CATID                 9
#define SKILL_CATID                     16

/////////////////////
// marketgroup ids //
/////////////////////
#define BLUEPRINTS_MKTGRPID             2
#define SKILLS_MKTGRPID                 150
#define SHIPS_MKTGRPID                  4
#define ITEMS_MKTGRPID                  9

///////////////////
// attributes id //
///////////////////
#define METALEVEL_ATTID                 633
#define RANK_ATTID                      275
#define PREREQUISITE_ATTRIBUTE_CATEGORY 8
#define MAINCOLOR_ATTID                 124
// armor
#define ARMOR_HP_ATTID                  265
#define ARMOR_EM_RESIST_ATTID           267
#define ARMOR_EXPLO_RESIST_ATTID        268
#define ARMOR_KINE_RESIST_ATTID         269
#define ARMOR_THERM_RESIST_ATTID        270
// capa
#define CAPACITOR_CAPACITY_ATTID        482
#define CAPACITOR_RECHARGE_TIME_ATTID   55
// fitting
#define CPU_OUTPUT_ATTID                48
#define GUN_HARDPOINTS_ATTID            102
#define LAUNCHER_HARDPOINTS_ATTID       101
#define RIG_SLOTS_ATTID                 1154
#define CALIBRATION_PONTS_ATTID         1132
#define LOW_SLOTS_ATTID                 12
#define MED_SLOTS_ATTID                 13
#define HI_SLOTS_ATTID                  14
#define POWERGRID_OUTPUT_ATTID          11
// shield
#define SHIELD_HP_ATTID                 263
#define SHIELD_RECHARGE_TIME_ATTID      479
#define SHIELD_EM_RESIST_ATTID          271
#define SHIELD_EXPLO_RESIST_ATTID       272
#define SHIELD_KINE_RESIST_ATTID        273
#define SHIELD_THERM_RESIST_ATTID       274
// structure
#define STRUCT_HP_ATTID                 9
#define STRUCT_EM_RESIST_ATTID          113
#define STRUCT_EXPLO_RESIST_ATTID       111
#define STRUCT_KINE_RESIST_ATTID        109
#define STRUCT_THERM_RESIST_ATTID       110
#define RADIUS_ATTID                    162
#define MASS_ATTID                      4
#define VOLUME_ATTID                    161
#define CAPACITY_ATTID                  38
// drones
#define DRONEBAY_CAPACITY_ATTID         283
#define DRONE_BANDWIDTH                 1271
// propulsion
#define MAX_VELOCITY_ATTID              37
#define AGILITY_ATTID                   70
#define WARP_SPEED_MULTUPLIER_ATTID     600
// targeting
#define TARGETING_RANGE_ATTID           76
#define TARGET_COUNT_ATTID              192
#define SCAN_RESOLUTION_ATTID           564
#define SIGNATURE_RADIUS_ATTID          552
#define SENSOR_RADAR_ATTID              208
#define SENSOR_LADAR_ATTID              209
#define SENSOR_MAGNETO_ATTID            210
#define SENSOR_GRAVI_ATTID              211

//////////////
// unit IDs //
//////////////
#define DURATION_MILLISECS_UNITID       101
#define DAMAGE_RESISTANCE_UNITID        108
#define PERCENT_MULTIPLIER_UNITID       109
#define GROUPID_UNITID                  115
#define TYPEID_UNITID                   116
#define SIZECLASS_UNITID                117
#define ATTRIBUTEID_UNITID              119






///////////
// paths //
///////////
#define CHAR_SHEET_PATH             "cache/CharacterSheets/"
#define EVE_ICONS_PATH              ":/icons/EVE/"
#define PROXY_CONFIG_FILE_PATH      "settings/proxy.configuration"

#define BLANK_PORTRAIT              ":/icons/pausequafe/blank_portrait.jpg"
#define WINDOW_ICON                 ":/icons/pausequafe/pauseQuafe_windowIcon.png"

#define SPLASH_SCREEN               ":/icons/pausequafe/pausequafe_Splash.jpg"

#define ERROR_ICON_FILE             ":/icons/pausequafe/error.png"
#define QUESTION_ICON_FILE          ":/icons/pausequafe/question.png"
#define DOWNLOADING_ICON            ":/icons/pausequafe/QuafeCan_active.mng"
#define IDLE_ICON                   ":/icons/pausequafe/QuafeCan_idle.png"
#define SERVERSTATUS_ONLINE_ICON    ":/icons/pausequafe/serverStatus_online.png"
#define SERVERSTATUS_OFFLINE_ICON   ":/icons/pausequafe/serverStatus_offline.png"
#define SERVERSTATUS_UNKONWN_ICON   ":/icons/pausequafe/serverStatus_unknown.png"
#define WARNING_SMALL_ICON          ":/icons/pausequafe/warning_small.png"

// prerequisites
#define SKILL_OK                    ":/icons/pausequafe/skill_ok.png"
#define SKILL_KNOWN                 ":/icons/pausequafe/skill_known.png"
#define SKILL_NOT_KNOWN             ":/icons/pausequafe/skill_not_known.png"

// skill tree
static const QString SKILL_LEVEL_ICON[] = {
                                    ":/icons/pausequafe/skill_level_0.png",
                                    ":/icons/pausequafe/skill_level_1.png",
                                    ":/icons/pausequafe/skill_level_2.png",
                                    ":/icons/pausequafe/skill_level_3.png",
                                    ":/icons/pausequafe/skill_level_4.png",
                                    ":/icons/pausequafe/skill_level_5.png" };

#define SKILL_TRAINABLE             ":/icons/pausequafe/skill_trainable.png"
#define SKILL_NOT_TRAINABLE         ":/icons/pausequafe/skill_not_trainable.png"

// item tree
#define NO_ITEM_SELECTED_ICON       ":/icons/pausequafe/no_item_selected.png"
#define BLUEPRINTBG_ICON            ":/icons/pausequafe/blueprint_bg.png"

static const QString METAGROUP_ICONS_TAG[] = {
                                    ":/icons/pausequafe/tech1_no_tag.png",
                                    ":/icons/pausequafe/tech1_no_tag.png",
                                    ":/icons/pausequafe/tag_tech2.png",
                                    ":/icons/pausequafe/tag_storyline",
                                    ":/icons/pausequafe/tag_faction.png",
                                    ":/icons/pausequafe/tag_officer.png",
                                    ":/icons/pausequafe/tag_deadspace.png",
                                    ":/icons/pausequafe/tag_tech3.png" };
static const QString METAGROUP_ICONS_SMALL[] = {
                                    ":/icons/pausequafe/tag_tech1_small.png",
                                    ":/icons/pausequafe/tag_named_small.png",
                                    ":/icons/pausequafe/tag_tech2_small.png",
                                    ":/icons/pausequafe/tag_storyline_small.png",
                                    ":/icons/pausequafe/tag_faction_small.png",
                                    ":/icons/pausequafe/tag_officer_small.png",
                                    ":/icons/pausequafe/tag_deadspace_small.png",
                                    ":/icons/pausequafe/tag_tech3_small.png" };

////////////////////
// error messages //
////////////////////
#define USER_DB_CORRUPTED_ERROR     "User database corrupted.\nReset user settings?"
#define API_AUTHENTICATION_ERROR    "The API details you provided for ? are incorrect."
#define CONNECTION_ERROR            "Unable to connect to the API server. "\
                                        "Please Check your network connection and proxy settings."

#endif // CONSTANTS_H
