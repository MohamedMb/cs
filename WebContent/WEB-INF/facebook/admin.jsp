<!DOCTYPE html>

<html>
<head>
<title>Projet Client/Serveur - Facebook</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="UTF-8" />
<!-- Bootstrap -->
<link href="bootstrapAdmin/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrapAdmin/css/style.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>

<script src="bootstrapAdmin/js/bootstrap.js"></script>

<script src="justGage/resources/js/raphael.2.1.0.min.js"></script>
<script src="justGage/resources/js/justgage.1.0.1.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-default" role="navigation"
		style="background-color: #3B5998;">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a href="./" class="navbar-brand"
					style="padding-left: 102px; color: white;">Administration Panel</a>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="container-fluid">
		<div class="col-md-2">
			<h4>Dashboard</h4>
			<div class="pill-pane">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#vitesse" data-toggle="pill">Vitesse</a></li>
					<li><a href="#div_bd" data-toggle="pill" id="monteeCharge">Montée
							en charge base de données</a></li>
					<li><a href="#chart_div" data-toggle="pill">Nombre de
							connectés</a></li>
				</ul>
			</div>
		</div>
		<div class="tab-content col-md-10" id="conect">
			<div id="vitesse" class=" tab-pane active">
				<div class="col-md-4">
					<div id="cpugauge" style="width: 400px; height: 320px"></div>
				</div>
				<div class="col-md-4">
					<div id="memgauge" style="width: 400px; height: 320px"></div>
				</div>
				<div class="col-md-4">
					<div id="speedgauge" style="width: 400px; height: 320px"></div>
				</div>
			</div>
			<div id="div_bd" style="width: 100%; height: 100%;" class="tab-pane">

			</div>
			<div class="well" id="monFormBdd" style="display: none;">
				<form method="get">
					Titre <input id="titre" type="text" /> Nombre de requête <input
						id="nbReq" name="nbReq" type="text" /> Utiliser avec threads <input
						type="checkbox" name="useThread" id="useThread" /> <select
						id="typeReq" name="typeReq">
						<option value="select">Select</option>
						<option value="insert">Insert</option>
					</select> <select id="typeBench" name="typeBench">
						<option value="simple">Connexion simple</option>
						<option value="pool">Bassin de connexion</option>
					</select>
				</form>
				<button id="btnTest">Start</button>
			</div>


			<div id="chart_div" style="width: 100%; height: 100%;"
				class="tab-pane"></div>
			<!--<div id="essai" class="col-md-10 tab-pane"> coucou </div>-->
		</div>
	</div>
	<style>
.loading-container {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	display: none;
}

