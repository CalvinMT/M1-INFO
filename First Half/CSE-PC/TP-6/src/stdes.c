#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <stdarg.h>
#include <math.h>
#include <sys/stat.h>
#include "stdes.h"

FICHIER *stdsortie = &(struct _FICHIER_ES) {STDOUT_FILENO, 0};
FICHIER *stderreur = &(struct _FICHIER_ES) {STDERR_FILENO, 0};

/**
 * Inverse l'ordre des lettres d'une chaîne de caractères.
 * 
 * retourne : une chaîne de caractères
 */
char *invcha(char *s) {
    char *debut = s;
    char *fin = s + strlen(s) - 1;
    char c;

    while (fin > s) {
        c = *debut;
        *debut = *fin;
        *fin = c;
        debut++;
        fin--;
    }

    return s;
}

/**
 * Entier à chaîne de caractères.
 * 
 * retourne : une chaîne de caractères
 */
char* entacha(int n, char *s) {
    int i = floor(log10(abs(n))) + 1;

    if (n == 0) {
        return "0";
    }

    s[i] = '\0';
    i--;

    if (n < 0) {
        s[0] = '-';
        n = abs(n);
        i++;
    }

    while (n > 0) {
        int c = n % 10;
        if (c > 9) {
            s[i] = c - 10 + 'a';
        }
        else {
            s[i] = c + '0';
        }
        n = n / 10;
        i--;
    }

    return s;
}

/**
 * mode : L pour ouvrir en lecture
 *        E pour ouvrir en écriture
 * 
 * retourne : FICHIER* ; NULL sinon
 */
