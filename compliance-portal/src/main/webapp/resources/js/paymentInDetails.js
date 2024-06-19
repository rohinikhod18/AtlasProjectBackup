var isUnwatched=false;
var noOfRowsFrg=0;
var noOfRowsCustomCheck=0; //AT-3738
var noOfRowsIntuitionCheck = 0; //AT-4306
var noOfRowsSanction=0;
var prevCountfrg=0;
var prevCountSanction=0;
var prevCountCustomCheck=0; //AT-3738
var prevCountIntuitionCheck = 0; //AT-4306
var clicked= false;
$(document).ready(function() {
	if (document.referrer.endsWith("paymentInReport")) {
		onSubNav('payIn-report-sub-nav');
	} else {
		onSubNav('payIn-sub-nav');
	}
	addPaymentInStatusReasonsIntoData();
	addWatchlistIntoData();
});

function addPaymentInStatusReasonsIntoData() {
	var newData = [];

	$('input[type="radio"][name="payDetails_payStatusReasons"]').each(
			function() {
				newData.push({
					"name" : $(this).val(),
					"preValue" : $(this).prop('checked'),
					"updatedValue" : $(this).prop('checked')
				});
			});
	$("#payment_statusReasons").data('prePaymentInStatusReasons', newData);
	var paymentInStatus = $("input[name='paymentStatus']:checked").val();
	$("#paymentInDetails_Status_radio").data('preConStatus', paymentInStatus);
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

function getPayInNextRecord(custType, id) {
	var searchCriteria = getNextRecord();
	if (searchCriteria != null) {
		postPayInDetails(searchCriteria, custType, id);
	}
}

function getPayInPreviousRecord(custType, id) {
	var searchCriteria = getPreviousRecord();
	if (searchCriteria != null) {
		postPayInDetails(searchCriteria, custType, id);
	}

}

function getPayInLastRecord(custType, id) {
	var searchCriteria = getLastRecord();
	postPayInDetails(searchCriteria, custType, id);
}

function getPayInFirstRecord(custType, id) {
	var searchCriteria = getFirstRecord();
	postPayInDetails(searchCriteria, custType, id);
}

function postPayInDetails(searchCriteria, custType, id) {
	disableAllButtons();
	disableAllPaginationBlock();
	disableAllCheckBlocks();
	$('#custType').val(custType);
	$('#searchSortCriteria').val(getJsonString(searchCriteria));
	$('#paymentInId').val(id);
	$('#payInDetailForm').submit();
}

function lockResource() {
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId === null || userResourceLockId === undefined
			|| userResourceLockId === "") {
		var resourceId = $('#paymentinId').val();
		var resourceType = 'PAY_IN';
		var request = getLockResourceRequest(resourceId, resourceType);
		postLockOrUnlock(request);
		console.log(request);
	}

}

function unlockResource() {
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId !== null && userResourceLockId !== undefined
			&& userResourceLockId !== "") {
		var resourceId = $('#paymentInId').val();
		var resourceType = 'PAY_IN';
		var request = getUnlockResourceRequest(resourceId, resourceType,
				userResourceLockId);
		postLockOrUnlock(request);
		console.log(request);
	}
}

function executeActions(isAutoUnlock) {
	var isAllWatchlistReasonRemoved=false;
	var numberOfWatchlistReason=$('input[name="payment_watchlist[]"]:checked').length;
	var prePaymentInStatus = $("#paymentInDetails_Status_radio").data(
			'preConStatus');
	var updatedPaymentInStatus = $("input[name='paymentStatus']:checked").val();
	var comment = $('#comments').val();
	var isOnQueue = $('#payment_isOnQueue').val();
	var sancBeneStatus = $("#sanctions_contact tr:nth-child(1) td:nth-child(9)").text();
	var intuitionCurrentStatus = $("#intuitionCurrentStatus").val();
	var intuitionCurrentAction = $("#intuitionCurrentAction").val();
	
	if(updatedPaymentInStatus === 'WATCHED' && numberOfWatchlistReason === 0){
		isAllWatchlistReasonRemoved =true;
	}
	
	if((comment === null ||comment === undefined  || comment === '')
			&& (!isNull(updatedPaymentInStatus) && !isEmpty(updatedPaymentInStatus) && updatedPaymentInStatus != 'CLEAR')){
			alert("Please add comment");
	}else if (updatedPaymentInStatus !== 'CLEAR'
			&& $("input[name='payDetails_payStatusReasons']:checked").length === 0) {
		alert("Please select atleast one reason for " + updatedPaymentInStatus);
	} else if (sancBeneStatus === null || updatedPaymentInStatus === 'CLEAR' && sancBeneStatus == 'clear' 
			|| sancBeneStatus === undefined) {
		populateErrorMessage("main-content__body",
				"Payment can not be clear since last Debtor sanction check is fail ",
				"payment_update_error_field", "updatePaymentIn");
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
	var prePaymentInStatus = $("#paymentInDetails_Status_radio").data(
			'preConStatus');
	var updatedPaymentInStatus = $("input[name='paymentStatus']:checked").val();
	var comment = $('#comments').val();
	var isOnQueue = $('#payment_isOnQueue').val();
	var sancBeneStatus = $("#sanctions_contact tr:nth-child(1) td:nth-child(9)").text();
	var intuitionCurrentStatus = $("#intuitionCurrentStatus").val();
	
	if (updatedPaymentInStatus === 'WATCHED') {
		updatedPaymentInStatus = prePaymentInStatus;
		isUnwatched =true;
	}		
	
	if(isAllWatchlistReasonRemoved){
		updatedPaymentInStatus = 'CLEAR' ;
	} 
	
	$('#gifloaderforpayinpfx').css('visibility', 'visible');
	$("#paymentInDetails_Status_radio").data('preConStatus',
			updatedPaymentInStatus);
	var request = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var accountSfId = $('#contact_crmAccountId').val();
	var contactSfId = $('#contact_crmContactId').val();
	var paymentInId = $('#paymentinId').val();
	var orgCode = $('#account_organisation').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradeContractNumber = $('#payment_transNum').text();
	var custType = $('#account_clientType').text();
	var fraugsterEventServiceLogId = Number($('#fraugster_eventServiceLogId').val());
	var debtorAmount = $('#payment_amount').text();
	var sellCurrency = $('#payment_sellCurrency').text();
	var contactName = $('#contact_name').text();
	var clientNumber = $('#account_tradeAccountNum').text();
	var paymentMethod = $('#payment_paymentMethod').text();
	var statusReason = $("input[name='payDetails_payStatusReasons']:checked").val();
	var userResourceId = $('#userResourceId').val();
	var countryOfFundRiskLevel = $('#countryOfFundRiskLevel').val();
	var email = $('#contact_FurtherClient_email').text();
	var legalEntity = $('#leaglEntity').text();
	addField('accountId', accountId, request);
	addField('contactId', contactId, request);
	addField('paymentinId', paymentInId, request);
	addField('accountSfId', accountSfId, request);
	addField('contactSfId', contactSfId, request);
	addField('orgCode', orgCode, request);
	addField("statusReasons", getPaymentInReasons(), request);
	addField("watchlist", getWatchlists(), request);
	addField('overallWatchlistStatus', findInWatchlists(), request);
	addField("prePaymentInStatus", prePaymentInStatus, request);
	addField("updatedPaymentInStatus", updatedPaymentInStatus, request);
	addField("tradePaymentId", tradePaymentId, request);
	addField("tradeContactId", tradeContactId, request);
	addField("tradeContractNumber", tradeContractNumber, request);
	addField("custType", custType, request);
	addField('fragusterEventServiceLogId',fraugsterEventServiceLogId,request);
	addField("isOnQueue", isOnQueue, request);
	addField("debtorAmount", debtorAmount, request);
	addField("sellCurrency", sellCurrency, request);
	addField("contactName", contactName, request);
	addField("clientNumber", clientNumber, request);
	addField("userName", getUserObject().name, request);
	addField("paymentMethod", paymentMethod, request);
	addField("statusReason", statusReason, request);
	addField("comment", comment, request);
	addField("userResourceId",userResourceId,request);
	addField("countryRiskLevel", countryOfFundRiskLevel, request);
	addField('email', email, request);
	addField('legalEntity', legalEntity, request);
	
	$("#payment_statusReasons").data('preConStatus', updatedPaymentInStatus);
	addField("comment", comment, request);
	var onlineAccountStatus=$('#payment_complianceStatusdetails').text();
	if(onlineAccountStatus=='ACTIVE'){
		postPaymentInUpdate(request,prePaymentInStatus,updatedPaymentInStatus,isAutoUnlock);
	}
	
	else {
		$('#gifloaderforpayinpfx').css('visibility', 'hidden');
		alert("Account is Inactive,cannot clear payment");
	}		
}

function postPaymentInUpdate(request,prevStatus,updatedStatus,isAutoUnlock) {
	$("#updatePaymentIn").attr("disabled", true);
	$("#updatePaymentIn").addClass("disabled");

	$
			.ajax({
				url : '/compliance-portal/paymentInUpdate',
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforpayinpfx').css('visibility', 'hidden');
					if (data.errorCode != null) {
						alert('Error while updating status');
						$("#updatePaymentIn").attr("disabled", false);
						$("#updatePaymentIn").removeClass("disabled");
					} else {
						var addedRecords = Number($(
								'#actLogTotalRecordsPayInId').val())
								+ data.activityLogData.length;
						setActivityLogTotalRecords(addedRecords);
						getActivities(1, 10, Number($('#paymentinId').val()),
								false);
						setPaymentInInfo(request.updatedPaymentInStatus);

						$("#payment_complianceStatus").text(
								$("#payment_statusReasons")
										.data('preConStatus'));
						$("#updatePaymentIn").attr("disabled", false);
						$("#updatePaymentIn").removeClass("disabled");
						if (request.updatedPaymentInStatus === "CLEAR") {
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
								"payment_update_error_field", "updatePaymentIn");
						var accountTMFlag = $('#accountTMFlag').val();
						var isWatchlistUpdated = data.isWatchlistUpdated;
						if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4){		
							
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
								updateAccountIntuitionRepeatCheckStatusForPayment(intuitionRequest, getUser(), getComplianceServiceBaseUrl(), 'FundsIn'); 
							}
							else{
								updateFundsInIntuitionRepeatCheckStatus(getUser(),getComplianceServiceBaseUrl());
							}
							
							
							
						}
					}
				},
				error : function() {
					$('#gifloaderforpayinpfx').css('visibility', 'hidden');
					$("#updatePaymentIn").attr("disabled", false);
					$("#updatePaymentIn").removeClass("disabled");
					populateErrorMessage("main-content__body",
							"Error while updating ",
							"payment_update_error_field", "updatePaymentIn");
				}
			});
}

