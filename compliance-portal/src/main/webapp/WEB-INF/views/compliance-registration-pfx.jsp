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
  <link rel="stylesheet" href="resources/css/neo4j/neo4jd3.css">
  
   <style>
            
   </style>
  
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
						<c:choose>
					<c:when test="${regDetails.source == 'queue' }">
						<c:set var="redirectUrl" value="/compliance-portal/reg"/>
						<c:set var="redirectTo" value="Registration queue"/>
					</c:when>
					<c:otherwise>
						<c:set var="redirectUrl" value="/compliance-portal/regReport"/>
						<c:set var="redirectTo" value="Registration report"/>
					</c:otherwise>
				</c:choose>
							<form id="redirectQueueForm" action="${redirectUrl }" method="post">
							<div class="grid__col--9">
								<h1>
									Client #<span id="account_tradeAccountNum">${regDetails.account.tradeAccountNumber}</span>
									
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
							<div class="grid__col--3">

								<div class="toast">
									<c:choose>
										<c:when test="${fn:length(regDetails.otherContacts) gt 0}">
											<span id="regDetails_checkOtherCon"
												class="message--toast rhs page-load"> <i
												class="material-icons">assignment_ind</i> <a
												href="#accordion-section-more-people"
												class="accordion-trigger"
												data-accordion-section="accordion-section-more-people">${fn:length(regDetails.otherContacts)}
													more person on this account</a>
											</span>
										</c:when>
										<c:otherwise>
											<span id="regDetails_checkOtherCon" class="rhs page-load">
											</span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>

						</div>

					</div>

						<div id="main-content__body">
							<div  class="message--positive" style="display: none">
							<div class="copy">
							</div>
						</div>
						  <div class="message--negative" style="display: none">
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
												<c:choose>
													<c:when test="${regDetails.currentContact.complianceStatus == 'INACTIVE' || regDetails.currentContact.complianceStatus == 'REJECTED'}">
														<p id ="contact_status"><span class="indicator--negative" id="contact_compliacneStatus">${regDetails.currentContact.complianceStatus}</span></p>
													</c:when>
													<c:when test="${regDetails.currentContact.complianceStatus =='ACTIVE'}">
														<p id ="contact_status"><span class="indicator--positive" id="contact_compliacneStatus">${regDetails.currentContact.complianceStatus}</span></p>
													</c:when>
													<c:otherwise>
														<p id ="contact_status"><span class="indicator--negative" id="contact_compliacneStatus">${'INACTIVE'}</span></p>
													</c:otherwise>
												</c:choose>
												</div>

												<div class="grid__col--6">

													<div id="lock" class="f-right">
													<!-- <object  class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
												 	<!-- <img id="ajax-loader-lock-toggle" class="ajax-loader space-next" src="/img/ajax-loader.svg" width="50" height="50" alt="Loading...">  -->
												<c:choose>
													<c:when test="${regDetails.locked  && regDetails.lockedBy == regDetails.user.name}">
														<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> You own(s) this record</span>
														<div id="toggle-edit-lock" class="toggle f-right" >
															<a href="#" id="toggle-record-lock" onclick="lockResource()" class="toggle__option--on " data-ot="Lock this record to own it"><i class="material-icons">lock_outline</i></a>
															<a href="#" id="toggle-record-unlock" onclick="unlockResource()" class="toggle__option unlock-specific" data-ot="Unlock this record for others"><i class="material-icons unlock-specific">lock_open</i></a>
														</div>
													</c:when>
													<c:when test="${regDetails.locked}">
														<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> ${regDetails.lockedBy} own(s) this record</span>
													</c:when>
													<c:otherwise>
														<div id="toggle-edit-lock" class="toggle f-right" >
															<a href="#" id="toggle-record-lock" onclick="lockResource()" class="toggle__option " data-ot="Lock this record to own it"><i class="material-icons">lock_outline</i></a>
															<a href="#" id="toggle-record-unlock" onclick="unlockResource()" class="toggle__option--on unlock-specific" data-ot="Unlock this record for others"><i class="material-icons unlock-specific">lock_open</i></a>
														</div>
													</c:otherwise>
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

															<dt>Name</dt>
															<dd><span class="wordwrap"
															id="contact_name"><a href="#accordion-section-client-details" class="accordion-trigger" 
															data-accordion-section="accordion-section-client-details" >
															${regDetails.currentContact.name}</a></span></dd>
															<dt>Client type</dt>
															<dd id="account_clientType">${regDetails.account.clientType}</dd>

															<dt>Occupation</dt>
															<dd id="contact_occupation">${regDetails.currentContact.occupation}</dd>

															<dt>Email address</dt>
															<dd>
																<%-- <a id="contact_email"
																	href="${regDetails.currentContact.email}"> --%>${regDetails.currentContact.email}<!-- </a> -->
															</dd>
															
															<dt>Legal Entity</dt>
															<dd>${regDetails.account.legalEntity}</dd>
															
															<dt>Intuition Risk Level</dt>
															<dd>
																<c:choose>
																	<c:when test="${regDetails.intuitionRiskLevel == 'Low'}">
																		<p id ="regDetails_intuition_risk_level"><span class="indicator--positive" id="intuition_risk_level">${regDetails.intuitionRiskLevel}</span></p>
																	</c:when>
																	<c:when test="${regDetails.intuitionRiskLevel == 'Medium'}">
																		<p id ="regDetails_intuition_risk_level"><span class="indicator--neutral" id="intuition_risk_level">${regDetails.intuitionRiskLevel}</span></p>
																	</c:when>
																	<c:when test="${regDetails.intuitionRiskLevel == 'High'}">
																		<p id ="regDetails_intuition_risk_level"><span class="indicator--negative" id="intuition_risk_level">${regDetails.intuitionRiskLevel}</span></p>
																	</c:when>
																	<c:when test="${regDetails.intuitionRiskLevel == 'Extreme'}">
																		<p id ="regDetails_intuition_risk_level"><span class="indicator--extreme" id="intuition_risk_level">${regDetails.intuitionRiskLevel}</span></p>
																	</c:when>
																	<c:otherwise>
																		<p id="regDetails_intuition_risk_level">${regDetails.intuitionRiskLevel}</p>
																	</c:otherwise>
																</c:choose>
															</dd>

														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">

														<dl class="split-list">

															<%-- <dt>Source</dt>
															<dd id="account_source">${regDetails.account.source}</dd> --%>
															
															<dt>Date of birth</dt>
															<dd  id="contact_dateofbirth">${regDetails.currentContact.dob}</dd>

															<dt>Currency Pair</dt>
															<dd id="account_currencyPair">${regDetails.account.currencyPair}</dd>

															<dt>Estimated transaction value</dt>
															<dd id="account_estimatedTxnValue">${regDetails.account.estimTransValue}</dd>

															<dt>Purpose of transaction</dt>
															<dd id="account_purposeOfTxn">${regDetails.account.purposeOfTran}</dd>
															
															<dt>AI ETV Band</dt>
															<c:choose>
																<c:when test = "${ !empty regDetails.account.conversionPrediction.eTVBand }">
																	<dd id="account_conversionpredictionetvband">${regDetails.account.conversionPrediction.eTVBand}</dd>
																</c:when>
																<c:otherwise>
																	<dd id="account_conversionpredictionetvband">----</dd>
																</c:otherwise>
															</c:choose>
														</dl>
													</div>

													<div class="grid__col--4 grid__col--pad-left">
														<dl class="split-list">

															<dt>Country of residence</dt>
															<dd id="contact_countryOfResidence">${regDetails.currentContact.countryOfResidence}</dd>

															<dt>Organization</dt>
															<dd id="account_organisation">${regDetails.account.orgCode}</dd>

															<dt>Source of funds</dt>
															<dd id="account_sourceOfFunds">${regDetails.account.sourceOfFund}</dd>
															
															<dt>Is primary contact</dt>
															<c:choose>

																<c:when test="${regDetails.currentContact.primaryContact}">
																	<dd id="is_primaryContact">Yes</dd>
																</c:when>
																<c:otherwise>
																	<dd id="is_primaryContact">No</dd>
																</c:otherwise>
															</c:choose>
															
															<dd><input id="account_AccountDetails" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
															 value="VIEW ORIGINAL" onclick="showAccountHistory('${regDetails.account.id}')"><dd>
															
															<dd><input id="account_SocialData" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
															 value="VIEW SOCIAL DATA" onclick="getSocialData('${regDetails.currentContact.email}')"><dd>
															 
															 
									<!-- 	AT-2084- code for forget me button On details page by Shraddha p. -->				 	
							    <input id="forget_me_button" type="button" class="button--secondary button--small button--disabled modal-trigger" data-modal="modal-original-summary"
															 value="FORGET ME" onclick="dataAnonymize()"/>
														             	 <dd>
																			<input type="button" class="button--secondary button--small" id="fraud_ring_button-${regDetails.currentContact.crmContactId}" value="View FraudRing" onclick="showFraudRingGraph()">
																			<object id="gifloaderforfraudring${regDetails.currentContact.crmContactId}"  class="ajax-loader-lock-toggle" height="50" width="50" 
																				data="resources/img/ajax-loader.svg" 
																				preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;"></object>
														            	</dd>
								
													                    </dl>
												                    	</div>
															<div class="grid__col--12 grid__col--pad-left alertComplianceLog">
																<dl>														 
																<dt>Compliance log</dt><br>
  																<dd id="regDetails_alert_compliance_log" class="wordwrap">${regDetails.alertComplianceLog}</dd>
																</dl>
															</div>
															
															<!-- Changes for Fraud Ring Graph starts here -->
															<!-- <div class="grid__col--12 grid__col--pad-left"></div> -->
								
															<div id="FraudRingPopups" class="popupDiv" title="Fraud Ring Graph">
																<div id="system_not_available" style="display:none"><h1><center>System Not Available</center></h1></div>
																<div id="no_relation" style="display:none"><h1><center>No Information Found</center></h1></div>
																<div id="neo4jd3" class="popupTextAreaHolisticView popup"></div>
																<div id="node_summary" class="node_summary">
																	<div id="close_button" style="text-align: right;">
																		<button id="node__summary_close" type="button" class="ui-button ui-corner-all ui-widget ui-button-icon-only" title="Close" onclick="closeNodeSummary();">
																			<span class="ui-button-icon ui-icon ui-icon-closethick"></span><span class="ui-button-icon-space"></span>
																		</button>
																	</div>
																	<div id="loaderForNodeSummary" style="text-align: center, display:none;">
																		<svg width='20px' height='10px' xmlns="http://www.w3.org/2000/svg" viewBox="-40 -40 180 180" preserveAspectRatio="xMidYMid" class="node_loader">
																	      <rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect><circle cx="50" cy="50" r="42.5" stroke-dasharray="173.57299411083608 93.46238144429634" stroke="#2b76b6" fill="none" stroke-width="15">
	  																	  <animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s"></animateTransform></circle></svg>
																	</div>
																	<div id="node_summary_table_div">
																		<table id="node_summary_table"><tbody id="node_summary_table_body"></tbody></table>
																	</div>
																</div>
															</div>
															<input type= "hidden" id="fraudRingTreeData" value="">
															<!-- Changes for Fraud Ring Graph ends here -->
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
														href="javascript:void(0);" data-ot="Expand all checks info"><i onclick = "viewMoreLoadData(); showProviderResponse('${regDetails.fraugster.id}','FRAUGSTER','FraugsterChart');"
															class="material-icons">add</i></a></li>
													<li class="quick-control__control--close-all"><a
														href="javascript:void(0);" data-ot="Close all checks info"><i onclick = "viewMoreResetData()"
															class="material-icons">close</i></a></li>
												</ul>
											</div>
											<div class="accordion__section">

												<div id="regDetails_blacklist_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Blacklist
													<!-- Condition added by Vishal J to show 'NOT REQUIRED on UI' -->
														<c:if test="${regDetails.internalRule.blacklist.isRequired}">
															<c:if
																test="${regDetails.internalRule.blacklist.failCount gt 0}">
																<span id="regDetails_blackPass"
																	class="indicator--negative">${regDetails.internalRule.blacklist.failCount}</span>
															</c:if> <c:if
																test="${regDetails.internalRule.blacklist.passCount gt 0}">
																<span id="regDetails_blackNeg"
																	class="indicator--positive">${regDetails.internalRule.blacklist.passCount}</span>
															</c:if>
														</c:if>
													<!-- End of Condition added by Vishal J to show 'NOT REQUIRED on UI' -->
													</a>
												</div>

												<div class="accordion__content">
													<table class="fixed">
														<thead>
															<tr>
																<th class="center">Name</th>
																<th class="center">Phone</th>
																<th class="center">Email</th>
																<th class="center">Domain</th>
																<th class="center">IP</th>
																<th class="center">Overall status</th>
															</tr>
														</thead>
														<tbody id="regDetails_blacklist">
															<!-- Condition added by Vishal J to show 'NOT REQUIRED on UI' -->
															<c:choose>
															 <c:when test="${not empty regDetails.internalRule.blacklist}">
															 <c:choose>
																<c:when test="${regDetails.internalRule.blacklist.isRequired}">
																	<tr id = "blacklist_row">
																	
																		<c:if test="${regDetails.internalRule.blacklist.name eq 'Not Required'}">
																			<td class = "nowrap center">${regDetails.internalRule.blacklist.name}</td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.name eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.name eq 'true'}">
																			<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																			<br>${regDetails.internalRule.blacklist.nameMatchedData}</td>
																		</c:if>
																		<c:choose>
																			<c:when
																				test="${regDetails.internalRule.blacklist.phone}">
																				<td class="yes-cell"><i class="material-icons">check</i></td>
																			</c:when>
																			<c:otherwise>
																				<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																				<br>${regDetails.internalRule.blacklist.phoneMatchedData }</td>
																			</c:otherwise>
																		</c:choose>
																		<c:if test="${regDetails.internalRule.blacklist.email eq 'Not Required'}">
																			<td class = "nowrap center">${regDetails.internalRule.blacklist.email}</td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.email eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.email eq 'true'}">
																			<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																			<br>${regDetails.internalRule.blacklist.emailMatchedData }</td>
																		</c:if>	
																		
																		<c:if test="${regDetails.internalRule.blacklist.domain eq 'Not Required'}">
																			<td class = "nowrap center">${regDetails.internalRule.blacklist.domain}</td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.domain eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.domain eq 'true'}">
																			<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																			<br>${regDetails.internalRule.blacklist.domainMatchedData }</td>
																		</c:if>	
																		<c:if test="${regDetails.internalRule.blacklist.ip eq 'Not Required'}">
																			<td class = "nowrap center">${regDetails.internalRule.blacklist.ip}</td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.ip eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${regDetails.internalRule.blacklist.ip eq 'true'}">
																			<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																			<br>${regDetails.internalRule.blacklist.ipMatchedData}</td>
																		</c:if>																		  
																		<c:choose>
																			<c:when
																				test="${regDetails.internalRule.blacklist.status}">
																				<td class="yes-cell"><i class="material-icons">check</i></td>
																			</c:when>
																			<c:otherwise>
																				<td class="no-cell"><i class="material-icons">clear</i></td>
																			</c:otherwise>
																		</c:choose>															
																	</tr>
																</c:when>
																<c:otherwise>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>
																	<td class = "nowrap center">${regDetails.internalRule.blacklist.statusValue}</td>																
																</c:otherwise>
															</c:choose>
															 </c:when>
															</c:choose>
															
														<!-- End of Condition added by Vishal J to show 'NOT REQUIRED on UI' -->
														</tbody>
													</table>
													
													<p></p>
													<form id = "blacklist_ReCheckId">
														<p class="flush-margin eid-field">
															<input id="regDetails_blacklist_recheck" type="button"
																class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																onclick="resendBlacklist('CONTACT');" <c:out value="${buttonDisable}" />>
															<object id="gifloaderforBlacklistresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
															</object> 
															<span id="blacklist_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
															</span>
														</p>
													</form>
																					
												</div>
											</div>
											<div class="accordion__section">

												<div id="regDetails_kyc_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>EID
														<c:if test="${regDetails.kyc.isRequired}">														
															<c:if
																test="${regDetails.kyc.failCount gt 0}">
																<span id="regDetails_kycPass" class="indicator--negative">${regDetails.kyc.failCount}</span>
															</c:if> <c:if test="${regDetails.kyc.passCount gt 0}">
																<span id="regDetails_kycNeg" class="indicator--positive">${regDetails.kyc.passCount}</span>
															</c:if> 
														</c:if>
													</a>
												</div>

												<div class="accordion__content">

													<table>
														<thead>
															<tr>
																<th class="tight-cell">Check date/time</th>
																<th class="center">Performed</th>
																<th class="tight-cell">Verification result</th>
																<th class="tight-cell">Reference Id</th>
																<th>Date of birth</th>
																<th class="center">Overall Status</th>
															</tr>
														</thead>
														<tbody id="regDetails_eid">
														<!--Condition added to check whether KYC checking is required or not by Vishal J on 25th Jan-->
														<c:choose>
														  <c:when test="${regDetails.kyc.isRequired}">
															<tr>
																<td>${regDetails.kyc.checkedOn}</td>
																<c:choose>
																	<c:when test="${regDetails.kyc.eidCheck}">
																		<td class="yes-cell"><i class="material-icons">check</i></td>
																	</c:when>
																	<c:otherwise>
																		<td class="no-cell"><i class="material-icons">clear</i></td>
																	</c:otherwise>
																</c:choose>
																<td class="number">${regDetails.kyc.verifiactionResult}</td>
																<td><a href="javascript:void(0);" onclick="showProviderResponse('${regDetails.kyc.id}','KYC')">${regDetails.kyc.referenceId}</a></td>
																<td class="nowrap">${regDetails.kyc.dob}</td>
																<c:choose>
																	<c:when test="${regDetails.kyc.status}">
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
																<td>${regDetails.kyc.checkedOn}</td>
																<!-- We are showing 'Not Required' by following line (showing status value) -->
																<td class="nowrap">${regDetails.kyc.statusValue}</td>
																<td class="number">${regDetails.kyc.verifiactionResult}</td>
																<td><a href="javascript:void(0);" onclick="showProviderResponse('${regDetails.kyc.id}','KYC')">${regDetails.kyc.referenceId}</a></td>
																<td class="nowrap">${regDetails.kyc.dob}</td>
																<td class="nowrap">${regDetails.kyc.statusValue}</td>
															</tr>													  
														  </c:otherwise>
														</c:choose>
														<!--End of - Condition added to check whether KYC checking is required or not by Vishal J -->
														</tbody>
													</table>
										<a href="javascript:void(0);" id="viewMoreDetails_kyc"  class="load-more space-after" onclick="viewMoreDetails('KYC' ,'regDetails_eid','kycTotalRecordsId','leftRecordsIdKyc');">
											VIEW <span class="load-more__extra" id = "viewMore_KycId"> </span> MORE
											<span class="load-more__left" id= "leftRecordsIdKyc" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											 <object id="gifloaderforviewmore" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
											<!-- <img id="ajax-loader-clear-search" class="ajax-loader" src="resources/img/ajax-loader_20x20.png" width="16" height="16" alt="Loading..."/> -->
										</a>

													<form id = "kyc_ReCheckId">
													<c:choose>
													<c:when test="${regDetails.currentContact.isCountrySupported}">
													<p class="flush-margin eid-field">
															<input id="regDetails_kyc_recheck" type="button"
																class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																onclick="resendKyc();" <c:out value="${buttonDisable}" />>
																<object id="gifloaderforkycresend" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="kyc_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
														</p>
													</c:when>
													<c:otherwise>
														<p class="flush-margin eid-field"><input id="regDetails_kyc_recheck" type="submit" class="button--primary button--disabled no-lock-support" value="Repeat checks" disabled></p>
														<span id="kyc_error_field" class="form__field-error" hidden="hidden" >
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
													</c:otherwise>	
													</c:choose>	
													</form>
												
												</div>

											</div>
											<div class="accordion__section">

												<div id="regDetails_sanction_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Sanctions
													<!-- Condition added by Vishal J to show NOT REQUIRED status on UI -->
														<c:if test="${regDetails.sanction.isRequired}">	
															<c:if test="${regDetails.sanction.failCount gt 0}">
																<span id="regDetails_sanctionPass"
																	class="indicator--negative">${regDetails.sanction.failCount}</span>
															</c:if> <c:if test="${regDetails.sanction.passCount gt 0}">
																<span id="regDetails_sanctionNeg"
																	class="indicator--positive">${regDetails.sanction.passCount}</span>
															</c:if>
														</c:if>
													<!--End of Condition added by Vishal J to show NOT REQUIRED status on UI -->
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
															<tbody id="regDetails_sanction" class="sanction_contact">
															<!--condition added by Vishal J to show NOT REQUIRED on UI  -->
																<c:choose>
																	<c:when test="${regDetails.sanction.isRequired}">
																		<tr>
																			<td hidden="hidden" class="center">${regDetails.sanction.eventServiceLogId}</td>
																			<td>${regDetails.sanction.updatedOn}</td>
																			<td class="nowrap">${regDetails.sanction.updatedBy}</td>
																			<td><a href="javascript:void(0);"  onclick="showProviderResponse('${regDetails.sanction.eventServiceLogId}','SANCTION')">${regDetails.sanction.sanctionId}</a></td>
																			<td class="nowrap">${regDetails.sanction.ofacList}</td>
																			<td class="nowrap">${regDetails.sanction.worldCheck}</td>
																			<c:choose>
																				<c:when test="${regDetails.sanction.status }">
																					<td id="sanction_status" class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td id="sanction_status" class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
																		</tr>
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td hidden="hidden" class="center">${regDetails.sanction.eventServiceLogId}</td>
																			<td>${regDetails.sanction.updatedOn}</td>
																			<td class="nowrap">${regDetails.sanction.updatedBy}</td>
																			<td><a href="javascript:void(0);"  onclick="showProviderResponse('${regDetails.sanction.eventServiceLogId}','SANCTION')">${regDetails.sanction.sanctionId}</a></td>
																			<td class="nowrap">${regDetails.sanction.ofacList}</td>
																			<td class="nowrap">${regDetails.sanction.worldCheck}</td>
																			<td id="sanction_status" class="nowrap">${regDetails.sanction.statusValue}</td>
																		</tr>
																	</c:otherwise>
																</c:choose>
															<!--End of condition added by Vishal J to show NOT REQUIRED on UI  -->
															</tbody>
														</table>

                                        <a href="javascript:void(0);" id="viewMoreDetails_sanc"  class="load-more space-after"  onclick="viewMoreDetails('SANCTION' ,'regDetails_sanction','sanctionTotalRecordsId','leftRecordsIdSanc');">
											VIEW <span class="load-more__extra" id = "viewMore_SancId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdSanc" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											 <object id="gifloaderforviewmore" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
										</a>

														<div class="boxpanel--shadow space-after sanction-field">
															<fieldset>
																<legend>Update selected sanctions</legend>
																<div class="grid">
																	<div class="grid__row">
																		<div class="grid__col--6">
																			<div class="form__field-wrap">

																				<p class="label">Which field?</p>

																				<div id="singlelist-sanction-field"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">
																								<li><label for="rad-sanction-field-2">
																										<input id="rad-sanction-field-2" type="radio"
																										name="regDetails_updateField" value="ofaclist" />OFAC
																										List
																								</label></li>
																								<li><label for="rad-sanction-field-3">
																										<input id="rad-sanction-field-3" type="radio"
																										name="regDetails_updateField"
																										value="worldcheck" />World Check
																								</label></li>
																							</ul>

																						</fieldset>

																						<span class="clickpanel__arrow"></span>

																					</div>

																				</div>
																			</div>
																		</div>

																		<div class="grid__col--6 grid__col--pad-left">

																			<div class="form__field-wrap">

																				<p class="label">Set this field to...</p>

																				<div id="singlelist-sanction-field-val"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">

																								<li><label for="rad-sanction-fieldval-1">
																										<input id="rad-sanction-fieldval-1"
																										type="radio"
																										name="regDetails_updateField_Value"
																										value="Confirmed hit" /> Confirmed hit
																								</label></li>
																								<li><label for="rad-sanction-fieldval-2">
																										<input id="rad-sanction-fieldval-2"
																										type="radio"
																										name="regDetails_updateField_Value"
																										value="Safe" /> Safe
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
																<span id="sanction_update_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																	<input id="regDetails_updateSanction" type="button"
																		class="<c:out value="${buttonClass}"/>" value="Apply"
																		onclick="updateSanction()" <c:out value="${buttonDisable}"/> />
																		 <object id="gifloaderforupdatesanction" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																	
																</p>

															</fieldset>

														</div>

													</form>

													<form>
														<p class="flush-margin sanction-field">
															<input id="regDetails_sanction_recheck" type="button"
																class="<c:out value="${buttonClass}"/> sanction-specific" value="Repeat checks"
																onclick="resendSanction();" <c:out value="${buttonDisable}"/> />
																 <object id="gifloaderforresendsanction" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																<span id="sanction_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
															<!-- <a href="#" class="button--primary" onclick="resendSanction()">Repeat checks</a></p> -->
													</form>

												</div>

											</div>
		
											<div class="accordion__section">

												<div  id="regDetails_fraugster_indicatior"  class="accordion__header">
													<a href="#"  onclick="showProviderResponse('${regDetails.fraugster.id}','FRAUGSTER','FraugsterChart')"><i class="material-icons">add</i>FRAUDPREDICT
														<%-- <c:if test="${regDetails.fraugster.isRequired}"> --%>	
															<c:if test="${regDetails.fraugster.failCount gt 0}">
																<span id="regDetails_fraugsterFail"
																	class="indicator--negative">${regDetails.fraugster.failCount}</span>
															</c:if> <c:if test="${regDetails.fraugster.passCount gt 0}">
																<span id="regDetails_fraugsterPass"
																	class="indicator--positive">${regDetails.fraugster.passCount}</span>
															</c:if>
														<%-- </c:if> --%>
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
														<tbody id="regDetails_fraugster">
															<c:choose>
																	<c:when test="${regDetails.fraugster.isRequired}">
																		<tr href="javascript:void(0);" onclick="showProviderResponse('${regDetails.fraugster.id}','FRAUGSTER','FraugsterChart')">
																<td>${regDetails.fraugster.createdOn }</td>
																<td class="nowrap">${regDetails.fraugster.updatedBy }</td>
																<td class=""><a href="#"  onclick="showProviderResponse('${regDetails.fraugster.id}','FRAUGSTER')">${regDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${regDetails.fraugster.score }</td>
																<c:choose>
																	<c:when test="${regDetails.fraugster.status }">
																					<td id="fraugster_status" class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td id="fraugster_status" class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
															</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr>
																<td>${regDetails.fraugster.createdOn }</td>
																<td class="nowrap">${regDetails.fraugster.updatedBy }</td>
																<td class="nowrap"><a href="#"  onclick="showProviderResponse('${regDetails.fraugster.id}','FRAUGSTER')">${regDetails.fraugster.fraugsterId }</a></td>
																<td class="nowrap" class="number">${regDetails.fraugster.score }</td>
																<td id="fraugster_status"  class="nowrap">${regDetails.fraugster.statusValue }</td>
															</tr>	
																	</c:otherwise>
																</c:choose>															
														</tbody>
													</table>
                                        <a href="javascript:void(0);" id="viewMoreDetails_fraug"  class="load-more space-after"  onclick="viewMoreDetails('FRAUGSTER' ,'regDetails_fraugster','fraugsterTotalRecordsId','leftRecordsIdFraug');">
											VIEW <span class="load-more__extra" id = "viewMore_FraugId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdFraug" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
										</a>
												<form id = "fraugster_ReCheckId">
													<p class="flush-margin eid-field">
															<input id="regDetails_fraugster_recheck" type="button"
																class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																onclick="resendFraugster();" <c:out value="${buttonDisable}" />>
																<object id="gifloaderforresendfraugster" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
																</object> 
																<span id="fraugster_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
														</p>
													</form>
														<!-- <form>
			<p class="flush-margin"><a href="#" class="button--primary">Repeat checks</a></p>
		</form> -->
		
													<div id = "boxpanel-space-before" class="boxpanel space-before">
														<div id="pfx-fraugster-chart"></div>
													</div>
												</div>

											</div>
											<div class="accordion__section">
												
												<div id="regDetails_customcheck_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Custom Checks
														<%--<c:if test="${regDetails.internalRule.customCheck.failCount gt 0}">
																<span id="regDetails_custPass"
																	class="indicator--negative">${regDetails.internalRule.customCheck.customCheckTotalRecords}</span>
														</c:if> 
														<c:if test="${regDetails.internalRule.customCheck.passCount gt 0}">
																<span id="regDetails_custNeg"
																	class="indicator--positive">${regDetails.internalRule.customCheck.customCheckTotalRecords}</span>
															</c:if> --%>
													</a>
												</div>

												<div class="accordion__content">
													<table>
														<thead>
															<tr>
																<th class="tight-cell">Check date/time</th>
																<th class="tight-cell">Rules</th>
																<th>Status</th>
															</tr>
														</thead>
														<tbody id="regDetails_customchecks">
															<!-- Condition added by Vishal J to show 'Not Required' status on UI -->
															<c:choose>
																<c:when test="${regDetails.internalRule.customCheck.ipCheck.isRequired}">
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.ipCheck.checkedOn}</td>
																		<td class="nowrap">IP Distance Check</td>
																		<td class="breakword">${regDetails.internalRule.customCheck.ipCheck.status}     ${regDetails.internalRule.customCheck.ipCheck.ipCity} ${regDetails.internalRule.customCheck.ipCheck.ipCountry} ${regDetails.internalRule.customCheck.ipCheck.geoDifference}</td>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.ipCheck.checkedOn}</td>
																		<td class="nowrap">IP Distance Check</td>
																		<td class="nowrap">${regDetails.internalRule.customCheck.ipCheck.statusValue}</td>
																	</tr>
																</c:otherwise>
															</c:choose>		
															
															<c:choose>
																<c:when test="${regDetails.internalRule.customCheck.globalCheck.isRequired}">
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.globalCheck.checkedOn}</td>
																		<td class="nowrap">Global Check</td>
																		<td class="nowrap">${regDetails.internalRule.customCheck.globalCheck.status}</td>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.globalCheck.checkedOn}</td>
																		<td class="nowrap">Global Check</td>
																		<td class="nowrap">${regDetails.internalRule.customCheck.globalCheck.statusValue}</td>
																	</tr>
																</c:otherwise>
															</c:choose>
															
															<c:choose>
																<c:when test="${regDetails.internalRule.customCheck.countryCheck.isRequired}">
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.countryCheck.checkedOn}</td>
																		<td class="nowrap">Country Check</td>
																		<td class="nowrap">${regDetails.internalRule.customCheck.countryCheck.status}</td>
																	</tr>
																</c:when>
																<c:otherwise>
																	<tr class="talign">
																		<td class="nowrap">${regDetails.internalRule.customCheck.countryCheck.checkedOn}</td>
																		<td class="nowrap">Country Check</td>
																		<td class="nowrap">${regDetails.internalRule.customCheck.countryCheck.statusValue}</td>
																	</tr>
																</c:otherwise>
															</c:choose>
															<!--End of Condition added by Vishal J to show 'Not Required' status on UI -->
															
															<%-- <tr class="talign">
																<td class="nowrap">${regDetails.internalRule.ip.checkedOn}</td>
																<td>${regDetails.internalRule.ip.ipAddress}</td>
																<td>
																	<ul>
																		<c:forEach var="rule"
																			items="${regDetails.internalRule.ip.ipRule}">
																			<li>${rule.description}</li>
																		</c:forEach>
																	</ul>
																</td>
															</tr> --%>
														</tbody>
													</table>

                                      <%--  <a href="javascript:void(0);" id="viewMoreDetails_CustomChk"  class="load-more space-after"  onclick="viewMoreDetails('CUSTOMCHECK' ,'regDetails_customchecks','customCheckTotalRecordsId','leftRecordsIdCustomChk');">
											VIEW <span class="load-more__extra" id = "viewMore_CustomChkId"> </span> MORE
											<span class="load-more__left" id= "leftRecordsIdCustomChk" > </span>
											  <!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading...">  -->
											<object id=gifloader class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="display:none;">
											</object> 
										</a> --%>
										
											</div>
											</div>
											
											<%-- Start TM Tab - AT-4114 --%>
											<div class="accordion__section">

												<div  id="regDetails_intuition_indicatior"  class="accordion__header">
													<a href="#"><i class="material-icons">add</i> Intuition
															<c:if test="${regDetails.intuition.failCount gt 0}">
																<span id="regDetails_intuitionFail"
																	class="indicator--negative">${regDetails.intuition.failCount}</span>
															</c:if> 
															<c:if test="${regDetails.intuition.passCount gt 0}">
																<span id="regDetails_intuitionPass"
																	class="indicator--positive">${regDetails.intuition.passCount}</span>
															</c:if>
													</a>
												</div>
												
												<div class="accordion__content">

													<table>
														<thead>
															<tr>
																<th class="tight-cell sorted desc">Created On <i></i></th>
																<th>Updated By</th>
																<th>Correlation Id</th>
																<th>Intuition Risk Level</th>
																<th>ProfileScore</th>
																<th>RuleScore</th>
																<th>Status</th>
															</tr>
														</thead>
														<tbody id="regDetails_intuition">
															<c:choose>
																	<c:when test="${regDetails.intuition.isRequired}">
																		<tr href="javascript:void(0);" onclick="showProviderResponse('${regDetails.intuition.id}','TRANSACTION_MONITORING')">
																			<td>${regDetails.intuition.createdOn }</td>
																			<td class="wrapword">${regDetails.intuition.updatedBy }</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${regDetails.intuition.id}','TRANSACTION_MONITORING')">${regDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${regDetails.intuition.riskLevel}</td>
																			<td class="nowrap" class="number">${regDetails.intuition.profileScore}</td>
																			<td class="nowrap" class="number">${regDetails.intuition.ruleScore}</td>
																			<c:choose>
																				<c:when test="${regDetails.intuition.status}">
																					<td id="intuition_status" class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td id="intuition_status" class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
																		</tr>	
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td>${regDetails.intuition.createdOn }</td>
																			<td class="wrapword">${regDetails.intuition.updatedBy }</td>
																			<td class="wrapword"><a href="#"  onclick="showProviderResponse('${regDetails.intuition.id}','TRANSACTION_MONITORING')">${regDetails.intuition.correlationId}</a></td>
																			<td class="nowrap">${regDetails.intuition.riskLevel}</td>
																			<td class="nowrap" class="number">${regDetails.intuition.profileScore}</td>
																			<td class="nowrap" class="number">${regDetails.intuition.ruleScore}</td>
																			<td id="intuition_status"  class="nowrap">${regDetails.intuition.statusValue }</td>
																		</tr>	
																	</c:otherwise>
																</c:choose>															
														</tbody>
													</table>
			                                        <a href="javascript:void(0);" id="viewMoreDetails_intuition"  class="load-more space-after"  onclick="viewMoreDetails('TRANSACTION_MONITORING' ,'regDetails_intuition','intuitionTotalRecordsId','leftRecordsIdIntuition');">
														VIEW <span class="load-more__extra" id = "viewMore_IntuitionId" > </span> MORE
														<span class="load-more__left" id= "leftRecordsIdIntuition" > </span>
													</a>
												</div>

											</div>
											<%-- End TM tab - AT-4114 --%>
											
											<%--Start ADD ONFIDO Details on UI--%>
											<div class="accordion__section">
												<div id="regDetails_onfido_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>ONFIDO
														<c:if test="${regDetails.onfido.isRequired}">	
															<c:if test="${regDetails.onfido.failCount gt 0}">
																<span id="regDetails_onfidoPass"
																	class="indicator--negative">${regDetails.onfido.failCount}</span>
															</c:if> <c:if test="${regDetails.onfido.passCount gt 0}">
																<span id="regDetails_onfidoNeg"
																	class="indicator--positive">${regDetails.onfido.passCount}</span>
															</c:if>
														</c:if>
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
																	<th>Onfido ID</th>
																	<th>Reviewed</th>
																	<th>Status</th>
																</tr>
															</thead>
															<tbody id="regDetails_onfido" class="onfido_contact">
																<c:choose>
																	<c:when test="${regDetails.onfido.isRequired}">
																		<tr>
																			<td hidden="hidden" class="center">${regDetails.onfido.eventServiceLogId}</td>
																			<td>${regDetails.onfido.updatedOn}</td>
																			<td class="nowrap">${regDetails.onfido.updatedBy}</td>
																			<td><a href="javascript:void(0);"  onclick="showOnfidoProviderResponse('${regDetails.onfido.eventServiceLogId}','ONFIDO')">${regDetails.onfido.onfidoId}</a></td>
																			<td class="nowrap">${regDetails.onfido.reviewed}</td>
																			<c:choose>
																				<c:when test="${regDetails.onfido.status }">
																					<td id="onfido_status" class="yes-cell"><i class="material-icons">check</i></td>
																				</c:when>
																				<c:otherwise>
																					<td id="onfido_status" class="no-cell"><i class="material-icons">clear</i></td>
																				</c:otherwise>
																			</c:choose>
																		</tr>
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td hidden="hidden" class="center">${regDetails.onfido.eventServiceLogId}</td>
																			<td>${regDetails.onfido.updatedOn}</td>
																			<td class="nowrap">${regDetails.onfido.updatedBy}</td>
																			<td><a href="javascript:void(0);"  onclick="showOnfidoProviderResponse('${regDetails.onfido.eventServiceLogId}','ONFIDO')">${regDetails.onfido.onfidoId}</a></td>
																			<td class="nowrap">${regDetails.onfido.reviewed}</td>
																			<td id="onfido_status" class="nowrap">${regDetails.onfido.statusValue}</td>
																		</tr>
																	</c:otherwise>
																</c:choose>
															</tbody>
														</table>

                                        <a href="javascript:void(0);" id="viewMoreDetails_onfido"  class="load-more space-after"  onclick="viewMoreDetails('ONFIDO' ,'regDetails_onfido','onfidoTotalRecordsId','leftRecordsIdOnfido');">
											VIEW <span class="load-more__extra" id = "viewMore_OnfidoId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdOnfido" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											 <object id="gifloaderforviewmore" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
										</a>

														<div class="boxpanel--shadow space-after onfido-field">
															<fieldset>
																<legend>Update Reviewed Status</legend>
																<div class="grid">
																	<div class="grid__row">
																		<!-- <div class="grid__col--6">
																			<div class="form__field-wrap">

																				<p class="label">Which field?</p>

																				<div id="singlelist-onfido-field"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">
																								<li><label for="rad-onfido-field-4">
																										<input id="rad-onfido-field-4" type="radio"
																										name="regDetails_updateField" value="reviewed" />Reviewed
																								</label></li>
																							</ul>

																						</fieldset>

																						<span class="clickpanel__arrow"></span>

																					</div>

																				</div>
																			</div>
																		</div> -->

																		<div class="grid__col--6 grid__col--pad-left">

																			<div class="form__field-wrap">

																				<p class="label">Set this field to...</p>

																				<div id="singlelist-onfido-field-val"
																					class="singlelist clickpanel--right">

																					<p class="singlelist__chosen clickpanel__trigger">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options">

																								<li><label for="rad-onfido-fieldval-1">
																										<input id="rad-onfido-fieldval-1"
																										type="radio"
																										name="regDetails_updateOnfidoField_Value"
																										value="Accepted" /> Accepted
																								</label></li>
																								<li><label for="rad-onfido-fieldval-2">
																										<input id="rad-onfido-fieldval-2"
																										type="radio"
																										name="regDetails_updateOnfidoField_Value"
																										value="Rejected" /> Rejected
																								</label></li>
																								<li><label for="rad-onfido-fieldval-3">
																										<input id="rad-onfido-fieldval-3"
																										type="radio"
																										name="regDetails_updateOnfidoField_Value"
																										value="Fraud" /> Fraud
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
																<span id="onfido_update_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
																</span>
																	<input id="regDetails_updateOnfido" type="button"
																		class="<c:out value="${buttonClass}"/>" value="Apply"
																		onclick="updateOnfido()" <c:out value="${buttonDisable}"/> />
																		 <object id="gifloaderforupdateonfido" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																	
																</p>

															</fieldset>

														</div>

													</form>

												</div>
											</div>
											<%--END ADD ONFIDO Details on UI--%>
											<div class="accordion__section">

												<div id="doc_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Attached
														documents <c:if
															test="${fn:length(regDetails.documents) gt 0}">
															<span id="docConunt" class="indicator">${fn:length(regDetails.documents)}</span>
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
															<c:forEach var="document" items="${regDetails.documents}">
																<tr class="talign">
																	<td>${document.createdOn}</td>
																	<td>${document.createdBy}</td>
																	<td class="breakword"><a href="${document.url}">${document.documentName}</a></td>
																	<td class="breakword">${document.documentType}</td>
																	<td class="wrap-cell">${document.note}</td>
																	<c:choose>
																	<c:when test="${document.authorized }">
																		<td class="wrap-cell"><input type="checkbox" class="enableDocButton" name="" value="" checked="checked"></td>
																	</c:when>
																	<c:otherwise>
																		<td class="wrap-cell"><input type="checkbox" class="enableDocButton" name="" value=""></td>
																	</c:otherwise>
																	</c:choose>
														           <td hidden="hidden" id="documentId-${document.documentName}">${document.documentId}</td>
																</tr>
															
															</c:forEach>
														</tbody>
														<c:if test="${fn:length(regDetails.documents) > 0}">
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
														<form id="attachDocUmentContactFormId">
															<fieldset>
																<legend>Attach another document</legend>

																<div class="grid space-after">

																	<div class="grid__row">

																		<div class="grid__col--6">

																			<div class="form__field-wrap">

																				<p class="label">Document type</p>

																				<div id="singlelist-document-type"
																					class="singlelist clickpanel--right">

																					<p id="docTypeSelectionContactId" class="singlelist__chosen clickpanel__trigger2">Please
																						select</p>

																					<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																					<div class="clickpanel__content--hidden">

																						<fieldset>

																							<ul class="singlelist__options multilist__options">
																								<c:forEach var="documentList" items="${regDetails.documentList}"  varStatus="loop">
																									
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
															<%--AT-3391 EU POI UPDATE --%>
								                             <div>
															 <input id="eu_poi_update_button" type="button" class="button--secondary button--small button--disabled modal-trigger" data-modal="modal-original-summary"
															 value="Approve POI" onclick="approvePoi()"/> 
															 </div>
																
												
																<p class="right">
																  <input id="isDocumentAuthorized" type="checkbox" value="" name="isDocumentAuthorized">
																  <span style="margin-right:10px">Approve document </span>
																	<!-- Id given to upload documents button to make it enable and disable 
																		 Changes done by Vishal J -->
																	<input id="attach_document_button_id" type="button" class="<c:out value="${buttonClass}"/>"
																		value="Attach" onclick="uploadDocument('${regDetails.currentContact.crmAccountId}','${regDetails.currentContact.crmContactId}','text-note','file-choose-document','attachDoc','documentType','attach_document_button_id','${regDetails.account.orgCode}','gifloaderfordocumentloader');" <c:out value="${buttonDisable}"/> />
																<object id="gifloaderfordocumentloader" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																
																</p>
															</fieldset>
														</form>

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
														href="#" data-ot="Expand all checks info"><i onclick = "viewMoreLoadDataActLog()"
															class="material-icons">add</i></a></li>
													<li class="quick-control__control--close-all"><a
														href="#" data-ot="Close all checks info"><i onclick = "viewMoreResetActLogData()"
															class="material-icons">close</i></a></li>
												</ul>
											</div>
											<div id="accordion-section-more-people"
												class="accordion__section">

												<div id="regDetails_ohterCon_indicatior"
													class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Other
														people on this account <c:if
															test="${fn:length(regDetails.otherContacts) gt 0}">
															<span id="regDetails_otherConCount" class="indicator">${fn:length(regDetails.otherContacts)}</span>
														</c:if> </a>
												</div>

												<div class="accordion__content" >
												<form id="otherContactForm" action="/compliance-portal/registrationDetails" method="POST">	
													<c:choose>													
														<c:when test="${fn:length(regDetails.otherContacts) eq 0}">
															<div id="noOhterContact"class="errorMessage">No other contacts found.</div>
													
													
														<table id = "otherpeople_contacts" hidden="hidden">
															<thead>
																<tr>
																	<th class="tight-cell">Status</th>
																	<th>Name</th>
																</tr>
															</thead>
															<tbody id="regDetails_OtherPeople">
																<c:forEach var="otherContact"
																	items="${regDetails.otherContacts}">

																	<tr>
																		<td hidden="hidden">${otherContact.id}</td>
																		<%-- <c:if test="${otherContact.complianceStatus=='ACTIVE'}">
																			<td><span class="indicator--positive">${otherContact.complianceStatus}</span>
																		</td>
																		</c:if> --%>
																			<td><span class="indicator--negative">${otherContact.complianceStatus}</span>
																		</td>
																		<td class="valign"><a href="#"
																			onclick="getRegDetails(${otherContact.id},'${otherContact.custType}',this)">${otherContact.name}</a></td>
																	</tr>
																</c:forEach>
															</tbody>
													</table>

												
													</c:when>
													<c:otherwise>
														<div id="noOhterContact" class="errorMessage" hidden="hidden">No other contacts found.</div>
													<table id = "otherpeople_contacts">
														<thead>
															<tr>
																<th class="tight-cell">Status</th>
																<th>Name</th>
															</tr>
														</thead>
														<tbody id="regDetails_OtherPeople">
															<c:forEach var="otherContact"
																items="${regDetails.otherContacts}">

																<tr>
																	<td hidden="hidden">${otherContact.id}</td>															
																	<c:choose>
																		<c:when test="${otherContact.complianceStatus=='ACTIVE'}">
																			<td><span class="indicator--positive">${otherContact.complianceStatus}</span>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<td><span class="indicator--negative">${otherContact.complianceStatus}</span>
																			</td>
																		</c:otherwise>
																	</c:choose>
																	<td class="valign"><a href="#"
																		onclick="getRegDetails(${otherContact.id},'${otherContact.custType}',this)">${otherContact.name}</a></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													
													</c:otherwise>
													</c:choose>
