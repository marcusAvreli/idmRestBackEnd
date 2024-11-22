package idmRestBackEnd.config;




import idmRestBackEnd.idm.connection.def.DataBaseType;

public class DbConfig {

	
	
	private static DataBaseType dbType = null;
	
	
	static {
		// should be read via console
		parseXML(  );
	}
	
		
	private static void parseXML(  )  {
		

						dbType = DataBaseType.IDM;
				
		
		
	}
	
	public static DataBaseType getDbType() {
		return dbType;
	}
	

}