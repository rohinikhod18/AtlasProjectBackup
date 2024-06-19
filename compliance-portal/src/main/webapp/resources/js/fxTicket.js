$("#fxticket_indicatior").click(function(){
	if ($('#fxTicketTableDiv').is(':empty')){
			
		
		var fxTicketForm ='<form id="fxticketform" method="POST" >';
			fxTicketForm ='<p class="table-note">Open tickets are indicated in <strong>bold</strong></p>';
			fxTicketForm+='<table id="fxTicketTableId" class="micro space-after">';
			fxTicketForm+='<thead><tr><th class="datetime-cell">Booking date</th>';
			fxTicketForm+='<th>Reference</th>';
			fxTicketForm+='<th>Deal type</th>';
			fxTicketForm+='<th class="number">Amount</th>';
			fxTicketForm+='<th class="small-cell">From</th>';
			fxTicketForm+='<th class="small-cell">To</th>';
			fxTicketForm+='<th class="number">Rate</th>';
			fxTicketForm+='<th>Source of Funds</th>';
			fxTicketForm+='<th>Status</th>';
			fxTicketForm+='</tr></thead>';
			fxTicketForm+='<tbody id="attachFxTicketList">';
			fxTicketForm+='</tbody>';
			fxTicketForm+='</table>';
			fxTicketForm+='<div id="fxTicketLoadingGif" style="display:none;">';
			fxTicketForm+='<svg width="20px" height="10px" xmlns="http://www.w3.org/2000/svg" viewBox="-40 -40 180 180" preserveAspectRatio="xMidYMid" class="uil-ring">';
			fxTicketForm+='<rect x="0" y="0" width="100" height="100" fill="none" class="bk"></rect>';
			fxTicketForm+='<circle cx="50" cy="50" r="42.5" stroke-dasharray="173.57299411083608 93.46238144429634" stroke="#2b76b6" fill="none" stroke-width="15">';
			fxTicketForm+='<animateTransform attributeName="transform" type="rotate" values="0 50 50;180 50 50;360 50 50;" keyTimes="0;0.5;1" dur="1s" repeatCount="indefinite" begin="0s">';
			fxTicketForm+='</animateTransform>';
			fxTicketForm+='</circle>';
			fxTicketForm+='</svg>';
			fxTicketForm+='</div>';
			fxTicketForm+='<div id="fxTicketPagination"></div>';
			fxTicketForm+='</form>';
		$('#fxTicketTableDiv').append(fxTicketForm);
		
		$('#attachFxTicketList,#fxTicketPagination,p.table-note').css('display','none');
		$('#fxTicketLoadingGif').css('display','block');
		$('#fxTicketTableId').ready(function() {
			getFxTicketListRequest(1);
		});
	}
});

function postAttachedDocumentTableData(fxTicketDetailsRequest, user){
	$.ajax({
		url : '/compliance-portal/getCustomerFXTicketList',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(fxTicketDetailsRequest),
		contentType : "application/json",
		success : function(data) {
			showFxTicketTableData(data);
			$('#fxTicketLoadingGif').css('display','none');	
			$('#attachFxTicketList,#fxTicketPagination,p.table-note').css('display','contents');

		},
		error : function(data) {
			//$('#main-content__body_negative').empty();
            //document.getElementById('main-content__body_negative').style.display = 'block';
           // $('#main-content__body_negative').append('<p>Service is Not Available</p>');
			
			$("#fxTicketTableDiv").empty();
			var error ='<div style="text-align: center;"><h5>Something Went Wrong</h5></div> ';
			$('#fxTicketTableDiv').append(error);
		}
	});
}