FICHIER *ouvrir(char *nom, char mode) {
    FICHIER *f;

    f = malloc(sizeof(FICHIER));

    if (mode == 'L') {
        f->fd = open(nom, O_RDONLY);
        if (f->fd < 0) {
            perreur("ERREUR : ouverture du fichier en lecture");
            free(f);
            return NULL;
        }
    }
    else if (mode == 'E') {
        f->fd = open(nom, O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
        if (f->fd < 0) {
            perreur("ERREUR : ouverture du fichier en lecture-écriture");
            free(f);
            return NULL;
        }
    }
    else {
        perreur("ERREUR : mode inconnu");
        free(f);
        return NULL;
    }
    
    f->indiceBuffer = 0;

    return f;
}

/**
 * retourne : 0 si fermeture avec succés ; -1 sinon
 */
int fermer(FICHIER *f) {
    int resultat = close(f->fd);
    free(f);
    return resultat;
}

/**
 * retourne : <= 0 si vidé avec succés; -1 sinon
 */
int vider(FICHIER *f) {
    int resultat = write(f->fd, (void *) f->buffer, f->indiceBuffer);
    f->indiceBuffer = 0;
    return resultat;
}

/**
 * p : buffer de lecture
 * taille : taille en octet des éléments à lire
 * nbElem : nombre d'élements à lire
 * f : fichier depuis lequel lire
 * 
 * retourne : nombre d'éléments lus ; -1 sinon
 */
int lire(void *p, unsigned int taille, unsigned int nbElem, FICHIER *f) {
    if (taille == 0) {
        return -1;
    }

    unsigned int nbOctetsALire = nbElem * taille;
    int nbOctetsLus_read = 1;

    while (nbOctetsALire > 0  &&  nbOctetsLus_read > 0) {
        if (nbOctetsALire < TAILLE_BUFFER - f->indiceBuffer) {
            nbOctetsLus_read = read(f->fd, (void *) (f->buffer + f->indiceBuffer), nbOctetsALire);
        }
        else {
            nbOctetsLus_read = read(f->fd, (void *) (f->buffer + f->indiceBuffer), TAILLE_BUFFER - f->indiceBuffer);
        }
        if (nbOctetsLus_read < 0) {
            perreur("ERREUR : lecture d'octets");
            return -1;
        }
        f->indiceBuffer = f->indiceBuffer + nbOctetsLus_read;
        nbOctetsALire -= nbOctetsLus_read;
        if (f->indiceBuffer == TAILLE_BUFFER  ||  nbOctetsALire == 0  ||  nbOctetsLus_read == 0) {
            for (int i=0; i<f->indiceBuffer; i++) {
                ((char *) p)[i] = f->buffer[i];
            }
            f->indiceBuffer = 0;
        }
    }

    return nbElem - (nbOctetsALire / taille);
}

/**
 * p : buffer d'écriture
 * taille : taille en octet des éléments à écrire
 * nbElem : nombre d'élements à écrire
 * f : fichier dans lequel écrire
 * 
 * retourne : nombre d'éléments écrits ; -1 sinon
 */
int ecrire(const void *p, unsigned int taille, unsigned int nbElem, FICHIER *f) {
    if (taille == 0) {
        return -1;
    }

    unsigned int nbOctetsAEcrire = nbElem * taille;
    int nbOctetsEcrits_p = 0;
    int nbOctetsEcrits_write = 0;

    while (nbOctetsAEcrire > 0) {
        nbOctetsEcrits_p = 0;
        while (nbOctetsEcrits_p < nbOctetsAEcrire  &&  nbOctetsEcrits_p < TAILLE_BUFFER) {
            f->buffer[nbOctetsEcrits_p] = ((char *) p)[nbOctetsEcrits_p];
            nbOctetsEcrits_p++;
        }
        f->indiceBuffer = f->indiceBuffer + nbOctetsEcrits_p;
        if (f->indiceBuffer == TAILLE_BUFFER  ||  nbOctetsEcrits_p == nbOctetsAEcrire) {
            nbOctetsEcrits_write = write(f->fd, (void *) f->buffer, f->indiceBuffer);
            if (nbOctetsEcrits_write < 0) {
                perreur("ERREUR : écriture d'octets");
                return -1;
            }
            f->indiceBuffer = 0;
            nbOctetsAEcrire -= nbOctetsEcrits_write;
        }
    }

    return nbElem - (nbOctetsAEcrire / taille);
}

/**
 * f : fichier depuis lequel lire
 * format : formatage de la lecture
 * 
 * retourne : nombre d'éléments lus ; -1 sinon
 */
int fliref(FICHIER *f, const char *format, ...) {
    int nbElementsLus = 0;

    va_list ap;
    char *c;
    int *d;
    char *s;

    char p;
    int nbCaracteresLus;

    int i;
    int entier;

    va_start(ap, format);

    while (*format) {
        switch (*format) {
            case '%':
                format++;
                switch (*format) {
                    case 'c':
                        c = va_arg(ap, char *);
                        nbCaracteresLus = lire(&p, 1, 1, f);
                        if (nbCaracteresLus < 0) {
                            return -1;
                        }
                        *c = p;
                        nbElementsLus++;
                        break;
                    case 'd':
                        d = (int *) va_arg(ap, char *);
                        i = 0;
                        entier = 0;
                        do {
                            nbCaracteresLus = lire(&p, 1, 1, f);
                        } while (nbCaracteresLus > 0  &&  p == ' ');
                        while (nbCaracteresLus > 0  &&  p >= '0'  &&  p <= '9') {
                            entier = entier * pow(10, i) + (p - '0');
                            nbCaracteresLus = lire(&p, 1, 1, f);
                            i++;
                        }
                        if (nbCaracteresLus < 0) {
                            return -1;
                        }
                        *d = entier;
                        nbElementsLus++;
                        break;
                    case 's':
                        s = va_arg(ap, char *);
                        i = 0;
                        do {
                            nbCaracteresLus = lire(&p, 1, 1, f);
                        } while (nbCaracteresLus > 0  &&  p == ' ');
                        while (nbCaracteresLus > 0  &&  p != ' '  &&  p != '\n') {
                            s[i] = p;
                            nbCaracteresLus = lire(&p, 1, 1, f);
                            i++;
                        }
                        if (nbCaracteresLus < 0) {
                            return -1;
                        }
                        nbElementsLus++;
                        break;
                    default:
                        perreur("ERREUR : format inconnu");
                        return -1;
                }
                break;
            default:
                break;
        }
        format++;
    }

    va_end(ap);

    return nbElementsLus;
}

/**
 * f : fichier dans lequel écrire
 * format : formatage de l'écriture
 * 
 * retourne : nombre de caractères écrits ; -1 sinon
 */
int fecriref(FICHIER *f, const char *format, ...) {
    int nbCaracteresEcrits = 0;

    va_list ap;
    char c;
    int d;
    char *s;

    int i;
    int entier;
    char espace = ' ';

    va_start(ap, format);

    while (*format) {
        switch (*format) {
            case '%':
                format++;
                while (*format == ' ') {
                    format++;
                }
                i = 0;
                entier = 0;
                while (*format >= '0'  &&  *format <= '9') {
                    entier = entier * pow(10, i) + (*format - '0');
                    i++;
                    format++;
                }
                for (i=0; i<entier; i++) {
                    ecrire(&espace, 1, 1, f);
                }
                switch (*format) {
                    case 'c':
                        c = (char) va_arg(ap, int);
                        ecrire(&c, 1, 1, f);
                        nbCaracteresEcrits++;
                        break;
                    case 'd':
                        d = va_arg(ap, int);
                        int longueur = 1;
                        if (d != 0) {
                            longueur = floor(log10(abs(d))) + 1;
                            if (d < 0) {
                                longueur++;
                            }
                        }
                        char mot[TAILLE_BUFFER];
                        ecrire(entacha(d, mot), longueur, 1, f);
                        nbCaracteresEcrits += longueur;
                        break;
                    case 's':
                        s = va_arg(ap, char *);
                        while (*s) {
                            ecrire(s, 1, 1, f);
                            nbCaracteresEcrits++;
                            s++;
                        }
                        break;
                    default:
                        perreur("ERREUR : format inconnu");
                        return -1;
                }
                break;
            default:
                ecrire(format, 1, 1, f);
                nbCaracteresEcrits++;
                break;
        }
        format++;
    }

    va_end(ap);

    return nbCaracteresEcrits;
}

/**
 * Ecrit dans le stream d'erreurs.
 */
void perreur(const char *s) {
    fecriref(stderreur, "%s\n", s);
    vider(stderreur);
}
