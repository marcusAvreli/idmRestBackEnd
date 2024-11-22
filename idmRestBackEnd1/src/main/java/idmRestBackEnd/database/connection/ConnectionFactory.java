package idmRestBackEnd.database.connection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.config.DbConfig;
import idmRestBackEnd.database.connection.idm.IdmConnection;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.tools.GeneralException;


public class ConnectionFactory {
	private static Connection connection = null;
	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	public static Connection getConnection() {
		
		if( connection != null ) return connection;
		
		switch( DbConfig.getDbType() ) {
		case IDM:		
			connection = new IdmConnection();
			break;
		
		default:break;
		};
	
		// open connection
		connection.open();
		
		return connection;
	}
	
	

}
