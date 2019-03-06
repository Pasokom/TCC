<?php
    include_once '../config/database.php';
    include_once '../db_functions/Schedule.php';
    include_once '../db_functions/HolidayDB.php';

    date_default_timezone_set("America/Sao_Paulo");

    $link = Database::get_connection();
    $date = $_GET['date'] or die('Data não informada');

    Schedule::create_holiday_schedule($link);

    if(isset($_GET['type'])){

        switch ($_GET['type']) {
            case 'monthly':
                $holidays = HolidayDB::getMonthHolidays($link, $date);
                break;
            case 'daily':
                $holidays = HolidayDB::getDayHolidays($link, $date);
                break;
            default:
                die('Tipo de consulta não reconhecido');
                break;
        }
    }
    else{
        die('Tipo de consulta não informado');
    }

    echo json_encode(array("data" => $holidays), JSON_UNESCAPED_UNICODE);
?>