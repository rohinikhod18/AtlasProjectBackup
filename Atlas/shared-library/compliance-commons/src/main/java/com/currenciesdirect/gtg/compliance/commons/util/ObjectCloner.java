package com.currenciesdirect.gtg.compliance.commons.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectCloner {
	private static final String ERROR_IN_DEEP_COPY = "error in deepCopy";
	private static final Logger LOG = LoggerFactory.getLogger(ObjectCloner.class);

	private ObjectCloner() {
	}

	public static Object deepCopy(Object oldObj)  {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
			oos = new ObjectOutputStream(bos);
			oos.writeObject(oldObj);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin);
			return ois.readObject();
		} catch (Exception e) {
			LOG.error(ERROR_IN_DEEP_COPY,e);
		} finally {
			if(oos!=null)
				try {
					oos.close();
				} catch (IOException e) {
					LOG.error(ERROR_IN_DEEP_COPY,e);
				}
			if(ois!=null)
				try {
					ois.close();
				} catch (IOException e) {
					LOG.error(ERROR_IN_DEEP_COPY,e);
				}
		}
		return null;
	}
	
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
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				copyValues(toObject, fromObject, pd);
			}
		} catch (IntrospectionException e) {
			LOG.warn("copyNullFields", e);
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
				if (pd.getReadMethod().invoke(toObject) == null) {
					pd.getWriteMethod().invoke(toObject, pd.getReadMethod().invoke(fromObject));
				}
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warn("copyValues", e);
		} catch (Exception ex) {
			LOG.warn("copyValues", ex);
		}
	}
}