function setPaymentInInfo(status) {
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

function getPaymentInReasons() {
	var newData = [];
	var saveData = [];
	var preValue = $("#payment_statusReasons")
			.data('prePaymentInStatusReasons');
	$.each(preValue, function(index, data) {
		newData[index] = {
			"name" : data.name,
			"preValue" : data.preValue,
			"updatedValue" : $(
					"input[value='" + data.name
							+ "'][name='payDetails_payStatusReasons']").prop(
					'checked')
		}
		saveData[index] = {
			"name" : data.name,
			"preValue" : newData[index].updatedValue,
			"updatedValue" : newData[index].updatedValue
		};
	});

	$("#payment_statusReasons").data('prePaymentInStatusReasons', saveData);
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

function setActivityLogTotalRecords(totalRecordValue) {
	$("#actLogTotalRecordsPayInId").val(totalRecordValue);
}

function setContactSanctionTotalRecords(totalRecordValue) {
	$("#sanctionTotalRecordsPayInId_contact").val(totalRecordValue);
}

function setCustomCheckPaymentInTotalRecords(totalRecordValue) {
	$("#customCheckTotalRecordsPayInId").val(totalRecordValue);
}

function getActivities(minRecord, maxRecord, paymentInId, isViewMoreRequest) {
	var request = {};
	var comment = $('#comments').val();
	var accountId =$('#contact_accountId').val();
	var rowToFetch = getViewMoreRecordsize();
	addField("minRecord", minRecord, request);
	addField("maxRecord", maxRecord, request);
	addField("entityId", paymentInId, request);
	addField("comment",comment,request);
	addField("accountId",accountId,request);
	addField("rowToFetch",rowToFetch,request);
	getActivityLogs(request, isViewMoreRequest);
}

function getActivityLogs(request, isViewMoreRequest) {
	$.ajax({
		url : '/compliance-portal/paymentInActivites',
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

function postCustomeCheckResend(request, baseUrl, user) {
	disableButton('payin_customChecks_recheck');
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/customCheckfundsIn',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforcustomresend').css('visibility', 'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayInId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					setCustomCheckResend(data.response, data.checkedOn);
					getActivities(1, 10, Number($('#paymentinId').val()), false);
					populateSuccessMessage("main-content__body",
							"Custom Repeat Check Successfully done",
							"customChecks_resend_error_field",
							"payin_customChecks_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevCustomCheckStatus = $('#customCheckStatus').val();
					var currentCustomCheckStatus = data.response.overallStatus;
					if (prevCustomCheckStatus.toUpperCase() != currentCustomCheckStatus && currentCustomCheckStatus != 'SERVICE_FAILURE' &&
						(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						
						updateFundsInIntuitionRepeatCheckStatus(user, baseUrl);
						
						var previousCustomCheckStatus = document.getElementById("customCheckStatus");
						previousCustomCheckStatus.value = currentCustomCheckStatus;
					}

					enableButton('payin_customChecks_recheck');

				},
				error : function() {
					$('#gifloaderforcustomresend').css('visibility', 'hidden');
					enableButton('payin_customChecks_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending customCheck",
							"customChecks_resend_error_field",
							"payin_customChecks_recheck");
				}
			});
}

function resendSanction(entityId, entityType) {
	$('#gifloaderforSanctionresend').css('visibility', 'visible');
	var sanction = {};
	var paymentInId = Number($('#paymentinId').val());
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	addField('paymentInId', paymentInId, sanction);
	addField('tradeAccountNumber', tradeAccountNumber, sanction);
	addField('tradeContactId', tradeContactId, sanction);
	addField('tradePaymentId', tradePaymentId, sanction);
	addField('entityId', entityId, sanction);
	addField('entityType', entityType, sanction);
	addField('org_code', orgCode, sanction);
	postSanctionResend(sanction, getComplianceServiceBaseUrl(), getUser());
}

function postSanctionResend(request, baseUrl, user) {
	disableButton('updateSanction_contact');// disable update and repeat check
											// button
	disableButton('sanction_recheck_contact');
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/fundsInSanction',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforSanctionresend')
							.css('visibility', 'hidden');
					var addedRecords = Number($('#actLogTotalRecordsPayInId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					
					var currentSanctionStatus = data.summary.status;

					if (data.summary.status === 'PASS') {
						data.summary.status = true;
					} else {
						data.summary.status = false;
					}
					setResendSanctionResponse(data.summary, request.entityType,
							request.entityId);

					getActivities(1, 10, Number($('#paymentinId').val()), false);
					enableButton('updateSanction_contact');
					enableButton('sanction_recheck_contact');
					populateSuccessMessage("main-content__body",
							"Sanction Repeat Check Successfully done",
							"sanction_resend_error_field",
							"sanction_recheck_contact");
						
					var accountTMFlag = $('#accountTMFlag').val();
					var prevSanctionStatus = $('#sanctionStatus').val();
					if (prevSanctionStatus.toUpperCase() != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' &&
						(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						
						updateFundsInIntuitionRepeatCheckStatus(user, baseUrl);
						
						var previousSanctionStatus = document.getElementById("sanctionStatus");
						previousSanctionStatus.value = currentSanctionStatus;
					}

				},
				error : function() {
					$('#gifloaderforSanctionresend')
							.css('visibility', 'hidden');
					enableButton('updateSanction_'
							+ request.entityType.toLowerCase());
					enableButton('sanction_recheck_'
							+ request.entityType.toLowerCase());
					populateErrorMessage("main-content__body",
							"Error while resending sanction",
							"sanction_resend_error_field",
							"sanction_recheck_contact");
				}
			});
}

function setResendSanctionResponse(sanction, entityType, entityId) {
	var updatedOn = getDateTimeFormat(getEmptyIfNull(sanction.updatedOn));
	var updatedBy = getEmptyIfNull(sanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.worldCheck);
	var status = getEmptyIfNull(sanction.status);
	var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
	var row = '<tr>';
	row += '<td hidden="hidden" class="center">' + sanction.eventServiceLogId
			+ '</td>';
	row += '<td hidden="hidden">' + entityType + '</td>';
	row += '<td hidden="hidden">' + entityId + '</td>';
	row += '<td class="nowrap">' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	row += '<td class="nowrap"><a href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	row += getYesOrNoCell(status);
	row += '</tr>'
	var totalRecords = Number($('#sanctionTotalRecordsPayInId_contact').val()) + 1;
	if (sanction.passCount === undefined || sanction.passCount === null) {
		if (status) {
			setContactSanctionIndicator(totalRecords, 0);
			setContactSanctionTotalRecords(totalRecords);
		} else {
			setContactSanctionIndicator(0, totalRecords);
			setContactSanctionTotalRecords(totalRecords);
		}
	}
	$('#sanctions_contact').prepend(row);
	var noOfRows = countRows("sanctions_contact");
	var viewMoreTotalRecords = Number($('#sanctionTotalRecordsPayInId_contact')
			.val());
	var leftRecords = updateLeftRecords(noOfRows, viewMoreTotalRecords,
			"leftRecordsPayInIdSanc_contact");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_SancId_contact",
			"viewMoreDetailsPayIn_Sanc_contact");

}

function setContactSanctionIndicator(passCount, failCount) {
	$('#payInDetails_sanctioncon_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'payInDetails_contactSanctionPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'payInDetails_contactSanctionFail');
	}
	indicator += '</a>';
	$('#payInDetails_sanctioncon_indicatior').append(indicator);
}

function updateSanction(entityId, entityType) {
	var fieldName = $("input[name='updateField_contact']:checked").val();
	var value = $("input[name='updateField_value_contact']:checked").val();
	
	var previousOfacValue = getPreviousOfacValue(entityType);
	var previousWorldCheckValue = getPreviousWorldCheckValue(entityType);
	
	if (value === undefined || value === null || fieldName === undefined
			|| fieldName === null) {
		alert("Please select required fields for sanction update");
	} else if( (previousOfacValue === value && fieldName === 'ofaclist') ||
					(previousWorldCheckValue === value && fieldName === 'worldcheck')) {
		alert("Please change "+fieldName+" value for "+entityType.toLowerCase()+" and then update sanction");
		hideGifLoaderForSanctionUpdate(entityType);
	} else {
		$('#gifloaderforUpdatesanction').css('visibility', 'visible');
		var request = {};
		var accountId = Number($('#contact_accountId').val());
		var paymentinid = Number($('#paymentinId').val());
		var orgCode = $('#account_organisation').text();
		var eventId = Number($("#sanctions_contact").find('tr:first').find(
				'td:first').text());
		addField("accountId", accountId, request);
		addField('resourceId', paymentinid, request);
		addField('resourceType', 'FUNDSIN', request);
		addField("orgCode", orgCode, request);

		var sanctions = [];
		addField("sanctions", sanctions, request);
		sanctions.push({
			"eventServiceLogId" : eventId,
			"field" : fieldName,
			"value" : value,
			"entityId" : entityId,
			"entityType" : entityType
		});
		postUpdateSanction(request, getComplianceServiceBaseUrl(), getUser());
	}
}

function postUpdateSanction(request, baseUrl, user) {
	disableButton('updateSanction_contact');// disable update and repeat check
											// button
	disableButton('sanction_recheck_contact');
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
						$('#gifloaderforUpdatesanction').css('visibility', 'hidden');
						enableButton('updateSanction_contact');
						enableButton('sanction_recheck_contact');
						populateErrorMessage("main-content__body","Error while updating sanction","sanction_update_error_field","updateSanction_contact");
					}else {
						$('#gifloaderforUpdatesanction')
						.css('visibility', 'hidden');
						var addedRecords = Number($('#actLogTotalRecordsPayInId')
						.val())
						+ data.activityLogs.activityLogData.length;
						setActivityLogTotalRecords(addedRecords);
						getActivities(1, 10, Number($('#paymentinId').val()), false);
						updateSanctionColumn(data);
						enableButton('updateSanction_contact');
						enableButton('sanction_recheck_contact');
						populateSuccessMessage("main-content__body",
						"Updatin sanction Successfully done",
						"sanction_update_error_field",
						"updateSanction_contact");
						var accountTMFlag = $('#accountTMFlag').val();
						var currentSanctionStatus = data.status;
						var prevSanctionStatus = $('#sanctionStatus').val();
						if (prevSanctionStatus.toUpperCase() != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' &&
							(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {

							updateFundsInIntuitionRepeatCheckStatus(user, baseUrl);

							var previousSanctionStatus = document.getElementById("sanctionStatus");
							previousSanctionStatus.value = currentSanctionStatus;
						}
					}
					
				},
				error : function() {
					$('#gifloaderforUpdatesanction')
							.css('visibility', 'hidden');
					enableButton('updateSanction_contact');
					enableButton('sanction_recheck_contact');
					populateErrorMessage("main-content__body",
							"Error while updating sanction",
							"sanction_update_error_field",
							"updateSanction_contact");

				}
			});
}

