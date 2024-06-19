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
var noOfRowsIntuition=0; //AT-4114
var prevCountIntuition=0;
var clicked= false;
$(document).ready(function() {
	if (document.referrer.endsWith("regReport")) {
		onSubNav('reg-report-sub-nav');
	} else {
		onSubNav('reg-sub-nav');
	}
	getComplianceServiceBaseUrl();
	getAttachDocBaseUrl();
	getViewMoreRecordsize();
	addContactStatusReasonsIntoData();
	addWatchlistIntoData();
	if("PFX" == $("#customerType").val()) {
		isFraudRingPresent();
	}
});

function addContactStatusReasonsIntoData() {
	var newData = [];
	
	$('input[type="checkbox"][name="regDetails_contactStatusReson\\[\\]"]').each(function(){
		newData.push({"name":$(this).val(),"preValue":$(this).prop('checked'),"updatedValue":$(this).prop('checked')});
	});
	$("#regDetails_contactStatusReasons").data('preConStatusReasons',newData);
	var contactStatus = $("input[name='regDetails_contactStatus_radio']:checked").val();
	$("#regDetails_contactStatusReasons").data('preConStatus',contactStatus);
}

function addWatchlistIntoData() {
	var newData = [];
	
	$('input[type="checkbox"][name="regDetails_watchlist\\[\\]"]').each(function(){
		newData.push({"name":$(this).val(),"preValue":$(this).prop('checked'),"updatedValue":$(this).prop('checked')});
	});
	$("#regDetails_watchlists").data('preWatchlists',newData);
}

function resendKyc() {
	$('#gifloaderforkycresend').css('visibility','visible');
	var kyc = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	addField('accountId',accountId,kyc);
	addField('contactId',contactId,kyc);
	addField('orgCode',orgCode,kyc);
	postKycResend(kyc,getComplianceServiceBaseUrl(),getUser());
}

function resendFraugster() {
	$('#gifloaderforresendfraugster').css('visibility','visible');
	var fraugster = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	addField('accountId',accountId,fraugster);
	addField('entityId',contactId,fraugster);
	addField('entityType','CONTACT',fraugster);
	addField('orgCode',orgCode,fraugster);
	addField('contactId',contactId,fraugster);
	postFraugsterResend(fraugster,getComplianceServiceBaseUrl(),getUser());
}

function resendSanction() {
	$('#gifloaderforresendsanction').css('visibility','visible');
	var sanction = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	addField('accountId',accountId,sanction);
	addField('entityId',contactId,sanction);
	addField('entityType','CONTACT',sanction);
	addField('orgCode',orgCode,sanction);
	postSanctionResend(sanction,getComplianceServiceBaseUrl(),getUser());
}

function getRegNextRecord(custType,id) {	
	var searchCriteria = getNextRecord();
	if(searchCriteria != null){
		postRegDetails(searchCriteria,custType,id);
	}		
}

function getRegPreviousRecord(custType,id) {
	var searchCriteria = getPreviousRecord();
	if(searchCriteria != null){
		postRegDetails(searchCriteria,custType,id);
	}
	
}

function getRegLastRecord(custType,id) {	
	var searchCriteria = getLastRecord();
	postRegDetails(searchCriteria,custType,id);
}

function getRegFirstRecord(custType,id) {
	//if(custType != '' && id !=''){
		var searchCriteria = getFirstRecord();
		postRegDetails(searchCriteria,custType,id);
	//}	
}


function postRegDetails(searchCriteria,custType,id) {
	disableAllButtons();
	disableAllPaginationBlock();
	disableAllCheckBlocks();
	$('#custType').val(custType);
	$('#searchSortCriteria').val(getJsonString(searchCriteria));
	$('#contactId').val(id);

	$('#regDetailForm').submit();
}


function postProfileUpdate(request,isAutoUnlock) {
	$("#regDetails_profile_update").attr("disabled", true);
	$("#regDetails_profile_update").addClass("disabled");
	var accountTMFlag = $('#accountTMFlag').val();
	
	$.ajax({
		url : '/compliance-portal/profileUpdate',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforprofileupdate').css('visibility','hidden');
			$('#account_complianceDoneOn').val(data.complianceDoneOn);
			$('#account_registrationInDate').val(data.registrationInDate);
			$('#account_complianceExpiry').val(data.complianceExpiry);
			var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogData.length;			
			Number($('#actLogTotalRecordsId').val(addedRecords));//In Activity log 1 or 2 row inserted so increment by 1 or 2
			getActivities(1,10,Number($('#contact_contactId').val()),false);
			getStatus(request.updatedContactStatus);
			setDataAnonService();
		
			$("#contact_compliacneStatus").text($("#regDetails_contactStatusReasons").data('preConStatus'));
			$("#regDetails_profile_update").attr("disabled", false);
			$("#regDetails_profile_update").removeClass("disabled");
			if(request.updatedContactStatus === 'ACTIVE'){
				decreamentTotalRecords(1);
			}
			var complianceLog = $('#regDetails_compliance_log').val();
			if(complianceLog != undefined && complianceLog !=''){
				$('#regDetails_alert_compliance_log').html(complianceLog);
			}
			if(isAutoUnlock){
				unlockResource();
				applyClassesToAutoUnLockButton();
			}
			$('#regDetails_comments').val('');
			$('#regDetails_compliance_log').val('');
			
			if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)
				updateIntuitionRegStatus(request, getUser(), getComplianceServiceBaseUrl());
			
			populateSuccessMessage("main-content__body","Updated successfully","profile_update_error_field","regDetails_profile_update");
				
		},
		error : function() {
			$('#gifloaderforprofileupdate').css('visibility','hidden');
			$("#regDetails_profile_update").attr("disabled", false);
			$("#regDetails_profile_update").removeClass("disabled");
			populateErrorMessage("main-content__body","Error while updating ","profile_update_error_field","regDetails_profile_update");
		}
	});
}

function postKycResend(request,baseUrl,user) {
	$("#regDetails_kyc_recheck").attr("disabled", true);
	$("#regDetails_kyc_recheck").addClass("disabled");
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/repeatcheck/kyc',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '999'){
				$('#gifloaderforkycresend').css('visibility','hidden');
				$("#regDetails_kyc_recheck").attr("disabled", false);
				$("#regDetails_kyc_recheck").removeClass("disabled");
				populateErrorMessage("main-content__body","Error while resending kyc","kyc_error_field","regDetails_kyc_recheck");
			}else {
				$('#gifloaderforkycresend').css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
				
				var accountTMFlag = $('#accountTMFlag').val();
				var previousKycStatus = $('#kycStatus').val();
				var currentKycStatus = data.summary.status;
				
				Number($('#actLogTotalRecordsId').val(addedRecords));////In Activity log 1 row inserted so increment by 1
				setKycResendResponse(data.summary);
				getActivities(1,10,Number($('#contact_contactId').val()),false);
				$("#regDetails_kyc_recheck").attr("disabled", false);
				$("#regDetails_kyc_recheck").removeClass("disabled");
				
				if(previousKycStatus != currentKycStatus && currentKycStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var kycRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').text();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'kyc';
					var custType = $('#customerType').val();
					addField('accountId', accountId, kycRequest);
					addField('contactId', contactId, kycRequest);
					addField('orgCode', orgCode, kycRequest);
					addField('registrationDateTime',regDateTime,kycRequest);
					addField('kycStatus',currentKycStatus,kycRequest);
					addField('tradeAccountNumber',tradeAccountNumber,kycRequest);
					addField('checkType',checkType,kycRequest);
					addField('custType',custType,kycRequest);
					updateIntuitionRepeatCheckStatus(kycRequest, getUser(), getComplianceServiceBaseUrl());
					var prevKycStatus = document.getElementById("'kycStatus'");
					prevKycStatus.value = currentKycStatus;
				}
				
				populateSuccessMessage("main-content__body","Kyc Repeat Check Successfully done","kyc_error_field","regDetails_kyc_recheck");
			}
		},
		error : function() {
			$('#gifloaderforkycresend').css('visibility','hidden');
			$("#regDetails_kyc_recheck").attr("disabled", false);
			$("#regDetails_kyc_recheck").removeClass("disabled");
			populateErrorMessage("main-content__body","Error while resending kyc","kyc_error_field","regDetails_kyc_recheck");
		}
	});
}
function postFraugsterResend(request,baseUrl,user) {
	disableButton('regDetails_updateFraugster');
	disableButton('regDetails_fraugster_recheck');
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
				$('#gifloaderforresendfraugster').css('visibility','hidden');
				enableButton('regDetails_updateFraugster');
				enableButton('regDetails_fraugster_recheck');
				populateErrorMessage("main-content__body","Error while resending Fraugster","fraugster_error_field","regDetails_fraugster_recheck");
			}else {
				$('#gifloaderforresendfraugster').css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
				
				var accountTMFlag = $('#accountTMFlag').val();
				var previousFraugsterStatus = $('#fraugsterStatus').val();
				var currentFraugsterStatus = data.summary.status;
				
				Number($('#actLogTotalRecordsId').val(addedRecords));//In Activity log 1 row inserted so increment by 1
				data.summary.updatedBy=getJsonObject(getUser()).name;
				if(data.summary.status === 'PASS'){
					data.summary.status=true;
				} else {
					data.summary.status=false;
				}
				setFraugsterResendResponse(data.summary);
				getActivities(1,10,Number($('#contact_contactId').val()),false);
				enableButton('regDetails_updateFraugster');
				enableButton('regDetails_fraugster_recheck');
				
				if(previousFraugsterStatus != currentFraugsterStatus && currentFraugsterStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var fraugsterRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').text();
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
					var prevFraugsterStatus = document.getElementById("fraugsterStatus");
					prevFraugsterStatus.value = currentFraugsterStatus;
				}
				
				populateSuccessMessage("main-content__body","Fraugster Repeat Check Successfully done","fraugster_error_field","regDetails_fraugster_recheck");
			}
		},
		error : function() {
			$('#gifloaderforresendfraugster').css('visibility','hidden');
			enableButton('regDetails_updateFraugster');
			enableButton('regDetails_fraugster_recheck');
			populateErrorMessage("main-content__body","Error while resending Fraugster","fraugster_error_field","regDetails_fraugster_recheck");

		}
	});
}

