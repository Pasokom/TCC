<?php
	$oCon = mysqli_connect('localhost', 'root', '1234', 'cyberlife_calendar');
	mysqli_set_charset($oCon, 'utf8');
	$vTitulo = array();
    $vValores = array();
	$vCores = array();
	$vCoresBorda = array();
	
	$cSQL = "SELECT IF(CONCLUIDO, 'CONCLUIDO', 'FAZER') CONCLUIDO, IF(CONCLUIDO, '#0277BD', '#E0E0E0') COR, COUNT(0) QUANTIDADE FROM TAREFA WHERE FK_USUARIO = '$_GET[usuario]' AND FK_PROJETO = '$_GET[projeto]' GROUP BY CONCLUIDO ORDER BY CONCLUIDO DESC";
	
	$dados = mysqli_query($oCon, $cSQL);

	mysqli_set_charset($oCon, 'UTF8');

	while($vReg = mysqli_fetch_assoc($dados)){
		$vTitulo[] = $vReg['CONCLUIDO'];
        $vValores[] = $vReg['QUANTIDADE'];
        $vCores[] = $vReg['COR'];
    }

	mysqli_close($oCon);
?>
<html>
    <style>
        h3 {
            font-family: "Arial Black", Gadget, sans-serif;
            font-weight: bold;
            color: #0277BD;
            text-align: center;
            display: block;
            margin-top: 40px;
        }

        p {
            font-family: verdana;
            text-align: center;
        }

        .chart-container {
            position: relative;
            margin: auto;
            height: 60vh;
            width: 80vw;
            top: 20px;
        }

        .message {
            position: absolute;
            top: 40%;
        }
    </style>
    <body>
        <div class="chart-container">
            <canvas id="objgrafico"></canvas>
        </div>
        <h3><?php echo $_GET['nome'] ?></h3>
        <p class="message"> 
            <?php if (empty($vValores)) {
                echo 'nenhuma tarefa cadastrada';
            } ?> 
        </p>
    </body>
    <script src="lib/chart.js"></script>
    <script>
        var oGrafico = new Chart(document.all.objgrafico.getContext('2d'), 
            { 
                type: 'doughnut',
                data: {  
                    labels: <?php echo json_encode($vTitulo, JSON_UNESCAPED_UNICODE); ?>,
                    datasets: [ 
                        { 
                            label: 'população', 
                            data: <?php echo json_encode($vValores); ?>,
                            backgroundColor: <?php echo json_encode($vCores); ?>,
                            borderColor: ['#FFFFFF', '#FFFFFF'],
                            borderWidth: 2	 
                        }
                    ]
                },
                options: {
                    legend: {
                        display: false
                    },
                    responsive: true,
                    maintainAspectRatio: false
                } 
            });             
    </script>
</html>

