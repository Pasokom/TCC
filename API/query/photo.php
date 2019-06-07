<?php 
    $id = $_GET['id'];
    $file = "http://localhost/cyberlife/imagens/img$id.jpeg";

    if(@getimagesize($file)) {
        echo 'existe';
    }
    else {
        echo 'non existe';
    }
?>