function postSanctionResend(request,baseUrl,user) {
	disableButton('regDetails_updateSanction');
	disableButton('regDetails_sanction_recheck');
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/repeatcheck/sanction',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '888'){
				$('#gifloaderforresendsanction').css('visibility','hidden');
				enableButton('regDetails_updateSanction');
				enableButton('regDetails_sanction_recheck');
				populateErrorMessage("main-content__body","Error while resending Sanction","sanction_error_field","regDetails_sanction_recheck");
			}else {
				$('#gifloaderforresendsanction').css('visibility','hidden');
				var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
				Number($('#actLogTotalRecordsId').val(addedRecords));//In Activity log 1 row inserted so increment by 1
				
				var accountTMFlag = $('#accountTMFlag').val();
				var prevSanctionStatus = $('#sanctionStatus').val();
				var currentSanctionStatus = data.summary.status;
				
				if(data.summary.status === 'PASS'){
					data.summary.status=true;
				} else {
					data.summary.status=false;
				}
				data.summary.updatedBy=getJsonObject(getUser()).name;
				setSanctionResendResponse(data.summary);
				getActivities(1,10,Number($('#contact_contactId').val()),false);
				enableButton('regDetails_updateSanction');
				enableButton('regDetails_sanction_recheck');
				
				if(prevSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var sanctionRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').text();
					var regDateTime = $('#account_registrationInDate').val();
					var checkType = 'sanction';
					var custType = $('#customerType').val();
					addField('accountId', accountId, sanctionRequest);
					addField('contactId', contactId, sanctionRequest);
					addField('orgCode', orgCode, sanctionRequest);
					addField('registrationDateTime',regDateTime,sanctionRequest);
					addField('sanctionStatus',currentSanctionStatus,sanctionRequest);
					addField('tradeAccountNumber',tradeAccountNumber,sanctionRequest);
					addField('checkType',checkType,sanctionRequest);
					addField('custType',custType,sanctionRequest);
					updateIntuitionRepeatCheckStatus(sanctionRequest, getUser(), getComplianceServiceBaseUrl());
					var prevSanctionStatus = document.getElementById("sanctionStatus");
					prevSanctionStatus.value = currentSanctionStatus;
				}
				
				populateSuccessMessage("main-content__body","Sanction Repeat Check Successfully done","sanction_error_field","regDetails_sanction_recheck");
			}
		},
		error : function() {
			$('#gifloaderforresendsanction').css('visibility','hidden');
			enableButton('regDetails_updateSanction');
			enableButton('regDetails_sanction_recheck');
			populateErrorMessage("main-content__body","Error while resending Sanction","sanction_error_field","regDetails_sanction_recheck");

		}
	});
}

function setRegDetailsResponse(regDetails) {
	
	setAccountDetails(regDetails.account);
	setContactDetails(regDetails.currentContact);
	setCurrentRecord(regDetails.currentRecord);
	setTotalRecords(regDetails.totalRecords);
	setSearchCriteria(regDetails.searchCriteria);
	setDetailsLockResponse(regDetails.user.name,regDetails.locked,regDetails.lockedBy,'lockResource','unlockResource',regDetails.userResourceId);
	setServices(regDetails);
	setWatchlist(regDetails.watchlist.watchlistData);
	setActivityLog(regDetails.activityLogs.activityLogData);
	setContactStatus(getEmptyIfNull(regDetails.currentContact.complianceStatus));
	getStatus(getEmptyIfNull(regDetails.currentContact.complianceStatus));
	setStatusReasonReasonse(regDetails.statusReason.statusReasonData);
	setServicesTotalRecords(regDetails);
	addContactStatusReasonsIntoData();
	addWatchlistIntoData();
}

function setServicesTotalRecords(regDetails){
	
	$("#kycTotalRecordsId").val(regDetails.kyc.kycTotalRecords);
	$("#sanctionTotalRecordsId").val(regDetails.sanction.sanctionTotalRecords);
	$("#fraugsterTotalRecordsId").val(regDetails.fraugster.fraugsterTotalRecords);
	$("#customCheckTotalRecordsId").val(regDetails.internalRule.customCheck.customCheckTotalRecords);
	$("#actLogTotalRecordsId").val(regDetails.activityLogs.totalRecords);
	$("#onfidoTotalRecordsId").val(regDetails.onfido.onfidoTotalRecords);
	
}

function setServices(regDetails) {
	setOtherContacts(regDetails);
	setInternalRuleService(regDetails);
	if(regDetails.kyc !== undefined  && regDetails.kyc !== null){
		setKycResponse(regDetails.kyc);
	}
	if(regDetails.sanction !== undefined  && regDetails.sanction !== null && regDetails.sanction.sanctiondata !== null){
		setSanctionResponse(regDetails.sanction);
	}
	if(regDetails.fraugster !== undefined  && regDetails.fraugster !== null){
		setFraugsterResponse(regDetails.fraugster);
	}
	if(regDetails.onfido !== undefined  && regDetails.onfido !== null && regDetails.onfido.onfidodata !== null){
		setOnfidoResponse(regDetails.onfido);
	}
	if(regDetails.documents !== undefined  && regDetails.documents !== null){
		setAttachedDocument(regDetails.documents);
	}
}

function setOtherContacts(regDetails) {
	if(regDetails.otherContacts !== undefined  && regDetails.otherContacts !== null){
		setOtherContact(regDetails.otherContacts);
	}
}

function setInternalRuleService(regDetails) {
	if(regDetails.internalRule.blacklist !== undefined  && regDetails.internalRule.blacklist !== null){
		setBlacklistResponse(regDetails.internalRule.blacklist);
	}
	setCustomCheck(regDetails.internalRule.customCheck);
}

function setCustomCheck(customCheck){
	$("#regDetails_customchecks").empty();
	$("#regDetails_customchecks").append(getIpCheck(customCheck.ipCheck) 
			+ getGlobalCheck(customCheck.globalCheck) 
			+ getCountryCheck(customCheck.countryCheck));
}

function getActivities(minRecord,maxRecord,contactId,isViewMoreRequest){
	var request = {};
	var custType = $('#customerType').val();
	var comment = $('#regDetails_comments').val();
	var accountId = $('#contact_accountId').val();
	addField("minRecord",minRecord,request);
	addField("maxRecord",maxRecord,request);
	addField("entityId",contactId,request);
	addField("custType",custType,request);
	addField("comment",comment,request);
	addField("accountId",accountId,request);
	getActivityLogs(request,isViewMoreRequest);
}

function getIpCheck(ipCheck){
	if(ipCheck != null){
	var checkedOn = getEmptyIfNull(ipCheck.checkedOn);
	var status = getEmptyIfNull(ipCheck.status);
	var city = getEmptyIfNull(ipCheck.ipCity)
	var country = getEmptyIfNull(ipCheck.ipCountry)
	var distance = getEmptyIfNull(ipCheck.geoDifference)
	var row = '<tr>';
	row += '<td class="nowrap">'+checkedOn+'</td>';
	row +='<td class="nowrap">IP Distance Check</td>';
	row += '<td class="breakword">'+status +city +country +distance+'</td>';
	row += '</tr>';
	return row;
	}
}

function getGlobalCheck(globalCheck){
	if(globalCheck != null){
	var checkedOn = getEmptyIfNull(globalCheck.checkedOn);
	var status = getEmptyIfNull(globalCheck.status);
	var row = '<tr>';
	row += '<td class="nowrap">'+checkedOn+'</td>';
	row +='<td class="nowrap">Global Check</td>';
	row += '<td class="nowrap">'+status+'</td>';
	row += '</tr>';
	return row;
	}
}

function getCountryCheck(countryCheck){
	if(countryCheck != null){
	var checkedOn = getEmptyIfNull(countryCheck.checkedOn);
	var status = getEmptyIfNull(countryCheck.status);
	var row = '<tr>';
	row += '<td class="nowrap">'+checkedOn+'</td>';
	row +='<td class="nowrap">Country Check</td>';
	row += '<td class="nowrap">'+status+'</td>';
	row += '</tr>';
	return row;
	}
}



function setBlacklistResponse(blacklist) {
	$("#regDetails_blacklist").empty();
	var nameStatus = getEmptyIfNull(blacklist.name);
	var ipStatus = getEmptyIfNull(blacklist.ip);
	var emailStatus = getEmptyIfNull(blacklist.email);
	var phoneStatus = getEmptyIfNull(blacklist.phone);
	var blacklistStatus = getEmptyIfNull(blacklist.status);
	var row = '<tr>';
	row +=getYesOrNoCell(nameStatus);
	row +=getYesOrNoCell(phoneStatus);
	row +=getYesOrNoCell(emailStatus);
	row +=getYesOrNoCell(ipStatus);
    row +=getYesOrNoCell(blacklistStatus);
    row += '</tr>'
    $("#regDetails_blacklist").append(row);
	var blacklistPassCount = blacklist.passCount;
	var blacklistFailCount = blacklist.failCount;
    setBlacklistIndicator(blacklistPassCount, blacklistFailCount)
}

function setKycResponse(kyc) {
	$("#regDetails_eid").empty();
	var checkedOn = getEmptyIfNull(kyc.checkedOn);
	var eidCheck = getEmptyIfNull(kyc.eidCheck);
	var verifiactionResult = getEmptyIfNull(kyc.verifiactionResult);
	var referenceId = getEmptyIfNull(kyc.referenceId);
	var status = getEmptyIfNull(kyc.status);
	var dob = getEmptyIfNull(kyc.dob);
	var eventServiceLogId = getEmptyIfNull(kyc.id);
	var row = '<tr>';
    row += '<td>'+checkedOn+'</td>';
    row +=getYesOrNoCell(eidCheck);
    row +='<td class="number">'+verifiactionResult+'</td>';
    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'KYC\')">'+referenceId+'</a></td>';
    row +='<td class="nowrap">'+dob+'</td>';
    row +=getYesOrNoCell(status);
    row += '</tr>'
	$("#regDetails_eid").append(row);
    var kycPassCount = kyc.passCount;
	var kycFailCount = kyc.failCount;
	setKycIndicator(kycPassCount, kycFailCount)
}

function setSanctionResponse(sanction) {
	$("#regDetails_sanction").empty();
		    var updatedOn = getEmptyIfNull(sanction.updatedOn);
			var updatedBy = getEmptyIfNull(sanction.updatedBy);
			var sanctionId = getEmptyIfNull(sanction.sanctionId);
			var ofacList = getEmptyIfNull(sanction.ofacList);
			var worldCheck = getEmptyIfNull(sanction.worldCheck);
			var status = getEmptyIfNull(sanction.status);
			var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
			
			var row = '<tr>';
			row +='<td hidden="hidden" class="center">'+sanction.eventServiceLogId+'</td>';
		    row +='<td>'+updatedOn+'</td>';
		    row +='<td class="nowrap">'+updatedBy+'</td>';
		    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'SANCTION\')">'+sanctionId+'</a></td>';
		    row +='<td class="nowrap">'+ofacList+'</td>';
		    row +='<td class="nowrap">'+worldCheck+'</td>';
		    row +=getYesOrNoCell(status);
		    row += '</tr>'
    if(sanction.passCount === undefined || sanction.passCount === null){
    	if(status){
    		sanction.passCount = 1;
    	} else {
    		sanction.failCount = 1;
    	}
    }
		    
	var sanctionPassCount = sanction.passCount;
	var sanctionFailCount = sanction.failCount;
	setSanctionIndicator(sanctionPassCount,sanctionFailCount)
	$("#regDetails_sanction").append(row);
}

