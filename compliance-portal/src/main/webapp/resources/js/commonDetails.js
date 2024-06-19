/**
 * functions added by Vishal J to disable specific pagination For any details
 * page, if user is on first record then first 2 pagination buttons should be
 * disabled and similarly if user is on last record then last 2 pagination
 * buttons should be disabled
 * 
 * So for every details page this page is loaded commonly so we implement that
 * logic here. - Changes done by Vishal J to resolve AT - 312
 */
var intuitionFlag = false;
var applicationDate;

$(document).ready(function() {
	disablePagination();
	if($('#isRecordLocked').val()== "true"){
		setDataAnonService();
		approvePoiService();
	}
	//AT-2388
	restrictActionsOnRegDetailsPage();
});

function disablePagination() {
	var currentRecord = getTextById("currentRecord");
	var totalRecords = getTextById("totolRecords");
	if (currentRecord === 1) {
		disableFirstRecordButton();
		disablePreviousRecordButton();
	} else if (currentRecord === totalRecords) {
		disableNextRecordButton();
		disableLastRecordButton();
	}
}

function getSearchCriteria() {
	return getJsonObject(getValueById('searchCriteria'));
}

function getUser() {
	return $("#userInfo").val();
}

function getNextRecord() {
	$(".accordion--quick-controls .quick-control__control--close-all").click();
	var searchCriteria = getSearchCriteria();
	var lastRecord = searchCriteria.page.totalRecords
	var currentRecord = searchCriteria.page.currentRecord;
	if ((currentRecord + 1) <= lastRecord) {
		searchCriteria.page.currentRecord = currentRecord + 1;
	} else {
		searchCriteria = null;
	}
	return searchCriteria;
}

function getPreviousRecord() {
	$(".accordion--quick-controls .quick-control__control--close-all").click();
	var searchCriteria = getSearchCriteria();
	var currentRecord = searchCriteria.page.currentRecord;
	if ((currentRecord - 1) > 0) {
		searchCriteria.page.currentRecord = currentRecord - 1;
	} else {
		searchCriteria = null;
	}
	return searchCriteria;
}

function getLastRecord() {
	$(".accordion--quick-controls .quick-control__control--close-all").click();
	var searchCriteria = getSearchCriteria();
	var currentRecord = searchCriteria.page.totalRecords;
	searchCriteria.page.currentRecord = currentRecord;
	return searchCriteria;
}

function getFirstRecord() {
	$(".accordion--quick-controls .quick-control__control--close-all").click();
	var searchCriteria = getSearchCriteria();
	searchCriteria.page.currentRecord = 1;
	return searchCriteria;
}

function getLockResourceRequest(resourceId, resourceType) {
	var request = {};
	addField("resourceId", Number(resourceId), request);
	addField("resourceType", resourceType, request);
	addField("lock", true, request);
	return request;
}

function getUnlockResourceRequest(resourceId, resourceType, userResourceLockId) {
	var request = {};
	addField("resourceId", Number(resourceId), request);
	addField("resourceType", resourceType, request);
	addField("lock", false, request);
	addField("userResourceId", Number(userResourceLockId), request);
	return request;
}

function postLockOrUnlock(request) {
	$
			.ajax({
				url : '/compliance-portal/lockResource',
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					if (request.lock) {
						var lockresponse = data;
						setLockResponse(lockresponse.userResourceId,
								lockresponse.name);
						$('#isRecordLocked').val(true);
					} else {
						setUnlockResponse();
						$('#isRecordLocked').val(false);
					}

				},
				error : function() {
					alert('Error while fetching data');
				}
			});
}

function postLockOrUnlockByAccountId(request) {
	$
			.ajax({
				url : '/compliance-portal/lockResourceMultiContact',
				type : 'POST',
				data : getJsonString(request),
				contentType : "application/json",
				success : function(data) {
					if (request.lock) {
						var lockresponse = data;
						setLockResponse(lockresponse.userResourceId,
								lockresponse.name);
						$('#isRecordLocked').val(true);
					} else {
						setUnlockResponse();
						$('#isRecordLocked').val(false);
					}
				},
				error : function() {
					alert('Error while fetching data');
				}
			});
}

function setLockResponse(userResourceId, lockedBy) {
	var user = getUserObject();
	if (user.name === lockedBy) {
		setUserResourceIdVal(userResourceId);
		$("#lock")
				.prepend(
						'<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> You own(s) this record</span>');
		enableAllLockBasedButtons();
		setDataAnonService();
		approvePoiService();	
		$('#appliedLock').hide();
	} else {
		$("#lock").empty();
		$("#lock")
				.append(
						'<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> '
								+ lockedBy + ' own(s) this record</span>');
		disableAllButtons();
		enableButton($('#fraud_ring_button_id').val());
	}
}

function setUnlockResponse() {
	$("#ownRecord").remove();
	setUserResourceIdVal('');
	disableAllButtons();
	$('#appliedLock').show();
	enableButton($('#fraud_ring_button_id').val());
	disableButton('forget_me_button');
}

function getUserResourceIdVal() {
	return $('#userResourceId').val();
}

function setUserResourceIdVal(userResourceId) {
	$('#userResourceId').val(userResourceId);
}

function setDetailsLockResponse(userName, locked, lockedBy, lockResFunName,unlockResFunName, userResourceId) {
	$("#lock").empty();
	if (locked && userName === lockedBy) {
		$("#lock")
				.append(
						'<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> You own(s) this record</span>');
		$("#lock")
				.append(
						'<div id="toggle-edit-lock" class="toggle f-right" data-ot-show-on-load="Lock this record to own it and update"><a href="#" id="toggle-record-lock" onclick="'
								+ lockResFunName
								+ '()" class="toggle__option--on " data-ot="Lock this record to own it"><i class="material-icons">lock_outline</i></a><a href="#" id="toggle-record-unlock" onclick="'
								+ unlockResFunName
								+ '()" class="toggle__option " data-ot="Unlock this record for others"><i class="material-icons">lock_open</i></a></div>');
		setUserResourceIdVal(userResourceId);
	} else if (locked) {
		$("#lock")
				.append(
						'<span id="ownRecord" class="space-next toggle-support-text"><i class="material-icons">person_pin</i> '
								+ lockedBy + ' own(s) this record</span>');
		setUserResourceIdVal(userResourceId);
	} else {
		$("#lock")
				.append(
						'<div id="toggle-edit-lock" class="toggle f-right" data-ot-show-on-load="Lock this record to own it and update"><a href="#" id="toggle-record-lock" onclick="'
								+ lockResFunName
								+ '()" class="toggle__option " data-ot="Lock this record to own it"><i class="material-icons">lock_outline</i></a><a href="#" id="toggle-record-unlock" onclick="'
								+ unlockResFunName
								+ '()" class="toggle__option--on " data-ot="Unlock this record for others"><i class="material-icons">lock_open</i></a></div>');
		setUserResourceIdVal(null);
	}
	if ((locked && userName !== lockedBy) || !locked) {
		disableAllButtons();
	} else {
		enableAllButtons();
	}
}

function setContactDetails(contact) {
	setTextById('contact_compliacneStatus',
			getEmptyIfNull(contact.complianceStatus));
	setTextById('contact_name', getEmptyIfNull(contact.name));
	setTextById('contact_email', getEmptyIfNull(contact.email));
	setTextById('contact_occupation', getEmptyIfNull(contact.occupation));
	setTextById('contact_countryOfResidence',
			getEmptyIfNull(contact.countryOfResidence));
	setTextById('contact_FurtherClient_Address',
			getEmptyIfNull(contact.address));
	setTextById('contact_FurtherClient_regIn', getEmptyIfNull(contact.regIn));
	setTextById('contact_FurtherClient_regComplete',
			getEmptyIfNull(contact.regComplete));
	setTextById('contact_FurtherClient_phone', getEmptyIfNull(contact.phone));
	setTextById('contact_FurtherClient_mobile', getEmptyIfNull(contact.mobile));
	setTextById('contact_FurtherClient_email', getEmptyIfNull(contact.email));
	setTextById('contact_FurtherClient_nationality',
			getEmptyIfNull(contact.nationality));
	setValueById('contact_FurtherClient_ipAddress',
			getEmptyIfNull(contact.ipAddress));
	setValueById('contact_contactId', getEmptyIfNull(contact.id));
	setValueById('contact_crmAccountId', getEmptyIfNull(contact.crmAccountId));
	setValueById('contact_crmContactId', getEmptyIfNull(contact.crmContactId));
	setValueById('contact_accountId', getEmptyIfNull(contact.accountId));
	setValueById('contact_tradeContactId',
			getEmptyIfNull(contact.tradeContactId))
	// contact_FurtherClient_usClient
	if (contact.isUsClient) {
		setTextById('contact_FurtherClient_usClient', 'Yes');
	} else {
		setTextById('contact_FurtherClient_usClient', 'No');
	}
}

function setAccountDetails(account) {
	setTextById('account_tradeAccountNum',
			getEmptyIfNull(account.tradeAccountNum));
	setTextById('account_source', getEmptyIfNull(account.source));
	setTextById('account_clientType', getEmptyIfNull(account.clientType));
	setTextById('account_currencyPair', getEmptyIfNull(account.currencyPair));
	setTextById('account_estimatedTxnValue',
			getEmptyIfNull(account.estimTransValue));
	setTextById('account_purposeOfTxn', getEmptyIfNull(account.purposeOfTrans));
	setTextById('account_organisation', getEmptyIfNull(account.orgCode));
	setTextById('account_sourceOfFunds', getEmptyIfNull(account.sourceOfFund));
	setTextById('account_FurtherClient_regMode',
			getEmptyIfNull(account.regMode));
	setTextById('account_FurtherClient_serviceReq',
			getEmptyIfNull(account.serviceRequired));
	// setTextById('account_FurtherClient_browserType',getEmptyIfNull(account.browserType));
	// setTextById('account_FurtherClient_cookiesInfo',getEmptyIfNull(account.cookieInfo));
	setTextById('account_FurtherClient_refferalText',
			getEmptyIfNull(account.refferalText));
	setTextById('account_FurtherClient_affiliateName',
			getEmptyIfNull(account.affiliateName));
}

function setCurrentRecord(currentRecord) {
	setTextById('currentRecord', getEmptyIfNull(currentRecord));
}

function setTotalRecords(totolRecords) {
	setTextById('totolRecords', getEmptyIfNull(totolRecords));
}

function setSearchCriteria(searchCriteria) {
	setValueById('searchCriteria', getEmptyIfNull(searchCriteria));
}

function setAttachedDocument(documents) {
	$("#attachDoc").empty();
	var rows = '';
	$.each(documents, function(index, document) {
		var createdOn = getEmptyIfNull(document.createdOn);
		var createdBy = getEmptyIfNull(document.createdBy);
		var documentName = getEmptyIfNull(document.documentName);
		var documentType = getEmptyIfNull(document.documentType);
		var documentUrl = getEmptyIfNull(document.url);
		var note = getEmptyIfNull(document.note);
		var row = '<tr class="talign">';
		row += '<td class="nowrap">' + createdOn + '</td>';
		row += '<td>' + createdBy + '</td>';
		row += '<td><a href="' + documentUrl + '">' + documentName
				+ '</a></td>';
		row += '<td>' + documentType + '</td>';
		row += '<td  class="wrap-cell">' + note + '</td>';
		row += '</tr>'
		rows += row;
	});
	var documentSize = (documents === undefined || documents === null) ? 0
			: documents.length;
	setDocuemntIndicator(documentSize);
	$("#attachDoc").append(rows);
}

function setUploadedDocument(document, request, attachDocBodyId,isDocumentAuthorized) {
	var fileNames = request.fileNames;
	var fileName = fileNames[0];
	var documentType = request.documentType;
	var downloadUrls = document.downloadURLList;
	var downloadUrl = downloadUrls[0];
	var note = request.note;
	if (note === undefined || note === null) {
		note = '-';
	}
	var newDate = new Date();
	var day = newDate.getDate();
	var month = newDate.getMonth() + 1;
	var year = newDate.getFullYear();
	var hours = newDate.getHours();
	var minutes = newDate.getMinutes();
	var seconds = newDate.getSeconds();
	var milliSec = newDate.getMilliseconds();
	var createdBy = request.createdBy;
	month = '' + month;
	if (month.length < 2) {
		month = '0' + month;
	}
	day = '' + day;
	if (day.length < 2) {
		day = '0' + day;
	}
	hours = '' + hours;
	if (hours.length < 2) {
		hours = '0' + hours;
	}
	minutes = '' + minutes;
	if (minutes.length < 2) {
		minutes = '0' + minutes;
	}
	seconds = '' + seconds;
	if (seconds.length < 2) {
		seconds = '0' + seconds;
	}
	milliSec = '' + milliSec;
	if (milliSec.length < 2) {
		milliSec = '0' + milliSec;
	}
	var createdOn = day + '/' + month + '/' + year + ' ' + hours + ':'
			+ minutes + ':' + seconds;
	var row = '<tr class="talign">';
	row += '<td class="nowrap">' + createdOn + '</td>';
	row += '<td>' + createdBy + '</td>';
	row += '<td><a href="' + downloadUrl + '">' + fileName + '</a></td>';
	row += '<td>' + documentType + '</td>';
	row += '<td  class="wrap-cell">' + note + '</td>';
	if(isDocumentAuthorized){
		row += '<td class="wrap-cell"><input type="checkbox" class="enableDocButton"  name="" value="" checked="checked"></td>';
	}else{
		row += '<td class="wrap-cell"><input type="checkbox" class="enableDocButton"  name="" value=""></td>';
	}
	row += '</tr>'
	$("#" + attachDocBodyId).prepend(row);
	 var rowCount = $('#attachDoc').children('tr').length;
	 var hasFooter = $('#docTableId > tfoot').length;
	 if(hasFooter == 0 && rowCount > 0){
		 var footer= '';
		 footer += '<tfoot> <tr><td class="footerHorizontalLine" colspan="6">';
		 footer += '<span id="document_error_field" class="form__field-error" hidden="hidden">Some kind of error message. ';
		 footer += '<a href="#" class="">Back to summary</a></span><input id="attach_document_approve_button_id" type="button" ';
		 footer += 'class="button--primary f-right button--disabled" value="APPLY CHANGES" onclick="approveDoc();"> ';
		 footer += '</td></tr></tfoot>';
		 $(footer).insertAfter('#docTableId tbody');				
	 }
	var preVal = 0;
	if ($("#docConunt").length) {
		preVal = Number($("#docConunt").text());
	}
	setDocuemntIndicator(preVal + 1);
}

function setDocuemntIndicator(doucmentSize) {
	$('#doc_indicatior').empty();
	var indicator = '<a href="#"><i class="material-icons">add</i>Attached documents';
	if (doucmentSize > 0) {
		indicator += getIndicator(doucmentSize, 'docConunt');
	}
	indicator += '</a>';
	$('#doc_indicatior').append(indicator);
}

function getIndicator(value, id) {
	return '<span id="' + id + '" class="indicator">' + value + '</span>'
}

function getYesOrNoCell(status) {
	if (status) {
		return '<td class="yes-cell"><i class="material-icons">check</i></td>';
	} else {
		return '<td class="no-cell"><i class="material-icons">clear</i></td>';
	}
}

