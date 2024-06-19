<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<div id="model-fxticket" class="modal--hidden modal--large wallet-modal slip--full slip--open ui-resizable ui-resizable-disabled">
	<c:set var="wallets" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.fxDetails.wallet}"></c:set>
	<c:set var="tradeDetails" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.fxDetails.tradeDetails}"></c:set>
	<c:set var="account" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.account}"></c:set>
	<c:set var="mainCustomerInstruction" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction}"></c:set>
	<c:set var="contactDetail" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.account.contactList[0].contactDetail}"></c:set>
	<c:set var="forwardTradeDetails" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.forwardTradeDetails}"></c:set>
	<c:set var="fxDetails" value="${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.fxDetails}"></c:set> 

	<c:set var="defaultSuccessResponseCode" value="000"></c:set>
	<c:set var="defaultFailureResponseCode" value="999"></c:set>

	<h1>
		FX ticket for client #${account.accountNumber}

	</h1>

		<div id="fxticketaccordian" >

	<c:if test="${fxTicketDetailWrapper.responseCode ne defaultSuccessResponseCode}">
			<div class="message--negative">
				<div class="copy">
					<p>${fxTicketDetailWrapper.responseDescription}</p>
				</div>
			</div>
	</c:if>
			<p>
			Wallet
			<c:choose>
				<c:when test="${wallets.availableBalance ge 0 }">
					<span class="indicator--positive"
						data-ot="Available wallet balance">${wallets.availableBalance}</span>
				</c:when>
				<c:otherwise>
					<span class="indicator--negative"
						data-ot="Available wallet balance">${wallets.availableBalance}</span>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${wallets.totalBalance ge 0 }">

					<span class="indicator--positive" data-ot="Total wallet balance">${wallets.totalBalance}</span>
				</c:when>
				<c:otherwise>
					<span class="indicator--negative" data-ot="Total wallet balance">${wallets.totalBalance}</span>
				</c:otherwise>
			</c:choose>
			</p>

			<section class="main-content__section">

				<div class="boxpanel--shadow">

					<div class="grid">

						<div class="grid__row">

							<div class="grid__col--6">

								<dl class="split-list">
									
									<dt>Creation date</dt>
									<dd id="dd-booking-date" class="visibleField">${tradeDetails.bookingDate}</dd>

									<dt>Client name</dt>
        							<dd class="visibleField">${account.accountName}</dd>

									<dt>Value date</dt>
									<dd id="dd-value-date" class="visibleField">${tradeDetails.valueDate}</dd>
							
									<c:choose>
										<c:when test="${empty forwardTradeDetails.openDate}">
											<dt>Open date</dt>
											<dd id="dd-open-date" class="visibleField">-</dd>
										</c:when>
										<c:otherwise>
											<dt>Open date</dt>
											<dd id="dd-open-date" class="visibleField">${forwardTradeDetails.openDate}</dd>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${empty forwardTradeDetails.maturityDate}">
											<dt>Maturity date</dt>
											<dd id="dd-maturity-date" class="visibleField">-</dd>
										</c:when>
										<c:otherwise>
											<dt>Maturity date</dt>
											<dd id="dd-maturity-date" class="visibleField">${forwardTradeDetails.maturityDate}</dd>
										</c:otherwise>
									</c:choose>
									
									<dt>Live rate</dt>
									<dd id="live-rate" class="visibleField rateField">${tradeDetails.liveRate}</dd>

                                    <!-- ADDED as per Jira AT-2486 -->
                                    <c:choose>
                                       <c:when test="${fxDetails.sourceOfFunds == null}">
                                           <dt>Source of Funds</dt>
                                           <dd class="visibleField">-</dd>
                                       </c:when>
                                       <c:otherwise>
                                           <dt>Source of Funds</dt>
                                           <dd class="visibleField">${fxDetails.sourceOfFunds}</dd>
                                       </c:otherwise>
                                    </c:choose>
                                    
								</dl>

							</div>

							<div class="grid__col--6 grid__col--pad-left">

								<dl class="split-list">

									<dt>Agreed rate / Suggested rate</dt>
									<dd ><span id="agreed-rate" class=" visibleField rateField">${tradeDetails.agreedRate}</span > / <span id="suggested-rate" class=" visibleField rateField">${tradeDetails.systemRate}</span></dd>

									<dt>Buy currency</dt>
									<dd id="txt-buy-currency-dd" class="visibleField" data-val="${tradeDetails.buyingCurrency}">${tradeDetails.buyingCurrency}</dd>

									<dt>Sell currency</dt>
									<dd id="txt-sell-currency-dd" class="visibleField" data-val="${tradeDetails.sellingCurrency}">${tradeDetails.sellingCurrency}</dd>

									<dt>Buy amount</dt>
									<dd id="txt-buy-currency-amt-dd" class="visibleField amountField" data-val="${tradeDetails.buyingAmount}">${tradeDetails.buyingAmount}</dd>

									<dt>Sell amount</dt>
									<dd id="txt-sell-currency-amt-dd" class="visibleField amountField" data-val="${tradeDetails.sellingAmount}">${tradeDetails.sellingAmount}</dd>

									<dt>Profit</dt>
									<dd id="profit" class="visibleField amountField">${tradeDetails.netProfit}</dd>
									
									<dt>Deal Type</dt>
									<dd id="deal-type-dd" class="visibleField" data-val="${mainCustomerInstruction.dealType}">${mainCustomerInstruction.dealType}</dd>
					
								</dl>

							</div>

						</div>

					</div>

				</div>

			</section>
			<!--  <section class="main-content__section">

					<div>
						<h2>Further client details</h2>
					</div>

					<div class="boxpanel--shadow">

						<div class="grid">

							<div class="grid__row">

								<div class="grid__col--6">
									<dl class="split-list">

										<dt>Contact Name</dt>
										<dd class="visibleField">${contactDetail.firstName} ${contactDetail.middleName} ${contactDetail.lastName}</dd>
												
										<dt>Job title</dt>
										<dd class="visibleField">${contactDetail.jobTitle}</dd>

										<dt>Phone</dt>
										<dd class="visibleField">${contactDetail.homePhone}</dd>

										<dt>Mobile</dt>
										<dd class="visibleField">${contactDetail.mobilePhone}</dd>
										
										<dt>Email</dt>
										<dd class="visibleField">${contactDetail.emailAddress}</dd>

									</dl>
								</div>

								<div class="grid__col--6 grid__col--pad-left">
									<dl class="split-list">
												
									<dt>Country of nationality</dt>
									<dd class="visibleField">${contactDetail.countryOfNationality}</dd>

									<dt>Country of residence</dt>
									<dd class="visibleField">${contactDetail.countryOfResidency}</dd>

									<dt>Authorised signatory</dt>
									<dd class="visibleField">${fxTicketDetailWrapper.fxTicketDetailList[0].mainCustomerInstruction.account.contactList[0].isAuthorisedSignatory}</dd>

								<%-- <dt>Contact Status</dt>
									<dd class="visibleField">${contact.contactStatus}</dd> --%>

								</dl>
							</div>

						</div>

					</div>

				</div>
			</section>-->
			
			</div>
		</div>
