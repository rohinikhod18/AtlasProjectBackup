<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

<html lang="en">

	<head>
		<meta charset="utf-8"/>
		<meta name="description" content="Atlas"/>
		<meta name="copyright" content="Currencies Direct"/>
		
		<link rel="stylesheet" href="resources/css/jquery-ui.css">
		<link rel="stylesheet" href="resources/css/popup.css">
			</head>

	<body>


<div id="modal-global-just-a-moment" class="modal--hidden modal--small center">
<h2>Just a moment...</h2>
<img class="ajax-loader space-after space-before" src="resources/img/ajax-loader.svg" width="50" height="50" alt="Please wait...">
</div>
<div id="master-grid" class="grid">

	<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<h1>
						Registration queue

						<span class="breadcrumbs">

	<span class="breadcrumbs__crumb--in">in</span>
	<span class="breadcrumbs__crumb--area">Compliance</span>
</span>					</h1>

					<div id="main-content__body">

						<div class="grid">

							<div class="grid-annex-main  grid__col--9 grid__col--12">
							<div id = "loadingobject" class= "ajax-loader-div">
									<h1><center>Loading</center></h1><br>
									<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid" class="uil-ring">
									 	<rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect>
									 		<circle cx="50" cy="50" r="42.5" stroke-dasharray="173.57299411083608 93.46238144429634" stroke="#2b76b6" fill="none" stroke-width="15">
									  			<animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s">
												</animateTransform>
											</circle>
									</svg>
							  	</div>
                            <form id="regQueueForm" action="/compliance-portal/registrationDetails" method="POST" >
								<table id="table-registration-queue" class="micro table-fix space-after">

	<thead>
		<tr>
			<th  class="tight-cell" id="col_TradeAccountNumber"><a href="#">Client number</a></th>
			<th class="sorted desc" id="col_RegisteredOn"><a href="#" onclick="sortByField('RegisteredOn')">Date <i></i></a></th>
			<th id="col_ContactName"><a href="#">Client name</a></th>
			<th id="col_Type"><a href="#">Type</a></th>
			<th id="col_CountryOfResidence" class = "breakwordHeader"><a href="#">Country of Residence</a></th>
			<th id="col_Organization"><a href="#">Organization</a></th>
			<th id="col_LegalEntity"><a href="#">Legal Entity</a></th>
			<th id="col_NewOrUpdated"><a href="#">N/U</a></th>
			<th id="col_RegistrationDate"><a href="#">Registration Date</a></th>
			<th id="col_TransactionValue"><a href="#">Transaction value</a></th>
			<th class="center" id="col_KYCStatus" data-ot="EID">E</th>
			<th class="center" id="col_FraugsterStatus" data-ot="FraudPredict">F</th>
			<th class="center" id="col_SanctionStatus" data-ot="Sanction">S</th>
			<th class="center" id="col_BlacklistStatus"  data-ot="Blacklist">B</th>
			<th class="center" id="col_CustomCheckStatus"  data-ot="CustomCheck">C</th>
		</tr>
	</thead>

	<tbody id="regQueueBody">

										<c:forEach var="listValue"
											items="${registrationQueueDto.registrationQueue}"  varStatus="loop">
											<c:set var="regDetailsUrl" 
												value="/compliance-portal/registrationDetails?contactId=${listValue.contactId}" />
												<c:choose>
												 	<c:when test="${listValue.locked  && listValue.lockedBy==registrationQueueDto.user.name}">
												 		<tr class="owned talign" data-ot="You own(s) this record">
												 	</c:when>
												 	<c:when test="${listValue.locked }">
												 		<tr class="talign unavailable" data-ot="${listValue.lockedBy } own(s) this record">
												 	</c:when>
												 	<c:otherwise>
												 		<tr class="available talign ">
												 	</c:otherwise>
												</c:choose>
												<%-- <c:choose>
												 	<c:when test="${listValue.type eq 'PFX'}">
												 	<c:set var="contactIdORaccountId" value="${listValue.contactId}"  />
												 	</c:when>
												 	<c:when test="${listValue.type eq 'CFX'}">
												 	<c:set var="contactIdORaccountId" value="${listValue.contactId}"  />
												 	</c:when>
												</c:choose> --%>
												
												
											    <td hidden="hidden"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#"  >${loop.index + 1}</a></td>
												<td class="number"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#"  >${listValue.tradeAccountNum}</a></td>
												<td class="nowrap"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)"
													href="#"  >${listValue.registeredOn}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.contactName}</a></td>
												<%-- <td><a href="compliance-registration-item.jsp">${listValue.organisation}</a></td> --%>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.type}</a></td>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.countryOfResidence}</a></td>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.organisation}</a></td>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.legalEntity}</a></td>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.newOrUpdated}</a></td>
												<td style="text-align: center"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.registeredDate}</a></td>
												<td class="number"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.transactionValue}</a></td>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.eidCheck,'PASS' )}">
														<td class="yes-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)"
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.eidCheck,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)"
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'PASS' )}">
														<td class="yes-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.sanction,'PASS' )}">
														<td class="yes-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.sanction,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'PASS' )}">
														<td class="yes-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
													<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'PASS' )}">
														<td class="yes-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												
												
												
											<!-- </tr> -->
										</c:forEach>



									</tbody>
									

