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
					  <li class="active"><a href="#option1" data-toggle="tab">Option1</a></li>
					  <li><a href="#option2" data-toggle="tab">Option2</a></li>
					  <li><a href="#option3" data-toggle="tab">Option3</a></li>
					</ul>
					<div class="tab-content">
						<div id="option1" class="input-group tab-pane active" style="padding-top:5px;">
						  <input type="checkbox" name="my-checkbox" checked>
						  option1
						</div>
							
						<div id="option2" class="input-group tab-pane" style="padding-top:5px;">
						  <input type="checkbox" name="my-checkbox" checked>
						  option2
						</div>
							
						<div id="option3" class="input-group tab-pane" style="padding-top:5px;">
						  <input type="checkbox" name="my-checkbox" checked>
						  option3					  
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