function showFxTicketTableData(jsondata){
	$("#attachFxTicketList").empty();
	$("#fxTicketPagination").empty();
	$.each(jsondata.fx_ticket_list , function(i, d) {
		var date = d.trade_details.booking_date;
		var reference = d.customer_instruction_details.reference;
		var dealType = d.customer_instruction_details.deal_type;
		var sellingAmount = d.trade_details.selling_amount;
		var sellingCurrency = d.trade_details.selling_currency;
		var buyingCurrency = d.trade_details.buying_currency;
		/*
		 * TITAN-3275
		 * if agreed rate is present then show agreed rate else show treasury rate in rate column on UI - Sneha Zagade
		 * */
		var treasuryRate = (d.trade_details.agreed_rate != 0) ? d.trade_details.agreed_rate : d.trade_details.treasury_rate;
		
		var id = d.id;
		var status = d.status;
		var accountNumber = d.customer_instruction_details.account_number;
		var orgnization = d.customer_instruction_details.organization_code;
		
		if(orgnization == "Currencies Direct"){
			orgnization = "CurrenciesDirect";
		}
		if (typeof reference === "undefined") {
			reference = "-";
		}
		
		if (typeof date !== "undefined") {
			bookingDate = dateForamt(date);
		}
		
		var sourceOfFunds = d.income_source_of_fund; //ADDED for AT-2486
		if (sourceOfFunds == null) {
			sourceOfFunds = "-";
		}
		
		sellingAmount = getNumberFormat(sellingAmount);
		treasuryRate = getFourDecimalNumber(treasuryRate);
		treasuryRate = (d.trade_details.agreed_rate != 0) ? (d.trade_details.agreed_rate == d.trade_details.system_rate)? treasuryRate+'(PE)' : treasuryRate+'(AR)' : treasuryRate+'(PE)'
/*		if(status == 'OPEN') {
			var row='<tr class="owned talign" class>';
		} else {*/
			var row='<tr class="talign">';
		//}
		
			row+='<td class="datetime-cell"><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+bookingDate+'</a></td>';
			row+='<td><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+reference+'</a></td>';
			row+='<td><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+dealType+'</a></td>';
			row+='<td class="number"><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+sellingAmount+'</a></td>';
			row+='<td class="small-cell"><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+sellingCurrency+'</a></td>';
			row+='<td class="small-cell"><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+buyingCurrency+'</a></td>';
			row+='<td class="number"><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+treasuryRate+'</a></td>';
			row+='<td><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+sourceOfFunds+'</a></td>';
			row+='<td><a onClick=getFXTicketDetails("'+id+'","'+accountNumber+'","'+orgnization+'","'+status+'") href="javascript:void(0);" >'+status+'</a></td>';
/*			if(status == 'OPEN') {
				row+='</tr>';
			} else {*/
				row+='</tr>';
			//}
		$('#attachFxTicketList').append(row);
	});
	
	if(jsondata.response_code == 000){
		var minRecords = jsondata.search_criteria.page.min_record;
		var maxRecords = jsondata.search_criteria.page.max_record;
		var currentRecord = jsondata.search_criteria.page.current_record;
		var totalRecords = jsondata.search_criteria.page.total_records;
		var currentPage = jsondata.search_criteria.page.current_page;
		var pageSize = jsondata.search_criteria.page.size;
		var totalPages = jsondata.search_criteria.page.total_pages;
		var nextPage = currentPage + 1;
		var previousPage = currentPage - 1;
		console.log("nextPage : "+nextPage);
		var pagination ='<p id="pageCountDetails" >Showing ';
		if(totalRecords > pageSize) {
			pagination+='<strong id="queueMinRecord">'+minRecords+'</strong> <strong>-</strong> <strong id="queueMaxRecord">'+maxRecords+'</strong>';
			pagination+=' of <strong id="queueMinRecord">'+totalRecords+'</strong> records' 
		}
			pagination+='<ul class="horizontal containing pagination" id="paginationBlock" >';
		if(totalPages > 1) {
			if(currentPage > 1){
				pagination+='<li onclick=getFirstOrLastPage("'+1+'") class="pagination__jump" value="1"><a id="firstPage" href="javascript:void(0);" title="First page"><i class="material-icons">first_page</i></a></li>';
				pagination+='<li onClick=getPreviousPage("'+previousPage+'") class="pagination__jump"><a id="previousPage" href="javascript:void(0);" title="Previous page"><i class="material-icons">navigate_before</i></a></li>';
				if(currentPage == totalPages){
					pagination+='<li onClick=getNextPage("'+nextPage+'") class="pagination__jump--disabled"><a id="nextPage" href="javascript:void(0);" title="Next page"><i class="material-icons">navigate_next</i></a></li>'
					pagination+='<li onClick=getFirstOrLastPage("'+totalPages+'") class="pagination__jump--disabled" value="33"><a id="lastPage" href="javascript:void(0);" title="Last page"><i class="material-icons">last_page</i></a></li>';
				} else {
					pagination+='<li onClick=getNextPage("'+nextPage+'") class="pagination__jump"><a id="nextPage" href="javascript:void(0);" title="Next page"><i class="material-icons">navigate_next</i></a></li>'
					pagination+='<li onClick=getFirstOrLastPage("'+totalPages+'") class="pagination__jump" value="33"><a id="lastPage" href="javascript:void(0);" title="Last page"><i class="material-icons">last_page</i></a></li>';
				}
			} else {
				pagination+='<li id="firstPage" onclick=getFirstOrLastPage("'+1+'") class="pagination__jump--disabled" value="1"><a href="javascript:void(0);" title="First page"><i class="material-icons">first_page</i></a></li>';
				pagination+='<li onClick=getPreviousPage("'+previousPage+'") class="pagination__jump--disabled"><a id="previousPage" href="javascript:void(0);" title="Previous page"><i class="material-icons">navigate_before</i></a></li>';
				pagination+='<li onClick=getNextPage("'+nextPage+'") class="pagination__jump"><a id="nextPage" href="javascript:void(0);" title="Next page"><i class="material-icons">navigate_next</i></a></li>'
				pagination+='<li onClick=getFirstOrLastPage("'+totalPages+'") class="pagination__jump" value="33"><a id="lastPage" href="javascript:void(0);" title="Last page"><i class="material-icons">last_page</i></a></li>';
			}
			
			pagination+='</ul>';
			$('#fxTicketPagination').append(pagination);
		}
	} else if(jsondata.response_code == 555){
		$("#fxTicketTableDiv").empty();
		var error ='<div style="text-align: center;"><h5> '+jsondata.response_description+' </h5></div> ';
		$('#fxTicketTableDiv').append(error);
	} 
	else {
		$("#fxTicketTableDiv").empty();
		var error ='<div style="text-align: center;"><h5>Records Not Found</h5></div> ';
		$('#fxTicketTableDiv').append(error);
	}

}

