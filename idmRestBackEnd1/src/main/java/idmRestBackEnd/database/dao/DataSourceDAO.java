package idmRestBackEnd.database.dao;

import java.util.List;

import idmRestBackEnd.entity.DataSource;
import idmRestBackEnd.entity.Report;


public interface DataSourceDAO {
	public boolean createReport(DataSource rprt);
	public Report getAll();
	public Report findById(String functionName);
}