function setSanctionResendResponse(sanction) {
	//$("#regDetails_sanction").empty();
	    var updatedOn = getDateTimeFormat(getEmptyIfNull(sanction.updatedOn));
		var updatedBy = getEmptyIfNull(sanction.updatedBy);
		var sanctionId = getEmptyIfNull(sanction.sanctionId);
		var ofacList = getEmptyIfNull(sanction.ofacList);
		var worldCheck = getEmptyIfNull(sanction.worldCheck);
		var status = getEmptyIfNull(sanction.status);
		var eventServiceLogId = getEmptyIfNull(sanction.eventServiceLogId);
		var row = '<tr>';
		row +='<td hidden="hidden" class="center">'+sanction.eventServiceLogId+'</td>';
	    row +='<td>'+updatedOn+'</td>';
	    row +='<td class="nowrap">'+updatedBy+'</td>';
	    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'SANCTION\')">'+sanctionId+'</a></td>';
	    row +='<td class="nowrap">'+ofacList+'</td>';
	    row +='<td class="nowrap">'+worldCheck+'</td>';
	    row +=getYesOrNoCellWithId(status,'sanction_status');
	    row += '</tr>'
	    var totalRecords = Number($('#sanctionTotalRecordsId').val()) + 1;
		if(sanction.passCount === undefined || sanction.passCount === null){
		if(status){
			sanction.passCount = totalRecords;
		} else {
			sanction.failCount = totalRecords;
		}
		}
		    
		var sanctionPassCount = sanction.passCount;
		var sanctionFailCount = sanction.failCount;
		setSanctionIndicator(sanctionPassCount,sanctionFailCount)
		//$("#regDetails_sanction").append(row);
		$('#regDetails_sanction').prepend(row);
		var noOfRows = countRows("regDetails_sanction");
		//var totalRecords = Number($('#sanctionTotalRecordsId').val()) + 1;
		var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdSanc");
						  updateViewMoreBlock(leftRecords,"viewMore_SancId","viewMoreDetails_sanc");
						  Number($('#sanctionTotalRecordsId').val(totalRecords));
}


function setFraugsterResendResponse(fraugster) {
	//$("#regDetails_fraugster").empty();
	var createdOn = getEmptyIfNull(fraugster.createdOn);
	var updatedBy = getEmptyIfNull(fraugster.updatedBy);
	var fraugsterId = getEmptyIfNull(fraugster.frgTransId);
	var score = getEmptyIfNull(fraugster.score);
	var eventServiceLogId = getEmptyIfNull(fraugster.eventServiceLogId);
	var status = getEmptyIfNull(fraugster.status);
	var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\',\'FraugsterChart\')">';
    row +='<td>'+createdOn+'</td>';
    row +='<td class="nowrap">'+updatedBy+'</td>';
    row +='<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\')">'+fraugsterId+'</a></td>';
    row +='<td class="nowrap" class="number">'+score+'</td>'
    row +=getYesOrNoCellWithId(status,'fraugster_status');
    row += '</tr>'
    	
	
    var totalRecords = Number($('#fraugsterTotalRecordsId').val()) + 1;
	if(fraugster.passCount === undefined || fraugster.passCount === null){
	if(status){
		fraugster.passCount = totalRecords;
		setFraugsterIndicator(totalRecords,0)
	} else {
		fraugster.failCount = totalRecords;
		setFraugsterIndicator(0,totalRecords)
	}
	}
			
	$('#regDetails_fraugster').prepend(row);
	var noOfRows = countRows("regDetails_fraugster");
	//var totalRecords = Number($('#fraugsterTotalRecordsId').val()) + 1;
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdFraug");
					  updateViewMoreBlock(leftRecords,"viewMore_FraugId","viewMoreDetails_fraug");
					  Number($('#fraugsterTotalRecordsId').val(totalRecords));
}


function setFraugsterResponse(fraugster) {
	$("#regDetails_fraugster").empty();
	var createdOn = getEmptyIfNull(fraugster.createdOn);
	var updatedBy = getEmptyIfNull(fraugster.updatedBy);
	var fraugsterId = getEmptyIfNull(fraugster.fraugsterId);
	var score = getEmptyIfNull(fraugster.score);
	var eventServiceLogId = getEmptyIfNull(fraugster.eventServiceLogId);
	var status = getEmptyIfNull(fraugster.status);
	
	var row = '<tr href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\',\'FraugsterChart\')">';
    row +='<td class="nowrap">'+createdOn+'</td>';
    row +='<td class="nowrap">'+updatedBy+'</td>';
    row +='<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\')">'+fraugsterId+'</a></td>';
    row +='<td class="nowrap" class="number">'+score+'</td>'
    row +=getYesOrNoCell(status);
    row += '</tr>'
	$("#regDetails_fraugster").append(row);
}

function setOnfidoResponse(onfido) {
	$("#regDetails_onfido").empty();
		    var updatedOn = getEmptyIfNull(onfido.updatedOn);
			var updatedBy = getEmptyIfNull(onfido.updatedBy);
			var onfidoId = getEmptyIfNull(onfido.sanctionId);
			var reViewed = getEmptyIfNull(onfido.reviewed);
			var status = getEmptyIfNull(onfido.status);
			var eventServiceLogId = getEmptyIfNull(onfido.eventServiceLogId);
			
			var row = '<tr>';
			row +='<td hidden="hidden" class="center">'+onfido.eventServiceLogId+'</td>';
		    row +='<td>'+updatedOn+'</td>';
		    row +='<td class="nowrap">'+updatedBy+'</td>';
		    row +='<td><a href="javascript:void(0);" onclick="showOnfidoProviderResponse('+eventServiceLogId+',\'ONFIDO\')">'+onfidoId+'</a></td>';
		    row +='<td class="nowrap">'+reViewed+'</td>';
		    row +=getYesOrNoCell(status);
		    row += '</tr>'
    if(onfido.passCount === undefined || onfido.passCount === null){
    	if(status){
    		onfido.passCount = 1;
    	} else {
    		onfido.failCount = 1;
    	}
    }
		    
	var onfidoPassCount = onfido.passCount;
	var onfidoFailCount = onfido.failCount;
	setOnfidoIndicator(onfidoPassCount,onfidoFailCount)
	$("#regDetails_onfido").append(row);
}

function setIpResponse(ip) {
	$("#regDetails_ipInfo").empty();
	var checkedOn = getEmptyIfNull(ip.checkedOn);
	var ipAddress = getEmptyIfNull(ip.ipAddress);
	var ipRule =ip.ipRule;
	var row = '<tr>';
	row += '<td class="nowrap">'+checkedOn+'</td>';
    row +='<td>'+ipAddress+'</td>';
    var rules='';
    $.each(ipRule, function(index, rule) {
    	rules += '<li>'+getEmptyIfNull(rule.description)+'</li>'
	});
    row += '<td><ul>'+rules+'</ul></td>'
    row += '</tr>'
	$("#regDetails_ipInfo").append(row);
}

function setOtherContact(otherContacts) {
	$("#regDetails_OtherPeople").empty();
	if(otherContacts.length==0){
		//alert("hello");
		$("#noOhterContact").show();
		$("#noOhterContact").html('<div class="errorMessage">No other contacts found.</div>')
		$("#otherpeople_contacts").hide();
	}
	else{
		$("#noOhterContact").hide();
		$("#otherpeople_contacts").show();
		var rows ='';
		$.each(otherContacts, function(index, otherContact) {
		    var complianceStatus = getEmptyIfNull(otherContact.complianceStatus);
			var name = getEmptyIfNull(otherContact.name);
			var row = '<tr class="talign">';
			if(complianceStatus=='ACTIVE'){
				row +='<td><span class="indicator--positive">'+complianceStatus+'</span>';
			}
			else{
				row +='<td><span class="indicator--negative">'+complianceStatus+'</span>';
			}
		    
		    row +='<td class="valign"><a href="#" onclick="getRegDetails('+otherContact.id+',\''+otherContact.custType+'\',this)">'+name+'</a></td>';
		    row += '</tr>'
		    rows += row;

		});
	
		$("#regDetails_OtherPeople").append(rows);
	}
	//var otherConSize = (otherContacts === undefined || otherContacts === null) ? 0 : otherContacts.length;
	setMoreContactAnchor(otherContacts.length);
	setOtherContactIndicator(otherContacts.length);
	
}

function setMoreContactAnchor(otherConSize) {
	if(otherConSize > 0) {
		$("#regDetails_checkOtherCon").empty();
		$("#regDetails_checkOtherCon").addClass("message--toast");
		var content = '<i class="material-icons">assignment_ind</i>'+
			'<a href="#accordion-section-more-people" class="accordion-trigger" data-accordion-section="accordion-section-more-people">1 more person on this account</a>';
		$("#regDetails_checkOtherCon").append(content);
	} else {
		$("#regDetails_checkOtherCon").empty();
		$("#regDetails_checkOtherCon").removeClass("message--toast");
	}
}

function setWatchlist(watchlistData) {
	$("#regDetails_watchlists").data('preWatchlists',watchlistData);
	$.each(watchlistData, function(index, watchlist) {
		   var name = watchlist.name;
		   var value= watchlist.value;
		   if(value) {
			   if ($("input[value='"+name+"'][name='regDetails_watchlist[]']").prop('checked') === false){
		           $("input[value='"+name+"'][name='regDetails_watchlist[]']").prop('checked', true);
		           $("input[value='"+name+"'][name='regDetails_watchlist[]']").trigger('change');
			   }
		   } else {
			   if ($("input[value='"+name+"'][name='regDetails_watchlist[]']").prop('checked') === true){
				   $("input[value='"+name+"'][name='regDetails_watchlist[]']").prop('checked', false);
		           $("input[value='"+name+"'][name='regDetails_watchlist[]']").trigger('change');
			   }
		   }
		});
}


function setStatusReasonReasonse(reasonse) {
	$("#regDetails_contactStatusReasons").data('preConStatusReasons',reasonse);
	$.each(reasonse, function(index, reason) {
		   var name = reason.name;
		   var value= reason.value;
		   if(value) {
			   if ($("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").prop('checked') === false){
		           $("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").prop('checked', true);
		           $("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").trigger('change');
			   }
		   } else {
			   if ($("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").prop('checked') === true){
				   $("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").prop('checked', false);
		           $("input[value='"+name+"'][name='regDetails_contactStatusReson[]']").trigger('change');
			   }
		   }
		});
}

