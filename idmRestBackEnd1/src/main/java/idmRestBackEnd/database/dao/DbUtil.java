package idmRestBackEnd.database.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.dao.idm.IdmDataSourceDAO;
import idmRestBackEnd.entity.ColumnDefinition;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RowDefinition;
import mgJdbi.mgJdbi.SqlBuilder;

public class DbUtil {
	private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);
	public static boolean mgExecuteUpdate(Connection connection,String inQueryTemplate,Map<String,Object> inMappedValue){
		SqlBuilder.build(inQueryTemplate);
		String theQuery = SqlBuilder.getQuery();		
		List<String> paramNames = SqlBuilder.getParamNames();
		logger.info("the_query:"+theQuery);
		logger.info("paramNames:"+paramNames);
		boolean success = false;
	    
	    PreparedStatement myStmt = null;
		try {
			myStmt = connection.prepareStatement(theQuery);
		
	    int counter =0;
	    for(String paramName : paramNames) {
	    	Object inValue = inMappedValue.get(paramName);
	    	counter++;
	    	
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
	public static Report runCallableStatement(Connection connection,String name){
		Map<String,Object> inputParams = new HashMap<String,Object>();
		
		Report report = runCallableStatement(connection,name,inputParams);
		return report;
	}
	public static Report runCallableStatement(Connection connection,String name, Map<String, Object> inputParams){
		CallableStatement cs = null;
		Report report = null;
		ResultSet resultSet = null;
		try {
			cs = connection.prepareCall("call "+ name + "()");
			resultSet = cs.executeQuery();
			 report = iterateOverResultSet(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				resultSet.close();
				cs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return report;
	}
	
	public static Report iterateOverResultSet(ResultSet inRs) {
		Report resultReport = new Report();
		 try {
		 ResultSetMetaData metaData = inRs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			 for(int i=1;i<=columnCount;i++) {
				 String columnName = metaData.getColumnName(i);
				 String fieldType = metaData.getColumnTypeName(i);
				 int columnSize =	metaData.getPrecision(i);
				
				 
					ColumnDefinition cd = new ColumnDefinition();
					cd.setField(columnName);
					cd.setTitle(columnName);
			 		if(fieldType.equals("BIT") && columnSize == 1) {
			 			cd.setFormatter("wj-cell-checkbox");
			 		}
					
				 
					resultReport.addColumn(cd);
			 }
			
			
				while (inRs.next()) {
					 Map<String,Object> resultMap = new HashMap<String,Object>();
					 /*if(null == resultList || resultList.isEmpty()) {
						 resultList = new ArrayList<Map<String,Object>>();
					 }
					 */
					 for(int i=1;i<=columnCount;i++) {
						 String columnName = metaData.getColumnName(i);
					
						 Object objectValue = inRs.getObject(columnName);
						
						 String fieldType = metaData.getColumnTypeName(i);
						 int columnSize =	metaData.getPrecision(i);
						 if(fieldType.equals("BIT") && columnSize == 1) {
							 int myInt = (Boolean)objectValue ? 1 : 0;
							 resultMap.put(columnName, myInt);
					 		}else {
						 resultMap.put(columnName, objectValue);
					 		}
					 }
					 RowDefinition rf = new RowDefinition();
					 rf.setData(resultMap);
					 
					 resultReport.addRow(resultMap);
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return resultReport;
	}
	public static Report mgExecuteSelect(PreparedStatement myStmt){
		List<Map<String,Object>> resultList = null;
		Report resultReport = new Report();
		ResultSet rs = null;
		try {
			rs = myStmt.executeQuery();
			resultReport = iterateOverResultSet(rs);
		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				rs.close();
				myStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultReport;
	}
	public static Report mgExecuteSelect(Connection connection,String inQueryTemplate,Map<String,Object> inMappedValue){
		
		SqlBuilder.build(inQueryTemplate);
		String theQuery = SqlBuilder.getQuery();		
		List<String> paramNames = SqlBuilder.getParamNames();
		logger.info("the_query:"+theQuery);
		logger.info("paramNames:"+paramNames);
		logger.info("inMappedValue:"+inMappedValue);
		boolean success = false;
		Report resultReport = new Report();
		PreparedStatement myStmt = null;
		ResultSet rs = null;
		
		try {
			myStmt = connection.prepareStatement(theQuery);		
		    int counter =0;
		    for(String paramName : paramNames) {
		    	Object inValue = inMappedValue.get(paramName);
		    	counter++;
		    	logger.info("setting_value:"+inValue);
		    	logger.info("setting_value_paramName:"+paramName);
		    	myStmt.setObject(counter, inValue);
		    }
		    resultReport = mgExecuteSelect(myStmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return resultReport;
	}
	public static Report mgExecuteSelect(Connection connection,String inQueryTemplate){
		
		Report resultReport = new Report();
		List<Map<String,Object>> resultList = null;
		PreparedStatement myStmt = null;
		ResultSet rs = null;
			
		try {
			myStmt = connection.prepareStatement(inQueryTemplate);
			rs = myStmt.executeQuery();
			resultReport = mgExecuteSelect(myStmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return resultReport;
		
	}
	
}
