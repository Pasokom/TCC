<?php
    include_once "Schedule.php";

    $oCon = mysqli_connect("localhost", "root", "1234", "CYBER_LIFE");
    
    $schedule = new Schedule();
    $schedule->create_schedule($oCon);

    $cSQL = "SELECT * FROM EVENTO 
            left join E_REPETIR on EVENTO.COD_EVENTO = E_REPETIR.FK_EVENTO
            left join E_FIM_REPETICAO on EVENTO.COD_EVENTO = E_FIM_REPETICAO.FK_EVENTO
            left join E_RECORRENCIA on EVENTO.COD_EVENTO = E_RECORRENCIA.EVENTO
            WHERE FK_USUARIO = $_GET[user]";

    $oTabela = mysqli_query($oCon, $cSQL);

    $jsonArray = array();

    while($oReg = mysqli_fetch_assoc($oTabela)){
        
        $event = new EventJSON;
        $event->set_cod_evento($oReg["COD_EVENTO"]);
        $event->set_titulo($oReg["TITULO"]);
        $event->set_data_inicio(strtotime($oReg["RPT_INICIO"]));
        $event->set_data_fim(strtotime($oReg["RPT_FIM"]));
        $event->set_dia_todo($oReg["DIA_TODO"]);
        $event->set_local_evento($oReg["LOCAL_EVENTO"]);
        $event->set_descricao($oReg["DESCRICAO"]);
        $event->set_concluido($oReg["CONCLUIDO"]);
        $event->set_excluido($oReg["EXCLUIDO"]);
        $event->set_tipo_repeticao($oReg["TIPO_REPETICAO"]);
        $event->set_tipo_fim_repeticao($oReg["TIPO_FIM_REPETICAO"]);
        $event->set_fk_usuario($oReg["FK_USUARIO"]);

        $eventSchedule = new EventSchedule;
        $eventSchedule->set_cod_repeticao($oReg["COD_REPETICAO"]);
        $eventSchedule->set_intervalo($oReg["INTERVALO"]);
        $eventSchedule->set_dias_semana($oReg["DIAS_SEMANA"]);
        $eventSchedule->set_fk_evento($oReg["FK_EVENTO"]);

        $eventEndSchedule = new EventEndSchedule;
        $eventEndSchedule->set_cod_fim_repeticao($oReg["COD_FIM_REPETICAO"]);
        $eventEndSchedule->set_dia_fim(strtotime($oReg["DIA_FIM"]));
        $eventEndSchedule->set_qtd_recorrencias($oReg["QTD_RECORRENCIAS"]);
        $eventEndSchedule->set_fk_evento($oReg["FK_EVENTO"]);

        $event->set_horario_evento($eventSchedule);
        $event->set_horario_fim_evento($eventEndSchedule);

        $jsonArray[] = $event;
    }

    echo json_encode(array("data" => $jsonArray));
?>