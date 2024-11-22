package idmRestBackEnd.database.dao;

import idmRestBackEnd.database.connection.ConnectionFactory;
import idmRestBackEnd.database.dao.idm.IdmRprtMetaDAO;
import idmRestBackEnd.database.dao.idm.IdmRprtUserViewColumnDAO;

public class DbRprtMetaDAOFactory {
	public static IdmRprtMetaDAO getDAO() {			
		// use driver specified according to database		
		return new IdmRprtMetaDAO( ConnectionFactory.getConnection() );		
	}
}
