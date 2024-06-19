<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>
		<link href="https://fonts.googleapis.com/css?family=Roboto+Slab:300" rel="stylesheet">
		<link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>

<link rel="stylesheet" type="text/css" href="resources/css/cd.css"/>
<link rel="stylesheet" type="text/css" href="resources/css/pages/cd.page.compliance.css"/>


</head>
<body>
	<%-- <div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<tiles:insertAttribute name="menu" />
	</div> --%>
	<div>
		<tiles:insertAttribute name="body" />
	</div>
	<%-- <div style="clear:both"><tiles:insertAttribute name="footer" /></div>   --%>

</body>
</html>
