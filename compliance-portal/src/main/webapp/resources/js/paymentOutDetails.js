var searchCriteria;
var complianceServiceBaseUrl;
var attachDocBaseUrl;
var notRequiredStatus = "NOT_REQUIRED";
var notRequiredFormattedStatus = "Not Required";
var isUnwatched=false;
var noOfRowsFrg=0;
var prevCountfrg=0;
var noOfRowsSanBank=0;
var noOfRowsSanBene=0;
var noOfRowsSanContact=0;
var noOfRowsPayRefChk=0;//AT-3658
var noOfRowsIntuitionCheck=0;//AT-4306
var prevCountBank=0;
var prevCountBene=0;
var prevCountContact=0;
var prevCountPayRef = 0; //At-3658
var prevCountIntuitionCheck=0;//AT-4306
var clicked= false;
$(document).ready(function() {
	if (document.referrer.endsWith("paymentOutReport")) {
		onSubNav('payOut-report-sub-nav');
	} else {
		onSubNav('payOut-sub-nav');
	}
	getComplianceServiceBaseUrl();
	getAttachDocBaseUrl();
	getViewMoreRecordsize();
	addPaymentOutStatusReasonsIntoData();
	addWatchlistIntoData();

});

function addPaymentOutStatusReasonsIntoData() {
	var newData = [];

	$('input[type="radio"][name="payOutDetails_payOutStatusReasons"]').each(
			function() {
				newData.push({
					"name" : $(this).val(),
					"preValue" : $(this).prop('checked'),
					"updatedValue" : $(this).prop('checked')
				});
			});
	$("#paymentOut_statusReasons").data('prePaymentStatusReasons', newData);
	var paymentOutStatus = $("input[name='paymentStatus']:checked").val();
	$("#paymentOutDetails_Status_radio").data('prePaymentStatus',
			paymentOutStatus);
}

function addWatchlistIntoData() {
	var newData = [];

	$('input[type="checkbox"][name="payment_watchlist\\[\\]"]').each(
			function() {
				newData.push({
					"name" : $(this).val(),
					"preValue" : $(this).prop('checked'),
					"updatedValue" : $(this).prop('checked')
				});
			});
	$("#payment_watchlists").data('preWatchlists', newData);
}

function getPayOutNextRecord(custType, id) {
	var searchCriteria = getNextRecord();
	if (searchCriteria !== undefined && searchCriteria !== null) {
		postPaymentOutDetails(searchCriteria, custType, id);
	}

}

function getPayOutPreviousRecord(custType, id) {
	var searchCriteria = getPreviousRecord();
	if (searchCriteria !== undefined && searchCriteria !== null) {
		postPaymentOutDetails(searchCriteria, custType, id);
	}
}

function getPayOutLastRecord(custType, id) {
	var searchCriteria = getLastRecord();
	postPaymentOutDetails(searchCriteria, custType, id);
}

function getPayOutFirstRecord(custType, id) {
	var searchCriteria = getFirstRecord();
	postPaymentOutDetails(searchCriteria, custType, id);
}

function postPaymentOutDetails(searchCriteria, custType, id) {
	disableAllButtons();
	disableAllPaginationBlock();
	disableAllCheckBlocks();
	$('#custType').val(custType);
	$('#searchSortCriteria').val(getJsonString(searchCriteria));
	$('#searchCriteriaForMostRecentPayment').val(getJsonString(searchCriteria));
	$('#form_paymentOutId').val(id);
	$('#paymentOutDetailForm').submit();
	/*
	 * $.ajax({ url : '/compliance-portal/paymentOutDetails', type : 'POST',
	 * data : getJsonString(request), contentType : "application/json", success :
	 * function(data) { setPaymentOutDetailsResponse(data) //enableAllButtons();
	 * enableAllPaginationBloack(); }, error : function() {
	 * //enableAllButtons(); enableAllPaginationBloack(); alert('Error while
	 * fetching data'); } });
	 */
}

function setPaymentOutDetailsResponse(paymentOutDetails) {
	setAccountDetails(paymentOutDetails.account);
	setContactDetails(paymentOutDetails.currentContact);
	setCurrentRecord(paymentOutDetails.currentRecord);
	setTotalRecords(paymentOutDetails.totalRecords);
	setSearchCriteria(paymentOutDetails.searchCriteria);
	setDetailsLockResponse(paymentOutDetails.user.name,
			paymentOutDetails.locked, paymentOutDetails.lockedBy,
			'lockResource', 'unlockResource', paymentOutDetails.userResourceId);
	if (paymentOutDetails.documents !== undefined
			&& paymentOutDetails.documents !== null) {
		setAttachedDocument(paymentOutDetails.documents);
	}
	setActivityLog(paymentOutDetails.activityLogs.activityLogData);
	setPaymentOutDetails(paymentOutDetails.paymentOutInfo);
	setServices(paymentOutDetails);
	setPaymentStatusReasons(paymentOutDetails.statusReason.statusReasonData);
	setWatchlist(paymentOutDetails.watchlist.watchlistData);
	setFurtherPaymentInDetails(paymentOutDetails.furtherPaymentDetails.furtherPaymentInDetails);
	setFurtherPaymentOutDetails(paymentOutDetails.furtherPaymentDetails.furtherPaymentOutDetails);
	setContactWatchlist(paymentOutDetails.contactWatchlist.watchlistData);
	setPaymentOutStatus(paymentOutDetails.status);
	setCustomCheck(paymentOutDetails.customCheck.paymentOutCustomCheck);
	setCountryCheck(paymentOutDetails.customCheck.countryCheck);
	setPaymentOutStatuses(paymentOutDetails.status.statuses);
	setPaymentOutInfo(paymentOutDetails.paymentOutInfo.status);
	setContactSanctionTotalRecords(paymentOutDetails.sanction.contactSanction.sanctionTotalRecords);
	setBankSanctionTotalRecords(paymentOutDetails.sanction.bankSanction.sanctionTotalRecords);
	setBeneficiarySanctionTotalRecords(paymentOutDetails.sanction.beneficiarySanction.sanctionTotalRecords);
	setFraugsterTotalRecords(paymentOutDetails.fraugster.fraugsterTotalRecords);
	setActivityLogTotalRecords(paymentOutDetails.activityLogs.totalRecords);
	setFurtherPaymentInTotalRecords(paymentOutDetails.furtherPaymentDetails.payInDetailsTotalRecords);
	setFurtherPaymentOutTotalRecords(paymentOutDetails.furtherPaymentDetails.payOutDetailsTotalRecords);
	setCustomCheckPaymentOutTotalRecords(paymentOutDetails.customCheck.paymentOutCustomCheck.totalRecords);
	setCountryCheckPaymentOutTotalRecords(paymentOutDetails.customCheck.countryCheck.countryCheckTotalRecords);
	setPaymentReferenceCheckPaymentOutTotalRecords(paymentOutDetails.paymentReference.totalRecords);
	addPaymentOutStatusReasonsIntoData();
	addWatchlistIntoData();
}

function setContactSanctionTotalRecords(totalRecordValue) {
	$("#sanctionTotalRecordsPayOutId_contact").val(totalRecordValue);
}

function setBankSanctionTotalRecords(totalRecordValue) {
	$("#sanctionTotalRecordsPayOutId_bank").val(totalRecordValue);
}

function setBeneficiarySanctionTotalRecords(totalRecordValue) {
	$("#sanctionTotalRecordsPayOutId_beneficiary").val(totalRecordValue);
}

function setFraugsterTotalRecords(totalRecordValue) {
	$("#fraugsterTotalRecordsPayOutId").val(totalRecordValue);
}

function setActivityLogTotalRecords(totalRecordValue) {
	$("#actLogTotalRecordsPayOutId").val(totalRecordValue);
}

function setFurtherPaymentInTotalRecords(totalRecordValue) {
	$("#furPayInDetailsTotalRecordsPayOutId").val(totalRecordValue);
}

function setFurtherPaymentOutTotalRecords(totalRecordValue) {
	$("#furPayOutDetailsTotalRecordsPayOutId").val(totalRecordValue);
}

function setCustomCheckPaymentOutTotalRecords(totalRecordValue) {
	$("#customCheckTotalRecordsPayOutId").val(totalRecordValue);
}

function setCountryCheckPaymentOutTotalRecords(totalRecordValue) {
	$("#countryCheckTotalRecordsPayOutId").val(totalRecordValue);
}

//AT-3658
function setPaymentReferenceCheckPaymentOutTotalRecords(totalRecordValue) {
	$("#paymentReferenceCheckTotalRecordsPayOutId").val(totalRecordValue);
}

function setServices(paymentOutDetails) {
	if (isBlacklistDataPresent(paymentOutDetails.blacklist)) {
		// setContactBlacklist(paymentOutDetails.blacklist);
		setBeneficiaryBlacklist(paymentOutDetails.blacklist);
		// setBankBlacklist(paymentOutDetails.blacklist);
	}

	if (isCustomCheckDataPresent(paymentOutDetails.customChecks)) {
		setCustomChecks(paymentOutDetails.customChecks.customcheckDatas);
	}

	if (isSanctionDataPresent(paymentOutDetails.sanction)) {
		/* setSanctions(paymentOutDetails.sanction.sanctions); */
		setContactSanction(paymentOutDetails.sanction);
		setBeneficiarySanction(paymentOutDetails.sanction);
		setBankSanction(paymentOutDetails.sanction);
	}

	if (isFraugsterDataPresent(paymentOutDetails.fraugster)) {
		setFraugster(paymentOutDetails.fraugster);
	}
}

function setPaymentOutStatus(status) {
	var statusData = '';
	$('#statues').remove();
	$.each(status.statuses, function(index, statusData) {
		var data = "<li>"
		if (statusData.status === 'CLEAR') {

			data += getClearStatusData(statusData);
		}
		if (statusData.status === 'WATCHED') {

			data += getUnwatchStatusData(statusData);
		}
		if (statusData.status === 'REJECT') {

			data += getRejectStatusData(statusData);
		}
		if (statusData.status === 'SEIZE') {

			data += getSeizeStatusData(statusData);
		}
		if (statusData.status === 'HOLD') {

			data += getHoldStatusData(statusData);
		}
		data += "</li>";
		statusData += data;
	});

	$('#statuses').append(statusData);
	$("#rad-status-clear").data('more-hide', 'input-more-areas-reasons');
}

function getClearStatusData(statusData) {
	if (statusData.isSelected) {
		return '<label class="pill-choice__choice--positive pill-choice__choice--on" for="rad-status-clear">'
				+ '<input id="rad-status-clear" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Clear			</label>';
	} else {
		return '<label class="pill-choice__choice--positive" for="rad-status-clear">'
				+ '<input id="rad-status-clear" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Clear			</label>';
	}
}

function getUnwatchStatusData(statusData) {
	if (statusData.status === 'WATCHED') {
		if (statusData.isSelected) {
			return '<label class="pill-choice__choice--positive pill-choice__choice--on" for="rad-status-unwatch">'
					+ '<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="'
					+ statusData.status
					+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
					+ 'Clear			</label>';
		} else {
			return '<label class="pill-choice__choice--positive" for="rad-status-unwatch">'
					+ '<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="'
					+ statusData.status
					+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
					+ 'Clear			</label>';
		}
	}

}

function getRejectStatusData(statusData) {
	if (statusData.isSelected) {
		return '<label class="pill-choice__choice--negative pill-choice__choice--on" for="rad-status-reject">'
				+ '<input id="rad-status-reject" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Reject			</label>';
	} else {
		return '<label class="pill-choice__choice--negative">'
				+ '<input id="rad-status-reject" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Reject			</label>';
	}
}

function getSeizeStatusData(statusData) {
	if (statusData.isSelected) {
		return '<label class="pill-choice__choice--negative pill-choice__choice--on" for="rad-status-seize">'
				+ '<input id="rad-status-seize" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Seize			</label>';
	} else {
		return '<label class="pill-choice__choice--negative" for="rad-status-seize">'
				+ '<input id="rad-status-seize" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Seize			</label>';
	}
}

function getHoldStatusData(statusData) {
	if (statusData.isSelected) {
		return '<label class="pill-choice__choice--neutral pill-choice__choice--on" for="rad-status-hold">'
				+ '<input id="rad-status-hold" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Hold			</label>';
	} else {
		return '<label class="pill-choice__choice--neutral" for="rad-status-hold">'
				+ '<input id="rad-status-hold" type="radio" name="paymentStatus" value="'
				+ statusData.status
				+ '" class="input-more-hide" data-more-hide="input-more-areas-reasons" checked />'
				+ 'Hold			</label>';
	}
}

function executeActions(isAutoUnlock) {
	var isAllWatchlistReasonRemoved=false;
	var numberOfWatchlistReason=$('input[name="payment_watchlist[]"]:checked').length;
	var comment = $('#comments').val();
	var isOnQueue = $('#payment_isOnQueue').val();
	var intuitionCurrentStatus = $("#intuitionCurrentStatus").val();
	var intuitionCurrentAction = $("#intuitionCurrentAction").val();
		
	var prePaymentStatus = $("#paymentOutDetails_Status_radio").data(
			'prePaymentStatus');
	var updatedPaymentStatus = $("input[name='paymentStatus']:checked").val();
 
	if(updatedPaymentStatus === 'WATCHED' && numberOfWatchlistReason === 0){
		isAllWatchlistReasonRemoved =true;
	}
	
	if((comment === null ||comment === undefined  || comment === '')
			&& (!isNull(updatedPaymentStatus) && !isEmpty(updatedPaymentStatus) && updatedPaymentStatus != 'CLEAR')){
			alert("Please add comment");
	}else if (updatedPaymentStatus !== 'CLEAR'
			&& $("input[name='payOutDetails_payOutStatusReasons']:checked").length === 0) {
		populateErrorMessage("main-content__body",
				"Please select atleast one reason for " + updatedPaymentStatus,
				"updatePaymentOut_error_field", "updatePaymentOut");
	} else if ((intuitionCurrentAction === null || intuitionCurrentAction === undefined || intuitionCurrentAction === '' || isEmpty(intuitionCurrentAction))
		 && (intuitionCurrentStatus === 'Hold')){
		updateStatusConfirm(isAutoUnlock);
	}
	else {
		executeUpdateAction(isAutoUnlock);	
	}
}

function updateStatusConfirm(isAutoUnlock){
	$.confirm({
	    title: 'Confirm!',
	    content: 'Manual action on Intuition is pending, do you want to continue clearing?',
	    buttons: {
	        Yes: function () {
	            executeUpdateAction(isAutoUnlock);
	        },
	        No: function () {
	            this.$$No.prop('disabled', true);
	        }
	    }
	});
}

function executeUpdateAction(isAutoUnlock){
	var isAllWatchlistReasonRemoved=false;
	var numberOfWatchlistReason=$('input[name="payment_watchlist[]"]:checked').length;
	var comment = $('#comments').val();
	var isOnQueue = $('#payment_isOnQueue').val();
	var prePaymentStatus = $("#paymentOutDetails_Status_radio").data(
			'prePaymentStatus');
	var updatedPaymentStatus = $("input[name='paymentStatus']:checked").val();
	
	if (updatedPaymentStatus === 'WATCHED') {
		updatedPaymentStatus = prePaymentStatus;
		isUnwatched =true;
	}
	
	if(sanctionStatusForPaymentStatusChange(updatedPaymentStatus))
		return;
	
	if(isAllWatchlistReasonRemoved && sancBeneStatus.indexOf("clear") == -1){
		updatedPaymentStatus = 'CLEAR' ;
	} 
	$('#gifloaderforPayOut').css('visibility', 'visible');
	$("#paymentOutDetails_Status_radio").data('prePaymentStatus',updatedPaymentStatus);
	var request = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var accountSfId = $('#contact_crmAccountId').val();
	var contactSfId = $('#contact_crmContactId').val();
	var paymentOutId = $('#paymentOutId').val();
	var orgCode = $('#account_organisation').text();
	var tradeBeneficiayId = $('#trade_beneficiary_id').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradeContractNumber = $('#payment_transNum').text();
	var custType = $('#account_clientType').text();
	var fraugsterEventServiceLogId = Number($('#fraugster_eventServiceLogId').val());
	var beneficiaryName = $('#payment_beneficiaryName').text();
	var beneficiaryAmount = $('#payment_amount').text();
	var buyCurrency = $('#payment_buyCurrency').text();
	var clientNumber = $('#account_tradeAccountNum').text();
	var statusReason = $("input[name='payOutDetails_payOutStatusReasons']:checked").val();
	var beneficiaryCountry = $('#payment_countryOfBeneficiary').text();
	var beneficiaryCountryRiskLevel = $('#beneficiaryCountryRiskLevel').val();
	var beneficiaryCheckStatus = $('#beneficiaryCheckStatus').val();
	var userResourceId = $('#userResourceId').val();
	var email = $('#contact_FurtherClient_email').text();
	var legalEntity = $('#leaglEntity').text();
	
	addField('accountId', accountId, request);
	addField('contactId', contactId, request);
	addField('paymentOutId', paymentOutId, request);
	addField('accountSfId', accountSfId, request);
	addField('contactSfId', contactSfId, request);
	addField('orgCode', orgCode, request);
	addField("statusReasons", getPaymentOutReasons(), request);
	addField("watchlist", getWatchlists(), request);
	addField('overallWatchlistStatus', findInWatchlists(), request);
	addField("prePaymentOutStatus", prePaymentStatus, request);
	addField("updatedPaymentOutStatus", updatedPaymentStatus, request);
	addField("tradeBeneficiayId", tradeBeneficiayId, request);
	addField("tradePaymentId", tradePaymentId, request);
	addField("tradeContactId", tradeContactId, request);
	addField("tradeContractNumber", tradeContractNumber, request);
	addField("custType", custType, request);
	addField('fragusterEventServiceLogId',fraugsterEventServiceLogId,request);
	addField("isOnQueue", isOnQueue, request);
	addField("beneficiaryName", beneficiaryName, request);
	addField("beneficiaryAmount", beneficiaryAmount, request);
	addField("buyCurrency", buyCurrency, request);
	addField("clientNumber", clientNumber, request);
	addField("statusReason", statusReason, request);
	addField("comment", comment, request);
	addField("userName", getUserObject().name, request);
	$("#paymentOut_statusReasons").data('prePaymentStatus', updatedPaymentStatus);
	addField("comment", comment, request);
	addField("country", beneficiaryCountry, request);
	addField("countryRiskLevel", beneficiaryCountryRiskLevel, request);
	addField("beneCheckStatus", beneficiaryCheckStatus, request);
	addField("userResourceId",userResourceId,request);
	addField('email', email, request);
	addField('legalEntity', legalEntity, request);
	
	var onlineAccountStatus=$('#payment_complianceStatusdetails').text();
	if(onlineAccountStatus=='ACTIVE'){
		postPaymentOutUpdate(request,prePaymentStatus,updatedPaymentStatus,isAutoUnlock);  
	}
	
	else {
		$('#gifloaderforPayOut').css('visibility', 'hidden');
		alert("Account is Inactive,cannot clear payment");
	}
}

