var searchCriteria;
var complianceServiceBaseUrl;
var attachDocBaseUrl;
var viewMoreRecordsize;
var noOfRowsFrg=0;
var noOfRowsSanction=0;
var noOfRowsEid=0;
var prevCountfrg=0;
var prevCountSanction=0;
var prevCountEID=0;
var clicked= false;
var noOfRowsIntuition=0; //AT-4114
var prevCountIntuition=0;
$(document).ready(function() {
	getComplianceServiceBaseUrl();
	getAttachDocBaseUrl();
	getViewMoreRecordsize();
	addContactStatusReasonsIntoData();
	addWatchlistIntoData();
});

function updateCFXContactSanction(contactId) {
	var fieldName = $(
			"input[name='regDetails_updateField-" + contactId + "']:checked")
			.val();
	var value = $(
			"input[name='regDetails_updateField_Value-" + contactId
					+ "']:checked").val();
	
	var sanctionRowForOfac = $("#regDetails_sanction-"+contactId+" tr:first");
	var previousOfacValue = sanctionRowForOfac.find("td:nth-child(5)").text();
	
	var sanctionRowForWC = $("#regDetails_sanction-"+contactId+" tr:first");
	var previousWorldCheckValue = sanctionRowForWC.find("td:nth-child(6)").text();
	
	if (value === undefined || value === null || fieldName === undefined
			|| fieldName === null) {
		alert("Please select required fields for sanction update");
	} else if( (previousOfacValue === value && fieldName === 'ofaclist') ||
					(previousWorldCheckValue === value && fieldName === 'worldcheck')) {
		alert("Please change "+fieldName+" value for Contact and then update sanction");
	} else {
		$('#gifloaderforupdatesanctionContactcfx-' + contactId ).css('visibility', 'visible');
		var request = {};
		var accountId = Number($('#contact_accountId').val());
		var orgCode = $('#account_organisation').val();
		var eventId = Number($("#regDetails_sanction-" + contactId).find(
				'tr:first').find('td:first').text());
		addField("accountId", accountId, request);
		addField('resourceId', contactId, request);
		addField('resourceType', 'PROFILE', request);
		addField("orgCode", orgCode, request);
		
		if(eventId === "" || eventId === null || eventId === undefined || eventId === 0){
			$('#gifloaderforupdatesanctionContactcfx-' + contactId).css('visibility', 'hidden');
			return	populateErrorMessage("main-content__body",
					"Perform repeat check to update "
							+ getTextById("contact-name-" + contactId),
					"update_sanction_error_field-" + contactId,
					'regDetails_updateSanction-' + contactId);
	}

		var sanctions = [];
		addField("sanctions", sanctions, request);
		sanctions.push({
			"eventServiceLogId" : eventId,
			"field" : fieldName,
			"value" : value,
			"entityId" : contactId,
			"entityType" : "CONTACT"
		});
		postUpdateCFXContactSanction(request, getComplianceServiceBaseUrl(),
				getUser(), contactId);
	}
}

function updateCFXAccountSanction(accountId) {
	var fieldName = $(
			"input[name='regDetails_updateField--" + accountId + "']:checked")
			.val();
	var value = $(
			"input[name='regDetails_updateField_Value--" + accountId
					+ "']:checked").val();
	
	var sanctionRowForOfac = $("#regDetails_sanction--"+accountId+" tr:first");
	var previousOfacValue = sanctionRowForOfac.find("td:nth-child(5)").text();
	
	var sanctionRowForWC = $("#regDetails_sanction--"+accountId+" tr:first");
	var previousWorldCheckValue = sanctionRowForWC.find("td:nth-child(6)").text();
	
	if (value === undefined || value === null || fieldName === undefined
			|| fieldName === null) {
		alert("Please select required fields for sanction update");
	} else if( (previousOfacValue === value && fieldName === 'ofaclist') ||
					(previousWorldCheckValue === value && fieldName === 'worldcheck')) {
		alert("Please change "+fieldName+" value for Account and then update sanction");
	} else {
		$('#gifloaderforupdatesanctionaccountcfx').css('visibility', 'visible');
		var request = {};
		var orgCode = $('#account_organisation').val();
		var eventId = Number($("#regDetails_sanction--" + accountId).find(
				'tr:first').find('td:first').text());
		addField("accountId", accountId, request);
		addField('resourceId', accountId, request);
		addField('resourceType', 'PROFILE', request);
		addField("orgCode", orgCode, request);
       
		if(eventId === "" || eventId === null || eventId === undefined || eventId === 0){
			$('#gifloaderforupdatesanctionaccountcfx').css('visibility', 'hidden');
		return 	populateErrorMessage("main-content__body","Perform repeat check to update "+ getTextById("account-name"),
					"update_sanction_account_error_field-" + accountId,'regDetails_updateSanction--' + accountId);
	}
		var sanctions = [];
		addField("sanctions", sanctions, request);
		sanctions.push({
			"eventServiceLogId" : eventId,
			"field" : fieldName,
			"value" : value,
			"entityId" : accountId,
			"entityType" : "ACCOUNT"
		});
		postUpdateCFXAccountSanction(request, getComplianceServiceBaseUrl(),
				getUser(), accountId);
	}
}

function resendCFXContactSanction(contactId) {
	$('#gifloaderforresendsanctionCFX-' + contactId).css('visibility', 'visible');
	var sanction = {};
	var accountId = Number($('#contact_accountId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId', accountId, sanction);
	addField('entityId', contactId, sanction);
	addField('entityType', 'CONTACT', sanction);
	addField('orgCode', orgCode, sanction);
	postCFXContactSanctionResend(sanction, getComplianceServiceBaseUrl(),
			getUser(), contactId);
}

function resendCFXAccountSanction(accountID) {
	$('#gifloaderforresendsanctionCFXAccount').css('visibility', 'visible');
	var sanction = {};
	var accountId = Number($('#contact_accountId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId', accountId, sanction);
	addField('entityId', accountId, sanction);
	addField('entityType', 'ACCOUNT', sanction);
	addField('orgCode', orgCode, sanction);
	postCFXAccountSanctionResend(sanction, getComplianceServiceBaseUrl(),
			getUser(), accountID);
}

function resendCFXContactKyc(contactId) {
	$('#gifloaderforresendKYCContact-' + contactId).css('visibility', 'visible');
	var kyc = {};
	var accountId = Number($('#contact_accountId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId', accountId, kyc);
	addField('contactId', contactId, kyc);
	addField('orgCode', orgCode, kyc);
	postCFXContactKycResend(kyc, getComplianceServiceBaseUrl(), getUser(),
			contactId);
}

function postCFXContactSanctionResend(request, baseUrl, user, contactId) {
	disableButton('regDetails_updateSanction-' + contactId);
	disableButton('regDetails_sanction_recheck-' + contactId);
	$.ajax({
		url : baseUrl
				+ '/compliance-service/services-internal/repeatcheck/sanction',
		type : 'POST',
		headers : {
			"user" : user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforresendsanctionCFX-' + contactId).css('visibility', 'hidden');
				enableButton('regDetails_updateSanction-' + contactId);
				enableButton('regDetails_sanction_recheck-' + contactId);
				populateErrorMessage("main-content__body", "Error while resending sanction for contact " + getTextById("contact-name-" + contactId),
						"resend_sanction_error_field-" + contactId, 'regDetails_sanction_recheck-' + contactId);
			}else{
				$('#gifloaderforresendsanctionCFX-' + contactId).css('visibility', 'hidden');
				enableButton('regDetails_updateSanction-' + contactId);
				enableButton('regDetails_sanction_recheck-' + contactId);

				var accountTMFlag = $('#accountTMFlag').val();
				var prevSanctionStatus = $('#contactSanctionStatus-'+contactId).val();
				var currentSanctionStatus = data.summary.status;

				var addedRecords = Number($('#actLogTotalRecordsId').val())
						+ data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));// In Activity log 1 row  inserted  so  increment  by 1
				if (data.summary.status === 'PASS') {
					data.summary.status = true;
				} else {
					data.summary.status = false;
				}
				data.summary.updatedBy = getJsonObject(getUser()).name;
				setCFXContactSanctionResendResponse(data.summary, contactId);
				getCFXActivities(1, 10, Number($('#contact_accountId').val()), false);
				populateSuccessMessage("main-content__body", "Sanction Repeat Check Successfully done for contact "
								+ getTextById("contact-name-" + contactId), "resend_sanction_error_field-" + contactId, 'regDetails_sanction_recheck-' + contactId);
				if (prevSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
					var sanctionRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').val();
					var checkType = 'sanction';
					var regDateTime = $('#account_registrationInDate').val();
					var custType = $('#customerType').val();
					addField('accountId', accountId, sanctionRequest);
					addField('contactId', contactId, sanctionRequest);
					addField('orgCode', orgCode, sanctionRequest);
					addField('registrationDateTime', regDateTime, sanctionRequest);
					addField('sanctionStatus', currentSanctionStatus, sanctionRequest);
					addField('tradeAccountNumber', tradeAccountNumber, sanctionRequest);
					addField('checkType', checkType, sanctionRequest);
					addField('custType', custType, sanctionRequest);
					updateIntuitionRepeatCheckStatus(sanctionRequest, getUser(), getComplianceServiceBaseUrl());
					var prevSanctionStatus = document.getElementById("contactSanctionStatus-"+contactId);
					prevSanctionStatus.value = currentSanctionStatus;
				}
			}
		},
		error : function() {
			$('#gifloaderforresendsanctionCFX-' + contactId).css('visibility', 'hidden');
			enableButton('regDetails_updateSanction-' + contactId);
			enableButton('regDetails_sanction_recheck-' + contactId);
			alert('Error while resending Sanction');
			populateErrorMessage("main-content__body", "Error while resending sanction for contact " + getTextById("contact-name-" + contactId),
					"resend_sanction_error_field-" + contactId, 'regDetails_sanction_recheck-' + contactId);
		}
	});
}

