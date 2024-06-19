<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="en">

	<head>
		<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
		<meta name="description" content="Enterprise tools" />
		<meta name="copyright" content="Currencies Direct" />
	
		<link rel="stylesheet" type="text/css" href="resources/css/cd1.css"/>
		<link rel="stylesheet" type="text/css" href="resources/css/cd.page.compliance.min.css"/>
		<link rel="stylesheet" href="resources/css/jquery-ui.css">
	</head>

	<body>


<main class="main-content--large">
		
			<c:set var="redirectUrl" value="/compliance-portal/beneReport"/>
			<c:set var="redirectTo" value="BENEFICIARIES"/>
	<h1>

		${payeeDetailsResponse.payee.fullName}
		
<!-- BEGIN: BREADCRUMBS -->
	<form id="benelist2" action="${redirectUrl }" method="POST">
		<div class="grid__col--9">
		<h1>
		<span class="breadcrumbs"> 
			<span class="breadcrumbs__crumb--in">in</span> 
			<span class="breadcrumbs__crumb--area">Compliance</span>
			<span class="breadcrumbs__crumb"> <a onclick="redirectToPayee();" >${redirectTo }</a></span>
		</span>
		</h1>
		</div>
		<input type="hidden" id="ququefilter" name="ququefilter" value="">
	</form>


<!-- END: BREADCRUMBS -->
	</h1>

	<div class="main-content__wrap">

		
		<div class="main-content__main small">

      <section class="main-content__section">

				<h2 class="hidden">Summary</h2>

				<div id="beneficiary-summary" class="boxpanel summary">
					   <c:forEach var="listValuePaymentMethodList" items="${payeeDetailsResponse.payee.payeePaymentMethodList}"  varStatus="loop">
					<div class="grid">
					  
						<div class="grid__col--3">

							<dl class="split-list">
								<dt>Last paid (by ${listValuePaymentMethodList.payeeBank.accountName})</dt>
								<dd>${listValuePaymentMethodList.payeeBank.updatedOnDateForUI}</dd>

								<dt>Organization</dt>
								<dd>${payeeDetailsResponse.payee.organizationName}</dd>

								<dt>Third Party?</dt>
								<c:choose>
								<c:when
								test="${payeeDetailsResponse.payee.self == 'true'}">
								<dd>No</dd>
								</c:when>
								<c:otherwise>
								<dd>Yes</dd>
								</c:otherwise>
								</c:choose> 

							</dl>

						</div>

						<div class="grid__col--3 grid__col--pad-left">

							<dl class="split-list">

								<dt>Acc. No / IBAN</dt>
								<c:choose>
								<c:when
								test="${listValuePaymentMethodList.payeeBank.accountNumber == null}">
								<c:choose>
								<c:when
								test="${listValuePaymentMethodList.payeeBank.iban == null}">
								<dd>-----</dd>
								</c:when>
								<c:otherwise>
								<dd>${listValuePaymentMethodList.payeeBank.iban}</dd>
								<input type="hidden" id="beneAccNo" value="${listValuePaymentMethodList.payeeBank.iban}">
								</c:otherwise>
								</c:choose> 
								</c:when>
								<c:otherwise>
								<dd>${listValuePaymentMethodList.payeeBank.accountNumber}</dd>
								<input type="hidden" id="beneAccNo" value="${listValuePaymentMethodList.payeeBank.accountNumber}">
								</c:otherwise>
								</c:choose> 
								

								<dt>Swift code</dt>
								<c:choose>
								<c:when
								test="${listValuePaymentMethodList.payeeBank.bankBIC == null}">
								<dd>-----</dd>
								</c:when>
								<c:otherwise>
								<dd>${listValuePaymentMethodList.payeeBank.bankBIC}</dd>
								</c:otherwise>
								</c:choose> 
								

								<dt>CCY</dt>
								<dd>${listValuePaymentMethodList.payeeBank.payeeCurrencyCode}</dd>

							</dl>

						</div>

						<div class="grid__col--2 grid__col--pad-left">

							<dl class="split-list">

								<dt>Country</dt>
								<dd>${payeeDetailsResponse.payee.countryName}</dd>

								<dt>SIC Code</dt>
								<c:choose>
								<c:when
								test="${listValuePaymentMethodList.payeeBank.bankIntraCountryCode == null}">
								<dd>-----</dd>
								</c:when>
								<c:otherwise>
								<dd>${listValuePaymentMethodList.payeeBank.bankIntraCountryCode}</dd>
								</c:otherwise>
								</c:choose> 

								<dt>Industry</dt>
								<dd>-----</dd>

							</dl>

						</div>

						<div class="grid__col--4 grid__col--pad-left">

							<dl class="split-list">

								<dt>Website</dt>
								<dd>-----</dd>

								<!-- <dt>Upload document</dt>
								<dd><input type="file" class="alone"></dd> -->

							</dl>

						</div>
						
				</div>