function getFXTicketDetails(id, accountNumber, orgnization, status){
	
	var fxTicketDetailsRequest = {};
	var fxDetailRequest = {};
	var fxTicketPayload = {};
	var fxDetailsRequestList = [];
	
	if(orgnization == "CurrenciesDirect"){
		orgnization = "Currencies Direct";
	}
	var sourceApplication = "Atlas";
	var titanAccountNumber = accountNumber;
	var fxTicketId = id;
	var fxStatus = status;
	
	addField('titan_account_number',titanAccountNumber,fxTicketPayload);		
	addField('org_code',orgnization,fxTicketPayload);
	addField('fx_ticket_id',fxTicketId,fxTicketPayload);
	addField('fx_status',fxStatus,fxTicketPayload);
	//addField('request_source',requestSource,fxTicketPayload);
	
	addField('fx_ticket_payload',fxTicketPayload,fxDetailRequest);
	fxDetailsRequestList.push(fxDetailRequest);
	addField('source_application',sourceApplication,fxTicketDetailsRequest);
	addField('fx_ticket_detail_request_list',fxDetailsRequestList,fxTicketDetailsRequest);
	
	$.ajax({
		url : '/compliance-portal/getCustomerFXTicketDetails',
		type : 'POST',
		data : getJsonString(fxTicketDetailsRequest),
		contentType : "application/json",
		success : function(data) {
			showFXTicketDetails(data);
		},
		error : function(data) {
			console.log("data 1 : "+JSON.stringify(data));
			alert("something went wrong..!!"+JSON.stringify(data));
		}
	});
}

function showFXTicketDetails(data) {
	$("#fxticketView").addClass("popupLinks");
	var view =$(data).find('#model-fxticket').html();
	$("#fxticketView").append(view);
	$("#fxticketaccordian").css('display', 'block');
	$("#fxticketView").attr('readonly', true);
	$("#fxticketpopups").dialog({
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
				$("#model-fxticket").addClass("modal-mask--hidden");
				$("#fxticketView").html('');
			}
		}
	});
}

function getPreviousPage(previousPage) {
	getFxTicketListRequest(previousPage);
}

function getFirstOrLastPage(pageNumber) {
	getFxTicketListRequest(pageNumber);
}

function getNextPage(nextPage) {
	getFxTicketListRequest(nextPage);
}

function getFxTicketListRequest(pageNumber) {
	var fxTicketDetailsRequest = {};
	var fxTicketSearchCriteria = {};
	var fxTicketFilter = {};
	var fxTicketPayload = {};
	var page = {};
		
	var currentPage = pageNumber;
	var size = 5;
	var offset = 0;
	var tradeAccountNumber = $("#account_tradeAccountNum").text();
	var orgnization = [$("#account_organisation").text()];
		
	addField('current_page',currentPage,page);
	addField('size',size,page);
	addField('offset',offset,page);
		
	addField('titan_account_number',tradeAccountNumber,fxTicketPayload);
					
	addField('organization',orgnization,fxTicketFilter);
	addField('filter',fxTicketFilter,fxTicketSearchCriteria);
		
	addField('page',page,fxTicketSearchCriteria);
	addField('fx_ticket_payload',fxTicketPayload,fxTicketDetailsRequest);
	addField('search_criteria',fxTicketSearchCriteria,fxTicketDetailsRequest);
	
	postAttachedDocumentTableData(fxTicketDetailsRequest, getUser());
}

function dateForamt(date) {
	var dateValue = new Date(date);
	
	var dd = dateValue.getDate();
	var mm = dateValue.getMonth()+1; //January is 0!
	var yyyy = dateValue.getFullYear();
	
	if(dd<10){
	    dd='0'+dd;
	} 
	if(mm<10){
	    mm='0'+mm;
	} 
	dateValue = dd+'/'+mm+'/'+yyyy;
	return dateValue;
}

function getNumberFormat(value){
	if(!isNull(value) && !isEmpty(value) && getDashIfListIsEmpty(value) != '--'){
		return value.toLocaleString('en-UK',{ minimumFractionDigits: 2 });
	}
	return value
}

function getFourDecimalNumber(value){
	if(!isNull(value) && !isEmpty(value) && getDashIfListIsEmpty(value) != '--'){
		return value.toLocaleString(undefined, { minimumFractionDigits: 4 });
	}
	return value
}
