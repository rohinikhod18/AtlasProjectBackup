
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="utf-8" />
<title>Enterprise tools</title>
<meta name="description" content="Enterprise tools" />
<meta name="copyright" content="Currencies Direct" />

<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/popup.css">
<link rel="stylesheet" href="resources/css/jsontotable.css">
<link rel="stylesheet" href="resources/css/fxticket.css">
<link rel="stylesheet" href="resources/css/jquery-confirm.css">
</head>

<body>
	<div id="master-grid" class="grid">
		<c:choose>
			<c:when
				test="${paymentInDetails.locked  && paymentInDetails.lockedBy != paymentInDetails.user.name || paymentInDetails.locked == null || !paymentInDetails.locked}">
				<c:set var="buttonClass" value="button--primary button--disabled"></c:set>
				<c:set var="buttonDisable" value="disabled='disabled'"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="buttonClass" value="button--primary"></c:set>
				<c:set var="buttonDisable" value=""></c:set>
			</c:otherwise>
		</c:choose>
		<c:set var = "whitelistCurrency" value = "${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.currency}" />
		<c:set var = "whitelistAmountRange" value = "${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.amoutRange}" />
		<c:set var = "whitelistThirdParty" value = "${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.thirdParty}" />
		<input id ="customRepeatCheck" type="hidden" value="<c:out value="${customRepeatCheck}"/>" >
		
		<input type="hidden" id = "whitelistCurrency" value = "<c:out value = "${whitelistCurrency}"/>">
		<input type="hidden" id = "whitelistAmountRange" value = "<c:out value = "${whitelistAmountRange}"/>">
		<input type="hidden" id = "whitelistThirdParty" value = "<c:out value = "${whitelistThirdParty}"/>">
		
		<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">
				<c:choose>
					<c:when test="${paymentInDetails.source == 'queue' }">
						<c:set var="redirectUrl" value="/compliance-portal/paymentInQueue"/>
						<c:set var="redirectTo" value="Payments in queue "/>
					</c:when>
					<c:otherwise>
						<c:set var="redirectUrl" value="/compliance-portal/paymentInReport"/>
						<c:set var="redirectTo" value="Payments in report"/>
					</c:otherwise>
				</c:choose>
				
					<form id="redirectQueueForm" action="${redirectUrl }" method="post">
					<div class="grid__col--9">
						<h1>
							Transaction #<span id="payment_transNum">${paymentInDetails.paymentInInfo.transactionNumber}</span>
	
							<span class="breadcrumbs"> <span
								class="breadcrumbs__crumb--in">in</span> <span
								class="breadcrumbs__crumb--area">Compliance </span> <span class="breadcrumbs__crumb"><a
									onclick="redirectToQueue();">${redirectTo }
								</a></span>
	
							</span>
						</h1>
						<input id="ququefilter" type="hidden" name="searchCriteria" value="">
					</div>
					</form>
					
					<div class="grid__col--3">

						<div class="toast">
							<c:if test="${paymentInDetails.noOfContactsForAccount > 1}">
								<span class="message--toast rhs page-load"> <i
									class="material-icons">assignment_ind</i> <a
									 onclick="openClientDetail()" class="accordion-trigger" href="#">${paymentInDetails.noOfContactsForAccount - 1}
										more person on this account</a>
								</span>
							</c:if>
						</div>
					</div>


					<div id="main-content__body">
						<div id="main-content__body_positive"  class="message--positive" style="display: none">
							<div class="copy">
							</div>
						</div>
						  <div id="main-content__body_negative" class="message--negative" style="display: none">
							<div class="copy">
							<ul>
							</ul>
							</div>
						</div>
						<div class="grid">

							<div class="grid__row">

								<div class="grid__col--8 grid__col--pad-right">

									<section class="main-content__section">

										<h2 class="hidden">Summary</h2>

										<div class="grid">

											<div class="grid__row">

												<div class="grid__col--6">
													<%-- <c:if test="${paymentOutDetails.paymentOutInfo.status == 'HOLD'}">
														<p><span class="indicator--neutral" id="payment_complianceStatus">${paymentOutDetails.paymentOutInfo.status}</span> - Awaiting more info</p>
												</c:if> --%>

													<c:choose>
														<c:when
															test="${paymentInDetails.paymentInInfo.status == 'SEIZE'}">
															<p id="payment_status">
																<span class="indicator--negative"
																	id="payment_complianceStatus">SEIZE</span>
															</p>
														</c:when>
														<c:when
															test="${ paymentInDetails.paymentInInfo.status == 'REJECT'}">
															<p id="payment_status">
																<span class="indicator--negative"
																	id="payment_complianceStatus">${paymentInDetails.paymentInInfo.status}</span>
															</p>
														</c:when>
														<c:when
															test="${paymentInDetails.paymentInInfo.status == 'HOLD'}">
															<p id="payment_status">
																<span class="indicator--neutral"
																	id="payment_complianceStatus">${paymentInDetails.paymentInInfo.status}</span>
															</p>
														</c:when>
														<c:otherwise>
															<p id="payment_status">
																<span class="indicator--positive"
																	id="payment_complianceStatus">${paymentInDetails.paymentInInfo.status}</span>
															</p>
														</c:otherwise>
													</c:choose>



												</div>

												<div class="grid__col--6">

													<div id="lock" class="f-right">

														<!-- <img id="ajax-loader-lock-toggle" class="ajax-loader space-next" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->
														<c:choose>
														<c:when
																test="${paymentInDetails.paymentInInfo.isDeleted == false}">
														<c:choose>
															<c:when
																test="${paymentInDetails.locked  && paymentInDetails.lockedBy == paymentInDetails.user.name}">
																<span id="ownRecord"
																	class="space-next toggle-support-text"><i
																	class="material-icons">person_pin</i> You own(s) this
																	record</span>
																<div id="toggle-edit-lock" class="toggle f-right">
																	<a href="#" id="toggle-record-lock"
																		onclick="lockResource()" class="toggle__option--on "
																		data-ot="Lock this record to own it"><i
																		class="material-icons">lock_outline</i></a> <a href="#"
																		id="toggle-record-unlock" onclick="unlockResource()"
																		class="toggle__option "
																		data-ot="Unlock this record for others"><i
																		class="material-icons">lock_open</i></a>
																</div>
															</c:when>
															<c:when test="${paymentInDetails.locked}">
																<span id="ownRecord"
																	class="space-next toggle-support-text"><i
																	class="material-icons">person_pin</i>
																	${paymentInDetails.lockedBy} own(s) this record</span>
															</c:when>
															<c:otherwise>
																<div id="toggle-edit-lock" class="toggle f-right">
																	<a href="#" id="toggle-record-lock"
																		onclick="lockResource()" class="toggle__option "
																		data-ot="Lock this record to own it"><i
																		class="material-icons">lock_outline</i></a> <a href="#"
																		id="toggle-record-unlock" onclick="unlockResource()"
																		class="toggle__option--on "
																		data-ot="Unlock this record for others"><i
																		class="material-icons">lock_open</i></a>
																</div>
															</c:otherwise>
														</c:choose>
														</c:when>
														</c:choose>

													</div>

												</div>

											</div>
										</div>
										<div class="boxpanel--shadow">

											<div class="grid">

												<div class="grid__row">

													<div class="grid__col--4">
														<dl class="split-list">

															<dt>Name / Client number</dt>
															<dd>
															<span class="wordwrap"
																	id="contact_name"><a href="#accordion-section-client-details" class="accordion-trigger" data-accordion-section="accordion-section-client-details" >
																	${paymentInDetails.name}</a></span>
																/ <span id="account_tradeAccountNum">${paymentInDetails.account.tradeAccountNumber}</span>
															</dd>

															<dt>Client type</dt>
															<dd id="account_clientType">${paymentInDetails.account.clientType}</dd>
															
															<dt>Account status</dt>
															<c:choose>
																<c:when test="${paymentInDetails.account.complianceStatus=='ACTIVE'}">
															<dd id="account_Client_status"><span class="indicator--positiveforaccountstatus" id="payment_complianceStatusdetails">${paymentInDetails.account.complianceStatus}</span></dd>
																	</c:when>
															<c:otherwise>
																<dd id="account_Client_status"><span class="indicator--negativeforaccountstatus" id="payment_complianceStatusdetails">${paymentInDetails.account.complianceStatus}</span></dd>
																</c:otherwise>
															</c:choose>

															<dt>Organisation</dt>
															<dd id="account_organisation">${paymentInDetails.account.orgCode}</dd>
															
															<!-- AT-4078 -->
															<dt>Org Wallet Funding Reason</dt>
															<dd id="acc_org_funding_reason">${paymentInDetails.paymentInInfo.transferReason}</dd>
															
															<dt>Watchlists</dt>
															<dd>
																<ul id="contactWatchlist">
																	<%-- <c:forEach var="watchlist"
																		items="${paymentInDetails.contactWatchlist.watchlistData}"
																		varStatus="loop">
																		<li>${watchlist.name}</li>
																	</c:forEach> --%>
																	<c:forEach var="watchlistRes"
																					items="${ paymentInDetails.contactWatchlist.watchlistData}"
																					varStatus="loop">
																			<c:if test="${watchlistRes.value}">
																				<li>${watchlistRes.name}</li>
																			</c:if>																		
																	</c:forEach>
																</ul>
															</dd>
															
															<dt>Legal Entity</dt>
															<dd id="leaglEntity">${paymentInDetails.paymentInInfo.legalEntity}</dd>
					 										<!-- AT-3471 -->
															<dt>Initial Status</dt>
															<dd id="initialStatus">${paymentInDetails.paymentInInfo.initialStatus}</dd>
															<!-- AT-4187 -->
															<dt>Intuition Risk Level</dt>
															<c:choose>
																<c:when test="${paymentInDetails.paymentInInfo.intuitionRiskLevel == 'Low'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--positive" id="intuitionRiskLevel">${paymentInDetails.paymentInInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentInDetails.paymentInInfo.intuitionRiskLevel == 'Medium'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--neutral" id="intuitionRiskLevel">${paymentInDetails.paymentInInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentInDetails.paymentInInfo.intuitionRiskLevel == 'High'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--negative" id="intuitionRiskLevel">${paymentInDetails.paymentInInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentInDetails.paymentInInfo.intuitionRiskLevel == 'Extreme'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--extreme" id="intuitionRiskLevel">${paymentInDetails.paymentInInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:otherwise>
																	<dd id="paymentIntuitionRiskLevel">${paymentInDetails.paymentInInfo.intuitionRiskLevel}</dd>
																</c:otherwise>
															</c:choose>
														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Date of payment</dt>
															<dd id="payment_dateOfPayment">${paymentInDetails.paymentInInfo.dateOfPayment}</dd>

															<dt>Amount</dt>
															<dd id="payment_amount">${paymentInDetails.paymentInInfo.amount}</dd>

															<dt>Sell currency</dt>
															<dd id="payment_sellCurrency">${paymentInDetails.paymentInInfo.sellCurrency}</dd>

															<dt>Country of fund</dt>
															<%-- <dd id="payment_countryOfFund">${paymentInDetails.paymentInInfo.countryOfFund}</dd> --%>
															<c:choose>
																		<c:when
																			test="${paymentInDetails.paymentInInfo.countryOfFundFullName == '------'}">
																			<dd id="payment_countryOfFund">${paymentInDetails.paymentInInfo.countryOfFundFullName}</dd>
																		</c:when>
																		<c:otherwise>
																			<dd id="payment_countryOfFund">${paymentInDetails.paymentInInfo.countryOfFundFullName} (${paymentInDetails.paymentInInfo.countryOfFund})</dd>
																		</c:otherwise>
															</c:choose> 
															
															
															<dt>Occupation</dt>
															<dd id="contact_occupation">${paymentInDetails.currentContact.occupation}</dd>
															
															<dt>Reversal</dt>
															<dd id="is_reversal">${paymentInDetails.paymentInInfo.isDeleted}</dd>
															
															<dt>Reversal Date</dt>
															<dd id="reversal_date">${paymentInDetails.paymentInInfo.updatedOn}</dd>
															
															 <dt>Estimated transaction value</dt>
															 <dd id="account_estimatedTxnValue">${paymentInDetails.account.estimTransValue}</dd>
				                                             
				                                           <dt>Third Party ?</dt>
															<c:choose>
																<c:when test="${paymentInDetails.paymentInInfo.thirdPartyPayment == true}">
																	<dd id="third_party_flag">Yes</dd>
																</c:when>
																<c:otherwise>
																	<dd id="third_party_flag">No</dd>
																</c:otherwise>
															</c:choose>
															</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Payment method</dt>
															<dd id="payment_paymentMethod">${paymentInDetails.paymentInInfo.paymentMethod}</dd>

															<dt>Name on card/account</dt>
															<dd class="wordwrap" id="payment_nameOnCard">${paymentInDetails.paymentInInfo.nameOnCard}</dd>

															<dt>Account / Card number</dt>
															<dd class="wordwrap" id="payment_accountOrCardNumber">${paymentInDetails.paymentInInfo.debtorAccountNumber}</dd>
											
															<dt>Risk score</dt>
															<c:choose>
															 <c:when test="${paymentInDetails.paymentInInfo.tScoreStatus=='PASS'}">
															<dd id="payment_riskScore" class="indicator--positiveforRiskscore">${paymentInDetails.paymentInInfo.tScore}</dd>
															</c:when> 
				                                              <c:otherwise>
				                                              <c:choose>
				                                               <c:when test="${paymentInDetails.paymentInInfo.tScoreStatus=='FAIL'}">
				                                             <dd id="payment_riskScore" class="indicator--negativeforRiskscore">${paymentInDetails.paymentInInfo.tScore}</dd>
				                                             </c:when> 
				                                              <c:otherwise>
				                                              <dd id="payment_riskScore">${paymentInDetails.paymentInInfo.tScore}</dd>
				                                               </c:otherwise>
				                                              </c:choose>
				                                              </c:otherwise>
				                                              </c:choose>
				                                              
				                                            <dt>FraudSight Decision</dt><dd id="payment_fraudSightScore">${paymentInDetails.paymentInInfo.fsMessage}</dd>
				                                            <dt>FraudSight Reason</dt><dd id="payment_fraudSightScore">${paymentInDetails.paymentInInfo.fsReasonCode}</dd>
				                                             
				                                           <dt>3DS Outcome</dt>
															<dd id="payment_3DS2Authorised">${paymentInDetails.paymentInInfo.threeDsTwoAuthorised}</dd>
															
															 <dt>AVS Result</dt>
															<dd id="payment_avsResult">${paymentInDetails.paymentInInfo.avsResult}</dd>
															
															 <dt>CVC/CVV Result</dt>
															<dd id="payment_cvcResult">${paymentInDetails.paymentInInfo.cvcResult}</dd> 
														</dl>
														
													</div>
													
														<div class="grid__col--12 grid__col--pad-left alertComplianceLog">
																<dl>														 
																	<dt>Compliance log</dt><br>
	  																<dd id="paymentIn_compliance_log" class="wordwrap">${paymentInDetails.alertComplianceLog}</dd>
																</dl>
																
														</div>

												</div>

											</div>

										</div>

									</section>

									<section class="main-content__section">

										<h2>Checks</h2>

										<div class="accordion--quick-controls">

											<div class="quick-controls">
												<ul class="containing horizontal">
													<li class="quick-control__control--open-all"><a
														href="javascript:void(0);"
														data-ot="Expand all checks info"><i
															onclick="viewMoreLoadData(); showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER','FraugsterChart');" class="material-icons">add</i></a>
													</li>
													<li class="quick-control__control--close-all"><a
														href="javascript:void(0);" data-ot="Close all checks info"><i
															onclick="viewMoreResetData()" class="material-icons">close</i></a>
													</li>
												</ul>
											</div>
											
								<div class="accordion__section">
												<div class="accordion__header"
													id="debitor_blacklist_indicator">
													<a href="#"><i class="material-icons">add</i>Blacklist
													<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:if test="${paymentInDetails.debitorBlacklist.isRequired}">
															<c:if
																test="${paymentInDetails.debitorBlacklist.failCount gt 0}">
																<span id="debitor_blacklist_negative"
																	class="indicator--negative">${paymentInDetails.debitorBlacklist.failCount}</span>
															</c:if> <c:if
																test="${paymentInDetails.debitorBlacklist.passCount gt 0}">
																<span id="debitor_blacklist_positive"
																	class="indicator--positive">${paymentInDetails.debitorBlacklist.passCount}</span>
															</c:if> 
														</c:if>
													<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
													</a>
												</div>
												<div class="accordion__content">

													<table class="fixed">

														<thead>
															<tr>
																<th class="center">Account Number Blacklist</th>
																<th class="center">Name Blacklist</th>
														<%--   		<c:if test="${not empty paymentInDetails.debitorBlacklist.bankName}"> --%>
																	<th class="center">Bank Name Blacklist</th>
																<th class="center">Overall Status</th>
															</tr>
														</thead>
														<tbody id="blacklist_debitor">
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
															<c:when test="${paymentInDetails.debitorBlacklist.isRequired}">
																<tr id="blacklist_row">
																	<td hidden="hidden">${paymentInDetails.debitorBlacklist.id}</td>
																	<%-- <c:choose>
																		<c:when
																			test="${paymentInDetails.debitorBlacklist.accountNumber}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose> --%>
																	<c:if test="${paymentInDetails.debitorBlacklist.accountNumber eq 'Not Required'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.accountNumber}</td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.accountNumber eq 'Not Available'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.accountNumber}</td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.accountNumber eq 'false'}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.accountNumber eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentInDetails.debitorBlacklist.accNumberMatchedData }</td>
																	</c:if>
																	
																	<%-- <c:choose>
																		<c:when
																			test="${paymentInDetails.debitorBlacklist.name}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose> --%>
																	<c:if test="${paymentInDetails.debitorBlacklist.name eq 'Not Required'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.name}</td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.name eq 'Not Available'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.name}</td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.name eq 'false'}">
																		<td class="yes-cell" ><i class="material-icons">check</i></td>
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.name eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentInDetails.debitorBlacklist.nameMatchedData }</td>
																	</c:if>
																	
																	
																	 <c:if test="${not empty paymentInDetails.debitorBlacklist.bankName}">
																	<c:if test="${paymentInDetails.debitorBlacklist.bankName eq 'Not Required'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.bankName}</td>																
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.bankName eq 'Not Available'}">
																		<td class = "nowrap center">${paymentInDetails.debitorBlacklist.bankName}</td>																
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.bankName eq 'false'}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>																
																	</c:if>
																	<c:if test="${paymentInDetails.debitorBlacklist.bankName eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentInDetails.debitorBlacklist.bankNameMatchedData }</td>																
																	</c:if>
																	</c:if>
															
																	<c:choose>
																		<c:when test="${paymentInDetails.debitorBlacklist.status}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																			<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																		</c:choose>
																</tr>
															</c:when>
															<c:otherwise>
																<tr>
																	<td hidden="hidden">${paymentInDetails.debitorBlacklist.id}</td>
																	<td class = "nowrap center">${paymentInDetails.debitorBlacklist.statusValue}</td>
																	<td class = "nowrap center">${paymentInDetails.debitorBlacklist.statusValue}</td>
																	<td class = "nowrap center">${paymentInDetails.debitorBlacklist.statusValue}</td>
																	<td class = "nowrap center">${paymentInDetails.debitorBlacklist.statusValue}</td>
																</tr>															
															</c:otherwise>
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
                                                     <br>
													<form>
                                                           <p class="flush-margin">
                                                           <c:choose>
															<c:when test="${paymentInDetails.paymentInInfo.paymentMethod eq 'SWITCH/DEBIT'}">
															<input id="payin_blacklist_recheck" type="button" onClick="resendBlacklistCheck();"
																class="<c:out value="${buttonClass}"/> blacklist-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																<object id="gifloaderforblacklistresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											                    </object> 
											                   </c:when> 
											                   <c:otherwise>
											                   <p class="flush-margin"><input  id="payin_blacklist_recheck" type="button" class="button--primary button--disabled blacklist-field no-lock-support" value="Repeat checks" disabled></p>	
											                   </c:otherwise>					    
														</c:choose>
																<span id="blacklistChecks_resend_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>		
														</p>
                                                    </form>	
												</div>
											</div>
											<div id="accordion-section-country"
												class="accordion__section">

												<div id="payInDetails_countrycheck_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Country
														Check 
													<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
													<c:if test="${paymentInDetails.customCheck.countryCheck.isRequired}">
														<c:if
															test="${paymentInDetails.customCheck.countryCheck.failCount gt 0}">
															<span id="countryCheck_negative"
																class="indicator--negative">${paymentInDetails.customCheck.countryCheck.failCount}</span>
														</c:if> <c:if
															test="${paymentInDetails.customCheck.countryCheck.passCount gt 0}">
															<span id="countryCheck_positive"
																class="indicator--positive">${paymentInDetails.customCheck.countryCheck.passCount}</span>
														</c:if>
													</c:if>
													<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
													</a>
												</div>

												<div class="accordion__content">

													<table >
														<thead>
															<tr>
																<th class="tight-cell">Check date/time</th>
																<th class="tight-cell">Status</th>
															</tr>
														</thead>
														<tbody id="countryCheck_contact">
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
															<c:when test="${paymentInDetails.customCheck.countryCheck.isRequired}">
															<c:if
																test="${paymentInDetails.customCheck.countryCheck.id > 0 }">
																<tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.countryCheck.checkedOn}</td>
																	<td class="nowrap">${paymentInDetails.customCheck.countryCheck.status}
																	${paymentInDetails.customCheck.countryCheck.riskLevel}</td>
																</tr>
															</c:if>
															</c:when>
															<c:otherwise>
																<tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.countryCheck.checkedOn}</td>
																	<td class="nowrap center">${paymentInDetails.customCheck.countryCheck.statusValue}</td>
																</tr>														
															</c:otherwise>
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
													<!-- <a href="javascript:void(0);" id="viewMoreDetailsPayIn_CountryChk"  class="load-more space-after"  onclick="viewMoreDetails('COUNTRYCHECK' ,'countryCheck_contact','countryCheckTotalRecordsPayInId','leftRecordsPayInIdCountryChk','CONTACT');">
											VIEW <span class="load-more__extra" id = "viewMorePayIn_CountryChkId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsPayInIdCountryChk" > </span>
													</a> -->
												</div>
											</div>
											
											<div id="accordion-section-custom" class="accordion__section">

												<div id="payInDetails_customchecks_indicatior"
													class="accordion__header">
													<a href="#" ><i class="material-icons">add</i>Custom
														checks
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:if test="${paymentInDetails.customCheck.paymentOutCustomCheck.isRequired}">
															<c:if
																test="${paymentInDetails.customCheck.paymentOutCustomCheck.failCount gt 0}">
																<span id="account_customCheck_negative"
																	class="indicator--negative">${paymentInDetails.customCheck.paymentOutCustomCheck.failCount}</span>
															</c:if> <c:if
																test="${paymentInDetails.customCheck.paymentOutCustomCheck.passCount gt 0}">
																<span id="account_customCheck_positive"
																	class="indicator--positive">${paymentInDetails.customCheck.paymentOutCustomCheck.passCount}</span>
															</c:if> 
														</c:if>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->												
													</a>
												</div>

												<div class="accordion__content">

													<table >
														<thead>
															<tr>
																<th class="tight-cell">Check date/time</th>
																<th class="tight-cell">Rules</th>
																<th class="tight-cell">Status</th>
															</tr>
														</thead>
														<tbody id="customChecks">
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
															<c:choose>
															<c:when test="${paymentInDetails.customCheck.paymentOutCustomCheck.isRequired}">
															<c:if
																test="${paymentInDetails.customCheck.paymentOutCustomCheck.id > 0 }">
																<%-- <tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																	<td>Velocity Check</td>
																	<td>
																		<ul>
																		
																	<li>Beneficary Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck}</li>
																		<li>Amount Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.permittedAmoutcheck}</li>
																		<li>No of transaction check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.noOffundsoutTxn}</li>
																		</ul>
																	</td>
																</tr> --%>
																<tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																	<td>Whitelist Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "Currency Check" >Currency Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.currency}</li>
																		<li id="Amount range check" >Amount range check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.amoutRange}</li>
																		<li id = "Third party check" >Third party check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.thirdParty}</li>
																		<%-- <li>Reason of transfer check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.reasonOfTransfer}</li> --%>
																	</ul>
																	</td>
																</tr>
																<!-- Added for AT-3346- FirstCredit Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>FirstCredit Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "FirstCredit Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.firstCreditCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																<!-- Added for AT-3349 EUPOIDoc Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>EUPOIDoc Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "EUPOIDoc Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.euPoiCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																
																<!-- Added for AT-3738 CDINC FirstCredit Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>CDINC FirstCredit Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "CDINC FirstCredit Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.cdincFirstCreditCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																
															</c:if>
															</c:when>
															<c:otherwise>
																<%-- <tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																	<td>Velocity Check</td>
																	<td>
																		<ul>
																		
																	<li>Beneficary Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck}</li>
																		<li>Amount Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.permittedAmoutcheck}</li>
																		<li>No of transaction check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.velocityCheck.noOffundsoutTxn}</li>
																		</ul>
																	</td>
																</tr> --%>
																<tr class="talign">
																	<td class="nowrap">${paymentInDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																	<td>Whitelist Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "Currency Check">Currency Check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.currency}</li>
																		<li id="Amount range check" >Amount range check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.amoutRange}</li>
																		<li id = "Third party check">Third party check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.thirdParty}</li>
																		<%-- <li>Reason of transfer check :
																		${paymentInDetails.customCheck.paymentOutCustomCheck.whiteListCheck.reasonOfTransfer}</li> --%>
																	</ul>
																	</td>
																</tr>
																<!-- Added for AT-3346- FirstCredit Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>FirstCredit Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "FirstCredit Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.firstCreditCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																
																<!-- Added for AT-3349 EUPOIDoc Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>EUPOIDoc Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "EUPOIDoc Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.euPoiCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																
																<!-- Added for AT-3738 CDINC FirstCredit Check -->
																<tr class="talign">
																	<td class="nowrap"></td>
																	<td>CDINC FirstCredit Check</td>
																	<td><ul id="customCheckTable">
																	<li id = "CDINC FirstCredit Check" > Status:
																		${paymentInDetails.customCheck.paymentOutCustomCheck.cdincFirstCreditCheck.status}</li>
																	</ul>
																	</td>
																</tr>
																
															</c:otherwise>
														</c:choose>
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
											<a href="javascript:void(0);" id="viewMoreDetailsPayIn_CusChk"  class="load-more space-after"  onclick="viewMoreDetails('VELOCITYCHECK' ,'customChecks','customCheckTotalRecordsPayInId','leftRecordsPayInIdCusChk','ACCOUNT');">
											VIEW <span class="load-more__extra" id = "viewMorePayIn_CusChkId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsPayInIdCusChk" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading...">
											<object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
										</a>
													<input id ="customRepeatCheck" type="hidden" value="<c:out value="${customRepeatCheck}"/>" >
													<form>
														<p class="flush-margin">
															<input id="payin_customChecks_recheck" type="button" onClick="resendCustomCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																<object id="gifloaderforcustomresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="customChecks_resend_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																
																<input id="account_AccountWhiteList" type="button" class="button--primary custom-field float--right" 
														data-modal="modal-original-summary" value="WHITE LIST DATA" onclick="showAccountWhiteListData('${paymentInDetails.currentContact.accountId}' , '${paymentInDetails.account.orgCode}')">
														
														</p>
													</form>

												</div>

											</div>
											<div class="accordion__section">

												<div class="accordion__header"
													id="payInDetails_sanctioncon_indicatior">
													<a href="#"><i class="material-icons">add</i>Sanctions
													<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														 <c:if test="${paymentInDetails.thirdPartySanction.isRequired}">														 
															 <c:if
																test="${paymentInDetails.thirdPartySanction.failCount gt 0 }">
																<span id="payInDetails_contactSanctionFail"
																	class="indicator--negative">${paymentInDetails.thirdPartySanction.failCount}</span>
															</c:if> <c:if
																test="${paymentInDetails.thirdPartySanction.passCount gt 0}">
																<span id="payInDetails_contactSanctionPass"
																	class="indicator--positive">${paymentInDetails.thirdPartySanction.passCount}</span>
															</c:if> 
														</c:if>
													<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
													</a>
												</div>

												<div class="accordion__content">

													<form>

														<table>
															<thead>
																<tr>
																	<th class="sorted desc">Updated on
																			<i></i>
																	</th>
																	<th>Updated by</th>
																	<th>Sanction ID</th>
																	<th>OFAC List</th>
																	<th>World check</th>
																	<th>Status</th>
																</tr>
															</thead>
															<tbody id="sanctions_contact">
															<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
															<c:choose>
																<c:when test="${paymentInDetails.thirdPartySanction.isRequired}">
																	<tr>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.eventServiceLogId}</td>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.entityType}</td>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.entityId}</td>
																		
																		<!-- <td class="center"><input type="checkbox"/></td> -->
																		<!-- <td>CONTACT</td> -->
																		<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																		<td>${paymentInDetails.thirdPartySanction.updatedOn}</td>
																		<td class="nowrap">${paymentInDetails.thirdPartySanction.updatedBy}</td>
																		<td class="nowrap"><a href="javascript:void(0);"
																			onclick="showProviderResponse('${paymentInDetails.thirdPartySanction.eventServiceLogId}','SANCTION')">${paymentInDetails.thirdPartySanction.sanctionId}</a></td>
																		<td class="nowrap">${paymentInDetails.thirdPartySanction.ofacList}</td>
																		<td class="nowrap">${paymentInDetails.thirdPartySanction.worldCheck}</td>
																		<c:choose>
																			<c:when
																				test="${paymentInDetails.thirdPartySanction.status }">
																				<td id ="statusContact" class="yes-cell"><i class="material-icons">check</i></td>
																			</c:when>
																			<c:otherwise>
																				<td id ="statusContact" class="no-cell"><i class="material-icons">clear</i></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.eventServiceLogId}</td>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.entityType}</td>
																		<td hidden="hidden">${paymentInDetails.thirdPartySanction.entityId}</td>
																		
																		<!-- <td class="center"><input type="checkbox"/></td> -->
																		<!-- <td>CONTACT</td> -->
																		<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																		<td>${paymentInDetails.thirdPartySanction.updatedOn}</td>
																		<td class="nowrap center">${paymentInDetails.thirdPartySanction.updatedBy}</td>
																		<td class="nowrap center"><a href="javascript:void(0);"
																			onclick="showProviderResponse('${paymentInDetails.thirdPartySanction.eventServiceLogId}','SANCTION')">${paymentInDetails.thirdPartySanction.sanctionId}</a></td>
																		<td class="nowrap center">${paymentInDetails.thirdPartySanction.ofacList}</td>
																		<td class="nowrap center" style="padding: 2px">${paymentInDetails.thirdPartySanction.worldCheck}</td>
																		<td class="nowrap center" style="padding: inherit;">${paymentInDetails.thirdPartySanction.statusValue}</td>
																	</tr>
																</c:otherwise>
															</c:choose>
															<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
															</tbody>
														</table>
														<a href="javascript:void(0);"
															id="viewMoreDetailsPayIn_Sanc_contact"
															class="load-more space-after"
															onclick="viewMoreDetails('SANCTION' ,'sanctions_contact','sanctionTotalRecordsPayInId_contact','leftRecordsPayInIdSanc_contact','DEBTOR');">
															VIEW <span class="load-more__extra"
															id="viewMorePayIn_SancId_contact"> </span> MORE <span
															class="load-more__left"
															id="leftRecordsPayInIdSanc_contact"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading...">
															<object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
														</a>

														<div class="boxpanel--shadow space-after sanction-field">
															<fieldset>
																<legend>Update selected sanctions</legend>
																<div class="grid space-after">
																	<div class="grid__row">
																		<div class="grid__col--6">
																			<div class="form__field-wrap flush-margin">

																				<p class="label">Which field?</p>

																				<div id="singlelist-sanction-field"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">

																								<li><label
																									for="rad-sanction-contact-field-2"> <input
																										id="rad-sanction-contact-field-2"
																										value="ofaclist" type="radio"
																										name="updateField_contact" /> OFAC List
																								</label></li>
																								<li><label
																									for="rad-sanction-contact-field-3"> <input
																										id="rad-sanction-contact-field-3"
																										value="worldcheck" type="radio"
																										name="updateField_contact" /> World Check
																								</label></li>

																							</ul>

																						</fieldset>

																						<span class="clickpanel__arrow"></span>

																					</div>

																				</div>
																			</div>
																		</div>

																		<div class="grid__col--6 grid__col--pad-left">

																			<div class="form__field-wrap flush-margin">

																				<p class="label">Set this field to...</p>

																				<div id="singlelist-sanction-field-val"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">

																								<li><label
																									for="rad-sanction-fieldval-contact-1">
																										<input id="rad-sanction-fieldval-contact-1"
																										value="Confirmed hit" type="radio"
																										name="updateField_value_contact" /> Confirmed hit
																								</label></li>
																								<li><label
																									for="rad-sanction-fieldval-contact-2">
																										<input id="rad-sanction-fieldval-contact-2"
																										value="Safe" type="radio"
																										name="updateField_value_contact" /> Safe
																								</label></li>

																							</ul>

																						</fieldset>

																						<span class="clickpanel__arrow"></span>

																					</div>

																				</div>
																			</div>

																		</div>

																	</div>

																</div>
																
																<c:choose>
																	<c:when test="${paymentInDetails.paymentInInfo.thirdPartyPayment && paymentInDetails.paymentInInfo.debitorName != '------' }">
																		<p class="right">
																		<span id="sanction_update_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																			<input type="button" id="updateSanction_contact"
																				onClick="updateSanction(${paymentInDetails.thirdPartySanction.entityId},'${paymentInDetails.thirdPartySanction.entityType}');"
																				class="<c:out value="${buttonClass}"/> sanction-field" value="Apply"
																				<c:out value="${buttonDisable}"/> />
																				<object id="gifloaderforUpdatesanction" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																		</p>
																	</c:when>
																		<c:otherwise>
																			<p class="right"><input type="button" class="button--primary button--disabled sanction-field no-lock-support" value="Apply" disabled></p>	
																		</c:otherwise>
																</c:choose>	
																	<!-- 	<small class="button--supporting"><a href="#">Lock this record</a> to own it</small> -->
																	<!-- <img  class="ajax-loader space-next" src="/img/ajax-loader.svg" width="16" height="16" alt="Loading..."/> -->

																


															</fieldset>

														</div>

													</form>

													<form>
														<p class="flush-margin">
														<c:choose>
															<c:when test="${paymentInDetails.thirdPartyPayment && paymentInDetails.paymentInInfo.debitorName != '------'}">
																																														
																	<input type="button" id="sanction_recheck_contact"
																		onClick="resendSanction(${paymentInDetails.thirdPartySanction.entityId},'${paymentInDetails.thirdPartySanction.entityType}');"
																		class="<c:out value="${buttonClass}"/> sanction-field"
																		value="Repeat checks" <c:out value="${buttonDisable}"/> />
																		<object id="gifloaderforSanctionresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																		
															</c:when>
																<c:otherwise>
																	<p class="flush-margin"><input  id="sanction_recheck_contact" type="button" class="button--primary button--disabled sanction-field no-lock-support" value="Repeat checks" disabled></p>	
																</c:otherwise>
															</c:choose>
															<span id="sanction_resend_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																		
																</p>
														
													</form>

												</div>

											</div>

											<div class="accordion__section">

												<div id="payInDetails_fraugster_indicatior"
													class="accordion__header">
													<a href="#" onclick="showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER','FraugsterChart');"><i class="material-icons">add</i>FRAUDPREDICT
														
														<c:if test="${paymentInDetails.fraugster.failCount gt 0}">
															<span id="paymentInDetails_fraugsterFail"
															class="indicator--negative">${paymentInDetails.fraugster.failCount}</span>
														</c:if> 
														<c:if test="${paymentInDetails.fraugster.passCount gt 0}">
															<span id="paymentINDetails_fraugsterPass"
															class="indicator--positive">${paymentInDetails.fraugster.passCount}</span>
														</c:if>
														
													</a>
												</div>

												<div class="accordion__content">

													<table>
														<thead>
															<tr>
																<th class="tight-cell sorted desc">Created
																		on <i></i>
																</th>
																<th>Updated by</th>
																<th>Fraugster Id</th>
																<th>Score</th>
																<th>Status</th>
															</tr>
														</thead>
														<tbody id="fraugster">
															<c:choose>
																	<c:when test="${paymentInDetails.fraugster.isRequired && paymentInDetails.fraugster.isRequired != null}">
																		<tr href="#" onclick="showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER','FraugsterChart');">
																<td>${paymentInDetails.fraugster.createdOn }</td>
																<td class="nowrap">${paymentInDetails.fraugster.updatedBy }</td>
																<td class=""><a href="#"  onclick="showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER')">${paymentInDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${paymentInDetails.fraugster.score }</td>
																<c:choose>
																	<c:when test="${paymentInDetails.fraugster.status }">
																					<td class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
															</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr href="#" onclick="showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER','FraugsterChart');">
																<td class="nowrap">${paymentInDetails.fraugster.createdOn }</td>
																<td class="nowrap">${paymentInDetails.fraugster.updatedBy }</td>
																<td class="nowrap"><a href="#"  onclick="showProviderResponse('${paymentInDetails.fraugster.id}','FRAUGSTER')">${paymentInDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${paymentInDetails.fraugster.score }</td>
																<td class="nowrap">${paymentInDetails.fraugster.statusValue }</td>
															</tr>	
																	</c:otherwise>
																</c:choose>		
														</tbody>
													</table>
													<a href="javascript:void(0);"
														id="viewMoreDetailsPayIn_Fraug"
														class="load-more space-after"
														onclick="viewMoreDetails('FRAUGSTER' ,'fraugster','fraugsterTotalRecordsPayInId','leftRecordsPayInIdFraug','CONTACT');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_FraugId"> </span> MORE <span
														class="load-more__left" id="leftRecordsPayInIdFraug">
													</span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

												
		                                          <form>
														<p class="flush-margin">
															<input id="payin_fraugster_recheck" type="button" onClick="resendFraugsterCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																<object id="gifloaderforfraugsterresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											                    </object> 
																<span id="fraugsterChecks_resend_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>		
														</p>
													</form>
													<div id = "boxpanel-space-before" class="boxpanel space-before">
														<div id="paymentIn-fraugster-chart"></div>
													</div>

												</div>

											</div>
											
											<%-- Added for AT-4306 --%>
											<div class="accordion__section">

												<div  id="payInDetails_intuition_indicatior"  class="accordion__header">
													<a href="#"><i class="material-icons">add</i> Intuition
															<c:if test="${paymentInDetails.intuition.failCount gt 0}">
																<span id="payInDetails_intuitionFail"
																	class="indicator--negative">${paymentInDetails.intuition.failCount}</span>
															</c:if> 
															<c:if test="${paymentInDetails.intuition.passCount gt 0}">
																<span id="payInDetails_intuitionPass"
																	class="indicator--positive">${paymentInDetails.intuition.passCount}</span>
															</c:if>
													</a>
												</div>
												
												<div class="accordion__content">

													<table>
														<thead>
															<tr>
																<th class="tight-cell sorted desc">Created On<i></i></th>
																<th>Updated By</th>
																<th>Correlation Id</th>
																<th>Profile Risk Level</th>
																<th>Payment Risk Level</th>
																<th>RuleScore</th>
																<th>Decision</th>
															</tr>
														</thead>
														<tbody id="payInDetails_intuition">
															<c:choose>
																	<c:when test="${paymentInDetails.intuition.isRequired}">
																		<tr href="javascript:void(0);" onclick="showProviderResponse('${paymentInDetails.intuition.id}','TRANSACTION_MONITORING')">
																			<td>${paymentInDetails.intuition.createdOn}</td>
																			<td class="wrapword">${paymentInDetails.intuition.updatedBy}</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${paymentInDetails.intuition.id}','TRANSACTION_MONITORING')">${paymentInDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${paymentInDetails.intuition.clientRiskLevel}</td>
																			<td class="nowrap">${paymentInDetails.intuition.ruleRiskLevel}</td>
																			<td class="nowrap" class="number">${paymentInDetails.intuition.ruleScore}</td>
																			<td class="nowrap">${paymentInDetails.intuition.decision}</td>
																		</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td>${paymentInDetails.intuition.createdOn}</td>
																			<td class="wrapword">${paymentInDetails.intuition.updatedBy}</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${paymentInDetails.intuition.id}','TRANSACTION_MONITORING')">${paymentInDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${paymentInDetails.intuition.clientRiskLevel}</td>
																			<td class="nowrap">${paymentInDetails.intuition.ruleRiskLevel}</td>
																			<td class="nowrap" class="number">${paymentInDetails.intuition.ruleScore}</td>
																			<td id="intuition_status"  class="nowrap">${paymentInDetails.intuition.decision}</td>
																		</tr>	
																	</c:otherwise>
																</c:choose>															
														</tbody>
													</table>
													
			                                        <a href="javascript:void(0);" id="viewMoreDetails_intuition"  class="load-more space-after"  onclick="viewMoreDetails('TRANSACTION_MONITORING' ,'payInDetails_intuition','intuitionTotalRecordsId','leftRecordsIdIntuition','ACCOUNT');">
														VIEW <span class="load-more__extra" id = "viewMore_IntuitionId" > </span> MORE
														<span class="load-more__left" id= "leftRecordsIdIntuition" > </span>
													</a>
												</div>

											</div>
											
											<div class="accordion__section">
												<div id="doc_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Attached
														documents <c:if
															test='${fn:length(paymentInDetails.documents) gt 0 }'>
															<span id="docConunt" class="indicator">${fn:length(paymentInDetails.documents)}</span>
														</c:if> </a>
												</div>

												<div class="accordion__content">

													<table id="docTableId" class="space-after">
														<thead>
															<tr>
																<th class="tight-cell sorted desc">Created
																		on <i></i>
																</th>
																<th>Created by</th>
																<th>Document name</th>
																<th>Type</th>
																<th>Note</th>
																<th>Status</th>
															</tr>
														</thead>
														<tbody id="attachDoc">
															<c:forEach var="document"
																items="${paymentInDetails.documents}">
																<tr class="talign">
																	<td>${document.createdOn}</td>
																	<td>${document.createdBy}</td>
																	<td class="breakword"><a href="${document.url}">${document.documentName}</a></td>
																	<td class="breakword">${document.documentType}</td>
																	<td class="wrap-cell">${document.note}</td>
																<c:choose>	
																<c:when test="${document.authorized}">
																	<td class="wrap-cell"><input type="checkbox" class="enableDocButton"  name="" value="" checked="checked"></td>
																</c:when>
																<c:otherwise>
																	<td class="wrap-cell"><input type="checkbox" class="enableDocButton"  name="" value=""></td>
																</c:otherwise>	
																</c:choose>	
																	<td hidden="hidden" id="documentId-${document.documentName}">${document.documentId}</td>	<!--Add for AT-5274  -->														
																</tr>
															</c:forEach>
														</tbody>
															<c:if test="${fn:length(paymentInDetails.documents) > 0}">
																<tfoot>
																		<tr>
																			<td class="footerHorizontalLine" colspan="6">
																			<span id="document_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																				</span>
																				 <input id="attach_document_approve_button_id"    
																				 		type="button"
																						class="button--primary button--disabled f-right"
																						value="APPLY CHANGES" onclick="approveDoc();"/>
 																			 </td>
 																		</tr>
																</tfoot>																	 
														</c:if>
													</table>

													<div class="boxpanel--shadow">
														<form>
															<fieldset>
																<legend>Attach another document</legend>

																<div class="grid space-after">

																	<div class="grid__row">

																		<div class="grid__col--6">

																			<div class="form__field-wrap">

																				<p class="label">Document type</p>

																				<div id="singlelist-document-type"
																					class="singlelist clickpanel--right">

																					<p id="docTypeSelectionPfxId" class="singlelist__chosen clickpanel__trigger2">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options multilist__options">

																								<c:forEach var="documentList" items="${paymentInDetails.documentList}"  varStatus="loop">
																									
																									<li>
																									<label  for="rad-doctype-${loop.index}">
																										    <input name="documentType" id="rad-doctype-${loop.index}"
																										     type="radio" value="${documentList.documentName}" />${documentList.documentName}	
																									</label>
																									</li>
																												
																								 </c:forEach>

																							</ul>

																						</fieldset>

																						<span class="clickpanel__arrow"></span>

																					</div>

																				</div>
																			</div>
																		</div>
																		<div class="grid__col--6 grid__col--pad-left">
																			<div class="form__field-wrap--fixed-height">
																				<label for="file-choose-document">Choose
																					file</label> <input type="file" id="file-choose-document" />
																			</div>
																		</div>
																	</div>
																	<div class="grid_row">
																		<div class="grid__col--12">
																			<div class="form__field-wrap flush-margin">
																				<label for="text-note">Note</label> <input
																					type="text" id="text-note" />
																			</div>
																		</div>
																	</div>
																</div>
	                                                            <%--AT-3450 EU POI UPDATE --%>
								                                <div>
															   		 <input id="eu_poi_update_button" type="button" class="button--secondary button--small button--disabled modal-trigger" data-modal="modal-original-summary"
															 		 value="Approve POI" onclick="approvePoiForPaymentIn()"/> 
															 	</div>
																<p class="right">
																	 <input id="isDocumentAuthorized" type="checkbox" value="" 
																	 		name="isDocumentAuthorized">
																 	 <span style="margin-right:10px">Approve document </span>
																	<!-- Id given to upload documents button to make it enable and disable 
																		 Changes done by Vishal J -->
																	<input id="attach_document_button_id" type="button"
																		class="<c:out value="${buttonClass}"/>" value="Apply"
																		<c:out value="${buttonDisable}"/>
																		onclick="uploadDocument('${paymentInDetails.currentContact.crmAccountId}','${paymentInDetails.currentContact.crmContactId}','text-note','file-choose-document','attachDoc','documentType','attach_document_button_id','${paymentInDetails.account.orgCode}','gifloaderfordocumentloader');" />
																	<!-- <small class="button--supporting"><a href="#">Lock this record</a> to own it</small> -->
																	<!-- <img  class="ajax-loader space-next" src="/img/ajax-loader.svg" width="16" height="16" alt="Loading..."/> -->
																	<object id="gifloaderfordocumentloader" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 

																</p>


															</fieldset>
														</form>

													</div>

												</div>

											</div>
										</div>

									</section>

									<section id="other-info">

										<h2>Other information</h2>

										<div class="accordion--quick-controls">

											<div class="quick-controls">
												<ul class="containing horizontal">
													<li class="quick-control__control--open-all"><a
														href="#" data-ot="Expand all checks info"><i
															onclick="viewMoreLoadDataActLog()" class="material-icons">add</i></a>
													</li>
													<li class="quick-control__control--close-all"><a
														href="#" data-ot="Close all checks info"><i
															onclick="viewMoreResetActLogData()"
															class="material-icons">close</i></a></li>
												</ul>
											</div>
											<div id="accordion-section-other-payments-in"
												class="accordion__section">

												<div class="accordion__header"
													id="further_PayInDetails_indicatior">
													<a href="#"><i class="material-icons">add</i>Further
														payment details <!-- <span class="indicator">3</span> -->
													</a>
												</div>

												<div class="accordion__content">

													<h3 class="flush-margin">Payments in</h3>

													<table>
														<thead>
															<tr>
															    <th class="breakwordHeader">Contract Number</th>
																<th class="breakwordHeader">Date of payment</th>
																<th>Amount</th>
																<th class="breakwordHeader">Sell currency</th>
																<th class="breakwordHeader">Payment method</th>
													 			<th class="breakwordHeader">Account / Cardholder name</th>
																<th class="breakwordHeader">Account / Card number</th>
																<th class="breakwordHeader">Bank Name</th>
																<th class="breakwordHeader">Risk score</th>
																
															</tr>
														</thead>
														<tbody id="further_paymentInDetails">
															<c:forEach var="payment"
																items="${paymentInDetails.furtherPaymentDetails.furtherPaymentInDetails}">
																<tr>
																    <td class="breakword">${payment.tradeContractNumber }</td>
																	<td>${payment.dateOfPayment }</td>
																	<td class="number">${payment.amount }</td>
																	<td>${payment.sellCurrency }</td>
																	<td class="breakword" >${payment.method }</td>
																	<!-- style added to <td> to fit long name properly on UI - Vishal J -->
																	<td class="breakword">${payment.accountName }</td>
																	<!-- Condition added by Vishal J to resolve AT - 471 -->
																	<c:choose>
																		<c:when test="${payment.account eq '-'}">
																			<td style="font-weight:bold" class = "center">${payment.account }</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${payment.account }</td>
																		</c:otherwise>
																	</c:choose>
																	<!-- AT-1731 - SnehaZagade -->
																	<c:choose>
																		<c:when test="${payment.bankName eq '-'}">
																			<td style="font-weight:bold" class = "center">${payment.bankName }</td>
																		</c:when>
																		<c:otherwise>
																		<c:choose>
																		<c:when test="${payment.bankName == null}">
																			<td style="font-weight:bold" class = "center">-</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${payment.bankName }</td>
																		</c:otherwise>
																	</c:choose>
																		</c:otherwise>
																	</c:choose>
																	<c:choose>
																		<c:when test="${payment.riskGuardianScore eq '-'}">
																			<td style="font-weight:bold" class = "center">${payment.riskGuardianScore }</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${payment.riskGuardianScore }</td>
																		</c:otherwise>
																	</c:choose>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<a href="javascript:void(0);"
														id="viewMoreDetailsPayIn_FurPayInDetails"
														class="load-more space-after"
														onclick="viewMoreDetails('PAYMENT_IN' ,'further_paymentInDetails','furPayInDetailsTotalRecordsPayInId','leftRecordsPayInIdFurPayInDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_FurPayInDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayInIdFurPayInDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

													<h3 class="flush-margin">Payments out</h3>

													<table>
														<thead>
															<tr>
															    <th class="breakwordHeader">Contract Number</th>
																<th>Date of payment</th>
																<th>Amount</th>
																<th class="tight-cell">Buy currency</th>
																<th>Beneficiary name</th>
																<th class="breakwordHeader">Beneficiary account number</th>
																<th class="breakwordHeader">Bank Name</th>
																<th class="breakwordHeader">Payment Reference</th>
															</tr>
														</thead>
														<tbody id="further_paymentOutDetails">
															<c:forEach var="payment"
																items="${paymentInDetails.furtherPaymentDetails.furtherPaymentOutDetails}">
																<tr>
																    <td class="breakword">${payment.tradeContractNumber }</td>
																	<td>${payment.valuedate }</td>
																	<td class="number">${payment.amount }</td>
																	<td>${payment.buyCurrency }</td>
																	<!-- style added to <td> to fit long name properly on UI - Vishal J -->
																	<td class="breakword">${payment.accountName }</td>
																	<td class="breakword">${payment.account }</td>
																	<!-- AT-1731 - SnehaZagade -->
																	<c:choose>
																		<c:when test="${payment.bankName eq '-'}">
																			<td style="font-weight:bold" class = "center">${payment.bankName }</td>
																		</c:when>
																		<c:otherwise>
																		<c:choose>
																		<c:when test="${payment.bankName == null}">
																			<td style="font-weight:bold" class = "center">-</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${payment.bankName }</td>
																		</c:otherwise>
																	</c:choose>
																		</c:otherwise>
																	</c:choose>
																	<c:choose>
																		<c:when test="${payment.paymentReference eq '-'}">
																			<td style="font-weight:bold" class = "center">${payment.paymentReference }</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${payment.paymentReference }</td>
																		</c:otherwise>
																	</c:choose>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<a href="javascript:void(0);"
														id="viewMoreDetailsPayIn_FurPayOutDetails"
														class="load-more space-after"
														onclick="viewMoreDetails('PAYMENT_OUT' ,'further_paymentOutDetails','furPayOutDetailsTotalRecordsPayInId','leftRecordsPayInIdFurPayOutDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_FurPayOutDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayInIdFurPayOutDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

												</div>

											</div>
				<div id="accordion-section-client-details"
								class="accordion__section">

					<div class="accordion__header">
							<a href="#"><i class="material-icons">add</i>Further
														client details</a>
												</div>
																	
			<div class="accordion__content">

													<div class="boxpanel--shadow">

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">

																		<dt>Address</dt>
																		<dd class="wordwrap" id="contact_FurtherClient_Address">${paymentInDetails.currentContact.address}</dd>

																		<dt>Registration in</dt>
																		<dd id="contact_FurtherClient_regIn">${paymentInDetails.currentContact.regIn}</dd>

																		<dt>Registered</dt>
																		<dd id="contact_FurtherClient_regComplete">${paymentInDetails.currentContact.regComplete}</dd>

																		<dt>Phone</dt>
																		<dd id="contact_FurtherClient_phone">${paymentInDetails.currentContact.phone}</dd>

																		<dt>Mobile</dt>
																		<dd id="contact_FurtherClient_mobile">${paymentInDetails.currentContact.mobile}</dd>

																		<dt>Email</dt>
																		<dd id="contact_FurtherClient_email">
																			${paymentInDetails.currentContact.email}
																		</dd>

																		<dt>Country of nationality</dt>
																		<dd id="contact_FurtherClient_nationality">${paymentInDetails.currentContact.nationality}</dd>

																		<dt>Online account status</dt>
																		<dd id="account_FurtherClient_status">${paymentInDetails.account.complianceStatus}</dd>

																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>Is US client</dt>
																		<c:choose>
																			<c:when
																				test="${paymentInDetails.currentContact.isUsClient}">
																				<dd id="contact_FurtherClient_usClient">Yes</dd>
																			</c:when>
																			<c:otherwise>
																				<dd id="contact_FurtherClient_usClient">No</dd>
																			</c:otherwise>
																		</c:choose>

																		<dt>Service required</dt>
																		<dd id="account_FurtherClient_serviceReq">${paymentInDetails.account.serviceRequired}</dd>
																		
																		<dt>Date of birth</dt>
																		<dd>${paymentInDetails.currentContact.dob}</dd>
																		
																		<dt>Work phone</dt>
																		<dd>${paymentInDetails.currentContact.phoneWork}</dd>

																	<dt>Device info</dt>
																	
																	<%-- <p><a href="#"  id="account_FurtherClient_deviceInfo"  class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" 
																	onclick="showDeviceInfo('${paymentInDetails.currentContact.accountId}')" >View Device Info</a></p> --%>
																	<input id="account_FurtherClient_deviceInfo" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" value="View Device Info"
																	onclick="showDeviceInfo('${paymentInDetails.currentContact.accountId}')">
																	<%-- <input id="account_FurtherClient_deviceInfo" type="button" class="button--primary" value="View Device Info"
																	onclick="showDeviceInfo('${paymentInDetails.currentContact.accountId}')">
																	 --%>
																	<dt>Client Details</dt>
																	
																	<%-- <p><a href="#"  id="account_FurtherClient_deviceInfo"  class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" 
																	onclick="showDeviceInfo('${paymentInDetails.currentContact.accountId}')" >View Device Info</a></p> --%>
																	<input id="account_FurtherClient_viewClient" type="button" class="button--secondary button--small" value="View Client Details"
																	onclick="openClientDetail()">
																	
																		<%-- <dt>Browser type</dt>
							<dd>${paymentOutDetails.account.browserType}</dd>

							<dt>Cookie info</dt>
							<dd>${paymentOutDetails.account.cookieInfo}</dd>

							<dt>Referral text</dt>
							<dd>${paymentOutDetails.account.refferalText}</dd>

							<dt>Affiliate name</dt>
							<dd>${paymentOutDetails.account.affiliateName}</dd>
 --%>
																	</dl>
																</div>

															</div>

														</div>

													</div>

												</div>

											</div>
											
		<div id="accordion-section-wallet" class="accordion__section">

												<div id="client-wallets" class="accordion__header">
													<a href="#" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;"><i class="material-icons">add</i>Wallets
											      </a>
												</div>
									<div id="walletid" class="accordion__content">
																	
									<table id="customerWalletTable" class="micro" border="1"> 
											<thead> 
												<tr> 
												   <th>Currency</th> 
													<th>Wallet ID</th> 
													<th class="number">Available</th> 
													<th class="number">Total</th>
												</tr> 
											 </thead> 
										 <tbody id="customerWalletTablebody" > 
										 </tbody>
										</table>
									<div id="walletTableLoadingGIF" style="displa:none;" >
									<svg width='20px' height='10px' xmlns="http://www.w3.org/2000/svg" viewBox="-40 -40 180 180" preserveAspectRatio="xMidYMid" class="uil-ring">
									 <rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect>
									  <circle cx="50" cy="50" r="42.5" stroke-dasharray="173.57299411083608 93.46238144429634" stroke="#2b76b6" fill="none" stroke-width="15">
									   <animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s">
									   </animateTransform>
									  </circle>
									</svg>
									</div>
								   </div>
																
							</div>	
							
											<div class="accordion__section">

												<div id="fxticket_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Fx Tickets</a>
												</div>
												<div id="fxTicketTableDiv" class="accordion__content"></div>
											</div>		
											
											<div class="accordion__section">

												<div id="payInDetails_activitylog_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Activity
														log</a>
												</div>

												<div class="accordion__content">

													<div class="audit-trail" align="center">
														<section style="width:250px;">
															<p class="label filterInputHeader">Filter</p>
															<select id="SelectedAuditTrailFilter"
																style="border-color: #ccc;"
																onchange="getActivityLogsByModule('1','10', '${paymentInDetails.currentContact.accountId}', 
																false , this.value, 'leftRecordsIdActLog_AuditTrail',
																'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetailsPayIn_ActLog');">
																<option value="DEFAULT">Select</option>
																<option value="REGISTRATION">Registration</option>
																<option value="PAYMENT IN">Payment In</option>
																<option value="PAYMENT OUT">Payment Out</option>
																<option value="ALL">All</option>
															</select>
														</section>
														
													</div>

													<table>
														<thead>
															<tr>
																<th class="whiteSpacePreLine sorted desc">Activity
																		date/time <i></i>
																</th>
																<th class="whiteSpacePreLine">Trade Contract Number</a></th>
																<th>User</th>
																<th>Activity</th>
																<th>Activity type</th>
																<th>Comment</th>
															</tr>
														</thead>
														<tbody id="activityLog">
															<c:forEach var="activityData"
																items="${paymentInDetails.activityLogs.activityLogData}">
																<tr class="talign">
																	<td>${activityData.createdOn}</td>
																	<td>${paymentInDetails.paymentInInfo.transactionNumber}</td>
																	<td class="nowrap">${activityData.createdBy}</td>
																	<td>
																		<ul>
																			<li>${activityData.activity }</li>
																		</ul>
																	</td>
																	<td>${activityData.activityType }</td>
																	<c:choose>
																		<c:when test="${empty activityData.comment }">
																			<td style="font-weight:bold" class = "center">-</td>
																		</c:when>
																		<c:otherwise>
																			<td class="breakword">${activityData.comment }</td>																			
																		</c:otherwise>
																	</c:choose>
																</tr>
															</c:forEach>
														</tbody>
														<tbody id="auditTrailActivityLog">
														</tbody>
													</table>
													<a href="javascript:void(0);"
														id="viewMoreDetailsPayIn_ActLog"
														class="load-more space-after"
														onclick="viewMoreDetails('ACTIVITYLOG' ,'activityLog','actLogTotalRecordsPayInId','leftRecordsPayInId_ActLog');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_ActLogId"> </span> MORE <span
														class="load-more__left" id="leftRecordsPayInId_ActLog">
													</span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>
													
													<a href="javascript:void(0);" id="viewMoreAuditTrailDetails_ActLog"  class="load-more space-after"  
										  onclick="getViewMoreActivityLogsByModule('${paymentInDetails.currentContact.accountId}', true, 'leftRecordsIdActLog_AuditTrail', 'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetails_ActLog');">
											VIEW <span class="load-more__extra" id = "viewMore_AuditTrail_ActLogId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdActLog_AuditTrail" > </span>
										  </a>


												</div>

											</div>

											
										</div>

									</section>

								</div>

								<div class="grid__col--4 grid__col--pad-left p-rel">

									<section id="pagepanel">

										<form>

											<h2 class="pagepanel__title">Actions</h2>

											<div class="pagepanel__content boxpanel--shadow--splits">
											
											<section class="watchlist-field">

													<h3 class="hidden">Add to watchlists</h3>

													<fieldset>

														<legend>Add to watchlists</legend>

														<ul class="form__fields--bare">

															<li class="form__field-wrap">

																<p class="label">Select watchlists</p>

																<div id="multilist-add-to-watchlists"
																	class="multilist clickpanel--right">

																	<ul class="multilist__chosen">
																		<li class="clickpanel__trigger">Please select</li>
																	</ul>

																	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																	<div class="clickpanel__content--hidden"
																		id="payment_watchlists">

																		<fieldset>
																			<input class="multilist__search space-after" type="search" placeholder="Search list">
																			<ul class="multilist__options">
																				<c:forEach var="watchlistRes"
																					items="${ paymentInDetails.watchlist.watchlistData}"
																					varStatus="loop">
																					<li><label
																						for="chk-watchlist-${loop.index + 1}"> <c:choose>
																								<c:when test="${watchlistRes.value}">

																									<input id="chk-watchlist-${loop.index + 1}"
																										type="checkbox" checked="checked"
																										name="payment_watchlist[]"
																										value="${watchlistRes.name}" />
																										${watchlistRes.name}					
																							    </c:when>
																								<c:otherwise>
																									<input id="chk-watchlist-${loop.index + 1}"
																										type="checkbox" name="payment_watchlist[]"
																										value="${watchlistRes.name}" />
																									${watchlistRes.name}					
					 																	   </c:otherwise>
																							</c:choose>
																					</label></li>
																				</c:forEach>
																			</ul>

																		</fieldset>

																		<span class="clickpanel__arrow"></span>

																	</div>

																</div>
															</li>

														</ul>

													</fieldset>

												</section>
											
											
											<!-- Upadate Status section starts here -->
												<section>

													<h3 class="hidden">Update status</h3>

													<fieldset>

														<legend>Update status</legend>

														<ul class="form__fields--bare">

															<li class="form__field-wrap">

																<fieldset>

																	<legend class="label">Set status to...</legend>

																	<ul id="paymentInDetails_Status_radio"
																		class="pill-choice--small">
																		<c:forEach var="statusData"
																			items="${paymentInDetails.status.statuses }">
																			<li><c:if
																					test="${statusData.status == 'CLEAR' }">
																					<c:choose>
																						<c:when test="${statusData.isSelected  }">
																							<label
																								id="rad-status-clear-label"
																								class="pill-choice__choice--positive pill-choice__choice--on"
																								for="rad-status-clear"> <input
																								id="rad-status-clear" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }"
																								class="input-more-hide"
																								data-more-hide="input-more-areas-reasons"
																								checked /> Clear
																							</label>
																						</c:when>
																						<c:otherwise>
																							<label class="pill-choice__choice--positive"
																								id="rad-status-clear-label"
																								for="rad-status-clear"> <input
																								id="rad-status-clear" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }"
																								class="input-more-hide"
																								data-more-hide="input-more-areas-reasons" />
																								Clear
																							</label>
																						</c:otherwise>
																					</c:choose>
																				</c:if>  <c:if test="${statusData.status == 'REJECT' }">
																					<c:choose>
																						<c:when test="${statusData.isSelected  }">
																							<label
																								class="pill-choice__choice--negative pill-choice__choice--on"
																								id="rad-status-reject-label"
																								for="rad-status-reject"> <input
																								id="rad-status-reject" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons"
																								checked /> Reject
																							</label>
																						</c:when>
																						<c:otherwise>
																							<label class="pill-choice__choice--negative "
																								id="rad-status-reject-label"
																								for="rad-status-reject"> <input
																								id="rad-status-reject" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons" />
																								Reject
																							</label>
																						</c:otherwise>
																					</c:choose>
																				</c:if> <c:if test="${statusData.status == 'SEIZE' }">
																					<c:choose>
																						<c:when test="${statusData.isSelected  }">
																							<label
																								class="pill-choice__choice--negative pill-choice__choice--on"
																								id="rad-status-seize-label"
																								for="rad-status-seize"> <input
																								id="rad-status-seize" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons"
																								checked /> Seize
																							</label>
																						</c:when>
																						<c:otherwise>
																							<label class="pill-choice__choice--negative "
																								id="rad-status-seize-label"
																								for="rad-status-seize"> <input
																								id="rad-status-seize" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons" />
																								Seize
																							</label>
																						</c:otherwise>
																					</c:choose>
																				</c:if> <c:if test="${statusData.status == 'HOLD' }">
																					<c:choose>
																						<c:when test="${statusData.isSelected  }">
																							<label
																								class="pill-choice__choice--neutral pill-choice__choice--on rightBorder"
																								id="rad-status-hold-label"
																								for="rad-status-hold"> <input
																								id="rad-status-hold" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons"
																								checked /> Hold
																							</label>
																						</c:when>
																						<c:otherwise>
																							<label class="pill-choice__choice--neutral rightBorder"
																								id="rad-status-hold-label"
																								for="rad-status-hold"> <input
																								id="rad-status-hold" type="radio"
																								name="paymentStatus"
																								value="${statusData.status }" class="input-more"
																								data-more-area="input-more-reject-reasons" />
																								Hold
																							</label>
																						</c:otherwise>
																					</c:choose>
																				</c:if></li>
																		</c:forEach>

																	</ul>

																</fieldset>
															</li>

															<li class="form__field-wrap">

																<div id="input-more-areas-reasons"
																	class="input-more-areas">

																	<div id="input-more-unwatch"
																		class="input-more-areas__area--hidden watchlist-field">

																		<p class="label">Remove from watchlists</p>

																		<div id="multilist-unwatch"
																			class="multilist clickpanel--right">

																			<ul class="multilist__chosen" id="removedWatchlist">
																				<li class="clickpanel__trigger">Removed from
																					all watchlists</li>
																			</ul>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden">

																				<fieldset>

																				</fieldset>

																				<span class="clickpanel__arrow"></span>

																			</div>

																		</div>
																	</div>

																	<div id="input-more-reject-reasons"
																		class="input-more-areas__area--hidden">

																		<p class="label">Select a reason</p>

																		<div id="singlelist-reject-reasons"
																			class="singlelist clickpanel--right">

																			<p class="singlelist__chosen clickpanel__trigger">Please
																				select</p>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden"
																				id="payOutDetails_paymentOutStatusReasons">

																				<fieldset>

																					<ul class="singlelist__options scrollSinglelist"
																						id="payment_statusReasons">
																						<c:forEach var="reason"
																							items="${ paymentInDetails.statusReason.statusReasonData}"
																							varStatus="loop">
																							<li><c:choose>
																									<c:when test="${reason.value}">
																										<label
																											for="rad-reject-reason-${loop.index + 1}">
																											<input
																											id="rad-reject-reason-${loop.index + 1}"
																											type="radio" checked="checked"
																											name="payDetails_payStatusReasons"
																											value="${reason.name}" /> ${reason.name}
																										</label>

																									</c:when>
																									<c:otherwise>
																										<label
																											for="rad-reject-reason-${loop.index + 1}">
																											<input
																											id="rad-reject-reason-${loop.index + 1}"
																											type="radio"
																											name="payDetails_payStatusReasons"
																											value="${reason.name}" /> ${reason.name}
																										</label>
																									</c:otherwise>
																								</c:choose></li>
																						</c:forEach>
							

																					</ul>

																				</fieldset>

																				<span class="clickpanel__arrow"></span>

																			</div>

																		</div>
																	</div>

																	<div id="input-more-seize-reasons"
																		class="input-more-areas__area--hidden">

																		<p class="label">Select a reason for siezing</p>

																		<div id="singlelist-seize-reasons"
																			class="singlelist clickpanel--right">

																			<p class="singlelist__chosen clickpanel__trigger">Please
																				select</p>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden">

																				<fieldset>

																					<ul class="singlelist__options">

																						<li><label for="rad-seize-reason-1">
																								<input id="rad-seize-reason-1" type="radio"
																								name="SomeNameSeizeReason" /> First reason
																						</label></li>
																						<li><label for="rad-seize-reason-2">
																								<input id="rad-seize-reason-2" type="radio"
																								name="SomeNameSeizeReason" /> Second reason
																						</label></li>
																						<li><label for="rad-seize-reason-3">
																								<input id="rad-seize-reason-3" type="radio"
																								name="SomeNameSeizeReason" /> Third reason
																						</label></li>

																					</ul>

																				</fieldset>

																				<span class="clickpanel__arrow"></span>

																			</div>

																		</div>
																	</div>

																	<div id="input-more-hold-reasons"
																		class="input-more-areas__area--hidden">

																		<p class="label">Select a reason for putting on
																			hold</p>

																		<div id="singlelist-hold-reasons"
																			class="singlelist clickpanel--right">

																			<p class="singlelist__chosen clickpanel__trigger">Please
																				select</p>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden">

																				<fieldset>

																					<ul class="singlelist__options">

																						<li><label for="rad-hold-reason-1"> <input
																								id="rad-hold-reason-1" type="radio"
																								name="SomeNameHoldReason" /> First reason
																						</label></li>
																						<li><label for="rad-hold-reason-2"> <input
																								id="rad-hold-reason-2" type="radio"
																								name="SomeNameHoldReason" /> Second reason
																						</label></li>
																						<li><label for="rad-hold-reason-3"> <input
																								id="rad-hold-reason-3" type="radio"
																								name="SomeNameHoldReason" /> Third reason
																						</label></li>

																					</ul>

																				</fieldset>

																				<span class="clickpanel__arrow"></span>

																			</div>

																		</div>
																	</div>

																</div>

															</li>

														</ul>

													</fieldset>

												</section>

												<section>

													<h3 class="hidden">Add comments</h3>

													<label class="legend" for="comments">Add comments</label>
													<textarea id="comments" maxlength="1024"></textarea>

												</section>

												<section class="section--actions">
													<h3 class="hidden">Apply</h3>


													<input id="updatePaymentIn" type="button"
														class="<c:out value="${buttonClass}"/>" value="Apply"
														<c:out value="${buttonDisable}"/>
														onclick="executeActions(false);" />
														
													<input id="updatePaymentInAndUnlock" type="button"
														class="<c:out value="${buttonClass}"/>" value="Apply & UNLOCK"
														<c:out value="${buttonDisable}"/>
														onclick="executeActions(true);" />	
														
														 <object id="gifloaderforpayinpfx" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
														
													<c:choose>
														<c:when test="${(paymentInDetails.locked == null || !paymentInDetails.locked)}">
														<span id = "appliedLock">
														<small  id ="applyLock" class="button--supporting"><a href="#">Lock this record</a> to own it</small>
														</span>
														</c:when>
														<c:otherwise>
														<span hidden="hidden" id = "appliedLock">
															<small  id ="applyLock" class="button--supporting"><a href="#">Lock this record</a> to own it</small>
														</span>
														</c:otherwise>
													</c:choose>
													<span id="payment_update_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
													
													<!-- <small class="button--supporting"><a href="#">Lock this record</a> to own it</small> -->
													<!-- <img  class="ajax-loader space-next" src="/img/ajax-loader.svg" width="16" height="16" alt="Loading..."/> -->

												</section>


											</div>

										</form>

									</section>

								</div>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>


		</main>
		
				
		<div id="fxticketpopups" class="popupDiv" title="Client Details for Fx Ticket">
			<div id="fxticketView" class="popupTextAreaHolisticView popup">
			</div>
		</div>
		
		<form id="payInDetailForm" action="/compliance-portal/paymentInDetail" method="POST">
			<input type="hidden" id="paymentInId" value="" name="paymentInId"/>
			<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/>
			<input type="hidden" id="custType" value="" name="custType"/>
		<%-- <c:choose>
			<c:when test="${paymentInDetails.isPagenationRequired==true}">
		<ul id="paginationBlock" class="pagination--fixed page-load">
			<li class="pagination__jump--disabled"><a id="firstRecord"
				onclick="getPayInFirstRecord('${paymentInDetails.paginationDetails.firstRecord.custType}','${paymentInDetails.paginationDetails.firstRecord.id}');" href="#" data-ot="First record"><i
					class="material-icons">first_page</i></a></li>
			<li class="pagination__jump"><a id="previousRecord"
				onclick="getPayInPreviousRecord('${paymentInDetails.paginationDetails.prevRecord.custType}','${paymentInDetails.paginationDetails.prevRecord.id}');" href="#"
				data-ot="Previous record"><i class="material-icons">navigate_before</i></a>
			</li>
			<li class="pagination__jump"><a id="nextRecord" onclick="getPayInNextRecord('${paymentInDetails.paginationDetails.nextRecord.custType}','${paymentInDetails.paginationDetails.nextRecord.id}');"
				href="#" data-ot="Next record"><i class="material-icons">navigate_next</i></a>
			</li>
			<li class="pagination__jump"><a id="lastRecord" onclick="getPayInLastRecord('${paymentInDetails.paginationDetails.totalRecords.custType}','${paymentInDetails.paginationDetails.totalRecords.id}');"
				href="#" data-ot="Last record"><i class="material-icons">last_page</i></a>
			</li>
			<li class="pagination__text">Record <strong id="currentRecord">${paymentInDetails.currentRecord }</strong>
				of <span id="totolRecords">${paymentInDetails.totalRecords }</span>
			</li>
		</ul>
		</c:when>
	</c:choose> --%>
	</form>
	<form id="openClientForm" action="/compliance-portal/registrationDetails" method="POST" target="_blank">
																		<input type="hidden" id="contactId" value="${paymentInDetails.currentContact.id}" name="contactId"/>
																		<input type="hidden" id="custType" value="${paymentInDetails.account.clientType}" name="custType"/>
																		<input type="hidden" id="source" value="queue" name="source"/>
																	 </form>
	</div>

	<div id="drawer-user" class="drawer--closed">

		<h2 class="drawer__heading">
			Your profile<span class="drawer__close"><i
				class="material-icons">close</i></span>
		</h2>

		<h3>Site preferences</h3>

		<form>

			<ul class="form__fields">

				<li class="form__field-wrap--check"><input
					id="chk-preferences-menu-minimised" type="checkbox" required
					checked /> <label for="chk-preferences-menu-minimised">Navigation
						minimised by default</label></li>

			</ul>

			<input type="button" class="<c:out value="${buttonClass}"/>"
				value="Save" <c:out value="${buttonDisable}"/> />

		</form>

		<input type="hidden" id="searchCriteria"
			value='${paymentInDetails.searchCriteria}' /> 
		<input type="hidden" id="contact_contactId" 
			value='${paymentInDetails.currentContact.id}' />
		<input type="hidden" id="contact_crmAccountId"
			value='${paymentInDetails.currentContact.crmAccountId}' /> 
		<input type="hidden" id="contact_crmContactId"
			value='${paymentInDetails.currentContact.crmContactId}' /> 
		<input type="hidden" id="contact_accountId"
			value='${paymentInDetails.currentContact.accountId}' /> 
		<input type="hidden" id="userResourceId"
			value='${paymentInDetails.userResourceId}' /> 
		<input type="hidden"id="paymentinId" 
			value='${paymentInDetails.paymentInInfo.id}' />
		<input type="hidden" id="contact_tradeContactId"
			value='${paymentInDetails.currentContact.tradeContactID}' /> 
		<input type="hidden" id="tradePaymentId"
			value='${paymentInDetails.paymentInInfo.tradePaymentId}' /> 
		<input hidden="hidden" id="third_partyPayment"
			value='${paymentInDetails.thirdPartyPayment}' />
		<%-- <input type="hidden" id="kycTotalRecordsId" value='${paymentOutDetails.kyc.kycTotalRecords}'/> --%>
		<input type="hidden" id="sanctionTotalRecordsPayInId_contact"
			value='${paymentInDetails.thirdPartySanction.sanctionTotalRecords}' />
		<input type="hidden" id="fraugsterTotalRecordsPayInId"
			value='${paymentInDetails.fraugster.fraugsterTotalRecords}' /> 
			<input type="hidden" id="customCheckTotalRecordsPayInId" 
			value='${paymentInDetails.customCheck.paymentOutCustomCheck.totalRecords}'/>
		<input type="hidden" id="actLogTotalRecordsPayInId"
			value='${paymentInDetails.activityLogs.totalRecords}' /> 
		<input type="hidden" id="furPayInDetailsTotalRecordsPayInId"
			value='${paymentInDetails.furtherPaymentDetails.payInDetailsTotalRecords}' />
		<input type="hidden" id="furPayOutDetailsTotalRecordsPayInId"
			value='${paymentInDetails.furtherPaymentDetails.payOutDetailsTotalRecords}' />
		<input type="hidden" id="countryCheckTotalRecordsPayInId" 
			value='${paymentInDetails.customCheck.countryCheck.countryCheckTotalRecords}' />
		<input type="hidden" id="fraugster_eventServiceLogId" value='${paymentInDetails.fraugster.id}' />
		<input type="hidden" id="payment_isOnQueue" value='${paymentInDetails.isOnQueue}' />	
		<input type="hidden" id="documentCategory" value='PaymentIn' />
		<input type="hidden" id="orgganizationCode" value='${paymentInDetails.account.orgCode}' />
		<input type="hidden" id="isRecordLocked" value='${paymentInDetails.locked}' />
		<input type="hidden" id="countryOfFundRiskLevel" 
			value='${paymentInDetails.customCheck.countryCheck.riskLevel}' />
			<input type="hidden" id="auditTrailActLogTotalRecords" value=""/>
			<input type="hidden" id="auditTrailActLogEntityType" value=""/>
        <input type="hidden" id="poiExists" value='${paymentInDetails.poiExists}'/>
        <input type="hidden" id="intuitionTotalRecordsId" value='${paymentInDetails.intuition.intuitionTotalRecords}'/>
        <input type="hidden" id="blacklistStatus" value='${paymentInDetails.debitorBlacklist.statusValue}'/>
        <input type="hidden" id="customCheckStatus" value='${paymentInDetails.customCheck.paymentOutCustomCheck.status}'/>
        <input type="hidden" id="sanctionStatus" value='${paymentInDetails.thirdPartySanction.statusValue}'/>
        <input type="hidden" id="fraugsterStatus" value='${paymentInDetails.fraugster.statusValue}'/>
        <input type="hidden" id="accountTMFlag" value='${paymentInDetails.accountTMFlag}'/>
        <input type="hidden" id="accountVersion" value='${paymentInDetails.accountVersion}'/>
        <input type="hidden" id="intuitionCurrentStatus" value='${paymentInDetails.intuition.status}'/> 
		<input type="hidden" id="intuitionCurrentAction" value='${paymentInDetails.intuition.currentAction}'/>
	</div>
	<div id="ProviderResponsepopups" class="popupDiv"
		title="Provider Response">
		<textarea id="providerResponseJson" class="popupTextArea">NOT AVAILABLE</textarea>
	</div>
	<div id="DeviceInfopopups" class="popupDiv"
		title="Device Info">
		<table id="deviceInfo" class="popupTextAreaDeviceInfo"></table>
	</div>
	
	<div id="AccountWhiteListDatapopups" class="popupDiv"
		title="Account WhiteList Data">
		<table id="accountWhiteListData" class="popupTextAreaDeviceInfo"></table>
	</div>
	
	<div id="HolisticViewpopups" class="popupDiv"
		title="Holistic view">
		<div id="holisticView" class="popupTextAreaHolisticView popup">
		<!-- <div class="scrollpane--large-h scrollpane--borderless"></div> -->
		</div>
	</div>
	
	<div id="Walletpopups" class="popupDiv" title="Transaction Details for Wallet">
		<div id="WalletView" class="popupTextAreaWalletView popup">
		</div>
	</div>
	
	<%-- AT-3450 EU POI UPDATE  --%>
	<div id="ApprovePoiPopup" class="popupDiv">
		<div id="approvePoiPopupdiv">
			<h1>Are you sure?</h1>
		</div>
	</div>
	
	
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript"
		src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/paymentInDetails.js"></script>
	<script type="text/javascript" src="resources/js/jsontotable.js"></script>
    <script type="text/javascript" src="resources/js/wallet.js"></script>
    <script type="text/javascript" src="resources/js/fxTicket.js"></script>
    <script type="text/javascript" src="resources/js/amcharts/cd_amcharts_min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-confirm.js"></script>
</body>

</html>