function updateSanction(entityId, entityType) {
	/**
	 * get sanction Contact, Bank and Beneficiary status value, this value are
	 * used to update sanction status in paymentout table : Abhijit G
	 */
	var sanctionContactStatus = $('#paymentOutSanctionContactStatus').val();
	var sanctionBankStatus = $('#paymentOutSanctionBankStatus').val();
	var sanctionBeneficiaryStatus = $('#paymentOutSanctionBeneficiaryStatus')
			.val();
	
	if(sanctionBeneficiaryStatus==="" || sanctionBeneficiaryStatus===null || sanctionBeneficiaryStatus ===undefined ){
		sanctionBeneficiaryStatus="SERVICE_FAILURE";
	}
	
	if(sanctionBankStatus==="" || sanctionBankStatus===null || sanctionBankStatus ===undefined){
		sanctionBankStatus="SERVICE_FAILURE";
	}
	
	if(sanctionContactStatus==="" || sanctionContactStatus===null || sanctionContactStatus ===undefined){
		sanctionContactStatus="SERVICE_FAILURE";
	}

	if (sanctionContactStatus === notRequiredFormattedStatus) {
		sanctionContactStatus = notRequiredStatus;
		$('#paymentOutSanctionContactStatus').val(notRequiredStatus)
	}
	if (sanctionBankStatus === notRequiredFormattedStatus) {
		sanctionBankStatus = notRequiredStatus;
		$('#paymentOutSanctionBankStatus').val(notRequiredStatus)
	}
	if (sanctionBeneficiaryStatus === notRequiredFormattedStatus) {
		sanctionBeneficiaryStatus = notRequiredStatus;
		$('#paymentOutSanctionBeneficiaryStatus').val(notRequiredStatus);
	}
	if (entityType === "CONTACT") {
		$('#gifloaderforupdatesanctioncontact').css('visibility', 'visible');
	} else if (entityType === "BENEFICIARY") {
		$('#gifloaderforupdatesanctionbenefeciary')
				.css('visibility', 'visible');
	} else if (entityType == "BANK") {
		$('#gifloaderforupdatesanctionbank').css('visibility', 'visible');
	}

	var fieldName = $(
			"input[name='updateField_" + entityType.toLowerCase()
					+ "']:checked").val();
	var value = $(
			"input[name='updateField_value_" + entityType.toLowerCase()
					+ "']:checked").val();
	
	var previousOfacValue = getPreviousOfacValue(entityType);
	var previousWorldCheckValue = getPreviousWorldCheckValue(entityType);
	
	if (value === undefined || value === null || fieldName === undefined
			|| fieldName === null) {
		populateErrorMessage("main-content__body",
				"Please select required fields for sanction update",
				"updateSanction_" + entityType.toLowerCase() + "_error_field",
				"updateSanction_" + entityType.toLowerCase());
		hideGifLoaderForSanctionUpdate(entityType);
		
	} else if( (previousOfacValue === value && fieldName === 'ofaclist') ||
					(previousWorldCheckValue === value && fieldName === 'worldcheck')) {
		alert("Please change "+fieldName+" value for "+entityType.toLowerCase()+" and then update sanction");
		hideGifLoaderForSanctionUpdate(entityType);
				
	} else {
		$('#gifloaderforupdatesanction').css('visibility', 'visible');
		var request = {};
		var accountId = Number($('#contact_accountId').val());
		var paymentoutid = Number($('#paymentOutId').val());
		var orgCode = $('#account_organisation').text();
		var eventId = Number($("#sanctions_" + entityType.toLowerCase() + "")
				.find('tr:first').find('td:first').text());
	
		if(eventId === "" || eventId === null || eventId === undefined || eventId === 0){
			hideGifLoaderForSanctionUpdate(entityType);			
			return populateErrorMessage("main-content__body",
					"Perform repeat check to update",
					"updateSanction_" + entityType.toLowerCase() + "_error_field",
					"updateSanction_" + entityType.toLowerCase());			
		}
		
		addField("accountId", accountId, request);
		addField('resourceId', paymentoutid, request);
		addField('resourceType', 'FUNDSOUT', request);
		addField("orgCode", orgCode, request);

		var sanctions = [];
		addField("sanctions", sanctions, request);
		sanctions.push({
			"eventServiceLogId" : eventId,
			"field" : fieldName,
			"value" : value,
			"entityId" : entityId,
			"entityType" : entityType,
			"contactStatus" : sanctionContactStatus,
			"bankStatus" : sanctionBankStatus,
			"beneficiaryStatus" : sanctionBeneficiaryStatus
		});
		postUpdateSanction(request, getComplianceServiceBaseUrl(), getUser());
	}

}

function postUpdateSanction(request, baseUrl, user) {
	var entityType = request.sanctions[0].entityType.toLowerCase();
	disableButton('updateSanction_' + entityType);
	disableButton('sanction_recheck_' + entityType);
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/updateSanction',
				type : 'POST',
				headers : {
					"user" : user
				},
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
				  if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '799'){ 
					  if (entityType === "contact") {
							$('#gifloaderforupdatesanctioncontact').css(
									'visibility', 'hidden');
						} else if (entityType === "beneficiary") {
							$('#gifloaderforupdatesanctionbenefeciary').css(
									'visibility', 'hidden');
						} else if (entityType == "bank") {
							$('#gifloaderforupdatesanctionbank').css('visibility',
									'hidden');
						}

						enableButton('updateSanction_' + entityType);
						enableButton('sanction_recheck_' + entityType);
						populateErrorMessage("main-content__body",
								"Error while updating sanction for " + entityType,
								"updateSanction_" + entityType + "_error_field",
								"updateSanction_" + entityType);

				  }else {
					  if (entityType === "contact") {
							$('#gifloaderforupdatesanctioncontact').css(
									'visibility', 'hidden');
						} else if (entityType === "beneficiary") {
							$('#gifloaderforupdatesanctionbenefeciary').css(
									'visibility', 'hidden');
						} else if (entityType == "bank") {
							$('#gifloaderforupdatesanctionbank').css('visibility',
									'hidden');
						}
						
					  var currentSanctionStatus = data.status; //Fail FAIL
					  if (entityType === "contact") {
						  var prevSanctionStatus = $('#paymentOutSanctionContactStatus').val();
					  } else if (entityType === "beneficiary") {
						  var prevSanctionStatus = $('#paymentOutSanctionBeneficiaryStatus').val();
					  } else if (entityType == "bank") {
						  var prevSanctionStatus = $('#paymentOutSanctionBankStatus').val();
					  }
						
						var addedRecords = Number($('#actLogTotalRecordsPayOutId')
								.val())
								+ data.activityLogs.activityLogData.length;
						setActivityLogTotalRecords(addedRecords);
						getActivities(1, 10, Number($('#paymentOutId').val()),
								false);
						updateSanctionColumn(data, entityType);
						enableButton('updateSanction_' + entityType);
						enableButton('sanction_recheck_' + entityType);
						populateSuccessMessage("main-content__body",
								"Sanction update Successfully done for "
										+ entityType, "updateSanction_"
										+ entityType + "_error_field",
								"updateSanction_" + entityType);
								
						var accountTMFlag = $('#accountTMFlag').val();
					  if (prevSanctionStatus.toUpperCase() != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						  updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						  if (entityType === "contact") {
							  var previousSanctionStatus = document.getElementById("paymentOutSanctionContactStatus");
							  previousSanctionStatus.value = currentSanctionStatus;
						  } else if (entityType === "beneficiary") {
							  var previousSanctionStatus = document.getElementById("paymentOutSanctionBeneficiaryStatus");
							  previousSanctionStatus.value = currentSanctionStatus;
						  } else if (entityType == "bank") {
							  var previousSanctionStatus = document.getElementById("paymentOutSanctionBankStatus");
							  previousSanctionStatus.value = currentSanctionStatus;
						  }
					  }
				  }
				},
				error : function() {
					if (entityType === "contact") {
						$('#gifloaderforupdatesanctioncontact').css(
								'visibility', 'hidden');
					} else if (entityType === "beneficiary") {
						$('#gifloaderforupdatesanctionbenefeciary').css(
								'visibility', 'hidden');
					} else if (entityType == "bank") {
						$('#gifloaderforupdatesanctionbank').css('visibility',
								'hidden');
					}

					enableButton('updateSanction_' + entityType);
					enableButton('sanction_recheck_' + entityType);
					populateErrorMessage("main-content__body",
							"Error while updating sanction for " + entityType,
							"updateSanction_" + entityType + "_error_field",
							"updateSanction_" + entityType);

				}
			});
}

function updateSanctionColumn(data, entityType) {
	var fieldName = $("input[name='updateField_" + entityType + "']:checked")
			.val();
	var value = $("input[name='updateField_value_" + entityType + "']:checked")
			.val();
	if (fieldName.toLowerCase() === "ofaclist") {
		$('#sanctions_' + entityType + ' tr:nth-child(1) td:nth-child(7)')
				.text(value);
	}
	if (fieldName.toLowerCase() === "worldcheck") {
		$('#sanctions_' + entityType + ' tr:nth-child(1) td:nth-child(8)')
				.text(value);
	}
	if (data.status === 'PASS') {
		$('#sanctions_' + entityType + ' tr:nth-child(1) td:nth-child(9)')
				.removeClass('no-cell').addClass('yes-cell').html(
						'<i class="material-icons">check</i>');
		
		entityType = entityType.toLowerCase();
		if (entityType === 'contact') {
			var totalrecords = $("#sanctionTotalRecordsPayOutId_contact").val();
				setContactSanctionIndicator(totalrecords, 0);

		} else if (entityType === 'beneficiary') {
			var totalrecord = $("#sanctionTotalRecordsPayOutId_beneficiary").val();
				setBeneficiarySanctionIndicator(totalrecord, 0);
			
		} else {
			var totalrecords = $("#sanctionTotalRecordsPayOutId_bank").val();
				setBankSanctionIndicator(totalrecords, 0);

		}
	} else {
		$('#sanctions_' + entityType + ' tr:nth-child(1) td:nth-child(9)')
				.removeClass('yes-cell').addClass('no-cell').html(
						'<i class="material-icons">clear</i>');
		
		entityType = entityType.toLowerCase();
		if (entityType === 'contact') {
			var totalrecords = $("#sanctionTotalRecordsPayOutId_contact").val();
				setContactSanctionIndicator(0, totalrecords);
			
		} else if (entityType === 'beneficiary') {
			var totalrecord = $("#sanctionTotalRecordsPayOutId_beneficiary").val();
				setBeneficiarySanctionIndicator(0, totalrecord);

		} else {
			var totalrecords = $("#sanctionTotalRecordsPayOutId_bank").val();
				setBankSanctionIndicator(0, totalrecords);
		}
	}

	setSanctionStatusValuesToHiddenFields(data, entityType);
}

function postPaymentOutUpdate(request,prevStatus,updatedStatus,isAutoUnlock) {
	$("#updatePaymentOut").attr("disabled", true);
	$("#updatePaymentOut").addClass("disabled");

	$.ajax({
		url : '/compliance-portal/paymentOutUpdate',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforPayOut').css('visibility', 'hidden');
			if (data.errorCode != null) {
				populateErrorMessage("main-content__body",
						"Error while updating status",
						"updatePaymentOut_error_field", "updatePaymentOut");
				$("#updatePaymentOut").attr("disabled", false);
				$("#updatePaymentOut").removeClass("disabled");
			} else {
				var addedRecords = Number($('#actLogTotalRecordsPayOutId')
						.val())
						+ data.activityLogData.length;
				setActivityLogTotalRecords(addedRecords);
				getActivities(1, 10, Number($('#paymentOutId').val()), false);
				setPaymentOutInfo(request.updatedPaymentOutStatus);
				$("#payment_complianceStatus") .text($("#paymentOut_statusReasons").data('prePaymentStatus'));
				$("#updatePaymentOut").attr("disabled", false);
				$("#updatePaymentOut").removeClass("disabled");
				// commited changes for AT-256 Neelesh Pant
				if (request.updatedPaymentOutStatus === 'CLEAR') {
					decreamentTotalRecords(1);
				}
				$('#comments').val('');
				
				if(isUnwatched){
					setPaymentOutStatusForUnwatch(prevStatus, updatedStatus)
				}
				var watchlistReasons = '';
				watchlistReasons = updateCurrentWatchlists(request);
				
				if(isAutoUnlock){
					unlockResource();
					applyClassesToAutoUnLockButton();
				}
				
				$('#contactWatchlist li').remove();
				$('#contactWatchlist').append(watchlistReasons);
				populateSuccessMessage("main-content__body",
						"Updated successfully",
						"updatePaymentOut_error_field", "updatePaymentOut");
						
				var accountTMFlag = $('#accountTMFlag').val();
				var isWatchlistUpdated = data.isWatchlistUpdated;
				if (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4) {
					
					if (isWatchlistUpdated) {
						var accountId = $('#contact_accountId').val();
						var contactId = $('#contact_contactId').val();
						var tradeAccountNumber = $('#account_tradeAccountNum').text();
						var orgCode = $('#account_organisation').text();

						var intuitionRequest = {};

						addField('accountId', accountId, intuitionRequest);
						addField('contactId', contactId, intuitionRequest);
						addField('tradeAccountNumber', tradeAccountNumber, intuitionRequest);
						addField('orgCode', orgCode, intuitionRequest);

						//callback
						updateAccountIntuitionRepeatCheckStatusForPayment(intuitionRequest, getUser(), getComplianceServiceBaseUrl(), 'FundsOut'); 
					}
					else{
						updateFundsOutIntuitionRepeatCheckStatus(getUser(), getComplianceServiceBaseUrl());
					}
				}
			}
		},
		error : function() {
			$('#gifloaderforPayOut').css('visibility', 'hidden');
			$("#updatePaymentOut").attr("disabled", false);
			$("#updatePaymentOut").removeClass("disabled");
			populateErrorMessage("main-content__body",
					"Error while updating ",
					"updatePaymentOut_error_field", "updatePaymentOut");
		}
	});
}

function getPaymentOutReasons() {
	var newData = [];
	var saveData = [];
	var preValue = $("#paymentOut_statusReasons").data(
			'prePaymentStatusReasons');
	$.each(preValue, function(index, data) {
		newData[index] = {
			"name" : data.name,
			"preValue" : data.preValue,
			"updatedValue" : $(
					"input[value='" + data.name
							+ "'][name='payOutDetails_payOutStatusReasons']")
					.prop('checked')
		}
		saveData[index] = {
			"name" : data.name,
			"preValue" : newData[index].updatedValue,
			"updatedValue" : newData[index].updatedValue
		};
	});

	$("#paymentOut_statusReasons").data('prePaymentStatusReasons', saveData);
	return newData;
}

function getWatchlists() {
	var newData = [];
	var saveData = [];
	var preValue = $("#payment_watchlists").data('preWatchlists');
	$.each(preValue, function(index, data) {
		newData[index] = {
			"name" : data.name,
			"preValue" : data.preValue,
			"updatedValue" : $(
					"input[value='" + data.name
							+ "'][name='payment_watchlist[]']").prop('checked')
		}
		saveData[index] = {
			"name" : data.name,
			"preValue" : newData[index].updatedValue,
			"updatedValue" : newData[index].updatedValue
		};
	});
	$("#payment_watchlists").data('preWatchlists', saveData);
	return newData;
}

function findInWatchlists() {
	return $('#payment_watchlists :checkbox:checked').length > 0;
}

function isBlacklistDataPresent(blacklist) {
	return blacklist !== undefined && blacklist !== null
	/*
	 * && blacklist.blacklists !== undefined && blacklist.blacklists !== null
	 */
}
function isCustomCheckDataPresent(customChecks) {
	return customChecks !== undefined && customChecks !== null
			&& customChecks.customcheckDatas !== undefined
			&& customChecks.customcheckDatas !== null
}
function isSanctionDataPresent(sanction) {
	return sanction !== undefined && sanction !== null
	/* && sanction.sanctions !== undefined && sanction.sanctions !== null */
}
function isFraugsterDataPresent(fraugster) {
	return fraugster !== undefined && fraugster !== null;
}

