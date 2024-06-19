<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div id = "holisticTableViews" class="grid slip--full ui-resizable slip--open" style="display:inherit" >
	<div id="accordion-section-client-details_funds_in"
			class="slip__content">

			<h3>Transaction View - Funds In</h3>
			

			<div class="boxpanel--micro space-after">

				<div class="boxpanel--shadow" >

					<div class="grid">

						<div class="grid__row">

							<div class="grid__col--6">
								<dl class="split-list">
								
									<dt>FI Name</dt>
									<dd id="funds_in_name">---</dd>

									<dt>Electronic transfer</dt>
									<dd class="wordwrap" id="funds_in_electronic_transfer">---</dd>

									<dt>Sell Currency</dt>
									<dd id="funds_in_sell_currency">${holisticAccountResponse.paymentInSummary.sellCurrency == "" || holisticAccountResponse.paymentInSummary.sellCurrency == null ? '--' : holisticAccountResponse.paymentInSummary.sellCurrency}</dd>

									<dt>DD set up date</dt>
									<dd id="funds_in_dd_setup_date">---</dd>
									
									<dt>Third party flag</dt>
									<dd id="funds_in_third_party_flag">${holisticAccountResponse.paymentInSummary.thirdPartyFlag == null ? '--' : holisticAccountResponse.paymentInSummary.thirdPartyFlag}</dd>

									<dt>Account number</dt>
									<dd id="funds_in_account_number">${holisticAccountResponse.paymentInSummary.debtorAccountNumber == "" || holisticAccountResponse.paymentInSummary.debtorAccountNumber == null ? '--' : holisticAccountResponse.paymentInSummary.debtorAccountNumber}</dd>

									<dt>DC Card Name on card</dt>
									<dd id="funds_in_dc_card_name">${holisticAccountResponse.paymentInSummary.debtorName == "" || holisticAccountResponse.paymentInSummary.debtorName == null ? '--' : holisticAccountResponse.paymentInSummary.debtorName}</dd>

									<dt>Start/Expiry</dt>
									<dd id="card_startandEnd_date">---</dd>

									<dt>Last changed date</dt>
									<dd id="funds_in_last_changed_date">---</dd>
									
								</dl>
							</div>

							<div class="grid__col--6 grid__col--pad-left">
								<dl class="split-list">
								
									<dt>Added by</dt>
									<dd id="card_added_by">---</dd>

									<dt>Country</dt>
									<dd id="funds_in_country">${holisticAccountResponse.paymentInSummary.countryOfFund == "" || holisticAccountResponse.paymentInSummary.countryOfFund == null ? '--' : holisticAccountResponse.paymentInSummary.countryOfFund}</dd>

									<dt>Card Type</dt>
									<dd id="funds_in_card_type">---</dd>

									<dt>Tokenised</dt>
									<dd id="card_tokenised">---</dd>
									
									<dt>Deleted</dt>
									<dd id="funds_in_deleted">${holisticAccountResponse.paymentInSummary.isDeleted == null ? '--' : holisticAccountResponse.paymentInSummary.isDeleted}</dd>

									<dt>Deleted date</dt>
									<dd id="funds_in_deleted_date">${holisticAccountResponse.paymentInSummary.deletedDate == "" || holisticAccountResponse.paymentInSummary.deletedDate == null ? '--' : holisticAccountResponse.paymentInSummary.deletedDate}</dd>
									
									<dt>DD amount</dt>
									<dd id="funds_in_dd_amount">---</dd>
								
									<dt>Collection date</dt>
									<dd id="funds_in_collection_date">---</dd>
									
									<dt>Modified by</dt>
									<dd id="funds_in_modified_by">---</dd>
									
								</dl>
							</div>

						</div>

					</div>

				</div>

			</div>

	</div>
	
	
</div>
	