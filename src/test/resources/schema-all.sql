create table if not exists TB_PESSOA(
   id long not null auto_increment primary key,
   nome varchar(255) not null,
   cpf varchar (20) not null
);
