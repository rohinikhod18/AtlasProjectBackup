var tradeAccountNumberOfClient = 0;
var currentPage = 1;

addEventListener("keyup", function(event) {
	if (event.keyCode == 27) {
		$('#clientName').text("");
		$('#beneClientDetailsTableBody').empty();
	}
});

function redirectToPayee() {
	var searchCriteria = getValueById('searchCriteria');
	setValueById('ququefilter', searchCriteria);
	$("#benelist2").submit();
}

function getListOfPayees(event, accountId, name) {
	$('#total-no--benes').text(name);
	$("#tbl-payments").hide();
	$("#total_payments").hide();
	$("#tbl-bene-div").hide();
	$("#loadingobject").show();
	getBeneficiaryListOfClientAjax(accountId);
	event.preventDefault();
	event.stopPropagation();
}

function getBeneficiaryListOfClientAjax(accountId) {
	$.ajax({
		url : '/compliance-portal/getBeneficiaryListOfClient',
		type : 'POST',
		data : getJsonString(accountId),
		contentType : "application/json",
		success : function(data) {
			listOfBenes(data);
			$('#clientPayee').css('display','none');
		}
	});
}

function listOfBenes(response) {
	var rows = '';
	$.each(response.payee_list, function(index, payee) {
		rows += getPayeeTable(payee, index + 1);
	});
	$("#loadingobject").hide();
	$("#total_benes").show();
	$("#tbl-bene-body").empty();
	$("#tbl-bene").append(rows);
	$("#tbl-bene-div").show();
	$("#tbl-bene").show();
	
}
function getPayeeTable(payee, rowNumber) {
	var conId = payee.payee_id;
	var payeeAccountNumber;
	var beneAccNo = $('#beneAccNo').val();
	if(payee.payee_payment_method_list[0].payee_bank.account_number)
		payeeAccountNumber = payee.payee_payment_method_list[0].payee_bank.account_number;
	else
		payeeAccountNumber = payee.payee_payment_method_list[0].payee_bank.iban;
	var row;
	if(beneAccNo == payeeAccountNumber)
		row = '<tr id="clientPayee" class="available talign">';
	else
		row = '<tr class="available talign">';
	$.each(payee.payee_payment_method_list,function(index, payeePaymentMethodList) {
						row += getPayeeNameColumn(payee.account_number, getEmptyIfNull(payee.fullName), getEmptyIfNull(payee.payee_id), getEmptyIfNull(payee.org_code));
						row += getPayeeAccountNumberColumn(payee.account_number, getEmptyIfNull(payeePaymentMethodList.payee_bank.account_number), getEmptyIfNull(payeePaymentMethodList.payee_bank.iban), getEmptyIfNull(payee.payee_id), getEmptyIfNull(payee.org_code),getEmptyIfNull(payee.fullName));
						row += '</tr>';
					});
	return row;
}

function getPayeeNameColumn(tradeAccountNumber, fullName, tradeBeneId, org) {

	fullName = (fullName === null || fullName === "") ? '-------' : fullName;
	var payeeDetailsFunctionName = 'getPayeeTransactionList(\'' + tradeBeneId+'\',\'' + org+'\',\''+ tradeAccountNumber + '\',\''+ fullName + '\');'
	return '<td class="nowrap"><a onclick="' + payeeDetailsFunctionName	+ '" href="#" data-modal="modal-private-name-beneClientDetails" class="modal-trigger">' + fullName + '</a></td>';

}

function getPayeeAccountNumberColumn(tradeAccountNumber, account_number,iban, tradeBeneId, org, fullName) {
	account_number = (account_number === null || account_number === "") ? (iban === null || iban === "") ? '-------': iban : account_number;
	var payeeDetailsFunctionName = 'getPayeeTransactionList(\'' + tradeBeneId+'\',\'' + org+'\',\''+ tradeAccountNumber + '\',\''+ fullName + '\');'
	return '<td class="nowrap"><a onclick="' + payeeDetailsFunctionName	+ '" href="#" data-modal="modal-private-name-beneClientDetails" class="modal-trigger">' + account_number + '</a></td>';
}

function getAllPayments(){
	$("#tbl-bene-div").hide();
	$("#total_benes").hide();
	$("#total_payments").show();
	$("#tbl-payments").show();
}

function getPayeeTransactionList(tradeBeneId, orgCode, tradeAccountNumber,fullName){
	var payeeRequest = {};
	addField('tradeBeneId', tradeBeneId, payeeRequest);
	addField('tradeAccountNumber', tradeAccountNumber, payeeRequest);
	addField('orgCode', orgCode, payeeRequest);
	getTransactionListAjax(payeeRequest,fullName);
}

function getTransactionListAjax(payeeRequest,fullName) {
	$.ajax({
		url : '/compliance-portal/getTransactionList',
		type : 'POST',
		data : getJsonString(payeeRequest),
		contentType : "application/json",
		async : false,
		success : function(data) {
			listOfTransactions(data,fullName);
		},
		error : function(data) {
			alert("Error resending", data);
		}
	});
}

function listOfTransactions(data,fullName){
	$("#beneClientDetailsTableBody").empty();
	$('#clientName').text("");
	var date;
	var table;
	var updatedon;
	for(var i=0; i<data.length; i++){
		date = getFormattedDate(data[i].date);
		updatedon = getFormattedDate(data[i].updatedOn);
		table += '<tr><td>'+ date +'</td>';
		table += '<td>' + data[i].currency + '</td>';
		table += '<td>' + data[i].amount + '</td>';
		table += '<td>' + data[i].reference + '</td>';
		table += '<td>' + data[i].status + '</td>';
		table += '<td>' + updatedon + '</td></tr>';
	}
	$('#clientName').text(fullName);
	$('#beneClientDetailsTableBody').append(table);
	$('#modal-private-name-beneClientDetails').css('display','block');
}

function getFormattedDate(date){
	var formatDate = new Date(date);
	var specifiedDate = formatDate.toLocaleDateString('en-US');
	var specifiedTime = formatDate.toLocaleTimeString();
	return specifiedDate + " " + specifiedTime;	
}

function closePopUpBene(){
	$('#clientName').text("");
	$('#beneClientDetailsTableBody').empty();
}