<input type="hidden" id="othercontactId" value="" name="contactId"/>
<input type="hidden" id="otherContactsearchCriteria" value="" name="searchCriteria"/>
<input type="hidden" id="otherContactcustType" value="" name="custType"/>
<input type="hidden" id="source" value="${regDetails.source}" name="source"/>

													</form>
													
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
																		<dd class="wordwrap" id="contact_FurtherClient_Address">${regDetails.currentContact.address}</dd>

																		<dt>Registration in</dt>
																		<dd id="contact_FurtherClient_regIn">${regDetails.account.registrationInDate}</dd>

																		<dt>Registered</dt>
																		<dd id="contact_FurtherClient_regComplete">${regDetails.account.regCompleteAccount}</dd>
																		<%-- <dd id="contact_FurtherClient_regComplete">${regDetails.account.regCompleteAccount}</dd> --%> 
																		
																		<dt>Phone</dt>
																		<dd id="contact_FurtherClient_phone">${regDetails.currentContact.phone}</dd>

																		<dt>Mobile</dt>
																		<dd id="contact_FurtherClient_mobile">${regDetails.currentContact.mobile}</dd>

																		<dt>Email</dt>
																		<dd id="contact_FurtherClient_email">
																			<!-- <a href="mailTo:roger.federer@wimbledon.com"> -->${regDetails.currentContact.email}<!-- </a> -->
																		</dd>

																		<dt>Country of nationality</dt>
																		<dd id="contact_FurtherClient_nationality">${regDetails.currentContact.nationality}</dd>

																		<dt>Registration Mode</dt>
																		<dd id="account_FurtherClient_regMode">${regDetails.account.regMode}</dd>
																	
																		<dt>WhiteList Data</dt>
																	
																		<input id="account_AccountWhiteList" type="button" class="button--secondary button--small modal-trigger margin--left" data-modal="modal-original-summary" 
																		value="WhiteList Data" onclick="showAccountWhiteListData('${regDetails.currentContact.accountId}' , '${regDetails.account.orgCode}')">
																		
																	</dl>
																</div>

																<div class="grid__col--6 grid__col--pad-left">
																	<dl class="split-list">

																		<dt>Is US client</dt>
																		<c:choose>
																			<c:when
																				test="${regDetails.currentContact.isUsClient}">
																				<dd id="contact_FurtherClient_usClient">Yes</dd>
																			</c:when>
																			<c:otherwise>
																				<dd id="contact_FurtherClient_usClient">No</dd>
																			</c:otherwise>
																		</c:choose>

																		<dt>Service required</dt>
																		<dd id="account_FurtherClient_serviceReq">${regDetails.account.serviceRequired}</dd>

																		<dt>IP Address</dt>
																		<dd id="contact_FurtherClient_ipAddress">${regDetails.currentContact.ipAddress}</dd>
																		
																		<%-- <dt>Browser type</dt>
																		<dd id="account_FurtherClient_browserType">${regDetails.account.browserType}</dd>

																		<dt>Cookie info</dt>
																		<dd id="account_FurtherClient_cookiesInfo">${regDetails.account.cookieInfo}</dd> --%>

																		<dt>Referral text</dt>
																		<dd id="account_FurtherClient_refferalText">${regDetails.account.refferalText}</dd>

																		<dt>Affiliate name</dt>
																		<dd id="account_FurtherClient_affiliateName">${regDetails.account.affiliateName}</dd>
																		
																		<%-- <dt>Date of birth</dt>
																		<dd  id="account_FurtherClient_affiliateName">${regDetails.currentContact.dateofbirth}</dd>
																		 --%>
																		 
																		<dt>Source</dt>
																		<dd id="account_source">${regDetails.account.source}</dd>
																		
																		<dt>Work phone</dt>
																		<dd id="account_FurtherClient_affiliateName">${regDetails.currentContact.phoneWork}</dd>
																	
																		<dt>Device info</dt>
																	
																		<input id="account_FurtherClient_deviceInfo" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" 
																		value="View Device Info" onclick="showDeviceInfo('${regDetails.currentContact.accountId}')">
																		
																	</dl>
																</div>

															</div>

														</div>

													</div>

												</div>

											</div>
											<div id="accordion-section-activity-log" class="accordion__section">

												<div id="regDetails_activitylog_indicatior" class="accordion__header">
													<a href="#"><i class="material-icons">add</i>Activity
														log</a>
												</div>

												<div class="accordion__content">

													<div class="audit-trail" align="center">
														<section style="width:250px;">
															<p class="label filterInputHeader">Filter</p>
															<select id="SelectedAuditTrailFilter"
																style="border-color: #ccc;"
																onchange="getActivityLogsByModule('1','10', '${regDetails.currentContact.accountId}', 
																false , this.value, 'leftRecordsIdActLog_AuditTrail',
																'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetails_ActLog');">
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
																<th class="whiteSpacePreLine sorted desc"><a href="#">Activity
																		date/time <i></i>
																</a></th>
																<th class="whiteSpacePreLine"><a href="#">Trade Contract Number</a></th>
																<th><a href="#">User</a></th>
																<th><a href="#">Activity</th>
																<th><a href="#">Activity type</a></th>
																<th><a href="#">Comment</a></th>
															</tr>
														</thead>
														<tbody id="activityLog">
															<c:forEach var="activityData"
																items="${regDetails.activityLogs.activityLogData}">
																<tr class="talign">
																	<td>${activityData.createdOn}</td>
																	<td>---</td>
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
													
																										
										  <a href="javascript:void(0);" id="viewMoreDetails_ActLog"  class="load-more space-after"  onclick="viewMoreDetails('ACTIVITYLOG' ,'activityLog','actLogTotalRecordsId','leftRecordsIdActLog');">
											VIEW <span class="load-more__extra" id = "viewMore_ActLogId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdActLog" > </span>
											<!-- <img class="ajax-loader-lock-toggle" src="resources/img/ajax-loader.svg" type="image/svg+xml" width="16" height="16" alt="Loading..."> -->
											<!-- <object class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml">
											</object> -->
										  </a>
										 
										  <a href="javascript:void(0);" id="viewMoreAuditTrailDetails_ActLog"  class="load-more space-after"  
										  onclick="getViewMoreActivityLogsByModule('${regDetails.currentContact.accountId}', true, 'leftRecordsIdActLog_AuditTrail', 'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetails_ActLog');">
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
																		id="regDetails_watchlists">

																		<fieldset>
																			<input class="multilist__search space-after" type="search" placeholder="Search list">
																			<ul class="multilist__options">
																				<c:forEach var="watchlistRes"
																					items="${ regDetails.watchlist.watchlistData}"
																					varStatus="loop">
																					<li><label
																						for="chk-watchlist-${loop.index + 1}"> <c:choose>
																								<c:when test="${watchlistRes.value}">

																									<input id="chk-watchlist-${loop.index + 1}"
																										type="checkbox" checked="checked"
																										name="regDetails_watchlist[]"
																										value="${watchlistRes.name}" />
																										${watchlistRes.name}					
																							    </c:when>
																								<c:otherwise>
																									<input id="chk-watchlist-${loop.index + 1}"
																										type="checkbox" name="regDetails_watchlist[]"
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
<!-- PROFILE STATUS REASON BIGIN -->							
												<section>

													<h3 class="hidden">Update status</h3>

													<fieldset>

														<legend>Update status</legend>

														<ul class="form__fields--bare">

															<li class="form__field-wrap">

																<fieldset>

																	<legend class="label">Set status to...</legend>

																	<ul id="pillchoice-status" class="pill-choice">
																		<li><c:choose>
																				<c:when
																					test="${regDetails.currentContact.complianceStatus == 'ACTIVE' }">
																					<label
																						class="pill-choice__choice--positive pill-choice__choice--on"
																						for="contact_contactStatus_active"> <input
																						id="contact_contactStatus_active"
																						value="ACTIVE" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more-hide"
																						data-more-hide="input-more-areas-reasons" checked />
																						ACTIVE
																					</label>
																				</c:when>
																				<c:otherwise>
																					<label class="pill-choice__choice--positive"
																						for="contact_contactStatus_active"> <input
																						id="contact_contactStatus_active"
																						value="ACTIVE" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more-hide"
																						data-more-hide="input-more-areas-reasons" />
																						ACTIVE
																					</label>
																				</c:otherwise>
																			</c:choose></li>
																		<li><c:choose>
																				<c:when
																					test="${regDetails.currentContact.complianceStatus == 'INACTIVE' }">
																					<label
																						class="pill-choice__choice--negative pill-choice__choice--on"
																						for="contact_contactStatus_inactive"> <input
																						id="contact_contactStatus_inactive"
																						value="INACTIVE" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more"
																						data-more-area="input-more-inactive-reason"
																						checked /> INACTIVE
																					</label>
																				</c:when>
																				<c:otherwise>
																					<label class="pill-choice__choice--negative"
																						for="contact_contactStatus_inactive"> <input
																						id="contact_contactStatus_inactive"
																						value="INACTIVE" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more"
																						data-more-area="input-more-inactive-reason" />
																						INACTIVE
																					</label>
																				</c:otherwise>
																			</c:choose></li>
																		<li><c:choose>
																				<c:when
																					test="${regDetails.currentContact.complianceStatus == 'REJECTED' }">
																					<label
																						class="pill-choice__choice--negative pill-choice__choice--on"
																						for="contact_contactStatus_reject"> <input
																						id="contact_contactStatus_reject"
																						value="REJECTED" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more"
																						data-more-area="input-more-inactive-reason"
																						checked /> REJECTED
																					</label>
																				</c:when>
																				<c:otherwise>
																					<label class="pill-choice__choice--negative"
																						for="contact_contactStatus_reject"> <input
																						id="contact_contactStatus_reject"
																						value="REJECTED" type="radio"
																						name="regDetails_contactStatus_radio"
																						class="input-more"
																						data-more-area="input-more-inactive-reason" />
																						REJECTED
																					</label>
																				</c:otherwise>
																			</c:choose></li>
																	</ul>

																</fieldset>
															</li>

															<li class="form__field-wrap">

																<div id="input-more-areas-reasons"
																	class="input-more-areas">

																	<div id="input-more-inactive-reason"
																		class="input-more-areas__area--hidden">

																		<p class="label">Select a reason</p>

																		<div id="multilist-inactive-reasons"
																			class="multilist clickpanel--right">

																			<ul class="multilist__chosen">
																				<li class="clickpanel__trigger">Please select</li>
																			</ul>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden"
																				id="regDetails_contactStatusReasons">

																				<fieldset>

																					<ul class="multilist__options">
																						<c:forEach var="contactStatusReson"
																							items="${ regDetails.statusReason.statusReasonData}"
																							varStatus="loop">
																							<li><label
																								for="chk-inactive-reason-${loop.index + 1}">
																									<c:choose>
																										<c:when test="${contactStatusReson.value}">

																											<input
																												id="chk-inactive-reason-${loop.index + 1}"
																												type="checkbox" checked="checked"
																												name="regDetails_contactStatusReson[]"
																												value="${contactStatusReson.name}" />${contactStatusReson.name}
																												</c:when>
																										<c:otherwise>
																											<input
																												id="chk-inactive-reason-${loop.index + 1}"
																												type="checkbox"
																												name="regDetails_contactStatusReson[]"
																												value="${contactStatusReson.name}" />${contactStatusReson.name}	
																												</c:otherwise>
																									</c:choose>
																							</label></li>
																						</c:forEach>
																					</ul>

																				</fieldset>

																				<span class="clickpanel__arrow"></span>

																			</div>

																		</div>
																	</div>

																	<div id="input-more-reject-reasons"
																		class="input-more-areas__area--hidden">

																		<p class="label">Select a reason for rejecting</p>

																		<div id="multilist-reject-reasons"
																			class="multilist clickpanel--right">

																			<ul class="multilist__chosen">
																				<li class="clickpanel__trigger">Please select</li>
																			</ul>

																			<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																			<div class="clickpanel__content--hidden">

																				<fieldset>

																					<ul class="multilist__options">

																						<li><label for="chk-reject-reason-1">
																								<input id="chk-reject-reason-1" type="checkbox" />
																								First reject reason
																						</label></li>
																						<li><label for="chk-reject-reason-2">
																								<input id="chk-reject-reason-2" type="checkbox" />
																								Second reject reason
																						</label></li>
																						<li><label for="chk-reject-reason-3">
																								<input id="chk-reject-reason-3" type="checkbox" />
																								Third reject reason
																						</label></li>
																						<li><label for="chk-reject-reason-4">
																								<input id="chk-reject-reason-4" type="checkbox" />
																								Fourth reject reason
																						</label></li>
																						<li><label for="chk-reject-reason-5">
																								<input id="chk-reject-reason-5" type="checkbox" />
																								Fifth reject reason
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
<!-- PROFILE STATUS REASON ENDED -->					
												<section>

													<h3 class="hidden">Add comments</h3>

													<label class="legend" for="regDetails_comments">Add
														comments</label>
													<textarea id="regDetails_comments" maxlength="1024"></textarea>

												</section>
												<!--compliance log section START  -->
												<section>
													<h3 class="hidden">Add compliance log</h3>

													<label class="legend" for="regDetails_compliance_log">Add
														compliance log</label>
													<textarea id="regDetails_compliance_log" maxlength="1024"></textarea>

												</section>
												<!--compliance log section END  -->
												<section class="section--actions">
													<h3 class="hidden">Apply</h3>
													
													<input type="button" class="<c:out value="${buttonClass}"/>"
														id="regDetails_profile_update" value="Apply"
														onclick="executeActions(false);" <c:out value="${buttonDisable}"/>/>
														
														<input type="button" class="<c:out value="${buttonClass}"/>"
														id="regDetails_profile_update_and_unlock" value="Apply & UNLOCK"
														onclick="executeActions(true);" <c:out value="${buttonDisable}"/>/>	
														
														<object id="gifloaderforprofileupdate"  class="ajax-loader-lock-toggle" height="50" width="50" 
														data="resources/img/ajax-loader.svg" 
														preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object>
													<c:choose>
														<c:when test="${(regDetails.locked == null ||!regDetails.locked)}">
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
													
												</section>
												<span id="profile_update_error_field" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
												</span>
											</div>

										</form>

									</section>
									<c:if test='${not empty regDetails.activityLogs.activityLogData}'>
									<div class="boxpanel copy space-after">
										<small>Last updated by <strong>${regDetails.activityLogs.activityLogData[0].createdBy}</strong> on ${regDetails.activityLogs.activityLogData[0].createdOn} <br>
										<a href="#accordion-section-activity-log" class="accordion-trigger" data-accordion-section="accordion-section-activity-log">See activity log</a> for more details
										</small>
									</div>
									</c:if>
									</div>
									
							
							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

		</main>
		
		<form id="regDetailForm" action="/compliance-portal/registrationDetails" method="POST">
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/>
			<input type="hidden" id="custType" value="" name="custType"/>
		<%-- <c:choose>
			<c:when test="${regDetails.isPagenationRequired==true}">
				<ul class="pagination--fixed page-load" id="paginationBlock">
			<li class="pagination__jump--disabled"><a id="firstRecord"
				onclick="getRegFirstRecord('${regDetails.paginationDetails.firstRecord.custType}','${regDetails.paginationDetails.firstRecord.id}');" href="#" data-ot="First record"><i
					class="material-icons">first_page</i></a></li>
			<li class="pagination__jump"><a id="previousRecord" onclick="getRegPreviousRecord('${regDetails.paginationDetails.prevRecord.custType}','${regDetails.paginationDetails.prevRecord.id}');"
				href="#" data-ot="Previous record"><i class="material-icons">navigate_before</i></a>
			</li>
			<li class="pagination__jump"><a id="nextRecord" onclick="getRegNextRecord('${regDetails.paginationDetails.nextRecord.custType}','${regDetails.paginationDetails.nextRecord.id}');"
				href="#" data-ot="Next record"><i class="material-icons">navigate_next</i></a>
			</li>
			<li class="pagination__jump"><a id="lastRecord" onclick="getRegLastRecord('${regDetails.paginationDetails.totalRecords.custType}','${regDetails.paginationDetails.totalRecords.id}');"
				href="#" data-ot="Last record"><i class="material-icons">last_page</i></a>
			</li>
			<li class="pagination__text">Record <strong
				id="currentRecord">${regDetails.currentRecord }</strong>
				of <span id="totolRecords">${regDetails.totalRecords }</span>
			</li>
		</ul>
			</c:when>
		</c:choose> --%>
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
			value='${regDetails.searchCriteria}' /> <input type="hidden"
			id="contact_contactId" value='${regDetails.currentContact.id}' />
		<input type="hidden" id="contact_crmAccountId"
			value='${regDetails.currentContact.crmAccountId}' /> <input
			type="hidden" id="contact_crmContactId"
			value='${regDetails.currentContact.crmContactId}' /> <input
			type="hidden" id="contact_accountId"
			value='${regDetails.currentContact.accountId}' /> <input
			type="hidden" id="userResourceId"
			value='${regDetails.userResourceId}' />
			<input type="hidden" id="kycTotalRecordsId" value='${regDetails.kyc.kycTotalRecords}'/>
			<input type="hidden" id="sanctionTotalRecordsId" value='${regDetails.sanction.sanctionTotalRecords}'/>
			<input type="hidden" id="fraugsterTotalRecordsId" value='${regDetails.fraugster.fraugsterTotalRecords}'/>
			<input type="hidden" id="customCheckTotalRecordsId" value='${regDetails.internalRule.customCheck.customCheckTotalRecords}'/>
			<input type="hidden" id="actLogTotalRecordsId" value='${regDetails.activityLogs.totalRecords}'/>
			<input type="hidden" id="customerType" value='${regDetails.account.clientType}'/>
			<input type="hidden" id="account_complianceDoneOn" value='${regDetails.account.regCompleteAccount}' />
			<input type="hidden" id="account_registrationInDate" value='${regDetails.account.registrationInDate}' />
			<input type="hidden" id="account_complianceExpiry" value='${regDetails.account.complianceExpiry}' />
			<input type="hidden" id="fraugster_eventServiceLogId" value='${regDetails.fraugster.id}' />
			<input type="hidden" id="contact_isOnQueue" value='${regDetails.isOnQueue}' />
			<input type="hidden" id="documentCategory" value='Registration' />
			<input type="hidden" id="orgganizationCode" value='${regDetails.account.orgCode}' />
			<input type="hidden" id="isRecordLocked" value='${regDetails.locked}' />
			<input type="hidden" id="fraud_ring_button_id" value="fraud_ring_button-${regDetails.currentContact.crmContactId}" />
			<input type="hidden" id="fraud_ring_node_summary_details" value=""/>
			<input type="hidden" id="auditTrailActLogTotalRecords" value=""/>
			<input type="hidden" id="auditTrailActLogEntityType" value=""/>
			<input type="hidden" id="onfidoTotalRecordsId" value='${regDetails.onfido.onfidoTotalRecords}'/>
			<input type="hidden" id="onfidoReport" value='${regDetails.onfido.onfidoReport}'/>
			<input type="hidden" id="dataAnonStatus" value='${regDetails.dataAnonStatus}'/>
			<input type="hidden" id="poiExists" value='${regDetails.poiExists}'/>
			<input type="hidden" id="intuitionTotalRecordsId" value='${regDetails.intuition.intuitionTotalRecords}'/>
			<input type="hidden" id="accountTMFlag" value='${regDetails.account.accountTMFlag}'/>
			<input type="hidden" id="sanctionStatus" value='${regDetails.sanction.prevStatusValue }'/>
			<input type="hidden" id="blacklistStatus" value='${regDetails.internalRule.blacklist.prevStatusValue}'/>
			<input type="hidden" id="kycStatus" value='${regDetails.kyc.prevStatusValue}'/>
			<input type="hidden" id="fraugsterStatus" value='${regDetails.fraugster.prevStatusValue }'/>
			<input type="hidden" id="accountVersion" value='${regDetails.accountVersion }'/>
	</div>
	<div id="OnfidoProviderResponsepopups" class="popupDiv"
		title="Provider Response">
		<textarea id="onfidoproviderResponseJson" class="popupTextArea">NOT AVAILABLE</textarea>
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
	
	<div id="SocialDataPopup" class="popupDiv" title="Client Social Data">
		<div id="socialDatapopupdiv" class="popupTextAreaHolisticView popup">
			<div class="scrollpane--large-h scrollpane--borderless"></div>
		</div>
	</div>
	
	
	<div id="DataAnonPopup" class="popupDiv" title="Forget Me ">
		<div id="dataAnonPopupdiv">
			<div id="errorDiv" class="message--negative" style="display: none;">
								<div class="copy">
									<p id = "errorDescription"></p>
								</div>
	                      </div>
			<h1>Enter Reason </h1>
			<fieldset>
							<ul class="form__fields">
									<li class="form__field-wrap filterInputWrap">
									<textarea id="DataAnonReason" cols="1" rows="6" maxlength="1024" name="Name"></textarea></li>
								</ul>
	</fieldset>
		</div>
	</div>
	
	     <%-- AT-3391 EU POI UPDATE  --%>
		<div id="ApprovePoiPopup" class="popupDiv">
		<div id="approvePoiPopupdiv">
			<h1>Are you sure?</h1>
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
	 <div id="png-container"></div>
	 <p id="canvasHolder"></p>
	<form id="fraudRingNodeRegDetails" action="/compliance-portal/registrationDetails" method="POST" target="_blank">
			<input type="hidden" id="nodeContactId" value="" name="contactId"/>
			<!-- <input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/> -->
			<input type="hidden" id="nodeCustType" value="" name="custType"/>
	</form>
	
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	
	<script type="text/javascript" src="resources/js/amcharts/cd_amcharts_min.js"></script>
	
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/regDetails.js"></script>
	<script type="text/javascript" src="resources/js/jsontotable.js"></script>
	<script type="text/javascript" src="resources/js/fraudring.js"></script>
	<script type="text/javascript" src="resources/js/neo4j/neo4jd3.js"></script>
	<script type="text/javascript" src="resources/js/neo4j/d3v4.min.js"></script>
	<!-- <script type="text/javascript" src="resources/js/exportFraudring/saveSvgAsPngJpgSvg.js"></script> -->
	<!-- <script type="text/javascript" src="resources/js/exportFraudring/canvas-to-blob.js"></script>
	<script type="text/javascript" src="resources/js/exportFraudring/exportFraudRing.js"></script>
	<script type="text/javascript" src="resources/js/exportFraudring/fileSaver.js"></script>
	<script type="text/javascript" src="resources/js/exportFraudring/saveSVGAsPNG.js"></script>
	<script type="text/javascript" src="resources/js/exportFraudring/print.js"></script> -->
	
</body>
</html>