<!DOCTYPE html>
<%@ page import="fr.miage.facebook.utilisateur.UtilisateurService" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.miage.facebook.utilisateur.Statut" %>
<%@ page import="fr.miage.facebook.utilisateur.Photo" %>
<%@ page import="fr.miage.facebook.utilisateur.Utilisateur" %>
<html>
<!-- Header -->
<%@ include file="header.jsp"%>
<body>
	<!-- MENU HAUT -->
	<jsp:useBean id="currentUser" class="fr.miage.facebook.utilisateur.Utilisateur" scope="session" />
	<%
		currentUser = (fr.miage.facebook.utilisateur.Utilisateur) request.getAttribute(UtilisateurService.currentUser);
	%>
	<!-- Navbar -->
	<%@ include file="navbar.jsp"%>

	<!-- CORPS -->
	<div id="cover" class="row">
		<div class="col-md-10 col-md-offset-1" style="position: relative;">
			<img class="media-object"
				src="bootstrap/img/photo-couverture-facebook.jpg"
				alt="cover_picture"
				style="width: 100%; height: 315px; z-index: -999">
			<!-- Il faut changer le chemin de la tof' de couv' via une requete -->
			<div style="position: absolute">
				<a href="#" class="thumbnail pull-left"
					style="z-index: 999; margin-top: -150px; margin-left: 30px;"> <img
					data-src="holder.js/180x180" alt="..." src="bootstrap/img/user.png"
					style="width: 180px; height: 180px;">
				</a>
			</div>
			<div style="position: absolute;">
				<!-- Faire le test, si d�ja ami ou si ajout  -->
				<button type="button" class="btn btn-primary"
					style="z-index: 999; margin-left: 93.7%; margin-top: -90px; background-color: #3B5998; border: 2px solid white;">
					<span class="glyphicon glyphicon-plus"></span> Ajouter
				</button>
				<!--<button type="button" class="btn btn-success" style="z-index: 999;margin-left:95%; margin-top:-90px; background-color:#3B5998; border:2px solid white;"><span class="glyphicon glyphicon-ok"></span>
                    Ami</button>-->
			</div>
		</div>
	</div>
	<div class="row" style="margin-left: -1px;">
		<div class="col-md-8 col-md-offset-3">
			<ul class="nav nav-pills nav-justified">
				<li class="active"><a href="#statut" data-toggle="pill">Statut</a></li>
				<li id="test"><a href="#photo" data-toggle="pill">Photo</a></li>
				<li>
					<a href="#aff_amis" data-toggle="pill">Amis
						<span class="badge">
							<% 
								if (request.getAttribute("amis") != null)
									out.print(((List<Utilisateur>)request.getAttribute("amis")).size());
							%>
						</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="row-fluid" style="margin-top: 20px;">

		<!-- Menu vertical -->
		<%@ include file="menu_vertical.jsp"%>

		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="tab-content">
						<!-- Affichage tab status + ecriture statut -->
						<div id="statut" class="input-group tab-pane active" style="padding-top: 5px;">
							<form id="formAjoutStatut" method="post" action="index" novalidate="novalidate">
										<textarea class="form-control textarea" rows="3" placeholder="Partage ton statut !" cols="500"></textarea>
										<span class="txtError">Vous devez remplir le champs de texte.</span>
										<input type="submit" class="btn btn-primary pull-right" />
							</form>
							<hr>
							<% 
								if (request.getAttribute("statuts") != null && ((List<Statut>)request.getAttribute("statuts")).size() > 0) {
									List<Statut> statuts = (List<Statut>)request.getAttribute("statuts");
									for (Statut statut : statuts) {
							%>
										<div class="media">
											<a class="pull-left" href="#"> 
												<img class="media-object img-thumbnail" src="bootstrap/img/user.png"
													 alt="64x64" style="width: 64px; height: 64px;">
											</a>
											<div class="media-body">
												<h4 class="media-heading"><%= statut.getUtilisateur().getPrenom() %> <%= statut.getUtilisateur().getNom() %></h4>
												<p><%= statut.getLibelle() %></p>
												<a class="btn btn-link" style="color: green;">J'aime</a> <a
													class="btn btn-link" style="color: red;">J'aime pas</a> <a
													class="btn btn-link btn_comment">Commenter</a>
											</div>
										</div>
										<hr />
							<%
									}
								}else{
							%>
									<div class="alert alert-info">
										<i class="glyphicon glyphicon-warning-sign"></i> Vous n'avez aucun statuts pour le moment.
									</div>
							<%
								}
							%>
							
						</div>
						<!-- Affichage tab photos -->
						<div id="photo" class="input-group tab-pane"
							style="padding-top: 5px;">
							<div id="blueimp-gallery" class="blueimp-gallery">
								<!-- The container for the modal slides -->
								<div class="slides"></div>
								<!-- Controls for the borderless lightbox -->
								<h3 class="title"></h3>
								<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a>
								<a class="play-pause"></a>
								<ol class="indicator"></ol>
								<!-- The modal dialog, which will be used to wrap the lightbox content -->
								<div class="modal fade">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" aria-hidden="true">&times;</button>
												<h4 class="modal-title"></h4>
											</div>
											<div class="modal-body next"></div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default pull-left prev">
													<i class="glyphicon glyphicon-chevron-left"></i> Previous
												</button>
												<button type="button" class="btn btn-primary next">
													Next <i class="glyphicon glyphicon-chevron-right"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div id="blueimp-gallery-carousel"
								class="blueimp-gallery blueimp-gallery-carousel">
								<div class="slides"></div>
								<h3 class="title"></h3>
								<a class="prev">‹</a> <a class="next">›</a> <a
									class="play-pause"></a>
								<ol class="indicator"></ol>
							</div>

							<!-- gallerie de photos -->
							<div id="links" class="container-fluid">
								<div class="row-fluid col-md-12">
								<% 
									if (request.getAttribute("photos") != null && ((List<Photo>)request.getAttribute("photos")).size() > 0) {
										List<Photo> photos = (List<Photo>)request.getAttribute("photos");
										int i = 0;
										for (Photo photo : photos) {
											
								%>
											<div class="col-md-3">
												<a href="<%= photo.getLien() %>" title="<%= photo.getLien() %>" data-gallery> 
													<img src="<%= photo.getLien() %>" alt="<%= photo.getLien() %>" width="100%">
												</a>
											</div>
								<%
											i++;
											if (i % 4 == 0 ) {
												out.println("</div>");
												out.println("<div class=\"row-fluid col-md-12\">");
											}
										}
									}else{
								%>
										<div class="alert alert-info">
											<i class="glyphicon glyphicon-warning-sign"></i> Vous n'avez aucun ami pour le moment.
										</div>
								<%
									}
								%>
								</div>
								
								
							</div>
						</div>

						<!-- Affichage tab Amis -->
						<div id="aff_amis" class="input-group tab-pane" style="padding-top: 5px;">
							<ul class="list-inline">
								<%
									if (request.getAttribute("amis") != null && ((List<Utilisateur>)request.getAttribute("amis")).size() > 0) {
										List<Utilisateur> amis = (List<Utilisateur>)request.getAttribute("amis");
										int i = 0;
										for (Utilisateur ami : amis) {
												
								%>
											<li style="margin-right: 15px; margin-left: 15px;" class="span4">
												<div class="media">
													<a class="pull-left" href="#">
														<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="64x64" style="width: 64px; height: 64px;" />
													</a>
													<div class="media-body pull-right">
														<h4 class="media-heading" style="margin-top: 25px;"><%= ami.getPrenom() %> <%= ami.getNom() %></h4>
													</div>
												</div>
											</li>
								<%
											i++;
											if (i % 3 == 0 ) {
												out.println("</ul>");
												out.println("<ul class=\"list-inline\">");
											}
										}
									}else{
								%>
										<div class="alert alert-info">
											<i class="glyphicon glyphicon-warning-sign"></i> Vous n'avez aucun ami pour le moment.
										</div>
								<%			
									}
								%>
							</ul>
						</div>
					</div>

				</div>
				<div id="application" class="tab-pane"></div>
			</div>
		</div>
	</div>
