package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

/**
 * The Class WhitelistBeneficiaryResponse.
 */
public class WhitelistBeneficiaryResponse {
	
	
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The created by. */
		private String firstName;
		
		/** The created by. */
		private String accountNumber;

		/** The created on. */
		private String createdOn;
		
		/** The notes. */
		private String notes;
		
		/** The status. */
		private String status;
		
		/** The error code. */
		protected String errorCode;

		/** The error description. */
		protected String errorDescription;

		/**
		 * Gets the first name.
		 *
		 * @return the first name
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * Sets the first name.
		 *
		 * @param firstName the new first name
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * Gets the account number.
		 *
		 * @return the account number
		 */
		public String getAccountNumber() {
			return accountNumber;
		}

		/**
		 * Sets the account number.
		 *
		 * @param accountNumber the new account number
		 */
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}


		/**
		 * Gets the created on.
		 *
		 * @return the created on
		 */
		public String getCreatedOn() {
			return createdOn;
		}

		/**
		 * Sets the created on.
		 *
		 * @param createdOn the new created on
		 */
		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}

		/**
		 * Gets the notes.
		 *
		 * @return the notes
		 */
		public String getNotes() {
			return notes;
		}

		/**
		 * Sets the notes.
		 *
		 * @param notes the new notes
		 */
		public void setNotes(String notes) {
			this.notes = notes;
		}

		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * Sets the status.
		 *
		 * @param status the new status
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * Gets the error code.
		 *
		 * @return the error code
		 */
		public String getErrorCode() {
			return errorCode;
		}

		/**
		 * Sets the error code.
		 *
		 * @param errorCode the new error code
		 */
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		/**
		 * Gets the error description.
		 *
		 * @return the error description
		 */
		public String getErrorDescription() {
			return errorDescription;
		}

		/**
		 * Sets the error description.
		 *
		 * @param errorDescription the new error description
		 */
		public void setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
		}

}