</c:forEach>
			</div>

		</section>
<!-- <div class="grid__col--2 copy">
		<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('Name')" >Name</a></p>
	</div> -->
	
	<c:choose>
		<c:when test = "${payeeDetailsResponse.isBeneficiaryWhitelisted}">
			<br>
			<div  class="boxpanel summary">
				<textarea id = "beneIsWhiteListed" readonly>
					${payeeDetailsResponse.payee.fullName} is whitelisted
				</textarea>
			</div>
			<form id="beneClientList" action="${redirectUrl }" method="POST">
							<input type="hidden" id="ququefilter" name="ququefilter" value="">
						</form>
						<% String  searchCriteria = request.getParameter("searchCriteria"); %>
					<input type="hidden" id="searchCriteria" name="searchCriteria" value='<%=searchCriteria %>'/>
		</c:when>
		<c:otherwise>
		<c:choose>
		<c:when test = "${empty payeeESReponse.children}">
		<br>
			<div  class="boxpanel summary">
				<textarea id = "beneHasNoTransactions" readonly>
					${payeeDetailsResponse.payee.fullName} has no transactions
				</textarea>
			</div>
			<form id="beneClientList" action="${redirectUrl }" method="POST">
							<input type="hidden" id="ququefilter" name="ququefilter" value="">
						</form>
						<% String  searchCriteria = request.getParameter("searchCriteria"); %>
					<input type="hidden" id="searchCriteria" name="searchCriteria" value='<%=searchCriteria %>'/>
		</c:when>
		<c:otherwise>
			<section class="main-content__section">

			<h3>Relationships and payments to clients</h3>

			<div class="grid">

				<div class="grid__row">

					<div class="grid__col--6 grid__col--pad-right">

						<ul class="key">
							<li class="key__item">
								<span class="key__symbol" style="display:inline-block;width:20px;height:20px;border:2px solid #333;border-radius:100px;background:#fff;"></span>
								<span class="key__label">Beneficiary</span>
							</li>
							<li class="key__item">
								<span class="key__symbol"><img height="20px" alt="Client" src="resources/img/d3-key-client.png"> </span>
								<span class="key__label">Client</span>
							</li>
							<li class="key__item">
								<span class="key__symbol"><img height="20px" alt="Client with shared benes" src="resources/img/d3-key-client-danger.png"></span>
								<span class="key__label">Client with shared benes</span>
							</li>
							<li class="key__item">
								<span class="key__label" id="total-no-payments"></span>
								<span class="key__label">Payments</span> 
							</li>
						</ul>
						<form id="beneClientList" action="${redirectUrl }" method="POST">
							<div id="relationships"></div>
							<input type="hidden" id="ququefilter" name="ququefilter" value="">
						</form>
					</div>
					<% String  searchCriteria = request.getParameter("searchCriteria"); %>
					<input type="hidden" id="searchCriteria" name="searchCriteria" value='<%=searchCriteria %>'/>
					<div class="grid__col--6 grid__col--pad-left">

						<p id="total_payments" class="label space-after"><span class="label__support">This beneficiary has received a total of</span>
						 <span id="total-no--payments"></span> payments</p>

						<div class="scrollpane--container" data-height="724">

							<table id="tbl-payments"  class="micro">
								<thead>
									<tr>
										<th>Client</th>
										<th>Payment date/time</th>
										<th>Reference</th>
										<th class="number">Amount</th>
										<th>Currency</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>

							</div>
							<p id="total_benes" class="label space-after"><span id="total-no--benes"></span> has paid following beneficiaries</p>
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
							<div id="tbl-bene-div">
							<i onclick="getAllPayments()" class="material-icons">close</i>

								<table id="tbl-bene"  class="micro">
									<thead>
										<tr>
											<th>Beneficiary Name</th>
											<th>Beneficiary Account Number</th>
										</tr>
									</thead>
									<tbody id="tbl-bene-body"></tbody>
								</table>

							</div>
						</div>
					</div>
	
				</div>

			</section>
		</c:otherwise>
		</c:choose>
		</c:otherwise>
	</c:choose>
	</div>

