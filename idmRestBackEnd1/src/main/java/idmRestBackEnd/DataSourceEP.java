package idmRestBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DataSourceDAO;
import idmRestBackEnd.database.dao.DataSourceDAOFactory;
import idmRestBackEnd.database.dao.DbFunctionFieldDAO;
import idmRestBackEnd.database.dao.DbFunctionFieldDAOFactory;
import idmRestBackEnd.database.dao.idm.IdmDataSourceDAO;
import idmRestBackEnd.entity.DataSource;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RowDefinition;

@Path("dataSource")
public class DataSourceEP {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceEP.class);

	@Context
	private HttpServletRequest httpRequest;
	@POST
	@Path("create")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response createDefinition( InputStream stream/*@JsonProperty("queryMap") String queryMap,@JsonProperty("queryString") String queryString*/) throws IOException {
		logger.info("create_report_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			logger.info("token_valid");
			DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDataSourceDAO();
			
		  StringBuilder crunchifyBuilder = new StringBuilder();
	        try {
	            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
	            String line = null;
	            while ((line = in.readLine()) != null) {
	                crunchifyBuilder.append(line);
	            }
	        } catch (Exception e) {
	        	logger.error("Error Parsing: - ");
	        }
	        
	        String inString = crunchifyBuilder.toString();
	        logger.info("Data Received: " + inString);
	        DataSource bean = new ObjectMapper().readValue(inString, DataSource.class);
	        dataSourceDAO.createReport(bean);
	        
		}else {
			logger.info("token_is_not_valid");
		}
		logger.info("create_report_finish");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
	@GET
	@Path("getAll")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response reportList() throws IOException {		
		logger.info("get_report_list_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> validationResult = vt.validateToken(httpRequest);		
		boolean success = (Boolean) validationResult.get("success");
		if(success) {
			logger.info("token_validated:"+success);		
			DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDataSourceDAO();
			Report finalReport = dataSourceDAO.getAll();
			
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		logger.info("get_report_list_finish_empty");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
	
	@GET
	@Path("/{functionName}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response findById(@PathParam("functionName") String inFunctionName) throws IOException {
		logger.info("fieldsByFunction_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> validationResult = vt.validateToken(httpRequest);		
		boolean success = (Boolean) validationResult.get("success");
		if(success) {
		
			DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDataSourceDAO();
			Report finalReport = dataSourceDAO.findById(inFunctionName);
			List<Map<String,Object>> data = finalReport.getData();
			if(null != data) {
				Map<String,Object> resultData = data.get(0);
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(resultData).build();
			}else {
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
			}
		}
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
}
