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

#ifndef SQLCONSTANTS_H
#define SQLCONSTANTS_H

#include "misc/util/Constants.h"


#define EVE_DATABASE           "jdbc:sqlite:resources/eve-online.db"
#define EVE_DATABASE_FILE      "resources/eve-online.db"
#define USER_DATABASE          "jdbc:sqlite:settings/user.db"
#define USER_DATABASE_FILE     "settings/user.db"


// Columns name
#define TYPEID_COL         "typeID"
#define BPTYPEID_COL       "blueprintTypeID"
#define TYPENAME_COL       "typeName"
#define GROUPID_COL        "groupID"
#define GROUPNAME_COL      "groupName"
#define DESCRIPTION_COL    "description"
#define MARKETGRPID_COL    "marketGroupID"
#define MARKETGRPNAME_COL  "marketGroupName"
#define HASTYPE_COL        "hasTypes"
#define CHILDID_COL        "childID"
#define BASEPRICE_COL      "basePrice"
#define ICON_COL           "icon"
#define METAGROUPID_COL    "metaGroupID"
#define MASS_COL           "mass"
#define RADIUS_COL         "radius"
#define VOLUME_COL         "volume"
#define CAPACITY_COL       "capacity"
#define ATTRIBUTE_NAME_COL "attributeName"
#define ATTR_CATEGORY_COL  "categoryName"
#define VAL_FLOAT_COL      "valueFloat"
#define VAL_INT_COL        "valueInt"
#define ATTR_VALUE_COL     "value"
#define ATTRIBUTEID_COL    "attributeID"
#define UNIT_COL           "unit"
#define UNITID_COL         "unitID"
#define CATEGORYNAME_COL   "categoryName"
#define CATEGORYID_COL     "categoryID"
#define PARENTGRPID_COL    "parentGroupID"
#define SKILLID_COL        "skillID"
#define SKILLLEVEL_COL     "skillLevel"
#define PORTIONSIZE_COL    "portionSize"
#define PRODUCTTYPEID_COL  "productTypeID"
#define TECHLEVEL_COL      "techLevel"
#define PRODTIME_COL       "productionTime"
#define METIME_COL         "researchMaterialTime"
#define PETIME_COL         "researchProductivityTime"
#define INVENTIONTIME_COL  "researchTechTime"
#define COPYTIME_COL       "researchCopyTime"
#define WASTE_COL          "wasteFactor"
#define ACTIVITYID_COL     "activityID"
#define REQTYPEID_COL      "requiredTypeID"
#define QUANTITY_COL       "quantity"
#define DAMAGEPERJOB_COL   "damagePerJob"
#define MAXRUNS_COL        "maxProductionLimit"
#define RECYCLE_COL        "recycle"
#define DISPLAY_NAME_COL   "displayName"



// Queries
#define QUERY_MARKETGRP_BY_ID (\
                    "SELECT "MARKETGRPID_COL\
                          ","MARKETGRPNAME_COL\
                          ","HASTYPE_COL\
                    " FROM invMarketGroups WHERE "MARKETGRPID_COL" in (?);")

#define QUERY_MARKETGRP_BY_PARENT (\
                    "SELECT "MARKETGRPID_COL\
                          ","MARKETGRPNAME_COL\
                          ","HASTYPE_COL\
                          ","PARENTGRPID_COL\
                    " FROM invMarketGroups WHERE "PARENTGRPID_COL" in (?);")

#define QUERY_MARKETGRP_BY_ID_WITHCHILDREN (\
                    "SELECT mk1."MARKETGRPID_COL" as "MARKETGRPID_COL\
                          ",mk1."MARKETGRPNAME_COL" as "MARKETGRPNAME_COL\
                          ",mk1."HASTYPE_COL" as "HASTYPE_COL\
                          ",mk2."MARKETGRPID_COL" as "CHILDID_COL\
                   " FROM   invMarketGroups mk1"\
                         ", invMarketGroups mk2"\
                   " WHERE mk1."MARKETGRPID_COL" in (?)"\
                   " AND mk1."MARKETGRPID_COL" = mk2."PARENTGRPID_COL\
               " UNION "\
                   " SELECT mk1."MARKETGRPID_COL" as "MARKETGRPID_COL\
                          ",mk1."MARKETGRPNAME_COL" as "MARKETGRPNAME_COL\
                          ",mk1."HASTYPE_COL" as "HASTYPE_COL\
                          ",t."TYPEID_COL" as " + CHILDID_COL\
                   " FROM invMarketGroups mk1,invTypes t "\
                   " WHERE mk1."MARKETGRPID_COL" in (?)"\
                   " AND mk1."MARKETGRPID_COL"=t."MARKETGRPID_COL";")

