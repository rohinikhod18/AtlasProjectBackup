package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
}