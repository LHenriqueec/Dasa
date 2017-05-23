package com.iveso.dasa.util;

import com.google.gson.Gson;

public abstract class JsonUtils {

	private static Gson gson;
	
	
	public static String toJson(Object obj) {
		instance();
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String value, Class<T> clazz) {
		instance();
		return gson.fromJson(value, clazz);
	}
	
	private static void instance() {
		if (gson == null || gson.equals(null)) gson = new Gson();
	}
}
