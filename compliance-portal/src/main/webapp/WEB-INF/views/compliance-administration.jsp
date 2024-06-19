<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html lang="en">

	<head>
		<meta charset="utf-8"/>
		<meta name="description" content="Enterprise tools"/>
		<meta name="copyright" content="Currencies Direct"/>
		<!--  <link rel="stylesheet" href="resources/css/scrollpane.style.css"> -->
		 <link rel="stylesheet" href="resources/css/pages/timepicker.css">
		 <link rel = "stylesheet" href = "resources/css/jquery-ui.css">
	</head>

	<body>

<main class="main-content--large">

	<h1>
		Administration

		
<!-- BEGIN: BREADCRUMBS -->

<span class="breadcrumbs">

	<span class="breadcrumbs__crumb--in">in</span>
	<span class="breadcrumbs__crumb--area">Your Profile</span>

	
</span>

<!-- END: BREADCRUMBS -->
	</h1>

	<div class="main-content__wrap">

		
<!-- BEGIN: MINIMISED FILTERS -->

<div class="filter-minimised hidden">
	<i class="material-icons">filter_list</i>
	<span class="filter-minimised__number">3</span>
</div>

<!-- END: MINIMISED FILTERS -->

		<div class="main-content__main">

			<!-- <div class="tabs"> -->

				<!-- <ul class="tabs__tabs">

					<li class="tabs__tab--on">
						<a href="#"><i class="material-icons">face</i> Private</a>
					</li>

					<li class="tabs__tab">
						<a href="#"><i class="material-icons">work</i> Corporate</a>
					</li>

				</ul> -->

			<!-- 	<div class="tabs__content--on"> -->
				
<div class="grid">

	<div class="grid__row">

		<div class="grid__col--8">

			<form action="#">

				<div class="splitpanel space-after" style="width: 1090px;">

					<section class="splitpanel__section">

						<div class="grid">

							<div class="grid__row">

								<div class="grid__col--3">
									<h2 class="label">Blacklists</h2>
								</div>

								<div class="grid__col--9">
								
								<div class="grid">
								
								<div class="grid__row">
								
								
								<div class="grid__col--2 copy">
									<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('Name')" >Name</a></p>
								</div>
						 
		<div class="grid__col--2 grid__col--pad-left copy">
			 <p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('Email')" >Email</a></p>
		</div>
		<div class="grid__col--2 grid__col--pad-left copy">
			<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('DOMAIN')" >DOMAIN</a></p>
		</div>
		<div class="grid__col--2 grid__col--pad-left copy">
			<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('IPADDRESS')" >IP</a></p>
		</div>	
		<div class="grid__col--2 grid__col--pad-left copy">
			<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('ACC_NUMBER')" >ACC #</a></p>
		</div>
		<div class="grid__col--2 grid__col--pad-left copy">
			<p><a href="#" class="button--secondary block modal-trigger" style="width: 100px;" data-modal="modal-private-name-blacklist" onclick="showBlackListNames('PHONE_NUMBER')" >TEL #</a></p>
		</div>
		
		
		<div id="modal-private-name-blacklist" class="modal--hidden modal--large topDecrease" style="display : none">

								<h2 id ="popupHeading" ></h2>
							
								<div class="grid">
							
									<div class="grid__row">
							
										<div class="grid__col--8 ">
							
											<div id="successDiv" class="message--positive" style="display: none;">
								<div class="copy">
									<p>Data Saved Successfully</p>
								</div>
							</div>
											<div id="errorDiv" class="message--negative" style="display: none;">
								<div class="copy">
									<p id = "errorDescription"></p>
								</div>
							</div>
							
							<div id="successDeleteDiv" class="message--positive" style="display: none;">
								<div class="copy">
									<p>Data Deleted Successfully</p>
								</div>
							</div>
									<div id="errorDeleteDiv" class="message--negative" style="display: none;">
								<div class="copy">
									<p>Error while deleting data</p>
								</div>
							</div>
							
							<div id="emptyErrorDiv" class="message--negative" style="display: none;">
								<div class="copy">
									<p>Please enter something and then proceed</p>
								</div>
							</div>
							
							
							<div class="scrollpane--blacklists">
			
								<table class="micro" id = "blackListNameTable" >
			
									<thead>
										<tr>
											<th class="small-cell"></th>
											<th class="tight-cell">Added</th>
											<th>Value</th>
											<th>Notes</th>
										</tr>
									</thead>
									<tbody id ='blackListNameTableBody' >
									</tbody>
									<!-- <tbody>
																	<tr>
											<td class="small-cell"><a href="#" class="material-icons">close</a></td>
											<td class="tight-cell">12/12/2017 12:12:12</td>
											<td>Lorem ipsum</td>
										</tr>
																	<tr>
											<td class="small-cell"><a href="#" class="material-icons">close</a></td>
											<td class="tight-cell">12/12/2017 12:12:12</td>
											<td>Dolor sit</td>
										</tr>
																	
									</tbody> -->
			
								</table>
			
							</div>
			
						</div>

			<div class="grid__col--4 grid__col--pad-left">

				<div class="pagepanel">

					<h3 class="pagepanel__title">Actions</h3>

					<div class="splitpanel">

						<section class="splitpanel__section">

							<div class="form__field-wrap">

								<label id="txt-private-blacklist-name-add-label-id" for="txt-private-blacklist-name-add" class="label">Add name</label>
								<input type="text" id="txt-private-blacklist-name-add" maxlength="1024">
								<!-- <textarea id="txt-private-blacklist-name-add" maxlength="1024"></textarea> -->
							</div>

							<input id= "add_button" type="button" class="button--primary space-before space-next" onclick = "addBlackListData()" value="Add">
							<!-- <img src="/img/ajax-loader.svg" alt="Loading..." width="16" height="16"> -->

						</section>

						<section class="splitpanel__section">

							<div class="form__field-wrap">

								<label for="txt-private-blacklist-name-search" class="label">Search blacklist</label>
								<input type="text" id="txt-private-blacklist-name-search" maxlength="1024">

							</div>

							<input type="button" class="button--secondary space-before space-next" onclick = "searchBlackListData()" value="Search">
							<!-- <img src="/img/ajax-loader.svg" alt="Loading..." width="16" height="16"> -->

						</section>
						
						

					</div>

				</div>

			</div>

		</div>

	</div>

	<a href="#" class="modal__close-x" onclick = "closePopUp()"><i class="material-icons">close</i></a>

