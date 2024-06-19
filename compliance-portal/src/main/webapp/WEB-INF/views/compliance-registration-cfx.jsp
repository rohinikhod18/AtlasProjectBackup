<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="utf-8" />
<title>Enterprise tools</title>
<meta name="description" content="Enterprise tools" />
<meta name="copyright" content="Currencies Direct" />

<link rel="stylesheet" type="text/css" href="resources/css/cd.css" />
<link rel="stylesheet" type="text/css"
	href="resources/css/pages/cd.page.compliance.css" />
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/popup.css">
<link rel="stylesheet" href="resources/css/jsontotable.css">
<link rel="stylesheet" href="resources/css/neo4j/neo4jd3.css">
</head>

<body>

	<div id="master-grid" class="grid">


		<c:choose>
			<c:when
				test="${(regDetails.locked  && regDetails.lockedBy != regDetails.user.name) || regDetails.locked == null || !regDetails.locked}">
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
										class="breadcrumbs__crumb"><a
											onclick="redirectToQueue();">${redirectTo }</a></span>
									</span>
								</h1>
								<input id="ququefilter" type="hidden" name="searchCriteria" value="">
							</div>
							</form>
							<div class="grid__col--3">

								<div class="toast">

							<input type="hidden" id="cfxContactCount" value='${fn:length(regDetails.contactDetails)}'/>
									<c:if test="${fn:length(regDetails.contactDetails) >0}">
										<span class="message--toast rhs page-load"> <i
											class="material-icons">assignment_ind</i> <a
											href="#section-contacts" class="accordion-trigger">${fn:length(regDetails.contactDetails)}
												contacts at this company</a>
										</span>
									</c:if>

								</div>
							</div>

						</div>

					</div>

					<div id="main-content__body">
						<div class="message--positive" style="display: none">
							<div class="copy"></div>
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
															<c:when
																test="${regDetails.account.complianceStatus == 'INACTIVE' || regDetails.account.complianceStatus == 'REJECTED'}">
																<p id="account_status">
																	<span class="indicator--negative"
																		id="account_compliacneStatus">${regDetails.account.complianceStatus}</span>
																</p>
															</c:when>
															<c:when
																test="${regDetails.account.complianceStatus =='ACTIVE'}">
																<p id="account_status">
																	<span class="indicator--positive"
																		id="account_compliacneStatus">${regDetails.account.complianceStatus}</span>
																</p>
															</c:when>
														</c:choose>
													</div>

													<div class="grid__col--6">

														<div id="lock" class="f-right">
															<c:choose>
																<c:when
																	test="${regDetails.locked  && regDetails.lockedBy == regDetails.user.name}">
																	<span id="ownRecord"
																		class="space-next toggle-support-text"><i
																		class="material-icons">person_pin</i> You own(s) this
																		record</span>
																	<div id="toggle-edit-lock" class="toggle f-right">
																		<a href="#" id="toggle-record-lock"
																			onclick="lockResourceByAccountID()"
																			class="toggle__option--on "
																			data-ot="Lock this record to own it"><i
																			class="material-icons">lock_outline</i></a> <a href="#"
																			id="toggle-record-unlock"
																			onclick="unlockResourceByAccountId()"
																			class="toggle__option unlock-specific"
																			data-ot="Unlock this record for others"><i
																			class="material-icons unlock-specific">lock_open</i></a>
																	</div>
																</c:when>
																<c:when test="${regDetails.locked}">
																	<span id="ownRecord"
																		class="space-next toggle-support-text"><i
																		class="material-icons">person_pin</i>
																		${regDetails.lockedBy} own(s) this record</span>
																</c:when>
																<c:otherwise>
																	<div id="toggle-edit-lock" class="toggle f-right">
																		<a href="#" id="toggle-record-lock"
																			onclick="lockResourceByAccountID()"
																			class="toggle__option "
																			data-ot="Lock this record to own it"><i
																			class="material-icons">lock_outline</i></a> <a href="#"
																			id="toggle-record-unlock"
																			onclick="unlockResourceByAccountId()"
																			class="toggle__option--on unlock-specific"
																			data-ot="Unlock this record for others"><i
																			class="material-icons unlock-specific">lock_open</i></a>
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
																<dd>
																	<span class="wordwrap" id="account-name"><a
																		href="#accordion-section-client-details"
																		class="accordion-trigger"
																		data-accordion-section="accordion-section-client-details">
																			${regDetails.account.name}</a></span>
																</dd>

																<dt>Client type</dt>
																<dd>${regDetails.account.clientType}</dd>

																<dt>Industry</dt>
																<dd>${regDetails.account.company.industry}</dd>

																<dt>Website</dt>
																<c:choose>
																	<c:when test ="${regDetails.account.website == ' ----'}" >
																	<dd>${regDetails.account.website}</dd>
																	</c:when>
																	<c:otherwise>
																	<dd>
																	   <a href="${regDetails.account.website}">${regDetails.account.website}</a>
																    </dd>
																	</c:otherwise>
																</c:choose>
																<%-- <dd>
																	<a href="${regDetails.account.webSite}">${regDetails.account.webSite}</a>
																</dd> --%>

																<dt>E-tailer</dt>
																<c:choose>

																<c:when test="${regDetails.account.company.etailer}">
																	<dd id="e_tailer">Yes</dd>
																</c:when>
																<c:otherwise>
																	<dd id="e_tailer">No</dd>
																</c:otherwise>
															</c:choose>
																
																<dd><input id="account_AccountDetails" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" value="VIEW ORIGINAL"
																	onclick="showAccountHistory('${regDetails.account.id}')"><dd>
										
											<!-- 	AT-2084- code for forget me button On details page by Shraddha p. -->				 	
							        			<input id="forget_me_button" type="button" class="button--secondary button--small button--disabled modal-trigger" data-modal="modal-original-summary"
															 value="FORGET ME" onclick="dataAnonymize()"/>
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">

																<%-- <dt>Source</dt>
																<dd>${regDetails.account.source}</dd> --%>
																<c:forEach var="contactDetail"
																			items="${regDetails.contactDetails}">
																<c:if
																	test="${contactDetail.currentContact.primaryContact}">
																<dt>Date of birth</dt>
																<dd>${contactDetail.currentContact.dob}</dd>
																</c:if>
																</c:forEach>

																<dt>Currency Pair</dt>
																<dd>${regDetails.account.currencyPair}</dd>

																<dt>Annual FX Turnover</dt>
																<dd>${regDetails.account.annualFXRequirement}</dd>

																<dt>Purpose of transaction</dt>
																<dd>${regDetails.account.purposeOfTran}</dd>

																<dt>Option</dt>
																<dd>${regDetails.account.company.option}</dd>
																
																<dt>Phone</dt>
																<dd>${regDetails.account.company.companyPhone}</dd>
																			
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">

																<dt>Organization</dt>
																<dd>${regDetails.account.orgCode}</dd>	

																<dt>T &amp; C signed date</dt>
																<dd>${regDetails.account.company.tAndcSignedDate}</dd>

																<dt>SIC code</dt>
																<dd>${regDetails.account.corperateCompliance.sic}</dd>

																<dt>SIC description</dt>
																<dd>${regDetails.account.corperateCompliance.sicDesc}</dd>
																
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
															<div class="grid__col--12 grid__col--pad-left alertComplianceLog">
																<dl>														 
																<dt>Compliance log</dt><br>
  																<dd id="regDetails_alert_compliance_log" class="wordwrap">${regDetails.alertComplianceLog}</dd>
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
															href="#" data-ot="Expand all checks info"><i
																class="material-icons"
																onclick="onLoadViewMore('SANCTION',${regDetails.account.id},'ACCOUNT');">add</i></a>
														</li>
														<li class="quick-control__control--close-all"><a
															href="#" data-ot="Close all checks info"><i
																class="material-icons">close</i></a></li>
													</ul>
												</div>
												<div class="accordion__section">

													<div id="regDetails_blacklist_indicatior"
														class="accordion__header">
														<c:forEach var="internalRule"
															items="${regDetails.internalRule}">
															<a href="#"><i class="material-icons">add</i>Blacklist
																<c:if test="${internalRule.blacklist.failCount gt 0}">
																	<span id="regDetails_blackPass "
																		class="indicator--negative">${internalRule.blacklist.failCount}</span>
																</c:if> <c:if test="${internalRule.blacklist.passCount gt 0}">
																	<span id="regDetails_blackNeg"
																		class="indicator--positive">${internalRule.blacklist.passCount}</span>
																</c:if> </a>
														</c:forEach>
													</div>

													<div class="accordion__content">
														<table class="fixed" id="blackListAccountId">
															<thead>
																<tr>
																	<th class="center">IP blacklist</th>
																	<th class="center">Name blacklist</th>
																	<th class="center">Website blacklist</th>
																	<th class="center">Overall status</th>
																</tr>
															</thead>
															<tbody id="cfx_regDetails_AccountblackList">
																<c:forEach var="internalRule"
																	items="${regDetails.internalRule}">
																	<c:choose>
																	    <c:when test="${not empty internalRule.blacklist}">
																	        <tr>
																		<c:if test="${internalRule.blacklist.ip eq 'Not Required'}">
																			<td class = "nowrap center">${internalRule.blacklist.ip}</td>
																		</c:if>
																		<c:if test="${internalRule.blacklist.ip eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${internalRule.blacklist.ip eq 'true'}">
																			<td class="no-cell"><i class="material-icons">clear</i>
																			<br>${internalRule.blacklist.ipMatchedData}</td>
																		</c:if>
																		<c:if
																			test="${internalRule.blacklist.name eq 'Not Required'}">
																			<td class="nowrap center">${internalRule.blacklist.name eq 'Not Required'}</td>
																		</c:if>
																		<c:if test="${internalRule.blacklist.name eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if test="${internalRule.blacklist.name eq 'true'}">
																			<td class="no-cell"><i class="material-icons">clear</i>
																			<br>${internalRule.blacklist.nameMatchedData}</td>
																		</c:if>
																		<%-- <c:choose>
																		<c:when test="${internalRule.blacklist.webSite}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:when>
																		<c:otherwise>
																			<td class="no-cell"><i class="material-icons">clear</i></td>
																		</c:otherwise>
																	</c:choose> --%>
																		<c:if
																			test="${internalRule.blacklist.webSite eq 'Not Required'}">
																			<td class="nowrap center">${internalRule.blacklist.webSite}</td>
																		</c:if>
																		<c:if
																			test="${internalRule.blacklist.webSite eq 'false'}">
																			<td class="yes-cell"><i class="material-icons">check</i></td>
																		</c:if>
																		<c:if
																			test="${internalRule.blacklist.webSite eq 'true'}">
																			<td class="no-cell"><i class="material-icons">clear</i>
																			<br>${internalRule.blacklist.websiteMatchedData}</td>
																		</c:if>
																		<c:choose>
																			<c:when test="${internalRule.blacklist.status  eq true}">
																				<td class="yes-cell"><i class="material-icons">check</i></td>
																			</c:when>
																			<c:when test="${internalRule.blacklist.status  eq false}">
																				<td class="no-cell"><i class="material-icons">clear</i></td>
																			</c:when>
																		</c:choose>
																	</tr>
																	    </c:when>
																	   
																	</c:choose>
																	
																</c:forEach>
															</tbody>
														</table>
														
														<p></p>
													<form id = "blacklist_ReCheckId--${regDetails.account.id}">
														<p class="flush-margin eid-field">
															<input id="regDetails_blacklist_recheck--${regDetails.account.id}" type="button"
																class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																onclick="resendCFXAccountBlacklist('${regDetails.account.id}');" <c:out value="${buttonDisable}" />>
															<object id="gifloaderforBlacklistresendForCFXAccount" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
															</object> 
															<span id="blacklist_error_field--${regDetails.account.id}" class="form__field-error" hidden="hidden">
																Some kind of error message. <a href="#" class="">Back to summary</a>
															</span>
														</p>
													</form>
													</div>

												</div>
												<div class="accordion__section">

													<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Corporate
															compliance</a>
													</div>

													<div class="accordion__content">

														<div class="boxpanel--shadow space-after">

															<div class="grid">

																<div class="grid__row">

																	<div class="grid__col--4">
																		<dl class="split-list">

																			<dt>SIC code</dt>
																			<dd>${regDetails.account.corperateCompliance.sic}</dd>

																			<dt>SIC description</dt>
																			<dd>${regDetails.account.corperateCompliance.sicDesc}</dd>

																			<dt>Registration number</dt>
																			<dd>${regDetails.account.corperateCompliance.registrationNumber}</dd>

																			<dt>Former name</dt>
																			<dd>${regDetails.account.corperateCompliance.formerName}</dd>

																			<dt>Legal form</dt>
																			<dd>${regDetails.account.corperateCompliance.legalForm}</dd>

																			<!-- newly added fields for DNB data  -->

																			<dt>Global ultimate duns</dt>
																			<dd>${regDetails.account.corperateCompliance.globalUltimateDuns}</dd>

																			<dt>Global ultimate name</dt>
																			<dd>${regDetails.account.corperateCompliance.globalUltimateName}</dd>


																			<dt>Global ultimate country</dt>
																			<dd>${regDetails.account.corperateCompliance.globalUltimateCountry}</dd>

																			<dt>Registration date</dt>
																			<dd>${regDetails.account.corperateCompliance.registrationDate}</dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<dt>Match name</dt>
																			<dd>${regDetails.account.corperateCompliance.matchName}</dd>

																			<dt>Iso country code two digit</dt>
																			<dd>${regDetails.account.corperateCompliance.isoCountryCode2Digit}</dd>

																			<dt>Iso Country Code Three Digit</dt>
																			<dd>${regDetails.account.corperateCompliance.isoCountryCode3Digit}</dd>

																			<dt>Statement date</dt>
																			<dd>${regDetails.account.corperateCompliance.statementDate}</dd>


																			<dt>Foreign owned company</dt>
																			<dd>${regDetails.account.corperateCompliance.foreignOwnedCompany}</dd>

																			<dt>Net worth</dt>
																			<dd>${regDetails.account.corperateCompliance.netWorth}</dd>

																			<dt>Fixed assets</dt>
																			<dd>${regDetails.account.corperateCompliance.fixedAssets}</dd>

																			<dt>Total liabilities and equities</dt>
																			<dd>${regDetails.account.corperateCompliance.totalLiabilitiesAndEquities}</dd>

																			<dt>Total share holders</dt>
																			<dd>${regDetails.account.corperateCompliance.totalShareHolders}</dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<!-- newly added DNB Data  -->

																			<dt>Gross income</dt>
																			<dd>${regDetails.account.corperateCompliance.grossIncome}</dd>

																			<dt>Net income</dt>
																			<dd>${regDetails.account.corperateCompliance.netIncome}</dd>

																			<dt>Total current assets</dt>
																			<dd>${regDetails.account.corperateCompliance.totalCurrentAssets}</dd>

																			<dt>Total assets</dt>
																			<dd>${regDetails.account.corperateCompliance.totalAssets}</dd>

																			<dt>Total longterm Liabilities</dt>
																			<dd>${regDetails.account.corperateCompliance.totalLongTermLiabilities}</dd>

																			<dt>Total current liabilities</dt>
																			<dd>${regDetails.account.corperateCompliance.totalCurrentLiabilities}</dd>

																			<dt>Total matched shareholders</dt>
																			<dd>${regDetails.account.corperateCompliance.totalMatchedShareholders}</dd>

																			<dt>Financial strength</dt>
																			<dd>${regDetails.account.corperateCompliance.financialStrength}</dd>


																		</dl>
																	</div>

																</div>

															</div>

														</div>

														<!-- <form>
			<input type="button" class="button--primary" value="Get new data"/>
		</form> -->

													</div>

												</div>

												<!-- ACCOUNT SANCTION BEGIN -->
												<div class="accordion__section">
													<div
														id="regDetails_sanction_indicatior--${regDetails.account.id}"
														class="accordion__header"
														onclick="onLoadViewMore('SANCTION',${regDetails.account.id},'ACCOUNT');">
														<a href="#"><i class="material-icons">add</i>Sanctions
															<c:if test="${regDetails.sanctionDetails.failCount gt 0}">
																<span
																	id="regDetails_sanctionPass--${regDetails.account.id}"
																	class="indicator--negative">${regDetails.sanctionDetails.failCount}</span>
															</c:if> <c:if
																test="${regDetails.sanctionDetails.passCount gt 0}">
																<span
																	id="regDetails_sanctionNeg--${regDetails.account.id}"
																	class="indicator--positive">${regDetails.sanctionDetails.passCount}</span>
															</c:if> </a>
													</div>

													<div class="accordion__content">

														<form>

															<table>
																<thead>
																	<tr>
																		<th class="sorted desc"><a href="#">Updated
																				on <i></i>
																		</a></th>
																		<th><a href="#">Updated by</a></th>
																		<th>Sanction ID</th>
																		<th><a href="#">OFAC List</a></th>
																		<th><a href="#">World check</a></th>
																		<th><a href="#">Status</a></th>
																	</tr>
																</thead>
																<tbody
																	id="regDetails_sanction--${regDetails.account.id}" class="checkSanctions">
																	<c:forEach var="sanction"
																		items="${regDetails.sanctionDetails.sanction}">
																		<tr>
																			<td hidden="hidden" class="center">${sanction.eventServiceLogId}</td>
																			<!-- class "nowrap" is removed to set columns on UI properly -->
																			<td>${sanction.updatedOn}</td>
																			<td class="nowrap">${sanction.updatedBy}</td>
																			<!-- class "nowrap" is removed to set columns on UI properly -->
																			<td><a href="javascript:void(0);"
																				onclick="showCFXProviderResponse('${sanction.eventServiceLogId}','SANCTION')">${sanction.sanctionId}</a></td>
																			<td class="nowrap">${sanction.ofacList}</td>
																			<td class="nowrap">${sanction.worldCheck}</td>

																			<c:if
																				test="${sanction.statusValue eq 'Not Required'}">
																				<td id="sanction_status" class="nowrap center">${sanction.statusValue}</td>
																			</c:if>
																			<c:if
																				test="${sanction.statusValue ne 'Not Required'}">
																				<c:choose>
																					<c:when test="${sanction.status}">
																						<td id="sanction_status" class="yes-cell"><i
																							class="material-icons">check</i></td>
																					</c:when>
																					<c:otherwise>
																						<td id="sanction_status" class="no-cell"><i class="material-icons">clear</i></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																		</tr>
																		<%-- <input type="hidden"
																			id="sanctionTotalRecordsId--${regDetails.account.id}"
																			value='${regDetails.sanctionDetails.sanctionTotalRecords}' /> --%>
																	</c:forEach>
																</tbody>
															</table>
															<input type="hidden"
															id="sanctionTotalRecordsId--${regDetails.account.id}"
														    value='${regDetails.sanctionDetails.sanctionTotalRecords}' />
															<a href="javascript:void(0);"
																id="viewMoreDetails_sanc--${regDetails.account.id}"
																class="load-more space-after"
																onclick="viewMoreCFXDetails('SANCTION' ,'regDetails_sanction--${regDetails.account.id}','sanctionTotalRecordsId--${regDetails.account.id}','leftRecordsIdSanc--${regDetails.account.id}',${regDetails.account.id},'ACCOUNT');">
																VIEW <span class="load-more__extra"
																id="viewMore_SancId--${regDetails.account.id}"> </span>
																MORE <span class="load-more__left"
																id="leftRecordsIdSanc--${regDetails.account.id}">
															</span>
															</a>

															<div class="boxpanel--shadow space-after sanction-field">
																<fieldset>
																	<legend>Update selected sanctions</legend>
																	<div class="grid space-after">
																		<div class="grid__row">
																			<div class="grid__col--6">
																				<div class="form__field-wrap flush-margin">

																					<p class="label">Which field?</p>

																					<div id="singlelist-sanction-field-company"
																						class="singlelist clickpanel--right">

																						<p class="singlelist__chosen clickpanel__trigger">Please
																							select</p>

																						<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																						<div class="clickpanel__content--hidden">


																							<fieldset>
																								<ul class="singlelist__options">
																									<li><label
																										for="rad-sanction-field-1--${regDetails.account.id}">
																											<input
																											id="rad-sanction-field-1--${regDetails.account.id}"
																											type="radio"
																											name="regDetails_updateField--${regDetails.account.id}"
																											value="ofaclist" />OFAC List
																									</label></li>
																									<li><label
																										for="rad-sanction-field-2--${regDetails.account.id}">
																											<input
																											id="rad-sanction-field-2--${regDetails.account.id}"
																											type="radio"
																											name="regDetails_updateField--${regDetails.account.id}"
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

																				<div class="form__field-wrap flush-margin">

																					<p class="label">Set this field to...</p>

																					<div id="singlelist-sanction-field-val-company"
																						class="singlelist clickpanel--right">

																						<p class="singlelist__chosen clickpanel__trigger">Please
																							select</p>

																						<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																						<div class="clickpanel__content--hidden">

																							<fieldset>

																								<ul class="singlelist__options">

																									<li><label
																										for="rad-sanction-fieldval-1--${regDetails.account.id}">
																											<input
																											id="rad-sanction-fieldval-1--${regDetails.account.id}"
																											type="radio"
																											name="regDetails_updateField_Value--${regDetails.account.id}"
																											value="Confirmed hit" /> Confirmed hit
																									</label></li>
																									<li><label
																										for="rad-sanction-fieldval-2--${regDetails.account.id}">
																											<input
																											id="rad-sanction-fieldval-2--${regDetails.account.id}"
																											type="radio"
																											name="regDetails_updateField_Value--${regDetails.account.id}"
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
																	 <span
																			id="update_sanction_account_error_field-${regDetails.account.id}"
																			class="form__field-error" hidden="hidden">
																			Some kind of error message. <a href="#" class="">Back
																				to summary</a>
																		</span>
																		<input
																			id="regDetails_updateSanction--${regDetails.account.id}"
																			type="button" class="<c:out value="${buttonClass}"/>"
																			value="Apply"
																			onclick="updateCFXAccountSanction('${regDetails.account.id}')"
																			<c:out value="${buttonDisable}"/> />
																			 <object id="gifloaderforupdatesanctionaccountcfx" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																	</p>


																</fieldset>

															</div>

														</form>

														<form>
															<input
																id="regDetails_sanction_recheck--${regDetails.account.id}"
																type="button"
																class="<c:out value="${buttonClass} sanction-field" />"
																value="Repeat checks"
																onclick="resendCFXAccountSanction('${regDetails.account.id}');"
																<c:out value="${buttonDisable}"/> />
																 <object id="gifloaderforresendsanctionCFXAccount" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																 <span
																id="resend_sanction_account_error_field-${regDetails.account.id}"
																class="form__field-error" hidden="hidden"> Some
																kind of error message. <a href="#" class="">Back to
																	summary</a>
															</span>
														</form>

													</div>

												</div>
												<!-- ACCOUNT SANCTION ENDED -->
												<%-- Start TM Tab - AT-4114 --%>
												<div class="accordion__section">
													<div
														id="regDetails_intuition_indicatior--${regDetails.account.id}"
														class="accordion__header"
														onclick="onLoadViewMore('TRANSACTION_MONITORING',${regDetails.account.id},'ACCOUNT');">
														<a href="#"><i class="material-icons">add</i>Intuition
															<c:if test="${regDetails.intuitionDetails.failCount gt 0}">
																<span
																	id="regDetails_intuitionPass--${regDetails.account.id}"
																	class="indicator--negative">${regDetails.intuitionDetails.failCount}</span>
															</c:if> <c:if
																test="${regDetails.intuitionDetails.passCount gt 0}">
																<span
																	id="regDetails_intuitionNeg--${regDetails.account.id}"
																	class="indicator--positive">${regDetails.intuitionDetails.passCount}</span>
															</c:if> </a>
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
																<tbody
																	id="regDetails_intuition--${regDetails.account.id}" class="checkIntuition">
																	<c:forEach var="intuition"
																		items="${regDetails.intuitionDetails.intuition}">
																		<tr>
																			<td hidden="hidden" class="center">${intuition.id}</td>
																			<td>${intuition.createdOn}</td>
																			<td class="wrapword">${intuition.updatedBy}</td>
																			<td class="wrapword"><a href="javascript:void(0);"
																				onclick="showCFXProviderResponse('${intuition.id}','TRANSACTION_MONITORING')">${intuition.correlationId}</a></td>
																			<td class="nowrap">${intuition.riskLevel}</td>
																			<td class="nowrap">${intuition.profileScore}</td>
																			<td class="nowrap">${intuition.ruleScore}</td>

																			<c:if
																				test="${intuition.statusValue eq 'NOT_PERFORMED'}">
																				<td id="intuition_status" class="nowrap center">${intuition.statusValue}</td>
																			</c:if>
																			<c:if
																				test="${intuition.statusValue eq 'NOT_REQUIRED'}">
																				<td id="intuition_status" class="nowrap center">${intuition.statusValue}</td>
																			</c:if>
																			<c:if
																				test="${intuition.statusValue ne 'NOT_PERFORMED' && intuition.statusValue ne 'NOT_REQUIRED'}">
																				<c:choose>
																					<c:when test="${intuition.status}">
																						<td id="intuition_status" class="yes-cell"><i
																							class="material-icons">check</i></td>
																					</c:when>
																					<c:otherwise>
																						<td id="intuition_status" class="no-cell"><i class="material-icons">clear</i></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																		</tr>
																		
																	</c:forEach>
																</tbody>
															</table>
															<input type="hidden"
															id="intuitionTotalRecordsId--${regDetails.account.id}"
														    value='${regDetails.intuitionDetails.intuitionTotalRecords}' />
															<a href="javascript:void(0);"
																id="viewMoreDetails_intuition--${regDetails.account.id}"
																class="load-more space-after"
																onclick="viewMoreCFXDetails('TRANSACTION_MONITORING' ,'regDetails_intuition--${regDetails.account.id}','intuitionTotalRecordsId--${regDetails.account.id}','leftRecordsIdIntuition--${regDetails.account.id}',${regDetails.account.id},'ACCOUNT');">
																VIEW <span class="load-more__extra"
																id="viewMore_intuitionId--${regDetails.account.id}"> </span>
																MORE <span class="load-more__left"
																id="leftRecordsIdIntuition--${regDetails.account.id}">
															</span>
															</a>

													</div>

												</div>
												<%-- End TM tab - AT-4114 --%>
												
												<!-- RISK PROFILE BEGIN -->
												<div class="accordion__section">

													<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Risk
															profile</a>
													</div>

													<div class="accordion__content">

														<div class="boxpanel--shadow space-after">

															<div class="grid">

																<div class="grid__row">

																	<div class="grid__col--4">
																		<dl class="split-list">

																			<dt>Country risk indicator</dt>
																			<dd>${regDetails.account.riskProfile.countryRiskIndicator}</dd>

																			<dt>Risk trend</dt>
																			<dd>${regDetails.account.riskProfile.riskTrend}</dd>

																			<dt>Risk direction</dt>
																			<dd>${regDetails.account.riskProfile.riskDirection}</dd>
																			<!-- **** newly added fields added by neelesh pant *******/ -->
																			<dt>Continent</dt>
																			<dd>${regDetails.account.riskProfile.continent}</dd>


																			<dt>Country</dt>
																			<dd>${regDetails.account.riskProfile.country}</dd>


																			<dt>State country</dt>
																			<dd>${regDetails.account.riskProfile.stateCountry}</dd>

																			<dt>Duns number</dt>
																			<dd>${regDetails.account.riskProfile.dunsNumber}</dd>


																			<dt>Trading styles</dt>
																			<dd>${regDetails.account.riskProfile.tradingStyles}</dd>


																			<dt>us1987PrimarySic4Digit</dt>
																			<dd>${regDetails.account.riskProfile.us1987PrimarySic4Digit}</dd>


																			<dt>Financial figures month</dt>
																			<dd>${regDetails.account.riskProfile.financialFiguresMonth}</dd>


																			<dt>Financial figures year</dt>
																			<dd>${regDetails.account.riskProfile.financialFiguresYear}</dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">


																			<dt>Financial year endDate</dt>
																			<dd>${regDetails.account.riskProfile.financialYearEndDate}</dd>


																			<dt>Annual sales</dt>
																			<dd>${regDetails.account.riskProfile.annualSales}</dd>


																			<dt>Modelled annual sales</dt>
																			<dd>${regDetails.account.riskProfile.modelledAnnualSales}</dd>

																			<dt>Net Worth amount</dt>
																			<dd>${regDetails.account.riskProfile.netWorthAmount}</dd>

																			<dt>Credit limit</dt>
																			<dd>${regDetails.account.riskProfile.creditLimit}</dd>


																			<dt>Updated risk</dt>
																			<dd>${regDetails.account.riskProfile.updatedRisk}</dd>

																			<dt>Failure score</dt>
																			<dd>${regDetails.account.riskProfile.failureScore}</dd>

																			<dt>Delinquency failure score</dt>
																			<dd>${regDetails.account.riskProfile.delinquencyFailureScore}</dd>
																			<!-- **** newly added fields added by neelesh pant *******/ -->
																			<dt>Modelled networth</dt>
																			<dd>${regDetails.account.riskProfile.modelledNetWorth}</dd>

																			<dt>Location type</dt>
																			<dd>${regDetails.account.riskProfile.locationType}</dd>

																			<dt>Import export indicator</dt>
																			<dd>${regDetails.account.riskProfile.importExportIndicator}</dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<dt>Domestic ultimate record</dt>
																			<dd>${regDetails.account.riskProfile.domesticUltimateRecord}</dd>

																			<dt>Global ultimate record</dt>
																			<dd>${regDetails.account.riskProfile.globalUltimateRecord}</dd>

																			<dt>Group structure number of levels</dt>
																			<dd>${regDetails.account.riskProfile.groupStructureNumberOfLevels}</dd>

																			<dt>Headquarter details</dt>
																			<dd>${regDetails.account.riskProfile.headquarterDetails}</dd>

																			<dt>Immediateparent details</dt>
																			<dd>${regDetails.account.riskProfile.immediateParentDetails}</dd>

																			<dt>Domestic ultimate parent details</dt>
																			<dd>${regDetails.account.riskProfile.domesticUltimateParentDetails}</dd>

																			<dt>Global ultimate parent details</dt>
																			<dd>${regDetails.account.riskProfile.globalUltimateParentDetails}</dd>

																			<dt>Risk rating</dt>
																			<dd>${regDetails.account.riskProfile.riskRating}</dd>

																			<dt>Profit loss</dt>
																			<dd>${regDetails.account.riskProfile.profitLoss}</dd>

																		</dl>
																	</div>
																</div>

															</div>

														</div>

														<!-- <form>
			<input type="button" class="button--primary" value="Get new data"/>
		</form>
 -->
													</div>

												</div>
												<!-- RISK PROFILE ENDED -->

												<!-- ACCOUNT ATTACH DOCUMENT BEGIN -->
												<div class="accordion__section">

													<div id="doc_indicatior" class="accordion__header">
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

																<c:forEach var="document"
																	items="${regDetails.documents}">
																	<tr class="talign">
																		<td>${document.createdOn}</td>
																		<td>${document.createdBy}</td>
																		<td class="breakword"><a href="${document.url}">${document.documentName}</a>
																		</td>
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
															<form>
																<fieldset>
																	<legend>Attach another document</legend>

																	<div class="grid space-after">

																		<div class="grid__row">

																			<div class="grid__col--6">

																				<div class="form__field-wrap">

																					<p class="label">Document type</p>

																					<div id="singlelist-document-type-company"
																						class="singlelist clickpanel--right">

																						<p id="docTypeSelectionAccountId" class="singlelist__chosen clickpanel__trigger2">Please
																							select</p>

																						<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																						<div class="clickpanel__content--hidden">

																							<fieldset>

																								<input class="singlelist__search space-after"
																									type="search" placeholder="Search list" />

																								<ul class="singlelist__options multilist__options">
																									<c:forEach var="documentList"
																										items="${regDetails.documentList}"
																										varStatus="loop">

																										<li><label
																											for="rad-doctype-${loop.index}"> <input
																												name="documentType"
																												id="rad-doctype-${loop.index}" type="radio"
																												value="${documentList.documentName}" />${documentList.documentName}
																										</label></li>

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
																	<input type="hidden" id="contact_crmAccountId"
																		value='${regDetails.currentContact.crmAccountId}' />
																	<input type="hidden" id="contact_crmContactId"
																		value='${regDetails.currentContact.crmContactId}' />

																	<p class="right">
																	 <input id="isDocumentAuthorized" type="checkbox" value="" 
																	 		name="isDocumentAuthorized">
																 	 <span style="margin-right:10px">Approve document </span>
																		<!-- Id given to upload documents button to make it enable and disable 
								Changes done by Vishal J -->
																		<input id="attach_document_button_id_account"
																			type="button" class="<c:out value="${buttonClass}"/>"
																			value="Attach"
																			onclick="uploadDocument('${regDetails.currentContact.crmAccountId}','${regDetails.currentContact.crmContactId}','text-note','file-choose-document','attachDoc','documentType','attach_document_button_id_account','${regDetails.account.orgCode}','gifloaderfordocumentloaderAccount');"
																			<c:out value="${buttonDisable}"/> />
								<object id="gifloaderfordocumentloaderAccount" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																</fieldset>
															</form>

														</div>

													</div>

												</div>
												<!-- ACCOUNT ATTACH DOCUMENT ENDED -->
											</div>
										</section>

										<section id="other-info" class="main-content__section">

											<h2>Other information</h2>

											<div class="accordion--quick-controls">

												<div class="quick-controls">
													<ul class="containing horizontal">
														<li class="quick-control__control--open-all"><a
															href="#" data-ot="Expand all checks info"><i
																onclick="viewMoreCFXLoadDataActLog();"
																class="material-icons">add</i></a></li>
														<li class="quick-control__control--close-all"><a
															href="#" data-ot="Close all checks info"><i
																class="material-icons">close</i></a></li>
													</ul>
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

																			<dt>Billing address</dt>
																			<dd class="wordwrap">${regDetails.account.company.billingAddress}</dd>

																			<dt>Registration in</dt>
																			<dd>${regDetails.account.registrationInDate}</dd>

																			<dt>Registered</dt>
																			<dd>${regDetails.account.regCompleteAccount}</dd>
																			
																			<dt>Country of establishment</dt>
																			<dd>${regDetails.account.company.countryOfEstablishment}</dd>

																			<dt>Corporate type</dt>
																			<dd>${regDetails.account.company.corporateType}</dd>

																			<dt>Incorporation date</dt>
																			<dd>${regDetails.account.company.incorporationDate}</dd>

																			<dt>Type of financial accounts</dt>
																			<dd>${regDetails.account.company.typeOfFinancialAccount}</dd>

																			<dt>Registration number</dt>
																			<dd>${regDetails.account.company.registrationNo}</dd>

																			<dt>VAT number</dt>
																			<dd>${regDetails.account.company.vatNo}</dd>
																			
																			<dt>WhiteList Data</dt>
																	
																			<input id="account_AccountWhiteList" type="button" class="button--secondary button--small modal-trigger margin--left" data-modal="modal-original-summary" 
																			value="WhiteList Data" onclick="showAccountWhiteListData('${regDetails.account.id}' , '${regDetails.account.orgCode}')">
																			
																		</dl>
																	</div>

																	<div class="grid__col--6 grid__col--pad-left">
																		<dl class="split-list">

																			<dt>CCJ</dt>
																			<dd>${regDetails.account.company.ccj}</dd>

																			<dt>Source lookup</dt>
																			<dd>${regDetails.account.sourceLookup}</dd>

																			<dt>Source</dt>
																			<dd>${regDetails.account.source}</dd>

																			<dt>Ongoing due diligence date</dt>
																			<dd>${regDetails.account.company.ongoingDueDiligenceDate}</dd>

																			<dt>Annual FX requirement</dt>
																			<dd>${regDetails.account.annualFXRequirement}</dd>

																			<dt>Est no. transactions pcm</dt>
																			<dd>${regDetails.account.company.estNoTransactionsPcm}</dd>

																			<dt>Average transaction value</dt>
																			<dd>${regDetails.account.avgTransactionValue}</dd>

																			<dt>Countries of operation</dt>
																			<dd>${regDetails.account.countriesOfOperation}</dd>
																			
																			<dt>Device info</dt>
																	
																			<input id="account_FurtherClient_deviceInfo" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary" 
																			value="View Device Info" onclick="showDeviceInfo('${regDetails.account.id}')">
																		
																			
																		</dl>
																	</div>

																</div>

															</div>

														</div>

													</div>

												</div>

												<!-- ACTIVITY LOG DIV BEGIN -->
												<div class="accordion__section">
													<div id="regDetails_activitylog_indicatior"
														class="accordion__header"
														onclick="getOnLoadActivityLogViewMore();">
														<a href="#"><i class="material-icons">add</i>Activity
															log</a>
													</div>
													<div class="accordion__content">

													<div class="audit-trail" align="center">
														<section style="width:250px;">
															<p class="label filterInputHeader">Filter</p>
															<select id="SelectedAuditTrailFilter"
																style="border-color: #ccc;"
																onchange="getActivityLogsByModule('1','10', '${regDetails.account.id}', 
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

														<a href="javascript:void(0);" id="viewMoreDetails_ActLog"
															class="load-more space-after"
															onclick="viewMoreCFXDetails('ACTIVITYLOG' ,'activityLog','actLogTotalRecordsId','leftRecordsIdActLog',${regDetails.account.id},'ACCOUNT');">
															VIEW <span class="load-more__extra"
															id="viewMore_ActLogId"> </span> MORE <span
															class="load-more__left" id="leftRecordsIdActLog">
														</span>
														</a>
														<a href="javascript:void(0);" id="viewMoreAuditTrailDetails_ActLog"  class="load-more space-after"  
										  onclick="getViewMoreActivityLogsByModule('${regDetails.account.id}', true, 'leftRecordsIdActLog_AuditTrail', 'viewMore_AuditTrail_ActLogId','viewMoreAuditTrailDetails_ActLog','viewMoreDetails_ActLog');">
											VIEW <span class="load-more__extra" id = "viewMore_AuditTrail_ActLogId" > </span> MORE
											<span class="load-more__left" id= "leftRecordsIdActLog_AuditTrail" > </span>
										  </a>
													</div>

												</div>
												<!-- ACTIVITY LOG DIV ENDED -->
											</div>
										</section>
										
										<!-- CONTACTS SECTION BEGIN -->
										<section id="section-contacts">

											<h2>Contacts</h2>
											<div class="accordion--quick-controls">
												<div class="quick-controls">
													<ul class="containing horizontal">
														<li class="quick-control__control--open-all"><a
															href="#" data-ot="Expand all checks info"><i
																class="material-icons">add</i></a></li>
														<li class="quick-control__control--close-all"><a
															href="#" data-ot="Close all checks info"><i
																class="material-icons">close</i></a></li>
													</ul>
												</div>

												<!-- Contact Details Begin  -->

												<c:forEach var="contactDetail"
													items="${regDetails.contactDetails}">

													<div class="accordion__section">

														<div id="cfx_contacts_list-${contactDetail.currentContact.crmContactId}" class="accordion__header" onclick="isFraudRingPresent('${contactDetail.currentContact.crmContactId}')">
															<a href="#"><i class="material-icons">add</i><span
																id="contact-name-${contactDetail.currentContact.id}">${contactDetail.currentContact.name}</span>
																<span class="reset-font"> -
																	${contactDetail.currentContact.occupation}</span> <c:if
																	test="${contactDetail.currentContact.primaryContact}">
																	<span class="indicator">Primary</span>
																</c:if>
																<c:if
																	test="${contactDetail.currentContact.authorisedSignatory || contactDetail.currentContact.authorisedSignatory == 'Yes'}">
																	<span class="indicator">Authorised Signatory</span>
																</c:if>
																 </a>
														</div>

														<div class="accordion__content">

															<div class="boxpanel--shadow space-after">

																<div class="grid">

																	<div class="grid__row">

																		<div class="grid__col--6">

																			<dl class="split-list">

																				<dt>Job title</dt>
																				<dd>${contactDetail.currentContact.occupation}</dd>

																				<dt>Position of significance</dt>
																				<dd>${contactDetail.currentContact.positionOfSignificance}</dd>

																				<dt>Authorised signatory</dt>
																				<dd>${contactDetail.currentContact.authorisedSignatory}</dd>

																				<dt>Country of nationality</dt>
																				<dd>${contactDetail.currentContact.nationality}</dd>

																				<dt>Country of residence</dt>
																				<dd>${contactDetail.currentContact.countryOfResidence}</dd>
																				
																				<dt>Date of birth</dt>
																				<dd>${contactDetail.currentContact.dob}</dd>							
															
																			</dl>
																				
																			<!-- Changes for Fraud Ring Graph starts here -->
																			<div class="grid__col--12 grid__col--pad-left">
																				<dl>
																					<input type="button" class="button--secondary button--small" id="fraud_ring_button-${contactDetail.currentContact.crmContactId}" value="View FraudRing" onclick="showFraudRingGraph('${contactDetail.currentContact.crmContactId}')">
																					<object id="gifloaderforfraudring${contactDetail.currentContact.crmContactId}"  class="ajax-loader-lock-toggle" height="50" width="50" 
																				data="resources/img/ajax-loader.svg" 
																				preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;"></object>
																				</dl>
																			</div>
																			<!-- Changes for Fraud Ring Graph ends here -->
																		</div>

																		<div class="grid__col--6 grid__col--pad-left">

																			<dl class="split-list">

																				<dt>Phone</dt>
																				<dd>${contactDetail.currentContact.phone}</dd>

																				<dt>Mobile</dt>
																				<dd>${contactDetail.currentContact.mobile}</dd>

																				<dt>Email</dt>
																				<dd>${contactDetail.currentContact.email}</dd>

																				<dt>Mailing address</dt>
																				<dd class="wordwrap">${contactDetail.currentContact.address}</dd>

																				<dt>Address type</dt>
																				<dd>${contactDetail.currentContact.addressType}</dd>
																				
																				<dt>Work phone</dt>
																		        <dd>${contactDetail.currentContact.phoneWork}</dd>
																				
																			</dl>
																			
																			<div class="grid__col--12 grid__col--pad-right">
																				<dl>
																					<input id="account_SocialData" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
															 						value="VIEW SOCIAL DATA" onclick="getSocialData('${contactDetail.currentContact.email}')">
																				</dl>
																			</div>
																		</div>		
																    </div>
																</div>
											<%-- 	<!-- 	AT-2084- code for forget me button On details page by Shraddha p. -->				 	
							        											 	
                                                   <c:choose>  
												       <c:when test="${regDetails.currentContact.complianceStatus == 'ACTIVE'}">
															 <input id="forget_me_button" type="button" disable="disable" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
															 value="FORGET ME"/>
															 </c:when>
															 	<c:otherwise>
															 	<dd><input id="forget_me_button" type="button" class="button--secondary button--small modal-trigger" data-modal="modal-original-summary"
															 value="FORGET ME" onclick="dataAnonymize()"><dd>
															 </c:otherwise>
												</c:choose> --%>

															</div>
															<section class="main-content__section flush-margin">

																<h3>
																	Checks on <span>${contactDetail.currentContact.name}</span>
																</h3>

																<div class="accordion">

																	<div class="accordion__section">
																		<!-- Condition added to resolve AT-259 by Vishal J -->

																		<div
																			id="regDetails_blacklist_indicatior-${contactDetail.currentContact.id}"
																			class="accordion__header">
																			<c:forEach var="blackListContact"
																				items="${contactDetail.internalRule}">
																				<a href="#"><i class="material-icons">add</i>Blacklist
																					<c:if
																						test="${blackListContact.blacklist.isRequired}">
																						<c:if
																							test="${blackListContact.blacklist.failCount gt 0}">
																							<span
																								id="regDetails_blackPass-${contactDetail.currentContact.id}"
																								class="indicator--negative">${blackListContact.blacklist.failCount}</span>
																						</c:if>
																						<c:if
																							test="${blackListContact.blacklist.passCount gt 0}">
																							<span
																								id="regDetails_blackNeg-${contactDetail.currentContact.id}"
																								class="indicator--positive">${blackListContact.blacklist.passCount}</span>
																						</c:if>
																					</c:if> </a>
																			</c:forEach>
																		</div>


																		<div class="accordion__content">

																			<table class="fixed">
																				<thead>
																					<tr>
																						<th class="center">IP blacklist</th>
																						<th class="center">Email blacklist</th>
																						<!-- Field added (Name Blacklist) to resolve AT-322 by Vishal J -->
																						<th class="center">Name blacklist</th>
																						<th class="center">Phone blacklist</th>
																						<th class="center">Overall status</th>
																					</tr>
																				</thead>
																				<tbody id="cfx_regDetails_ContactBlackList-${contactDetail.currentContact.id}">
																					<c:forEach var="blackListContact"
																						items="${contactDetail.internalRule}">
																						<c:choose>
																							<c:when
																								test="${blackListContact.blacklist.isRequired}">
																								<tr>
																									<c:if test="${blackListContact.blacklist.ip eq 'Not Required'}">
																										<td class = "nowrap center">${blackListContact.blacklist.ip}</td>
																									</c:if>
																									<c:if test="${blackListContact.blacklist.ip eq 'false'}">
																										<td class="yes-cell"><i class="material-icons">check</i></td>
																									</c:if>
																									<c:if test="${blackListContact.blacklist.ip eq 'true'}">
																										<td class="no-cell wordwrap"><i class="material-icons">clear</i>
																										<br>${blackListContact.blacklist.ipMatchedData}</td>
																									</c:if>
																									<c:if
																										test="${blackListContact.blacklist.email eq 'Not Required'}">
																										<td class="nowrap center">${blackListContact.blacklist.email}</td>
																									</c:if>
																									<c:if
																										test="${blackListContact.blacklist.email eq 'false'}">
																										<td class="yes-cell"><i
																											class="material-icons">check</i></td>
																									</c:if>
																									<c:if
																										test="${blackListContact.blacklist.email eq 'true'}">
																										<td class="no-cell wordwrap"><i
																											class="material-icons">clear</i>
																											<br>${blackListContact.blacklist.emailMatchedData}</td>
																									</c:if>
																									<!-- Field added to resolve AT-322 by Vishal J -->
																									<%-- <c:choose>
																									<c:when
																										test="${blackListContact.blacklist.name}">
																										<td class="yes-cell"><i
																											class="material-icons">check</i></td>
																									</c:when>
																									<c:otherwise>
																										<td class="no-cell"><i
																											class="material-icons">clear</i></td>
																									</c:otherwise>
																								</c:choose> --%>
																									<c:if
																										test="${blackListContact.blacklist.name eq 'Not Required'}">
																										<td class="nowrap center">${blackListContact.blacklist.name}</td>
																									</c:if>
																									<c:if
																										test="${blackListContact.blacklist.name eq 'false'}">
																										<td class="yes-cell"><i
																											class="material-icons">check</i></td>
																									</c:if>
																									<c:if
																										test="${blackListContact.blacklist.name eq 'true'}">
																										<td class="no-cell wordwrap"><i
																											class="material-icons">clear</i>
																											<br>${blackListContact.blacklist.nameMatchedData}</td>
																									</c:if>
																									<!--End of Field added to resolve AT-322 by Vishal J -->
																									<c:choose>
																										<c:when
																											test="${blackListContact.blacklist.phone}">
																											<td class="yes-cell"><i
																												class="material-icons">check</i></td>
																										</c:when>
																										<c:otherwise>
																											<td class="no-cell wordwrap"><i
																												class="material-icons">clear</i>
																												<br>${blackListContact.blacklist.phoneMatchedData}</td>
																										</c:otherwise>
																									</c:choose>
																									<c:choose>
																										<c:when
																											test="${blackListContact.blacklist.status}">
																											<td class="yes-cell"><i
																												class="material-icons">check</i></td>
																										</c:when>
																										<c:otherwise>
																											<td class="no-cell"><i
																												class="material-icons">clear</i></td>
																										</c:otherwise>
																									</c:choose>
																								</tr>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<td class="nowrap center">${blackListContact.blacklist.statusValue}</td>
																									<td class="nowrap center">${blackListContact.blacklist.statusValue}</td>
																									<td class="nowrap center">${blackListContact.blacklist.statusValue}</td>
																									<td class="nowrap center">${blackListContact.blacklist.statusValue}</td>
																									<td class="nowrap center">${blackListContact.blacklist.statusValue}</td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
																					</c:forEach>
																					<input type="hidden" id="contactBlacklistStatus-${contactDetail.currentContact.id}" value="${contactDetail.internalRule[0].blacklist.prevStatusValue}"/>
																					<!-- End of Condition added to resolve AT-259 by Vishal J -->
																				</tbody>
																			</table>
																			
																			<p></p>
																			<form id = "blacklist_ReCheckId-${contactDetail.currentContact.id}">
																				<p class="flush-margin eid-field">
																					<input id="regDetails_blacklist_recheck-${contactDetail.currentContact.id}" type="button"
																						class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																						onclick="resendCFXContactBlacklist('${contactDetail.currentContact.id}');" <c:out value="${buttonDisable}" />>
																					<object id="gifloaderforBlacklistresend-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
																					</object> 
																					<span id="blacklist_error_field-${contactDetail.currentContact.id}" class="form__field-error" hidden="hidden">
																						Some kind of error message. <a href="#" class="">Back to summary</a>
																					</span>
																				</p>
																			</form>

																		</div>

																	</div>
																	<div class="accordion__section">

																		<div
																			id="regDetails_kyc_indicatior-${contactDetail.currentContact.id}"
																			class="accordion__header"
																			onclick="onLoadViewMore('KYC','${contactDetail.currentContact.id}','CONTACT');">
																			<a href="#"><i class="material-icons">add</i>EID
																				<%-- <c:if test="${contactEid.isRequired}"> --%>
																					<c:if
																						test="${contactDetail.kycDetails.failCount gt 0}">
																						<span
																							id="regDetails_kycPass-${contactDetail.currentContact.id}"
																							class="indicator--negative">${contactDetail.kycDetails.failCount}</span>
																					</c:if>
																					<c:if
																						test="${contactDetail.kycDetails.passCount gt 0}">
																						<span
																							id="regDetails_kycNeg-${contactDetail.currentContact.id}"
																							class="indicator--positive">${contactDetail.kycDetails.passCount}</span>
																					</c:if>
																				<%-- </c:if> --%> </a>
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
																						<th class="center">Overall <br /> status
																						</th>
																					</tr>
																				</thead>
																				<tbody
																					id="regDetails_eid-${contactDetail.currentContact.id}">
																					<c:forEach var="contactEid"
																						items="${contactDetail.kycDetails.kyc}">
																						<c:choose>
																							<c:when test="${contactEid.isRequired}">
																								<tr>
																									<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																									<td>${contactEid.checkedOn}</td>
																									<c:choose>
																										<c:when test="${contactEid.eidCheck}">
																											<td class="yes-cell"><i
																												class="material-icons">check</i></td>
																										</c:when>
																										<c:otherwise>
																											<td class="no-cell"><i
																												class="material-icons">clear</i></td>
																										</c:otherwise>
																									</c:choose>

																									<td class="number">${contactEid.verifiactionResult}</td>
																									<td><a href="javascript:void(0);"
																										onclick="showCFXProviderResponse('${contactEid.id}','KYC')">${contactEid.referenceId}</a></td>
																									<td class="nowrap">${contactEid.dob}</td>
																									<c:choose>
																										<c:when test="${contactEid.status}">
																											<td class="yes-cell"><i
																												class="material-icons">check</i></td>
																										</c:when>
																										<c:otherwise>
																											<td class="no-cell"><i
																												class="material-icons">clear</i></td>
																										</c:otherwise>
																									</c:choose>
																								</tr>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																									<td>${contactEid.checkedOn}</td>
																									<td class="nowrap">${contactEid.statusValue}</td>
																									<td class="number">${contactEid.verifiactionResult}</td>
																									<td><a href="javascript:void(0);"
																										onclick="showCFXProviderResponse('${contactEid.id}','KYC')">${contactEid.referenceId}</a></td>
																									<td class="nowrap">${contactEid.dob}</td>
																									<td class="nowrap">${contactEid.statusValue}</td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
																					</c:forEach>
																					<input type="hidden" id="contactKycStatus-${contactDetail.currentContact.id}" value="${contactDetail.kycDetails.kyc[0].prevStatusValue}"/>
																				</tbody>
																			</table>
																			<input type="hidden"
																				id="kycTotalRecordsId-${contactDetail.currentContact.id}"
																				value='${contactDetail.kycDetails.kycTotalRecords}' />
																			<a href="javascript:void(0);"
																				id="viewMoreDetails_kyc-${contactDetail.currentContact.id}"
																				class="load-more space-after"
																				onclick="viewMoreCFXDetails('KYC' ,'regDetails_eid-${contactDetail.currentContact.id}','kycTotalRecordsId-${contactDetail.currentContact.id}','leftRecordsIdKyc-${contactDetail.currentContact.id}',${contactDetail.currentContact.id},'CONTACT');">
																				VIEW <span class="load-more__extra"
																				id="viewMore_KycId-${contactDetail.currentContact.id}">
																			</span> MORE <span class="load-more__left"
																				id="leftRecordsIdKyc-${contactDetail.currentContact.id}">
																			</span>
																			 <object id="gifloaderforViewmoreKycCFX" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																			</a>
																			<form
																				id="kyc_ReCheckId-${contactDetail.currentContact.id}">
																				<p class="flush-margin eid-field">
																					<c:choose>
																						<c:when
																							test="${contactDetail.currentContact.isCountrySupported}">

																							<input
																								id="regDetails_kyc_recheck-${contactDetail.currentContact.id}"
																								type="button"
																								class="<c:out value="${buttonClass} eid-specific"/>"
																								value="Repeat checks"
																								onclick="resendCFXContactKyc('${contactDetail.currentContact.id}');"
																								
																								<c:out value="${buttonDisable}"/>>
																								 <object id="gifloaderforresendKYCContact-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																						</c:when>
																						<c:otherwise>
																							<input type="submit"
																								id="regDetails_kyc_recheck-${contactDetail.currentContact.id}"
																								class="button--primary button--disabled eid-specific"
																								value="Repeat checks" disabled>

																						</c:otherwise>
																					</c:choose>
																					<span
																						id="kyc_error_field-${contactDetail.currentContact.id}"
																						class="form__field-error" hidden="hidden">
																						Some kind of error message. <a href="#" class="">Back
																							to summary</a>
																					</span>
																				</p>
																			</form>
																			<%-- 		</c:otherwise>
		</c:choose> --%>



																		</div>

																	</div>


																	<div class="accordion__section">
																		<div
																			id="regDetails_sanction_indicatior-${contactDetail.currentContact.id}"
																			class="accordion__header"
																			onclick="onLoadViewMore('SANCTION','${contactDetail.currentContact.id}','CONTACT');">
																			<a href="#"><i class="material-icons">add</i>Sanctions
																				<c:if
																					test="${contactDetail.sanctionDetails.failCount gt 0}">
																					<span
																						id="regDetails_sanctionPass-${contactDetail.currentContact.id}"
																						class="indicator--negative">${contactDetail.sanctionDetails.failCount}</span>
																				</c:if> <c:if
																					test="${contactDetail.sanctionDetails.passCount gt 0}">
																					<span
																						id="regDetails_sanctionNeg-${contactDetail.currentContact.id}"
																						class="indicator--positive">${contactDetail.sanctionDetails.passCount}</span>
																				</c:if> </a>
																		</div>

																		<div class="accordion__content">

																			<form>

																				<table>
																					<thead>
																						<tr>
																							<th class="sorted desc">Updated on <i></i></th>
																							<th>Updated by</th>
																							<th>Sanction ID</th>
																							<th>OFAC List</th>
																							<th>World check</th>
																							<th>Status</th>
																						</tr>
																					</thead>
																					<tbody
																						id="regDetails_sanction-${contactDetail.currentContact.id}" class="checkSanctions saction_contact">
																						<c:forEach var="contactSanction"
																							items="${contactDetail.sanctionDetails.sanction}">
																							<c:choose>
																								<c:when test="${contactSanction.isRequired}">
																									<tr>
																										<td hidden="hidden" class="center">${contactSanction.eventServiceLogId}</td>
																										<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																										<td>${contactSanction.updatedOn}</td>
																										<td class="nowrap">${contactSanction.updatedBy}</td>
																										<!-- Removed class="nowrap" from 'sanction ID' to set columns of table properly on UI -->
																										<td><a href="javascript:void(0);"
																											onclick="showCFXProviderResponse('${contactSanction.eventServiceLogId}','SANCTION')">${contactSanction.sanctionId}</a></td>
																										<td class="nowrap">${contactSanction.ofacList}</td>
																										<td class="nowrap">${contactSanction.worldCheck}</td>
																										<c:choose>
																											<c:when test="${contactSanction.status }">
																												<td id="sanction_status" class="yes-cell"><i
																													class="material-icons">check</i></td>
																											</c:when>
																											<c:otherwise>
																												<td id="sanction_status" class="no-cell"><i
																													class="material-icons">clear</i></td>
																											</c:otherwise>
																										</c:choose>
																									</tr>
																								</c:when>
																								<c:otherwise>
																									<tr>
																										+<td hidden="hidden" class="center">${contactSanction.eventServiceLogId}</td>
																										<!-- Removed class="nowrap" from 'checkedOn' to set columns of table properly on UI -->
																										<td>${contactSanction.updatedOn}</td>
																										<td class="nowrap">${contactSanction.updatedBy}</td>
																										<td><a href="javascript:void(0);"
																											onclick="showCFXProviderResponse('${contactSanction.eventServiceLogId}','SANCTION')">${contactSanction.sanctionId}</a></td>
																										<td class="nowrap">${contactSanction.ofacList}</td>
																										<td class="nowrap">${contactSanction.worldCheck}</td>
																										<td id="sanction_status" class="nowrap">${contactSanction.statusValue}</td>
																									</tr>
																								</c:otherwise>
																							</c:choose>
																						</c:forEach>
																						<input type="hidden" id="contactSanctionStatus-${contactDetail.currentContact.id}" value="${contactDetail.sanctionDetails.sanction[0].prevStatusValue}"/>
																					</tbody>
																				</table>
																				<input type="hidden"
																					id="sanctionTotalRecordsId-${contactDetail.currentContact.id}"
																					value='${contactDetail.sanctionDetails.sanctionTotalRecords}' />
																				<a href="javascript:void(0);"
																					id="viewMoreDetails_sanc-${contactDetail.currentContact.id}"
																					class="load-more space-after"
																					onclick="viewMoreCFXDetails('SANCTION' ,'regDetails_sanction-${contactDetail.currentContact.id}','sanctionTotalRecordsId-${contactDetail.currentContact.id}','leftRecordsIdSanc-${contactDetail.currentContact.id}',${contactDetail.currentContact.id},'CONTACT');">
																					VIEW 
						
																					<span class="load-more__extra"
																					id="viewMore_SancId-${contactDetail.currentContact.id}">
																				</span> MORE 
																				<!-- <object id="gifloaderforViewmoresanctionCFX" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object>  -->
																				
																				<span class="load-more__left"
																					id="leftRecordsIdSanc-${contactDetail.currentContact.id}">
																				</span>
																				</a>

																				<div
																					class="boxpanel--shadow space-after sanction-field">
																					<fieldset>
																						<legend>Update selected sanctions</legend>
																						<div class="grid space-after">
																							<div class="grid__row">
																								<div class="grid__col--6">
																									<div class="form__field-wrap flush-margin">

																										<p class="label">Which field?</p>

																										<div
																											id="singlelist-sanction-field-sharon-beavan"
																											class="singlelist clickpanel--right">

																											<p 
																												class="singlelist__chosen clickpanel__trigger">Please
																												select</p>
																											<i
																												class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																											<div class="clickpanel__content--hidden">

																												<fieldset>
																													<ul class="singlelist__options">
																														<li><label
																															for="rad-sanction-field-1-${contactDetail.currentContact.id}">
																																<input
																																id="rad-sanction-field-1-${contactDetail.currentContact.id}"
																																type="radio"
																																name="regDetails_updateField-${contactDetail.currentContact.id}"
																																value="ofaclist" />OFAC List
																														</label></li>
																														<li><label
																															for="rad-sanction-field-2-${contactDetail.currentContact.id}">
																																<input
																																id="rad-sanction-field-2-${contactDetail.currentContact.id}"
																																type="radio"
																																name="regDetails_updateField-${contactDetail.currentContact.id}"
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

																									<div class="form__field-wrap flush-margin">

																										<p class="label">Set this field to...</p>

																										<div
																											id="singlelist-sanction-field-val-sharon-beavan"
																											class="singlelist clickpanel--right">

																											<p
																												class="singlelist__chosen clickpanel__trigger">Please
																												select</p>

																											<i
																												class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																											<div class="clickpanel__content--hidden">

																												<fieldset>
																													<ul class="singlelist__options">
																														<li><label
																															for="rad-sanction-fieldval-1-${contactDetail.currentContact.id}">
																																<input
																																id="rad-sanction-fieldval-1-${contactDetail.currentContact.id}"
																																type="radio"
																																name="regDetails_updateField_Value-${contactDetail.currentContact.id}"
																																value="Confirmed hit" /> Confirmed hit
																														</label></li>
																														<li><label
																															for="rad-sanction-fieldval-2-${contactDetail.currentContact.id}">
																																<input
																																id="rad-sanction-fieldval-2-${contactDetail.currentContact.id}"
																																type="radio"
																																name="regDetails_updateField_Value-${contactDetail.currentContact.id}"
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
																						<span
																								id="update_sanction_error_field-${contactDetail.currentContact.id}"
																								class="form__field-error" hidden="hidden">
																								Some kind of error message. <a href="#" class="">Back
																									to summary</a>
																							</span>
																							<input
																								id="regDetails_updateSanction-${contactDetail.currentContact.id}"
																								type="button"
																								class="<c:out value="${buttonClass}"/>"
																								value="Apply"
																								onclick="updateCFXContactSanction('${contactDetail.currentContact.id}')"
																								<c:out value="${buttonDisable}"/> />
																								<object id="gifloaderforupdatesanctionContactcfx-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																								 
																						</p>
																					</fieldset>

																				</div>

																			</form>

																			<form>
																				<p class="flush-margin sanction-field">
																					<input
																						id="regDetails_sanction_recheck-${contactDetail.currentContact.id}"
																						type="button"
																						class="<c:out value="${buttonClass}"/>"
																						value="Repeat checks"
																						onclick="resendCFXContactSanction('${contactDetail.currentContact.id}');"
																						<c:out value="${buttonDisable}"/> /> 
																						 <object id="gifloaderforresendsanctionCFX-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
																						</object> 
																					<span
																						id="resend_sanction_error_field-${contactDetail.currentContact.id}"
																						class="form__field-error" hidden="hidden">
																						Some kind of error message. <a href="#" class="">Back
																							to summary</a>
																					</span>
																				</p>
																			</form>

																		</div>

																	</div>

																	<div class="accordion__section">
																		<div class="accordion__section">
																			<div
																				id="regDetails_fraugster_indicatior-${contactDetail.currentContact.id}"  href="#"
																				class="accordion__header"
																				onclick="onLoadViewMore('FRAUGSTER','${contactDetail.currentContact.id}','CONTACT');">
																				<c:forEach var="fraugsterContact"
																					items="${contactDetail.fraugsterDetails.fraugster}">
																					<a href="#" onclick= "showCFXProviderResponse('${fraugsterContact.id}','FRAUGSTER','FraugsterChart','${contactDetail.currentContact.id}');"><i class="material-icons">add</i>FRAUDPREDICT
																						<c:if test="${fraugsterContact.failCount gt 0}">
																							<span id="regDetails_fraugsterFail-${contactDetail.currentContact.id}"
																								class="indicator--negative">${fraugsterContact.failCount}</span>
																						</c:if> <c:if test="${fraugsterContact.passCount gt 0}">
																							<span id="regDetails_fraugsterPass-${contactDetail.currentContact.id}"
																								class="indicator--positive">${fraugsterContact.passCount}</span>
																						</c:if> </a>
																				</c:forEach>
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
																					<tbody
																						id="regDetails_fraugster-${contactDetail.currentContact.id}">
																						<c:forEach var="contactFraugster"
																							items="${contactDetail.fraugsterDetails.fraugster}">
																							<tr href="#" onclick="showCFXProviderResponse('${contactFraugster.id}','FRAUGSTER','FraugsterChart','${contactDetail.currentContact.id}')">
																								<td>${contactFraugster.createdOn }</td>
																								<td class="nowrap">${contactFraugster.updatedBy }</td>
																								<td class=""><a href="#"
																									onclick="showCFXProviderResponse('${contactFraugster.id}','FRAUGSTER')">${contactFraugster.fraugsterId }</a></td>
																								<td class="nowrap" class="number">${contactFraugster.score }</td>
																								<c:choose>
																									<c:when test="${contactFraugster.status }">
																										<td id="fraugster_status" class="yes-cell"><i
																										class="material-icons">check</i></td>
																									</c:when>
																									<c:when test="${contactFraugster.status eq false}">
																										<td id="fraugster_status" class="no-cell"><i
																										class="material-icons">clear</i></td>
																									</c:when>
																									<c:when test="${contactFraugster.status ne true}">
																										<td class="nowrap">${contactFraugster.status }</td>
																									</c:when>																																																		
																									<c:otherwise>
																										<td class="nowrap">${contactFraugster.status }</td>
																									</c:otherwise>
																								</c:choose>
																								
																							</tr>
																							<input type="hidden"
																								id="fraugsterTotalRecordsId-${contactDetail.currentContact.id}"
																								value='${contactDetail.fraugsterDetails.fraugsterTotalRecords}' />
																						</c:forEach>
																						<input type="hidden" id="contactFraugsterStatus-${contactDetail.currentContact.id}" value="${contactDetail.fraugsterDetails.fraugster[0].prevStatusValue}"/>
																					</tbody>
																				</table>

																				<a href="javascript:void(0);"
																					id="viewMoreDetails_fraug-${contactDetail.currentContact.id}"
																					class="load-more space-after"
																					onclick="viewMoreCFXDetails('FRAUGSTER' ,'regDetails_fraugster-${contactDetail.currentContact.id}','fraugsterTotalRecordsId-${contactDetail.currentContact.id}','leftRecordsIdFraug-${contactDetail.currentContact.id}',${contactDetail.currentContact.id},'CONTACT');">
																					VIEW <span class="load-more__extra"
																					id="viewMore_FraugId-${contactDetail.currentContact.id}">
																				</span> MORE <span class="load-more__left"
																					id="leftRecordsIdFraug-${contactDetail.currentContact.id}">
																				</span>
																				 <object id="gifloaderforViewmoreFraugsterCFX" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																				</a>
																				<input type=hidden id = "currenctContactId" value = '${contactDetail.currentContact.id}'/>
																				<form id = "fraugster_ReCheckId">
																					<p class="flush-margin eid-field">
																						<input id="regDetails_fraugster_recheck-${contactDetail.currentContact.id}" type="button"
																							class="<c:out value="${buttonClass}"/>" value="Repeat checks"
																							onclick="resendCFXContactFraugster('${contactDetail.currentContact.id}');" <c:out value="${buttonDisable}" />>
																							<object id="gifloaderforresendfraugster-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
																							</object> 
																							<span id="fraugster_error_field" class="form__field-error" hidden="hidden">
																							Some kind of error message. <a href="#" class="">Back to summary</a>
																							</span>
																					</p>
																				</form>
																				<div id = "boxpanel-space-before-${contactDetail.currentContact.id}" class="boxpanel space-before">
																				<div id="cfx-fraugster-chart-${contactDetail.currentContact.id}" class="cfx-fraugster-chart"></div>
																			</div>

																			</div>
																			
																		</div>

																		</div>
																		
																		<div class="accordion__section">
																			<div
																				id="doc_indicatior-${contactDetail.currentContact.id}"
																				class="accordion__header">
																				<a href="#"><i class="material-icons">add</i>Attached
																					documents <c:if
																						test="${fn:length(contactDetail.documents) gt 0}">
																						<span
																							id="docConunt-${contactDetail.currentContact.id}"
																							class="indicator">${fn:length(contactDetail.documents)}</span>
																					</c:if> </a>
																			</div>

																			<div class="accordion__content">
																				<table class="space-after">
																					<thead>
																						<tr>
																							<th class="tight-cell sorted desc">Created
																								on <i></i>
																							</th>
																							<th>Created by</th>
																							<th>Document name</th>
																							<th>Type</th>
																							<th>Note</th>
																						</tr>
																					</thead>
																					<tbody
																						id="cfx_attachDoc-${contactDetail.currentContact.id}">
																						<c:forEach var="contactDocument"
																							items="${contactDetail.documents}">
																							<tr class="talign">
																								<td>${contactDocument.createdOn}</td>
																								<td>${contactDocument.createdBy}</td>
																								<td class="breakword"><a
																									href="${contactDocument.url}">${contactDocument.documentName}</a>
																								</td>
																								<td class="breakword">${contactDocument.documentType}</td>
																								<td class="wrap-cell">${contactDocument.note}</td>
																							</tr>
																						</c:forEach>
																					</tbody>
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

																											<div
																												id="singlelist-document-type-sharon-beavan"
																												class="singlelist clickpanel--right">

																												<p id="docTypeSelectionContactId"
																													class="singlelist__chosen clickpanel__trigger2">Please
																													select</p>

																												<i
																													class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																												<div class="clickpanel__content--hidden">

																													<fieldset>

																														<input
																															class="singlelist__search space-after"
																															type="search" placeholder="Search list" />

																														<ul class="singlelist__options multilist__options">

																															<c:forEach var="documentList"
																																items="${regDetails.documentList}"
																																varStatus="loop">

																																<li><label
																																	for="rad-doctype-${loop.index}-${contactDetail.currentContact.id}">
																																		<input
																																		name="documentType-${contactDetail.currentContact.id}"
																																		id="rad-doctype-${loop.index}-${contactDetail.currentContact.id}"
																																		type="radio"
																																		value="${documentList.documentName}" />${documentList.documentName}
																																</label></li>
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
																												file</label> <input type="file"
																												id="file-choose-document-${contactDetail.currentContact.id}" />
																										</div>
																									</div>
																								</div>
																								<div class="grid_row">
																									<div class="grid__col--12">
																										<div class="form__field-wrap flush-margin">
																											<label for="text-note">Note</label> <input
																												type="text"
																												id="text-note-${contactDetail.currentContact.id}" />
																										</div>
																									</div>
																								</div>
																							</div>

																							<p class="right">
																								<!-- Id given to upload documents button to make it enable and disable 
									Changes done by Vishal J -->
																								<input id="attach_document_button_id_contact"
																									type="button"
																									class="<c:out value="${buttonClass}"/>"
																									value="Attach"
																									onclick="uploadDocument('${contactDetail.currentContact.crmAccountId}','${contactDetail.currentContact.crmContactId}','text-note-${contactDetail.currentContact.id}','file-choose-document-${contactDetail.currentContact.id}','cfx_attachDoc-${contactDetail.currentContact.id}','documentType-${contactDetail.currentContact.id}','attach_document_button_id_contact','${regDetails.account.orgCode}','gifloaderfordocumentloader-${contactDetail.currentContact.id}');"
																									<c:out value="${buttonDisable}"/> />
																									<object id="gifloaderfordocumentloader-${contactDetail.currentContact.id}" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
																							</p>
																						</fieldset>
																					</form>
																				</div>
																			</div>
																		</div>
																	</div>
															</section>
														</div>
													</div>
												</c:forEach>
												<!-- Contact Details Ended  -->
											</div>
										</section>
										<!-- CONTACTS SECTION ENDED -->
									</div>

									<!-- Action Div Begin -->
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
																				<input class="multilist__search space-after"
																					type="search" placeholder="Search list">
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
																						test="${regDetails.account.complianceStatus == 'ACTIVE' }">
																						<label
																							class="pill-choice__choice--positive pill-choice__choice--on"
																							for="contact_contactStatus_active"> <input
																							id="contact_contactStatus_active" value="ACTIVE"
																							type="radio"
																							name="regDetails_contactStatus_radio"
																							class="input-more-hide"
																							data-more-hide="input-more-areas-reasons" checked />
																							ACTIVE
																						</label>
																					</c:when>
																					<c:otherwise>
																						<label class="pill-choice__choice--positive"
																							for="contact_contactStatus_active"> <input
																							id="contact_contactStatus_active" value="ACTIVE"
																							type="radio"
																							name="regDetails_contactStatus_radio"
																							class="input-more-hide"
																							data-more-hide="input-more-areas-reasons" />
																							ACTIVE
																						</label>
																					</c:otherwise>
																				</c:choose></li>
																			<li><c:choose>
																					<c:when
																						test="${regDetails.account.complianceStatus == 'INACTIVE' }">
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
																						test="${regDetails.account.complianceStatus == 'REJECTED' }">
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
														
														    <input type="button"
															class="<c:out value="${buttonClass}"/>"
															id="regDetails_profile_update" value="Apply"
															onclick="executeCFXActions(false);"
															<c:out value="${buttonDisable}"/> />
															
																	<input type="button"
															class="<c:out value="${buttonClass}"/>"
															id="regDetails_profile_update_and_unlock" value="Apply & UNLOCK"
															onclick="executeCFXActions(true);"
															<c:out value="${buttonDisable}"/> />
															
															 <object id="gifloaderforprofileupdateCFX" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object> 
														<c:choose>
															<c:when
																test="${(regDetails.locked == null ||!regDetails.locked)}">
																<span id="appliedLock"> <small id="applyLock"
																	class="button--supporting"><a href="#">Lock
																			this record</a> to own it</small>
																</span>
															</c:when>
															<c:otherwise>
																<span hidden="hidden" id="appliedLock"> <small
																	id="applyLock" class="button--supporting"><a
																		href="#">Lock this record</a> to own it</small>
																</span>
															</c:otherwise>
														</c:choose>
														<span id="profile_update_error_field"
															class="form__field-error" hidden="hidden"> Some
															kind of error message. <a href="#" class="">Back to
																summary</a>
														</span>
													</section>

												</div>

											</form>

										</section>

									</div>
									<!-- Action Div Ended -->

								</div>

							</div>

						</div>

					</div>

			</div>

		</div>

		</main>
		<form id="regDetailForm"
			action="/compliance-portal/registrationDetails" method="POST">
			<input type="hidden" id="contactId" value="" name="contactId" /> <input
				type="hidden" id="searchSortCriteria" value="" name="searchCriteria" />
			<input type="hidden" id="custType" value="" name="custType" />
			<%-- <c:choose>
				<c:when test="${regDetails.isPagenationRequired==true}">
					<ul class="pagination--fixed page-load" id="paginationBlock">
						<li class="pagination__jump--disabled"><a id="firstRecord"
							onclick="getRegFirstRecord('${regDetails.paginationDetails.firstRecord.custType}','${regDetails.paginationDetails.firstRecord.id}');"
							href="#" data-ot="First record"><i class="material-icons">first_page</i></a></li>
						<li class="pagination__jump"><a id="previousRecord"
							onclick="getRegPreviousRecord('${regDetails.paginationDetails.prevRecord.custType}','${regDetails.paginationDetails.prevRecord.id}');"
							href="#" data-ot="Previous record"><i class="material-icons">navigate_before</i></a>
						</li>
						<li class="pagination__jump"><a id="nextRecord"
							onclick="getRegNextRecord('${regDetails.paginationDetails.nextRecord.custType}','${regDetails.paginationDetails.nextRecord.id}');"
							href="#" data-ot="Next record"><i class="material-icons">navigate_next</i></a>
						</li>
						<li class="pagination__jump"><a id="lastRecord"
							onclick="getRegLastRecord('${regDetails.paginationDetails.totalRecords.custType}','${regDetails.paginationDetails.totalRecords.id}');"
							href="#" data-ot="Last record"><i class="material-icons">last_page</i></a>
						</li>
						<li class="pagination__text">Record <strong
							id="currentRecord">${regDetails.currentRecord }</strong> of <span
							id="totolRecords">${regDetails.totalRecords}</span>
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
			id="contact_contactId" value='${regDetails.currentContact.id}' /> <input
			type="hidden" id="userResourceId"
			value='${regDetails.userResourceId}' /> <input type="hidden"
			id="contact_accountId" value='${regDetails.account.id}' /> <input
			type="hidden" id="trade_accountId"
			value='${regDetails.account.tradeAccountID}' /> <input type="hidden"
			id="account_organisation" value='${regDetails.account.orgCode}' /> <input
			type="hidden" id="customerType"
			value='${regDetails.account.clientType}' /> <input type="hidden"
			id="current_contact_crmContactId"
			value='${regDetails.currentContact.ccCrmContactId}' /> <input
			type="hidden" id="actLogTotalRecordsId"
			value='${regDetails.activityLogs.totalRecords}' />
			<input type="hidden" id="account_complianceDoneOn"
			value='${regDetails.account.regCompleteAccount}' />
			<input type="hidden" id="account_registrationInDate" value='${regDetails.account.registrationInDate}' />
			<input type="hidden" id="account_complianceExpiry" value='${regDetails.account.complianceExpiry}' />
			<input type="hidden" id="account_isOnQueue" value='${regDetails.isOnQueue}' />
			<input type="hidden" id="documentCategory" value='Registration' />
			<input type="hidden" id="orgganizationCode" value='${regDetails.account.orgCode}' />
			<input type="hidden" id="isRecordLocked" value='${regDetails.locked}' />
			<input type="hidden" id="fraugsterTotalRecordsId" value='${regDetails.fraugsterDetails.fraugsterTotalRecords}'/>
			<input type="hidden" id="auditTrailActLogTotalRecords" value=""/>
			<input type="hidden" id="auditTrailActLogEntityType" value=""/>
			<input type="hidden" id="fraud_ring_button_id" value="fraud_ring_button-${contactDetail.currentContact.crmContactId}" />
			<input type="hidden" id="fraud_ring_node_summary_details" value=""/>
			<input type="hidden" id="contactSfidArray" value=""/>
            <input type="hidden" id="dataAnonStatus" value='${regDetails.dataAnonStatus}'/>
            <input type="hidden" id="poiExists" value='${regDetails.poiExists}'/>
            <input type="hidden" id="accountTMFlag" value='${regDetails.account.accountTMFlag}'/>
            <input type="hidden" id="accountBlacklistStatus" value='${regDetails.internalRule[0].blacklist.prevStatusValue}'/>
            <input type="hidden" id="accountSanctionStatus" value='${regDetails.sanctionDetails.sanction[0].prevStatusValue}'/>
            <input type="hidden" id="accountVersion" value='${regDetails.accountVersion}'/>
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
	
	<div id="ContactHistorypopups" class="popupDiv"
		title="Client #${regDetails.account.tradeAccountNumber} original details">
		<div id="contactHistory" class="popupTextAreaAccountHistory">
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
	
								
	<div id= "contact_Details" class="boxpanel--shadow space-after" style="display:none" >

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
																		
																			</dl>

																		</div>

																	</div>

																</div>

	</div>									
	
	<div id = "account_corporateDetails" class="accordion__section" style="display:none" >
	<!-- <div id = "account_corporateDetails" class="accordion__section" style="display:none" > -->
	<div class="accordion__section" >
													<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Corporate
															compliance</a>
													</div>

													<div class="accordion__content">

														<div class="boxpanel--shadow space-after">

															<div class="grid">

																<div class="grid__row">

																	<div class="grid__col--4">
																		<dl class="split-list">

																			<dt>SIC code</dt>
																			<dd id="account-corporatecompliance-sic-popup" ></dd>

																			<dt>SIC description</dt>
																			<dd id="account-corporatecompliance-sicDesc-popup" ></dd>
			
																			<dt>Registration number</dt>
																			<dd id="account-corporatecompliance-registrationNumber-popup" ></dd>

																			<dt>Former name</dt>
																			<dd id = "account-corporatecompliance-formerName-popup"></dd>

																			<dt>Legal form</dt>
																			<dd id = "account-corporatecompliance-legalForm-popup"></dd>

																			<!-- newly added fields for DNB data  -->

																			<dt>Global ultimate duns</dt>
																			<dd id = "account-corporatecompliance-globalUltimateDuns-popup" ></dd>

																			<dt>Global ultimate name</dt>
																			<dd id = "account-corporatecompliance-globalUltimateName-popup" ></dd>

																			<dt>Global ultimate country</dt>
																			<dd id = "account-corporatecompliance-globalUltimateCountry-popup" ></dd>

																			<dt>Registration date</dt>
																			<dd id = "account-corporatecompliance-registrationDate-popup" ></dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<dt>Match name</dt>
																			<dd id = "account-corporatecompliance-matchName-popup" ></dd>

																			<dt>Iso country code two digit</dt>
																			<dd id = "account-corporatecompliance-isoCountryCode2Digit-popup" ></dd>

																			<dt>Iso Country Code Three Digit</dt>
																			<dd id = "account-corporatecompliance-isoCountryCode3Digit-popup" ></dd>

																			<dt>Statement date</dt>
																			<dd id = "account-corporatecompliance-statementDate-popup" ></dd>

																			<dt>Foreign owned company</dt>
																			<dd id = "account-corporatecompliance-foreignOwnedCompany-popup" ></dd>

																			<dt>Net worth</dt>
																			<dd id = "account-corporatecompliance-netWorth-popup" ></dd>

																			<dt>Fixed assets</dt>
																			<dd id = "account-corporatecompliance-fixedAssets-popup" ></dd>

																			<dt>Total liabilities and equities</dt>
																			<dd id = "account-corporatecompliance-totalLiabilitiesAndEquities-popup" ></dd>

																			<dt>Total share holders</dt>
																			<dd id = "account-corporatecompliance-totalShareHolders-popup" ></dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<!-- newly added DNB Data  -->

																			<dt>Gross income</dt>
																			<dd id = "account-corporatecompliance-grossIncome-popup" ></dd>

																			<dt>Net income</dt>
																			<dd id = "account-corporatecompliance-netIncome-popup" ></dd>

																			<dt>Total current assets</dt>
																			<dd id = "account-corporatecompliance-totalCurrentAssets-popup" ></dd>

																			<dt>Total assets</dt>
																			<dd id = "account-corporatecompliance-totalAssets-popup" ></dd>

																			<dt>Total longterm Liabilities</dt>
																			<dd id = "account-corporatecompliance-totalLongTermLiabilities-popup" ></dd>

																			<dt>Total current liabilities</dt>
																			<dd id = "account-corporatecompliance-totalCurrentLiabilities-popup" ></dd>

																			<dt>Total matched shareholders</dt>
																			<dd id = "account-corporatecompliance-totalMatchedShareholders-popup" ></dd>

																			<dt>Financial strength</dt>
																			<dd id = "account-corporatecompliance-financialStrength-popup" ></dd>

																		</dl>
																	</div>

																</div>

															</div>

														</div>
										</div>
		</div>
	</div>
		
	<div id = "account_accountDetails" class="accordion__section" style="display:none" >
	<div class="accordion__section" >
													<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Account</a>
													</div>

													<div class="accordion__content">
	
	
											<div class="boxpanel--shadow" >

												<div class="grid">

													<div class="grid__row">

														<div class="grid__col--4">
															<dl class="split-list">

																<dt>Name</dt>
																<dd id="account-name-popup" ></dd>

																<dt>Client type</dt>
																<dd id="account-clienttype-popup"></dd>
																
																<dt>Website</dt>
																<dd>
																	<a href="#" id ="account-webSite-popup"></a>
																</dd>
																
																<dt>Source Of Fund</dt>
																<dd id="account-sourceOfFund-popup"></dd>
																
																<dt>Service Required</dt>
																<dd id="account-serviceRequired-popup"></dd>
																
																<dt>EstimTransValue</dt>
																<dd id="account-estimTransValue-popup"></dd>
																
																<dt>Affiliate Name</dt>
																<dd id="account-affiliateName-popup"></dd>
																
																<dt>Browser Type</dt>
																<dd id="account-browserType-popup"></dd>
																
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">

																<dt>Source</dt>
																<dd id="account-source-popup" ></dd>

																<dt>Currency Pair</dt>
																<dd id="account-currencyPair-popup"></dd>

																<dt>Purpose of transaction</dt>
																<dd id="account-purposeOfTrans-popup" ></dd>
																
																<dt>Cookie Info</dt>
																<dd id="account-cookieInfo-popup"></dd>
																
																<dt>Browser Type</dt>
																<dd id="account-refferalText-popup"></dd>
																
																<dt>Date Of Reg</dt>
																<dd id="account-dateOfReg-popup"></dd>
																
																<dt>Trade Account Number</dt>
																<dd id="account-tradeAccountNum-popup"></dd>
																
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">
																
																<dt>Account SF ID</dt>
																<dd id = "account-accSFID-popup" ></dd>
																		
																<dt>Legal Entity</dt>
																<dd id="account-legalentity-popup" ></dd> 
																
																<dt>Annual FX requirement</dt>
																<dd id="account-annualFXRequirement-popup" ></dd>
																
																<dt>TradeAccountId</dt>
																<dd id="account-tradeAccountId-popup"></dd>
																
																<dt>Sourcelookup</dt>
																<dd id="account-sourceLookup-popup"></dd>
																
																<dt>AvgTransactionValue</dt>
																<dd id="account-avgTransactionValue-popup"></dd>
																
																<dt>Countries Of Operation</dt>
																<dd id="account-countriesOfOperation-popup"></dd>
																
															</dl>
														</div>

													</div>

												</div>

								</div> 
							</div>

					</div> 
			</div> 
			
	<div id = "account_companyDetails" class="boxpanel--shadow" style="display:none" >
		<div class="accordion__section" >
												<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Company</a>
													</div>

									<div class="accordion__content">
										<div class="boxpanel--shadow" >
												<div class="grid">

													<div class="grid__row">

														<div class="grid__col--4">
															<dl class="split-list">

																<!-- company start1-->
																<dt>E-tailer</dt>
																
																<dd id="account-company-etailer-popup"></dd>
																
																<dt>Vat No</dt>
																
																<dd id="account-company-vatNo-popup"></dd>
																
																<dt>Phone</dt>
																<dd id="account-company-companyphone-popup" ></dd>
																
																<dt>Country Region</dt>
																
																<dd id="account-company-countryRegion-popup"></dd>
																
																<dt>Billing Address</dt>
																
																<dd id="account-company-billingAddress-popup"></dd>
																
																<dt>Option</dt>
																
																<dd id="account-company-option-popup" ></dd>
																<!-- company end1 -->
																
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">

																<!-- company start2-->
																
																<dt>Registration No</dt>
																
																<dd id="account-company-registrationNo-popup"></dd>
																
																<dt>T &amp; C signed date</dt>
																<dd id="account-company-tAndcSignedDate-popup" ></dd>
																
																<dt>Corporate Type</dt>
																
																<dd id="account-company-corporateType-popup"></dd>
																
																<dt>Incorporation Date</dt>
																
																<dd id="account-company-incorporationDate-popup"></dd>
																
																<dt>CCJ</dt>
																
																<dd id="account-company-ccj-popup"></dd>
																
																<dt>Ongoing Due DiligenceDate</dt>
																
																<dd id="account-company-ongoingDueDiligenceDate-popup"></dd>
																<!-- company end2 -->
																
															</dl>
														</div>

														<div class="grid__col--4 grid__col--pad-left">
															<dl class="split-list">

																<!-- company start3-->
																<dt>Est No TransactionsPcm</dt>
																
																<dd id="account-company-estNoTransactionsPcm-popup"></dd>
																
																<dt>Type Of Financial Account</dt>
																
																<dd id="account-company-typeOfFinancialAccount-popup"></dd>
																
																<dt>Shipping Address</dt>
																
																<dd id="account-company-shippingAddress-popup"></dd>
																
																<dt>Country Of Establishment</dt>
																
																<dd id="account-company-countryOfEstablishment-popup"></dd>
																
																<dt>Industry</dt>
																<dd id="account-company-industry-popup"></dd>
																<!-- company end 3-->
																
															</dl>
														</div>

													</div>

												</div>

											</div>
							</div>
					</div>
				</div>
				
				
				<!-- RISK PROFILE BEGIN -->
	<div id = "account_riskProfileDetails" class="boxpanel--shadow" style="display:none" >
				<div class="accordion__section">

													<div class="accordion__header">
														<a href="#"><i class="material-icons">add</i>Risk
															profile</a>
													</div>

													<div class="accordion__content">

														<div class="boxpanel--shadow space-after">

															<div class="grid">

																<div class="grid__row">

																	<div class="grid__col--4">
																		<dl class="split-list">

																			<dt>Country risk indicator</dt>
																			<dd id = "account-riskProfile-countryRiskIndicator-popup" ></dd>

																			<dt>Risk trend</dt>
																			<dd id = "account-riskProfile-riskTrend-popup" ></dd>

																			<dt>Risk direction</dt>
																			<dd id = "account-riskProfile-riskDirection-popup" ></dd>
																			<!-- **** newly added fields added by neelesh pant *******/ -->
																			<dt>Continent</dt>
																			<dd id = "account-riskProfile-continent-popup" ></dd>

																			<dt>Country</dt>
																			<dd id = "account-riskProfile-country-popup" ></dd>

																			<dt>State country</dt>
																			<dd id = "account-riskProfile-stateCountry-popup" ></dd>

																			<dt>Duns number</dt>
																			<dd id = "account-riskProfile-dunsNumber-popup" ></dd>

																			<dt>Trading styles</dt>
																			<dd id = "account-riskProfile-tradingStyles-popup" ></dd>

																			<dt>us1987PrimarySic4Digit</dt>
																			<dd id = "account-riskProfile-us1987PrimarySic4Digit-popup" ></dd>

																			<dt>Financial figures month</dt>
																			<dd id = "account-riskProfile-financialFiguresMonth-popup" ></dd>

																			<dt>Financial figures year</dt>
																			<dd id = "account-riskProfile-financialFiguresYear-popup" ></dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">


																			<dt>Financial year endDate</dt>
																			<dd id = "account-riskProfile-financialYearEndDate-popup" ></dd>

																			<dt>Annual sales</dt>
																			<dd id = "account-riskProfile-annualSales-popup" ></dd>

																			<dt>Modelled annual sales</dt>
																			<dd id = "account-riskProfile-modelledAnnualSales-popup" ></dd>

																			<dt>Net Worth amount</dt>
																			<dd id = "account-riskProfile-netWorthAmount-popup" ></dd>

																			<dt>Credit limit</dt>
																			<dd id = "account-riskProfile-creditLimit-popup" ></dd>

																			<dt>Updated risk</dt>
																			<dd id = "account-riskProfile-updatedRisk-popup" ></dd>

																			<dt>Failure score</dt>
																			<dd id = "account-riskProfile-failureScore-popup" ></dd>

																			<dt>Delinquency failure score</dt>
																			<dd id = "account-riskProfile-delinquencyFailureScore-popup" ></dd>
																			<!-- **** newly added fields added by neelesh pant *******/ -->
																			<dt>Modelled networth</dt>
																			<dd id = "account-riskProfile-modelledNetWorth-popup" ></dd>

																			<dt>Location type</dt>
																			<dd id = "account-riskProfile-locationType-popup" ></dd>

																			<dt>Import export indicator</dt>
																			<dd id = "account-riskProfile-importExportIndicator-popup" ></dd>

																		</dl>
																	</div>
																	<div class="grid__col--4 grid__col--pad-left">
																		<dl class="split-list">

																			<dt>Domestic ultimate record</dt>
																			<dd id = "account-riskProfile-domesticUltimateRecord-popup" ></dd>

																			<dt>Global ultimate record</dt>
																			<dd id = "account-riskProfile-globalUltimateRecord-popup" ></dd>

																			<dt>Group structure number of levels</dt>
																			<dd id = "account-riskProfile-groupStructureNumberOfLevels-popup" ></dd>

																			<dt>Headquarter details</dt>
																			<dd id = "account-riskProfile-headquarterDetails-popup" ></dd>

																			<dt>Immediateparent details</dt>
																			<dd id = "account-riskProfile-immediateParentDetails-popup" ></dd>

																			<dt>Domestic ultimate parent details</dt>
																			<dd id = "account-riskProfile-domesticUltimateParentDetails-popup" ></dd>

																			<dt>Global ultimate parent details</dt>
																			<dd id = "account-riskProfile-globalUltimateParentDetails-popup" ></dd>

																			<dt>Risk rating</dt>
																			<dd id = "account-riskProfile-riskRating-popup" ></dd>

																			<dt>Profit loss</dt>
																			<dd id = "account-riskProfile-profitLoss-popup" ></dd>

																		</dl>
																	</div>
																</div>

															</div>

														</div>

														<!-- <form>
			<input type="button" class="button--primary" value="Get new data"/>
		</form>
 -->
													</div>

												</div>
								</div>
												<!-- RISK PROFILE ENDED -->
												
													
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
	<form id="fraudRingNodeRegDetails" action="/compliance-portal/registrationDetails" method="POST" target="_blank">
			<input type="hidden" id="nodeContactId" value="" name="contactId"/>
			<!-- <input type="hidden" id="searchSortCriteria" value="" name="searchCriteria"/> -->
			<input type="hidden" id="nodeCustType" value="" name="custType"/>
	</form>
	<input type= "hidden" id="fraudRingTreeData" value="">
											
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>

	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/regDetails.js"></script>
	<script type="text/javascript" src="resources/js/regCfxDetails.js"></script>
	<script type="text/javascript" src="resources/js/jsontotable.js"></script>
	<script type="text/javascript" src="resources/js/fraudring.js"></script>
	<script type="text/javascript" src="resources/js/neo4j/neo4jd3.js"></script>
	<script type="text/javascript" src="resources/js/amcharts/cd_amcharts_min.js"></script>
	<script type="text/javascript" src="resources/js/neo4j/d3v4.min.js"></script>

</body>

</html>