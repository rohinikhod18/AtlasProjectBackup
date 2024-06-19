package com.currenciesdirect.gtg.compliance.transactionmonitoring.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ObjectUtils.
 */
public class ObjectUtils {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);
	
	/** The Constant ERROR. */
	private static final String ERROR = "Error";

	/**
	 * Instantiates a new object utils.
	 */
	private ObjectUtils() {

	}

	/**
	 * Sets the null fields to default.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String setNullFieldsToDefault(Object object) {
		List<String> fieldsModified = new ArrayList<>();
		try {

			Class<?> current = object.getClass();

			do {
				Field[] fieldsuper = current.getDeclaredFields();
				for (Field field : fieldsuper) {
					setNullStringFieldToEmpty(object, field);
				}
				current = current.getSuperclass();
			} while (current != null);

		} catch (Exception e) {
			LOGGER.trace(ERROR, e);
		}
		return fieldsModified.toString();
	}

	/**
	 * Sets the null string field to empty.
	 *
	 * @param object the object
	 * @param field the field
	 */
	@SuppressWarnings("squid:S3011")
	private static void setNullStringFieldToEmpty(Object object, Field field) {

		try {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(String.class) && field.get(object) == null) {
				field.set(object, "");

			} else if (field.getType().isAssignableFrom(Integer.class) && field.get(object) == null) {
				field.set(object, 0);
			} else if (field.getType().isAssignableFrom(Float.class) && field.get(object) == null) {
				field.set(object, 0F);
			} else if (field.getType().isAssignableFrom(Boolean.class) && field.get(object) == null) {
				field.set(object, Boolean.FALSE);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.trace(ERROR, e);
		}
	}

	/**
	 * Replace empty with null.
	 *
	 * @param <T>    the generic type
	 * @param object the object
	 * @param clazz  the clazz
	 * @return the t
	 */
	@SuppressWarnings("squid:S3011")
	public static <T> T replaceEmptyWithNull(T object, Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String objStr;
		Object value;
		Object type;
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				type = field.getType();
				if (String.class.equals(type)) {
					value = field.get(object);
					objStr = (String) value;
					if (value == null || "".equals(objStr)) {
						field.set(object, null);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOGGER.trace(ERROR, e);
			}
		}
		return object;
	}
}
