package migrator;
import java.io.File;
import java.sql.*;
import java.util.LinkedList;

public class Migrator {

	public static void main(String[] args) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Class.forName("org.sqlite.JDBC");
		
		File dbFile = new File("resources/eve-online.db");
		if (dbFile.exists()){
			System.out.println("eve-online.db already exists, deleting...");
			dbFile.delete();
		}

		System.out.print("Establishing connections to the databases... ");
		Connection conMSSQL = DriverManager.getConnection(	"jdbc:sqlserver:" +
				"//localhost\\EVE;" +
				"instanceName=EVE;" +
				"databaseName=EVE;" +
				"user=SA;" +
				"password=connard;");
		Connection conSQLite = DriverManager.getConnection("jdbc:sqlite:resources/eve-online.db");

		System.out.println("Connected");

		genDgmAttributeCategories(conMSSQL, conSQLite);
		genDgmAttributeTypes(conMSSQL, conSQLite);
		genDgmEffects(conMSSQL, conSQLite);
		genDgmTypeAttributes(conMSSQL, conSQLite);
		genDgmTypeEffects(conMSSQL, conSQLite);
		genEveGraphics(conMSSQL, conSQLite);
		genEveUnits(conMSSQL, conSQLite);
		genInvBlueprintTypes(conMSSQL, conSQLite);
		genInvCategories(conMSSQL, conSQLite);
		genInvFlags(conMSSQL, conSQLite);
		genInvGroups(conMSSQL, conSQLite);
		genInvMarketGroups(conMSSQL, conSQLite);
		genInvMetaGroups(conMSSQL, conSQLite);
		genInvMetaTypes(conMSSQL, conSQLite);
		genInvTypeReactions(conMSSQL, conSQLite);
		genInvTypes(conMSSQL, conSQLite);
		genRamActivities(conMSSQL, conSQLite);
		genTypeActivityMaterials(conMSSQL, conSQLite);
		
		conMSSQL.close();

		System.out.println("SQLite database successfully generated from MSSQL database.");
		System.out.println("Customizing database...");
		
		completeTech3Tags(conSQLite);
		deleteTradeGoods(conSQLite);
		completeShipsMarketGroups(conSQLite);
		completeModulesMarketGroups(conSQLite);
		completeBlueprintsMarketGroups(conSQLite);
		correctBugs(conSQLite);
		
		System.out.println("Vacuuming SQLite file... ");
		Statement statSQLite = conSQLite.createStatement();
		statSQLite.executeUpdate("VACUUM");
		System.out.println("You can go now.");
		
		conSQLite.close();
	}

