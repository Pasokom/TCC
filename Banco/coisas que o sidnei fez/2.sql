CALL HORARIO_TAREFAS(1);

SELECT EVENTO_RECORRENCIA.DATA_INICIO, EVENTO_RECORRENCIA.DATA_FIM FROM EVENTO_RECORRENCIA 
	INNER JOIN EVENTO ON FK_EVENTO = COD_EVENTO
    WHERE FK_USUARIO = 1 AND EXCLUIDO = 0 AND (EVENTO_RECORRENCIA.DATA_INICIO > DATE(NOW())
    AND EVENTO_RECORRENCIA.DATA_INICIO < DATE(DATE_ADD(NOW(), INTERVAL 7 DAY)))
    AND ((TIME(EVENTO_RECORRENCIA.DATA_INICIO) >= '13:00:00' AND TIME(EVENTO_RECORRENCIA.DATA_INICIO) <= '16:00:00') 
    OR (TIME(EVENTO_RECORRENCIA.DATA_FIM) <= '16:00:00' AND TIME(EVENTO_RECORRENCIA.DATA_FIM) >= '13:00:00'))
    UNION SELECT TEMP_TAREFAS.DATA_INICIO, TEMP_TAREFAS.DATA_FIM FROM TEMP_TAREFAS
    ORDER BY DATA_INICIO;