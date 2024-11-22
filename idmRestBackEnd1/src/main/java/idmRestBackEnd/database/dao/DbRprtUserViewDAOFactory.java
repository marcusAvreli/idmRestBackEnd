package idmRestBackEnd.database.dao;

import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmRprtUserViewDAO;

public class DbRprtUserViewDAOFactory {
	public static IdmRprtUserViewDAO getDAO() {			
		// use driver specified according to database		
		return new IdmRprtUserViewDAO( ConnectionFactory.getConnection() );		
	}
}
