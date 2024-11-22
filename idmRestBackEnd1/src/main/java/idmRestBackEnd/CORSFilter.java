package idmRestBackEnd;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

@Provider
public class CORSFilter implements ContainerRequestFilter,ContainerResponseFilter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	
	// preflight request method
		private static final String OPTIONS_METHOD = "OPTIONS";
		// preflight request headers
		private static final String ORIGIN = "Origin";
		private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
		private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
		// preflight response headers
		private static final String VARY = "Vary";
		private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
		private static final String ACCESS_CONTROL_ALLOW_METHOD = "Access-Control-Allow-Method";
		private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	@Override
	public void filter(ContainerRequestContext reqContext) {
			
		String method = reqContext.getMethod();
		MultivaluedMap<String, String> reqHeaders = reqContext.getHeaders();

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("method = %s", method));
			logger.debug(String.format("reqHeaders = %s", reqHeaders));
		}

		if (OPTIONS_METHOD.equals(method)
				&& reqHeaders.keySet().containsAll(Arrays.asList(ORIGIN, ACCESS_CONTROL_REQUEST_METHOD))) {

			String requestMethods = reqContext.getHeaderString(ACCESS_CONTROL_REQUEST_METHOD);
			String allowHeaders = reqContext.getHeaderString(ACCESS_CONTROL_REQUEST_HEADERS);

			Response response = Response.status(Status.NO_CONTENT)
					.header(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
					.header(ACCESS_CONTROL_ALLOW_METHOD, requestMethods)
					.header(ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders).header(VARY, ORIGIN).build();
			reqContext.abortWith(response);
		}
		
	}
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
    	
    	//if (request.getHeaderString(ORIGIN) == null
			//	||
				
		if(OPTIONS_METHOD.equalsIgnoreCase(request.getMethod())) {
			
			return;
		}
        response.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
     MultivaluedMap<String, Object> multi = response.getHeaders();
     
      
        response.getHeaders().add("Access-Control-Allow-Headers",
                "CSRF-Token, X-Requested-By, Authorization, Content-Type");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        
    }
}