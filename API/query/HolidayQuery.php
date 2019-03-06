<?php
    include_once "Schedule.php";

    class HolidayQuery
    {
        public static function getHolidays($link){

            $query = "SELECT * FROM DATAS_COMEMORATIVAS
                        LEFT JOIN H_DATA_COMEMORATIVA ON COD_FERIADO = DATA_COMEMORATIVA";

            $table = mysqli_query($link, $query);

            $holidays = array();

            while($oReg = mysqli_fetch_assoc($table)){

                $holiday = new Holiday;
                $holiday->set_cod_feriado($oReg["COD_FERIADO"]);
                $holiday->set_nome($oReg["NOME"]);
                $holiday->set_tipo_data($oReg["TIPO_DATA"]);
                $holiday->set_dia($oReg["DIA"]);
                $holiday->set_dia_semana($oReg["DIA_SEMANA"]);
                $holiday->set_semana($oReg["SEMANA"]);
                $holiday->set_mes($oReg["MES"]);
                $holiday->set_data(strtotime($oReg["RPT_DATA"]));

                $holidays[] = $holiday;
            }

            return $holidays;
        }

        public static function getDayHolidays($link, $user, int $date){

            $formatted_date = date("Y-m-d", $date);
            
            $query = "SELECT * FROM DATAS_COMEMORATIVAS
                        LEFT JOIN H_DATA_COMEMORATIVA ON COD_FERIADO = DATA_COMEMORATIVA
                        WHERE date(RPT_DATA) = '$formatted_date'";

            $table = mysqli_query($link, $query);

            $holidays = array();

            while($oReg = mysqli_fetch_assoc($table)){

                $holiday = new Holiday;
                $holiday->set_cod_feriado($oReg["COD_FERIADO"]);
                $holiday->set_nome($oReg["NOME"]);
                $holiday->set_tipo_data($oReg["TIPO_DATA"]);
                $holiday->set_dia($oReg["DIA"]);
                $holiday->set_dia_semana($oReg["DIA_SEMANA"]);
                $holiday->set_semana($oReg["SEMANA"]);
                $holiday->set_mes($oReg["MES"]);
                $holiday->set_data(strtotime($oReg["RPT_DATA"]));

                $holidays[] = $holiday;
            }

            return $holidays;
        }

        public static function getMonthHolidays($link, $user, int $date){

            $formatted_min_date = date("Y-m-01", $date);
            $formatted_max_date = date("Y-m-t" , $date);
            
            $query = "SELECT * FROM DATAS_COMEMORATIVAS
                        LEFT JOIN H_DATA_COMEMORATIVA ON COD_FERIADO = DATA_COMEMORATIVA
                        WHERE date(RPT_DATA) >= '$formatted_min_date' AND date(RPT_DATA) <= '$formatted_max_date'";

            $table = mysqli_query($link, $query);

            $holidays = array();

            while($oReg = mysqli_fetch_assoc($table)){

                $holiday = new Holiday;
                $holiday->set_cod_feriado($oReg["COD_FERIADO"]);
                $holiday->set_nome($oReg["NOME"]);
                $holiday->set_tipo_data($oReg["TIPO_DATA"]);
                $holiday->set_dia($oReg["DIA"]);
                $holiday->set_dia_semana($oReg["DIA_SEMANA"]);
                $holiday->set_semana($oReg["SEMANA"]);
                $holiday->set_mes($oReg["MES"]);
                $holiday->set_data(strtotime($oReg["RPT_DATA"]));

                $holidays[] = $holiday;
            }

            return $holidays;
        }
    }
?>