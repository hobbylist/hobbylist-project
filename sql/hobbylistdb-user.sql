drop user 'hobbylist'@'localhost';
create user 'hobbylist'@'localhost' identified by 'hobbylist';
grant all privileges on hobbylistdb.* to 'hobbylist'@'localhost';
flush privileges;