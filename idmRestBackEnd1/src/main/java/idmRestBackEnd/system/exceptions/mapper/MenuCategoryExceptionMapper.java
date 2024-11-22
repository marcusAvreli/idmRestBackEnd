package idmRestBackEnd.system.exceptions.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import idmRestBackEnd.system.exceptions.application.ApplicationDoesNotExistException;
import idmRestBackEnd.system.exceptions.file.MissingFileException;


public class MenuCategoryExceptionMapper {
	   @Provider
	    public static class MenuCategoryUpdateExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MissingFileException> {
	        
	        public Response toResponse(MissingFileException exception) {
	            return this.buildResponse(exception.getErrorStatus(), exception);
	        }
	    }
	   
	   @Provider
	    public static class ApplicationDoesnotExistMapper extends BasicExceptionMapper implements ExceptionMapper<ApplicationDoesNotExistException> {
	        
	        public Response toResponse(ApplicationDoesNotExistException exception) {
	            return this.buildResponse(exception.getErrorStatus(), exception);
	        }
	    }
}