function executeActions(isAutoUnlock) {
	var preContactStatus = $("#regDetails_contactStatusReasons").data('preConStatus');
	var updatedContactStatus = $("input[name='regDetails_contactStatus_radio']:checked").val();
	var comment = $('#regDetails_comments').val();
	var complianceLog = $('#regDetails_compliance_log').val();
	var isOnQueue = $('#contact_isOnQueue').val();
	
	 if((isNull(comment) || isEmpty(comment)) && (isNull(complianceLog) || isEmpty(complianceLog) ) 
			 && (!isNull(updatedContactStatus) && !isEmpty(updatedContactStatus) && updatedContactStatus != 'ACTIVE')) {
			alert("Please add comment or compliance log");
	}else if( preContactStatus !== updatedContactStatus && updatedContactStatus !== 'ACTIVE' 
		&& $("input[name='regDetails_contactStatusReson[]']:checked").length === 0) {
		alert("Please select atleast one reason for "+updatedContactStatus);
	} else {
		var tbodies = $(".sanction_contact");
		var sanctionPassed = false;
		$.each(tbodies, function(index, tbody) {
			var sanContactStatus = $(tbody).find("tr:first").find('#sanction_status').text();
			if(updatedContactStatus==='ACTIVE' && !sanctionPassed && (
						sanContactStatus === null || sanContactStatus.indexOf("Not Required") !== -1 || sanContactStatus.indexOf("clear") !== -1)){
				sanctionPassed = true;
			}
			
		});
		
		if(sanctionPassed){
			alert("Account cannot be active since last sanction contact check is fail ");
		}else {
			$('#gifloaderforprofileupdate').css('visibility','visible');
			var request={};
			var accountId = Number($('#contact_accountId').val());
			var contactId = Number($('#contact_contactId').val());
			var accountSfId = $('#contact_crmAccountId').val();
			var contactSfId = $('#contact_crmContactId').val();
			var custType = $('#customerType').val();
			var orgCode = $('#account_organisation').text();
			var complianceDoneOn = $('#account_complianceDoneOn').val();
			var registrationInDate = $('#account_registrationInDate').val();
			var complianceExpiry = $('#account_complianceExpiry').val();
			var fraugsterEventServiceLogId = Number($('#fraugster_eventServiceLogId').val());
			var userResourceId = $('#userResourceId').val();
			var tradeAccountNumber = $('#account_tradeAccountNum').text();
			var accountVersion = $('#accountVersion').val();
			addField('accountId',accountId,request);
			addField('contactId',contactId,request);
			addField('accountSfId',accountSfId,request);
			addField('contactSfId',contactSfId,request);
			addField('orgCode',orgCode,request);
			addField('contactStatusReasons',getContactReasons(),request);
			addField('watchlist',getWatchlists(),request);
			addField('overallWatchlistStatus',findInWatchlists(),request);
			addField('preContactStatus',preContactStatus,request);
			addField('updatedContactStatus',updatedContactStatus,request);
			addField('custType',custType,request);
			addField('complianceDoneOn', complianceDoneOn, request);
			addField('registrationInDate', registrationInDate, request);
			addField('complianceExpiry', complianceExpiry, request);
			$("#regDetails_contactStatusReasons").data('preConStatus',updatedContactStatus);
			addField("comment",comment,request);
			addField("complianceLog",complianceLog,request);
			addField('fraugsterEventServiceLogId',fraugsterEventServiceLogId,request);
			addField("isOnQueue",isOnQueue,request);
			addField("userResourceId",userResourceId,request);
			addField("tradeAccountNumber",tradeAccountNumber,request);
			addField("accountVersion",accountVersion,request);
			postProfileUpdate(request,isAutoUnlock);
		}		
	}
}


function findInWatchlists(){
//	var overallWatchlistStatus = false;
	return $('#regDetails_watchlists :checkbox:checked').length > 0;
//		overallWatchlistStatus= true;
//		return overallWatchlistStatus;
//	}
	//return overallWatchlistStatus;
}

function getContactReasons(){
	var newData = [];
	var saveData = [];
	var preValue = $("#regDetails_contactStatusReasons").data('preConStatusReasons');
	
	$.each(preValue,function(index,data){
		newData[index] = {"name":data.name,"preValue":data.preValue,"updatedValue":$("input[value='"+data.name+"'][name='regDetails_contactStatusReson[]']").prop('checked')}
		saveData[index] = {"name":data.name,"preValue":newData[index].updatedValue,"updatedValue":newData[index].updatedValue};
	});
	
	$("#regDetails_contactStatusReasons").data('preConStatusReasons',saveData);
	return newData;
}

function getContactStatus() {
	
}


function getWatchlists(){
	var newData = [];
	var saveData = [];
	var preValue = $("#regDetails_watchlists").data('preWatchlists');
	$.each(preValue,function(index,data){
		newData[index] = {"name":data.name,"preValue":data.preValue,"updatedValue":$("input[value='"+data.name+"'][name='regDetails_watchlist[]']").prop('checked')}
		saveData[index] = {"name":data.name,"preValue":newData[index].updatedValue,"updatedValue":newData[index].updatedValue};
	});
	$("#regDetails_watchlists").data('preWatchlists',saveData);
	return newData;
}

function setContactStatus(status) {
	if(status === 'ACTIVE') {
		$("#contact_contactStatus_active").prop( "checked", true )
		$("#contact_contactStatus_inactive").prop( "checked", false )
		$("#contact_contactStatus_reject").prop( "checked", false )
		$("#contact_contactStatus_active").trigger('change');
	}
	if(status === 'INACTIVE') {
		$("#contact_contactStatus_active").prop( "checked", false )
		$("#contact_contactStatus_inactive").prop( "checked", true )
		$("#contact_contactStatus_reject").prop( "checked", false )
		$("#contact_contactStatus_inactive").trigger('change');
	} 
	if(status === 'REJECTED') {
		$("#contact_contactStatus_active").prop( "checked", false )
		$("#contact_contactStatus_inactive").prop( "checked", false )
		$("#contact_contactStatus_reject").prop( "checked", true )
		$("#contact_contactStatus_reject").trigger('change');
	}
}

function getRegDetails(contactId,custType,anchor) {
	var url = '/compliance-portal/registrationDetails?contactId='+contactId;
	if(searchCriteria === undefined) {
		searchCriteria = getSearchCriteria();
	}
	url +='&searchCriteria='+getJsonString(searchCriteria);
	//$(anchor).attr("href", url); 
	$('#otherContactcustType').val(custType);
	$('#othercontactId').val(contactId);
	$('#otherContactsearchCriteria').val(getJsonString(searchCriteria));
	$('#otherContactForm').submit();
}


function setKycResendResponse(kyc){
	//$("#regDetails_eid").empty();
	var checkedOn = getDateTimeFormat(getEmptyIfNull(kyc.checkedOn));
	var eidCheck = getEmptyIfNull(kyc.eidCheck);
	var verifiactionResult = getEmptyIfNull(kyc.verifiactionResult);
	var referenceId = getEmptyIfNull(kyc.referenceId);
	var status = getEmptyIfNull(kyc.status);
	status = ('PASS'===status) ? true : false;
	var dob = getDashIfNull(kyc.dob);
	var eventServiceLogId = getEmptyIfNull(kyc.id);
	var row = '<tr>';
    row += '<td>'+checkedOn+'</td>';
    row +=getYesOrNoCell(eidCheck);
    row +='<td class="number">'+verifiactionResult+'</td>';
    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'KYC\')">'+referenceId+'</a></td>';
    row +='<td class="nowrap">'+dob+'</td>';
    row +=getYesOrNoCell(status);
    row += '</tr>'
    $('#regDetails_eid').prepend(row);
    var kycPassCount = 0;
	var kycFailCount = 0;
	/*if(eidCheck){
		kycPassCount++;
	} else {
		kycFailCount++;
	}*/
	var totalRecords = Number($('#kycTotalRecordsId').val()) + 1;
	if(status) {
		kycPassCount = totalRecords;
	} else {
		kycFailCount = totalRecords;
	}
	setKycIndicator(kycPassCount, kycFailCount)
	var noOfRows = countRows("regDetails_eid");
	//var totalRecords = Number($('#kycTotalRecordsId').val()) + 1;
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdKyc");
	updateViewMoreBlock(leftRecords,"viewMore_KycId","viewMoreDetails_kyc");
	Number($('#kycTotalRecordsId').val(totalRecords));
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

function setAllIndicators(regDetails) {
	var blacklistPassCount = regDetails.internalRule.blacklist.passCount;
	var blacklistFailCount = regDetails.internalRule.blacklist.failCount;
	var kycPassCount = regDetails.kyc.passCount;
	var kycFailCount = regDetails.kyc.failCount;
	var sanctionPassCount = regDetails.sanction.passCount;
	var sanctionFailCount = regDetails.sanction.failCount;
	var fraugsterPassCount = regDetails.fraugster.passCount;
	var fraugsterFailCount = regDetails.fraugster.failCount;
	var docs = regDetails.documents;
	var documentSize = (docs === undefined || docs === null) ? 0 : docs.length;
	var otherCons = regDetails.otherContacts;
	var otherConSize = (otherCons === undefined || otherCons === null) ? 0 : otherCons.length;
	setBlacklistIndicator(blacklistPassCount,blacklistFailCount);
	setKycIndicator(kycPassCount,kycFailCount);
	setSanctionIndicator(sanctionPassCount,sanctionFailCount);
	setFraugsterIndicator(fraugsterPassCount,fraugsterFailCount);
	setDocuemntIndicator(documentSize);
	setOtherContactIndicator(otherConSize);
	setOnfidoIndicator(onfidoPassCount,onfidoFailCount);
}

function getPositiveIndicator(value,id){
	return '<span id="'+id+'" class="indicator--positive">'+value+'</span>'
}

function getNegativeIndicator(value,id){
	return '<span id="'+id+'" class="indicator--negative">'+value+'</span>'
}

function getIndicator(value,id) {
	return '<span id="'+id+'" class="indicator">'+value+'</span>'
}

function setBlacklistIndicator(passCount,failCount) {
	$('#regDetails_blacklist_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Blacklist';
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_blackNeg');
	} 
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_blackPass');
	} 
	indicator += '</a>';
	$('#regDetails_blacklist_indicatior').append(indicator);
}


function setKycIndicator(passCount,failCount) {
	$('#regDetails_kyc_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>EID';
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_kycPass');
	} 
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_kycNeg');
	} 
	indicator += '</a>';
	$('#regDetails_kyc_indicatior').append(indicator);
}

function setFraugsterIndicator(passCount,failCount) {
	$('#regDetails_fraugster_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>FraudPredict';
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_fraugsterPass');
	} 
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_fraugsterNeg');
	} 
	indicator += '</a>';
	$('#regDetails_fraugster_indicatior').append(indicator);
}

