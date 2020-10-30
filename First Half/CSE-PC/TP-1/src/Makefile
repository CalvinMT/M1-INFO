# Auteurs : Thomas Ropars et Renaud Lachaize pour OS MOSIG 2018-2019
# Adapté les commentaires et traduit en français : V.Marangozova-Martin

# Ce makefile montre quelques techniques de base pour définir les règles
# de compilation C. N'hésitez pas à lire et à apprendre!.

# Pour voir ce que fait cette commande barbare, lire les explications qui viennent à la suite,
# la réponse est à la fin du fichier.
.SUFFIXES:

#--------------------------------------------------------------------------
# INTRO GENERALE
#--------------------------------------------------------------------------
# Dans un makefile, on définit des règles de compilation qui ont toutes
# la même structure
#
# cible: dépendences
# 	actions à effectuer
#
# où
# - la cible est le fichier que nous voulons générer
# - les dépendences sont les fichiers dont nous avons besoin pour le générer
# - les actions définissent ce que nous allons faire avec les dépendences pour
#   générer la cible
#
# De manière standard, les exécutables C (les binaires), n'ont pas d'extension.
# Ceci n'est pas très pratique quand vous voulez nettoyer i.e. effacer les binaires
# puisque cela vous force à connaître leurs noms exacts.
# Dans ce qui suit, les exécutables générés ont l'extension .run.
# Par exemple, si on a un fichier ex.c qui contient la fonction main,
# la compilation standard générera un exécutable nommé ex.
# Ici, il s'appelera ex.run
#--------------------------------------------------------------------------


# Pour compiler un seul programme: 'make my_prog.run'
# Pour compiler tous les programmes: 'make all'

CC=gcc
CFLAGS=-W -Wall -pedantic -g -std=c99 
LDFLAGS=


EXEC=ex2_td.run ex3_td.run ex1.run ex2.run rec.run prog_0.run prog_1.run prog_2.run prog_3.run prog_4.run

# Dans le cas où chaque fichier .c définit un programme à part,
# il est possible de définir la variable EXEC de la manière suivante
# en utilisant la fonction wildcard  pour la substitution de suffixes. 
# SRC=$(wildcard *.c)
# EXEC=$(SRC:.c=.run)


#générer tous les exécutables
all: $(EXEC)

# Règles de base pour compiler ex1.c et générer ex1.run
# ici utilisation uniquement de la variable CC définie plus haut
# Les variables sont manipulées comme dans les environnements shell.
ex1.run: ex1.o
	$(CC) -o ex1.run ex1.o

ex1.o: ex1.c
	$(CC) -c ex1.c $(CFLAGS)


# Dans les Makefiles il est possible d'utiliser des variables automatiques
# qui simplifient l'écriture des règles 
# pour plus de détails https://www.gnu.org/software/make/manual/make.html#Automatic-Variables

# exemple with ex2.run
ex2.run: ex2.o
	$(CC) -v -o $@ $<

ex2.o: ex2.c
	$(CC) -c $< $(CFLAGS)


# Heureusement, il n'est pas nécessaire d'écrire ce genre de règles pour chaque
# exécutable que nous voulons générer (imaginez vous que vous ayez 100 ou 1000
# petits exemples comme ceux-là)!
# Il est possible donc de définir des règles génériques en utilisant des motifs statiques
# voir https://www.gnu.org/software/make/manual/make.html#Static-Pattern
%.run: %.o
	$(CC) -o $@ $<

%.o: %.c
	$(CC) -c $< $(CFLAGS)


# a la fin, on définit une règle pour nettoyer
clean:
	rm -rf *.o *.run

# la plupart des utilitaires make viennent avec des règles implicites
# Typiquement, les règles génériques ci-dessus, n'ont pas besoin d'être écrites!
# Pour désactiver les règles implicites, mettre en début de Makefile
# .SUFFIXES:
