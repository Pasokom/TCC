<?php
    class Moon implements \JsonSerializable
    {
        private
            $phase,
            $description;

        public function get_phase(){
            return $this->phase;
        }

        public function set_phase($phase){
            $this->phase = $phase;
        }

        public function get_description(){
            return $this->description;
        }

        public function set_description($description){
            $this->description = $description;
        }

        public function jsonSerialize() {

            $vars = get_object_vars($this);
            return $vars;
        }
    }
?>