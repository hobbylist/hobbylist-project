drop database if exists hobbylistdb;
create database hobbylistdb;
 
use hobbylistdb;
 
create table users (
	username				varchar(20) not null primary key,
	userpass				char(32) not null,
	name					varchar(70) not null,
	email					varchar(255) not null
);
 
create table user_roles (
	username				varchar(20) not null,
	rolename 				varchar(20) not null,
	foreign key(username) 	references users(username) on delete cascade,
	primary key (username, rolename)
);
 
create table hobbies (
	hobbyid 				int not null primary key auto_increment,
	classification			varchar(20) not null,
	title 					varchar(100) not null,	
	synopsis				varchar(500) not null,
	genre					varchar(20) not null,
	director				varchar(100),
	author					varchar(100),
	company					varchar(100),
	year					varchar(20) not null,
	imageurl				varchar(200) not null,
	creation_timestamp		datetime not null default current_timestamp
);

create table lists (
	listid					int not null primary key auto_increment,
	hobbyid					int not null,
	username				varchar(20) not null,
	tag 					varchar(20) not null,
	rank 					varchar(20),
	foreign key(hobbyid) 	references hobbies(hobbyid),
	foreign key(username) 	references users(username)
);

/*	create table invite (
	invitationid 			int not null primary key auto_increment,
	sender					varchar(20) not null,
	receiver				varchar(20) not null,
	hobbyid 				varchar(100) not null,
	tag 					varchar(20) not null,
	state					varchar(20) not null,
	foreign key(hobbyid)	references hobbies(hobbyid)
);*/

create table messages (

	messageid				int not null primary key auto_increment,
	sender					varchar(20) not null,
	receiver				varchar(20) not null,
	subject					varchar(100) not null,
	content					varchar(500) not null,	
	creation_timestamp		datetime not null default current_timestamp,
	foreign key(sender)		references users(username),
	foreign key(receiver)	references users(username)
);