function getYesOrNoCellWithId(status,id) {
	if (status) {
		return '<td id="'+id+'" class="yes-cell"><i class="material-icons">check</i></td>';
	} else {
		return '<td id="'+id+'" class="no-cell"><i class="material-icons">clear</i></td>';
	}
}

function uploadDocument(crmAccountId, crmContactId, textNoteId, fileChooseId,
		attachDocBodyId, documentTypeName, attachDocumentButtonID, orgCode,gifloaderfordocumentloader) {
	var category = $('#documentCategory').val();
	var isAuthorized = $('input[name="isDocumentAuthorized"]:checked');
	var isDocumentAuthorized=false;
	if(isAuthorized != undefined && isAuthorized[0] !=undefined  && isAuthorized[0].checked){
		isDocumentAuthorized =true;
	}
	var docTypeValue = $("input[name=" + documentTypeName + "]:checked").val();
	if ((docTypeValue === null || docTypeValue === undefined || docTypeValue === 'Please select')) {
		alert("Please select Document Type ");
		return;
	}
	var status = validateFileExtension(document.getElementById(fileChooseId));
	if (!status) { // if false means validation fail so return
		return;
	}
	var id = crmContactId;
	var accid = crmAccountId;
	var reader = new FileReader();
	var noteVal = document.getElementById(textNoteId).value;
	var e = $("input[name=" + documentTypeName + "]:checked").val();
	var f = document.getElementById(fileChooseId).files;
	var mergeVal = f[0].name + "~" + e + "~" + noteVal;
	reader.fileName = mergeVal;
	reader.onload = function(t) {
		var str = t.target.result
		var encoded = str.toString();
		var enc = encoded.split(',');
		var split_data = t.target.fileName;
		var spltedData = split_data.split('~');
		var document = {};
		addField('source', 'compliance', document);
		addField('orgCode', orgCode, document);
		addField('isChunked', false, document);
		addField('fileNames', [ spltedData[0] ], document);
		addField('documentType', spltedData[1], document);
		addField('note', spltedData[2], document);
		addField('base64EncodedString', [ enc[1] ], document);
		addField('crmContactID', id, document);
		addField('crmAccountID', accid, document);
		addField('withDetails', false, document);
		addField('createdBy', getUserObject().name, document);
		addField('isAuthorized', [isDocumentAuthorized], document);
		addField('category', [category], document);
		postAttachDocument(document, getAttachDocBaseUrl(), attachDocBodyId,attachDocumentButtonID, textNoteId, 
				fileChooseId,gifloaderfordocumentloader,isDocumentAuthorized);
	}
	reader.readAsDataURL(f[0]);
}

function postAttachDocument(request, baseUrl, attachDocBodyId,
		attachDocumentButtonID, textNoteId, fileChooseId,gifloaderfordocumentloader,isDocumentAuthorized) {
	$('#'+gifloaderfordocumentloader).css('visibility', 'visible');
	disableButton(attachDocumentButtonID);
	var docUploadReq = getJsonString(request);
	console.log(docUploadReq);
	$.ajax({
		url : baseUrl + '/es-restfulinterface/rest/documentService/uploadDocsCompliance',
		type : 'POST',
		data : docUploadReq,
		contentType : "text/plain",
		success : function(data) {
			$('#'+gifloaderfordocumentloader).css('visibility', 'hidden');
			setUploadedDocument(data, request, attachDocBodyId,isDocumentAuthorized);
			enableButton(attachDocumentButtonID);
			$('input[id^="rad-doctype"]:checked').prop("checked", false);
			$('p[id^="docTypeSelection"]').removeClass("clickpanel__trigger singlelist__chosen--on")
			.addClass("clickpanel__trigger singlelist__chosen");
			$('p[id^="docTypeSelection"]').text("Please select");
			$('#' + textNoteId).val("");
			$('#' + fileChooseId).val("");
			$('#isDocumentAuthorized').prop('checked', false);
			//document.location.reload(); //it will refresh the page after uploading the document
		},
		error : function() {
			$('#'+gifloaderfordocumentloader).css('visibility', 'hidden');
			alert('Error while attaching document');
			$('input[id^="rad-doctype"]:checked').prop("checked", false);
			$('p[id^="docTypeSelection"]').removeClass("clickpanel__trigger singlelist__chosen--on").addClass("clickpanel__trigger singlelist__chosen");
			$('p[id^="docTypeSelection"]').text("Please select");
			$('#' + textNoteId).val("");
			$('#' + fileChooseId).val("");
			$('#isDocumentAuthorized').prop('checked', false);
			enableButton(attachDocumentButtonID);
		}
	});
}

function setActivityLog(activities) {
	$("#activityLog").empty();
	var rows = '';
	applicationDate = activities[0].createdOn;
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
		var comment = getEmptyIfNull(activityData.comment);
						var tradeContractNumber = (activityData.contractNumber === "" || activityData.contractNumber === null) ? 
								($('#payment_transNum').text() === "") ? '---'
								: $('#payment_transNum').text()
								: activityData.contractNumber; //AT-1794 - Snehaz
		var row = '<tr class="talign">';
		row += '<td>' + createdOn + '</td>';
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
}

function setAddedActivityLog(activities) {
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
		var comment = getEmptyIfNull(activityData.comment);
		var tradeContractNumber = ($('#payment_transNum').text() === "")?'---':$('#payment_transNum').text(); //AT-1794 - Snehaz
		var row = '<tr class="talign">';
		row += '<td>' + createdOn + '</td>';
		row +='<td>'+tradeContractNumber+'</td>';
		row += '<td class="nowrap">' + createdBy + '</td>';
		row += '<td><ul><li>' + activity + '</li></ul></td>';
		row += '<td >' + activityType + '</td>';
		if(comment == '')
			row += '<td style="font-weight:bold" class = "center">-</td>';
		else row += '<td class="breakword">' + comment + '</td>';
		row += '</tr>'
		var count = $('#activityLog tr').length;
		if (count > 0) {
			$("#activityLog tr:first").before(row);
		} else {
			$("#activityLog").append(row);
		}
	});
}

