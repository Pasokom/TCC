use CYBER_LIFE;

create table EVENTO(
	COD_EVENTO int not null primary key auto_increment,
    TITULO varchar(100) not null, 
    DATA_INICIO datetime not null,
    DATA_FIM datetime not null,
    LOCAL_EVENTO varchar(150),
    DESCRICAO varchar(255),
    TIPO_REPETICAO int,
    TIPO_FIM_REPETICAO int,
    FK_USUARIO int,
    foreign key (FK_USUARIO) references USUARIO(UCODIGO)
);

create table E_REPETIR(
	COD_REPETICAO int not null primary key auto_increment,
    INTERVALO int not null,
    DIAS_SEMANA varchar(15),
    FK_EVENTO int,
    unique key UNIQUE_EVENTO (FK_EVENTO),
    foreign key (FK_EVENTO) references EVENTO(COD_EVENTO)
);

create table E_FIM_REPETICAO(
	COD_FIM_REPETICAO int not null primary key auto_increment,
    DIA_FIM datetime,
    QTD_RECORRENCIAS int,
    FK_EVENTO int,
    unique key UNIQUE_EVENTO (FK_EVENTO),
    foreign key (FK_EVENTO) references EVENTO(COD_EVENTO)
);

select * from evento 
	left join e_repetir on evento.cod_evento = e_repetir.fk_evento
	left join e_fim_repeticao on evento.cod_evento = e_fim_repeticao.fk_evento;

