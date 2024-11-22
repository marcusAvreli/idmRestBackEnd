package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DbFunctionFieldDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.entity.DbFunctionField;
import idmRestBackEnd.entity.Report;

public class IdmDbFunctionFieldDAO implements DbFunctionFieldDAO{
	private Connection connection = null;
	private static final String tableName = "tbl_all_type_attrs";
	private static final Logger logger = LoggerFactory.getLogger(IdmDbFunctionFieldDAO.class);
	public IdmDbFunctionFieldDAO(idmRestBackEnd.database.connection.Connection connection ) {
		
		this.connection = (Connection) connection.get();
	}
	
	@Override
	public boolean create(DbFunctionField inDbFunctionField) {
		boolean success = false;
		logger.info("create_function_field_start");
		String sql = "insert into "+tableName+" (name,function_name) values (:name,:functionName)";
		//statement.set
		//ResultSet result = statement.(sql);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = objectMapper
	      .convertValue(inDbFunctionField, new TypeReference<Map<String, Object>>() {});
		
	    success = DbUtil.mgExecuteUpdate(connection,sql,map);
	    logger.info("create_function_field_finish");
		return success;
	}

	@Override
	public Report getAll() {
		logger.info("get_all_data_source_start");
		String sql = "select * from "+ tableName;
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql);
				
		logger.info("get_all_data_source_finish:"+resultReport);
		
		return resultReport;
	}

	@Override
	public Report getByFunctionName(String inFunctionName) {
		String sql = "select * from "+ tableName +" where function_name=:functionName";
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("functionName", inFunctionName);
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		List<Map<String,Object>> listData =  resultReport.getData();
		return resultReport;
	}

	@Override
	public boolean delete(Map<String,Object> inDbFunctionField) {
		boolean success = false;
		logger.info("delete_function_field_start");
		String sql = "delete from "+tableName+" where name=:name and function_name=:function_name";
		//statement.set
		//ResultSet result = statement.(sql);
		/*
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = objectMapper
	      .convertValue(inDbFunctionField, new TypeReference<Map<String, Object>>() {});
		*/
	    success = DbUtil.mgExecuteUpdate(connection,sql,inDbFunctionField);
	    logger.info("delete_function_field_finish");
		return success;
	}
}
