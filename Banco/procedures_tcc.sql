DELIMITER // 
CREATE PROCEDURE EMAIL_EXISTE(IN UEMAIL VARCHAR(255), OUT RESULT BIT)
BEGIN 
	DECLARE NUMERO_REGISTROS INT;  
	SELECT COUNT(0) INTO NUMERO_REGISTROS FROM USUARIO WHERE EMAIL = UEMAIL;        
           
    IF NUMERO_REGISTROS > 0 THEN               
        SET RESULT = 1;        
    ELSE                 
        SET RESULT = 0;        
	END IF;    
END 
//

DELIMITER // 
-- DROP PROCEDURE IF EXISTS ADICIONAR_USUARIO;
CREATE PROCEDURE ADICIONAR_USUARIO(IN UEMAIL VARCHAR(255), IN UNOME VARCHAR(255),             
	IN USOBRENOME VARCHAR(255), IN USENHA VARCHAR(255))
BEGIN                                                                                  
	IF USOBRENOME IS NULL OR USOBRENOME = '' THEN                                      
		INSERT INTO USUARIO (EMAIL,NOME,SENHA) VALUES (UEMAIL, UNOME, md5(USENHA));         
	ELSE                                                                             
		INSERT INTO USUARIO (EMAIL,NOME,SOBRENOME,SENHA) VALUES (UEMAIL,UNOME,USOBRENOME,md5(USENHA));                                  
	END IF;                                                                         
END 
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGAR_LEMBRETE;
CREATE PROCEDURE CARREGAR_LEMBRETE(IN COD INT)
BEGIN
	SELECT * FROM LEMBRETE
    INNER JOIN LEMBRETE_REPETICAO ON LEMBRETE_REPETICAO.FK_LEMBRETE = COD_LEMBRETE
    INNER JOIN LEMBRETE_FIM_REPETICAO ON LEMBRETE_FIM_REPETICAO.FK_LEMBRETE = COD_LEMBRETE
    WHERE COD_LEMBRETE = COD;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGAR_EVENTO;
CREATE PROCEDURE CARREGAR_EVENTO(IN COD INT)
BEGIN
	SELECT * FROM EVENTO
    INNER JOIN EVENTO_REPETICAO ON EVENTO_REPETICAO.FK_EVENTO = COD_EVENTO
    INNER JOIN EVENTO_FIM_REPETICAO ON EVENTO_FIM_REPETICAO.FK_EVENTO = COD_EVENTO
    WHERE COD_EVENTO = COD;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS MARCAR_EVENTOS;
CREATE PROCEDURE MARCAR_EVENTOS()
BEGIN
	DECLARE dtIni DATETIME;
    DECLARE dtFim DATETIME;
    DECLARE eCod INT;
    DECLARE tRepeticao INT;
    DECLARE nInterval INT;
    DECLARE tSemana VARCHAR(15);
    
    DECLARE aDtIni DATETIME; -- guarda a data inicial antes de qualquer alteração
    
    DECLARE dtLimite DATETIME;
    DECLARE dtUltima DATETIME;

	DECLARE lErro INT DEFAULT 0;

	DECLARE oDados CURSOR FOR
		SELECT DATA_INICIO, DATA_FIM, COD_EVENTO, TIPO_REPETICAO, INTERVALO, DIAS_SEMANA FROM EVENTO 
        LEFT JOIN EVENTO_REPETICAO ON FK_EVENTO = COD_EVENTO
        ;
        
	-- lErro fica igual a 1 quando acabam os registros
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;
        
	OPEN oDados;
    
    get_evento : LOOP
		FETCH oDados INTO dtIni, dtFim, eCod, tRepeticao, nInterval, tSemana;
        IF lErro = 1 THEN
			LEAVE get_evento;
        END IF;

		SET aDtIni = dtIni;

        CALL CALCULO_DATA_LIMITE(2, eCod, @dt_limite);
        SELECT @dt_limite INTO dtLimite;

        SET dtUltima = ULTIMA_RECORRENCIA(2, eCod);

        IF dtUltima IS NULL THEN
			IF tRepeticao != 2 THEN
				INSERT INTO EVENTO_RECORRENCIA (DATA_INICIO, DATA_FIM, FK_EVENTO) VALUES (dtIni, dtFim, eCod);
			ELSE
				CALL INSERT_SEMANA(2, dtIni, dtFim, eCod, tSemana);
			END IF;
            SET dtUltima = dtIni; 
        END IF;

        SET dtIni = dtUltima;
        SET dtFim = DATE_ADD(dtIni, INTERVAL DATEDIFF(dtFim, aDtIni) DAY);

        WHILE dtIni < dtLimite DO
            SET dtIni = CALCULO_RECORRENCIA(dtIni, nInterval, tRepeticao);
            SET dtFim = CALCULO_RECORRENCIA(dtFim, nInterval, tRepeticao);
            
            IF tRepeticao != 2 THEN
				INSERT INTO EVENTO_RECORRENCIA (DATA_INICIO, DATA_FIM, FK_EVENTO) VALUES (dtIni, dtFim, eCod);
			ELSE
				CALL INSERT_SEMANA(2, dtIni, dtFim, eCod, tSemana);
			END IF;
		END WHILE;
	END LOOP get_evento;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS MARCAR_LEMBRETES;
