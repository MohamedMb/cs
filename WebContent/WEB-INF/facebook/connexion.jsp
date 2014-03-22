<!DOCTYPE html>
<html>
	<!-- Header -->
    <%@ include file="header.jsp" %>
	<body>
		<nav class="navbar navbar-inner navbar-static-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a href="./" class="navbar-brand" style="padding-left: 102px;">
					<img class="img-rounded" src="bootstrap/img/facebook_w.png" alt="logo" style="width: 32px; height: 32px;">
				</a>
			</div>
	
			<!-- Collect the nav links, forms, and other content for toggling -->
			<ul class="nav navbar-nav navbar-left"
				style="padding-top: 5px; margin-left: 168px;">
	
				<!-- TEST SUR VARIABL DE SESSION -->
	
				<!--<li>
				<form class="navbar-form" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Recherche" style="width:500px;">
					</div>
					<button type="submit" class="btn btn-default">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</form>
			</li>-->
			</ul>
			<ul class="nav navbar-nav navbar-right"
				style="padding-top: 8.5px; padding-right: 158px;">
				<li style="margin-right: 10px; padding-top: 6px;">
					<form class="form-inline" action="/cs/connexion" method="post">
						<div class="form-group">
							<input name="mail" id="mail" type="text" class="form-control" placeholder="Login"/>
						</div>
						<div class="form-group">
							<input name="password" id="password" type="password" class="form-control" placeholder="Mot de passe"/>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-default">OK</button>
						</div>
					</form>
				</li>
				<!--<li>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Mot de passe">
				</div>
			</li>	-->
	
			</ul>
		</nav>
	
	
	
		<div class="row-fluid">
			<div class="col-md-6 col-md-offset-1">
				<img class="img img-rounded" src="bootstrap/img/reseau-social.jpeg"
					alt="schema_reseau_social" style="margin-top: 150px;">
			</div>
			<div class="col-md-4">
				<form id="formInscription" role="form" method="post" action="connexion">
					<div class="form-group">
						<label for="exampleInputEmail1">Nom</label>
						<input name="txtNomInscription" type="text" class="form-control" placeholder="Nom">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Prénom</label>
						<input name="txtPrenomInscription" type="text" class="form-control" id="exampleInputEmail1" placeholder="Prenom">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Email</label>
						<input name="txtEmailInscription" type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label>
						<input name="txtPasswordInscription" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
					</div>
					<div class="checkbox">
						<input name="chkEnvoiMailInscription" type="checkbox"> M'envoyer par mail mes informations !
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
	
			</div>
		</div>
		<script>
			$(document).on('click', 'ta_comment > button.close', function() {
				console.log('TEST');
			});
			
			$(document).ready(function(){
				
				//--- validation du formulaire d'inscription ---
				$('#formInscription').on('submit', function(e) {
					e.preventDefault();
					var isFormValid = true;
					var tabDataPost = [];
					$('#formInscription input').each(function() {
						if ($.trim($(this).val()).length == 0)
							isFormValid = false;
						tabDataPost.push(($(this).attr('name') + ': ' + $(this).val()));
					});
					
					if (isFormValid) {
						$.ajax({
							url: $(this).attr('action'),
							type: 'POST',
							data: {dataInscription: tabDataPost}
						});
					}
				});
				
				$("#upload_link").on('click', function(e){
					e.preventDefault();
					$("#upload:hidden").trigger('click');
				});
				
				$('.btn_comment').on('click', function() {
					if (!$(this).parents('.media-body').hasClass('comment_active')) {
						$(this).css('display', 'none');
						$(this).parents('.media-body').addClass('comment_active');
						$(this).parents('.media-body').append('<div class="ta_comment" style="width: 100%;">'+
																	'<button type="button" class="close pull-right" aria-hidden="true">&times;</button>'+
																   '<textarea class="form-control" rows="1" placeholder="Commente !" cols="500"></textarea>'+															   
															    '</div>');
					}
				});
				
				//$('.ta_comment').children('.close').on('click', function() {
				$('button.close').on('click', function() {
					console.log('TEST');
				});
				
				$('[data-toggle=popover]').on('click', function() {
					console.log('test');
				});
			});
					
		</script>
	</body>
</html>