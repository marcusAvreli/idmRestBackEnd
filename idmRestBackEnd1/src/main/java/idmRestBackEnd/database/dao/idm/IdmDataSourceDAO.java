package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DataSourceDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.database.dao.MapperUtil;
import idmRestBackEnd.entity.ColumnDefinition;
import idmRestBackEnd.entity.DataSource;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RowDefinition;
//import myRestApi.database.dao.derby.SqlRprtDAO;
import mgJdbi.mgJdbi.SqlBuilder;


public class IdmDataSourceDAO implements DataSourceDAO{
	private Connection connection = null;
	private static final String tableName = "tbl_rprt";
	idmRestBackEnd.database.connection.Connection customConnection = null;
	private static final Logger logger = LoggerFactory.getLogger(IdmDataSourceDAO.class);
	public IdmDataSourceDAO(idmRestBackEnd.database.connection.Connection connection ) {
		this.customConnection = connection;
		this.connection = (Connection) this.customConnection.get();
	}
	@Override
	public boolean createReport(DataSource rprt) {
			logger.info("create_report_start");
			boolean success = false;
			// part of input parameters based on entity
			//entity must be the same on rest and on client
			boolean exists = false;
			String dataSourceName  =rprt.getName();
			ObjectMapper objectMapper = new ObjectMapper();
		    Map<String, Object> map = objectMapper
		      .convertValue(rprt, new TypeReference<Map<String, Object>>() {});
			
		    
			String sql = "select name from " +tableName +"  where name=:name";
			Report report = DbUtil.mgExecuteSelect(connection, sql, map);
			
			if(Report.isEmpty(report)) {				
				sql = "insert into " +tableName +" (id,name,display_name,description,disabled) values (:id,:name,:display_name,:description,:disabled)";
			}else {
				sql = "update " +tableName +" set display_name=:display_name,description=:description,disabled=:disabled where name=:name";				
			}
			success = DbUtil.mgExecuteUpdate(connection,sql,map);
		    logger.info("create_report_finish");
		return success;
	}


	
	
	@Override
	public Report getAll() {
		logger.info("get_all_data_source_start");
		String sql = "select * from tbl_rprt";
		Report resultReport = mgExecuteSelect(sql);
				
		logger.info("get_all_data_source_finish");
		
		return resultReport;
	}
	
	private Report mgExecuteSelect(String inQueryTemplate){
		logger.info("mgExecuteSelect_start");
		Report resultReport = new Report();
		List<Map<String,Object>> resultList = null;
		PreparedStatement myStmt = null;
		ResultSet rs = null;
			try {
				myStmt = this.connection.prepareStatement(inQueryTemplate);
				rs = myStmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				 for(int i=1;i<=columnCount;i++) {
					 String columnName = metaData.getColumnName(i);
					 ColumnDefinition cd = new ColumnDefinition();
					 cd.setField(columnName);
					 cd.setTitle(columnName);
					 String typeName = metaData.getColumnTypeName(i);
					 int typeCode = metaData.getColumnType(i);
					 
					 resultReport.addColumn(cd);
				 }
				
				 while (rs.next()) {
					 Map<String,Object> resultMap = new HashMap<String,Object>();
					 if(null == resultList || resultList.isEmpty()) {
						 resultList = new ArrayList<Map<String,Object>>();
					 }
					 for(int i=1;i<=columnCount;i++) {
						 String columnName = metaData.getColumnName(i);
					
						 Object objectValue = rs.getObject(columnName);
						 

						 resultMap.put(columnName, objectValue);
					 }
					 RowDefinition rf = new RowDefinition();
					 rf.setData(resultMap);
					 
					 resultReport.addRow(resultMap);
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			logger.info("mgExecuteSelect_finish:"+resultList);
			   //if(this.customConnection.close()) {
			    //	logger.info("context_released");
			    //}
		return resultReport;
		
	}
	private boolean mgExecuteUpdate(String inQueryTemplate,Map<String,Object> inMappedValue){
		SqlBuilder.build(inQueryTemplate);
		String theQuery = SqlBuilder.getQuery();		
		List<String> paramNames = SqlBuilder.getParamNames();
		logger.info("theQuery:"+theQuery);
		logger.info("paramNames:"+paramNames);
		boolean success = false;
	    
	    PreparedStatement myStmt = null;
		try {
			myStmt = connection.prepareStatement(theQuery);
		
	    int counter =0;
	    for(String paramName : paramNames) {
	    	Object inValue = inMappedValue.get(paramName);
	    	counter++;
	    	logger.info("paramName:"+paramName+ " inValue:"+inValue+ " counter:"+counter);
	    	myStmt.setObject(counter, inValue);
	    }
	    int res = myStmt.executeUpdate();
	    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				myStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		   if(this.customConnection.close()) {
		    	logger.info("context_released");
		    }
		   */
	    return success;
	}
	@Override
	public Report findById(String inFunctionName) {
		String sql = "select * from "+ tableName +" where name=:functionName";
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("functionName", inFunctionName);
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		return resultReport;
	}
	@Override
	public boolean delete(DataSource inDataSource) {
		logger.info("data_source_delete");
		Map<String,Object> inMap = MapperUtil.convertToMap(inDataSource);
		String sql = "delete from "+ tableName +" where id=:id";
		Map<String,Object> inputParams = new HashMap<String,Object>();
		String id = (String) inMap.get("id");
		logger.info("id:"+id);
		inputParams.put("id", id);
		boolean success = DbUtil.mgExecuteUpdate(connection,sql,inputParams);
		
		return success;
	}

}
