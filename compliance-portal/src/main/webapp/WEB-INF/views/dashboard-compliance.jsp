<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html lang="en">

	<head>
		<meta charset="utf-8"/>
		<title>Enterprise tools</title>
		<meta name="description" content="Enterprise tools"/>
		<meta name="copyright" content="Currencies Direct"/>
		
		<link rel="stylesheet" type="text/css" href="resources/css/cd.css"/>
		<link rel="stylesheet" type="text/css" href="resources/css/pages/cd.page.dashboard.css"/>
		
			</head>

	<body>

<div id="master-grid" class="grid">

	<main id="main-content" class="main-content--large">

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--12">

					<div class="grid">

						<div class="grid__row">

							<div class="grid__col--6">

								<h1>
									Dashboard

									<span class="breadcrumbs">

	<span class="breadcrumbs__crumb--in">in</span>
	<span class="breadcrumbs__crumb--area">Compliance</span>

	
</span>
								</h1>

							</div>

							<div class="grid__col--6">

								<div class="toast">

<form id="dashboardRefreshForm" action="/compliance-portal/" method="GET">
	<span class="message--toast rhs page-load">

		
					Last updated @ ${dashboard.refreshOn} (<a onclick="postRefreshDashboard();" href="#">Refresh</a>)		
	</span>
</form>
</div>
							</div>

						</div>

					</div>

	<div id="page-registration" class="page--on">

		<div id="main-content__body">

			<h2><a href="/compliance-portal/reg">${dashboard.regDashboard.totalRegRecords} registration records</a></h2>

				<div class="grid">

					<div class="grid__row">

						<div class="grid__col--6 grid__col--pad-right-even">

<!-- REGISTRATION PFX DASHBOARD BEGIN -->		
						 	<div class="boxpanel--shadow space-after">

								<form id="regPfxQueueForm" action="/compliance-portal/regQueueWithCriteria" method="POST">
								<h3 class="center">
								
									<%-- <a href="compliance-registration-queue.html">${dashboard.regDashboard.totalPfxRecords} PFX records</a> --%>
									<a onclick="applyDashboardRegQueueSearchCriteria('PFX');">${dashboard.regDashboard.totalPfxRecords} PFX records</a>
												(${dashboard.regDashboard.percentPfxRecords}% of queue)
								
									</h3>
									<input type="hidden" id="custType" value="" name="custType"/>
									</form>

										<div class="boxpanel--splits">

											<section class="center">

												<h4>PFX registration by geography</h4>

												<div id="map-reg-pfx-geography"></div>
													<div class="grid-annex-main--scroller" style="height:100px;">
											<table class="micro">
												<thead>
													<tr>
														<th>Country</th>
														<th>Number in queue</th>
													</tr>
												</thead>
												<tbody>
										<c:forEach var="pfxQueueRecord" items="${dashboard.regDashboard.regPfxByGeography.queueRecordsPerCountry}">
												<tr>
												<td>${pfxQueueRecord.countryName}</td>
												<td class="number tight-cell">${pfxQueueRecord.value}</td>
												</tr>
											</c:forEach>	
											<!-- 	<tr>
													<td>Australia</td>
													<td class="number tight-cell">100</td>
												</tr>
												<tr>
													<td>Chile</td>
													<td class="number tight-cell">90</td>
												</tr>
												<tr>
													<td>Algeria</td>
													<td class="number tight-cell">10</td>
												</tr> -->
											</tbody>
										</table>
									</div>
									</section>

								<section class="center">
								
									<h4>PFX registration by legal entity</h4>
								
									<div id="bar-reg-pfx-business"></div>
								
								</section>
								
<section class="center">

<h4>PFX registration fulfilment (Today)</h4>

	<div id="donut-reg-pfx-fulfilment"></div>

		<div class="grid">

			<div class="grid__row">

				<div class="grid__col--4">

					<h5>Average clearing time</h5>

					<p class="dash-number">${dashboard.regDashboard.regPfxFulfilment.avgClearingTime} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Average per hour</h5>

				<p class="dash-number">${dashboard.regDashboard.regPfxFulfilment.avgPerHour} <span class="dash-number__text">record</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Cleared today</h5>

				<p class="dash-number">${dashboard.regDashboard.regPfxFulfilment.clearedToday} <span class="dash-number__text">records</span></p>

			</div>

		</div>

	</div>

</section>


