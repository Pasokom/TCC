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
	("Dia das Mães", 1, 0, 2, 5),
	("Dia dos Pais", 1, 0, 2, 8);

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
    
/*
 *	criando um usuário para demonstração
 */
 
INSERT INTO USUARIO(COD_USUARIO, EMAIL, NOME, SOBRENOME, SENHA) VALUES (1, 'herbert@padrao.com', 'Herbert', 'Richers', md5('1234'));
INSERT INTO USUARIO(COD_USUARIO, EMAIL, NOME, SOBRENOME, SENHA) VALUES (2, 'alamo@padrao.com', 'Álamo', '', md5('1234'));
INSERT INTO USUARIO(COD_USUARIO, EMAIL, NOME, SOBRENOME, SENHA) VALUES (3, 'peri@padrao.com', 'Peri', '', md5('1234'));

INSERT INTO PROJETO(COD_PROJETO, TITULO, DATA_INICIO, DATA_ENTREGA, FK_USUARIO) VALUES (1, 'Dublagem do filme do batman', now(), '2019-06-19 00:00:00', 1);
INSERT INTO USUARIO_PROJETO(FK_USUARIO, FK_PROJETO, ACEITO) VALUES(2, 1, 1);
INSERT INTO USUARIO_PROJETO(FK_USUARIO, FK_PROJETO, ACEITO) VALUES(3, 1, 1);

INSERT INTO MARCADOR(NOME_MARCADOR, FK_PROJETO) VALUES ('Batman', 1);
INSERT INTO MARCADOR(NOME_MARCADOR, FK_PROJETO) VALUES ('Robin', 1);
INSERT INTO MARCADOR(NOME_MARCADOR, FK_PROJETO) VALUES ('Coringa', 1);
INSERT INTO MARCADOR(NOME_MARCADOR, FK_PROJETO) VALUES ('Charada', 1);

INSERT INTO TAREFA(NOME_TAREFA, DURACAO_MINUTOS, IMPORTANCIA, CONCLUIDO, FK_NOME_MARCADOR, FK_PROJETO, FK_USUARIO) VALUES 
	('"Ou você morre como um herói, ou vive o bastante para se tornar um vilão"', 30, 1, 0, 'Batman', 1, 1),
    ('"Sabe por que caímos? Para aprendermos a levantar"', 30, 1, 0, 'Batman', 1, 1),
    ('"Sua compaixão é uma fraqueza que seus inimigos não irão compartilhar"', 30, 3, 0, 'Batman', 1, 1),
    ('"Eu sou o Batman!!!"', 30, 5, 0, 'Batman', 1, 1),
    ('"Não é quem eu sou por dentro e sim, o que eu faço é que me define"', 30, 1, 0, 'Batman', 1, 1),
    ('"Santa Festa à Fantasia"', 30, 1, 0, 'Robin', 1, 2),
    ('"Santo Robô de Controle Remoto"', 30, 1, 0, 'Robin', 1, 2),
    ('"Santo Erro de Casting"', 30, 3, 0, 'Robin', 1, 2),
    ('"Santo Metal Mais Resistente do Mundo"', 30, 5, 0, 'Robin', 1, 2),
    ('"Santo Inatingível Processo de Memória Fotográfica"', 30, 1, 0, 'Robin', 1, 2),
    ('"Vamos colocar um sorriso nesse rosto"', 30, 1, 0, 'Coringa', 1, 3),
    ('"A loucura é como a gravidade, só precisa de um empurrãozinho..."', 30, 1, 0, 'Coringa', 1, 3),
    ('"Você pode se vingar do mal, sem se tornar parte dele?"', 30, 3, 0, 'Coringa', 1, 3),
    ('"De certa forma, é bom saber que não sou o único fingindo ser normal."', 30, 5, 0, 'Coringa', 1, 3),
    ('"Se você é bom em alguma coisa, nunca a faça de graça."', 30, 1, 0, 'Coringa', 1, 3),
    ('"O amor é um campo minado, e a sua unica arma e a sinceridade"', 30, 1, 0, 'Charada', 1, 1),
    ('"Quem tem a angústia como aliada faz do tempo sua forca"', 30, 1, 0, 'Charada', 1, 3);

INSERT INTO TAREFA(NOME_TAREFA, DURACAO_MINUTOS, IMPORTANCIA, CONCLUIDO, DATA_CONCLUIDO, FK_NOME_MARCADOR, FK_PROJETO, FK_USUARIO) VALUES
	('"Ou você morre como um herói, ou vive o bastante para se tornar um vilão"', 30, 1, 1, '2019-06-18 00:00:00', 'Batman', 1, 1),
    ('"Já pensaram no poder curativo das gargalhadas?"', 30, 1, 1, '2019-06-17 00:00:00', 'Batman', 1, 1),
    ('"Santo Corno de Caça"', 30, 1, 1, '2019-06-16 00:00:00', 'Robin', 1, 2),
    ('"Santa Sardinha"', 30, 1, 1, '2019-06-18 00:00:00', 'Robin', 1, 2),
    ('"Santo Purê de Batatas"', 30, 1, 1, '2019-06-19 00:00:00', 'Robin', 1, 2),
    ('"Santa Prontidão, Batman!"', 30, 1, 1, '2019-06-19 00:00:00', 'Robin', 1, 2),('"Ou você morre como um herói, ou vive o bastante para se tornar um vilão"', 30, 1, 1, '2019-06-18 00:00:00', 'Batman', 1, 1),
    ('"Eu acredito que tudo o que não te mata, te deixa mais estranho"', 30, 1, 1, '2019-06-17 00:00:00', 'Coringa', 1, 3),
    ('"Não leve a vida a sério, você não vai sair vivo dela!"', 30, 1, 1, '2019-06-18 00:00:00', 'Coringa', 1, 3);
 