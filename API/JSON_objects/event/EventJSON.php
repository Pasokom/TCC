<?php

    include_once "EventSchedule.php";
    include_once "EventEndSchedule.php";

    class EventJSON implements \JsonSerializable {

        private $type = "event";
        private $cod_evento;
        private $titulo;
        private $data_inicio;
        private $data_fim;
        private $dia_todo;
        private $local_evento;
        private $descricao;
        private $concluido;
        private $excluido;
        private $tipo_repeticao;
        private $tipo_fim_repeticao;
        private $fk_usuario;
        private $horario_evento;
        private $horario_fim_evento;

        public function get_cod_evento() : int { 
            return $this->cod_evento;
        }

        public function set_cod_evento(int $cod_evento){
            $this->cod_evento = $cod_evento;
        }

        public function get_titulo() : string {
            return $this->titulo;
        }

        public function set_titulo(string $titulo){
            $this->titulo = $titulo;
        }

        public function get_data_inicio() : int {
            return $this->data_inicio;
        }

        public function set_data_inicio(int $data_inicio){
            $this->data_inicio = $data_inicio;
        }
        
        public function get_data_fim() : int {
            return $this->data_fim;
        }

        public function set_data_fim(int $data_fim){
            $this->data_fim = $data_fim;
        }

        public function get_dia_todo() : bool {
            return $this->dia_todo;
        }

        public function set_dia_todo(bool $dia_todo){
            $this->dia_todo = $dia_todo;
        }

        public function get_local_evento() : string {
            return $this->local_evento;
        }

        public function set_local_evento(string $local_evento){
            $this->local_evento = $local_evento;
        }

        public function get_descricao() : string {
            return $this->descricao;
        }

        public function set_descricao(string $descricao){
            $this->descricao = $descricao;
        }

        public function is_concluido() : bool {
            return $this->concluido;
        }

        public function set_concluido(bool $concluido) {
            $this->concluido = $concluido;
        }

        public function is_excluido() : bool {
            return $this->excluido;
        }

        public function set_excluido(bool $excluido) {
            $this->excluido = $excluido;
        }

        public function get_tipo_repeticao() : int {
            return $this->tipo_repeticao;
        }

        public function set_tipo_repeticao(int $tipo_repeticao){
            $this->tipo_repeticao = $tipo_repeticao;
        }

        public function get_tipo_fim_repeticao() : int {
            return $this->tipo_fim_repeticao;
        }

        public function set_tipo_fim_repeticao(int $tipo_fim_repeticao){
            $this->tipo_fim_repeticao = $tipo_fim_repeticao;
        }

        public function get_fk_usuario() : int {
            return $this->fk_usuario;
        }

        public function set_fk_usuario(int $fk_usuario){
            $this->fk_usuario = $fk_usuario;
        }

        public function get_horario_evento() : EventSchedule {
            return $this->horario_evento;
        }

        public function set_horario_evento(EventSchedule $horario_evento){
            $this->horario_evento = $horario_evento;
        }

        public function get_horario_fim_evento() : EventEndSchedule {
            return $this->horario_fim_evento;
        }

        public function set_horario_fim_evento(EventEndSchedule $horario_fim_evento){
            $this->horario_fim_evento = $horario_fim_evento;
        }

        /**
         * retorna o intervalo de cada repetição
         */
        public function get_interval(int $coefficient = 1) : DateInterval {

            $_intervalo = $this->horario_evento->get_intervalo();

            switch ($this->tipo_repeticao) {
                case 1:
                    $_tipo = "D"; # diario
                    break;
                case 2:
                    $_tipo = "W"; # semanal
                    break;
                case 3:
                    $_tipo = "M"; # mensal
                    break;
                case 4:
                    $_tipo = "Y"; # anual
                    break;
                default:
                    $_tipo = "D";
                    break;
            }

            return new DateInterval("P" . $_intervalo * $coefficient . $_tipo);
        }

        /**
         * retorna o ultimo dia de repetição.
         * caso a repetição não termine nunca retorna o dia após 30 repetições
         */
        public function get_data_fim_repeticao() : DateTime {

            $_dia = $this->horario_fim_evento->get_dia_fim();
            $_qtd = $this->horario_fim_evento->get_qtd_recorrencias();

            $dt_inicio = new DateTime();
            $dt_inicio->setTimestamp($this->data_inicio);

            switch ($this->tipo_fim_repeticao) {
                case 0:
                    # nunca
                    return $dt_inicio->add($this->get_interval(30));
                case 1:
                    # dia definido
                    $dt_fim = new DateTime();
                    $dt_fim->setTimestamp($_dia);
                    return $dt_fim;
                case 2:
                    # qtd recorrencias
                    return $dt_inicio->add($this->get_interval($_qtd));
                default:
                    echo "fim de repeticao não conhecido";
                    break;
            }
        }

        public function jsonSerialize()
        {
            $vars = get_object_vars($this);
            return $vars;
        }
    }
?>