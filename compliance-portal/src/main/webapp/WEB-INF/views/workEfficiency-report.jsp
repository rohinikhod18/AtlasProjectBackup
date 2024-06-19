<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<title>Enterprise tools</title>
<meta name="description" content="Enterprise tools">
<meta name="copyright" content="Currencies Direct">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
</head>

<body>

	<div id="master-grid" class="grid">

		<main id="main-content" class="main-content--large"> 
		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<h1>
						Work Efficiency Report <span class="breadcrumbs"> <span
							class="breadcrumbs__crumb--in">IN</span> <span
							class="breadcrumbs__crumb--area">Compliance </span>


						</span>
					</h1>

					<c:choose>
						<c:when test="${not empty workEfficiencyReportDto.errorMessage}">
						   Error : ${workEfficiencyReportDto.errorMessage}
						</c:when>
						<c:otherwise>


							<div id="main-content__body">
								
									    <p>
											Showing
											Last  <strong id="dateDifferenceBetweenReports">${workEfficiencyReportDto.dateDifference}</strong>
											Days Records
										</p>
								<div class="grid">

									<div class="grid-annex-main--scroller grid__col--9">

										<table class="micro table-fix space-after">
											<thead>
												<tr>

													<th class="rowColour" id="col_Queue_Type">Queue Type</th>
													<th class="rowColour" id="col_User">User</th>
													<!-- <th class="sorted desc" onclick = "sortByField('Date')"  > <input type="hidden"id="col_Date" value="" /> <a href="#">Date<i></i></a></th> -->
													<th class="rowColour" id="col_Account_Type">Account
															Type</th>
													<th class="rowColour" id="col_LockedRecords">Locked
															Records</th>
													<th class="rowColour" id="col_ReleasedRecords">Released
															Records</th>
													<!-- <th class="rowColour" id="col_Percent_Efficiency">%
															Efficiency</th> -->
													<th class="rowColour" id="col_Time_For_Clearance">Avg Time
															For Clearance(Min)</th>
													<th class="rowColour" id="col_Sla_time">SLA time(Min)</th>
													<th class="rowColour" id="col_Time_Efficiency">Work
															Efficiency(%)</th>

												</tr>
											</thead>
											<tbody id="workEfficiencyBody">

												<c:forEach var="listValue"
													items="${workEfficiencyReportDto.workEfficiencyReportData}"
													varStatus="loop">

													<tr class="talign">
														<td class="nowrap rowColour">${listValue.queueType}</td>
														<td class="nowrap rowColour">${listValue.userName}</td>
														<td class="nowrap rowColour">${listValue.accountType}</td>
														<td class="number rowColour">${listValue.lockedRecords}</td>
														<td class="number rowColour">${listValue.releasedRecords}</td>
														
														<td class="number rowColour">${listValue.seconds}</td>
														<td class="number rowColour">${listValue.slaValue}</td>
														<td class="number rowColour">${listValue.percentEfficiency}</td>
														
														<!-- 	</tr> -->
												</c:forEach>
											</tbody>
										</table>


										<p>
											Showing
											<c:choose>
												<c:when
													test="${workEfficiencyReportDto.page.totalRecords ge workEfficiencyReportDto.page.pageSize}">
													<strong id="queueMinRecord">${workEfficiencyReportDto.page.minRecord}</strong>
													<strong>-</strong>
													<strong id="queueMaxRecord">${workEfficiencyReportDto.page.maxRecord}</strong>
												</c:when>
												<c:otherwise>
													<strong id="queueMinRecord">${workEfficiencyReportDto.page.minRecord}</strong>
													<strong>-</strong>
													<strong id="queueMaxRecord">${workEfficiencyReportDto.page.maxRecord}</strong>
												</c:otherwise>
											</c:choose>
											of <strong id="queueTotalRecords">${workEfficiencyReportDto.page.totalRecords}</strong>
											records
										</p>
										<input type="hidden" id="queueTotalPages"
											value="${workEfficiencyReportDto.page.totalPages}"></input>
											<ul class="horizontal containing pagination" id="paginationBlock" style="display: block;">	
										<c:if test="${workEfficiencyReportDto.page.totalPages gt 1 }">
											<%-- <ul class="horizontal containing pagination"
												id="paginationBlock">--%>
						
												<li onclick="getSelectedPage(1)"
													class="pagination__jump--disabled" value="1"><a id="firstPage"
													href="#" title="First page"><i class="material-icons">first_page</i></a>
												</li>
												
												<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage"
													href="#" title="Previous page"><i
														class="material-icons">navigate_before</i></a></li>
												
												
				                                  <li onClick="getNextPage()" class="pagination__jump"><a id="nextPage"
													href="#" title="Next page"><i class="material-icons">navigate_next</i></a></li>
	
	                                          <li
													onClick="getSelectedPage(${workEfficiencyReportDto.page.totalPages})"
													class="pagination__jump"
													value="${workEfficiencyReportDto.page.totalPages}"><a id="lastPage"
													href="#" title="Last page"><i class="material-icons">last_page</i></a></li>
			
						
											<%-- 	<c:forEach var="i" begin="1"
													end="${workEfficiencyReportDto.page.totalPages}">
													<c:choose>
														<c:when test="${i eq 1}">
															<li id="page${i }" onClick="getSelectedPage(${ i })"
																class="pagination__page--on" value="${ i }"><a
																href="#" title="Page ${i }">${i }</a></li>
														</c:when>
														<c:otherwise>
															<c:if test="${i eq 11  }">
																<li class="pagination__more"><a href="#"><i
																		class="material-icons">more_horiz</i></a></li>
															</c:if>
															<c:choose>
																<c:when
																	test="${i ge 11 && workEfficiencyReportDto.page.totalPages ne i}">
																	<li id="page${i }" onClick="getSelectedPage(${ i })"
																		class="pagination__page pagination__page--hidden"
																		value="${ i }"><a href="#" title="Page ${i }">${i }</a></li>
																</c:when>
																<c:otherwise>
																	<li id="page${i }" onClick="getSelectedPage(${ i })"
																		class="pagination__page" value="${ i }"><a
																		href="#" title="Page ${i }">${i }</a></li>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:forEach>--%>
													</c:if>
											</ul>
									

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
	<li class="pagination__page">
		<a href="#" title="Page 3">3</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 4">4</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 5">5</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 6">6</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 7">7</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 8">8</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 9">9</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 10">10</a>
	</li>
	<li class="pagination__more"><a href="#"><i class="material-icons">more_horiz</i></a></li><li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 11">11</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 12">12</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 13">13</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 14">14</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 15">15</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 16">16</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 17">17</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 18">18</a>
	</li>
	<li class="pagination__page pagination__page--hidden">
		<a href="#" title="Page 19">19</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 20">20</a>
	</li>
