source hobbylistdb-schema.sql;

insert into users values('ruben', MD5('ruben'), 'Ruben', 'ruben@acme.com');
insert into user_roles values ('ruben', 'registered');

insert into users values('marc', MD5('marc'), 'Marc', 'marc@acme.com');
insert into user_roles values ('marc', 'registered');

insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Matrix', 'Una peli muy buena con tiros y camaras lentas','accion','Wachowski Brothers',null,null,'1999','http://frasesdelapelicula.com/wp-content/uploads/2011/03/Matrix.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'El libro de la selva', 'Drama Disney donde un niño vive en la selva pero no tiene dinero para comprarse un libro','animacion','Disney',null,null,'1967','http://www.adisney.com/peliculas/libroselva/img/el-libro-de-la-selva.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Robin Hood', 'Robin Hood, no tiene mucho misterio','accion','Kevin Reynolds',null,null,'1991','http://leticianoval.es/wp-content/uploads/2014/05/1a108322029.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Harry Potter 4', 'Harry es un mago al que su mejor amigo le roba la niña repelente que hoy en dia esta mu buena','fantasia','Mike Newell',null,null,'2005','http://pics.filmaffinity.com/Harry_Potter_y_el_c_liz_de_fuego-872646483-large.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Saint Seiya', 'Dame tu fuerza, Pegaso!','shonen','Kozo Morishita',null,null,'1986','http://d212dsb2sdisoj.cloudfront.net/wp-content/uploads/2014/12/Saint-Seiya-Soul-of-Gold.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Babe, el cerdito valiente', 'Peliculon sobre la superacion y la confianza en uno mismo','comedia','Chris Noonan',null,null,'1995','http://www.peliculasdcine.com/wp-content/uploads/2013/06/Babe-El-cerdito-valiente2.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Kill Bill vol1', 'Tarantino + Katanas = <3','accion','Quentin Tarantino',null,null,'2003','http://www.martialartsactionmovies.com/wp-content/uploads/2012/10/Kill-Bill-POster.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Movie', 'Top Gun', 'Tu ego extiende cheques que tu cuerpo no puede pagar','accion','Tony Scott',null,null,'1986','http://pics.filmaffinity.com/Top_Gun_dolos_del_aire-784306900-large.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Book', 'Battle Royale', 'El gobierno envia 50 chavales a una isla para que se maten entre ellos','cienciaficcion',null,'Koushun Takami',null,'1999','http://upload.wikimedia.org/wikipedia/en/5/54/Battle_Royale_2009_ediiton.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Book', 'Apocalipsis', 'Fin del mundo de la pluma de Stephen King','terror',null,'Stephen King',null,'1994','http://upload.wikimedia.org/wikipedia/en/5/54/Battle_Royale_2009_ediiton.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Book', 'Diez negritos', 'Diez desconocidos son citados en una mansion apartada en una isla abandonada. Van muriendo uno a uno tal como describe una cancion infantil','intriga',null,'Agatha Christie',null,'1939','http://ecx.images-amazon.com/images/I/51o0UB-9YwL._SY344_BO1,204,203,200_.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Book', 'El Hobbit', 'Un hobbit y un anillo','fantasia',null,'JRR Tolkien',null,'1937','https://cabaltc.files.wordpress.com/2014/12/el-hobbit-j-r-r-tolkien-minotauro.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Game', 'Star Ocean 4', 'El mejor RPG de la anterior generación','rpg',null,null,'TriAce','2009','https://upload.wikimedia.org/wikipedia/ru/8/88/Star_Ocean_The_Last_Hope_PS3_Cover.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Game', 'Final Fantasy XV', 'No se de que va porque no ha salido y nunca lo hara','actionrpg',null,null,'SquareEnix','2020','http://i1063.photobucket.com/albums/t520/jamesrob12/ff15boxps4_zps87bec7c0.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Game', 'Metal Gear 4', 'Tiros y sigilo, en ese orden, como los pros','sigilo',null,null,'Konami','2008','http://i11c.3djuegos.com/juegos/1421/metal_gear_solid_4_guns_of_the_patriots/fotos/ficha/metal_gear_solid_4_guns_of_the_patriots-1686810.jpg');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Game', 'Kirby', 'Kirby se come lo que sea y consigue sus poderes','plataformas',null,null,'Nintendo','1992','https://pbs.twimg.com/media/B2Phpd6CAAAhaf5.png');
insert into hobbies (classification, title, synopsis, genre, director, author, company, year, imageurl) values ('Game', 'Borderlands', 'Recorre Pandora completando misiones y disparando a todo lo que se mueva','shooterrpg',null,null,'Gearbox','2009','http://upload.wikimedia.org/wikipedia/en/0/01/Borderlandscover.jpg');

insert into lists (hobbyid, username, tag, rank) values ('1', 'ruben', 'Visto', '9');
insert into lists (hobbyid, username, tag, rank) values ('3', 'ruben', 'Visto', '9');
insert into lists (hobbyid, username, tag, rank) values ('9', 'ruben', 'Visto', '9');
insert into lists (hobbyid, username, tag, rank) values ('14', 'ruben', 'Pendiente', '0');
insert into lists (hobbyid, username, tag, rank) values ('15', 'ruben', 'Visto', '9');
insert into lists (hobbyid, username, tag, rank) values ('17', 'ruben', 'Visto', '9');
insert into lists (hobbyid, username, tag, rank) values ('1', 'marc', 'Visto', '8');
insert into lists (hobbyid, username, tag, rank) values ('4', 'marc', 'Pendiente', '6');
insert into lists (hobbyid, username, tag, rank) values ('8', 'marc', 'Visto', '5');
insert into lists (hobbyid, username, tag, rank) values ('13', 'ruben', 'Pendiente', '0');
insert into lists (hobbyid, username, tag, rank) values ('16', 'ruben', 'Pendiente', '0');

insert into messages (sender, receiver, subject, content) values ('ruben', 'marc', 'hola', 'que tal');
insert into messages (sender, receiver, subject, content) values ('ruben', 'marc', 'blabla', 'me comi a mi gemelo en el utero');
insert into messages (sender, receiver, subject, content) values ('ruben', 'marc', 'hola', 'caracola');
insert into messages (sender, receiver, subject, content) values ('ruben', 'marc', 'lerele', 'yippee ki yay');
insert into messages (sender, receiver, subject, content) values ('marc', 'ruben', 'hola', 'la vaca hace muuuuu');