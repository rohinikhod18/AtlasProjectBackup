package com.currenciesdirect.gtg.compliance.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);
	private static final String ERROR = "Error";

	protected ObjectUtils() {

	}

	/**
	 * @param object
	 * @return
	 */
	public static Boolean isAllFieldsEmpty(Object object) {
		boolean result = false;
		try {

			Class<?> current = object.getClass();

			do {
				Field[] fieldsuper = current.getDeclaredFields();
				result = checkFieldIsEmpty(object, fieldsuper);
				if (result) {
					return result;
				}

				current = current.getSuperclass();
			} while (current != null);

		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return result;

	}

	private static boolean checkFieldIsEmpty(Object object, Field[] fieldsuper) {
		boolean result = false;
		for (Field field : fieldsuper) {
			if (isFieldsEmpty(object, field)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param object
	 * @param field
	 * @return
	 */
	@SuppressWarnings("squid:S3011")
	public static boolean isFieldsEmpty(Object object, Field field) {

		try {
			field.setAccessible(true);
			if (field.get(object) != null) {
				return false;

			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.trace(ERROR, e);
		}
		return true;
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isObjectNull(Object object) {

		return (null == object);
	}
}
