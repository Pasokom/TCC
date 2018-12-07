DELIMITER //
CREATE PROCEDURE `spRepete`(IN dtLimite DATETIME, IN uCodigo INT)
BEGIN
       DECLARE dtIni DATETIME;
       DECLARE dtFim DATETIME;
       DECLARE nCod  INT;
       DECLARE nInterv INT;
       DECLARE codRepet INT;
    
       DECLARE lErro INT DEFAULT 0;
       
       DECLARE oDados CURSOR FOR 
        SELECT COD_EVENTO, DATA_INICIO, DATA_FIM, TIPO_REPETICAO, INTERVALO
          FROM EVENTO
          LEFT JOIN E_REPETIR ON COD_EVENTO = FK_EVENTO
          WHERE TIPO_REPETICAO > 0;

       DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;

       CREATE TEMPORARY TABLE REPETICAO (RPTEVENTO INT, RPTDATA_INICIO DATETIME, RPTDATA_FIM DATETIME);
       INSERT INTO REPETICAO (RPTEVENTO, RPTDATA_INICIO, RPTDATA_FIM)
        SELECT COD_EVENTO, DATA_INICIO, DATA_FIM FROM EVENTO WHERE TIPO_REPETICAO = 0;
       
       OPEN oDados;
       MARCA_INICIO: LOOP
         FETCH oDados INTO nCod, dtIni, dtFim, codRepet, nInterv;
         IF lErro = 1 THEN
           LEAVE MARCA_INICIO;
         END IF;
         WHILE dtIni < dtLimite DO
           INSERT INTO REPETICAO (RPTEVENTO, RPTDATA_INICIO, RPTDATA_FIM) VALUES (nCod, dtIni, dtFim);
           SET dtIni = CALCULO_RECORRENCIA(dtIni, codRepet, nInterv);
           SET dtFim = CALCULO_RECORRENCIA(dtFim, codRepet, nInterv); 
         END WHILE;
       END LOOP;
    
        select COD_EVENTO, TITULO, RPTDATA_INICIO, RPTDATA_FIM, DIA_TODO, LOCAL_EVENTO, DESCRICAO,
          TIPO_REPETICAO, TIPO_FIM_REPETICAO, FK_USUARIO, COD_REPETICAO, INTERVALO, DIAS_SEMANA,
          COD_FIM_REPETICAO, DIA_FIM, QTD_RECORRENCIAS from evento 
	        left join e_repetir on evento.cod_evento = e_repetir.fk_evento
	        left join e_fim_repeticao on evento.cod_evento = e_fim_repeticao.fk_evento
          left join REPETICAO on evento.cod_evento = REPETICAO.RPTEVENTO
          WHERE FK_USUARIO = uCodigo;

       SELECT * FROM REPETICAO;
       DROP TEMPORARY TABLE REPETICAO;
     END;
//

DELIMITER //
CREATE PROCEDURE `spNowEvents`(IN uCodigo INT)
BEGIN
       DECLARE dtIni DATETIME;
       DECLARE dtFim DATETIME;
       DECLARE nCod  INT;
       DECLARE nInterv INT;
       DECLARE codRepet INT;
    
       DECLARE lErro INT DEFAULT 0;
       
       DECLARE oDados CURSOR FOR 
        SELECT COD_EVENTO, DATA_INICIO, DATA_FIM, TIPO_REPETICAO, INTERVALO
          FROM EVENTO
          LEFT JOIN E_REPETIR ON COD_EVENTO = FK_EVENTO
          WHERE TIPO_REPETICAO > 0;

       DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;

       CREATE TEMPORARY TABLE REPETICAO (RPTEVENTO INT, RPTDATA_INICIO DATETIME, RPTDATA_FIM DATETIME);
       INSERT INTO REPETICAO (RPTEVENTO, RPTDATA_INICIO, RPTDATA_FIM)
        SELECT COD_EVENTO, DATA_INICIO, DATA_FIM FROM EVENTO WHERE TIPO_REPETICAO = 0;
       
       OPEN oDados;
       MARCA_INICIO: LOOP
         FETCH oDados INTO nCod, dtIni, dtFim, codRepet, nInterv;
         IF lErro = 1 THEN
           LEAVE MARCA_INICIO;
         END IF;
         WHILE dtIni < NOW() DO
           INSERT INTO REPETICAO (RPTEVENTO, RPTDATA_INICIO, RPTDATA_FIM) VALUES (nCod, dtIni, dtFim);
           SET dtIni = CALCULO_RECORRENCIA(dtIni, codRepet, nInterv);
           SET dtFim = CALCULO_RECORRENCIA(dtFim, codRepet, nInterv); 
         END WHILE;
       END LOOP;
    
        select COD_EVENTO, TITULO, RPTDATA_INICIO, RPTDATA_FIM, DIA_TODO, LOCAL_EVENTO, DESCRICAO,
          TIPO_REPETICAO, TIPO_FIM_REPETICAO, FK_USUARIO, COD_REPETICAO, INTERVALO, DIAS_SEMANA,
          COD_FIM_REPETICAO, DIA_FIM, QTD_RECORRENCIAS from evento 
	        left join e_repetir on evento.cod_evento = e_repetir.fk_evento
	        left join e_fim_repeticao on evento.cod_evento = e_fim_repeticao.fk_evento
          left join REPETICAO on evento.cod_evento = REPETICAO.RPTEVENTO
          WHERE FK_USUARIO = uCodigo and RPTDATA_INICIO = now();

       SELECT * FROM REPETICAO;
       DROP TEMPORARY TABLE REPETICAO;
     END;
//