.loading-container:before {
	position: absolute;
	top: 50%;
	left: 50%;
	display: block;
	width: 100px;
	height: 100px;
	line-height: 100px;
	margin: -50px 0 0 -50px;
	color: #fff;
	text-align: center;
	background: rgba(0, 0, 0, .9);
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	content: 'Chargement...';
}
</style>
	<div id="loading-container" class="loading-container"></div>
	<div id="content-container" class="content-container"></div>
	<script type="text/javascript" language="javascript">
		//var g1, g2;
		var dataArray = [ [ 'Connexion' ], [ '200' ], [ '400' ], [ '600' ],
				[ '800' ], [ '1000' ] ]; //Pour le benchmark bdd, PAS TOUCHE !

		//Pour debugger array js
		function print_r(arr, level) {
			var dumped_text = "";
			if (!level)
				level = 0;

			//The padding given at the beginning of the line.
			var level_padding = "";
			for ( var j = 0; j < level + 1; j++)
				level_padding += "    ";

			if (typeof (arr) == 'object') { //Array/Hashes/Objects 
				for ( var item in arr) {
					var value = arr[item];

					if (typeof (value) == 'object') { //If it is an array,
						dumped_text += level_padding + "'" + item + "' ...\n";
						dumped_text += print_r(value, level + 1);
					} else {
						dumped_text += level_padding + "'" + item + "' => \""
								+ value + "\"\n";
					}
				}
			} else { //Stings/Chars/Numbers etc.
				dumped_text = "===>" + arr + "<===(" + typeof (arr) + ")";
			}
			return dumped_text;
		}

		$(document).ready(function() {
			var loadingContainer = $('#loading-container', this.el);
			$(document).ajaxStart(function() {
				loadingContainer.show();
			});
			$(document).ajaxStop(function() {
				loadingContainer.hide();
			});
			$(document).on('click', 'a', function() {
				$('#monFormBdd').hide();
			});

			$(document).on('click', '#monteeCharge', function() {
				$('#monFormBdd').show();
			});

			$(document).on('click', '#btnTest', function() {
				//var $country = $("select#country").val()
				var $typeReq = $("#typeReq").val();
				var $nbReq = $("#nbReq").val();
				var $useThread = $("#useThread").is(':checked');
				var $typeBench = $("#typeBench").val();
				var $titre = $("#titre").val();

				$.get('http://localhost:8080/cs/admin_json', {
					typeReq : $typeReq,
					nbReq : $nbReq,
					useThread : $useThread,
					typeBench : $typeBench
				}, function(responseJson) {
					var i = 1;

					dataArray[0] = dataArray[0].concat($titre);
					$.each(responseJson, function(key, value) {
						dataArray[i] = dataArray[i].concat(parseInt(value));
						i++;
					});
					//alert(print_r(dataArray));
					editChartBD(dataArray);
				});

			});
		});

		window.onload = function() {

			var g1 = new JustGage({
				id : "cpugauge",
				//value: 25,
				value : getRandomInt(0, 100),
				min : 0,
				max : 100,
				title : "CPU",
				label : "% Load",
				startAnimationType : 'bounce',
				startAnimationTime : 1500,
				levelColors : [ '#CE1B21', '#D0532A', '#FFC414', '#85A137' ]
			});

			var g2 = new JustGage({
				id : "memgauge",
				//value: 70, 
				value : getRandomInt(0, 100),
				min : 0,
				max : 100,
				title : "Memory",
				label : "% Used",
				startAnimationType : 'bounce',
				startAnimationTime : 1500,
				levelColors : [ '#CE1B21', '#D0532A', '#FFC414', '#85A137' ]
			});

			var g3 = new JustGage({
				id : "speedgauge",
				//value: 25,
				value : getRandomInt(0, 100),
				min : 0,
				max : 100,
				title : "Speed",
				label : "% Fast",
				startAnimationType : 'bounce',
				startAnimationTime : 1500,
				levelColors : [ '#CE1B21', '#D0532A', '#FFC414', '#85A137' ]
			});

			setInterval(function() {
				g1.refresh(getRandomInt(0, 100));
				g2.refresh(getRandomInt(0, 100));
				g3.refresh(getRandomInt(0, 100));
			}, 2500);

			/*setInterval(function() {
			    $.get('ajax/cpu.php', function (newValue) { g1.refresh(newValue); });
			    $.get('ajax/mem.php', function (newValue) { g2.refresh(newValue); });          
			    $.get('ajax/mem.php', function (newValue) { g3.refresh(newValue); });          
			}, 2500);*///Ajax request nÃ©cessaires pour recharger les donnÃ©es de la BD au bout de 2500 secondes
		}; //CrÃ©ation des gauges pour l'onglet "Vitesse"

		google.load("visualization", "1", {
			packages : [ "corechart" ]
		});
		//google.setOnLoadCallback(drawChartBD);
		google.setOnLoadCallback(drawChart);

		function editChartBD(data) {
			var tempData = google.visualization.arrayToDataTable(data);

			new google.visualization.LineChart(document
					.getElementById('div_bd')).draw(tempData, {
				curveType : "function",
				//fontSize:8,
				width : 1000,
				height : 400,
				vAxis : {
					minValue : 0,
					maxValue : 1000
				},
				hAxis : {
					minValue : 0,
					maxValue : 40000
				}
			});
			//chart.draw(data, options);
		}
		function drawChartBD() {
			var data = google.visualization.arrayToDataTable(dataArray);

			new google.visualization.LineChart(document
					.getElementById('div_bd')).draw(data, {
				curveType : "function",
				//fontSize:8,
				width : 1000,
				height : 400,
				vAxis : {
					minValue : 0,
					maxValue : 1000
				},
				hAxis : {
					minValue : 0,
					maxValue : 40000
				}
			});
			//chart.draw(data, options);
		} //CrÃ©ation graph BD

		function drawChart() {

			var data = google.visualization.arrayToDataTable([
					[ 'Month', 'Visitors' ], [ 'Jan', getRandomInt(0, 100) ],
					[ 'Feb', getRandomInt(0, 100) ],
					[ 'March', getRandomInt(0, 100) ],
					[ 'Apr', getRandomInt(0, 100) ],
					[ 'May', getRandomInt(0, 100) ],
					[ 'Sept', getRandomInt(0, 100) ],
					[ 'Oct', getRandomInt(0, 100) ],
					[ 'Nov', getRandomInt(0, 100) ],
					[ 'Dec', getRandomInt(0, 100) ] ]);

			new google.visualization.LineChart(document
					.getElementById('chart_div')).draw(data, {
				curveType : "function",
				width : 1000,
				height : 400
			});
			//chart.draw(data, options);
		} //CrÃ©ation du nombre de visiteurs
	</script>

</body>

</html>