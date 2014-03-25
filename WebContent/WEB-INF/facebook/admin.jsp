<!DOCTYPE html>

<html>
  <head>
    <title>Projet Client/Serveur - Facebook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8"/>
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
  <nav class="navbar navbar-default" role="navigation" style="background-color:#3B5998;">
	  <div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
		  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		  </button>
		  <a href="./" class="navbar-brand" style="padding-left:102px; color:white;">Administration Panel</a>		  
		</div>		
	  </div><!-- /.container-fluid -->
	</nav>
	<div class="container-fluid">
		<div class="col-md-2">
			<h4> Dashboard </h4>
			<div class="pill-pane">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#vitesse" data-toggle="pill">Vitesse</a></li>
					<li><a href="#div_bd" data-toggle="pill">Base de donn�es</a></li>
					<li><a href="#chart_div" data-toggle="pill">Nombre de connect�s</a></li>
				</ul>
			</div>
		</div>
		<div class="tab-content col-md-10" id="conect">
			<div id="vitesse" class=" tab-pane active">
				<div class="col-md-4">
					<div id="cpugauge" style="width:400px; height:320px"></div>
				</div>
				<div class="col-md-4">
					<div id="memgauge" style="width:400px; height:320px"></div>
				</div>
				<div class="col-md-4">
					<div id="speedgauge" style="width:400px; height:320px"></div>
				</div>
			</div>
			<div id="div_bd" style="width: 100%; height: 100%;" class="tab-pane"></div>
			<div id="chart_div" style="width: 100%; height: 100%;" class="tab-pane"></div>
			<!--<div id="essai" class="col-md-10 tab-pane"> coucou </div>-->
		</div>
	</div>
	<script type="text/javascript" language="javascript">	
	var g1, g2;

  window.onload = function(){

    var g1 = new JustGage({
      id: "cpugauge", 
      //value: 25,
	  value: getRandomInt(0, 100), 	  
      min: 0,
      max: 100,
      title: "CPU",
      label: "Load",
	  levelColors: ['#CE1B21', '#D0532A', '#FFC414', '#85A137']
    });

    var g2 = new JustGage({
      id: "memgauge", 
      //value: 70, 
      value: getRandomInt(0, 100), 
	  min: 0,
      max: 100,
      title: "Memory",
      label: "Used",
	  levelColors: ['#CE1B21', '#D0532A', '#FFC414', '#85A137']
    });
	
	var g3 = new JustGage({
      id: "speedgauge", 
      //value: 25,
	  value: getRandomInt(0, 100), 	 
      min: 0,
      max: 100,
      title: "Speed",
      label: "Fast",
	  levelColors: ['#CE1B21', '#D0532A', '#FFC414', '#85A137']
    });

    /*setInterval(function() {
        $.get('ajax/cpu.php', function (newValue) { g1.refresh(newValue); });
        $.get('ajax/mem.php', function (newValue) { g2.refresh(newValue); });          
        $.get('ajax/mem.php', function (newValue) { g3.refresh(newValue); });          
    }, 2500);*/ //Ajax request nécessaires pour recharger les données de la BD au bout de 2500 secondes
  }; //Création des gauges pour l'onglet "Vitesse"
  
  
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawChartBD);
	google.setOnLoadCallback(drawChart);
	
	function drawChartBD() {
	var data = google.visualization.arrayToDataTable([
	  ['Hours', 'Insert Request', 'Select Resquest'],
	  ['1h00',  getRandomInt(0, 100), getRandomInt(0, 100)],
	  ['2h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['3h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['4h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['5h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['6h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['7h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['8h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['9h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['10h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['11h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['12h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['13h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['14h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['15h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['16h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['17h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['18h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['19h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['20h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['21h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['22h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['23h00',  getRandomInt(0, 100),getRandomInt(0, 100)],
	  ['00h00',  getRandomInt(0, 100),getRandomInt(0, 100)]
	]);
	
	new google.visualization.LineChart(document.getElementById('div_bd')).
	draw(
		data, {
					curveType: "function", 
					//fontSize:8,
					width: 1000, 
					height: 400
				}
	  );
	//chart.draw(data, options);
  } //Création graph BD
	
	
	function drawChart() {
 
	var data = google.visualization.arrayToDataTable([
	  ['Month', 'Visitors'],
	  ['Jan',  getRandomInt(0, 100)],
	  ['Feb',  getRandomInt(0, 100)],
	  ['March',  getRandomInt(0, 100)],
	  ['Apr',  getRandomInt(0, 100)],
	  ['May',  getRandomInt(0, 100)],
	  ['Sept',  getRandomInt(0, 100)],
	  ['Oct',  getRandomInt(0, 100)],
	  ['Nov',  getRandomInt(0, 100)],
	  ['Dec',  getRandomInt(0, 100)]
	]);

	new google.visualization.LineChart(document.getElementById('chart_div')).
	draw(data, {curveType: "function",
			  width: 1000, height: 400}
	  );
	//chart.draw(data, options);
  } //Création du nombre de visiteurs	

	</script>
	
</body>

</html>