function updateSanctionColumn(data) {
	var fieldName = $("input[name='updateField_contact']:checked").val();
	var value = $("input[name='updateField_value_contact']:checked").val();
	if (fieldName.toLowerCase() === "ofaclist") {
		$('#sanctions_contact tr:nth-child(1) td:nth-child(7)').text(value);
	}
	if (fieldName.toLowerCase() === "worldcheck") {
		$('#sanctions_contact tr:nth-child(1) td:nth-child(8)').text(value);
	}
	if (data.status === 'PASS') {
		$('#sanctions_contact tr:nth-child(1) td:nth-child(9)').removeClass(
				'no-cell').addClass('yes-cell').html(
				'<i class="material-icons">check</i>');
	} else {
		$('#sanctions_contact tr:nth-child(1) td:nth-child(9)').removeClass(
				'yes-cell').addClass('no-cell').html(
				'<i class="material-icons">clear</i>');
	}

}

function resendCustomCheck() {
	$('#gifloaderforcustomresend').css('visibility', 'visible');
	var request = {};
	var paymentInId = Number($('#paymentinId').val());
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	addField('paymentInId', paymentInId, request);
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('tradeContactId', tradeContactId, request);
	addField('tradePaymentId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	postCustomeCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function setCustomCheckResend(customCheck, checkedOn) {
	var custType = $('#account_clientType').text();// Add for AT-3738

	if(custType == "PFX"){
		$("#customChecks").prepend(getWhitelistCheck(getDateTimeFormat(getEmptyIfNull(checkedOn)),
					customCheck.whiteListCheck) + getFirstCreditCheck(customCheck.firstCreditCheck) + getEuPoiCheck(customCheck.euPoiCheck) + getCDINCFirstCreditCheck(customCheck.cdincFirstCreditCheck));	
	}else{
		$("#customChecks").prepend(getWhitelistCheck(getDateTimeFormat(getEmptyIfNull(checkedOn)),
					customCheck.whiteListCheck) + getFirstCreditCheck(customCheck.firstCreditCheck) + getEuPoiCheck(customCheck.euPoiCheck));
	}
	
	var noOfRows = countRows("customChecks");
	var totalRecords = Number($('#customCheckTotalRecordsPayInId').val()) + 1;
	var leftRecords = updateLeftRecordsForCusChk(noOfRows, totalRecords,"leftRecordsPayInIdCusChk");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_CusChkId","viewMoreDetailsPayIn_CusChk");
	Number($('#customCheckTotalRecordsPayInId').val(totalRecords));
	if (customCheck.overallStatus === 'PASS') {
		setCustomCheckIndicator(totalRecords, 0);
	}
	else {
		setCustomCheckIndicator(0, totalRecords);
	}
}

function getVelocityCheck(checkedOn, velocityCheck) {

	var row = '<tr>';
	row += '<td class="nowrap">' + checkedOn + '</td>';
	row += '<td >Velocity Check</td>';
	row += '<td class="nowrap"><ul><li>Amount Check : '
			+ velocityCheck.permittedAmoutcheck + '</li></ul></td>';
	row += '</tr>';
	return row;

}

function getWhitelistCheck(checkedOn, whiteListCheck) {
	var amountRangeStatus = whiteListCheck.amoutRange,
	currencyStatus = whiteListCheck.currency,
	thirdPartyStatus = whiteListCheck.thirdParty;
	
	currencyStatus = getWhiteListCheckCaseSet(currencyStatus);
	amountRangeStatus = getWhiteListCheckCaseSet(amountRangeStatus);
	thirdPartyStatus = getWhiteListCheckCaseSet(thirdPartyStatus);
	
	var row = '<tr>';
	row += '<td class="nowrap">' + checkedOn + '</td>';
	row += '<td >Whitelist Check</td>';
	if(amountRangeStatus === "FAIL") {
		row += '<td class="nowrap"><ul><li>Currency Check : '
				+ currencyStatus + '</li><li>Amount range check : '
				+ amountRangeStatus + ' (Max:'+whiteListCheck.maxAmount+')' + '</li><li>Third party check : '
				+ thirdPartyStatus + '</li></ul></td>';
	} else {
		row += '<td class="nowrap"><ul><li>Currency Check : '
			+ currencyStatus + '</li><li>Amount range check : '
			+ amountRangeStatus + '</li><li>Third party check : '
			+ thirdPartyStatus + '</li></ul></td>';
	}
	row += '</tr>';
	return row;
}

function getFirstCreditCheck(firstCreditCheck) {
    var firstCreditCheckstatus= firstCreditCheck.status;
    firstCreditCheckstatus = getFirstCreditCheckCaseSet(firstCreditCheckstatus);
	var row = '<tr>';
	row += '<td class="nowrap"> </td>';
	row += '<td >FirstCredit Check</td>';
	row += '<td class="nowrap"><ul><li> Status: '+ firstCreditCheckstatus +'</li></ul></td>';
	row += '</tr>';
	return row;
}

function getFirstCreditCheckCaseSet(firstCreditCheck) {

	switch(firstCreditCheck) {
	case 'PASS': firstCreditCheck = 'Pass';
	break;
	case 'FAIL' : firstCreditCheck = 'Fail';
	break;
	case 'NOT_PERFORMED': firstCreditCheck = 'Not Performed';
	break;
	case 'NO_MATCH': firstCreditCheck = 'No Match';
	break;
	case 'SERVICE_FAILURE': firstCreditCheck = 'Service Failure';
	break;
	case 'NOT_REQUIRED': firstCreditCheck = 'Not Required';
	break;
	}
    return firstCreditCheck;
}

//AT-3349
function getEuPoiCheck(euPoiCheck) {
    var euPoiCheckStatus = euPoiCheck.status;
    euPoiCheckStatus = getEuPoiCheckCaseSet(euPoiCheckStatus);
	var row = '<tr>';
	row += '<td class="nowrap"> </td>';
	row += '<td >EUPOIDoc Check</td>';
	row += '<td class="nowrap"><ul><li> Status: '+ euPoiCheckStatus +'</li></ul></td>';
	row += '</tr>';
	return row;
}

function getEuPoiCheckCaseSet(euPoiCheck) {

	switch(euPoiCheck) {
	case 'PASS': euPoiCheck = 'Pass';
	break;
	case 'FAIL' : euPoiCheck = 'Fail';
	break;
	case 'NOT_PERFORMED': euPoiCheck = 'Not Performed';
	break;
	case 'NO_MATCH': euPoiCheck = 'No Match';
	break;
	case 'SERVICE_FAILURE': euPoiCheck = 'Service Failure';
	break;
	case 'NOT_REQUIRED': euPoiCheck = 'Not Required';
	break;
	}
    return euPoiCheck;
}

//AT-3738
function getCDINCFirstCreditCheck(cdincFirstCreditCheck) {
    var cdincFirstCreditCheckstatus= cdincFirstCreditCheck.status;
    cdincFirstCreditCheckstatus = getFirstCreditCheckCaseSet(cdincFirstCreditCheckstatus);
	var row = '<tr>';
	row += '<td class="nowrap"> </td>';
	row += '<td >CDINC FirstCredit Check</td>';
	row += '<td class="nowrap"><ul><li> Status: '+ cdincFirstCreditCheckstatus +'</li></ul></td>';
	row += '</tr>';
	return row;
}

function getWhiteListCheckCaseSet(whiteListCheck) {

	switch(whiteListCheck) {
	case 'PASS': whiteListCheck = 'Pass';
	break;
	case 'FAIL' : whiteListCheck = 'Fail';
	break;
	case 'REFER': whiteListCheck = 'Refer';
	break;
	case 'PENDING': whiteListCheck = 'Pending';
	break;
	case 'NOT_PERFORMED': whiteListCheck = 'Not Performed';
	break;
	case 'NO_MATCH': whiteListCheck = 'No Match';
	break;
	case 'WATCHLIST': whiteListCheck = 'Watchlist';
	break;
	case 'SERVICE_FAILURE': whiteListCheck = 'Service Failure';
	break;
	case 'NOT_REQUIRED': whiteListCheck = 'Not Required';
	break;
	}
    return whiteListCheck;
}


function setCustomCheckIndicator(passCount, failCount) {
	$('#payInDetails_customchecks_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Custom checks';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'account_customCheck_positive');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'account_customCheck_negative');
	}
	indicator += '</a>';
	$('#payInDetails_customchecks_indicatior').append(indicator);
}

