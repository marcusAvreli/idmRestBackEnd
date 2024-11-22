package idmRestBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DbFunctionFieldDAO;
import idmRestBackEnd.database.dao.DbFunctionFieldDAOFactory;
import idmRestBackEnd.entity.DbFunctionField;
import idmRestBackEnd.entity.Report;

@Path("dbField")
public class DbFunctionFieldEP {
	private static final Logger logger = LoggerFactory.getLogger(DbFunctionFieldEP.class);

	@Context
	private HttpServletRequest httpRequest;
	@POST
	@Path("create")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response createDefinition( InputStream stream) throws IOException {
		logger.info("create_db_object_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			logger.info("token_valid");
			DbFunctionFieldDAO dbFunctionField = DbFunctionFieldDAOFactory.getDataSourceDAO();
			
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
	        DbFunctionField bean = new ObjectMapper().readValue(inString, DbFunctionField.class);
	        dbFunctionField.create(bean);
	        
		}else {
			logger.info("token_is_not_valid");
		}
		logger.info("create_db_object_finish");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
	@POST
	@Path("delete")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response delete( InputStream stream) throws IOException {
		logger.info("delete_db_object_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			logger.info("token_valid");
			DbFunctionFieldDAO dbFunctionField = DbFunctionFieldDAOFactory.getDataSourceDAO();
			
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
	     //   DbFunctionField bean = new ObjectMapper().readValue(inString, DbFunctionField.class);
	        
	        ObjectMapper mapper = new ObjectMapper(); 
	       
	        TypeReference<HashMap<String,Object>> typeRef 
	                = new TypeReference<HashMap<String,Object>>() {};

	        HashMap<String,Object> deleteSubject = mapper.readValue(inString, typeRef); 
	        
	        
	        dbFunctionField.delete(deleteSubject);
	        
		}else {
			logger.info("token_is_not_valid");
		}
		logger.info("delete_db_object_finish");
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
			DbFunctionFieldDAO dbFunctionField = DbFunctionFieldDAOFactory.getDataSourceDAO();
			Report finalReport = dbFunctionField.getAll();
			logger.info("get_report_list_finish_data");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		logger.info("get_report_list_finish_empty");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
	@GET
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response fieldsByFunction(@PathParam("id") String inFunctionName) throws IOException {
		logger.info("fieldsByFunction_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> validationResult = vt.validateToken(httpRequest);		
		boolean success = (Boolean) validationResult.get("success");
		if(success) {
		
			DbFunctionFieldDAO dbFunctionField = DbFunctionFieldDAOFactory.getDataSourceDAO();
			Report finalReport = dbFunctionField.getByFunctionName(inFunctionName);
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
}
