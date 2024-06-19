package com.currenciesdirect.gtg.compliance.commons.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The Class DoubleSerializer.
 */
public class DoubleSerializer extends JsonSerializer<Double> {

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		BigDecimal d = BigDecimal.valueOf(value);
        gen.writeNumber(d.toPlainString());		
	}

}
