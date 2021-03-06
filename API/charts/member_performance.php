<?php
	$oCon = mysqli_connect('localhost', 'root', '1234', 'cyberlife_calendar');
	mysqli_set_charset($oCon, 'utf8');
	$vTitulo = array();
    $vValores = array();
    $vCores = array();
    
	$cSQL = "CALL DESEMPENHO_MEMBRO($_GET[projeto], $_GET[usuario])";
	
	$dados = mysqli_query($oCon, $cSQL);

	while($vReg = mysqli_fetch_assoc($dados)){
		$vTitulo[] = $vReg['DATA_TAREFA'];
        $vValores[] = $vReg['QTD_TAREFAS'];
        $vCores[] = $vReg['COR'];
    }

	mysqli_close($oCon);
?>
<html>
    <style>
        .chart-container {
            position: relative;
            margin: auto;
            height: 60vh;
            width: 80vw;
            top: 20px;
        }
        h3 {
            font-family: "Arial Black", Gadget, sans-serif;
            font-weight: bold;
            color: #0277BD;
            text-align: center;
            display: block;
            margin-top: 40px;
        }
    </style>
    <body>
        <h3><?php echo $_GET['nome'] ?></h3>
        <div class="chart-container">
            <canvas id="objgrafico"></canvas>
        </div>
    </body>
    <script src="lib/chart.js"></script>
    <script>
        var oGrafico = new Chart(document.all.objgrafico.getContext('2d'), 
            { 
                type: 'line',
                data: {  
                    labels: <?php echo json_encode($vTitulo, JSON_UNESCAPED_UNICODE); ?>,
                    datasets: [ 
                        { 
                            label: 'tarefas concluídas', 
                            data: <?php echo json_encode($vValores); ?>,
                            borderColor: '#0277BD',
                            fill: false,
                            borderWidth: 4	 
                        }
                    ]
                },
                options: {
                    elements: {
                        line: {
                            tension: 0.1
                        }
                    },
                    scales: {
                        yAxes: [{
                            stacked: true,
                            ticks: {
                                beginAtZero: true,
                                stepSize: 1
                            }
                        }]
                    },
                    legend: {
                        display: false
                    },
                    responsive: true,
                    maintainAspectRatio: false
                } 
            });             
    </script>
</html>