function setContactBlacklist(blacklist) {
	$("#blacklist_contact").empty();
	var rows = '';
	rows += getContactBlacklistRow(blacklist);
	$("#blacklist_contact").append(rows);
	var blacklistPassCount = blacklist.contactBlacklist.passCount;
	var blacklistFailCount = blacklist.contactBlacklist.failCount;
	setContactBlacklistIndicator(blacklistPassCount, blacklistFailCount)
}

function setBeneficiaryBlacklist(blacklist) {
	$("#blacklist_beneficiary").empty();
	var rows = '';
	rows += getBenficiaryBlacklistRow(blacklist);
	$("#blacklist_beneficiary").append(rows);
	var blacklistPassCount = blacklist.benficiaryBlacklist.passCount;
	var blacklistFailCount = blacklist.benficiaryBlacklist.failCount;
	setBeneficiaryBlacklistIndicator(blacklistPassCount, blacklistFailCount)
}

function setBankBlacklist(blacklist) {
	$("#blacklist_bank").empty();
	var rows = '';
	rows += getBankBlacklistRow(blacklist);
	$("#blacklist_bank").append(rows);
}

function getContactBlacklistRow(blacklist) {
	var eventServiceLogId = getEmptyIfNull(blacklist.contactBlacklist.id);
	var ipStatus = getEmptyIfNull(blacklist.contactBlacklist.ip);
	var emailStatus = getEmptyIfNull(blacklist.contactBlacklist.email);
	var phoneStatus = getEmptyIfNull(blacklist.contactBlacklist.phone);
	var blacklistStatus = getEmptyIfNull(blacklist.contactBlacklist.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	row += getYesOrNoCell(ipStatus);
	row += getYesOrNoCell(emailStatus);
	row += getYesOrNoCell(phoneStatus);
	row += getYesOrNoCell(blacklistStatus);
	row += '</tr>';
	return row;
}

function getBenficiaryBlacklistRow(blacklist) {
	var eventServiceLogId = getEmptyIfNull(blacklist.benficiaryBlacklist.id);
	// var ipStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.ip);
	// var emailStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.email);
	// var phoneStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.phone);
	var nameStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.name);
	var accountNumberStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.accountNumber);
	var blacklistStatus = getEmptyIfNull(blacklist.benficiaryBlacklist.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	// row += getYesOrNoCell(ipStatus);
	// row += getYesOrNoCell(emailStatus);
	// row += getYesOrNoCell(phoneStatus);
	row += getYesOrNoCell(accountNumberStatus);
	row += getYesOrNoCell(nameStatus);
	row += getYesOrNoCell(blacklistStatus);
	row += '</tr>';
	return row;
}

/*
 * function getBankBlacklistRow(blacklist){ var eventServiceLogId
 * =getEmptyIfNull(blacklist.bankBlacklist.id); var ipStatus =
 * getEmptyIfNull(blacklist.bankBlacklist.ip); var emailStatus =
 * getEmptyIfNull(blacklist.bankBlacklist.email); var phoneStatus =
 * getEmptyIfNull(blacklist.bankBlacklist.phone); var blacklistStatus =
 * getEmptyIfNull(blacklist.bankBlacklist.status); var row = '<tr>'; row += '<td hidden="hidden">' +
 * eventServiceLogId + '</td>'; row += getYesOrNoCell(ipStatus); row +=
 * getYesOrNoCell(emailStatus); row += getYesOrNoCell(phoneStatus); row +=
 * getYesOrNoCell(blacklistStatus); row += '</tr>'; return row; }
 */

/*
 * function setCustomChecks(customeChecks) {
 * 
 * $("#customChecks").empty(); var rows = ''; $.each(customeChecks,
 * function(index, customCheck) { var eventId =
 * getEmptyIfNull(customCheck.eventId); var entityType =
 * getEmptyIfNull(customCheck.entityType); var checkedOn =
 * getEmptyIfNull(customCheck.checkedOn); var name =
 * getEmptyIfNull(customCheck.name); var rules =
 * getEmptyIfNull(customCheck.velocityRules); var row = '<tr>'; row += '<td hidden="hidden">' +
 * eventId + '</td>'; row += '<td>' + entityType + '</td>'; row += '<td class="nowrap">' +
 * checkedOn + '</td>'; row += '<td>' + name + '</td>'; row += '<td><ul>';
 * if (rules !== '') { $.each(rules, function(index, rule) { row += '<li>' +
 * rule + '</li>'; }); }
 * 
 * row += '</ul></td>'; rows += row; });
 * 
 * $("#customChecks").append(rows); }
 */

/*
 * function setSanctions(sanction) { $("#sanctions").empty(); var rows = '';
 * rows += getContactSanctionRow(sanction.contactSanction); rows +=
 * getBenficiarySanctionRow(sanction.beneficiarySanction); rows +=
 * getBankSanctionRow(sanction.bankSanction); $("#sanctions").append(rows); }
 */