CREATE PROCEDURE MARCAR_LEMBRETES()
BEGIN
	DECLARE dtIni DATETIME;
    DECLARE dtFim DATETIME;
    DECLARE tMinutos INT;
    DECLARE eCod INT;
    DECLARE tRepeticao INT;
    DECLARE nInterval INT;
    DECLARE tSemana VARCHAR(15);
    
    DECLARE dtLimite DATETIME;
    DECLARE dtUltima DATETIME;

	DECLARE lErro INT DEFAULT 0;

	DECLARE oDados CURSOR FOR
		SELECT HORARIO, HORARIO_FIM, INTERVALO_MINUTOS, COD_LEMBRETE, TIPO_REPETICAO, 
			INTERVALO, DIAS_SEMANA FROM LEMBRETE 
        LEFT JOIN LEMBRETE_REPETICAO ON FK_LEMBRETE = COD_LEMBRETE
        WHERE ATIVO = 1;
        
	-- lErro fica igual a 1 quando acabam os registros
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;
        
	OPEN oDados;
    
    get_evento : LOOP
		FETCH oDados INTO dtIni, dtFim, tMinutos, eCod, tRepeticao, nInterval, tSemana;
        IF lErro = 1 THEN
			LEAVE get_evento;
        END IF;

        CALL CALCULO_DATA_LIMITE(1, eCod, @dt_limite);
        SELECT @dt_limite INTO dtLimite;

        SET dtUltima = ULTIMA_RECORRENCIA(1, eCod);

        IF dtUltima IS NULL THEN
			IF tRepeticao != 2 THEN
				IF tMinutos = 0 THEN
					INSERT INTO LEMBRETE_RECORRENCIA (DATA_RECORRENCIA, FK_LEMBRETE) VALUES (dtIni, eCod);
				ELSE
					CALL INSERT_HORARIOS(dtIni, dtFim, tMinutos, eCod);
                END IF;
			ELSE
				CALL INSERT_SEMANA(1, dtIni, null, eCod, tSemana);
			END IF;
            SET dtUltima = dtIni; 
        END IF;

        SET dtIni = dtUltima;

        WHILE dtIni < dtLimite DO
            SET dtIni = CALCULO_RECORRENCIA(dtIni, nInterval, tRepeticao);
            SET dtFim = CALCULO_RECORRENCIA(dtFim, nInterval, tRepeticao);
            
            IF tRepeticao != 2 THEN
				IF tMinutos = 0 THEN
					INSERT INTO LEMBRETE_RECORRENCIA (DATA_RECORRENCIA, FK_LEMBRETE) VALUES (dtIni, eCod);
				ELSE
					CALL INSERT_HORARIOS(dtIni, dtFim, tMinutos, eCod);
                END IF;
			ELSE
				CALL INSERT_SEMANA(1, dtIni, null, eCod, tSemana);
			END IF;
		END WHILE;
	END LOOP get_evento;
