drop table LesMaladies;
drop table LesResponsables;
drop table LesGardiens;
drop table LesSpecialites;
drop table LesHistoiresAff;

drop table LesAnimaux FORCE;
drop table LesEmployes FORCE;
drop table LesCages FORCE;

drop type ens_cages FORCE;
drop type ens_maladies FORCE;
drop type ens_gardiens FORCE;

drop type Tresponsable;
drop type Tgardien;
drop type Temploye;
drop type Tcage;
drop type Tspecialites;
--drop type Tmaladie;

-- ATTENTION: Il est plus prudent d'exécuter chaque commande de manière indépendante, et pas le script complet
-- => plus facile à debuguer

-- Declarer un type tcage
create type Tcage;
/

-- Definir une collection libre (nested table) ens_cages de references sur tcage
create type ens_cages as table of REF Tcage;
/

-- Definir une collection statique (Varray) tspecialites de chaines de caracteres (fonction de la cage)
create type Tspecialites as Varray(10) of varchar2(20);
/

-- Definir un type temploye
create type Temploye as object(
	nomE varchar2(20),
	adresse varchar2(20),
	fonction_cage Tspecialites--,
	--constraint LesEmployes_C1 primary key (nomE)
)
NOT FINAL;
/

-- Definir les sous-types de temploye: tgardien et tresponsable
create type Tgardien UNDER Temploye (
	liste_cages ens_cages
);
/

create type Tresponsable UNDER Temploye (
	noAllee number(3)
);
/

-- Creation de la table lesemployes (Attention les collections des classes herites n'ont pas de "containers", c'est une exception au modèle!)
create table LesEmployes of Temploye;

-- Definir une collection libre ens_gardiens de references sur tgardien
create type ens_gardiens as table of REF Tgardien;
/

-- Definir le type tcage (dejà declarer au debut)
create type Tcage as object (
	noCage number(3),
	fonction varchar2(20),
	noAllee number(3),
	liste_gardiens ens_gardiens--,
	--constraint LesCages_C1 primary key (noCage),
	--constraint LesCages_C2 check (noCage between 1 and 999),
	--constraint LesCages_C3 check (noAllee between 1 and 999)
);
/

-- Creation de la table lescages (Les collections de classes "final" possedent des "containers")
create table LesCages of Tcage
nested table liste_gardiens store as gardiens;

-- Definir une collection libre ens_maladies de chaine de caracteres (maladie)
--create type Tmaladie as object (maladie varchar2(20));
--/
--create type ens_maladies as table of Tmaladie;
--/

create type ens_maladies as table of varchar2(20);
/

-- Creation de la table lesanimaux
create table LesAnimaux (
	nomA varchar2(20),
	sexe varchar2(13),
	type_an varchar2(15),
	fonction_cage varchar2(20),
	pays varchar2(20),
	anNais number(4),
	lacage REF Tcage,
	liste_maladies ens_maladies,
	constraint LesAnimaux_C1 primary key (nomA),
	constraint LesAnimaux_C2 check (sexe in ('femelle','male','hermaphrodite')),
	constraint LesAnimaux_C3 check (anNais >= 1900)
)
nested table liste_maladies store as maladies;






