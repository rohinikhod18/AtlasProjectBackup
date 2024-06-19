<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div id = "holisticTableViewsFundsOut" class="grid slip--full ui-resizable slip--open" >
<div id="accordion-section-client-details_funds_out"
			class="slip__content">

		<h3>Transaction View - Funds Out</h3>

		<div class="boxpanel--micro space-after">

			<div class="boxpanel--shadow" >

				<div class="grid" >

					<div class="grid__row" >

						<div class="grid__col--6" >
							<dl class="split-list">
							
								<dt>OPI Name</dt>
								<dd class="wordwrap" id="opi_name">${holisticAccountResponse.paymentOutSummary.beneficiaryName == "" || holisticAccountResponse.paymentOutSummary.beneficiaryName == null ? '--' : holisticAccountResponse.paymentOutSummary.beneficiaryName}</dd>
								
								<dt>Bank Name</dt>
								<dd class="wordwrap" id="bene_bank_name">${holisticAccountResponse.paymentOutSummary.bankName == "" || holisticAccountResponse.paymentOutSummary.bankName == null ? '--' : holisticAccountResponse.paymentOutSummary.bankName}</dd>

								<dt>Beneficiary account number</dt>
								<dd class="wordwrap" id="beneficiary_account_number">${holisticAccountResponse.paymentOutSummary.beneficiaryAccountNumber == "" || holisticAccountResponse.paymentOutSummary.beneficiaryAccountNumber == null ? '--' : holisticAccountResponse.paymentOutSummary.beneficiaryAccountNumber}</dd>

								<dt>Country</dt>
								<dd id="beneficiary_country">${holisticAccountResponse.paymentOutSummary.countryOfBeneficiary == "" || holisticAccountResponse.paymentOutSummary.countryOfBeneficiary == null ? '--' : holisticAccountResponse.paymentOutSummary.countryOfBeneficiary}</dd>

								<dt>Currency</dt>
								<dd id="buy_currency">${holisticAccountResponse.paymentOutSummary.buyCurrency == "" || holisticAccountResponse.paymentOutSummary.buyCurrency == null ? '--' : holisticAccountResponse.paymentOutSummary.buyCurrency}</dd>
								
								<dt>Last Updated date</dt>
								<dd id="last_updated_date">---</dd>

								<dt>Updated by</dt>
								<dd id="updated_by">---</dd>

							</dl>
						</div>

						<div class="grid__col--6 grid__col--pad-left" >
							<dl class="split-list">
								
								<dt>Status (Deleted)</dt>
								<dd id="funds_out_is_deleted">${holisticAccountResponse.paymentOutSummary.isDeleted == null ? '--' : holisticAccountResponse.paymentOutSummary.isDeleted}</dd>
								
								<dt>OPI reference</dt>
								<dd id="bene_reference">${holisticAccountResponse.paymentOutSummary.paymentReference == "" || holisticAccountResponse.paymentOutSummary.paymentReference == null ? '--' : holisticAccountResponse.paymentOutSummary.paymentReference}</dd>

								<dt>OPI phone number</dt>
								<dd id="bene_phone_number">${holisticAccountResponse.paymentOutSummary.phone == "" || holisticAccountResponse.paymentOutSummary.phone == null ? '--' : holisticAccountResponse.paymentOutSummary.phone}</dd>

								<dt>OPI email</dt>
								<dd id="bene_email">${holisticAccountResponse.paymentOutSummary.beneficiaryEmail == "" || holisticAccountResponse.paymentOutSummary.beneficiaryEmail == null ? '--' : holisticAccountResponse.paymentOutSummary.beneficiaryEmail}</dd>
								
								<dt>OPI address</dt>
								<dd id="bene_address">---</dd>

								<dt>Individual or company marker</dt>
								<dd id="bene_type">${holisticAccountResponse.paymentOutSummary.beneficiaryType == "" || holisticAccountResponse.paymentOutSummary.beneficiaryType == null ? '--' : holisticAccountResponse.paymentOutSummary.beneficiaryType}</dd>
								
							</dl>
						</div>

					</div>

				</div>

			</div>

		</div>

	</div>
</div>