END
//

DELIMITER //
-- DROP PROCEDURE MARCAR_METAS;
CREATE PROCEDURE MARCAR_METAS()
BEGIN
	DECLARE mCod INT;
    DECLARE lErro INT DEFAULT 0;
    
    DECLARE oMetas CURSOR FOR
		SELECT COD_META FROM META;
        
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;
    
    OPEN oMetas;
    
    getMetas : LOOP
		FETCH oMetas INTO mCod;
        IF lErro = 1 THEN
			LEAVE getMetas;
        END IF;
        IF NOT EXISTS (SELECT * FROM META_SEMANA WHERE FK_META = mCod AND INICIO_SEMANA <= NOW() AND FIM_SEMANA >= NOW()) THEN
			CALL CRIAR_SEMANA_META(mCod);
        END IF;
	END LOOP getMetas;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CALCULO_DATA_LIMITE;
CREATE PROCEDURE CALCULO_DATA_LIMITE(IN TIPO INT, IN COD INT, OUT DATA_LIMITE DATETIME)
BEGIN

	DECLARE _DATA_INICIO DATETIME;
    DECLARE _TIPO_REPETICAO INT;
    DECLARE _TIPO_FIM INT;
    DECLARE _INTERVALO INT;
    DECLARE _DIA_FIM DATETIME;
    DECLARE _QTD_RECORRENCIAS INT;
    
    DECLARE _DT_LIMITE DATETIME;
    DECLARE _DT_LIMITE_QTD DATETIME;
    
    IF TIPO = 1 THEN
		SELECT
			HORARIO, TIPO_REPETICAO, TIPO_FIM_REPETICAO, INTERVALO, DIA_FIM, QTD_RECORRENCIAS
		INTO
			_DATA_INICIO, _TIPO_REPETICAO, _TIPO_FIM, _INTERVALO, _DIA_FIM, _QTD_RECORRENCIAS
		FROM 
			LEMBRETE 
			LEFT JOIN LEMBRETE_REPETICAO ON LEMBRETE_REPETICAO.FK_LEMBRETE = COD_LEMBRETE
			LEFT JOIN LEMBRETE_FIM_REPETICAO ON LEMBRETE_FIM_REPETICAO.FK_LEMBRETE = COD_LEMBRETE
			WHERE COD_LEMBRETE = COD;
    END IF;
    
    IF TIPO = 2 THEN
		SELECT
			DATA_INICIO, TIPO_REPETICAO, TIPO_FIM_REPETICAO, INTERVALO, DIA_FIM, QTD_RECORRENCIAS
		INTO
			_DATA_INICIO, _TIPO_REPETICAO, _TIPO_FIM, _INTERVALO, _DIA_FIM, _QTD_RECORRENCIAS
		FROM 
			EVENTO 
			LEFT JOIN EVENTO_REPETICAO ON EVENTO_REPETICAO.FK_EVENTO = COD_EVENTO
			LEFT JOIN EVENTO_FIM_REPETICAO ON EVENTO_FIM_REPETICAO.FK_EVENTO = COD_EVENTO
			WHERE COD_EVENTO = COD;
    END IF;

    IF _TIPO_REPETICAO = 0 THEN
        SET DATA_LIMITE = _DATA_INICIO;
    ELSE
    
        SET _DT_LIMITE = CALCULO_RECORRENCIA(TIMESTAMP(DATE(NOW()), TIME(_DATA_INICIO)), 
			_INTERVALO * 10, _TIPO_REPETICAO);

        IF _TIPO_FIM = 0 THEN
            SET DATA_LIMITE = _DT_LIMITE;
        END IF;

        IF _TIPO_FIM = 1 THEN
            IF _DT_LIMITE < _DIA_FIM THEN
                SET DATA_LIMITE = _DT_LIMITE;
            ELSE
                SET DATA_LIMITE = _DIA_FIM;
            END IF;
        END IF;

        IF _TIPO_FIM = 2 THEN

            SET _DT_LIMITE_QTD = CALCULO_RECORRENCIA(_DATA_INICIO, _INTERVALO * _QTD_RECORRENCIAS, _TIPO_REPETICAO);

            IF _DT_LIMITE < _DT_LIMITE_QTD THEN
                SET DATA_LIMITE = _DT_LIMITE;
            ELSE 
                SET DATA_LIMITE = _DT_LIMITE_QTD;
            END IF;
        END IF;
    END IF;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS INSERT_SEMANA;