function postCFXAccountSanctionResend(request, baseUrl, user, accountID) {
	disableButton('regDetails_updateSanction--' + accountID);
	disableButton('regDetails_sanction_recheck--' + accountID);
	$.ajax({
		url : baseUrl
				+ '/compliance-service/services-internal/repeatcheck/sanction',
		type : 'POST',
		headers : {
			"user" : user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforresendsanctionCFXAccount').css('visibility', 'hidden');
				enableButton('regDetails_updateSanction--' + accountID);
				enableButton('regDetails_sanction_recheck--' + accountID);
				populateErrorMessage("main-content__body", "Error while resending sanction for account " + getTextById("account-name"),
				"resend_sanction_account_error_field-" + accountID, 'regDetails_sanction_recheck--' + accountID);
			} else {
				$('#gifloaderforresendsanctionCFXAccount').css('visibility',
						'hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val())
						+ data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));// In Activity log 1 row  inserted so  increment  by 1
				
				var accountTMFlag = $('#accountTMFlag').val();
				var prevSanctionStatus = $('#accountSanctionStatus').val();
				var currentSanctionStatus = data.summary.status;
				
				if (data.summary.status === 'PASS') {
					data.summary.status = true;
				} else {
					data.summary.status = false;
				}
				data.summary.updatedBy = getJsonObject(getUser()).name;
				setCFXAccountSanctionResendResponse(data.summary, accountID);
				getCFXActivities(1, 10, Number($('#contact_accountId').val()), false);
				enableButton('regDetails_updateSanction--' + accountID);
				enableButton('regDetails_sanction_recheck--' + accountID);
				
				if(prevSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var sanctionRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').val();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'cfxSanction';
					var custType = $('#customerType').val();
					addField('accountId', accountId, sanctionRequest);
					addField('contactId', contactId, sanctionRequest);
					addField('orgCode', orgCode, sanctionRequest);
					addField('registrationDateTime',regDateTime,sanctionRequest);
					addField('accountSanctionStatus',currentSanctionStatus,sanctionRequest);
					addField('tradeAccountNumber',tradeAccountNumber,sanctionRequest);
					addField('checkType',checkType,sanctionRequest);
					addField('custType',custType,sanctionRequest);
					updateIntuitionRepeatCheckStatus(sanctionRequest, getUser(), getComplianceServiceBaseUrl());
					var prevSanctionStatus = document.getElementById("accountSanctionStatus");
					prevSanctionStatus.value = currentSanctionStatus;
				}
				
				populateSuccessMessage("main-content__body", "Sanction Repeat Check Successfully done for account " + getTextById("account-name"),
						"resend_sanction_account_error_field-" + accountID, 'regDetails_sanction_recheck--' + accountID);
			}
		},
		error : function() {
			$('#gifloaderforresendsanctionCFXAccount').css('visibility', 'hidden');
			enableButton('regDetails_updateSanction--' + accountID);
			enableButton('regDetails_sanction_recheck--' + accountID);
			populateErrorMessage("main-content__body", "Error while resending sanction for account " + getTextById("account-name"),
					"resend_sanction_account_error_field-" + accountID, 'regDetails_sanction_recheck--' + accountID);

		}
	});
}

function postUpdateCFXContactSanction(request, baseUrl, user, contactId) {
	disableButton('regDetails_updateSanction-' + contactId);
	disableButton('regDetails_sanction_recheck-' + contactId);
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
						$('#gifloaderforupdatesanctionContactcfx-' + contactId ).css('visibility', 'hidden');
						enableButton('regDetails_updateSanction-' + contactId);
						enableButton('regDetails_sanction_recheck-' + contactId);
						populateErrorMessage("main-content__body",
								"Error while updating sanction for contact "
										+ getTextById("contact-name-" + contactId),
								"update_sanction_error_field-" + contactId,
								'regDetails_updateSanction-' + contactId);
					}else  {
						$('#gifloaderforupdatesanctionContactcfx-' + contactId ).css('visibility', 'hidden');
						var addedRecords = Number($('#actLogTotalRecordsId').val())
								+ data.activityLogs.activityLogData.length;
						Number($('#actLogTotalRecordsId').val(addedRecords));
						getCFXActivities(1, 10, Number($('#contact_accountId')
								.val()), false);
								
						var accountTMFlag = $('#accountTMFlag').val();
						var previousSanctionStatus = $('#contactSanctionStatus-' + contactId).val();
						var currentSanctionStatus = data.status;		
						
						updateCFXContactSanctionColumn(data, contactId);
						enableButton('regDetails_updateSanction-' + contactId);
						enableButton('regDetails_sanction_recheck-' + contactId);
						populateSuccessMessage("main-content__body",
								"Sanction upating successfully done for contact "
										+ getTextById("contact-name-" + contactId),
								"update_sanction_error_field-" + contactId,
								'regDetails_updateSanction-' + contactId);
								
						if (previousSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
							var sanctionRequest = {};
							var accountId = Number($('#contact_accountId').val());
							var tradeAccountNumber = $('#account_tradeAccountNum').text();
							var orgCode = $('#account_organisation').val();
							var regDateTime = $('#account_registrationInDate').val();
							var checkType = 'sanction';
							var custType = $('#customerType').val();
							addField('accountId', accountId, sanctionRequest);
							addField('contactId', contactId, sanctionRequest);
							addField('orgCode', orgCode, sanctionRequest);
							addField('registrationDateTime', regDateTime, sanctionRequest);
							addField('sanctionStatus', currentSanctionStatus, sanctionRequest);
							addField('tradeAccountNumber', tradeAccountNumber, sanctionRequest);
							addField('checkType', checkType, sanctionRequest);
							addField('custType', custType, sanctionRequest);
							updateIntuitionRepeatCheckStatus(sanctionRequest, getUser(), getComplianceServiceBaseUrl());
							var prevBlacklistStatus = document.getElementById("contactSanctionStatus-" + contactId);
							prevBlacklistStatus.value = currentSanctionStatus;
						}
					}
				},
				error : function() {
					$('#gifloaderforupdatesanctionContactcfx-' + contactId ).css('visibility', 'hidden');
					enableButton('regDetails_updateSanction-' + contactId);
					enableButton('regDetails_sanction_recheck-' + contactId);
					populateErrorMessage("main-content__body",
							"Error while updating sanction for contact "
									+ getTextById("contact-name-" + contactId),
							"update_sanction_error_field-" + contactId,
							'regDetails_updateSanction-' + contactId);

				}
			});
}

function postUpdateCFXAccountSanction(request, baseUrl, user, acountId) {
	disableButton('regDetails_updateSanction--' + acountId);
	disableButton('regDetails_sanction_recheck--' + acountId);
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
					if (data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '799'){
						$('#gifloaderforupdatesanctionaccountcfx').css('visibility', 'hidden');
						enableButton('regDetails_updateSanction--' + acountId);
						enableButton('regDetails_sanction_recheck--' + acountId);
						populateErrorMessage("main-content__body","Error while updating sanction done for account "+ getTextById("account-name"),
								"update_sanction_account_error_field-" + acountId,'regDetails_updateSanction--' + acountId);
					}else  {
						$('#gifloaderforupdatesanctionaccountcfx').css('visibility', 'hidden');
						var addedRecords = Number($('#actLogTotalRecordsId').val())+ data.activityLogs.activityLogData.length;
						Number($('#actLogTotalRecordsId').val(addedRecords));
						getCFXActivities(1, 10, Number($('#contact_accountId')
								.val()), false);
								
						var accountTMFlag = $('#accountTMFlag').val();
						var prevSanctionStatus = $('#accountSanctionStatus').val();
						var currentSanctionStatus = data.status;

						updateCFXAccountSanctionColumn(data, acountId);
						enableButton('regDetails_updateSanction--' + acountId);
						enableButton('regDetails_sanction_recheck--' + acountId);
						populateSuccessMessage("main-content__body",
								"Sanction Repeat Check Successfully done for account "
										+ getTextById("account-name"),
								"update_sanction_account_error_field-" + acountId,
								'regDetails_updateSanction--' + acountId);
					
					if (prevSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						var sanctionRequest = {};
						var accountId = Number($('#contact_accountId').val());
						var contactId = Number($('#contact_contactId').val());
						var tradeAccountNumber = $('#account_tradeAccountNum').text();
						var orgCode = $('#account_organisation').val();
						var checkType = 'cfxSanction';
						var regDateTime = $('#account_registrationInDate').val();
						var custType = $('#customerType').val();
						addField('accountId', accountId, sanctionRequest);
						addField('contactId', contactId, sanctionRequest);
						addField('orgCode', orgCode, sanctionRequest);
						addField('registrationDateTime', regDateTime, sanctionRequest);
						addField('accountSanctionStatus', currentSanctionStatus, sanctionRequest);
						addField('tradeAccountNumber', tradeAccountNumber, sanctionRequest);
						addField('checkType', checkType, sanctionRequest);
						addField('custType', custType, sanctionRequest);
						updateIntuitionRepeatCheckStatus(sanctionRequest, getUser(), getComplianceServiceBaseUrl());
						var prevSanctionStatus = document.getElementById("accountSanctionStatus");
						prevSanctionStatus.value = currentSanctionStatus;
					}
					}
				},
				error : function() {
					$('#gifloaderforupdatesanctionaccountcfx').css(
							'visibility', 'hidden');
					enableButton('regDetails_updateSanction--' + acountId);
					enableButton('regDetails_sanction_recheck--' + acountId);
					populateErrorMessage("main-content__body",
							"Error while updating sanction done for account "
									+ getTextById("account-name"),
							"update_sanction_account_error_field-" + acountId,
							'regDetails_updateSanction--' + acountId);

				}
			});
}