</div>


								</div>
								
								</div>
								
								</div>

							</div>

						</div>
					</section>
					
					<div id="addBlacklist" class="modal--hidden modal--medium " style="display : none; top: 2px !important;">

								<h2 id ="popupHeadingAddBlacklist" style="margin-bottom: 13px !important"></h2>
							
								<div class="grid">
							
									<div class="grid__row">
							
										<div class="grid__col--11 ">
							
											<div id="allBlacklistSuccessDiv" class="message--positive" style="display: none; height: 48px;">
												<div class="copy">
													<p>Data Saved Successfully</p>
												</div>
											</div>
											<div id="allBlacklistErrorDiv" class="message--negative" style="display: none; height: 48px;">
												<div class="copy">
													<p id = "allBlacklistErrorDescription"></p>
												</div>
											</div>
							
											<div id="allBlacklistEmptyErrorDiv" class="message--negative" style="display: none; height: 48px;">
												<div class="copy">
													<p>Please enter something and then proceed</p>
												</div>
											</div>
							
							
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-name-add-label-id" for="txt-blacklist-name-add" class="label" style="margin-bottom: 3px !important;">Add name</label>
												<input type="text" id="txt-blacklist-name-add" maxlength="1024" placeholder="Firstname Lastname">
											</div>
										
										
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-email-add-label-id" for="txt-blacklist-email-add" class="label" style="margin-bottom: 3px !important;">Add email</label>
												<input type="text" id="txt-blacklist-email-add" maxlength="1024" placeholder="name@example.com">
											</div>
										
										
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-domain-add-label-id" for="txt-blacklist-domain-add" class="label" style="margin-bottom: 3px !important;">Add domain</label>
												<input type="text" id="txt-blacklist-domain-add" maxlength="1024" placeholder="example.com">
											</div>
										
										
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-ip-add-label-id" for="txt-blacklist-ip-add" class="label" style="margin-bottom: 3px !important;">Add IP</label>
												<input type="text" id="txt-blacklist-ip-add" maxlength="1024" placeholder="67.67.67.67">
											</div>
										
										
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-accNum-add-label-id" for="txt-blacklist-accNum-add" class="label" style="margin-bottom: 3px !important;">Add Account Number</label>
												<input type="text" id="txt-blacklist-accNum-add" maxlength="1024" placeholder="674587348597">
											</div>
										
										
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-tel-add-label-id" for="txt-blacklist-tel-add" class="label" style="margin-bottom: 3px !important;">Add Telephone</label>
												<input type="text" id="txt-blacklist-tel-add" maxlength="1024" placeholder="+449999999999">
											</div>
											
											<div class="form__field-wrap" style="height: 62px;">
												<label id="txt-blacklist-notes-add-label-id" for="txt-blacklist-notes-add" class="label" style="margin-bottom: 3px !important;">Add Note</label>
												<input type="text" id="txt-blacklist-notes-add" maxlength="1024" placeholder="Your note here...">
											</div>
																													
						</div>
				<p style="text-align: center;"><a href="#" class="button--primary space-next" onclick="addMultipleBlacklistData()">SAVE TO BLACKLIST</a>
				<object id="gifloaderforblacklistaddition" class="ajax-loader-lock-toggle" height="50" width="50" data="resources/img/ajax-loader.svg" preserveAspectRatio="xMidYMid slice" viewBox="0 0 100 100" type="image/svg+xml" style="visibility:hidden;">
				</object> 
				
				</p>
		</div>

	</div>

	<a href="#" class="modal__close-x" onclick = "closePopUp()"><i class="material-icons">close</i></a>

