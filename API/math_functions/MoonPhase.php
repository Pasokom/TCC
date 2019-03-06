<?php
    include_once '../objects/Moon.php';

    class MoonPhase {

        public static function calculate($date) {

            $Y = date('Y', $date);
            $M = date('n', $date);
            $D = date('j', $date);

            if($Y < 3) {

                $Y -= 1;
                $M += 12;
            }

            /* Calculando 'Julian date' apartir da data */
            $A = (int)($Y / 100);
            $B = (int)($A / 4);
            $C = 2 - $A + $B;
            $E = (int)(365.25 * ($Y + 4716));
            $F = 30.6001 * ($M + 1);
            $JD = $C + $D + $E + $F - 1524.5;

            $days_since_new = $JD - 2451549.5;
            $new_moons = $days_since_new / 29.53;

            $days_into_cycle = ($new_moons - floor($new_moons)) * 29.53;

            $moon = new Moon();

            if($days_into_cycle < 7){
                $moon->set_phase(1);
                $moon->set_description('Lua nova');
            }
            else {
                if($days_into_cycle < 15){
                    $moon->set_phase(2);
                    $moon->set_description('Quarto crescente');
                }
                else {
                    if($days_into_cycle < 22){
                        $moon->set_phase(3);
                        $moon->set_description('Lua cheia');
                    }
                    else {
                        if($days_into_cycle < 29.5){
                            $moon->set_phase(4);
                            $moon->set_description('Quarto minguante');
                        }
                    }
                }
            }

            return $moon;
        }
    }
?>