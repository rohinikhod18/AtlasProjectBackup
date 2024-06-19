

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

<html lang="en">

	<head>
		<meta charset="utf-8"/>
		<title>Enterprise tools</title>
		<meta name="description" content="Enterprise tools"/>
		<meta name="copyright" content="Currencies Direct"/>
			
		<link rel="stylesheet" href="resources/css/jquery-ui.css">
  		<link rel="stylesheet" href="resources/css/popup.css">

			</head>

	<body>
<!-- START: DO NOT COPY THIS DIV. FOR DEVELOPMENT ONLY
<div id="dev-notes" class="dev-notes--minimised">
	<i class="material-icons">assignment</i>
	<div class="dev-notes__content dev-notes__close">
		<h3>Development notes <i class="material-icons f-right">close</i></h3>
		<h4>Filters</h4>
		<ul>
			<li>Only show filters button in top bar on pages with filterable tables.</li>
		</ul>
	</div>
</div>
 END: DO NOT COPY THIS DIV. FOR DEVELOPMENT ONLY -->

<div id="master-grid" class="grid">

	<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<h1>
						Payment In Report
						<span class="breadcrumbs">
							<span class="breadcrumbs__crumb--in">in</span>
							<span class="breadcrumbs__crumb--area">Compliance</span>
						</span>
					</h1>

					<div id="main-content__body">
						<div class="grid-annex-main grid__col--9 grid__col--12">
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
						
						<form id="payInQueueForm" action="/compliance-portal/paymentInDetail" method="POST" >
							<table id="payInReportTableId"  class="micro table-fix space-after">
							<thead>
								<tr>
									<th id ="col_Transactions"><a href="#">Transaction #</a></th>
									<th id="col_TransactionDate" class="sorted desc default-sort-desc"> <a href="#" onclick="sortByField('TransactionDate')">Date<i></i></a></th>
									<th id ="col_tradeaccountnumber" class="sorted desc"><a href="#" onclick="sortByField('tradeaccountnumber')">Client #<i></i></a></th>
									<!-- <th><a href="#">Client #</a></th> -->
									<th id ="col_ClientName"><a href="#">Client name</a></th>									
									<th id ="col_type" class="sorted desc"><a href="#" onclick="sortByField('type')">Type<i></i></a></th>	
									<th id="col_Organization"><a href="#">Organization</a></th>	
									<th id="col_LegalEntity"><a href="#">Legal Entity</a></th>									
									<th id ="col_Sell"><a href="#">Sell</a></th>									
									<th id ="col_Amount"><a href="#">Amount</a></th>									
									<th id ="col_Method"><a href="#">Method</a></th>
									<!-- <th><a href="#">Method</a></th> -->
									<th id ="col_Country"><a href="#">Country</a></th>
									<th id ="col_OverAllStatus"><a href="#">Overall status</a></th>
									<th class="center" data-ot="Watchlist">W</th>
									<th class="center" data-ot="FraudPredict">F</th>
									<th class="center" data-ot="Sanction">S</th>
									<th class="center"  data-ot="Blacklist">B</th>
									<th class="center"  data-ot="Custom Check">C</th>
									<th class="center"  data-ot="FS Status">FS</th>
									<th class="center"  data-ot="Intuition Status">I</th> <!-- AT-4607 -->
								</tr>
							</thead>
							<tbody id="payInQueueBody">
							
							<c:forEach var="listValue"
											items="${paymentInQueueDto.paymentInQueue}"  varStatus="loop">
											<%-- <c:set var="regDetailsUrl"
												value="/compliance-portal/registrationDetails?contactId=${listValue.contactId}" /> --%>
												<c:choose>
												 	<c:when test="${listValue.locked  && listValue.lockedBy==paymentInQueueDto.user.name}">
												 		<tr class="owned talign" data-ot="You own(s) this record">
												 	</c:when>
												 	<c:when test="${listValue.locked }">
												 		<tr class="talign unavailable" data-ot="${listValue.lockedBy } own(s) this record">
												 	</c:when>
												 	<c:otherwise>
												 		<tr class="available talign">
												 	</c:otherwise>
												</c:choose>
											    <td hidden="hidden"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#"  >${loop.index + 1}</a></td>
												<td class="nowrap number"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#"  >${listValue.transactionId}</a></td>
												<td class="nowrap"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)"
													href="#"  >${listValue.date}</a></td>
												<td class="number"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.clientId}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.contactName}</a></td>
												<%-- <td><a href="compliance-registration-item.jsp">${listValue.organisation}</a></td> --%>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.type}</a></td>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.organization}</a></td>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.legalEntity}</a></td>
												<%-- <td><a onClick="getRegistrationQueueDetails(${listValue.contactId},this)" href="#" >${listValue.buyCurrency}</a></td> --%>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.sellCurrency}</a></td>
												<td class="number"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},this)" href="#" >${listValue.amount}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.method}</a></td>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.countryFullName}</a></td>
												<td><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" href="#" >${listValue.overallStatus}</a></td>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)"
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)"
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'WATCH_LIST' )}">
														<td class="amber-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)"
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.sanction,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.sanction,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.riskStatus,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.riskStatus,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<!-- Added intuition check status column , AT-4607 -->
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.intuitionStatus,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.intuitionStatus,'NOT_REQUIRED') ||  listValue.intuitionStatus == null}">
														<td class="na-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentInQueueDetails(${listValue.paymentInId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
							
								<!-- 								<tr>
									<td><a href="/compliance-portal/PaymentInDetails">55879301834</a></td>
									<td><a href="compliance-payments-in-item.html">33385749384</a></td>
									<td class="nowrap"><a href="compliance-payments-in-item.html">22/08/2016 12:45:09</a></td>
									<td class="nowrap"><a href="compliance-payments-in-item.html">Andrew Murray</a></td>
									<td><a href="compliance-payments-in-item.html">PFX</a></td>
									<td><a href="compliance-payments-in-item.html">GBP</a></td>
									<td class="number"><a href="compliance-payments-in-item.html">&pound;12,525</a></td>
									<td class="nowrap"><a href="compliance-payments-in-item.html">Direct Debit</a></td>
									<td><a href="compliance-payments-in-item.html">USA</a></td>
									<td class="neutral-cell nowrap"><a href="compliance-payments-in-item.html">On hold - Awaiting more info</a></td>
									<td class="no-cell"><a href="compliance-payments-in-item.html"><i class="material-icons">clear</i></a></td>
									<td class="yes-cell"><a href="compliance-payments-in-item.html"><i class="material-icons">check</i></a></td>
									<td class="yes-cell"><a href="compliance-payments-in-item.html"><i class="material-icons">check</i></a></td>
									<td class="no-cell"><a href="compliance-payments-in-item.html"><i class="material-icons">clear</i></a></td>
								</tr>
																																<tr>
									
 -->						</tbody>
						</table>
						<input type="hidden" id="paymentInId" value="" name="paymentInId"/>
							<input type="hidden" id="searchCriteria" value="" name="searchCriteria"/>
							<input type="hidden" id="custType" value="" name="custType"/>
							<input type="hidden" id="source" value="report" name="source"/>
							
						</form>
					<p id="pageCountDetails" style="display: none;"> <!-- style="display: none;" -->Showing
					<c:choose>
										<c:when test="${paymentInQueueDto.page.totalRecords ge paymentInQueueDto.page.pageSize}">
											<strong id="queueMinRecord">${paymentInQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${paymentInQueueDto.page.maxRecord}</strong>
										</c:when>
										<c:otherwise>
										     <strong id="queueMinRecord">${paymentInQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${paymentInQueueDto.page.maxRecord}</strong>
										</c:otherwise>
									</c:choose>
									of <strong id="queueTotalRecords">${paymentInQueueDto.page.totalRecords}</strong>
									records
								</p>
								
								<ul class="horizontal containing pagination" id="paginationBlock" style="display: none;">
									<c:if test="${paymentInQueueDto.page.totalPages gt 1 }">
										<li onclick="getSelectedPage(1)" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
											title="First page"><i class="material-icons">first_page</i></a>
										</li>
										<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
											title="Previous page"><i class="material-icons">navigate_before</i></a>
										</li>
										<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
											title="Next page"><i class="material-icons">navigate_next</i></a></li>
										<li  onClick="getSelectedPage(${paymentInQueueDto.page.totalPages })" class="pagination__jump" value="${paymentInQueueDto.page.totalPages }"><a id="lastPage" href="#"
											title="Last page"><i class="material-icons">last_page</i></a></li>
										<%-- <c:forEach var="i" begin="1"
											end="${paymentInQueueDto.page.totalPages }">
											<c:choose>
												<c:when test="${i eq 1}">
													<li id="page${i }" onClick="getSelectedPage(${ i })" class="pagination__page--on" value="${ i }"><a href="#"
														title="Page ${i }">${i }</a></li>
												</c:when>
												<c:otherwise>
												
													<c:choose>
														<c:when test="${i ge 11 && paymentInQueueDto.page.totalPages ne i}">
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
									<input type="hidden" id="queueTotalPages" value="${paymentInQueueDto.page.totalPages }"></input>
						<!-- <p>Showing <strong>1 - 50</strong> of <strong>234</strong> records</p>
<ul class="horizontal containing pagination">
	<li class="pagination__jump--disabled">
		<a href="#" title="First page"><i class="material-icons">first_page</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Previous page"><i class="material-icons">navigate_before</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Next page"><i class="material-icons">navigate_next</i></a>
	</li>
	<li class="pagination__jump">
		<a href="#" title="Last page"><i class="material-icons">last_page</i></a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 1">1</a>
	</li>
	<li class="pagination__page--on">
		<a href="#" title="Page 2">2</a>
	</li>
</ul> -->
								<span class="annex-tab annex-trigger">
									<i class="material-icons">filter_list</i>
								</span>
						
						</div>

				<form class="frm-table-filters" id="queueFilterForm">
							

							<div class="grid-annex-side grid__col--3 hidden">

								<div class="annex annex--hidden">

									<h2>
										Generate report
										<span class="f-right annex-trigger"><i class="material-icons">close</i></span>
									</h2>
									

									<div class="pagepanel__content boxpanel--shadow--splits">
									<div id="addedFilters"> 
										</div>
									
										
										
<section>

				

					
				<ul class="button-group">
					<li>
						<a href="#" class="button--primary button--small" onclick="clearAndApplyFilter()">Clear</a>
					</li>
					<li>
				
						<div class="hoverpanel--right hoverpanel--small">

							Download
							<i class="material-icons">keyboard_arrow_down</i>
							
							<div class="hoverpanel__content--small">
								<ul class="block-list">
									<li>
										<!-- <a href="#"  class="icon-link--msexcel" onclick="searchPayInReportDownLoadSearchCriteria()" >Microsoft Excel</a> -->
									<a href="#" class="icon-link--msexcel"  onclick="checkTotalRecordsAndDownload()" >Microsoft Excel</a>
									</li>									
								</ul>
								<span class="hoverpanel__arrow"></span>
							</div>
							
						
						</div>

					</li>
					<li>
					<!-- 	<img id="ajax-loader-clear-search" class="ajax-loader " src="/img/ajax-loader.svg" width="20" height="20" alt="Loading...">	 -->				</li>

				</ul>

				<span class="section--results-arrow"></span>

			</section>
					
											<!-- <h2 class="hidden">Enter search criteria</h2>

											<section class="section--results"> -->

	<!-- <h3>3 filters applied</h3> -->
	<!-- <ul class="containing space-after delete-list">
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Organisations</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Client type</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Blacklist</li>
	</ul>
	<a href="#" class="button--primary button--small">Clear all</a>
	<img id="ajax-loader-clear-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/>
	<span class="section--results-arrow"></span>

</section> -->
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
			              													<c:forEach var="savedSearch" items="${paymentInQueueDto.savedSearch.savedSearchData}" varStatus="loop">
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




						<li class="form__field-wrap filterInputWrap">
							<label for="txt-search-keyword" class="label filterInputHeader">Keyword</label>
							<input id="txt-search-keyword" type="text" name="keyword" placeholder="Client number, name etc..." 
							data-ot="For Contract Number, type contractNo:3111I264697-003
									<br/>For PaymentMethod, type  pm:Switch/Debit
									<br/>For Amount, type amt:2300
									<br/>For Occupation, type ocp:Pilot	"										
						></li>
						
						
						
						<li class="form__field-wrap filterInputWrap">

							<p class="label filterInputHeader">Status</p>

							<div id="multilist-status" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Please select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			
			<ul class="multilist__options">
					
					 <c:forEach var="paymentStatus" items="${paymentInQueueDto.paymentStatus}"  varStatus="loop">
				
				<li>
				<label  for="chk-status-${loop.index}">
					    <input name="paymentStatus[]" class="filterInput" id="chk-status-${loop.index}"
					     type="checkbox" value="${paymentStatus}" />${paymentStatus}	
				</label>
				</li>
							
			  </c:forEach>

					
							<!-- 	<li>
					<label for="chk-status-cleared">
						<input id="chk-status-cleared" type="checkbox">
						Cleared					</label>
				</li>
								<li>
					<label for="chk-status-unwatched">
						<input id="chk-status-unwatched" type="checkbox">
						Unwatched					</label>
				</li>
								<li>
					<label for="chk-status-rejected">
						<input id="chk-status-rejected" type="checkbox">
						Rejected					</label>
				</li>
								<li>
					<label for="chk-status-siezed">
						<input id="chk-status-siezed" type="checkbox">
						Siezed					</label>
				</li>
								<li>
					<label for="chk-status-neutral">
						<input id="chk-status-neutral" type="checkbox">
						Neutral					</label>
				</li> -->
				
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
						</li>
						
						
						
						
						
						
						
						
						


<%-- <li class="form__field-wrap filterInputWrap">

							<p class="label filterInputHeader">Watchlists</p>

							<div id="multilist-add-to-watchlists" class="multilist clickpanel--right" >

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Please select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			<input class="multilist__search space-after" type="search" placeholder="Search list"/>
			
			<ul class="multilist__options">


					<c:forEach var="watchListReason" items="${paymentOutQueueDto.watchListReason}"  varStatus="loop">
				
					<li>
						<label  for="chk-watchlist-${loop.index}">
					    	<input name="watchListReason[]" class="filterInput" id="chk-watchlist-${loop.index}"
					    		 type="checkbox" value="${watchListReason}" />${watchListReason}	
						</label>
					</li>
							
			      </c:forEach>
							<!-- 	<li>
					<label for="chk-watchlist-1">
						<input id="chk-watchlist-1"  name="watchListReason[]" class="filterInput"  value="PEP" type="checkbox" />
						PEP					</label>
				</li>
								<li>
					<label for="chk-watchlist-2">
						<input id="chk-watchlist-2" name="watchListReason[]" class="filterInput" value="High risk industry" type="checkbox" />
						High risk industry	</label>
				</li>
								<li>
					<label for="chk-watchlist-3">
						<input id="chk-watchlist-3" name="watchListReason[]" class="filterInput" type="checkbox" />
						Third watchlist					</label>
				</li>
								<li>
					<label for="chk-watchlist-4">
						<input id="chk-watchlist-4" name="watchListReason[]" class="filterInput" type="checkbox" />
						Fourth watchlist					</label>
				</li>
								<li>
					<label for="chk-watchlist-5">
						<input id="chk-watchlist-5" name="watchListReason[]" class="filterInput" type="checkbox" />
						Fifth watchlist					</label>
				</li> -->
				
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
						</li>
 --%>
<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader">Watchlists</p>

		<div id="multilist-add-to-watchlists" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListWatchListId"  class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

				 <c:forEach var="watchListReason" items="${paymentInQueueDto.watchList.watchlistData}"  varStatus="loop">
				
				<li>
				<label  for="chk-watchList-${loop.index}">
					    <input name="watchListReason[]" class="filterInput" id="chk-watchList-${loop.index}"
					     type="checkbox" value="${watchListReason.id}" />${watchListReason.name}	
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

<p class="label filterInputHeader">Owner <!-- <span class="label__support">(who's working on it)</span> --></p>
		<div id="singlelist-owner" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListOwnerId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

				 <c:forEach var="owner" items="${paymentInQueueDto.owner}"  varStatus="loop">
				
				<li>
				<label  for="chk-owner-${loop.index}">
					    <input name="owner[]" class="filterInput" id="chk-owner-${loop.index}"
					     type="checkbox" value="${owner}" />${owner}	
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

		<p class="label filterInputHeader">Organisations</p>

		<div id="multilist-organisations" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListOrgId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

				 <c:forEach var="organization" items="${paymentInQueueDto.organization}"  varStatus="loop">
				
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
	
	<!-- legal entity changes start -->
		<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader">Legal Entity</p>

		<div id="multilist-legalEntity" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListLegalEntityId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

				 <c:forEach var="legalEntity" items="${paymentInQueueDto.legalEntity}"  varStatus="loop">
				
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
	<!-- legal entity changes end -->

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
					<label for="date-payment-from" class="filterInputHeader">Date from</label>
					<input name="dateFrom" id="date-payment-from"  class="datepicker filterInput" type="text" value="${paymentInQueueDto.dateFrom}" placeholder="dd/mm/yyyy"/>
				</div>
				<div class="grid__col--6 filterInputWrap">
					<label for="date-payment-to" class="filterInputHeader">Date to</label>
					<input name="dateTo" id="date-payment-to"  class="datepicker filterInput" type="text"  value="${paymentInQueueDto.dateTo}" placeholder="dd/mm/yyyy"/>
				</div>
			</div>
		</div>
	</li>

	<li class="form__field-wrap filterInputWrap">
		<fieldset>

		<legend class="label filterInputHeader">Client type</legend>
	
	<ul id="pillchoice-client-type" class="pill-choice--small">
				<li>
			<label class="pill-choice__choice pfx-field" for="rad-type-pfx">
				<input name="custType[]" id="rad-type-pfx" type="checkbox" value="PFX" class="filterInput" />
				PFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx">
				<input name="custType[]" id="rad-type-cfx" type="checkbox" value="CFX" class="filterInput" />
				CFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx-etailer">
				<input name="custType[]" id="rad-type-cfx-etailer" type="checkbox" value="CFX (etailer)" class="filterInput" />
				CFX (etailer)			</label>
		</li>
			</ul>

</fieldset>	</li>

	<li class="form__field-wrap filterInputWrap">
	
		<p class="label filterInputHeader">Sell currency</p>

		<div id="multilist-sell-currency" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListSellCurrencyListId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

								<c:forEach var="currency" items="${paymentInQueueDto.currency}"  varStatus="loop">
				
									<li>
									<label  for="chk-sell-currency-${loop.index}">
										    <input name="sellCurrency[]" class="filterInput" id="chk-sell-currency-${loop.index}"
										     type="checkbox" value="${currency}" />${currency}	
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

		<p class="label filterInputHeader">Country of fund</p>

		<div id="multilist-country-of-fund" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Please select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListCountryOfFundListId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

								<c:forEach var="country" items="${paymentInQueueDto.country}" varStatus="loop">
				
					<li>
						<label  for="chk-country-${loop.index}">
					    	<input name="countryofFund[]" class="filterInput" id="chk-country-${loop.index}"
					    		 type="checkbox" value="${country}" />${country}	
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

		<p class="label filterInputHeader">Payment Method </p>

		<div id="multilist-payment-method" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Please select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListPaymentMethodId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

								<c:forEach var="paymentMethod" items="${paymentInQueueDto.paymentMethod}" varStatus="loop">
				
					<li>
						<label  for="chk-paymentMethod-${loop.index}">
					    	<input name="paymentMethod[]" class="filterInput" id="chk-paymentMethod-${loop.index}"
					    		 type="checkbox" value="${paymentMethod}" />${paymentMethod}	
						</label>
					</li>
							
			  	</c:forEach>
				
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
	</li>
	

	<li id="pay-in-service__status-filter" class="form__field-wrap">

		<div class="grid--micro">

			<div class="grid__row">

				<div class="grid__col--6 grid__col--pad-bottom watchlist-field">
					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">Watchlist</legend>
	
	<ul id="pillchoice-watchlist" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-watchlist-passed">
				<input id="rad-watchlist-passed" name="watchListStatus[]" type="radio" value="PASS" class="filterInput" />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-watchlist-failed">
				<input id="rad-watchlist-failed" name="watchListStatus[]" type="radio" value="FAIL" class="filterInput" />
				<i class="material-icons">close</i>			</label>
		</li>
			</ul>

</fieldset>				</div>

				<div class="grid__col--6 grid__col--pad-bottom fraud-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">FraudPredict</legend>
	
	<ul id="pillchoice-fraugster" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-fraugster-passed">
				<input id="rad-fraugster-passed" name="fraugsterStatus[]" type="radio" value="PASS" class="filterInput" />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-fraugster-failed">
				<input id="rad-fraugster-failed" name="fraugsterStatus[]" type="radio" value="FAIL " class="filterInput"/>
				<i class="material-icons">close</i>			</label>
		</li>
			</ul>

</fieldset>
				</div>

		<!-- 	</div>

		</div>

		<div class="grid--micro">

			<div class="grid__row"> -->

				<div class="grid__col--6 grid__col--pad-bottom sanction-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">Sanction</legend>
	
	<ul id="pillchoice-sanction" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-sanction-passed">
				<input id="rad-sanction-passed" name="sanctionStatus[]"  type="radio" value="PASS" class="filterInput" />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-sanction-failed">
				<input id="rad-sanction-failed" name="sanctionStatus[]" type="radio" value="FAIL" class="filterInput" />
				<i class="material-icons">close</i>			</label>
		</li>
			</ul>

</fieldset>
				</div>

				<div class="grid__col--6 grid__col--pad-bottom blackList-field">

					<fieldset class="filterInputWrap">

						<legend class="label filterInputHeader">Blacklist</legend>
				
						<ul id="pillchoice-blacklist" class="pill-choice">
									<li>
								<label class="pill-choice__choice--positive" for="rad-blacklist-passed">
									<input id="rad-blacklist-passed" name="blacklistStatus[]" type="radio" value="PASS" class="filterInput"/>
									<i class="material-icons">check</i>			</label>
							</li>
									<li>
								<label class="pill-choice__choice--negative" for="rad-blacklist-failed">
									<input id="rad-blacklist-failed" name="blacklistStatus[]" type="radio" value="FAIL" class="filterInput" />
									<i class="material-icons">close</i>			</label>
							</li>
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
									<i class="material-icons">check</i>			</label>
						</li>
						<li>
							<label class="pill-choice__choice--negative" for="rad-customCheck-failed">
								<input name="customCheckStatus[]" id="rad-customCheck-failed"  value="FAIL" type="radio" class="filterInput"   />
									<i class="material-icons">close</i>			</label>
						</li>
					</ul>

				</fieldset>
			</div>
				
			<div class="grid__col--6 grid__col--pad-bottom riskStatus-field">

				<fieldset class="filterInputWrap">

					<legend class="label filterInputHeader">FS Status</legend>
	
					<ul id="pillchoice-riskStatus" class="pill-choice">
						<li>
							<label class="pill-choice__choice--positive" for="rad-riskStatus-passed">
								<input name="riskStatus[]" id="rad-riskStatus-passed"  value="PASS" type="radio" class="filterInput"   />
								<i class="material-icons">check</i>			
							</label>
						</li>
						<li>
							<label class="pill-choice__choice--negative" for="rad-riskStatus-failed">
									<input name="riskStatus[]" id="rad-riskStatus-failed"  value="FAIL" type="radio" class="filterInput"   />
									<i class="material-icons">close</i>			
							</label>
						</li>
					</ul>

				</fieldset>
			</div>
	
			<!--Added for AT-4614-->
				<div class="grid__col--6 grid__col--pad-bottom intuitionStatus-field">
				<fieldset class="filterInputWrap">

					<legend class="label filterInputHeader">Intuition Status</legend>
	
					<ul id="pillchoice-intuitionStatus" class="pill-choice">
						<li>
							<label class="pill-choice__choice--positive" for="rad-intuitionStatus-passed">
								<input name="intuitionStatus[]" id="rad-intuitionStatus-passed"  value="PASS" type="radio" class="filterInput"   />
								<i class="material-icons">check</i>			
							</label>
						</li>
						<li>
							<label class="pill-choice__choice--negative" for="rad-intuitionStatus-failed">
									<input name="intuitionStatus[]" id="rad-intuitionStatus-failed"  value="FAIL" type="radio" class="filterInput"   />
									<i class="material-icons">close</i>			
							</label>
						</li>
					</ul>

				</fieldset>
			</div>
			
			<!-- Added for AT-4655 -->
			<div class="grid__col--6 grid__col--pad-bottom transactionMonitoringRequest-field">
				<fieldset class="filterInputWrap">

					<legend class="label filterInputHeader">TM request</legend>
	
					<ul id="pillchoice-transactionMonitoringRequest" class="pill-choice">
						<li>
							<label class="pill-choice__choice--positive" for="rad-transactionMonitoringRequest-passed">
								<input name="transactionMonitoringRequest[]" id="rad-transactionMonitoringRequest-passed"  value="PASS" type="radio" class="filterInput"   />
								<i class="material-icons">check</i>			
							</label>
						</li>
						<li>
							<label class="pill-choice__choice--negative" for="rad-transactionMonitoringRequest-failed">
									<input name="transactionMonitoringRequest[]" id="rad-transactionMonitoringRequest-failed"  value="FAIL" type="radio" class="filterInput"   />
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
											 <h2 id= "defaultMonth" style="visibility:hidden">Date defaults to last 6 months </h2>
												<input id="payIn_report_Filter"type="button" class="button--primary button--small" onclick="searchPayInQueueSearchCriteria()" value="Generate"/>
												<!-- <img id="ajax-loader-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/>-->
												<input type="button" id="save_search_button" class="button--primary button--small" value="Save" onclick="saveSearchPopup()"/>
												<input type="button" id="delete_search_button" class="button--primary button--small" value="Delete" onclick="deleteSaveSearchPopup()"/>
 												<object id="gifloaderforPayinreport" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;"></object>
											 </section>
									</div>

								</div>
								</div>
								</form>
					<form id="payInDownLoadForm" action="/compliance-portal/paymentInReportDownLoad" method="POST" >
					<input type="hidden" value="" id="downloadSearchCriteria" name = "downloadCriteria">
					</form>
							</div>
						</div>

						</div>

					</div>

				</div>

<!-- 			</div> -->

		

	</main>



	<div id="drawer-user" class="drawer--closed">

	<h2 class="drawer__heading">Your profile<span class="drawer__close"><i class="material-icons">close</i></span></h2>

	<h3>Site preferences</h3>

	<form>

		<ul class="form__fields">

			<li class="form__field-wrap--check">
				<input id="chk-preferences-menu-minimised" type="checkbox" required checked/>
				<label for="chk-preferences-menu-minimised">Navigation minimised by default</label>
			</li>

		</ul>

		<input type="submit" class="button--primary" value="Save"/>

	</form>
<input type="hidden" id="searchCriteriaQueue" value='${paymentInQueueDto.searchCriteria}' />
<input type="hidden" id="currentpage" value=""/>
<input type="hidden" id="isFromDetails" value="${paymentInQueueDto.isFromDetails}"/>
<input type="hidden" id="fraudSightFlag" value="${paymentInQueueDto.fraudSightFlag=='true'}"/>
<input type="hidden" id="pageType" value="PaymentInReport"/>
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
					<input id="saveSearchName" type="text" name="Name">
				</li>
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

<div id="downloadReportPopup" class="popupDiv" title="Alert">
		<textarea style="resize: none" id="downloadReportMessage"></textarea>
	</div>

	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/paymentInReport.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
	
	
	</body>

</html>