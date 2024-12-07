package idmRestBackEnd.database.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.DataSourceEP;
import idmRestBackEnd.database.connection.Connection;
import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmDataSourceDAO;


public class DataSourceDAOFactory {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceDAOFactory.class);
	
	public static DataSourceDAO getDAO() {
		// get connection
Connection		connection = (Connection) ConnectionFactory.getConnection();
		logger.info("connection:"+connection);
		// use driver specified according to database
		
				return new IdmDataSourceDAO( connection );
		
		}
	

}
