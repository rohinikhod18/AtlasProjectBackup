var REG_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var regDetailsViewUrl = '#';
var fieldName;

$(document).ready(function() {
	onSubNav('workEff-sub-nav');
});

function applyWorkEfficiencySearchCriteria() {
	currentPage = 1;
	var request = getQueueSearchCriteriaObject();
	postWorkEfficiencyReportAjax(request,true);
}

function clearAndApplyFilter(){
	clearFilters();
	applyWorkEfficiencySearchCriteria();
}

function getNextPage() {
	enableSpecificPaginationBlock("firstPage");
    enableSpecificPaginationBlock("previousPage");
    enableSpecificPaginationBlock("nextPage");
    enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	
	 var searchCriteria = getCriteriaForNextPage();
		if(searchCriteria.page.currentPage != null){
			postWorkEfficiencyReportAjax(searchCriteria,false);
		}
		
		if(searchCriteria.page.currentPage == lastPage)
	    {
	    	disableSpecificPaginationBlock("nextPage");
			disableSpecificPaginationBlock("lastPage");
	    }
}

function getPreviousPage() {
	

	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");

	var searchCriteria = getCriteriaForPreviousPage();
    if(searchCriteria.page.currentPage > 0){
    	postWorkEfficiencyReportAjax(searchCriteria,false);
    }
    if(searchCriteria.page.currentPage == 1)
	{
	   	disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
	
}

function getSelectedPage(pageNumber) {
	
	enableSpecificPaginationBlock("firstPage");
	enableSpecificPaginationBlock("previousPage");
	enableSpecificPaginationBlock("nextPage");
	enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var searchCriteria = getCriteriaForSelectedPage(pageNumber);
	postWorkEfficiencyReportAjax(searchCriteria,false);
	if(pageNumber == 1)
	{
		disableSpecificPaginationBlock("firstPage");
		disableSpecificPaginationBlock("previousPage");
	}
	else if(pageNumber == lastPage)
	{
		disableSpecificPaginationBlock("nextPage");
		disableSpecificPaginationBlock("lastPage");
	}	

}


function postWorkEfficiencyReportAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	disableButton('filterButton');
	$.ajax({
		url : '/compliance-portal/workEfficiencyReport',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			if(data.errorMessage !== null) {
				alert('Error : '+data.errorMessage);
				enableAllPaginationBloack();
				enableButton('filterButton');
			} else {
			setWorkEfficiencyReportResponse(data, status, jqXHR,isFilterReq);
			enableAllPaginationBloack();
			enableButton('filterButton');
			}
		},
		error : function(data, status, jqXHR) {
			alert('Error while fetching work efficiency report data');
			enableAllPaginationBloack();
			enableButton('filterButton');
		}
	});
}

function setWorkEfficiencyReportResponse(response, status, jqXHR,isFilterReq) {
	var rows = '';
	 removeWorkEfficiencyReportData();
	 $.each(response.workEfficiencyReportData, function(index, workEfficiency) {
		 rows += getWorkEfficiencyReportRow(workEfficiency,index+1,response.user);
		});
	 $("#workEfficiencyBody").append(rows);
	 setQueuePageObjectText(response.page,isFilterReq);
	 setDateDifferenceBetweenReports(response.dateDifference);
	 initUserLockedPopup();
}

function setDateDifferenceBetweenReports(dateDifference){
	
	$("#dateDifferenceBetweenReports").text(dateDifference);
}

