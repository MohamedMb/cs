<!DOCTYPE html>
<html>
	<!-- Header -->
    <%@ include file="header.jsp" %>
	<body>
		<nav class="navbar navbar-inner navbar-static-top" role="navigation">
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
	
			<ul class="nav navbar-nav navbar-left"
				style="padding-top: 5px; margin-left: 168px;">
			</ul>
			<ul class="nav navbar-nav navbar-right"
				style="padding-top: 8.5px; padding-right: 158px;">
				<li style="margin-right: 10px; padding-top: 6px;">
					<form class="form-inline" action="/cs/connexion" method="get">
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
						<input id="txtNomInscription" name="txtNomInscription" type="text" class="form-control" placeholder="Nom">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Prénom</label>
						<input id="txtPrenomInscription" name="txtPrenomInscription" type="text" class="form-control" placeholder="Prenom">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Email</label>
						<input id="txtEmailInscription" name="txtEmailInscription" type="email" class="form-control" placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label>
						<input id="txtPasswordInscription" name="txtPasswordInscription" type="password" class="form-control" placeholder="Password">
					</div>
					<div class="checkbox">
						<input id="chkEnvoiMailInscription" name="chkEnvoiMailInscription" type="checkbox"> M'envoyer par mail mes informations !
					</div>
					<button type="submit" class="btn btn-default">Valider</button>
				</form>
			</div>
		</div>
		<div id="modalInscription" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
			    	<div class="modal-header">
			      		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			      		<h4 class="modal-title"></h4>
			    	</div>
			    	<div class="modal-body">
			    		
			    	</div>
			    	<div class="modal-footer">
			      		<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
			    	</div>
			  	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
	</body>
	<script>
		$(document).on('click', 'ta_comment > button.close', function() {
			console.log('TEST');
		});
		
		$(document).ready(function(){
			
			//--- validation du formulaire d'inscription ---
			$('#formInscription').on('submit', function(e) {
				e.preventDefault();
				var isFormValid = true;
				$('#formInscription input').each(function() {
					if ($.trim($(this).val()).length == 0)
						isFormValid = false;
				});
				
				if (isFormValid) {
					$.ajax({
						url: $(this).attr('action'),
						type: 'POST',
						data: {dataInscription: {
													txtNomInscription: $('#txtNomInscription').val(),
													txtPrenomInscription: $('#txtPrenomInscription').val(),
													txtEmailInscription: $('#txtEmailInscription').val(),
													txtPasswordInscription: $('#txtPasswordInscription').val(),
													chkEnvoiMailInscription: $('#chkEnvoiMailInscription').is(':checked')
												}
							  },
						success: function(result) {
							if (result) {
								$('#modalInscription .modal-title').text('Inscription réussie');
								$('#modalInscription .modal-body').html('<p>Votre inscription s\'est déroulée correctement.</p>');
							}else{
								$('#modalInscription .modal-title').text('Erreur lors de l\inscription');
								$('#modalInscription .modal-body').text('<p>Une erreur s\'est produite durant votre inscription.</p>');
							}
							$('#modalInscription').modal();
						}
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
</html>