create database belajar_development;

create table biodata(
    id int primary key auto_increment,
    nama varchar(150) not null,
    alamat varchar(150) not null,
    tanggalLahir date not null,
    email varchar (150) unique not null
)engine=InnoDB;