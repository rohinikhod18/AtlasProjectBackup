/**
 * @author deepakk
 */
var DATA_ANON_QUQUE_PAGE_SIZE = 50;
var currentPage=1;
var regDetailsViewUrl = '#';

$(document).ready(function() {
	onSubNav('dataAnon-sub-nav');
	/*disableSpecificPaginationBlock("firstPage");
	disableSpecificPaginationBlock("previousPage");*/
	setFilterFieldsAsPerCriteria();
});

/**added to enable filter button on press of Enter key when search criteria is selected in filter */
addEventListener("keyup", function(event) {
if (event.keyCode == 13) {
    document.getElementById("data_anon_queue_Filter").click();
}
});

function applyDataAnonQueueSearchCriteria() {
	
	$('#dataAnonForm, #pageCountDetails, #paginationBlock').css('display','none');
	$('#loadingobject').css('display','block');

	$('#gifloaderforqueue').css('visibility','visible');	
	currentPage = 1;
	setFilterApply(true);
	$('#currentpage').val(currentPage);

	// Added isFilterForDashboard to apply filter and pagination from dashboard
	$('#isDashboardSearchCriteria').val(false);
	var request = getQueueSearchCriteriaObject();
	postDataAnonQueueAjax(request,true);
}

function clearAndApplyFilter(){
	clearFilters();  
	setFilterApply(false);
	applyDataAnonQueueSearchCriteria();
}

function getNextPage() {
	
	enableSpecificPaginationBlock("firstPage");
    enableSpecificPaginationBlock("previousPage");
    enableSpecificPaginationBlock("nextPage");
    enableSpecificPaginationBlock("lastPage");
	var lastPage = $("#queueTotalPages").val();
	var	 searchCriteria = getCriteriaForNextPage();	 
	if(searchCriteria.page.currentPage != null){
		postDataAnonQueueAjax(searchCriteria,false);
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
		postDataAnonQueueAjax(searchCriteria,false);
	}
	    
	if(searchCriteria.page.currentPage === 1)
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
	var  searchCriteria = getCriteriaForSelectedPage(pageNumber);
	postDataAnonQueueAjax(searchCriteria,false);
	
	if(pageNumber === 1)
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

function postDataAnonQueueAjax(request,isFilterReq) {
	disableAllPaginationBlock();
	//disableButton('"data_anon_queue_Filter"');
	$.ajax({
		url : '/compliance-portal/dataAnonQueue',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data, status, jqXHR) {
			$('#gifloaderforqueue').css('visibility','hidden');
			
			enableButton('data_anon_queue_Filter');
			setDataAnonQueueResponse(data, status, jqXHR,isFilterReq);
			
			 enableAllPaginationBloack();
			 //to display total records found after applying filter -Neelesh pant
			if (isFilterReq) {
				getTotalRecordsAfterApplyingFilter();
				$('html, body').animate({scrollTop: '0px'}, 0);
			}
			$('#loadingobject').css('display','none');
			$('#dataAnonForm,#pageCountDetails').css('display','block');
		},
		error : function(data, status, jqXHR) {
			$('#gifloaderforqueue').css('visibility','hidden');
			enableButton('data_anon_queue_Filter');
			setRegQueueErrorResponse(getJsonObject(data), status, jqXHR);
			enableAllPaginationBloack();
			$('#loadingobject').css('display','none');
			$('#dataAnonForm,#pageCountDetails').css('display','block');
		}
	});
}

function setDataAnonQueueResponse(response, status, jqXHR,isFilterReq) {
	var rows = '';
	removeAnonQueueData();
	 $.each(response.dataAnonymisation, function(index, anonymisation) {
		 rows += getDataAnonQueueRow(anonymisation,index+1,response.user);
		});
	 
	 $("#dataAnonBody").append(rows);
	 setQueuePageObjectText(response.page,isFilterReq);
	 $("#searchCriteriaQueue").val(response.searchCriteria);
	 $("#currentpage").val(response.page.currentPage);
	 var keywordInput = $("input[name=keyword]");
	 var keywordValue = $(keywordInput).val();
	 if(keywordValue !== undefined && isMatchToRegEx('^[0-9]{6}$',keywordValue)) {
		 clearFiltersExceptKeywordSearch();
	 }
	 initUserLockedPopup();
	 if(!response.user.permissions.canViewRegistrationDetails||response.user.permissions.isReadOnlyUser){
		
			$('#dataAnonBody a').addClass('removeHyperLink');
	 }	 
}

