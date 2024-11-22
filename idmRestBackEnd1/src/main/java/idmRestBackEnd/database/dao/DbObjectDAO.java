package idmRestBackEnd.database.dao;

import java.util.Map;

import idmRestBackEnd.entity.DataSource;
import idmRestBackEnd.entity.DbObject;
import idmRestBackEnd.entity.Report;

public interface DbObjectDAO {
	public boolean createReport(DbObject inDbObject);
	public Report getAll();
	public boolean delete(Map<String,Object> inDbFunctionField);
}
