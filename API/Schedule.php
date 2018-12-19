<?php

    include_once 'JSON_objects/event/EventJSON.php';
    include_once 'JSON_objects/event/EventSchedule.php';
    include_once 'JSON_objects/event/EventEndSchedule.php';

    /**
     * Cria uma tabela temporária que contém os registros de repetições de compromissos
     * 
     * TODO:
     * Ocultar e marcar itens com status alterado pelo usuário
     * 
     */
    class Schedule{

        public function create_schedule($oCon){

            $cSQL = "CALL select_events()";

            $stmt = $oCon->prepare($cSQL);
            $stmt->execute();
            $oTable = $stmt->get_result();
            $stmt->close();

            $this->create_temp_table($oCon);

            while($oReg = mysqli_fetch_assoc($oTable)){

                $event = new EventJSON;
                $event->set_cod_evento($oReg["COD_EVENTO"]);
                $event->set_titulo($oReg["TITULO"]);
                $event->set_data_inicio(strtotime($oReg["DATA_INICIO"]));
                $event->set_data_fim(strtotime($oReg["DATA_FIM"]));
                $event->set_dia_todo($oReg["DIA_TODO"]);
                $event->set_local_evento($oReg["LOCAL_EVENTO"]);
                $event->set_descricao($oReg["DESCRICAO"]);
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

                /* inserindo repetições em uma tabela temporaria */

                $dt_event = new DateTime;
                $dt_event->setTimestamp($event->get_data_inicio());

                $dt_event_fim = new DateTime;
                $dt_event_fim->setTimestamp($event->get_data_fim());
                
                $dt_limit = $event->get_data_fim_repeticao();
                
                $cIn = "INSERT INTO E_RECORRENCIA(EVENTO, RPT_INICIO, RPT_FIM, CONCLUIDO, EXCLUIDO) 
                VALUES (?, ?, ?, ?, ?)"; 

                $stmtIn = $oCon->prepare($cIn);

                if(!$stmtIn){
                    die("Erro ao preparar insert na tabela temporária");
                }

                $stmtIn->bind_param("issii", $_evento, $_data_inicio, $_data_fim, $_concluido, $_excluido);

                while($dt_event < $dt_limit || $dt_event->diff($dt_limit)->days == 0){
                    
                    $_evento = $event->get_cod_evento();
                    $_data_inicio = $dt_event->format("Y-m-d H:i:s");
                    $_data_fim = $dt_event_fim->format("Y-m-d H:i:s");
                    $_concluido = 0;
                    $_excluido = 0; 
                    $stmtIn->execute();

                    $dt_event->add($event->get_interval());
                    $dt_event_fim->add($event->get_interval());
                }

                $stmtIn->close();
            }
        }

        /**
         * Cria uma tabela temporaria para cada repetição de compromisso
         */
        private function create_temp_table($oCon){

            $cTemp = "
                CREATE TEMPORARY TABLE E_RECORRENCIA(
                    EVENTO int not null,
                    RPT_INICIO datetime,
                    RPT_FIM datetime,
                    CONCLUIDO bit,
                    EXCLUIDO bit
                )
            ";

            if(!mysqli_query($oCon, $cTemp)){
                die("Erro ao criar tabela temporaria");
            }
        }
    }
?>