package idmRestBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DbFunctionFieldDAO;
import idmRestBackEnd.database.dao.DbFunctionFieldDAOFactory;
import idmRestBackEnd.database.dao.DbRprtUserViewDAO;
import idmRestBackEnd.database.dao.DbRprtUserViewDAOFactory;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtUserView;

@Path("rprtUV")
public class RprtUserViewEP {
	private static final Logger logger = LoggerFactory.getLogger(RprtUserViewEP.class);
	
	@Context
	private HttpServletRequest httpRequest;
	@POST
	@Path("create")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response createDefinition( InputStream stream/*@JsonProperty("queryMap") String queryMap,@JsonProperty("queryString") String queryString*/) throws IOException {
		logger.info("create_user_view");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			logger.info("token_valid");
			DbRprtUserViewDAO dataSourceDAO = DbRprtUserViewDAOFactory.getDAO();
			
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
	       
	        RprtUserView bean = new ObjectMapper().readValue(inString, RprtUserView.class);
	        dataSourceDAO.createUpdate(bean);
	        
		}else {
			logger.info("token_is_not_valid");
		}
		logger.info("create_user_view_finish");
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
			DbRprtUserViewDAO dataSourceDAO = DbRprtUserViewDAOFactory.getDAO();
			Report finalReport = dataSourceDAO.getAll();
			logger.info("get_report_list_finish_data");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		logger.info("get_report_list_finish_empty");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
}
