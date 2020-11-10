#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/time.h>
#include <getopt.h>
#include <sys/resource.h>
#include <math.h>

#include "algo_principal.h"
#include "temps.h"
#include "commun.h"

void usage(char *commande) {
    fprintf(stderr, "Usage :\n");
    fprintf(stderr, "%s [ --parallelism number ] [ --quiet ] [ --time ] "
                    "[ --rusage ] [ --arg argument ] [ --help ]\n\n",
                    commande);
    fprintf(stderr, "Ce programme lit sur son entree standard un vecteur "
                    "a traiter. Il accepte comme options --parallelism qui "
                    "indique le nombre de threads/processus a creer (un seul "
                    "par defaut), --quiet qui supprime les affichages "
                    "superflus, --time qui affiche le temps total passe "
                    "dans l'algorithme principal, --rusage qui affiche "
                    "le temps d'utilisation des resources attribue aux "
                    "differents threads/processus et --arg qui permet de "
                    "transmettre un argument à l'algorithme execute.\n");
    exit(1);
}

int quiet=0;



void afficherVecteur(int *tableau, int taille) {
  affiche("Tableau trie :\n");
  for (int i=0; i<taille; i++)
      affiche("%d ", tableau[i]);
  affiche("\n");
}



void calculerTemps(int parallelism, int *tableau, int taille, char *arg) {
    // Temps de début
    struct timeval temps_debut;
    if (gettimeofday(&temps_debut, NULL) < 0) {
        fprintf(stderr, "Erreur temps de début : \n");
    }

    /* Algo */
    algo_principal(parallelism, tableau, taille, arg);

    // Temps de fin
    struct timeval temps_fin;
    if (gettimeofday(&temps_fin, NULL) < 0) {
        fprintf(stderr, "Erreur temps de fin\n");
    }

    // Temps total en microseconde
    int sec = temps_fin.tv_sec - temps_debut.tv_sec;
    int micro_sec = temps_fin.tv_usec - temps_debut.tv_usec;
    int temps_total = sec * pow(10, 6) + micro_sec;
    printf("%d\n", temps_total);
}



void calculerRessources(int parallelism, int *tableau, int taille, char *arg) {
    struct rusage usage;

    // Temps de début
    if (getrusage(RUSAGE_SELF, &usage) < 0) {
        fprintf(stderr, "Erreur temps de début : \n");
    }
    struct timeval temps_debut = usage.ru_stime;

    /* Algo */
    algo_principal(parallelism, tableau, taille, arg);

    // Temps de fin
    if (getrusage(RUSAGE_SELF, &usage) < 0) {
        fprintf(stderr, "Erreur temps de fin\n");
    }
    struct timeval temps_fin = usage.ru_stime;

    // Temps total en microseconde
    int sec = temps_fin.tv_sec - temps_debut.tv_sec;
    int micro_sec = temps_fin.tv_usec - temps_debut.tv_usec;
    int temps_total = sec * pow(10, 6) + micro_sec;
    printf("%d\n", temps_total);
}



int main(int argc, char *argv[]) {
    int opt, parallelism = 1;
    int taille, i, temps = 0, ressources = 0;
    int *tableau;
    char *arg=NULL;
    
    struct option longopts[] = {
        { "help", required_argument, NULL, 'h' },
        { "parallelism", required_argument, NULL, 'p' },
        { "quiet", no_argument, NULL, 'q' },
        { "time", no_argument, NULL, 't' },
        { "rusage", no_argument, NULL, 'r' },
        { "arg", required_argument, NULL, 'a' },
        { NULL, 0, NULL, 0 }
    };

    while ((opt = getopt_long(argc, argv, "hp:qrta:", longopts, NULL)) != -1) {
        switch (opt) {
          case 'p':
            parallelism = atoi(optarg);
            break;
          case 'q':
            quiet = 1;
            break;
          case 'r':
            ressources = 1;
            break;
          case 't':
            temps = 1;
            break;
          case 'a':
            arg = optarg;
            break;
          case 'h':
          default:
            usage(argv[0]);
        }
    }
    argc -= optind;
    argv += optind;

    affiche("Saisissez la taille du vecteur\n");
    scanf(" %d", &taille);
    tableau = (int *) malloc(taille*sizeof(int));
    if (tableau == NULL) {
        fprintf(stderr,"Erreur de malloc\n");
        exit(3);
    }
    affiche("Saisissez tous les elements du vecteur\n");
    for (i=0; i<taille; i++)
        scanf(" %d", &tableau[i]);

    if (temps) {
      calculerTemps(parallelism, tableau, taille, arg);
    }
    else if (ressources) {
      calculerRessources(parallelism, tableau, taille, arg);
    }

    // Affiche le vecteur trié
    afficherVecteur(tableau, taille);

    return 0;
}
