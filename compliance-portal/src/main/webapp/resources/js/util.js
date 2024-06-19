$(document).ready(function() {
	setUserPermissionByRole();
	$("#date-payment-from, #date-payment-to, #date-registered-from, #date-registered-to, #value-date-payment-from, #value-date-payment-to").datepicker('option', 'dateFormat', 'dd/mm/yy');
});

function getJsonObject(text){
	return JSON.parse(text);
}

function getJsonString(object) {
	return JSON.stringify(object);
}

function addField(field,value,object){
	if(!isEmpty(value))
	     object[field] = value;
	return object;
}

function isEmpty(value){
	return (value === "" || value === undefined) ? true :false; 
}

function isNull(value){
	return (value === null || value === undefined) ? true :false; 
}

function getEmptyIfNull(value) {
	if(value === undefined || value === null) {
		return '';
	}
	return value;
}

function getDashIfNull(value) {
	if(value === undefined || value === null || value==='') {
		return '--';
	}
	return value;
}

function getSingleDashIfNull(value) {
	if(value === undefined || value === null || value==='') {
		return '-';
	}
	return value;
}

function getIsEmptyOrNull(value) {
	if(value === undefined || value === null || value==='') {
		return true;
	}
	return false;
}

function setValueById(id,value) {
	return $('#'+id).val(value);
}

function getValueById(id) {
	return $('#'+id).val();
}

function setTextById(id,value) {
	$('#'+id).text(value);
}

function getTextById(id) {
	return $('#'+id).text();
}

function disableAllLockBasedButtons(){
	if(!$('input[type=button]:not(.no-lock-support)').hasClass('button--disabled')){
		$('input[type=button:not(.no-lock-support)]').addClass('button--disabled');
		$('input[type=button]:not(.no-lock-support)').attr('disabled',true);
	}
}

function disableAllButtons(){
	$('input[type=button]').each(function(){
		 var element = $(this);
		if(!$(element).hasClass('button--disabled')  && !(element.attr("id") === 'account_viewHolisticView' 
			|| element.attr("id") ==='account_AccountDetails' || element.attr("id") ==='account_FurtherClient_deviceInfo'
			|| element.attr("id") ==='account_AccountWhiteList' || element.attr("id") ==='account_FurtherClient_viewClient')){
			$(element).addClass('button--disabled');
			$(element).attr('disabled',true);
		}
	});
}

function disableButton(id){
	if(!$('#'+id).hasClass('button--disabled')){
		$('#'+id).addClass('button--disabled');
		$('#'+id).attr('disabled',true);
	}
}

function enableButton(id){
	if($('#'+id).hasClass('button--disabled')){
		$('#'+id).removeClass('button--disabled');
		$('#'+id).attr('disabled',false);
	}
}

function enableAllButtons(){
	if($('input[type=button]').hasClass('button--disabled')){
		$('input[type=button]').removeClass('button--disabled');
		$('input[type=button]').attr('disabled',false);
	}
}

function enableAllLockBasedButtons(){
	if($('input[type=button]:not(.no-lock-support)').hasClass('button--disabled')){
		$('input[type=button]:not(.no-lock-support)').removeClass('button--disabled');
		$('input[type=button]:not(.no-lock-support)').attr('disabled',false);
	}
}

function toggleButtonDisability(){
	if($('input[type=button]').hasClass('button--disabled')){
		$('input[type=button]').removeClass('button--disabled');
		$('input[type=button]').attr('disabled',true);
	} else {
		$('input[type=button]').addClass('button--disabled');
		$('input[type=button]').attr('disabled',false);
	}
}


$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
    	var isValid = true;
    	if( this.value === undefined || this.value === null || this.value === '') {
    		isValid = false;
    	}
    	if(isValid) {
    		var isArray=false;
    		if(this.name.indexOf("[]") !== -1) {
        		isArray=true;
        		this.name = this.name.replace("[]", "");
        	}
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else if(isArray) {
                    o[this.name] = [this.value || ''];
            } else {
                o[this.name] = this.value || '';
            }
    	}
    });
    return o;
};

function getDateTimeFormat(dateTime){
	  var date = dateTime.split(' ')[0].split('-');
      var od = date[2] + "/" + date[1] + "/" + date[0];
      var time=dateTime.split(" ")[1].split('.');
      var result = od + " " +time[0];
	return result;
}

function getDateFormat(dateTime){
	if(dateTime===null || dateTime===undefined || dateTime===''){
		return "----";
	}
	var date = dateTime.split(' ')[0].split('-');
	var result = date[2] + "/" + date[1] + "/" + date[0];
	return result;
}