function postCFXContactKycResend(request, baseUrl, user, contactId) {

	disableButton('regDetails_kyc_recheck-' + contactId);
	$
			.ajax({
				url : baseUrl
						+ '/compliance-service/services-internal/repeatcheck/kyc',
				type : 'POST',
				headers : {
					"user" : user
				},
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '999'){
						$('#gifloaderforresendKYCContact-' + contactId).css('visibility', 'hidden');
						enableButton('regDetails_kyc_recheck-' + contactId);
						populateErrorMessage("main-content__body", "Error while resending kyc for contact " + getTextById("contact-name-" + contactId),
								"kyc_error_field-" + contactId, 'regDetails_kyc_recheck-' + contactId);
					}else {
						$('#gifloaderforresendKYCContact-' + contactId).css('visibility', 'hidden');
						enableButton('regDetails_kyc_recheck-' + contactId);
						
						var accountTMFlag = $('#accountTMFlag').val();
						var previousKycStatus = $('#contactKycStatus-' + contactId).val();
						var currentKycStatus = data.summary.status;
						
						var addedRecords = Number($('#actLogTotalRecordsId').val())
								+ data.activityLogs.activityLogData.length;
						Number($('#actLogTotalRecordsId').val(addedRecords)); //In  Activity  log  1  row  inserted  so  increment  by 1
						setCFXContactKycResendResponse(data.summary, contactId);
						getCFXActivities(1, 10, Number($('#contact_accountId').val()), false);
						enableButton('regDetails_kyc_recheck-' + contactId);
						populateSuccessMessage("main-content__body", "Kyc Repeat Check Successfully done for contact "
										+ getTextById("contact-name-" + contactId), "kyc_error_field", 'regDetails_kyc_recheck-' + contactId);
						
						if (previousKycStatus != currentKycStatus && currentKycStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
							var kycRequest = {};
							var accountId = Number($('#contact_accountId').val());
							var tradeAccountNumber = $('#account_tradeAccountNum').text();
							var orgCode = $('#account_organisation').val();
							var regDateTime = $('#account_registrationInDate').val();
							var checkType = 'kyc';
							var custType = $('#customerType').val();
							addField('accountId', accountId, kycRequest);
							addField('contactId', contactId, kycRequest);
							addField('orgCode', orgCode, kycRequest);
							addField('registrationDateTime', regDateTime, kycRequest);
							addField('kycStatus', currentKycStatus, kycRequest);
							addField('tradeAccountNumber', tradeAccountNumber, kycRequest);
							addField('checkType', checkType, kycRequest);
							addField('custType', custType, kycRequest);
							updateIntuitionRepeatCheckStatus(kycRequest, getUser(), getComplianceServiceBaseUrl());
							var prevBlacklistStatus = document.getElementById("contactKycStatus-" + contactId);
							prevBlacklistStatus.value = currentKycStatus;
						}
					}
				},
				error : function() {
					$('#gifloaderforresendKYCContact-' + contactId).css('visibility', 'hidden');
					enableButton('regDetails_kyc_recheck-' + contactId);
					populateErrorMessage("main-content__body", "Error while resending kyc for contact " + getTextById("contact-name-" + contactId),
							"kyc_error_field-" + contactId, 'regDetails_kyc_recheck-' + contactId);
				}
			});
}

function setCFXContactSanctionResendResponse(sanction, contactId) {
	var updatedOn = getDateTimeFormat(getEmptyIfNull(sanction.updatedOn));
	var updatedBy = getEmptyIfNull(sanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.worldCheck);
	var status = getEmptyIfNull(sanction.status);
	var statusValue = getEmptyIfNull(sanction.status);
	var notRequired = "Not Required";
	var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
	var row = '<tr>';
	row += '<td hidden="hidden" class="center">' + sanction.eventServiceLogId
			+ '</td>';
	// Removed class="nowrap" from 'checkedOn' to set columns of table properly
	// on UI
	row += '<td>' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	// Removed class="nowrap" from 'sanction ID' to set columns of table
	// properly on UI
	row += '<td><a href="javascript:void(0);" onclick="showCFXProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	if (statusValue === "Not Required")
		row += '<td id="sanction_status" class="nowrap">' + notRequired + '</td>';
	else
		row += getYesOrNoCellWithId(status,'sanction_status');
	row += '</tr>'
	var totalRecords = Number($('#sanctionTotalRecordsId-' + contactId).val()) + 1;
	if (sanction.passCount === undefined || sanction.passCount === null) {
		if (status) {
			sanction.passCount = totalRecords;
		} else {
			sanction.failCount = totalRecords;
		}
	}

	var sanctionPassCount = sanction.passCount;
	var sanctionFailCount = sanction.failCount;
	setCFXContactSanctionIndicator(sanctionPassCount, sanctionFailCount,
			contactId);

	$('#regDetails_sanction-' + contactId).prepend(row);
	var noOfRows = countCFXRows("regDetails_sanction-" + contactId);
	var totalRecords = Number($('#sanctionTotalRecordsId-' + contactId).val()) + 1;
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdSanc-" + contactId);
	updateCFXViewMoreBlock(leftRecords, "viewMore_SancId-" + contactId,
			"viewMoreDetails_sanc-" + contactId);
	Number($('#sanctionTotalRecordsId-' + contactId).val(totalRecords));
}

function setCFXAccountSanctionResendResponse(sanction, accountID) {
	var updatedOn = getDateTimeFormat(getEmptyIfNull(sanction.updatedOn));
	var updatedBy = getEmptyIfNull(sanction.updatedBy);
	var sanctionId = getEmptyIfNull(sanction.sanctionId);
	var ofacList = getEmptyIfNull(sanction.ofacList);
	var worldCheck = getEmptyIfNull(sanction.worldCheck);
	var status = getEmptyIfNull(sanction.status);
	var notRequired = "Not Required";
	var statusValue = getEmptyIfNull(sanction.statusValue);
	var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
	var row = '<tr>';
	row += '<td hidden="hidden" class="center">' + sanction.eventServiceLogId
			+ '</td>';
	// Removed class="nowrap" from 'checkedOn' to set columns of table properly
	// on UI
	row += '<td>' + updatedOn + '</td>';
	row += '<td class="nowrap">' + updatedBy + '</td>';
	// Removed class="nowrap" from 'sanction ID' to set columns of table
	// properly on UI
	row += '<td><a href="javascript:void(0);" onclick="showCFXProviderResponse('
			+ eventServiceLogId + ',\'SANCTION\')">' + sanctionId + '</a></td>';
	row += '<td class="nowrap">' + ofacList + '</td>';
	row += '<td class="nowrap">' + worldCheck + '</td>';
	if (statusValue === "Not Required")
		row += '<td id="sanction_status" class="nowrap">' + notRequired + '</td>';
	else
		row +=  getYesOrNoCellWithId(status,'sanction_status');
	row += '</tr>'
	var totalRecords = Number($('#sanctionTotalRecordsId--' + accountID).val()) + 1;
	if (sanction.passCount === undefined || sanction.passCount === null) {
		if (status) {
			sanction.passCount = totalRecords;
		} else {
			sanction.failCount = totalRecords;
		}
	}

	var sanctionPassCount = sanction.passCount;
	var sanctionFailCount = sanction.failCount;
	setCFXAccountSanctionIndicator(sanctionPassCount, sanctionFailCount,
			accountID);

	$('#regDetails_sanction--' + accountID).prepend(row);
	var noOfRows = countCFXRows("regDetails_sanction--" + accountID);
	var totalRecords = Number($('#sanctionTotalRecordsId--' + accountID).val()) + 1;
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdSanc--" + accountID);
	updateCFXViewMoreBlock(leftRecords, "viewMore_SancId--" + accountID,
			"viewMoreDetails_sanc--" + accountID);
	Number($('#sanctionTotalRecordsId--' + accountID).val(totalRecords));
}

function updateCFXContactSanctionColumn(data, contactId) {
	var fieldName = $(
			"input[name='regDetails_updateField-" + contactId + "']:checked")
			.val();
	var value = $(
			"input[name='regDetails_updateField_Value-" + contactId
					+ "']:checked").val();
	if (fieldName.toLowerCase() === "ofaclist") {
		$(
				'#regDetails_sanction-' + contactId
						+ ' tr:nth-child(1) td:nth-child(5)').text(value);
	}
	if (fieldName.toLowerCase() === "worldcheck") {
		$(
				'#regDetails_sanction-' + contactId
						+ ' tr:nth-child(1) td:nth-child(6)').text(value);
	}
	if (data.status === 'PASS') {
		$(
				'#regDetails_sanction-' + contactId
						+ ' tr:nth-child(1) td:nth-child(7)').removeClass(
				'no-cell').addClass('yes-cell').html(
				'<i class="material-icons">check</i>');
		var totalRecords = Number($('#sanctionTotalRecordsId-' + contactId)
				.val());
		setCFXContactSanctionIndicator(totalRecords, 0, contactId);
	} else {
		$(
				'#regDetails_sanction-' + contactId
						+ ' tr:nth-child(1) td:nth-child(7)').removeClass(
				'yes-cell').addClass('no-cell').html(
				'<i class="material-icons">clear</i>');
		var totalRecords = Number($('#sanctionTotalRecordsId-' + contactId)
				.val());
		setCFXContactSanctionIndicator(0, totalRecords, contactId);
	}

}

function updateCFXAccountSanctionColumn(data, accountId) {
	var fieldName = $(
			"input[name='regDetails_updateField--" + accountId + "']:checked")
			.val();
	var value = $(
			"input[name='regDetails_updateField_Value--" + accountId
					+ "']:checked").val();
	if (fieldName.toLowerCase() === "ofaclist") {
		$(
				'#regDetails_sanction--' + accountId
						+ ' tr:nth-child(1) td:nth-child(5)').text(value);
	}
	if (fieldName.toLowerCase() === "worldcheck") {
		$(
				'#regDetails_sanction--' + accountId
						+ ' tr:nth-child(1) td:nth-child(6)').text(value);
	}
	if (data.status === 'PASS') {
		$(
				'#regDetails_sanction--' + accountId
						+ ' tr:nth-child(1) td:nth-child(7)').removeClass(
				'no-cell').addClass('yes-cell').html(
				'<i class="material-icons">check</i>');
		var totalRecords = Number($('#sanctionTotalRecordsId--' + accountId)
				.val());
		setCFXAccountSanctionIndicator(totalRecords, 0, accountId);
	} else {
		$(
				'#regDetails_sanction--' + accountId
						+ ' tr:nth-child(1) td:nth-child(7)').removeClass(
				'yes-cell').addClass('no-cell').html(
				'<i class="material-icons">clear</i>');
		var totalRecords = Number($('#sanctionTotalRecordsId-' + accountId)
				.val());
		setCFXAccountSanctionIndicator(0, totalRecords, accountId);
	}

}