////////////////////////////////////////////////////
	public static void genDgmAttributeCategories(Connection conREAD, Connection conWRITE) 
																			throws SQLException{
		System.out.print("Generating dgmAttributeCategories... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE dgmAttributeCategories " +
									"(" +
										"categoryID tinyint PRIMARY KEY,"+
										"categoryName nvarchar(50),"+
										"categoryDescription nvarchar(200)"+
									")");


		ResultSet rs = statMSSQL.executeQuery("select * from dgmAttributeCategories");

		PreparedStatement prep = conWRITE.prepareStatement(
												"INSERT INTO dgmAttributeCategories(categoryID, " +
												"categoryName, categoryDescription)" +
												" VALUES (?, ?, ?)");
		while(rs.next()){
			prep.setShort(1, rs.getShort("categoryID"));
			prep.setString(2, rs.getString("categoryName"));
			prep.setString(3, rs.getString("categoryDescription"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genDgmAttributeTypes(Connection conREAD, Connection conWRITE) 
																		throws SQLException{
		System.out.print("Generating dgmAttributeTypes... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE dgmAttributeTypes " +
									"(" +
										"attributeID int PRIMARY KEY,"+
										"attributeName varchar(100),"+
										"description varchar(500),"+
										"graphicID smallint,"+
										"defaultValue double,"+
										"published bit,"+
										"displayName varchar(100),"+
										"unitID tinyint,"+
										"stackable bit,"+
										"highIsGood bit,"+
										"categoryID tinyint"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from dgmAttributeTypes");

		PreparedStatement prep = conWRITE.prepareStatement(
									"INSERT INTO dgmAttributeTypes(attributeID, attributeName, " +
									"description, graphicID, defaultValue, published, displayName, " +
									"unitID, stackable, highIsGood, categoryID)" +
									" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("attributeID"));
			prep.setString(2, rs.getString("attributeName"));
			prep.setString(3, rs.getString("description"));
			int tempInt = rs.getInt("graphicID");
			if(!rs.wasNull())
				prep.setInt(4, tempInt);
			else 
				prep.setNull(4, java.sql.Types.NULL);
			prep.setDouble(5, rs.getDouble("defaultValue"));
			prep.setByte(6, rs.getByte("published"));
			prep.setString(7, rs.getString("displayName"));
			short tempShort = rs.getShort("unitID");
			if(!rs.wasNull())
				prep.setShort(8, tempShort);
			else 
				prep.setNull(8, java.sql.Types.NULL);
			prep.setByte(9, rs.getByte("stackable"));
			prep.setByte(10, rs.getByte("highIsGood"));
			prep.setShort(11, rs.getShort("categoryID"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genDgmEffects(Connection conREAD, Connection conWRITE) 
																		throws SQLException{
		System.out.print("Generating dgmEffects... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE dgmEffects"+
									"("+
										"effectID smallint PRIMARY KEY,"+
										"effectName varchar(400),"+
										"effectCategory smallint,"+
										"preExpression int,"+
										"postExpression int,"+
										"description varchar(1000),"+
										"guid varchar(60),"+
										"isOffensive bit,"+
										"isAssistance bit,"+
										"durationAttributeID smallint,"+
										"trackingSpeedAttributeID smallint,"+
										"dischargeAttributeID smallint,"+
										"rangeAttributeID smallint,"+
										"falloffAttributeID smallint,"+
										"disallowAutoRepeat bit,"+
										"published bit,"+
										"displayName varchar(100),"+
										"isWarpSafe bit,"+
										"rangeChance bit,"+
										"electronicChance bit,"+
										"propulsionChance bit,"+
										"distribution tinyint,"+
										"sfxName varchar(20),"+
										"npcUsageChanceAttributeID smallint,"+
										"npcActivationChanceAttributeID smallint,"+
										"fittingUsageChanceAttributeID smallint"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from dgmEffects");

		PreparedStatement prep = conWRITE.prepareStatement(
				"INSERT INTO dgmEffects(effectID, effectName, " +
				"effectCategory, preExpression, postExpression, description, guid, " +
				"isOffensive, isAssistance, durationAttributeID, " +
				"trackingSpeedAttributeID, dischargeAttributeID, rangeAttributeID, " +
				"falloffAttributeID, disallowAutoRepeat, published, displayName, " +
				"isWarpSafe, rangeChance, electronicChance, propulsionChance, " +
				"distribution, sfxName, npcUsageChanceAttributeID, " +
				"npcActivationChanceAttributeID, fittingUsageChanceAttributeID)" +
				" VALUES (?, ? ,? ,? , ?, ?, ?, ?, ?, ?, ? ,? ,? ," +
				"? , ?, ?, ?, ?, ?, ?, ? ,? ,? ,? , ?, ?)");
		
		while(rs.next()){
			prep.setInt(1, rs.getInt("effectID"));
			prep.setString(2, rs.getString("effectName"));
			prep.setInt(3, rs.getInt("effectCategory"));
			prep.setInt(4, rs.getInt("preExpression"));
			prep.setInt(5, rs.getInt("postExpression"));
			prep.setString(6, rs.getString("description"));
			prep.setString(7, rs.getString("guid"));
			prep.setByte(8, rs.getByte("isOffensive"));
			prep.setByte(9, rs.getByte("isAssistance"));
			int tempInt = rs.getInt("durationAttributeID");
			if(!rs.wasNull())
				prep.setInt(10, tempInt);
			else 
				prep.setNull(10, java.sql.Types.NULL);
			tempInt = rs.getInt("trackingSpeedAttributeID");
			if(!rs.wasNull())
				prep.setInt(11, tempInt);
			else 
				prep.setNull(11, java.sql.Types.NULL);
			tempInt = rs.getInt("dischargeAttributeID");
			if(!rs.wasNull())
				prep.setInt(12, tempInt);
			else 
				prep.setNull(12, java.sql.Types.NULL);
			tempInt = rs.getInt("rangeAttributeID");
			if(!rs.wasNull())
				prep.setInt(13, tempInt);
			else 
				prep.setNull(13, java.sql.Types.NULL);
			tempInt = rs.getInt("falloffAttributeID");
			if(!rs.wasNull())
				prep.setInt(14, tempInt);
			else 
				prep.setNull(14, java.sql.Types.NULL);
			prep.setByte(15, rs.getByte("disallowAutoRepeat"));
			prep.setByte(16, rs.getByte("published"));
			prep.setString(17, rs.getString("displayName"));
			prep.setByte(18, rs.getByte("isWarpSafe"));
			prep.setByte(19, rs.getByte("rangeChance"));
			prep.setByte(20, rs.getByte("electronicChance"));
			prep.setByte(21, rs.getByte("propulsionChance"));
			short tempShort = rs.getShort("distribution");
			if(!rs.wasNull())
				prep.setShort(22, tempShort);
			else 
				prep.setNull(22, java.sql.Types.NULL);
			prep.setString(23, rs.getString("sfxName"));
			tempInt = rs.getInt("npcUsageChanceAttributeID");
			if(!rs.wasNull())
				prep.setInt(24, tempInt);
			else 
				prep.setNull(24, java.sql.Types.NULL);
			tempInt = rs.getInt("npcActivationChanceAttributeID");
			if(!rs.wasNull())
				prep.setInt(25, tempInt);
			else 
				prep.setNull(25, java.sql.Types.NULL);
			tempInt = rs.getInt("fittingUsageChanceAttributeID");
			if(!rs.wasNull())
				prep.setInt(26, tempInt);
			else 
				prep.setNull(26, java.sql.Types.NULL);
			
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genDgmTypeAttributes(Connection conREAD, Connection conWRITE) 
																	throws SQLException{
		System.out.print("Generating dgmTypeAttributes... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE dgmTypeAttributes " +
									"(" +
										"typeID int NOT NULL,"+
										"attributeID int NOT NULL,"+
										"valueInt int,"+
										"valueFloat double,"+
						
										"CONSTRAINT dgmTypeAttributes_PK PRIMARY KEY (typeID, attributeID)"+
									")");


		ResultSet rs = statMSSQL.executeQuery("SELECT a.* FROM dgmTypeAttributes a, invTypes t " +
												"WHERE t.published=1 AND a.typeID=t.typeID");

		PreparedStatement prep = conWRITE.prepareStatement("INSERT INTO dgmTypeAttributes(" +
													"typeID, attributeID, valueInt, valueFloat)" +
													" VALUES (?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("typeID"));
			prep.setInt(2, rs.getInt("attributeID"));
			int tempInt = rs.getInt("valueInt");
			if(!rs.wasNull())
				prep.setInt(3, tempInt);
			else 
				prep.setNull(3, java.sql.Types.NULL);
			double tempDouble = rs.getDouble("valueFloat");
			if(!rs.wasNull())
				prep.setDouble(4, tempDouble);
			else 
				prep.setNull(4, java.sql.Types.NULL);
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genDgmTypeEffects(Connection conREAD, Connection conWRITE) 
																	throws SQLException{
		System.out.print("Generating dgmTypeEffects... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE dgmTypeEffects " +
									"(" +
										"typeID int NOT NULL,"+
										"effectID int NOT NULL,"+
										"isDefault bit,"+
						
										"CONSTRAINT dgmTypeEffects_PK PRIMARY KEY (typeID, effectID)"+
									")");


		ResultSet rs = statMSSQL.executeQuery("SELECT e.* FROM dgmTypeEffects e, invTypes t " +
												"WHERE t.published=1 AND e.typeID=t.typeID");

		PreparedStatement prep = conWRITE.prepareStatement(
								"INSERT INTO dgmTypeEffects(typeID, effectID, isDefault)" +
								" VALUES (?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("typeID"));
			prep.setInt(2, rs.getInt("effectID"));
			prep.setByte(3, rs.getByte("isDefault"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genEveGraphics(Connection conREAD, Connection conWRITE) 
																		throws SQLException{
		System.out.print("Generating eveGraphics... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE eveGraphics"+
									"("+
										"graphicID smallint PRIMARY KEY,"+
										"description varchar(256),"+
										"icon varchar(100)"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from eveGraphics where published=1");

		PreparedStatement prep = conWRITE.prepareStatement(
									"INSERT INTO eveGraphics(graphicID, description, " +
									"icon) VALUES (?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("graphicID"));
			prep.setString(2, rs.getString("description"));
			prep.setString(3, rs.getString("icon"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}

////////////////////////////////////////////////////
	public static void genEveUnits(Connection conREAD, Connection conWRITE) 
																	throws SQLException{

		System.out.print("Generating eveUnits... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE eveUnits " +
									"(" +
										"unitID tinyint PRIMARY KEY,"+
										"unitName varchar(100),"+
										"displayName varchar(20),"+
										"description varchar(128)"+
									")");


		ResultSet rs = statMSSQL.executeQuery("select * from eveUnits");

		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO eveUnits(unitID, " +
											"unitName, displayName, description)" +
											" VALUES (?, ?, ?, ?)");
		while(rs.next()){
			prep.setShort(1, rs.getShort("unitID"));
			prep.setString(2, rs.getString("unitName"));
			prep.setString(3, rs.getString("displayName"));
			prep.setString(4, rs.getString("description"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}

////////////////////////////////////////////////////
	public static void genInvBlueprintTypes(Connection conREAD, Connection conWRITE) 
																				throws SQLException{
		System.out.print("Generating invBlueprintTypes... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invBlueprintTypes " +
									"(" +
										"blueprintTypeID smallint PRIMARY KEY,"+
										"parentBlueprintTypeID smallint,"+
										"productTypeID smallint,"+
										"productionTime int,"+
										"techLevel smallint,"+
										"researchProductivityTime int,"+
										"researchMaterialTime int,"+
										"researchCopyTime int,"+
										"researchTechTime int,"+
										"productivityModifier int,"+
										"materialModifier smallint,"+
										"wasteFactor smallint,"+
										"maxProductionLimit int"+
									")");


		ResultSet rs = statMSSQL.executeQuery(	"SELECT b.* FROM invTypes i, invBlueprintTypes b " +
												"WHERE i.published=1 and i.typeID=b.productTypeID");

		PreparedStatement prep = conWRITE.prepareStatement(
						"INSERT INTO invBlueprintTypes(blueprintTypeID, parentBlueprintTypeID, " +
						"productTypeID, productionTime, techLevel, researchProductivityTime, " +
						"researchMaterialTime, researchCopyTime, researchTechTime, productivityModifier, " +
						"materialModifier, wasteFactor, maxProductionLimit)" +
						" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("blueprintTypeID"));
			int temp = rs.getInt("parentBlueprintTypeID");
			if(!rs.wasNull())
				prep.setInt(2,temp);
			else 
				prep.setNull(2, java.sql.Types.NULL);
			prep.setInt(3, rs.getInt("productTypeID"));
			prep.setInt(4, rs.getInt("productionTime"));
			prep.setShort(5, rs.getShort("techLevel"));
			prep.setInt(6, rs.getInt("researchProductivityTime"));
			prep.setInt(7, rs.getInt("researchMaterialTime"));
			prep.setInt(8, rs.getInt("researchCopyTime"));
			prep.setInt(9, rs.getInt("researchTechTime"));
			prep.setInt(10, rs.getInt("productivityModifier"));
			prep.setInt(11, rs.getInt("materialModifier"));
			prep.setInt(12, rs.getInt("wasteFactor"));
			prep.setInt(13, rs.getInt("maxProductionLimit"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvCategories(Connection conREAD, Connection conWRITE) 
																	throws SQLException{
		System.out.print("Generating invCategories... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invCategories " +
									"(" +
										"categoryID tinyint PRIMARY KEY,"+
										"categoryName nvarchar(64),"+
										"description nvarchar(128),"+
										"graphicID smallint"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from invCategories where published=1");

		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invCategories(categoryID, " +
											"categoryName, description, graphicID)" +
											" VALUES (?, ?, ?, ?)");
		while(rs.next()){
			prep.setShort(1, rs.getShort("categoryID"));
			prep.setString(2, rs.getString("categoryName"));
			prep.setString(3, rs.getString("description"));
			int temp =rs.getInt("graphicID");
			if(!rs.wasNull())
				prep.setInt(4, temp);
			else 
				prep.setNull(4, java.sql.Types.NULL);
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvFlags(Connection conREAD, Connection conWRITE) 
	throws SQLException{
System.out.print("Generating invFlags... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invFlags " +
									"(" +
										"flagID tinyint PRIMARY KEY,"+
										"flagName varchar(100),"+
										"flagText varchar(100),"+
										"orderID smallint"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from invFlags");

		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invFlags(flagID, " +
											"flagName, flagText, orderID)" +
											" VALUES (?, ?, ?, ?)");
		while(rs.next()){
			prep.setShort(1, rs.getShort("flagID"));
			prep.setString(2, rs.getString("flagName"));
			prep.setString(3, rs.getString("flagText"));
			prep.setInt(4, rs.getInt("orderID"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvGroups(Connection conREAD, Connection conWRITE) 
	throws SQLException{
		System.out.print("Generating invGroups... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invGroups " +
									"(" +
										"groupID smallint PRIMARY KEY,"+
										"categoryID tinyint,"+
										"groupName nvarchar(100),"+
										"description nvarchar(3000),"+
										"graphicID smallint,"+
										"useBasePrice bit,"+
										"allowManufacture bit,"+
										"allowRecycler bit,"+
										"anchored bit,"+
										"anchorable bit,"+
										"fittableNonSingleton bit"+
									")");


		ResultSet rs = statMSSQL.executeQuery("select * from invGroups where published=1");

		PreparedStatement prep = conWRITE.prepareStatement(
						"INSERT INTO invGroups(groupID, categoryID, groupName, " +
						"description, graphicID, useBasePrice, allowManufacture, " +
						"allowRecycler, anchored, anchorable, fittableNonSingleton)" +
						" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("groupID"));
			short tempShort = rs.getShort("categoryID");
			if (!rs.wasNull())
				prep.setShort(2, tempShort);
			else 
				prep.setNull(2, java.sql.Types.NULL);
			prep.setString(3, rs.getString("groupName"));
			prep.setString(4, rs.getString("description"));
			int tempInt = rs.getInt("graphicID");
			if (!rs.wasNull())
				prep.setInt(5, tempInt);
			else 
				prep.setNull(5, java.sql.Types.NULL);
			prep.setByte(6, rs.getByte("useBasePrice"));
			prep.setByte(7, rs.getByte("allowManufacture"));
			prep.setByte(8, rs.getByte("allowRecycler"));
			prep.setByte(9, rs.getByte("anchored"));
			prep.setByte(10, rs.getByte("anchorable"));
			prep.setByte(11, rs.getByte("fittableNonSingleton"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvMarketGroups(Connection conREAD, Connection conWRITE) 
																			throws SQLException{
		System.out.print("Generating invMarketGroups... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invMarketGroups " +
									"(" +
										"marketGroupID smallint PRIMARY KEY,"+
										"parentGroupID smallint,"+
										"marketGroupName nvarchar(100),"+
										"description nvarchar(512),"+
										"graphicID smallint,"+
										"hasTypes bit"+
									")");

		ResultSet rs = statMSSQL.executeQuery("select * from invMarketGroups");

		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invMarketGroups(marketGroupID, parentGroupID, " + 
											"marketGroupName, description, graphicID, hasTypes)" +
											" VALUES (?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("marketGroupID"));
			int tempInt = rs.getInt("parentGroupID");
			if(!rs.wasNull())
				prep.setInt(2, tempInt);
			else 
				prep.setNull(2, java.sql.Types.NULL);
			prep.setString(3, rs.getString("marketGroupName"));
			prep.setString(4, rs.getString("description"));
			tempInt = rs.getInt("graphicID");
			if(!rs.wasNull())
				prep.setInt(5, tempInt);
			else 
				prep.setNull(5, java.sql.Types.NULL);
			prep.setByte(6, rs.getByte("hasTypes"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvMetaGroups(Connection conREAD, Connection conWRITE) 
																		throws SQLException{
		System.out.print("Generating invMetaGroups... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();
		
		statSQLite.executeUpdate(	"CREATE TABLE invMetaGroups " +
									"(" +
										"metaGroupID smallint PRIMARY KEY,"+
										"metaGroupName nvarchar(100),"+
										"description nvarchar(512)"+
									")");
		
		ResultSet rs = statMSSQL.executeQuery("select * from invMetaGroups");
		
		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invMetaGroups(metaGroupID, " +
											"metaGroupName, description)" +
											" VALUES (?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("metaGroupID"));
			prep.setString(2, rs.getString("metaGroupName"));
			prep.setString(3, rs.getString("description"));
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		rs.close();
		
		statSQLite.executeUpdate("INSERT INTO invMetaGroups(metaGroupID, metaGroupName, description) VALUES (0, 'Tech I', 'basic tech 1 items')");
		statSQLite.executeUpdate("UPDATE invMetaGroups SET metaGroupName='Named', description='named items' WHERE metaGroupID=1");
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvMetaTypes(Connection conREAD, Connection conWRITE) 
																	throws SQLException{
		System.out.print("Generating invMetaTypes... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();
		
		statSQLite.executeUpdate(	"CREATE TABLE invMetaTypes " +
									"(" +
										"typeID smallint PRIMARY KEY,"+
										"parentTypeID smallint,"+
										"metaGroupID smallint"+
									")");
		
		ResultSet rs = statMSSQL.executeQuery(	"SELECT m.* FROM invMetaTypes m, invTypes t " +
												"WHERE t.published=1 AND m.typeID=t.typeID");
		
		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invMetaTypes(typeID, " +
											"parentTypeID, metaGroupID)" +
											" VALUES (?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("typeID"));
			prep.setInt(2, rs.getInt("parentTypeID"));
			prep.setInt(3, rs.getInt("metaGroupID"));
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		
		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genInvTypeReactions (Connection conREAD, Connection conWRITE) 
																		throws SQLException{
		System.out.print("Generating invTypeReactions... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();
		
		statSQLite.executeUpdate(	"CREATE TABLE invTypeReactions " +
									"(" +
										"reactionTypeID smallint NOT NULL,"+
										"input bit NOT NULL," +
										"typeID smallint NOT NULL,"+
										"quantity tinyint,"+
						
										"CONSTRAINT invTypeReactions_PK PRIMARY KEY " +
															"(reactionTypeID, input, typeID)"+
									")");
		
		ResultSet rs = statMSSQL.executeQuery(	"SELECT r.* FROM invTypeReactions r, invTypes t " +
												"WHERE t.published=1 AND r.typeID=t.typeID");
		
		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO invTypeReactions(reactionTypeID, " +
											"input, typeID, quantity)" +
											" VALUES (?, ?, ?, ?)");
		while(rs.next()){
			prep.setByte(1, rs.getByte("reactionTypeID"));
			prep.setInt(2, rs.getInt("input"));
			prep.setInt(3, rs.getInt("typeID"));
			prep.setShort(4, rs.getShort("quantity"));
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		
		rs.close();
		System.out.println("Success");
	}

////////////////////////////////////////////////////
	public static void genInvTypes(Connection conREAD, Connection conWRITE) 
																throws SQLException{

		System.out.print("Generating invTypes... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE invTypes	" +
									"(" +
										"typeID smallint PRIMARY KEY," +
										"groupID smallint," +
										"typeName nvarchar(100), " +
										"description nvarchar(3000)," +
										"icon nvachar(32)," +
										"radius double," +
										"mass double," +
										"volume double," +
										"capacity double," +
										"basePrice double," +
										"marketGroupID smallint," +
										"chanceOfDuplicating double," +
										"metaGroupID tinyint" +
						
									")");


		ResultSet rs = statMSSQL.executeQuery(
				"select t.*, g.icon , m.metaGroupID "+
				"from invTypes t left outer join eveGraphics g on t.graphicID = g.graphicID, "+
				"     invTypes t2 left outer join invMetaTypes m on t2.typeID = m.typeID "+
				"where t.published=1 "+
				"and t.typeID=t2.typeID");
		
		PreparedStatement prep = conWRITE.prepareStatement(
										"INSERT INTO invTypes(typeID, groupID, " +
										"typeName, description, icon, radius, mass, " +
										"volume, capacity, basePrice, " +
										"marketGroupID, chanceOfDuplicating, metaGroupID)" +
										" VALUES (?, ? ,? ,? ,? , ?, ?, ?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("typeID"));
			prep.setInt(2, rs.getInt("groupID"));
			prep.setString(3, rs.getString("typeName"));
			prep.setString(4, rs.getString("description"));
			String tempString = rs.getString("icon");
			if (tempString == null || tempString.length()<2)
				prep.setString(5, "" + rs.getInt("typeID"));
			else 
				prep.setString(5, "icon" + tempString);
			prep.setDouble(6, rs.getDouble("radius"));
			prep.setDouble(7, rs.getDouble("mass"));
			prep.setDouble(8, rs.getDouble("volume"));
			prep.setDouble(9, rs.getDouble("capacity"));
			prep.setDouble(10, rs.getDouble("basePrice"));
			short tempShort = rs.getShort("marketGroupID");
			if (rs.wasNull())
				prep.setNull(11, java.sql.Types.NULL);
			else 
				prep.setShort(11, tempShort);
			prep.setDouble(12, rs.getDouble("chanceOfDuplicating"));
			tempShort = rs.getShort("metaGroupID");
			if (rs.wasNull())
				prep.setNull(13, java.sql.Types.NULL);
			else 
				prep.setShort(13, tempShort);
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}
	
////////////////////////////////////////////////////
	public static void genRamActivities(Connection conREAD, Connection conWRITE) 
	throws SQLException{
		System.out.print("Generating ramActivities... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();
		
		statSQLite.executeUpdate(	"CREATE TABLE ramActivities " +
									"(" +
										"activityID tinyint PRIMARY KEY,"+
										"activityName nvarchar(100),"+
										"description nvarchar(128)"+
									")");
		
		ResultSet rs = statMSSQL.executeQuery("SELECT * FROM ramActivities");
		
		PreparedStatement prep = conWRITE.prepareStatement(
									"INSERT INTO ramActivities(activityID, activityName, " + 
									"description) VALUES (?, ?, ?)");
		
		while(rs.next()){
			prep.setShort(1, rs.getShort("activityID"));
			prep.setString(2, rs.getString("activityName"));
			prep.setString(3, rs.getString("description"));
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		
		rs.close();
		System.out.println("Success");
	}

////////////////////////////////////////////////////
	public static void genTypeActivityMaterials(Connection conREAD, Connection conWRITE) 
																				throws SQLException{
		System.out.print("Generating typeActivityMaterials... ");
		Statement statMSSQL = conREAD.createStatement();
		Statement statSQLite = conWRITE.createStatement();

		statSQLite.executeUpdate(	"CREATE TABLE typeActivityMaterials " +
									"(" +
										"typeID smallint NOT NULL,"+
										"activityID tinyint,"+
										"requiredTypeID int,"+
										"quantity int,"+
										"damagePerJob double,"+
										"recycle bit,"+
						
										"CONSTRAINT typeActivityMaterials_PK PRIMARY KEY " +
										"(typeID, activityID, requiredTypeID)"+
									")");

		ResultSet rs = statMSSQL.executeQuery(	"SELECT m.* " +
												"FROM invTypes i, typeActivityMaterials m " +
												"WHERE i.published=1 " +
												"AND i.typeID=m.typeID");

		PreparedStatement prep = conWRITE.prepareStatement(
											"INSERT INTO typeActivityMaterials(typeID, activityID, " + 
											"requiredTypeID, quantity, damagePerJob, recycle)" +
											" VALUES (?, ?, ?, ?, ?, ?)");
		while(rs.next()){
			prep.setInt(1, rs.getInt("typeID"));
			prep.setShort(2, rs.getShort("activityID"));
			prep.setInt(3, rs.getInt("requiredTypeID"));
			prep.setInt(4, rs.getInt("quantity"));
			prep.setInt(5, rs.getInt("damagePerJob"));
			prep.setByte(6, rs.getByte("recycle"));
			prep.addBatch();
		}

		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);


		rs.close();
		System.out.println("Success");
	}

/////////////////////////////////////////////////////
	public static void deleteTradeGoods(Connection conWRITE) throws SQLException{
		
		System.out.print("Deleting Trade Goods... ");
		Statement statSQLite = conWRITE.createStatement();
		
		ResultSet rs = statSQLite.executeQuery(	
				"SELECT m2.marketGroupID, m2.hasTypes FROM invMarketGroups m1, invMarketGroups m2 " +
				"WHERE m1.marketGroupName='Trade Goods' " +
				"AND m1.marketGroupID = m2.parentGroupID ");
		
		LinkedList<Integer> groupsToBeDeleted = new LinkedList<Integer>();
				
		while(rs.next()){
			groupsToBeDeleted.add(rs.getInt("marketGroupID"));
		}
		
		rs = statSQLite.executeQuery(	
				"SELECT marketGroupID FROM invMarketGroups WHERE parentGroupID IN " +
				"(SELECT m2.marketGroupID FROM invMarketGroups m1, invMarketGroups m2 " +
				"WHERE m1.marketGroupName='Trade Goods' " +
				"AND m1.marketGroupID = m2.parentGroupID " +
		"AND m2.hasTypes = 0)");
				
		while(rs.next()){
			groupsToBeDeleted.add(rs.getInt("marketGroupID"));
		}
		
		
		PreparedStatement prep = conWRITE.prepareStatement("DELETE FROM invTypes WHERE marketGroupID = ? ");
		for(int groupID : groupsToBeDeleted){
			prep.setInt(1, groupID);
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		prep = conWRITE.prepareStatement("DELETE FROM invMarketGroups WHERE marketGroupID = ? ");
		for(int groupID : groupsToBeDeleted){
			prep.setInt(1, groupID);
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		statSQLite.executeUpdate("DELETE FROM invMarketGroups WHERE marketGroupName = 'Trade Goods'");
		statSQLite.executeUpdate("delete from invTypes where marketGroupID is null and groupID in (select groupID from invGroups where categoryID=17)");
		
		rs.close();
		System.out.println("Done");
		
	}
	
/////////////////////////////////////////////////////
	public static void completeShipsMarketGroups(Connection conWRITE) throws SQLException{
		System.out.print("Completing ships market groups... ");
		Statement statSQLite = conWRITE.createStatement();
				
		ResultSet rs = statSQLite.executeQuery(	
											"SELECT DISTINCT mk1.parentGroupID " +
											"FROM invTypes t , invGroups g , invCategories c , "+
											     "invMetaGroups mg , invMetaTypes mt , "+
											     "invTypes t2 , invMarketGroups mk1 "+
											"WHERE c.categoryName = 'Ship' "+
											  "AND c.categoryID=g.categoryID "+
											  "AND g.groupID = t.groupID "+
											  "AND mg.metaGroupName = 'Faction' "+
											  "AND mg.metaGroupID=mt.metaGroupID "+
											  "AND mt.typeID = t.typeID "+
											  "AND mt.parentTypeId = t2.typeID "+
											  "AND t2.marketGroupID = mk1.marketGroupID ");
		
		LinkedList<Integer> parentGroupIDs = new LinkedList<Integer>();
		while(rs.next()){
			parentGroupIDs.add(rs.getInt(1));
		}
		
		PreparedStatement prep = conWRITE.prepareStatement(
				"INSERT INTO invMarketGroups (marketGroupID,parentGroupID, " +
									"marketGroupName,description,hasTypes) " +
				"VALUES (" + 
					"(SELECT max(marketGroupID) FROM invMarketGroups) + 1, " +
					"?, 'Faction','Faction ship designs',1 " +
						")");
		
		for(int parentGroupID : parentGroupIDs){
			prep.setInt(1, parentGroupID);
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		rs = statSQLite.executeQuery(	
				"SELECT t.typeID , mk2.marketGroupID "+
				"FROM invTypes t , invGroups g , invCategories c , "+
				     "invMetaGroups mg , invMetaTypes mt , "+
				     "invTypes t2 , invMarketGroups mk1 ,invMarketGroups mk2 "+
				"WHERE c.categoryName = 'Ship' "+
				  "AND c.categoryID=g.categoryID "+
				  "AND g.groupID = t.groupID "+
				  "AND mg.metaGroupName = 'Faction' "+
				  "AND mg.metaGroupID=mt.metaGroupID "+
				  "AND mt.typeID = t.typeID "+
				  "AND mt.parentTypeId = t2.typeID "+
				  "AND t2.marketGroupID = mk1.marketGroupID "+
				  "AND mk1.parentGroupID = mk2.parentGroupID "+
				  "AND mk2.marketGroupName = 'Faction' ");
		
		
		prep = conWRITE.prepareStatement(	"UPDATE invTypes " +
											"SET marketGroupID = ? " +
											"WHERE typeID = ? ");
		
		
		while(rs.next()){
			prep.setInt(1, rs.getInt("marketGroupID"));
			prep.setInt(2, rs.getInt("typeID"));
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		
		
		
		rs = statSQLite.executeQuery( 	"SELECT t.typeID, g.groupName "+
										"FROM invTypes t, invGroups g "+
										"WHERE g.categoryID=6 "+
										"AND g.groupID=t.groupID "+
										"AND t.typeID not in (SELECT typeID FROM invMetaTypes) "+
										"AND t.marketGroupID IS NULL ");
		
		LinkedList<Integer> typeIDs = new LinkedList<Integer>();
		LinkedList<String> groupNames = new LinkedList<String>();
		
		while(rs.next()){
			typeIDs.add(rs.getInt("typeID"));
			groupNames.add(rs.getString("groupName") + "s");
		}
		
		prep = conWRITE.prepareStatement("UPDATE invTypes " +
										"SET marketGroupID = " +
										"("+
											"SELECT g1.marketGroupID "+
											"FROM invMarketGroups g1, invMarketGroups g2 "+
											"WHERE g1.marketGroupName='Faction' "+
											"AND g1.marketGroupID=g2.marketGroupID "+
											"AND g2.marketGroupID in "+
											"(" +
												"SELECT marketGroupID "+
												"FROM invMarketGroups "+
												"WHERE parentGroupID in "+
												"(" +
													"SELECT g1.marketGroupID "+
													"FROM invMarketGroups g1 , invMarketGroups g2 "+
													"WHERE g1.marketGroupName = ? "+
													"AND g2.parentGroupID=4 "+
													"AND g1.marketGroupID=g2.marketGroupID" +
												")" +
											")" +
										"), metaGroupID=4 " +
										"WHERE typeID = ?");
		
		PreparedStatement prep2 = conWRITE.prepareStatement("INSERT INTO invMetaTypes " +
															"(typeID, parentTypeID, metaGroupID) " +
															"VALUES(?,null,4)");

		for(int i=0 ; i<typeIDs.size() ; i++){
			prep.setString(1, groupNames.get(i));
			prep.setInt(2, typeIDs.get(i));
			prep2.setInt(1, typeIDs.get(i));
			prep.addBatch();
			prep2.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		prep2.executeBatch();
		conWRITE.setAutoCommit(true);
		
		statSQLite.executeUpdate(	"INSERT INTO invMarketGroups" +
									"(marketGroupID, parentGroupID, marketGroupName," +
									"description, graphicID, hasTypes)" +
									"VALUES((SELECT max(marketGroupID) FROM invMarketGroups) + 1," +
									"391, 'Faction', 'Faction ship designs', null , 1)  ");
		
		statSQLite.executeUpdate(	"UPDATE invTypes "+
									"SET marketGroupID = (SELECT max(marketGroupID) FROM invMarketGroups), " +
									"	 metaGroupID = 4 "+
									"WHERE typeID IN "+
									"(SELECT typeID FROM invTypes "+
									"WHERE groupID=31 AND marketGroupID IS NULL) ");
		
		
		
		
		rs.close();
		System.out.println("Done");
	}
	
/////////////////////////////////////////////////////
	public static void completeModulesMarketGroups(Connection conWRITE) throws SQLException{
		System.out.print("Completing modules market groups... ");
		Statement statSQLite = conWRITE.createStatement();
				
		ResultSet rs = statSQLite.executeQuery(	"SELECT t1.typeID , t2.marketGroupID " +
												"FROM invTypes t1, invTypes t2, invMetaTypes m " +
												"WHERE t1.marketGroupID IS NULL " +
												"AND m.typeID=t1.typeID " +
												"AND t2.typeID=m.parentTypeID ");
		
		PreparedStatement prep = conWRITE.prepareStatement(	"UPDATE invTypes " +
															"SET marketGroupID = ? " +
															"WHERE typeID = ? ");
		
		while (rs.next()){
			prep.setInt(1, rs.getInt("marketGroupID"));
			prep.setInt(2, rs.getInt("typeID"));
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		rs.close();
		
		statSQLite.executeUpdate("UPDATE invMarketGroups SET parentGroupID=9 " +
									"where marketGroupID in (11,24,157,955)");
		
		String marketUpdateQuery = "INSERT INTO invMarketGroups(marketGroupID, parentGroupID, " + 
									"marketGroupName, description, hasTypes) " +
									"VALUES ?";
		String[] createNewMarketGroups = {
				"(10001, 535, 'EM', 'armor EM hardeners', 1)",
				"(10002, 535, 'Explosive', 'armor Explosive hardeners', 1)",
				"(10003, 535, 'Kinetic', 'armor Kinetic hardeners', 1)",
				"(10004, 535, 'Thermal', 'armor Thermal hardeners', 1)",
		
				"(10005, 540, 'Adaptive', 'armor adaptive plating', 1)",
				"(10006, 540, 'EM', 'armor EM plating', 1)",
				"(10007, 540, 'Explosive', 'armor Explosive plating', 1)",
				"(10008, 540, 'Kinetic', 'armor Kinetic plating', 1)",
				"(10009, 540, 'Thermal', 'armor Thermal plating', 1)",
				"(10010, 540, 'Regenerative', 'armor Regenerative plating', 1)",

				"(10011, 541, 'Adaptive', 'armor adaptive energized plating', 1)",
				"(10012, 541, 'EM', 'armor EM energized plating', 1)",
				"(10013, 541, 'Explosive', 'armor Explosive energized plating', 1)",
				"(10014, 541, 'Kinetic', 'armor Kinetic energized plating', 1)",
				"(10015, 541, 'Thermal', 'armor Thermal energized plating', 1)",
				"(10016, 541, 'Regenerative', 'armor Regenerative energized plating', 1)",
		
				"(10017, 550, 'EM', 'shield EM resistance amps', 1)",
				"(10018, 550, 'Explosive', 'shield Explosive resistance amps', 1)",
				"(10019, 550, 'Kinetic', 'shield Kinetic resistance amps', 1)",
				"(10020, 550, 'Thermal', 'shield Thermal resistance amps', 1)",
				
				"(10021, 553, 'EM', 'shield EM hardeners', 1)",
				"(10022, 553, 'Explosive', 'shield Explosive hardeners', 1)",
				"(10023, 553, 'Kinetic', 'shield Kinetic hardeners', 1)",
				"(10024, 553, 'Thermal', 'shield Thermal hardeners', 1)",
				"(10025, 553, 'Invulnerability Fields', 'shield adaptive hardeners', 1)"};
		
		for(String group : createNewMarketGroups){
			statSQLite.executeUpdate(marketUpdateQuery.replace("?", group));
		}
		String[] invTypesUpdate = {
		"UPDATE invTypes SET marketGroupID = 10001 WHERE marketGroupID=535 AND typeName LIKE '%EM Hardener%'",
		"UPDATE invTypes SET marketGroupID = 10002 WHERE marketGroupID=535 AND typeName LIKE '%Explosive Hardener%'",
		"UPDATE invTypes SET marketGroupID = 10003 WHERE marketGroupID=535 AND typeName LIKE '%Kinetic Hardener%'",
		"UPDATE invTypes SET marketGroupID = 10004 WHERE marketGroupID=535 AND typeName LIKE '%Thermic Hardener%'",
		
		"UPDATE invTypes SET marketGroupID = 10005 WHERE marketGroupID=540 AND typeName LIKE '%Adaptive Nano%'",
		"UPDATE invTypes SET marketGroupID = 10006 WHERE marketGroupID=540 AND typeName LIKE '%Reflective Plating%'",
		"UPDATE invTypes SET marketGroupID = 10007 WHERE marketGroupID=540 AND typeName LIKE '%Reactive Plating%'",
		"UPDATE invTypes SET marketGroupID = 10008 WHERE marketGroupID=540 AND typeName LIKE '%Magnetic Plating%'",
		"UPDATE invTypes SET marketGroupID = 10009 WHERE marketGroupID=540 AND typeName LIKE '%Thermic Plating%'",
		"UPDATE invTypes SET marketGroupID = 10010 WHERE marketGroupID=540 AND typeName LIKE '%Regenerative Plating%'",
		
		"UPDATE invTypes SET marketGroupID = 10011 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Adaptive%'",
		"UPDATE invTypes SET marketGroupID = 10012 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Reflective%'",
		"UPDATE invTypes SET marketGroupID = 10013 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Reactive%'",
		"UPDATE invTypes SET marketGroupID = 10014 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Magnetic%'",
		"UPDATE invTypes SET marketGroupID = 10015 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Thermic%'",
		"UPDATE invTypes SET marketGroupID = 10016 WHERE marketGroupID=541 AND typeName LIKE '%Energized %Regenerative%'",
		
		"UPDATE invTypes SET marketGroupID = 10017 WHERE marketGroupID=550 AND typeName LIKE '%Magnetic Scattering%'",
		"UPDATE invTypes SET marketGroupID = 10017 WHERE marketGroupID=550 AND typeName LIKE '%EM Ward Salubrity%'",
		"UPDATE invTypes SET marketGroupID = 10018 WHERE marketGroupID=550 AND typeName LIKE '%Explosion Dampening%'",
		"UPDATE invTypes SET marketGroupID = 10018 WHERE marketGroupID=550 AND typeName LIKE '%Ballistic Screen Stabilizer%'",
		"UPDATE invTypes SET marketGroupID = 10018 WHERE marketGroupID=550 AND typeName LIKE '%F-S8%'",
		"UPDATE invTypes SET marketGroupID = 10019 WHERE marketGroupID=550 AND typeName LIKE '%Kinetic Deflection%'",
		"UPDATE invTypes SET marketGroupID = 10019 WHERE marketGroupID=550 AND typeName LIKE '%F-S9%'",
		"UPDATE invTypes SET marketGroupID = 10020 WHERE marketGroupID=550 AND typeName LIKE '%Heat Dissipation%'",
		"UPDATE invTypes SET marketGroupID = 10020 WHERE marketGroupID=550 AND typeName LIKE '%Thermal Barrier Emitter%'",
		
		"UPDATE invTypes SET marketGroupID = 10021 WHERE marketGroupID=553 AND typeName LIKE '%Photon Scattering%'",
		"UPDATE invTypes SET marketGroupID = 10021 WHERE marketGroupID=553 AND typeName LIKE '%EM Ward Reinforcement%'",
		"UPDATE invTypes SET marketGroupID = 10022 WHERE marketGroupID=553 AND typeName LIKE '%Explosion Dampening%'",
		"UPDATE invTypes SET marketGroupID = 10022 WHERE marketGroupID=553 AND typeName LIKE '%F-S15%'",
		"UPDATE invTypes SET marketGroupID = 10023 WHERE marketGroupID=553 AND typeName LIKE '%Ballistic Deflection%'",
		"UPDATE invTypes SET marketGroupID = 10023 WHERE marketGroupID=553 AND typeName LIKE '%Non-Inertial Ballistic%'",
		"UPDATE invTypes SET marketGroupID = 10024 WHERE marketGroupID=553 AND typeName LIKE '%Heat Dissipation%'",
		"UPDATE invTypes SET marketGroupID = 10024 WHERE marketGroupID=553 AND typeName LIKE '%Ditrigonal Thermal Barrier%'",
		"UPDATE invTypes SET marketGroupID = 10025 WHERE marketGroupID=553 AND typeName LIKE '%Invulnerability%'",
		"UPDATE invTypes SET marketGroupID = 10025 WHERE marketGroupID=553 AND typeName LIKE '%V-M15%'"};
		
		for(String update : invTypesUpdate){
			statSQLite.executeUpdate(update);
		}
		
		statSQLite.executeUpdate("UPDATE invMarketGroups SET hasTypes=0 " +
									"where marketGroupID in (535,540,541,550,553)");
		
		System.out.println("Done");
		
	}

/////////////////////////////////////////////////////
	private static void completeTech3Tags(Connection conWRITE) throws SQLException {
		System.out.print("Completing Tech3 tags... ");
		Statement statSQLite = conWRITE.createStatement();
		
		ResultSet rs = statSQLite.executeQuery("SELECT DISTINCT parentTypeID FROM invMetaTypes WHERE metaGroupID=14");
		
		PreparedStatement prep = conWRITE.prepareStatement("UPDATE invTypes SET metaGroupID=14 WHERE typeID=?");
		
		while(rs.next()){
			prep.setInt(1, rs.getInt(1));
			prep.addBatch();
		}
		
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		
		rs.close();
		
		statSQLite.executeUpdate("UPDATE invTypes SET metaGroupID=14 WHERE typeID IN (29984,29986,29988,29990)");
		
		System.out.println("Done");
	}
	
	
	
	
	
/////////////////////////////////////////////////////
	private static void completeBlueprintsMarketGroups(Connection conWRITE) throws SQLException {
		System.out.print("Completing blueprints market groups... ");
		Statement statSQLite = conWRITE.createStatement();
		
		ResultSet rs = statSQLite.executeQuery
		
		
		("SELECT b1.blueprintTypeID, t2.marketGroupID " +
				"FROM invTypes t1, invBlueprintTypes b1, invBlueprintTypes b2, invMetaTypes m, invTypes t2 "+
				"WHERE t1.groupID IN (SELECT groupID FROM invGroups WHERE categoryID=9) "+
				"AND t1.marketGroupID IS NULL "+
				"AND t1.typeID = b1.blueprintTypeID "+
				"AND m.typeID = b1.productTypeID "+
				"AND m.parentTypeID = b2.productTypeID "+
				"AND t2.typeID = b2.blueprintTypeID ");
		
		PreparedStatement prep = conWRITE.prepareStatement(	"UPDATE invTypes " +
															"SET marketGroupID = ? " +
															"WHERE typeID = ? ");
		
		while (rs.next()){
			prep.setInt(1, rs.getInt("marketGroupID"));
			prep.setInt(2, rs.getInt("blueprintTypeID"));
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		rs.close();
		
		prep = conWRITE.prepareStatement(
				"INSERT INTO invMarketGroups ('marketGroupID','parentGroupID', " +
									"'marketGroupName','description','hasTypes') " +
				"VALUES (" + 
					"(SELECT max(marketGroupID) FROM invMarketGroups) + 1, ?, " +
					"'Faction','Faction ship blueprints',1 )");
		
		int[] parentGroupIDs = {205,206,207};
		for(int parentGroupID : parentGroupIDs){
			prep.setInt(1, parentGroupID);
			prep.addBatch();
		}
		conWRITE.setAutoCommit(false);
		prep.executeBatch();
		conWRITE.setAutoCommit(true);
		rs.close();
		
		statSQLite.executeUpdate(	"UPDATE invTypes "+
				"SET marketGroupID = (SELECT max(marketGroupID) FROM invMarketGroups)-2 "+
				"WHERE typeID IN "+
				"(SELECT typeID FROM invTypes "+
				"WHERE groupID=105 AND marketGroupID IS NULL) ");
		statSQLite.executeUpdate(	"UPDATE invTypes "+
				"SET marketGroupID = (SELECT max(marketGroupID) FROM invMarketGroups)-1 "+
				"WHERE typeID IN "+
				"(SELECT typeID FROM invTypes "+
				"WHERE groupID=106 AND marketGroupID IS NULL) ");
		statSQLite.executeUpdate(	"UPDATE invTypes "+
				"SET marketGroupID = (SELECT max(marketGroupID) FROM invMarketGroups) "+
				"WHERE typeID IN "+
				"(SELECT typeID FROM invTypes "+
				"WHERE groupID=107 AND marketGroupID IS NULL) ");
		
		statSQLite.executeUpdate(	"UPDATE invTypes "+
				"SET marketGroupID = (SELECT max(marketGroupID) FROM invMarketGroups) "+
				"WHERE typeID IN "+
				"(SELECT typeID FROM invTypes "+
				"WHERE groupID=107 AND marketGroupID IS NULL) ");
		
		System.out.println("Done");
	}
	

/////////////////////////////////////////////////////
	public static void correctBugs(Connection conWRITE) throws SQLException{
		System.out.print("Correcting bugs... ");
		Statement statSQLite = conWRITE.createStatement();
				
		statSQLite.executeUpdate("UPDATE dgmTypeAttributes SET valueInt=valueFloat WHERE typeID=29637 AND attributeID IN (180,181,182,183,184,277,278,279,280,1047)");
		statSQLite.executeUpdate("UPDATE dgmTypeAttributes SET valueFloat=NULL WHERE typeID=29637 AND attributeID IN (180,181,182,183,184,277,278,279,280,1047)");
		statSQLite.executeUpdate("DELETE FROM eveGraphics WHERE icon='' ");
		
		statSQLite.executeUpdate("UPDATE invTypes SET metaGroupID=0 WHERE metaGroupID IS NULL");
		statSQLite.executeUpdate("UPDATE invTypes SET metaGroupID=7 WHERE metaGroupID=14");
		statSQLite.executeUpdate("UPDATE invTypes SET icon=typeID WHERE groupID IN (SELECT groupID FROM invGroups WHERE categoryID=9) AND icon LIKE 'icon%'");
		statSQLite.executeUpdate("UPDATE invTypes SET icon=typeID WHERE typeID IN (29984,29986,29988,29990)");
		statSQLite.executeUpdate("UPDATE invGroups SET graphicID=NULL WHERE graphicID NOT IN (SELECT graphicID FROM eveGraphics)");
		statSQLite.executeUpdate("UPDATE invCategories SET graphicID=NULL WHERE graphicID NOT IN (SELECT graphicID FROM eveGraphics)");
		statSQLite.executeUpdate("UPDATE invMarketGroups SET graphicID=NULL WHERE graphicID NOT IN (SELECT graphicID FROM eveGraphics)");
		statSQLite.executeUpdate("UPDATE dgmAttributeTypes SET graphicID=NULL WHERE graphicID NOT IN (SELECT graphicID FROM eveGraphics)");
		statSQLite.executeUpdate("INSERT INTO eveUnits(unitID,unitName, displayName, description) VALUES (0, 'noUnit', '', 'no unit')");
		statSQLite.executeUpdate("UPDATE dgmAttributeTypes SET unitID=0 WHERE unitID IS NULL ");
		
		statSQLite.executeUpdate("DELETE FROM invTypes WHERE groupID NOT IN (SELECT groupID FROM invGroups)");
		statSQLite.executeUpdate("DELETE FROM invTypes WHERE marketGroupID NOT IN (SELECT marketGroupID FROM invMarketGroups)");
		statSQLite.executeUpdate("DELETE FROM invTypes WHERE marketGroupID IS NULL");
		
		statSQLite.executeUpdate("DELETE FROM invMarketGroups WHERE marketGroupID NOT IN (SELECT marketGroupID FROM invTypes) AND hasTypes=1");
		statSQLite.executeUpdate("DELETE FROM invMarketGroups WHERE marketGroupID NOT IN (SELECT parentGroupID FROM invMarketGroups)");
		
		statSQLite.executeUpdate("DELETE FROM invGroups WHERE groupID NOT IN (SELECT groupID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM invGroups WHERE categoryID NOT IN (SELECT categoryID FROM invCategories)");
		statSQLite.executeUpdate("DELETE FROM invCategories WHERE categoryID NOT IN (SELECT categoryID FROM invGroups)");

		statSQLite.executeUpdate("DELETE FROM invMetaTypes WHERE typeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM invBlueprintTypes WHERE blueprintTypeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM invBlueprintTypes WHERE productTypeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM typeActivityMaterials WHERE typeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM dgmTypeAttributes WHERE typeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM dgmTypeEffects WHERE typeID NOT IN (SELECT typeID FROM invTypes)");
		statSQLite.executeUpdate("DELETE FROM eveGraphics " +
									"WHERE graphicID NOT IN " +
									"(SELECT graphicID FROM invGroups WHERE graphicID NOT NULL " +
									"UNION SELECT graphicID FROM invMarketGroups WHERE graphicID NOT NULL " +
									"UNION SELECT graphicID FROM dgmAttributeTypes WHERE graphicID NOT NULL " +
									"UNION SELECT graphicID FROM invCategories WHERE graphicID NOT NULL)");
		
		System.out.println("Done");
	}
}
