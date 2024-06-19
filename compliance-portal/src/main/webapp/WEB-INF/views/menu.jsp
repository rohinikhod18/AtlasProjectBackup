

<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="utf-8" />
<title>Enterprise tools</title>
<meta name="description" content="Enterprise tools" />
<meta name="copyright" content="Currencies Direct" />
<!-- <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,700,400italic'
	rel='stylesheet' type='text/css'>
<link href="https://fonts.googleapis.com/css?family=Roboto+Slab:300"
	rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Lato'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" type="text/css" href="resources/css/cd.css" />
<link rel="stylesheet" type="text/css"
	href="resources/css/pages/cd.page.compliance.css" /> -->
</head>

<body>
<!-- 	<!-- START: DO NOT COPY THIS DIV. FOR DEVELOPMENT ONLY -->
<!-- 	<div id="dev-notes" class="dev-notes--minimised">
		<i class="material-icons">assignment</i>
		<div class="dev-notes__content dev-notes__close">
			<h3>
				Development notes <i class="material-icons f-right">close</i>
			</h3>
			<h4>Filters</h4>
			<ul>
				<li>Only show filters button in top bar on pages with
					filterable tables.</li>
			</ul>
		</div>
	</div> -->
<!--	END: DO NOT COPY THIS DIV. FOR DEVELOPMENT ONLY -->

	<div id="master-grid" class="grid">

		<!-- start: main nav -->
		<nav id="main-nav" class="main-nav--minimised">

			<ul>
			
				<li id="main-nav--dashboard" class="main-nav__item">
			<a href="#">
				<i class="material-icons main-nav__icon">view_module</i>
				<span class="main-nav__text">Dashboard</span>
			</a>
			<nav class="main-nav__sub-nav">
				<ul>
					<li class="sub-nav__title">Dashboards</li>
					<li id="dashboard-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/">Compliance</a>
					</li>
					<li hidden="hidden" class="sub-nav__item">
						<a href="/compliance-portal/dashboardBanking">Banking</a>
					</li>
				</ul>
			</nav>
		</li>

				<li id="main-nav--compliance" class="main-nav__item--on"><a
					href="#"> <i class="material-icons main-nav__icon">check_circle</i>
						<span class="main-nav__text">Compliance</span>
				</a>
					<nav class="main-nav__sub-nav">
						<ul>
							<li class="sub-nav__title">Compliance</li> <!--  sub-nav__item--on -->
							<li class="sub-nav__sub-title">Queues</li>
							<li id="reg-sub-nav" class="sub-nav__item"><a href="/compliance-portal/reg">Registration</a></li>
							<li id="payIn-sub-nav" class="sub-nav__item"><a
								href="/compliance-portal/paymentInQueue">Payments in</a></li>
							<li id="payOut-sub-nav" class="sub-nav__item"><a
								href="/compliance-portal/payOutQueue">Payments out</a></li>
							<li id="dataAnon-sub-nav" class="sub-nav__item"><a
								href="/compliance-portal/dataAnon">Data Anonymisation</a></li>
							<li class="sub-nav__sub-title">Reports</li>
					<li id="reg-report-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/regReport">Registrations</a>
					</li>
					<li  id="payIn-report-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/paymentInReport">Payments in </a>
					</li>
					<li id="payOut-report-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/paymentOutReport">Payments out </a>
					</li>
					<li id="workEff-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/workEfficiencyReport">Work Efficiency</a>
					</li>
					<li id="holistic-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/holisticViewReport">Holistic View</a>
					</li>
					<li id = "other-nav" class="sub-nav__sub-title">OTHER</li>
					<li id="bene-report-sub-nav" class="sub-nav__item">
						<a href="/compliance-portal/beneReport">Beneficiaries</a>
					</li>
						</ul>
					</nav>
				</li>
				
				<li id="main-nav--banking" class="main-nav__item" hidden="hidden">
			<a href="#">
				<i class="material-icons main-nav__icon">account_balance</i>
				<span class="main-nav__text">Banking</span>
			</a>
			<nav class="main-nav__sub-nav" >
				<ul>
					<li class="sub-nav__title">Banking</li>
					<li class="sub-nav__item">
						<a href="/banking-currency-pairs.html">Currency pairs</a>
					</li>
				</ul>
			</nav>
		</li>

			</ul>

		</nav>
	<input type="hidden" id="payeeSearchCriteria" value="" />
	</div>

	<form id="beneListForm" action="/compliance-portal/beneReport" method="post" >
		<input type="hidden" id="payeeListRequestId" value="" name="payeeListRequest"/>
	</form>
	<!-- end: main nav -->


</body>

</html>