<section class="center">

	<h4>PFX registration timeline snapshot</h4>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">
				<form id="regDetailForm" action="/compliance-portal/registrationDashboardDetails" method="POST">
				<h5>
					<!-- <a href="#">Oldest record</a> -->
					<a onclick="getDashboardRegDetail('${dashboard.regDashboard.regPfxTimelineSnapshot.oldPfxContactId}','PFX','null');">Oldest record
					</a>
				</h5>	
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="custTypes" value="" name="custType"/>
			<input type="hidden" id="searchCriteria" value="" name="searchCriteria">
			</form>
			${dashboard.regDashboard.regPfxTimelineSnapshot.oldestRecord} <span class="dash-number__text">days</span>
		</div>

			<div class="grid__col--4">

				<h5>Average record age</h5>

				<p class="dash-number">${dashboard.regDashboard.regPfxTimelineSnapshot.avgRecordAge} <span class="dash-number__text">days</span></p>

			</div>

			<div class="grid__col--4">
				<form id="regDetailForm" action="/compliance-portal/registrationDashboardDetails" method="POST">
				<h5>
					<a onclick="getDashboardRegDetail('${dashboard.regDashboard.regPfxTimelineSnapshot.newPfxContactId}','PFX','null');">Newest record
					</a>
				</h5>	
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="custTypes" value="" name="custType"/>
			<input type="hidden" id="searchCriteria" value="" name="searchCriteria">
			</form>
				${dashboard.regDashboard.regPfxTimelineSnapshot.newestRecord} <span class="dash-number__text">day</span>

			</div>

		</div>

	</div>

</section>
						</div>

					</div>
<!-- REGISTRATION PFX DASHBOARD ENDED -->					

<!-- PAYMENT IN DASHBOARD BEGIN -->
	<h2>
		<a href="/compliance-portal/paymentInQueue">${dashboard.paymentInDashboard.totalRecords} payments in records</a>
	</h2>

	<div class="boxpanel--shadow--splits">

		<section class="center">

			<h3>Payments in by legal entity</h3>

				<div id="bar-pay-in-business"></div>

		</section>

<section class="center">

	<h3>Payments in fulfilment (Today)</h3>

	<div id="donut-pay-in-fulfilment"></div>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">

				<h5>Average clearing time</h5>

				<p class="dash-number">${dashboard.paymentInDashboard.fulfilment.avgClearingTime} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Average per hour</h5>

				<p class="dash-number">${dashboard.paymentInDashboard.fulfilment.avgPerHour} <span class="dash-number__text">record</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Cleared today</h5>

				<p class="dash-number">${dashboard.paymentInDashboard.fulfilment.clearedToday} <span class="dash-number__text">records</span></p>

			</div>

		</div>

	</div>

</section>
		
<section class="center">

	<h3>Payments in timeline snapshot</h3>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">
				<form id="paymentInDetailForm" action="/compliance-portal/paymentInDashboardDetails" method="POST">
				<h5>
					<a onclick="getDashboardPaymentInDetail('${dashboard.paymentInDashboard.timelineSnapshot.oldPfxContactId}','${dashboard.paymentInDashboard.timelineSnapshot.oldCustomerType}','null');">Oldest record
					</a>
				</h5>	
			<input type="hidden" id="paymentInId" value="" name="paymentInId"/>
			<input type="hidden" id="payIncustType" value="" name="custType"/>
			<input type="hidden" id="searchCriterias" value="" name="searchCriteria">
			</form>
				${dashboard.paymentInDashboard.timelineSnapshot.oldestRecord} <span class="dash-number__text">minutes</span>

			</div>

			<div class="grid__col--4">

				<h5>Average record age</h5>

				<p class="dash-number">${dashboard.paymentInDashboard.timelineSnapshot.oldestRecord} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">
				<form id="paymentInDetailForm" action="/compliance-portal/paymentInDashboardDetails" method="POST">
				<h5>
					<a onclick="getDashboardPaymentInDetail('${dashboard.paymentInDashboard.timelineSnapshot.newPfxContactId}','${dashboard.paymentInDashboard.timelineSnapshot.newCustomerType}','null');">Newest record
					</a>
				</h5>	
			<input type="hidden" id="paymentInId" value="" name="paymentInId"/>
			<input type="hidden" id="payIncustType" value="" name="custType"/>
			<input type="hidden" id="searchCriterias" value="" name="searchCriteria">
			</form>
				${dashboard.paymentInDashboard.timelineSnapshot.newestRecord} <span class="dash-number__text">minute</span>

			</div>

		</div>

	</div>

</section>

										</div>

									</div>
<!-- PAYMENT IN DASHBOARD ENDED -->		
									