</div>

<div id="repeatCheck" class="modal--hidden " style="width:1100px;">
	
	<div id = "repeatCheckComplete" class="administrationMessageDiv message--positive">
		<div class="copy">
			<p>Repeat check completed</p>
		</div>
	</div>
	<div id = "repeatForceCheckComplete" class="administrationMessageDiv message--positive">
		<div class="copy">
			<p>Record cleared without check completed</p>
		</div>
	</div>
	<div id = "dateErrorDiv" class="administrationMessageDiv message--negative">
		<div class="copy">
			<p>Please select date and then proceed</p>
		</div>
	</div>
	<div id = "dateComparisonErrorDiv" class="administrationMessageDiv message--negative">
		<div class="copy">
			<p>Please select 'To Date' proper and proceed</p>
		</div>
	</div>
	<div id = "repeatCheckError" class="administrationMessageDiv message--negative">
		<div class="copy">
			<p>Something went wrong please try after sometime</p>
		</div>
	</div>
    
    <div class="annex--rc--visible" style="width:1030px;">
<section>
    <li class="form__field-rcwrap grid__col--6">

		<p class="label filterInputHeader">Module</p>
	<select id="SelectedModule" style="border-color: #ccc;">
    <option>Registration</option>
    <option>Payment In</option>
    <option>Payment Out</option>
</select>

	</li>
	<li class="form__field-wrap grid__col--6" style="width: 500px; padding-bottom: 24px; padding-top: 10px; height: 90px; display: inline-table; ">

		<div class="grid">
			<div class="grid__row">
				<div class="grid__col--6 filterInputWrap">
					<label for="date-registered-from" class="filterInputHeader" style="height:25px;">Date from</label>
					<input type="text" name="time" id="timePicker1" placeholder="mm/dd/yyyy">
				</div>
				<div class="grid__col--6 filterInputWrap">
					<label for="date-registered-to" class="filterInputHeader" style="height:25px;">Date to</label>
					<input type="text" name="time" id="timePicker2" placeholder="mm/dd/yyyy">
				</div>
			</div>
		</div>

	</li>
	 
				
				<div class="grid__col--12" style="text-align: center; width: 900px">
							<input type="button" class="button--secondary space-before space-next block modal-trigger" id ="fraugster-count-search" data-modal="repeatCheck" value = "Search" onclick="SearchServiceFailed()">							
					</div>
				</section>
    </div>
    <br>
  <h2 id="blacklist" class="h2--repeatcheck"></h2> 
   <h2 id="fraugster" class="h2--repeatcheck"> </h2> 
    <h2 id="sanction" class="h2--repeatcheck"> </h2> 
      <h2 id="customCheck" class="h2--repeatcheck"> </h2> 
       <h2 id="eid" class="h2--repeatcheck"> </h2>
       
       <div  id = "barDIV" style="display : none">
       	<div id = "progressbar-5"></div>
       	<div id = "progress-label"></div>
       	<p id = "check-display" >Repeat Check done <span id = "done-count"> 0 </span> of <span id = "total-count"> 0 </span></p>
       	
       	<div class="pad-top txt-alignC" id="repeatcheck-count"></div> <!-- AT-4289 -->
       </div><br>
       
		<div class="grid__col--12" style="text-align: center;">
							<input type="button" class="button--primary space-before space-next block modal-trigger button--disabled" id="refresh-count" value= "Show Status" title="Show pass, fail and exception record count" onclick="showCount()" disabled = "disabled">
							<input type="button" class="button--primary space-before space-next block modal-trigger button--disabled" id="fraudpredict-repeat" value= "Perform Repeat Check" onclick="getServiceFailureData()" disabled = "disabled">
					
							<input type="button"  class="button--primary space-before space-next block modal-trigger button--disabled" id= "force-clear-data" value="Without check clear record" onclick="forceClearData()" disabled = "disabled">
					</div>
								<a href="#" class="modal__close-x" onclick = "closePopUp()"><i class="material-icons">close</i></a>