function getProviderResponse(request,flag,id) {
	$.ajax({
		url : '/compliance-portal/getProviderResponse',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
				showProviderResponseJson(data,flag,id);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}
/*************************************************************************/

function showProviderResponsePopup(data) {
	var jsonString = data.responseJson;
	var jsonPretty = JSON.stringify(JSON.parse(jsonString), null, 2);
	$("#providerResponseJson").text(jsonPretty);
	$("#providerResponseJson").attr('readonly', true);
	$("#ProviderResponsepopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons :[{
			text: 'Json',
			click : function() {
				showProviderResponseJson(data);
			}
		},
		{
			text: 'Table',
			click : function() {
				processJson($.parseJSON(jsonString));
			}
		},
		{	
			text: 'Close',
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
}

function showProviderResponseJson(data,flag,id)
{	
	var jsonString = data.responseJson;
	var jsonPretty = JSON.stringify(JSON.parse(jsonString), null, 2);
	$("#ProviderResponsepopups").html('<pre>'+jsonPretty+'</pre>');
	$("#providerResponseJson").attr('readonly', true);
	if(flag === "FraugsterChart") {
		fraugsterChartData(jsonString,id);
	}
	else {
		showProviderResponsePopup(data);
	}
}

function getSortObject() {
	var sort = {};
	var isAscend = "false";// descending order. Latest record first
	var prevAscendVal = $("#transactDate").val();
	if (prevAscendVal === isAscend) {
		$("#transactDate").val(true);
	} else {
		$("#transactDate").val(false);
	}
	addField("isAscend", $("#transactDate").val(), sort);
	addField("fieldName", fieldName, sort);
	return sort;
}

function getComplianceServiceBaseUrl() {
	return $("#complianceServiceUrl").val();
}

function getAttachDocBaseUrl() {
	return $("#enterpriseUrl").val();
}
function getViewMoreRecordsize() {
	return $("#viewMoreRecords").val();
}

function getTitanBaseUrl() {
	return $("#titanUrl").val();
}

function getUserObject() {
	return getJsonObject($('#userInfo').val());
}

function getPositiveIndicator(value, id) {
	return '<span id="' + id + '" class="indicator--positive">' + value
			+ '</span>'
}

function getNegativeIndicator(value, id) {
	return '<span id="' + id + '" class="indicator--negative">' + value
			+ '</span>'
}

function getIndicator(value, id) {
	return '<span id="' + id + '" class="indicator">' + value + '</span>'
}

function validateFileExtension(fld) {
	if (fld.files.length != 0 && fld.files[0].size > 6000000) {
		alert("Maximum File size to Upload is 6 MB");
		var field = document.getElementById(fld.id);
		field.value = field.defaultValue;
		return false;
	} else if (fld.files.length = 0 || fld.files[0] == undefined) {
		alert("Sorry,Could'nt be upload as your file is empty");
		var field = document.getElementById(fld.id);
		field.value = field.defaultValue;
		return false;
	}
	else {
		if (!/(\.pdf|\.png|\.jpg|\.jpeg|\.doc|\.docx|\.xml|\.txt|\.pst|\.ost|\.xlsx|\.html|\.htm|\.msg)$/i
				.test(fld.value)) {
			alert("Invalid File type,supports only pdf/png/jpg/jpeg/doc/docx/xml/txt/pst/ost/xlsx/html/htm/msg");
			var field = document.getElementById(fld.id);
			field.value = field.defaultValue;
			fld.focus();
			return false;
		}
		return true;
	}
}

function decreamentTotalRecords(count) {
	var searchCriteria = getSearchCriteria();
	searchCriteria.page.totalRecords = searchCriteria.page.totalRecords - count;
	setSearchCriteria(getJsonString(searchCriteria));
}

function redirectToQueue() {
	var searchCriteria = getValueById('searchCriteria');
	setValueById('ququefilter', searchCriteria);
	$("#redirectQueueForm").submit();
}

function showDeviceInfo(accountId) {
	var getDeviceInfoRequest = {};
	addField('accountId', accountId, getDeviceInfoRequest);
	getDeviceInfo(getDeviceInfoRequest);
}

function getDeviceInfo(request) {
	$.ajax({
		url : '/compliance-portal/getDeviceInfo',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showDeviceInfoPopup(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function decodeMachineInfo(encodedString){
	if(encodedString != null){
		return window.atob(encodedString);
	}else {return "Not Available";}	
}		

function showDeviceInfoPopup(data) {
	var jsonString = data.deviceInfoList;
	if(jsonString.length !== 0 && jsonString !== null && jsonString !== undefined){	
	var table = $("<tbody>");
    var header = [["No.","CreatedOn","EventType","User Agent" ]]; //headers
	makeTable($("#deviceInfo"), header,table);
		for(var i=0; i<jsonString.length; i++){
					if (i<10){
						table += '<tr class= "show10DeviceInfoRecords"><td style="width: 109px; height: 60px;">' + [i+1] + '</td>';
						table += '<td>' + jsonString[i].createdOn + '</td>';
						table += '<td>' + deviceInfoEventType(jsonString[i].eventType) + '</td>';
						table += '<td>' + decodeMachineInfo(jsonString[i].userAgent) + '</td></tr>';
					}else {
						table += '<tr class= "showHiddenDeviceInfoRecords" style = "display:none"><td>' + [i+1] + '</td>';
						table += '<td>' + jsonString[i].createdOn + '</td>';
						table += '<td>' + deviceInfoEventType(jsonString[i].eventType) + '</td>';
						table += '<td>' + decodeMachineInfo(jsonString[i].userAgent) + '</td></tr>';
					}
		}
	table += "</tbody>"
	
	$('#deviceInfo').append(table);
	$('#deviceInfo').append('<button type="button" id="deviceInfoViewMoreRecords" class="ui-button ui-corner-all ui-widget" style=" width: 109.3px; height: 32.78px;"onclick ="viewMoreDeviceInfoRecords()" >View More</button>');
	isViewMoreDeviceInfoButtonEnable(jsonString.length);
	}else {
		$("#deviceInfo").text('No More Information Available');
	}
	$("#deviceInfo").attr('readonly', true);
	$("#DeviceInfopopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons : {
			"Close" : function() {
				$(this).dialog("close");
				$("#modal-mask").removeClass("modal-mask--visible").addClass("modal-mask--hidden");
			}
		}
	});
}

function makeTable(container, data,table) {
    $.each(data, function(rowIndex, r) {
        var row = $("<tr/>");
        $.each(r, function(colIndex, c) { 
            row.append($("<t"+(rowIndex == 0 ?  "h" : "d")+"/>").text(c));
        });
        table.append(row);
    });
    return container.html(table);
}

function viewMoreDeviceInfoRecords(){
	$('#deviceInfoViewMoreRecords').after('<span id="noMoreDeviceRecords" style="display: inline-block;">'
	+'<small id="noMoreDeviceInfoRecords" class="button--supporting1">No more records</small>'
    +'</span>');
	$("#deviceInfoViewMoreRecords").attr("disabled", true);
	$('#deviceInfoViewMoreRecords').addClass("disabled");
	$('.showHiddenDeviceInfoRecords').css("display","table-row");
	$('.show10DeviceInfoRecords').css("display","table-row");
}

function isViewMoreDeviceInfoButtonEnable(length){
	 if(length < 10){
		 $("#deviceInfoViewMoreRecords").attr("disabled", true);
		 $('#deviceInfoViewMoreRecords').addClass("disabled");
	 }else{
		 $("#deviceInfoViewMoreRecords").attr("disabled", false);
		 $('#deviceInfoViewMoreRecords').removeClass("disabled");
	 }
}

function deviceInfoEventType(eventType){
	switch(eventType)
    {
        case 'PROFILE_NEW_REGISTRATION':
          return eventType = "Sign Up";
        case 'PROFILE_ADD_CONTACT':
      	  return eventType = "Add Contact";
        case 'PROFILE_UPDATE_ACCOUNT':
      	  return eventType = "Update Account";
        case 'PROFILE_KYC_RESEND':
          return eventType = "KYC Repeat Check";
        case 'PROFILE_SANCTION_RESEND':
          return eventType = "Sanction Repeat Check";
        case 'FUNDSIN':
          return eventType = "Funds In";
        case 'FUNDSOUT_CREATE':
          return eventType = "Funds Out";
        case 'FUNDSOUT_UPDATE':
            return eventType = "Funds Out Update";
        case 'FUNDSOUT_DELETE':
           return eventType = "Funds Out Delete";
        case 'FUNDSOUT_SANCTION_RESEND':
           return eventType = "Funds Out Sanction Repeat Check";
        case 'FUNDSOUT_CUSTOMCHECK_RESEND':
           return eventType = "Funds Out Custom Check Repeat Check";
        case 'FUNDSIN_CUSTOMCHECK_RESEND':
            return eventType = "Funds In Custom Check Repeat Check";
        case 'FUNDSIN_SANCTION_RESEND':
           return eventType = "Funds In Sanction Repeat Check";
        default:
        	return eventType;
    }
}

function showAccountHistory(accountId) {
	var getAccountHistoryRequest = {};
	addField('accountId', accountId, getAccountHistoryRequest);
	getAccountHistory(getAccountHistoryRequest);
}

function getAccountHistory(request) {
	$.ajax({
		url : '/compliance-portal/getAccountHistory',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showAccountHistoryPopup(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function showAccountHistoryPopup(data) {
	$("#accountHistory").addClass("popupLinks");
	var row = setHistoryRow(data);
	$('#accountHistory').html(row);
	$("#accountHistory").attr('readonly', true);
	$("#AccountHistorypopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons : {
			"Close" : function() {
				$(this).dialog("close");
				$("#modal-mask").addClass("modal-mask--hidden");
				$("#accountHistory").html('');
			}
		}
	});
}

function setHistoryRow(data){
	var row='';
	row += setAccountHistory(data);
	if(data.clientType === 'CFX' ){
		row += setRiskProfileHistory(data);
		row += setCorporateComplianceHistory(data);
		row += setCompanyHistory(data);
	}
	row += setContactHistory(data);
	row += setDeviceInfoHistory(data);
	return row;
}

function setDeviceInfoHistory(data){
	var row='';
	var userAgentJson = "";
	if(null != data.deviceInfo){
		if( null != data.deviceInfo.userAgent  || data.deviceInfo.userAgent != undefined ){
			userAgentJson = JSON.parse(decodeMachineInfo(data.deviceInfo.userAgent));
		}
		row +='</br><span class ="bold">- - - Device Info - - -</span></br></br>';
		row +='<span class ="bold">Browser Online: </span><span> '+getDashIfNull(data.deviceInfo.browserOnline)+'</span><br>';
		row+='<span class ="bold">Browser Language: </span><span> '+getDashIfNull(data.deviceInfo.browserLanguage)+'</span><br>';
		row+='<span class ="bold">Browser Type: </span><span> '+getDashIfNull(data.deviceInfo.browserType)+'</span><br>';
		row+='<span class ="bold">Browser Version: </span><span> '+getDashIfNull(data.deviceInfo.browserVersion)+'</span><br>';
		row+='<span class ="bold">Application Id: </span><span> '+getDashIfNull(data.deviceInfo.applicationId)+'</span><br>';
		row+='<span class ="bold">Application Version: </span><span> '+getDashIfNull(data.deviceInfo.applicationVersion)+'</span><br>';
		row+='<span class ="bold">Device Id: </span><span> '+getDashIfNull(data.deviceInfo.deviceId)+'</span><br>';
		row+='<span class ="bold">Device Manufacturer: </span><span> '+getDashIfNull(data.deviceInfo.deviceManufacturer)+'</span><br>';
		row+='<span class ="bold">Device Name: </span><span> '+getDashIfNull(data.deviceInfo.deviceName)+'</span><br>';
		row+='<span class ="bold">Device Type: </span><span> '+getDashIfNull(data.deviceInfo.deviceType)+'</span><br>';
		row+='<span class ="bold">Device Version: </span><span> '+getDashIfNull(data.deviceInfo.deviceVersion)+'</span><br>';
		row+='<span class ="bold">OS Timestamp: </span><span> '+getDashIfNull(data.deviceInfo.oSTimestamp)+'</span><br>';
		row+='<span class ="bold">OS Type: </span><span> '+getDashIfNull(data.deviceInfo.oSType)+'</span><br>';
		row+='<span class ="bold">Screen Resolution: </span><span> '+getDashIfNull(userAgentJson.screenResolution)+'</span><br>';	
		row+='<span class ="bold">Browser User Agent: </span><span> '+getDashIfNull(decodeMachineInfo(data.deviceInfo.userAgent))+'</span><br>';	
	}
	return row;
}

function setAccountHistory(data){
	var row='';
	row +='</br><span class ="bold">- - - Account - - -</span></br></br>';
	row+='<span class ="bold">Account Name: </span><span> '+getDashIfNull(data.name)+'</span><br>';
	row+='<span class ="bold">Add Campaign: </span><span> '+getDashIfNull(data.addCampaign)+'</span><br>';
	row+='<span class ="bold">Affiliate Name: </span><span> '+getDashIfNull(data.affiliateName)+'</span><br>';
	row+='<span class ="bold">Affiliate Number: </span><span> '+getDashIfNull(data.affiliateNumber)+'</span><br>';
	row+='<span class ="bold">Average Transaction: </span><span> '+getDashIfNull(data.avgTransactionValue)+'</span><br>';
	row+='<span class ="bold">Branch: </span><span> '+getDashIfNull(data.branch)+'</span><br>';
	row+='<span class ="bold">Buying Currency: </span><span> '+getDashIfNull(data.buyingCurrency)+'</span><br>';
	row+='<span class ="bold">Channel: </span><span> '+getDashIfNull(data.channel)+'</span><br>';
	row+='<span class ="bold">Country Of Interest: </span><span> '+getDashIfNull(data.countryOfInterest)+'</span><br>';
	row+='<span class ="bold">Customer Type: </span><span> '+getDashIfNull(data.clientType)+'</span><br>';
	row+='<span class ="bold">Extended Referral: </span><span> '+getDashIfNull(data.extendedReferral)+'</span><br>';
	row+='<span class ="bold">Keywords: </span><span> '+getDashIfNull(data.keywords)+'</span><br>';
	row+='<span class ="bold">Operation Country: </span><span> '+getDashIfNull(data.countriesOfOperation)+'</span><br>';
	row+='<span class ="bold">Organization Legal Entity: </span><span> '+getDashIfNull(data.legalEntity)+'</span><br>';
	row+='<span class ="bold">Referral Id: </span><span> '+getDashIfNull(data.referralId)+'</span><br>';
	row+='<span class ="bold">Referral Name: </span><span> '+getDashIfNull(data.refferalText)+'</span><br>';
	row+='<span class ="bold">Registration Date Time: </span><span> '+getDashIfNull(data.dateOfReg)+'</span><br>';
	row+='<span class ="bold">Registration Done On: </span><span> '+getDashIfNull(data.complianceDoneOn)+'</span><br>';
	row+='<span class ="bold">Mode Of Registration: </span><span> '+getDashIfNull(data.regMode)+'</span><br>';
	row+='<span class ="bold">Search Engine: </span><span> '+getDashIfNull(data.searchEngine)+'</span><br>';
	row+='<span class ="bold">Selling Currency: </span><span> '+getDashIfNull(data.sellingCurrency)+'</span><br>';
	row+='<span class ="bold">Service Required: </span><span> '+getDashIfNull(data.serviceRequired)+'</span><br>';
	row+='<span class ="bold">Source: </span><span> '+getDashIfNull(data.source)+'</span><br>';
	row+='<span class ="bold">Source Of Fund: </span><span> '+getDashIfNull(data.sourceOfFund)+'</span><br>';
	row+='<span class ="bold">Sub Source: </span><span> '+getDashIfNull(data.subSource)+'</span><br>';
	row+='<span class ="bold">Trade Account Id: </span><span> '+getDashIfNull(data.tradeAccountId)+'</span><br>';
	row+='<span class ="bold">Trade Account Number: </span><span> '+getDashIfNull(data.tradeAccountNum)+'</span><br>';
	row+='<span class ="bold">Trade Name: </span><span> '+getDashIfNull(data.tradeName)+'</span><br>';
	row+='<span class ="bold">Transaction Purpose: </span><span> '+getDashIfNull(data.purposeOfTrans)+'</span><br>';
	row+='<span class ="bold">Turnover: </span><span> '+getDashIfNull(data.turnover)+'</span><br>';
	row+='<span class ="bold">Transaction Value: </span><span> '+getDashIfNull(data.estimTransValue)+'</span><br>';
	row+='<span class ="bold">Website: </span><span> '+getDashIfNull(data.webSite)+'</span><br>';
	row+='<span class ="bold">Organization Code: </span><span> '+getDashIfNull(data.orgCode)+'</span><br>';
	row+='<span class ="bold">Source Application: </span><span> '+getDashIfNull(data.sourceLookup)+'</span><br>';
	return row;
}

function setCorporateComplianceHistory(data){
	var row='';
	row +='</br><span class ="bold">- - - Corporate Compliance - - -</span></br></br>';
	row +='<span class ="bold">Financial Strength: </span><span>'+getDashIfNull(data.corporateCompliance.financialStrength)+'</span></br>';
	row+='<span class ="bold">Fixed Assets: </span><span>'+getDashIfNull(data.corporateCompliance.fixedAssets)+'</span></br>';
	row += '<span class ="bold">Foreign Owned Company: </span><span>'+getDashIfNull(data.corporateCompliance.foreignOwnedCompany)+'</span></br>';
	row += '<span class ="bold">Former Name: </span><span>'+getDashIfNull(data.corporateCompliance.formerName)+'</span></br>';
	row += '<span class ="bold">Global Ultimate DUNS: </span><span>'+getDashIfNull(data.corporateCompliance.globalUltimateDuns)+'</span></br>';
	row += '<span class ="bold">Global Ultimate Country: </span><span>'+getDashIfNull(data.corporateCompliance.globalUltimateCountry)+'</span></br>';
	row += '<span class ="bold">Global Ultimate Name: </span><span>'+getDashIfNull(data.corporateCompliance.globalUltimateName)+'</span></br>';
	row += '<span class ="bold">Gross Income: </span><span>'+getDashIfNull(data.corporateCompliance.grossIncome)+'</span></br>';
	row += '<span class ="bold">ISO Country Code 2 Digit: </span><span>'+getDashIfNull(data.corporateCompliance.isoCountryCode2Digit)+'</span></br>';
	row += '<span class ="bold">ISO Country Code 3 Digit: </span><span>'+getDashIfNull(data.corporateCompliance.isoCountryCode3Digit)+'</span></br>';
	row += '<span class ="bold">Legal Form: </span><span>'+getDashIfNull(data.corporateCompliance.legalForm)+'</span></br>';
	row += '<span class ="bold">Match Name: </span><span>'+getDashIfNull(data.corporateCompliance.matchName)+'</span></br>';
	row += '<span class ="bold">Net Income: </span><span>'+getDashIfNull(data.corporateCompliance.netIncome)+'</span></br>';
	row += '<span class ="bold">Net Worth: </span><span>'+getDashIfNull(data.corporateCompliance.netWorth)+'</span></br>';
	row += '<span class ="bold">Registration Date: </span><span>'+getDashIfNull(data.corporateCompliance.registrationDate)+'</span></br>';
	row += '<span class ="bold">Registration Number: </span><span>'+getDashIfNull(data.corporateCompliance.registrationNumber)+'</span></br>';
	row += '<span class ="bold">SIC: </span><span>'+getDashIfNull(data.corporateCompliance.sic)+'</span></br>';
	row += '<span class ="bold">SIC Description: </span><span>'+getDashIfNull(data.corporateCompliance.sicDesc)+'</span></br>';
	row += '<span class ="bold">Statement Date: </span><span>'+getDashIfNull(data.corporateCompliance.statementDate)+'</span></br>';
	row += '<span class ="bold">Total Asset: </span><span>'+getDashIfNull(data.corporateCompliance.totalAssets)+'</span></br>';
	row += '<span class ="bold">Total Current Assets: </span><span>'+getDashIfNull(data.corporateCompliance.totalCurrentAssets)+'</span></br>';
	row += '<span class ="bold">Total Current Liabilities: </span><span>'+getDashIfNull(data.corporateCompliance.totalCurrentLiabilities)+'</span></br>';
	row += '<span class ="bold">Total Liabilities and Equities: </span><span>'+getDashIfNull(data.corporateCompliance.totalLiabilitiesAndEquities)+'</span></br>';
	row += '<span class ="bold">Total Long Term Liabilities: </span><span>'+getDashIfNull(data.corporateCompliance.totalLongTermLiabilities)+'</span></br>';
	row += '<span class ="bold">Total Matched Shareholders: </span><span>'+getDashIfNull(data.corporateCompliance.totalMatchedShareholders)+'</span></br>';
	row += '<span class ="bold">Total Share Holders: </span><span>'+getDashIfNull(data.corporateCompliance.totalShareHolders)+'</span></br>';
	return row;
}

function setCompanyHistory(data){
	var row='';
	row +='</br><span class ="bold">- - - Company - - -</span></br></br>';
	row += '<span class ="bold">Billing Address: </span><span>'+getDashIfNull(data.company.billingAddress)+'</span></br>';
	row += '<span class ="bold">CCJ: </span><span>'+getDashIfNull(data.company.ccj)+'</span></br>';
	row += '<span class ="bold">Company Phone Number: </span><span>'+getDashIfNull(data.company.companyPhone)+'</span></br>';
	row += '<span class ="bold">Corporate Type: </span><span>'+getDashIfNull(data.company.corporateType)+'</span></br>';
	row += '<span class ="bold">Country Of Establishment: </span><span>'+getDashIfNull(data.company.countryOfEstablishment)+'</span></br>';
	row += '<span class ="bold">Country Of Region: </span><span>'+getDashIfNull(data.company.countryRegion)+'</span></br>';
	row += '<span class ="bold">Estimate Number Of Transaction: </span><span>'+getDashIfNull(data.company.estNoTransactionsPcm)+'</span></br>';
	row += '<span class ="bold">E-tailer: </span><span>'+getDashIfNull(data.company.etailer)+'</span></br>';
	row += '<span class ="bold">Incorporation Date: </span><span>'+getDashIfNull(data.company.incorporationDate)+'</span></br>';
	row += '<span class ="bold">Industry: </span><span>'+getDashIfNull(data.company.industry)+'</span></br>';
	row += '<span class ="bold">Ongoing Due Diligence Date: </span><span>'+getDashIfNull(data.company.ongoingDueDiligenceDate)+'</span></br>';
	row += '<span class ="bold">Option: </span><span>'+getDashIfNull(data.company.option)+'</span></br>';
	row += '<span class ="bold">Registration Number: </span><span>'+getDashIfNull(data.company.registrationNo)+'</span></br>';
	row += '<span class ="bold">Shipping Address: </span><span>'+getDashIfNull(data.company.shippingAddress)+'</span></br>';
	row += '<span class ="bold">Terms and Condition Signed Date: </span><span>'+getDashIfNull(data.company.tAndcSignedDate)+'</span></br>';
	row += '<span class ="bold">Type Of Financial Account: </span><span>'+getDashIfNull(data.company.typeOfFinancialAccount)+'</span></br>';
	row += '<span class ="bold">VAT Number: </span><span>'+getDashIfNull(data.company.vatNo)+'</span></br>';
	return row;
}

function setRiskProfileHistory(data){
	var row='';
	row +='</br><span class ="bold">- - - Risk Profile - - -</span></br></br>';
	row +='<span class ="bold">Annual Sales: </span><span> '+getDashIfNull(data.riskProfile.annualSales)+'</span><br>';
	row+='<span class ="bold">Continent: </span><span> '+getDashIfNull(data.riskProfile.continent)+'</span><br>';
	row+='<span class ="bold">Country Of Birth: </span><span> '+getDashIfNull(data.riskProfile.country)+'</span><br>';
	row+='<span class ="bold">Country Risk Indicator: </span><span> '+getDashIfNull(data.riskProfile.countryRiskIndicator)+'</span><br>';
	row+='<span class ="bold">Credit Limit: </span><span> '+getDashIfNull(data.riskProfile.creditLimit)+'</span><br>';
	row+='<span class ="bold">Delinquency Failure Score: </span><span> '+getDashIfNull(data.riskProfile.delinquencyFailureScore)+'</span><br>';
	row+='<span class ="bold">Domestic Ultimate Parent Details: </span><span> '+getDashIfNull(data.riskProfile.domesticUltimateParentDetails)+'</span><br>';
	row+='<span class ="bold">Domestic Ultimate Record: </span><span> '+getDashIfNull(data.riskProfile.domesticUltimateRecord)+'</span><br>';
	row+='<span class ="bold">Duns Number: </span><span> '+getDashIfNull(data.riskProfile.dunsNumber)+'</span><br>';
	row+='<span class ="bold">Failure Score: </span><span> '+getDashIfNull(data.riskProfile.failureScore)+'</span><br>';
	row+='<span class ="bold">Financial Figures Month: </span><span> '+getDashIfNull(data.riskProfile.financialFiguresMonth)+'</span><br>';
	row+='<span class ="bold">Financial Figures Year: </span><span> '+getDashIfNull(data.riskProfile.financialFiguresYear)+'</span><br>';
	row+='<span class ="bold">Financial Year End Date: </span><span> '+getDashIfNull(data.riskProfile.financialYearEndDate)+'</span><br>';
	row+='<span class ="bold">Global Ultimate Parent Details: </span><span> '+getDashIfNull(data.riskProfile.globalUltimateParentDetails)+'</span><br>';
	row+='<span class ="bold">Global Ultimate Record: </span><span> '+getDashIfNull(data.riskProfile.globalUltimateRecord)+'</span><br>';
	row+='<span class ="bold">Group Structure Number Of Levels: </span><span> '+getDashIfNull(data.riskProfile.groupStructureNumberOfLevels)+'</span><br>';
	row+='<span class ="bold">Headquarter Details: </span><span> '+getDashIfNull(data.riskProfile.headquarterDetails)+'</span><br>';
	row+='<span class ="bold">Immediate Parent Details: </span><span> '+getDashIfNull(data.riskProfile.immediateParentDetails)+'</span><br>';
	row+='<span class ="bold">Import Export Indicator: </span><span> '+getDashIfNull(data.riskProfile.importExportIndicator)+'</span><br>';
	row+='<span class ="bold">Location Type: </span><span> '+getDashIfNull(data.riskProfile.locationType)+'</span><br>';
	row+='<span class ="bold">Modelled Annual Sales: </span><span> '+getDashIfNull(data.riskProfile.modelledAnnualSales)+'</span><br>';
	row+='<span class ="bold">Modelled Net Worth: </span><span> '+getDashIfNull(data.riskProfile.modelledNetWorth)+'</span><br>';
	row+='<span class ="bold">Net Worth Amount: </span><span> '+getDashIfNull(data.riskProfile.netWorthAmount)+'</span><br>';
	row+='<span class ="bold">Profit Loss: </span><span> '+getDashIfNull(data.riskProfile.profitLoss)+'</span><br>';
	row+='<span class ="bold">Risk Direction: </span><span> '+getDashIfNull(data.riskProfile.riskDirection)+'</span><br>';
	row+='<span class ="bold">Risk Rating: </span><span> '+getDashIfNull(data.riskProfile.riskRating)+'</span><br>';
	row+='<span class ="bold">Risk Trend: </span><span> '+getDashIfNull(data.riskProfile.riskTrend)+'</span><br>';
	row+='<span class ="bold">State Country: </span><span> '+getDashIfNull(data.riskProfile.stateCountry)+'</span><br>';
	row+='<span class ="bold">Trading Styles: </span><span> '+getDashIfNull(data.riskProfile.tradingStyles)+'</span><br>';
	row+='<span class ="bold">Updated Risk: </span><span> '+getDashIfNull(data.riskProfile.updatedRisk)+'</span><br>';
	row+='<span class ="bold">Us1987 primary SIC 4 digit: </span><span> '+getDashIfNull(data.riskProfile.us1987PrimarySic4Digit)+'</span><br>';
	return row;
}

function setContactHistory(data){
	var count=0;
	var row='';
	$.each(data.contactHistory, function(index, contact) {
		count++;
		row +='</br><span class ="bold">- - - Contact '+count+'- - -</span></br></br>';
		row +='<span class ="bold">Trade Contact Id : </span><span> ' +getDashIfNull(contact.tradeContactId)+'</span></br>';
		row +='<span class ="bold">Contact SF Id : </span><span> ' +getDashIfNull(contact.ccCrmContactId)+'</span></br>';
		row +='<span class ="bold">Title : </span><span> ' +getDashIfNull(contact.title)+'</span></br>';	
		row+='<span class ="bold">First Name: </span><span>'+getDashIfNull(contact.firstName)+'</span></br>';
		row+='<span class ="bold">Middle Name: </span><span>'+getDashIfNull(contact.middleName)+'</span></br>';
		row+='<span class ="bold">Last Name: </span><span>'+getDashIfNull(contact.lastName)+'</span></br>';
		row+='<span class ="bold">Date Of Birth: </span><span>'+getDashIfNull(contact.dateofbirth)+'</span></br>';
		row+='<span class ="bold">Gender: </span><span>'+getDashIfNull(contact.gender)+'</span></br>';
		row+='<span class ="bold">Occupation: </span><span>'+getDashIfNull(contact.occupation)+'</span></br>';
		row+='<span class ="bold">Job Title: </span><span>'+getDashIfNull(contact.jobTitle)+'</span></br>';
		row +='<span class ="bold">Preferred Name : </span><span> ' +getDashIfNull(contact.prefName)+'</span></br>';
		row +='<span class ="bold">Country Of Nationality : </span><span> ' +getDashIfNull(contact.nationality)+'</span></br>';
		row +='<span class ="bold">Country Of Birth : </span><span> ' +getDashIfNull(contact.countryOfBirth)+'</span></br>';
		row+='<span class ="bold">Email: </span><span>'+getDashIfNull(contact.email)+'</span></br>';
		row +='<span class ="bold">Second Email Id : </span><span> ' +getDashIfNull(contact.secondEmail)+'</span></br>';
		row+='<span class ="bold">Mobile Phone: </span><span>'+getDashIfNull(contact.mobile)+'</span></br>';
		row +='<span class ="bold">Primary Phone Number : </span><span> ' +getDashIfNull(contact.primaryPhoneNumber)+'</span></br>';	
		row+='<span class ="bold">Home Phone Number: </span><span>'+getDashIfNull(contact.phoneHome)+'</span></br>';
		row +='<span class ="bold">Work Phone Number : </span><span> ' +getDashIfNull(contact.workphone)+'</span></br>';	
		row +='<span class ="bold">Work Phone Extension : </span><span> ' +getDashIfNull(contact.workphoneext)+'</span></br>';
		row +='<span class ="bold">Address Type: </span><span> ' +getDashIfNull(contact.addressType)+'</span></br>';
		row +='<span class ="bold">Street Type : </span><span> ' +getDashIfNull(contact.streetType)+'</span></br>';	
		row +='<span class ="bold">Building Name : </span><span> ' +getDashIfNull(contact.buildingNumber)+'</span></br>';
		row +='<span class ="bold">Street : </span><span> ' +getDashIfNull(contact.street)+'</span></br>';	
		row +='<span class ="bold">Street Number : </span><span> ' +getDashIfNull(contact.streetNumber)+'</span></br>';	
		row +='<span class ="bold">Area Number : </span><span> ' +getDashIfNull(contact.areaNumber)+'</span></br>';
		row +='<span class ="bold">Unit Number : </span><span> ' +getDashIfNull(contact.unitNumber)+'</span></br>';	
		row +='<span class ="bold">Civic Number : </span><span> ' +getDashIfNull(contact.civicNumber)+'</span></br>';		
		row +='<span class ="bold">Town City Municipality : </span><span> ' +getDashIfNull(contact.townCityMuncipalty)+'</span></br>';
		row +='<span class ="bold">Post Code : </span><span> ' +getDashIfNull(contact.postCode)+'</span></br>';	
		row +='<span class ="bold">State Province Country : </span><span> ' +getDashIfNull(contact.stateProvinceCounty)+'</span></br>';
		row +='<span class ="bold">District : </span><span> ' +getDashIfNull(contact.district)+'</span></br>';
		row +='<span class ="bold">Country Of Residence : </span><span> ' +getDashIfNull(contact.countryOfResidence)+'</span></br>';
		row +='<span class ="bold">Australia RTA Card Number : </span><span> ' +getDashIfNull(contact.australiaRTACardNumber)+'</span></br>';
		row +='<span class ="bold">Authorized Signatory : </span><span> ' +getDashIfNull(contact.authorisedSignatory)+'</span></br>';
		row +='<span class ="bold">AZA : </span><span> ' +getDashIfNull(contact.aza)+'</span></br>';	
		row +='<span class ="bold">State Of Birth : </span><span> ' +getDashIfNull(contact.stateOfBirth)+'</span></br>';	
		row +='<span class ="bold">Sub Building : </span><span> ' +getDashIfNull(contact.subBuilding)+'</span></br>';	
		row +='<span class ="bold">Sub City : </span><span> ' +getDashIfNull(contact.subCity)+'</span></br>';	
		row +='<span class ="bold">Years In Address : </span><span> ' +getDashIfNull(contact.yearsInAddress)+'</span></br>';
		row +='<span class ="bold">Designation : </span><span> ' +getDashIfNull(contact.designation)+'</span></br>';	
		row+='<span class ="bold">Driving Expiry Date: </span><span>'+getDashIfNull(contact.dlExpiryDate)+'</span></br>';
		row+='<span class ="bold">Driving License: </span><span>'+getDashIfNull(contact.dlLicenseNumber)+'</span></br>';
		row+='<span class ="bold">Driving License Card Number: </span><span>'+getDashIfNull(contact.dlCardNumber)+'</span></br>';
		row+='<span class ="bold">Driving License Country: </span><span>'+getDashIfNull(contact.dlCountryCode)+'</span></br>';
		row+='<span class ="bold">Driving State Code: </span><span>'+getDashIfNull(contact.dlStateCode)+'</span></br>';
		row+='<span class ="bold">Driving Version Number: </span><span>'+getDashIfNull(contact.dlVersionNumber)+'</span></br>';
		row+='<span class ="bold">Floor Number: </span><span>'+getDashIfNull(contact.floorNumber)+'</span></br>';
		row+='<span class ="bold">IP Address: </span><span>'+getDashIfNull(contact.ipAddress)+'</span></br>';
		row+='<span class ="bold">Is Primary Contact: </span><span>'+getDashIfNull(contact.isPrimaryContact)+'</span></br>';
		row+='<span class ="bold">Is US Client: </span><span>'+getDashIfNull(contact.isUsClient)+'</span></br>';
		row+='<span class ="bold">Is Country Supported: </span><span>'+getDashIfNull(contact.isCountrySupported)+'</span></br>';	
		row+='<span class ="bold">Medicare Card Number: </span><span>'+getDashIfNull(contact.medicareCardNumber)+'</span></br>';
		row+='<span class ="bold">Medicare Reference Number: </span><span>'+getDashIfNull(contact.medicareReferenceNumber)+'</span></br>';
		row+='<span class ="bold">Mother\'s Surname: </span><span>'+getDashIfNull(contact.mothersSurname)+'</span></br>';
		row+='<span class ="bold">Municipality Of Birth: </span><span>'+getDashIfNull(contact.municipalityOfBirth)+'</span></br>';
		row+='<span class ="bold">National Id Number: </span><span>'+getDashIfNull(contact.nationalIdNumber)+'</span></br>';
		row+='<span class ="bold">National Id Type: </span><span>'+getDashIfNull(contact.nationalIdType)+'</span></br>';
		row+='<span class ="bold">Passport Birth Family Name: </span><span>'+getDashIfNull(contact.passportFamilyNameAtBirth)+'</span></br>';
		row+='<span class ="bold">Passport Birth Place: </span><span>'+getDashIfNull(contact.passportPlaceOfBirth)+'</span></br>';
		row+='<span class ="bold">Passport Country Code: </span><span>'+getDashIfNull(contact.passportCountryCode)+'</span></br>';
		row+='<span class ="bold">Passport Expiry Date: </span><span>'+getDashIfNull(contact.passportExiprydate)+'</span></br>';
		row+='<span class ="bold">Passport Full Name: </span><span>'+getDashIfNull(contact.passportFullName)+'</span></br>';
		row+='<span class ="bold">Passport MRZ Line 1: </span><span>'+getDashIfNull(contact.passportMRZLine1)+'</span></br>';
		row+='<span class ="bold">Passport MRZ Line 2: </span><span>'+getDashIfNull(contact.passportMRZLine2)+'</span></br>';
		row+='<span class ="bold">Passport Name as Citizen: </span><span>'+getDashIfNull(contact.passportNameAtCitizenship)+'</span></br>';
		row+='<span class ="bold">Passport Number: </span><span>'+getDashIfNull(contact.passportNumber)+'</span></br>';
		row+='<span class ="bold">Position Of Significance: </span><span>'+getDashIfNull(contact.positionOfSignificance)+'</span></br>';
		row +='<span class ="bold">Prefecture : </span><span> ' +getDashIfNull(contact.prefecture)+'</span></br>';		
		row +='<span class ="bold">Region Suburb : </span><span> ' +getDashIfNull(contact.regionSuburb)+'</span></br>';	
		row +='<span class ="bold">Residential Status : </span><span> ' +getDashIfNull(contact.residentialStatus)+'</span></br>';	
	    row +='<span class ="bold">Second Surname : </span><span> ' +getDashIfNull(contact.secondSurname)+'</span></br>';	
	}); 
	return row;
}

function setPaymentOutStatusForUnwatch(prevStatus, updatedStatus){
	if(updatedStatus === 'CLEAR'){ 
		$("#rad-status-clear-label").addClass('pill-choice__choice--on');
	}else if(updatedStatus === 'REJECT'){
		$("#rad-status-reject-label").addClass('pill-choice__choice--on');
	}else if(updatedStatus === 'HOLD'){
		$("#rad-status-hold-label").addClass('pill-choice__choice--on');
	}else if(updatedStatus === 'SEIZE'){
		$("#rad-status-seize-label").addClass('pill-choice__choice--on');
	}else if(updatedStatus === 'WATCHED'){
		$("#rad-status-unwatch-label").addClass('pill-choice__choice--on');
	}
	if(prevStatus === 'CLEAR' && updatedStatus != 'CLEAR'){
		$("#rad-status-clear-label").removeClass('pill-choice__choice--on');
	}else if(prevStatus === 'REJECT' && updatedStatus != 'REJECT'){
		$("#rad-status-reject-label").removeClass('pill-choice__choice--on');
	}else if(prevStatus === 'HOLD' && updatedStatus != 'HOLD'){
		$("#rad-status-hold-label").removeClass('pill-choice__choice--on');
	}else if(prevStatus === 'SEIZE' && updatedStatus != 'SEIZE'){
		$("#rad-status-seize-label").removeClass('pill-choice__choice--on');
	}
		$("#rad-status-unwatch-label").removeClass('pill-choice__choice--on');
}

function showAccountWhiteListData(accountId, orgCode) {
	var getAccountWhiteListDataRequest = {};
	addField('accId', accountId, getAccountWhiteListDataRequest);
	addField('orgCode',orgCode, getAccountWhiteListDataRequest); //org code
	getAccountWhiteListData(getAccountWhiteListDataRequest);
}

function getAccountWhiteListData(request) {
	$.ajax({
		url : '/custom-checks-service/searchWhiteListData',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showAccountWhiteListDataPopup(data);
		},
		error : function() {
			alert('Error while fetching whitelist data');
		}
	});
}

function showAccountWhiteListDataPopup(data) {
	$("#accountWhiteListData").addClass("popupLinks");
	if (data.errorCode != '0000'){
		$('#accountWhiteListData').text('No Data Found');
		$("#accountWhiteListData").addClass("no-border-bottom");
	}else {
		var table = $("<tbody>");
	    var header = [["Category","Value","Limit"]]; //headers
		makeTable($("#accountWhiteListData"), header,table);
		table = setWhiteListData(data,table);
		table += "</tbody>";
		$("#accountWhiteListData").removeClass("no-border-bottom");
		$('#accountWhiteListData').append(table);
	}
	$("#accountWhiteListData").attr('readonly', true);
	$("#AccountWhiteListDatapopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons : {
			"Close" : function() {
				$(this).dialog("close");
				$("#modal-mask").addClass("modal-mask--hidden");
			}
		}
	});
}

function setWhiteListData(data,table){
	table += setWhiteListDataRow(data,table);
	return table;
}

function setWhiteListDataRow(data,table){
	
		/*table += '<tr><td class ="bold" style="width: 250px;" >Created On : </td>'
		table +='<td><ul><li><span> ' +getDashIfNull(data.whiteListData.createdOn)+ '</span></li></ul></td>';
		
		table += '<tr><td class ="bold">Updated On : </td>'
		table +='<td><ul><li><span> ' +getDashIfNull(data.whiteListData.updatedOn)+ '</span></br></li></ul></td>';*/
	
		table += '<tr><td class ="bold" style="width: 250px;">Reason Of Transfer : </td>' //1st column
		table+='<td style="width: 250px;">';                                              //2nd column
		for(var i=0; i<data.whiteListData.approvedReasonOfTransList.length; i++){
			table +='<span>' +getDashIfNull(data.whiteListData.approvedReasonOfTransList[i])+'</span></br>'
		}
		table +='</td>';
		table +='<td style="width: 250px;"></td>';                                        //3rd column
		
		table += '<tr><td class ="bold">Buy Currency : </td>'
		if(isListEmpty (data.whiteListData.approvedBuyCurrencyAmountRangeList.length)){
			table += '<td><span>--</span></br></td>';
			table += '<td></td>';
		}else {
			table +='<td>'
			for(var j=0; j<data.whiteListData.approvedBuyCurrencyAmountRangeList.length; j++){
				table +='<span>' +getDashIfListIsEmpty(data.whiteListData.approvedBuyCurrencyAmountRangeList[j].code)+'</span></br>';
			}
			table +='</td>'; 
			
			table +='<td>'
			for(var n=0; n<data.whiteListData.approvedBuyCurrencyAmountRangeList.length; n++){
				table +='<span>' + getEnglishNumberFormat(getDashIfListIsEmpty(data.whiteListData.approvedBuyCurrencyAmountRangeList[n].txnAmountUpperLimit))+'</span></br>';
			}
			table +='</td>'; 
		}
		
		table += '<tr><td class ="bold">Sell Currency : </td>'
			if(isListEmpty (data.whiteListData.approvedSellCurrencyAmountRangeList.length)){
				table += '<td><span>--</span></br></td>';
				table += '<td></td>';
			}else {
				table +='<td>'
				for(var k=0; k<data.whiteListData.approvedSellCurrencyAmountRangeList.length; k++){
					table +='<span>' +getDashIfListIsEmpty(data.whiteListData.approvedSellCurrencyAmountRangeList[k].code)+'</span></br>'
				}
				table +='</td>'; 
				
				table +='<td>'
				for(var g=0; g<data.whiteListData.approvedSellCurrencyAmountRangeList.length; g++){
					table +='<span>' + getEnglishNumberFormat(getDashIfListIsEmpty(data.whiteListData.approvedSellCurrencyAmountRangeList[g].txnAmountUpperLimit))+'</span></br>'
				}
				table +='</td>'; 
			}
		
		table += '<tr ><td class ="bold">Third Party Account : </td>'
			if(isListEmpty (data.whiteListData.approvedThirdpartyAccountList.length)){
				table += '<td><span>--</span></br></td>';
				table += '<td></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.approvedThirdpartyAccountList.length; l++){
					table +='<span>' +getDashIfNull(data.whiteListData.approvedThirdpartyAccountList[l])+'</span></br>'
				}
				table +='</td>'; 
			}
		
		table += '<tr ><td class ="bold">High Risk Country OPI: </td>'
			if(isListEmpty (data.whiteListData.approvedHighRiskCountryList.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.approvedHighRiskCountryList.length; l++){
					table +='<span>' +getCountryName(data.whiteListData.approvedHighRiskCountryList[l])+'</span></br>'
				}
				table +='</td>'; 
			}
		
		table += '<tr ><td class ="bold">Beneficiary Account Number: </td>'
			if(isListEmpty (data.whiteListData.approvedOPIAccountNumber.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.approvedOPIAccountNumber.length; l++){
					table +='<span>' +data.whiteListData.approvedOPIAccountNumber[l]+'</span></br>'
				}
				table +='</td>'; 
			}
		table += '<tr ><td class ="bold">Documentation Required Watchlist Sell-Currency: </td>'
			if(isListEmpty (data.whiteListData.documentationRequiredWatchlistSellCurrency.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.documentationRequiredWatchlistSellCurrency.length; l++){
					table +='<span>' +data.whiteListData.documentationRequiredWatchlistSellCurrency[l]+'</span></br>'
				}
				table +='</td>'; 
			}
		/*table += '<tr ><td class ="bold">US Client List B Debtor Account Number: </td>'
			if(isListEmpty (data.whiteListData.usClientListBDebtorAccountNumber.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.usClientListBDebtorAccountNumber.length; l++){
					table +='<span>' +data.whiteListData.usClientListBDebtorAccountNumber[l]+'</span></br>'
				}
				table +='</td>'; 
			}*/
		table += '<tr ><td class ="bold">US Client List B Bene Account Number: </td>'
			if(isListEmpty (data.whiteListData.usClientListBBeneAccNumber.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.usClientListBBeneAccNumber.length; l++){
					table +='<span>' +data.whiteListData.usClientListBBeneAccNumber[l]+'</span></br>'
				}
				table +='</td>'; 
			}
		table += '<tr ><td class ="bold">High Risk Country For Funds In: </td>'
			if(isListEmpty (data.whiteListData.approvedHighRiskCountryListForFundsIn.length)){
				table += '<td><span>--</span></br></td>';
			}else {
				table +='<td>'
				for(var l=0; l<data.whiteListData.approvedHighRiskCountryListForFundsIn.length; l++){
					table +='<span>'+'CountryCode : '+data.whiteListData.approvedHighRiskCountryListForFundsIn[l].countryCode+' <br>DisplayName : '+data.whiteListData.approvedHighRiskCountryListForFundsIn[l].displayName+'</span></br>'
				}
				table +='</td>'; 
			}
		
		table +='<td></td>';
	return table;
}

function getCountryName(highRiskCountry){
	if(highRiskCountry.displayName === undefined || highRiskCountry.displayName === null || highRiskCountry.displayName==='') {
		return '--';
	}
	return highRiskCountry.displayName;
}

function updateCurrentWatchlists(request) {
	var updatedWatchlist = [];
	var watchlist = '';
	$.each(request.watchlist, function(index, data) {
		if(data.updatedValue === true) {
			updatedWatchlist.push(data.name);
		}
	});
	$.each(updatedWatchlist, function(index, data) {
		watchlist = watchlist+"<li>"+data+"</li>";
	}); 
	return watchlist;
}

function openHolisticView(custType) {
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	//var custType = $('#customerType').val();
	var request = {};
	addField('accountId', accountId, request);
	addField('contactId', contactId, request);
	$.ajax({
		url : '/compliance-portal/getHolisticViewDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showHolisticViewPopup(data,custType);
		},
		error : function() {
			alert('Error while fetching openHolisticView data');
		}
	});
}

function showHolisticViewPopup(data,custType) {
	$("#holisticView").addClass("popupLinks");
	var view = $(data).find('#holisticTableViews').html();
	$("#holisticView").append(view);
	if(custType == 'CFX'){
		$("#holisticView_CompanyDetails").css('display', 'block');
	}else {
		$("#holisticView_CompanyDetails").css('display', 'none');
	}
	$("#holisticView").attr('readonly', true);
	$("#HolisticViewpopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons : {
			"Close" : function() {
				$(this).dialog("close");
				$("#modal-mask").addClass("modal-mask--hidden");
				$("#holisticView").html('');
			}
		}
	});
}

