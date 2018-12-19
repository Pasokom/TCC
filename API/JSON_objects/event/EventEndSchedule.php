<?php
    class EventEndSchedule implements \JsonSerializable {

        private $cod_fim_repeticao;
        private $dia_fim;
        private $qtd_recorrencias;
        private $fk_evento;
    
        public function get_cod_fim_repeticao() : int {
            return $this->cod_fim_repeticao;
        }

        public function set_cod_fim_repeticao(int $cod_fim_repeticao){
            $this->cod_fim_repeticao = $cod_fim_repeticao;
        }

        public function get_dia_fim() : int {
            return $this->dia_fim;
        }

        public function set_dia_fim(int $dia_fim){
            $this->dia_fim = $dia_fim;
        }

        public function get_qtd_recorrencias() : int {
            return $this->qtd_recorrencias;
        }

        public function set_qtd_recorrencias(int $qtd_recorrencias){
            $this->qtd_recorrencias = $qtd_recorrencias;
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