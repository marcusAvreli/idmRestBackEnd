package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.dao.DbRprtUserViewDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.database.dao.MapperUtil;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtUserView;

public class IdmRprtUserViewDAO implements DbRprtUserViewDAO{
	private static final Logger logger = LoggerFactory.getLogger(IdmRprtUserViewDAO.class);
	private Connection connection = null;
	private static final String tableName = "tbl_rprt_uv";
	public IdmRprtUserViewDAO(idmRestBackEnd.database.connection.Connection connection ) {
		
		this.connection = (Connection) connection.get();
	}
	@Override
	public boolean createUpdate(RprtUserView inRprtUserView) {
		logger.info("create_user_view_start");
		boolean success = false;
		Map<String,Object> map = MapperUtil.convertToMap(inRprtUserView);
		// part of input parameters based on entity
		//entity must be the same on rest and on client
		String sql = "select * from " +tableName +"  where display_name=:display_name";
		Report report = DbUtil.mgExecuteSelect(connection, sql, map);
		
		if(Report.isEmpty(report)) {				
			sql = "insert into " +tableName +" (id,rprt_id,name,display_name,description) values (:id,:rprt_id,:name,:display_name,:description)";
		}else {
			sql = "update " +tableName +" set description=:description where display_name=:display_name";				
		}
		success = DbUtil.mgExecuteUpdate(connection,sql,map);
		logger.info("create_user_view_finish");
		return success;
	}
	@Override
	public Report getAll() {
		logger.info("get_all_data_source_start");
		String sql = "select * from "+tableName;
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql);
				
		logger.info("get_all_data_source_finish");
		
		return resultReport;
	}
	@Override
	public Report getById(String uvId) {
		String sql = "select * from "+tableName + " where id=:id";
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("id", uvId);
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		return resultReport;
	}
}
