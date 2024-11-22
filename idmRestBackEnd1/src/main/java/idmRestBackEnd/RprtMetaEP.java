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

import idmRestBackEnd.database.dao.DbRprtMetaDAO;
import idmRestBackEnd.database.dao.DbRprtMetaDAOFactory;
import idmRestBackEnd.database.dao.DbRprtUserViewColumnDAO;
import idmRestBackEnd.database.dao.DbRprtUserViewColumnDAOFactory;
import idmRestBackEnd.entity.Report;

@Path("rprtBuilder")
public class RprtMetaEP {
	private static final Logger logger = LoggerFactory.getLogger(RprtMetaEP.class);
	@Context
	private HttpServletRequest httpRequest;
	
	@GET
	@Path("/{reportId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})	
	public Response createDefinition( @PathParam("reportId") String inDataSourceName) throws IOException {
		logger.info("start_producing_report");
		ValidateToken vt = new ValidateToken();		
		Map<String, Object> result = vt.validateToken(httpRequest);
		
		
		boolean success = (Boolean) result.get("success");
		
		if(success) {
			DbRprtMetaDAO dataSourceDAO = DbRprtMetaDAOFactory.getDAO();
			 /*StringBuilder crunchifyBuilder = new StringBuilder();
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
		        */
		        logger.info("Data Received: " + inDataSourceName);
		        
		        
		        Report finalReport = dataSourceDAO.getMetaData(inDataSourceName);
				logger.info("finish_producing_report");
				return Response.status(200).type(MediaType.APPLICATION_JSON).entity(finalReport).build();
		}
		logger.info("finish_producing_report");
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(null).build();
	}
}
