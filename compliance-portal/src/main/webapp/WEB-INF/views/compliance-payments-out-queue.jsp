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
<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>

	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/commonQueue.js"></script>
    <script type="text/javascript" src="resources/js/payOutQueue.js"></script>

<div id="master-grid" class="grid">

	<!-- start: top bar -->
<!-- <div class="grid__row">

	<div class="grid__col--12">

		<nav id="top-bar">

			<span class="burger--maximise"><span></span></span>
			<p id="top-bar__title"><a href="dashboard.html">Enterprise portal</a></p>

			<ul class="top-bar__utils">

				
				<li class="top-bar__util drawer-trigger" data-ot="Your profile" data-drawer="drawer-user">
					<a href="#" class="space-next"><i class="material-icons">person</i>Hi, FirstName LastName</a>
				</li>

				<li class="top-bar__util" data-ot="Logout">
					<a href="#"><i class="material-icons">power_settings_new</i></a>
				</li>

			</ul>

		</nav>

	</div>

</div> -->
<!-- end: top bar -->
	<!-- start: main nav -->
<!-- <nav id="main-nav" class="main-nav--minimised">

	<ul>

		<li id="main-nav--dashboard" class="main-nav__item">
			<a href="#">
				<i class="material-icons main-nav__icon">view_module</i>
				<span class="main-nav__text">Dashboard</span>
			</a>
			<nav class="main-nav__sub-nav">
				<ul>
					<li class="sub-nav__title">Dashboards</li>
					<li class="sub-nav__item">
						<a href="/dashboard-compliance.html">Compliance</a>
					</li>
					<li class="sub-nav__item">
						<a href="/dashboard-banking.html">Banking</a>
					</li>
					<li class="sub-nav__item space-after">
						<a href="/dashboard-b2b.html">B2B</a>
					</li>
				</ul>
			</nav>
		</li>

		<li id="main-nav--compliance" class="main-nav__item--on">
			<a href="#">
				<i class="material-icons main-nav__icon">check_circle</i>
				<span class="main-nav__text">Compliance</span>
			</a>
			<nav class="main-nav__sub-nav">
				<ul>
					<li class="sub-nav__title">Compliance</li>
					<li class="sub-nav__sub-title">Queues</li>
					<li class="sub-nav__item">
						<a href="/compliance-registration-queue.html">Registrations</a>
					</li>
					<li class="sub-nav__item">
						<a href="/compliance-payments-in-queue.html">Payments in</a>
					</li>
					<li class="sub-nav__item--on space-after">
						<a href="/compliance-payments-out-queue.html">Payments out</a>
					</li>
					<li class="sub-nav__sub-title">Reports</li>
					<li class="sub-nav__item">
						<a href="/compliance-registration-report.html">Registrations</a>
					</li>
					<li class="sub-nav__item">
						<a href="/compliance-payments-in-report.html">Payments in</a>
					</li>
					<li class="sub-nav__item">
						<a href="/compliance-payments-out-report.html">Payments out</a>
					</li>
				</ul>
			</nav>
		</li>

		<li id="main-nav--banking" class="main-nav__item">
			<a href="/banking.html">
				<i class="material-icons main-nav__icon">account_balance</i>
				<span class="main-nav__text">Banking</span>
			</a>
		</li>

		<li id="main-nav--b2b" class="main-nav__item">
			<a href="#">
				<i class="material-icons main-nav__icon">business</i>
				<span class="main-nav__text">B2B</span>
			</a>
		</li>

	</ul>

</nav> -->

<!-- end: main nav -->
	<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<h1>
						Payments out queue

						<span class="breadcrumbs">

	<span class="breadcrumbs__crumb--in">in</span>
	<span class="breadcrumbs__crumb--area">Compliance </span>

	
</span>					</h1>


