/* datas comemorativas com dia fixo */
INSERT INTO DATAS_COMEMORATIVAS (NOME, TIPO_DATA, DIA, MES) VALUES
	("Ano Novo", 0, 1, 1),
    ("Tiradentes", 0, 21, 4),
    ("Dia do Trabalho", 0, 1, 5),
    ("Dia dos Namorados", 0, 12, 6),
    ("Independência", 0, 7, 9),
    ("Nossa Senhora de Aparecida", 0, 12, 10),
    ("Dia do Professor", 0, 15, 10),
    ("Dia do Servidor Público", 0, 28, 10),
    ("Finados", 0, 2, 11),
    ("Proclamação da República", 0, 15, 11),
    ("Dia da Consciência Negra", 0, 20, 11),
    ("Véspera de Natal", 0, 24, 12),
    ("Natal", 0, 25, 12),
    ("Véspera de Ano Novo", 0, 31, 12);

/* datas comemorativas com dia da semana fixo */
INSERT INTO DATAS_COMEMORATIVAS (NOME, TIPO_DATA, DIA_SEMANA, SEMANA, MES) VALUES
	("Dias das Mães", 1, 0, 2, 5),
	("Dias dos Pais", 1, 0, 2, 8);

/*
 * datas comemorativas móveis (que dependem da páscoa)
 * as datas são calculadas levando em conta os dias antes ou depois da páscoa
 */    
INSERT INTO DATAS_COMEMORATIVAS (NOME, TIPO_DATA, DIA) VALUES
	("Carnaval", 2, -47),
	("Cinzas", 2, -46),
	("Sexta-Feira da Paixão", 2, -2),
	("Domingo de Páscoa", 2, 0),
	("Corpus Christi", 2, 60);