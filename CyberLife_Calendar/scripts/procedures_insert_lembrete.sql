DELIMITER // 
CREATE PROCEDURE ADICIONAR_LEMBRETE (IN LEMBRETE TEXT, IN DIA_TODO BIT, IN STATUS VARCHAR(20), IN TIPO_RECORRENCIA INT , OUT CODIGO_LEMBRETE INT,IN USUARIO INT) 
 BEGIN 
	 IF DIA_TODO IS NULL THEN 
            SET DIA_TODO = FALSE;
   	END IF; 
       INSERT INTO LEMBRETE (LEMBRETE, LDIA_TODO, L_STATUS, LRECORRENCIA_TIPO ,FK_USUARIO) 
  		 VALUES
          (LEMBRETE, DIA_TODO, STATUS, TIPO_RECORRENCIA ,USUARIO);
         
       SET CODIGO_LEMBRETE = LAST_INSERT_ID();
     END 
//

DELIMITER // 
CREATE PROCEDURE HORARIO_SEM_RECORRENCIA (IN DATA_INICIO DATETIME, IN  DATA_FIM DATETIME,  IN INTERVALO INT, IN CODIGO_LEMBRETE INT)
BEGIN 
	IF INTERVALO = 0 THEN 
	  	INSERT INTO HORARIO_LEMBRETE (HL_HORARIO_INICIO, HL_HORARIO_FIM , FK_LEMBRETE) VALUES (DATA_INICIO,DATA_FIM, CODIGO_LEMBRETE);
	ELSE 
			INSERT INTO HORARIO_LEMBRETE (HL_HORARIO_INICIO, HL_HORARIO_FIM , HL_INTERVALO_MINUTOS, FK_LEMBRETE) VALUES (DATA_INICIO,DATA_FIM,INTERVALO, CODIGO_LEMBRETE);
	END IF;
END //


DELIMITER // 
CREATE PROCEDURE HORARIO_RECORRENCIA_SEM_FIM ( IN DATA_INICIO DATETIME, IN DATA_FIM  DATETIME, IN INTERVALO INT, IN RECORRENCIA INT, IN DIA_SEMANA INT, IN CODIGO_LEMBRETE INT)
BEGIN 
	IF INTERVALO = 0 THEN 
		INSERT INTO HORARIO_LEMBRETE (HL_HORARIO_INICIO, HL_RECORRENCIA, HL_SEMANA_DIA, FK_LEMBRETE) VALUES 
		(DATA_INICIO, RECORRENCIA, DIA_SEMANA,CODIGO_LEMBRETE);
	ELSE 
		INSERT INTO HORARIO_LEMBRETE(HL_HORARIO_INICIO, HL_HORARIO_FIM, HL_INTERVALO_MINUTOS, HL_RECORRENCIA, HL_SEMANA_DIA, FK_LEMBRETE) VALUES
		(DATA_INICIO, DATA_FIM, INTERVALO, RECORRENCIA,DIA_SEMANA,CODIGO_LEMBRETE);
	END IF;
END
//