create database import_excel_demo;

use import_excel_demo;

create table Student(
	id int auto_increment,
	student_no varchar(50),
	first_name varchar(100),
	last_name varchar(100),
	age int,
	address text,
    primary key(id)
);


insert into Student(student_no, first_name, last_name, age, address)
values('2021001', 'frank', 'test', 30, 'mocked address');



create user 'importdemo'@'%' identified by 'abc@123';

grant all on import_excel_demo.* to 'importdemo'@'%' ;