<c:choose>
						<c:when test="${not empty paymentOutQueueDto.errorMessage}">
						   Error : ${paymentOutQueueDto.errorMessage}
						</c:when>
						<c:otherwise>
					<div id="main-content__body">

						<div class="grid">
							
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
								<form id="payOutQueueForm" action="/compliance-portal/paymentOutDetail" method="POST" >
								<table class="micro table-fix space-after">
	<thead>
		<tr>
			
			<th id ="col_Transactions"><a href="#">Transaction #</a></th>
			<th id ="col_tradeaccountnumber" class="sorted desc"><a href="#" onclick="sortByField('tradeaccountnumber')">Client #<i></i></a></th>
			<th id="col_UpdatedOn" class="sorted desc default-sort-desc"><a href="#" onclick="sortByField('UpdatedOn')" >Date<i></i></a></th>
			<th id ="col_ClientName"><a href="#">Client name</a></th>
			<th id ="col_Organisation"><a href="#">Organisation</a></th>
			<th id ="col_LegalEntity"><a href="#">Legal Entity</a></th>
			<th id ="col_vMaturityDate" class="sorted desc" ><a href="#" onclick="sortByField('vMaturityDate')">Maturity Date<i></i></a></th>
			<th id ="col_type" class="sorted desc" onclick="sortByField('type')"><a href="#">Type<i></i></a></th>
			
			<th id ="col_Buy"><a href="#">Buy</a></th>
			<th id ="col_Amount"><a href="#">Amount</a></th>
			<th id ="col_Beneficiary"><a href="#">Beneficiary</a></th>
			<th id ="col_Country"><a href="#">Country</a></th>
			<th id ="col_OverAllStatus" class="breakwordHeader"><a href="#">Overall status</a></th>
			
			<th class="center" data-ot="Watchlist">W</th>
			<th class="center" data-ot="FraudPredict">F</th>
			<th class="center" data-ot="Sanction">S</th>
			<th class="center"  data-ot="Blacklist">B</th>
			<th class="center"  data-ot="Custom Check">C</th>
			<th class="center"  data-ot="Intuition Status">I</th> <!-- AT-4607 -->
		</tr>
	</thead>
	
	<tbody id="payOutQueueBody">

										<c:forEach var="listValue"
											items="${paymentOutQueueDto.paymentOutQueue}"  varStatus="loop">
											<%-- <c:set var="regDetailsUrl"
												value="/compliance-portal/paymentOutDetails?paymentOutId=${listValue.paymentOutId}" /> --%>
												<c:choose>                                                    
												 	<c:when test="${listValue.locked  && listValue.lockedBy==paymentOutQueueDto.user.name}">
												 		<tr class="owned talign" data-ot="You own(s) this record">
												 	</c:when>
												 	<c:when test="${listValue.locked }">
												 		<tr class="talign unavailable" data-ot="${listValue.lockedBy } own(s) this record">
												 	</c:when>
												 	<c:otherwise>
												 		<tr class="available talign">
												 	</c:otherwise>
												</c:choose>
											    <%-- <td hidden="hidden"><a onClick="getPaymentOutQueueDetails(${listValue.contractNumber},this)" href="#"  >${loop.index + 1}</a></td> --%>
												<td class="number"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#"  >${listValue.transactionId}</a></td>
												<td class="nowrap number"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)"href="#"  >${listValue.clientId}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.date}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.contactName}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.organisation}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.legalEntity}</a></td>
												<%-- <td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.valueDate}</a></td> --%>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.maturityDate}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.type}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.buyCurrency}</a></td>
												<td class="number"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.amount}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.beneficiary}</a></td>
												<td class="wordwrapfixwidth"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.country}</a></td>
												<td><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" href="#" >${listValue.overallStatus}</a></td>
										
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.watchlist,'WATCH_LIST' )}">
														<td class="amber-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.fraugster,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${(fn:containsIgnoreCase(listValue.sanction,'PASS')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'PASS') || fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_REQUIRED') || fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_PERFORMED'))}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													 <c:when test="${(fn:containsIgnoreCase(listValue.sanction,'NOT_REQUIRED')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'FAIL'))}">
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when> 
													<c:when test="${(fn:containsIgnoreCase(listValue.sanction,'FAIL') || fn:containsIgnoreCase(listValue.sanction,'SERVICEFAILURE')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'PASS'))}">
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when>
													<c:when test="${(fn:containsIgnoreCase(listValue.sanction,'PASS')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'FAIL') || fn:containsIgnoreCase(listValue.blacklistPayRef,'SERVICEFAILURE'))}">
														<td class="amber-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when>
													<c:when test="${(fn:containsIgnoreCase(listValue.sanction,'FAIL') || fn:containsIgnoreCase(listValue.sanction,'SERVICEFAILURE')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'FAIL')|| fn:containsIgnoreCase(listValue.blacklistPayRef,'SERVICEFAILURE') || fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_PERFORMED') || fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_REQUIRED'))}">
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:when>
													<c:when test="${(fn:containsIgnoreCase(listValue.sanction,'NOT_REQUIRED') || fn:containsIgnoreCase(listValue.sanction,'NOT_PERFORMED')) && (fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_REQUIRED')|| fn:containsIgnoreCase(listValue.blacklistPayRef,'NOT_PERFORMED'))}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
												</c:choose>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.blacklist,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<!-- Added custom check status column , foxed issue-AT464: Abhijit G -->
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.customCheck,'NOT_REQUIRED' )}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
												<!-- Added intuition check status column , AT-4607 -->
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(listValue.intuitionStatus,'PASS' )}">
														<td class="yes-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">check</i></a></td>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(listValue.intuitionStatus,'NOT_REQUIRED') || 
															 listValue.intuitionStatus == null}">
														<td class="na-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">not_interested</i></a></td>
													</c:when>
													<c:otherwise>
														<td class="no-cell"><a onClick="getPaymentOutQueueDetails(${listValue.paymentOutId},'${listValue.type}',this)" 
															href="#" target="_blank"><i
																class="material-icons">clear</i></a></td>
													</c:otherwise>
												</c:choose>
											<!-- </tr> -->
										</c:forEach>
									
									</tbody>
							</table>
							<input type="hidden" id="paymentOutId" value="" name="paymentOutId"/>
							<input type="hidden" id="searchCriteria" value="" name="searchCriteria"/>
							<input type="hidden" id="custType" value="" name="custType"/>
							<input type="hidden" id="source" value="queue" name="source"/>
							
						</form>