function showProviderResponse(eventServiceLogId,serviceType,chartDisplayFlag) {
	var getProviderResponseRequest = {};
	addField('eventServiceLogId', eventServiceLogId, getProviderResponseRequest);
	addField('serviceType', serviceType, getProviderResponseRequest);
	getProviderResponse(getProviderResponseRequest,chartDisplayFlag);
}

function viewMoreDetails(serviceType, id, totalRecordsId, leftRecordsId,
		entityType) {

	var viewMore = {};
	var noOfRows = countRows(id);
	if(id=="fraugster")// AT-3325
		 noOfRowsFrg=countRows(id);
		if(id=="sanctions_contact")
		 noOfRowsSanction=countRows(id);
		 //AT-3738
		if(id=="customChecks")
		 noOfRowsCustomCheck=countRows(id);
		//AT-4306
		if(id=="payInDetails_intuition")
		 noOfRowsIntuitionCheck=countRows(id);
		if((clicked==false) || (clicked== true && noOfRowsFrg>prevCountfrg)||(clicked== true && noOfRowsSanction>prevCountSanction)||(clicked== true && noOfRowsCustomCheck>prevCountCustomCheck)
		||(clicked== true && noOfRowsIntuitionCheck>prevCountIntuitionCheck))
		{
		clicked=true;
	var totalRecords = Number($("#" + totalRecordsId).val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, leftRecordsId);
	var paymentInId = Number($('#paymentinId').val());
	var orgCode = $('#account_organisation').text();
	var clientType = $('#account_clientType').text();
	var accountId = $('#contact_accountId').val();
	var minViewRecord = calculateMinRecord(noOfRows);
	var maxViewRecord = calculateMaxRecord(minViewRecord);

	addField('paymentInId', paymentInId, viewMore);
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
	if (serviceType == "SANCTION") {
		postSanctionMoreDetails(viewMore);
		prevCountSanction=noOfRowsSanction;
	} else if (serviceType == "FRAUGSTER") {
		prevCountfrg=noOfRowsFrg;
		postFraugsterMoreDetails(viewMore);
	} else if (serviceType == "VELOCITYCHECK") {
		prevCountCustomCheck=noOfRowsCustomCheck; //AT-3738
		var minViewRecordCustChk = calculateMinRecordCustChk(noOfRows);
		var maxViewRecordCustChk = calculateMaxRecordCustChk(minViewRecordCustChk);
		addField('minViewRecord', minViewRecordCustChk, viewMore);
		addField('maxViewRecord', maxViewRecordCustChk, viewMore);
		postCustomCheckMoreDetails(viewMore);
	} else if (serviceType == "ACTIVITYLOG") {
		getActivities(minViewRecord, maxViewRecord, paymentInId, true);
	} else if (serviceType == "PAYMENT_IN") {
		postPaymentInMoreDetails(viewMore);
	} else if (serviceType == "PAYMENT_OUT") {
		postPaymentOutMoreDetails(viewMore);
	} else if (serviceType == "COUNTRYCHECK") {
		// postCountryCheckMoreDetails(viewMore);
	} else if (serviceType == "TRANSACTION_MONITORING") {
		postIntuitionCheckMoreDetails(viewMore);
		prevCountIntuitionCheck=noOfRowsIntuitionCheck;
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

function calculateMinRecord(noOfRows) {

	return noOfRows + 1;
}

function calculateMaxRecord(minViewRecord) {

	return minViewRecord + Number(getViewMoreRecordsize()) - 1;
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
	var totalRecords = $('#furPayInDetailsTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdFurPayInDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_FurPayInDetailsId",
			"viewMoreDetailsPayIn_FurPayInDetails");

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

$("#further_PayInDetails_indicatior").click(
		function() {

			$("#further_paymentInDetails").find('tr').slice(10).remove();
			var noOfRows = countRows("further_paymentInDetails");
			var totalRecords = $('#furPayInDetailsTotalRecordsPayInId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayInIdFurPayInDetails");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_FurPayInDetailsId",
					"viewMoreDetailsPayIn_FurPayInDetails");

			$("#further_paymentOutDetails").find('tr').slice(10).remove();
			var noOfRowsOut = countRows("further_paymentOutDetails");
			var totalRecordsOut = $('#furPayOutDetailsTotalRecordsPayInId')
					.val();
			var leftRecordsOut = updateLeftRecords(noOfRowsOut,
					totalRecordsOut, "leftRecordsPayInIdFurPayOutDetails");
			updateViewMoreBlock(leftRecordsOut,
					"viewMorePayIn_FurPayOutDetailsId",
					"viewMoreDetailsPayIn_FurPayOutDetails");

		});

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
		var valuedate = getEmptyIfNull(payment.valuedate);
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
	var totalRecords = $('#furPayOutDetailsTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdFurPayOutDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_FurPayOutDetailsId",
			"viewMoreDetailsPayIn_FurPayOutDetails");

}

$("#payInDetails_fraugster_indicatior").click(
		function() {
			clicked=false;
			prevCountfrg=0;// AT-3325
			noOfRowsFrg=0;
			$("#fraugster").find('tr').slice(1).remove();
			var noOfRows = countRows("fraugster");
			var totalRecords = $('#fraugsterTotalRecordsPayInId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayInIdFraug");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_FraugId",
					"viewMoreDetailsPayIn_Fraug");
		});

function postFraugsterMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentInViewMoreDetails',
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
			var row = '<tr href="#" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'FRAUGSTER\',\'FraugsterChart\')">';
			row += '<td>' + createdOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'FRAUGSTER\')">'
					+ fraugsterId
					+ '</a></td>';
			row += '<td class="nowrap">Not Required</td>';
			row += '<td  class="nowrap center"><i class="material-icons">-</i></td>';
			row += '</tr>'
			$("#fraugster").append(row);
		}
		var noOfRows = countRows("fraugster");
		var totalRecords = Number($('#fraugsterTotalRecordsPayInId').val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayInIdFraug");
		updateViewMoreBlock(leftRecords, "viewMorePayIn_FraugId",
				"viewMoreDetailsPayIn_Fraug");
	}
	else {
		for (var i = 0; i < fraugster.length; i++) {
			var createdOn = getEmptyIfNull(fraugster[i].createdOn);
			var updatedBy = getEmptyIfNull(fraugster[i].updatedBy);
			var fraugsterId = getEmptyIfNull(fraugster[i].fraugsterId);
			var score = getEmptyIfNull(fraugster[i].score);
			var eventServiceLogId = getEmptyIfNull(fraugster[i].id);
			var status = getEmptyIfNull(fraugster[i].status);
			var row = '<tr href="#" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'FRAUGSTER\',\'FraugsterChart\')">';
			row += '<td>' + createdOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'FRAUGSTER\')">'
					+ fraugsterId
					+ '</a></td>';
			row += '<td  class="number" class="nowrap">' + score + '</td>'
			row += getYesOrNoCell(status);
			row += '</tr>'
			$("#fraugster").append(row);
		}
		var noOfRows = countRows("fraugster");
		var totalRecords = Number($('#fraugsterTotalRecordsPayInId').val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayInIdFraug");
		updateViewMoreBlock(leftRecords, "viewMorePayIn_FraugId",
				"viewMoreDetailsPayIn_Fraug");
	}
}

$("#payInDetails_sanctioncon_indicatior").click(
		function() {
			clicked= false;
			prevCountSanction=0;// AT-3325
			noOfRowsSanction=0;
			$("#sanctions_contact").find('tr').slice(1).remove();
			var noOfRows = countRows("sanctions_contact");
			var totalRecords = $('#sanctionTotalRecordsPayInId_contact').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayInIdSanc_contact");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_SancId_contact",
					"viewMoreDetailsPayIn_Sanc_contact");

		});

function postSanctionMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentInViewMoreDetails',
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
			var eventServiceLogId = getEmptyIfNull(sanction[i].eventServiceLogId);
			var row = '<tr>';
			row += '<td hidden="hidden" class="center">'
					+ sanction[i].eventServiceLogId + '</td>';
			row += '<td class="nowrap">' + updatedOn + '</td>';
			row += '<td class="nowrap">' + updatedBy + '</td>';
			row += '<td class="nowrap"><a href="javascript:void(0);" onclick="showProviderResponse('
					+ eventServiceLogId
					+ ',\'SANCTION\')">'
					+ sanctionId
					+ '</a></td>';
			row += '<td class="nowrap">' + ofacList + '</td>';
			row += '<td class="nowrap">' + worldCheck + '</td>';
			row += getYesOrNoCell(status);
			row += '</tr>'
			// if(sanction.passCount === undefined || sanction.passCount ===
			// null){
			if (status) {
				sanction.passCount = 1;
			} else {
				sanction.failCount = 1;
			}
			/*
			 * } var sanctionPassCount = sanction.passCount; var
			 * sanctionFailCount = sanction.failCount; //
			 * setSanctionIndicator(sanctionPassCount,sanctionFailCount) var id =
			 * sanction[i].entityType; id = id.toLowerCase();
			 */
			$("#sanctions_contact").append(row);
		}
		var noOfRows = countRows("sanctions_contact");
		var totalRecords = Number($('#sanctionTotalRecordsPayInId_contact')
				.val());
		var leftRecords = updateLeftRecords(noOfRows, totalRecords,
				"leftRecordsPayInIdSanc_contact");
		updateViewMoreBlock(leftRecords, "viewMorePayIn_SancId_contact",
				"viewMoreDetailsPayIn_Sanc_contact");
	}
}