</main>

	<div id="modal-global-just-a-moment" class="modal--hidden modal--small center">

	<h2>Just a moment...</h2>

	<img class="ajax-loader space-after space-before" src="resources/img/ajax-loader.gif" alt="Please wait...">

</div>	<div id="drawer-user" class="drawer--closed">

	<h2 class="drawer__heading">Your profile<span class="drawer__close"><i class="material-icons">close</i></span></h2>

	<ul class="split-list">
		<li>
			<i class="material-icons">settings</i> <a href="page-compliance-profile-administration.html">Administration</a>
		</li>
	</ul>

</div>

<div id="modal-private-name-beneClientDetails" class="modal--large topDecrease modal--hidden " style="display : block" height='500px'>
	<div class="ui-dialog-titlebar ui-corner-all ui-widget-header ui-helper-clearfix">
		<span id="clientName" class="ui-dialog-title"></span>
	</div><br>
	<div class="grid">
		<div class="scrollpane--blacklists"> <!-- class="scrollpane--blacklists" -->
		<table class="micro" id = "beneClientDetailsTable" >
	
		<thead>
			<tr>
				<!-- <th>Client</th> -->
				<th>Payment date/time</th>
				<th>Currency</th>
				<th>Amount</th>
				<th>Reference</th>
				<th>Status</th>
				<th>Updated On</th>
			</tr>
		</thead>
		<tbody id ='beneClientDetailsTableBody' ></tbody>
	</table>
	</div>
</div>
			<a href="#" class="modal__close-x" onclick = "closePopUpBene()"><i class="material-icons">close</i></a>
	</div>
	<!-- 
<div id="clientDetailspopups" class="popupDiv" title="Client Details">
		<textarea id="clientDetailsTextArea" class="popupTextArea">
		</textarea>
</div> -->
	
	<!-- BEGIN: DO NOT COPY THIS. THIS CODE IS JUST TO MIMIC DIFFERENT INTERACTIONS AND STATES. THIS WILL ALL BE HANDLED VIA AJAX IN THE BUILD. -->
