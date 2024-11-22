package idmRestBackEnd.database.connection.idm;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.tools.GeneralException;


public class IdmConnection implements idmRestBackEnd.database.connection.Connection {

	private static final Logger logger = LoggerFactory.getLogger(IdmConnection.class);
	
	Connection connection = null;
	private SailPointContext ctx = null;
	@Override
	public Object get() {
		return connection;
	}

	@Override
	public boolean open() {
		logger.info("open connection");
		connection = null;
	    try {
	    	
	    	

			
			try {
				ctx = SailPointFactory.getCurrentContext();
			} catch (GeneralException e) {
				logger.info("context_already_created");
			}
			if(null == ctx) {
				try {
					ctx = SailPointFactory.createContext("My Context");
				} catch (GeneralException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			if(null != ctx) {
				try {
					connection =  ctx.getJdbcConnection();
				} catch (GeneralException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	      
	    
	    } catch ( Exception e ) {
	    	logger.error("connection exception:",e);
	    	e.printStackTrace();
	      logger.debug( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    logger.debug("Opened database successfully");
	    
	   
	    
	    return true;
	}

	@Override
	public boolean close() {
		logger.info("release_context_start");
		boolean success = false;
		if(ctx !=null) {
			try {
				SailPointFactory.releaseContext(ctx);
				success = true;
				logger.info("CONTEXT_RELEASED");
			} catch (GeneralException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*if( connection != null ) {
			try {
				connection.close();
			} catch ( SQLException e ) {
				logger.debug( "Databank not closed correctly: " + e.getMessage() );
			}
			return true;
		}*/
		logger.info("release_context_finish");
		return success;
	}
	
	private boolean checkForUserSchema() {
		try {
			//Statement stmt = connection.createStatement();
			//ResultSet rs = stmt.executeQuery( "SELECT name FROM sqlite_master WHERE type='table' AND name='USER'" );
			//if (rs.next()) {
				return true;
			//}
			//rs.close();
			//stmt.close();
		} catch ( Exception e ) {
			logger.debug( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}
	
	
	
	

}