function applyClassesToAutoUnLockButton(){
	$('#toggle-record-lock').removeClass('toggle__option--on'); 
	$('#toggle-record-lock').addClass('toggle__option'); 
	$('#toggle-record-unlock').removeClass('toggle__option'); 
	$('#toggle-record-unlock').addClass('toggle__option--on unlock-specific'); 
}

function approveDoc(){
	var crmAccountId=$('#contact_crmAccountId').val();
	var crmContactId = $('#contact_crmContactId').val();
	var orgCode = $('#orgganizationCode').val();
	var document = {};
	var isDocumentAuthorized=[];
	addField('orgCode', orgCode , document);
	addField('crmContactID', crmContactId, document);
	addField('crmAccountID', crmAccountId, document);
	addField('source', 'compliance', document);
	addField('updatedBy', getUserObject().name, document);
	var table =$('#attachDoc');
	table.find('tr').each(function () {
	 		var fileDetails={};
	        var $tds = $(this).find('td'),
	         fileName = $tds.eq(2).text(),
	         isAuthorized = $tds.eq(5)[0].children[0].checked;
	         documentId = $tds.eq(6).text();
	        addField('fileName', fileName.trim() , fileDetails);
			addField('isAuthorized', isAuthorized , fileDetails);
			addField('document_id', documentId , fileDetails);
			isDocumentAuthorized.push(fileDetails);	
	    });
	addField('authDetails', isDocumentAuthorized , document);
	postApproveDocument(document,getAttachDocBaseUrl())
}

