package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DbObjectDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.entity.DbObject;
import idmRestBackEnd.entity.Report;

public class IdmDbObjectDAO implements DbObjectDAO{
	private static final Logger logger = LoggerFactory.getLogger(IdmDbObjectDAO.class);
	private Connection connection = null;
	private static final String tableName = "tbl_all_objects";
	public IdmDbObjectDAO(idmRestBackEnd.database.connection.Connection connection ) {
		
		this.connection = (Connection) connection.get();
	}
	@Override
	public boolean createReport(DbObject inDbObject) {
		boolean success = false;
		
		String sql = "insert into "+tableName+" (name,type) values (:object_name,:object_type)";
		//statement.set
		//ResultSet result = statement.(sql);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = objectMapper
	      .convertValue(inDbObject, new TypeReference<Map<String, Object>>() {});
		
	    success = DbUtil.mgExecuteUpdate(connection,sql,map);
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
	public boolean delete(Map<String, Object> inDbFunctionField) {
		boolean success = false;
		logger.info("delete_function_field_start: "+inDbFunctionField);
		String sql = "delete from "+tableName+" where name=:name and type=:type";
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
