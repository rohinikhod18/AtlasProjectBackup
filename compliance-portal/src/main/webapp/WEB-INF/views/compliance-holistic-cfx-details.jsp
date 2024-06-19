<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="en">

<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">>
<meta name="description" content="Enterprise tools" />
<meta name="copyright" content="Currencies Direct" />
	
 <link rel="stylesheet" href="resources/css/jquery-ui.css">
  <link rel="stylesheet" href="resources/css/popup.css">
  <link rel="stylesheet" href="resources/css/jsontotable.css">
  <link rel="stylesheet" href="resources/css/fxticket.css">
</head>

<body>
	<div id="master-grid" class="grid">
	<c:choose>
	<c:when test="${(regDetails.locked  && regDetails.lockedBy != regDetails.user.name) || regDetails.locked == null || !regDetails.locked}">
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

					<div class="grid">
						
						<div class="grid__row">
					
						<c:set var="redirectUrl" value="/compliance-portal/holisticViewReport"/>
						<c:set var="redirectTo" value="Holistic view report"/>
					
							<form id="redirectQueueForm" action="${redirectUrl }" method="post">
							<div class="grid__col--9">
								<h1>
									Client #<span id="account_tradeAccountNum">${holisticAccountResponse.holisticAccount.tradeAccountNumber}</span>
									
										<span class="breadcrumbs"> <span
										class="breadcrumbs__crumb--in">in</span> <span
										class="breadcrumbs__crumb--area">Compliance</span> <span
										class="breadcrumbs__crumb"> <a
											onclick="redirectToQueue();">${redirectTo }</a></span>

									</span>
									
									
									
								</h1>
								<input id="ququefilter" type="hidden" name="searchCriteria" value="">
							</div>
							</form>						

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

								<div class="grid__col--11 grid__col--pad-right">

									<section class="main-content__section">

										 <h2>Holistic View - CFX</h2> 
										 
										 <div class="boxpanel--shadow">

											<div class="grid">
												<div class="grid__row">

													<div class="grid__col--4">
														<dl class="split-list">

															<dt>Name</dt>
															<dd><span class="wordwrap" id="contact_name">
															${holisticAccountResponse.holisticAccount.name == "" || holisticAccountResponse.holisticAccount.name == null ? '--' : holisticAccountResponse.holisticAccount.name }
															</span></dd>
															<dt>Client type</dt>
															<dd id="account_clientType">${holisticAccountResponse.holisticAccount.clientType == "" || holisticAccountResponse.holisticAccount.clientType == null ? '--' : holisticAccountResponse.holisticAccount.clientType }</dd>

															<dt>Website</dt>
															<dd>${holisticAccountResponse.holisticAccount.website == "" || holisticAccountResponse.holisticAccount.website == null ? '--' : holisticAccountResponse.holisticAccount.website}</dd>
															
															<dt>Legal Entity</dt>
															<dd>${holisticAccountResponse.holisticAccount.legalEntity == "" || holisticAccountResponse.holisticAccount.legalEntity == null ? '--' : holisticAccountResponse.holisticAccount.legalEntity}</dd>
															
															<dt>Options</dt>
															<dd id="account_company_options">${holisticAccountResponse.holisticAccount.company.option == "" || holisticAccountResponse.holisticAccount.company.option == null ? '--' : holisticAccountResponse.holisticAccount.company.option}</dd>

														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">

														<dl class="split-list">

															<dt>Currency Pair</dt>
															<dd id="account_currencyPair">${holisticAccountResponse.holisticAccount.currencyPair == "" || holisticAccountResponse.holisticAccount.currencyPair == null  ? '--' : holisticAccountResponse.holisticAccount.currencyPair}</dd>

															<dt>Estimated transaction value</dt>
															<dd id="account_estimatedTxnValue">${holisticAccountResponse.holisticAccount.estimTransValue == "" || holisticAccountResponse.holisticAccount.estimTransValue == null ? '--' : holisticAccountResponse.holisticAccount.estimTransValue}</dd>

															<dt>Purpose of transaction</dt>
															<dd id="account_purposeOfTxn">${holisticAccountResponse.holisticAccount.purposeOfTran == "" || holisticAccountResponse.holisticAccount.purposeOfTran == null ? '--' : holisticAccountResponse.holisticAccount.purposeOfTran}</dd>
															
															<dt>Account Status</dt>
															<dd id="account_compliance_status">${holisticAccountResponse.holisticContact.accountStatus}</dd>
															
															<dt>Account Sub Status</dt>
															<dd id="account_sub_status">---</dd>

														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Countries of operation</dt>
															<dd id="account_countries_of_operation">${holisticAccountResponse.holisticAccount.countriesOfOperation == "" || holisticAccountResponse.holisticAccount.countriesOfOperation == null ? '--' : holisticAccountResponse.holisticAccount.countriesOfOperation }</dd>

															<dt>Organization</dt>
															<dd id="account_organisation">${holisticAccountResponse.holisticAccount.orgCode}</dd>

															<dt>Source of funds</dt>
															<dd id="account_sourceOfFunds">${holisticAccountResponse.holisticAccount.sourceOfFund == "" || holisticAccountResponse.holisticAccount.sourceOfFund ==  null ? '--' : holisticAccountResponse.holisticAccount.sourceOfFund}</dd>
															
															<dt>T & C signed date</dt>
															<dd id="account_tAndC_signed_date">${holisticAccountResponse.holisticAccount.company.tAndcSignedDate == "" || holisticAccountResponse.holisticAccount.company.tAndcSignedDate ==  null ? '--' : holisticAccountResponse.holisticAccount.company.tAndcSignedDate}</dd>
															
															<dt>E-tailer Flag</dt>
															<dd id="account_etailer_flag">${holisticAccountResponse.holisticAccount.company.etailer == "" || holisticAccountResponse.holisticAccount.company.etailer == null ? '--' : holisticAccountResponse.holisticAccount.company.etailer}</dd>
															
															 
														</dl>
													</div>
												</div>

											</div>

										</div>

										<div class="accordion--quick-controls">

											<div class="quick-controls">
												<ul class="containing horizontal">
													<li class="quick-control__control--open-all"><a
														href="javascript:void(0);" data-ot="Expand all checks info"><i onclick = "viewMoreLoadDataForHolisticView()"
															class="material-icons">add</i></a></li>
													<li class="quick-control__control--close-all"><a
														href="javascript:void(0);" data-ot="Close all checks info"><i onclick = "viewMoreResetDataForHolisticView()"
															class="material-icons">close</i></a></li>
												</ul>
											</div>
											<div id="accordion-section-client-details"
												class="accordion__section">

												<div class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Company Details</a>
												</div>

												<div class="accordion__content">

													<div class="boxpanel--shadow">

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">
																	
																		<dt>Company name</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.name == "" || holisticAccountResponse.holisticAccount.name == null  ? '--' : holisticAccountResponse.holisticAccount.name}</dd>
								
																		<dt>Legal entity type</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.legalEntity == "" || holisticAccountResponse.holisticAccount.legalEntity == null ? '--' : holisticAccountResponse.holisticAccount.legalEntity}</dd>
								
																		<dt>Corporate type</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.corporateType == "" || holisticAccountResponse.holisticAccount.company.corporateType == null ? '--' : holisticAccountResponse.holisticAccount.company.corporateType}</dd>
								
																		<dt>Company number</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.registrationNo == "" || holisticAccountResponse.holisticAccount.company.registrationNo == null ? '--' : holisticAccountResponse.holisticAccount.company.registrationNo}</dd>
								
																		<dt>Industry</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.industry == "" || holisticAccountResponse.holisticAccount.company.industry == null ? '--' : holisticAccountResponse.holisticAccount.company.industry}</dd>
																		
																		<dt>Country of Establishment</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.countryOfEstablishment == "" || holisticAccountResponse.holisticAccount.company.countryOfEstablishment == null ? '--' : holisticAccountResponse.holisticAccount.company.countryOfEstablishment}</dd>
																		
																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>SIC code</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.corperateCompliance.sic == "" || holisticAccountResponse.holisticAccount.corperateCompliance.sic == null ? '--' : holisticAccountResponse.holisticAccount.corperateCompliance.sic}</dd>
								
																		<dt>SIC description</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.corperateCompliance.sicDesc == "" || holisticAccountResponse.holisticAccount.corperateCompliance.sicDesc == null ? '--' : holisticAccountResponse.holisticAccount.corperateCompliance.sicDesc}</dd>
								
																		<dt>Incorporation date</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.incorporationDate == "" || holisticAccountResponse.holisticAccount.company.incorporationDate == null ? '--' : holisticAccountResponse.holisticAccount.company.incorporationDate}</dd>
								
																		<dt>Phone number</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.companyPhone == "" || holisticAccountResponse.holisticAccount.company.companyPhone == null ? '--' : holisticAccountResponse.holisticAccount.company.companyPhone}</dd>
								
																		<dt>VAT No</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.vatNo == "" || holisticAccountResponse.holisticAccount.company.vatNo == null ? '--' : holisticAccountResponse.holisticAccount.company.vatNo}</dd>
																		
																		<dt>Billing Address</dt>
																		<dd class="breakwordHeader">${holisticAccountResponse.holisticAccount.company.billingAddress == "" || holisticAccountResponse.holisticAccount.company.billingAddress == null ? '--' : holisticAccountResponse.holisticAccount.company.billingAddress}</dd>
																		
																	
																	</dl>
																</div>

															</div>
															
															<div class="boxpanel--micro space-after">
						
																<h3>Other Contacts List</h3>
										
																<c:choose>
																 	<c:when test="${fn:length(holisticAccountResponse.holisticContacts) == 0}">
																		No other contacts
																 	</c:when>
																    <c:otherwise>
																		<table class="micro">
																			<thead>
																				<tr>
																			    	<th class="breakwordHeader">Name</th>
																					<th class="breakwordHeader">Job tile</th>
																					<th class="breakwordHeader">Position of significance</th>
																					<th class="breakwordHeader">Authorised Signatory</th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach var="contact"
																						items="${holisticAccountResponse.holisticContacts}"  varStatus="loop">	
																					<tr>
																						<td class="breakwordHeader">${contact.name == "" ? '--' : contact.name}</td>
																						<td class="breakwordHeader">${contact.jobTitle == "" ? '--' : contact.jobTitle}</td>
																						<td class="breakwordHeader">${contact.positionOfSignificance == "" ? '--' : contact.positionOfSignificance}</td>
																						<td class="breakwordHeader">${contact.authorisedSignatory == "" ? '--' : contact.authorisedSignatory}</td>
																					</tr>
																				</c:forEach>
																			</tbody>
																		</table>
																	</c:otherwise>
																</c:choose>	
															</div>

														</div>

													</div>

												</div>

											</div>
											<div id="accordion-section-client-details"
												class="accordion__section">

												<div class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Registration Data</a>
												</div>

												<div class="accordion__content">

													<div class="boxpanel--shadow">

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">

																		<dt>Reg Marker</dt>
																		<dd class="wordwrap" id="contact_reg_marker">--</dd>
																		
																		<dt>Sub source</dt>
																		<dd class="wordwrap" id="contact_subsource">${holisticAccountResponse.holisticAccount.subSource == "" || holisticAccountResponse.holisticAccount.subSource == null ? '--' : holisticAccountResponse.holisticAccount.subSource}</dd>

																		<dt>Channel</dt>
																		<dd id="contact_channel">${holisticAccountResponse.holisticAccount.channel == "" || holisticAccountResponse.holisticAccount.channel == null ? '--' : holisticAccountResponse.holisticAccount.channel}</dd>

																		<dt>Affiliate Name</dt>
																		<dd id="contact_affiliate_name">${holisticAccountResponse.holisticAccount.affiliateName == "" || holisticAccountResponse.holisticAccount.affiliateName == null ? '--' : holisticAccountResponse.holisticAccount.affiliateName}</dd>
																		<%-- <dd id="contact_FurtherClient_regComplete">${regDetails.account.regCompleteAccount}</dd> --%> 
																		
																		<dt>Mode of Registration</dt>
																		<dd id="contact_mode_of_registration">${holisticAccountResponse.holisticAccount.regMode == "" || holisticAccountResponse.holisticAccount.regMode == null ? '--' : holisticAccountResponse.holisticAccount.regMode}</dd>

																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">
																		
																		<dt>Registration In Date</dt>
																		<dd id="contact_registration_id_date">${holisticAccountResponse.holisticAccount.registrationInDate == "" || holisticAccountResponse.holisticAccount.registrationInDate == null ? '--' : holisticAccountResponse.holisticAccount.registrationInDate}</dd>
																				
																		<dt>Registered Date</dt>
																		<dd id="contact_registered_date">${holisticAccountResponse.holisticAccount.complianceDoneOn == "" || holisticAccountResponse.holisticAccount.complianceDoneOn == null ? '--' : holisticAccountResponse.holisticAccount.complianceDoneOn}</dd>

																		<dt>Source</dt>
																		<dd id="contact_source">${holisticAccountResponse.holisticAccount.source == "" || holisticAccountResponse.holisticAccount.source == null ? '--' : holisticAccountResponse.holisticAccount.source}</dd>

																		<dt>Referral text</dt>
																		<dd id="contact_refferalText">${holisticAccountResponse.holisticAccount.refferalText == "" || holisticAccountResponse.holisticAccount.refferalText == null ? '--' : holisticAccountResponse.holisticAccount.refferalText}</dd>

																		<dt>T&Cs date signed</dt>
																		<dd id="contact_tc_signed_date">${holisticAccountResponse.holisticAccount.company.tAndcSignedDate == "" || holisticAccountResponse.holisticAccount.company.tAndcSignedDate == null ? '--' : holisticAccountResponse.holisticAccount.company.tAndcSignedDate}</dd>
																		
																	</dl>
																</div>

															</div>

														</div>

													</div>

												</div>

											</div>
											<div id="accordion-section-client-details"
												class="accordion__section">

												<div class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Registration KYC Data</a>
												</div>

												<div class="accordion__content">

													<div class="boxpanel--shadow">

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">
																	
																		<dt>Purpose of Transaction</dt>
																		<dd class="wordwrap" id="contact_purpose_of_transaction">${holisticAccountResponse.holisticAccount.purposeOfTran == "" || holisticAccountResponse.holisticAccount.purposeOfTran == null ? '--' : holisticAccountResponse.holisticAccount.purposeOfTran}</dd>

																		<dt>Service required</dt>
																		<dd id="contact_service_required">${holisticAccountResponse.holisticAccount.serviceRequired == "" || holisticAccountResponse.holisticAccount.serviceRequired == null ? '--' : holisticAccountResponse.holisticAccount.serviceRequired}</dd>
																		
																		<dt>Annual FX Requirement</dt>
																		<dd id="contact_service_required">${holisticAccountResponse.holisticAccount.annualFXRequirement == "" || holisticAccountResponse.holisticAccount.annualFXRequirement == null ? '--' : holisticAccountResponse.holisticAccount.annualFXRequirement}</dd>
																		
																		<dt>Device info</dt>
																	
																		<dd><input id="account_deviceInfo" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" 
																		value="View Device Info" onclick="showDeviceInfo('${holisticAccountResponse.holisticAccount.id}')"></dd>
																		
																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>Average Transaction Value</dt>
																		<dd id="contact_estimated_trans_value">${holisticAccountResponse.holisticAccount.estimTransValue == "" || holisticAccountResponse.holisticAccount.estimTransValue == null ? '--' : holisticAccountResponse.holisticAccount.estimTransValue}</dd>
																		
																		<dt>IP</dt>
																		<dd id="contact_ip_address">${holisticAccountResponse.holisticContact.ipAddress == "" || holisticAccountResponse.holisticContact.ipAddress == null ? '--' : holisticAccountResponse.holisticContact.ipAddress}</dd>
																		
																		<dt>CCJ</dt>
																		<dd id="contact_ip_address">${holisticAccountResponse.holisticAccount.company.ccj == "" || holisticAccountResponse.holisticAccount.company.ccj == null ? '--' : holisticAccountResponse.holisticAccount.company.ccj}</dd>
																		
																		<dd><input id="account_viewOriginal_Details" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
																		 value="VIEW ORIGINAL" onclick="showAccountHistory('${holisticAccountResponse.holisticAccount.id}')"><dd>
																		
																	</dl>
																</div>

															</div>

														</div>

													</div>

												</div>

											</div>
											<div id="accordion-section-client-details"
												class="accordion__section">

												<div class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Transactional Activity Summary Overview</a>
												</div>

												<div class="accordion__content">

													<div class="boxpanel--shadow">

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">

																		<dt>First trade date</dt>
																		<dd class="wordwrap" id="contact_first_trade_date">${holisticAccountResponse.paymentSummary.firstTradeDate == "" || holisticAccountResponse.paymentSummary.firstTradeDate == null ? '--' : holisticAccountResponse.paymentSummary.firstTradeDate}</dd>

																		<dt>Last trade date</dt>
																		<dd class="wordwrap" id="contact_last_trade_date">${holisticAccountResponse.paymentSummary.lastTradeDate == "" || holisticAccountResponse.paymentSummary.lastTradeDate == null ? '--' : holisticAccountResponse.paymentSummary.lastTradeDate}</dd>

																		<dt>Number of trades</dt>
																		<dd id="contact_number_of_trades">${holisticAccountResponse.paymentSummary.noOfTrades == "" || holisticAccountResponse.paymentSummary.noOfTrades == null ? '--' : holisticAccountResponse.paymentSummary.noOfTrades}</dd>

																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>Trade Sale Amount</dt>
																		<dd id="contact_trade_sale_amount">${holisticAccountResponse.paymentSummary.totalSaleAmount == "" || holisticAccountResponse.paymentSummary.totalSaleAmount == null ? '--' : holisticAccountResponse.paymentSummary.totalSaleAmount}</dd>
																		
																		<dt>Online contact status</dt>
																		<dd id="contact_online_status">---</dd>

																		<dt>Client activated date</dt>
																		<dd id="contact_activated_date">${holisticAccountResponse.holisticAccount.complianceDoneOn == "" || holisticAccountResponse.holisticAccount.complianceDoneOn == null ? '--' : holisticAccountResponse.holisticAccount.complianceDoneOn}</dd>

																	</dl>
																</div>

															</div>

														</div>

													</div>

												</div>

											</div>
											<div id="accordion-section-client-details"
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
																items="${holisticAccountResponse.furtherPaymentDetails.furtherPaymentInDetails}">
																<tr>
																    <td class="breakword"><a href="javascript:void(0);"  onclick="showPaymentInDetails('${payment.paymentId }')">
																    ${payment.tradeContractNumber }</a></td>
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
														onclick="viewMoreHolisticDetails('PAYMENT_IN' ,'further_paymentInDetails','furPayInDetailsTotalRecordsPayInId','leftRecordsPayInIdFurPayInDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_FurPayInDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayInIdFurPayInDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
														<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>
													<p></p>
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
																items="${holisticAccountResponse.furtherPaymentDetails.furtherPaymentOutDetails}">
																<tr>
																    <td class="breakword"><a href="javascript:void(0);"  onclick="showPaymentOutDetails('${payment.paymentId }')">
																    ${payment.tradeContractNumber }</a></td>
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
														onclick="viewMoreHolisticDetails('PAYMENT_OUT' ,'further_paymentOutDetails','furPayOutDetailsTotalRecordsPayInId','leftRecordsPayInIdFurPayOutDetails','null');">
														VIEW <span class="load-more__extra"
														id="viewMorePayIn_FurPayOutDetailsId"> </span> MORE <span
														class="load-more__left"
														id="leftRecordsPayInIdFurPayOutDetails"> </span> <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading...">
														<object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
													</a>

												</div>
											</div>
											
											<div class="accordion__section">

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
											   </div>
											</div>	
											
											<div class="accordion__section">
												<div id="fxticket_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Fx Tickets</a>
												</div>
												<div id="fxTicketTableDiv" class="accordion__content"></div>
											</div>								

											<div class="accordion__section">

												<div id="doc_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Attached
														documents <c:if
															test="${fn:length(holisticAccountResponse.documents) gt 0}">
															<span id="docConunt" class="indicator">${fn:length(holisticAccountResponse.documents)}</span>
														</c:if> </a>
												</div>

												<div class="accordion__content">

													<table id="docTableId" class="space-after">
														<thead>
															<tr>
																<th class="tight-cell sorted desc"><a href="#">Created
																		on <i></i>
																</a></th>
																<th><a href="#">Created by</a></th>
																<th>Document name</th>
																<th><a href="#">Type</a></th>
																<th>Note</th>
																<th>Status</th>
															</tr>
														</thead>
														<tbody id="attachDoc">
															<c:forEach var="document" items="${holisticAccountResponse.documents}">
																<tr class="talign">
																	<td>${document.createdOn}</td>
																	<td>${document.createdBy}</td>
																	<td class="breakword"><a href="${document.url}">${document.documentName}</a></td>
																	<td class="breakword">${document.documentType}</td>
																	<td class="wrap-cell">${document.note}</td>
																	<c:choose>
																	<c:when test="${document.authorized }">
																		<td class="wrap-cell"><input type="checkbox" disabled="disabled" name="" value="" checked="checked"></td>
																	</c:when>
																	<c:otherwise>
																		<td class="wrap-cell"><input type="checkbox" disabled="disabled" name="" value=""></td>
																	</c:otherwise>
																	</c:choose>
																</tr>
															
															</c:forEach>
														</tbody>														
													</table>
												</div>

											</div>
											<div id="accordion-section-activity-log" class="accordion__section">

												<div id="holisticDetails_activitylog_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Activity
														log</a>
												</div>

												<div class="accordion__content">

													<table>
														<thead>
															<tr>
																<th class="whiteSpacePreLine sorted desc"><a href="#">Activity
																		date/time <i></i>
																</a></th>
																<th class="whiteSpacePreLine"><a href="#">Trade Contract Number</a></th>
																<th><a href="#">User</a></th>
																<th>Activity</th>
																<th><a href="#">Activity type</a></th>
																<th><a href="#">Comment</a></th>
															</tr>
														</thead>
														<tbody id="activityLog">
															<c:forEach var="activityData"
																items="${holisticAccountResponse.activityLogs.activityLogData}">
																<tr class="talign">
																	<td>${activityData.createdOn}</td>
																	<td>${activityData.contractNumber}</td>
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
													</table>
													
										  <a href="javascript:void(0);" id="viewMoreDetails_ActLog"  class="load-more space-after"  onclick="viewMoreHolisticDetails('ACTIVITYLOG' ,'activityLog','actLogTotalRecordsId','leftRecordsIdActLog');">
											VIEW <span class="load-more__extra" id = "viewMore_ActLogId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdActLog" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
										  </a>

												</div>

											</div>

									</section>

									<section id="other-info">

										<!-- <h2>Other information</h2>

										<div class="accordion--quick-controls">

											<div class="quick-controls">
												<ul class="containing horizontal">
													<li class="quick-control__control--open-all"><a
														href="#" data-ot="Expand all checks info"><i onclick = "viewMoreLoadDataActLog()"
															class="material-icons">add</i></a></li>
													<li class="quick-control__control--close-all"><a
														href="#" data-ot="Close all checks info"><i onclick = "viewMoreResetActLogData()"
															class="material-icons">close</i></a></li>
												</ul>
											</div> -->
											
											
											
										</div>

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
		
		<form id="regDetailForm" action="/compliance-portal/registrationDetails" method="POST">
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/>
			<input type="hidden" id="custType" value="" name="custType"/>
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

			<input type="submit" class="button--primary" value="Save" />

		</form>
		<input type="hidden" id="searchCriteria"
			value='${holisticAccountResponse.searchCriteria}' /> <input type="hidden"
			id="contact_contactId" value='${holisticAccountResponse.holisticContact.id}' />
		<input type="hidden" id="contact_crmAccountId"
			value='${regDetails.currentContact.crmAccountId}' /> <input
			type="hidden" id="contact_crmContactId"
			value='${regDetails.currentContact.crmContactId}' /> <input
			type="hidden" id="contact_accountId"
			value='${holisticAccountResponse.holisticAccount.id}' /> <input
			type="hidden" id="userResourceId"
			value='${regDetails.userResourceId}' />
			<input type="hidden" id="kycTotalRecordsId" value='${regDetails.kyc.kycTotalRecords}'/>
			<input type="hidden" id="sanctionTotalRecordsId" value='${regDetails.sanction.sanctionTotalRecords}'/>
			<input type="hidden" id="fraugsterTotalRecordsId" value='${regDetails.fraugster.fraugsterTotalRecords}'/>
			<input type="hidden" id="customCheckTotalRecordsId" value='${regDetails.internalRule.customCheck.customCheckTotalRecords}'/>
			<input type="hidden" id="actLogTotalRecordsId" value='${holisticAccountResponse.activityLogs.totalRecords}'/>
			<input type="hidden" id="customerType" value='${regDetails.account.clientType}'/>
			<input type="hidden" id="account_complianceDoneOn" value='${regDetails.account.regCompleteAccount}' />
			<input type="hidden" id="account_registrationInDate" value='${regDetails.account.registrationInDate}' />
			<input type="hidden" id="account_complianceExpiry" value='${regDetails.account.complianceExpiry}' />
			<input type="hidden" id="fraugster_eventServiceLogId" value='${regDetails.fraugster.id}' />
			<input type="hidden" id="contact_isOnQueue" value='${regDetails.isOnQueue}' />
			<input type="hidden" id="documentCategory" value='Registration' />
			<input type="hidden" id="orgganizationCode" value='${regDetails.account.orgCode}' />
			<input type="hidden" id="isRecordLocked" value='${regDetails.locked}' />
			<input type="hidden" id="furPayInDetailsTotalRecordsPayInId"
			value='${holisticAccountResponse.furtherPaymentDetails.payInDetailsTotalRecords}' />
		<input type="hidden" id="furPayOutDetailsTotalRecordsPayInId"
			value='${holisticAccountResponse.furtherPaymentDetails.payOutDetailsTotalRecords}' />
			
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
	
	<div id="AccountHistorypopups" class="popupDiv"
		title="Client #${regDetails.account.tradeAccountNumber} original details">
		<div id="accountHistory" class="popupTextAreaAccountHistory popup">
		<div class="scrollpane--large-h scrollpane--borderless"></div>
		</div>
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
	
	<div id = "account_Details" class="boxpanel--shadow" style="" >

										<div id="accountContactHistoryId" class="grid">
											</div> 

										</div>
										
										
	<div id = "contact_Details" class="boxpanel--shadow" style="display:none" >

														<div class="grid">

															<div class="grid__row">

																<div class="grid__col--6">
																	<dl class="split-list">

																				<dt>Name</dt>
																				<dd id = "contact-furtherclient-Name-popup" ></dd>
																				
																				<dt>Job title</dt>
																				<dd id = "contact-furtherclient-jobtitle-popup" ><%-- ${contactDetail.currentContact.jobTitle} --%></dd>

																				<dt>Position of significance</dt>
																				<dd id = "contact-furtherclient-positionOfSignificance-popup" ><%-- ${contactDetail.currentContact.positionOfSignificance} --%></dd>

																				<dt>Authorised signatory</dt>
																				<dd id = "contact-furtherclient-authorisedSignatory-popup" ><%-- ${contactDetail.currentContact.authorisedSignatory} --%></dd>

																				<dt>Country of nationality</dt>
																				<dd id = "contact-furtherclient-nationality-popup" ></dd>

																				<dt>Country of residence</dt>
																				<dd id = "contact-furtherclient-countryOfResidence-popup" ><%-- ${contactDetail.currentContact.countryOfResidence} --%></dd>
																				
																				<dt>Date of birth</dt>
																				<dd id = "contact-furtherClient-dateofbirth-popup" ></dd>
																				
																				<dt>Occupation</dt>
																				<dd id = "contact-furtherclient-Occupation-popup" ></dd>
																				
																				<dt>ipAddress</dt>
																				<dd id = "contact-furtherClient-ipAddress-popup" ></dd>
																				
																				<dt>isCountrySupported</dt>
																				<dd id = "contact-furtherclient-isCountrySupported-popup" ></dd>
																		
																				<dt>Workphoneext</dt>
																				<dd id = "contact-furtherclient-workphoneext-popup" ></dd>
																		
																				<dt>isPrimaryContact</dt>
																				<dd id = "contact-furtherclient-isPrimaryContact-popup" ></dd>
																		
																		<!-- <dt>Address</dt>
																		<dd class="wordwrap" id="contact-furtherclient-address-popup"></dd>

																		<dt>Phone</dt>
																		<dd id="contact-furtherclient-phone-popup"></dd>

																		<dt>Mobile</dt>
																		<dd id="contact-furtherclient-mobile-popup"></dd>

																		<dt>Email</dt>
																		<dd id="contact-furtherclient-email-popup">
																		</dd>

																		<dt>Country of nationality</dt>
																		<dd id="contact-furtherclient-nationality-popup"></dd> -->

																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																				<dt>Contact SF ID</dt>
																				<dd id = "contact-furtherclient-crmContactId-popup" ></dd>
																				
																				<dt>TradeContactId</dt>
																				<dd id = "contact-furtherclient-tradeContactId-popup" ></dd> 
																				
																				<dt>Phone</dt>
																				<dd id = "contact-furtherclient-phone-popup" ></dd>

																				<dt>Mobile</dt>
																				<dd id = "contact-furtherclient-mobile-popup" ></dd>

																				<dt>Email</dt>
																				<dd id = "contact-furtherclient-email-popup" ></dd>

																				<dt>Mailing address</dt>
																				<dd id = "contact-furtherclient-address-popup" class="wordwrap"><%-- ${contactDetail.currentContact.address} --%></dd>

																				<dt>Address type</dt>
																				<dd id = "contact-furtherclient-addressType-popup" ><%-- ${contactDetail.currentContact.addressType} --%></dd>
																				
																				<dt>Work phone</dt>
																		        <dd id = "contact-furtherclient-workphone-popup" ></dd>
																				
																				<dt>isUsClient</dt>
																				<dd id = "contact-furtherclient-usClient-popup" ></dd>
																				
																				<dt>Designation</dt>
																				<dd id = "contact-furtherclient-designation-popup" ></dd>
																		
																		        
																		<!-- <dt>Is US client</dt>
																		<dd id="contact-furtherclient-usClient-popup"></dd>

																		<dt>IP Address</dt>
																		<dd id="contact-furtherclient-ipAddress-popup"></dd>
																		
																		<dt>Date of birth</dt>
																		<dd  id="contact-furtherClient-dateofbirth-popup"></dd>
																		
																		<dt>Work phone</dt>
																		<dd id="contact-furtherclient-workphone-popup"></dd> -->
																		
																	</dl>
																</div>

															</div>

														</div>

													</div>
										
   <div id="ContactHistorypopups" class="popupDiv"
		title="Client #${regDetails.account.tradeAccountNumber} original details">
		<div id="contactHistory" class="popupTextAreaAccountHistory">
		<div class="scrollpane--large-h scrollpane--borderless"></div>
		</div>
	</div>
	
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/holisticDetails.js"></script>
<!--<script type="text/javascript" src="resources/js/regDetails.js"></script>  -->
	<script type="text/javascript" src="resources/js/jsontotable.js"></script>	
	<script type="text/javascript" src="resources/js/wallet.js"></script>
	<script type="text/javascript" src="resources/js/fxTicket.js"></script>	
</body>

</html>