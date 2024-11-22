package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.database.dao.MapperUtil;
import idmRestBackEnd.database.dao.DbRprtColumnDAO;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtField;

public class IdmRprtColumnDAO implements DbRprtColumnDAO{
	private Connection connection = null;
	private static final String tableName = "tbl_rprt_field";
	idmRestBackEnd.database.connection.Connection customConnection = null;
	private static final Logger logger = LoggerFactory.getLogger(IdmRprtColumnDAO.class);
	public IdmRprtColumnDAO(idmRestBackEnd.database.connection.Connection connection ) {
		this.customConnection = connection;
		this.connection = (Connection) this.customConnection.get();
	}
	@Override
	public Report getByReportName(String inRprtColumn) {
		// part of input parameters based on entity
		//entity must be the same on rest and on client
		String sql = "select * from "+ tableName +" where rprt_name=:rprtName";
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("rprtName", inRprtColumn);
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		
		return resultReport;
	}
	@Override
	public boolean create(RprtField inField) {
		logger.info("create_report_column_start");
		// part of input parameters based on entity
		//entity must be the same on rest and on client
		/* String sql = "insert * from "+ tableName +" where rprt_name=:rprtName"; */
		Map<String,Object> map = MapperUtil.convertToMap(inField);
		boolean success = false;
		String sql = "select name from " +tableName +"  where name=:name and rprt_id=:rprt_id";
		logger.info("sql:"+sql);
		Report report = DbUtil.mgExecuteSelect(connection, sql, map);
		List<Map<String,Object>> datum = report.getData();
		logger.info("datum:"+datum);
		if(Report.isEmpty(report)) {
			logger.info("insert");
			sql = "insert into " +tableName +" (id,name,rprt_id,description,disabled,not_visible,display_iw) values (:id,:name,:rprt_id,:description,:disabled,:not_visible,:display_iw)";
			logger.info("sql:"+sql);
		}else {
			logger.info("update");
			sql = "update  " +tableName +" set  description=:description,disabled=:disabled,not_visible=:not_visible,display_iw=:display_iw where name=:name and rprt_id=:rprt_id";
			logger.info("sql:"+sql);
		}
		success = DbUtil.mgExecuteUpdate(connection,sql,map);
	    logger.info("create_report_column_finished");
		return false;
	}
	@Override
	public Report getByReportId(String inRprtId) {
		// part of input parameters based on entity
		//entity must be the same on rest and on client
		String sql = "select * from "+ tableName +" where rprt_id=:report_id";
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("report_id", inRprtId);
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		
		return resultReport;
	}

}