function getContactSanctionRow(sanction) {
	/*
	 * var eventId = getEmptyIfNull(sanction.contactSanction.eventId); var
	 * entity = getEmptyIfNull(sanction.entityType);
	 */
	var eventServiceLogId = getEmptyIfNull(sanction.contactSanction.eventServiceLogId);
	var entityType = getEmptyIfNull(sanction.contactSanction.entityType);
	var entityId = getEmptyIfNull(sanction.contactSanction.entityId);
	var updatedOn = getEmptyIfNull(sanction.contactSanction.updatedOn);
	var updatedBy = getEmptyIfNull(sanction.contactSanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.contactSanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.contactSanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.contactSanction.worldCheck);
	var status = getEmptyIfNull(sanction.contactSanction.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	/*
	 * row += '<td class="center"><input type="checkbox"/></td>'; row += '<td>' +
	 * entity + '</td>';
	 */
	row += '<td hidden="hidden">' + entityType + '</td>';
	row += '<td hidden="hidden">' + entityId + '</td>';
	row += '<td  class="nowrap">' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	row += '<td class="nowrap"><a href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	row += getYesOrNoCell(status);
	row += '</tr>'
	if (sanction.contactSanction.passCount === undefined
			|| sanction.contactSanction.passCount === null) {
		if (status) {
			sanction.contactSanction.passCount = 1;
		} else {
			sanction.contactSanction.failCount = 1;
		}
	}
	var contactSanctionPassCount = sanction.contactSanction.passCount;
	var contactSanctionFailCount = sanction.contactSanction.failCount;
	setContactSanctionIndicator(contactSanctionPassCount,
			contactSanctionFailCount);
	return row;
}

function getBenficiarySanctionRow(sanction) {
	/*
	 * var eventId = getEmptyIfNull(sanction.beneficiarySanction.eventId); var
	 * entity = getEmptyIfNull(sanction.entityType);
	 */

	var eventServiceLogId = getEmptyIfNull(sanction.beneficiarySanction.eventServiceLogId);
	var entityType = getEmptyIfNull(sanction.contactSanction.entityType);
	var entityId = getEmptyIfNull(sanction.contactSanction.entityId);
	var updatedOn = getEmptyIfNull(sanction.beneficiarySanction.updatedOn);
	var updatedBy = getEmptyIfNull(sanction.beneficiarySanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.beneficiarySanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.beneficiarySanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.beneficiarySanction.worldCheck);
	var status = getEmptyIfNull(sanction.beneficiarySanction.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	/*
	 * row += '<td hidden="hidden">' + eventId + '</td>'; row += '<td class="center"><input
	 * type="checkbox"/></td>'; row += '<td>' + entity + '</td>';
	 */
	row += '<td hidden="hidden">' + entityType + '</td>';
	row += '<td hidden="hidden">' + entityId + '</td>';
	row += '<td  class="nowrap">' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	// row += '<td>' + sanctionId + '</td>';
	row += '<td class="nowrap"><a href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	row += getYesOrNoCell(status);
	row += '</tr>'
	if (sanction.beneficiarySanction.passCount === undefined
			|| sanction.beneficiarySanction.passCount === null) {
		if (status) {
			sanction.beneficiarySanction.passCount = 1;
		} else {
			sanction.beneficiarySanction.failCount = 1;
		}
	}
	var beneficiarySanctionPassCount = sanction.beneficiarySanction.passCount;
	var beneficiarySanctionFailCount = sanction.beneficiarySanction.failCount;
	setBeneficiarySanctionIndicator(beneficiarySanctionPassCount,
			beneficiarySanctionFailCount);
	return row;
}

function getBankSanctionRow(sanction) {
	/*
	 * var eventId = getEmptyIfNull(sanction.bankSanction.eventId); var entity =
	 * getEmptyIfNull(sanction.entityType);
	 */
	var eventServiceLogId = getEmptyIfNull(sanction.bankSanction.eventServiceLogId);
	var entityType = getEmptyIfNull(sanction.contactSanction.entityType);
	var entityId = getEmptyIfNull(sanction.contactSanction.entityId);
	var updatedOn = getEmptyIfNull(sanction.bankSanction.updatedOn);
	var updatedBy = getEmptyIfNull(sanction.bankSanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.bankSanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.bankSanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.bankSanction.worldCheck);
	var status = getEmptyIfNull(sanction.bankSanction.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	/*
	 * row += '<td hidden="hidden">' + eventId + '</td>'; row += '<td class="center"><input
	 * type="checkbox"/></td>'; row += '<td>' + entity + '</td>';
	 */
	row += '<td hidden="hidden">' + entityType + '</td>';
	row += '<td hidden="hidden">' + entityId + '</td>';
	row += '<td  class="nowrap">' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	row += '<td class="nowrap"><a href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	row += getYesOrNoCell(status);
	row += '</tr>'
	if (sanction.bankSanction.passCount === undefined
			|| sanction.bankSanction.passCount === null) {
		if (status) {
			sanction.bankSanction.passCount = 1;
		} else {
			sanction.bankSanction.failCount = 1;
		}
	}
	var bankSanctionPassCount = sanction.bankSanction.passCount;
	var bankSanctionFailCount = sanction.bankSanction.failCount;
	setBankSanctionIndicator(bankSanctionPassCount, bankSanctionFailCount);
	return row;
}

function setFraugster(fraugster) {
	var custType = $('#account_clientType').text();
	
	if(custType == 'CFX'){

		$("#fraugster").empty();
		var eventId = getEmptyIfNull(fraugster.id);
		var createdOn = getEmptyIfNull(fraugster.createdOn);
		var updatedBy = getEmptyIfNull(fraugster.updatedBy);
		var fraugsterId = getEmptyIfNull(fraugster.fraugsterId);
		var score = getEmptyIfNull(fraugster.score);
		var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('
			+ eventId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden">' + eventId + '</td>';
		row += '<td class="nowrap">' + createdOn + '</td>';
		row += '<td class="nowrap">' + updatedBy + '</td>';
		// row += '<td><a href="#">' + fraugsterId + '</a></td>';
		row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		//row += '<td class="number">' + score + '</td>'
		row += '<td class="number">Not Required</td>'
		row += '</tr>'
		$("#fraugster").append(row);
	}
	else {
		$("#fraugster").empty();
		var eventId = getEmptyIfNull(fraugster.id);
		var createdOn = getEmptyIfNull(fraugster.createdOn);
		var updatedBy = getEmptyIfNull(fraugster.updatedBy);
		var fraugsterId = getEmptyIfNull(fraugster.fraugsterId);
		var score = getEmptyIfNull(fraugster.score);
		var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('
			+ eventId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden">' + eventId + '</td>';
		row += '<td class="nowrap">' + createdOn + '</td>';
		row += '<td class="nowrap">' + updatedBy + '</td>';
		// row += '<td><a href="#">' + fraugsterId + '</a></td>';
		row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		row += '<td class="number">' + score + '</td>'
		row += '</tr>'
		$("#fraugster").append(row);
	}
}

function setPaymentOutDetails(paymentOutInfo) {
	setTextById('payment_transNum',
			getEmptyIfNull(paymentOutInfo.transactionNumber));
	setTextById('payment_complianceStatus',
			getEmptyIfNull(paymentOutInfo.status));
	setTextById('payment_dateOfPayment',
			getEmptyIfNull(paymentOutInfo.dateOfPayment));
	setTextById('payment_amount', getEmptyIfNull(paymentOutInfo.amount));
	setTextById('payment_buyCurrency',
			getEmptyIfNull(paymentOutInfo.buyCurrency));
	setTextById('payment_countryOfBeneficiary',
			getEmptyIfNull(paymentOutInfo.countryOfBeneficiary));
	setTextById('payment_beneficiaryName',
			getEmptyIfNull(paymentOutInfo.beneficiaryName));
	setTextById('payment_reasonForTransfer',
			getEmptyIfNull(paymentOutInfo.reasonForTransfer));
	setTextById('payment_regReasonForTrade',
			getEmptyIfNull(paymentOutInfo.regReasonForTrade));
	setValueById('paymentOutId', paymentOutInfo.id);
	setValueById('tradePaymentId', paymentOutInfo.tradePaymentId);
	setValueById('trade_beneficiary_id', paymentOutInfo.tradeBeneficiaryId);
	setPaymentStatus(paymentOutInfo.status);

}

/*
 * function setStatusReasons(reasonse) {
 * $("#payment_StatusReasons").data('prePaymentStatusReasons', reasonse);
 * $.each(reasonse, function(index, reason) { var name = reason.name; var value =
 * reason.value; if (value) { if ($("input[value='" + name +
 * "'][name='paymentStatusReason']") .prop('checked') === false) {
 * $("input[value='" + name + "'][name='paymentStatusReason']") .prop('checked',
 * true); $("input[value='" + name + "'][name='paymentStatusReason']")
 * .trigger('change'); } } else { if ($("input[value='" + name +
 * "'][name='paymentStatusReason']") .prop('checked') === true) {
 * $("input[value='" + name + "'][name='paymentStatusReason']") .prop('checked',
 * false); $("input[value='" + name + "'][name='paymentStatusReason']")
 * .trigger('change'); } } }); }
 */

function getYesOrNoCell(status) {
	if (status) {
		return '<td class="yes-cell"><i class="material-icons">check</i></td>';
	} else {
		return '<td class="no-cell"><i class="material-icons">clear</i></td>';
	}
}

function lockResource() {
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId === null || userResourceLockId === undefined
			|| userResourceLockId === "") {
		var resourceId = $('#paymentOutId').val();
		var resourceType = 'PAY_OUT';
		var request = getLockResourceRequest(resourceId, resourceType);
		postLockOrUnlock(request);
		console.log(request);
	}

}

function unlockResource() {
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId !== null && userResourceLockId !== undefined
			&& userResourceLockId !== "") {
		var resourceId = $('#paymentOutId').val();
		var resourceType = 'PAY_OUT';
		var request = getUnlockResourceRequest(resourceId, resourceType,
				userResourceLockId);
		postLockOrUnlock(request);
		console.log(request);
	}
}

function setPaymentStatus(status) {
	if (status === 'CLEAR') {
		$("#payment_status_active").trigger('change');
	}
	if (status === 'INACTIVE') {
		$("#payment_status_inactive").trigger('change');
	}
	if (status === 'REJECTED') {
		$("#payment_status_reject").trigger('change');
	}
}

function setPaymentStatusReasons(reasons) {
	// $("#payment_StatusReasons").data('prePaymentStatusReasons',reasonse);
	$
			.each(
					reasons,
					function(index, reason) {
						var name = reason.name;
						var value = reason.value;
						if (value) {
							if ($(
									"input[value='"
											+ name
											+ "'][name='payOutDetails_payOutStatusReasons']")
									.prop('checked') === false) {
								$(
										"input[value='"
												+ name
												+ "'][name='payOutDetails_payOutStatusReasons']")
										.prop('checked', true);
								$(
										"input[value='"
												+ name
												+ "'][name='payOutDetails_payOutStatusReasons']")
										.trigger('change');
							}
						} else {
							if ($(
									"input[value='"
											+ name
											+ "'][name='payOutDetails_payOutStatusReasons']")
									.prop('checked') === true) {
								$(
										"input[value='"
												+ name
												+ "'][name='payOutDetails_payOutStatusReasons']")
										.prop('checked', false);
								$(
										"input[value='"
												+ name
												+ "'][name='payOutDetails_payOutStatusReasons']")
										.trigger('change');
							}
						}
					});
}

function setWatchlist(watchlistData) {
	// $("#payment_watchlists").data('preWatchlists',watchlistData);
	$("#payment_watchlists").empty();
	var watchlists = '';
	$.each(watchlistData, function(index, watchlist) {
		var name = watchlist.name;
		var contactId = watchlist.contactId;

		watchlists += '<li><label for="chk-watchlist-' + index
				+ '"><input type="hidden" class="watch_con"  value="'
				+ contactId + '" /><input id="chk-watchlist-' + index
				+ '" type="checkbox" name="payment_watchlist[]" value="' + name
				+ '" checked/>' + name + '					</label></li>';
	});
	$("#multilist-unwatch").data("chosen-options", []);
	// $("#removedWatchlist").empty();
	$("#payment_watchlists").append(watchlists);
	$.each(watchlistData, function(index, watchlist) {
		var name = watchlist.name;
		$("input[value='" + name + "'][name='payment_watchlist[]']").trigger(
				'change');
	});
}

function setContactWatchlist(conWatchData) {
	$("#contactWatchlist").empty();
	var watchlists = '';
	$.each(conWatchData, function(index, watchlist) {
		var name = watchlist.name;
		watchlists += '<li>' + name + '</li>';
	});
	$("#contactWatchlist").append(watchlists);
}

function setFurtherPaymentInDetails(paymentInData) {
	var rows = '';
	$("#further_paymentInDetails").empty();
	$.each(paymentInData, function(index, payment) {
		var dateOfPayment = getEmptyIfNull(payment.dateOfPayment);
		var amount = getEmptyIfNull(payment.amount);
		var sellCurrency = getEmptyIfNull(payment.sellCurrency);
		var method = getEmptyIfNull(payment.method);
		var accountName = getEmptyIfNull(payment.accountName);
		var account = getEmptyIfNull(payment.account);
		var riskGuardianScore = getEmptyIfNull(payment.riskGuardianScore);
		var dash = "-";
		var row = '<tr>';
		row += '<td class="nowrap">' + dateOfPayment + '</td>';
		row += '<td class="number">' + amount + '</td>';
		row += '<td class="nowrap">' + sellCurrency + '</td>';
		row += '<td class="nowrap">' + method + '</td>';
		row += '<td class="nowrap">' + accountName + '</td>';
		if (account == "-")
			row += '<td style="font-weight:bold" class = "center">' + account
					+ '</td>'
		else
			row += '<td class="breakword">' + account + '</td>';
		if (riskGuardianScore == "-" || riskGuardianScore == "")
			row += '<td style="font-weight:bold" class = "center">' + dash
					+ '</td>';
		else
			row += '<td class="breakword">' + riskGuardianScore + '</td>';
		row += '</tr>'
		rows += row;
	});

	$("#further_paymentInDetails").append(rows);

}

function setFurtherPaymentOutDetails(paymentOutData) {

	var rows = '';
	$("#further_paymentOutDetails").empty();
	$.each(paymentOutData, function(index, payment) {
		var dateOfPayment = getEmptyIfNull(payment.dateOfPayment);
		var amount = getEmptyIfNull(payment.amount);
		var buyCurrency = getEmptyIfNull(payment.buyCurrency);
		var accountName = getEmptyIfNull(payment.accountName);
		var account = getEmptyIfNull(payment.account);
		var row = '<tr>';
		row += '<td class="nowrap">' + dateOfPayment + '</td>';
		row += '<td class="number">' + amount + '</td>';
		row += '<td class="nowrap">' + buyCurrency + '</td>';
		row += '<td class="nowrap">' + accountName + '</td>';
		row += '<td class="number">' + account + '</td>';
		row += '</tr>'
		rows += row;
	});

	$("#further_paymentOutDetails").append(rows);
}

function resendSanction(entityId, entityType) {

	var sanction = {};
	var paymentOutId = Number($('#paymentOutId').val());
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	/**
	 * get sanction Contact, Bank and Beneficiary status value, this value are
	 * used to update sanction status in paymentout table : Abhijit G
	 */
	var sanctionContactStatus = $('#paymentOutSanctionContactStatus').val();
	var sanctionBankStatus = $('#paymentOutSanctionBankStatus').val();
	var sanctionBeneficiaryStatus = $('#paymentOutSanctionBeneficiaryStatus').val();
	
	if(sanctionBeneficiaryStatus==="" || sanctionBeneficiaryStatus===null ||sanctionBeneficiaryStatus===undefined){
		sanctionBeneficiaryStatus="SERVICE_FAILURE";
	}
	
	if(sanctionBankStatus==="" || sanctionBankStatus===null ||sanctionBankStatus===undefined){
		sanctionBankStatus="SERVICE_FAILURE";
	}
	
	if(sanctionContactStatus==="" || sanctionContactStatus===null ||sanctionContactStatus===undefined){
		sanctionContactStatus="SERVICE_FAILURE";
	}
	
	
	
	if (sanctionContactStatus == notRequiredFormattedStatus) {
		sanctionContactStatus = notRequiredStatus;
		$('#paymentOutSanctionContactStatus').val(notRequiredStatus)
	}
	if (sanctionBankStatus == notRequiredFormattedStatus) {
		sanctionBankStatus = notRequiredStatus;
		$('#paymentOutSanctionBankStatus').val(notRequiredStatus)
	}
	if (sanctionBeneficiaryStatus == notRequiredFormattedStatus) {
		sanctionBeneficiaryStatus = notRequiredStatus;
		$('#paymentOutSanctionBeneficiaryStatus').val(notRequiredStatus);
	}
	if (entityType === "CONTACT") {
		$('#gifloaderforResendSanction').css('visibility', 'visible');
	} else if (entityType === "BENEFICIARY") {
		$('#gifloaderforResendSanctionbenefeciary')
				.css('visibility', 'visible');
	} else if (entityType == "BANK") {
		$('#gifloaderforResendSanctionbank').css('visibility', 'visible');
	}
	addField('paymentOutId', paymentOutId, sanction);
	addField('tradeAccountNumber', tradeAccountNumber, sanction);
	addField('tradeContactId', tradeContactId, sanction);
	addField('tradePaymentId', tradePaymentId, sanction);
	addField('entityId', entityId, sanction);
	addField('entityType', entityType, sanction);
	addField('org_code', orgCode, sanction);
	addField('cust_type', custType, sanction);
	addField('contactStatus', sanctionContactStatus, sanction);
	addField('bankStatus', sanctionBankStatus, sanction);
	addField('beneficiaryStatus', sanctionBeneficiaryStatus, sanction);
	postSanctionResend(sanction, getComplianceServiceBaseUrl(), getUser());
}

function resendCustomCheck() {
	$('#gifloaderforCustomcheckresend').css('visibility', 'visible');
	var request = {};
	var paymentOutId = Number($('#paymentOutId').val());
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	var accNumber = $('#beneAccountNum').text();
	addField('paymentOutId', paymentOutId, request);
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('tradeContactId', tradeContactId, request);
	addField('tradePaymentId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	addField('beneAccountNumber', accNumber, request);
	postCustomeCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postCustomeCheckResend(request, baseUrl, user) {
	disableButton('payout_customChecks_recheck');
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/customCheck',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforCustomcheckresend').css('visibility',
							'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayOutId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					setCustomCheckResend(data.response, data.checkedOn);
					getActivities(1, 10, Number($('#paymentOutId').val()),
							false);
					enableButton('payout_customChecks_recheck');
					populateSuccessMessage("main-content__body",
							"Custom repeat check Successfully done",
							"payout_customChecks_recheck_error_field",
							"payout_customChecks_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevCustomCheckStatus = $('#customCheckStatus').val();
					var currentCustomCheckStatus = data.response.overallStatus;
					if (prevCustomCheckStatus.toUpperCase() != currentCustomCheckStatus && currentCustomCheckStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
						updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						var previousCustomCheckStatus = document.getElementById("customCheckStatus");
						previousCustomCheckStatus.value = currentCustomCheckStatus;
					}
				},
				error : function() {
					$('#gifloaderforCustomcheckresend').css('visibility',
							'hidden');
					enableButton('payout_customChecks_recheck');
					populateErrorMessage("main-content__body",
							"Error whilte resending custom check",
							"payout_customChecks_recheck_error_field",
							"payout_customChecks_recheck");
				}
			});
}

function postSanctionResend(request, baseUrl, user) {
	var entityType = request.entityType.toLowerCase();
	console.log("Sanction Request"+getJsonString(request));
	disableButton('updateSanction_' + entityType);
	disableButton('sanction_recheck_' + entityType);
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/fundsOutSanction',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					if (entityType === "contact") {
						$('#gifloaderforResendSanction').css('visibility',
								'hidden');
					} else if (entityType === "beneficiary") {
						$('#gifloaderforResendSanctionbenefeciary').css(
								'visibility', 'hidden');
					} else if (entityType === "bank") {
						$('#gifloaderforResendSanctionbank').css('visibility',
								'hidden');
					}
					if(data === undefined || data.summary === undefined || (data.responseCode !== undefined && data.responseCode === '888') ){
						populateErrorMessage("main-content__body",
								"Error while resending sanction for " + entityType,
								"sanction_recheck_" + entityType + "_error_field",
								"sanction_recheck_" + entityType);
						enableButton('updateSanction_' + entityType);
						enableButton('sanction_recheck_' + entityType);
					}
					var addedRecords = Number($('#actLogTotalRecordsPayOutId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					
					var currentSanctionStatus = data.summary.status;
					if (entityType === "contact") {
						var prevSanctionStatus = $('#paymentOutSanctionContactStatus').val();
					} else if (entityType === "beneficiary") {
						var prevSanctionStatus = $('#paymentOutSanctionBeneficiaryStatus').val();
					} else if (entityType == "bank") {
						var prevSanctionStatus = $('#paymentOutSanctionBankStatus').val();
					}
					
					setSanctionStatusValuesToHiddenFields(data.summary,
							request.entityType);
					if (data.summary.status === 'PASS') {
						data.summary.status = true;
					} else {
						data.summary.status = false;
					}
					setResendSanctionResponse(data.summary, request.entityType,
							request.entityId);
					getActivities(1, 10, Number($('#paymentOutId').val()),
							false);
					enableButton('updateSanction_' + entityType);
					enableButton('sanction_recheck_' + entityType);
					populateSuccessMessage("main-content__body",
							"Sanction Repeat Check Successfully done for "
									+ entityType, "sanction_recheck_"
									+ entityType + "_error_field",
							"sanction_recheck_" + entityType);
							
					var accountTMFlag = $('#accountTMFlag').val();
					if (prevSanctionStatus.toUpperCase() != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
						updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						if (entityType === "contact") {
							var previousSanctionStatus = document.getElementById("paymentOutSanctionContactStatus");
							previousSanctionStatus.value = currentSanctionStatus;
						} else if (entityType === "beneficiary") {
							var previousSanctionStatus = document.getElementById("paymentOutSanctionBeneficiaryStatus");
							previousSanctionStatus.value = currentSanctionStatus;
						} else if (entityType == "bank") {
							var previousSanctionStatus = document.getElementById("paymentOutSanctionBankStatus");
							previousSanctionStatus.value = currentSanctionStatus;
						}
					}
				},
				error : function() {
					if (entityType === "contact") {
						$('#gifloaderforResendSanction').css('visibility',
								'hidden');
					} else if (entityType === "beneficiary") {
						$('#gifloaderforResendSanctionbenefeciary').css(
								'visibility', 'hidden');
					} else if (entityType == "bank") {
						$('#gifloaderforResendSanctionbank').css('visibility',
								'hidden');
					}
					enableButton('updateSanction_' + entityType);
					enableButton('sanction_recheck_' + entityType);
					populateErrorMessage("main-content__body",
							"Error while resending sanction for " + entityType,
							"sanction_recheck_" + entityType + "_error_field",
							"sanction_recheck_" + entityType);
				}
			});
}

function isRecordLocked(response) {
	return response.lock;
}

function getUserNameOfLockedRecord(response) {
	return response.userName;
}

/*
 * function setStatusReasonReasonse(reasonse) {
 * $("#payOutDetails_paymentOutStatusReasons").data('prePayStatusReasons',reasonse);
 * $.each(reasonse, function(index, reason) { var name = reason.name; var value=
 * reason.value; if(value) { if
 * ($("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").prop('checked')
 * === false){
 * $("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").prop('checked',
 * true);
 * $("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").trigger('change'); } }
 * else { if
 * ($("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").prop('checked')
 * === true){
 * $("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").prop('checked',
 * false);
 * $("input[value='"+name+"'][name='payOutDetails_payOutStatusReasons']").trigger('change'); } }
 * }); }
 */

function setContactSanction(sanction) {
	// $("#sanction_contact").empty();
	$("#sanctions_contact").empty();
	var rows = '';
	rows += getContactSanctionRow(sanction);
	$("#sanctions_contact").append(rows);
}

function setResendSanctionResponse(sanction, entityType, entityId) {

	var id = 'sanctions_' + entityType.toLowerCase();
	var rows = '';
	rows += getSanctionRow(sanction, entityType, entityId);
	$("#" + id).prepend(rows);
	var id_Ext = entityType.toLowerCase();
	var noOfRows = countRows("sanctions_" + id_Ext);
	var totalRecords = Number($('#sanctionTotalRecordsPayOutId_' + id_Ext)
			.val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdSanc_" + id_Ext);
	updateViewMoreBlock(leftRecords, "viewMorePayOut_SancId_" + id_Ext,
			"viewMoreDetailsPayOut_Sanc_" + id_Ext);
}

function getSanctionRow(sanction, entityType, entityId) {
	/*
	 * var eventId = getEmptyIfNull(sanction.contactSanction.eventId); var
	 * entity = getEmptyIfNull(sanction.entityType);
	 */
	var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
	var updatedOn = getDateTimeFormat(getEmptyIfNull(sanction.updatedOn));
	var updatedBy = getEmptyIfNull(sanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.worldCheck);
	var status = getEmptyIfNull(sanction.status);
	var row = '<tr>';
	row += '<td hidden="hidden">' + eventServiceLogId + '</td>';
	row += '<td hidden="hidden">' + entityType + '</td>';
	row += '<td hidden="hidden">' + entityId + '</td>';
	/*
	 * row += '<td class="center"><input type="checkbox"/></td>'; row += '<td>' +
	 * entity + '</td>';
	 */
	row += '<td>' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	// row += '<td class="nowrap">' + sanctionId + '</td>';
	row += '<td><a href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	row += getYesOrNoCell(status);
	entityType = entityType.toLowerCase();
	if (entityType === 'contact') {
		var totalrecords = $("#sanctionTotalRecordsPayOutId_contact").val();
		totalrecords++;
		if (status) {
			setContactSanctionIndicator(totalrecords, 0);
			setContactSanctionTotalRecords(totalrecords);
		} else {
			setContactSanctionIndicator(0, totalrecords);
			setContactSanctionTotalRecords(totalrecords);
		}
	} else if (entityType === 'beneficiary') {
		var totalrecord = $("#sanctionTotalRecordsPayOutId_beneficiary").val();
		totalrecord++;
		if (status) {
			setBeneficiarySanctionIndicator(totalrecord, 0);
			setBeneficiarySanctionTotalRecords(totalrecord);
		} else {
			setBeneficiarySanctionIndicator(0, totalrecord);
			setBeneficiarySanctionTotalRecords(totalrecord);
		}
	} else {
		var totalrecords = $("#sanctionTotalRecordsPayOutId_bank").val();
		totalrecords++;
		if (status) {
			setBankSanctionIndicator(totalrecords, 0);
			setBankSanctionTotalRecords(totalrecords);
		} else {
			setBankSanctionIndicator(0, totalrecords);
			setBankSanctionTotalRecords(totalrecords);
		}
	}
	row += '</tr>'
	return row;
}

function setBeneficiarySanction(sanction) {
	// $("#sanction_beneficiary").empty();
	$("#sanctions_beneficiary").empty();
	var rows = '';
	rows += getBenficiarySanctionRow(sanction);
	$("#sanctions_beneficiary").append(rows);

}

function setBankSanction(sanction) {
	// $("#sanction_bank").empty();
	$("#sanctions_bank").empty();
	var rows = '';
	rows += getBankSanctionRow(sanction);
	$("#sanctions_bank").append(rows);

}

function setCustomCheck(customCheck) {
	$("#customChecks").empty();
	//Add Cust Type condition and CustomRuleFraudPredictCheck for AT-3161
	var custType = $('#account_clientType').text();
	var flag = $('#customRule_FPFlag').val();
	if (customCheck.id !== undefined && customCheck.id !== null) {
		if(custType == "PFX" && flag == "true"){
			$("#customChecks").append(
					getVelocityCheck(customCheck.checkedOn,
							customCheck.velocityCheck)
							+ getWhitelistCheck(customCheck.whiteListCheck)
							 + getCustomRuleEuPoiCheck(customCheck.euPoiCheck)
							 + getCustomRuleFraudPredictCheck(customCheck.fraudPredictStatus));
			
		}else{
			$("#customChecks").append(
					getVelocityCheck(customCheck.checkedOn,
							customCheck.velocityCheck)
							+ getWhitelistCheck(customCheck.whiteListCheck)
							+ getCustomRuleEuPoiCheck(customCheck.euPoiCheck));
		}
	}

	setBeneficiaryCustomCheckIndicator(customCheck.passCount,
			customCheck.failCount);
}

function setCustomCheckResend(customCheck, checkedOn) {

	//Add Cust Type condition and CustomRuleFraudPredictCheck for AT-3161
	var custType = $('#account_clientType').text();
	var flag = $('#customRule_FPFlag').val();
	if(custType == "PFX" && flag == "true"){
		$("#customChecks").prepend(
				getVelocityCheck(getDateTimeFormat(getEmptyIfNull(checkedOn)),
						customCheck.velocityCheck)
						+ getWhitelistCheck(customCheck.whiteListCheck)
						+ getCustomRuleEuPoiCheck(customCheck.euPoiCheck)
						+ getCustomRuleFraudPredictCheck(customCheck.fraudPredictStatus));
		
	}else{
		$("#customChecks").prepend(
				getVelocityCheck(getDateTimeFormat(getEmptyIfNull(checkedOn)),
						customCheck.velocityCheck)
						+ getWhitelistCheck(customCheck.whiteListCheck)
						+ getCustomRuleEuPoiCheck(customCheck.euPoiCheck));
	}
	var noOfRows = countRows("customChecks");
	var totalRecords = Number($('#customCheckTotalRecordsPayOutId').val()) + 1;
	var leftRecords = updateLeftRecordsForCusChk(noOfRows, totalRecords,
			"leftRecordsPayOutIdCusChk");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_CusChkId",
			"viewMoreDetailsPayOut_CusChk");
	Number($('#customCheckTotalRecordsPayOutId').val(totalRecords));
	if (customCheck.overallStatus === 'PASS') {
		setBeneficiaryCustomCheckIndicator(totalRecords, 0);
	} else {
		setBeneficiaryCustomCheckIndicator(0, totalRecords);
	}

}

/*
 * function getIpCheck(ipCheck){ var checkedOn =
 * getEmptyIfNull(ipCheck.checkedOn); var status =
 * getEmptyIfNull(ipCheck.status); var row = '<tr>'; row += '<td class="nowrap">'+checkedOn+'</td>';
 * row +='<td>IP Distance Check</td>'; row += '<td><ul>'+status+'</ul></td>';
 * row += '</tr>'; return row; }
 * 
 * function getGlobalCheck(globalCheck){ var checkedOn =
 * getEmptyIfNull(globalCheck.checkedOn); var status =
 * getEmptyIfNull(globalCheck.status); var row = '<tr>'; row += '<td class="nowrap">'+checkedOn+'</td>';
 * row +='<td>Global Check</td>'; row += '<td><ul>'+status+'</ul></td>';
 * row += '</tr>'; return row; }
 */

function setCountryCheck(countryCheck) {
	$("#countryCheck_beneficiary").empty();
	var checkedOn = getEmptyIfNull(countryCheck.checkedOn);
	var status = getEmptyIfNull(countryCheck.status);
	if (countryCheck.id != null) {
		var row = '<tr>';
		row += '<td class="nowrap">' + checkedOn + '</td>';
		row += '<td class="nowrap"><ul>' + status + '</ul></td>';
		row += '</tr>';
		$("#countryCheck_beneficiary").append(row);
		setCountryCheckIndicator(countryCheck.passCount, countryCheck.failCount);
	}
}

function getVelocityCheck(checkedOn, velocityCheck) {
	// var status = getEmptyIfNull(velocityCheck.status);
	// if(velocityCheck.id !=null){
	var velocityAmountCheckStatus = velocityCheck.permittedAmoutcheck;
	var beneCheckStatus = velocityCheck.beneCheck;
	var noOfFundsOutTxnCheck = velocityCheck.noOffundsoutTxn;
	var array = velocityCheck.matchedAccNumber;
	var row = '<tr>';
	row += '<td class="nowrap">' + checkedOn + '</td>';
	row += '<td >Velocity Check</td>';
	if(beneCheckStatus === "FAIL"){
		row += '<td class="nowrap"><ul class="breakwordHeader"><li class="breakwordHeader">Beneficary Check : '+ velocityCheck.beneCheck 
		+' (With account: ';
	    var anchors = getBeneRecentPaymentDetails(array);
		row+= anchors+')';
	   }     
	else
		row += '<td class="nowrap"><ul><li>Beneficary Check : '+ velocityCheck.beneCheck;
	if(velocityAmountCheckStatus === "FAIL") {
		row += '</li><li>Amount Check : '+ velocityCheck.permittedAmoutcheck;
		if(velocityCheck.maxAmount != null || velocityCheck.maxAmount != undefined){
			row+='(Max:'+velocityCheck.maxAmount+')';
		}
	}
	else 
		row += '</li><li>Amount Check : '+ velocityCheck.permittedAmoutcheck;
	if(noOfFundsOutTxnCheck === "FAIL") {
		row += '</li><li class="breakwordHeader">No of transaction check : '+ velocityCheck.noOffundsoutTxn 
		+' '+velocityCheck.noOfFundsOutTxnDetails + '</li></ul></td>';
	}
	else
		row += '</li><li class="breakwordHeader">No of transaction check : '+ velocityCheck.noOffundsoutTxn + '</li></ul></td>';
	row += '</tr>';
	return row;
	// }
}

//This function is added to add hyper links on trade account number for repeat check functionality
function getBeneRecentPaymentDetails(array){
	var i;
	var anchors='';
	var beneAccountNum= $('#beneAccountNumber').val();
    var res = array.replace(/^[, ]+|[, ]+$|[, ]+/g, " ").trim();
    var result=res.split(" ");
	for(i = 0; i < result.length; i++) {
	 anchors +='<a onclick="openPaymentDetail(\''+result[i]+'\',\''+beneAccountNum+'\')">' +result[i]+ '</a>';
	 if(i<(result.length)-1)
	 anchors +=', ';
  }
	return anchors;
}

function getWhitelistCheck(whiteListCheck) {
	
	var reasonOfTransfer=whiteListCheck.reasonOfTransfer;
	if(whiteListCheck.reasonOfTransfer === 'NOT_REQUIRED' ){
		reasonOfTransfer='Not Required'
	}
	var checkedOn = "checkedon";// getEmptyIfNull(velocityCheck.checkedOn);
	// var status = getEmptyIfNull(velocityCheck.status);
	// if(velocityCheck.id !=null){
	var amountRangeStatus = whiteListCheck.amoutRange;
	var row = '<tr>';
	row += '<td class="nowrap"></td>';
	row += '<td >Whitelist Check</td>';
	if(amountRangeStatus === "FAIL") {
		row += '<td class="nowrap"><ul><li>Currency Check : '
				+ whiteListCheck.currency + '</li><li>Amount range check : '
				+ whiteListCheck.amoutRange +' (Max:'+getDashIfNull(whiteListCheck.maxAmount)+')'
				+ '</li><li>Reason of transfer check : '
				+ reasonOfTransfer + '</li></ul></td>'; // <li>Third party check : '+whiteListCheck.thirdParty+'</li>
	} else {
		row += '<td class="nowrap"><ul><li>Currency Check : '
			+ whiteListCheck.currency + '</li><li>Amount range check : '
			+ whiteListCheck.amoutRange
			+ '</li><li>Reason of transfer check : '
			+ reasonOfTransfer + '</li></ul></td>'; // <li>Third // party // check : '+whiteListCheck.thirdParty+'</li>
	}
	row += '</tr>';
	return row;
	// }
}

//Add for AT-3161
function getCustomRuleFraudPredictCheck(fraudPredictStatus) {
	
	var row = '<tr>';
	row += '<td class="nowrap"></td>';
	row += '<td >FraudPredict Check</td>';
	row += '<td class="nowrap"><ul><li> Status : '+ fraudPredictStatus +'</li></ul></td>';
	row += '</tr>';
	return row;
}
//Add for AT-3349
function getCustomRuleEuPoiCheck(euPoiCheck) {
	 var poiStatus = euPoiCheck.status;
	var row = '<tr>';
	row += '<td class="nowrap"></td>';
	row += '<td >EUPOIDoc Check</td>';
	row += '<td class="nowrap"><ul><li> Status : '+ poiStatus +'</li></ul></td>';
	row += '</tr>';
	return row;
}

function setPaymentOutInfo(status) {
	$("#payment_status").empty();
	var data = '';
	if (status === 'SEIZE') {
		data = '<span class="indicator--negative" id="payment_complianceStatus1">'
				+ "SEIZE" + '</span>';

	} else if (status === 'REJECT') {
		data = '<span class="indicator--negative" id="payment_complianceStatus">'
				+ status + '</span>';

	} else if (status === 'HOLD') {
		data = '<span class="indicator--neutral" id="payment_complianceStatus">'
				+ status + '</span>';
	} else if (status === 'CLEAR') {
		data = '<span class="indicator--positive" id="payment_complianceStatus">'
				+ status + '</span>';
	}
	$("#payment_status").append(data);
}

function getActivities(minRecord, maxRecord, paymentOutId, isViewMoreRequest) {
	var request = {};
	var comment = $('#comments').val();
	var accountId= $('#contact_accountId').val();
	var rowToFetch = getViewMoreRecordsize();
	addField("minRecord", minRecord, request);
	addField("maxRecord", maxRecord, request);
	addField("entityId", paymentOutId, request);
	addField("comment",comment,request);
	addField("accountId",accountId,request);
	addField("rowToFetch",rowToFetch,request);
	getActivityLogs(request, isViewMoreRequest);
}

function getActivityLogs(request, isViewMoreRequest) {
	$.ajax({
		url : '/compliance-portal/paymentOutActivites',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if (isViewMoreRequest) {
				setActivityLogViewMore(data.activityLogData);
			} else {
				setActivityLog(data.activityLogData);
				setViewMoreActLogData();
			}
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function setPaymentOutStatuses(statuses) {
	var data = '';
	$.each(statuses, function(index, statusData) {
		data += '<li>';
		if (statusData.status === 'CLEAR') {
			if (statusData.isSelected) {
				data += getClearStatusData(true, statusData.status);
			} else {
				data += getClearStatusData(false, statusData.status);
			}

		} else if (statusData.status === 'WATCHED') {
			if (statusData.isSelected) {
				data += getUnwatchStatusData(true, statusData.status);
			} else {
				data += getUnwatchStatusData(false, statusData.status);
			}
		} else if (statusData.status === 'REJECT') {
			if (statusData.isSelected) {
				data += getRejectStatusData(true, statusData.status);
			} else {
				data += getRejectStatusData(false, statusData.status);
			}
		} else if (statusData.status === 'SEIZE') {
			if (statusData.isSelected) {
				data += getSeizeStatusData(true, statusData.status);
			} else {
				data += getSeizeStatusData(false, statusData.status);
			}
		} else if (statusData.status === 'HOLD') {
			if (statusData.isSelected) {
				data += getHoldStatusData(true, statusData.status);
			} else {
				data += getHoldStatusData(false, statusData.status);
			}
		}
		data += '</li>';
	});

	$('#paymentOutDetails_Status_radio').empty();
	$('#paymentOutDetails_Status_radio').append(data);
	$("#rad-status-clear").data('more-hide', 'input-more-areas-reasons');
	$("#input-more-unwatch").addClass("input-more-areas__area--hidden")
			.removeClass("input-more-areas__area");
	window['CurrenciesDirect'].InputMores.init();

}

function getRejectStatusData(isOn, Status) {

	if (isOn) {
		return '<label class="pill-choice__choice--negative pill-choice__choice--on" for="rad-status-reject">'
				+ '<input id="rad-status-reject" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons" checked />'
				+ 'Reject			</label>';
		$("#input-more-reject-reasons").removeClass(
				'input-more-areas__area--hidden').addClass(
				'input-more-areas__area');
		$("#rad-status-reject").change();
	} else {
		return '<label class="pill-choice__choice--negative" for="rad-status-reject">'
				+ '<input id="rad-status-reject" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons" />'
				+ 'Reject			</label>';
	}

}

function getClearStatusData(isOn, Status) {

	if (isOn) {
		return '<label class="pill-choice__choice--positive pill pill-choice__choice--on" for="rad-status-clear">'
				+ '<input id="rad-status-clear" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more-hide" data-more-area="input-more-areas-reasons" checked />'
				+ 'Clear			</label>';

		$("#rad-status-clear").change();

	} else {
		return '<label class="pill-choice__choice--positive pill" for="rad-status-clear">'
				+ '<input id="rad-status-clear" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more-hide" data-more-area="input-more-areas-reasons" />'
				+ 'Clear			</label>';
	}

}

function getUnwatchStatusData(isOn, Status) {

	if (isOn) {
		return '<label class="pill-choice__choice--positive pill pill-choice__choice--on" for="rad-status-unwatch">'
				+ '<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-unwatch" checked />'
				+ 'Unwatch			</label>';

		$("#rad-status-unwatch").change();
	} else {
		return '<label class="pill-choice__choice--positive" for="rad-status-unwatch">'
				+ '<input id="rad-status-unwatch" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-unwatch" />'
				+ 'Unwatch			</label>';
	}

}

function getSeizeStatusData(isOn, Status) {

	if (isOn) {
		return '<label class="pill-choice__choice--negative pill pill-choice__choice--on" for="rad-status-seize">'
				+ '<input id="rad-status-seize" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons" checked />'
				+ 'Seize			</label>';
		$("#input-more-reject-reasons").removeClass(
				'input-more-areas__area--hidden').addClass(
				'input-more-areas__area');
		$("#rad-status-seize").change();
	} else {
		return '<label class="pill-choice__choice--negative" for="rad-status-seize">'
				+ '<input id="rad-status-seize" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons" />'
				+ 'Seize			</label>';
	}
}

function getHoldStatusData(isOn, Status) {

	if (isOn) {
		return '<label class="pill-choice__choice--neutral pill pill-choice__choice--on" for="rad-status-hold">'
				+ '<input id="rad-status-hold" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons" checked />'
				+ 'Hold			</label>';
		$("#input-more-reject-reasons").removeClass(
				'input-more-areas__area--hidden').addClass(
				'input-more-areas__area');
		$("#rad-status-hold").change();

	} else {
		return '<label class="pill-choice__choice--neutral" for="rad-status-hold">'
				+ '<input id="rad-status-hold" type="radio" name="paymentStatus" value="'
				+ Status
				+ '" class="input-more" data-more-area="input-more-reject-reasons"  />'
				+ 'Hold			</label>';
	}
}

function setBeneficiaryCustomCheckIndicator(passCount, failCount) {
	$('#payOutDetails_customchecks_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Custom checks';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'beneficiary_customCheck_positive');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'beneficiary_customCheck_negative');
	}
	indicator += '</a>';
	$('#payOutDetails_customchecks_indicatior').append(indicator);
}

function setCountryCheckIndicator(passCount, failCount) {
	$('#payOutDetails_countrycheck_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Country Check';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount, 'countryCheck_negative');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount, 'countryCheck_positive');
	}
	indicator += '</a>';
	$('#payOutDetails_countrycheck_indicatior').append(indicator);
}

