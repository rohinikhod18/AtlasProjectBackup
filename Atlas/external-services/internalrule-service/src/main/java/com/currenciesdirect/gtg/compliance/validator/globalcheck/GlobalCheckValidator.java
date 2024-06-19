package com.currenciesdirect.gtg.compliance.validator.globalcheck;

import javax.validation.ValidationException;

import com.currenciesdirect.gtg.compliance.core.IGlobalCheckValidator;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckException;

/**
 * The Class GlobalCheckValidator.
 */
public class GlobalCheckValidator implements IGlobalCheckValidator {
	
		/** The validator. */
		private static IGlobalCheckValidator validator = new GlobalCheckValidator();

		/**
		 * Instantiates a new global check validator.
		 */
		private GlobalCheckValidator() {
		}

		/**
		 * Gets the single instance of GlobalCheckValidator.
		 *
		 * @return single instance of GlobalCheckValidator
		 */
		public static IGlobalCheckValidator getInstance() {
				synchronized (GlobalCheckValidator.class) {
					if (validator == null) {
						validator = new GlobalCheckValidator();
					}
				}
			return validator;
		}

		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.core.IGlobalCheckValidator#globalCheckValidator(com.currenciesdirect.gtg.compliance.core.domain.InternalServiceRequestData)
		 */
		@Override
		public Boolean globalCheckValidator(InternalServiceRequestData request) {
			Boolean result= Boolean.FALSE;
			try {

				if(request.getState() !=null && !request.getState().isEmpty()){
					result= Boolean.TRUE;
					return result;
			}
			} catch (Exception exception) {
				throw new ValidationException(new GlobalCheckException(GlobalCheckErrors.APPLY_RULE_FAILED,exception));
			}
			result= Boolean.FALSE;
			return result;
		}

	}
