<?php
    include_once "Schedule.php";
    include_once "query/HolidayQuery.php";
    include_once "query/EventQuery.php";
    include_once "JSON_objects/query_types/Daily.php";
    include_once "JSON_objects/query_types/Monthly.php";

    date_default_timezone_set("America/Sao_Paulo");

    $oCon = mysqli_connect("localhost", "root", "1234", "CYBER_LIFE");
    mysqli_set_charset($oCon, "utf8");

    $schedule = new Schedule();
    $schedule->create_schedule($oCon);

    // verifica se a variável foi iniciada
    if(isset($_GET["type"])){

        switch ($_GET["type"]) {
            case 'daily':
                if(isset($_GET["date"]))
                    type_daily($oCon, $_GET["date"]);
                else
                    type_daily($oCon, time());
                break;
            case 'monthly':
                if(isset($_GET["date"]))
                    type_monthly($oCon, $_GET["date"]);
                else
                    type_monthly($oCon, time());
                break;
            default:
                type_default($oCon);
                break;
        }
    }
    else
        type_default($oCon);

    function type_daily($oCon, int $day){
        $day = new Daily($oCon, $day);
        $day->encode();
    }

    function type_monthly($oCon, int $month){
        $month = new Monthly($oCon, $month);
        $month->encode();
    }

    function type_default($oCon){

        $events = EventQuery::getEvents($oCon, $_GET["user"]);
        $holidays = HolidayQuery::getHolidays($oCon);
    
        $jsonArray = array_merge($events, $holidays);
    
        echo json_encode(array("data" => $jsonArray), JSON_UNESCAPED_UNICODE, JSON_PRETTY_PRINT);
    }
?>