<script type="text/javascript">

	$(function() {

		window["CurrenciesDirect"].Util.checkDevMode();

		$('.tabs__tab').on('click',function(e) {

			var theTab = $(this),
				loader = $(theTab).find('.ajax-loader').length ? $(theTab).find('.ajax-loader') : $(theTab).siblings('.ajax-loader');

			$(loader).show();

			setTimeout(function() {
				$(loader).hide();
			},1000);

		});

		window["CurrenciesDirect"].PageInteractionExample = {

			advanceWizardStep:function(btn,data) {

				var nextButton 				= btn,
					loader 					= $(nextButton).siblings('.ajax-loader'),	
					wizardNamePart  		= data.wizardNamePart, 			// credit
					URL 	 				= data.URL,            			// page-treasury-profile--credit-method.html
					nextStepContent			= data.nextStepContent,			// wizard-step-credit-method
					currentInstructionStep 	= data.currentInstructionStep, 	// credit-wizard-basic and get credit-wizard-method
					progressBarAmount 		= data.progressBarAmount;

				$(loader).show();

				setTimeout(function() {

					$.post(URL, function(d) {

						$('#'+wizardNamePart+'-wizard-current').html($('#'+nextStepContent,d));
						
						window["CurrenciesDirect"].InputMores.showOnPageload();
						window["CurrenciesDirect"].PillChoice.setOnPageload();

						$('#'+currentInstructionStep).removeClass('wizard__step--on').removeClass('wizard__step--incomplete').addClass('wizard__step--complete');
						$('#'+currentInstructionStep).next().addClass('wizard__step--on');

						$('#'+currentInstructionStep).find('.wizard-step-complete-example').show();
						$('#'+currentInstructionStep).find('.wizard-step-incomplete-example').hide();

						$('.ajax-loader').hide();

					}, 'html');

					$(loader).hide();

				}, 1000)

				setTimeout(function() {
					window["CurrenciesDirect"].ProgressBar.update($('#progress-bar-'+wizardNamePart+'-instruction'),progressBarAmount);
					window["CurrenciesDirect"].Tooltips.reinit();
				}, 2000);
			},

			completeWizard:function(btn,data) {

				var finishButton 			= btn,
					loader 					= $(finishButton).siblings('.ajax-loader'),	
					wizardNamePart  		= data.wizardNamePart;			

				$(loader).show();

				setTimeout(function() {

					$('#wizard-step-'+wizardNamePart+'-done .message--info').hide();
					$('#wizard-step-'+wizardNamePart+'-done #message-'+wizardNamePart+'-done').show();

					$('#'+wizardNamePart+'-wizard .splitpanel__section--actions').hide();
					window["CurrenciesDirect"].Util.glowIt($('#'+wizardNamePart+'-wizard>.splitpanel'),false);

					$('#'+wizardNamePart+'-wizard .wizard__title .wizard-step-complete-example, #'+wizardNamePart+'-wizard .wizard__status, #'+wizardNamePart+'-wizard .close-x--tr').hide();
					$('#'+wizardNamePart+'-wizard .wizard__title .wizard-step-incomplete-example').show();
					$(loader).hide();

					window["CurrenciesDirect"].Slip.setHeight(200);

				}, 1000);

			}

		}

	});

</script>

<script>

	// DO NOT COPY. THIS IS AN EXAMPLE OF HOW TO INDICATE AN EDITABLE LINE AS SAVED
	$(function() {

		$('.editable-line').on('blur',function() {
			var wrapper = $(this).parent();
			$(wrapper).find('.editable-line__saved').remove();
			wrapper.append('<small class="editable-line__saved mute"><i class="material-icons">save</i> 13:45:31</small>')
		});

	});
</script>
<input type="hidden" id="PayeeESDetails" value='${payeeESResponseJSON}'>
<script>
	var treeData = ${payeeESResponseJSON};
</script>
<input type = "hidden" id = "beneClientTradeAccountNumber" value='${payeeESResponse.customcheck.tradeAccountNumber} '>
	<script type="text/javascript" src="resources/js/cd1.js"></script>
	<script type="text/javascript" src="resources/js/cd.d3.compliance.beneficiaries.js"></script>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery_validate_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/amcharts/cd_amcharts_min.js"></script>
	<script type="text/javascript" src="resources/js/cd.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/payee.js"></script>
	<script type="text/javascript" src="resources/js/payeeDetails.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<!-- <script type="text/javascript" src="resources/js/jsontotable.js"></script> -->
	<script type="text/javascript" src="resources/js/administrtion.js"></script>
</body>

</html>