CREATE PROCEDURE INSERT_SEMANA(IN TIPO INT, IN DATA_INICIO DATETIME, IN DATA_FIM DATETIME, IN COD INT, IN SEMANA VARCHAR(15))
BEGIN

    DECLARE P_SEMANA_INICIO DATETIME;
    DECLARE P_SEMANA_FIM DATETIME;
    DECLARE A_DATA_INICIO DATETIME;
    DECLARE A_DATA_FIM DATETIME;

    DECLARE N_CONT INT DEFAULT 1;
    DECLARE A_DIA_SEMANA VARCHAR(1); 

    SELECT 
        DATE_SUB(DATA_INICIO, INTERVAL (DAYOFWEEK(DATA_INICIO) - 1)  DAY), 
        DATE_SUB(DATA_FIM, INTERVAL (DAYOFWEEK(DATA_FIM) - 1) DAY)
    INTO 
        P_SEMANA_INICIO, P_SEMANA_FIM;

    WHILE N_CONT <= 7 DO
        
        SET A_DIA_SEMANA = SUBSTRING_INDEX(SUBSTRING_INDEX(SEMANA, ',', N_CONT), ',', -1);

        IF A_DIA_SEMANA = 1 THEN

			SET A_DATA_INICIO = DATE_ADD(P_SEMANA_INICIO, INTERVAL (N_CONT - 1) DAY);
            SET A_DATA_FIM = DATE_ADD(P_SEMANA_FIM, INTERVAL (N_CONT - 1) DAY);

			IF A_DATA_INICIO < DATA_INICIO THEN
				SET A_DATA_INICIO = DATE_ADD(A_DATA_INICIO, INTERVAL 1 WEEK);
            END IF;
            
            IF A_DATA_FIM < DATA_FIM THEN
				SET A_DATA_FIM = DATE_ADD(A_DATA_FIM, INTERVAL 1 WEEK);
            END IF;

			IF TIPO = 1 THEN
				INSERT INTO 
					LEMBRETE_RECORRENCIA (DATA_RECORRENCIA, FK_LEMBRETE) 
				VALUES (
					A_DATA_INICIO, COD);
            END IF;
            IF TIPO = 2 THEN
				INSERT INTO 
					EVENTO_RECORRENCIA (DATA_INICIO, DATA_FIM, FK_EVENTO) 
				VALUES (
					A_DATA_INICIO, A_DATA_FIM, COD);
            END IF;

        END IF;

        SET N_CONT = N_CONT + 1;
    END WHILE;
END 
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS INSERT_HORARIOS;
CREATE PROCEDURE INSERT_HORARIOS(IN DATA_INICIO DATETIME, IN DATA_FIM DATETIME, IN INTERVALO INT, IN COD INT)
BEGIN
	WHILE DATA_INICIO <= DATA_FIM DO
    
		INSERT INTO LEMBRETE_RECORRENCIA (DATA_RECORRENCIA, DATA_FIM, FK_LEMBRETE)
		VALUES (DATA_INICIO, DATA_FIM, COD);
        
		SET DATA_INICIO = DATE_ADD(DATA_INICIO, INTERVAL INTERVALO MINUTE);
    END WHILE;
END
//