function getPaymentOutSearchCriteriaObject() {
	var searchCriteria = {};
	addField('filter',getPaymentOutFilterObject(),searchCriteria);
	addField('page',getPaymentOutPageObject(),searchCriteria);
	//addField('sort',getSortObject(),searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutSearchFilter(filter,page) {
	var searchCriteria = {};
	addField('filter',filter,searchCriteria);
	addField('page',page,searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutSearchCriteriaForPage(page) {
	var searchCriteria = {};
	addField('page',page,searchCriteria);
	console.log('searchCriteria: '+ JSON.stringify(searchCriteria));
	return searchCriteria;
}

function getPaymentOutFilterObject() {
	var filter = $('#payOutQueueFilterForm').serializeObject();
	console.log('Filter: '+ JSON.stringify(filter));
	return filter;
}

function getPaymentOutPageObject() {
	var page = {};
	var minRecord = Number($("#payOutQueueMinRecord").text());
	var maxRecord = Number($("#payOutQueueMaxRecord").text());
	var totalRecords = Number($("#payOutQueueTotalRecords").text());
	var totalPages = Number($("#payOutQueueTotalPages").val());
	var pageSize = REG_QUQUE_PAGE_SIZE;
	addField("minRecord",minRecord,page);
	addField("maxRecord",maxRecord,page);
	addField("totalRecords",totalRecords,page);
	addField("currentPage",currentPage,page);
	addField("totalPages",totalPages,page);
	addField("pageSize",pageSize,page);
	console.log('page: '+ JSON.stringify(page));
	
	return page;
}

function setPaymentOutPageObjectText(page) {
	// searchCriteria.page = page;
	$("#payOutQueueMinRecord").text(page.minRecord);
	$("#payOutQueueMaxRecord").text(page.maxRecord);
	$("#payOutQueueTotalRecords").text(page.totalRecords);
	$("#payOutQueueTotalPages").val(page.totalPages);
	return page;
}

function removeWorkEfficiencyReportData() {
	$("#workEfficiencyBody").empty();
}

function getWorkEfficiencyReportRow(workEfficiency,rowNumber,user) {
	var row ='<tr class="talign">';

	row += getRowNumber(rowNumber,user);
	row += getWorkEfficiencyQueueTypeColumn(getEmptyIfNull(workEfficiency.queueType));
	row += getWorkEfficiencyUserNameColumn(getEmptyIfNull(workEfficiency.userName));
	row += getWorkEfficiencyAccountTypeColumn(getEmptyIfNull(workEfficiency.accountType));
	row += getWorkEfficiencyLockedRecordsColumn(getEmptyIfNull(workEfficiency.lockedRecords));
	row += getWorkEfficiencyReleasedRecordsColumn(getEmptyIfNull(workEfficiency.releasedRecords));
	row += getWorkEfficiencySecondsColumn(getEmptyIfNull(workEfficiency.seconds));
	row += getWorkEfficiencySlaValue(getEmptyIfNull(workEfficiency.slaValue));
	row += getWorkEfficiencyPercentEfficiencyColumn(getEmptyIfNull(workEfficiency.percentEfficiency));
	
	//row += getWorkEfficiencyTimeEfficiencyColumn(getEmptyIfNull(workEfficiency.timeEfficiency));
	row += '</tr>';
	return row;
}

function getRowNumber(rowNumber,user) {
	return '<td  hidden="hidden"> '+rowNumber+'</td>';
}

function getWorkEfficiencyQueueTypeColumn(queueType) {
	queueType = (queueType === null) ? '' : queueType;
	return '<td class="nowrap rowColour">  '+queueType+'  </td>';
}

function getWorkEfficiencyUserNameColumn(user) {
	user = (user === null) ? '' : user;
	return '<td class="nowrap rowColour">  '+user+'</td>';
}

function getWorkEfficiencyAccountTypeColumn(accountType) {
	accountType = (accountType === null) ? '' : accountType;
	return '<td class="nowrap rowColour"> '+accountType+' </td>';
}

function getWorkEfficiencyLockedRecordsColumn(lockedRecords) {
	lockedRecords = (lockedRecords === null) ? '' : lockedRecords;
	return '<td class="number rowColour"> '+lockedRecords+' </td>';
}

function getWorkEfficiencyReleasedRecordsColumn(releasedRecords) {
	releasedRecords = (releasedRecords === null) ? '' : releasedRecords;
	return '<td class="number rowColour"> '+releasedRecords+' </td>';
}

function getWorkEfficiencyPercentEfficiencyColumn(percentEfficiency) {
	percentEfficiency = (percentEfficiency === null) ? '' : percentEfficiency;
	return '<td class="number rowColour"> '+percentEfficiency+'</td>';
}

function getWorkEfficiencySecondsColumn(seconds) {
	seconds = (seconds === null) ? '' : seconds;
	return '<td class="number rowColour"> '+seconds+'</td>';
}

function getWorkEfficiencySlaValue(slaValue){
	slaValue = (slaValue === null) ? '':slaValue;
	return '<td class="number rowColour"> '+slaValue+'</td>';
}

function getWorkEfficiencyTimeEfficiencyColumn(timeEfficiency) {
	timeEfficiency = (timeEfficiency === null) ? '' : timeEfficiency;
	return '<td class="number rowColour"> '+timeEfficiency+' </td>';
}

/*function getPaymentOutQueueDetails(paymentOutId,anchor) {
	var url = '/compliance-portal/paymentOutDetail?paymentOutId='+paymentOutId;
	var	searchCriteria = getQueueSearchCriteriaObject(); 
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	url +='&searchCriteria='+getJsonString(searchCriteria);
	$('#custType').val(custType);
	$('#paymentOutId').val(paymentOutId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#payOutQueueForm').submit();
	//$(anchor).attr("href", url); 
}*/

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function getSortData(sortParameter) {
	fieldName = sortParameter;
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	postWorkEfficiencyReportAjax(request);
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postWorkEfficiencyReportAjax(searchCriteria,true);
	console.log(searchCriteria);
}

function searchRegReportDownLoadSearchCriteria() {
	currentPage = 1;
	var request = getQueueSearchCriteriaObject();
	searchCriteria = request;
	$('#downloadSearchCriteria').val(getJsonString(searchCriteria));
	$('#workEfficiencyReportDownLoadForm').submit();
}

function checkTotalRecordsAndDownload() {
	var totalRecords = Number(getTextById("queueTotalRecords"));
	if(totalRecords > 5000)
	{
		var theModalMask = $('#modal-mask');
		$("#downloadWorkEfficiencyMessage").text('More than 5000 records found. Please change filter criteria to limit search results.');
		$("#downloadWorkEfficiencyMessage").attr('readonly', true);
			$("#downloadWorkEfficiencyPopup").dialog({
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
						$(theModalMask).removeClass("modal-mask--visible").addClass("modal-mask--hidden");
					}
				}
			});
	}else if(totalRecords == 0){
		var theModalMask = $('#modal-mask');
		$("#downloadWorkEfficiencyMessage").text('Please change filter criteria to limit search results.');
		$("#downloadWorkEfficiencyMessage").attr('readonly', true);
			$("#downloadWorkEfficiencyPopup").dialog({
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
						$(theModalMask).removeClass("modal-mask--visible").addClass("modal-mask--hidden");
					}
				}
			});
	}
	else{
		searchRegReportDownLoadSearchCriteria();
	}
}



