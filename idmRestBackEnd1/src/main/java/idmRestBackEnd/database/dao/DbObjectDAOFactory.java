package idmRestBackEnd.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.connection.Connection;
import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmDataSourceDAO;
import idmRestBackEnd.database.dao.idm.IdmDbObjectDAO;

public class DbObjectDAOFactory {
private static final Logger logger = LoggerFactory.getLogger(DbObjectDAOFactory.class);
	
	public static DbObjectDAO getDataSourceDAO() {			
		// use driver specified according to database		
		return new IdmDbObjectDAO( ConnectionFactory.getConnection() );		
	}
	
}