DELIMITER //
-- DROP PROCEDURE CRIAR_SEMANA_META;
CREATE PROCEDURE CRIAR_SEMANA_META(IN COD_META INT)
BEGIN

	DECLARE dtInicio DATETIME;
    DECLARE dtFim DATETIME;
    
    SET dtInicio = DATE_SUB(DATE(NOW()), INTERVAL (DAYOFWEEK(NOW()) - 1) DAY);
    SET dtFim = TIMESTAMP(DATE_ADD(DATE(NOW()), INTERVAL (7 - DAYOFWEEK(NOW())) DAY), '23:59:59');
    
	INSERT INTO META_SEMANA(INICIO_SEMANA, FIM_SEMANA, FK_META) VALUES (dtInicio, dtFim, COD_META);
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGAR_MES;
CREATE PROCEDURE CARREGAR_MES(IN DATA_MES DATETIME, IN USUARIO INT)
BEGIN
	CALL HORARIO_TAREFAS(USUARIO);

	SELECT
		1 TIPO, DAY(DATA_RECORRENCIA) DIA, COD_RECORRENCIA, TITULO, DATA_RECORRENCIA DATA_INICIO, NULL DATA_FIM, 
		CONCLUIDO, FK_LEMBRETE CODIGO, FK_USUARIO
    FROM 
		LEMBRETE_RECORRENCIA 
	INNER JOIN
		LEMBRETE ON COD_LEMBRETE = FK_LEMBRETE
	WHERE 
		MONTH(DATA_RECORRENCIA) = MONTH(DATA_MES) AND YEAR(DATA_RECORRENCIA) = YEAR(DATA_MES) AND EXCLUIDO = FALSE AND FK_USUARIO = USUARIO
    UNION SELECT 
		2 TIPO, DAY(EVENTO_RECORRENCIA.DATA_INICIO), COD_RECORRENCIA, TITULO, EVENTO_RECORRENCIA.DATA_INICIO, 
        EVENTO_RECORRENCIA.DATA_FIM, NULL, FK_EVENTO, FK_USUARIO 
	FROM 
		EVENTO_RECORRENCIA 
	INNER JOIN 
		EVENTO ON COD_EVENTO = FK_EVENTO
	WHERE 
		MONTH(EVENTO_RECORRENCIA.DATA_INICIO) = MONTH(DATA_MES) AND 
        YEAR(EVENTO_RECORRENCIA.DATA_INICIO) = YEAR(DATA_MES) AND EXCLUIDO = FALSE AND FK_USUARIO = USUARIO
	UNION SELECT
		TIPO, NULL, NOME_TAREFA, DATA_INICIO, DATA_FIM, FALSE, CONCLUIDO, FK_TAREFA, TEMP_TAREFAS.FK_USUARIO
	FROM
		TEMP_TAREFAS
	INNER JOIN
		TAREFA ON COD_TAREFA = FK_TAREFA
	WHERE
		MONTH(TEMP_TAREFAS.DATA_INICIO) = MONTH(DATA_MES) AND 
        YEAR(TEMP_TAREFAS.DATA_INICIO) = YEAR(DATA_MES) AND CONCLUIDO = FALSE AND TEMP_TAREFAS.FK_USUARIO = USUARIO
	ORDER BY DATA_INICIO;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGAR_DIA;