#define QUERY_TYPES_BY_ID (\
                    "SELECT t."TYPEID_COL\
                          ",t."METAGROUPID_COL\
                          ",t."TYPENAME_COL\
                          ",g."CATEGORYID_COL\
                          ",at."ATTRIBUTEID_COL\
                          ",at."ATTRIBUTE_NAME_COL\
                          ",IFNULL(a."VAL_INT_COL", a."VAL_FLOAT_COL") AS "ATTR_VALUE_COL\
                   " FROM invTypes t,"\
                         "dgmTypeAttributes a,"\
                         "dgmAttributeTypes at,"\
                         "invGroups g "\
                   " WHERE t."TYPEID_COL" in (?) "\
                   " AND t."TYPEID_COL" = a."TYPEID_COL\
                   " AND a."ATTRIBUTEID_COL" = at."ATTRIBUTEID_COL\
                   " AND t."GROUPID_COL" = g."GROUPID_COL" "\
                   " AND at."ATTRIBUTEID_COL" in ("METALEVEL_ATTID","RANK_ATTID");")

#define QUERY_PREREQUISITES_BY_ID (\
                    "SELECT  t."TYPEID_COL\
                           ",IFNULL(ta1."VAL_INT_COL",ta1."VAL_FLOAT_COL") AS skillID"\
                           ",IFNULL(ta2."VAL_INT_COL",ta2."VAL_FLOAT_COL") AS skillLevel"\
                    " FROM   invTypes t"\
                           ",dgmTypeAttributes ta1"\
                           ",dgmTypeAttributes ta2"\
                           ",dgmAttributeTypes a1"\
                           ",dgmAttributeTypes a2"\
                    " WHERE t."MARKETGRPID_COL" in (?)"\
                      " AND t."TYPEID_COL"=ta1."TYPEID_COL\
                      " AND t."TYPEID_COL"=ta2."TYPEID_COL\
                      " AND ta1."ATTRIBUTEID_COL"=a1."ATTRIBUTEID_COL\
                      " AND ta2."ATTRIBUTEID_COL"=a2."ATTRIBUTEID_COL\
                      " AND a1."CATEGORYID_COL"="PREREQUISITE_ATTRIBUTE_CATEGORY\
                      " AND a2."ATTRIBUTE_NAME_COL" LIKE '%Level' "\
                      " AND a2."ATTRIBUTE_NAME_COL" LIKE (a1."ATTRIBUTE_NAME_COL" || '%')"\
                      " AND a2."ATTRIBUTE_NAME_COL" != a1."ATTRIBUTE_NAME_COL\
                    " ORDER BY a1."ATTRIBUTE_NAME_COL";")

#define QUERY_TYPE_BY_PARENT (\
                    "SELECT "TYPEID_COL\
                            ",t."MARKETGRPID_COL\
                            ",t."METAGROUPID_COL\
                            ",t."TYPENAME_COL\
                            ",g."CATEGORYID_COL\
                            ",at."ATTRIBUTEID_COL\
                            ",at."ATTRIBUTE_NAME_COL\
                            ",IFNULL(a."VAL_INT_COL", a."VAL_FLOAT_COL") AS "ATTR_VALUE_COL\
                    " FROM    invTypes t"\
                            ",dgmTypeAttributes a"\
                            ",dgmAttributeTypes at"\
                            ",invGroups g"\
                    " WHERE t."MARKETGRPID_COL" in (?)"\
                      " AND t."TYPEID_COL" = a."TYPEID_COL\
                      " AND a."ATTRIBUTEID_COL" = at."ATTRIBUTEID_COL\
                      " AND t."GROUPID_COL"=g."GROUPID_COL\
                      " AND at."ATTRIBUTEID_COL" in ("METALEVEL_ATTID","RANK_ATTID");")

#define QUERY_PREREQUISITES_BY_PARENT (\
                    "SELECT "TYPEID_COL\
                           ",IFNULL(ta1."VAL_INT_COL", ta1."VAL_FLOAT_COL") AS skillID"\
                           ",IFNULL(ta2."VAL_INT_COL", ta2."VAL_FLOAT_COL") AS skillLevel"\
                    " FROM invTypes t"\
                         ",dgmTypeAttributes ta1"\
                         ",dgmTypeAttributes ta2"\
                         ",dgmAttributeTypes a1"\
                         ",dgmAttributeTypes a2"\
                    " WHERE t."MARKETGRPID_COL" in (?)"\
                      " AND t."TYPEID_COL"=ta1."TYPEID_COL\
                      " AND t."TYPEID_COL"=ta2."TYPEID_COL\
                      " AND ta1."ATTRIBUTEID_COL" = a1."ATTRIBUTEID_COL\
                      " AND ta2."ATTRIBUTEID_COL" = a2."ATTRIBUTEID_COL\
                      " AND a1."CATEGORYID_COL" = "PREREQUISITE_ATTRIBUTE_CATEGORY\
                      " AND a2."ATTRIBUTE_NAME_COL" LIKE '%Level' "\
                      " AND a2."ATTRIBUTE_NAME_COL" LIKE (a1."ATTRIBUTE_NAME_COL" || '%')"\
                      " AND a2."ATTRIBUTE_NAME_COL" != a1."ATTRIBUTE_NAME_COL\
                    " ORDER BY a1.attributeName")

