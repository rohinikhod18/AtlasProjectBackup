package com.currenciesdirect.gtg.compliance.customchecks.util;

import java.util.Collection;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

public class DocumentTypeResolver  extends StdTypeResolverBuilder {
	 @Override
	    public TypeDeserializer buildTypeDeserializer(
	            final DeserializationConfig config, final JavaType baseType, final Collection<NamedType> subtypes) {
	        return new CustomPropertyTypeDeserializer(baseType, null,
	                _typeProperty, _typeIdVisible, null); // Changes for AT-4415
	    }
}
