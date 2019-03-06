<?php
    include_once '../math_functions/MoonPhase.php';

    $data = $_GET['date'] ;

    $moon = MoonPhase::calculate($data);

    echo json_encode($moon, JSON_UNESCAPED_UNICODE);
?>