function postApproveDocument(request, baseUrl){
	$('#attach_document_approve_button_id').addClass("button--disabled");
	$.ajax({
		url : baseUrl + '/es-restfulinterface/rest/documentService/updateAuthorization',
		type : 'POST',
		data : getJsonString(request),
		contentType : "text/plain",
		success : function(data) {
			populateSuccessMessage("main-content__body","Document status updated successfully",
					"document_error_field","attach_document_approve_button_id");
		},
	error : function() {
		populateErrorMessage("main-content__body","Error while updating document status",
				"document_error_field","attach_document_approve_button_id");
		}
	});
	
}

$(document).on('change', '.enableDocButton', function() {
	var isRecordLocked = $('#isRecordLocked').val();
	if((null != isRecordLocked || isRecordLocked != '' || isRecordLocked === undefined ) && isRecordLocked == 'true' ){
		$('#attach_document_approve_button_id').removeClass("button--disabled");
	}
});

$("#doc_indicatior").click(function(){
	$('#attach_document_approve_button_id').addClass("button--disabled");
});

function sortPFXFraugsterChartData(originalJSON) {
	var i = 0, j = 0, k = 0,l = 0, m = 0, score = 0, values = 0, maxPositiveValueArray = [], maxNegativeValueArray = [], maxNegativeValues = [], maxPositiveValues = [], maxPositiveSortedArray = [], maxNegativeSortedArray = [];
	for (name in originalJSON.decisionDrivers) {
		chartMeasure = name;
		if (originalJSON.decisionDrivers[name].hasOwnProperty('value')) {
			if (originalJSON.decisionDrivers[name].value.length > 0) {
				chartMeasure += ' ('
						+ originalJSON.decisionDrivers[name].value.trim() + ')';
			}
		}
		chartFeatureImportance = originalJSON.decisionDrivers[name].featureImportance;
		if (chartFeatureImportance > 0) {
			maxPositiveValueArray.push({
				value : chartMeasure,
				featureImportance : chartFeatureImportance
			});
		} else {
			maxNegativeValueArray.push({
				value : chartMeasure,
				featureImportance : chartFeatureImportance
			});
		}
	}
	maxPositiveSortedArray = maxPositiveValueArray.sort(function(a, b) {
		return b.featureImportance - a.featureImportance
	});
	maxNegativeSortedArray = maxNegativeValueArray.sort(function(a, b) {
		return a.featureImportance - b.featureImportance
	});
	
	if(maxPositiveSortedArray.length < 5) {
		l = maxPositiveSortedArray.length;
		for (k = 0; k < l; k++) {
				score = maxPositiveSortedArray[k].featureImportance;
				values = maxPositiveSortedArray[k].value;
				maxPositiveValues.push({
				value : values,
				featureImportance : score
			});
		}
	}
	else {
		for (k = 0; k < 5; k++) {
				score = maxPositiveSortedArray[k].featureImportance;
				values = maxPositiveSortedArray[k].value;
				maxPositiveValues.push({
				value : values,
				featureImportance : score
			});
		}
	}
	if( maxNegativeSortedArray.length < 5) {
		l = maxNegativeSortedArray.length;
		for (k = 0; k < l; k++) {
			score = maxNegativeSortedArray[k].featureImportance;
			values = maxNegativeSortedArray[k].value;
			maxNegativeValues.push({
				value : values,
				featureImportance : score
			});
		}
	}
	else {
		for (k = 0; k < 5; k++) {
			score = maxNegativeSortedArray[k].featureImportance;
			values = maxNegativeSortedArray[k].value;
			maxNegativeValues.push({
				value : values,
				featureImportance : score
			});
		}
	}
	maxNegativeValues.reverse();
	// This line append the negative array to positive array
	Array.prototype.push.apply(maxPositiveValues, maxNegativeValues);
	// The combine positive and negative value are returned.
	return maxPositiveValues;
}

function generateFraugsterChartData(originalJSON) {
	var chartDataItem = {}, chartData = [], chartMeasure = '', chartColour = '', chartFeatureImportance = index = 0, originaJSONSortedData = [];
	var originalJSONSortedData = sortPFXFraugsterChartData(originalJSON);
	for (name in originalJSONSortedData) {
		chartDataItem = {};
		chartMeasure = name;
		chartMeasure = originalJSONSortedData[name].value;
		chartFeatureImportance = originalJSONSortedData[name].featureImportance;
		chartColour = chartFeatureImportance < 0 ? "green" : "red";
		chartDataItem["measure"] = chartMeasure;
		chartDataItem["featureImportance"] = chartFeatureImportance;
		chartDataItem["colour"] = chartColour;
		chartData[index] = chartDataItem;
		index++;
	}
	return chartData;
}

function showFraudRingGraph(crmContactId){
	var currentCrmContactId, request = {};
	if (crmContactId)
		currentCrmContactId = crmContactId;
	else
		currentCrmContactId = $("#contact_crmContactId").val();
	disableButton('fraud_ring_button-'+currentCrmContactId);
	$('#gifloaderforfraudring'+currentCrmContactId).css('visibility','visible');
	addField('crmContactId', currentCrmContactId, request);
	$.ajax({
		url : '/compliance-portal/getFraudRingDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showFraudRingGraphPopup(data,currentCrmContactId);
			$('#gifloaderforfraudring'+currentCrmContactId).css('visibility','hidden');
		},
		error : function() {
			console.log("Error for graph data");
		}
	});
}

