package idmRestBackEnd.database.dao.idm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idmRestBackEnd.database.dao.DbRprtMetaDAO;
import idmRestBackEnd.database.dao.DbUtil;
import idmRestBackEnd.entity.ColumnDefinition;
import idmRestBackEnd.entity.FormatterParam;
import idmRestBackEnd.entity.HeaderFilterFuncParams;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtMeta;

public class IdmRprtMetaDAO implements DbRprtMetaDAO {
	private Connection connection = null;
	private static final String tableName = "tbl_rprt_field";
	idmRestBackEnd.database.connection.Connection customConnection = null;
	private static final Logger logger = LoggerFactory.getLogger(IdmRprtMetaDAO.class);
	public IdmRprtMetaDAO(idmRestBackEnd.database.connection.Connection connection ) {
		this.customConnection = connection;
		this.connection = (Connection) this.customConnection.get();
	}
	@Override
	public Report getMetaData(String inUVid) {
		
		String sql="select ordering,RprtField.name,RprtUv.rprt_id,RprtField.description,RprtField.display_iw,RprtUv.id  from tbl_rprt_uvc RprtUvc " 

		+" inner join tbl_rprt_uv RprtUv on RprtUv.id = RprtUvc.uv_id "
		+" inner join tbl_rprt_field RprtField on RprtField.id = RprtUvc.field_id "
		+" where RprtUv.id=:uv_id";
		// part of input parameters based on entity
					//entity must be the same on rest and on client
		Map<String,Object> inParams = new HashMap<String,Object>();
		inParams.put("uv_id", inUVid);	
		Report resultReport = DbUtil.mgExecuteSelect(connection,sql,inParams);
		Report resultReport2 = null;
		Report resultReport3 = null;
		if(!Report.isEmpty(resultReport)) {
			List<Map<String,Object>> finalReportColumns = resultReport.getData();
			Map<String,Object> firstDatum = finalReportColumns.get(0);
			String reportId = (String) firstDatum.get("rprt_id");
			logger.info("reportId:"+reportId);
			
			// part of input parameters based on entity
						//entity must be the same on rest and on client
			sql = "select name from tbl_rprt where id=:id";
			 inParams = new HashMap<String,Object>();
			 inParams.put("id", reportId);
			 resultReport2 = DbUtil.mgExecuteSelect(connection,sql,inParams);
			 List<ColumnDefinition> finalColumns = new ArrayList<ColumnDefinition>();
			 List<Map<String,Object>> reportDataName = resultReport2.getData();
			 Map<String,Object> reportMetaEntity = reportDataName.get(0);
			 String reportName = (String) reportMetaEntity.get("name");
			 
			 resultReport3 =  DbUtil.runCallableStatement(connection,reportName);
				
			 for(Map<String,Object> mapColumn : finalReportColumns) {
				 ColumnDefinition cd = new ColumnDefinition();
				 String name = (String) mapColumn.get("name");
				 cd.setField(name);
				 String display_iw = (String) mapColumn.get("display_iw");
				 if(null != display_iw && !display_iw.isEmpty()) {
					 cd.setTitle(display_iw);
				 }else {
					 cd.setTitle(name);
				 }
				 logger.info("meta_name:"+name);
				 if(name.toUpperCase().endsWith("_DATE")) {
					 logger.info("setting_date");
					 cd.setFormatter("wj-date");
					 cd.setHeaderFilter("myEditor");
					 
					/*cd.setFilterable(true);
					 cd.setFilterField(name);
					 cd.setSortField(name);
					 cd.setSorter("date");
					 */
					 FormatterParam fps = new FormatterParam();
					 fps.setTemplate("yyyy-mm-dd");
					 /*HeaderFilterFuncParams headerFilterFuncParams = new HeaderFilterFuncParams(); 
					 headerFilterFuncParams.setType("date-range");
					 cd.setHeaderFilterFuncParams(headerFilterFuncParams);
					*/
					 cd.setFormatterParams(fps);
					 
				 }
				 int ordering = (int) mapColumn.get("ordering");
				 cd.setOrdering(ordering);
				 finalColumns.add(cd);
			 }
				 
				 
			 resultReport3.setColumns(finalColumns);
				 
				
				
			 }
			
			 
		
			 
		
		
		return resultReport3;
	}

}