CREATE PROCEDURE CARREGAR_DIA(IN DIA DATETIME, IN USUARIO INT)
BEGIN

	CALL HORARIO_TAREFAS(USUARIO);

	SELECT
		1 TIPO, COD_RECORRENCIA, TITULO, DATA_RECORRENCIA DATA_INICIO, DATA_FIM, DIA_TODO, CONCLUIDO, FK_LEMBRETE CODIGO, FK_USUARIO
    FROM 
		LEMBRETE_RECORRENCIA 
	INNER JOIN
		LEMBRETE ON COD_LEMBRETE = FK_LEMBRETE
	WHERE 
		DATE(DATA_RECORRENCIA) = DATE(DIA) AND EXCLUIDO = FALSE AND FK_USUARIO = USUARIO
    UNION SELECT 
		2 TIPO, COD_RECORRENCIA, TITULO, EVENTO_RECORRENCIA.DATA_INICIO, EVENTO_RECORRENCIA.DATA_FIM, DIA_TODO, NULL, FK_EVENTO, FK_USUARIO 
	FROM 
		EVENTO_RECORRENCIA 
	INNER JOIN 
		EVENTO ON COD_EVENTO = FK_EVENTO
	WHERE 
		DATE(EVENTO_RECORRENCIA.DATA_INICIO) = DATE(DIA) AND EXCLUIDO = FALSE AND FK_USUARIO = USUARIO
	UNION SELECT
		TIPO, NULL, NOME_TAREFA, DATA_INICIO, DATA_FIM, FALSE, CONCLUIDO, FK_TAREFA, TEMP_TAREFAS.FK_USUARIO
	FROM
		TEMP_TAREFAS
	INNER JOIN
		TAREFA ON COD_TAREFA = FK_TAREFA
	WHERE
		TEMP_TAREFAS.FK_USUARIO = USUARIO AND CONCLUIDO = FALSE AND DATE(DATA_INICIO) = DATE(DIA)
	UNION SELECT
		3, NULL, NOME_TAREFA, TIMESTAMP(DATE(NOW()), '23:59:59'), '', FALSE, CONCLUIDO, COD_TAREFA, FK_USUARIO
	FROM
		TAREFA
	WHERE
		FK_USUARIO = USUARIO AND DATE(DATA_CONCLUIDO) = DATE(DIA) AND CONCLUIDO = TRUE
	ORDER BY DATA_INICIO;
    
    DROP TABLE IF EXISTS TEMP_TAREFAS;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS DELETAR_LEMBRETE;
CREATE PROCEDURE DELETAR_LEMBRETE(IN COD INT, IN TODOS BIT)
BEGIN

	DECLARE LEMBRETE INT;

	SELECT FK_LEMBRETE INTO LEMBRETE FROM LEMBRETE_RECORRENCIA WHERE COD_RECORRENCIA = COD;

	IF TODOS = TRUE THEN
		UPDATE LEMBRETE_RECORRENCIA SET EXCLUIDO = TRUE WHERE COD_RECORRENCIA = COD;
		UPDATE LEMBRETE_RECORRENCIA SET EXCLUIDO = TRUE WHERE FK_LEMBRETE = LEMBRETE AND DATA_RECORRENCIA > NOW();
    ELSE
		UPDATE LEMBRETE_RECORRENCIA SET EXCLUIDO = TRUE WHERE COD_RECORRENCIA = COD;
    END IF;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS DELETAR_EVENTO;
CREATE PROCEDURE DELETAR_EVENTO(IN COD INT, IN TODOS BIT)
BEGIN

	DECLARE EVENTO INT;

	SELECT FK_EVENTO INTO EVENTO FROM EVENTO_RECORRENCIA WHERE COD_RECORRENCIA = COD;

	IF TODOS = TRUE THEN
		UPDATE EVENTO_RECORRENCIA SET EXCLUIDO = TRUE WHERE COD_RECORRENCIA = COD;
		UPDATE EVENTO_RECORRENCIA SET EXCLUIDO = TRUE WHERE FK_EVENTO = EVENTO AND DATA_INICIO > NOW();
    ELSE
		UPDATE EVENTO_RECORRENCIA SET EXCLUIDO = TRUE WHERE COD_RECORRENCIA = COD;
    END IF;
END
//

DELIMITER //
CREATE PROCEDURE LEMBRETE_CONCLUIDO(IN COD INT)
BEGIN
	UPDATE LEMBRETE_RECORRENCIA SET CONCLUIDO = TRUE WHERE COD_RECORRENCIA = COD;
END
//

DELIMITER //
CREATE PROCEDURE TAREFA_CONCLUIDA(IN COD INT)
BEGIN
	UPDATE TAREFA SET CONCLUIDO = TRUE WHERE COD_TAREFA = COD;
END
//

DELIMITER //
CREATE PROCEDURE TAREFA_NAO_CONCLUIDA(IN COD INT)
BEGIN
	UPDATE TAREFA SET CONCLUIDO = FALSE WHERE COD_TAREFA = COD;
END
//