</ul> -->
										<span class="annex-tab annex-trigger hidden"> <i
											class="material-icons">search</i>
										</span>

									</div>
									<form class="frm-table-filters" id="queueFilterForm"
										autocomplete=off>
										<div class="grid-annex-side grid__col--3">

											<div class="annex annex--visible">

												<h2>
													Filter queue <span class="f-right annex-trigger"><i
														class="material-icons">close</i></span>
												</h2>

												<div class="pagepanel__content boxpanel--shadow--splits">
													<div id="addedFilters"></div>
													<%-- <form id="frm-search" class="form"> --%>

													<!-- <h2 class="hidden">Enter search criteria</h2> -->

													<!-- <section class="section--results">

	<h3>3 filters applied</h3>
	<ul class="containing space-after delete-list">
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>First filter's label</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>Another filter's label</li>
		<li><a href="#"><i class="material-icons icon--medium">close</i></a>One more filter's label</li>
	</ul>
	<a href="#" class="button--primary button--small">Clear all</a>
	<img id="ajax-loader-clear-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading...">
	<span class="section--results-arrow"></span>

</section> -->
<section>
     
    <fieldset>
				

				<ul class="button-group">
					<li>
						<a href="#" class="button--primary button--small"  onclick="clearAndApplyFilter()">Clear</a>
					</li>
					
					<li>
						<div class="hoverpanel--right hoverpanel--small">

							Download
							<i class="material-icons">keyboard_arrow_down</i>

							<div class="hoverpanel__content--small">
								<ul class="block-list">
									<li>
									<a href="#" class="icon-link--msexcel" onclick="checkTotalRecordsAndDownload()" >Microsoft Excel</a>
									</li>									
								</ul>
								<span class="hoverpanel__arrow"></span>
							</div>

						</div>

					</li>
					<li>
						<!-- <img id="ajax-loader-clear-search" class="ajax-loader " src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->					</li>
					
					</ul>
					<span class="section--results-arrow"></span>
					</fieldset>
					
	</section>
													
													
													<section>

														<h3>Select filters</h3>

														<fieldset>

															<ul class="form__fields">

																<!-- <li class="form__field-wrap">

		<p class="label">Organisations</p>

		<div id="multilist-organisations" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			
			<ul class="multilist__options">

								<li>
					<label for="chk-org-cduk-1">
						<input id="chk-org-cduk-1" type="checkbox">
						CD UK					</label>
				</li>
								<li>
					<label for="chk-org-cdsa-2">
						<input id="chk-org-cdsa-2" type="checkbox">
						CD SA					</label>
				</li>
								<li>
					<label for="chk-org-toruk-3">
						<input id="chk-org-toruk-3" type="checkbox">
						TOR UK					</label>
				</li>
								<li>
					<label for="chk-org-toraus-4">
						<input id="chk-org-toraus-4" type="checkbox">
						TOR AUS					</label>
				</li>
				
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
	</li>
 -->
																<li class="form__field-wrap">

																	<div class="grid">
																		<div class="grid__row">
																			<div class="grid__col--6 filterInputWrap">
																				<label for="date-payment-from"
																					class="filterInputHeader">Date from</label> <input
																					id="date-payment-from" name="dateFrom"
																					class="datepicker filterInput" type="text"
																					placeholder="dd/mm/yyyy">
																			</div>
																			<div class="grid__col--6 filterInputWrap">
																				<label for="date-payment-to"
																					class="filterInputHeader">From to</label> <input
																					id="date-payment-to" name="fromTo"
																					class="datepicker filterInput" type="text"
																					placeholder="dd/mm/yyyy">
																			</div>
																		</div>
																	</div>

																</li>

																<li class="form__field-wrap filterInputWrap">
																	<fieldset>

																		<legend class="label filterInputHeader">Account type</legend>

																		<ul id="pillchoice-client-type"
																			class="pill-choice--small">
																			<li><label class="pill-choice__choice"
																				for="rad-type-pfx"> <input name="custType[]"
																					id="rad-type-pfx" type="checkbox" value="PFX"
																					class="filterInput"> PFX
																			</label></li>
																			<li><label class="pill-choice__choice"
																				for="rad-type-cfx"> <input name="custType[]"
																					id="rad-type-cfx" type="checkbox" value="CFX"
																					class="filterInput" /> CFX
																			</label></li>
																			<li><label class="pill-choice__choice"
																				for="rad-type-cfx-etailer"> <input
																					name="custType[]" id="rad-type-cfx-etailer"
																					type="checkbox" value="CFX (etailer)"
																					class="filterInput" /> CFX (etailer)
																			</label></li>
																		</ul>

																	</fieldset>
																</li>

																<!-- <li class="form__field-wrap">

		<p class="label">Buy currency</p>

		<div id="multilist-sell-currency" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

						<input class="multilist__search space-after" type="search" placeholder="Search list">
			
			<ul class="multilist__options">

								<li>
					<label for="chk-sell-currency-1">
						<input id="chk-sell-currency-1" type="checkbox">
						GBP					</label>
				</li>
								<li>
					<label for="chk-sell-currency-2">
						<input id="chk-sell-currency-2" type="checkbox">
						USD					</label>
				</li>
								<li>
					<label for="chk-sell-currency-3">
						<input id="chk-sell-currency-3" type="checkbox">
						AUD					</label>
				</li>
								<li>
					<label for="chk-sell-currency-4">
						<input id="chk-sell-currency-4" type="checkbox">
						EUR					</label>
				</li>
								<li>
					<label for="chk-sell-currency-5">
						<input id="chk-sell-currency-5" type="checkbox">
						CHF					</label>
				</li>
				
			</ul>

		</fieldset>

		<span class="clickpanel__arrow"></span>

	</div>

