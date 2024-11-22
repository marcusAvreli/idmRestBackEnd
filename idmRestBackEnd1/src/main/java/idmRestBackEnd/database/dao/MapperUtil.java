package idmRestBackEnd.database.dao;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {

	public static Map<String,Object> convertToMap(Object inObject){
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = objectMapper
	      .convertValue(inObject, new TypeReference<Map<String, Object>>() {});
	    return map;
	}
}
