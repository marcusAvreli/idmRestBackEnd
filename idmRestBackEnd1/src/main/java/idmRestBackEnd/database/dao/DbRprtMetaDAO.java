package idmRestBackEnd.database.dao;

import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtMeta;

public interface DbRprtMetaDAO {

	public Report getMetaData(String inUVid);
}
