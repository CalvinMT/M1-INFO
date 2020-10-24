drop table LesMaladies;
drop table LesAnimaux;
drop table LesResponsables;
drop table LesGardiens;
drop table LesSpecialites;
drop table LesEmployes cascade constraint;
drop table LesCages cascade constraint;
drop table LesHistoiresAff;

create table LesCages (
	noCage number(3),
	fonction varchar2(20),
	noAllee number(3) not null,
	constraint LesCages_C1 primary key (noCage),
	constraint LesCages_C2 check (noCage between 1 and 999),
	constraint LesCages_C3 check (noAllee between 1 and 999)
);

create table LesEmployes (
	nomE varchar2(20),
	adresse varchar2(20),
	constraint LesEmployes_C1 primary key (nomE)
);

create table LesSpecialites (
	nomE varchar2(20),
	fonction_cage varchar2(20),
	constraint LesSpecialites_C1 primary key (nomE, fonction_cage),
	constraint LesSpecialites_C2 foreign key (nomE) references LesEmployes (nomE)
);

create table LesGardiens (
	noCage number(3),
	nomE varchar2(20),
	constraint LesGardiens_C1 primary key (noCage, nomE),
	constraint LesGardiens_C2 foreign key (nomE) references LesEmployes (nomE),
	constraint LesGardiens_C3 foreign key (noCage) references LesCages (noCage)
);

create table LesResponsables (
	noAllee number(3),
	nomE varchar2(20),
	constraint LesResponsables_C1 primary key (noAllee),
	constraint LesResponsables_C2 foreign key (nomE) references LesEmployes (nomE)
);
		
create table LesAnimaux (
	nomA varchar2(20),
	sexe varchar2(13),
	type_an varchar2(15),
	fonction_cage varchar2(20),
	pays varchar2(20),
	anNais number(4),
	noCage number(3) not null,
	nb_maladies number(3),
	constraint LesAnimaux_C1 primary key (nomA),
	constraint LesAnimaux_C2 check (sexe in ('femelle','male','hermaphrodite')),
	constraint LesAnimaux_C3 check (anNais >= 1900)
);

create table LesMaladies (
	nomA varchar2(20),
	nomM varchar2(20),
	constraint LesMaladies_C1 primary key (nomA,nomM),
	constraint LesMaladies_C2 foreign key (nomA) references LesAnimaux (nomA) on delete cascade
);

create table LesHistoiresAff (
	dateFin date,
	noCage number(3),
	nomE varchar2(20),
	constraint LesHistoiresAff_C1 primary key (dateFin, noCage, nomE)
);



--TRIGGER 5.1
--Lorsqu’un gardien voit l'une de ses 
--affectations modifiées, son ancienne affectation doit 
--être conservée dans la table LesHistoiresAff.
CREATE OR REPLACE TRIGGER guardian_function_delete 
AFTER DELETE OR UPDATE ON LesGardiens 
FOR EACH ROW 
BEGIN 
	INSERT INTO LesHistoiresAff 
	VALUES(sysdate, :old.noCage, :old.nomE);
END;
/

--TRIGGER 5.2
--Des animaux ne peuvent pas être placés dans 
--une cage dont la fonction est incompatible avec ces 
--animaux. On prendra en compte le fait que des animaux 
--peuvent être ajoutés, mais aussi déplacés d’une cage.
CREATE OR REPLACE TRIGGER animal_cage_change 
BEFORE UPDATE OR INSERT ON LesAnimaux 
FOR EACH ROW 
DECLARE 
	cageFonction LesCages.fonction%Type;
BEGIN 
	SELECT fonction INTO cageFonction 
	FROM LesCages 
	WHERE noCage=:new.noCage;
	IF cageFonction <> :new.fonction_cage 
		THEN raise_application_error(-20100, 'La fonction de la nouvelle cage est incompatible avec la fonction de l_animal.'); 
	END IF;
END;
/

--Trigger 5.3
--Un gardien ne peut pas être retiré de la 
--surveillance d’une cage si les animaux qu’elle 
--contient se retrouvent non gardés. On prendra en 
--compte le fait que des gardiens peuvent être retirés, 
--mais aussi affectés à une autre cage.
CREATE OR REPLACE TRIGGER guardian_assignment_change 
AFTER DELETE OR UPDATE ON LesGardiens 
DECLARE 
	nbAnimalsInCage number(3);
	nbCageWithoutGuardian number(3);