<!-- <p>Showing <strong>1 - 50</strong> of <strong>234</strong> records</p> -->
								<p id="pageCountDetails">
									Showing
									<c:choose>
										<c:when test="${paymentOutQueueDto.page.totalRecords ge paymentOutQueueDto.page.pageSize}">
											<strong id="queueMinRecord">${paymentOutQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${paymentOutQueueDto.page.maxRecord}</strong>
										</c:when>
										<c:otherwise>
										     <strong id="queueMinRecord">${paymentOutQueueDto.page.minRecord}</strong> <strong>-</strong> <strong id="queueMaxRecord">${paymentOutQueueDto.page.maxRecord}</strong>
										</c:otherwise>
									</c:choose>
									of <strong id="queueTotalRecords">${paymentOutQueueDto.page.totalRecords}</strong>
									records
								</p>
								<input type="hidden" id="queueTotalPages" value="${paymentOutQueueDto.page.totalPages}"></input>
								<ul class="horizontal containing pagination" id="paginationBlock">
								<c:if test="${paymentOutQueueDto.page.totalPages gt 1 }">
									
										<li onclick="getSelectedPage(1)" class="pagination__jump--disabled" value="1"><a id="firstPage" href="#"
											title="First page"><i class="material-icons">first_page</i></a>
										</li>
										<li onClick="getPreviousPage()" class="pagination__jump"><a id="previousPage" href="#"
											title="Previous page"><i class="material-icons">navigate_before</i></a>
										</li>
										<li onClick="getNextPage()" class="pagination__jump"><a id="nextPage" href="#"
											title="Next page"><i class="material-icons">navigate_next</i></a></li>
										<li  onClick="getSelectedPage(${paymentOutQueueDto.page.totalPages})" class="pagination__jump" value="${paymentOutQueueDto.page.totalPages}"><a id="lastPage" href="#"
											title="Last page"><i class="material-icons">last_page</i></a></li>
										<%-- <c:forEach var="i" begin="1"
											end="${paymentOutQueueDto.page.totalPages}">
											<c:choose>
												<c:when test="${i eq 1}">
													<li id="page${i }" onClick="getSelectedPage(${ i })" class="pagination__page--on" value="${ i }"><a href="#"
														title="Page ${i }">${i }</a></li>
												</c:when>
												<c:otherwise>
												
													<c:choose>
														<c:when test="${i ge 11 && paymentOutQueueDto.page.totalPages ne i}">
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
<!-- <ul class="horizontal containing pagination">
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
	<li class="pagination__page">
		<a href="#" title="Page 11">11</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 12">12</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 13">13</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 14">14</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 15">15</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 16">16</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 17">17</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 18">18</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 19">19</a>
	</li>
	<li class="pagination__page">
		<a href="#" title="Page 20">20</a>
	</li>
</ul> -->
								<span class="annex-tab annex-trigger">
									<i class="material-icons">filter_list</i>
								</span>
								
							</div>
							<form class="frm-table-filters" id="queueFilterForm" autocomplete=off>
							<div class="grid-annex-side grid__col--3 hidden">

								<div class="annex annex--hidden">

									<h2>
										Filter queue
										<span class="f-right annex-trigger"><i class="material-icons">close</i></span>
									</h2>

									<div class="pagepanel__content boxpanel--shadow--splits">
									<div id="addedFilters">
									</div>
										<%-- <form class="form"  id="queueFilterForm"> --%>

											<!-- <h2 class="hidden">Enter search criteria</h2>

											<section class="section--results">

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
			              													<c:forEach var="savedSearch" items="${paymentOutQueueDto.savedSearch.savedSearchData}" varStatus="loop">
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
																	for="txt-search-keyword"
																	class="label filterInputHeader" >Keyword</label> <input
																	id="txt-search-keyword" type="text" name="keyword"
																	placeholder="Client number, name etc..." 
																	data-ot="For Contract Number, type contractNo:3111I264697-003
																		<br/>For Reason of Transfer, type rot:HOUSE LOAN
																		<br/>For Amount, type amt:2300
																		<br/>For Benificiary Name, type bene:Rahul Sharma"	
																		></li>
													</ul>
												</fieldset>

												<h3>Select filters</h3>

												<fieldset>

													<ul class="form__fields">

	<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader">Organisations</p>

		<div id="multilist-organisations" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>

			<ul class="multilist__options">

				<c:forEach var="organization" items="${paymentOutQueueDto.organization}"  varStatus="loop">
				
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

			<ul class="multilist__options">

				<c:forEach var="legalEntity" items="${paymentOutQueueDto.legalEntity}"  varStatus="loop">
				
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

				<c:forEach var="owner" items="${paymentOutQueueDto.owner}"  varStatus="loop">
					
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
					<label for="date-payment-from"class="filterInputHeader">Date from</label>
					<input id="date-payment-from" name="dateFrom" class="datepicker filterInput" type="text"  value="${paymentOutQueueDto.dateFrom}" placeholder="dd/mm/yyyy"/>
				</div>
				<div class="grid__col--6 filterInputWrap">
					<label for="date-payment-to"class="filterInputHeader">Date to</label>
					<input id="date-payment-to" name="dateTo" class="datepicker filterInput" type="text" value="${paymentOutQueueDto.dateTo}" placeholder="dd/mm/yyyy"/>
				</div>
			</div>
		</div>
	</li>
	
	<li class="form__field-wrap">

		<div class="grid">
			<div class="grid__row">
			<label>Maturity Date</label>
				<div class="grid__col--6 filterInputWrap">
				<label style="display:none;" for="value-date-payment-from" class="filterInputHeader">Value Date from</label>
					<label >from</label>
					<input id="value-date-payment-from" name="valueDateFrom" class="datepicker filterInput" type="text"  value="" placeholder="dd/mm/yyyy"/>
				</div>
				<div class="grid__col--6 filterInputWrap">
				<label style="display:none;" for="value-date-payment-to" class="filterInputHeader">Value Date to</label>
					<label>to</label>
					<input id="value-date-payment-to" name="valueDateTo" class="datepicker filterInput" type="text" value="" placeholder="dd/mm/yyyy"/>
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
				<input  name="custType[]" id="rad-type-pfx" type="checkbox" value="PFX" class="filterInput">
				PFX			</label>
			
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx">
				<input name="custType[]" id="rad-type-cfx" type="checkbox"  value="CFX" class="filterInput" />
				CFX			</label>
		</li>
				<li>
			<label class="pill-choice__choice cfx-field" for="rad-type-cfx-etailer">
				<input name="custType[]" id="rad-type-cfx-etailer" type="checkbox" value="CFX (etailer)" class="filterInput"   />
				CFX (etailer)			</label>
		</li>
			</ul>

</fieldset>	</li>

	<li class="form__field-wrap filterInputWrap">

		<p class="label filterInputHeader">Buy currency</p>

		<div id="multilist-buy-currency" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListBuyCurrencyId" class="multilist__search space-after" type="search" placeholder="Search list">
				<ul class="multilist__options">
									
					<c:forEach var="currency" items="${paymentOutQueueDto.currency}"  varStatus="loop">
						<li>
							<label  for="chk-buy-currency-${loop.index}">
								<input name="buyCurrency[]" class="filterInput" id="chk-buy-currency-${loop.index}"
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

		<p class="label filterInputHeader">Country of beneficiary</p>

		<div id="multilist-country-of-fund" class="multilist clickpanel--right">

	<ul class="multilist__chosen">
		<li class="clickpanel__trigger">Please select</li>
	</ul>

	<i class="clickpanel__drop-arrow material-icons">keyboard_arrow_down</i>

	<div class="clickpanel__content--hidden">

		<fieldset>
			<input id="filterSearchListCountryOfBeneId" class="multilist__search space-after" type="search" placeholder="Search list">
			<ul class="multilist__options">

				<c:forEach var="country" items="${paymentOutQueueDto.country}" varStatus="loop">
				
					<li>
						<label  for="chk-country-${loop.index}">
					    	<input name="countryOfBeneficiary[]" class="filterInput" id="chk-country-${loop.index}"
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

	<li id="pay-out-service__status-filter" class="form__field-wrap">

		<div class="grid--micro">

			<div class="grid__row">

				<div class="grid__col--6 grid__col--pad-bottom watchlist-field"">
					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">Watchlist</legend>
	
	<ul id="pillchoice-watchlist" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-watchlist-passed">
				<input name="watchListStatus[]" value="PASS" id="rad-watchlist-passed"  value="PASS" type="radio" class="filterInput"   />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-watchlist-failed">
				<input name="watchListStatus[]" value="FAIL" id="rad-watchlist-failed"  value="FAIL" type="radio" class="filterInput"   />
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
				<input name="fraugsterStatus[]" value="PASS" id="rad-fraugster-passed" type="radio" class="filterInput"    />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-fraugster-failed">
				<input name="fraugsterStatus[]" value="FAIL" id="rad-fraugster-failed" type="radio" class="filterInput"    />
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
				<input name="sanctionStatus[]" value="PASS" id="rad-sanction-passed" type="radio" class="filterInput"    />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-sanction-failed">
				<input name="sanctionStatus[]" value="FAIL" id="rad-sanction-failed" type="radio" class="filterInput"    />
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
				<input name="blacklistStatus[]" id="rad-blacklist-passed"  value="PASS" type="radio" class="filterInput"   />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-blacklist-failed">
				<input name="blacklistStatus[]" id="rad-blacklist-failed"  value="FAIL" type="radio" class="filterInput"   />
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
				
				<!--Added for AT-4614-->
					<div class="grid__col--6 grid__col--pad-bottom custom-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">Intuition Check</legend>
	
	<ul id="pillchoice-intuitionStatus" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-intuitionStatus-passed">
				<input name="intuitionStatus[]" id="rad-intuitionStatus-passed"  value="PASS" type="radio" class="filterInput"   />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-intuitionStatus-failed">
				<input name="intuitionStatus[]" id="rad-intuitionStatus-failed"  value="FAIL" type="radio" class="filterInput"   />
				<i class="material-icons">close</i>			</label>
		</li>
			</ul>

</fieldset>
				</div>
				
				<!-- Added for AT-4656 -->
					<div class="grid__col--6 grid__col--pad-bottom transactionMonitoringRequest-field">

					<fieldset class="filterInputWrap">

		<legend class="label filterInputHeader">TM request</legend>
	
	<ul id="pillchoice-transactionMonitoringRequest" class="pill-choice">
				<li>
			<label class="pill-choice__choice--positive" for="rad-transactionMonitoringRequest-passed">
				<input name="transactionMonitoringRequest[]" id="rad-transactionMonitoringRequest-passed"  value="PASS" type="radio" class="filterInput"   />
				<i class="material-icons">check</i>			</label>
		</li>
				<li>
			<label class="pill-choice__choice--negative" for="rad-transactionMonitoringRequest-failed">
				<input name="transactionMonitoringRequest[]" id="rad-transactionMonitoringRequest-failed"  value="FAIL" type="radio" class="filterInput"   />
				<i class="material-icons">close</i>			</label>
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
												<input id="payOut_queue_Filter" type="button" class="button--primary button--small" onclick="applyPaymentOutSearchCriteria()" value="GENERATE"/>
												<!-- <img id="ajax-loader-search" class="ajax-loader space-prev" src="/img/ajax-loader.svg" width="20" height="20" alt="Loading..."/> -->
												<input type="button" id="save_search_button" class="button--primary button--small" value="Save" onclick="saveSearchPopup()"/>
												<input type="button" id="delete_search_button" class="button--primary button--small" value="Delete" onclick="deleteSaveSearchPopup()"/>	
												<object id="gifloaderforPayoutqueue" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;"></object>
											</section>

										<%-- </form> --%>

									</div>

								</div>

							</div>
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

</div>
	<input type="hidden" id="searchCriteriaQueue" value='${paymentOutQueueDto.searchCriteria}' />
	<input type="hidden" id="currentpage" value=""/>
	<input type="hidden" id="pageType" value="PaymentOutQueue"/>
	
	</body>

</html>