function setCFXContactKycResendResponse(kyc, contactId) {
	var checkedOn = getDateTimeFormat(getEmptyIfNull(kyc.checkedOn));
	var eidCheck = getEmptyIfNull(kyc.eidCheck);
	var verifiactionResult = getEmptyIfNull(kyc.verifiactionResult);
	var referenceId = getEmptyIfNull(kyc.referenceId);
	var status = getEmptyIfNull(kyc.status);
	var statusValue = getEmptyIfNull(kyc.status);
	var notRequired = "Not Required";
	status = ('PASS' === status) ? true : false;
	var dob = getDashIfNull(kyc.dob);
	var eventServiceLogId = getEmptyIfNull(kyc.id);
	var row = '<tr>';
	// Removed class="nowrap" from 'checkedOn' to set columns of table properly
	// on UI
	row += '<td>' + checkedOn + '</td>';
	if (statusValue === "Not Required")
		row += '<td class="nowrap">' + notRequired + '</td>';
	else
		row += getYesOrNoCell(eidCheck);
	row += '<td class="number center">' + verifiactionResult + '</td>';
	row += '<td><a href="javascript:void(0);" onclick="showCFXProviderResponse('
			+ eventServiceLogId + ',\'KYC\')">' + referenceId + '</a></td>';
	row += '<td class="nowrap">' + dob + '</td>';

	if (statusValue === "Not Required")
		row += '<td class="nowrap">' + notRequired + '</td>';
	else
		row += getYesOrNoCell(status);
	row += '</tr>'
	$('#regDetails_eid-' + contactId).prepend(row);
	var kycPassCount = 0;
	var kycFailCount = 0;

	var totalRecords = Number($('#kycTotalRecordsId-' + contactId).val()) + 1;

	if (status) {
		kycPassCount = totalRecords;
	} else {
		kycFailCount = totalRecords;
	}
	setCFXContactKycIndicator(kycPassCount, kycFailCount, contactId)
	var noOfRows = countCFXRows("regDetails_eid-" + contactId);
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdKyc-" + contactId);
	updateCFXViewMoreBlock(leftRecords, "viewMore_KycId-" + contactId,
			"viewMoreDetails_kyc-" + contactId);
	Number($('#kycTotalRecordsId-' + contactId).val(totalRecords));
}

function setCFXContactSanctionIndicator(passCount, failCount, contactId) {
	$('#regDetails_sanction_indicatior-' + contactId).empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount, 'regDetails_sanctionPass-'
				+ contactId);
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount, 'regDetails_sanctionNeg-'
				+ contactId);
	}
	indicator += '</a>';
	$('#regDetails_sanction_indicatior-' + contactId).append(indicator);
}

function setCFXAccountSanctionIndicator(passCount, failCount, accountId) {
	$('#regDetails_sanction_indicatior--' + accountId).empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'regDetails_sanctionPass--' + accountId);
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount, 'regDetails_sanctionNeg--'
				+ accountId);
	}
	indicator += '</a>';
	$('#regDetails_sanction_indicatior--' + accountId).append(indicator);
}

function setCFXContactKycIndicator(passCount, failCount, contactId) {
	$('#regDetails_kyc_indicatior-' + contactId).empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>EID';
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount, 'regDetails_kycPass');
	}
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount, 'regDetails_kycNeg');
	}
	indicator += '</a>';
	$('#regDetails_kyc_indicatior-' + contactId).append(indicator);
}

function executeCFXActions(isAutoUnlock) {

	var preContactStatus = $("#regDetails_contactStatusReasons").data(
			'preConStatus');
	var updatedContactStatus = $(
			"input[name='regDetails_contactStatus_radio']:checked").val();
	var comment = $('#regDetails_comments').val();
	var complianceLog = $('#regDetails_compliance_log').val();
	var isOnQueue = $('#account_isOnQueue').val();
	
	if((isNull(comment) || isEmpty(comment)) && (isNull(complianceLog) || isEmpty(complianceLog) ) 
			&& (!isNull(updatedContactStatus) && !isEmpty(updatedContactStatus) && updatedContactStatus != 'ACTIVE') ) {
		alert("Please add comment or compliance log");
		} else if (preContactStatus !== updatedContactStatus
			&& updatedContactStatus !== 'ACTIVE'
			&& $("input[name='regDetails_contactStatusReson[]']:checked").length === 0) {
		alert("Please select atleast one reason for " + updatedContactStatus);
	} else {
		 var tbodies = $(".checkSanctions");
		 var condition = false;
		$.each(tbodies, function(index, tbody) {
			var sanContactStatus = $(tbody).find("tr:first").find('#sanction_status').text();
			 if(updatedContactStatus==='ACTIVE' && !condition && (
						sanContactStatus == null || sanContactStatus.indexOf("Not Required") !== -1 || sanContactStatus.indexOf("clear") !== -1)){
				 condition = true;
					}
		});
		if(condition){
			alert("Account cannot be active since last sanction contact check is fail ");
		} else {
			$('#gifloaderforprofileupdateCFX').css('visibility', 'visible');
			var request = {};
			var accountId = Number($('#contact_accountId').val());
			var contactId = Number($('#contact_contactId').val());
			var accountSfId = $('#contact_crmAccountId').val();
			var custType = $('#customerType').val();
			var contactSfId = $('#current_contact_crmContactId').val();
			var orgCode = $('#account_organisation').val();
			var complianceDoneOn = $('#account_complianceDoneOn').val();
			var registrationInDate = $('#account_registrationInDate').val();
			var complianceExpiry = $('#account_complianceExpiry').val();
			var userResourceId = $('#userResourceId').val();
			var accountVersion = $('#accountVersion').val();
			var tradeAccountNumber = $('#account_tradeAccountNum').text();
			addField('accountId', accountId, request);
			addField('contactId', contactId, request);
			addField('accountSfId', accountSfId, request);
			addField('contactSfId', contactSfId, request);
			addField('orgCode', orgCode, request);
			addField("contactStatusReasons", getContactReasons(), request);
			addField("watchlist", getWatchlists(), request);
			addField('overallWatchlistStatus', findInWatchlists(), request);
			addField("preContactStatus", preContactStatus, request);
			/**addField("updatedContactStatus", updatedContactStatus, request);*/
			addField("custType", custType, request);
			addField("updatedAccountStatus", updatedContactStatus, request);
			addField("preAccountStatus", preContactStatus, request);
			addField('complianceDoneOn', complianceDoneOn, request);
			addField('registrationInDate', registrationInDate, request);
			addField('complianceExpiry', complianceExpiry, request);
			$("#regDetails_contactStatusReasons").data('preConStatus',
					updatedContactStatus);
			addField("comment", comment, request);
			addField("complianceLog",complianceLog,request);
			addField("isOnQueue",isOnQueue,request);
			addField("userResourceId",userResourceId,request);
			addField("tradeAccountNumber",tradeAccountNumber,request);
			addField("accountVersion",accountVersion,request);
			postCFXProfileUpdate(request,isAutoUnlock);
		}
	}
}
function postCFXProfileUpdate(request,isAutoUnlock) {
	disableButton('regDetails_profile_update');
	var accountTMFlag = $('#accountTMFlag').val();

	$.ajax({
				url : '/compliance-portal/profileUpdate',
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
						
					$('#gifloaderforprofileupdateCFX').css('visibility',
							'hidden');
				    $('#account_complianceDoneOn').val(data.complianceDoneOn);
				    $('#account_registrationInDate').val(data.registrationInDate);
				    $('#account_complianceExpiry').val(data.complianceExpiry);
					getAccountStatus(request.updatedAccountStatus);
					setDataAnonService();
					
					var addedRecords = Number($('#actLogTotalRecordsId').val())
							+ data.activityLogData.length;
					Number($('#actLogTotalRecordsId').val(addedRecords));// In  Activity  log  1 or  2  row  inserted  so  increment  by 1  or 2
					getCFXActivities(1, 10, Number($('#contact_accountId')
							.val()), false);
					$("#account_compliacneStatus").text(
							$("#regDetails_contactStatusReasons").data(
									'preConStatus'));
					enableButton('regDetails_profile_update');
					if (request.updatedContactStatus === 'ACTIVE') {
						decreamentTotalRecords(1);
					}
					$('#regDetails_comments').val('');
					
					var complianceLog = $('#regDetails_compliance_log').val();
					if(complianceLog != null && complianceLog !='' && complianceLog != undefined){
						$('#regDetails_alert_compliance_log').html(complianceLog);
					}
					
					if(isAutoUnlock){
						unlockResource();
						applyClassesToAutoUnLockButton();
					}
					
					if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)
						updateIntuitionRegStatus(request, getUser(), getComplianceServiceBaseUrl());
					
					$('#regDetails_compliance_log').val('');
					populateSuccessMessage("main-content__body",
							"Updated successfully",
							"profile_update_error_field",
							"regDetails_profile_update");
				},
				error : function() {
					$('#gifloaderforprofileupdateCFX').css('visibility',
							'hidden');
					enableButton('regDetails_profile_update');
					populateErrorMessage("main-content__body",
							"Error while updating ",
							"profile_update_error_field",
							"regDetails_profile_update");
				}
			});
}

function getCFXActivityLogs(request, isViewMoreRequest) {
	$.ajax({
		url : '/compliance-portal/regActivites',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if (isViewMoreRequest) {
				setCFXActivityLogViewMore(data.activityLogData);
			} else {
				setActivityLog(data.activityLogData);
				setCFXViewMoreActLogData();
				enableButton('regDetails_profile_update');
			}
		},
		error : function() {
			alert('Error while fetching data');
			enableButton('regDetails_profile_update');
		}
	});
}

function getCFXActivities(minRecord, maxRecord, contactId, isViewMoreRequest) {
	var request = {};
	var custType = $('#customerType').val();
	var comment = $('#regDetails_comments').val();
	addField("minRecord", minRecord, request);
	addField("maxRecord", maxRecord, request);
	addField("entityId", contactId, request);
	addField("custType", custType, request);
	addField("comment",comment,request);
	getCFXActivityLogs(request, isViewMoreRequest);
}

