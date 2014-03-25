<!DOCTYPE html>
<%@page import="fr.miage.facebook.utilisateur.UtilisateurService"%>
<html>
    <!-- Header -->
    <%@ include file="header.jsp" %>
	<script>
	    document.getElementById('links').onclick = function(event) {
	        event = event || window.event;
	        var target = event.target || event.srcElement, link = target.src ? target.parentNode
	                : target, options = {
	                    index: link,
	                    event: event
	                }, links = this.getElementsByTagName('a');
	        blueimp.Gallery(links, options);
	    };
	
	    $('document').on('click', 'ta_comment > button.close', function() {
	        console.log('TEST');
	    });
	
	    $(document).ready(function() {
	        $('#test').on('click', function() {
	            console.log('test');
	        });
	
	        $("#upload_link").on('click', function(e) {
	            e.preventDefault();
	            $("#upload:hidden").trigger('click');
	        });
	
	        $('.btn_comment').on('click', function() {
	            if (!$(this).parents('.media-body').hasClass('comment_active')) {
	                $(this).css('display', 'none');
	                $(this).parents('.media-body').addClass('comment_active');
	                $(this).parents('.media-body').append('<div class="ta_comment" style="width: 100%;">'
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
    <body>
        <!-- MENU HAUT -->
        <jsp:useBean id="currentUser" class="fr.miage.facebook.utilisateur.Utilisateur" scope="session" />
        <%
        	currentUser = (fr.miage.facebook.utilisateur.Utilisateur) request.getAttribute(UtilisateurService.currentUser);
        %>
        <!-- Navbar -->
		<%@ include file="navbar.jsp" %>

        <!-- CORPS -->
        <div id="cover" class="row">
            <div class="col-md-10 col-md-offset-1" style="position: relative;">
                <img class="media-object"
                     src="bootstrap/img/photo-couverture-facebook.jpg"
                     alt="cover_picture"
                     style="width: 100%; height: 315px; z-index: -999"><!-- Il faut changer le chemin de la tof' de couv' via une requete -->
                <div style="position: absolute">
                    <a href="#" class="thumbnail pull-left"
                       style="z-index: 999; margin-top: -150px; margin-left: 30px;"> <img
                            data-src="holder.js/180x180" alt="..." src="bootstrap/img/user.png"
                            style="width: 180px; height: 180px;">
                    </a>
                </div>
                <div style="position: absolute; "><!-- Faire le test, si d�ja ami ou si ajout  -->
                    <button type="button" class="btn btn-primary" style="z-index: 999;margin-left:94%; margin-top:-90px; background-color:#3B5998; border:2px solid white;"><span class="glyphicon glyphicon-plus"></span>
                    Ajouter</button>
                </div>
            </div>
        </div>
        <div class="row" style="margin-left: -1px;">
            <div class="col-md-8 col-md-offset-3">
                <ul class="nav nav-pills nav-justified">
                    <li class="active"><a href="#statut" data-toggle="pill">Statut</a></li>
                    <li id="test"><a href="#photo" data-toggle="pill">Photo</a></li>
                    <li><a href="#aff_amis" data-toggle="pill">Amis<span class="badge">42</span></a></li>
                </ul>
            </div>
        </div>
        <div class="row-fluid" style="margin-top: 20px;">
			
			<!-- Menu vertical -->
			<%@ include file="menu_vertical.jsp" %>
			
            <div class="col-md-8">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="tab-content">
                            <!-- Affichage tab status + ecriture statut -->
                            <div id="statut" class="input-group tab-pane active"
                                 style="padding-top: 5px;">
                                <textarea class="form-control" rows="1"
                                          placeholder="Exprime toi!" cols="500"></textarea>
                                <hr>
                                <div class="media">
                                    <a class="pull-left" href="#">
                                    	<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="64x64" style="width: 64px; height: 64px;">
                                    </a>
                                    <div class="media-body">
                                        <h4 class="media-heading">Prenom NOM</h4>
                                        <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
                                            metus scelerisque ante sollicitudin commodo. Cras purus odio,
                                            vestibulum in vulputate at, tempus viverra turpis. Fusce
                                            condimentum nunc ac nisi vulputate fringilla. Donec lacinia
                                            congue felis in faucibus.</p>
                                        <a class="btn btn-link" style="color: green;">J'aime</a> <a
                                            class="btn btn-link" style="color: red;">J'aime pas</a> <a
                                            class="btn btn-link btn_comment">Commenter</a>
                                    </div>
                                </div>
                                <hr />
                                <div class="media">
                                    <a class="pull-left" href="#">
                                    	<img class="media-object img-thumbnail" src="bootstrap/img/user.png"
                                             alt="64x64" style="width: 64px; height: 64px;">
                                    </a>
                                    <div class="media-body">
                                        <h4 class="media-heading">Prenom NOM</h4>
                                        <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
                                            metus scelerisque ante sollicitudin commodo. Cras purus odio,
                                            vestibulum in vulputate at, tempus viverra turpis. Fusce
                                            condimentum nunc ac nisi vulputate fringilla. Donec lacinia
                                            congue felis in faucibus.</p>
                                        <a class="btn btn-link" style="color: green;">J'aime</a> <a
                                            class="btn btn-link" style="color: red;">J'aime pas</a> <a
                                            class="btn btn-link btn_comment">Commenter</a>
                                        <div></div>
                                    </div>
                                </div>
                                <hr />
                                <div class="media">
                                    <a class="pull-left" href="#"> <img
                                            class="media-object img-thumbnail" src="bootstrap/img/user.png"
                                            alt="64x64" style="width: 64px; height: 64px;">
                                    </a>
                                    <div class="media-body">
                                        <h4 class="media-heading">Prenom NOM</h4>
                                        <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
                                            metus scelerisque ante sollicitudin commodo. Cras purus odio,
                                            vestibulum in vulputate at, tempus viverra turpis. Fusce
                                            condimentum nunc ac nisi vulputate fringilla. Donec lacinia
                                            congue felis in faucibus.</p>
                                        <a class="btn btn-link" style="color: green;">J'aime</a> <a
                                            class="btn btn-link" style="color: red;">J'aime pas</a> <a
                                            class="btn btn-link btn_comment">Commenter</a>
                                        <div></div>
                                    </div>
                                </div>
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

                                <div id="links">
                                    <a href="images/banana.jpg" title="Banana" data-gallery> <img
                                            src="bootstrap/img/gallery/image1.jpg" alt="Banana">
                                    </a> <a href="images/apple.jpg" title="Apple" data-gallery> <img
                                            src="bootstrap/img/gallery/image2.jpg" alt="Apple">
                                    </a> <a href="images/orange.jpg" title="Orange" data-gallery> <img
                                            src="bootstrap/img/gallery/image3.jpg" alt="Orange">
                                    </a> <a href="images/banana.jpg" title="Banana" data-gallery> <img
                                            src="bootstrap/img/gallery/image4.jpg" alt="Banana">
                                    </a> <a href="images/apple.jpg" title="Apple" data-gallery> <img
                                            src="bootstrap/img/gallery/image5.jpg" alt="Apple">
                                    </a> <a href="images/orange.jpg" title="Orange" data-gallery> <img
                                            src="bootstrap/img/gallery/image6.jpg" alt="Orange">
                                    </a> <a href="images/banana.jpg" title="Banana" data-gallery> <img
                                            src="bootstrap/img/gallery/image1.jpg" alt="Banana">
                                    </a> <a href="images/apple.jpg" title="Apple" data-gallery> <img
                                            src="bootstrap/img/gallery/image2.jpg" alt="Apple">
                                    </a> <a href="images/orange.jpg" title="Orange" data-gallery> <img
                                            src="bootstrap/img/gallery/image3.jpg" alt="Orange">
                                    </a> <a href="images/banana.jpg" title="Banana" data-gallery> <img
                                            src="bootstrap/img/gallery/image4.jpg" alt="Banana">
                                    </a> <a href="images/apple.jpg" title="Apple" data-gallery> <img
                                            src="bootstrap/img/gallery/image5.jpg" alt="Apple">
                                    </a> <a href="images/orange.jpg" title="Orange" data-gallery> <img
                                            src="bootstrap/img/gallery/image6.jpg" alt="Orange">
                                    </a>
                                </div>
                            </div>

                            <!-- Affichage tab Amis -->
                            <div id="aff_amis" class="input-group tab-pane" style="padding-top: 5px;">
							<ul class="list-inline">
								<li style="margin-right: 30px;"><div class="media">
										<a class="pull-left" href="#"> <img
											class="media-object img-thumbnail"
											src="bootstrap/img/user.png" alt="64x64"
											style="width: 64px; height: 64px;">
										</a>
										<div class="media-body pull-right">
											<h4 class="media-heading" style="margin-top: 25px;">Ami
												1</h4>
										</div>
									</div></li>
								<li style="margin-right: 30px;"><div class="media">
										<a class="pull-left" href="#"> <img
											class="media-object img-thumbnail"
											src="bootstrap/img/user.png" alt="64x64"
											style="width: 64px; height: 64px;">
										</a>
										<div class="media-body pull-right">
											<h4 class="media-heading" style="margin-top: 25px;">Ami
												2</h4>
										</div>
									</div></li>
							</ul>
						</div>
                        </div>

                    </div>
                    <div id="application" class="tab-pane"></div>
                </div>
            </div>
        </div>
    </body>
</html>