</body>
<script>
		$('#links').onclick = function(event) {
		event = event || window.event;
		var target = event.target || event.srcElement, link = target.src ? target.parentNode
				: target, options = {
			index : link,
			event : event
		}, links = this.getElementsByTagName('a');
		blueimp.Gallery(links, options);
	};

	$('document').on('click', 'ta_comment > button.close', function() {
		console.log('TEST');
	});	
		
	
	//--- ajout d'un statut ---
	$('#formAjoutStatut').on('submit', function(e) {
		e.preventDefault();
		// test si la textarea n'est pas vide
		if ($(this).find('textarea').val() != '') {
			$(this).find('span.txtError').hide();
			// envoi de la requ�te AJAX
			$.ajax({
				type: $(this).attr('method'),
				url: $(this).attr('action'),
				data: {
					statut: $(this).find('textarea').val()
				},
				success: function() {
					$('this textarea').val('');
				}
			});
		}else{
			$(this).find('span.txtError').show();
		}
	});

	$(document).ready(
					function() {
						$('#test').on('click', function() {
							console.log('test');
						});

						$("#upload_link").on('click', function(e) {
							e.preventDefault();
							$("#upload:hidden").trigger('click');
						});
						
						
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

						$('.btn_comment')
								.on(
										'click',
										function() {
											if (!$(this).parents('.media-body')
													.hasClass('comment_active')) {
												$(this).css('display', 'none');
												$(this)
														.parents('.media-body')
														.addClass(
																'comment_active');
												$(this)
														.parents('.media-body')
														.append(
																'<div class="ta_comment" style="width: 100%;">'
																		+ '<button type="button" class="close pull-right" aria-hidden="true">&times;</button>'
																		+ '<textarea class="form-control" rows="1" placeholder="Commente !" cols="500"></textarea>'
																		+ '</div>');
											}
										});

						//$('.ta_comment').children('.close').on('click', function() {
						$('button.close').on('click', function() {
							console.log('TEST');
						});
					});
</script>
</html>