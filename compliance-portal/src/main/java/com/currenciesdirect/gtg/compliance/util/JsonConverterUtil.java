package com.currenciesdirect.gtg.compliance.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class JsonConverterUtil.
 */
public class JsonConverterUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonConverterUtil.class);

	private JsonConverterUtil() {

	}

	/**
	 * Convert to json without null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static <T> String convertToJsonWithoutNull(T object) {
		try {
			if (object != null) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				return mapper.writeValueAsString(object);
			}
		} catch (IOException e) {
			log.error("Exception:JsonConverterUtil.convertToJsonWithoutNull", e);
		}

		return null;
	}

	/**
	 * Convert to json with null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static <T> String convertToJsonWithNull(T object) {
		try {
			if (object != null) {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(object);
			}

		} catch (IOException e) {
			log.error("Exception:JsonConverterUtil.convertToJsonWithNull", e);
		}

		return null;
	}

	/**
	 * Convert to object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param json
	 *            the json
	 * @return the t
	 */
	public static <T> T convertToObject(Class<T> clazz, String json) {
		try {
			if (json != null) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return mapper.readValue(json, clazz);
			}
		} catch (IOException e) {
			log.error("Exception:JsonConverterUtil.convertToObject", e);

		}

		return null;
	}

}