BEGIN 
	SELECT count(*) INTO nbCageWithoutGuardian 
	FROM ( 
		SELECT noCage 
		FROM LesAnimaux  
		MINUS 
		SELECT noCage 
		FROM LesGardiens
	);
	IF nbCageWithoutGuardian > 0 
		THEN raise_application_error(-20301, 'La fonction du gardien ne peut être retirée car la cage se retrouverait sans gardien.'); 
	END IF;
END;
/



insert into LesCages values (11 ,  'fauve'           , 10 );
insert into LesCages values (1     , 'fosse'         , 1 );
insert into LesCages values (2     , 'aquarium'      , 1 );
insert into LesCages values (3     , 'petits oiseaux'        , 2 );
insert into LesCages values (4     , 'grand aquarium'        , 1 );
insert into LesCages values (12     , 'fauve'           , 10);



insert into LesEmployes values ('Verdier'  ,       'Noumea' );
insert into LesEmployes values ('Spinnard'  ,       'Sartene' );
insert into LesEmployes values ('Labbe' ,    'Calvi' );
insert into LesEmployes values ('Lachaize' ,       'Pointe a Pitre' );
insert into LesEmployes values ('Desmoulins'  , 'Ushuaia' );
insert into LesEmployes values ('Jouanot'   , 'Papeete' );

insert into LesSpecialites values ('Verdier'  ,       'fauve' );
insert into LesSpecialites values ('Spinnard'  ,       'fauve' );
insert into LesSpecialites values ('Labbe' ,    'fauve' );
insert into LesSpecialites values ('Lachaize' ,       'fauve' );
insert into LesSpecialites values ('Lachaize' ,       'fosse' );
insert into LesSpecialites values ('Lachaize' ,       'petits oiseaux' );
insert into LesSpecialites values ('Desmoulins'  , 'fauve' );
insert into LesSpecialites values ('Desmoulins'  , 'fosse' );
insert into LesSpecialites values ('Desmoulins'  , 'petits oiseaux' );
insert into LesSpecialites values ('Jouanot'   , 'fosse' );
insert into LesSpecialites values ('Jouanot' ,       'aquarium' );
insert into LesSpecialites values ('Jouanot' ,       'grand aquarium' );


insert into LesResponsables values (10      ,       'Verdier' );
insert into LesResponsables values (1       ,       'Jouanot' );
insert into LesResponsables values (2       ,       'Desmoulins');


insert into LesGardiens values (11      ,       'Lachaize' );
insert into LesGardiens values (12      ,       'Spinnard' );
insert into LesGardiens values (12      ,       'Labbe' );
insert into LesGardiens values (11      ,       'Spinnard' );
insert into LesGardiens values (11      ,       'Labbe' );
insert into LesGardiens values (1       ,       'Lachaize' );
insert into LesGardiens values (3       ,       'Lachaize' );
insert into LesGardiens values (12      ,       'Lachaize' );

insert into LesAnimaux values ('Charly', 'male',   'lion', 'fauve',  'Kenya',  2010,   12,2);
insert into LesAnimaux values ('Arthur', 'male',   'ours', 'fosse',  'France', 2000,   1,0 );
insert into LesAnimaux values ('Chloe',  'femelle', 'pie', 'petits oiseaux' ,  'France', 2011,   3,1 );
insert into LesAnimaux values ('Milou',  'male' ,  'leopard', 'fauve', 'France', 2013,  11,1 );
insert into LesAnimaux values ('Tintin', 'male' , 'leopard', 'fauve', 'France', 2013,    11,0 );
insert into LesAnimaux values ('Charlotte', 'femelle', 'lion',  'fauve',  'Kenya',  2012,      12,0 );

insert into LesMaladies values ('Charly'        , 'rage de dents' );
insert into LesMaladies values ('Charly'        , 'grippe' );
insert into LesMaladies values ('Milou'         , 'angine' );
insert into LesMaladies values ('Chloe'         , 'grippe' );

