package com.currenciesdirect.gtg.compliance.commons.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface FieldDisplayName.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldDisplayName {
	
	/**
	 * Display name.
	 *
	 * @return the string
	 */
	String displayName() default "";

}
