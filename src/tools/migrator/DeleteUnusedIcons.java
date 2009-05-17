package migrator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DeleteUnusedIcons {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conSQLite = DriverManager.getConnection("jdbc:sqlite:resources/eve-online.db");
		
		Statement statSQLite = conSQLite.createStatement();
		ResultSet rs = statSQLite.executeQuery("SELECT DISTINCT icon FROM invTypes");
			
		HashMap<String, String> iconsUsed = new HashMap<String, String>();

		while(rs.next()){
			String icon = rs.getString("icon")+".png";
			iconsUsed.put(icon, icon);
		}
		
		rs.close();
		
		File path = new File("resources/icons/EVE/");
		File[] files = path.listFiles();
		
		int i=1;
		
		for(File file : files){
			if (file.getName().equals(".svn") || !iconsUsed.containsKey(file.getName())){
				System.out.println(i + " " + file.getName() + " not used. Deleting...");
				i++;
				file.deleteOnExit();
			}
		}
	}
	

}