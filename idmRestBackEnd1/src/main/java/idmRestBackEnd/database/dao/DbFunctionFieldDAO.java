package idmRestBackEnd.database.dao;

import java.util.Map;

import idmRestBackEnd.entity.DbFunctionField;
import idmRestBackEnd.entity.DbObject;
import idmRestBackEnd.entity.Report;

public interface DbFunctionFieldDAO {
	public boolean create(DbFunctionField inDbFunctionField);
	public boolean delete(Map<String,Object> inDbFunctionField);
	public Report getAll();
	public Report getByFunctionName(String inFunctionName);
}