function showFraudRingGraphPopup(data,currentCrmContactId){
	$("#FraudRingPopups").dialog({
		modal : false,
		draggable : true,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx ui-fraudring-popup ui-fraudring',
		position: [150,150],
		create: function(event, ui) {
			$('.ui-dialog-titlebar-close').css('visibility','visible');
			$('.ui-dialog-titlebar-close').click(function() {
				$('#node_summary').css('display','none');
				$("#neo4jd3").width('100%');
			});
			/*$("<button type='button' id='saveButton' title = 'Screenshot' style='float:right; margin-right:1em;'></button>").button({icons:{primary: "ui-icon-circle-arrow-s"},text: false}).insertBefore('.ui-dialog-titlebar-close');/*.click(function(e){
				printFraudRing();
			});*/
		},
		resize : function(event,ui) {
			$('#FraudRingPopups').height($('.ui-dialog').height() - 60);
		},
		close: function(){
			enableButton('fraud_ring_button-'+currentCrmContactId);
		}
	});
	
	if( null == data.results || null == data.results[0].data[0].graph)
		fraudRingGraphPopUpMessage('none','none','block');
	else if( null == data.results[0].data[0].graph.nodes || null == data.results[0].data[0].graph.relationships || data.results[0].data[0].graph.nodes.length == 0 || data.results[0].data[0].graph.relationships.length == 0 )
		fraudRingGraphPopUpMessage('block','none','none');
	else {
			fraudRingGraphPopUpMessage('none','block','none');
			init(data,currentCrmContactId);
	}
}

function fraudRingGraphPopUpMessage(noRelation,neo4j,systemNotAvailable){
	$('#system_not_available').css('display',systemNotAvailable);
	$('#no_relation').css('display',noRelation);
	$('#neo4jd3').css('display',neo4j);
}

function getPreviousOfacValue(entityType) {
	var sanctionRowForOfac = "";
	
	if(entityType==="BENEFICIARY"){
		sanctionRowForOfac = $("#sanctions_beneficiary tr:first");
	}
	if(entityType==="BANK"){
		sanctionRowForOfac = $("#sanctions_bank tr:first");
	}
	if(entityType==="CONTACT"){
		sanctionRowForOfac = $("#sanctions_contact tr:first");
	}
	if(entityType==="DEBTOR"){
		sanctionRowForOfac = $("#sanctions_contact tr:first");
	}
	
	return sanctionRowForOfac.find("td:nth-child(7)").text();
}

function getPreviousWorldCheckValue(entityType) {
	var sanctionRowForWC = "";
	if(entityType==="BENEFICIARY"){
		sanctionRowForWC = $("#sanctions_beneficiary tr:first");
	}
	if(entityType==="BANK"){
		sanctionRowForWC = $("#sanctions_bank tr:first");
	}
	if(entityType==="CONTACT"){
		sanctionRowForWC = $("#sanctions_contact tr:first");
	}
	if(entityType==="DEBTOR"){
		sanctionRowForWC = $("#sanctions_contact tr:first");
	}
	
	return sanctionRowForWC.find("td:nth-child(8)").text();
}

function isFraudRingPresent(crmContactId){
	var currentCrmContactId, request = {},contactSfId = [];
	if (crmContactId) {
		if($('#cfx_contacts_list-'+crmContactId).hasClass('accordion__header accordion__header--open')) {
			return;
		}
		if(isEmpty($('#contactSfidArray').val())) {
			storeContactSfId(contactSfId,crmContactId)
		}
		else {
			contactSfId = $('#contactSfidArray').val();
			contactSfId = getJsonObject(contactSfId);
			if(contactSfId.includes(crmContactId))
				return;
			storeContactSfId(contactSfId,crmContactId)
		}
		currentCrmContactId = crmContactId;
	}
	else
		currentCrmContactId = $("#contact_crmContactId").val();
	$('#gifloaderforfraudring'+currentCrmContactId).css('visibility','visible');
	addField('crmContactId', currentCrmContactId, request);
	$.ajax({
		url: '/compliance-portal/checkIsFraudRingPresent',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			highlightFraudRingButton(data,currentCrmContactId);
			$('#gifloaderforfraudring'+currentCrmContactId).css('visibility','hidden');
		},
		error: function(data) {
			console.log(data);
		}
	});
}

function highlightFraudRingButton(data,currentCrmContactId) {
	if(data) {
		document.getElementById("fraud_ring_button-"+currentCrmContactId).style.color = '#000000';
		document.getElementById("fraud_ring_button-"+currentCrmContactId).style.animation = "glowing 1500ms infinite";
	}
}

function storeContactSfId(contactSfId,crmContactId) {
	contactSfId.push(crmContactId);
	$('#contactSfidArray').val(getJsonString(contactSfId));
}