#define QUERY_ITEM_DETAILS_BY_ID (\
                    "SELECT   t."TYPEID_COL\
                            ",t."ICON_COL\
                            ",t."METAGROUPID_COL\
                            ",t."TYPENAME_COL\
                            ",t."PORTIONSIZE_COL\
                            ",ac."CATEGORYNAME_COL\
                            ",a."ATTRIBUTEID_COL\
                            ",a."ATTRIBUTE_NAME_COL\
                            ",IFNULL(ta."VAL_INT_COL", ta."VAL_FLOAT_COL") AS "ATTR_VALUE_COL\
                            ",u."UNITID_COL\
                            ",u."DISPLAY_NAME_COL" AS "UNIT_COL\
                            ",t."RADIUS_COL\
                            ",t."DESCRIPTION_COL\
                            ",t."MASS_COL\
                            ",t."VOLUME_COL\
                            ",t."CAPACITY_COL\
                            ",t."BASEPRICE_COL\
                     " FROM   invTypes t"\
                            ",dgmTypeAttributes ta"\
                            ",dgmAttributeTypes a"\
                            ",dgmAttributeCategories ac"\
                            ",eveUnits u "\
                     " WHERE t."TYPEID_COL" in (?)"\
                       " AND t."TYPEID_COL" = ta."TYPEID_COL\
                       " AND ta."ATTRIBUTEID_COL" = a."ATTRIBUTEID_COL\
                       " AND a."CATEGORYID_COL" = ac."CATEGORYID_COL\
                       " AND a."UNITID_COL" = u."UNITID_COL\
                       " AND a."CATEGORYID_COL" != "PREREQUISITE_ATTRIBUTE_CATEGORY\
                     " ORDER BY ac."CATEGORYNAME_COL";")

#define QUERY_BLUEPRINTS_SIMPLE (\
                    "SELECT   t."TYPEID_COL\
                            ",t."ICON_COL\
                            ",t2."METAGROUPID_COL\
                            ",t."TYPENAME_COL\
                            ",t."MARKETGRPID_COL\
                            ",b."PRODUCTTYPEID_COL\
                            ",m."ACTIVITYID_COL\
                            ",m."REQTYPEID_COL\
                            ",m."QUANTITY_COL\
                    " FROM    invTypes t"\
                            ",invTypes t2"\
                            ",invTypes t3"\
                            ",invBlueprintTypes b"\
                            ",ramBlueprintReqs m"\
                            ",invGroups g"\
                    " WHERE t."MARKETGRPID_COL" IN (?) "\
                      " AND b."PRODUCTTYPEID_COL" = t2."TYPEID_COL\
                      " AND t3."TYPEID_COL" = m."REQTYPEID_COL\
                      " AND t3."GROUPID_COL" = g."GROUPID_COL\
                      " AND g."CATEGORYID_COL" IN ("SKILL_CATID")"\
                      " AND t."TYPEID_COL" = b."BPTYPEID_COL\
                      " AND t."typeID" = m."BPTYPEID_COL";")

#define QUERY_BLUEPRINT_DETAILED (\
                    "SELECT   t."TYPEID_COL\
                            ",t."ICON_COL\
                            ",t2."METAGROUPID_COL\
                            ",t."TYPENAME_COL\
                            ",t."PORTIONSIZE_COL\
                            ",t."BASEPRICE_COL\
                            ",t."MARKETGRPID_COL\
                            ",b."PRODUCTTYPEID_COL\
                            ",b."PETIME_COL\
                            ",b."METIME_COL\
                            ",b."TECHLEVEL_COL\
                            ",g."CATEGORYID_COL\
                            ",b."PRODTIME_COL\
                            ",b."COPYTIME_COL\
                            ",b."INVENTIONTIME_COL\
                            ",b."WASTE_COL\
                            ",b."MAXRUNS_COL\
                            ",m."ACTIVITYID_COL\
                            ",m."REQTYPEID_COL\
                            ",m."QUANTITY_COL\
                            ",m."DAMAGEPERJOB_COL\
                    " FROM    invTypes t"\
                            ",invTypes t2"\
                            ",invTypes t3"\
                            ",invBlueprintTypes b"\
                            ",ramBlueprintReqs m"\
                            ",invGroups g"\
                    " WHERE t."TYPEID_COL" IN (?)"\
                      " AND b."PRODUCTTYPEID_COL" = t2."TYPEID_COL\
                      " AND t3."TYPEID_COL" = m."REQTYPEID_COL\
                      " AND t3."GROUPID_COL" = g."GROUPID_COL\
                      " AND g."CATEGORYID_COL" NOT IN ("SKILL_CATID")"\
                      " AND t."TYPEID_COL" = b."BPTYPEID_COL\
                      " AND t."TYPEID_COL" = m."BPTYPEID_COL";")

#define QUERY_GROUP_NAME_BY_ID ("SELECT "GROUPNAME_COL" FROM invGroups WHERE "GROUPID_COL" IN (?);")
#define QUERY_TYPE_NAME_BY_ID ("SELECT "TYPENAME_COL" FROM invTypes WHERE "TYPEID_COL" IN (?);")


#endif // SQLCONSTANTS_H
