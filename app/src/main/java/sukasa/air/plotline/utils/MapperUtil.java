package sukasa.air.plotline.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapperUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();


	public static Object convertMap2Object(Map<String, Object> map, Class<?> clazz) {

		return objectMapper.convertValue(map, clazz);
	}

	public static Map<String, Object> convertObject2Map(Object o) {

		return objectMapper.convertValue(o, Map.class);
	}

	public static String convertObject2JsonString(Object o) {

		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}