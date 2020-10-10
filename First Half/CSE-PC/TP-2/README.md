# Contenu de l'archive pour le TP Allocateur mémoire.

Cette archive contient :
*  le sujet du TP : TD-TP-Memoire.pdf
*  le compte rendu du TP : MASSONNET PERRIER - CSE-PC - TP2 - Allocateur Mémoire.pdf
*  les sources de l'allocateur : src/

## Contenu de src/
- `mem.h` et `mem_os.h` : l'interface de votre allocateur. 
  `mem.h` définit les fonctions utilisateur (`mem_alloc`, `mem_free`), 
   alors que `mem_os.h` définit les fonctions définissant la stratégie d'allocation
- `common.h` et `common.c` : définissent la mémoire à gérer et des fonctions utilitaires pour conna^tre sa taille et son adresse de début.
  Ces fonctions seront utilisées dans la fonction `mem_init`.
- `memshell.c` ; un interpreteur simple de commandes d'allocation et de libération 
   vous permettant de tester votre allocateur de manière intéractive
-`mem.c` : le code principal de l'allocateur
- `malloc_stub.h` et `malloc_stub.c` : utilisés pour la génération d'une bibliothèque permettant
  de remplacer la `libc` et de tester l'allocateur avec des programmes existant standard
- des fichiers de test : `test_*.c`
- `Makefile` simple
- des exemples de séquences courtes d'allocations et de libérations `alloc*.in`. 
  Vous pouvez les passer en redirigeant l'entrée de votre memshell.

## Compilation et exécution
Pour compiler le code rendu sous une plateforme Linux, il suffit d’ouvrir une console, 
de se placer dans le répertoire du code source dans lequel le Makefile est situé et de 
lancer la commande : 
    make

Une fois le code compilé, il sera possible de lancer le memshell, ainsi que différents 
tests parmi lesquels les tests donnés ainsi que le test_critique pourront être trouvés 
en prétextant l’exécutable voulu d’un point suivi d’un slash avant : 
    ./memshell
    ./test_init
    ./test_base
    ./test_cheese
    ./test_fusion
    ./test_critique
