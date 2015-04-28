drop database if exists hobbylistdb;
create database hobbylistdb;
 
use hobbylistdb;
 
create table users (
	username	varchar(20) not null primary key,
	userpass	char(32) not null,
	name		varchar(70) not null,
	email		varchar(255) not null
);
 
create table user_roles (
	username			varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);
 
create table movies (
	movieid 			int not null auto_increment primary key,
	title 				varchar(100) not null,
	tag 				varchar(20) not null,
	username 			varchar(20) not null,
	last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp		datetime not null default current_timestamp,
	foreign key(username) 		references users(username)
);

create table books (
	bookid 				int not null auto_increment primary key,
	title 				varchar(100) not null,
	tag 				varchar(20) not null,
	username 			varchar(20) not null,
	last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp		datetime not null default current_timestamp,
	foreign key(username) 		references users(username)
);

create table games (
	gameid 				int not null auto_increment primary key,
	title 				varchar(100) not null,
	tag 				varchar(20) not null,
	username 			varchar(20) not null,
	last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp		datetime not null default current_timestamp,
	foreign key(username) 		references users(username)
);