function removeAnonQueueData() {
	$("#dataAnonBody").empty();
}

function getDataAnonQueueRow(anonymisation,rowNumber,user) {
	var conId = anonymisation.contactId;
	var accId = anonymisation.accountId;
	var initialBy = anonymisation.initialApprovalBy;
	var Id=anonymisation.id;
	var row;
	if(anonymisation.locked && anonymisation.lockedBy === user.name){
		row = '<tr class="owned talign" data-ot="You own(s) this record">';
	} else if (anonymisation.locked){
		row = '<tr class="talign unavailable" data-ot="'+anonymisation.lockedBy+' own(s) this record">';
	} else {
		row='<tr class="talign">';
	}
	row += getRowNumber(rowNumber,conId, getEmptyIfNull(anonymisation.type));
	row += getRegQueuClientNumberColumn(conId,getEmptyIfNull(anonymisation.tradeAccountNum), getEmptyIfNull(anonymisation.type));
	row += getRegQueuClientNameColumn(conId,getEmptyIfNull(anonymisation.contactName), getEmptyIfNull(anonymisation.type));
	row += getRegQueueTypeColumn(conId,getEmptyIfNull(anonymisation.type));
	row += getRegQueueRequestInDateColumn(conId,getEmptyIfNull(anonymisation.initialApprovalDate), getEmptyIfNull(anonymisation.type));
	row += getRegQueueRequestByColumn(conId,getEmptyIfNull(anonymisation.initialApprovalBy), getEmptyIfNull(anonymisation.type));
	if(anonymisation.dataAnonymizationStatus == 2){
		row += getRegQueueApprovedInDateColumn(conId,getEmptyIfNull(anonymisation.finalApprovalDate), getEmptyIfNull(anonymisation.type));
		row += getRegQueueApprovedByColumn(conId,getEmptyIfNull(anonymisation.finalApprovalBy), getEmptyIfNull(anonymisation.type))
	}else{
		row += getRegQueueApprovedInDateColumn(conId,"",getEmptyIfNull(anonymisation.type));
		row += getRegQueueApprovedByColumn(conId,"",getEmptyIfNull(anonymisation.type));
	}
	row += getRegQueueStatusColumn(conId,getEmptyIfNull(anonymisation.complianceStatus), getEmptyIfNull(anonymisation.type));
	if(anonymisation.dataAnonymizationStatus == 2){
		row += '<td><input type="button" disabled="disabled" id="confirm_button" class="button--primary button--small button--disabled" value="Confirm" onclick="DataAnonHistory('+ Id +',this.id, \''+initialBy+'\','+accId+','+conId+')"/></td>';
	}else{
		row += '<td><input type="button" id="confirm_button" class="button--primary button--small" value="Confirm" onclick="DataAnonHistory('+ Id +',this.id, \''+initialBy+'\','+accId+','+conId+')"/></td>';
	}
	if(anonymisation.dataAnonymizationStatus == 2){
		row += '<td><input type="button" id="cancel_button" disabled="disabled" class="button--primary button--small button--disabled" value="Cancel" onclick="DataAnonHistory('+ Id +',this.id, \''+initialBy+'\','+accId+','+conId+')"/></td>';
	}else{
		row += '<td><input type="button" id="cancel_button" class="button--primary button--small" value="Cancel" onclick="DataAnonHistory('+ Id +',this.id, \''+initialBy+'\','+accId+','+conId+')"/></td>';
	}
		row += '</tr>';
	return row;
}

function getRowNumber(rowNumber, contactId, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td  hidden="hidden"><a '+regDetailsFunctionName+' >'+rowNumber+'</a></td>';
}

function getRegQueuClientNumberColumn(contactId, clientNumber, custType) {
	clientNumber = (clientNumber === null) ? '' : clientNumber;
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="number"><a '+regDetailsFunctionName+' >'+clientNumber+'</a></td>';
}

