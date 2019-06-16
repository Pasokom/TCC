DROP TABLE IF EXISTS TAREFA;
DROP TABLE IF EXISTS MARCADOR;
DROP TABLE IF EXISTS USUARIO_PROJETO;
DROP TABLE IF EXISTS PROJETO;

CREATE TABLE PROJETO (
	COD_PROJETO int AUTO_INCREMENT,
    TITULO varchar(255) NOT NULL,
    DATA_INICIO datetime NOT NULL,
    DATA_ENTREGA datetime NOT NULL,
    FINALIZADO bit DEFAULT 0,
    FK_USUARIO int NOT NULL,
    PRIMARY KEY (COD_PROJETO),
    FOREIGN KEY (FK_USUARIO) REFERENCES USUARIO(COD_USUARIO)
);

CREATE TABLE MARCADOR (
    NOME_MARCADOR varchar(50) NOT NULL,
    FK_PROJETO int AUTO_INCREMENT,
    PRIMARY KEY (NOME_MARCADOR, FK_PROJETO),
    FOREIGN KEY (FK_PROJETO) REFERENCES PROJETO(COD_PROJETO)
);

-- DROP TABLE TAREFA;
CREATE TABLE TAREFA (
	COD_TAREFA int AUTO_INCREMENT,
    NOME_TAREFA varchar(255) NOT NULL,
    DURACAO_MINUTOS int NOT NULL,
    IMPORTANCIA tinyint NOT NULL DEFAULT 1,
    DEPENDENCIA int,
    CONCLUIDO bit DEFAULT 0,
    DATA_CONCLUIDO datetime,
    FK_NOME_MARCADOR varchar(50),
    FK_PROJETO int NOT NULL,
    FK_USUARIO int NOT NULL,
    PRIMARY KEY (COD_TAREFA),
    FOREIGN KEY (FK_USUARIO) REFERENCES USUARIO(COD_USUARIO),
    FOREIGN KEY (FK_PROJETO) REFERENCES PROJETO(COD_PROJETO),
    FOREIGN KEY (DEPENDENCIA) REFERENCES TAREFA(COD_TAREFA),
    FOREIGN KEY (FK_NOME_MARCADOR, FK_PROJETO) REFERENCES MARCADOR (NOME_MARCADOR, FK_PROJETO)
);

CREATE TABLE USUARIO_PROJETO (
	FK_USUARIO int NOT NULL,
    FK_PROJETO int NOT NULL,
    ACEITO bit DEFAULT 0,
    PRIMARY KEY (FK_USUARIO, FK_PROJETO),
    FOREIGN KEY (FK_USUARIO) REFERENCES USUARIO(COD_USUARIO),
    FOREIGN KEY (FK_PROJETO) REFERENCES PROJETO(COD_PROJETO)
);