</table>
<input type="hidden" id="contactId" value="" name="contactId"/>
<input type="hidden" id="searchCriteria" value="" name="searchCriteria"/>
<input type="hidden" id="custType" value="" name="custType"/>
<input type="hidden" id="source" value="queue" name="source"/>
</form>
<p id="pageCountDetails">
									Showing
									<c:choose>
										<c:when test="${registrationQueueDto.page.totalRecords ge registrationQueueDto.page.pageSize}">
											<strong id="queueMinRecord">${registrationQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${registrationQueueDto.page.maxRecord}</strong>
										</c:when>
										<c:otherwise>
										     <strong id="queueMinRecord">${registrationQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${registrationQueueDto.page.maxRecord}</strong>
										</c:otherwise>
									</c:choose>
									of <strong id="queueTotalRecords">${registrationQueueDto.page.totalRecords}</strong>
									records
								</p>

									<ul class="horizontal containing pagination" id="paginationBlock">
									<c:if test="${registrationQueueDto.page.totalPages gt 1 }">
										<li onclick="getSelectedPage(1)" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
											title="First page"><i class="material-icons">first_page</i></a>
										</li>
										<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
											title="Previous page"><i class="material-icons">navigate_before</i></a>
										</li>
										<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
											title="Next page"><i class="material-icons">navigate_next</i></a></li>
										<li onClick="getSelectedPage(${registrationQueueDto.page.totalPages })" class="pagination__jump" value="${registrationQueueDto.page.totalPages }"><a id="lastPage" href="#"
											title="Last page"><i class="material-icons">last_page</i></a></li>
									<%-- 	<c:forEach var="i" begin="1"
											end="${registrationQueueDto.page.totalPages }">
											<c:choose>
												<c:when test="${i eq 1}">
													<li id="page${i }" onClick="getSelectedPage(${ i })" class="pagination__page--on" value="${ i }"><a href="#"
														title="Page ${i }">${i }</a></li>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${i ge 11 && registrationQueueDto.page.totalPages ne i}">
															<li id="page${i }" onClick="getSelectedPage(${ i })" class="pagination__page pagination__page--hidden" value="${ i }"><a href="#"
																title="Page ${i }">${i }</a></li>
														</c:when>
														<c:otherwise>
														     <li id="page${i }" onClick="getSelectedPage(${ i })" class="pagination__page" value="${ i }"><a href="#"
																title="Page ${i }">${i }</a></li>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:forEach> --%>
									</c:if>
									</ul>
									<input type="hidden" id="queueTotalPages" value="${registrationQueueDto.page.totalPages }"></input>
								

								<span class="annex-tab annex-trigger">
									<i class="material-icons">filter_list</i>
								</span>

							</div>

							<div class="grid-annex-side grid__col--3 hidden">

								<div class="annex annex--hidden">

									<h2>
										Filter queue
										<span class="f-right annex-trigger"><i class="material-icons">close</i></span>
									</h2>

									<div class="pagepanel__content boxpanel--shadow--splits">

										<form class="form" id="queueFilterForm" autocomplete=off>
											
											<div id="addedFilters">
											<!-- <section class="section--results">

	<h3>3 filters applied</h3>
	<ul class="containing space-after delete-list">
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Organisations</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Client type</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Blacklist</li>
	</ul>
	<a href="#" class="button--primary button--small" onclick="clearAndApplyFilter()">Clear all</a>
	<img id="ajax-loader-clear-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/>
	<span class="section--results-arrow"></span>

</section> -->
</div>
											<section>
											
											<h3>Enter search criteria</h3>
											
											<fieldset>
													<!-- Save Search -->
													<ul class="form__fields">
														<li class="form__field-wrap filterInputWrap">
															<p class="label filterInputHeader">Saved Reports</p>
															<div id="singlelist-saved-filters" class="singlelist clickpanel--right">
																<p id="rad_save_selected" class="singlelist__chosen clickpanel__trigger">Please select</p>
																<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>
																<div class="clickpanel__content--hidden">
																	<fieldset>
																		<ul class="singlelist__options">
			              													<c:forEach var="savedSearch" items="${registrationQueueDto.savedSearch.savedSearchData}" varStatus="loop">
																				<li>
																					<label id="label-rad-saved-search-${loop.index}" for="rad-saved-search-${loop.index}">
																					    <input name="savedSearch[]" class="filterInput" id="rad-saved-search-${loop.index}"
																					     type="radio" value='${savedSearch.saveSearchFilter}' onclick="applySelectedSavedSearch('rad-saved-search-${loop.index}')" />${savedSearch.saveSearchName}
																					</label>
																				</li>
																				<input type="hidden" id="saveSearchName-${loop.index}" value='${savedSearch.saveSearchName}' />
																				
																				<!-- Added for AT-3763 -->
																				<input type="hidden" id="saveSearchIndex-${loop.index}" value='${loop.index}' />
																				
																			  </c:forEach>
																		</ul>
																	</fieldset>
																	<span class="clickpanel__arrow"></span>
																</div>
															</div>
														</li>
													</ul>
												</fieldset>
											

												<fieldset>

													<ul class="form__fields">

														<li class="form__field-wrap filterInputWrap"><label
															for="txt-search-keyword" class="label filterInputHeader">Keyword</label>
															<input id="txt-search-keyword" type="text" name="keyword"
															placeholder="Client number, name etc..."
															data-ot="For Country Of Residence, type cor:United Kingdom 
																	<br/>For acsfid, type acsfid:235748
																	<br/>For Address, type addr:Stavanger
																	<br/>For Occupation, type ocp:Pilot	"											
																	></li>
													</ul>
												</fieldset>
												
												<fieldset>

													<ul class="form__fields">

														<li class="form__field-wrap filterInputWrap"><label
															for="txt-search-deviceId" class="label filterInputHeader">Device ID</label>
															<input id="txt-search-deviceId" type="text" name="deviceId"></li>
													</ul>
												</fieldset>

												<h3>Select filters</h3>

												<fieldset>

													<ul class="form__fields">

	<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader" >Organisations</p>

		<div id="multilist-organisations" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			<ul class="multilist__options">
				
              <c:forEach var="organization" items="${registrationQueueDto.organization}"  varStatus="loop">
				
				<li>
				<label  for="chk-org-cduk-${loop.index}">
					    <input name="organization[]" class="filterInput" id="chk-org-cduk-${loop.index}"
					     type="checkbox" value="${organization}" />${organization}	
				</label>
				</li>
							
			  </c:forEach>
		
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
	</li>
	
	<!-- Legal entity changes start -->
	
	<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader" >Legal Entity</p>

		<div id="multilist-legal-entity" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			<ul class="multilist__options">
				
              <c:forEach var="legalEntity" items="${registrationQueueDto.legalEntity}"  varStatus="loop">
				
				<li>
				<label  for="chk-legalEntity-cd-${loop.index}">
					    <input name="legalEntity[]" class="filterInput" id="chk-legalEntity-cd-${loop.index}"
					     type="checkbox" value="${legalEntity}" />${legalEntity}	
				</label>
				</li>
							
			  </c:forEach>
		
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
	</li>
	
	
	<!-- Legal entity changes end -->

	<li class="form__field-wrap">
	
		<div class="grid">

		<fieldset class="filterInputWrap">
		<legend class="label filterInputHeader">Date Filter</legend>
		
			<div class="pill-choice--small">
			<div class="grid__row">
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="todayFilter">
					<input id="todayFilter" name="dateFilterType[]" type="radio" value="Today" class="filterInput" onclick="setTodayDateValue()" />
					Today			</label>
				</div>
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="yesterdayFilter">
					<input id="yesterdayFilter" name="dateFilterType[]" type="radio" value="Yesterday" class="filterInput" onclick="setYesterdayDateValue()" />
					Yesterday			</label>
				</div>
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="thisWeekFilter">
					<input id="thisWeekFilter" name="dateFilterType[]" type="radio" value="ThisWeek" class="filterInput" onclick="setThisWeekDateValue()" />
					This Week			</label>
				</div>
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="thisMonthFilter">
					<input id="thisMonthFilter" name="dateFilterType[]" type="radio" value="ThisMonth" class="filterInput" onclick="setThisMonthDateValue()" />
					This Month			</label>
				</div>
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="thisYearFilter">
					<input id="thisYearFilter" name="dateFilterType[]" type="radio" value="ThisYear" class="filterInput" onclick="setThisYearDateValue()" />
					This Year			</label>
				</div>
				<div class="grid__col--6">
					<label style = "padding:0.3125rem" class="clientTypeChoice" for="customFilter">
					<input id="customFilter" name="dateFilterType[]" type="radio" value="Custom" class="filterInput" onclick="setCustomDateValue()" />
					Custom			</label>
				</div>
			</div>
			</div>
			</fieldset>
			<div class="grid__row">
				<div class="grid__col--6 filterInputWrap">
					<label for="date-registered-from" class="filterInputHeader">Date from</label>
					<input id="date-registered-from" name="dateFrom" class="datepicker filterInput" type="text" value="${registrationQueueDto.dateFrom}" placeholder="dd/mm/yyyy" />
				</div>
				<div class="grid__col--6 filterInputWrap">
					<label for="date-registered-to" class="filterInputHeader">Date to</label>
					<input id="date-registered-to" name="dateTo" class="datepicker filterInput" type="text"  value="${registrationQueueDto.dateTo}" placeholder="dd/mm/yyyy"/>
				</div>
			</div>
		</div>
	</li>

	<li class="form__field-wrap filterInputWrap">
		<fieldset>

		<legend class="label filterInputHeader" >Client type</legend>
	
	<ul id="pillchoice-client-type" class="pill-choice--small">
				<li>
			<label style = "padding:0.3125rem" class="clientTypeChoice pfx-field" for="rad-type-pfx">
				<input id="rad-type-pfx" name="custType[]" type="radio" value="PFX" class="filterInput"  />
				PFX			</label>
		</li>
				<li>
			<label style = "padding:0.3125rem" class="clientTypeChoice cfx-field"  for="rad-type-cfx">
				<input id="rad-type-cfx" name="custType[]" type="radio" value="CFX" class="filterInput"  />
				CFX			</label>
		</li>
				<li>
			<label style = "padding:0.3125rem" class="clientTypeChoice cfx-field" for="rad-type-cfx-etailer">
				<input id="rad-type-cfx-etailer" name="custType[]" type="radio" value="CFX (etailer)" class="filterInput"   />
				CFX (etailer)			</label>
		</li>
			</ul>

</fieldset>	</li>

	<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader" >Transaction value</p>

					<div id="multilist-transaction-value"
						class="multilist clickpanel--right">

						<ul class="multilist__chosen">
							<li class="clickpanel__trigger">Please select</li>
						</ul>

						<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

						<div class="clickpanel__content--hidden">

							<fieldset>

								<ul class="multilist__options">
									
									<c:forEach var="transactionValue" items="${registrationQueueDto.transValue}"  varStatus="loop">
									
									<li>
									<label  for="chk-transaction-value-${loop.index}">
										    <input name="transValue[]" class="filterInput" id="chk-transaction-value-${loop.index}"
										     type="checkbox" value="${transactionValue}" />${transactionValue}	
									</label>
									</li>
												
								  </c:forEach>

								</ul>

							</fieldset>

							<span class="clickpanel__arrow"></span>

						</div>

					</div>
	</li>
	
	<li class="form__field-wrap filterInputWrap">
		<fieldset>

		<legend class="label filterInputHeader">New or Updated</legend>
	
	<ul id="pillchoice-new-or-updated" class="pill-choice--small">
				<li>
			<label style = "padding:0.3125rem" class="clientTypeChoice" for="newRecord">
				<input id="newRecord" name="newOrUpdatedRecord[]" type="radio" value="New" class="filterInput"  />
				New			</label>
		</li>
		<li>
			<label style = "padding:0.3125rem" class="clientTypeChoice" for="updatedRecord">
				<input id="updatedRecord" name="newOrUpdatedRecord[]" type="radio" value="Updated" class="filterInput"  />
				Updated			</label>
		</li>
				
				
			</ul>

</fieldset>	</li>
<fieldset>
															<ul class="form__fields">

	<li class="form__field-wrap filterInputWrap ">
															
							<p class="label filterInputHeader">Owner</p>
							
							
							

							<div id="singlelist-owner" class="singlelist clickpanel--right">
							

	<p class="singlelist__chosen clickpanel__trigger">Please select</p>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

						<input id="filterSearchListOwnerListId" class="singlelist__search space-after" type="search" placeholder="Search list">
			
				<ul class="singlelist__options">

				<c:forEach var="owner" items="${registrationQueueDto.owner}"  varStatus="loop">
					
				<li>
				<label  for="rad-owner-owner-${loop.index}">
					    <input name="owner[]" class="filterInput" id="rad-owner-owner-${loop.index}"
					     type="radio" value="${owner}" />${owner}	
				</label>
				</li>
							
			  </c:forEach>
			  		
			</ul>

		</fieldset>
		<span class="clickpanel__arrow"></span>

	</div>

</div>
						</li>
						</ul>
						</fieldset>
						
<fieldset>
															<ul class="form__fields">

	<li class="form__field-wrap filterInputWrap ">
															
							<p class="label filterInputHeader">Onfido Status</p>
							
							
							

							<div id="singlelist-onfidoStatus" class="singlelist clickpanel--right">
							

	<p class="singlelist__chosen clickpanel__trigger">Please select</p>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

						<input id="filterSearchListOwnerListId" class="singlelist__search space-after" type="search" placeholder="Search list">
			
				<ul class="singlelist__options">

				<c:forEach var="onfidoStatus" items="${registrationQueueDto.onfidoStatus}"  varStatus="loop">
					
				<li>
				<label  for="rad-onfido-status-${loop.index}">
					    <input name="onfidoStatus[]" class="filterInput" id="rad-onfido-status-${loop.index}"
					     type="radio" value="${onfidoStatus}" />${onfidoStatus}	
				</label>
				</li>
							
			  </c:forEach>
			  		
			</ul>

		</fieldset>
		<span class="clickpanel__arrow"></span>

	</div>

</div>
						</li>
						</ul>
						</fieldset>						



	<li id="reg-service__status-filter" class="form__field-wrap">

		<div class="grid--micro">

			<div class="grid__row">

				<div class="grid__col--6 grid__col--pad-bottom eid-field">
					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">EID</legend>
	
						<ul id="pillchoice-eid" class="pill-choice eid-field">
							<li><label class="pill-choice__choice--positive"
								for="rad-eid-passed"> <input id="rad-eid-passed" name="kycStatus[]" value="PASS" class="filterInput"
									type="radio" /> <i class="material-icons">check</i>
							</label></li>
							<li><label class="pill-choice__choice--negative"
								for="rad-eid-failed"> <input id="rad-eid-failed" name="kycStatus[]" value="FAIL" class="filterInput"
									type="radio" /> <i class="material-icons">close</i>
							</label></li>
						</ul>

</fieldset>				</div>

				<div class="grid__col--6 grid__col--pad-bottom fraud-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">FraudPredict</legend>
	
						<ul id="pillchoice-fraugster" class="pill-choice fraud-field">
							<li><label class="pill-choice__choice--positive"
								for="rad-fraugster-passed"> <input name="fraugsterStatus[]" value="PASS" class="filterInput"
									id="rad-fraugster-passed" type="radio" /> <i
									class="material-icons">check</i>
							</label></li>
							<li><label class="pill-choice__choice--negative"
								for="rad-fraugster-failed"> <input name="fraugsterStatus[]" value="FAIL" class="filterInput"
									id="rad-fraugster-failed" type="radio" /> <i
									class="material-icons">close</i>
							</label></li>
						</ul>


</fieldset>
				</div>

				<div class="grid__col--6 grid__col--pad-bottom sanction-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader ">Sanction</legend>
	
						<ul id="pillchoice-sanction" class="pill-choice sanction-field">
							<li><label class="pill-choice__choice--positive"
								for="rad-sanction-passed"> <input name="sanctionStatus[]" value="PASS" class="filterInput"
									id="rad-sanction-passed" type="radio" /> <i
									class="material-icons">check</i>
							</label></li>
							<li><label class="pill-choice__choice--negative"
								for="rad-sanction-failed"> <input name="sanctionStatus[]" value="FAIL" class="filterInput"
									id="rad-sanction-failed" type="radio" /> <i
									class="material-icons">close</i>
							</label></li>
						</ul>

</fieldset>
				</div>

				<div class="grid__col--6 grid__col--pad-bottom blackList-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">Blacklist</legend>
	
						<ul id="pillchoice-blacklist" class="pill-choice blackList-field">
							<li><label class="pill-choice__choice--positive"
								for="rad-blacklist-passed"> <input name="blacklistStatus[]" value="PASS" class="filterInput"
									id="rad-blacklist-passed" type="radio" /> <i
									class="material-icons">check</i>
							</label></li>
							<li><label class="pill-choice__choice--negative"
								for="rad-blacklist-failed"> <input name="blacklistStatus[]" value="FAIL" class="filterInput"
									id="rad-blacklist-failed" type="radio" /> <i
									class="material-icons">close</i>
							</label></li>
						</ul>

</fieldset>
				</div>
			<div class="grid__col--6 grid__col--pad-bottom custom-field">

				<fieldset class="filterInputWrap">

					<legend class="label filterInputHeader">Custom Check</legend>
	
					<ul id="pillchoice-customCheck" class="pill-choice">
						<li>
							<label class="pill-choice__choice--positive" for="rad-customCheck-passed">
								<input name="customCheckStatus[]" id="rad-customCheck-passed"  value="PASS" type="radio" class="filterInput"   />
								<i class="material-icons">check</i>			
							</label>
						</li>
						<li>
							<label class="pill-choice__choice--negative" for="rad-customCheck-failed">
									<input name="customCheckStatus[]" id="rad-customCheck-failed"  value="FAIL" type="radio" class="filterInput"   />
									<i class="material-icons">close</i>			
							</label>
						</li>
					</ul>

				</fieldset>
			</div>

			</div>

		</div>

	</li>

</ul>
												</fieldset>

											</section>

											<section class="section--actions">
												<input id="reg_queue_Filter" type="button" class="button--primary button--small" value="GENERATE" onclick="applyRegQueueSearchCriteria()"/>
												<input type="button" id="save_search_button" class="button--primary button--small" value="Save" onclick="saveSearchPopup()"/>
												<input type="button" id="delete_search_button" class="button--primary button--small" value="Delete" onclick="deleteSaveSearchPopup()"/>
												<object id="gifloaderforqueue"  class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;"></object>
												<!-- <img id="ajax-loader-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->
											</section>
										</form>

									</div>

								</div>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

	</main>

</div>

<div id="saveSearchPopups" class="popupDiv"	title="Save Search Name">
<div style="height:100%">
<div id="errorDiv" class="message--negative" style="display: none;">
								<div class="copy">
									<p id = "errorDescription"></p>
								</div>
							</div>
							<div id="successDiv" class="message--positive" style="display: none;">
								<div class="copy">
									<p>Filters Saved Successfully</p>
								</div>
							</div>
<fieldset>

							<ul class="form__fields">

									<li class="form__field-wrap filterInputWrap">
									<input id="saveSearchName" type="text" name="Name"></li>
								</ul>
	</fieldset>
	</div>
</div>
	<div id="deleteSearchPopups" class="popupDiv" title="Delete Save Search">
		<div style="height:100%">
			<fieldset>
				<ul class="form__fields">
					<li class="form__field-wrap filterInputWrap">
						<p id="confirmSaveDelete"></p>
					</li>
				</ul>
			</fieldset>
		</div>
	</div>
	<div id="updateSearchPopup" class="popupDiv" title="Do you want to modify filter ?">
		<div style="height:100%">
			<fieldset>
				<ul class="form__fields">
					<li class="form__field-wrap filterInputWrap">
						<p id="confirmSaveUpdate"></p>
					</li>
				</ul>
			</fieldset>
		</div>
	</div>

	<div id="drawer-user" class="drawer--closed">

	<h2 class="drawer__heading">Your profile<span class="drawer__close"><i class="material-icons">close</i></span></h2>

	<ul class="split-list">
					<li>
						<i class="material-icons">settings</i> <a href="/compliance-portal/administration">Administration</a>
					</li>
			</ul>


</div>

<input type="hidden" id="custTypeForDashboardFilter" value="${registrationQueueDto.custType}" />
<input type="hidden" id="isDashboardSearchCriteria" value="${registrationQueueDto.isDashboardSearchCriteria}" />
<input type="hidden" id="searchCriteriaQueue" value='${registrationQueueDto.searchCriteria}' />
<input type="hidden" id="currentpage" value=""/>
<input type="hidden" id="pageType" value="RegistrationQueue"/>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>

	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
	<script type="text/javascript" src="resources/js/regQueue.js"></script>
	<!-- <script type="text/javascript" src="resources/js/cdmodel.js"></script> -->

	
	
	
	</body>

</html>