function disableAllPaginationBlock(){
	$("#paginationBlock").addClass("disabled");
}
/**functions added by Vishal J to disable specific pagination*/

function disableFirstRecordButton(){
	$("#firstRecord").addClass("disabled");
}

function disablePreviousRecordButton(){
	$("#previousRecord").addClass("disabled");
}

function disableNextRecordButton(){
	$("#nextRecord").addClass("disabled");
}

function disableLastRecordButton(){
	$("#lastRecord").addClass("disabled");
}

function disableSpecificPaginationBlock(id)
{
	$('#'+id).addClass("disabled");
}

function enableSpecificPaginationBlock(id)
{
	$('#'+id).removeClass("disabled");
}
//End of functions added by Vishal J

function enableAllPaginationBloack(){
	$("#paginationBlock").removeClass("disabled");
}

function disableAllCheckBlocks(){
	$(".accordion__header i").addClass("disabled");
}

function enableAllCheckBlocks(){
	$(".accordion__header i").removeClass("disabled");
}

function onSubNav(id){
	$('#'+id).removeClass('sub-nav__item').addClass('sub-nav__item--on');
}

/**
 * Implementation:
 * 1. Getting user information from hidden field with id userInfo
 * 2. From that setting permission for left menu , customer type 
 *    specific field permission
 * 3.setting permissions for service (EID,Sanction,Blacklist,Fraugster) management
 * 4.setting watchlist permissions
 * 5.setting lock/unlock record permissions
 * 
 */
function setUserPermissionByRole(){
	var user = getJsonObject(getValueById('userInfo'));
	var permissions = user.permissions;
	if(permissions === undefined || permissions === null){
		return ;
	}
	
	console.log(permissions);
	setLeftMenuPermissions(user);
	setCustTypePermissions(user);
	setServicePermissions(user);
	setWatchlistPermissions(user);
	setLockRecordPermisssion(user);
	setDetailsPagePermission(user);
	setAdministrationPagePermission(user);
	forcefullUnlock(user);
	canNotViewLock();   
}

function setLeftMenuPermissions(user){
	var permissions = user.permissions;
	if(!permissions.canViewRegistrationQueue){
		$("#reg-sub-nav").remove();
	}
	if(!permissions.canViewPaymentInQueue){
		$("#payIn-sub-nav").remove();
	}
	if(!permissions.canViewPaymentOutQueue){
		$("#payOut-sub-nav").remove();
	}
	if(!permissions.canViewRegistrationReport){
		$("#reg-report-sub-nav").remove();
	}
	if(!permissions.canViewPaymentInReport){
		$("#payIn-report-sub-nav").remove();
	}
	if(!permissions.canViewPaymentOutReport){
		$("#payOut-report-sub-nav").remove();
	}
	if(!permissions.canViewWorkEfficiacyReport){
		$("#workEff-sub-nav").remove();
	}
	if(!permissions.canViewDashboard){
		$("#dashboard-sub-nav").remove();
	}
	if(permissions.isReadOnlyUser){
		$("#holistic-sub-nav").remove();
	}
	if(!permissions.canManageBeneficiary){
		$("#bene-report-sub-nav").remove();
		$("#other-nav").remove();
	}
	/*if(!permissions.isReadOnlyUser){
		$("#bene-report-sub-nav").remove();
	}*/
}


/**
 * User may or may not have permission to manage services like Eid , Sanction ,Blacklist etc.
 * So in this method getting permissions of user and 
 * on that basis setting permission for each service.
 */
function setServicePermissions(user){
	var permissions = user.permissions;
	
	if(!permissions.canManageSanction){
		$(".sanction-field").remove();
	}
	if(!permissions.canManageBlackList){
		$(".blackList-field").remove();
	}
	if(!permissions.canManageEID){
		$(".eid-field").remove();
	}
	if(!permissions.canManageCustom){
		$(".custom-field").remove();
	}
	if(!permissions.canManageFraud){
		$(".fraud-field").remove();
	}

}

function setWatchlistPermissions(user){
	var permissions = user.permissions;
	if(!permissions.canManageWatchListCategory1 && !permissions.canManageWatchListCategory2){
		$(".watchlist-field").remove();
	}
}

function setLockRecordPermisssion(user){
	var permissions = user.permissions;
	if(!permissions.canUnlockRecords){
		$(".unlock-field").remove();
	}
}

