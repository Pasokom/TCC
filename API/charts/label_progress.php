<?php
	$oCon = mysqli_connect('localhost', 'root', '1234', 'cyberlife_calendar');
	mysqli_set_charset($oCon, 'utf8');
	$vTitulo = array();
    $vValores = array();
	$vCores = array();
	$vCoresBorda = array();
	
	$cSQL = "SELECT IF(CONCLUIDO, 'CONCLUIDO', 'FAZER') CONCLUIDO, COUNT(0) QUANTIDADE FROM TAREFA WHERE FK_NOME_MARCADOR = '$_GET[marcador]'  AND FK_PROJETO = '$_GET[projeto]' GROUP BY CONCLUIDO ORDER BY CONCLUIDO DESC";
	
	$dados = mysqli_query($oCon, $cSQL);

	mysqli_set_charset($oCon, 'UTF8');

	while($vReg = mysqli_fetch_assoc($dados)){
		$vTitulo[] = $vReg['CONCLUIDO'];
		$vValores[] = $vReg['QUANTIDADE'];
    }

	mysqli_close($oCon);
?>
<html>
    <style>
        h3 {
            font-family: verdana;
            font-weight: bold;
            color: #03073B;
            text-align: center;
            margin-top: 20px;
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
            top: 10px;
        }

        .message {
            position: absolute;
            top: 40%;
        }
    </style>
    <body>
        <h3><?php echo $_GET['marcador'] ?></h3>
        <div class="chart-container">
            <canvas id="objgrafico"></canvas>
        </div>
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
                type: 'pie',
                data: {  
                    labels: <?php echo json_encode($vTitulo, JSON_UNESCAPED_UNICODE); ?>,
                    datasets: [ 
                        { 
                            label: 'população', 
                            data: <?php echo json_encode($vValores); ?>,
                            backgroundColor: ['#CC361F', '#03073B'],
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

