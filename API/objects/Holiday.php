<?php 
    class Holiday implements \JsonSerializable{
        
        private $type = "holiday";
        private $cod_feriado;
        private $nome;
        private $tipo_data;
        private $dia;
        private $dia_semana;
        private $semana;
        private $mes;
        private $data;

        public function get_type(){
            return $this->type;
        }

        public function get_cod_feriado(){
            return $this->cod_feriado;
        }

        public function set_cod_feriado($cod_feriado){
            $this->cod_feriado = $cod_feriado;
        }

        public function get_nome(){
            return $this->nome;
        }

        public function set_nome($nome){
            $this->nome = $nome;
        }

        public function get_tipo_data(){
            return $this->tipo_data;
        }

        public function set_tipo_data($tipo_data){
            $this->tipo_data = $tipo_data;
        }

        public function get_dia(){
            return $this->dia;
        }

        public function set_dia($dia){
            $this->dia = $dia;
        }

        public function get_dia_semana(){
            return $this->dia_semana;
        }

        public function set_dia_semana($dia_semana){
            $this->dia_semana = $dia_semana;
        }

        public function get_semana(){
            return $this->semana;
        }

        public function set_semana($semana){
            $this->semana = $semana;
        }

        public function get_mes(){
            return $this->mes;
        }

        public function set_mes($mes){
            $this->mes = $mes;
        }

        public function get_data(){
            return $this->data;
        }

        public function set_data($data){
            $this->data = $data;
        }

        public function get_date_in_year() : DateTime {

            $today = new DateTime;
            $day = 1;

            switch ($this->tipo_data) {
                case 0:
                    # dia fixo
                    $year = $today->format("y");
                    $date = new DateTime("$year-$this->mes-$this->dia");
                    break;
                case 1:
                    # dia da semana fixo
                    $year = $today->format("y");

                    $week = new DateTime("$year-$this->mes-1");
                    $week = $week->format("w");

                    if($week > $this->dia_semana)
                        $day += (7 * $this->semana) + ($this->dia_semana - $week);
                    else
                        $day += (7 * ($this->semana - 1)) + ($this->dia_semana - $week); 

                    $date = new DateTime("$year-$this->mes-$day");
                    break;
                case 2:
                    # dia móvel
                    $year = $today->format("y");

                    $easter = new DateTime;
                    $easter->setTimestamp(easter_date());

                    if($this->dia >= 0)
                        $date = $easter->add(new DateInterval("P".$this->dia."D"));
                    else
                        $date = $easter->sub(new DateInterval("P".substr($this->dia, 1)."D"));
                    break;
                default:
                    $date = new DateTime();
                    break;
            }

            return $date;
        }

        public function jsonSerialize()
        {
            return [
                'type' => $this->type,
                'name' => $this->nome,
                'day' => date('j', $this->data),
                'date' => $this->data 
            ];
        }
    }
?>