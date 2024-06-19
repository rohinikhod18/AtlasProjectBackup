package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		BigDecimal d = BigDecimal.valueOf(value);
        gen.writeNumber(d.toPlainString());		
	}

}