$("#payInDetails_customchecks_indicatior").click(function() {

			//Added cust type for AT-3349
			clicked=false; //AT-3738
			prevCountCustomCheck=0; //AT-3738
			noOfRowsCustomCheck=0; //AT-3738
			var custType = $('#account_clientType').text(); //Add for AT-3738
			if(custType == "PFX"){
				$("#customChecks").find('tr').slice(4).remove();
			}else{
				$("#customChecks").find('tr').slice(3).remove();
			}
			var noOfRows = countRows("customChecks");
			var totalRecords = Number($('#customCheckTotalRecordsPayInId').val());
			var leftRecords = updateLeftRecordsForCusChk(noOfRows, totalRecords,"leftRecordsPayInIdCusChk");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_CusChkId","viewMoreDetailsPayIn_CusChk");
});

function calculateMinRecordCustChk(noOfRows) {
	var minViewRecordCustChk
	var custType = $('#account_clientType').text(); //Add for AT-3738
	if(custType == "PFX"){
		minViewRecordCustChk = noOfRows / 2;
	}else{
		minViewRecordCustChk = noOfRows / 2 + 1;
	}		
	return minViewRecordCustChk;
}

function calculateMaxRecordCustChk(minViewRecordCustChk) {
	var viewMoreConfSize = Number(getViewMoreRecordsize());
	var totalRecords = Number($("#customCheckTotalRecordsPayInId").val());
	var noOfRows = countRows("customChecks");
	var leftRecords = totalRecords - noOfRows / 2;
	if (leftRecords < viewMoreConfSize) {
		return minViewRecordCustChk + leftRecords;
	} else {
		return minViewRecordCustChk + Number(getViewMoreRecordsize()) - 1;
	}
}

function postCustomCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentInViewMoreDetails',
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

function updateLeftRecordsForCusChk(noOfRows, totalRecords, id) {
	//AT-3349
    var leftRecord;
    var custType = $('#account_clientType').text(); //Add for AT-3738
    
    if(custType == "PFX"){
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

function setCustomCheckMoreDetailsResponse(customCheck) {
	var custType = $('#account_clientType').text();
	$.each(customCheck, function(index, customCheck) {
	
		if(custType == "PFX"){
				$("#customChecks").append(getWhitelistCheck(customCheck.checkedOn,customCheck.whiteListCheck)
					+getFirstCreditCheck(customCheck.firstCreditCheck)
					+getEuPoiCheck(customCheck.euPoiCheck)
					+getCDINCFirstCreditCheck(customCheck.cdincFirstCreditCheck));
		}else{
				$("#customChecks").append(getWhitelistCheck(customCheck.checkedOn,customCheck.whiteListCheck)
					+getFirstCreditCheck(customCheck.firstCreditCheck)
					+getEuPoiCheck(customCheck.euPoiCheck));
		}
	});
	var noOfRows = countRows("customChecks");
	var totalRecords = Number($('#customCheckTotalRecordsPayInId').val());
	var leftRecords = updateLeftRecordsForCusChk(noOfRows, totalRecords,"leftRecordsPayInIdCusChk");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_CusChkId","viewMoreDetailsPayIn_CusChk");
}

$("#payInDetails_activitylog_indicatior").click(
		function() {

			$("#activityLog").find('tr').slice(10).remove();
			var noOfRows = countRows("activityLog");
			var totalRecords = $('#actLogTotalRecordsPayInId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayInId_ActLog");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_ActLogId",
					"viewMoreDetailsPayIn_ActLog");
			$("#viewMoreAuditTrailDetails_ActLog").hide();

		});

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
	var totalRecords = $('#actLogTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInId_ActLog");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_ActLogId",
			"viewMoreDetailsPayIn_ActLog");

}

function setViewMoreActLogData() {

	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInId_ActLog");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_ActLogId",
			"viewMoreDetailsPayIn_ActLog");
	setActivityLogTotalRecords(totalRecords);

}

function viewMoreLoadData() {

	var noOfRows = countRows("fraugster");
	var totalRecords = $('#fraugsterTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdFraug");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_FraugId",
			"viewMoreDetailsPayIn_Fraug");

	var noOfRowsSancChk = countRows("sanctions_contact");
	var totalRecordsSanChk = $('#sanctionTotalRecordsPayInId_contact').val();
	var leftRecordsSanChk = updateLeftRecords(noOfRowsSancChk,
			totalRecordsSanChk, "leftRecordsPayInIdSanc_contact");
	updateViewMoreBlock(leftRecordsSanChk, "viewMorePayIn_SancId_contact",
			"viewMoreDetailsPayIn_Sanc_contact");

	var noOfRowsCus = countRows("customChecks");
	var totalRecordsCus = Number($('#customCheckTotalRecordsPayInId').val());
	var leftRecordsCus = updateLeftRecordsForCusChk(noOfRowsCus, totalRecordsCus,"leftRecordsPayInIdCusChk");
	updateViewMoreBlock(leftRecordsCus, "viewMorePayIn_CusChkId","viewMoreDetailsPayIn_CusChk");
}

