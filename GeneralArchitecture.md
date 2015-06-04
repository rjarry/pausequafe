**/!\ This page is work in progress /!\**

# Introduction #

We'll discuss the general architecture of **Pause Quafé** here. First from a distant point of view, then we'll focus on the important details.


---

# Global Structure #

## Core ##

### EVE API Connection Module ###

This module must handle an HTTP connections with CCP's API server. We began to use [JEVECore](http://jeveproject.fomp.be/wiki/index.php?title=JEve_Core) but, as it not supported anymore, we decided to modify it to adapt it to our needs and to EVE current API.

### Request Handlers ###

We use Threads to handle HTTP requests asynchronously. The request themselves will be sent by **XxxFactories** singletons.

### Parsers ###

For each [EVE API](http://wiki.eve-id.net/APIv2_Page_Index) method that we use, we'll need a specific parser to construct business objects from it.

## Business Objects ##

### Characters ###

  * **CharacterSheet**: Attributes, Basic info and Skills
  * **APIData**: Connection details to access the character through CCP's API
  * **SkillInTraining**: Current skill in training
  * **SkillQueue**: Queued skills after the skill in training
  * **SkillPlan**:

### Items ###

  * **Item**: Base class for all items
  * **Skill**: Inherits from **Item**
  * **Blueprint**: Inherits from **Item**
  * **ItemDetailed**: Inherits from **Item**

## Data Models for Views ##

### Characters & SkillPlan ###
// TODO
### Items ###
// TODO