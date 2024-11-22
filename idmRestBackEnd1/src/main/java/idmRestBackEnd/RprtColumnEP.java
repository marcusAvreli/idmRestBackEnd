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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import idmRestBackEnd.database.dao.DbRprtColumnDAO;
import idmRestBackEnd.database.dao.DbRprtColumnDAOFactory;
import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtField;

@Path("rprtColumn")
public class RprtColumnEP {
	private static final Logger logger = LoggerFactory.getLogger(RprtColumnEP.class);

	@Context
	private HttpServletRequest httpRequest;
	@POST
	@Path("create")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response createDefinition( InputStream stream/*@JsonProperty("queryMap") String queryMap,@JsonProperty("queryString") String queryString*/) throws IOException {
		logger.info("create_report_column_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			logger.info("token_valid");
			DbRprtColumnDAO dataSourceDAO = DbRprtColumnDAOFactory.getDAO();
			
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
	       
	        RprtField bean = new ObjectMapper().readValue(inString, RprtField.class);
	        dataSourceDAO.create(bean);
	        
		}else {
			logger.info("token_is_not_valid");
		}
		logger.info("create_report_finish");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
	@GET
	@Path("/{reportId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response rprtColumnByName(@PathParam("reportId") String inDataSourceName) throws IOException {
		logger.info("rprtColumnByName_start");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> validationResult = vt.validateToken(httpRequest);		
		boolean success = (Boolean) validationResult.get("success");
		if(success) {
		
			DbRprtColumnDAO dbRprtColumn = DbRprtColumnDAOFactory.getDAO();
			Report finalReport = dbRprtColumn.getByReportId(inDataSourceName);
			logger.info("rprtColumnByName_finish_1");
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		logger.info("rprtColumnByName_finish_2");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
}
