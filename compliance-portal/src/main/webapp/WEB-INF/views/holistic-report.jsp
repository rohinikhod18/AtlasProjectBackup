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



<div id="master-grid" class="grid">

	<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<h1>
						Holistic report

						<span class="breadcrumbs">

	<span class="breadcrumbs__crumb--in">in</span>
	<span class="breadcrumbs__crumb--area">Compliance</span>

	
</span>					</h1>

					<div id="main-content__body">

						<div class="grid">

							<div class="grid-annex-main grid__col--9 grid__col--8">
                            <form id="holisticQueueForm" action="/compliance-portal/getHolisticViewDetails" method="POST" >
								<table class="micro table-fix space-after">

	<thead>
		<tr>
			<th class="tight-cell" id="col_TradeAccountNumber"><a href="#">Client number</a></th>
			<th id="col_ContactName"><a href="#">Client name</a></th>
			<th id="col_Type"><a href="#">Type</a></th>
			<th id="col_CountryOfResidence" class = "breakwordHeader"><a href="#">Country of Residence</a></th>
			<th id="col_Organization"><a href="#">Organization</a></th>
			<th class="sorted desc" id="col_RegisteredOn"><a href="#" onclick="sortByField('RegisteredOn')">Registered Date <i></i></a></th>
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
												 		<tr class="available talign">
												 	</c:otherwise>
												</c:choose>
											    <td hidden="hidden"><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#"  >${loop.index + 1}</a></td>
												<td class="number"><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#"  >${listValue.tradeAccountNum}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#" >${listValue.contactName}</a></td>
												<td class="nowrap"><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#" >${listValue.type}</a></td>
												<td><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#" >${listValue.countryOfResidence}</a></td>
												<td><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})" href="#" >${listValue.organisation}</a></td>
												<td class="nowrap"><a onClick="getHolisticViewDetails(${listValue.contactId},'${listValue.type}',this, ${listValue.accountId})"
													href="#"  >${listValue.registeredOn}</a></td>
										</c:forEach>
								</tbody>
									

</table>
<input type="hidden" id="contactId" value="" name="contactId"/>
<input type="hidden" id="searchCriteria" value="" name="searchCriteria"/>
<input type="hidden" id="custType" value="" name="custType"/>
<input type="hidden" id="source" value="report" name="source"/>
<input type="hidden" id="accountId" value="" name="accountId"/>
</form>
<p id="pageCountDetails" style="display: none;">
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

									<ul class="horizontal containing pagination" id="paginationBlock" style="display: none;">
									<c:if test="${registrationQueueDto.page.totalPages gt 1 }">
										<li onclick="getSelectedPage(1)" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
											title="First page"><i class="material-icons">first_page</i></a>
										</li>
										<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
											title="Previous page"><i class="material-icons">navigate_before</i></a>
										</li>
										<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
											title="Next page"><i class="material-icons">navigate_next</i></a></li>
										<li  onClick="getSelectedPage(${registrationQueueDto.page.totalPages })" class="pagination__jump" value="${registrationQueueDto.page.totalPages }"><a id="lastPage" href="#"
											title="Last page"><i class="material-icons">last_page</i></a></li>
									</c:if>
									</ul>
									<input type="hidden" id="queueTotalPages" value="${registrationQueueDto.page.totalPages }"></input>
								

								<span class="annex-tab annex-trigger hidden">
									<i class="material-icons">filter_list</i>
								</span>

							</div>

							<div class="grid-annex-side grid__col--3">

								<div class="annex annex--visible">

									<h2>
										Generate Report
										<span class="f-right annex-trigger"><i class="material-icons">close</i></span>
									</h2>

									<div class="pagepanel__content boxpanel--shadow--splits">

										<form class="form" id="queueFilterForm" autocomplete=off>
											
											


					
				<section>
												<h3>Enter search criteria</h3>

												<fieldset>

													<ul class="form__fields">

														<li class="form__field-wrap filterInputWrap"><label
															for="txt-search-keyword" class="label filterInputHeader">Keyword</label>
															<input id="txt-search-keyword" type="text" name="keyword"
															placeholder="Search Client number here"></li>
													</ul>
												</fieldset>
											
												
											</section>

											<section class="section--actions">
												<input id="reg_report_Filter" type="button" class="button--primary" value="GENERATE" onclick="applyRegQueueSearchCriteria()"/>
												<!-- <img id="ajax-loader-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->
												<object id="gifloaderforregreport" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
											</object>
											</section>
										</form>
							<form id="regReportDownLoadForm" action="/compliance-portal/registrationReportDownLoad" method="POST" >
								<input type="hidden" value="" id="downloadSearchCriteria" name = "downloadCriteria">
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
<input type="hidden" id="searchCriteriaQueue" value='${registrationQueueDto.searchCriteria}' />
<input type="hidden" id="currentpage" value=""/>
<input type="hidden" id="isFromDetails" value="${registrationQueueDto.isFromDetails}"/>
</div>

<div id="downloadReportPopup" class="popupDiv" title="Alert">
		<textarea style="resize: none" id="downloadReportMessage"></textarea>
	</div>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
	<script type="text/javascript" src="resources/js/holisticReport.js"></script>
	

	
	
	
	</body>

</html>	
