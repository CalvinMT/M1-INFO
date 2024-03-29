/*
# ###################################################### #
# 4.2
# Les cages compatibles avec les spécialités du gardien.
# ###################################################### #
*/

SELECT A.noCage, A.fonction, A.nomE 
FROM 
	(SELECT C.noCage, C.fonction, S.nomE 
	FROM LesCages C 
	INNER JOIN LesSpecialites S 
	ON C.fonction = S.fonction_cage 
	WHERE S.nomE = 'Lachaize') A 
MINUS 
SELECT noCage, fonction_cage, nomE 
FROM 
	(SELECT G.noCage, S.fonction_cage, G.nomE 
	FROM LesGardiens G 
	INNER JOIN LesSpecialites S 
	ON G.nomE = S.nomE 
	WHERE S.nomE = 'Lachaize') B;



/*
# ###################################################### #
# 4.3
# Le nombre de gardien par cage.
# ###################################################### #
*/

DROP TABLE StatistiqueGardienParCage;

CREATE TABLE StatistiqueGardienParCage (
	noCage number(3),
	nbGardien number(20),
	constraint StatistiqueGardienParCage_C1 primary key (noCage, nbGardien), 
	constraint StatistiqueGardienParCage_C2 foreign key (noCage) references LesCages (noCage),
	constraint StatistiqueGardienParCage_C3 check (nbGardien between 1 and 999)
);

SELECT noCage 
FROM LesCages;

SELECT count(*) 
FROM LesGardiens 
WHERE noCage = ?;

INSERT INTO StatistiqueGardienParCage VALUES (?, ?);



/*
# ###################################################### #
# 5.1
# Trigger : Lorsqu’un gardien voit l'une de ses 
# affectations modifiées, son ancienne affectation doit 
# être conservée dans la table LesHistoiresAff.
# ###################################################### #
*/

CREATE OR REPLACE TRIGGER guardian_function_delete 
AFTER DELETE OR UPDATE ON LesGardiens 
FOR EACH ROW 
BEGIN 
	INSERT INTO LesHistoiresAff 
	VALUES(sysdate, :old.noCage, :old.nomE);
END;
/

ALTER TRIGGER guardian_affectation_change ENABLE;



/*
# ###################################################### #
# 5.2
# Trigger : Des animaux ne peuvent pas être placés dans 
# une cage dont la fonction est incompatible avec ces 
# animaux. On prendra en compte le fait que des animaux 
# peuvent être ajoutés, mais aussi déplacés d’une cage.
# ###################################################### #
*/

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

ALTER TRIGGER animal_cage_change ENABLE;



/*
# ###################################################### #
# 5.3
# Trigger : Un gardien ne peut pas être retiré de la 
# surveillance d’une cage si les animaux qu’elle 
# contient se retrouvent non gardés. On prendra en 
# compte le fait que des gardiens peuvent être retirés, 
# mais aussi affectés à une autre cage.
# ###################################################### #
*/

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

ALTER TRIGGER guardian_assignment_change ENABLE;
