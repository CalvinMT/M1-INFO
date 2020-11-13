-- Commencer par insérer des instances de cages (par exemple)
-- Mais on ne peut pas encore insérer les gardiens à la liste de gardiens de chaque cage car les gardiens n'ont pas encore été créés.
insert into LesCages values (11,	'fauve',			10,	ens_gardiens(NULL));
-- faire de même pour les autres instances de cages
insert into LesCages values (1,		'fosse',			1,	ens_gardiens(NULL));
insert into LesCages values (2,		'aquarium',			1,	ens_gardiens(NULL));
insert into LesCages values (3,		'petits oiseaux',	2,	ens_gardiens(NULL));
insert into LesCages values (4,		'grand aquarium',	1,	ens_gardiens(NULL));
insert into LesCages values (12,	'fauve',			10,	ens_gardiens(NULL));


-- insérer les instances de responsables (avec les constructeurs qui vont bien)
insert into LesEmployes values (Tresponsable('Verdier',		'Noumea',			Tspecialites('fauve'), 10));
-- et même chose pour les autres responsables
insert into LesEmployes values (Tresponsable('Desmoulins',	'Ushuaia',			Tspecialites('fauve', 'fosse', 'petits oiseaux'), 2));
insert into LesEmployes values (Tresponsable('Jouanot',		'Papeete',			Tspecialites('fosse', 'aquarium', 'grand aquarium'), 1));

-- insérer les instances de gardiens
-- et remarquer l'intérêt d'avoir créé avant les cages
-- On utilise des requêtes SQL pour récupérer les références des cages 11 et 12 dans cet exemple
insert into LesEmployes values (Tgardien('Spinnard',	'Sartene',			Tspecialites('fauve'),
	ens_cages(
		(SELECT REF(c) FROM LesCages c WHERE noCage=11),
		(SELECT REF(c) FROM LesCages c WHERE noCage=12))
	) );

-- et même méthode pour les autres employés
insert into LesEmployes values (Tgardien('Labbe',		'Calvi',			Tspecialites('fauve'),
	ens_cages(
		(SELECT REF(c) FROM LesCages c WHERE noCage=11),
		(SELECT REF(c) FROM LesCages c WHERE noCage=12))
	) );

insert into LesEmployes values (Tgardien('Lachaize',	'Pointe a Pitre',	Tspecialites('fauve', 'fosse', 'petits oiseaux'),
	ens_cages(
		(SELECT REF(c) FROM LesCages c WHERE noCage=1),
		(SELECT REF(c) FROM LesCages c WHERE noCage=3),
		(SELECT REF(c) FROM LesCages c WHERE noCage=11),
		(SELECT REF(c) FROM LesCages c WHERE noCage=12))
	) );

-- Insertion des instances d'animaux
-- l'avant dernier attribut est une référence sur une cage => requête SQL
insert into LesAnimaux values ('Charly',	'male',		'lion',		'fauve',			'Kenya',	2010,	(SELECT REF(c) FROM LesCages c WHERE noCage=12),	ens_maladies('rage de dents', 'grippe'));
-- et faire de même pour les autres animaux
insert into LesAnimaux values ('Arthur',	'male',		'ours',		'fosse',			'France',	2000,	(SELECT REF(c) FROM LesCages c WHERE noCage=1),		ens_maladies(NULL));
insert into LesAnimaux values ('Chloe',		'femelle',	'pie',		'petits oiseaux',	'France',	2011,	(SELECT REF(c) FROM LesCages c WHERE noCage=3),		ens_maladies('grippe'));
insert into LesAnimaux values ('Milou',		'male',		'leopard',	'fauve',			'France',	2013,	(SELECT REF(c) FROM LesCages c WHERE noCage=11),	ens_maladies('angine'));
insert into LesAnimaux values ('Tintin',	'male',		'leopard',	'fauve',			'France',	2013,	(SELECT REF(c) FROM LesCages c WHERE noCage=11),	ens_maladies(NULL));
insert into LesAnimaux values ('Charlotte',	'femelle',	'lion',		'fauve',			'Kenya',	2012,	(SELECT REF(c) FROM LesCages c WHERE noCage=12),	ens_maladies(NULL));

-- Enfin on peut ajouter les gardiens à la liste de cages créées au tout début
-- Pour cela on récupère l'objet liste_gardiens qui est une table (nested table) dans laquelle on peut ajouter les gardiens
-- en fait on insère des références sur des gardiens (et pas directement les objets)
-- Demandez-vous pourquoi ce choix ? Et quel serait les implications d'un ajout direct d'objet par exemple ?
insert into Table(SELECT c.liste_gardiens FROM LesCages c WHERE c.noCage=1) value (SELECT TREAT(REF(g) AS REF Tgardien) FROM LesEmployes g WHERE g.nomE in ('Lachaize'));

-- idem pour compléter les autres cages avec leur liste de gardiens
insert into Table(SELECT c.liste_gardiens FROM LesCages c WHERE c.noCage=3) value (SELECT TREAT(REF(g) AS REF Tgardien) FROM LesEmployes g WHERE g.nomE in ('Lachaize'));

insert into Table(SELECT c.liste_gardiens FROM LesCages c WHERE c.noCage=11) value (SELECT TREAT(REF(g) AS REF Tgardien) FROM LesEmployes g WHERE g.nomE in ('Lachaize', 'Spinnard', 'Labbe'));

insert into Table(SELECT c.liste_gardiens FROM LesCages c WHERE c.noCage=12) value (SELECT TREAT(REF(g) AS REF Tgardien) FROM LesEmployes g WHERE g.nomE in ('Lachaize', 'Spinnard', 'Labbe'));

