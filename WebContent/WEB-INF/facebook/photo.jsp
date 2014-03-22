<!DOCTYPE html>
<html>
	<!-- Header -->
	<%@ include file="header.jsp" %>
  	<body>
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
			  <a href="./" class="navbar-brand" style="padding-left:102px;"><img class="img-rounded" src="bootstrap/img/facebook_w.png" alt="logo" style="width:32px; height:32px;"></a>		  
			</div>
		
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-left list-inline" style="margin-top:8px; margin-left:25px;">
					<li id="amis"><a id="popoverAmi" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-user" style="color:white;"></a></span></li>
					<li id="message"><a id="popoverMessage" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-send" style="color:white;"></span></a></li>
					<li id="notification"><a id="popoverNotification" class="btn btn-link" data-toggle="popover" data-placement="bottom"><span class="glyphicon glyphicon-globe" style="color:white;"></span></a></li>
				</ul>
				<ul class="nav navbar-nav" style="padding-top:5px;  margin-left:168px;">	
					<li>
						<form class="navbar-form" role="search">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Recherche" style="width:500px;">
							</div>
							<button type="submit" class="btn btn-default">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</form>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right" style="padding-top:8.5px;">
					<li>
						<img class="media-object img-thumbnail" src="bootstrap/img/user.png" alt="32x32" style="width:46px; height:46px;">
					</li>
					<li style="color:white; padding:12px; font-weight:bold;">
						Prenom Nom
					</li>					
					<li style="padding:5px; padding-right:20px;">
						<div class="btn-group">
							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
								<span class="glyphicon glyphicon-cog"></span>
									Option 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Creer un groupe</a></li>
								<li><a href="#">Creer une page</a></li>
								<li><a href="#">Parametres</a></li>
								<li class="divider"></li>
								<li><a href="#">Deconnexion</a></li>
							</ul>
						</div>
					</li>
				</ul>  
			</div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<!-- Contenu -->
		<div id="blueimp-gallery" class="blueimp-gallery">
		   <!-- The container for the modal slides -->
			<div class="slides"></div>
			<!-- Controls for the borderless lightbox -->
			<h3 class="title"></h3>
			<a class="prev">‹</a>
			<a class="next">›</a>
			<a class="close">×</a>
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
								<i class="glyphicon glyphicon-chevron-left"></i>
								Previous
							</button>
							<button type="button" class="btn btn-primary next">
								Next
								<i class="glyphicon glyphicon-chevron-right"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="links">
			<a href="bootstrap/img/gallery/image1.jpg" title="Banana" data-gallery>
				<img src="bootstrap/img/gallery/image1.jpg" alt="Banana">
			</a>
			<a href="bootstrap/img/gallery/image2.jpg" title="Apple" data-gallery>
				<img src="bootstrap/img/gallery/image2.jpg" alt="Apple">
			</a>
			<a href="bootstrap/img/gallery/image5.jpg" title="Apple" data-gallery>
				<img src="bootstrap/img/gallery/image5.jpg" alt="Apple">
			</a>
			<a href="bootstrap/img/gallery/image6.jpg" title="Orange" data-gallery>
				<img src="bootstrap/img/gallery/image6.jpg" alt="Orange">
			</a>
		</div>
		
		<!-- fin Contenu -->
		
		<script>
		  $(document).click(function{});
		  $(document).on('ready', '#', function(){});
		  
		  $(document).getElementById('links').onclick = function (event) {
		       event = event || window.event;
		       var target = event.target || event.srcElement,
		           link = target.src ? target.parentNode : target,
		           options = {index: link, event: event},
		           links = this.getElementsByTagName('a');
		       blueimp.Gallery(links, options);
		   };
		   
		   $(document).on('click', 'ta_comment > button.close', function() {
				console.log('TEST');
				
			});
			
			//--- fermeture du textarea des commentaires ---
			$(document).on('click', '.btn_comment_close', function() {
				$(this).parent('div.ta_comment').remove();
				$(this).parent('div.media-body > a.btn.btn-link.btn_comment').css('display', 'block');
			});
			
			$(document).ready(function(){
		                   
		                   blueimp.Gallery(document.getElementById('links').getElementsByTagName('a'),{
		                           container: '#blueimp-gallery-carousel',
		                           carousel: true
		                       }
		                   );
		                   
		                   
		                   
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