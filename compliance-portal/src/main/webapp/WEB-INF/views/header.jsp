

<!DOCTYPE html>

<html lang="en">

	<head>
		<meta charset="utf-8"/>
		<title>Enterprise Compliance Portal</title>
		<meta name="description" content="Enterprise tools"/>
		<meta name="copyright" content="Currencies Direct"/>
<!-- 		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<link href='https://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>
		<link href="https://fonts.googleapis.com/css?family=Roboto+Slab:300" rel="stylesheet">
		<link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>

<link rel="stylesheet" type="text/css" href="resources/css/cd.css"/>
<link rel="stylesheet" type="text/css" href="resources/css/pages/cd.page.compliance.css"/> -->
			</head>

	<body>


<div id="master-grid" class="grid">

	<!-- start: top bar -->
<div class="grid__row">

	<div class="grid__col--12">

		<nav id="top-bar">

			<span class="burger--maximise"><span></span></span>
			<p id="top-bar__title"><a href="/compliance-portal">Atlas</a></p>

			<ul class="top-bar__utils">

				
				<li class="top-bar__util drawer-trigger" data-ot="Your profile" data-drawer="drawer-user">
					<a href="#" class="space-next"><i class="material-icons">person</i>Hi, ${user.preferredUserName} </a>
				</li>

				<li class="top-bar__util" data-ot="Logout">
					<a href="/compliance-portal/logout?GLO=true" ><i class="material-icons">power_settings_new</i></a>
				</li>

			</ul>

		</nav>

	</div>

</div>
<!-- end: top bar -->
<!--Administration div changes start -->
<div id="drawer-user" class="drawer--closed">

	<h2 class="drawer__heading">Your profile<span class="drawer__close"><i class="material-icons">close</i></span></h2>

	<ul class="split-list">
					<li>
						<i class="material-icons">settings</i> <a href="/compliance-portal/administration">Administration</a>
					</li>
			</ul>


</div>
<!--Administration div changes end -->
	
</div>

	
	</body>

</html>
