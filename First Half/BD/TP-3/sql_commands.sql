/*
# ####################################################### #
# 3.A
# La liste des maladies de tous les animaux de l'allée 2.
#
# On récupère une collection de maladie, exemple
# d’application de DEREF().
# ####################################################### #
*/

SELECT DEREF(A.liste_maladies) 
FROM LesAnimaux A;



/*
# ###################################################### #
# 3.B
# Le nom et le type des animaux qui ont contracté la
# grippe.
#
# On récupère des tuples, application de TABLE() et
# VALUE().
# ###################################################### #
*/

SELECT A.nomA, A.type_an 
FROM LesAnimaux A, TABLE(A.liste_maladies) M 
WHERE VALUE(M.maladie)='grippe';



/*
# ###################################################### #
# 3.C
# Le nom de tous les employés qui sont gardiens.
#
# On récupère des tuples, application de « IS OF ».
# ###################################################### #
*/

SELECT E.nomE 
FROM LesEmployes E 
WHERE VALUE(E) IS OF (Tgardien);



/*
# ###################################################### #
# 3.D
# Le nom des gardiens affectés à la cage 1.
#
# Attention d’utiliser les opérateurs exploitant le
# modèle RO, et pas du SQL de base en détournant le
# modèle RO.
# ###################################################### #
*/

SELECT G.nomE 
FROM ( 
	SELECT E.* 
	FROM LesEmployes E 
	WHERE VALUE(E) IS OF (Tgardien)
	) G, TABLE(G.liste_cages) C 
WHERE C.noCage=1;



/*
# ###################################################### #
# 3.E
# Le numéro de cage et d'allée pour les cages affectées
# aux gardiens dont l'adresse est Calvi.
#
# On récupère des tuples, application de TREAT().
# Attention d’utiliser les opérateurs exploitant le
# modèle RO.
# ###################################################### #
*/

SELECT C.noCage, C.noAllee 
FROM LesCages C 
...
