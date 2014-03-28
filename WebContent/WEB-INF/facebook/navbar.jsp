<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
	  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		<span class="sr-only">Toggle navigation</span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	  </button>
	  <a href="index" class="navbar-brand" style="padding-left:102px;"><img class="img-rounded" src="bootstrap/img/facebook_w.png" alt="logo" style="width:32px; height:32px;"></a>		  
	</div>

	<!-- Collect the nav links, forms, and other content for toggling -->
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav navbar-left list-inline" style="margin-top:8px; margin-left:25px;">
            <li id="amis"><a id="popoverAmi" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-user" style="color:white;"></span></a></li>
			<li id="message"><a id="popoverMessage" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-send" style="color:white;"></span></a></li>
			<li id="notification"><a id="popoverNotification" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-globe" style="color:white;"></span></a></li>
		</ul>
		<form class="navbar-form navbar-left" role="search" style="width: 40%;">
			<div class="form-group" style="width: 80%;">
				<input type="text" class="form-control" placeholder="Recherche">
			</div>
			<button type="submit" class="btn btn-default">
				<span class="glyphicon glyphicon-search"></span>
			</button>
		</form>
		<ul class="nav navbar-nav navbar-right" style="padding-top:8.5px;">
			<li style="padding:-3px;">
				<a href="./profil" style="padding-top:-5px;"><img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="32x32" style="width:46px; height:46px;"></a>
<!-- 					<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="32x32" style="width:46px; height:46px;"> -->
			</li>
			<li style="color:white; padding:0px; font-weight:bold;">
				<a href="./profil" style="padding-top:-5px; color:white;">${currentUser.prenom} ${currentUser.nom}</a>
			</li>					
			<li style="padding:5px; padding-right:20px;">
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
						<span class="glyphicon glyphicon-cog"></span>
							Option 
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">Créer un groupe</a></li>
						<li><a href="#">Créer une page</a></li>
						<li><a href="./option">Paramètres</a></li>
						<li class="divider"></li>
						<li><a href="deconnexion">Déconnexion</a></li>
					</ul>
				</div>
			</li>
		</ul>  
	</div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>