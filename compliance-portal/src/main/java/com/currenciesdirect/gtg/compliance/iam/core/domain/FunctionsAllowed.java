package com.currenciesdirect.gtg.compliance.iam.core.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface FunctionsAllowed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FunctionsAllowed {

   /**
    * Functions.
    *
    * @return the string[]
    */
   String[] functions() default "";
   
   /**
    * Check all.
    *
    * @return true, if successful
    */
   boolean allRequired() default true;
}
