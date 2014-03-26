<!DOCTYPE html>
<%@page import="fr.miage.facebook.utilisateur.Statut"%>
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
		<%@ include file="navbar.jsp" %>
		
		<div class="row-fluid">
			<!-- Menu vertical -->
			<%@ include file="menu_vertical.jsp" %>
			
			<div class="col-md-8">
				<div class="panel panel-default">
					<div class="panel-body" >
						<div id="home" class="tab-pane">
							<ul class="nav nav-pills">
								<li class="active"><a href="#statut" data-toggle="pill">Statut</a></li>
								<li><a href="#photo" data-toggle="pill">Photo</a></li>
								<li><a href="#question" data-toggle="pill">Question</a></li>
							</ul>
							<div class="tab-content">
							
								<!-- Formulaire d'ajout de statut -->
								<div id="statut" class="input-group tab-pane active" style="padding-top:5px;">
									<form id="formAjoutStatut" method="post" action="index" novalidate="novalidate">
										<textarea class="form-control textarea" rows="3" placeholder="Partage ton statut !" cols="500"></textarea>
										<span class="txtError">Vous devez remplir le champs de texte.</span>
										<input type="submit" class="btn btn-primary pull-right" />
									</form>
									<hr />
									<div class="media">
	                                    <a class="pull-left" href="#">
	                                    	<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="64x64" style="width: 64px; height: 64px;">
	                                    </a>
	                                    <div class="media-body">
	                                        <h4 class="media-heading">Prenom NOM</h4>
	                                        <p>
	                                        	Cras sit amet nibh libero, in gravida nulla. Nulla vel
	                                            metus scelerisque ante sollicitudin commodo. Cras purus odio,
	                                            vestibulum in vulputate at, tempus viverra turpis. Fusce
	                                            condimentum nunc ac nisi vulputate fringilla. Donec lacinia
	                                            congue felis in faucibus.
	                                        </p>
	                                        <a class="btn btn-link" style="color: green;">J'aime</a>
	                                        <a class="btn btn-link" style="color: red;">J'aime pas</a>
	                                        <a class="btn btn-link btn_comment">Commenter</a>
	                                    </div>
	                                </div>
								</div>
								
								<!-- Formulaire d'upload de photos -->
								<div id="photo" class="input-group tab-pane" style="padding-top:5px;">
								  	<div class="panel-body">
										<div class="row-fluid">
											<div class="col-md-6">
												<div id="importPhoto" class="well">
													<span class="btn btn-success fileinput-button">
														<input id="fileupload" type="file" name="photo" data-url="index" />	
														<i class="glyphicon glyphicon-plus"></i>
														<span>Importer une photo</span>
													</span>
 													<div class="progress hide">
 														<div class="progress-bar progress-bar-success"></div>
 													</div>
<!-- 													<a id="upload_link" class="btn btn-link" style="text-decoration:none;"> -->
<!-- 														<p class="text-center" style="font-weight:bold;">Importer une photo</p> -->
<!-- 													</a> -->
												</div>
											</div>
									  		<div class="col-md-6">
									  			<div class="well">
									  				<a href="../">
									  					<span class="text-center" style="font-weight:bold; padding-top:12px;">Charger depuis un album</span>
									  				</a>
									  			</div>
									  		</div>
										</div>
								  	</div>
								</div>
								
								<!-- Formulaire d'ajout de question -->
								<div id="question" class="input-group tab-pane" style="padding-top:5px;">
								  	<input type="text" class="form-control" placeholder="Pose ta question !">						  
								</div>
							</div>
							<div class="divider"></div>
							<hr />
							<%
								if (currentUser.getStatuts() != null) {
									for (Statut statut : currentUser.getStatuts()) {
							%>
										<div class="media">
											<a class="pull-left" href="#">
												<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="64x64" style="width:64px; height:64px;">
											</a>
											<div class="media-body">
												<h4 class="media-heading">${currentUser.prenom} ${currentUser.nom}</h4>
												<p>
													${statut.libelle}
												</p>
												<a class="btn btn-link" style="color:green;">J'aime</a>
												<a class="btn btn-link" style="color:red;">J'aime pas</a>
												<a class="btn btn-link btn_comment">Commenter</a>
											</div>
										</div>
										<hr/>
							<%
									}
								}
							%>
						</div>	
						<div id="application" class="tab-pane"></div>
					</div>
				</div>
			</div>	  
		</div>
	</body>
	<script>	
		$(document).ready(function() {
			
			//--- upload de photos ---
			$(function () {
			    $('#fileupload').fileupload({
			        dataType: 'json',
			        singleFileUploads: true,
			        progressall: function (e, data) {
			            var progress = parseInt(data.loaded / data.total * 100, 10);
			            $('#progress .bar').css(
			                'width',
			                progress + '%'
			            );
			        },
			        done: function (e, data) {
				        if (data.result != null) {
			           	 	$.each(data.result.files, function (index, file) {
			                	$('<p/>').text(file.name).appendTo(document.body);
			            	});
				        }
			        }
			    });
			});
			
			//--- validation du formulaire d'ajout d'un statut ---
			$('#formAjoutStatut').on('submit', function(e) {
				e.preventDefault();
				// test si la textarea n'est pas vide
				if ($(this).find('textarea').val() != '') {
					$(this).find('span.txtError').hide();
					// envoi de la requête AJAX
					$.ajax({
						type: $(this).attr('method'),
						url: $(this).attr('action'),
						data: {
							statut: $(this).find('textarea').val()
						}
					});
				}else{
					$(this).find('span.txtError').show();
				}
			});
			
			
			//--- fermeture d'un commentaire ---
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
				
				$('#formAjoutStatut textarea').one('focus',function()
				{
					$(this).wysihtml5({
						stylesheets: [],
						color: true,
						locale: "fr-FR"
					});
				});
				
				/*$('#formAjoutStatut textarea').on('blur', function() {
					if ($('.wysihtml5-toolbar').exists()) {
						$('.wysihtml5-toolbar').remove();
					}
				});*/
				
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
		});
	</script>
</html>