</div>
	</li> -->

																<li class="form__field-wrap filterInputWrap">

																	<p class="label filterInputHeader">User</p>

																	<div id="multilist-user"
																		class="multilist clickpanel--right">

																		<ul class="multilist__chosen">
																			<li class="clickpanel__trigger">Please select</li>
																		</ul>

																		<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																		<div class="clickpanel__content--hidden">

																			<fieldset>

																				<input class="multilist__search space-after"
																					type="search" placeholder="Search list">
																				<ul class="multilist__options">


																				<c:forEach var="userName" items="${workEfficiencyReportDto.userNameList}"  varStatus="loop">
																					<li>
																						<label  for="chk-user-${loop.index}">
																							<input name="user[]" class="filterInput" id="chk-user-${loop.index}"
																								type="checkbox" value="${userName}" />${userName}	
																						</label>
																					</li>
																				</c:forEach>
																					<!-- <li><label for="chk-user-1"> <input
																							name="user[]" id="chk-user-1" value="abhay"
																							type="checkbox" class="filterInput">
																							abhay
																					</label></li>
																					<li><label for="chk-user-2"> <input
																							name="user[]" id="chk-user-2" value="Laxmi"
																							type="checkbox" class="filterInput">
																							Laxmi
																					</label></li>
																					<li><label for="chk-user-3"> <input
																							name="user[]" id="chk-user-3" value="Manish"
																							type="checkbox" class="filterInput">
																							Manish
																					</label></li> -->


																				</ul>

																			</fieldset>

																			<span class="clickpanel__arrow"></span>

																		</div>

																	</div>
																</li>

																<li class="form__field-wrap filterInputWrap">

																	<p class="label filterInputHeader">Queue Type</p>

																	<div id="multilist-queue-type"
																		class="multilist clickpanel--right">

																		<ul class="multilist__chosen">
																			<li class="clickpanel__trigger">Please select</li>
																		</ul>

																		<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

																		<div class="clickpanel__content--hidden">

																			<fieldset>

																				<input class="multilist__search space-after"
																					type="search" placeholder="Search list">

																				<ul class="multilist__options">

																					<c:forEach var="queueType" items="${workEfficiencyReportDto.queueList}"  varStatus="loop">
																						<li>
																							<label  for="chk-queue-type-${loop.index}">
																								<input name="queueType[]" class="filterInput" id="chk-queue-type-${loop.index}"
																									type="checkbox" value="${queueType}" />${queueType}	
																							</label>
																						</li>
																					</c:forEach>
																				
																				
																				
																					<!-- <li><label for="chk-queue-type-1"> <input
																							name="queueType[]" id="chk-queue-type-1"
																							value="All" type="checkbox" class="filterInput">
																							ALL
																					</label></li>
																					<li><label for="chk-queue-type-2"> <input
																							name="queueType[]" id="chk-queue-type-2"
																							value="CONTACT" type="checkbox"
																							class="filterInput"> CONTACT
																					</label></li>
																					<li><label for="chk-queue-type-3"> <input
																							name="queueType[]" id="chk-queue-type-3"
																							value="PAYMENT_IN" type="checkbox"
																							class="filterInput"> PAYMENT_IN
																					</label></li>
																					<li><label for="chk-queue-type-4"> <input
																							name="queueType[]" id="chk-queue-type-4"
																							value="PAYMENT_OUT" type="checkbox"
																							class="filterInput"> PAYMENT_OUT
																					</label></li> -->

																				</ul>

																			</fieldset>

																			<span class="clickpanel__arrow"></span>

																		</div>

																	</div>
																</li>

															</ul>
														</fieldset>

													</section>

													<section class="section--actions">

														<input id="filterButton" type="button" class="button--primary"
															onclick="applyWorkEfficiencySearchCriteria()"
															value="Filter">
														<!-- <img id="ajax-loader-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."> -->
													</section>

													<%-- 	</form> --%>

												</div>

											</div>

										</div>
									</form>
							<form id="workEfficiencyReportDownLoadForm" action="/compliance-portal/workEfficiencyReportDownLoad" method="POST" >
								<input type="hidden" value="" id="downloadSearchCriteria" name = "downloadCriteria">
							</form>
								</div>

							</div>
						</c:otherwise>
					</c:choose>
				</div>

			</div>

		</div>

		</main>

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
					checked> <label for="chk-preferences-menu-minimised">Navigation
						minimised by default</label></li>

			</ul>

			<input type="submit" class="button--primary" value="Save">

		</form>

	</div>
	<input type="hidden" id="currentPage" value='${workEfficiencyReportDto.page.currentPage}' />
<div id="downloadWorkEfficiencyPopup" class="popupDiv" title="Alert">
		<textarea style="resize: none" id="downloadWorkEfficiencyMessage"></textarea>
	</div>

	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript"
		src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
	<script type="text/javascript"
		src="resources/js/workEfficiencyReport.js"></script>





	<%-- <div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div><div id="opentip-10" class="opentip-container style-dark ot-show-effect-appear ot-hide-effect-fade stem-center ot-visible stem-top" style="position: absolute; left: 36px; top: 32px; visibility: visible; width: 191.078px; z-index: 100; transition-duration: 0s;"><canvas width="193" height="40" style="position: absolute; width: 193px; height: 40px; left: -1px; top: -6px;"></canvas><div class="opentip"><div class="ot-header"></div><div class="ot-content">Kam Biring owns this record</div></div></div> --%>
</body>
</html>