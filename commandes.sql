use mydb;
#select * from appareils;

#on peuple appareils
insert into appareils (immatriculation,type) values ('21','A320');
insert into appareils (immatriculation,type) values ('22','A380');
insert into appareils (immatriculation,type) values ('23','A380');
insert into appareils (immatriculation,type) values ('24','B720');
insert into appareils (immatriculation,type) values ('25','B720');
insert into appareils (immatriculation,type) values ('26','A320');

#on peuple les billets
insert into billets (numerobillet,dateemission,prix) values ('2566','2018-10-20','100');
insert into billets (numerobillet,dateemission,prix) values ('2567','2018-10-20','100');
insert into billets (numerobillet,dateemission,prix) values ('2568','2018-10-20','100');
insert into billets (numerobillet,dateemission,prix) values ('2569','2018-10-20','200');

#on peuple les departs
insert into depart (idvol,datedepart,nbplaceslibres,nbplacesoccupees) values ('1','2018-10-20','200','4');

#on peuple les équipages
insert into equipage (numerosecuritesociale,nom,prenom,adresse,salaire,fonction,heuresvol) values ('1000','Deubaze','Raymond','36 av guy de collongues 69130 ecully','2000','Chef de cabine','3000');
insert into equipage (numerosecuritesociale,nom,prenom,adresse,salaire,fonction,heuresvol) values ('10053','Burck','Lionel','51 ch des mouilles 69130 ecully','1500','Stewart','5000');

#on peuple les liaisons
insert into liaison (depart,arrivee,villedepart,villearrivee,idliaison) values ('CDG','LAX','Paris','Los Angeles','101');

#on peuple les passagers
insert into passagers (idpassager, nom,prenom,adresse) values ('001','Charles','Lionel','Charrière Blanche 69130 Ecully');
insert into passagers (idpassager, nom,prenom,adresse) values ('002','Charles','Elisabeth','Charrière Blanche 69130 Ecully');
insert into passagers (idpassager, nom,prenom,adresse) values ('003','Charles','Julien','Charrière Blanche 69130 Ecully');
insert into passagers (idpassager, nom,prenom,adresse) values ('004','Charles','Eleanor','Charrière Blanche 69130 Ecully');

#on peuple les pilotes
insert into pilotes (numerosecuritesociale,nom,prenom,adresse,salaire,licence,heuresvol) values ('999','Lamericain','John','Avenue des champs elysees','15000','152648','13000');

#on peuple les reservations
insert into reservation (idpassager, numerobillet,idvol,datedepart) values ('001','2566','1','2018-10-20');
insert into reservation (idpassager, numerobillet,idvol,datedepart) values ('002','2567','1','2018-10-20');
insert into reservation (idpassager, numerobillet,idvol,datedepart) values ('003','2568','1','2018-10-20');
insert into reservation (idpassager, numerobillet,idvol,datedepart) values ('004','2569','1','2018-10-20');

#on peuple les vols
insert into vol (idvol,periodedebut,periodefin,horairedepart,horairearrivee,idliaison,immatriculation) values ('1','2017-01-20','2020-12-25','1900-01-01 20:00:00','1900-01-01 04:00:00','4','23');


#select * from appareils;
#select * from liaison;
#select * from (appareils join vol join liaison);
#DELETE FROM vol WHERE idvol = 1;