package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CopyObjectUtil.
 */
public class CopyObjectUtil {
	
	/** The Constant JAVA_LANG_STRING. */
	public static final String JAVA_LANG_STRING = "java.lang.String";
	
	/** The Constant JAVA_LANG_BOOLEAN. */
	public static final String JAVA_LANG_BOOLEAN = "java.lang.boolean";

	/** The Constant ERROR. */
	private static final String ERROR = "Error ";
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CopyObjectUtil.class);
	
	/** The Constant SPEC_CHAR_REG_EXP. */
	private static final String SPEC_CHAR_REG_EXP = "[^\\p{L}^\\p{Nd}]+";

	/**
	 * Instantiates a new copy object util.
	 */
	private CopyObjectUtil (){}
	
	/**
	 * Copy null fields.
	 *
	 * @param <T> the generic type
	 * @param toObject the to object
	 * @param fromObject the from object
	 * @param clazz the clazz
	 * @return the t
	 */
	public static <T> T copyNullFields(T toObject, T fromObject, Class<T> clazz) {
		if(null == fromObject)
			return toObject;
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				copyValues(toObject, fromObject, pd);
			}
		} catch (IntrospectionException e) {
			LOGGER.trace(ERROR, e);
		}
		return toObject;
	}

	/**
	 * Copy values.
	 *
	 * @param <T> the generic type
	 * @param toObject the to object
	 * @param fromObject the from object
	 * @param pd the pd
	 */
	private static <T> void copyValues(T toObject, T fromObject, PropertyDescriptor pd) {
		try {
			 
			if (!"isContactRegistered".equals(pd.getName())) {
				if (pd.getWriteMethod() == null || pd.getReadMethod() == null)
					return;
				Object  fieldValue = pd.getReadMethod().invoke(toObject);
				if (fieldValue == null) {
					pd.getWriteMethod().invoke(toObject, pd.getReadMethod().invoke(fromObject));
				}
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.warn(ERROR, e);
		} catch (Exception ex) {
			LOGGER.warn(ERROR, ex);
		}
	}
	
	/**
	 * Compare fields.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973") //change to use equals is causing other issue
	public static String compareFields(Object oldVersion, Object newVersion) {
		List<String> fieldsModified = new ArrayList<>();

		if ((null == oldVersion || null == newVersion) || (oldVersion.getClass().getName() != newVersion.getClass().getName()))
			return null;
		Field[] oldVersionFields = oldVersion.getClass().getDeclaredFields();
		for (Field oldField : oldVersionFields) {
			compareFieldValues(oldVersion, newVersion, fieldsModified, oldField);
		}
		return fieldsModified.toString();
	}

	/**
	 * Compare field values.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 */
	@SuppressWarnings("squid:S3011")
	private static void compareFieldValues(Object oldVersion, Object newVersion, List<String> fieldsModified,
			Field oldField) {
		Field newField;
		try {
			newField = newVersion.getClass().getDeclaredField(oldField.getName());
			oldField.setAccessible(true);
			newField.setAccessible(true);
			if (newField.getType().getName().contains("java")) {
				if(JAVA_LANG_STRING.equalsIgnoreCase(newField.getType().getName())){
						String newValue = (String) newField.get(newVersion);
						String oldValue = (String) oldField.get(oldVersion);
						addModifiedFieldCaseSensitive(fieldsModified, newField, newValue, oldValue);
				} else if(newField.get(newVersion) != null && (oldField.get(oldVersion) == null || oldField.get(oldVersion) != null) && 
						(!newField.get(newVersion).equals(oldField.get(oldVersion)))){
					fieldsModified.add(newField.getName());
				}
			} 
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.warn(ERROR, e);
		}
	}
	
	/**
	 * Check for only case change.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 */
	private static void checkForOnlyCaseChange(List<String> fieldsModified, Field newField, String newValue,
			String oldValue) {
		Boolean oldValueNotEmptyAndNewValueNotEmpty = (null != oldValue && !oldValue.isEmpty()) && (newValue != null && !newValue.isEmpty());
		if(oldValueNotEmptyAndNewValueNotEmpty && checkIsOnlyCaseChanged(newValue, oldValue)) {
				fieldsModified.add(newField.getName()+",");
		}
	}

	/**
	 * Compare fields case insensitive.
	 *
	 * @param newVersion the new version
	 * @param oldVersion the old version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973")
	public static String compareFieldsCaseInsensitive(Object newVersion,Object oldVersion) {
		List<String> fieldsModified = new ArrayList<>();

		if ((null == oldVersion || null == newVersion) || (oldVersion.getClass().getName() != newVersion.getClass().getName()))
			return null;
		Field[] oldVersionFields = oldVersion.getClass().getDeclaredFields();
		for (Field oldField : oldVersionFields) {
			compareFieldValueCaseInsensitive(oldVersion, newVersion, fieldsModified, oldField,false);
		}
		fieldsModified.add("");
		return fieldsModified.toString();
	}

	/**
	 * Compare field value case insensitive.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 * @param removeSpecialCharacters the remove special characters
	 */
	@SuppressWarnings("squid:S3011")
	private static void compareFieldValueCaseInsensitive(Object oldVersion, Object newVersion, List<String> fieldsModified,
			Field oldField,boolean removeSpecialCharacters) {
		Field newField;
		try {
			newField = newVersion.getClass().getDeclaredField(oldField.getName());
			oldField.setAccessible(true);
			newField.setAccessible(true);
			
			if(oldField.getAnnotation(JsonProperty.class) == null || oldField.getType().getName().contains("java.util")){
				return;
			}
			if (newField.getType().getName().contains("java")) {
				addModifiedFieldsByRemovingSpecialChar(oldVersion, newVersion, fieldsModified, oldField,
						removeSpecialCharacters, newField);
			} 
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException 
				| IllegalAccessException |NullPointerException e) {
			LOGGER.warn(ERROR, e);
		}
	}

	/**
	 * Adds the modified fields by removing special char.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 * @param removeSpecialCharacters the remove special characters
	 * @param newField the new field
	 * @throws IllegalAccessException the illegal access exception
	 */
	private static void addModifiedFieldsByRemovingSpecialChar(Object oldVersion, Object newVersion,
			List<String> fieldsModified, Field oldField, boolean removeSpecialCharacters, Field newField)
			throws IllegalAccessException {
		if(JAVA_LANG_STRING.equalsIgnoreCase(newField.getType().getName())){
				String newValue = (String) newField.get(newVersion);
				String oldValue = (String) oldField.get(oldVersion) ;
			if (removeSpecialCharacters) {
				newValue = removeSpecialCharacters(newValue);
				oldValue = removeSpecialCharacters(oldValue);
			}
				addModifiedField(fieldsModified, newField, newValue, oldValue);
		} else if(newField.get(newVersion) != null && (oldField.get(oldVersion) == null || oldField.get(oldVersion) != null) 
				&& (!newField.get(newVersion).equals(oldField.get(oldVersion)))){
			fieldsModified.add(newField.getName());
		}
	}
	
	
	
	/**
	 * Removes the special characters.
	 *
	 * @param newValue the new value
	 * @return the string
	 */
	private static String removeSpecialCharacters(String newValue) {
		if (newValue == null || newValue.isEmpty()) {
			return newValue;
		}
		return newValue.replaceAll(SPEC_CHAR_REG_EXP, "");
	}

	/**
	 * Adds the modified field.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 */
	private static void addModifiedField(List<String> fieldsModified, Field newField, String newValue,
			String oldValue) {
		Boolean oldValueEmptyAndNewValueNotEmpty = (null == oldValue || oldValue.isEmpty()) && (newValue != null && !newValue.isEmpty());
		Boolean oldValueNotEmptyAndNewValueNotEmpty = (null != oldValue && !oldValue.isEmpty()) && (newValue != null && newValue.isEmpty());
		checkWhetherFieldsModified(fieldsModified, newField, newValue, oldValue, oldValueEmptyAndNewValueNotEmpty,
				oldValueNotEmptyAndNewValueNotEmpty); 
	}

	/**
	 * Check whether fields modified.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 * @param oldValueEmptyAndNewValueNotEmpty the old value empty and new value not empty
	 * @param oldValueNotEmptyAndNewValueNotEmpty the old value not empty and new value not empty
	 */
	private static void checkWhetherFieldsModified(List<String> fieldsModified, Field newField, String newValue,
			String oldValue, Boolean oldValueEmptyAndNewValueNotEmpty, Boolean oldValueNotEmptyAndNewValueNotEmpty) {
		Boolean oldValueNotEmptyAndNotEqualsNewValue = (oldValue != null && !oldValue.isEmpty()) && (newValue != null && !newValue.equalsIgnoreCase(oldValue));
		
		if (Boolean.TRUE.equals(oldValueEmptyAndNewValueNotEmpty) 
				|| Boolean.TRUE.equals(oldValueNotEmptyAndNewValueNotEmpty) 
				|| Boolean.TRUE.equals(oldValueNotEmptyAndNotEqualsNewValue)){
			fieldsModified.add(newField.getName()+",");
		}		
	}

	/**
	 * Check is only case changed.
	 *
	 * @param newValue the new value
	 * @param oldValue the old value
	 * @return the boolean
	 */
	private static Boolean checkIsOnlyCaseChanged(String newValue, String oldValue) {
		Boolean caseChanged = Boolean.FALSE;
		if(null != newValue && !newValue.equals(oldValue) && newValue.equalsIgnoreCase(oldValue)){
			caseChanged = Boolean.TRUE;
		}
		return caseChanged;
	}
	
	
	/**
	 * Adds the modified field case sensitive.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 */
	private static void addModifiedFieldCaseSensitive(List<String> fieldsModifiedCaseSensitive, Field newFieldCaseSensitive, String newValueCaseSensitive,
			String oldValueCaseSensitive) {
		Boolean oldValueEmptyAndNewValueNotEmpty = (null == oldValueCaseSensitive || oldValueCaseSensitive.isEmpty()) && (newValueCaseSensitive != null && !newValueCaseSensitive.isEmpty());
		Boolean oldValueNotEmptyAndNewValueNotEmpty = (null != oldValueCaseSensitive && !oldValueCaseSensitive.isEmpty()) && (newValueCaseSensitive != null && newValueCaseSensitive.isEmpty());
		checkWhetherFieldsModified(fieldsModifiedCaseSensitive, newFieldCaseSensitive, newValueCaseSensitive, oldValueCaseSensitive, oldValueEmptyAndNewValueNotEmpty,
				oldValueNotEmptyAndNewValueNotEmpty);
	}
	
	/**
	 * Removes the special charcter and compare.
	 *
	 * @param fields the fields
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973")
	public static String removeSpecialCharcterAndCompare(String[] fields,Object oldVersion, Object newVersion){
		List<String> fieldsModified = new ArrayList<>();

		if (oldVersion.getClass().getName() != newVersion.getClass().getName())
			return null;
		Field oldField; 
		for (String field : fields) {
			oldField = null;
			try {
				oldField = oldVersion.getClass().getDeclaredField(field);
				
			} catch (NoSuchFieldException |SecurityException e) {
				LOGGER.debug(ERROR, e);
			}
			if(null !=oldField)
				compareFieldValueCaseInsensitive(oldVersion, newVersion, fieldsModified, oldField,true);
		}
		fieldsModified.add("");
		return fieldsModified.toString();
	}

	/**
	 * Compare fields for case checking.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973")
	public static String compareFieldsForCaseChecking(Object oldVersion, Object newVersion) {
		List<String> fieldsModified = new ArrayList<>();
		if(null == oldVersion)
			return null;
		if (oldVersion.getClass().getName() != newVersion.getClass().getName())
			return null;
		Field[] oldVersionFields = oldVersion.getClass().getDeclaredFields();
		for (Field oldField : oldVersionFields) {
			compareFieldValuesForCaseChecking(oldVersion, newVersion, fieldsModified, oldField);
		}
		return fieldsModified.toString();
	}
	
	/**
	 * Compare field values for case checking.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 */
	@SuppressWarnings("squid:S3011")
	private static void compareFieldValuesForCaseChecking(Object oldVersion, Object newVersion, List<String> fieldsModified,
			Field oldField) {
		Field newField;
		try {
			newField = newVersion.getClass().getDeclaredField(oldField.getName());
			oldField.setAccessible(true);
			newField.setAccessible(true);
			if (newField.getType().getName().contains("java")) {
				if(JAVA_LANG_STRING.equalsIgnoreCase(newField.getType().getName())){
						String newValue = (String) newField.get(newVersion);
						String oldValue = (String) oldField.get(oldVersion);
						checkForOnlyCaseChange(fieldsModified, newField, newValue, oldValue);
				} else if(newField.get(newVersion) != null && (oldField.get(oldVersion) == null || oldField.get(oldVersion) != null) && 
						(!newField.get(newVersion).equals(oldField.get(oldVersion)))){
					fieldsModified.add(newField.getName());
				}
			} 
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.warn(ERROR, e);
		}
	}
	
	/**
	 * Compare fields for content checking.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973")
	public static String compareFieldsForContentChecking(Object oldVersion, Object newVersion) {
		List<String> fieldsModified = new ArrayList<>();

		if (oldVersion.getClass().getName() != newVersion.getClass().getName())
			return null;
		Field[] oldVersionFields = oldVersion.getClass().getDeclaredFields();
		for (Field oldField : oldVersionFields) {
			compareFieldValuesForContentChecking(oldVersion, newVersion, fieldsModified, oldField);
		}
		return fieldsModified.toString();
	}
	
	/**
	 * Compare field values for content checking.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 */
	@SuppressWarnings("squid:S3011")
	private static void compareFieldValuesForContentChecking(Object oldVersionContentChecking, Object newVersionContentChecking, List<String> fieldsModifiedContentChecking,
			Field oldFieldContentChecking) {
		Field newField;
		try {
			newField = newVersionContentChecking.getClass().getDeclaredField(oldFieldContentChecking.getName());
			oldFieldContentChecking.setAccessible(true);
			newField.setAccessible(true);
			if (newField.getType().getName().contains("java")) {
				if(JAVA_LANG_STRING.equalsIgnoreCase(newField.getType().getName())){
						String newValue = (String) newField.get(newVersionContentChecking);
						String oldValue = (String) oldFieldContentChecking.get(oldVersionContentChecking);
						addModifiedFieldCaseSensitive(fieldsModifiedContentChecking, newField, newValue, oldValue);
				} else if(newField.get(newVersionContentChecking) != null && (oldFieldContentChecking.get(oldVersionContentChecking) == null || oldFieldContentChecking.get(oldVersionContentChecking) != null) && 
						(!newField.get(newVersionContentChecking).equals(oldFieldContentChecking.get(oldVersionContentChecking)))){
					fieldsModifiedContentChecking.add(newField.getName());
				}
			} 
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.warn(ERROR, e);
		}
	}
	
	/**
	 * Compare fields for content checking activity log.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @return the string
	 */
	@SuppressWarnings("squid:S4973")
	public static String compareFieldsForContentCheckingActivityLog(Object oldVersion, Object newVersion) {
		List<String> fieldsModified = new ArrayList<>();

		if (oldVersion.getClass().getName() != newVersion.getClass().getName())
			return null;
		Field[] oldVersionFields = oldVersion.getClass().getDeclaredFields();
		for (Field oldField : oldVersionFields) {
			compareFieldValuesForContentCheckingActivityLog(oldVersion, newVersion, fieldsModified, oldField);
		}
		return fieldsModified.toString();
	}

	/**
	 * Compare field values for content checking activity log.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 */
	@SuppressWarnings("squid:S3011")
	private static void compareFieldValuesForContentCheckingActivityLog(Object oldVersion, Object newVersion,
			List<String> fieldsModified, Field oldField) {
		Field newField;
		try {
			newField = newVersion.getClass().getDeclaredField(oldField.getName());
			oldField.setAccessible(true);
			newField.setAccessible(true);
			if (newField.getType().getName().contains("java")) {
				if (JAVA_LANG_STRING.equalsIgnoreCase(newField.getType().getName())) {
					String newValue = (String) newField.get(newVersion);
					String oldValue = (String) oldField.get(oldVersion);
					addModifiedFieldCaseSensitiveActivityLog(fieldsModified, newField, newValue, oldValue);
				} else if(JAVA_LANG_BOOLEAN.equalsIgnoreCase(newField.getType().getName())){
					getFieldValues(oldVersion, newVersion, fieldsModified, oldField, newField);
					
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.warn(ERROR, e);
		}
	}

	/**
	 * Gets the field values.
	 *
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @param fieldsModified the fields modified
	 * @param oldField the old field
	 * @param newField the new field
	 * @return the field values
	 * @throws IllegalAccessException the illegal access exception
	 */
	private static void getFieldValues(Object oldVersion, Object newVersion, List<String> fieldsModified,
			Field oldField, Field newField) throws IllegalAccessException {
		Boolean newBooleanValue = (Boolean) newField.get(newVersion);
		Boolean oldBooleanValue = (Boolean) oldField.get(oldVersion);

		if (null != newBooleanValue && null != oldBooleanValue) {
			String newValue;
			String oldValue;
			if (Boolean.TRUE.equals(newBooleanValue)) {
				newValue = "true";
			} else {
				newValue = "false";
			}

			if (Boolean.TRUE.equals(oldBooleanValue)) {
				oldValue = "true";
			} else {
				oldValue = "false";
			}
			addModifiedFieldCaseSensitiveActivityLog(fieldsModified, newField, newValue, oldValue);
		}
	}

	/**
	 * Adds the modified field case sensitive activity log.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 */
	private static void addModifiedFieldCaseSensitiveActivityLog(List<String> fieldsModified, Field newField,
			String newValue, String oldValue) {
		Boolean oldValueEmptyAndNewValueNotEmpty = (null == oldValue || oldValue.isEmpty())
				&& (newValue != null && !newValue.isEmpty());
		Boolean oldValueNotEmptyAndNewValueNotEmpty = (null != oldValue && !oldValue.isEmpty())
				&& (newValue != null && newValue.isEmpty());
		checkWhetherFieldsModifiedActivityLog(fieldsModified, newField, newValue, oldValue,
				oldValueEmptyAndNewValueNotEmpty, oldValueNotEmptyAndNewValueNotEmpty);
	}

	/**
	 * Check whether fields modified activity log.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 * @param oldValueEmptyAndNewValueNotEmpty the old value empty and new value not empty
	 * @param oldValueNotEmptyAndNewValueNotEmpty the old value not empty and new value not empty
	 */
	private static void checkWhetherFieldsModifiedActivityLog(List<String> fieldsModified, Field newField,
			String newValue, String oldValue, Boolean oldValueEmptyAndNewValueNotEmpty,
			Boolean oldValueNotEmptyAndNewValueNotEmpty) {
		Boolean oldValueNotEmptyAndNotEqualsNewValue = (oldValue != null && !oldValue.isEmpty())
				&& (newValue != null && !newValue.equals(oldValue));

		if (Boolean.TRUE.equals(oldValueEmptyAndNewValueNotEmpty) 
				|| Boolean.TRUE.equals(oldValueNotEmptyAndNewValueNotEmpty)
				|| Boolean.TRUE.equals(oldValueNotEmptyAndNotEqualsNewValue)) {
			addModifiedFields(fieldsModified, newField, newValue, oldValue);
		}
	}

	/**
	 * Adds the modified fields.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 */
	private static void addModifiedFields(List<String> fieldsModified, Field newField, String newValue,
			String oldValue) {
		
		String date = DateTimeFormatter.getDateInRFC3339(newValue);

		if (!StringUtils.isNullOrTrimEmpty(date)) {
			getActivityLogList(fieldsModified, newField, date, oldValue);
		} else {
			getActivityLogList(fieldsModified, newField, newValue, oldValue);
		}
		
		
	}

	/**
	 * Gets the activity log list.
	 *
	 * @param fieldsModified the fields modified
	 * @param newField the new field
	 * @param newValue the new value
	 * @param oldValue the old value
	 * @return the activity log list
	 */
	private static void getActivityLogList(List<String> fieldsModified, Field newField, String newValue,
			String oldValue) {
		if (null != newField.getAnnotation(FieldDisplayName.class)) {
			String displayName = newField.getAnnotation(FieldDisplayName.class).displayName();

			if (null == oldValue || oldValue.isEmpty()) {
				fieldsModified.add(
						"Changed " + displayName + " to " + newValue);
			}

			if (null == newValue || newValue.isEmpty()) {
				fieldsModified.add("Removed " + displayName);
			}
			
			if(!StringUtils.isNullOrTrimEmpty(newValue) && !StringUtils.isNullOrTrimEmpty(oldValue))
			{
				fieldsModified.add("Changed " + displayName + " from "
						+ oldValue + " to " + newValue);
			}

		}
	}
}