function viewMoreResetData() {

	$("#fraugster").find('tr').slice(1).remove();
	$("#sanctions_contact").find('tr').slice(1).remove();
	$("#customChecks").find('tr').slice(2).remove();
}

function viewMoreLoadDataActLog() {

	var noOfRowsActLog = countRows("activityLog");
	var totalRecordsActLog = $('#actLogTotalRecordsPayInId').val();
	var leftRecordsActLog = updateLeftRecords(noOfRowsActLog,
			totalRecordsActLog, "leftRecordsPayInId_ActLog");
	updateViewMoreBlock(leftRecordsActLog, "viewMorePayIn_ActLogId",
			"viewMoreDetailsPayIn_ActLog");

	var noOfRows = countRows("further_paymentInDetails");
	var totalRecords = $('#furPayInDetailsTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdFurPayInDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_FurPayInDetailsId",
			"viewMoreDetailsPayIn_FurPayInDetails");

	var noOfRowsOut = countRows("further_paymentOutDetails");
	var totalRecordsOut = $('#furPayOutDetailsTotalRecordsPayInId').val();
	var leftRecordsOut = updateLeftRecords(noOfRowsOut, totalRecordsOut,
			"leftRecordsPayInIdFurPayOutDetails");
	updateViewMoreBlock(leftRecordsOut, "viewMorePayIn_FurPayOutDetailsId",
			"viewMoreDetailsPayIn_FurPayOutDetails");
	
	$("#fxticket_indicatior").trigger('click');
	$("#client-wallets").trigger('click');

}

function viewMoreResetActLogData() {

	$("#activityLog").find('tr').slice(10).remove();
	$("#further_paymentInDetails").find('tr').slice(3).remove();
	$("#further_paymentOutDetails").find('tr').slice(3).remove();

}

$("#payInDetails_countrycheck_indicatior").click(
		function() {

			$("#countryCheck_contact").find('tr').slice(1).remove();
			var noOfRows = countRows("countryCheck_contact");
			var totalRecords = $('#countryCheckTotalRecordsPayInId').val();
			var leftRecords = updateLeftRecords(noOfRows, totalRecords,
					"leftRecordsPayInIdCountryChk");
			updateViewMoreBlock(leftRecords, "viewMorePayIn_CountryChkId",
					"viewMoreDetailsPayIn_CountryChk");

		});

function postCountryCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentInViewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			setCountryCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			alert('Error while fetching country check more details');

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
		row += '<td class="nowrap"><ul>' + status + '</ul></td>';
		row += '</tr>';
		rows += row;
	});
	$("#countryCheck_contact").append(rows);
	var noOfRows = countRows("countryCheck_contact");
	var totalRecords = $('#countryCheckTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdCountryChk");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_CountryChkId",
			"viewMoreDetailsPayIn_CountryChk");

}


function openClientDetail() {
	$('#openClientForm').submit();
}

function resendFraugsterCheck() {
	$('#gifloaderforfraugsterresend').css('visibility', 'visible');
	var request = {};
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('paymentFundsInId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	postFraugsterCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postFraugsterCheckResend(request, baseUrl, user) {
	disableButton('payin_fraugster_recheck');
	
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/fraugsterCheckfundsIn',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforfraugsterresend').css('visibility', 'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayInId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					var rows = '';
					rows +=setFraugsterResendResponse(data.activityLogs, data.eventServiceLogId,data.summary);
					var id = 'fraugster';
					$("#" + id).prepend(rows);
					getActivities(1, 10, Number($('#paymentinId').val()), false);
				
					var noOfRows = countRows("fraugster");
					var totalRecords = Number($('#fraugsterTotalRecordsPayInId')
							.val());
					var leftRecords = updateLeftRecords(noOfRows, totalRecords,
							"leftRecordsPayInIdFraug");
					updateViewMoreBlock(leftRecords, "viewMorePayIn_FraugId",
							"viewMoreDetailsPayIn_Fraug");
					enableButton('payin_fraugster_recheck');
					populateSuccessMessage("main-content__body",
							"Fraugster Repeat Check Successfully done",
							"fraugsterChecks_resend_error_field",
							"payin_fraugster_recheck");
					
					var accountTMFlag = $('#accountTMFlag').val();
					var prevFraugsterStatus = $('#fraugsterStatus').val();
					var currentFraugsterStatus = data.summary.status;
					if (prevFraugsterStatus.toUpperCase() != currentFraugsterStatus && currentFraugsterStatus != 'SERVICE_FAILURE' &&
						(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						
						updateFundsInIntuitionRepeatCheckStatus(user, baseUrl);
						
						var previousFraugsterStatus = document.getElementById("fraugsterStatus");
						previousFraugsterStatus.value = currentFraugsterStatus;
					}

				},
				error : function() {
					$('#gifloaderforfraugsterresend').css('visibility', 'hidden');
					enableButton('payin_fraugster_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending fraugsterCheck",
							"fraugsterChecks_resend_error_field",
							"payin_fraugster_recheck");
				}
			});
}


