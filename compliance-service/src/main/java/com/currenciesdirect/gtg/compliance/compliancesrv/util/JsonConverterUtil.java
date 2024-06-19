package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverterUtil {
	private static final String ERROR = "Error";
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConverterUtil.class);

	private JsonConverterUtil() {	}

	public static <T> String convertToJsonWithoutNull(T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			LOGGER.debug(ERROR, e);
		}

		return null;
	}

	public static <T> String convertToJsonWithNull(T object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			LOGGER.debug(ERROR, e);
		}

		return null;
	}

	public static <T> T convertToObject(Class<T> clazz, String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			LOGGER.debug(ERROR, e);
		}

		return null;
	}
}