function setBeneficiaryBlacklistIndicator(passCount, failCount) {
	$('#beneficiary_blacklist_indicator').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist Beneficiary';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'beneficiary_blacklist_positive');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'beneficiary_blacklist_negative');
	}
	indicator += '</a>';
	$('#beneficiary_blacklist_indicator').append(indicator);
}

function setContactBlacklistIndicator(passCount, failCount) {
	$('#contact_blacklist_indicator').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist Contact';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'contact_blacklist_positive');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'contact_blacklist_negative');
	}
	indicator += '</a>';
	$('#contact_blacklist_indicator').append(indicator);
}

function setContactSanctionIndicator(passCount, failCount) {
	$('#payOutDetails_sanctioncon_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions Contact';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'payoutDetails_contactSanctionPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'payoutDetails_contactSanctionFail');
	}
	indicator += '</a>';
	$('#payOutDetails_sanctioncon_indicatior').append(indicator);
}

function setBeneficiarySanctionIndicator(passCount, failCount) {
	$('#payOutDetails_sanctionben_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions Beneficiary';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'payoutDetails_beneficiarySanctionPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'payoutDetails_beneficiarySanctionFail');
	}
	indicator += '</a>';
	$('#payOutDetails_sanctionben_indicatior').append(indicator);
}

function setBankSanctionIndicator(passCount, failCount) {
	$('#payOutDetails_sanctionbank_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions Bank';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'payoutDetails_bankSanctionPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'payoutDetails_bankSanctionFail');
	}
	indicator += '</a>';
	$('#payOutDetails_sanctionbank_indicatior').append(indicator);
}

//Added for AT-3658
function setPaymentReferenceCheckIndicator(passCount, failCount) {
	$('#payOutDetails_paymentReferencecheck_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Reference Check';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'paymentReferenceCheck_positive');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'paymentReferenceCheck_negative');
	}
	indicator += '</a>';
	$('#payOutDetails_paymentReferencecheck_indicatior').append(indicator);
}

function viewMoreDetails(serviceType, id, totalRecordsId, leftRecordsId,
		entityType) {

	var viewMore = {};
	var noOfRows = countRows(id);
	if(id=="fraugster")// AT-3325
		 noOfRowsFrg=countRows("fraugster");
		if(id=="sanctions_bank") 
			 noOfRowsSanBank=countRows(id);
			if( id=="sanctions_beneficiary")
				 noOfRowsSanBene=countRows(id);
			if(id=="sanctions_contact")
				 noOfRowsSanContact=countRows(id);
		if(id == "paymentReference")
			noOfRowsPayRefChk = countRows(id);
		if(id == "TRANSACTION_MONITORING")
			noOfRowsIntuitionCheck = countRows(id);
		if((clicked==false) || (clicked== true && noOfRowsFrg>prevCountfrg)||(clicked== true && noOfRowsSanBank>prevCountBank)||(clicked== true && noOfRowsSanBene>prevCountBene)||(clicked== true && noOfRowsSanContact>prevCountContact)
		 || (clicked==true && noOfRowsPayRefChk > prevCountPayRef) || (clicked==true && noOfRowsIntuitionCheck > prevCountIntuitionCheck))
		{
		clicked=true;
	var totalRecords = Number($("#" + totalRecordsId).val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, leftRecordsId);
	var paymentOutId = Number($('#paymentOutId').val());
	var orgCode = $('#account_organisation').text();
	var clientType = $('#account_clientType').text();
	var accountId = $('#contact_accountId').val();
	var minViewRecord = calculateMinRecord(noOfRows);
	var maxViewRecord = calculateMaxRecord(minViewRecord);

	addField('paymentOutId', paymentOutId, viewMore);
	addField('noOfDisplayRecords', noOfRows, viewMore);
	addField('totalRecords', totalRecords, viewMore);
	addField('minViewRecord', minViewRecord, viewMore);
	addField('maxViewRecord', maxViewRecord, viewMore);
	addField('leftRecords', leftRecords, viewMore);
	addField('organisation', orgCode, viewMore);
	addField('clientType', clientType, viewMore);
	addField('serviceType', serviceType, viewMore);
	addField('entityType', entityType, viewMore);
	addField('accountId', accountId, viewMore);
	if (serviceType == "KYC") {
		postKycMoreDetails(viewMore);
	} else if (serviceType == "SANCTION") {
		prevCountBank=noOfRowsSanBank;
		prevCountBene=noOfRowsSanBene;
		prevCountContact=noOfRowsSanContact;
		postSanctionMoreDetails(viewMore);
	} else if (serviceType == "FRAUGSTER") {
		prevCountfrg=noOfRowsFrg;
		postFraugsterMoreDetails(viewMore);
	} else if (serviceType == "VELOCITYCHECK") {
		var minViewRecordCustChk = calculateMinRecordCustChk(noOfRows);
		var maxViewRecordCustChk = calculateMaxRecordCustChk(minViewRecordCustChk);
		addField('minViewRecord', minViewRecordCustChk, viewMore);
		addField('maxViewRecord', maxViewRecordCustChk, viewMore);
		postCustomCheckMoreDetails(viewMore);
	} else if (serviceType == "ACTIVITYLOG") {
		getActivities(minViewRecord, maxViewRecord, paymentOutId, true);
	} else if (serviceType == "PAYMENT_IN") {
		postPaymentInMoreDetails(viewMore);
	} else if (serviceType == "PAYMENT_OUT") {
		postPaymentOutMoreDetails(viewMore);
	} else if (serviceType == "COUNTRYCHECK") {
		postCountryCheckMoreDetails(viewMore);
	} else if (serviceType == "BLACKLIST_PAY_REF") {
		prevCountPayRef = noOfRowsPayRefChk;
		postPaymentReferenceCheckMoreDetails(viewMore);
	} else if (serviceType == "TRANSACTION_MONITORING") {
		postIntuitionCheckMoreDetails(viewMore);
		prevCountIntuitionCheck=noOfRowsIntuitionCheck;
	}
	 
		}
}