/**
 * User may or may not have permissions to access customer type (i.e. PFX,CFX) specific fields
 * So from user permissions removing customer type specific fields  
 */
function setCustTypePermissions(user){
	var permissions = user.permissions;
	if(!permissions.canWorkOnCFX){
		$(".cfx-field").remove();
	}
	if(!permissions.canWorkOnPFX){
		$(".pfx-field").remove();
	}
}

function setDetailsPagePermission(user){
	var permissions = user.permissions;
	
	if(!permissions.canViewRegistrationDetails||permissions.isReadOnlyUser){

		$('#regQueueBody a').addClass('removeHyperLink');
		//$('#reg-service__status-filter').remove();
		$('#dataAnonBody a').addClass('removeHyperLink');
	}
	if(!permissions.canViewPaymentInDetails||permissions.isReadOnlyUser){
		//$('#payInQueueBody a').removeAttr('onclick');
		$('#payInQueueBody a').addClass('removeHyperLink');
        //$('#pay-in-service__status-filter').remove();
	}
   if(!permissions.canViewPaymentOutDetails||permissions.isReadOnlyUser){
			//$('#payOutQueueBody a').removeAttr('onclick');
			$('#payOutQueueBody a').addClass('removeHyperLink');
		    //$('#pay-out-service__status-filter').remove();
	}    	 	 
}


function setAdministrationPagePermission(user){
	var permissions = user.permissions;
	if(!permissions.canDoAdministration){
		$('#drawer-user').hide();
	}
}

function forcefullUnlock(user){
	var permissions = user.permissions;
	if( permissions.canUnlockRecords && $("#ownRecord").length !== 0 && $("#toggle-edit-lock").length === 0  ){
		$("#lock")
		.append(
				'<div id="toggle-edit-lock" class="toggle f-right" data-ot-show-on-load="Lock this record to own it and update"><a href="#" id="toggle-record-lock" onclick="'
						+ 'lockResource()" class="toggle__option--on " data-ot="Lock this record to own it"><i class="material-icons">lock_outline</i></a><a href="#" id="toggle-record-unlock" onclick="'
						+ 'unlockResource()" class="toggle__option " data-ot="Unlock this record for others"><i class="material-icons">lock_open</i></a></div>');
		enableAllLockBasedButtons();
	}
	
}

function populateSuccessMessage(id,message,errorFieldId,errorMessageFieldId){
	$("#"+id+" .message--positive .copy").empty().append("<p>"+message+"</p>");
	$("#"+id+" .message--positive").css("display","block");
	removeFieldByClass(errorMessageFieldId,id);
	hideErrorField(errorMessageFieldId,errorFieldId);
}

function populateErrorMessage(id,message,errorFieldId,errorMessageFieldId){
	removeFieldByClass(errorMessageFieldId,id);
	$("#"+id+" .message--negative .copy ul").append("<li class="+errorMessageFieldId+">"+message+"</li>");
	$("#"+id+" .message--negative").css("display","block");
	showErrorField(errorMessageFieldId,errorFieldId);
}

function hideErrorBlock(id){
	$("#"+id+" .message--negative").css("display","none");
}

function showErrorField(id,errorField){
	$("#"+id).addClass("form__field-wrap--errored");
	$("#"+errorField).show();
	
}

function hideErrorField(id,errorField){
	$("#"+id).removeClass("form__field-wrap--errored");
	$("#"+errorField).hide();
	
}

function removeErrorFieldByClass(clazz){
	$("."+clazz).remove();
	
}

function removeFieldByClass(clazz,id){
	$("."+clazz).remove();
	if($("#"+id+" .message--negative .copy ul li").length === 0){
		hideErrorBlock(id);
	}
}

function isMatchToRegEx(regExpression,value){
	  var patt = new RegExp(regExpression);
	    return patt.test(value);
}

function isListEmpty(listLength){
	if(listLength === 0){
		return true;
	}
	return false;
}

function getDashIfListIsEmpty(value){
	if(value === null || value === undefined || value ===''){
		return '--';
	}
	return value;
}

function getEnglishNumberFormat(value){
	
	if(!isNull(value) && !isEmpty(value) && getDashIfListIsEmpty(value) != '--'){
		var numberformat = Number(value);
		return numberformat.toLocaleString('en-UK',{minimumFractionDigits: 2});
	}
	return value
}

function removeWhiteSpaces(value) {
	return value.trim();
}

