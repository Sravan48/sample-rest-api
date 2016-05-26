package com.cognitivescale.rest.util;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {
	
	public static String toJSON(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object toObject(Class<?> type, String json) {
		try {
			return new ObjectMapper().readValue(json, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

