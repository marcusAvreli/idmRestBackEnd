package idmRestBackEnd.database.dao.idm;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.dao.DbRprtUserViewColumnDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.database.dao.MapperUtil;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtUserViewColumn;

public class IdmRprtUserViewColumnDAO implements DbRprtUserViewColumnDAO{
	private static final Logger logger = LoggerFactory.getLogger(IdmRprtUserViewColumnDAO.class);
	private Connection connection = null;
	private static final String tableName = "tbl_rprt_uvc";
	public IdmRprtUserViewColumnDAO(idmRestBackEnd.database.connection.Connection connection ) {
		
		this.connection = (Connection) connection.get();
	}
	@Override
	public boolean createUpdate(List<RprtUserViewColumn> inList) {
		boolean success = false;
		logger.info("create_user_view_column_start");
		for(RprtUserViewColumn uvc : inList) {
			
			Map<String,Object> map = MapperUtil.convertToMap(uvc);
			// part of input parameters based on entity
			//entity must be the same on rest and on client
			String sql = "select * from " +tableName +"  where field_id=:field_id and uv_id=:uv_id";
			Report report = DbUtil.mgExecuteSelect(connection, sql, map);
			if(Report.isEmpty(report)) {				
				sql = "insert into " +tableName +" (id,ordering,field_id,uv_id) values (:id,:ordering,:field_id,:uv_id)";
			}else {
				sql = "update " +tableName +" set ordering=:ordering where field_id=:field_id and uv_id=:uv_id";				
			}
			success = DbUtil.mgExecuteUpdate(connection,sql,map);
		}
		logger.info("create_user_view_column_finish");
		return success;
	}

}
