<?php 
    $id = $_GET['id'];
    $file = "localhost/cyberlife/imagens/$id.jpg";

    $file_headers = @get_headers($file);
    if(!$file_headers || $file_headers[0] == 'HTTP/1.1 404 Not Found') {
        echo 'non existe';
    }
    else {
        echo 'existe';
    }
?>