function setCFXActivityLogViewMore(activities) {

	var rows = '';
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
	    var comment = getEmptyIfNull(activityData.comment);
	    var tradeContractNumber = '---'; //AT-1794 - Snehaz
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
	var totalRecords = Number($('#actLogTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords, "viewMore_ActLogId",
			"viewMoreDetails_ActLog");

}
function updateViewMoreBlock(leftRecords,viewMoreValue,viewMoreBlockId){
	
	if(leftRecords === 0){
		$("#"+viewMoreValue).html(leftRecords);
		$("#"+viewMoreBlockId).attr("disabled", true);
		$("#"+viewMoreBlockId).addClass("disabled");

	}else {
		$("#"+viewMoreBlockId).attr("disabled", false);
		$("#"+viewMoreBlockId).removeClass("disabled");
		if(leftRecords < Number(getViewMoreRecordsize())){
			$("#"+viewMoreValue).html(leftRecords);
		}else{
			$("#"+viewMoreValue).html(Number(getViewMoreRecordsize()));
		}
	}
} 
function setCFXViewMoreActLogData() {

	var noOfRows = countRows("activityLog");
	var totalRecords = Number($('#actLogTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords, "viewMore_ActLogId",
			"viewMoreDetails_ActLog");
	Number($('#actLogTotalRecordsId').val(totalRecords));
}

function getAccountStatus(status) {
	var data = '';
	$("#account_status").empty();
	if (status === 'INACTIVE' || status === 'REJECTED') {
		data = '<span class="indicator--negative" id="account_compliacneStatus">'
				+ status + '</span>';
	} else if (status === 'ACTIVE') {
		data = '<span class="indicator--positive" id="account_compliacneStatus">'
				+ status + '</span>';
	} else {
		status = 'INACTIVE';
		data = '<span class="indicator--negative" id="account_compliacneStatus">'
				+ status + '</span>';
	}
	$("#account_status").append(data);
}

function showCFXProviderResponse(eventServiceLogId,serviceType,chartDisplayFlag,contactID) {
	var getProviderResponseRequest = {};
	addField('eventServiceLogId', eventServiceLogId, getProviderResponseRequest);
	addField('serviceType', serviceType, getProviderResponseRequest);
	getProviderResponse(getProviderResponseRequest,chartDisplayFlag,contactID);
}

function viewMoreCFXDetails(serviceType, id, totalRecordsId, leftRecordsId,
		entityId, entityType) {

	var viewMore = {};
	var noOfRows = countCFXRows(id);
	if(id=="regDetails_fraugster--"+entityId)  // AT-3325
		 noOfRowsFrg=countRows("regDetails_fraugster--"+entityId);
		if(id=="regDetails_sanction--"+entityId)
		 noOfRowsSanction=countRows("regDetails_sanction--"+entityId);
	   if(id=="regDetails_eid--"+entityId)
		 noOfRowsEid=countRows("regDetails_eid--"+entityId);
	   if(id=="regDetails_intuition--"+entityId) //AT-4114
		 noOfRowsIntuition=countRows("regDetails_intuition--"+entityId);
		if((clicked==false) || (clicked== true && noOfRowsFrg>prevCountfrg)||(clicked== true && noOfRowsSanction>prevCountSanction)||(clicked== true && noOfRowsEid>prevCountEID||(clicked== true && noOfRowsIntuition>prevCountIntuition)))
		{
		clicked=true;
	var totalRecords = Number($("#" + totalRecordsId).val());
	var leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
			leftRecordsId);
	var orgCode = $('#account_organisation').text();
	var clientType = $('#customerType').val();
	var minViewRecord = calculateCFXMinRecord(noOfRows);
	var maxViewRecord = calculateCFXMaxRecord(minViewRecord);
	var accountId = $('#contact_accountId').val(); //AT-4170
	
	addField('entityId', entityId, viewMore);
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
		$('#gifloaderforViewmoreKycCFX').css('visibility', 'visible');
		prevCountEID=noOfRowsEid;
		postCFXKycMoreDetails(viewMore, entityId);
	} else if (serviceType == "SANCTION") {
		$('#gifloaderforViewmoresanctionCFX').css('visibility', 'visible');
		 prevCountSanction=noOfRowsSanction; 
		 postCFXSanctionMoreDetails(viewMore, entityId, entityType);
	} else if (serviceType == "FRAUGSTER") {
		$('#gifloaderforViewmoreFraugsterCFX').css('visibility', 'visible');
		prevCountfrg=noOfRowsFrg;
		postCFXFraugsterMoreDetails(viewMore, entityId);
	} else if (serviceType == "CUSTOMCHECK") {
		$('#gifloaderforViewmoreCustomcheckCFX').css('visibility', 'visible');
		var minViewRecordCustChk = calculateMinRecordCustChk(noOfRows);
		var maxViewRecordCustChk = calculateMaxRecordCustChk(minViewRecordCustChk);
		addField('minViewRecord', minViewRecordCustChk, viewMore);
		addField('maxViewRecord', maxViewRecordCustChk, viewMore);
		postCFXCustomCheckMoreDetails(viewMore);
	} else if (serviceType == "TRANSACTION_MONITORING") {
		$('#gifloaderforViewmoreIntuitionCFX').css('visibility', 'visible');
		 prevCountIntuition=noOfRowsIntuition; 
		 postCFXIntuitionMoreDetails(viewMore, entityId, entityType);
	}else if (serviceType == "ACTIVITYLOG") {
		getCFXActivities(minViewRecord, maxViewRecord, entityId, true);
	}
		}
}

function updateLeftCFXRecords(noOfRows, totalRecords, id) {

	var leftRecord = totalRecords - noOfRows;
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + id).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + id).html("( " + leftRecord + " LEFT )");
	}

	return leftRecord;
}

function countCFXRows(id) {
	var totalRowCount = 0;
	var rowCount = 0;
	var table = document.getElementById(id);
	var rows = table.getElementsByTagName("tr")
	for (var i = 0; i < rows.length; i++) {
		totalRowCount++;
		if (rows[i].getElementsByTagName("td").length > 0) {
			rowCount++;
		}
	}
	return rowCount;
}

function calculateCFXMinRecord(noOfRows) {

	return noOfRows + 1;
}

function calculateCFXMaxRecord(minViewRecord) {

	return minViewRecord + Number(getViewMoreRecordsize()) - 1;
}

function postCFXKycMoreDetails(request, contactId) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforViewmoreKycCFX').css('visibility', 'hidden');
			setCFXKycMoreDetailsResponse(data.services, contactId);
		},
		error : function() {
			$('#gifloaderforViewmoreKycCFX').css('visibility', 'hidden');
			alert('Error while fetching kyc more details');
		}
	});
}

function postCFXSanctionMoreDetails(request, id, entityType) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforViewmoresanctionCFX').css('visibility', 'hidden');
			setCFXSanctionMoreDetailsResponse(data.services, id, entityType);
		},
		error : function() {
			$('#gifloaderforViewmoresanctionCFX').css('visibility', 'hidden');
			alert('Error while fetching sanction more details');

		}
	});
}

function postCFXFraugsterMoreDetails(request, contactId) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforViewmoreFraugsterCFX').css('visibility', 'hidden');
			setCFXFraugsterMoreDetailsResponse(data.services, contactId);
		},
		error : function() {
			$('#gifloaderforViewmoreFraugsterCFX').css('visibility', 'hidden');
			alert('Error while fetching fraugster more details');

		}
	});
}

function postCFXCustomCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforViewmoreCustomcheckCFX')
					.css('visibility', 'hidden');
			setCustomCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforViewmoreCustomcheckCFX')
					.css('visibility', 'hidden');
			alert('Error while fetching sanction more details');

		}
	});
}

function setCFXKycMoreDetailsResponse(kycViewMore, contactId) {

	for (var i = 0; i < kycViewMore.length; i++) {
		var checkedOn = getEmptyIfNull(kycViewMore[i].checkedOn);
		var eidCheck = getEmptyIfNull(kycViewMore[i].eidCheck);
		var verifiactionResult = getEmptyIfNull(kycViewMore[i].verifiactionResult);
		var referenceId = getEmptyIfNull(kycViewMore[i].referenceId);
		var status = getEmptyIfNull(kycViewMore[i].status);
		// field added to fetch status value as it is from kyc object (Changes
		// done by Vishal J)
		var statusValue = getEmptyIfNull(kycViewMore[i].statusValue);
		var notRequired = "Not Required";
		var dob = getEmptyIfNull(kycViewMore[i].dob);
		var eventServiceLogId = getEmptyIfNull(kycViewMore[i].id);
		var row = '<tr>';
		// Removed class="nowrap" from 'checkedOn' to set columns of table
		// properly on UI
		row += '<td>' + checkedOn + '</td>';
		if (statusValue === "Not Required")
			row += '<td class="nowrap">' + notRequired + '</td>';
		else
			row += getYesOrNoCell(eidCheck);
		row += '<td class="number">' + verifiactionResult + '</td>';
		row += '<td><a href="javascript:void(0);" onclick="showCFXProviderResponse('
				+ eventServiceLogId + ',\'KYC\')">' + referenceId + '</a></td>';
		row += '<td class="nowrap">' + dob + '</td>';
		// checking if status is 'NOT_REQUIRED' then show that on UI otherwise
		// 'PASS' or 'FAIL'
		if (statusValue === "Not Required")
			row += '<td class="nowrap">' + notRequired + '</td>';
		else
			row += getYesOrNoCell(status);
		row += '</tr>'
		$("#regDetails_eid-" + contactId).append(row);
		var kycPassCount = 0;
		var kycFailCount = 0;
		if (eidCheck) {
			kycPassCount++;
		} else {
			kycFailCount++;
		}
		if (status) {
			kycPassCount++;
		} else {
			kycFailCount++;
		}
		// setKycIndicator(kycPassCount, kycFailCount)
	}
	var noOfRows = countCFXRows("regDetails_eid-" + contactId);
	var totalRecords = Number($('#kycTotalRecordsId-' + contactId).val());
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdKyc-" + contactId);
	updateCFXViewMoreBlock(leftRecords, "viewMore_KycId-" + contactId,
			"viewMoreDetails_kyc-" + contactId);

}

function updateCFXLeftRecords(noOfRows, totalRecords, id) {

	var leftRecord = totalRecords - noOfRows;
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + id).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + id).html("( " + leftRecord + " LEFT )");
	}

	return leftRecord;
}

