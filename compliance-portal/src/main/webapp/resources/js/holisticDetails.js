var searchCriteria;

$(document).ready(function() {
	onSubNav('holistic-sub-nav');
	setFilterFieldsAsPerCriteria();
	getViewMoreRecordsize();
});


function getHolisticViewDetails(contactId,custType,anchor,accountId) {
	
	$('#otherContactcustType').val(custType);
	$('#othercontactId').val(contactId);
	$('#otheraccountId').val(accountId);
	$('#otherContactForm').submit();
}

function viewMoreHolisticDetails(serviceType,id,totalRecordsId,leftRecordsId) {
	
	
	var viewMore = {};
	var noOfRows = countRows(id);
	var totalRecords =Number($("#"+totalRecordsId).val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,leftRecordsId);
	var accountId = Number($('#contact_accountId').val());
	var contactId = Number($('#contact_contactId').val());
	var orgCode = $('#account_organisation').text();
	var clientType = $('#account_clientType').text();
	var minViewRecord = calculateMinRecord(noOfRows);
	var maxViewRecord = calculateMaxRecord(minViewRecord);
	
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
	addField('accountId',accountId,viewMore);
	if (serviceType == "ACTIVITYLOG"){
		getHolisticActivities(minViewRecord,maxViewRecord,true);
	} else if (serviceType == "PAYMENT_IN") {
		postHolisticPaymentInMoreDetails(viewMore);
	} else if (serviceType == "PAYMENT_OUT") {
		postPaymentOutMoreDetails(viewMore);
	}
}

function getHolisticActivities(minRecord,maxRecord,isViewMoreRequest){
	var request = {};
	var custType = $('#account_clientType').text();
	var comment = "";
	var accountId = $('#contact_accountId').val();
	addField("minRecord",minRecord,request);
	addField("maxRecord",maxRecord,request);
	addField("custType",custType,request);
	addField("comment",comment,request);
	addField("accountId",accountId,request);
	getHolisticActivityLogs(request,isViewMoreRequest);
}

function getHolisticActivityLogs(request,isViewMoreRequest) {
	$.ajax({
		url : '/compliance-portal/getAllActivityLogs',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if (isViewMoreRequest){
			    setHolisticActivityLogViewMore(data.activityLogData);
			}else{
				setActivityLog(data.activityLogData);
				setActivityViewMoreActLogData();
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

function setActivityViewMoreActLogData(){
	
	var noOfRows = countRows("activityLog");
	var totalRecords = Number($('#actLogTotalRecordsId').val());
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");
	Number($('#actLogTotalRecordsId').val(totalRecords));
}

function setHolisticActivityLogViewMore(activities) {
	
	var rows ='';
	$.each(activities, function(index, activityData) {
		    var createdOn = getEmptyIfNull(activityData.createdOn);
		    var createdBy = getEmptyIfNull(activityData.createdBy);
		    var activity = getEmptyIfNull(activityData.activity);
		    var activityType = getEmptyIfNull(activityData.activityType);
		    var comment = getEmptyIfNull(activityData.comment);
		    var tradeContractNumber = (activityData.contractNumber === "" || activityData.contractNumber === null) ? '---'
					: activityData.contractNumber; // AT-1794 -
													// Snehaz
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

function countRows(id) {
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
    /*var message = "Total Row Count: " + totalRowCount;
    message += "\nRow Count: " + rowCount;
    alert(message);*/
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

function calculateMinRecord(noOfRows){
    
    return noOfRows + 1;
}

function calculateMaxRecord(minViewRecord){
    
    return minViewRecord + Number(getViewMoreRecordsize()) - 1;
}

function showPaymentInDetails(paymentInId) {
	var request = {};
	addField('paymentInId', paymentInId, request);
	$.ajax({
		url : '/compliance-portal/getHolisticViewPaymentInDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showHolisticViewPopup(data, 'FUNDS_IN');
		},
		error : function() {
			alert('Error while fetching showing payment in details data');
		}
	});
}

function showPaymentOutDetails(paymentOutId) {
	var request = {};
	addField('paymentOutId', paymentOutId, request);
	$.ajax({
		url : '/compliance-portal/getHolisticViewPaymentOutDetails',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showHolisticViewPopup(data,'FUNDS_OUT');
		},
		error : function() {
			alert('Error while fetching showing payment out details data');
		}
	});
}

function showHolisticViewPopup(data, paymentType) {
	$("#holisticView").addClass("popupLinks");
	
	if(paymentType == 'FUNDS_IN'){
		var view = $(data).find('#holisticTableViews').html();
		$("#holisticView").append(view);
		$("#accordion-section-client-details_funds_in").css('display', 'block');
	}else {
		var view = $(data).find('#holisticTableViewsFundsOut').html();
		$("#holisticView").append(view);
		$("#accordion-section-client-details_funds_out").css('display', 'block');
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

function postHolisticPaymentInMoreDetails(request) {
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
		var paymentInId = payment.paymentId;
		
		var dash = "-";
		var row = '<tr>';
		row += '<td class="breakword"><a href="javascript:void(0);"  onclick="showPaymentInDetails('+paymentInId+')">' + tradeContractNumber + '</a></td>';
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
		var paymentOutId = payment.paymentId;
		
		var row = '<tr>';
		row += '<td class="breakword"><a href="javascript:void(0);"  onclick="showPaymentOutDetails('+paymentOutId+')">' + tradeContractNumber + '</a></td>';
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

$("#holisticDetails_activitylog_indicatior").click(function(){
	
	$("#activityLog").find('tr').slice(10).remove();
	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");

});

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
					
			cardPilot();		// At-4625		

		});

function viewMoreLoadDataForHolisticView() {
	
	$("#fxticket_indicatior").trigger('click');
	$("#client-wallets").trigger('click');
		
	var noOfRows = countRows("further_paymentInDetails");
	var totalRecords = $('#furPayInDetailsTotalRecordsPayInId').val();
	var leftRecords = updateLeftRecords(noOfRows, totalRecords,
			"leftRecordsPayInIdFurPayInDetails");
	updateViewMoreBlock(leftRecords, "viewMorePayIn_FurPayInDetailsId",
			"viewMoreDetailsPayIn_FurPayInDetails");
	
	var noOfRowsOut = countRows("further_paymentOutDetails");
	var totalRecordsOut = $('#furPayOutDetailsTotalRecordsPayInId')
			.val();
	var leftRecordsOut = updateLeftRecords(noOfRowsOut,
			totalRecordsOut, "leftRecordsPayInIdFurPayOutDetails");
	updateViewMoreBlock(leftRecordsOut,
			"viewMorePayIn_FurPayOutDetailsId",
			"viewMoreDetailsPayIn_FurPayOutDetails");
	
	var noOfRows = countRows("activityLog");
	var totalRecords = $('#actLogTotalRecordsId').val();
	var leftRecords = updateLeftRecords(noOfRows,totalRecords,"leftRecordsIdActLog");
	updateViewMoreBlock(leftRecords,"viewMore_ActLogId","viewMoreDetails_ActLog");

}

function viewMoreResetDataForHolisticView(){
	
	$("#further_paymentInDetails").find('tr').slice(10).remove();
	$("#further_paymentOutDetails").find('tr').slice(10).remove();
	$("#activityLog").find('tr').slice(10).remove();
}