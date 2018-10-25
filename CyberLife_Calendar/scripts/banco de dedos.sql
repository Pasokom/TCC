create database CyberLife_Calendar;
use CyberLife_Calendar;

create table lembrete(
cod_lembrete int(15) not null primary key auto_increment,
nome varchar(45),
descricao varchar(255),
data_lembrete date,
dia_inteiro bool,
repetir bool
);

create table repeticao(
cod_repeticao int(15),
dia_semana varchar(15),
dia_mes int,
dia_ano date,
constraint foreign key(cod_repeticao) references lembrete(cod_lembrete) 
);

insert into lembrete(nome, descricao, data_lembrete, dia_inteiro, repetir)
values('regar', 'lembrar de regar as plantas', '2019-03-12', true, true);

insert into repeticao(cod_repeticao, dia_semana)
values('1', 'sabado');

insert into repeticao(cod_repeticao, dia_semana)
values('1', 'domingo');

select * from lembrete
	left join repeticao on cod_lembrete = cod_repeticao;