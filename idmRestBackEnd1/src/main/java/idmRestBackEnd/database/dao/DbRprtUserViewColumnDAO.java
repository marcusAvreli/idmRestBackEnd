package idmRestBackEnd.database.dao;

import java.util.List;


import idmRestBackEnd.entity.RprtUserViewColumn;

public interface DbRprtUserViewColumnDAO {

	public boolean createUpdate(List<RprtUserViewColumn> inList);
}
