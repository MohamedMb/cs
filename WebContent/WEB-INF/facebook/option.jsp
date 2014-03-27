<!DOCTYPE html>
<%@page import="fr.miage.facebook.utilisateur.UtilisateurService"%>
<html>
	<!-- Header -->
    <%@ include file="header.jsp" %>
  	<body>
		<jsp:useBean id="currentUser" class="fr.miage.facebook.utilisateur.Utilisateur" scope="session"/>
		<%
			currentUser = (fr.miage.facebook.utilisateur.Utilisateur)request.getAttribute(UtilisateurService.currentUser);
		%>
		<!-- Navbar -->
		<%@ include file="navbar.jsp"%>
		
		<!-- Contenu -->
		<div class="row-fluid">
			<!--<div class="col-md-3 col-md-offset-1">
				<div class="list-group">
					<a href="#option1" class="list-group-item active">
						Cras justo odio
					</a>
					<a href="#option2" class="list-group-item">Dapibus ac facilisis in</a>
					<a href="#option3" class="list-group-item">Morbi leo risus</a>
					<a href="#" class="list-group-item">Porta ac consectetur ac</a>
					<a href="#" class="list-group-item">Vestibulum at eros</a>
				</div>
			</div>
			
			<div class="col-md-7">
				<div class="panel panel-default">
				  <div class="panel-body option1">
					<input type="checkbox" name="my-checkbox" checked>
				  </div>
				  <div class="panel-body option2">
					<input type="checkbox" name="my-checkbox" checked>
				  </div>
				  <div class="panel-body option3">
					<input type="checkbox" name="my-checkbox" checked>
				  </div>
				</div>
			</div>-->
			<div class="col-md-11 col-md-offset-1">
				<div class="tab-pane">
					<ul class="nav nav-tabs ">
					  <li class="active"><a href="#option1" data-toggle="tab"><span class="glyphicon glyphicon-eye-open"></span> Visibilité</a></li>
					  <li><a href="#option2" data-toggle="tab"><span class="glyphicon glyphicon-bullhorn"></span> Notifications</a></li>
					  <li><a href="#option3" data-toggle="tab"><span class="glyphicon glyphicon-user"></span> Données personnelles</a></li>
					</ul>
					<div class="tab-content">
						<div id="option1" class="input-group tab-pane active" style="padding-top:5px;">
						  <br />
						  <input type="checkbox" name="my-checkbox" checked> Autoriser les "amis" a voir mes status
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> Autoriser les "amis" a voir mes commentaires
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> Autoriser les "amis" a voir mes photos
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> Autoriser les "amis" a voir mes autres amis
						  <hr />
						</div>
							
						<div id="option2" class="input-group tab-pane" style="padding-top:5px;">
						  <br />
						  <h3>M'envoyer un message lors :</h3>
						  <br />
						  <input type="checkbox" name="my-checkbox" checked> De la publication d'un statut sur mon mur
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> De la publication du statut d'un(e) ami(e)
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> De l'identification sur une photo
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> De l'identification sur un statut
						  <hr />
						  <input type="checkbox" name="my-checkbox" checked> De l'identification sur un commentaire
						  <hr />
						</div>
							
						<div id="option3" class="input-group tab-pane" style="padding-top:5px;">
						  <div class="row-fluid">
						  	<div class="col-md-7">
							  <br />
							  <h3>Afficher mes informations :</h3>
							  <br />
							  <input type="checkbox" name="my-checkbox" checked> A tout le monde
							  <hr />
							  <input type="checkbox" name="my-checkbox" checked> Uniquement mes amis
							  <hr />
							  <input type="checkbox" name="my-checkbox" checked> Aux amis de mes amis
							  <hr />
							  <input type="checkbox" name="my-checkbox" checked> Aux amis du chien de la grande tante par alliance du coté de mon cousin
							  <hr />
							</div>
							<div class="col-md-5">
								<form role="form" action="infosperso" method="post">
									<div class="radio-inline">
									  <label>
									    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
									    Homme
									  </label>
									</div>
									<div class="radio-inline">
									  <label>
									    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
									    Femme
									  </label>
									</div>
									<div class="radio-inline">
									  <label>
									    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1">
									    Autre..
									  </label>
									</div>
								  <div class="form-group">
								    <label for="exampleInputEmail1">Nom</label>
								    <input type="text" class="form-control" id="InputNom" name="InputNom" placeholder="Entrez nom">
								  </div>
								  <div class="form-group">
								    <label for="exampleInputPassword1">Prénom</label>
								    <input type="text" class="form-control" id="InputPrenom" name="InputPrenom" placeholder="Entrez prénom">
								  </div>
								  <div class="form-group">
								    <label for="exampleInputPassword1">Password</label>
								    <input type="password" class="form-control" id="InputPassword" name="InputPassword" placeholder="Password">
								  </div>
								  <div class="form-group">
								    <label for="exampleInputPassword1">Confirmez password</label>
								    <input type="password" class="form-control" id="InputPassword2" name="InputPassword2" placeholder="Password Confirmation">
								  </div>
								  								  
								  <button type="submit" class="btn btn-default">Sauvegarder</button>
								</form>
							</div>
						  </div>					  
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- fin Contenu -->
		
		<script>
			$(document).on('click', 'ta_comment > button.close', function() {
				console.log('TEST');
				
			});
			
			//--- fermeture du textarea des commentaires ---
			$(document).on('click', '.btn_comment_close', function() {
				$(this).parent('div.ta_comment').remove();
				$(this).parent('div.media-body > a.btn.btn-link.btn_comment').css('display', 'block');
			});
			
			$(document).ready(function(){				
				$("#popoverAmi").popover({html:true, title: 'Amis', content: "Aucune demande d'ajout ! <img class='img-rounded' src='bootstrap/img/forever_alone.png' alt='foreverAlone' style='width:32px; height:32px;'>"});
				$("#popoverMessage").popover({html:true, title: 'Messages', content: "Aucun message ! <img class='img-rounded' src='bootstrap/img/forever_alone.png' alt='foreverAlone' style='width:32px; height:32px;'>"});
				$("#popoverNotification").popover({html:true, title: 'Notifications', content: "Aucune notification ! <img class='img-rounded' src='bootstrap/img/forever_alone.png' alt='foreverAlone' style='width:32px; height:32px;'>"});
				
				$('#taStatut').one('focus',function()
				{
					$('#taStatut').wysihtml5({
						color: true,
						locale: "fr-FR"
					});
				});
				
				$('#taStatut').on('blur', function() {
					if ($('.wysihtml5-toolbar').exist()) {
						$('.wysihtml5-toolbar').remove();
					}
				});
				
				//--- clic sur le bouton d'upload de fichier ---
				$("#upload_link").on('click', function(e){
					e.preventDefault();
					$("#upload:hidden").trigger('click');
				});
				
				//--- clic sur le bouton commenter ---
				$('.btn_comment').on('click', function() {
					//if (!$(this).parent('div').hasClass('comment_active')) {
						//$(this).parent('div').addClass('comment_active');
						$(this).css('display', 'none');
						$(this).parents('.media-body').append('<div class="ta_comment" style="width: 100%;">'+
																	'<button type="button" class="close pull-right btn_comment_close" aria-hidden="true">&times;</button>'+
																    '<textarea class="form-control" rows="1" placeholder="Commente !" cols="500"></textarea>'+															   
															    '</div>');
					//}
				});
				
				$('.ta_comment').children('.close').on('click', function() {
					$('button.close').on('click', function() {
						console.log('TEST');
					});
				});
				
				$('[data-toggle=popover]').on('click', function() {
					console.log('test');
				});				
				
			});
			
			$("[name='my-checkbox']").bootstrapSwitch();
					
		</script>
  	</body>
</html>