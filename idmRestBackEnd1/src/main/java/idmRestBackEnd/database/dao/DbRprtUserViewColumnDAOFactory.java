package idmRestBackEnd.database.dao;

import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmRprtUserViewColumnDAO;

public class DbRprtUserViewColumnDAOFactory {

	public static IdmRprtUserViewColumnDAO getDAO() {			
		// use driver specified according to database		
		return new IdmRprtUserViewColumnDAO( ConnectionFactory.getConnection() );		
	}
}