function getRegQueueRegisteredOnColumn(contactId, regOn, custType) {
	regOn = (regOn === null) ? '' : regOn;
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="nowrap"><a '+regDetailsFunctionName+' >'+regOn+'</a></td>';
}

function getRegQueuClientNameColumn(contactId,clientName, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td class="wordwrapfixwidth"><a '+regDetailsFunctionName+' >'+clientName+'</a></td>';
}

function getRegQueueTypeColumn(contactId, type) {
	var regDetailsFunctionName = 'getRegistrationQueueDetails('+contactId+',\''+type+'\',this);'
	return '<td><a '+regDetailsFunctionName+' >'+type+'</a></td>';
}

function getRegQueueCountryOfResidenceColumn(contactId, countryOfResidence, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+countryOfResidence+'</a></td>';
}

function getRegQueueRequestByColumn(contactId , initialApprovalBy, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+initialApprovalBy+'</a></td>';
}

function getRegQueueRequestInDateColumn(contactId, initialApprovalDate, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td style="text-align: center" class="nowrap"><a '+regDetailsFunctionName+' >'+initialApprovalDate+'</a></td>';
}

function getRegQueueApprovedByColumn(contactId , finalApprovalBy, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td><a '+regDetailsFunctionName+' >'+finalApprovalBy+'</a></td>';
}

function getRegQueueApprovedInDateColumn(contactId, finalApprovalDate, custType) {
	var regDetailsFunctionName = getFunctionDetails(contactId,custType);
	return '<td style="text-align: center" class="nowrap"><a '+regDetailsFunctionName+' >'+finalApprovalDate+'</a></td>';
}

function getRegQueueStatusColumn(contactId, complianceStatus, custType) {
	var regDetailsFunctionName = 'getRegistrationQueueDetails('+contactId+',\''+custType+'\',this);'
	return '<td class="nowrap"><a '+regDetailsFunctionName+' >'+complianceStatus+'</a></td>';
}

function getRegistrationQueueDetails(contactId,custType,anchor) {
	/** set false Origin is registration queue page*/
	setRequestOrigin(false);
	var searchCriteria = getQueueSearchCriteriaObject();
	var columnIndex = $(anchor).parent().parent().index()+1;
	addField("currentRecord",setCurrentRecord(searchCriteria.page.minRecord,columnIndex),searchCriteria.page);
	$('#custType').val(custType);
	$('#contactId').val(contactId);
	$('#searchCriteria').val(getJsonString(searchCriteria));
	$('#dataAnonForm').submit();
}

function setCurrentRecord(minRecord,columnIndex) {
	return Number(minRecord) - 1 + columnIndex;
}

function sortByField(fieldName){
	var searchCriteria =  getSortObjectWithSearchCriteria(fieldName);
	postDataAnonQueueAjax(searchCriteria,true);
	console.log(searchCriteria);
}

function isDetailsPermission(){
	var user = getJsonObject($("#userInfo").val());
	return user.permissions.canViewRegistrationDetails;
}

function getFunctionDetails(contactId,custType){
	if(isDetailsPermission()){
		return 'onclick="getRegistrationQueueDetails('+contactId+',\''+custType+'\',this);"';
	}
	return '';
}

function updateDataAnon(req) {
	var request = {};
	addField('enterComment',req.enterComment,request);
	addField('contactId', req.contactId, request);
	addField('accountId', req.accountId, request);
	
	$.ajax({
		url: '/compliance-portal/updateDataAnon',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();
		},
		error: function(data) {
		}
	});
}

function cancelDataAnon(req) {
	var request = {};
	addField('enterComment',req.enterComment,request);
	addField('accountId', req.accountId, request);
	
	$.ajax({
		url: '/compliance-portal/cancelDataAnon',
		type: 'POST',
		data: getJsonString(request),
		contentType: "application/json",
		success: function(data) {
			document.location.reload();

			
		},
		error: function(data) {
		}
	});
}

