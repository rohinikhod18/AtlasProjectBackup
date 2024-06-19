package com.currenciesdirect.gtg.compliance.customchecks.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customchecks.core.DocumentType;
import com.currenciesdirect.gtg.compliance.customchecks.domain.fundsin.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.fundsout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.ESDocument;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;

public class CustomPropertyTypeDeserializer extends AsPropertyTypeDeserializer {
	private static final Logger LOG = LoggerFactory.getLogger(CustomPropertyTypeDeserializer.class);
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPropertyTypeDeserializer(
            final JavaType bt, final TypeIdResolver idRes,
            final String typePropertyName, final boolean typeIdVisible, final JavaType defaultImpl) { //Changes done for AT-4415
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public CustomPropertyTypeDeserializer(
            final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(
            final BeanProperty prop) {
        return (prop == _property) ? this : new CustomPropertyTypeDeserializer(this, prop);
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object deserializeTypedFromObject(
            final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.readValueAsTree();
        Class<?> subType = findSubType(node);
        JavaType type = SimpleType.construct(subType);
        
        Object result =null;
        try (JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec())){
	        if (jsonParser.getCurrentToken() == null) {
	            jsonParser.nextToken();
	        }
	        
	        /* 16-Dec-2010, tatu: Since nominal type we get here has no (generic) type parameters,
	        *   we actually now need to explicitly narrow from base type (which may have parameterization)
	        *   using raw type.
	        *
	        *   One complication, though; can not change 'type class' (simple type to container); otherwise
	        *   we may try to narrow a SimpleType (Object.class) into MapType (Map.class), losing actual
	        *   type in process (getting SimpleType of Map.class which will not work as expected)
	        */
	        if (_baseType != null && _baseType.getClass() == type.getClass()) {
	            type = _baseType.forcedNarrowBy(type.getRawClass());
	        }
	        JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
	        result = deser.deserialize(jsonParser, ctxt);
        }catch (Exception ex){
        	LOG.warn("Error reading JSON", ex);
        }
        
        return result;
    }

    protected Class<?> findSubType(JsonNode node) {
        Class<? extends ESDocument> subType = null;
        String event = node.get("type").asText();
        
        if (DocumentType.FUNDS_OUT_ADD.name().equals(event)
        		|| DocumentType.FUNDS_OUT_UPDATE.name().equals(event)
        		|| DocumentType.FUNDS_OUT_DELETE.name().equals(event)
        		|| DocumentType.FUNDS_OUT_REPEAT.name().equals(event)) {
            subType = FundsOutRequest.class;
        
        }else if (DocumentType.FUNDS_IN_ADD.name().equals(event)) {
            subType = FundsInCreateRequest.class;
        
        }
        
        return subType;
    }
}