DELIMITER //
-- DROP PROCEDURE ATUALIZA_SEMANA_META;
CREATE PROCEDURE ATUALIZA_SEMANA_META(IN COD INT, IN VAL_QTD INT)
BEGIN
	DECLARE valor_max INT;
    DECLARE valor INT;
    
    SELECT QTD_SEMANA INTO valor_max FROM META_SEMANA INNER JOIN META ON COD_META = FK_META WHERE COD_SEMANA = COD;
    
    IF VAL_QTD < 0 THEN
		SET valor = 0;
	ELSEIF VAL_QTD > valor_max THEN
		SET valor = valor_max;
	ELSE
		SELECT VAL_QTD;
		SET valor = VAL_QTD;
	END IF;
    
    UPDATE META_SEMANA SET QTD_CONCLUIDO = valor WHERE COD_SEMANA = COD;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS TAREFA_ATUAL;
CREATE PROCEDURE TAREFA_ATUAL(IN COD_PROJETO INT, IN USUARIO INT)
BEGIN
	CALL HORARIO_TAREFAS(USUARIO);
	SELECT COD_TAREFA, DATA_INICIO, DATA_FIM, NOME_TAREFA, FK_PROJETO FROM TEMP_TAREFAS INNER JOIN TAREFA ON COD_TAREFA = FK_TAREFA WHERE FK_PROJETO = COD_PROJETO AND CONCLUIDO = 0 ORDER BY DATA_INICIO LIMIT 1; 
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS HORARIO_TAREFAS;
CREATE PROCEDURE HORARIO_TAREFAS(IN USUARIO INT)
BEGIN
	DECLARE tCod INT;
    DECLARE tDuracao INT;
    DECLARE tImportancia INT;
    DECLARE lErro INT DEFAULT 0;

	DECLARE DATA_INICIO DATETIME;
    DECLARE DATA_FIM DATETIME;

	DECLARE oTarefas CURSOR FOR 
		SELECT COD_TAREFA COD, DURACAO_MINUTOS, (SELECT 1 + IFNULL(SUM(IMPORTANCIA), 0) IMPORTANCIA FROM TAREFA WHERE DEPENDENCIA = COD) IMPORTANCIA FROM TAREFA 
        INNER JOIN PROJETO ON FK_PROJETO = COD_PROJETO 
        WHERE TAREFA.FK_USUARIO = USUARIO AND CONCLUIDO = FALSE ORDER BY IMPORTANCIA DESC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET lErro = 1;
    
    DROP TEMPORARY TABLE IF EXISTS TEMP_TAREFAS;
    CREATE TEMPORARY TABLE TEMP_TAREFAS (
		TIPO INT DEFAULT 3,
        DATA_INICIO DATETIME,
        DATA_FIM DATETIME,
        FK_TAREFA INT,
        FK_USUARIO INT
    );
    
    OPEN oTarefas;
    
    getTarefas : LOOP
		FETCH oTarefas INTO tCod, tDuracao, tImportancia;
        IF lErro = 1 THEN
			LEAVE getTarefas;
        END IF;
        
        SET DATA_INICIO = CALCULO_HORA_TAREFA(USUARIO, SEC_TO_TIME(tDuracao * 60));
        SET DATA_FIM = DATE_ADD(DATA_INICIO, INTERVAL tDuracao MINUTE);
        
        INSERT INTO TEMP_TAREFAS (DATA_INICIO, DATA_FIM, FK_TAREFA, FK_USUARIO) VALUES (DATA_INICIO, DATA_FIM, tCod, USUARIO);
        
	END LOOP getTarefas;
END
//

DELIMITER //
CREATE PROCEDURE ENVIAR_NOTIFICACAO(IN EMAIL_USUARIO VARCHAR(255), IN PROJETO INT)
BEGIN
	
    DECLARE oUSUARIO INT;

	SELECT COD_USUARIO INTO oUSUARIO FROM USUARIO WHERE EMAIL = EMAIL_USUARIO;
    
    IF oUSUARIO  > 0 THEN
		INSERT INTO USUARIO_PROJETO(FK_USUARIO, FK_PROJETO) VALUES (oUSUARIO, PROJETO);
	END IF;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGAR_NOTIFICACAO;
