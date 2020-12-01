#ifndef _STDES_H
#define _STDES_H

#define TAILLE_BUFFER 4096

typedef struct _FICHIER_ES {
    int fd;
    int indiceBuffer;
    char buffer[TAILLE_BUFFER];
} FICHIER;

FICHIER *stdsortie;
FICHIER *stderreur;

#define ecriref(format, ...) fecriref(stdsortie, format, ##__VA_ARGS__)

FICHIER *ouvrir(char *nom, char mode);
int fermer(FICHIER *f);
int vider(FICHIER *f);
int lire(void *p, unsigned int taille, unsigned int nbElem, FICHIER *f);
int ecrire(const void *p, unsigned int taille, unsigned int nbElem, FICHIER *f);

int fliref(FICHIER *f, const char *format, ...);
int fecriref(FICHIER *f, const char *format, ...);

void perreur(const char *s);

#endif
