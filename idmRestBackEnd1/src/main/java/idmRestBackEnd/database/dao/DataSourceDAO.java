package idmRestBackEnd.database.dao;

import java.util.List;
import java.util.Map;

import idmRestBackEnd.entity.DataSource;
import idmRestBackEnd.entity.Report;


public interface DataSourceDAO {
	public boolean createReport(DataSource rprt);
	public boolean delete(DataSource inDataSource);
	public Report getAll();
	public Report findById(String functionName);
}