function calculateMinRecord(noOfRows) {

	return noOfRows + 1;
}

function calculateMaxRecord(minViewRecord) {

	return minViewRecord + Number(getViewMoreRecordsize()) - 1;
}

function calculateMinRecordCustChk(noOfRows) {
	var minViewRecordCustChk ;
	var custType = $('#account_clientType').text();
	if(custType == "PFX" ){
		 minViewRecordCustChk = noOfRows / 2;
	}else{
		 minViewRecordCustChk = noOfRows / 2 + 1;
	}
	return minViewRecordCustChk;
}

function calculateMaxRecordCustChk(minViewRecordCustChk) {
	var viewMoreConfSize = Number(getViewMoreRecordsize());
	var totalRecords = Number($("#customCheckTotalRecordsPayOutId").val());
	var noOfRows = countRows("customChecks");
	var leftRecords = totalRecords - noOfRows / 2;
	if (leftRecords < viewMoreConfSize) {
		return minViewRecordCustChk + leftRecords;
	} else {
		return minViewRecordCustChk + Number(getViewMoreRecordsize()) - 1;
	}
}

function updateLeftRecords(noOfRows, totalRecords, id) {

	var leftRecord = totalRecords - noOfRows;
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + id).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + id).html("( " + leftRecord + " LEFT )");
	}

	return leftRecord;
}

function updateLeftRecordsForCusChk(noOfRows, totalRecords, id) {

	var leftRecord;
	//Add cust type for AT-3161
	var custType = $('#account_clientType').text();
	var flag = $('#customRule_FPFlag').val();
	if(custType == "PFX" && flag == "true"){
		 leftRecord = totalRecords - (noOfRows / 4);
	}else{
		 leftRecord = totalRecords - (noOfRows / 3);
	}
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + id).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + id).html("( " + leftRecord + " LEFT )");
	}

	return leftRecord;
}

function updateViewMoreBlock(leftRecords, viewMoreValue, viewMoreBlockId) {

	if (leftRecords === 0) {
		$("#" + viewMoreValue).html(leftRecords);
		$("#" + viewMoreBlockId).attr("disabled", true);
		$("#" + viewMoreBlockId).addClass("disabled");
	} else {
		$("#" + viewMoreBlockId).attr("disabled", false);
		$("#" + viewMoreBlockId).removeClass("disabled");
		if (leftRecords < Number(getViewMoreRecordsize())) {
			$("#" + viewMoreValue).html(leftRecords);
		} else {
			$("#" + viewMoreValue).html(Number(getViewMoreRecordsize()));
		}
	}
}

function countRows(id) {
	var totalRowCount = 0;
	var rowCount = 0;
	var table = document.getElementById(id);
	var rows = table.getElementsByTagName("tr");
	for (var i = 0; i < rows.length; i++) {
		totalRowCount++;
		if (rows[i].getElementsByTagName("td").length > 0) {
			rowCount++;
		}
	}
	return rowCount;
}

$("#payOutDetails_sanctioncon_indicatior")
		.click(
				function() {
					prevCountContact=0;
					clicked= false;// AT-3325
					noOfRowsSanContact=0;
					$("#sanctions_contact").find('tr').slice(1).remove();
					var noOfRows = countRows("sanctions_contact");
					var totalRecords = $(
							'#sanctionTotalRecordsPayOutId_contact').val();
					var leftRecords = updateLeftRecords(noOfRows, totalRecords,
							"leftRecordsPayOutIdSanc_contact");
					updateViewMoreBlock(leftRecords,
							"viewMorePayOut_SancId_contact",
							"viewMoreDetailsPayOut_Sanc_contact");

				});

$("#payOutDetails_sanctionben_indicatior").click(
		function() {
			prevCountBene=0;
			clicked= false;// AT-3325
			noOfRowsSanBene=0;
			$("#sanctions_beneficiary").find('tr').slice(1).remove();
			var noOfRows = countRows("sanctions_beneficiary");
			var totalRecords = $('#sanctionTotalRecordsPayOutId_beneficiary')
					.val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdSanc_beneficiary");
			updateViewMoreBlock(leftRecords,
					"viewMorePayOut_SancId_beneficiary",
					"viewMoreDetailsPayOut_Sanc_beneficiary");

		});

$("#payOutDetails_sanctionbank_indicatior").click(
		function() {
			prevCountBank=0;
			clicked= false;// AT-3325
			noOfRowsSanBank=0;
			$("#sanctions_bank").find('tr').slice(1).remove();
			var noOfRows = countRows("sanctions_bank");
			var totalRecords = $('#sanctionTotalRecordsPayOutId_bank').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdSanc_bank");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_SancId_bank",
					"viewMoreDetailsPayOut_Sanc_bank");

		});

$("#payOutDetails_fraugster_indicatior").click(
		function() {
			clicked= false;
			prevCountfrg=0;// AT-3325
			noOfRowsFrg=0;
			$("#fraugster").find('tr').slice(1).remove();
			var noOfRows = countRows("fraugster");
			var totalRecords = $('#fraugsterTotalRecordsPayOutId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdFraug");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_FraugId",
					"viewMoreDetailsPayOut_Fraug");
		});

$("#payOutDetails_activitylog_indicatior").click(
		function() {

			$("#activityLog").find('tr').slice(10).remove();
			var noOfRows = countRows("activityLog");
			var totalRecords = $('#actLogTotalRecordsPayOutId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutId_ActLog");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_ActLogId",
					"viewMoreDetailsPayOut_ActLog");
			$("#viewMoreAuditTrailDetails_ActLog").hide();

		});

$("#payOutDetails_customchecks_indicatior").click(
		function() {
			//Add cust type for AT-3161
			var custType = $('#account_clientType').text();
			var flag = $('#customRule_FPFlag').val();
			if(custType == "PFX" && flag == "true"){
				$("#customChecks").find('tr').slice(4).remove();
			}else{
				$("#customChecks").find('tr').slice(3).remove();
			}
			var noOfRows = countRows("customChecks");
			var totalRecords = Number($('#customCheckTotalRecordsPayOutId')
					.val());
			var leftRecords = updateLeftRecordsForCusChk(noOfRows,
					totalRecords, "leftRecordsPayOutIdCusChk");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_CusChkId",
					"viewMoreDetailsPayOut_CusChk");

		});

$("#payOutDetails_countrycheck_indicatior").click(
		function() {

			$("#countryCheck_beneficiary").find('tr').slice(1).remove();
			var noOfRows = countRows("countryCheck_beneficiary");
			var totalRecords = $('#countryCheckTotalRecordsPayOutId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdCountryChk");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_CountryChkId",
					"viewMoreDetailsPayOut_CountryChk");

		});

//AT-3658		
$("#payOutDetails_paymentReferencecheck_indicatior").click(
		function() {

			$("#paymentReference").find('tr').slice(1).remove();
			var noOfRows = countRows("paymentReference");
			var totalRecords = $('#paymentReferenceCheckTotalRecordsPayOutId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdPaymentReferenceChk");
			updateViewMoreBlock(leftRecords, "viewMorePayOut_PaymentReferenceChkId",
					"viewMoreDetailsPayOut_PaymentReferenceChk");

		});

function viewMoreLoadData() {

	var noOfRows = countRows("fraugster");
	var totalRecords = $('#fraugsterTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdFraug");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_FraugId",
			"viewMoreDetailsPayOut_Fraug");

	var noOfRowsSanc = countRows("sanctions_bank");
	var totalRecordSanc = $('#sanctionTotalRecordsPayOutId_bank').val();
	var leftRecordSanc = updateLeftRecords(noOfRowsSanc, totalRecordSanc,
			"leftRecordsPayOutIdSanc_bank");
	updateViewMoreBlock(leftRecordSanc, "viewMorePayOut_SancId_bank",
			"viewMoreDetailsPayOut_Sanc_bank");

	var noOfRowFraug = countRows("sanctions_beneficiary");
	var totalRecordsFraug = $('#sanctionTotalRecordsPayOutId_beneficiary')
			.val();
	var leftRecordsFraug = updateLeftRecords(noOfRowFraug, totalRecordsFraug,
			"leftRecordsPayOutIdSanc_beneficiary");
	updateViewMoreBlock(leftRecordsFraug, "viewMorePayOut_SancId_beneficiary",
			"viewMoreDetailsPayOut_Sanc_beneficiary");

	var noOfRowsCusChk = countRows("sanctions_contact");
	var totalRecordsCusChk = $('#sanctionTotalRecordsPayOutId_contact').val();
	var leftRecordsCusChk = updateLeftRecords(noOfRowsCusChk,
			totalRecordsCusChk, "leftRecordsPayOutIdSanc_contact");
	updateViewMoreBlock(leftRecordsCusChk, "viewMorePayOut_SancId_contact",
			"viewMoreDetailsPayOut_Sanc_contact");

	var noOfRowsCus = countRows("customChecks");
	var totalRecordsCus = Number($('#customCheckTotalRecordsPayOutId').val());
	var leftRecordsCus = updateLeftRecordsForCusChk(noOfRowsCus,
			totalRecordsCus, "leftRecordsPayOutIdCusChk");
	updateViewMoreBlock(leftRecordsCus, "viewMorePayOut_CusChkId",
			"viewMoreDetailsPayOut_CusChk");

	var noOfRowsCon = countRows("countryCheck_beneficiary");
	var totalRecordsCon = $('#countryCheckTotalRecordsPayOutId').val();
	var leftRecordsCon = updateLeftRecords(noOfRowsCon, totalRecordsCon,
			"leftRecordsPayOutIdCountryChk");
	updateViewMoreBlock(leftRecordsCon, "viewMorePayOut_CountryChkId",
			"viewMoreDetailsPayOut_CountryChk");
			
	//AT-3658
	var noOfRowsPayRef = countRows("paymentReference");
	var totalRecordsPayRef = $('#paymentReferenceCheckTotalRecordsPayOutId').val();
	var leftRecordsPayRef = updateLeftRecords(noOfRowsPayRef, totalRecordsPayRef,
			"leftRecordsPayOutIdPaymentReferenceChk");
	updateViewMoreBlock(leftRecordsPayRef, "viewMorePayOut_PaymentReferenceChkId",
			"viewMoreDetailsPayOut_PaymentReferenceChk");
}

function viewMoreResetData() {

	$("#fraugster").find('tr').slice(1).remove();
	$("#sanctions_bank").find('tr').slice(1).remove();
	$("#sanctions_beneficiary").find('tr').slice(1).remove();
	$("#sanctions_contact").find('tr').slice(1).remove();
	$("#customChecks").find('tr').slice(2).remove();
	$("#countryCheck_beneficiary").find('tr').slice(1).remove();
	$("#paymentReference").find('tr').slice(1).remove(); //AT-3658
}

function viewMoreLoadDataActLog() {

	var noOfRowsActLog = countRows("activityLog");
	var totalRecordsActLog = $('#actLogTotalRecordsPayOutId').val();
	var leftRecordsActLog = updateLeftRecords(noOfRowsActLog,
			totalRecordsActLog, "leftRecordsPayOutId_ActLog");
	updateViewMoreBlock(leftRecordsActLog, "viewMorePayOut_ActLogId",
			"viewMoreDetailsPayOut_ActLog");

	var noOfRows = countRows("further_paymentInDetails");
	var totalRecords = $('#furPayInDetailsTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdFurPayInDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_FurPayInDetailsId",
			"viewMoreDetailsPayOut_FurPayInDetails");

	var noOfRowsOut = countRows("further_paymentOutDetails");
	var totalRecordsOut = $('#furPayOutDetailsTotalRecordsPayOutId').val();
	var leftRecordsOut = updateLeftRecords(noOfRowsOut, totalRecordsOut,
			"leftRecordsPayOutIdFurPayOutDetails");
	updateViewMoreBlock(leftRecordsOut, "viewMorePayOut_FurPayOutDetailsId",
			"viewMoreDetailsPayOut_FurPayOutDetails");
	
	$("#fxticket_indicatior").trigger('click');
	$("#client-wallets").trigger('click');
	//$("#card_pilot").trigger('click'); //AT-4625
}

function viewMoreResetActLogData() {

	$("#activityLog").find('tr').slice(10).remove();
	$("#further_paymentInDetails").find('tr').slice(3).remove();
	$("#further_paymentOutDetails").find('tr').slice(3).remove();

}

function postSanctionMoreDetails(request) {

	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setSanctionMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching sanction more details');

		}
	});
}

function setSanctionMoreDetailsResponse(sanction) {

	if (sanction.length > 0) {
		for (var i = 0; i < sanction.length; i++) {
			var updatedOn = getEmptyIfNull(sanction[i].updatedOn);
			var updatedBy = getEmptyIfNull(sanction[i].updatedBy);
			var sanctionId = getEmptyIfNull(sanction[i].sanctionId);
			var ofacList = getEmptyIfNull(sanction[i].ofacList);
			var worldCheck = getEmptyIfNull(sanction[i].worldCheck);
			var status = getEmptyIfNull(sanction[i].status);
			var statusValue = getEmptyIfNull(sanction[i].statusValue);
			var eventServiceLogId = getEmptyIfNull(sanction[i].eventServiceLogId);
			var row = '<tr>';
			row += '<td hidden="hidden" class="center">'
					+ sanction[i].eventServiceLogId + '</td>';
			row += '<td>' + updatedOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'SANCTION\')">'
					+ sanctionId
					+ '</a></td>';
			row += '<td class="nowrap">' + ofacList + '</td>';
			row += '<td class="nowrap">' + worldCheck + '</td>';
			/** Set status as Not_Required - Saylee */
			if (statusValue === "Not Required") {
				row += '<td class="nowrap">Not Required</td>';
			} else {
				row += getYesOrNoCell(status);
			}
			row += '</tr>'
			if (sanction.passCount === undefined || sanction.passCount === null) {
				if (status) {
					sanction.passCount = 1;
				} else {
					sanction.failCount = 1;
				}
			}

			var sanctionPassCount = sanction.passCount;
			var sanctionFailCount = sanction.failCount;
			// setSanctionIndicator(sanctionPassCount,sanctionFailCount)
			var id = sanction[i].entityType;
			id = id.toLowerCase();

			$("#sanctions_" + id).append(row);

		}
		var noOfRows = countRows("sanctions_" + id);
		var totalRecords = Number($('#sanctionTotalRecordsPayOutId_' + id)
				.val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayOutIdSanc_" + id);
		updateViewMoreBlock(leftRecords, "viewMorePayOut_SancId_" + id,
				"viewMoreDetailsPayOut_Sanc_" + id);
	}
}

function postFraugsterMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setFraugsterMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching fraugster more details');

		}
	});
}

