<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

		<link rel="stylesheet" type="text/css" href="resources/css/pages/cd.page.compliance.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/cd.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/pages/administration.css"/>

</head>
<body>
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<tiles:insertAttribute name="menu" />
	</div>
	<div>
		<tiles:insertAttribute name="body" />
	</div>
	<div id="configuration">
		<input id="complianceServiceUrl" type="hidden" value="${applicationScope['complianceServiceUrl']}"/>
		<input id="enterpriseUrl" type="hidden" value="${applicationScope['enterpriseUrl']}"/>
		<input id="pageSize" type="hidden" value="${applicationScope['pageSize']}"/>
		<input id="viewMoreRecords" type="hidden" value="${applicationScope['viewMoreRecords']}"/>
		<input id="pastDateSize" type="hidden" value="${applicationScope['pastDateSize']}"/>
		<input  id="userInfo" type="hidden" value='${userJson }'>
	</div>
	<%-- <div style="clear:both"><tiles:insertAttribute name="footer" /></div>   --%>

</body>
</html>