function updateCFXViewMoreBlock(leftRecords, viewMoreValue, viewMoreBlockId) {

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

function setCFXSanctionMoreDetailsResponse(sanction, id, entityType) {

	var noOfRows;
	var totalRecords;
	var leftRecords;
	var accountId = $('#contact_accountId').val();

	for (var i = 0; i < sanction.length; i++) {
		var updatedOn = getEmptyIfNull(sanction[i].updatedOn);
		var updatedBy = getEmptyIfNull(sanction[i].updatedBy);
		var sanctionId = getEmptyIfNull(sanction[i].sanctionId);
		var ofacList = getEmptyIfNull(sanction[i].ofacList);
		var worldCheck = getEmptyIfNull(sanction[i].worldCheck);
		var status = getEmptyIfNull(sanction[i].status);
		// fetching status value from controller side (added by Vishal J)
		var statusValue = getEmptyIfNull(sanction[i].statusValue);
		var notRequired = "Not Required";
		var eventServiceLogId = getEmptyIfNull(sanction[i].eventServiceLogId);
		var row = '<tr>';
		row += '<td hidden="hidden" class="center">'
				+ sanction[i].eventServiceLogId + '</td>';
		// Removed class="nowrap" from 'checkedOn' to set columns of table
		// properly on UI
		row += '<td>' + updatedOn + '</td>';
		row += '<td class="nowrap">' + updatedBy + '</td>';
		// Removed class="nowrap" from 'checkedOn' to set columns of table
		// properly on UI
		row += '<td><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'SANCTION\')">'
				+ sanctionId
				+ '</a></td>';
		row += '<td class="nowrap">' + ofacList + '</td>';
		row += '<td class="nowrap">' + worldCheck + '</td>';
		// checking if status is 'NOT_REQUIRED' then show that on UI otherwise
		// 'PASS' or 'FAIL'
		if (statusValue === "Not Required")
			row += '<td class="nowrap">' + notRequired + '</td>';
		else
			row += getYesOrNoCell(status);
		row += '</tr>'
		if (sanction.passCount === undefined || sanction.passCount === null) {
			if (status) {
				sanction.passCount = 1;
			} else {
				sanction.failCount = 1;
			}
		}
		if (entityType == "ACCOUNT") {
			$("#regDetails_sanction--" + accountId).append(row);
		} else if (entityType == "CONTACT") {
			$("#regDetails_sanction-" + id).append(row);
		}

	}

	if (entityType == "ACCOUNT") {
		noOfRows = countCFXRows("regDetails_sanction--" + accountId);
		totalRecords = Number($('#sanctionTotalRecordsId--' + accountId).val());
		leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
				"leftRecordsIdSanc--" + accountId);
		updateCFXViewMoreBlock(leftRecords, "viewMore_SancId--" + accountId,
				"viewMoreDetails_sanc--" + accountId);

	} else if (entityType == "CONTACT") {
		noOfRows = countCFXRows("regDetails_sanction-" + id);
		totalRecords = Number($('#sanctionTotalRecordsId-' + id).val());
		leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
				"leftRecordsIdSanc-" + id);
		updateCFXViewMoreBlock(leftRecords, "viewMore_SancId-" + id,
				"viewMoreDetails_sanc-" + id);
	}

}

function setCFXFraugsterMoreDetailsResponse(fraugster, contactId) {

	for (var i = 0; i < fraugster.length; i++) {
		var createdOn = getEmptyIfNull(fraugster[i].createdOn);
		var updatedBy = getEmptyIfNull(fraugster[i].updatedBy);
		var fraugsterId = getEmptyIfNull(fraugster[i].fraugsterId);
		var score = getEmptyIfNull(fraugster[i].score);
		var eventServiceLogId = getEmptyIfNull(fraugster[i].id);
		var status = getEmptyIfNull(fraugster[i].status);
		var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('
			+ eventServiceLogId
			+ ',\'FRAUGSTER\',\'FraugsterChart\','+contactId+')">';
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
		$("#regDetails_fraugster-" + contactId).append(row);
	}
	var noOfRows = countCFXRows("regDetails_fraugster-" + contactId);
	var totalRecords = Number($('#fraugsterTotalRecordsId-' + contactId).val());
	var leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
			"leftRecordsIdFraug-" + contactId);
	updateCFXViewMoreBlock(leftRecords, "viewMore_FraugId-" + contactId,
			"viewMoreDetails_fraug-" + contactId);
}

function onLoadViewMore(serviceType, id, entityType) {

	if (serviceType == "KYC" && entityType == "CONTACT") {
        clicked=false;
        prevCountEID=0;
        noOfRowsEid=0;
		getOnLoadKycContactViewMore(id);

	} else if (serviceType == "SANCTION" && entityType == "CONTACT") {
		clicked= false;
		prevCountSanction=0;
		noOfRowsSanction=0;
		getOnLoadSanctionContactViewMore(id);

	} else if (serviceType == "FRAUGSTER" && entityType == "CONTACT") {
		clicked=false;
		prevCountfrg=0;
		noOfRowsFrg=0;
		getOnLoadFraugsterContactViewMore(id);

	} else if (serviceType == "SANCTION" && entityType == "ACCOUNT") {
		clicked= false;
		prevCountSanction=0;
		noOfRowsSanction=0;
		getOnLoadSanctionAccountViewMore(id)
		
	}else if (serviceType == "TRANSACTION_MONITORING" && entityType == "ACCOUNT") { //AT-4114
		clicked= false;
		prevCountIntuition=0;
		noOfRowsIntuition=0;
		getOnLoadIntuitionAccountViewMore(id)

	}else if (serviceType == "TRANSACTION_MONITORING" && entityType == "CONTACT") { //AT-4114
		clicked= false;
		prevCountIntuition=0;
		noOfRowsIntuition=0;
		getOnLoadIntuitionContactViewMore(id);

	}
}

function getOnLoadKycContactViewMore(id) {

	$("#regDetails_eid-" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_eid-" + id);
	var totalRecords = $('#kycTotalRecordsId-' + id).val();
	if (totalRecords === undefined || totalRecords === null
			|| totalRecords == 'NaN' || totalRecords === "") {
		totalRecords = 0;
	}
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdKyc-" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_KycId-" + id,
			"viewMoreDetails_kyc-" + id);
}

function getOnLoadSanctionContactViewMore(id) {

	$("#regDetails_sanction-" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_sanction-" + id);
	var totalRecords = $('#sanctionTotalRecordsId-' + id).val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdSanc-" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_SancId-" + id,
			"viewMoreDetails_sanc-" + id);
}

function getOnLoadFraugsterContactViewMore(id) {

	$("#regDetails_fraugster-" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_fraugster-" + id);
	var totalRecords = $('#fraugsterTotalRecordsId-' + id).val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdFraug-" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_FraugId-" + id,
			"viewMoreDetails_fraug-" + id);
}

function getOnLoadSanctionAccountViewMore(id) {

	$("#regDetails_sanction--" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_sanction--" + id);
	var totalRecords = $('#sanctionTotalRecordsId--' + id).val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdSanc--" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_SancId--" + id,
			"viewMoreDetails_sanc--" + id);
}

function getOnLoadActivityLogViewMore() {
	$("#activityLog").find('tr').slice(10).remove();
	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsId').val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdActLog");
	updateCFXViewMoreBlock(leftRecords, "viewMore_ActLogId",
			"viewMoreDetails_ActLog");
}

function viewMoreCFXLoadDataActLog() {

	var noOfRowsActLog = countRows("activityLog");
	var totalRecordsActLog = $('#actLogTotalRecordsId').val();
	var leftRecordsActLog = updateCFXLeftRecords(noOfRowsActLog,
			totalRecordsActLog, "leftRecordsIdActLog");
	updateCFXViewMoreBlock(leftRecordsActLog, "viewMore_ActLogId",
			"viewMoreDetails_ActLog");
}

function resendCFXContactFraugster(contactId) {
	$('#gifloaderforresendfraugster-'+contactId).css('visibility','visible');
	var fraugster = {};
	var accountId = Number($('#contact_accountId').val());
	//var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId',accountId,fraugster);
	addField('entityId',contactId,fraugster);
	addField('entityType','CONTACT',fraugster);
	addField('orgCode',orgCode,fraugster);
	addField('contactId',contactId,fraugster);
	postCFXContactFraugsterResend(fraugster,getComplianceServiceBaseUrl(),getUser(),contactId);
}

function postCFXContactFraugsterResend(request,baseUrl,user,contactId) {
	disableButton('regDetails_updateFraugster');
	disableButton('regDetails_fraugster_recheck-'+contactId);
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/repeatcheck/fraugster',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforresendfraugster-'+contactId).css('visibility','hidden');
				enableButton('regDetails_updateFraugster');
				enableButton('regDetails_fraugster_recheck-'+contactId);
				populateErrorMessage("main-content__body","Error while resending Fraugster","fraugster_error_field","regDetails_fraugster_recheck");
			}else {
				$('#gifloaderforresendfraugster-'+contactId).css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));//In Activity log 1 row inserted so increment by 1
				
				var accountTMFlag = $('#accountTMFlag').val();
				var previousFraugsterStatus = $('#contactFraugsterStatus-'+contactId).val();
				var currentFraugsterStatus = data.summary.status;
				
				data.summary.updatedBy=getJsonObject(getUser()).name;
				if(data.summary.status === 'PASS'){
					data.summary.status=true;
				} else {
					data.summary.status=false;
				}
				setFraugsterCFXContactResendResponse(data.summary , contactId);
				getCFXActivities(1,10,Number($('#contact_accountId').val()),false);
				enableButton('regDetails_updateFraugster');
				enableButton('regDetails_fraugster_recheck-'+contactId);
				populateSuccessMessage("main-content__body","Fraugster Repeat Check Successfully done","fraugster_error_field","regDetails_fraugster_recheck");
				
				if(previousFraugsterStatus != currentFraugsterStatus && currentFraugsterStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var fraugsterRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').val();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'fraugster';
					var custType = $('#customerType').val();
					var fraugsterScore = data.summary.score;
					var fraugsterDate = data.summary.createdOn;
					addField('accountId', accountId, fraugsterRequest);
					addField('contactId', contactId, fraugsterRequest);
					addField('orgCode', orgCode, fraugsterRequest);
					addField('registrationDateTime',regDateTime,fraugsterRequest);
					addField('fraugsterStatus',currentFraugsterStatus,fraugsterRequest);
					addField('tradeAccountNumber',tradeAccountNumber,fraugsterRequest);
					addField('checkType',checkType,fraugsterRequest);
					addField('custType',custType,fraugsterRequest);
					addField('fraugsterScore',fraugsterScore,fraugsterRequest);
					addField('fraugsterDate',fraugsterDate,fraugsterRequest);
					updateIntuitionRepeatCheckStatus(fraugsterRequest, getUser(), getComplianceServiceBaseUrl());
					var prevBlacklistStatus = document.getElementById("contactFraugsterStatus-"+contactId);
					prevBlacklistStatus.value = currentFraugsterStatus;
				}
			}
		},
		error : function() {
			$('#gifloaderforresendfraugster-'+contactId).css('visibility','hidden');
			enableButton('regDetails_updateFraugster');
			enableButton('regDetails_fraugster_recheck-'+contactId);
			populateErrorMessage("main-content__body","Error while resending Fraugster","fraugster_error_field","regDetails_fraugster_recheck");

		}
	});
}