function setFraugsterResendResponse(activityLogs, eventServiceLogId,summary) {
	var custType = $('#account_clientType').text();
	if(custType == 'CFX') {
		var createdOn = getDateTimeFormat(getEmptyIfNull(activityLogs.activityLogData[0].createdOn));
		var updatedBy = getEmptyIfNull(activityLogs.activityLogData[0].createdBy);
		var fraugsterId = getEmptyIfNull(summary.frgTransId);
		var score = getEmptyIfNull(summary.score);
		var status = getEmptyIfNull(summary.status);
		var statusValue;
		if(status ==='PASS'){
			statusValue = true;
		}
		else{
			statusValue= false;
		}
			
		var row = '<tr href="#" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden" class="center">' + eventServiceLogId
				+ '</td>';
		row += '<td>' + createdOn + '</td>';
		row += '<td class="nowrap">' + updatedBy + '</td>';
		row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		row += '<td class="nowrap">Not Required</td>';
		row += '<td class="nowrap center"><i class="material-icons">-</i></td>';
		row += '</tr>'
		var totalrecords = $("#fraugsterTotalRecordsPayInId").val();
		totalrecords++;
		if (statusValue) {
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
		var updatedBy = getEmptyIfNull(activityLogs.activityLogData[0].createdBy);
		var fraugsterId = getEmptyIfNull(summary.frgTransId);
		var score = getEmptyIfNull(summary.score);
		var status = getEmptyIfNull(summary.status);
		var statusValue;
		if(status ==='PASS'){
			statusValue = true;
		}
		else{
			statusValue= false;
		}
			
		var row = '<tr href="#" onclick="showProviderResponse('
			+ eventServiceLogId + ',\'FRAUGSTER\',\'FraugsterChart\')">';
		row += '<td hidden="hidden" class="center">' + eventServiceLogId
				+ '</td>';
		row += '<td>' + createdOn + '</td>';
		row += '<td class="nowrap">' + updatedBy + '</td>';
		row += '<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId + ',\'FRAUGSTER\')">' + fraugsterId + '</a></td>';
		row += '<td class="nowrap" class="number"  >' + score + '</td>';
		row += getYesOrNoCell(statusValue);
		row += '</tr>'
		var totalrecords = $("#fraugsterTotalRecordsPayInId").val();
		totalrecords++;
		if (statusValue) {
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
	$('#payInDetails_fraugster_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Fraugster';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'paymentINDetails_fraugsterPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'paymentInDetails_fraugsterFail');
	}
	indicator += '</a>';
	$('#payInDetails_fraugster_indicatior').append(indicator);
}
function setFraugsterTotalRecords(totalRecordValue) {
	$("#fraugsterTotalRecordsPayInId").val(totalRecordValue);
}



function resendBlacklistCheck() {
	$('#gifloaderforblacklistresend').css('visibility', 'visible');
	var request = {};
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var custType = $('#account_clientType').text();
	
	addField('tradeAccountNumber', tradeAccountNumber, request);
	addField('paymentFundsInId', tradePaymentId, request);
	addField('org_code', orgCode, request);
	addField('cust_type', custType, request);
	postBlacklistCheckResend(request, getComplianceServiceBaseUrl(), getUser());
}

function postBlacklistCheckResend(request, baseUrl, user) {
	disableButton('payin_blacklist_recheck');
	
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/blacklistCheckfundsIn',
				headers : {
					"user" : user
				},
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					$('#gifloaderforblacklistresend').css('visibility', 'hidden');
					console.log(getJsonString(data));
					var addedRecords = Number($('#actLogTotalRecordsPayInId')
							.val())
							+ data.activityLogs.activityLogData.length;
					setActivityLogTotalRecords(addedRecords);
					var rows = '';
					rows +=setBlacklistResendResponse(data.summary);
					var id = 'blacklist_debitor';
					$("#" + id).empty();  
					$("#" + id).append(rows);           
					getActivities(1, 10, Number($('#paymentinId').val()), false);
					enableButton('payin_blacklist_recheck');
					populateSuccessMessage("main-content__body",
							"Blacklist Repeat Check Successfully done",
							"blacklistChecks_resend_error_field",
							"payin_blacklist_recheck");
					var accountTMFlag = $('#accountTMFlag').val();
					var prevBlacklistStatus = $('#blacklistStatus').val();
					var currentBlacklistStatus = data.summary.status;
					if (prevBlacklistStatus.toUpperCase() != currentBlacklistStatus && currentBlacklistStatus != 'SERVICE_FAILURE' &&
						(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						
						updateFundsInIntuitionRepeatCheckStatus(user, baseUrl);
						
						var previousBlacklistStatus = document.getElementById("blacklistStatus");
						previousBlacklistStatus.value = currentBlacklistStatus;
					}


				},
				error : function() {
					$('#gifloaderforblacklistresend').css('visibility', 'hidden');
					enableButton('payin_blacklist_recheck');
					populateErrorMessage("main-content__body",
							"Error while resending blacklistCheck",
							"blacklistChecks_resend_error_field",
							"payin_blacklist_recheck");
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
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+summary.bankNameMatchedData+ ')</td>';
	      failCount++;
	}
	
	row += getYesOrNoCell(statusValue);
	row += '</tr>';
	setBlacklistIndicator(passCount, failCount);
	return row;
}



function setBlacklistIndicator(passCount, failCount) {
	$('#debitor_blacklist_indicator').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist';
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'debitor_blacklist_negative');
	}
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'debitor_blacklist_positive');
	}
	indicator += '</a>';
	$('#debitor_blacklist_indicator').append(indicator);
}

$("#regDetails_wallet_list").click(function(){
	var walletRequest = {};
	
	var tradeAccountNumber = $("#account_tradeAccountNum").text();
	var orgCode = $("#account_organisation").text();
	
	addField('accountNumber',tradeAccountNumber,walletRequest);
	addField('orgCode',orgCode,walletRequest);
	
	getCustomerAllWalletList(walletRequest, getUser());

});

function getCustomerAllWalletList(walletRequest, user) {
	$.ajax({
		url : '/compliance-portal/getCustomerAllWalletList',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data : getJsonString(walletRequest),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '999'){
				$('#gifloaderforBlacklistresend').css('visibility','hidden');
				$("#regDetails_blacklist_recheck").attr("disabled", false);
				$("#regDetails_blacklist_recheck").removeClass("disabled");
				populateErrorMessage("main-content__body","Error while resending blacklist","blacklist_error_field","regDetails_blacklist_recheck");
			}else {
				$('#gifloaderforBlacklistresend').css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));////In Activity log 1 row inserted so increment by 1
				setBlacklistResendResponse(data.summary);
				getActivities(1,10,Number($('#contact_contactId').val()),false);
				$("#regDetails_blacklist_recheck").attr("disabled", false);
				$("#regDetails_blacklist_recheck").removeClass("disabled");
				populateSuccessMessage("main-content__body","Blacklist Repeat Check Successfully done","blacklist_error_field","regDetails_blacklist_recheck");
			}
		},
		error : function() {
			$('#gifloaderforBlacklistresend').css('visibility','hidden');
			$("#regDetails_blacklist_recheck").attr("disabled", false);
			$("#regDetails_blacklist_recheck").removeClass("disabled");
			populateErrorMessage("main-content__body","Error while resending blacklist","blacklist_error_field","regDetails_blacklist_recheck");
		}
	});
}

function getCustomerWalletTransactionDetails(){
	var walletTransactionRequest = {};
	
	var orgCode = $("#account_organisation").text();
	var tradeAccountNumber = $("#account_tradeAccountNum").text();
	var walletNumber = "C-000000033-EUR-TRAN";
	
	addField('orgCode',orgCode,walletTransactionRequest);
	addField('accountNumber',tradeAccountNumber,walletTransactionRequest);
	addField('walletNumber',walletNumber,walletTransactionRequest);
	
	postCustomerWalletTransactionDetails(walletTransactionRequest, getUser());
	
	
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
		var chart = AmCharts.makeChart( "paymentIn-fraugster-chart", {
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
//AT-3450
function setPoiExistsFlagForPaymentIn() {
    var request ={ };
    var contact_sf_id = ($('#contact_crmContactId').val());
	addField('contact_sf_id', contact_sf_id, request);
	var trade_contact_id = Number($('#contact_tradeContactId').val());
	addField('trade_contact_id', trade_contact_id, request);	
	$.ajax({
		url: '/compliance-portal/setPoiExistsFlagForPaymentIn',
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

function approvePoiForPaymentIn(){
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
			setPoiExistsFlagForPaymentIn();
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
$("#payInDetails_intuition_indicatior").click(function() {
	clicked = false;
	prevCountIntuition = 0;
	noOfRowsIntuition = 0;
	$("#payInDetails_intuition").find('tr').slice(1).remove();
	var noOfRows = countRows("payInDetails_intuition");
	var totalRecords = $('#intuitionTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, "leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords, "viewMore_IntuitionId", "viewMoreDetails_intuition");
});

function postIntuitionCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/getPaymentInViewMoreDetails',
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

		$("#payInDetails_intuition").append(row);
	}
	var noOfRows = countRows("payInDetails_intuition");
	var totalRecords = Number($('#intuitionTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords, "leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords, "viewMore_IntuitionId", "viewMoreDetails_intuition");
}