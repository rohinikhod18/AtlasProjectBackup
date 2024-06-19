<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>

<html lang="en">

	<head>
		<head>
			<meta charset="utf-8"/>
			<meta name="description" content="Atlas"/>
			<meta name="copyright" content="Currencies Direct"/>
				
			<link rel="stylesheet" href="resources/css/jquery-ui.css">
	  		<link rel="stylesheet" href="resources/css/popup.css">
	  		<link rel="stylesheet" type="text/css" href="resources/css/cd.css"/>
			<link rel="stylesheet" type="text/css" href="resources/css/cd1.css"/>
			<link rel="stylesheet" type="text/css" href="resources/css/cd.page.compliance.min.css"/>
		</head>

	<body>

<main class="main-content--large">

<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">
	<h1>
		Beneficiaries

		<span class="breadcrumbs">
		
			<span class="breadcrumbs__crumb--in">in</span>
			<span class="breadcrumbs__crumb--area">Compliance</span>
			
		</span>

	</h1>
			<!-- my changes start -->
			<div id="main-content__body">
			<div class="grid">
			<div class="grid-annex-main grid__col--9 grid__col--8">
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
			 	<table id = "payeeListTableBody" class="micro nowrap space-after">
			 		<thead>
			 			<tr>
							<th class="sorted desc"><a href="#">Last paid</a></th>
							<th ><a href="#">Organisation</a></th>
							<th ><a href="#">Name</a></th>
							<th ><a href="#">Third Party</a></th>
							<th ><a href="#">Acc.No./iban</a></th>
							<th ><a href="#">Swift code</a></th>
							<th ><a href="#">CCY</a></th>
							<th ><a href="#">Country</a></th>
							<th ><a href="#">Client name</a></th>
			 			</tr>
			 		</thead>
			 		<tbody id = "payeeListTable">
			 			<c:forEach var="listValue" items="${payeeResponse.payeeList}"  varStatus="loop">
							<c:set var="beneDetailsUrl" value="/compliance-portal/beneficiaryDetails?payeeId=${listValue.id}" />
								<tr class="available talign">
								   		<td hidden="hidden"><a href="#"  >${loop.index + 1}</a></td>
								   		<c:forEach var="listValuePaymentMethodList" items="${listValue.payeePaymentMethodList}"  varStatus="loop">
								   		<td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#"  >${listValuePaymentMethodList.payeeBank.updatedOnDateForUI}</a></td>
										<td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#"  >${listValue.organizationName}</a></td>
										<c:choose>
    										<c:when test="${empty listValue.fullName}">
       												 <td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >------</a></td>
    										</c:when>
   											 <c:otherwise>
        												<td class="nowrap"><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#"  >${listValue.fullName}</a></td>
   											 </c:otherwise>
										</c:choose>
									
										<c:choose>
    										<c:when test="${listValue.self == 'true'}">
       												 <td class="wordwrapfixwidth"><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >No</a></td>
    										</c:when>
   											 <c:otherwise>
        											 <td class="wordwrapfixwidth"><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >Yes</a></td>
   											 </c:otherwise>
										</c:choose>
										
										<c:choose>
    										<c:when test="${listValuePaymentMethodList.payeeBank.accountNumber == null}">
       												 <c:choose>
    										<c:when test="${listValuePaymentMethodList.payeeBank.iban == null}">
       												 <td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >------</a></td>
    										</c:when>
   											 <c:otherwise>
        											 <td class="nowrap"><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValuePaymentMethodList.payeeBank.iban}</a></td>
   											 </c:otherwise>
										</c:choose>
    										</c:when>
   											 <c:otherwise>
        											 <td class="nowrap"><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValuePaymentMethodList.payeeBank.accountNumber}</a></td>
   											 </c:otherwise>
										</c:choose>
										
										<c:choose>
    										<c:when test="${listValuePaymentMethodList.payeeBank.bankBIC == null}">
       												 <td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >------</a></td>
    										</c:when>
   											 <c:otherwise>
        											 <td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValuePaymentMethodList.payeeBank.bankBIC}</a></td>
   											 </c:otherwise>
										</c:choose>
										<td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValuePaymentMethodList.payeeBank.payeeCurrencyCode}</a></td>
										<td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValue.countryName}</a></td>
										<td><a onClick="getPayeeDetails(${listValue.id}, '${listValue.accountNumber}', '${listValue.organizationName}', this)" href="#" >${listValuePaymentMethodList.payeeBank.accountName}</a></td>
						       </c:forEach>
						        </tr>
						</c:forEach>
			 		</tbody>
			 	</table>
			<div id = "payee_system_message" style="display:none;">
				<h1><center>System Not Available</center></h1>
			</div>
			<form id="payeeReportForm" action="/compliance-portal/beneficiaryDetails" method="POST" >
				<input type="hidden" id="payeeDetailsRequest" value="" name="payeeDetailsRequest"/>
				<input type="hidden" id="searchCriteria" value="" name="searchCriteria"/>
			</form>
			<p id="pageCountDetails" style="display: none;" >
				Showing
				<c:choose>
					<c:when test="${ empty payeeResponse.searchCriteria.page.totalRecords || empty payeeResponse.searchCriteria.page.minRecord || empty payeeResponse.searchCriteria.page.maxRecord }" >
						<strong id="queueMinRecord">0</strong> <strong>-</strong> <strong id="queueMaxRecord">0</strong>
						
						of <strong id="queueTotalRecords">0</strong>
						records
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${payeeResponse.searchCriteria.page.totalRecords ge payeeResponse.searchCriteria.page.pageSize}">
								<strong id="queueMinRecord">${payeeResponse.searchCriteria.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${payeeResponse.searchCriteria.page.maxRecord}</strong>
							</c:when>
							<c:otherwise>
					    		 <strong id="queueMinRecord">${payeeResponse.searchCriteria.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${payeeResponse.searchCriteria.page.maxRecord}</strong>
							</c:otherwise>
						</c:choose>
						of <strong id="queueTotalRecords">${payeeResponse.searchCriteria.page.totalRecords}</strong>
						records
					</c:otherwise>
				</c:choose>
			</p>

			<ul class="horizontal containing pagination" id="paginationBlock" style="display: none;">
				<c:if test="${payeeResponse.searchCriteria.page.totalPages >= 1 }">
					<li onclick="getSelectedPage(1)" class="pagination__jump" value="1"><a id="firstPage" href="#"
						title="First page"><i class="material-icons">first_page</i></a>
					</li>
					
					<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
						title="Previous page"><i class="material-icons">navigate_before</i></a>
					</li>
					
					<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
						title="Next page"><i class="material-icons">navigate_next</i></a></li>
						
					<li  onClick="getSelectedPage(${payeeResponse.searchCriteria.page.totalPages })" class="pagination__jump" value="${payeeResponse.searchCriteria.page.totalPages }"><a id="lastPage" href="#"
						title="Last page"><i class="material-icons">last_page</i></a></li>
				</c:if>
				</ul>
				<input type="hidden" id="queueTotalPages" value="${payeeResponse.searchCriteria.page.totalPages }"></input>

				<span class="annex-tab annex-trigger hidden">
					<i class="material-icons">filter_list</i>
				</span>
			</div>
			<form class="frm-table-filters" id="beneFilterForm">		
			<div class="grid-annex-side grid__col--3">

				<div class="annex annex--visible">

					<h2 class="pagepanel__title">Filter 
						<span class="f-right annex-trigger"><i class="material-icons">close</i></span>
					</h2>

					<div class="pagepanel__content boxpanel--shadow--splits">
					
						<form class="form" id="queueFilterForm" autocomplete=off>
					
						<!-- <div id="addedFilters">
												
						</div> -->
						
<!-- BEGIN: FILTER SUMMARY -->

					<section>
     
    					<fieldset>

							<ul class="button-group">
								<li>
									<a href="#" class="button--primary button--small"  onclick="clearAndApplyFilter()">Clear</a>
								</li>
							</ul>
							<span class="section--results-arrow"></span>
						
						</fieldset>
					
					</section>

<!-- END: FILTER SUMMARY -->

						<section class="splitpanel__section">

							<h3>Set filters</h3>

							<ul class="form__fields">
							
							<li class="form__field-wrap filterInputWrap">
								<label for="txt-search-keyword" class="label filterInputHeader">Keyword</label>
								<input id="txt-search-keyword" type="text" name="keyword" placeholder="Beneficiary name and account number">
							</li>

							<li class="form__field-wrap filterInputWrap">

								<p class="label filterInputHeader" >Organisations</p>

								<div id="multilist-organisations" class="multilist clickpanel--right">

								<ul class="multilist__chosen">
									<li class="clickpanel__trigger">Select</li>
								</ul>

								<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

								<div class="clickpanel__content--hidden">
							
									<fieldset>
									<input id="filterSearchListOrgId" class="multilist__search space-after" type="search" placeholder="Search list">
							
										<ul class="multilist__options">
											
							              <c:forEach var="organization" items="${payeeQueueDto.organization}"  varStatus="loop">
											
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

						<!-- <li class="form__field-wrap">

							<div class="grid">
								<div class="grid__row">
									<div class="grid__col--6 filterInputWrap">
										<label for="date-registered-from" class="filterInputHeader">Date from</label>
										<input id="date-registered-from" name="date_from" class="datepicker filterInput" type="text" value="${payeeQueueDto.dateFrom}" placeholder="dd/mm/yyyy" />
									</div>
									<div class="grid__col--6 filterInputWrap">
										<label for="date-registered-to" class="filterInputHeader">Date to</label>
										<input id="date-registered-to" name="date_to" class="datepicker filterInput" type="text" value="${payeeQueueDto.dateTo}" placeholder="dd/mm/yyyy"/>
									</div>
								</div>
							</div>
					
						</li>

						<li class="form__field-wrap filterInputWrap">

							<fieldset>

								<legend class="label filterInputHeader">Third party</legend>
								
									<ul id="pillchoice-filter-third-party" class="pill-choice--small">
										<li>
											<label style = "padding:0.3125rem" class="pill-choice__choice pfx-field" for="chk-filter-third-party-yes" >
												<input id="chk-filter-third-party-yes" type="radio" name="is_third_party" value="true" class="filterInput"  />
												Yes
											</label>
										</li>
										<li>
											<label style = "padding:0.3125rem" class="pill-choice__choice pfx-field" for="chk-filter-third-party-no" >
												<input id="chk-filter-third-party-no" type="radio" name="is_third_party" value="false" class="filterInput"  />
												No
											</label>
										</li>
									</ul>
							
							</fieldset>
						</li>
								
						<li class="form__field-wrap filterInputWrap">

							<p class="label filterInputHeader">Currency</p>

	
							<div id="multilist-filter-currency" class="multilist clickpanel--right" >
							
								<ul class="multilist__chosen">
									<li class="clickpanel__trigger">Select</li>
								</ul>
							
								<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>
							
								<div class="clickpanel__content--hidden">
							
									<fieldset>
							
										<input id="filterSearchListCurrencyListId" class="multilist__search space-after" type="search" placeholder="Search list"/>
										
										<ul class="multilist__options">
							
											<c:forEach var="currency" items="${payeeQueueDto.currency}"  varStatus="loop">
							
												<li>
													<label  for="chk-sell-currency-${loop.index}">
														    <input name="currency_code_list[]" class="filterInput" id="chk-sell-currency-${loop.index}"
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

							<p class="label filterInputHeader">Country</p>

								<div id="multilist-filter-countries" class="multilist clickpanel--right" >
								
									<ul class="multilist__chosen">
										<li class="clickpanel__trigger">Select</li>
									</ul>
								
									<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>
								
									<div class="clickpanel__content--hidden">
								
										<fieldset>
								
											<input id="filterSearchListCountryListId" class="multilist__search space-after" type="search" placeholder="Search List"/>
											
											<ul class="multilist__options">
								
												<c:forEach var="country" items="${payeeQueueDto.country}" varStatus="loop">
												
													<li>
														<label  for="chk-country-${loop.index}">
													    	<input name="country[]" class="filterInput" id="chk-country-${loop.index}"
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
		<fieldset>

		<legend class="label filterInputHeader" >Client type</legend>
	
	<ul id="pillchoice-client-type" class="pill-choice--small">
				<li>
			<label class="pill-choice__choice pfx-field" for="rad-type-pfx">
				<input id="rad-type-pfx" name="client_type" type="checkbox" value="PFX" class="filterInput"  />
				PFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field"  for="rad-type-cfx">
				<input id="rad-type-cfx" name="client_type" type="checkbox" value="CFX" class="filterInput"  />
				CFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx-etailer">
				<input id="rad-type-cfx-etailer" name="client_type" type="checkbox" value="CFX (etailer)" class="filterInput"   />
				CFX (etailer)			</label>
		</li>
			</ul>

</fieldset>	</li> -->

					</ul>

				</section>

				<section class="section--actions">
					<input id="bene_report_Filter" type="button" class="button--primary" value="APPLY" onclick="searchPayeeSearchCriteria()"/>
					<object id="gifloaderforpayinfilter" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 16 16" type="image/svg+xml" style="visibility:hidden;">
					</object>
				</section>
				</form>

			</div>

	</div>


</div>
</form>
</div>
</div>
				<!-- my changes end -->
			
	</div>
	</div>
	</div>

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
<% String beneListFromDetails = request.getParameter("ququefilter"); 
   String tradeAccountNumber  = request.getParameter("ququeClientfilter");
%>

<input type="hidden" id ="beneListFromDetails" value='<%= beneListFromDetails%>'/>
<input type="hidden" id ="beneListFromDetailsAlternate" name = "beneListFromDetailsAlternate" value=""/>
<input type="hidden" id="payeeListCheck" value='${payeeResponse.isSystemNotAvailaible}' />
<input type="hidden" id="searchCriteriaQueue" value='${payeeResponse.searchCriteriaForUI}' />
<input type="hidden" id="currentpage" value=""/>
<input type="hidden" id="isFromDetails" value=""/>
</div>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/payee.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	</body>

</html>