CREATE PROCEDURE CARREGAR_NOTIFICACAO(IN USUARIO INT)
BEGIN
	DECLARE TEXTO VARCHAR(255) DEFAULT ' te convidou para a o projeto ';
    
	SELECT CONCAT(NOME, ' ', SOBRENOME, TEXTO, TITULO) MENSAGEM, USUARIO_PROJETO.FK_USUARIO, USUARIO_PROJETO.FK_PROJETO 
    FROM USUARIO_PROJETO 
    INNER JOIN PROJETO ON FK_PROJETO = COD_PROJETO
    INNER JOIN USUARIO ON PROJETO.FK_USUARIO = COD_USUARIO
    WHERE USUARIO_PROJETO.FK_USUARIO = USUARIO AND ACEITO = 0;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS CARREGA_MEMBROS;
CREATE PROCEDURE CARREGA_MEMBROS(IN PROJETO INT)
BEGIN
	SELECT COD_USUARIO, EMAIL, NOME, SOBRENOME FROM USUARIO_PROJETO 
	INNER JOIN USUARIO ON COD_USUARIO = FK_USUARIO
	WHERE FK_PROJETO = PROJETO AND ACEITO = 1;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS DESEMPENHO_EQUIPE;
CREATE PROCEDURE DESEMPENHO_EQUIPE(IN COD_PROJETO INT)
BEGIN
	DECLARE DATA_INICIAL DATE DEFAULT DATE_SUB(DATE(NOW()), INTERVAL 7 DAY);
    DECLARE DATA_FINAL DATE DEFAULT DATE(NOW());
    
    DECLARE oQtd INT;
    
    DROP TEMPORARY TABLE IF EXISTS DATA_TAREFAS;
    CREATE TEMPORARY TABLE DATA_TAREFAS(
		QTD_TAREFAS INT,
        DATA_TAREFA DATE
    );
    
    WHILE DATA_INICIAL <= DATA_FINAL DO
		SELECT COUNT(COD_TAREFA) INTO oQTD FROM TAREFA WHERE DATE(DATA_CONCLUIDO) = DATA_INICIAL AND FK_PROJETO = COD_PROJETO;
        INSERT INTO DATA_TAREFAS(QTD_TAREFAS, DATA_TAREFA) VALUES (oQtd, DATA_INICIAL);
        SET DATA_INICIAL =  DATE_ADD(DATA_INICIAL, INTERVAL 1 DAY);
    END WHILE;
    
    SELECT QTD_TAREFAS, DATA_TAREFA, '#0277BD' COR FROM DATA_TAREFAS;
END
//

DELIMITER //
-- DROP PROCEDURE IF EXISTS DESEMPENHO_MEMBRO;
CREATE PROCEDURE DESEMPENHO_MEMBRO(IN COD_PROJETO INT, IN COD_USUARIO INT)
BEGIN
	DECLARE DATA_INICIAL DATE DEFAULT DATE_SUB(DATE(NOW()), INTERVAL 7 DAY);
    DECLARE DATA_FINAL DATE DEFAULT DATE(NOW());
    
    DECLARE oQtd INT;
    
    DROP TEMPORARY TABLE IF EXISTS DATA_TAREFAS;
    CREATE TEMPORARY TABLE DATA_TAREFAS(
		QTD_TAREFAS INT,
        DATA_TAREFA DATE
    );
    
    WHILE DATA_INICIAL <= DATA_FINAL DO
		SELECT COUNT(COD_TAREFA) INTO oQTD FROM TAREFA WHERE DATE(DATA_CONCLUIDO) = DATA_INICIAL AND FK_PROJETO = COD_PROJETO AND FK_USUARIO = COD_USUARIO;
        INSERT INTO DATA_TAREFAS(QTD_TAREFAS, DATA_TAREFA) VALUES (oQtd, DATA_INICIAL);
        SET DATA_INICIAL =  DATE_ADD(DATA_INICIAL, INTERVAL 1 DAY);
    END WHILE;
    
    SELECT QTD_TAREFAS, DATA_TAREFA, '#0277BD' COR FROM DATA_TAREFAS;
END
//