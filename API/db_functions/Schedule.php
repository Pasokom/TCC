<?php

    include_once '../objects/Holiday.php';

    /**
     * Cria uma tabela temporária que contém os registros de repetições de compromissos
     */
    class Schedule{

        public static function create_holiday_schedule($link){

            $cSQL = "SELECT * FROM DATAS_COMEMORATIVAS";
            $stmt = $link->prepare($cSQL);
            $stmt->execute();
            $oTable = $stmt->get_result();
            $stmt->close();

            self::create_temp_table_holidays($link);

            while($oReg = mysqli_fetch_assoc($oTable)){

                $holiday = new Holiday;
                $holiday->set_cod_feriado($oReg["COD_FERIADO"]);
                $holiday->set_nome($oReg["NOME"]);
                $holiday->set_tipo_data($oReg["TIPO_DATA"]);
                $holiday->set_dia($oReg["DIA"]);
                $holiday->set_dia_semana($oReg["DIA_SEMANA"]);
                $holiday->set_semana($oReg["SEMANA"]);
                $holiday->set_mes($oReg["MES"]);

                $cIn = "INSERT INTO H_DATA_COMEMORATIVA(DATA_COMEMORATIVA, RPT_DATA)
                VALUES (?, ?)";

                $stmtIn = $link->prepare($cIn);

                if(!$stmtIn){
                    die("Erro ao preparar insert na tabela temporária (data_comemorativa)");
                }

                $stmtIn->bind_param("is", $_data_comemorativa, $_data);
                $_data_comemorativa = $holiday->get_cod_feriado();
                $_data = $holiday->get_date_in_year()->format("Y-m-d");
                $stmtIn->execute();
                $stmtIn->close();
            }
        }

        /**
         * Cria uma tabela temporaria com os dias de cada data comemorativa.
         * 
         * @param connection $link objeto que representa uma conexão com o banco de dados.
         */
        private static function create_temp_table_holidays($link){

            $cTemp = "
                CREATE TEMPORARY TABLE H_DATA_COMEMORATIVA(
                    DATA_COMEMORATIVA int not null,
                    RPT_DATA datetime
                )
            ";
            
            if(!mysqli_query($link, $cTemp)){
                die("Erro ao criar tabela temporaria (data comemorativa)");
            }
        }
    }
?>