<!-- REGISTRATION CFX DASHBOARD BEGIN -->
				<div class="grid__col--6 grid__col--pad-left-even">

					<div class="boxpanel--shadow space-after">

						<h3 class="center">
							<%-- <a href="compliance-registration-queue.html">${dashboard.regDashboard.totalCfxRecords} CFX records</a> --%>
							<a onclick="applyDashboardRegQueueSearchCriteria('CFX');" >${dashboard.regDashboard.totalCfxRecords} CFX records</a>
												(${dashboard.regDashboard.percentCfxRecords}% of queue)
						</h3>

					<div class="boxpanel--splits">

			<section class="center">

				<h4>CFX registration by geography</h4>

					<div id="map-reg-cfx-geography"></div>
						<div class="grid-annex-main--scroller" style="height:100px;">
							<table class="micro">
								<thead>
									<tr>
										<th>Country</th>
										<th>Number in queue</th>
									</tr>
							</thead>
							<tbody>
								<c:forEach var="cfxQueueRecord" items="${dashboard.regDashboard.regCfxByGeography.queueRecordsPerCountry}">
										<tr>
											<td>${cfxQueueRecord.countryName}</td>
											<td class="number tight-cell">${cfxQueueRecord.value}</td>
										</tr>
								</c:forEach>
								<!-- 	<tr>
										<td>Australia</td>
										<td class="number tight-cell">100</td>
									</tr>
									<tr>
										<td>Chile</td>
										<td class="number tight-cell">90</td>
									</tr>
									<tr>
										<td>Algeria</td>
										<td class="number tight-cell">10</td>
									</tr> -->
							</tbody>
						</table>
					</div>

				</section>
		
	<section class="center">

		<h4>CFX registration by legal entity</h4>

		<div id="bar-reg-cfx-business"></div>

	</section>
												<section class="center">

	<h4>CFX registration fulfilment (Today)</h4>

	<div id="donut-reg-cfx-fulfilment"></div>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">

				<h5>Average clearing time</h5>

				<p class="dash-number">${dashboard.regDashboard.regCfxFulfilment.avgClearingTime} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Average per hour</h5>

				<p class="dash-number">${dashboard.regDashboard.regCfxFulfilment.avgPerHour} <span class="dash-number__text">record</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Cleared today</h5>

				<p class="dash-number">${dashboard.regDashboard.regCfxFulfilment.clearedToday} <span class="dash-number__text">records</span></p>

			</div>

		</div>

	</div>

</section>

<section class="center">

	<h4>CFX registration timeline snapshot</h4>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">
				<form id="regDetailForm" action="/compliance-portal/registrationDashboardDetails" method="POST">
				<h5>
					<a onclick="getDashboardRegDetail('${dashboard.regDashboard.regCfxTimelineSnapshot.oldCfxContactId}','CFX','null');">Oldest record
					</a>
				</h5>	
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="custTypes" value="" name="custType"/>
			<input type="hidden" id="searchCriteria" value="" name="searchCriteria">
			</form>
				${dashboard.regDashboard.regCfxTimelineSnapshot.oldestRecord} <span class="dash-number__text">days</span>

			</div>

			<div class="grid__col--4">

				<h5>Average record age</h5>

				<p class="dash-number">${dashboard.regDashboard.regCfxTimelineSnapshot.avgRecordAge} <span class="dash-number__text">days</span></p>

			</div>

			<div class="grid__col--4">
				<form id="regDetailForm" action="/compliance-portal/registrationDashboardDetails" method="POST">
				<h5>
					<a onclick="getDashboardRegDetail('${dashboard.regDashboard.regCfxTimelineSnapshot.newCfxContactId}','CFX','null');">Newest record
					</a>
				</h5>	
			<input type="hidden" id="contactId" value="" name="contactId"/>
			<input type="hidden" id="custTypes" value="" name="custType"/>
			<input type="hidden" id="searchCriteria" value="" name="searchCriteria">
			</form>
				${dashboard.regDashboard.regCfxTimelineSnapshot.newestRecord} <span class="dash-number__text">day</span>

			</div>

		</div>

	</div>

</section>
							</div>

						</div>
<!-- REGISTRATION CFX DASHBOARD ENDED -->

<!-- PAYMENT OUT DASHBOARD BEGIN -->
			<h2>
				<a href="/compliance-portal/payOutQueue">${dashboard.paymentOutDashboard.totalRecords} payments out records</a>
			</h2>

			<div class="boxpanel--shadow--splits">

	<section class="center">

		<h3>Payments out by legal entity</h3>

		<div id="bar-pay-out-business"></div>

	</section>

