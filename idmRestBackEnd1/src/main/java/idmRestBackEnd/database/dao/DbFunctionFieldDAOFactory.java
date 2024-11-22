package idmRestBackEnd.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.connection.Connection;
import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmDbFunctionFieldDAO;
import idmRestBackEnd.database.dao.idm.IdmDbObjectDAO;

public class DbFunctionFieldDAOFactory {
private static final Logger logger = LoggerFactory.getLogger(DbFunctionFieldDAOFactory.class);
	
	public static DbFunctionFieldDAO getDataSourceDAO() {			
		// use driver specified according to database			
		return new IdmDbFunctionFieldDAO( ConnectionFactory.getConnection() );		
	}
	
}

