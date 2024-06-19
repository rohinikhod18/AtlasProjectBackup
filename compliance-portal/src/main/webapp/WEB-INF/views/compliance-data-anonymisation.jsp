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
						Data Anonymisation

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
                            <form id="dataAnonForm" action="/compliance-portal/dataAnonDetails" method="POST" >
								<table id="table-data-anonymisation" class="micro table-fix space-after">

	<thead>
		<tr>
			<th  class="tight-cell" id="col_TradeAccountNumber"><a href="#">Client number</a></th>
			<th id="col_ContactName"><a href="#">Client name</a></th>
			<th id="col_Type"><a href="#">Type</a></th>
			<th class="sorted desc" id="col_RequestDate"><a href="#" onclick="sortByField('RequestDate')">Request Date <i></i></a></th>
			<th id="col_RequestBy"><a href="#">Request By</a></th>
			<th id="col_ApprovedDate"><a href="#">Approved Date</a></th>
			<th id="col_ApprovedBy"><a href="#">Approved By</a></th>
			<th id="col_Status"><a href="#">Status</a></th>
			<th id="col_Confirm"><a href="#">Confirm</a></th>
			<th id="col_Cancel"><a href="#">Cancel</a></th>
		</tr>
	</thead>

	<tbody id="dataAnonBody">

										<c:forEach var="listValue"
											items="${dataAnonymisationDto.dataAnonymisation}"  varStatus="loop">
											<c:set var="anonDetailsUrl" 
												value="/compliance-portal/dataAnonDetails?contactId=${listValue.contactId}" />
												<c:choose>
												 	<c:when test="${listValue.locked  && listValue.lockedBy==dataAnonymisationDto.user.name}">
												 		<tr class="owned talign" data-ot="You own(s) this record">
												 	</c:when>
												 	<c:when test="${listValue.locked }">
												 		<tr class="talign unavailable" data-ot="${listValue.lockedBy } own(s) this record">
												 	</c:when>
												 	<c:otherwise>
												 		<tr class="available talign ">
												 	</c:otherwise>
												</c:choose>
												
											    <td hidden="hidden"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}','${listValue.dataAnonymizationStatus}',this)" href="#"  >${loop.index + 1}</a></td>
												<td class="number"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#"  >${listValue.tradeAccountNum}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.contactName}</a></td>
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.type}</a></td>
												<td style="text-align: center"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.initialApprovalDate}</a></td>
											    <td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.initialApprovalBy}</a></td>
													<c:choose>
														<c:when test="${listValue.dataAnonymizationStatus == 2}">
															<td style="text-align: center"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.finalApprovalDate}</a></td>
											            	<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.finalApprovalBy}</a></td>
														</c:when>
														<c:otherwise>
														<td style="text-align: center"><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" ></a></td>
											            	<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" ></a></td>
														</c:otherwise>
														</c:choose>
											<!-- 	</td> -->
												<td><a onClick="getRegistrationQueueDetails(${listValue.contactId},'${listValue.type}',this)" href="#" >${listValue.complianceStatus}</a></td>
												<td>
													<c:choose>
														<c:when test="${listValue.dataAnonymizationStatus == 2}">
															<input type="button" id="confirm_button" disabled="disabled" class="button--primary button--small button--disabled" value="Confirm" onclick="DataAnonHistory('${listValue.id}',this.id,'${listValue.initialApprovalBy}','${listValue.accountId}','${listValue.contactId}')"/>
															
														</c:when>
														<c:otherwise>
															 <input type="button" id="confirm_button" class="button--primary button--small" value="Confirm" onclick="DataAnonHistory('${listValue.id}',this.id,'${listValue.initialApprovalBy}','${listValue.accountId}','${listValue.contactId}')"/>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${listValue.dataAnonymizationStatus == 2  }">
															<input type="button" id="cancel_button" disabled="disabled" class="button--primary button--small button--disabled" value="Cancel" onclick="DataAnonHistory('${listValue.id}',this.id,'${listValue.initialApprovalBy}','${listValue.accountId}','${listValue.contactId}')"/>
															
														</c:when>
														<c:otherwise>
															 <input type="button" id="cancel_button" class="button--primary button--small" value="Cancel" onclick="DataAnonHistory('${listValue.id}',this.id,'${listValue.initialApprovalBy}','${listValue.accountId}','${listValue.contactId}')"/>
														</c:otherwise>
													</c:choose>
												</td>
												
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
										<c:when test="${dataAnonymisationDto.page.totalRecords ge dataAnonymisationDto.page.pageSize}">
											<strong id="queueMinRecord">${dataAnonymisationDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${dataAnonymisationDto.page.maxRecord}</strong>
										</c:when>
										<c:otherwise>
										     <strong id="queueMinRecord">${dataAnonymisationDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${dataAnonymisationDto.page.maxRecord}</strong>
										</c:otherwise>
									</c:choose>
									of <strong id="queueTotalRecords">${dataAnonymisationDto.page.totalRecords}</strong>
									records
								</p>

									<ul class="horizontal containing pagination" id="paginationBlock">
									<c:if test="${dataAnonymisationDto.page.totalPages gt 1 }">
										<li onclick="getSelectedPage(1)" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
											title="First page"><i class="material-icons">first_page</i></a>
										</li>
										<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
											title="Previous page"><i class="material-icons">navigate_before</i></a>
										</li>
										<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
											title="Next page"><i class="material-icons">navigate_next</i></a></li>
										<li onClick="getSelectedPage(${dataAnonymisationDto.page.totalPages })" class="pagination__jump" value="${dataAnonymisationDto.page.totalPages }"><a id="lastPage" href="#"
											title="Last page"><i class="material-icons">last_page</i></a></li>
									
									</c:if>
									</ul>
									<input type="hidden" id="queueTotalPages" value="${dataAnonymisationDto.page.totalPages }"></input>
								

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

										<form class="form" onsubmit="return false" id="queueFilterForm" autocomplete=off>
											
											<div id="addedFilters">
											
</div>
											<section>
											
											<h3>Enter search criteria</h3>
											

												<fieldset>

													<ul class="form__fields">

														<li class="form__field-wrap filterInputWrap"><label
															for="txt-search-keyword" class="label filterInputHeader">Keyword</label>
															<input id="txt-search-keyword" type="text" name="keyword"
															placeholder="Client number, name etc..."											
																	></li>
													</ul>
												</fieldset>

												<h3>Select filters</h3>

												<fieldset>

													<ul class="form__fields">


	<%-- <li class="form__field-wrap">
	
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
					<input id="date-registered-from" name="dateFrom" class="datepicker filterInput" type="text" value="${dataAnonymisationDto.dateFrom}" placeholder="dd/mm/yyyy" />
				</div>
				<div class="grid__col--6 filterInputWrap">
					<label for="date-registered-to" class="filterInputHeader">Date to</label>
					<input id="date-registered-to" name="dateTo" class="datepicker filterInput" type="text"  value="${dataAnonymisationDto.dateTo}" placeholder="dd/mm/yyyy"/>
				</div>
			</div>
		</div>
	</li> --%>

	<li class="form__field-wrap filterInputWrap">
		<fieldset>

		<legend class="label filterInputHeader" >Client type</legend>
	
	<ul id="pillchoice-client-type" class="pill-choice--small">
				<li>
			<label class="pill-choice__choice pfx-field" for="rad-type-pfx">
				<input id="rad-type-pfx" name="custType[]" type="checkbox" value="PFX" class="filterInput"  />
				PFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field"  for="rad-type-cfx">
				<input id="rad-type-cfx" name="custType[]" type="checkbox" value="CFX" class="filterInput"  />
				CFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx-etailer">
				<input id="rad-type-cfx-etailer" name="custType[]" type="checkbox" value="CFX (etailer)" class="filterInput"   />
				CFX (etailer)			</label>
		</li>
			</ul>

</fieldset>	</li>
										

</ul>
												</fieldset>

											</section>

											<section class="section--actions">
												<input id="data_anon_queue_Filter" type="button" class="button--primary button--small" value="GENERATE" onclick="applyDataAnonQueueSearchCriteria()"/>
												<!-- <input type="button" id="save_search_button" class="button--primary button--small" value="Save" onclick="saveSearchPopup()"/>
												<input type="button" id="delete_search_button" class="button--primary button--small" value="Delete" onclick="deleteSaveSearchPopup()"/> -->
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
	
	<div id="DataAnonPopup" class="popupDiv" title="Confirm Data Anonymisation ">
		<div id="dataAnonPopupdiv">
			<div id="errorDiv1" class="message--negative" style="display: none;">
								<div class="copy">
									<p id = "errorDescription1"></p>
								</div>
	                      </div>
			<h1>Enter reason </h1>
			<fieldset>
							<ul class="form__fields">
									<li class="form__field-wrap filterInputWrap">
								<textarea id="DataAnonReason" cols="1" rows="6" maxlength="1024" name="Name"></textarea></li> 
								</ul>
	</fieldset>
		</div>
	</div>
	<div id="DataAnonHistorypopups" class="popupDiv" >
		<div>
		<div id="BtnId" style="font-size: large"></div>
		<fieldset>
							<ul class="form__fields">
									<li class="form__field-wrap filterInputWrap">    
								<textarea id="RejectDataAnonReason" cols="1" rows="3" name="Name"></textarea></li> 
								</ul>
			</fieldset>
								<div id="errorDiv2" class="message--negative" style="display: none;">
								<div class="copy">
									<p id = "errorDescription2"></p>
								</div>
	                      </div></div>
	
		<div id="DataAnonHistory" class="popupTextAreaDataAnonHistory popup" style="overflow: scroll; height: 500px;">
		
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

<input type="hidden" id="custTypeForDashboardFilter" value="${dataAnonymisationDto.custType}" />
<input type="hidden" id="isDashboardSearchCriteria" value="${dataAnonymisationDto.isDashboardSearchCriteria}" />
<input type="hidden" id="searchCriteriaQueue" value='${dataAnonymisationDto.searchCriteria}' />
<input type="hidden" id="currentpage" value=""/>
<input type="hidden" id="pageType" value="DataAnonymisation"/>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>

	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
	<script type="text/javascript" src="resources/js/dataAnonQueue.js"></script>
	<!-- <script type="text/javascript" src="resources/js/cdmodel.js"></script> -->

	
	
	
	</body>

</html>