function DataAnonHistory(ID,buttonId,initialBy,accId,conId) {
	var getDataAnonHistoryRequest = {};
	addField('buttonId',buttonId,getDataAnonHistoryRequest);
	addField('id',  Number(ID), getDataAnonHistoryRequest);
	addField('initialBy',  initialBy, getDataAnonHistoryRequest);
	addField('accountId',  accId, getDataAnonHistoryRequest);
	addField('contactId',  conId, getDataAnonHistoryRequest);
	getDataAnonHistory(getDataAnonHistoryRequest);
}

function getDataAnonHistory(request) {
	$.ajax({
		url : '/compliance-portal/getDataAnonHistory',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			addField('initialBy',request.initialBy  , data);
			addField('id',request.Id  , data);
			addField('buttonId',request.buttonId,data);
			addField('accountId',  request.accountId, data);
			addField('contactId',  request.contactId, data);
			showDataAnonHistoryPopup(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function showDataAnonHistoryPopup(data) {
	$("#DataAnonHistory").addClass("popupLinks");
	var buttonId=data.buttonId;
	var row = setDataAnonHistoryRow(data);
	if (data.buttonId =="cancel_button") {
		$('#BtnId').html("Enter Reason to Cancel Data Anonymisation");
	}
	else
		{
		$('#BtnId').html("Enter Reason to Confirm Data Anonymisation");
		}
	$('#DataAnonHistory').html(row);
	$("#DataAnonHistory").attr('readonly', true);
	
	$("#DataAnonHistorypopups").dialog({
		modal : true,
		draggable : false,
		resizable : false,
		show : 'blind',
		hide : 'blind',
		width : (800),
		dialogClass : 'ui-dialog-osx',
		buttons :[{
			text: 'OK',
			click : function() {
				var request = {};
				var  enterComment = document.getElementById("RejectDataAnonReason").value.trim();
				addField('enterComment',enterComment,request);
				addField('Id',data.id,request);
				addField('buttonId',data.buttonId,request);
				addField('accountId', data.accountId, request);
				addField('contactId', data.contactId, request);
				var userinfo = getJsonObject(getValueById('userInfo'));
				var user = userinfo.name;
				if(user!=data.initialBy)
					{
					if (checkIfOnlySpaceEntered('RejectDataAnonReason')) {
						$("#RejectDataAnonReason").val('');
						$("#errorDescription2").text("Please Enter reason");
						$("#errorDiv2").css('display', 'block');
					} else if (request.buttonId =="cancel_button") {
						$("#BtnId").val('Enter Reason For Cancel Button');
					cancelDataAnon(request);
				    } else {
					$("#BtnId").val('Enter Reason For Confirm Button');
					updateDataAnon(request);
					$(this).dialog("close");
				      }
			       }else{
			    	   $(this).dialog("close");
					   setTimeout(function(){alert('Data anonymisation request Initiator and Approver cannot be same') }, 1000);
			}
		  }
		},
		{
			text: 'Close',
			click : function() {
			$(this).dialog("close");
			$("#modal-mask").addClass("modal-mask--hidden");
			$("#DataAnonHistory").html('');	
			$("#errorDiv2").css('display', 'none');
			$("#RejectDataAnonReason").val('');
			}
		}],
	});
}

function setDataAnonHistoryRow(data){
	var row='';
	row += setRejectedHistory(data);
	return row;
}
function setRejectedHistory(data){
	var row='';
	var j=data.dataAnonymisation.length;
	row +='<span class ="bold">-- Data Anonymisation request history --</span></br></br>';
	
	for(var i=0; i< data.dataAnonymisation.length; i++)
			{ 
			   row +='</br><span>'+j+' </span><span class ="bold">. Data Anonymisation <span> '+data.dataAnonymisation[i].activity +'</span> request</span></br></br>';
			   row+='<span class ="bold"> Invoked By : </span><span> '+getDashIfNull(data.dataAnonymisation[i].activityBy)+'</span><br>';
			   row+='<span class ="bold"> Invoked Date :  </span><span> '+getDashIfNull(data.dataAnonymisation[i].activityDate)+'</span><br>';
			   row+='<span class ="bold">Comment :  </span><span> '+getDashIfNull(data.dataAnonymisation[i].comment)+'</span><br><br>';
		
			   j--;
			}
	return row;
	
}