function setFraugsterMoreDetailsResponse(fraugster) {
	var custType = $('#account_clientType').text();
	if(custType == 'CFX') {
		for (var i = 0; i < fraugster.length; i++) {
			var createdOn = getEmptyIfNull(fraugster[i].createdOn);
			var updatedBy = getEmptyIfNull(fraugster[i].updatedBy);
			var fraugsterId = getEmptyIfNull(fraugster[i].fraugsterId);
			var score = getEmptyIfNull(fraugster[i].score);
			var eventServiceLogId = getEmptyIfNull(fraugster[i].id);
			var status = getEmptyIfNull(fraugster[i].status);
			var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'FRAUGSTER\',\'FraugsterChart\')">';
			row += '<td>' + createdOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'FRAUGSTER\')">'
					+ fraugsterId
					+ '</a></td>';
			//row += '<td class="nowrap" class="number">' + score + '</td>'
			row += '<td class="nowrap" class="number">Not Required</td>'
			row += '<td class="nowrap center"><i class="material-icons">-</i></td>'
			//row +=getYesOrNoCellWithId(status,'fraugster_status');
			row += '</tr>'
			$("#fraugster").append(row);
		}
		var noOfRows = countRows("fraugster");
		var totalRecords = Number($('#fraugsterTotalRecordsPayOutId').val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayOutIdFraug");
		updateViewMoreBlock(leftRecords, "viewMorePayOut_FraugId",
				"viewMoreDetailsPayOut_Fraug");
	}
	else {
		for (var i = 0; i < fraugster.length; i++) {
			var createdOn = getEmptyIfNull(fraugster[i].createdOn);
			var updatedBy = getEmptyIfNull(fraugster[i].updatedBy);
			var fraugsterId = getEmptyIfNull(fraugster[i].fraugsterId);
			var score = getEmptyIfNull(fraugster[i].score);
			var eventServiceLogId = getEmptyIfNull(fraugster[i].id);
			var status = getEmptyIfNull(fraugster[i].status);
			var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'FRAUGSTER\',\'FraugsterChart\')">';
			row += '<td>' + createdOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'FRAUGSTER\')">'
					+ fraugsterId
					+ '</a></td>';
			row += '<td class="nowrap" class="number">' + score + '</td>'
			row +=getYesOrNoCellWithId(status,'fraugster_status');
			row += '</tr>'
			$("#fraugster").append(row);
		}
		var noOfRows = countRows("fraugster");
		var totalRecords = Number($('#fraugsterTotalRecordsPayOutId').val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayOutIdFraug");
		updateViewMoreBlock(leftRecords, "viewMorePayOut_FraugId",
				"viewMoreDetailsPayOut_Fraug");
	}
}

function postCustomCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setCustomCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching custom check more details');

		}
	});
}
function setCustomCheckMoreDetailsResponse(customCheck) {
	// $("#customChecks").empty();
	//Add Cust Type condition and CustomRuleFraudPredictCheck for AT-3161
	var custType = $('#account_clientType').text();
	var flag = $('#customRule_FPFlag').val();
	$.each(customCheck, function(index, customCheck) {
		
		if(custType == "PFX" && flag == "true"){
			$("#customChecks").prepend(
					getVelocityCheck(customCheck.checkedOn,
							customCheck.velocityCheck)
							+ getWhitelistCheck(customCheck.whiteListCheck)
							 + getCustomRuleEuPoiCheck(customCheck.euPoiCheck)
							 + getCustomRuleFraudPredictCheck(customCheck.fraudPredictStatus));
		}else{
			$("#customChecks").append(
					getVelocityCheck(customCheck.checkedOn,
							customCheck.velocityCheck)	
							+ getWhitelistCheck(customCheck.whiteListCheck)
							 + getCustomRuleEuPoiCheck(customCheck.euPoiCheck));
		}
	});
	var noOfRows = countRows("customChecks");
	var totalRecords = Number($('#customCheckTotalRecordsPayOutId').val());
	var leftRecords = updateLeftRecordsForCusChk(noOfRows, totalRecords,
			"leftRecordsPayOutIdCusChk");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_CusChkId",
			"viewMoreDetailsPayOut_CusChk");
}

//AT-3658
function postPaymentReferenceCheckMoreDetails(request) {
	clicked = false;
	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setPaymentReferenceCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching payment reference check more details');

		}
	});
}

function setPaymentReferenceCheckMoreDetailsResponse(reference) {
	if(reference.length > 0) {
		for ( var i = 0; i < reference.length; i++) {
			var checkDateTime = getEmptyIfNull(reference[i].checkedOn);
			var paymentReference = getEmptyIfNull(reference[i].paymentReference);
			var matchedKeyword = getEmptyIfNull(reference[i].matchedKeyword);
			var closenessScore = getEmptyIfNull(reference[i].closenessScore);
			var status = getEmptyIfNull(reference[i].overallStatus);
			var eventServiceLogId = getEmptyIfNull(reference[i].eventServiceLogId);
			
			var row = '<tr>';
			row += '<td hidden="hidden" class="center">' + eventServiceLogId + '</td>' ;
			row += '<td>' + checkDateTime + '</td>';
			row += '<td class="wrapword">' + paymentReference + '</td>';
			row += '<td class="wrapword">' + matchedKeyword + '</td>';
			row += '<td>' + closenessScore + '</td>';
			if(status == 'PASS'){
				row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
			}
			else if(status == 'NOT_PERFORMED') {
				row += '<td> NOT AVAILABLE </td>';
			}
			else{
				row += '<td class="no-cell"><i class="material-icons">clear</i></td>';
			}
			row += '</tr>';
			$("#paymentReference").append(row);
		}
	var noOfRowsPayRef = countRows("paymentReference");
	var totalRecordsPayRef = $('#paymentReferenceCheckTotalRecordsPayOutId').val();
	var leftRecordsPayRef = updateLeftRecords(noOfRowsPayRef, totalRecordsPayRef,
			"leftRecordsPayOutIdPaymentReferenceChk");
	updateViewMoreBlock(leftRecordsPayRef, "viewMorePayOut_PaymentReferenceChkId",
			"viewMoreDetailsPayOut_PaymentReferenceChk");
	}
}

/*
 * function setCustomCheckMoreDetailsResponse(customeChecks){
 * //$("#customChecks").empty(); var rows = ''; $.each(customeChecks,
 * function(index, customCheck) { var eventId =
 * getEmptyIfNull(customCheck.eventId); var entityType =
 * getEmptyIfNull(customCheck.entityType); var checkedOn =
 * getEmptyIfNull(customCheck.checkedOn); var name =
 * getEmptyIfNull(customCheck.name); var rules =
 * getEmptyIfNull(customCheck.velocityRules); var row = '<tr>'; row += '<td hidden="hidden">' +
 * eventId + '</td>'; row += '<td>' + entityType + '</td>'; row += '<td class="nowrap">' +
 * checkedOn + '</td>'; row += '<td>' + name + '</td>'; row += '<td class="nowrap"><ul>';
 * if (rules !== '') { $.each(rules, function(index, rule) { row += '<li>' +
 * rule + '</li>'; }); }
 * 
 * row += '</ul></td>'; rows += row; });
 * 
 * $("#customChecks").append(rows); var noOfRows = countRows("customChecks");
 * var totalRecords = Number($('#customCheckTotalRecordsPayOutId').val()); var
 * leftRecords =
 * updateLeftRecords(noOfRows,totalRecords,"leftRecordsPayOutIdCusChk");
 * updateViewMoreBlock(leftRecords,"viewMorePayOut_CusChkId","viewMoreDetailsPayOut_CusChk");
 *  }
 */
function setActivityLogViewMore(activities) {

	var rows = '';
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
	    var comment = getEmptyIfNull(activityData.comment);
	    var tradeContractNumber = $('#payment_transNum').text();//AT-1794 - Snehaz
		var row = '<tr class="talign">';
		row += '<td class="nowrap">' + createdOn + '</td>';
		row +='<td>'+tradeContractNumber+'</td>';
		row += '<td class="nowrap">' + createdBy + '</td>';
		row += '<td><ul><li>' + activity + '</li></ul></td>';
		row += '<td >' + activityType + '</td>';
		if(comment == '')
			row += '<td style="font-weight:bold" class = "center">-</td>';
		else row += '<td class="breakword">' + comment + '</td>';
		row += '</tr>'
		rows += row;
	});
	$("#activityLog").append(rows);
	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutId_ActLog");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_ActLogId",
			"viewMoreDetailsPayOut_ActLog");

}

function showProviderResponse(eventServiceLogId, serviceType,chartDisplayFlag) {
	var getProviderResponseRequest = {};
	addField('eventServiceLogId', eventServiceLogId, getProviderResponseRequest);
	addField('serviceType', serviceType, getProviderResponseRequest);
	getProviderResponse(getProviderResponseRequest,chartDisplayFlag);
}

function setViewMoreActLogData() {

	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutId_ActLog");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_ActLogId",
			"viewMoreDetailsPayOut_ActLog");
	// Number($('#actLogTotalRecordsPayOutId').val(totalRecords));
	setActivityLogTotalRecords(totalRecords);

}

function postPaymentInMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getFurtherPaymentOutDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setFurtherPaymentInMoreDetails(data.services);
		},
		error : function() {
			alert('Error while fetching fraugster more details');

		}
	});
}

function setFurtherPaymentInMoreDetails(paymentInData) {
	var rows = '';
	$.each(paymentInData, function(index, payment) {
		var dateOfPayment = getEmptyIfNull(payment.dateOfPayment);
		var amount = getEmptyIfNull(payment.amount);
		var sellCurrency = getEmptyIfNull(payment.sellCurrency);
		var method = getEmptyIfNull(payment.method);
		var accountName = getEmptyIfNull(payment.accountName);
		var account = getEmptyIfNull(payment.account);
		var riskGuardianScore = getEmptyIfNull(payment.riskGuardianScore);
		var tradeContractNumber = getEmptyIfNull(payment.tradeContractNumber);
		var bankName = getEmptyIfNull(payment.bankName);
		var dash = "-";
		var row = '<tr>';
		row += '<td class="breakword">' + tradeContractNumber + '</td>';
		row += '<td class="nowrap">' + dateOfPayment + '</td>';
		row += '<td class="number">' + amount + '</td>';
		row += '<td class="nowrap">' + sellCurrency + '</td>';
		row += '<td class="nowrap">' + method + '</td>';
		row += '<td class="breakword">' + accountName + '</td>';
		if (account == "-")
			row += '<td style="font-weight:bold" class = "center">' + account
					+ '</td>'
		else
			row += '<td class="breakword">' + account + '</td>';
		//AT-1731 - SnehaZagade
		if (bankName == "-" || bankName == "")
			row += '<td style="font-weight:bold" class = "center">' + dash
					+ '</td>';
		else
			row += '<td class="breakword">' + bankName + '</td>';
		if (riskGuardianScore == "-" || riskGuardianScore == "")
			row += '<td style="font-weight:bold" class = "center">' + dash
					+ '</td>';
		else
			row += '<td class="breakword">' + riskGuardianScore + '</td>';
		
		row += '</tr>'
		rows += row;
	});

	$("#further_paymentInDetails").append(rows);
	var noOfRows = countRows("further_paymentInDetails");
	var totalRecords = $('#furPayInDetailsTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdFurPayInDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_FurPayInDetailsId",
			"viewMoreDetailsPayOut_FurPayInDetails");

}

function postPaymentOutMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getFurtherPaymentOutDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setFurtherPaymentOutMoreDetails(data.services);
		},
		error : function() {
			alert('Error while fetching fraugster more details');

		}
	});
}

function setFurtherPaymentOutMoreDetails(paymentOutData) {

	var rows = '';
	$.each(paymentOutData, function(index, payment) {
		var valuedate = getEmptyIfNull(payment.valuedate)
		//var dateOfPayment = getEmptyIfNull(payment.dateOfPayment);
		var amount = getEmptyIfNull(payment.amount);
		var buyCurrency = getEmptyIfNull(payment.buyCurrency);
		var accountName = getEmptyIfNull(payment.accountName);
		var account = getEmptyIfNull(payment.account);
		var tradeContractNumber = getEmptyIfNull(payment.tradeContractNumber);
		var paymentReference = getEmptyIfNull(payment.paymentReference);
		var bankName = getEmptyIfNull(payment.bankName);
		var dash = "-";
		var row = '<tr>';
		row += '<td class="breakword">' + tradeContractNumber + '</td>';
		row += '<td class="nowrap">' + valuedate + '</td>';
		row += '<td class="number">' + amount + '</td>';
		row += '<td class="nowrap">' + buyCurrency + '</td>';
		row += '<td class="breakword">' + accountName + '</td>';
		row += '<td class="breakword">' + account + '</td>';
		//AT-1731 - SnehaZagade
		if (bankName == "-" || bankName == "")
			row += '<td style="font-weight:bold" class = "center">' + dash
					+ '</td>';
		else
			row += '<td class="breakword">' + bankName + '</td>';
		if (paymentReference == "-" || paymentReference == "")
			row += '<td style="font-weight:bold" class = "center">' + dash
					+ '</td>';
		else
			row += '<td class="breakword">' + paymentReference + '</td>';
		row += '</tr>'
		rows += row;
	});

	$("#further_paymentOutDetails").append(rows);
	var noOfRows = countRows("further_paymentOutDetails");
	var totalRecords = $('#furPayOutDetailsTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdFurPayOutDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_FurPayOutDetailsId",
			"viewMoreDetailsPayOut_FurPayOutDetails");

}

$("#further_PayOutDetails_indicatior").click(
		function() {

			$("#further_paymentInDetails").find('tr').slice(10).remove();
			var noOfRows = countRows("further_paymentInDetails");
			var totalRecords = $('#furPayInDetailsTotalRecordsPayOutId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayOutIdFurPayInDetails");
			updateViewMoreBlock(leftRecords,
					"viewMorePayOut_FurPayInDetailsId",
					"viewMoreDetailsPayOut_FurPayInDetails");

			$("#further_paymentOutDetails").find('tr').slice(10).remove();
			var noOfRowsOut = countRows("further_paymentOutDetails");
			var totalRecordsOut = $('#furPayOutDetailsTotalRecordsPayOutId')
					.val();
			var leftRecordsOut = updateLeftRecords(noOfRowsOut,
					totalRecordsOut, "leftRecordsPayOutIdFurPayOutDetails");
			updateViewMoreBlock(leftRecordsOut,
					"viewMorePayOut_FurPayOutDetailsId",
					"viewMoreDetailsPayOut_FurPayOutDetails");
					
			cardPilot();		// At-4625

		});

function postCountryCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setCountryCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching fraugster more details');

		}
	});
}

function setCountryCheckMoreDetailsResponse(countryCheck) {

	var rows = '';
	$.each(countryCheck, function(index, countryCheck) {
		var checkedOn = getEmptyIfNull(countryCheck.checkedOn);
		var status = getEmptyIfNull(countryCheck.status);
		var row = '<tr>';
		row += '<td class="nowrap">' + checkedOn + '</td>';
		row += '<td class="nowrap">' + status + '</td>';
		row += '</tr>';
		rows += row;
	});
	$("#countryCheck_beneficiary").append(rows);
	var noOfRows = countRows("countryCheck_beneficiary");
	var totalRecords = $('#countryCheckTotalRecordsPayOutId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayOutIdCountryChk");
	updateViewMoreBlock(leftRecords, "viewMorePayOut_CountryChkId",
			"viewMoreDetailsPayOut_CountryChk");

}

function setSanctionStatusValuesToHiddenFields(sanction, entityType) {
	var status = getEmptyIfNull(sanction.status);
	entityType = entityType.toLowerCase();
	if (entityType === 'contact') {
		$("#paymentOutSanctionContactStatus").val(status);
	} else if (entityType === 'beneficiary') {
		$("#paymentOutSanctionBeneficiaryStatus").val(status);
	} else {
		$("#paymentOutSanctionBankStatus").val(status);
	}
}

function openClientDetail() {
	$('#openClientForm').submit();
}

function openPaymentDetail(tradeId,account){
 var res = tradeId.replace(/^[, ]+|[, ]+$|[, ]+/g, " ").trim();
 var accountNumber=account.replace(/^[, ]+|[, ]+$|[, ]+/g, " ").trim();
	$('#tradeId').val(res);
	$('#accountNumber').val(accountNumber);
	$('#openPaymentForm').submit();
}


function resendFraugsterCheck() {
	$('#gifloaderforfraugsterresend').css('visibility', 'visible');
	var request = {};
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();

	addField('paymentFundsoutId', tradePaymentId, request);
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	postFraugsterCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postFraugsterCheckResend(request, baseUrl, user) {
	disableButton('payout_fraugster_recheck');
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/fraugsterCheckfundsOut',
						
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforfraugsterresend').css('visibility',
							'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayOutId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					
					var currentFraugsterStatus = data.summary.status;
					
					if(data.summary.status === 'PASS'){
						data.summary.status=true;
					} else {
						data.summary.status=false;
					}
					var rows = '';
					rows += setFraugsterResendResponse(data.activityLogs, data.eventServiceLogId, data.summary);
					var id = 'fraugster';
					$("#" + id).prepend(rows);
					getActivities(1, 10, Number($('#paymentOutId').val()),false);
					
					var noOfRows = countRows("fraugster");
					var totalRecords = Number($('#fraugsterTotalRecordsPayOutId')
							.val());
					var leftRecords = updateLeftRecords(noOfRows, totalRecords,
							"leftRecordsPayOutIdFraug");
					updateViewMoreBlock(leftRecords, "viewMorePayOut_FraugId",
							"viewMoreDetailsPayOut_Fraug");
					
					enableButton('payout_fraugster_recheck');
					populateSuccessMessage("main-content__body",
							"Fraugster Repeat Check Successfully done",
							"fraugsterChecks_resend_error_field",
							"payout_fraugster_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevfraugsterStatus = $('#fraugsterStatus').val();
					if (prevfraugsterStatus.toUpperCase() != currentfraugsterStatus && currentfraugsterStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
						updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						var previousFraugsterStatus = document.getElementById("fraugsterStatus");
						previousFraugsterStatus.value = currentFraugsterStatus;
					}

				},
				error : function() {
					$('#gifloaderforfraugsterresend').css('visibility',
							'hidden');
					enableButton('payout_fraugster_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending fraugsterCheck",
							"fraugsterChecks_resend_error_field",
							"payout_fraugster_recheck");
				}
			});
}