function setSanctionIndicator(passCount,failCount) {
	$('#regDetails_sanction_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Sanctions';
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_sanctionPass');
	} 
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_sanctionNeg');
	} 
	indicator += '</a>';
	$('#regDetails_sanction_indicatior').append(indicator);
}

function setOnfidoIndicator(passCount,failCount) {
	$('#regDetails_onfido_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Onfido';
	if(passCount > 0) {
		indicator += getPositiveIndicator(passCount,'regDetails_onfidoPass');
	} 
	if(failCount > 0) {
		indicator += getNegativeIndicator(failCount,'regDetails_onfidoNeg');
	} 
	indicator += '</a>';
	$('#regDetails_onfido_indicatior').append(indicator);
}

function setOtherContactIndicator(otherContactSize) {
	$('#regDetails_ohterCon_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Other people on this account';
	if(otherContactSize > 0) {
		indicator += getIndicator(otherContactSize,'regDetails_otherConCount');
	} 
	indicator += '</a>';
	$('#regDetails_ohterCon_indicatior').append(indicator);
}

function updateSanction() {
	
	var fieldName = $("input[name='regDetails_updateField']:checked").val();
	var value = $("input[name='regDetails_updateField_Value']:checked").val();
	
	var previousOfacValue = getRegistrationPreviousOfacValue();
	var previousWorldCheckValue = getRegistrationPreviousWorldCheckValue();
	
	if(value === undefined || value === null || fieldName === undefined || fieldName === null){
		alert("Please select required fields for sanction update");
	} else if( (previousOfacValue === value && fieldName === 'ofaclist') ||
					(previousWorldCheckValue === value && fieldName === 'worldcheck')) {
		alert("Please change "+fieldName+" value for Contact and then update sanction");
	} else {
		$('#gifloaderforupdatesanction').css('visibility','visible')
		var request = {};
		var accountId = Number($('#contact_accountId').val());
		var contactId = Number($('#contact_contactId').val());
		var orgCode = $('#account_organisation').text();
		var eventId = Number($("#regDetails_sanction").find('tr:first').find('td:first').text());
		addField("accountId",accountId,request);
		addField('resourceId',contactId,request);
		addField('resourceType','PROFILE',request);
		addField("orgCode",orgCode,request);
		
		if(eventId === "" || eventId === null || eventId === undefined || eventId === 0){
				$('#gifloaderforupdatesanction').css('visibility', 'hidden');
				return populateErrorMessage("main-content__body",
					"Perform repeat check to update","sanction_update_error_field","regDetails_updateSanction");
		}

		var sanctions = [];
		addField("sanctions",sanctions,request);
			sanctions.push({"eventServiceLogId" : eventId,"field": fieldName,"value" : value,"entityId" : contactId,"entityType" : "CONTACT"});
		postUpdateSanction(request,getComplianceServiceBaseUrl(),getUser());
	}
	
	
}


function postUpdateSanction(request,baseUrl,user) {
	disableButton('regDetails_updateSanction');
	disableButton('regDetails_sanction_recheck');
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/repeatcheck/updateSanction',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
				if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '799'){
					$('#gifloaderforupdatesanction').css('visibility','hidden')
					enableButton('regDetails_updateSanction');
					enableButton('regDetails_sanction_recheck');
					populateErrorMessage("main-content__body","Error while updating sanction","sanction_update_error_field","regDetails_updateSanction");
				}else {
					$('#gifloaderforupdatesanction').css('visibility','hidden')
					var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
					Number($('#actLogTotalRecordsId').val(addedRecords));
					getActivities(1,10,Number($('#contact_contactId').val()),false);
					updateSanctionColumn(data);
					enableButton('regDetails_updateSanction');
					enableButton('regDetails_sanction_recheck');
					removeFieldByClass("regDetails_updateSanction","main-content__body");
					hideErrorField("regDetails_updateSanction","sanction_update_error_field");
					
					var accountTMFlag = $('#accountTMFlag').val();
					var prevSanctionStatus = $('#sanctionStatus').val();
					var currentSanctionStatus = data.status;
					
					if (prevSanctionStatus != currentSanctionStatus && currentSanctionStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)) {
						var sanctionRequest = {};
						var accountId = Number($('#contact_accountId').val());
						var contactId = Number($('#contact_contactId').val());
						var tradeAccountNumber = $('#account_tradeAccountNum').text();
						var orgCode = $('#account_organisation').text();
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
						var prevSanctionStatus = document.getElementById("sanctionStatus");
						prevSanctionStatus.value = currentSanctionStatus;
					}
					populateSuccessMessage("main-content__body","Sanction upating successfully done","sanction_update_error_field","regDetails_updateSanction");
				}
			},
		error : function() {
			$('#gifloaderforupdatesanction').css('visibility','hidden')
			enableButton('regDetails_updateSanction');
			enableButton('regDetails_sanction_recheck');
			populateErrorMessage("main-content__body","Error while updating sanction","sanction_update_error_field","regDetails_updateSanction");
		}
	});
}

function updateSanctionColumn(data){
	var fieldName = $("input[name='regDetails_updateField']:checked").val();
	var value = $("input[name='regDetails_updateField_Value']:checked").val();
	if(fieldName.toLowerCase() === "ofaclist") {
		 $('#regDetails_sanction tr:nth-child(1) td:nth-child(5)').text(value);
	} 
	if(fieldName.toLowerCase() === "worldcheck"){
		 $('#regDetails_sanction tr:nth-child(1) td:nth-child(6)').text(value);
	}
	if(data.status === 'PASS'){
		 $('#regDetails_sanction tr:nth-child(1) td:nth-child(7)').removeClass('no-cell').addClass('yes-cell').html('<i class="material-icons">check</i>');
		 var totalRecords = Number($('#sanctionTotalRecordsId').val());
		 setSanctionIndicator(totalRecords,0);
	} else {
		 $('#regDetails_sanction tr:nth-child(1) td:nth-child(7)').removeClass('yes-cell').addClass('no-cell').html('<i class="material-icons">clear</i>');
		 var totalRecords = Number($('#sanctionTotalRecordsId').val());
		 setSanctionIndicator(0,totalRecords);
	}
	
}

function lockResource() {
	/*$('#gifloader').css('display','block')*/
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId === null || userResourceLockId === undefined ||  userResourceLockId === "") {
		var resourceId = $('#contact_contactId').val();
		var resourceType = 'CONTACT';
		var request = getLockResourceRequest(resourceId,resourceType);
		postLockOrUnlock(request);
		console.log(request);
		
	}

}


function unlockResource(){
	
	var userResourceLockId = getUserResourceIdVal();
	if(userResourceLockId !== null && userResourceLockId !== undefined && userResourceLockId !== ""){
		var resourceId = $('#contact_contactId').val();
		var resourceType = 'CONTACT';
		var request = getUnlockResourceRequest(resourceId,resourceType,userResourceLockId);
		postLockOrUnlock(request);
		console.log(request);
	}
}

function lockResourceByAccountID() {
	var userResourceLockId = getUserResourceIdVal();
	if (userResourceLockId === null || userResourceLockId === undefined ||  userResourceLockId === "" ) {
		var resourceId = $('#contact_accountId').val();
		var resourceType = 'ACCOUNT';
		var request = getLockResourceRequest(resourceId,resourceType);
		postLockOrUnlockByAccountId(request);
		console.log(request);
		
	}

}


function unlockResourceByAccountId(){
	var userResourceLockId = getUserResourceIdVal();
	if(userResourceLockId !== null && userResourceLockId !== undefined && userResourceLockId !== ""){
		var resourceId = $('#contact_accountId').val();
		var resourceType = 'ACCOUNT';
		var request = getUnlockResourceRequest(resourceId,resourceType,userResourceLockId);
		postLockOrUnlockByAccountId(request);
		console.log(request);
	}
}

function isRecordLocked(response) {
	return response.lock;
}

function getUserNameOfLockedRecord(response) {
	return response.userName;
}

function createDefaultActivityLogRequest(entityId){
	var request = {};
	addField("minRecord",1,request);
	addField("maxRecord",10,request);
	addField("entityId",entityId,request);
	return request;
}

function getActivityLogs(request,isViewMoreRequest) {
	$.ajax({
		url : '/compliance-portal/regActivites',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if (isViewMoreRequest){
			    setActivityLogViewMore(data.activityLogData);
			}else{
				setActivityLog(data.activityLogData);
				setViewMoreActLogData();
				$("#regDetails_profile_update").attr("disabled", false);
				$("#regDetails_profile_update").removeClass("disabled");
			}
		},
		error : function() {
			alert('Error while fetching data');
			$("#regDetails_profile_update").attr("disabled", false);
			$("#regDetails_profile_update").removeClass("disabled");
		}
	});	
	}
function postKycMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden');
			setKycMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
		}
	});
}

function postSanctionMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden')
			setSanctionMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
			
			
		}
	});
}

function postFraugsterMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden')
			setFraugsterMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
			
		}
	});
}

function postOnfidoMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden')
			setOnfidoMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
			
			
		}
	});
}

function postCustomCheckMoreDetails(request) {
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden')
			setCustomCheckMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
		}
	});
}

function postIntuitionMoreDetails(request) { //AT-4114
	$.ajax({
		url : '/compliance-portal/viewMoreDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			$('#gifloaderforviewmore').css('visibility','hidden')
			setIntuitionMoreDetailsResponse(data.services);
		},
		error : function() {
			$('#gifloaderforviewmore').css('visibility','hidden')
			
		}
	});
}

