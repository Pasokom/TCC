<?php 
    class Database{

        private static $host = 'localhost';
        private static $database = 'cyberlife_calendar';
        private static $password = '1234';
        private static $user = 'root';
        
        public $link;

        public static function get_connection() {

            $link = mysqli_connect(self::$host, self::$user, self::$password, self::$database);
            return $link;
        }
    }
?>