function setFraugsterResendResponse(activityLogs, eventServiceLogId, summary) {
	var custType = $('#account_clientType').text();
	if(custType == 'CFX') {
		var createdOn = getDateTimeFormat(getEmptyIfNull(activityLogs.activityLogData[0].createdOn));
		var createdBy = getEmptyIfNull(activityLogs.activityLogData[0].createdBy);
		var fraugsterId = getEmptyIfNull(summary.frgTransId);
		var score = getEmptyIfNull(summary.score);
		var status = getEmptyIfNull(summary.status);
		var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('+ eventServiceLogId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden" class="center">' + eventServiceLogId
		+ '</td>';
		row += '<td>' + createdOn + '</td>';
		row += '<td>' + createdBy + '</td>';
		row += '<td><a href="javascript:void(0);" onclick="showProviderResponse('+ eventServiceLogId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		//row += '<td class="nowrap" class="number">' + score + '</td>';
		row += '<td class="nowrap" class="number">Not Required</td>';
		row += '<td class="nowrap center"><i class="material-icons">-</i></td>';
		//row +=getYesOrNoCellWithId(status,'fraugster_status');
		row += '</tr>'
			
		var totalrecords = $("#fraugsterTotalRecordsPayOutId").val();
		totalrecords++;
		if (status) {
			//setFraugsterIndicator(totalrecords, 0);
			setFraugsterTotalRecords(totalrecords);
		} else {
			//setFraugsterIndicator(0, totalrecords);
			setFraugsterTotalRecords(totalrecords);
		}
		return row;
	}
	else {
		var createdOn = getDateTimeFormat(getEmptyIfNull(activityLogs.activityLogData[0].createdOn));
		var createdBy = getEmptyIfNull(activityLogs.activityLogData[0].createdBy);
		var fraugsterId = getEmptyIfNull(summary.frgTransId);
		var score = getEmptyIfNull(summary.score);
		var status = getEmptyIfNull(summary.status);
		var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('+ eventServiceLogId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden" class="center">' + eventServiceLogId
		+ '</td>';
		row += '<td>' + createdOn + '</td>';
		row += '<td>' + createdBy + '</td>';
		row += '<td><a href="javascript:void(0);" onclick="showProviderResponse('+ eventServiceLogId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		row += '<td class="nowrap" class="number">' + score + '</td>';
		row +=getYesOrNoCellWithId(status,'fraugster_status');
		row += '</tr>'
			
		var totalrecords = $("#fraugsterTotalRecordsPayOutId").val();
		totalrecords++;
		if (status) {
			//setFraugsterIndicator(totalrecords, 0);
			setFraugsterTotalRecords(totalrecords);
		} else {
			//setFraugsterIndicator(0, totalrecords);
			setFraugsterTotalRecords(totalrecords);
		}
		return row;
	}
}

function setFraugsterIndicator(passCount, failCount) {
	$('#payOutDetails_fraugster_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Fraugster';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'paymentOutDetails_fraugsterPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'paymentOutDetails_fraugsterFail');
	}
	indicator += '</a>';
	$('#payOutDetails_fraugster_indicatior').append(indicator);
}




function resendBlacklistCheck() {
	$('#gifloaderforblacklistresend').css('visibility', 'visible');
	var request = {};
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('paymentFundsOutId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	postBlacklistCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postBlacklistCheckResend(request, baseUrl, user) {
	disableButton('payout_blacklist_recheck');
	
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/blacklistCheckfundsOut',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforblacklistresend').css('visibility', 'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayOutId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					var rows = '';
					rows +=setBlacklistResendResponse(data.summary);
					var id = 'blacklist_beneficiary';
					$("#" + id).empty();  
					$("#" + id).append(rows); 
					getActivities(1, 10, Number($('#paymentOutId').val()), false);
					enableButton('payout_blacklist_recheck');
					populateSuccessMessage("main-content__body",
							"Blacklist Repeat Check Successfully done",
							"blacklistChecks_resend_error_field",
							"payout_blacklist_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevBlacklistStatus = $('#blacklistStatus').val();
					var currentBlacklistStatus = data.summary.status;
					if (prevBlacklistStatus.toUpperCase()  != currentBlacklistStatus && currentBlacklistStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
						updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						var previousBlacklistStatus = document.getElementById("blacklistStatus");
						previousBlacklistStatus.value = currentBlacklistStatus;
					}

				},
				error : function() {
					$('#gifloaderforblacklistresend').css('visibility', 'hidden');
					enableButton('payout_blacklist_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending blacklistCheck",
							"blacklistChecks_resend_error_field",
							"payout_blacklist_recheck");
				}
			});
}


function setBlacklistResendResponse(summary) {
	
	var accountNumber = getEmptyIfNull(summary.accountNumber);
	var bankName = getEmptyIfNull(summary.bankName);
	var name= getEmptyIfNull(summary.name);
	var status= getEmptyIfNull(summary.status);
	var passCount=0;
	var failCount=0;
	var statusValue;
	if(status ==='PASS'){
		statusValue = true;
	}
	else{
		statusValue= false;
	}
	
	if(status ==='SERVICE_FAILURE')
	failCount=3;
	
	var row = '<tr>';
	if(accountNumber === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	
	else if(accountNumber === 'Not Available')
		row += '<td class="nowrap center">'+accountNumber+'</td>';
	
	else if(accountNumber === 'false'){
		row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		passCount++;
	}
	else {
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+summary.accNumberMatchedData+ ')</td>';
		failCount++;
	 }
	
	
	if(name === 'NOT_REQUIRED')
	    row += '<td class="nowrap center">Not Required</td>';
	else if(name === 'Not Available')
		row += '<td class="nowrap center">'+name+'</td>';
	else if(name === 'false'){
		    row +='<td class="yes-cell"><i class="material-icons">check</i></td>';
		    passCount++;
	}
	else {
	       row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+summary.nameMatchedData+ ')</td>';
	       failCount++;
	 }
	
	
	if(bankName === 'NOT_REQUIRED'|| bankName ==="")
		row += '<td class="nowrap center">Not Required</td>';
	
	else if(bankName === 'Not Available')
		row += '<td class="nowrap center">'+bankName+'</td>';
	else if(bankName === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else{
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+summary.bankNameMatchedData+ ')</td>';
	      failCount++;
	}
	row += getYesOrNoCell(statusValue);
	row += '</tr>';
	setBlacklistIndicator(passCount, failCount);
	return row;
}



function setBlacklistIndicator(passCount, failCount) {
	$('#beneficiary_blacklist_indicator').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist';
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'beneficiary_blacklist_negative');
	}
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'beneficiary_blacklist_positive');
	}
	indicator += '</a>';
	$('#beneficiary_blacklist_indicator').append(indicator);
}

//AT-3658
function resendPaymentReferenceCheck() {
	$('#gifloaderforpaymentreferenceresend').css('visibility', 'visible');
	var request = {};
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('paymentFundsOutId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	postPaymentReferenceCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postPaymentReferenceCheckResend(request, baseUrl, user) {
	disableButton('payout_paymentreference_recheck');
	
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/referenceCheckfundsOut',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforpaymentreferenceresend').css('visibility', 'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayOutId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					var rows = '';
					rows +=setPaymentReferenceResendResponse(data.activityLogs, data.eventServiceLogId, data.summary);
					var id = 'paymentReference'; 
					$("#" + id).prepend(rows); 
					
					getActivities(1, 10, Number($('#paymentOutId').val()), false);
					
					var noOfRows = countRows("paymentReference");
					var totalRecords = Number($('#paymentReferenceCheckTotalRecordsPayOutId')
							.val());
					var leftRecords = updateLeftRecords(noOfRows, totalRecords,
							"leftRecordsPayOutIdPaymentReferenceChk");
					updateViewMoreBlock(leftRecords, "viewMorePayOut_PaymentReferenceChkId",
							"viewMoreDetailsPayOut_PaymentReferenceChk");
					
					enableButton('payout_paymentreference_recheck');
					populateSuccessMessage("main-content__body",
							"Payment Reference Repeat Check Successfully done",
							"paymentReferenceChecks_resend_error_field",
							"payout_payment_reference_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevPaymentReferenceStatus = $('#overallStatusPayRef').val();
					var currentPaymentReferenceStatus = data.summary.status;
					if (prevPaymentReferenceStatus.toUpperCase() != currentPaymentReferenceStatus && currentPaymentReferenceStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
						updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl);
						var previousPaymentReferenceStatus = document.getElementById("overallStatusPayRef");
						previousPaymentReferenceStatus.value = currentPaymentReferenceStatus;
					}

				},
				error : function() {
					$('#gifloaderforpaymentreferenceresend').css('visibility', 'hidden');
					enableButton('payout_payment_reference_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending paymentReferenceCheck",
							"paymentReferenceChecks_resend_error_field",
							"payout_payment_reference_recheck");
				}
			});
}

function setPaymentReferenceResendResponse(activityLog, eventServiceLogId, summary) {

		var checkedOn = getDateTimeFormat(getEmptyIfNull(activityLog.activityLogData[0].createdOn));
		var paymentReference = getEmptyIfNull(summary.paymentReference);
		var matchedKeyword = getEmptyIfNull(summary.sanctionText);
		var closenessScore = getEmptyIfNull(summary.tokenSetRatio);
		var status = getEmptyIfNull(summary.status);
		
		var row = '<tr>';
		row += '<td hidden="hidden" class="center">' + eventServiceLogId
		+ '</td>';
		row += '<td>' + checkedOn + '</td>';
		row += '<td class="wrapword">' + paymentReference + '</td>';
		row += '<td class="wrapword">' + matchedKeyword + '</td>';
		row += '<td>' + closenessScore + '</td>';
		if(status == 'PASS'){
				row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
			}
			else if(status == 'NOT_PERFORMED') {
				row += '<td> NOT AVAILABLE </td>';
			}
			else{
				row += '<td class="no-cell"><i class="material-icons">clear</i></td>';
			}
		row += '</tr>'

	
		var totalrecords = $("#paymentReferenceCheckTotalRecordsPayOutId").val();
		totalrecords++;
		setPaymentReferenceCheckPaymentOutTotalRecords(totalrecords);
		if (status == 'PASS') {
			setPaymentReferenceCheckIndicator(totalrecords,0);
		} else {
			setPaymentReferenceCheckIndicator(0,totalrecords);
		}
		
		return row;
}

function fraugsterChartData(originalJSON) {
	var originalJSONParsed = {};
	
	originalJSONParsed = JSON.parse(originalJSON);
	
	if(null == originalJSONParsed) {
		$("#boxpanel-space-before").css('display','none');
	}
	else {	
		if(originalJSONParsed.hasOwnProperty('decisionDrivers') == '' || originalJSONParsed['status'] == 'SERVICE_FAILURE' || originalJSONParsed['status'] == 'NOT_REQUIRED') {
			$("#boxpanel-space-before").css('display','none');
		}
		else {
			$("#boxpanel-space-before").css('display','block');
			var chart = AmCharts.makeChart( "paymentOut-fraugster-chart", {
				"creditsPosition" : "bottom-right",
				"type": "serial",
			  	"theme": "light",
			  	"rotate":true,
			  	"dataProvider":generateFraugsterChartData(originalJSONParsed),
			 	"gridAboveGraphs": true,
			  	"startDuration": 1,
			  	"graphs": [ {
			    	"balloonText": "[[category]]: <b>[[value]]</b>",
			    	"fillAlphas": 0.8,
			    	"lineAlpha": 0.2,
			    	"type": "column",
			    	"valueField": "featureImportance",
			    	"fillColorsField":"colour"
			  	} ],
			  	"chartCursor": {
			    	"categoryBalloonEnabled": false,
			    	"cursorAlpha": 0,
			    	"zoomable": false
			  	},
			  	"categoryField": "measure",
			  	"categoryAxis": {
				    "gridPosition": "start",
				    "gridAlpha": 0.1,
				    "tickLength": 5
			  	}
	
			});
			//$("a[title='JavaScript charts']").remove();
		}
	}
}

function sanctionStatusForPaymentStatusChange(updatedPaymentStatus) {
	var sancContactStatus = getSanctionRowData('#sanctions_contact','statusContact');
	var sancBeneStatus = getSanctionRowData('#sanctions_beneficiary','statusBenef');
	var sancBankStatus = getSanctionRowData('#sanctions_bank','statusBank');
	return getSanctionErrorMessage(sancContactStatus,sancBeneStatus,sancBankStatus,updatedPaymentStatus);
}

function getSanctionRowData(id,statusId) {
	var row_text1 = $(id+" tr:first");
	return row_text1.find("td").attr('id',statusId).text();
}

function getSanctionErrorMessage(sancContactStatus,sancBeneStatus,sancBankStatus,updatedPaymentStatus) {
	var sanctionContact=0,sanctionBene=0,sanctionBank=0,sancError='';
	
	if(getSanctionError(sancContactStatus,updatedPaymentStatus)) {
		sanctionContact=1;
		sancError = sancError + 'contact,';
	}
	if(getSanctionError(sancBeneStatus,updatedPaymentStatus)) {
		sanctionBene=1;
		sancError = sancError + ' beneficiary,';
	}
	if(getSanctionError(sancBankStatus,updatedPaymentStatus)) {
		sanctionBank=1;
		sancError = sancError + ' bank';
	}
	if(sanctionContact == 1 || sanctionBene == 1 || sanctionBank == 1) {
		populateErrorMessage("main-content__body",
				"Payment can not be clear since last "+ sancError +" sanction check is fail ","updatePaymentOut_error_field", "updatePaymentOut");
		return true;
	}
	return false;
}

function getSanctionError(sancStatus,updatedPaymentStatus) {
	if (sancStatus === null || updatedPaymentStatus === 'CLEAR' && sancStatus.indexOf("clear") !== -1 || sancStatus === undefined) {
		return true;
	}
	return false;
}

function hideGifLoaderForSanctionUpdate(entityType){
	if(entityType==="BENEFICIARY"){
		$('#gifloaderforupdatesanctionbenefeciary').css('visibility', 'hidden');
	}
	if(entityType==="BANK"){
	$('#gifloaderforupdatesanctionbank').css('visibility', 'hidden');
	}
	if(entityType==="CONTACT"){
		$('#gifloaderforupdatesanctioncontact').css('visibility', 'hidden');
	}
}

//AT-3450
function setPoiExistsFlagForPaymentOut() {
    var request ={ };
    var contact_sf_id = ($('#contact_crmContactId').val());
	addField('contact_sf_id', contact_sf_id, request);
	var trade_contact_id = Number($('#contact_tradeContactId').val());
	addField('trade_contact_id', trade_contact_id, request);	
	$.ajax({
		url: '/compliance-portal/setPoiExistsFlagForPaymentOut',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function() {
			document.location.reload();
		},
		error: function() {
			alert("Something has gone wrong!!");
		}
	});
}

function approvePoiForPaymentOut(){
	$("#approvePoiPopupdiv").attr('readonly', true);
	$("#ApprovePoiPopup").dialog({
	modal : true,
	draggable : false,
	resizable : false,
	show : 'blind',
	hide : 'blind',
	width : (300),
	height : (250),
	dialogClass : 'ui-dialog-osx',
	buttons :[{
		text: 'YES',
		click : function() {
			setPoiExistsFlagForPaymentOut();
			$(this).dialog("close");
			unlockResource();
			populateSuccessMessage("main-content__body",
					"POI exists flag updated successfully",
					"poi_exists_error_field",
					"regDetails_profile_update");
			$("#modal-mask").addClass("modal-mask--hidden");
			}
	},
	{
		text: 'NO',
		click : function() {
			$(this).dialog("close");
			$("#errorDiv").css('display','none');
			$("#modal-mask").addClass("modal-mask--hidden");
			unlockResource();
			document.location.reload();
		}
	}]
	})
}

//AT-4306
$("#payOutDetails_intuition_indicatior").click(function() {
	clicked = false;
	prevCountIntuition = 0;
	noOfRowsIntuition = 0;
	$("#payOutDetails_intuition").find('tr').slice(1).remove();
	var noOfRows = countRows("payOutDetails_intuition");
	var totalRecords = $('#intuitionTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, "leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords, "viewMore_IntuitionId", "viewMoreDetails_intuition");
});

function postIntuitionCheckMoreDetails(request) {
	clicked = false;
	$.ajax({
		url : '/compliance-portal/getPaymentOutViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setIntuitionCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching intuition check more details');

		}
	});
}

function setIntuitionCheckMoreDetailsResponse(intuition){
	for (var i = 0; i < intuition.length; i++) {
		var createdOn = getEmptyIfNull(intuition[i].createdOn);
		var updatedBy = getEmptyIfNull(intuition[i].updatedBy);
		if (updatedBy == '') {
			updatedBy = 'system';
		}
		var correlationId = getSingleDashIfNull(intuition[i].correlationId);
		var profileRiskLevel = getSingleDashIfNull(intuition[i].clientRiskLevel);
		var ruleScore = getEmptyIfNull(intuition[i].ruleScore);
		var paymentRiskLevel = getSingleDashIfNull(intuition[i].ruleRiskLevel);
		var status = getSingleDashIfNull(intuition[i].status);
		var decision = getSingleDashIfNull(intuition[i].decision);//Add for AT-5028
		var eventServiceLogId = getEmptyIfNull(intuition[i].id);

		var row = "<tr  href='javascript:void(0);' onclick='showProviderResponse(" + eventServiceLogId + ",\"TRANSACTION_MONITORING\")'>";
		row += "<td>" + createdOn + "</td>";
		row += "<td class='wrapword'>" + updatedBy + "</td>";
		row += "<td class='wrapword'><a href='javascript:void(0);' onclick='showProviderResponse(" + eventServiceLogId + ",\"TRANSACTION_MONITORING\")'>" + correlationId + "</a></td>";
		row += "<td class='nowrap'>" + profileRiskLevel + "</td>";
		row += "<td class='nowrap'>" + paymentRiskLevel + "</td>";
		row += "<td class='nowrap' class='number'>" + ruleScore + "</td>";
		row += "<td class='nowrap'>" + decision + "</td>";
		row += "</tr>";

		$("#payOutDetails_intuition").append(row);
	}
	var noOfRows = countRows("payOutDetails_intuition");
	var totalRecords = Number($('#intuitionTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, "leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords, "viewMore_IntuitionId", "viewMoreDetails_intuition");
}