function setIntuitionMoreDetailsResponse(intuition) { //AT-4114

	for(var i=0 ; i< intuition.length; i++) {
	var createdOn = getEmptyIfNull(intuition[i].createdOn);
	var updatedBy = getEmptyIfNull(intuition[i].updatedBy);
	var intuitionId = getEmptyIfNull(intuition[i].correlationId);
	//var score = getEmptyIfNull(intuition[i].score);
	var profileScore = getEmptyIfNull(intuition[i].profileScore);
	var ruleScore = getEmptyIfNull(intuition[i].ruleScore);
	var eventServiceLogId = getEmptyIfNull(intuition[i].id);
	var status = getEmptyIfNull(intuition[i].status);
	var statusValue = getEmptyIfNull(intuition[i].statusValue);
	var notPerformed = "NOT_PERFORMED";
	var notRequired = "NOT_REQUIRED";
	var riskLevel = getSingleDashIfNull(intuition[i].riskLevel);
	var row = '<tr  href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'TRANSACTION_MONITORING\')">';
    row +='<td>'+createdOn+'</td>';
    row +='<td class="wrapword">'+updatedBy+'</td>';
    row +='<td class="wrapword"><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'TRANSACTION_MONITORING\')">'+intuitionId+'</a></td>';
    row +='<td class="nowrap" class="number">'+riskLevel+'</td>';
    row +='<td class="nowrap" class="number">'+profileScore+'</td>'
    row +='<td class="nowrap" class="number">'+ruleScore+'</td>'
    if (statusValue === "NOT_PERFORMED")
		row += '<td class="nowrap">' + notPerformed + '</td>';
	else if (statusValue === "NOT_REQUIRED")
		row += '<td class="nowrap">' + notRequired + '</td>';
	else
			row +=getYesOrNoCellWithId(status,'intuition_status');
	row += '</tr>'
    
    $("#regDetails_intuition").append(row);	
	}
	var noOfRows = countRows("regDetails_intuition");
	var totalRecords = Number($('#intuitionTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords,"viewMore_IntuitionId","viewMoreDetails_intuition");
	
}

function setCustomCheckMoreDetailsResponse(customCheck){
	for(var i=0 ; i< customCheck.length; i++) {
	$("#regDetails_customchecks").append(getIpCheck(customCheck[i].ipCheck) 
			+ getGlobalCheck(customCheck[i].globalCheck) 
			+ getCountryCheck(customCheck[i].countryCheck));
	}
	var noOfRows = countRows("regDetails_customchecks");
	var totalRecords = Number($('#customCheckTotalRecordsId').val());
	var leftRecords = updateLeftRecordsForCusChk(noOfRows,totalRecords,"leftRecordsIdCustomChk");
	updateViewMoreBlock(leftRecords,"viewMore_CustomChkId","viewMoreDetails_CustomChk");
	
}

function setKycMoreDetailsResponse(kycViewMore){
	
	for(var i=0 ; i< kycViewMore.length ; i++) {
		var checkedOn = getEmptyIfNull(kycViewMore[i].checkedOn);
		var eidCheck = getEmptyIfNull(kycViewMore[i].eidCheck);
		var verifiactionResult = getEmptyIfNull(kycViewMore[i].verifiactionResult);
		var referenceId = getEmptyIfNull(kycViewMore[i].referenceId);
		var status = getEmptyIfNull(kycViewMore[i].status);
		var statusValue = getEmptyIfNull(kycViewMore[i].statusValue);
		var dob = getDashIfNull(kycViewMore[i].dob);
		var eventServiceLogId = getEmptyIfNull(kycViewMore[i].id);
		var row = '<tr>';
	    row += '<td>'+checkedOn+'</td>';
	    /**Set status as Not_Required - Saylee*/
	    if(statusValue === "Not Required"){
	    	row +='<td>Not Required</td>';
	    } else {
	    	row +=getYesOrNoCell(eidCheck);
	    }
	    	    
	    row +='<td class="number">'+verifiactionResult+'</td>';
	    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'KYC\')">'+referenceId+'</a></td>';
	    row +='<td class="nowrap">'+dob+'</td>';
	    /**Set status as Not_Required - Saylee*/
	    if(statusValue === "Not Required"){
	    	row +='<td>Not Required</td>';
	    } else {
	    	row +=getYesOrNoCell(status);
	    }
	    
	    row += '</tr>'
		$("#regDetails_eid").append(row);
	    var kycPassCount = 0;
		var kycFailCount = 0;
		if(eidCheck){
			kycPassCount++;
		} else {
			kycFailCount++;
		}
		if(status) {
			kycPassCount++;
		} else {
			kycFailCount++;
		}
	}
	var noOfRows = countRows("regDetails_eid");
	var totalRecords = Number($('#kycTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdKyc");
	updateViewMoreBlock(leftRecords,"viewMore_KycId","viewMoreDetails_kyc");
	
}

function setSanctionMoreDetailsResponse(sanction) {

	for(var i=0 ; i< sanction.length ; i++) {
		    var updatedOn = getEmptyIfNull(sanction[i].updatedOn);
			var updatedBy = getEmptyIfNull(sanction[i].updatedBy);
			var sanctionId = getEmptyIfNull(sanction[i].sanctionId);
			var ofacList = getEmptyIfNull(sanction[i].ofacList);
			var worldCheck = getEmptyIfNull(sanction[i].worldCheck);
			var status = getEmptyIfNull(sanction[i].status);
			var statusValue = getEmptyIfNull(sanction[i].statusValue);
			var eventServiceLogId = getEmptyIfNull(sanction[i].eventServiceLogId);
			var row = '<tr>';
			row +='<td hidden="hidden" class="center">'+sanction[i].eventServiceLogId+'</td>';
		    row +='<td>'+updatedOn+'</td>';
		    row +='<td class="nowrap">'+updatedBy+'</td>';
		    row +='<td><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'SANCTION\')">'+sanctionId+'</a></td>';
		    row +='<td class="nowrap">'+ofacList+'</td>';
		    row +='<td class="nowrap">'+worldCheck+'</td>';
		    /**Set status as Not_Required - Saylee*/
		    if(statusValue === "Not Required"){
		    	row +='<td>Not Required</td>';
		    } else {	
		    	row +=getYesOrNoCell(status);
		    }
		    row += '</tr>'
    if(sanction.passCount === undefined || sanction.passCount === null){
    	if(status){
    		sanction.passCount = 1;
    	} else {
    		sanction.failCount = 1;
    	}
    }
		    
	$("#regDetails_sanction").append(row);
	
	}
	var noOfRows = countRows("regDetails_sanction");
	var totalRecords = Number($('#sanctionTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdSanc");
	updateViewMoreBlock(leftRecords,"viewMore_SancId","viewMoreDetails_sanc");
	
}

function setFraugsterMoreDetailsResponse(fraugster) {

	for(var i=0 ; i< fraugster.length ; i++) {
	var createdOn = getEmptyIfNull(fraugster[i].createdOn);
	var updatedBy = getEmptyIfNull(fraugster[i].updatedBy);
	var fraugsterId = getEmptyIfNull(fraugster[i].fraugsterId);
	var score = getEmptyIfNull(fraugster[i].score);
	var eventServiceLogId = getEmptyIfNull(fraugster[i].id);
	var status = getEmptyIfNull(fraugster[i].status);
	var row = '<tr  href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\',\'FraugsterChart\')">';
    row +='<td>'+createdOn+'</td>';
    row +='<td class="nowrap">'+updatedBy+'</td>';
    row +='<td class=""><a href="javascript:void(0);" onclick="showProviderResponse('+eventServiceLogId+',\'FRAUGSTER\')">'+fraugsterId+'</a></td>';
    row +='<td class="nowrap" class="number">'+score+'</td>'
    row +=getYesOrNoCellWithId(status,'fraugster_status');
    row += '</tr>'
    $("#regDetails_fraugster").append(row);	
	}
	var noOfRows = countRows("regDetails_fraugster");
	var totalRecords = Number($('#fraugsterTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdFraug");
	updateViewMoreBlock(leftRecords,"viewMore_FraugId","viewMoreDetails_fraug");
	
}

function setOnfidoMoreDetailsResponse(onfido) {

	for(var i=0 ; i< onfido.length ; i++) {
		    var updatedOn = getEmptyIfNull(onfido[i].updatedOn);
			var updatedBy = getEmptyIfNull(onfido[i].updatedBy);
			var onfidoId = getEmptyIfNull(onfido[i].onfidoId);
			var reViewed = getDashIfNull(onfido[i].reviewed);
			var status = getEmptyIfNull(onfido[i].status);
			var statusValue = getEmptyIfNull(onfido[i].status);
			var eventServiceLogId = getEmptyIfNull(onfido[i].eventServiceLogId);
			var row = '<tr>';
			row +='<td hidden="hidden" class="center">'+onfido[i].eventServiceLogId+'</td>';
		    row +='<td>'+updatedOn+'</td>';
		    row +='<td class="nowrap">'+updatedBy+'</td>';
		    row +='<td><a href="javascript:void(0);" onclick="showOnfidoProviderResponse('+eventServiceLogId+',\'ONFIDO\')">'+onfidoId+'</a></td>';
		    row +='<td class="nowrap">'+reViewed+'</td>';
		    if(statusValue === "Not Required"){
		    	row +='<td>Not Required</td>';
		    } else {	
		    	row +=getYesOrNoCell(status);
		    }
		    row += '</tr>'
    if(onfido.passCount === undefined || onfido.passCount === null){
    	if(status){
    		onfido.passCount = 1;
    	} else {
    		onfido.failCount = 1;
    	}
    }
		    
	$("#regDetails_onfido").append(row);
	
	}
	var noOfRows = countRows("regDetails_onfido");
	var totalRecords = Number($('#onfidoTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdOnfido");
	updateViewMoreBlock(leftRecords,"viewMore_OnfidoId","viewMoreDetails_onfido");
	
}

function setActivityLogViewMore(activities) {
	
	var rows ='';
	$.each(activities, function(index, activityData) {
		    var createdOn = getEmptyIfNull(activityData.createdOn);
		    var createdBy = getEmptyIfNull(activityData.createdBy);
		    var activity = getEmptyIfNull(activityData.activity);
		    var activityType = getEmptyIfNull(activityData.activityType);
		    var comment = getEmptyIfNull(activityData.comment);
		    var tradeContractNumber = '---'; //AT-1794 - Snehaz
			var row = '<tr class="talign">';
		    row +='<td>'+createdOn+'</td>';
		    row +='<td>'+tradeContractNumber+'</td>';
		    row +='<td class="nowrap">'+createdBy+'</td>';
		    row +='<td><ul><li>'+activity+'</li></ul></td>';
		    row +='<td >'+activityType+'</td>';
		    if(comment == '')
		    	row += '<td style="font-weight:bold" class = "center">-</td>';
		    else row += '<td class="breakword">' + comment + '</td>';
		    row += '</tr>'
		    rows += row;
		});
	$("#activityLog").append(rows);
	var noOfRows = countRows("activityLog");
	var totalRecords = Number($('#actLogTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");
	
}

$("#regDetails_activitylog_indicatior").click(function(){
	
	$("#activityLog").find('tr').slice(10).remove();
	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");
	$("#viewMoreAuditTrailDetails_ActLog").hide();

});

$("#regDetails_customcheck_indicatior").click(function(){
	
	$("#regDetails_customchecks").find('tr').slice(3).remove();
	var noOfRows = countRows("regDetails_customchecks");
	var totalRecords = $('#customCheckTotalRecordsId').val();
	var leftRecords = updateLeftRecordsForCusChk(noOfRows,totalRecords,"leftRecordsIdCustomChk");
	updateViewMoreBlock(leftRecords,"viewMore_CustomChkId","viewMoreDetails_CustomChk");
	
});

$("#regDetails_fraugster_indicatior").click(function(){
	clicked=false;
	prevCountfrg=0;// AT-3325
	noOfRowsFrg=0;
	$("#regDetails_fraugster").find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_fraugster");
	var totalRecords = $('#fraugsterTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdFraug");
	updateViewMoreBlock(leftRecords,"viewMore_FraugId","viewMoreDetails_fraug");
	//fraugsterChartData();
});

$("#regDetails_sanction_indicatior").click(function(){
	clicked= false;
	prevCountSanction=0;// AT-3325
	noOfRowsSanction=0;
	$("#regDetails_sanction").find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_sanction");
	var totalRecords = $('#sanctionTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdSanc");
	updateViewMoreBlock(leftRecords,"viewMore_SancId","viewMoreDetails_sanc");
	
});

$("#regDetails_kyc_indicatior").click(function(){
	clicked= false;
	prevCountEID=0;// AT-3325
	noOfRowsEid=0;
	$("#regDetails_eid").find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_eid");
	var totalRecords = $('#kycTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdKyc");
	updateViewMoreBlock(leftRecords,"viewMore_KycId","viewMoreDetails_kyc");
	
});

$("#regDetails_onfido_indicatior").click(function(){
	
	$("#regDetails_onfido").find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_onfido");
	var totalRecords = $('#onfidoTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdOnfido");
	updateViewMoreBlock(leftRecords,"viewMore_OnfidoId","viewMoreDetails_onfido");
	
});

$("#regDetails_intuition_indicatior").click(function(){ //AT-4114
	clicked=false;
	prevCountIntuition=0;
	noOfRowsIntuition=0;
	$("#regDetails_intuition").find('tr').slice(1).remove();
	var noOfRows = countRows("regDetails_intuition");
	var totalRecords = $('#intuitionTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecords,"viewMore_IntuitionId","viewMoreDetails_intuition");
}); 

function viewMoreDetails(serviceType,id,totalRecordsId,leftRecordsId) {
	
	var viewMore = {};
	var noOfRows = countRows(id);
	if(id=="regDetails_fraugster")  // AT-3325
		 noOfRowsFrg=countRows("regDetails_fraugster");
		if(id=="regDetails_sanction")
		 noOfRowsSanction=countRows("regDetails_sanction");
	   if(id=="regDetails_eid")
		 noOfRowsEid=countRows("regDetails_eid");
		 if(id=="regDetails_intuition") //AT-4114
		 	noOfRowsIntuition=countRows("regDetails_intuition");
		if((clicked==false )|| (clicked== true && noOfRowsFrg>prevCountfrg)||(clicked==true && noOfRowsSanction>prevCountSanction)||(clicked==true && noOfRowsEid>prevCountEID) || (clicked== true && noOfRowsIntuition>prevCountIntuition))
		{
		clicked=true;
	var totalRecords =Number($("#"+totalRecordsId).val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,leftRecordsId);
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	var clientType = $('#account_clientType').text();
	var minViewRecord = calculateMinRecord(noOfRows);
	var maxViewRecord = calculateMaxRecord(minViewRecord);
	var accountId = Number($('#contact_accountId').val()); //AT-4170
	addField('entityId',contactId,viewMore);
	addField('noOfDisplayRecords',noOfRows,viewMore);
	addField('totalRecords',totalRecords,viewMore);
	addField('minViewRecord',minViewRecord,viewMore);
	addField('maxViewRecord',maxViewRecord,viewMore);
	addField('leftRecords',leftRecords,viewMore);
	addField('organisation',orgCode,viewMore);
	addField('clientType',clientType,viewMore);
	addField('serviceType',serviceType,viewMore);
	addField('entityType',"CONTACT",viewMore);
	addField('accountId',accountId,viewMore) //AT-4170
	if (serviceType == "KYC"){
		$('#gifloaderforviewmore').css('visibility','visible')
		prevCountEID=noOfRowsEid+1;
		postKycMoreDetails(viewMore);
	}else if (serviceType == "SANCTION"){
		$('#gifloaderforviewmore').css('visibility','visible')
	     prevCountSanction=noOfRowsSanction+1; 
		postSanctionMoreDetails(viewMore);
	}else if (serviceType == "FRAUGSTER"){
		prevCountfrg=noOfRowsFrg+1;
		postFraugsterMoreDetails(viewMore);
	}else if (serviceType == "CUSTOMCHECK"){
		var minViewRecordCustChk = calculateMinRecordCustChk(noOfRows);
		var maxViewRecordCustChk = calculateMaxRecordCustChk(minViewRecordCustChk);
		addField('minViewRecord',minViewRecordCustChk,viewMore);
		addField('maxViewRecord',maxViewRecordCustChk,viewMore);
		postCustomCheckMoreDetails(viewMore);
	}else if (serviceType == "TRANSACTION_MONITORING"){ //AT-4114
		prevCountIntuition=noOfRowsIntuition+1;
		postIntuitionMoreDetails(viewMore);
	}else if (serviceType == "ACTIVITYLOG"){
		getActivities(minViewRecord,maxViewRecord,contactId,true);
	}else if (serviceType == "ONFIDO"){
		$('#gifloaderforviewmore').css('visibility','visible')
		postOnfidoMoreDetails(viewMore);
	}
		}
}
function showProviderResponse(eventServiceLogId,serviceType,chartDisplayFlag,iD){
	var getProviderResponseRequest = {};
	addField('eventServiceLogId',eventServiceLogId,getProviderResponseRequest);
	addField('serviceType',serviceType,getProviderResponseRequest);
	getProviderResponse(getProviderResponseRequest,chartDisplayFlag,iD);
}
/*FOR Onfido response*/
function showOnfidoProviderResponse(eventServiceLogId,serviceType,chartDisplayFlag,iD){
	var getProviderResponseRequest = {};
	addField('eventServiceLogId',eventServiceLogId,getProviderResponseRequest);
	addField('serviceType',serviceType,getProviderResponseRequest);
	getOnfidoProviderResponse(getProviderResponseRequest,chartDisplayFlag,iD);
}

function calculateMinRecord(noOfRows){
    
    return noOfRows + 1;
}
function calculateMinRecordCustChk(noOfRows){
	var minViewRecordCustChk = noOfRows/3;
	return minViewRecordCustChk;
}

function calculateMaxRecordCustChk(minViewRecordCustChk){
	var viewMoreConfSize =  Number(getViewMoreRecordsize());
	var totalRecords =Number($("#customCheckTotalRecordsId").val());
	var noOfRows = countRows("regDetails_customchecks");
	var leftRecords = totalRecords - noOfRows/3 ;
	if(leftRecords < viewMoreConfSize){
		return minViewRecordCustChk +  leftRecords;
	} else {
		return minViewRecordCustChk +  Number(getViewMoreRecordsize());
	}
}
function updateLeftRecords(noOfRows,totalRecords,id){
	
	var leftRecord = totalRecords - noOfRows;
	if(leftRecord < 0){
		leftRecord = 0;
		 $("#"+id).html("( "+leftRecord+" LEFT )");
	}else {
		$("#"+id).html("( "+leftRecord+" LEFT )");
	}
     
    return leftRecord;
}
function updateLeftRecordsForCusChk(noOfRows,totalRecords,id){
	
	var leftRecord = totalRecords - (noOfRows/3);
	if(leftRecord < 0){
		leftRecord = 0;
		 $("#"+id).html("( "+leftRecord+" LEFT )");
	}else {
		$("#"+id).html("( "+leftRecord+" LEFT )");
	}
     
    return leftRecord;
}

function calculateMaxRecord(minViewRecord){
    
    return minViewRecord + Number(getViewMoreRecordsize()) - 1;
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
    /*var message = "Total Row Count: " + totalRowCount;
    message += "\nRow Count: " + rowCount;
    alert(message);*/
}

function viewMoreLoadData() {
	
	$("#fxticket_indicatior").trigger('click');
	$("#client-wallets").trigger('click');
	var noOfRows = countRows("regDetails_eid");
	var totalRecords = $('#kycTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdKyc");
	updateViewMoreBlock(leftRecords,"viewMore_KycId","viewMoreDetails_kyc");
	
	var noOfRowsSanc = countRows("regDetails_sanction");
	var totalRecordSanc = $('#sanctionTotalRecordsId').val();
	var leftRecordSanc = updateLeftRecords(noOfRowsSanc,totalRecordSanc,"leftRecordsIdSanc");
	updateViewMoreBlock(leftRecordSanc,"viewMore_SancId","viewMoreDetails_sanc");
	
	var noOfRowFraug = countRows("regDetails_fupdateViewMoreBlockraugster");
	var totalRecordsFraug = $('#fraugsterTotalRecordsId').val();
	var leftRecordsFraug = updateLeftRecords(noOfRowFraug,totalRecordsFraug,"leftRecordsIdFraug");
	updateViewMoreBlock(leftRecordsFraug,"viewMore_FraugId","viewMoreDetails_fraug");
	
	var noOfRowsCusChk = countRows("regDetails_customchecks");
	var totalRecordsCusChk = $('#customCheckTotalRecordsId').val();
	var leftRecordsCusChk = updateLeftRecordsForCusChk(noOfRowsCusChk,totalRecordsCusChk,"leftRecordsIdCustomChk");
	updateViewMoreBlock(leftRecordsCusChk,"viewMore_CustomChkId","viewMoreDetails_CustomChk");
	
	var noOfRowsOnfido = countRows("regDetails_onfido");
	var totalRecordOnfido = $('#onfidoTotalRecordsId').val();
	var leftRecordOnfido = updateLeftRecords(noOfRowsOnfido,totalRecordOnfido,"leftRecordsIdOnfido");
	updateViewMoreBlock(leftRecordSanc,"viewMore_onfidoId","viewMoreDetails_onfido");
	
	var noOfRowsIntuition = countRows("regDetails_intuition"); //AT-4114
	var totalRecordIntuition = $('#intuitionTotalRecordsId').val();
	var leftRecordIntuition = updateLeftRecords(noOfRowsIntuition,totalRecordIntuition,"leftRecordsIdIntuition");
	updateViewMoreBlock(leftRecordIntuition,"viewMore_IntuitionId","viewMoreDetails_intuition");
}

function viewMoreLoadDataActLog(){
	
	var noOfRowsActLog = countRows("activityLog");
	var totalRecordsActLog = $('#actLogTotalRecordsId').val();
	var leftRecordsActLog = updateLeftRecords(noOfRowsActLog,totalRecordsActLog,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecordsActLog,"viewMore_ActLogId","viewMoreDetails_ActLog");
}
function viewMoreResetData(){
	
	$("#regDetails_eid").find('tr').slice(1).remove();
	$("#regDetails_sanction").find('tr').slice(1).remove();
	$("#regDetails_fraugster").find('tr').slice(1).remove();
	$("#regDetails_customchecks").find('tr').slice(3).remove();
	$("#regDetails_onfido").find('tr').slice(1).remove();
}

function viewMoreResetActLogData(){
	
	$("#activityLog").find('tr').slice(10).remove();
}

function getStatus(status){
	var data='';
	$("#contact_status").empty();
	if(status==='INACTIVE'|| status==='REJECTED'){
		data='<span class="indicator--negative" id="contact_compliacneStatus">'+status+'</span>';
	}
	else if(status==='ACTIVE') {
		data='<span class="indicator--positive" id="contact_compliacneStatus">'+status+'</span>';
	}
	else {
		status='INACTIVE';
		data='<span class="indicator--negative" id="contact_compliacneStatus">'+status+'</span>';
	}
	$("#contact_status").append(data);
}

function setViewMoreActLogData(){
	
	var noOfRows = countRows("activityLog");
	var totalRecords = Number($('#actLogTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");
	Number($('#actLogTotalRecordsId').val(totalRecords));
}

function showIpDetails(data) {
	alert("hello");
}

function resendBlacklist(entityType) {
	$('#gifloaderforBlacklistresend').css('visibility','visible');
	var blacklist = {};
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	 
	addField('accountId',accountId,blacklist);
	addField('contactId',contactId,blacklist);
	addField('orgCode',orgCode,blacklist);
	addField('entityType',entityType,blacklist);
	if("CONTACT" === entityType)
		addField('entityId',contactId,blacklist);
	else 
		addField('entityId',accountId,blacklist);
	postBlacklistResend(blacklist,getComplianceServiceBaseUrl(),getUser());
}

function postBlacklistResend(request,baseUrl,user) {
	$("#regDetails_blacklist_recheck").attr("disabled", true);
	$("#regDetails_blacklist_recheck").addClass("disabled");
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/repeatcheck/blacklist',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data : getJsonString(request),
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
				var accountTMFlag = $('#accountTMFlag').val();
				var previousBlacklistStatus = $('#blacklistStatus').val();
				var currentBlacklistStatus = data.summary.status;
				
				setBlacklistResendResponse(data.summary);
				getActivities(1,10,Number($('#contact_contactId').val()),false);
				$("#regDetails_blacklist_recheck").attr("disabled", false);
				$("#regDetails_blacklist_recheck").removeClass("disabled");
				
				if(previousBlacklistStatus != currentBlacklistStatus && currentBlacklistStatus != 'SERVICE_FAILURE' && (accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4)){
					var blacklistRequest = {};
					var accountId = Number($('#contact_accountId').val());
					var contactId = Number($('#contact_contactId').val());
					var tradeAccountNumber = $('#account_tradeAccountNum').text();
					var orgCode = $('#account_organisation').text();
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
					var prevBlacklistStatus = document.getElementById("blacklistStatus");
					prevBlacklistStatus.value = currentBlacklistStatus;
				}
				
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

function setBlacklistResendResponse(blacklist){
	var name = getEmptyIfNull(blacklist.name);
	var phone = getEmptyIfNull(blacklist.phone);
	var email = getEmptyIfNull(blacklist.email);
	var domain = getEmptyIfNull(blacklist.domain);
	var ip = getEmptyIfNull(blacklist.ip);
	var status = getEmptyIfNull(blacklist.status);
	var failCount = 0;
	var passCount = 0;
	
	status = ('PASS'===status) ? true : false;
	var row = '<tr>';
	if(name === 'NOT_REQUIRED')
		row += '<td class="nowrap center">Not Required</td>';
	else if(name === 'false'){
		row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		passCount++;
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
	else {
	       row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i><br>('+blacklist.phoneMatchedData+ ')</td>';
	       failCount++;
	 }
	if(email === 'NOT_REQUIRED'|| email ==="")
		row += '<td class="nowrap center">Not Required</td>';
	else if(email === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.emailMatchedData+ ')</td>';
	      failCount++;
	}
	if(domain === 'NOT_REQUIRED'|| domain ==="")
		row += '<td class="nowrap center">Not Required</td>';
	else if(domain === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.domainMatchedData+ ')</td>';
	      failCount++;
	}
	if(ip === 'NOT_REQUIRED'|| ip ==="")
		row += '<td class="nowrap center">Not Required</td>';
	else if(ip === 'false'){
		   row += '<td class="yes-cell"><i class="material-icons">check</i></td>';
		   passCount++;
	   }
	else {
	      row += '<td class="no-cell wordwrap"><i class="material-icons">clear</i>('+blacklist.ipMatchedData+ ')</td>';
	      failCount++;
	}
    row += getYesOrNoCell(status);
    row += '</tr>'
    setBlacklistIndicator(passCount, failCount);
    
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
    $("#regDetails_blacklist").empty();
    $("#regDetails_blacklist").append(row);
   
}

function fraugsterChartData(originalJSON) {
	var originalJSONParsed = {};

	originalJSONParsed = JSON.parse(originalJSON);

	if (originalJSONParsed.hasOwnProperty('decisionDrivers') == ''
			|| originalJSONParsed['status'] == 'SERVICE_FAILURE'
			|| originalJSONParsed['status'] == 'NOT_REQUIRED') {
		$("#boxpanel-space-before").css('display', 'none');
	} else {
		$("#boxpanel-space-before").css('display', 'block');
		var chart = AmCharts.makeChart("pfx-fraugster-chart", {
			"creditsPosition" : "bottom-right",
			"type" : "serial",
			"theme" : "light",
			"rotate" : true,
			"dataProvider" : generateFraugsterChartData(originalJSONParsed),
			"gridAboveGraphs" : true,
			"startDuration" : 1,
			"graphs" : [ {
				"balloonText" : "[[category]]: <b>[[value]]</b>",
				"fillAlphas" : 0.8,
				"lineAlpha" : 0.2,
				"type" : "column",
				"valueField" : "featureImportance",
				"fillColorsField" : "colour"
			} ],
			"chartCursor" : {
				"categoryBalloonEnabled" : false,
				"cursorAlpha" : 0,
				"zoomable" : false
			},
			"categoryField" : "measure",
			"categoryAxis" : {
				"gridPosition" : "start",
				"gridAlpha" : 0.1,
				"tickLength" : 5
			}
		});
		//$("a[title='JavaScript charts']").remove();
	}
}

function getRegistrationPreviousOfacValue(){
	var sanctionRowForOfac = $("#regDetails_sanction tr:first");
	return sanctionRowForOfac.find("td:nth-child(5)").text();
}

function getRegistrationPreviousWorldCheckValue(){
	var sanctionRowForWC = $("#regDetails_sanction tr:first");
	return sanctionRowForWC.find("td:nth-child(6)").text();
}

/*For Onfido Update changes*/
function updateOnfido() {
	var fieldName = "reviewed";
	var value = $("input[name='regDetails_updateOnfidoField_Value']:checked").val();
	var previousReviewedValue = getRegistrationPreviousReviewed();
	if(value === undefined || value === null || fieldName === undefined || fieldName === null){
		alert("Please select required fields for onfido update");
	} else if(previousReviewedValue === value && fieldName === 'reviewed') {
		alert("Please change "+fieldName+" value for Contact and then update onfido");
	} else {
		$('#gifloaderforupdateonfido').css('visibility','visible')
		var request = {};
		var accountId = Number($('#contact_accountId').val());
		var contactId = Number($('#contact_contactId').val());
		var orgCode = $('#account_organisation').text();
		var eventId = Number($("#regDetails_onfido").find('tr:first').find('td:first').text());
		addField("accountId",accountId,request);
		addField('resourceId',contactId,request);
		addField('resourceType','PROFILE',request);
		addField("orgCode",orgCode,request);
		if(eventId === "" || eventId === null || eventId === undefined || eventId === 0){
				$('#gifloaderforupdateonfido').css('visibility', 'hidden');
				return populateErrorMessage("main-content__body",
					"Perform repeat check to update","onfido_update_error_field","regDetails_updateOnfido");
		}
		var onfido = [];
		addField("onfido",onfido,request);
			onfido.push({"eventServiceLogId" : eventId,"field": fieldName,"value" : value,"entityId" : contactId,"entityType" : "CONTACT"});
		postUpdateOnfido(request,getComplianceServiceBaseUrl(),getUser());
	}
}

function postUpdateOnfido(request,baseUrl,user) {
	disableButton('regDetails_updateOnfido');
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/updateOnfido',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
				if(data.responseCode !== null && data.responseCode !== undefined && data.responseCode === '799'){
					$('#gifloaderforupdateonfido').css('visibility','hidden')
					enableButton('regDetails_updateOnfido');
					populateErrorMessage("main-content__body","Error while updating onfido","onfido_update_error_field","regDetails_updateOnfido");
				}else {
					$('#gifloaderforupdateonfido').css('visibility','hidden')
					var addedRecords = Number($('#actLogTotalRecordsId').val()) + data.activityLogs.activityLogData.length;
					Number($('#actLogTotalRecordsId').val(addedRecords));
					getActivities(1,10,Number($('#contact_contactId').val()),false);
					updateOnfidoColumn(data);
					enableButton('regDetails_updateOnfido');
					removeFieldByClass("regDetails_updateOnfido","main-content__body");
					hideErrorField("regDetails_updateOnfido","onfido_update_error_field");
					populateSuccessMessage("main-content__body","Onfido upating successfully done","onfido_update_error_field","regDetails_updateOnfido");
				}
			},
		error : function() {
			$('#gifloaderforupdateonfido').css('visibility','hidden')
			enableButton('regDetails_updateOnfido');
			populateErrorMessage("main-content__body","Error while updating onfido","onfido_update_error_field","regDetails_updateOnfido");
		}
	});
}

function updateOnfidoColumn(data){
	var fieldName = "reviewed";
	var value = $("input[name='regDetails_updateOnfidoField_Value']:checked").val();
	if(fieldName.toLowerCase() === "reviewed") {
		 $('#regDetails_onfido tr:nth-child(1) td:nth-child(5)').text(value);
	}
	if(data.status === 'PASS'){
		 $('#regDetails_onfido tr:nth-child(1) td:nth-child(6)').removeClass('no-cell').addClass('yes-cell').html('<i class="material-icons">check</i>');
		 var totalRecords = Number($('#onfidoTotalRecordsId').val());
		 setOnfidoIndicator(totalRecords,0);
	} else {
		 $('#regDetails_onfido tr:nth-child(1) td:nth-child(6)').removeClass('yes-cell').addClass('no-cell').html('<i class="material-icons">clear</i>');
		 var totalRecords = Number($('#onfidoTotalRecordsId').val());
		 setOnfidoIndicator(0,totalRecords);
	}	
}

function getRegistrationPreviousReviewed(){
	var onfidoRowForReviewed = $("#regDetails_onfido tr:first");
	return onfidoRowForReviewed.find("td:nth-child(5)").text();
}