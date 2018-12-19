<?php
    class EventSchedule implements \JsonSerializable {

        private $cod_repeticao;
        private $intervalo;
        private $dias_semana;
        private $fk_evento;

        public function get_cod_repeticao() : int {
            return $this->cod_repeticao;
        }

        public function set_cod_repeticao(int $cod_repeticao){
            $this->cod_repeticao = $cod_repeticao;
        }

        public function get_intervalo() : int {
            return $this->intervalo;
        }

        public function set_intervalo(int $intervalo){
            $this->intervalo = $intervalo;
        }

        public function get_dias_semana() : string {
            return $this->dias_semana;
        }

        public function set_dias_semana(string $dias_semana){
            $str_array = preg_split("/,/",$dias_semana);
            $bool_array = array();

            foreach ($str_array as $value) {
                $bool_array[] = filter_var($value, FILTER_VALIDATE_BOOLEAN);
            }

            $this->dias_semana = $bool_array;
        }

        public function get_fk_evento() : int {
            return $this->fk_evento;
        }

        public function set_fk_evento(int $fk_evento){
            $this->fk_evento = $fk_evento;
        }

        public function jsonSerialize()
        {
            $vars = get_object_vars($this);
            return $vars;
        }
    }
?>