/**
 * Deep compare of two objects.
 *
 * Note that this does not detect cyclical objects as it should.
 * Need to implement that when this is used in a more general case. It's currently only used
 * in a place that guarantees no cyclical structures.
 *
 * @param {*} x
 * @param {*} y
 * @return {Boolean} Whether the two objects are equivalent, that is,
 *         every property in x is equal to every property in y recursively. Primitives
 *         must be strictly equal, that is "1" and 1, null an undefined and similar objects
 *         are considered different
 */
function isObjectEquals(x,y){
	    // If both x and y are null or undefined and exactly the same
	    if ( x === y ) {
	        return true;
	    }

	    // If they are not strictly equal, they both need to be Objects
	    if ( ! ( x instanceof Object ) || ! ( y instanceof Object ) ) {
	        return false;
	    }

	    // They must have the exact same prototype chain, the closest we can do is
	    // test the constructor.
	    if ( x.constructor !== y.constructor ) {
	        return false;
	    }

	    for ( var p in x ) {
	        // Inherited properties were tested using x.constructor === y.constructor
	        if ( x.hasOwnProperty( p ) ) {
	            // Allows comparing x[ p ] and y[ p ] when set to undefined
	            if ( ! y.hasOwnProperty( p ) ) {
	                return false;
	            }

	            // If they have the same strict value or identity then they are equal
	            if ( x[ p ] === y[ p ] ) {
	                continue;
	            }

	            // Numbers, Strings, Functions, Booleans must be strictly equal
	            if ( typeof( x[ p ] ) !== "object" ) {
	                return false;
	            }

	            // Objects and Arrays must be tested recursively
	            if ( !isObjectEquals( x[ p ],  y[ p ] ) ) {
	                return false;
	            }
	        }
	    }

	    for ( p in y ) {
	        // allows x[ p ] to be set to undefined
	        if ( y.hasOwnProperty( p ) && ! x.hasOwnProperty( p ) ) {
	            return false;
	        }
	    }
	    return true;
}


function elementAvailable(elementId) {
	var element = document.getElementById(elementId);
	if(element!=null) {
		return true;
	}
	return false;
}

function formatDate(date) {
	var dd = date.getDate();
	if(dd<10) {
		dd='0'+dd;
	}
	var mm = date.getMonth()+1; 
	if(mm<10) {
		mm='0'+mm;
	}
	var yyyy = date.getFullYear();
	date = dd+'/'+mm+'/'+yyyy;
	return date;
}

function checkIfOnlySpaceEntered(id){
	if("" === removeWhiteSpaces($("#"+id).val()))
		return true;
	return false;
}

function printNestedJson(object,itr,tab){
	var row = '';
	for(var keys in object){
		if(typeof(object[keys]) === 'object'){
			itr++;
			if($.isNumeric(keys) || null === object[keys])
				row+='';
			else
				row+='<br>'+createTab(tab)+'<span class ="bold">'+keys+' : </span><br>';
			tab++;
			row+= printNestedJson(object[keys],itr,tab);
			tab--;
		}
		else
			row+=createTab(tab)+'<span class ="bold">'+keys+' : </span><span> '+getDashIfNull(object[keys])+'</span><br>';
	}
	return row;
}

function isObjectPropertyExist(object,property){
	return object.hasOwnProperty(property);
}

function getDashIfObjectPropertyNotExist(value){
	if(value === false) {
		return '--';
	}
	return value;
}

function createTab(count){
	var tab = '';
	for(var i = 0; i < count; i++){
		tab = tab + '&emsp;';
	}
	return tab;
}

function isJson(str){
	var jSon;
	try{ jSon = JSON.parse(str);
	}catch(e){console.log(e);
		return jSon = {};
	}
	return jSon;
}

function canNotViewLock() {
	var user = getJsonObject(getValueById('userInfo'));
	var permissions = user.permissions;
	if (permissions.canNotLockAccount)
		genericCanNotViewLock();
}

function removeLockUnclockButtons() {
	$("#lock").attr("style", "display:none");
}

function genericCanNotViewLock(){
	removeLockUnclockButtons();
	unlockResource();
}

function getDateTimeFormatTZ(dateTime){
	if(dateTime===null || dateTime===undefined || dateTime===''){
		return "-";
	}
	var dt = dateTime.replace('T', ' ').replace('Z', '');
	var date = dt.split(' ')[0].split('-');
	var od = date[2] + "/" + date[1] + "/" + date[0];
	var time=dt.split(" ")[1].split('.');
	var result = od + " " +time[0];
	return result;
}