function getActivityLogsByModule(minRecord, maxRecord, accountId, isViewMoreRequest, entityType,
		leftRecordsIdActLog, viewMore_AuditTrail_ActLogId,
		viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	var request = {};
	addField("accountId", accountId, request);
	addField("minRecord", minRecord, request);
	addField("maxRecord", maxRecord, request);
	setActivityLogEntityType(entityType);
	if(entityType === 'PAYMENT IN') {
		getPaymentInActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if(entityType === 'PAYMENT OUT') {
		getPaymentOutActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if(entityType === 'REGISTRATION') {
		getRegistrationActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if(entityType === 'ALL'){
		getAllActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if(entityType === 'DEFAULT'){
		getDefaultActivityLogs(viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	}
}

function getDefaultActivityLogs(viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog){
	$("#auditTrailActivityLog").hide();
	$("#activityLog").show()
	$("#"+viewMoreAuditTrailDetailsBlockId).hide();
	$("#"+previousViewMoreDetails_ActLog).show();
}

function getViewMoreActivityLogsByModule(accountId, isViewMoreRequest,
		leftRecordsIdActLog, viewMore_AuditTrail_ActLogId,
		viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	var request = {};
	var noOfRows = countRowsOfAudirTrail("auditTrailActivityLog");
	var minRecord = calculateMinRecord(noOfRows);
	var maxRecord = calculateMaxRecord(minRecord);
	addField("accountId", accountId, request);
	addField("minRecord", minRecord, request);
	addField("maxRecord", maxRecord, request);
	if($("#auditTrailActLogEntityType").val() === 'PAYMENT IN') {
		getPaymentInActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if($("#auditTrailActLogEntityType").val() === 'PAYMENT OUT') {
		getPaymentOutActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if($("#auditTrailActLogEntityType").val() === 'REGISTRATION') {
		getRegistrationActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	} else if($("#auditTrailActLogEntityType").val() === 'ALL'){
		getAllActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	}
}

function getRegistrationActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	$.ajax({
		url : '/compliance-portal/getRegistrationActivityLogs',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(isViewMoreRequest){
				setAuditTrailViewMoreActivityLog(data.activityLogData)
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}else{
				setAuditTrailActivityLog(data.activityLogData);
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}
			},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function getPaymentInActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	$.ajax({
		url : '/compliance-portal/getPaymentInActivityLogs',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(isViewMoreRequest){
				setAuditTrailViewMoreActivityLog(data.activityLogData)
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}else{
				setAuditTrailActivityLog(data.activityLogData);
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}
			},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function getPaymentOutActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	$.ajax({
		url : '/compliance-portal/getPaymentOutActivityLogs',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(isViewMoreRequest){
				setAuditTrailViewMoreActivityLog(data.activityLogData)
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}else{
				setAuditTrailActivityLog(data.activityLogData);
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}
			},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function getAllActivityLogs(request, isViewMoreRequest, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	$.ajax({
		url : '/compliance-portal/getAllActivityLogs',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(isViewMoreRequest){
				setAuditTrailViewMoreActivityLog(data.activityLogData)
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}else{
				setAuditTrailActivityLog(data.activityLogData);
				setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
			}
			},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function setAuditTrailViewMoreActLogData(data, leftRecordsIdActLog, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog) {
	var noOfRows = countRowsOfAudirTrail("auditTrailActivityLog");
	var totalRecords = data.totalRecords;
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			leftRecordsIdActLog);
	updateAuditTrailViewMoreBlock(leftRecords, viewMore_AuditTrail_ActLogId, viewMoreAuditTrailDetailsBlockId, previousViewMoreDetails_ActLog);
	setAuditTrailActivityLogTotalRecords(totalRecords);

}

function updateLeftRecords(noOfRows, totalRecords, leftRecordsIdActLog) {
	var leftRecord = totalRecords - noOfRows;
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + leftRecordsIdActLog).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + leftRecordsIdActLog).html("( " + leftRecord + " LEFT )");
	}
	return leftRecord;
}

function updateAuditTrailViewMoreBlock(leftRecords, viewMoreValue, viewMoreBlockId, previousViewMoreDetails_ActLog) {
	if (leftRecords === 0) {
		$("#" + viewMoreValue).html(leftRecords);
		$("#" + viewMoreBlockId).show();
		$("#" + viewMoreBlockId).attr("disabled", true);
		$("#" + viewMoreBlockId).addClass("disabled");
		$("#" + previousViewMoreDetails_ActLog).hide();
		$("#activityLogTable").hide();
	} else {
		$("#" + previousViewMoreDetails_ActLog).hide();
		$("#activityLogTable").hide();
		$("#" + viewMoreBlockId).show();
		$("#" + viewMoreBlockId).attr("disabled", false);
		$("#" + viewMoreBlockId).removeClass("disabled");
		if (leftRecords < Number(getViewMoreRecordsize())) {
			$("#" + viewMoreValue).html(leftRecords);
		} else {
			$("#" + viewMoreValue).html(Number(getViewMoreRecordsize()));
		}
	}
}

function setAuditTrailActivityLogTotalRecords(totalRecordValue) {
	$("#auditTrailActLogTotalRecords").val(totalRecordValue);
}

function setActivityLogEntityType(entityType) {
	$("#auditTrailActLogEntityType").val(entityType);
}
function countRowsOfAudirTrail(id) {
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

function setAuditTrailViewMoreActivityLog(activities) {
	var rows = '';
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
		var comment = getEmptyIfNull(activityData.comment);
						var tradeContractNumber = (activityData.contractNumber === "" || activityData.contractNumber === null) ? 
								($('#payment_transNum').text() === "") ? '---'
								: $('#payment_transNum').text()
								: activityData.contractNumber; //AT-1794 - Snehaz
		var row = '<tr class="talign">';
		row += '<td>' + createdOn + '</td>';
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
	
	$("#auditTrailActivityLog").append(rows);
}

function setAuditTrailActivityLog(activities) {
	$("#auditTrailActivityLog").empty();
	$("#activityLog").hide();
	var rows = '';
	$.each(activities, function(index, activityData) {
		var createdOn = getEmptyIfNull(activityData.createdOn);
		var createdBy = getEmptyIfNull(activityData.createdBy);
		var activity = getEmptyIfNull(activityData.activity);
		var activityType = getEmptyIfNull(activityData.activityType);
		var comment = getEmptyIfNull(activityData.comment);
						var tradeContractNumber = (activityData.contractNumber === "" || activityData.contractNumber === null) ? 
								($('#payment_transNum').text() === "") ? '---'
								: $('#payment_transNum').text()
								: activityData.contractNumber; //AT-1794 - Snehaz
		var row = '<tr class="talign">';
		row += '<td>' + createdOn + '</td>';
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
	
	$("#auditTrailActivityLog").append(rows);
	$("#auditTrailActivityLog").show();
	$("#activityLog").hide()
}

function getSocialData(emailId){
	var request = {};
	addField('emailId',emailId,request);
	
	$.ajax({
		url: '/compliance-portal/getSocialData',
		type: 'POST',
		data: getJsonString(request),
		contentType: 'application/json',
		success: function(data){
			showSocialDataPopup(data);
		},
		error:function(){}
	});
}

function showSocialDataPopup(data) {
	$("#socialDatapopupdiv").addClass("popupLinks");
		var row = setSocialData(data);
		$('#socialDatapopupdiv').html(row);
		
	$("#socialDatapopupdiv").attr('readonly', true);
	$("#SocialDataPopup").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons : {
			"Close" : function() {
				$(this).dialog("close");
				$("#modal-mask").addClass("modal-mask--hidden");
				$("#socialDatapopupdiv").html('');
			}
		}
	});
}

function setSocialData(data){
	var row='';
	row+='<span class ="bold">Full Name: </span><span> '+getDashIfNull(data.fullName)+'</span><br>';
	row+='<span class ="bold">Gender : </span><span> '+getDashIfNull(data.gender)+'</span><br>';
	row+='<span class ="bold">Location : </span><span> '+getDashIfNull(data.location)+'</span><br>';
	row+='<span class ="bold">Title : </span><span> '+getDashIfNull(data.title)+'</span><br>';
	row+='<span class ="bold">Organization : </span><span> '+getDashIfNull(data.organization)+'</span><br>';
	row+='<span class ="bold">Linkedin : </span><span> '+getDashIfNull(data.linkedin)+'</span><br>';
	row+='<span class ="bold">Bio : </span><span> '+getDashIfNull(data.bio)+'</span><br>';
	if(null !== data.details)
		row+= setSocialDataDetails(data);
	if(null !== data.dataAddOns)
		row+= setSocialDataAddOns(data);
	row+='<br><span class ="bold">Updated : </span><span> '+getDashIfNull(data.updated)+'</span><br>';	
	
	return row;
}

function setSocialDataDetails(data){
	var i,j,row='';
	
	row+='<br><span class ="bold">Details : </span><br>';
	row+='&emsp;<span class ="bold">Name : </span><br>';
	row+=printNestedJson(data.details.name,0,3);
	
	row+='<br>&emsp;<span class ="bold">Age : </span>';
	row+=printNestedJson(data.details.age,0,3);
	
	row+='<br>&emsp;<span class ="bold">Gender : </span><span> '+getDashIfNull(data.details.gender)+'</span><br>';
	
	row+='<br>&emsp;<span class ="bold">Emails : </span><br>';
	row+=printNestedJson(data.details.emails,0,3);
	
	row+='<br>&emsp;<span class ="bold">Phones : </span><br>';
	row+=printNestedJson(data.details.phones,0,3);
	
	row+='<br>&emsp;<span class ="bold">Profiles : </span><br>';
	row+=printNestedJson(data.details.profiles,0,3);
	
	row+='<br>&emsp;<span class ="bold">Locations : </span>';
	for(i = 0; i < data.details.locations.length ; i++){
		row+='<br>&emsp;&emsp;<span class ="bold">Label : </span><span> '+getDashIfNull(data.details.locations[i].label)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">City : </span><span> '+getDashIfNull(data.details.locations[i].city)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Region : </span><span> '+getDashIfNull(data.details.locations[i].region)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Region Code : </span><span> '+getDashIfNull(data.details.locations[i].regionCode)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Country : </span><span> '+getDashIfNull(data.details.locations[i].country)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Country Code : </span><span> '+getDashIfNull(data.details.locations[i].countryCode)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Formatted : </span><span> '+getDashIfNull(data.details.locations[i].formatted)+'</span><br>';
	}
	
	row+='<br>&emsp;<span class ="bold">Employment : </span><br>';
	row+=printNestedJson(data.details.employment,0,3);
	
	row+='<br>&emsp;<span class ="bold">Photos : </span><br>';
	row+=printNestedJson(data.details.photos,0,3);
	
	row+='<br>&emsp;<span class ="bold">Education : </span>';
	for(i = 0; i < data.details.education.length ; i++){
		row+='<br>&emsp;&emsp;<span class ="bold">Name : </span><span> '+getDashIfNull(data.details.education[i].name)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Degree : </span><span> '+getDashIfNull(data.details.education[i].degree)+'</span><br>';
		if(isObjectPropertyExist(data.details.education[i],'end'))
			row+='&emsp;&emsp;<span class ="bold">End Year : </span><span> '+getDashIfNull(data.details.education[i].end.year)+'</span><br>';
	}
	
	row+='<br>&emsp;<span class ="bold">URLs : </span><br>';
	row+=printNestedJson(data.details.urls,0,3);
	
	row+='<br>&emsp;<span class ="bold">Interests : </span><br>';
	for(i = 0; i < data.details.interests.length ; i++){
		row+='&emsp;&emsp;<span class ="bold">ID : </span><span> '+getDashIfNull(data.details.interests[i].id)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Name : </span><span> '+getDashIfNull(data.details.interests[i].name)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">Affinity : </span><span> '+getDashIfNull(data.details.interests[i].affinity)+'</span><br>';
		row+='&emsp;&emsp;<span class ="bold">ParentIds : </span><br>';
		for(j=0; j < data.details.interests[i].parentIds.length ;j++){
			row+='&emsp;&emsp;<span> '+getDashIfNull(data.details.interests[i].parentIds[j])+'</span><br>';
		}
		row+='&emsp;&emsp;<span class ="bold">Category : </span><span> '+getDashIfNull(data.details.interests[i].category)+'</span><br>';
	}
	
	row+='<br>&emsp;<span class ="bold">Topics : </span><br>';
	row+=printNestedJson(data.details.topics,0,3);
	return row;
}

function setSocialDataAddOns(data){
	var i,row='';
	row+='<br><span class ="bold">AddOns : </span><br>';
	for(i = 0; i < data.dataAddOns.length; i++){
		row+='<br>&emsp;<span class ="bold">ID : </span><span> '+getDashIfNull(data.dataAddOns[i].id)+'</span><br>';
		row+='&emsp;<span class ="bold">Name : </span><span> '+getDashIfNull(data.dataAddOns[i].name)+'</span><br>';
		row+='&emsp;<span class ="bold">Enabled : </span><span> '+getDashIfNull(data.dataAddOns[i].enabled)+'</span><br>';
		row+='&emsp;<span class ="bold">Applied : </span><span> '+getDashIfNull(data.dataAddOns[i].applied)+'</span><br>';
		row+='&emsp;<span class ="bold">Description : </span><span> '+getDashIfNull(data.dataAddOns[i].description)+'</span><br>';
		row+='&emsp;<span class ="bold">DocLink : </span><span> '+getDashIfNull(data.dataAddOns[i].docLink)+'</span><br>';
	}
	return row;
}

/*for onfido Provider Respose code*/
function getOnfidoProviderResponse(request,flag,id) {
        var onfidoReport = $('#onfidoReport').val();
          var onfido = JSON.parse(onfidoReport);
                $("#onfidoproviderResponseJson").attr('readonly', true);
                showOnfidoDetails(onfido);
}

function showOnfidoProviderResponseJson(onfido){ 
        $("#onfidoproviderResponseJson").text(onfido);
        $("#onfidoproviderResponseJson").attr('readonly', true);
        $("#OnfidoProviderResponsepopups").dialog({
                modal : true,
                draggable : false,
                resizable : false,
                show : 'blind',
                hide : 'blind',
                width : ($(window).width()-50),
                height : ($(window).height()-50),
                dialogClass : 'ui-dialog-osx',
                buttons :[
                {
                        text: 'Close',
                        click : function() {
                                $(this).dialog("close");
                        }
                }]
        })
}

function showOnfidoDetails(onfido) {
    var req = onfido.breakdown;
    var content = " ";
    content += "<table>";
    content +=      "<tr>";
    content += "<th><strong> Verification Type </strong></th><th><strong>"+" Result"+"</strong></th> ";
    content += "<tr><td>Onfido Result Status</td><td>" +getDashIfNull(onfido.result)+"</td></tr> ";
    if("visual_authenticity" in req){
    	content += "<tr><td>Visual Authenticity</td><td>"+getDashIfNull(req.visual_authenticity.result)+"</td></tr>";
    }else{
    	content += "<tr><td>Visual Authenticity</td><td>"+ "---" +"</td></tr>";
    }
    if("compromised_document" in req){
    	content += "<tr><td>Compromised Document</td><td>"+getDashIfNull(req.compromised_document.result)+"</td></tr>";
    }else{
    	content += "<tr><td>Compromised Document</td><td>"+ "---" +"</td></tr>";
    }
    if("age_validation" in req){
        content += "<tr><td>Age Validation</td><td>"+getDashIfNull(req.age_validation.result)+"</td></tr>";
    }else{
    	content += "<tr><td>Age Validation</td><td>"+ "---" +"</td></tr>";
    }
    if("data_validation" in req){
    	content += "<tr><td>Data Validation</td><td>"+getDashIfNull(req.data_validation.result)+"</td></tr>";
    }else{
    	content += "<tr><td>Data Validation</td><td>"+ "---" +"</td></tr>";
    }
    content += "</tr>";
    content += "</table>";
    $("#OnfidoProviderResponsepopups").html('<pre>'+content+'</pre>');
    showOnfidoProviderResponseJson(onfido);
}

function getDataAnonymize(request) {
	var request ={ };
	var  enterComment = document.getElementById("DataAnonReason").value;
	addField('enterComment',enterComment,request);
	var accountId = Number($('#contact_accountId').val());
	addField('accountId', accountId, request);
	var contactId = Number($('#contact_contactId').val());
	addField('contactId', contactId, request);	
	$.ajax({
		url: '/compliance-portal/getDataAnonymize',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();
		},
		error: function(data) {
			alert("Something has gone wrong!!");
		}
	});
}
	
function dataAnonymize(){
	$("#dataAnonPopupdiv").attr('readonly', true);
	var request = document.getElementById("DataAnonReason").value.trim();
	$("#DataAnonPopup").dialog({
	modal : true,
	draggable : false,
	resizable : false,
	show : 'blind',
	hide : 'blind',
	width : (700),
	height : (380),
	dialogClass : 'ui-dialog-osx',
	buttons :[{
		text: 'OK',
		click : function() {
			if (checkIfOnlySpaceEntered('DataAnonReason')) {
				$("#DataAnonReason").val('');
				$("#errorDescription").text("Please Enter reason");
				$("#errorDiv").css('display', 'block');
			} else {
			getDataAnonymize(request);
			$(this).dialog("close");
			unlockResource();
			populateSuccessMessage("main-content__body",
					"Anonymisation request initiated successfully",
					"anonymised_error_field",
					"regDetails_profile_update");
			$("#modal-mask").addClass("modal-mask--hidden");
			}
		}
	},
	{
		text: 'Cancel',
		click : function() {
			$(this).dialog("close");
			$("#DataAnonReason").val('');
			$("#errorDiv").css('display','none');
			$("#modal-mask").addClass("modal-mask--hidden");
			unlockResource();
			document.location.reload();
		}
	}]
	})
}

function setDataAnonService() {
	var user = getJsonObject(getValueById('userInfo'));
	var role = user.role.functions,i;
	for (i = 0; i < role.length; i++) {
		if (role[i].name == 'canInitiateDataAnon') {
			var rl = role[i].hasAccess;
			disableButton('forget_me_button');
			break;
		}
	}
	if (rl) {
		var dataAnonStatus = $('#dataAnonStatus').val();
		var custType = $("#customerType").val();
		if ("PFX" == custType)
			setDataAnonServicePFX(dataAnonStatus);
		else if ("CFX" == custType)
			setDataAnonServiceCFX(dataAnonStatus);
	}
}

function setDataAnonServicePFX(dataAnonStatus){
	var pfxStatus = $('#contact_compliacneStatus').text();
	if (pfxStatus == 'ACTIVE' || pfxStatus == 'REJECTED') {
		disableButton('forget_me_button');
	} else if (pfxStatus == 'INACTIVE') {
		if (dataAnonStatus == 1 || dataAnonStatus == 2)
			disableButton('forget_me_button');
		else
			enableButton('forget_me_button');
	}
	if ($('#regDetails_otherConCount').text() >= 1) {
		var otherComplianceStatus = [];
		var table = $('#otherpeople_contacts');
		table.find('tr').each(
				function() {
					var statusDetails = {};
					var $tds = $(this).find('td');
					var complianceStatus = $tds.eq(1).text();
					if (complianceStatus != "") {
						addField('complianceStatus', complianceStatus
								.trim(), statusDetails);
						otherComplianceStatus.push(statusDetails);
					}
				});
		for (var i = 0; i < otherComplianceStatus.length; i++) {
			if (otherComplianceStatus[i].complianceStatus == 'ACTIVE') {
				disableButton('forget_me_button');
				break;
			}
		}
	}
}

function setDataAnonServiceCFX(dataAnonStatus){
	var cfxStatus = $('#account_compliacneStatus').text();
	var contactCount = $("#cfxContactCount").val();
	if (cfxStatus == 'ACTIVE' || cfxStatus == 'REJECTED') {
		disableButton('forget_me_button');
	} else if (cfxStatus == 'INACTIVE') {
		if (dataAnonStatus == 1 || dataAnonStatus == 2)
			disableButton('forget_me_button');
		else
			enableButton('forget_me_button');
	}
	if (contactCount > 1)
		disableButton('forget_me_button');
}

function restrictActionsOnRegDetailsPage(){
	var dataAnonStatus = $('#dataAnonStatus').val();
	var user = getJsonObject(getValueById('userInfo'));
	var roleNames = user.role.names;
	if (roleNames.includes("ATLAS_DATA_ANON_APPROVER") && dataAnonStatus === '2' )
		genericCanNotViewLock();
	else if(roleNames.includes("ATLAS_DATA_ANON_INITIATOR") && dataAnonStatus === '1')
		genericCanNotViewLock();
}
//AT-3391
function setPoiExistsFlag() {
    var request ={ };
    var contact_sf_id = ($('#contact_crmContactId').val());
	addField('contact_sf_id', contact_sf_id, request);
	var trade_contact_id = Number($('#contact_tradeContactId').val());
	addField('trade_contact_id', trade_contact_id, request);	
	$.ajax({
		url: '/compliance-portal/setPoiExistsFlag',
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

function approvePoi(){
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
			setPoiExistsFlag();
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

function approvePoiService(poiExists) {
var poiExists = $('#poiExists').val();
if (poiExists == 3 || poiExists == 4 || poiExists == 5 || poiExists == 6)
			enableButton('eu_poi_update_button');
     else
			disableButton('eu_poi_update_button');
}

function updateIntuitionRegStatus(request, user, baseUrl){
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/intuition/updateRegStatus',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data :getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			//document.location.reload();
			setIntuitionStatusUpdateResponse(data, user);
		},
		error : function() {
			alert("Error while sending status to intuition");
		}
		
	});
}

function setIntuitionStatusUpdateResponse(data, user){
	var createdOn = applicationDate;
	var users = getEmptyIfNull(JSON.parse(user));
	var updatedBy = getEmptyIfNull(users.name);
	//var status = getEmptyIfNull(data.status);
	var summary = getEmptyIfNull(JSON.parse(data.summary));
	var profileScore = getSingleDashIfNull(summary.profileScore);
	var ruleScore = getSingleDashIfNull(summary.ruleScore);
	var status = getEmptyIfNull(summary.status);
	var providerResponse = getEmptyIfNull(JSON.parse(data.providerResponse));
	var intuitionId = getSingleDashIfNull(providerResponse.correlationId);
	var riskLevel = getSingleDashIfNull(summary.riskLevel);
	var updatedCount;
	if($("#customerType").val() == "PFX"){
		var intuitionCountPass = parseInt($("#regDetails_intuitionPass").text()) || 0;
		$("#regDetails_intuitionPass").text(intuitionCountPass+1);
		$("#intuitionTotalRecordsId").val(intuitionCountPass+1);
		
		if($("#intuitionTotalRecordsId").val() == 1){
			var intuitionCountFail = parseInt($("#regDetails_intuitionFail").text()) || 0;
			$("#regDetails_intuitionFail").text(intuitionCountFail+1);
			$("#intuitionTotalRecordsId").val(intuitionCountFail+1);
		}
		
		updatedCount = $("#intuitionTotalRecordsId").val();
	}
	else{
		var intuitionCountPass = parseInt($("#regDetails_intuitionPass--"+data.entityId).text()) || 0;
		$("#regDetails_intuitionPass--"+data.entityId).text(intuitionCountPass+1);
		$("#intuitionTotalRecordsId--"+data.entityId).val(intuitionCountPass+1);
		
		if($("#intuitionTotalRecordsId--"+data.entityId).val() == 1){
			var intuitionCountFail = parseInt($("#regDetails_intuitionNeg--"+data.entityId).text()) || 0;
			$("#regDetails_intuitionNeg--"+data.entityId).text(intuitionCountFail+1);
			$("#intuitionTotalRecordsId--"+data.entityId).val(intuitionCountFail+1);
		}
		
		updatedCount = $("#intuitionTotalRecordsId--"+data.entityId).val();
	}
	
	var intuitionStatus = false;
	if(status == 'PASS'){
		intuitionStatus = true;
	}
	
	var jsonPretty = JSON.stringify(data.providerResponse);
	
	var statusDiv;
    if(status == 'NOT_PERFORMED')
        statusDiv = "<td class='nowrap' class='number'>"+status+"</td>";
    else
        statusDiv = getYesOrNoCellWithId(intuitionStatus,"intuition_status");    
	
	var row = "<tr  href='javascript:void(0);' onclick='showProviderResponseIntuition("+jsonPretty+")'>";
    row +="<td>"+createdOn+"</td>";
    row +="<td class='wrapword'>"+updatedBy+"</td>";
    row +="<td class='wrapword'><a href='javascript:void(0);' onclick='showProviderResponseIntuition("+jsonPretty+")'>"+intuitionId+"</a></td>";
    row +="<td class='nowrap' class='number'>"+riskLevel+"</td>";
    row +="<td class='nowrap' class='number'>"+profileScore+"</td>"
    row +="<td class='nowrap' class='number'>"+ruleScore+"</td>"
    row += statusDiv;
    row += "</tr>"
    
    $("#regDetails_intuition").prepend(row);	
    $("#regDetails_intuition--"+data.entityId).prepend(row);	
    
    if(riskLevel != null && riskLevel != '-'){
		
		var data;
		
		$('#regDetails_intuition_risk_level').empty();
		
		if(riskLevel == 'Low')
			data = '<span class="indicator--positive" id="intuition_risk_level">'+riskLevel+'</span>';
		else if(riskLevel == 'Medium')
			data = '<span class="indicator--neutral" id="intuition_risk_level">'+riskLevel+'</span>';
		else if(riskLevel == 'High')
			data = '<span class="indicator--negative" id="intuition_risk_level">'+riskLevel+'</span>';
		else if(riskLevel == 'Extreme')
			data = '<span class="indicator--extreme" id="intuition_risk_level">'+riskLevel+'</span>';
		
    	$('#regDetails_intuition_risk_level').append(data);
    }
    
    intuitionFlag = true;
	if(intuitionFlag){
		$("#regDetails_intuition_indicatior").click(function(){ 
			var noOfRows = 1;
			var totalRecords = updatedCount;
			var leftRecords = updateIntuitionLeftRecords(noOfRows,totalRecords,"leftRecordsIdIntuition");
			updateIntuitionViewMoreBlock(leftRecords,"viewMore_IntuitionId","viewMoreDetails_intuition");
			intuitionFlag = false;
		});
	}
}

function updateIntuitionLeftRecords(noOfRows, totalRecords, leftRecordsIdActLog) {
	var leftRecord = totalRecords - noOfRows;
	if (leftRecord < 0) {
		leftRecord = 0;
		$("#" + leftRecordsIdActLog).html("( " + leftRecord + " LEFT )");
	} else {
		$("#" + leftRecordsIdActLog).html("( " + leftRecord + " LEFT )");
	}
	return leftRecord;
}

function updateIntuitionViewMoreBlock(leftRecords,viewMoreValue,viewMoreBlockId){
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

function showProviderResponseIntuition(data){
	var jsonPretty = JSON.stringify(JSON.parse(data), null, 2);
	$("#ProviderResponsepopups").html('<pre>'+jsonPretty+'</pre>');
	$("#providerResponseJson").attr('readonly', true);
	showPopupProviderResponse(data);
}

function showPopupProviderResponse(data) {
	var jsonPretty = JSON.stringify(JSON.parse(data), null, 2);
	$("#providerResponseJson").text(jsonPretty);
	$("#providerResponseJson").attr('readonly', true);
	$("#ProviderResponsepopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : ($(window).width()-50),
		height : ($(window).height()-50),
		dialogClass : 'ui-dialog-osx',
		buttons :[{
			text: 'Json',
			click : function() {
				showProviderResponseIntuition(data);
			}
		},
		{
			text: 'Table',
			click : function() {
				processJson($.parseJSON(data));
			}
		},
		{	
			text: 'Close',
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
}


function updateIntuitionRepeatCheckStatus(request,user, baseUrl){
	var intuitionRequest = {};
	
	var accountVersion = $('#accountVersion').val();
	
	var sanctionStatus = null;
	if(request.checkType == 'sanction'){
		sanctionStatus = request.sanctionStatus;
	}
	
	var blacklistStatus = null;
	if(request.checkType == 'blacklist'){
		blacklistStatus = request.blacklistStatus;
	}
	
	var kycStatus = null;
	if(request.checkType == 'kyc'){
		kycStatus = request.kycStatus;
	}
	
	var fraugsterDate = null;
	var fraugsterScore = null;
	if(request.checkType == 'fraugster'){
		fraugsterDate = request.fraugsterDate;
		fraugsterScore = request.fraugsterScore;
	}
	
	var accountBlacklist = null;
	if(request.checkType == 'cfxBlacklist'){
		accountBlacklist = request.accountBlacklistStatus;
	}
	
	var accountSanction = null;
	if(request.checkType == 'cfxSanction'){
		accountSanction = request.accountSanctionStatus;
	}
	
	
	addField('accountId',request.accountId,intuitionRequest);
	addField('contactId',request.contactId,intuitionRequest);
	addField('tradeAccountNumber',request.tradeAccountNumber,intuitionRequest);
	addField('orgCode', request.orgCode, intuitionRequest);
	addField('registrationInDate', request.registrationDateTime,intuitionRequest);
	addField('custType',request.custType,intuitionRequest);
	addField('sanctionStatus',sanctionStatus,intuitionRequest);
	addField('blacklistStatus',blacklistStatus,intuitionRequest);
	addField('kycStatus',kycStatus,intuitionRequest);
	addField('fraugsterDate',fraugsterDate,intuitionRequest);
	addField('fraugsterScore',fraugsterScore,intuitionRequest);
	addField('accountBlacklist',accountBlacklist,intuitionRequest);
	addField('accountSanction',accountSanction,intuitionRequest);
	addField('accountVersion',accountVersion,intuitionRequest);
	
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/intuition/updateRepeatCheckStatus',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data :getJsonString(intuitionRequest),
		contentType : "application/json",
		success : function(data) {
			setIntuitionStatusUpdateResponse(data, user);
		},
		error : function() {
			alert("Error while sending repeat check status to intuition");
		}
		
	});
}

//AT-4451
function updateFundsInIntuitionRepeatCheckStatus(user, baseUrl){
	var intuitionRequest = {};
	
	var paymentInId = $('#paymentinId').val();
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var accountId = $('#contact_accountId').val();
	var accountVersion = $('#accountVersion').val();
	
	addField('paymentInId', paymentInId, intuitionRequest);
	addField('tradeAccountNumber', tradeAccountNumber, intuitionRequest);
	addField('tradeContactId', tradeContactId, intuitionRequest);
	addField('tradePaymentId', tradePaymentId, intuitionRequest);
	addField('org_code', orgCode, intuitionRequest);
	addField('accountId',accountId,intuitionRequest);
	addField('accountVersion',accountVersion,intuitionRequest);
	
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/intuition/updateFundsInRepeatCheckStatus',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data :getJsonString(intuitionRequest),
		contentType : "application/json",
		success : function(data) {
			setIntuitionStatusUpdateResponseForPaymentIn(data);
			console.log(data);
		},
		error : function() {
			alert("Error while sending repeat check status to intuition");
		}
		
	});
}

//AT-4451
function updateFundsOutIntuitionRepeatCheckStatus(user, baseUrl){
	var intuitionRequest = {};
	
	var paymentOutId = $('#paymentOutId').val();
	var tradeAccountNumber = $('#account_tradeAccountNum').text();
	var tradeContactId = $('#contact_tradeContactId').val();
	var tradePaymentId = $('#tradePaymentId').val();
	var orgCode = $('#account_organisation').text();
	var accountId = $('#contact_accountId').val();
	var accountVersion = $('#accountVersion').val();
	
	addField('paymentOutId', paymentOutId, intuitionRequest);
	addField('tradeAccountNumber', tradeAccountNumber, intuitionRequest);
	addField('tradeContactId', tradeContactId, intuitionRequest);
	addField('tradePaymentId', tradePaymentId, intuitionRequest);
	addField('org_code', orgCode, intuitionRequest);
	addField('accountId',accountId,intuitionRequest);
	addField('accountVersion',accountVersion,intuitionRequest);
	
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/intuition/updateFundsOutRepeatCheckStatus',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data :getJsonString(intuitionRequest),
		contentType : "application/json",
		success : function(data) {
			setIntuitionStatusUpdateResponseForPaymentOut(data);
			console.log(data);
		},
		error : function() {
			alert("Error while sending repeat check status to intuition");
		}
		
	});
}

function setIntuitionStatusUpdateResponseForPaymentIn(data) {
	var createdOn = applicationDate;
	var summary = getEmptyIfNull(JSON.parse(data.summary));
	var updatedBy = getEmptyIfNull(summary.userId);
	if(updatedBy == ''){
		updatedBy = 'system';
	}
	var correlationId = getSingleDashIfNull(summary.correlationId);
	var profileRiskLevel = getSingleDashIfNull(summary.clientRiskLevel);
	var ruleScore = getEmptyIfNull(summary.ruleScore);
	var paymentRiskLevel = getSingleDashIfNull(summary.ruleRiskLevel);
	var status = getSingleDashIfNull(summary.status);


	var intuitionCountPass = parseInt($("#payInDetails_intuitionPass").text()) || 0;
		$("#payInDetails_intuitionPass").text(intuitionCountPass+1);
		$("#intuitionTotalRecordsId").val(intuitionCountPass+1);
		
		if($("#intuitionTotalRecordsId").val() == 1){
			var intuitionCountFail = parseInt($("#payInDetails_intuitionFail").text()) || 0;
			$("#payInDetails_intuitionFail").text(intuitionCountFail+1);
			$("#intuitionTotalRecordsId").val(intuitionCountFail+1);
		}
		
	updatedCount = $("#intuitionTotalRecordsId").val();

	var jsonPretty = JSON.stringify(data.providerResponse);

	var row = "<tr  href='javascript:void(0);' onclick='showProviderResponseIntuition(" + jsonPretty + ")'>";
	row += "<td>" + createdOn + "</td>";
	row += "<td class='wrapword'>" + updatedBy + "</td>";
	row += "<td class='wrapword'><a href='javascript:void(0);' onclick='showProviderResponseIntuition(" + jsonPretty + ")'>" + correlationId + "</a></td>";
	row += "<td class='nowrap'>" + profileRiskLevel + "</td>";
	row += "<td class='nowrap'>" + paymentRiskLevel + "</td>";
	row += "<td class='nowrap' class='number'>" + ruleScore + "</td>";
	row += "<td class='nowrap'>" + status + "</td>";
	row += "</tr>";

	$("#payInDetails_intuition").prepend(row);
	$("#payInDetails_intuition--" + data.entityId).prepend(row);
	
	intuitionFlag = true;
	if(intuitionFlag){
		$("#payInDetails_intuition_indicatior").click(function(){ 
			var noOfRows = 1;
			var totalRecords = updatedCount;
			var leftRecords = updateIntuitionLeftRecords(noOfRows,totalRecords,"leftRecordsIdIntuition");
			updateIntuitionViewMoreBlock(leftRecords,"viewMore_IntuitionId","viewMoreDetails_intuition");
			intuitionFlag = false;
		});
	}

}

function setIntuitionStatusUpdateResponseForPaymentOut(data) {
	var createdOn = applicationDate;
	var summary = getEmptyIfNull(JSON.parse(data.summary));
	var updatedBy = getEmptyIfNull(summary.userId);
	if(updatedBy == ''){
		updatedBy = 'system';
	}
	var correlationId = getSingleDashIfNull(summary.correlationId);
	var profileRiskLevel = getSingleDashIfNull(summary.clientRiskLevel);
	var ruleScore = getEmptyIfNull(summary.ruleScore);
	var paymentRiskLevel = getSingleDashIfNull(summary.ruleRiskLevel);
	var status = getSingleDashIfNull(summary.status);

		var intuitionCountPass = parseInt($("#payOutDetails_intuitionPass").text()) || 0;
		$("#payOutDetails_intuitionPass").text(intuitionCountPass+1);
		$("#intuitionTotalRecordsId").val(intuitionCountPass+1);
		
		if($("#intuitionTotalRecordsId").val() == 1){
			var intuitionCountFail = parseInt($("#payOutDetails_intuitionFail").text()) || 0;
			$("#payOutDetails_intuitionFail").text(intuitionCountFail+1);
			$("#intuitionTotalRecordsId").val(intuitionCountFail+1);
		}
		
	updatedCount = $("#intuitionTotalRecordsId").val();

	var jsonPretty = JSON.stringify(data.providerResponse);

	var row = "<tr  href='javascript:void(0);' onclick='showProviderResponseIntuition(" + jsonPretty + ")'>";
	row += "<td>" + createdOn + "</td>";
	row += "<td class='wrapword'>" + updatedBy + "</td>";
	row += "<td class='wrapword'><a href='javascript:void(0);' onclick='showProviderResponseIntuition(" + jsonPretty + ")'>" + correlationId + "</a></td>";
	row += "<td class='nowrap'>" + profileRiskLevel + "</td>";
	row += "<td class='nowrap'>" + paymentRiskLevel + "</td>";
	row += "<td class='nowrap' class='number'>" + ruleScore + "</td>";
	row += "<td class='nowrap'>" + status + "</td>";
	row += "</tr>";

	$("#payOutDetails_intuition").prepend(row);
	$("#payOutDetails_intuition--" + data.entityId).prepend(row);
	
	intuitionFlag = true;
	if(intuitionFlag){
		$("#payOutDetails_intuition_indicatior").click(function(){ 
			var noOfRows = 1;
			var totalRecords = updatedCount;
			var leftRecords = updateIntuitionLeftRecords(noOfRows,totalRecords,"leftRecordsIdIntuition");
			updateIntuitionViewMoreBlock(leftRecords,"viewMore_IntuitionId","viewMoreDetails_intuition");
			intuitionFlag = false;
		});
	}
}

// common fun for payment in and out (callback function)
function updateAccountIntuitionRepeatCheckStatusForPayment(request,user, baseUrl, paymentType){
	var intuitionRequest = {};
	
	var accountVersion = $('#accountVersion').val();
	
	var sanctionStatus = null;
	if(request.checkType == 'sanction'){
		sanctionStatus = request.sanctionStatus;
	}
	
	var blacklistStatus = null;
	if(request.checkType == 'blacklist'){
		blacklistStatus = request.blacklistStatus;
	}
	
	var kycStatus = null;
	if(request.checkType == 'kyc'){
		kycStatus = request.kycStatus;
	}
	
	var fraugsterDate = null;
	var fraugsterScore = null;
	if(request.checkType == 'fraugster'){
		fraugsterDate = request.fraugsterDate;
		fraugsterScore = request.fraugsterScore;
	}
	
	var accountBlacklist = null;
	if(request.checkType == 'cfxBlacklist'){
		accountBlacklist = request.accountBlacklistStatus;
	}
	
	var accountSanction = null;
	if(request.checkType == 'cfxSanction'){
		accountSanction = request.accountSanctionStatus;
	}
	
	
	addField('accountId',request.accountId,intuitionRequest);
	addField('contactId',request.contactId,intuitionRequest);
	addField('tradeAccountNumber',request.tradeAccountNumber,intuitionRequest);
	addField('orgCode', request.orgCode, intuitionRequest);
	addField('registrationInDate', request.registrationDateTime,intuitionRequest);
	addField('custType',request.custType,intuitionRequest);
	addField('sanctionStatus',sanctionStatus,intuitionRequest);
	addField('blacklistStatus',blacklistStatus,intuitionRequest);
	addField('kycStatus',kycStatus,intuitionRequest);
	addField('fraugsterDate',fraugsterDate,intuitionRequest);
	addField('fraugsterScore',fraugsterScore,intuitionRequest);
	addField('accountBlacklist',accountBlacklist,intuitionRequest);
	addField('accountSanction',accountSanction,intuitionRequest);
	addField('accountVersion',accountVersion,intuitionRequest);
	
	$.ajax({
		url : baseUrl+'/compliance-service/services-internal/intuition/updateRepeatCheckStatus',
		type : 'POST',
		headers: {
		        "user": user
		 },
		data :getJsonString(intuitionRequest),
		contentType : "application/json",
		success : function(data) {
			setIntuitionStatusUpdateResponse(data, user);
			if(paymentType === 'FundsIn'){
				updateFundsInIntuitionRepeatCheckStatus(getUser(),getComplianceServiceBaseUrl());
			}
			else{
				updateFundsOutIntuitionRepeatCheckStatus(getUser(), getComplianceServiceBaseUrl());
			}
		},
		error : function() {
			alert("Error while sending repeat check status to intuition");
		}
		
	});
}