<section class="center">

	<h3>Payments out fulfilment (Today)</h3>

	<div id="donut-pay-out-fulfilment"></div>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">

				<h5>Average clearing time</h5>

				<p class="dash-number">${dashboard.paymentOutDashboard.fulfilment.avgClearingTime} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Average per hour</h5>

				<p class="dash-number">${dashboard.paymentOutDashboard.fulfilment.avgPerHour} <span class="dash-number__text">record</span></p>

			</div>

			<div class="grid__col--4">

				<h5>Cleared today</h5>

				<p class="dash-number">${dashboard.paymentOutDashboard.fulfilment.clearedToday} <span class="dash-number__text">records</span></p>

			</div>

		</div>

	</div>

</section>

<section class="center">

	<h3>Payments out timeline snapshot</h3>

	<div class="grid">

		<div class="grid__row">

			<div class="grid__col--4">
				<form id="paymentOutDetailForm" action="/compliance-portal/paymentOutDashboardDetails" method="POST">
				<h5>
					
					<a onclick="getDashboardPaymentOutDetail('${dashboard.paymentOutDashboard.timelineSnapshot.oldPfxContactId}','${dashboard.paymentOutDashboard.timelineSnapshot.oldCustomerType}','null');">Oldest record
					</a>
				</h5>	
			<input type="hidden" id="paymentOutId" value="" name="paymentOutId"/>
			<input type="hidden" id="payOutcustType" value="" name="custType"/>
			<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria">
			</form>
				${dashboard.paymentOutDashboard.timelineSnapshot.oldestRecord} <span class="dash-number__text">minutes</span>
			</div>

			<div class="grid__col--4">

				<h5>Average record age</h5>

				<p class="dash-number">${dashboard.paymentOutDashboard.timelineSnapshot.avgRecordAge} <span class="dash-number__text">minutes</span></p>

			</div>

			<div class="grid__col--4">

				<form id="paymentOutDetailForm" action="/compliance-portal/paymentOutDashboardDetails" method="POST">
				<h5>
					
					<a onclick="getDashboardPaymentOutDetail('${dashboard.paymentOutDashboard.timelineSnapshot.newPfxContactId}','${dashboard.paymentOutDashboard.timelineSnapshot.newCustomerType}','null');">Newest record
					</a>
				</h5>	
				<input type="hidden" id="paymentOutId" value="" name="paymentOutId"/>
				<input type="hidden" id="payOutcustType" value="" name="custType"/>
				<input type="hidden" id="searchSortCriteria" value="" name="searchCriteria">
				</form>

				${dashboard.paymentOutDashboard.timelineSnapshot.newestRecord} <span class="dash-number__text">minute</span>

			</div>

		</div>

	</div>

</section>
										</div>
<!-- PAYMENT OUT DASHBOARD ENDED -->										

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

	<input type="hidden" id="regPfxByGeographyJsonStringId" value='${dashboard.regDashboard.regPfxByGeographyJsonString}'/>
	<input type="hidden" id="regCfxByGeographyJsonStringId" value='${dashboard.regDashboard.regCfxByGeographyJsonString}'/>
	<input type="hidden" id="regPfxByBusinessUnitJsonStringId" value='${dashboard.regDashboard.regPfxByBusinessUnitJsonString}'/>
	<input type="hidden" id="regCfxByBusinessUnitJsonStringId" value='${dashboard.regDashboard.regCfxByBusinessUnitJsonString}'/>
	<input type="hidden" id="regPfxFulfilmentJsonStringId" value='${dashboard.regDashboard.regPfxFulfilmentJsonString}'/>
	<input type="hidden" id="regCfxFulfilmentJsonStringId" value='${dashboard.regDashboard.regCfxFulfilmentJsonString}'/>
	<input type="hidden" id="paymentInBusinessUnitJsonStringId" value='${dashboard.paymentInDashboard.businessUnitJsonString}'/>
	<input type="hidden" id="paymentOutBusinessUnitJsonStringId" value='${dashboard.paymentOutDashboard.businessUnitJsonString}'/>
	<input type="hidden" id="paymentInFulfilmentJsonStringId" value='${dashboard.paymentInDashboard.fulfilmentJsonString}'/>
	<input type="hidden" id="paymentOutFulfilmentJsonStringId" value='${dashboard.paymentOutDashboard.fulfilmentJsonString}'/>

		
		
		<script type="text/javascript" src="resources/js/jquery_min.js"></script>
		<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script> 
		<!-- amcharts js are access offline : Abhijit G -->
		<script type="text/javascript" src="resources/js/amcharts/amcharts.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/ammap.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/serial.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/pie.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/light.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/dataloader.min.js"></script>
		<script type="text/javascript" src="resources/js/amcharts/worldLow.js"></script>
		
		<script type="text/javascript" src="resources/js/cd.js"></script>
		<script type="text/javascript" src="resources/js/util.js"></script>
		<script type="text/javascript" src="resources/js/dashboard.js"></script>
	
	
	</body>

</html>