function setFraugsterCFXContactResendResponse(fraugster , contactId) {
	//$("#regDetails_fraugster").empty();
	var createdOn = getEmptyIfNull(fraugster.createdOn);
	var updatedBy = getEmptyIfNull(fraugster.updatedBy);
	var fraugsterId = getEmptyIfNull(fraugster.frgTransId);
	var score = getEmptyIfNull(fraugster.score);
	var eventServiceLogId = getEmptyIfNull(fraugster.eventServiceLogId);
	var status = getEmptyIfNull(fraugster.status);
	var row = '<tr href="javascript:void(0);" onclick="showCFXProviderResponse('+eventServiceLogId+',\'FRAUGSTER\',\'FraugsterChart\','+contactId+')">';
    row +='<td>'+createdOn+'</td>';
    row +='<td class="nowrap">'+updatedBy+'</td>';
    row +='<td class=""><a href="javascript:void(0);" onclick="showCFXProviderResponse('+eventServiceLogId+',\'FRAUGSTER\')">'+fraugsterId+'</a></td>';
    row +='<td class="nowrap" class="number">'+score+'</td>'
    row +=getYesOrNoCellWithId(status,'fraugster_status');
    row += '</tr>'
	
    var totalRecords = Number($('#fraugsterTotalRecordsId-'+contactId).val()) + 1;
	if(fraugster.passCount === undefined || fraugster.passCount === null){
	if(fraugster.status){
		fraugster.passCount = totalRecords;
		setFraugsterTotalRecords(totalRecords, contactId);
	} else {
		fraugster.failCount = totalRecords;
		setFraugsterTotalRecords(totalRecords, contactId);
	}
	}
	var fraugsterPassCount = fraugster.passCount;
	var fraugsterFailCount = fraugster.failCount;
	setCFXContactFraugsterIndicator(fraugsterPassCount, fraugsterFailCount, contactId);
	$("#regDetails_fraugster-"+contactId).prepend(row);
	var noOfRows = countCFXRows("regDetails_fraugster-"+contactId);
	var leftRecords = updateCFXLeftRecords(noOfRows,totalRecords,"leftRecordsIdFraug-"+contactId);
					  updateCFXContactViewMoreBlock(leftRecords,"viewMore_FraugId-"+contactId,"viewMoreDetails_fraug-"+contactId);
					  Number($('#fraugsterTotalRecordsId').val(totalRecords));
}

function setCFXContactFraugsterIndicator(passCount,failCount, contactId) {	
	$('#regDetails_fraugster_indicatior-'+contactId).empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>FRAUDPREDICT';
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_fraugsterPass-'+contactId);
	} 
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_fraugsterFail-'+contactId);
	} 
	indicator += '</a>';
	$('#regDetails_fraugster_indicatior-'+contactId).append(indicator);
}

function getPositiveIndicator(value,id){
	return '<span id="'+id+'" class="indicator--positive">'+value+'</span>'
}

function getNegativeIndicator(value,id){
	return '<span id="'+id+'" class="indicator--negative">'+value+'</span>'
}

function updateCFXContactViewMoreBlock(leftRecords,viewMoreValue,viewMoreBlockId){
	
	if(leftRecords === 0){
		$("#"+viewMoreValue).html(leftRecords);
		$("#"+viewMoreBlockId).attr("disabled", true);
		$("#"+viewMoreBlockId).addClass("disabled");

	}else {
		$("#"+viewMoreBlockId).attr("disabled", false);
		$("#"+viewMoreBlockId).removeClass("disabled");
		if(leftRecords < Number(getViewMoreRecordsize())){
			$("#"+viewMoreValue).html(leftRecords);
		}else{
			$("#"+viewMoreValue).html(Number(getViewMoreRecordsize()));
		}
	}
}

function setFraugsterTotalRecords(totalRecordValue, contactId) {
	$("#fraugsterTotalRecordsId-"+contactId).val(totalRecordValue);
}

function resendCFXAccountBlacklist(accountID) {
	$('#gifloaderforBlacklistresendForCFXAccount').css('visibility', 'visible');
	var blacklist = {};
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId', accountID, blacklist);
	addField('entityId', accountID, blacklist);
	addField('entityType', 'ACCOUNT', blacklist);
	addField('orgCode', orgCode, blacklist);
	addField('contactId', contactId, blacklist);
	postCFXAccountBlacklistResend(blacklist, getComplianceServiceBaseUrl(),
			getUser(), accountID);
}

function postCFXAccountBlacklistResend(request, baseUrl, user, accountID) {
	disableButton('regDetails_blacklist_recheck--' + accountID);
	$.ajax({
		url : baseUrl+ '/compliance-service/services-internal/repeatcheck/blacklist',
		type : 'POST',
		headers : {
			"user" : user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforBlacklistresendForCFXAccount').css('visibility', 'hidden');
				enableButton('regDetails_blacklist_recheck--' + accountID);
				populateErrorMessage("main-content__body", "Error while resending blacklist for account " + getTextById("account-name"),
				"blacklist_error_field--" + accountID, 'regDetails_blacklist_recheck--' + accountID);
			} else {
				$('#gifloaderforBlacklistresendForCFXAccount').css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val())
						+ data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));// In Activity log 1 row  inserted so  increment  by 1
				setCFXAccountBlacklistResendResponse(data.summary);
				getCFXActivities(1, 10, Number($('#contact_accountId').val()), false);
				enableButton('regDetails_blacklist_recheck--' + accountID);
				
				var accountTMFlag = $('#accountTMFlag').val();
				var previousBlacklistStatus = $('#accountBlacklistStatus').val();
				var currentBlacklistStatus = data.summary.status;
				if(previousBlacklistStatus != currentBlacklistStatus && currentBlacklistStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var blacklistRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').val();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'cfxBlacklist';
					var custType = $('#customerType').val();
					addField('accountId', accountId, blacklistRequest);
					addField('contactId', contactId, blacklistRequest);
					addField('orgCode', orgCode, blacklistRequest);
					addField('registrationDateTime',regDateTime,blacklistRequest);
					addField('accountBlacklistStatus',currentBlacklistStatus,blacklistRequest);
					addField('tradeAccountNumber',tradeAccountNumber,blacklistRequest);
					addField('checkType',checkType,blacklistRequest);
					addField('custType',custType,blacklistRequest);
					updateIntuitionRepeatCheckStatus(blacklistRequest, getUser(), getComplianceServiceBaseUrl());
					var prevBlacklistStatus = document.getElementById("accountBlacklistStatus");
					prevBlacklistStatus.value = currentBlacklistStatus;
				}
				populateSuccessMessage("main-content__body", "Blacklist Repeat Check Successfully done for account " + getTextById("account-name"),
						"blacklist_error_field--" + accountID, 'regDetails_blacklist_recheck--' + accountID);
			}
		},
		error : function() {
			$('#gifloaderforBlacklistresendForCFXAccount').css('visibility', 'hidden');
			enableButton('regDetails_blacklist_recheck--' + accountID);
			populateErrorMessage("main-content__body", "Error while resending blacklist for account " + getTextById("account-name"),
					"blacklist_error_field--" + accountID, 'regDetails_blacklist_recheck--' + accountID);

		}
	});
}

function setCFXAccountBlacklistResendResponse(blacklist) {
	var ip = getEmptyIfNull(blacklist.ip);
	var name = getEmptyIfNull(blacklist.name);
	var website = getEmptyIfNull(blacklist.webSite);	
	var status = getEmptyIfNull(blacklist.status);
	var failCount = 0;
	var passCount = 0;
	
	status = ('PASS'===status) ? true : false;
	var row = '<tr>';
	if(ip === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(ip === 'false'){
		row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		passCount++;
	}
	else if(ip === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
		failCount++;
	}
	else {
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+blacklist.ipMatchedData+ ')</td>';
		failCount++;
	 }
	if(name === 'NOT_REQUIRED')
	    row += '<td class="nowrap center">Not Required</td>';
	else if(name === 'false'){
		    row +='<td class="yes-cell"><i class="material-icons">check</i></td>';
		    passCount++;
	}
	else if(name === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
		failCount++;
	}
	else {
	       row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+blacklist.nameMatchedData+ ')</td>';
	       failCount++;
	 }
	if(website === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(website === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else if(website === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
		failCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.websiteMatchedData+ ')</td>';
	      failCount++;
	}
	
    row += getYesOrNoCell(status);
    row += '</tr>'
    setBlacklistIndicator(passCount, failCount);
    
    $("#cfx_regDetails_AccountblackList").empty();
    $("#cfx_regDetails_AccountblackList").append(row);
   
}

function setBlacklistIndicator(passCount, failCount) {
	$('#regDetails_blacklist_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist';
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'regDetails_blackPass');
	}
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'regDetails_blackNeg');
	}
	indicator += '</a>';
	$('#regDetails_blacklist_indicatior').append(indicator);
}


function resendCFXContactBlacklist(contactId) {
	$('#gifloaderforBlacklistresend-' + contactId).css('visibility', 'visible');
	var blacklist = {};
	var accountId = Number($('#contact_accountId').val());
	var orgCode = $('#account_organisation').val();
	addField('accountId', accountId, blacklist);
	addField('contactId',contactId,blacklist);
	addField('entityId', contactId, blacklist);
	addField('entityType', 'CONTACT', blacklist);
	addField('orgCode', orgCode, blacklist);
	postCFXContactBlacklistResend(blacklist, getComplianceServiceBaseUrl(),
			getUser(), contactId);
}