</div>


<!-- Add For AT-4773  -->
 <div id="syncRegCheck" class="modal--hidden " style="margin-left: 131px; display: block; width: 845px;padding-top: 0px; padding-left: 25px; height: 280px;"  >


    <div class="annex--rc--visible" style="width:760px; height: 188px; align-items: center; padding-left: 34px; padding-top: 36px; margin-left: 14px; margin-top: 29px; padding-right: 49px;">
<input type="text" id="textAreaFields" placeholder="Please enter the comma seprated trade account number" style=" height: 100px; border-radius: 1px; border-color: blue;     text-align: center; margin-left: 7px;
    margin-top: 2px;" >

	
	
	<div class="grid__col--12" style="text-align: center; width: 900px">
							<input type="button" class="button--secondary space-before space-next block modal-trigger" id ="fraugster-count-search" data-modal="syncRegCheck" value = "Process" onclick="regSyncWithIntuition()" style="margin-right: 22%;    margin-top: 60px;">							
					</div>							
							
    </div>	
					
<a href="#" class="modal__close-x" onclick = "closePopUp()"><i class="material-icons">close</i></a>
</div>

				</div>
			</form>
			
			
			

		</div>
		<div class="grid__col--4"></div>

	</div>

</div>
<br>

	<!--        WHITE LIST BENEFICIARY START         -->
			
			<div class="grid">

				<div class="grid__row">

					<div class="grid__col--8">

						<form action="#">
							<div class="splitpanel space-after" style="width: 1090px;">
								<section class="splitpanel__section">
									<!-- <div class="grid__col--3" style="text-align: center;">
																	
									</div>
									<div class="grid__col--4" style="text-align: center;">
																	
									</div> -->
									<div class="grid__col" style="text-align: center;">
										<a href = "#" id="Add_to_blacklist" class="button--primary space-before space-next block modal-trigger" data-modal="addBlacklist" onclick="addingBlackListDataPopup()">ADD TO BLACKLIST</a>
										<a href = "#" class="button--primary space-before space-next block modal-trigger"  data-modal="repeatCheck" onclick="RepeatCheckPopup()">Repeat Check</a>
										<a href = "#" class="button--primary space-before space-next block modal-trigger" data-modal="modal-private-name-whitelist" onclick="showWhitelistBeneficiaryNames()">ADD TO WHITELIST BENEFICIARY</a>
										<!-- Add For AT-4185 -update TMMQ retry count -->
										<a href = "#" class="button--primary space-before space-next block" data-modal="intuitionRepeatCheck" onclick="updateTMMQRetryCount()">Intuition Repeat Check</a> 
										<!-- Add For AT-4773  -->
										<a href = "#" class="button--primary space-before space-next block modal-trigger" data-modal="syncRegCheck"  onclick="regSyncIntuitionPopUp()" >Sync Reg with Intuition</a>
										<!-- Add For AT-5023 -->
										<a href = "#" class="button--primary space-before space-next block" data-modal="postCardRepeatCheck" onclick="updatePCTMQRetryCount()">Post Card Repeat Check</a>
									</div>
								</section>
							</div>
							<div id="modal-private-name-whitelist"
								class="modal--hidden modal--large topDecrease"
								style="display: none">

								<h2 id="popupHeading">Whitelist Beneficiary</h2>

								<div class="grid">

									<div class="grid__row">

										<div class="grid__col--8 ">

											<div id="whitelistSuccessDiv" class="message--positive"
												style="display: none;">
												<div class="copy">
													<p>Data Saved Successfully</p>
												</div>
											</div>
											<div id="whitelistErrorDiv" class="message--negative"
												style="display: none;">
												<div class="copy">
													<p id="whitelistErrorDescription"></p>
												</div>
											</div>

											<div id="whitelistSuccessDeleteDiv" class="message--positive"
												style="display: none;">
												<div class="copy">
													<p>Data Deleted Successfully</p>
												</div>
											</div>
											<div id="whitelistErrorDeleteDiv" class="message--negative"
												style="display: none;">
												<div class="copy">
													<p>Error while deleting data</p>
												</div>
											</div>

											<div id="whitelistEmptyErrorDiv" class="message--negative"
												style="display: none;">
												<div class="copy">
													<p>Please enter something and then proceed</p>
												</div>
											</div>


											<div class="scrollpane--whitelists">

												<table class="micro" id="whiteListBeneficiaryNameTable">

													<thead>
														<tr>
															<th class="small-cell"></th>
															<th class="tight-cell">Added</th>
															<th>Beneficiary Name</th>
															<th>Beneficiary Account Number</th>
														</tr>
													</thead>
													<tbody id='whiteListBeneficiaryNameTableBody'>
													</tbody>
												</table>

											</div>

										</div>

										<div class="grid__col--4 grid__col--pad-left">

											<div class="pagepanel">

												<h3 class="pagepanel__title">Actions</h3>

												<div class="splitpanel">

													<section class="splitpanel__section">

														<div class="form__field-wrap">

															<label id="txt-private-whitelistbeneficiary-name-add-label-id"
																for="txt-private-whitelistbeneficiary-name-add" class="label">Add Beneficiary Name</label> 
																<input type="text" id="txt-private-whitelistbeneficiary-name-add" maxlength="1024">
															
														</div>
														
														<div class="form__field-wrap">
																
															<label id="txt-private-whitelistbeneficiary-actNum-add-label-id"
																for="txt-private-whitelistbeneficiary-actNum-add" class="label">Add Beneficiary
																Account Number</label> <input type="text"
																id="txt-private-whitelistbeneficiary-actNum-add" maxlength="1024">
														</div>

														<input id="Whitelist_add_button" type="button"
															class="button--primary space-before space-next"
															onclick="addWhitelistBeneficiaryData()" value="Add">
													</section>

													<section class="splitpanel__section">

														<div class="form__field-wrap">

															<label for="txt-private-WhitelistBeneficiary-name-search"
																class="label">Search Whitelisted Beneficiary</label> <input
																type="text" id="txt-private-WhitelistBeneficiary-name-search"
																maxlength="1024">

														</div>

														<input type="button"
															class="button--secondary space-before space-next"
															onclick="searchWhiteListBeneficiaryData()" value="Search">

													</section>

												</div>

											</div>

										</div>

									</div>

								</div>

								<a href="#" class="modal__close-x" onclick="closePopUpWhitelistBeneficiary()"><i
									class="material-icons">close</i></a>

							</div>


						</form>


					</div>

					<div class="grid__col--4"></div>

				</div>

			</div>

	<!--             WHITE LIST BENEFICIARY END              -->
	

		</div>

	</div>

</main>
			<input type="hidden" id="addBlackListType" value=''/>
			<input type="hidden" id="searchBlackListType" value=''/>

	<div id="modal-global-just-a-moment" class="modal--hidden modal--small center">

	<h2>Just a moment...</h2>

	<!-- <img class="ajax-loader space-after space-before" src="/img/ajax-loader.svg" width="50" height="50" alt="Please wait..."> -->

</div>	<div id="drawer-user" class="drawer--closed">

	<h2 class="drawer__heading">Your profile<span class="drawer__close"><i class="material-icons">close</i></span></h2>

	<ul class="split-list">
		<li>
			<i class="material-icons">settings</i> <a href="page-profile-administration.html">Administration</a>
		</li>
	</ul>

</div>
	<script type="text/javascript" src="resources/js/jquery_min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/js/administrtion.js"></script>
	<script type="text/javascript" src="resources/js/commonDetails.js"></script>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/pages/cd.profile.js"></script>
	<script type="text/javascript" src="resources/js/pages/timepicker.js"></script>
	
	
	</body>

</html>
