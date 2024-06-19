package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class UpdateRequestGenerator.
 * 
 * @author Rajesh Shinde
 */
public class UpdateRequestGenerator {
	static final Logger LOG = LoggerFactory
			.getLogger(UpdateRequestGenerator.class);
	Map<String, String> inlineMap = new HashMap<>();
	Map<String, String> paramMap = new HashMap<>();

	/**
	 * Gets the elastic search update request.
	 * 
	 * @param document
	 *            the document
	 * @return the elastic search update request
	 */
	public String getElasticSearchUpdateRequest(DBModel document) {
		LOG.debug("UpdateRequestGenerator.getElasticSearchUpdateRequest() :start");
		UpdateQueryBuilder builder = new UpdateQueryBuilder();
		builder.addScript();
		String className = document.getClass().getName();
		Class<?> cls;
		try {
			cls = Class.forName(className);
			Field[] fieldlist = cls.getDeclaredFields();
			for (Field field : fieldlist) {
				field.setAccessible(true);
					if (field.get(document) != null
							&& field.getType() != String.class) {
						addObjectIntoMap(field.get(document), field.getName());
					} else if (addIntoParamMap(field.getName(),
							(String) field.get(document))) {
						addIntoInlineMap(field.getName());
					}
			}
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			LOG.error("Error while building update query", e);
		}
		if (inlineMap.size() > 0) {
			builder.addInline(inlineMap);
			builder.addParams(paramMap);
		}
		return builder.build();
	}

	private boolean addIntoMap(String field, String value,
			Map<String, String> map) {
		if (field != null && value != null && !value.isEmpty()) {
			map.put(field, value);
			return true;
		}
		return false;
	}

	/**
	 * Adds the into inline map.
	 * 
	 * @param field
	 *            the field
	 */
	private void addIntoInlineMap(String field) {
		addIntoMap(field, field, inlineMap);
	}

	/**
	 * Adds the into param map.
	 * 
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	private boolean addIntoParamMap(String field, String value) {
		return addIntoMap(field, value, paramMap);
	}

	/**
	 * Adds the object whose all field are String.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object (all fields type must be String)
	 * @param fieldName
	 *            the field name
	 * @return true, if successful
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	private <T extends Object> boolean addObjectIntoMap(T object,
			String fieldName) throws ClassNotFoundException,
			 IllegalAccessException {
		String className = object.getClass().getName();
		Class<?> cls = Class.forName(className);
		Field[] fieldlist = cls.getDeclaredFields();
		for (Field field : fieldlist) {
			field.setAccessible(true);
			if (addIntoParamMap(field.getName(), (String) field.get(object))) {
				addIntoMap(fieldName + "." + field.getName(), field.getName(),
						inlineMap);
			}
		}
		return true;

	}

}