function postCFXContactBlacklistResend(request, baseUrl, user, contactId) {
	disableButton('regDetails_blacklist_recheck-' + contactId);
	$.ajax({
		url : baseUrl + '/compliance-service/services-internal/repeatcheck/blacklist',
		type : 'POST',
		headers : {
			"user" : user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforBlacklistresend-' + contactId).css('visibility', 'hidden');
				enableButton('regDetails_blacklist_recheck-' + contactId);
				populateErrorMessage("main-content__body", "Error while resending blacklist for contact " + getTextById("contact-name-" + contactId),
						"blacklist_error_field-" + contactId, 'regDetails_blacklist_recheck-' + contactId);
			}else{
				$('#gifloaderforBlacklistresend-' + contactId).css('visibility', 'hidden');
				enableButton('regDetails_blacklist_recheck-' + contactId);

				var addedRecords = Number($('#actLogTotalRecordsId').val())
						+ data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));// In Activity log 1 row  inserted  so  increment  by 1
				
				var accountTMFlag = $('#accountTMFlag').val();
				var previousBlacklistStatus = $('#contactBlacklistStatus-'+contactId).val();
				var currentBlacklistStatus = data.summary.status;
				
				setCFXContactBlacklistResendResponse(data.summary,contactId);
				getCFXActivities(1, 10, Number($('#contact_accountId').val()), false);
				populateSuccessMessage("main-content__body", "Blacklist Repeat Check Successfully done for contact "
								+ getTextById("contact-name-" + contactId), "blacklist_error_field-" + contactId, 'regDetails_blacklist_recheck-' + contactId);
				
				if(previousBlacklistStatus != currentBlacklistStatus && currentBlacklistStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var blacklistRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').val();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'blacklist';
					var custType = $('#customerType').val();
					addField('accountId', accountId, blacklistRequest);
					addField('contactId', contactId, blacklistRequest);
					addField('orgCode', orgCode, blacklistRequest);
					addField('registrationDateTime',regDateTime,blacklistRequest);
					addField('blacklistStatus',currentBlacklistStatus,blacklistRequest);
					addField('tradeAccountNumber',tradeAccountNumber,blacklistRequest);
					addField('checkType',checkType,blacklistRequest);
					addField('custType',custType,blacklistRequest);
					updateIntuitionRepeatCheckStatus(blacklistRequest, getUser(), getComplianceServiceBaseUrl());
					var prevBlacklistStatus = document.getElementById("contactBlacklistStatus-"+contactId);
					prevBlacklistStatus.value = currentBlacklistStatus;
				}
			}
		},
		error : function() {
			$('#gifloaderforBlacklistresend-' + contactId).css('visibility', 'hidden');
			enableButton('regDetails_blacklist_recheck-' + contactId);
			alert('Error while resending Blacklist');
			populateErrorMessage("main-content__body", "Error while resending blacklist for contact " + getTextById("contact-name-" + contactId),
					"blacklist_error_field-" + contactId, 'regDetails_blacklist_recheck-' + contactId);
		}
	});
}

function setCFXContactBlacklistResendResponse(blacklist,contactId) {

	var ip = getEmptyIfNull(blacklist.ip);
	var email = getEmptyIfNull(blacklist.email);
	var name = getEmptyIfNull(blacklist.name);
	var phone = getEmptyIfNull(blacklist.phone);
	var status = getEmptyIfNull(blacklist.status);
	var failCount = 0;
	var passCount = 0;
	
	status = ('PASS'===status) ? true : false;
	var row = '<tr>';
	if(ip === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(ip === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else if(ip === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
	    failCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.ipMatchedData+ ')</td>';
	      failCount++;
	}
	if(email === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(email === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else if(email === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
	    failCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.emailMatchedData+ ')</td>';
	      failCount++;
	}
	if(name === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(name === 'false'){
		row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		passCount++;
	}
	else if(name === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
	    failCount++;
	   }
	else {
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+blacklist.nameMatchedData+ ')</td>';
		failCount++;
	 }
	if(phone === 'NOT_REQUIRED')
	    row += '<td class="nowrap center">Not Required</td>';
	else if(phone === false){
		    row +='<td class="yes-cell"><i class="material-icons">check</i></td>';
		    passCount++;
	}
	else if(name === ''){
		row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i></td>';
	    failCount++;
	   }
	else {
	       row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+blacklist.phoneMatchedData+ ')</td>';
	       failCount++;
	 }
	
    row += getYesOrNoCell(status);
    row += '</tr>'
    setBlacklistCFXContactIndicator(passCount, failCount, contactId);
        
    $("#cfx_regDetails_ContactBlackList-" + contactId).empty();
    $("#cfx_regDetails_ContactBlackList-" + contactId).append(row);   
	
}

function setBlacklistCFXContactIndicator(passCount, failCount, contactId) {
	$('#regDetails_blacklist_indicatior-' + contactId).empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist';
	if (failCount > 0) {
		indicator += getNegativeIndicator(failCount,
				'regDetails_blackPass');
	}
	if (passCount > 0) {
		indicator += getPositiveIndicator(passCount,
				'regDetails_blackNeg');
	}
	indicator += '</a>';
	$('#regDetails_blacklist_indicatior-' + contactId).append(indicator);
}

function fraugsterChartData(originalJSON,id) {
	var originalJSONParsed = {};
	
	originalJSONParsed = JSON.parse(originalJSON);
	
	if(null == originalJSONParsed) {
		$("#boxpanel-space-before-"+id).css('display','none');
	}
	else {	
	if(originalJSONParsed.hasOwnProperty('decisionDrivers') == '' 
		|| originalJSONParsed['status'] == 'SERVICE_FAILURE' || originalJSONParsed['status'] == 'NOT_REQUIRED') {
		$("#boxpanel-space-before-"+id).css('display','none');
	}
	else {
		$("#boxpanel-space-before-"+id).css('display','block');
		var chart = AmCharts.makeChart( "cfx-fraugster-chart-"+id, {
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

//AT-4114
function postCFXIntuitionMoreDetails(request, id, entityType) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforViewmoreIntuitionCFX').css('visibility', 'hidden');
			setCFXIntuitionMoreDetailsResponse(data.services, id, entityType);
		},
		error : function() {
			$('#gifloaderforViewmoreIntuitionCFX').css('visibility', 'hidden');
			alert('Error while fetching sanction more details');

		}
	});
}

//AT-4114
function setCFXIntuitionMoreDetailsResponse(intuition, id, entityType) {

	var noOfRows;
	var totalRecords;
	var leftRecords;
	var accountId = $('#contact_accountId').val();

	for (var i = 0; i < intuition.length; i++) {
		var createdOn = getEmptyIfNull(intuition[i].createdOn);
		var updatedBy = getEmptyIfNull(intuition[i].updatedBy);
		var intuitionId = getEmptyIfNull(intuition[i].correlationId);
		//var score = getEmptyIfNull(intuition[i].score);
		var profileScore = getEmptyIfNull(intuition[i].profileScore);
		var ruleScore = getEmptyIfNull(intuition[i].ruleScore);
		var status = getEmptyIfNull(intuition[i].status);
		var statusValue = getEmptyIfNull(intuition[i].statusValue);
		var notPerformed = "NOT_PERFORMED";
		var notRequired = "NOT_REQUIRED";
		var eventServiceLogId = getEmptyIfNull(intuition[i].id);
		var riskLevel = getEmptyIfNull(intuition[i].riskLevel);
		var row = '<tr>';
		row += '<td hidden="hidden" class="center">'+eventServiceLogId+'</td>';
		row += '<td>' + createdOn + '</td>';
		row += '<td class="wrapword">' + updatedBy + '</td>';
		row += '<td class="wrapword"><a href="javascript:void(0);" onclick="showProviderResponse('
				+ eventServiceLogId
				+ ',\'TRANSACTION_MONITORING\')">'
				+ intuitionId
				+ '</a></td>';
		row += '<td class="nowrap">'+riskLevel+'</td>';
		row += '<td class="nowrap">'+profileScore+'</td>';
		row += '<td class="nowrap">'+ruleScore+'</td>';
		
		if (statusValue === "NOT_PERFORMED")
			row += '<td class="nowrap">' + notPerformed + '</td>';
		else if (statusValue === "NOT_REQUIRED")
			row += '<td class="nowrap">' + notRequired + '</td>';
		else
			row += getYesOrNoCell(status);
		row += '</tr>'
		if (intuition.passCount === undefined || intuition.passCount === null) {
			if (status) {
				intuition.passCount = 1;
			} else {
				intuition.failCount = 1;
			}
		}
		if (entityType == "ACCOUNT") {
			$("#regDetails_intuition--" + accountId).append(row);
		} else if (entityType == "CONTACT") {
			$("#regDetails_intuition-" + id).append(row);
		}

	}

	if (entityType == "ACCOUNT") {
		noOfRows = countCFXRows("regDetails_intuition--" + accountId);
		totalRecords = Number($('#intuitionTotalRecordsId--' + accountId).val());
		leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
				"leftRecordsIdIntuition--" + accountId);
		updateCFXViewMoreBlock(leftRecords, "viewMore_intuitionId--" + accountId,
				"viewMoreDetails_intuition--" + accountId);

	} else if (entityType == "CONTACT") {
		noOfRows = countCFXRows("regDetails_intuition-" + id);
		totalRecords = Number($('#intuitionTotalRecordsId-' + id).val());
		leftRecords = updateLeftCFXRecords(noOfRows, totalRecords,
				"leftRecordsIdIntuition-" + id);
		updateCFXViewMoreBlock(leftRecords, "viewMore_intuitionId-" + id,
				"viewMoreDetails_intuition-" + id);
	}
}

//AT-4114
function getOnLoadIntuitionAccountViewMore(id) { 
	$("#regDetails_intuition--" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_intuition--" + id);
	var totalRecords = $('#intuitionTotalRecordsId--' + id).val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdIntuition--" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_intuitionId--" + id,
			"viewMoreDetails_intuition--" + id);
}

//AT-4114
function getOnLoadIntuitionContactViewMore(id) {
	$("#regDetails_intuition-" + id).find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_intuition-" + id);
	var totalRecords = $('#intuitionTotalRecordsId-' + id).val();
	var leftRecords = updateCFXLeftRecords(noOfRows, totalRecords,
			"leftRecordsIdIntuition-" + id);
	updateCFXViewMoreBlock(leftRecords, "viewMore_intuitionId-" + id,
			"viewMoreDetails_intuition-" + id);
}