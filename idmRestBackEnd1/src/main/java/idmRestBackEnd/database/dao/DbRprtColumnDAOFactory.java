package idmRestBackEnd.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmDbObjectDAO;
import idmRestBackEnd.database.dao.idm.IdmRprtColumnDAO;

public class DbRprtColumnDAOFactory {
private static final Logger logger = LoggerFactory.getLogger(DbRprtColumnDAOFactory.class);
	
	public static DbRprtColumnDAO getDAO() {			
		// use driver specified according to database		
		return new IdmRprtColumnDAO( ConnectionFactory.getConnection() );		
	}
}
