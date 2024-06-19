
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
				test="${paymentOutDetails.locked  && paymentOutDetails.lockedBy != paymentOutDetails.user.name || paymentOutDetails.locked == null || !paymentOutDetails.locked}">
				<c:set var="buttonClass" value="button--primary button--disabled"></c:set>
				<c:set var="buttonDisable" value="disabled='disabled'"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="buttonClass" value="button--primary"></c:set>
				<c:set var="buttonDisable" value=""></c:set>
			</c:otherwise>
		</c:choose>
		<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">
				
				<div class="grid__col--12">
				<c:choose>
					<c:when test="${paymentOutDetails.source == 'queue' }">
						<c:set var="redirectUrl" value="/compliance-portal/payOutQueue"/>
						<c:set var="redirectTo" value="Payments out queue"/>
					</c:when>
					<c:otherwise>
						<c:set var="redirectUrl" value="/compliance-portal/paymentOutReport"/>
						<c:set var="redirectTo" value="Payments out report"/>
					</c:otherwise>
				</c:choose>
					<form id="redirectQueueForm" action="${redirectUrl }" method="post">
						<div class="grid__col--9">
							<h1>
								Transaction #<span id="payment_transNum">${paymentOutDetails.paymentOutInfo.transactionNumber}</span>
		
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

							<c:if test="${paymentOutDetails.noOfContactsForAccount > 1}">
								<span class="message--toast rhs page-load"> <i
									class="material-icons">assignment_ind</i> <a
									 onclick="openClientDetail()" class="accordion-trigger" href="#">${paymentOutDetails.noOfContactsForAccount - 1}
										more person on this account</a>
								</span>
							</c:if>

						</div>
					</div>
					
					<div id="main-content__body">
						<div id="main-content__body_positive" class="message--positive" style="display: none">
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
															test="${paymentOutDetails.paymentOutInfo.status == 'SEIZE'}">
															<p id="payment_status">
																<span class="indicator--negative"
																	id="payment_complianceStatus">SEIZE</span>
															</p>
														</c:when>
											<c:when
															test="${paymentOutDetails.paymentOutInfo.status == 'REJECT'}">
															<p id="payment_status">
																<span class="indicator--negative"
																	id="payment_complianceStatus">${paymentOutDetails.paymentOutInfo.status}</span>
															</p>
														</c:when>
														
														<c:when
															test="${paymentOutDetails.paymentOutInfo.status == 'HOLD'}">
															<p id="payment_status">
																<span class="indicator--neutral"
																	id="payment_complianceStatus">${paymentOutDetails.paymentOutInfo.status}</span>
															</p>
														</c:when>
														<c:otherwise>
															<p id="payment_status">
																<span class="indicator--positive"
																	id="payment_complianceStatus">${paymentOutDetails.paymentOutInfo.status}</span>
															</p>
														</c:otherwise>
													</c:choose>



												</div>

												<div class="grid__col--6">

													<div id="lock" class="f-right">

														<!-- <img id="ajax-loader-lock-toggle" class="ajax-loader space-next" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->
														<c:choose>
															<c:when 
														            test="${paymentOutDetails.paymentOutInfo.isDeleted == false}">
														 <c:choose>
																
															<c:when
																test="${paymentOutDetails.locked  && paymentOutDetails.lockedBy == paymentOutDetails.user.name}">
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
															<c:when test="${paymentOutDetails.locked}">
																<span id="ownRecord"
																	class="space-next toggle-support-text"><i
																	class="material-icons">person_pin</i>
																	${paymentOutDetails.lockedBy} own(s) this record</span>
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
																	id="contact_name"><a href="#accordion-section-client-details" class="accordion-trigger" data-accordion-section="accordion-section-client-details" >${paymentOutDetails.name}</a></span>
																/ <span id="account_tradeAccountNum">${paymentOutDetails.account.tradeAccountNumber}</span>
															</dd>

															<dt>Client type</dt>
															<dd id="account_clientType">${paymentOutDetails.account.clientType}</dd>

															<dt>Occupation</dt>
															<dd id="contact_occupation">${paymentOutDetails.currentContact.occupation}</dd>

															<dt>Organisation</dt>
															<dd id="account_organisation">${paymentOutDetails.account.orgCode}</dd>
															
																<dt>Account status</dt>
															<c:choose>

																<c:when test="${paymentOutDetails.account.complianceStatus=='ACTIVE'}">
															<dd id="account_Client_status"> <span class="indicator--positiveforaccountstatus" id="payment_complianceStatusdetails">${paymentOutDetails.account.complianceStatus}</span></dd>
															 </c:when>
															 <c:otherwise>
															 <dd id="account_Client_status"> <span class="indicator--negativeforaccountstatus" id="payment_complianceStatusdetails">${paymentOutDetails.account.complianceStatus}</span></dd>
															</c:otherwise>
															</c:choose>
															
															<dt>Legal Entity</dt>
															<dd id="leaglEntity">${paymentOutDetails.paymentOutInfo.legalEntity}</dd>
                                                              <!-- AT-3471 -->
                                                              <dt>Initial Status</dt>
															 <dd id="initialStatus">${paymentOutDetails.paymentOutInfo.initialStatus}</dd>
                                                             <!-- AT-4187 -->
															<dt>Intuition Risk Level</dt>
															<c:choose>
																<c:when test="${paymentOutDetails.paymentOutInfo.intuitionRiskLevel == 'Low'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--positive" id="intuitionRiskLevel">${paymentOutDetails.paymentOutInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentOutDetails.paymentOutInfo.intuitionRiskLevel == 'Medium'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--neutral" id="intuitionRiskLevel">${paymentOutDetails.paymentOutInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentOutDetails.paymentOutInfo.intuitionRiskLevel == 'High'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--negative" id="intuitionRiskLevel">${paymentOutDetails.paymentOutInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:when test="${paymentOutDetails.paymentOutInfo.intuitionRiskLevel == 'Extreme'}">
																	<dd id ="paymentIntuitionRiskLevel"><span class="indicator--extreme" id="intuitionRiskLevel">${paymentOutDetails.paymentOutInfo.intuitionRiskLevel}</span></dd>
																</c:when>
																<c:otherwise>
																	<dd id="paymentIntuitionRiskLevel">${paymentOutDetails.paymentOutInfo.intuitionRiskLevel}</dd>
																</c:otherwise>
															</c:choose>
														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Date of payment</dt>  
															<dd id="payment_dateOfPayment">${paymentOutDetails.paymentOutInfo.maturityDate}</dd>

															<dt>Amount</dt>
															<dd id="payment_amount">${paymentOutDetails.paymentOutInfo.amount}</dd>

															<dt>Buy currency</dt>
															<dd id="payment_buyCurrency">${paymentOutDetails.paymentOutInfo.buyCurrency}</dd>

															<dt>Country of beneficiary</dt>
															<%-- <dd id="payment_countryOfBeneficiary">${paymentOutDetails.paymentOutInfo.countryOfBeneficiary}</dd> --%>
															<c:choose>
																		<c:when
																			test="${paymentOutDetails.paymentOutInfo.countryOfBeneficiaryFullName == '------'}">
																			<dd id="payment_countryOfBeneficiary">${paymentOutDetails.paymentOutInfo.countryOfBeneficiaryFullName}</dd>
																		</c:when>
																		<c:otherwise>
																			<dd id="payment_countryOfBeneficiary">${paymentOutDetails.paymentOutInfo.countryOfBeneficiaryFullName} (${paymentOutDetails.paymentOutInfo.countryOfBeneficiary})</dd>
																		</c:otherwise>
															</c:choose> 
															
															<dt>Estimated transaction value</dt>
															<dd id="account_estimatedTxnValue">${paymentOutDetails.account.estimTransValue}</dd>
				                                               
															<dt>Reversal</dt>
															<dd id="is_reversal">${paymentOutDetails.paymentOutInfo.isDeleted}</dd>
															
														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Beneficiary</dt>
															<dd class="wordwrap" id="payment_beneficiaryName">${paymentOutDetails.paymentOutInfo.beneficiaryName}</dd>

															<dt>Reason for transfer</dt>
															<dd id="payment_reasonForTransfer">${paymentOutDetails.paymentOutInfo.reasonForTransfer}</dd>

															<dt>Registered reason for trade</dt>
															<dd id="account_purposeOfTxn">${paymentOutDetails.account.purposeOfTran}</dd>

															<dt>Watchlists</dt>
															<dd>
																<ul id="contactWatchlist">
																	<%-- <c:forEach var="watchlist"
																		items="${ paymentOutDetails.contactWatchlist.watchlistData}"
																		varStatus="loop">
																		<li>${watchlist.name}</li>
																	</c:forEach> --%>
																	<c:forEach var="watchlistRes"
																					items="${ paymentOutDetails.contactWatchlist.watchlistData}"
																					varStatus="loop">
																			<c:if test="${watchlistRes.value}">
																				<li>${watchlistRes.name}</li>
																			</c:if>																		
																	</c:forEach>
																</ul>
															</dd>
															
															<dt>Reversal Date</dt>
															<dd id="reversal_date">${paymentOutDetails.paymentOutInfo.updatedOn}</dd>
															
															<dt>Third Party ?</dt>
															<c:choose>
				                                            	<c:when test="${paymentOutDetails.paymentOutInfo.thirdPartyPayment == true}">
																	<dd id="third_party_flag">Yes</dd> 
																</c:when>
																<c:otherwise>
																	<dd id="third_party_flag">No</dd>
																</c:otherwise>
															</c:choose>

														</dl>
													</div>
													
															<div class="grid__col--12 grid__col--pad-left alertComplianceLog">
																<dl>														 
																<dt>Compliance log</dt><br>
  																<dd id="paymentOut_compliance_log" class="wordwrap">${paymentOutDetails.alertComplianceLog}</dd>
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
															onclick="viewMoreLoadData(); showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER','FraugsterChart');" class="material-icons">add</i></a>
													</li>
													<li class="quick-control__control--close-all"><a
														href="javascript:void(0);" data-ot="Close all checks info"><i
															onclick="viewMoreResetData()" class="material-icons">close</i></a>
													</li>
												</ul>
											</div>
											<%-- 					<div class="accordion__section">

	<div class="accordion__header" id ="contact_blacklist_indicator">
		<a href="#"><i class="material-icons">add</i>Blacklist Contact
			<c:if test= "${paymentOutDetails.blacklist.contactBlacklist.failCount gt 0}">
				<span id ="contact_blacklist_negative" class="indicator--negative">${paymentOutDetails.blacklist.contactBlacklist.failCount}</span>
			</c:if>
			<c:if test="${paymentOutDetails.blacklist.contactBlacklist.passCount gt 0 }">
				<span id ="contact_blacklist_positive" class="indicator--positive">${paymentOutDetails.blacklist.contactBlacklist.passCount}</span>
			</c:if>
		</a>
	</div>

	<div class="accordion__content">
	
			<table class="fixed">
				
			<thead>
				<tr>
					<!-- <th class="center">Entity</th> -->
					<th class="center">IP Blacklist</th>
					<th class="center">Email Blacklist</th>
					<th class="center">Phone Blacklist</th>
					<th class="center">Overall Status</th>
				</tr>
			</thead>
			<tbody id="blacklist_contact">
			<c:forEach var="blacklist"	items="${paymentOutDetails.blacklist.blacklists}">
				<tr>
				<td hidden="hidden">${paymentOutDetails.blacklist.contactBlacklist.id}</td>
					<!-- <td>CONTACT</td> -->
					<c:choose>
								<c:when test="${paymentOutDetails.blacklist.contactBlacklist.ip}">
									<td class="yes-cell"><i class="material-icons">check</i></td>
								</c:when>
								<c:otherwise>
									<td class="no-cell"><i class="material-icons">clear</i></td>
								</c:otherwise>
					</c:choose>
					<c:choose>
							<c:when test="${paymentOutDetails.blacklist.contactBlacklist.email}">
								<td class="yes-cell"><i class="material-icons">check</i></td>
							</c:when>
							<c:otherwise>
								<td class="no-cell"><i class="material-icons">clear</i></td>
							</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when	test="${paymentOutDetails.blacklist.contactBlacklist.phone}">
							<td class="yes-cell"><i class="material-icons">check</i></td>
						</c:when>
						<c:otherwise>
							<td class="no-cell"><i class="material-icons">clear</i></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when	test="${paymentOutDetails.blacklist.contactBlacklist.status}">
							<td class="yes-cell"><i class="material-icons">check</i></td>
						</c:when>
						<c:otherwise>
							<td class="no-cell"><i class="material-icons">clear</i></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</tbody>
		</table>

	</div>
</div> --%>
											<div class="accordion__section">
												<div class="accordion__header"
													id="beneficiary_blacklist_indicator">
													<a href="#"><i class="material-icons">add</i>Blacklist
														Beneficiary 
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
														<c:when test="${paymentOutDetails.blacklist.benficiaryBlacklist.isRequired}">
														<c:if
															test="${paymentOutDetails.blacklist.benficiaryBlacklist.failCount gt 0}">
															<span id="beneficiary_blacklist_negative"
																class="indicator--negative">${paymentOutDetails.blacklist.benficiaryBlacklist.failCount}</span>
														</c:if> <c:if
															test="${paymentOutDetails.blacklist.benficiaryBlacklist.passCount gt 0}">
															<span id="beneficiary_blacklist_positive"
																class="indicator--positive">${paymentOutDetails.blacklist.benficiaryBlacklist.passCount}</span>
														</c:if> 
														</c:when>
														</c:choose>
														</a>
												</div>
												<div class="accordion__content">

													<table class="fixed">

														<thead>
															<tr>
																<th class="center">Account Number Blacklist</th>
																<th class="center">Name Blacklist</th>
																 <%-- <c:if test="${not empty paymentOutDetails.blacklist.benficiaryBlacklist.bankName}"> --%>
																<th class="center">Bank Name Blacklist</th>
																<th class="center">Overall Status</th>
															</tr>
														</thead>
														<tbody id="blacklist_beneficiary">
														<!--Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
															<c:when test="${paymentOutDetails.blacklist.benficiaryBlacklist.isRequired}">
																<tr id="blacklist_beneficiary_row">
																	<td hidden="hidden">${paymentOutDetails.blacklist.benficiaryBlacklist.id}</td>
																	<%-- <c:choose>
																		<c:when
																			test="${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose> --%>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber eq 'Not Required'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber eq 'Not Available'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber eq 'false'}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.accountNumber eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentOutDetails.blacklist.benficiaryBlacklist.accNumberMatchedData }</td>																
																	</c:if>
																	<%-- <c:choose>
																		<c:when
																			test="${paymentOutDetails.blacklist.benficiaryBlacklist.name}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose> --%>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.name eq 'Not Required'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.name}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.name eq 'Not Available'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.name}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.name eq 'false'}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.name eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentOutDetails.blacklist.benficiaryBlacklist.nameMatchedData }</td>																
																	</c:if>
																	
																	
                                                                    <c:if test="${not empty paymentOutDetails.blacklist.benficiaryBlacklist.bankName}">
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.bankName eq 'Not Required'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.bankName}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.bankName eq 'Not Available'}">
																		<td class = "nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.bankName}</td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.bankName eq 'false'}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>																
																	</c:if>
																	<c:if test="${paymentOutDetails.blacklist.benficiaryBlacklist.bankName eq 'true'}">
																		<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																		<br>${paymentOutDetails.blacklist.benficiaryBlacklist.bankNameMatchedData }</td>																
																	</c:if>
																	  </c:if>
																	 
																	 <c:if test="${empty paymentOutDetails.blacklist.benficiaryBlacklist.bankName}">
																	<td class = "nowrap center">Not Performed</td>
														            </c:if>
																	<c:choose>
																		<c:when
																			test="${paymentOutDetails.blacklist.benficiaryBlacklist.status}">
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
																	<td hidden="hidden">${paymentOutDetails.blacklist.benficiaryBlacklist.id}</td>
																	<td class="nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.statusValue}</td>
																	<td class="nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.statusValue}</td>
																	<td class="nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.statusValue}</td>
																	<td class="nowrap center">${paymentOutDetails.blacklist.benficiaryBlacklist.statusValue}</td>
																</tr>															
															</c:otherwise>
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
                                                    <br>
                                                    <form>
														<p class="flush-margin">
															<input id="payout_blacklist_recheck" type="button"
																onClick="resendBlacklistCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
															<object id="gifloaderforblacklistresend"
																class="ajax-loader-lock-toggle" height="50" width="50"
																data="resources/img/ajax-loader.svg"
																preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16"
																type="image/svg+xml" style="visibility: hidden;">
															</object>
															<span id="blacklistChecks_resend_error_field"
																class="form__field-error" hidden="hidden"> Some
																kind of error message. <a href="#" class="">Back to
																	summary</a>
															</span>
														</p>
													</form>
												</div>
											</div>
											
											<!-- AT-3658 -->
											<div id="accordion-section-paymentreference"
												class="accordion__section">

												<div id="payOutDetails_paymentReferencecheck_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Reference
														Check 
														<c:choose>	
															<c:when test="${paymentOutDetails.paymentReference.isRequired}">													
															<c:if
																test="${paymentOutDetails.paymentReference.failCount gt 0}">
																<span id="paymentReferenceCheck_negative"
																	class="indicator--negative">${paymentOutDetails.paymentReference.failCount}</span>
															</c:if> <c:if
																test="${paymentOutDetails.paymentReference.passCount gt 0}">
																<span id="paymentReferenceCheck_positive"
																	class="indicator--positive">${paymentOutDetails.paymentReference.passCount}</span>
															</c:if>
															</c:when>
														</c:choose>
														
														</a>
												</div>
												
												<div class="accordion__content">

													<table >
														<thead>
															<tr>
																<th class="tight-cell">Check date/time</th>
																<th class="tight-cell">Payment Reference</th>
																<th class="tight-cell">Matched Keyword</th>
																<th class="tight-cell">Closeness Score</th>
																<th class="tight-cell">Overall Status</th>
															</tr>
														</thead>
														<tbody id="paymentReference">
														
														<c:choose>
															<c:when test="${paymentOutDetails.paymentReference.isRequired}">
															
																<tr class="talign">
																	<td class="wrap">${paymentOutDetails.paymentReference.checkedOn}</td>
																	<td class="wrapword">${paymentOutDetails.paymentReference.paymentReference}</td>
																	<td class="wrapword">${paymentOutDetails.paymentReference.matchedKeyword}</td>
																	<td class="nowrap">${paymentOutDetails.paymentReference.closenessScore}</td>
																	<c:choose>
																		<c:when
																			test="${paymentOutDetails.paymentReference.overallStatus == 'Pass'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:when test="${paymentOutDetails.paymentReference.overallStatus == 'NA'}">
																			<td>NOT PERFORMED</td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose>
																</tr>
															</c:when>
															<c:otherwise>
																	<tr class="talign">
																		<td class="wrap">${paymentOutDetails.paymentReference.checkedOn}</td>
																		<td class="wrapword">${paymentOutDetails.paymentReference.paymentReference}</td>
																		<td class="wrapword">${paymentOutDetails.paymentReference.matchedKeyword}</td>
																		<td class="nowrap">${paymentOutDetails.paymentReference.closenessScore}</td>
																		<c:choose>
																		<c:when
																			test="${paymentOutDetails.paymentReference.overallStatus == 'Pass'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:when test="${paymentOutDetails.paymentReference.overallStatus == 'NA'}">
																			<td>NOT PERFORMED</td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose>
																	</tr>																													
															</c:otherwise>
														</c:choose>
														</tbody>
													</table>
													<a href="javascript:void(0);" id="viewMoreDetailsPayOut_PaymentReferenceChk"  class="load-more space-after"  onclick="viewMoreDetails('BLACKLIST_PAY_REF' ,'paymentReference','paymentReferenceCheckTotalRecordsPayOutId','leftRecordsPayOutIdPaymentReferenceChk','BENEFICIARY');">
											VIEW <span class="load-more__extra" id = "viewMorePayOut_PaymentReferenceChkId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsPayOutIdPaymentReferenceChk" > </span>
		</a>
													<br>
													<form>
														<p class="flush-margin">
															<input id="payout_paymentreference_recheck" type="button"
																onClick="resendPaymentReferenceCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
															<object id="gifloaderforpaymentreferenceresend"
																class="ajax-loader-lock-toggle" height="50" width="50"
																data="resources/img/ajax-loader.svg"
																preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16"
																type="image/svg+xml" style="visibility: hidden;">
															</object>
															<span id="paymentReferenceChecks_resend_error_field"
																class="form__field-error" hidden="hidden"> Some
																kind of error message. <a href="#" class="">Back to
																	summary</a>
															</span>
														</p>
													</form>

												</div>

											</div>
											
											<div id="accordion-section-country"
												class="accordion__section">

												<div id="payOutDetails_countrycheck_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Country
														Check 
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>	
															<c:when test="${paymentOutDetails.customCheck.countryCheck.isRequired}">													
															<c:if
																test="${paymentOutDetails.customCheck.countryCheck.failCount gt 0}">
																<span id="countryCheck_negative"
																	class="indicator--negative">${paymentOutDetails.customCheck.countryCheck.failCount}</span>
															</c:if> <c:if
																test="${paymentOutDetails.customCheck.countryCheck.passCount gt 0}">
																<span id="countryCheck_positive"
																	class="indicator--positive">${paymentOutDetails.customCheck.countryCheck.passCount}</span>
															</c:if>
															</c:when>
														</c:choose>
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
														<tbody id="countryCheck_beneficiary">
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
															<c:when test="${paymentOutDetails.customCheck.countryCheck.isRequired}">
															<c:if
																test="${paymentOutDetails.customCheck.countryCheck.id > 0 }">
																<tr class="talign">
																	<td class="nowrap">${paymentOutDetails.customCheck.countryCheck.checkedOn}</td>
																	<td class="nowrap">${paymentOutDetails.customCheck.countryCheck.status}
																	${paymentOutDetails.customCheck.countryCheck.riskLevel}</td>
																</tr>
															</c:if>
															</c:when>
															<c:otherwise>
																	<tr class="talign">
																		<td class="nowrap">${paymentOutDetails.customCheck.countryCheck.checkedOn}</td>
																		<td class="nowrap">${paymentOutDetails.customCheck.countryCheck.statusValue}</td>
																	</tr>																													
															</c:otherwise>
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
													<a href="javascript:void(0);" id="viewMoreDetailsPayOut_CountryChk"  class="load-more space-after"  onclick="viewMoreDetails('COUNTRYCHECK' ,'countryCheck_beneficiary','countryCheckTotalRecordsPayOutId','leftRecordsPayOutIdCountryChk','BENEFICIARY');">
											VIEW <span class="load-more__extra" id = "viewMorePayOut_CountryChkId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsPayOutIdCountryChk" > </span>
		</a>

												</div>

											</div>
											<div id="accordion-section-custom" class="accordion__section">

												<div id="payOutDetails_customchecks_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Custom
														checks
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
														<c:choose>
															<c:when test="${paymentOutDetails.customCheck.paymentOutCustomCheck.isRequired}">
																<c:if
																	test="${paymentOutDetails.customCheck.paymentOutCustomCheck.failCount gt 0}">
																	<span id="beneficiary_customCheck_negative"
																		class="indicator--negative">${paymentOutDetails.customCheck.paymentOutCustomCheck.failCount}</span>
																</c:if> <c:if
																	test="${paymentOutDetails.customCheck.paymentOutCustomCheck.passCount gt 0}">
																	<span id="beneficiary_customCheck_positive"
																		class="indicator--positive">${paymentOutDetails.customCheck.paymentOutCustomCheck.passCount}</span>
																</c:if>
															</c:when> 
														</c:choose>
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
															<c:when test="${paymentOutDetails.customCheck.paymentOutCustomCheck.isRequired}">
																	<c:if
																		test="${paymentOutDetails.customCheck.paymentOutCustomCheck.id > 0 }">
																		<tr class="talign">
																			<td class="nowrap">${paymentOutDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																			<td>Velocity Check</td>
																			<td>
																				<ul>
																				
																			<li class="breakwordHeader">Beneficary Check :
																			${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck}
																						<c:forEach items="${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneTradeAccountid}" var="tradeId" varStatus="loop">
  																						 <a onclick="openPaymentDetail('${tradeId}','${paymentOutDetails.paymentOutInfo.beneAccountNumber}')">${tradeId}</a>
  																						<c:if test="${fn:length(paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneTradeAccountid) gt loop.index+1}">
  																						 <% out.print(",");%>
  																						 </c:if>
																						</c:forEach>
																					  	<c:if test="${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck=='FAIL(With account :'}">
																					    <% out.print(")"); %>
																					</c:if>
																				</li>
																				<li>Amount Check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.permittedAmoutcheck}</li>
																				<li>No of transaction check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.noOffundsoutTxn}</li>
																				</ul>
																			</td>
																		</tr>
																		<tr class="talign">
																			<td class="nowrap"></td>
																			<td>Whitelist Check</td>
																			<td><ul>
																			<li>Currency Check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.currency}</li>
																				<li>Amount range check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.amoutRange}</li>
																				<%-- <li>Third party check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.thirdParty}</li> --%>
																				<li>Reason of transfer check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.reasonOfTransfer}</li>
																			</ul>
																			</td>
																		</tr>
																		 <!-- 	Add for AT-3349 EUPOIDoc Check -->
																		<tr class="talign">
																			<td class="nowrap"></td>
																			<td>EUPOIDoc Check</td>
																			<td><ul>
																				<li>Status :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.euPoiCheck.status}</li>
																			</ul>
																			</td>
																		</tr>
																		<%--Add for AT-3161 New Custom Rule Fraud Predict --%>
																		<c:choose>
																			<c:when test="${paymentOutDetails.customRuleFPFlag eq 'true'}">
																				<tr class="talign">
																					<td class="nowrap"></td>
																						<td>FraudPredict Check</td>
																						<td><ul>
																							<li>Status :
																								${paymentOutDetails.customCheck.paymentOutCustomCheck.fraudPredictStatus}</li>
																						</ul>	
																					</td>
																				</tr>
																			</c:when>
																		</c:choose>
									                                  
																	</c:if>
																</c:when>
																<c:otherwise>
																	<c:if
																		test="${paymentOutDetails.customCheck.paymentOutCustomCheck.id > 0 }">
																		<tr class="talign">
																			<td class="nowrap">${paymentOutDetails.customCheck.paymentOutCustomCheck.checkedOn}</td>
																			<td>Velocity Check</td>
																			<td>
																				<ul>
																			<li>Beneficary Check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck}
																					 	<c:forEach items="${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneTradeAccountid}" var="tradeId" varStatus="loop">
  																						 <a onclick="openPaymentDetail('${tradeId}','${paymentOutDetails.paymentOutInfo.beneAccountNumber}')">${tradeId}</a>
  																					<c:if test="${fn:length(paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneTradeAccountid) gt loop.index+1}">
  																						 <% out.print(","); %>
  																						 </c:if>
																						</c:forEach>
																						
																					<c:if test="${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck=='FAIL(With account :'}">
																					<%out.print(")"); %>
																						</c:if>
																						
																			</li>
																				<li>Amount Check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.permittedAmoutcheck}</li>
																				<li>No of transaction check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.noOffundsoutTxn}</li>
																				</ul>
																			</td>
																		</tr>
																		<tr class="talign">
																			<td class="nowrap"></td>
																			<td>Whitelist Check</td>
																			<td><ul>
																			<li>Currency Check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.currency}</li>
																				<li>Amount range check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.amoutRange}</li>
																				<%-- <li>Third party check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.thirdParty}</li> --%>
																				<li>Reason of transfer check :
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.whiteListCheck.reasonOfTransfer}</li>
																			</ul>
																			</td>
																		</tr>
																		<!-- 	Add for AT-3349 EUPOIDoc Check -->
																		<tr class="talign">
																			<td class="nowrap"></td>
																			<td>EUPOIDoc Check</td>
																			<td><ul>
																				<li>Status:
																				${paymentOutDetails.customCheck.paymentOutCustomCheck.euPoiCheck.status}</li>
																			</ul>
																			</td>
																		</tr>
																		<%--Add for AT-3161 New Custom Rule Fraud Predict --%>
																		<c:choose>
																			<c:when test="${paymentOutDetails.customRuleFPFlag eq 'true'}">
																				<tr class="talign">
																					<td class="nowrap"></td>
																					<td>FraudPredict Check</td>
																						<td><ul>
																							<li>Status :
																								${paymentOutDetails.customCheck.paymentOutCustomCheck.fraudPredictStatus}</li>
																						</ul>	
																					</td>
																				</tr>
																			</c:when>
																		</c:choose>
																		
																	</c:if>															
																</c:otherwise>															
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</tbody>
													</table>
													 <a href="javascript:void(0);" id="viewMoreDetailsPayOut_CusChk"  class="load-more space-after"  onclick="viewMoreDetails('VELOCITYCHECK' ,'customChecks','customCheckTotalRecordsPayOutId','leftRecordsPayOutIdCusChk','BENEFICIARY');">
											VIEW <span class="load-more__extra" id = "viewMorePayOut_CusChkId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsPayOutIdCusChk" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading...">
											<object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
		</a>

													<form>
														<p class="flush-margin">
															<input id="payout_customChecks_recheck" type="button" onClick="resendCustomCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																<object id="gifloaderforCustomcheckresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="payout_customChecks_recheck" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																
																<input id="account_AccountWhiteList" type="button" class="button--primary custom-field float--right" 
																data-modal="modal-original-summary" value="WHITE LIST DATA" onclick="showAccountWhiteListData('${paymentOutDetails.currentContact.accountId}' , '${paymentOutDetails.account.orgCode}')">
														
														</p>
													</form>

												</div>

											</div>
											<div class="accordion__section">

												<div class="accordion__header"
													id="payOutDetails_sanctioncon_indicatior">
													<a href="#"><i class="material-icons">add</i>Sanctions
														Contact 
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:choose>
															<c:when test="${paymentOutDetails.sanction.contactSanction.isRequired}">
																<c:if
																	test="${paymentOutDetails.sanction.contactSanction.failCount gt 0 }">
																	<span id="payoutDetails_contactSanctionFail"
																		class="indicator--negative">${paymentOutDetails.sanction.contactSanction.failCount}</span>
																</c:if> <c:if
																	test="${paymentOutDetails.sanction.contactSanction.passCount gt 0}">
																	<span id="payoutDetails_contactSanctionPass"
																		class="indicator--positive">${paymentOutDetails.sanction.contactSanction.passCount}</span>
																</c:if>
															</c:when>
														</c:choose>
														<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
														</a>
												</div>

												<div class="accordion__content">

													<form>

														<table>
															<thead>
																<tr>
																	<!-- <th class="chk-cell">Select</th>
						<th >Entity</th> -->
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

																<%-- <c:forEach var="sanction" items="${paymentOutDetails.sanction.sanctions }"> --%>
																
																<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
																<c:choose>
																	<c:when test="${paymentOutDetails.sanction.contactSanction.isRequired}">
																		<tr>
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.eventServiceLogId}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.entityType}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.entityId}</td>
																			<!-- <td class="center"><input type="checkbox"/></td> -->
																			<!-- <td>CONTACT</td> -->
																			<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																			<td>${paymentOutDetails.sanction.contactSanction.updatedOn}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.contactSanction.updatedBy}</td>
																			<td><a href="javascript:void(0);"
																				onclick="showProviderResponse('${paymentOutDetails.sanction.contactSanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.contactSanction.sanctionId}</a></td>
																			<td class="nowrap">${paymentOutDetails.sanction.contactSanction.ofacList}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.contactSanction.worldCheck}</td>
																			<c:choose>
																				<c:when
																					test="${paymentOutDetails.sanction.contactSanction.status }">
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
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.eventServiceLogId}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.entityType}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.contactSanction.entityId}</td>
																			<!-- <td class="center"><input type="checkbox"/></td> -->
																			<!-- <td>CONTACT</td> -->
																			<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																			<td>${paymentOutDetails.sanction.contactSanction.updatedOn}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.contactSanction.updatedBy}</td>
																			<td><a href="javascript:void(0);"
																				onclick="showProviderResponse('${paymentOutDetails.sanction.contactSanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.contactSanction.sanctionId}</a></td>
																			<td class="nowrap">${paymentOutDetails.sanction.contactSanction.ofacList}</td>
																			<td class="nowrap" style="padding: 2px">${paymentOutDetails.sanction.contactSanction.worldCheck}</td>
																			<td class="nowrap" style="padding: inherit;">${paymentOutDetails.sanction.contactSanction.statusValue}</td>
																		</tr>
																	</c:otherwise>
																</c:choose>
																<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI -->
																
																<%-- <tr>
																	<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.eventId}</td>
																	<td class="center"><input type="checkbox"/></td>
																	<td>BENEFICIARY</td>
																	<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.updatedOn}</td>
																	<td>${paymentOutDetails.sanction.beneficiarySanction.updatedBy}</td>
																	<td>${paymentOutDetails.sanction.beneficiarySanction.sanctionId}</td>
																	<td>${paymentOutDetails.sanction.beneficiarySanction.ofacList}</td>
																	<td>${paymentOutDetails.sanction.beneficiarySanction.worldCheck}</td>
																	<c:choose>
																		<c:when test="${paymentOutDetails.sanction.beneficiarySanction.status }">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose>
					</tr>
					<tr>
																	<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.eventId}</td>
																	<td class="center"><input type="checkbox"/></td>
																	<td>BANK</td>
																	<td class="nowrap">${paymentOutDetails.sanction.bankSanction.updatedOn}</td>
																	<td>${paymentOutDetails.sanction.bankSanction.updatedBy}</td>
																	<td>${paymentOutDetails.sanction.bankSanction.sanctionId}</td>
																	<td>${paymentOutDetails.sanction.bankSanction.ofacList}</td>
																	<td>${paymentOutDetails.sanction.bankSanction.worldCheck}</td>
																	<c:choose>
																		<c:when test="${paymentOutDetails.sanction.bankSanction.status }">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose>
					</tr> --%>
																<%-- 	</c:forEach> --%>

																<!-- <tr>
						<td class="center"><input type="checkbox"/></td>
						<td>12/06/2016 12:44:01</td>
						<td>John Smith</td>
						<td>987654321</td>
						<td>No match found</td>
						<td>High Risk</td>
						<td>No match found</td>
						<td class="no-cell"><i class="material-icons">clear</i></td>
					</tr>
					<tr>
						<td class="center"><input type="checkbox"/></td>
						<td>12/06/2016 12:44:01</td>
						<td>Andrew Mulford</td>
						<td>111222333</td>
						<td>No match found</td>
						<td>High Risk</td>
						<td>No match found</td>
						<td class="no-cell"><i class="material-icons">clear</i></td>
					</tr> -->
															</tbody>
														</table>
														<a href="javascript:void(0);"
															id="viewMoreDetailsPayOut_Sanc_contact"
															class="load-more space-after"
															onclick="viewMoreDetails('SANCTION' ,'sanctions_contact','sanctionTotalRecordsPayOutId_contact','leftRecordsPayOutIdSanc_contact','CONTACT');">
															VIEW <span class="load-more__extra"
															id="viewMorePayOut_SancId_contact"> </span> MORE <span
															class="load-more__left"
															id="leftRecordsPayOutIdSanc_contact"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
															  <object id="gifloaderforviewmoresanctioncontact" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 														</a>

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


																<p class="right">

																    <span id="updateSanction_contact_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																	<input type="button" id="updateSanction_contact"
																		onClick="updateSanction(${paymentOutDetails.sanction.contactSanction.entityId},'${paymentOutDetails.sanction.contactSanction.entityType}');"
																		class="<c:out value="${buttonClass}"/> sanction-field" value="Apply"
																		<c:out value="${buttonDisable}"/> />
																		 <object id="gifloaderforupdatesanctioncontact" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																	<!-- 	<small class="button--supporting"><a href="#">Lock this record</a> to own it</small> -->
																	<!-- <img  class="ajax-loader space-next" src="/img/ajax-loader.svg" width="16" height="16" alt="Loading..."/> -->

																</p>


															</fieldset>

														</div>

													</form>

													<form>
														<p class="flush-margin">
															<input type="button" id="sanction_recheck_contact"
																onClick="resendSanction(${paymentOutDetails.sanction.contactSanction.entityId},'${paymentOutDetails.sanction.contactSanction.entityType}');"
																class="<c:out value="${buttonClass}"/> sanction-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																 <object id="gifloaderforResendSanction" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="sanction_recheck_contact_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
														</p>
													</form>

												</div>

											</div>

											<div class="accordion__section">
												<div class="accordion__header"
													id="payOutDetails_sanctionben_indicatior">
													<a href="#"><i class="material-icons">add</i>Sanctions
														Beneficiary
														
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
														<c:choose>
															<c:when test="${paymentOutDetails.sanction.beneficiarySanction.isRequired}">
																<c:if
																	test="${paymentOutDetails.sanction.beneficiarySanction.failCount gt 0 }">
																	<span id="payoutDetails_beneficiarySanctionFail"
																		class="indicator--negative">${paymentOutDetails.sanction.beneficiarySanction.failCount}</span>
																</c:if> <c:if
																	test="${paymentOutDetails.sanction.beneficiarySanction.passCount gt 0}">
																	<span id="payoutDetails_beneficiarySanctionPass"
																		class="indicator--positive">${paymentOutDetails.sanction.beneficiarySanction.passCount}</span>
																</c:if> 
															</c:when>
														</c:choose>
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
															<tbody id="sanctions_beneficiary">
															<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
															<c:choose>
																<c:when test="${paymentOutDetails.sanction.beneficiarySanction.isRequired}">
																	<tr>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.eventServiceLogId}</td>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.entityType}</td>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.entityId}</td>
																		<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																		<td>${paymentOutDetails.sanction.beneficiarySanction.updatedOn}</td>
																		<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.updatedBy}</td>
																		<td class="nowrap"><a href="javascript:void(0);"
																			onclick="showProviderResponse('${paymentOutDetails.sanction.beneficiarySanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.beneficiarySanction.sanctionId}</a></td>
																		<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.ofacList}</td>
																		<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.worldCheck}</td>
																		<c:choose>
																			<c:when
																				test="${paymentOutDetails.sanction.beneficiarySanction.status }">
																				<td id =statusBenf class="yes-cell"><i class="material-icons">check</i></td>
																			</c:when>
																			<c:otherwise>
																				<td id =statusBenf class="no-cell"><i class="material-icons">clear</i></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.eventServiceLogId}</td>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.entityType}</td>
																		<td hidden="hidden">${paymentOutDetails.sanction.beneficiarySanction.entityId}</td>
																		<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																		<td>${paymentOutDetails.sanction.beneficiarySanction.updatedOn}</td>
																		<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.updatedBy}</td>
																		<td class="nowrap"><a href="javascript:void(0);"
																			onclick="showProviderResponse('${paymentOutDetails.sanction.beneficiarySanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.beneficiarySanction.sanctionId}</a></td>
																		<td class="nowrap">${paymentOutDetails.sanction.beneficiarySanction.ofacList}</td>
																		<td class="nowrap" style="padding: 2px">${paymentOutDetails.sanction.beneficiarySanction.worldCheck}</td>
																		<td class="nowrap" style="padding: inherit;">${paymentOutDetails.sanction.beneficiarySanction.statusValue}</td>																		
																	</tr>
																</c:otherwise>
															</c:choose>
															<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
															</tbody>
														</table>

														<a href="javascript:void(0);"
															id="viewMoreDetailsPayOut_Sanc_beneficiary"
															class="load-more space-after"
															onclick="viewMoreDetails('SANCTION' ,'sanctions_beneficiary','sanctionTotalRecordsPayOutId_beneficiary','leftRecordsPayOutIdSanc_beneficiary','BENEFICIARY');">
															VIEW <span class="load-more__extra"
															id="viewMorePayOut_SancId_beneficiary"> </span> MORE <span
															class="load-more__left"
															id="leftRecordsPayOutIdSanc_beneficiary"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
															<!-- <object id="gifloaderforSanctionviewmorebeneficiary" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object>  -->
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
																									for="rad-sanction-beneficiary-field-2">
																										<input id="rad-sanction-beneficiary-field-2"
																										value="ofaclist" type="radio"
																										name="updateField_beneficiary" /> OFAC List
																								</label></li>
																								<li><label
																									for="rad-sanction-beneficiary-field-3">
																										<input id="rad-sanction-beneficiary-field-3"
																										value="worldcheck" type="radio"
																										name="updateField_beneficiary" /> World Check
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
																									for="rad-sanction-beneficiary-fieldval-1">
																										<input
																										id="rad-sanction-beneficiary-fieldval-1"
																										value="Confirmed hit" type="radio"
																										name="updateField_value_beneficiary" /> Confirmed hit
																								</label></li>
																								<li><label
																									for="rad-sanction-beneficiary-fieldval-2">
																										<input
																										id="rad-sanction-beneficiary-fieldval-2"
																										value="Safe" type="radio"
																										name="updateField_value_beneficiary" /> Safe
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


																<p class="right">

																	<span id="updateSanction_beneficiary_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																	<input type="button" id="updateSanction_beneficiary"
																		onClick="updateSanction(${paymentOutDetails.sanction.beneficiarySanction.entityId},'${paymentOutDetails.sanction.beneficiarySanction.entityType}');"
																		class="<c:out value="${buttonClass}"/> sanction-field" value="Apply"
																		<c:out value="${buttonDisable}"/> />
																		<object id="gifloaderforupdatesanctionbenefeciary" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 


																</p>


															</fieldset>

														</div>

													</form>

													<form>
														<p class="flush-margin">
															<input type="button" id="sanction_recheck_beneficiary"
																onClick="resendSanction(${paymentOutDetails.sanction.beneficiarySanction.entityId},'${paymentOutDetails.sanction.beneficiarySanction.entityType}');"
																class="<c:out value="${buttonClass}"/> sanction-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																 <object id="gifloaderforResendSanctionbenefeciary" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="sanction_recheck_beneficiary_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
														</p>
													</form>

												</div>

											</div>

											<div class="accordion__section">

												<!-- <div class="accordion__header">
		<a href="#"><i class="material-icons">add</i>Sanctions Bank<span class="indicator--negative">3</span></a>
	</div> -->
												<div class="accordion__header"
													id="payOutDetails_sanctionbank_indicatior">
													<a href="#"><i class="material-icons">add</i>Sanctions
														Bank 
														<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
														<c:choose>
															<c:when test="${paymentOutDetails.sanction.bankSanction.isRequired}">
																<c:if
																	test="${paymentOutDetails.sanction.bankSanction.failCount gt 0 }">
																	<span id="payoutDetails_bankSanctionFail"
																		class="indicator--negative">${paymentOutDetails.sanction.bankSanction.failCount}</span>
																</c:if> <c:if
																	test="${paymentOutDetails.sanction.bankSanction.passCount gt 0}">
																	<span id="payoutDetails_bankSanctionPass"
																		class="indicator--positive">${paymentOutDetails.sanction.bankSanction.passCount}</span>
																</c:if>
															</c:when> 
														</c:choose>
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
															<tbody id="sanctions_bank">
															<!-- Condition added by Vishal J to show NOT REQUIRED status on UI --> 
																<c:choose>
																	<c:when test="${paymentOutDetails.sanction.bankSanction.isRequired}">
																		<tr>
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.eventServiceLogId}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.entityType}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.entityId}</td>
																			<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																			<td>${paymentOutDetails.sanction.bankSanction.updatedOn}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.bankSanction.updatedBy}</td>
																			<td class="nowrap"><a href="javascript:void(0);"
																				onclick="showProviderResponse('${paymentOutDetails.sanction.bankSanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.bankSanction.sanctionId}</a></td>
																			<td class="nowrap">${paymentOutDetails.sanction.bankSanction.ofacList}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.bankSanction.worldCheck}</td>
																			<c:choose>
																				<c:when
																					test="${paymentOutDetails.sanction.bankSanction.status }">
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
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.eventServiceLogId}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.entityType}</td>
																			<td hidden="hidden">${paymentOutDetails.sanction.bankSanction.entityId}</td>
																			<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																			<td>${paymentOutDetails.sanction.bankSanction.updatedOn}</td>
																			<td class="nowrap">${paymentOutDetails.sanction.bankSanction.updatedBy}</td>
																			<td class="nowrap"><a href="javascript:void(0);"
																				onclick="showProviderResponse('${paymentOutDetails.sanction.bankSanction.eventServiceLogId}','SANCTION')">${paymentOutDetails.sanction.bankSanction.sanctionId}</a></td>
																			<td class="nowrap">${paymentOutDetails.sanction.bankSanction.ofacList}</td>
																			<td class="nowrap" style="padding: inherit;">${paymentOutDetails.sanction.bankSanction.worldCheck}</td>
																			<td class="nowrap" style="padding: inherit;">${paymentOutDetails.sanction.bankSanction.statusValue}</td>																			
																		</tr>
																	</c:otherwise>
																</c:choose>
																<!-- End of - Condition added by Vishal J to show NOT REQUIRED status on UI --> 	
															</tbody>
														</table>

														<a href="javascript:void(0);"
															id="viewMoreDetailsPayOut_Sanc_bank"
															class="load-more space-after"
															onclick="viewMoreDetails('SANCTION' ,'sanctions_bank','sanctionTotalRecordsPayOutId_bank','leftRecordsPayOutIdSanc_bank','BANK');">
															VIEW <span class="load-more__extra"
															id="viewMorePayOut_SancId_bank"> </span> MORE <span
															class="load-more__left" id="leftRecordsPayOutIdSanc_bank">
														</span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
															<!-- <object id="gifloaderforSanctionviewmorebank" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object>  -->
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

																								<li><label for="rad-sanction-bank-field-2">
																										<input id="rad-sanction-bank-field-2"
																										value="ofaclist" type="radio"
																										name="updateField_bank" /> OFAC List
																								</label></li>
																								<li><label for="rad-sanction-bank-field-3">
																										<input id="rad-sanction-bank-field-3"
																										value="worldcheck" type="radio"
																										name="updateField_bank" /> World Check
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
																									for="rad-sanction-bank-fieldval-1"> <input
																										id="rad-sanction-bank-fieldval-1"
																										value="Confirmed hit" type="radio"
																										name="updateField_value_bank" /> Confirmed hit
																								</label></li>
																								<li><label
																									for="rad-sanction-bank-fieldval-2"> <input
																										id="rad-sanction-bank-fieldval-2"
																										value="Safe" type="radio"
																										name="updateField_value_bank" /> Safe
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


																<p class="right">

																	<span id="updateSanction_bank_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																	<input type="button" id="updateSanction_bank"
																		onClick="updateSanction(${paymentOutDetails.sanction.bankSanction.entityId},'${paymentOutDetails.sanction.bankSanction.entityType}');"
																		class="<c:out value="${buttonClass}"/> sanction-field" value="Apply"
																		<c:out value="${buttonDisable}"/> />
																		<object id="gifloaderforupdatesanctionbank" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 


																</p>


															</fieldset>

														</div>

													</form>

													<form>
														<p class="flush-margin">
															<input type="button" id="sanction_recheck_bank"
																onClick="resendSanction(${paymentOutDetails.sanction.bankSanction.entityId},'${paymentOutDetails.sanction.bankSanction.entityType}');"
																class="<c:out value="${buttonClass}"/> sanction-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
																 <object id="gifloaderforResendSanctionbank" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="sanction_recheck_bank_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
														</p>
													</form>

												</div>

											</div>
											<div class="accordion__section">

												<div id="payOutDetails_fraugster_indicatior"
													class="accordion__header">
													<a href="#" onclick="showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER','FraugsterChart')"><i class="material-icons">add</i>FRAUDPREDICT
														<c:if test="${paymentOutDetails.fraugster.failCount gt 0}">
															<span id="paymentOutDetails_fraugsterFail"
																class="indicator--negative">${paymentOutDetails.fraugster.failCount}</span>
														</c:if> 
														<c:if test="${paymentOutDetails.fraugster.passCount gt 0}">
															<span id="paymentOutDetails_fraugsterPass"
																class="indicator--positive">${paymentOutDetails.fraugster.passCount}</span>
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
																	<c:when test="${paymentOutDetails.fraugster.isRequired}">
																		<tr href="#"  onclick="showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER','FraugsterChart')">
																<td>${paymentOutDetails.fraugster.createdOn }</td>
																<td class="nowrap">${paymentOutDetails.fraugster.updatedBy }</td>
																<td class=""><a href="#"  onclick="showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER')">${paymentOutDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${paymentOutDetails.fraugster.score }</td>
																<c:choose>
																	<c:when test="${paymentOutDetails.fraugster.status }">
																					<td class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
															</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr href="#"  onclick="showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER','FraugsterChart')">
																<td class="nowrap">${paymentOutDetails.fraugster.createdOn }</td>
																<td class="nowrap">${paymentOutDetails.fraugster.updatedBy }</td>
																<td class="nowrap"><a href="#"  onclick="showProviderResponse('${paymentOutDetails.fraugster.id}','FRAUGSTER')">${paymentOutDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${paymentOutDetails.fraugster.score }</td>
																<td class="nowrap">${paymentOutDetails.fraugster.statusValue }</td>
															</tr>	
																	</c:otherwise>
																</c:choose>		
														</tbody>
													</table>
													<a href="javascript:void(0);"
														id="viewMoreDetailsPayOut_Fraug"
														class="load-more space-after"
														onclick="viewMoreDetails('FRAUGSTER' ,'fraugster','fraugsterTotalRecordsPayOutId','leftRecordsPayOutIdFraug','CONTACT');">
														VIEW <span class="load-more__extra"
														id="viewMorePayOut_FraugId"> </span> MORE <span
														class="load-more__left" id="leftRecordsPayOutIdFraug">
													</span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

													<form>
														<p class="flush-margin">
															<input id="payout_fraugster_recheck" type="button"
																onClick="resendFraugsterCheck();"
																class="<c:out value="${buttonClass}"/> custom-field"
																value="Repeat checks" <c:out value="${buttonDisable}"/> />
															<object id="gifloaderforfraugsterresend"
																class="ajax-loader-lock-toggle" height="50" width="50"
																data="resources/img/ajax-loader.svg"
																preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16"
																type="image/svg+xml" style="visibility: hidden;">
															</object>
															<span id="fraugsterChecks_resend_error_field"
																class="form__field-error" hidden="hidden"> Some
																kind of error message. <a href="#" class="">Back to
																	summary</a>
															</span>
														</p>
													</form>
													<div id = "boxpanel-space-before" class="boxpanel space-before">
														<div id="paymentOut-fraugster-chart"></div>
													</div>

												</div>

											</div>
											
											<%-- Added for AT-4306 --%>
											<div class="accordion__section">

												<div  id="payOutDetails_intuition_indicatior"  class="accordion__header">
													<a href="#"><i class="material-icons">add</i> Intuition
															<c:if test="${paymentOutDetails.intuition.failCount gt 0}">
																<span id="payOutDetails_intuitionFail"
																	class="indicator--negative">${paymentOutDetails.intuition.failCount}</span>
															</c:if> 
															<c:if test="${paymentOutDetails.intuition.passCount gt 0}">
																<span id="payOutDetails_intuitionPass"
																	class="indicator--positive">${paymentOutDetails.intuition.passCount}</span>
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
														<tbody id="payOutDetails_intuition">
															<c:choose>
																	<c:when test="${paymentOutDetails.intuition.isRequired}">
																		<tr href="javascript:void(0);" onclick="showProviderResponse('${paymentOutDetails.intuition.id}','TRANSACTION_MONITORING')">
																			<td>${paymentOutDetails.intuition.createdOn}</td>
																			<td class="wrapword">${paymentOutDetails.intuition.updatedBy}</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${paymentOutDetails.intuition.id}','TRANSACTION_MONITORING')">${paymentOutDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${paymentOutDetails.intuition.clientRiskLevel}</td>
																			<td class="nowrap">${paymentOutDetails.intuition.ruleRiskLevel}</td>
																			<td class="nowrap" class="number">${paymentOutDetails.intuition.ruleScore}</td>
																			<td class="nowrap">${paymentOutDetails.intuition.decision}</td>
																		</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td>${paymentOutDetails.intuition.createdOn}</td>
																			<td class="wrapword">${paymentOutDetails.intuition.updatedBy}</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${paymentOutDetails.intuition.id}','TRANSACTION_MONITORING')">${paymentOutDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${paymentOutDetails.intuition.clientRiskLevel}</td>
																			<td class="nowrap">${paymentOutDetails.intuition.ruleRiskLevel}</td>
																			<td class="nowrap" class="number">${paymentOutDetails.intuition.ruleScore}</td>
																			<td id="intuition_status"  class="nowrap">${paymentOutDetails.intuition.decision}</td>
																		</tr>	
																	</c:otherwise>
																</c:choose>															
														</tbody>
													</table>
													
			                                        <a href="javascript:void(0);" id="viewMoreDetails_intuition"  class="load-more space-after"  onclick="viewMoreDetails('TRANSACTION_MONITORING' ,'payOutDetails_intuition','intuitionTotalRecordsId','leftRecordsIdIntuition','ACCOUNT');">
														VIEW <span class="load-more__extra" id = "viewMore_IntuitionId" > </span> MORE
														<span class="load-more__left" id= "leftRecordsIdIntuition" > </span>
													</a>
												</div>

											</div>
											
											<div class="accordion__section">
												<div id="doc_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Attached
														documents <c:if
															test='${fn:length(paymentOutDetails.documents) gt 0 }'>
															<span id="docConunt" class="indicator">${fn:length(paymentOutDetails.documents)}</span>
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
																items="${paymentOutDetails.documents}">
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
															<c:if test="${fn:length(paymentOutDetails.documents) > 0}">
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

																								<c:forEach var="documentList" items="${paymentOutDetails.documentList}"  varStatus="loop">
																									
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
															 		 value="Approve POI" onclick="approvePoiForPaymentOut()"/> 
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
																		onclick="uploadDocument('${paymentOutDetails.currentContact.crmAccountId}','${paymentOutDetails.currentContact.crmContactId}','text-note','file-choose-document','attachDoc','documentType','attach_document_button_id','${paymentOutDetails.account.orgCode}','gifloaderfordocumentloader');" />
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
													id="further_PayOutDetails_indicatior">
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
																<th class="breakwordHeader">Account / Card Number</th>
																<th class="breakwordHeader">Bank Name</th>
																<th class="breakwordHeader">Risk score</th>
															</tr>
														</thead>
														<tbody id="further_paymentInDetails">
															<c:forEach var="payment"
																items="${paymentOutDetails.furtherPaymentDetails.furtherPaymentInDetails}">
																<tr>
															    	<td class="breakword">${payment.tradeContractNumber }</td>
																	<td>${payment.dateOfPayment }</td>
																	<td class="number">${payment.amount }</td>
																	<td>${payment.sellCurrency }</td>
																	<td>${payment.method }</td>
																	<!-- style added to <td> to fit long name properly on UI - Vishal J -->
																	<td class="breakword">${payment.accountName }</td>
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
														id="viewMoreDetailsPayOut_FurPayInDetails"
														class="load-more space-after"
														onclick="viewMoreDetails('PAYMENT_IN' ,'further_paymentInDetails','furPayInDetailsTotalRecordsPayOutId','leftRecordsPayOutIdFurPayInDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayOut_FurPayInDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayOutIdFurPayInDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

													<h3 class="flush-margin">Payments out</h3>

													<table>
														<thead>
															<tr>
															    <th class="breakwordHeader">Contract Number</th>
																<th class="breakwordHeader">Date of payment</th>
																<th>Amount</th>
																<th class="breakwordHeader">Buy currency</th>
																<th>Beneficiary name</th>
																<th class="breakwordHeader">Beneficiary account number</th>
																<th class="breakwordHeader">Bank Name</th>
																<th class="breakwordHeader">Payment Reference</th>
															</tr>
														</thead>
														<tbody id="further_paymentOutDetails">
															<c:forEach var="payment"
																items="${paymentOutDetails.furtherPaymentDetails.furtherPaymentOutDetails}">
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
														id="viewMoreDetailsPayOut_FurPayOutDetails"
														class="load-more space-after"
														onclick="viewMoreDetails('PAYMENT_OUT' ,'further_paymentOutDetails','furPayOutDetailsTotalRecordsPayOutId','leftRecordsPayOutIdFurPayOutDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayOut_FurPayOutDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayOutIdFurPayOutDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

												<!-- AT-4625 card pilot -->
												<!-- changes for AT-4790 -->
												<h3 class="flush-margin">Card Out</h3>

													<table id="cardPilotTblId">
														<thead>
															<tr>
															    <th class="breakwordHeader">Instruction Number</th>
																<th class="breakwordHeader">Date</th>
																<th class="breakwordHeader">Amount</th>
																<th class="breakwordHeader">Currency</th>
																<th class="breakwordHeader">Merchant Name</th>
																<th class="breakwordHeader">Merchant type</th>
																<th class="breakwordHeader">Transaction Type</th>
																<th class="breakwordHeader">GPS transaction reference</th>
															</tr>
														</thead>
														<tbody id="cardPilotTablebody">
															
														</tbody>
													</table>
													<div id="cardPilotLoadingGIF" style="display:none;" >
														<svg width='20px' height='10px' xmlns="http://www.w3.org/2000/svg" viewBox="-40 -40 180 180" preserveAspectRatio="xMidYMid" class="uil-ring">
												 			<rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect>
												  			<circle cx="50" cy="50" r="42.5" stroke-dasharray="173.57299411083608 93.46238144429634" stroke="#2b76b6" fill="none" stroke-width="15">
												   			<animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s">
												   			</animateTransform>
												  			</circle>
														</svg>
													</div> 
													<div id="cardPilotViewMore"> </div>
													<div id="cardPilotViewMoreBtn"> </div>
													
												<!-- End AT-4625 card pilot -->

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
																		<dd class="wordwrap" id="contact_FurtherClient_Address">${paymentOutDetails.currentContact.address}</dd>

																		<dt>Registration in</dt>
																		<dd id="contact_FurtherClient_regIn">${paymentOutDetails.currentContact.regIn}</dd>

																		<dt>Registered</dt>
																		<dd id="contact_FurtherClient_regComplete">${paymentOutDetails.currentContact.regComplete}</dd>

																		<dt>Phone</dt>
																		<dd id="contact_FurtherClient_phone">${paymentOutDetails.currentContact.phone}</dd>

																		<dt>Mobile</dt>
																		<dd id="contact_FurtherClient_mobile">${paymentOutDetails.currentContact.mobile}</dd>

																		<dt>Email</dt>
																		<dd id="contact_FurtherClient_email">
																			${paymentOutDetails.currentContact.email}
																		</dd>

																		<dt>Country of nationality</dt>
																		<%-- <dd id="contact_FurtherClient_nationality">${paymentOutDetails.currentContact.nationality}</dd> --%>
																		<%-- <dd id="contact_FurtherClient_nationality">${paymentOutDetails.currentContact.nationalityFullName} (${paymentOutDetails.currentContact.nationality})</dd> --%>
																		<c:choose>
																			<c:when
																				test="${paymentOutDetails.currentContact.nationalityFullName == '------'}">
																				<dd id="contact_FurtherClient_nationality">${paymentOutDetails.currentContact.nationalityFullName}</dd>
																			</c:when>
																			<c:otherwise>
																				<dd id="contact_FurtherClient_nationality">${paymentOutDetails.currentContact.nationalityFullName} (${paymentOutDetails.currentContact.nationality})</dd>
																			</c:otherwise>
																		</c:choose>
																		
																		
																		<dt>Online account status</dt>
																		<dd id="account_FurtherClient_status">${paymentOutDetails.account.complianceStatus}</dd>

																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>Is US client</dt>
																		<c:choose>
																			<c:when
																				test="${paymentOutDetails.currentContact.isUsClient}">
																				<dd id="contact_FurtherClient_usClient">Yes</dd>
																			</c:when>
																			<c:otherwise>
																				<dd id="contact_FurtherClient_usClient">No</dd>
																			</c:otherwise>
																		</c:choose>

																		<dt>Service required</dt>
																		<dd id="account_FurtherClient_serviceReq">${paymentOutDetails.account.serviceRequired}</dd>
																		
																		<dt>Date of birth</dt>
																		<dd  id="account_FurtherClient_affiliateName">${paymentOutDetails.currentContact.dob}</dd>
																		
																		<dt>Work phone</dt>
																		<dd id="account_FurtherClient_affiliateName">${paymentOutDetails.currentContact.phoneWork}</dd>
																		
																		
																		<dt>Device info</dt>
																	
																		<input id="account_FurtherClient_deviceInfo" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" value="View Device Info"
																		onclick="showDeviceInfo('${paymentOutDetails.currentContact.accountId}')">
																		
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
										<table class="micro" border="1"> 
											<thead> 
												<tr> 
												   <th>Currency</th> 
													<th>Wallet ID</th> 
													<th class="number">Available</th> 
													<th class="number">Total</th>
												</tr> 
											 </thead> 
										 <tbody id="customerWalletTablebody"> 
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

												<div id="payOutDetails_activitylog_indicatior"
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
																onchange="getActivityLogsByModule('1','10', '${paymentOutDetails.currentContact.accountId}', 
																false , this.value, 'leftRecordsIdActLog_AuditTrail',
																'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetailsPayOut_ActLog');">
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
																items="${paymentOutDetails.activityLogs.activityLogData}">
																<tr class="talign">
																	<td>${activityData.createdOn}</td>
																	<td>${paymentOutDetails.paymentOutInfo.transactionNumber}</td>
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
														id="viewMoreDetailsPayOut_ActLog"
														class="load-more space-after"
														onclick="viewMoreDetails('ACTIVITYLOG' ,'activityLog','actLogTotalRecordsPayOutId','leftRecordsPayOutId_ActLog');">
														VIEW <span class="load-more__extra"
														id="viewMorePayOut_ActLogId"> </span> MORE <span
														class="load-more__left" id="leftRecordsPayOutId_ActLog">
													</span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>
													
													<a href="javascript:void(0);" id="viewMoreAuditTrailDetails_ActLog"  class="load-more space-after"  
										  onclick="getViewMoreActivityLogsByModule('${paymentOutDetails.currentContact.accountId}', true, 'leftRecordsIdActLog_AuditTrail', 'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetails_ActLog');">
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
											
											
											<!-- Add to watchlist section start -->
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
																					items="${ paymentOutDetails.watchlist.watchlistData}"
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
												
												<!-- Add to watchlist section end -->
											
											
											<!-- Update section starts here -->
												<section>

													<h3 class="hidden">Update status</h3>

													<fieldset>

														<legend>Update status</legend>

														<ul class="form__fields--bare">

															<li class="form__field-wrap">

																<fieldset>

																	<legend class="label">Set status to...</legend>

																	<ul id="paymentOutDetails_Status_radio"
																		class="pill-choice--small">
																		<c:forEach var="statusData"
																			items="${paymentOutDetails.status.statuses }">
																			<li><c:if
																					test="${statusData.status == 'CLEAR' }">
																					<c:choose>
																						<c:when test="${statusData.isSelected  }">
																							<label id="rad-status-clear-label"
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
																							<label class="pill-choice__choice--positive" id="rad-status-clear-label"
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
																				</c:if> <c:if test="${statusData.status == 'REJECT' }">
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
																		<%-- <li>
	<c:choose>
			<c:when test="${paymentOutDetails.paymentOutInfo.status == 'CLEAR' }">
					<label class="pill-choice__choice--positive pill-choice__choice--on" for="rad-status-clear">
				<input id="rad-status-clear" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />
				Clear			</label>
			</c:when>
			<c:otherwise>
					<label class="pill-choice__choice--positive" for="rad-status-clear">
				<input id="rad-status-clear" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more-hide" data-more-hide="input-more-areas-reasons" />
				Clear			</label>
			</c:otherwise>
	</c:choose>
	</li>
	<li>
	<c:choose>
			<c:when test="${paymentOutDetails.paymentOutInfo.status == 'UNWATCH' }">
			<label class="pill-choice__choice--positive pill-choice__choice--on" for="rad-status-unwatch">
				<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-unwatch" checked />
				Unwatch			</label>
			</c:when>
			<c:otherwise>
			<label class="pill-choice__choice--positive" for="rad-status-unwatch">
				<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-unwatch" />
				Unwatch		</label>
			</c:otherwise>
	</c:choose>
	</li>
	
	<li>
	<c:choose>
			<c:when test="${paymentOutDetails.paymentOutInfo.status == 'REJECT' }">
			<label class="pill-choice__choice--negative pill-choice__choice--on" for="rad-status-reject">
				<input id="rad-status-reject" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" checked />
				Reject			</label>
			</c:when>
			<c:otherwise>
			<label class="pill-choice__choice--negative " for="rad-status-reject">
				<input id="rad-status-reject" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" />
				Reject			</label>
			</c:otherwise>
	</c:choose>
	</li>
	
	<li>
	<c:choose>
			<c:when test="${paymentOutDetails.paymentOutInfo.status == 'SIEZE' }">
			<label class="pill-choice__choice--negative pill-choice__choice--on" for="rad-status-sieze">
				<input id="rad-status-sieze" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" checked />
				Sieze			</label>
			</c:when>
			<c:otherwise>
			<label class="pill-choice__choice--negative" for="rad-status-sieze">
				<input id="rad-status-sieze" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" />
				Sieze			</label>
			</c:otherwise>
	</c:choose>
	</li>
	
		<li>
	<c:choose>

			<c:when test="${paymentOutDetails.paymentOutInfo.status == 'HOLD' }">
			<label class="pill-choice__choice--neutral pill-choice__choice--on" for="rad-status-hold">
				<input id="rad-status-hold" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" checked/>
				Hold			</label>
			</c:when>
			<c:otherwise>
			<label class="pill-choice__choice--neutral" for="rad-status-hold">
				<input id="rad-status-hold" type="radio" name="paymentStatus" value="${paymentOutDetails.paymentOutInfo.status}" class="input-more" data-more-area="input-more-reject-reasons" />
				Hold			</label>
			</c:otherwise>
	</c:choose>
	</li> --%>
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
																						id="paymentOut_statusReasons">
																						<c:forEach var="reason"
																							items="${ paymentOutDetails.statusReason.statusReasonData}"
																							varStatus="loop">
																							<li><c:choose>
																									<c:when test="${reason.value}">
																										<label
																											for="rad-reject-reason-${loop.index + 1}">
																											<input
																											id="rad-reject-reason-${loop.index + 1}"
																											type="radio" checked="checked"
																											name="payOutDetails_payOutStatusReasons"
																											value="${reason.name}" /> ${reason.name}
																										</label>

																									</c:when>
																									<c:otherwise>
																										<label
																											for="rad-reject-reason-${loop.index + 1}">
																											<input
																											id="rad-reject-reason-${loop.index + 1}"
																											type="radio"
																											name="payOutDetails_payOutStatusReasons"
																											value="${reason.name}" /> ${reason.name}
																										</label>
																									</c:otherwise>
																								</c:choose></li>
																						</c:forEach>
																						<%-- 	<label for="rad-reject-reason-1">
						<input id="rad-reject-reason-1" type="radio" name="SomeNameRejectReason" />
						First reason					</label>
					</li>
					
								<li>
					<label for="rad-reject-reason-1">
						<input id="rad-reject-reason-1" type="radio" name="SomeNameRejectReason" />
						First reason					</label>
				</li>
								<li>
					<label for="rad-reject-reason-2">
						<input id="rad-reject-reason-2" type="radio" name="SomeNameRejectReason" />
						Second reason					</label>
				</li>
								<li>
					<label for="rad-reject-reason-3">
						<input id="rad-reject-reason-3" type="radio" name="SomeNameRejectReason" />
						Third reason					</label>
				</li> --%>

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


													<input id="updatePaymentOut" type="button"
														class="<c:out value="${buttonClass}"/>" value="Apply"
														<c:out value="${buttonDisable}"/>
														onclick="executeActions(false);" />
													
													
													<input id="updatePaymentOutAndUnlock" type="button"
														class="<c:out value="${buttonClass}"/>" value="Apply & UNLOCK"
														<c:out value="${buttonDisable}"/>
														onclick="executeActions(true);" />
															
														<object id="gifloaderforPayOut" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
													<c:choose>
														<c:when test="${(paymentOutDetails.locked == null || !paymentOutDetails.locked)}">
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
													<span id="updatePaymentOut_error_field" class="form__field-error" hidden="hidden">
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
		
	<form id="paymentOutDetailForm" action="/compliance-portal/paymentOutDetail" method="POST">
			<input type="hidden" id="form_paymentOutId" value="" name="paymentOutId"/>
			<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/>
			<input type="hidden" id="custType" value="" name="custType"/>
		<%-- <c:choose>
			<c:when test="${paymentOutDetails.isPagenationRequired==true}">
			
		<ul id="paginationBlock" class="pagination--fixed page-load">
			<li class="pagination__jump--disabled"><a id="firstRecord"
				onclick="getPayOutFirstRecord('${paymentOutDetails.paginationDetails.firstRecord.custType}','${paymentOutDetails.paginationDetails.firstRecord.id}');" href="#" data-ot="First record"><i
					class="material-icons">first_page</i></a></li>
			<li class="pagination__jump"><a id="previousRecord"
				onclick="getPayOutPreviousRecord('${paymentOutDetails.paginationDetails.prevRecord.custType}','${paymentOutDetails.paginationDetails.prevRecord.id}');" href="#"
				data-ot="Previous record"><i class="material-icons">navigate_before</i></a>
			</li>
			<li class="pagination__jump"><a id="nextRecord" onclick="getPayOutNextRecord('${paymentOutDetails.paginationDetails.nextRecord.custType}','${paymentOutDetails.paginationDetails.nextRecord.id}');"
				href="#" data-ot="Next record"><i class="material-icons">navigate_next</i></a>
			</li>
			<li class="pagination__jump"><a id="lastRecord" onclick="getPayOutLastRecord('${paymentOutDetails.paginationDetails.totalRecords.custType}','${paymentOutDetails.paginationDetails.totalRecords.id}');"
				href="#" data-ot="Last record"><i class="material-icons">last_page</i></a>
			</li>
			<li class="pagination__text">Record <strong id="currentRecord">${paymentOutDetails.currentRecord }</strong>
				of <span id="totolRecords">${paymentOutDetails.totalRecords }</span>
			</li>
		</ul>
		</c:when>
		</c:choose> --%>
		</form>
		<form id="openClientForm" action="/compliance-portal/registrationDetails" method="POST" target="_blank">
																		<input type="hidden" id="contactId" value="${paymentOutDetails.currentContact.id}" name="contactId"/>
																		<input type="hidden" id="custType" value="${paymentOutDetails.account.clientType}" name="custType"/>
																		<input type="hidden" id="source" value="queue" name="source"/>
																	 </form>
																	 
		<form id="openPaymentForm" action="/compliance-portal/mostRecentPaymentOutDetails" method="POST" target="_blank">
																		<input type="hidden" id="tradeId" value="${tradeId}" name="tradeId"/>
																		<input type="hidden" id="accountNumber" value="" name="accountNumber"/>
																		<input type="hidden" id="source" value="report" name="source"/>
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
			value='${paymentOutDetails.searchCriteria}' /> <input type="hidden"
			id="contact_contactId" value='${paymentOutDetails.currentContact.id}' />
		<input type="hidden" id="contact_crmAccountId"
			value='${paymentOutDetails.currentContact.crmAccountId}' /> <input
			type="hidden" id="contact_crmContactId"
			value='${paymentOutDetails.currentContact.crmContactId}' /> <input
			type="hidden" id="contact_accountId"
			value='${paymentOutDetails.currentContact.accountId}' /> <input
			type="hidden" id="userResourceId"
			value='${paymentOutDetails.userResourceId}' /> <input type="hidden"
			id="paymentOutId" value='${paymentOutDetails.paymentOutInfo.id}' />
		<input type="hidden" id="contact_tradeContactId"
			value='${paymentOutDetails.currentContact.tradeContactID}' /> <input
			type="hidden" id="tradePaymentId"
			value='${paymentOutDetails.paymentOutInfo.tradePaymentId}' /> <input
			type="hidden" id="trade_beneficiary_id"
			value='${paymentOutDetails.paymentOutInfo.tradeBeneficiaryId}' />
		<%-- <input type="hidden" id="kycTotalRecordsId" value='${paymentOutDetails.kyc.kycTotalRecords}'/> --%>
		<input type="hidden" id="sanctionTotalRecordsPayOutId_contact"
			value='${paymentOutDetails.sanction.contactSanction.sanctionTotalRecords}' />
		<input type="hidden" id="sanctionTotalRecordsPayOutId_bank"
			value='${paymentOutDetails.sanction.bankSanction.sanctionTotalRecords}' />
		<input type="hidden" id="sanctionTotalRecordsPayOutId_beneficiary"
			value='${paymentOutDetails.sanction.beneficiarySanction.sanctionTotalRecords}' />
		<input type="hidden" id="fraugsterTotalRecordsPayOutId"
			value='${paymentOutDetails.fraugster.fraugsterTotalRecords}' /> <input
			type="hidden" id="customCheckTotalRecordsPayOutId" value='${paymentOutDetails.customCheck.paymentOutCustomCheck.totalRecords}'/>
		<input type="hidden" id="actLogTotalRecordsPayOutId"
			value='${paymentOutDetails.activityLogs.totalRecords}' /> <input
			type="hidden" id="furPayInDetailsTotalRecordsPayOutId"
			value='${paymentOutDetails.furtherPaymentDetails.payInDetailsTotalRecords}' />
		<input type="hidden" id="furPayOutDetailsTotalRecordsPayOutId"
			value='${paymentOutDetails.furtherPaymentDetails.payOutDetailsTotalRecords}' />
		<input type="hidden" id="countryCheckTotalRecordsPayOutId" 
			value='${paymentOutDetails.customCheck.countryCheck.countryCheckTotalRecords}' />
		<!-- AT-3658 -->
		<input type="hidden" id="paymentReferenceCheckTotalRecordsPayOutId" 
			value='${paymentOutDetails.paymentReference.totalRecords}' />
		<input type="hidden" id="beneficiaryCountryRiskLevel" 
			value='${paymentOutDetails.customCheck.countryCheck.riskLevel}' />
		<input type="hidden" id="beneficiaryCheckStatus" 
			value='${paymentOutDetails.customCheck.paymentOutCustomCheck.velocityCheck.beneCheck}' />				
		<input type="hidden" id="payment_isOnQueue"  value='${paymentOutDetails.isOnQueue}' />		
		<input type="hidden" id="documentCategory" value='PaymentOut' />
		<input type="hidden" id="orgganizationCode" value='${paymentOutDetails.account.orgCode}' />
		<input type="hidden" id="overallStatusPayRef" value='${paymentOutDetails.paymentReference.overallStatus}'/> 
			
	<!-- set sanction Contact, Bank and Beneficiary status  value on loading and after reepeat check, this value are used to update sanction status in paymentout table : Abhijit G -->
	<input type="hidden" id="paymentOutSanctionContactStatus"  value='${paymentOutDetails.sanction.contactSanction.statusValue}' />
	<input type="hidden" id="paymentOutSanctionBeneficiaryStatus"  value='${paymentOutDetails.sanction.beneficiarySanction.statusValue }' />
	<input type="hidden" id="paymentOutSanctionBankStatus"  value='${paymentOutDetails.sanction.bankSanction.statusValue }' />	
	<input type="hidden" id="isRecordLocked" value='${paymentOutDetails.locked}' />
	
	<input type="hidden" id="fraugster_eventServiceLogId" value='${paymentOutDetails.fraugster.id}' />	
	<!-- Add for AT-3161 New Fraud Predict Custom Rule -->
	<input type="hidden" id="customRule_FPFlag" value='${paymentOutDetails.customRuleFPFlag}' />	
	
<input type="hidden" id="auditTrailActLogTotalRecords" value=""/>
			<input type="hidden" id="auditTrailActLogEntityType" value=""/>
	<input type="hidden" id="poiExists" value='${paymentOutDetails.poiExists}'/>
	<input type="hidden" id="intuitionTotalRecordsId" value='${paymentOutDetails.intuition.intuitionTotalRecords}'/>
	<input type="hidden" id="blacklistStatus" value='${paymentOutDetails.blacklist.benficiaryBlacklist.statusValue}'/>
	<input type="hidden" id="customCheckStatus" value='${paymentOutDetails.customCheck.paymentOutCustomCheck.status}'/>
	<input type="hidden" id="fraugsterStatus" value='${paymentOutDetails.fraugster.statusValue}'/>
	<input type="hidden" id="accountTMFlag" value='${paymentOutDetails.accountTMFlag}'/>
	<input type="hidden" id="accountVersion" value='${paymentOutDetails.accountVersion}'/>
	<input type="hidden" id="intuitionCurrentStatus" value='${paymentOutDetails.intuition.status}'/> 
	<input type="hidden" id="intuitionCurrentAction" value='${paymentOutDetails.intuition.currentAction}'/>
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
	<script type="text/javascript" src="resources/js/paymentOutDetails.js"></script>
	<script type="text/javascript" src="resources/js/jsontotable.js"></script>
	<script type="text/javascript" src="resources/js/wallet.js"></script>
	<script type="text/javascript" src="resources/js/fxTicket.js"></script>
	<script type="text/javascript" src="resources/js/amcharts/cd_amcharts_min.js"></script>
	<script type="text/javascript" src="resources/js/cardPilot.js"></script> <!-- AT-4625 -->
	<script type="text/javascript" src="resources/js/jquery-confirm.js"></script>


<input type="hidden" id="beneAccountNumber" name="beneAccountNumber" value='${paymentOutDetails.paymentOutInfo.beneAccountNumber}'>

</body>

</html>