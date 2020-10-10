
#include "mem.h"
#include "mem_os.h"
#include "common.h"
#include <stdio.h>
#include <assert.h>
/* La taille prends en compte l'entete du bloc */


/* fb pour free block */
typedef struct fb {
	size_t size;
	struct fb *next;
	/* ... */
} fb;

typedef struct bb {
	size_t size;
	/* ... */
} bb;

typedef struct entete {
	struct fb *tete;

	//Pour se souvenir de la fonction
	mem_fit_function_t* fc;
} entete;

//-------------------------------------------------------------
// getter
//-------------------------------------------------------------
entete* getHead() {
	return (entete*) get_memory_adr();
}

//-------------------------------------------------------------
// mem_init
//-------------------------------------------------------------
void mem_init() {
	entete* premice = (entete*) get_memory_adr();

	//&memory[0] (get_memory_adr()) OU (char*)premice
	fb* t = (fb*)((char*) get_memory_adr() + sizeof(entete));
	t->size = get_memory_size() - sizeof(entete);
	t->next = NULL;

	premice->tete = t;

	// --------------------------------------------------------
	// Stratégies d'allocation possibles : 
	//		- mem_first_fit
	//		- mem_best_fit
	//		- mem_worst_fit
	premice->fc = mem_first_fit;
	// --------------------------------------------------------
}

//-------------------------------------------------------------
// mem_alloc
//-------------------------------------------------------------
void* mem_alloc(size_t size) {
	//J'appelle la fonction sauvegardée
	fb* firstFreeBloc = getHead()->fc(getHead()->tete, size);

	//Si la mémoire peut être allouée
	if (firstFreeBloc != NULL) {
		size_t firstFreeBlocSize = firstFreeBloc->size;
		//Les deux prochaines variables enregistrent les valeurs qui 
		//peuvent être effacées si 'size' est trop petit (< 8).
		fb* firstFreeBlocNext = firstFreeBloc->next;
		fb* tete = getHead()->tete;
		//Cas particulier : 
		//Si firstFreeBloc est le premier bloque vide, 
		// - soit la place restante après allocation devient le premier bloque vide, 
		// - soit le second bloque vide devient le premier bloque vide (si place restante insuffisante).
		if (tete == firstFreeBloc) {
			if (tete->size - size - sizeof(fb) >= sizeof(fb) + sizeof(int)) {
				getHead()->tete = (fb*)((char*)firstFreeBloc + size + sizeof(fb));
				//Si le nouveau premier bloque vide est à l'intérieur du tableau.
				if ((char*)getHead()->tete < (char*)((char*)get_memory_adr() + get_memory_size())) {
					getHead()->tete->size = firstFreeBlocSize - size - sizeof(fb);
					getHead()->tete->next = firstFreeBlocNext;
				}
				//Si le nouveau premier bloque vide est à l'extérieur du tableau, 
				//il n'y a plus de bloque vide (mémoire pleine).
				else {
					getHead()->tete = NULL;
				}
			}
			else {
				getHead()->tete = firstFreeBloc->next;
				//La taille allouée prend en compte la taille restante insuffisante.
				size += tete->size - size - sizeof(fb);
			}
		}
		//Si firstFreeBloque n'est pas le premier bloque vide, 
		//tant que le prochain bloque vide de l'indice (tete) n'est pas firstFreeBloc, 
		//l'indice (tete) devient le prochain bloque vide.
		else {
			while (tete->next != firstFreeBloc) {
				tete = tete->next;
			}
			//On sort de la boucle, donc le prochain bloque vide de l'indice (tete) est firsFreeBloc, 
			// - soit la place restante après allocation devient le premier bloque vide, 
			// - soit le second bloque vide devient le premier bloque vide (si place restante insuffisante).
			if (firstFreeBloc->size - size - sizeof(fb) >= sizeof(fb) + sizeof(int)) {
				tete->next = (fb*)((char*)firstFreeBloc + size + sizeof(fb));
				tete->next->size = firstFreeBlocSize - size - sizeof(fb);
				tete->next->next = firstFreeBlocNext;
			}
			else {
				tete->next = firstFreeBloc->next;
				//La taille allouée prend en compte la taille restante insuffisante.
				size += tete->size - size - sizeof(fb);
			}
		}

		//On prépare l'allocation du bloque avant de le renvoyer en
		//en écrivant sa taille.
		bb* toReturn = (bb*)firstFreeBloc;
		toReturn->size = size + sizeof(fb);

		//On renvoie l'adresse de l'allocation en omettant l'entête.
		return (bb*)((char*)toReturn + sizeof(bb));
	} else {
		//On a reçu NULL, on retourne NULL
		return NULL;
	}
}

//-------------------------------------------------------------
// Vérifie si des bloques vides sont contigus. Si oui, les fusionner.
// mem_free_fusion
//-------------------------------------------------------------
void mem_free_fusion() {
	fb* blocPrecedent = getHead()->tete;
	fb* blocSuivant = blocPrecedent->next;
	while (blocSuivant != NULL) {
		if ((char*)blocPrecedent + blocPrecedent->size == (char*)blocSuivant) {
			blocPrecedent->size += blocSuivant->size;
			blocPrecedent->next = blocSuivant->next;
		}
		else {
			blocPrecedent = blocSuivant;
		}
		blocSuivant = blocSuivant->next;
	}
}

//-------------------------------------------------------------
// mem_free
//-------------------------------------------------------------
void mem_free(void* zone) {
	//Si la zone à libérer est en dehors du tableau (plus l'en-tête), 
	//on annule la libération de la zone.
	if ((char*)zone < (char*)(get_memory_adr() + sizeof(getHead())) || 
		(char*)zone >= (char*)(get_memory_adr() + get_memory_size())) {
		return;
	}

	//Je récupère le pointeur bb de la zone
	bb* zoneToFree = (bb*)((char*)zone - sizeof(bb));

	//Je récupère la taille de la zone
	size_t totalSize = zoneToFree->size;

	//Je récupère la tête
	fb* blocPrecedent = getHead()->tete;
	fb* blocSuivant = NULL;

	//Si la pile est complète : tete de l'entete en dehors du tableau
	if (getHead()->tete == NULL) {
		//La pile est complète
		getHead()->tete = (fb*)zoneToFree;
		getHead()->tete->size = totalSize;
		getHead()->tete->next = NULL;
	} else {
		//On est dans le tableau et on cherche zoneToFree.
		//Si zoneTofree est avant le premier bloque vide, 
		//zoneToFree devient le premier bloque vide.
		if ((char*)blocPrecedent > (char*)zoneToFree) {
			getHead()->tete = (fb*)zoneToFree;
			getHead()->tete->size = totalSize;
			getHead()->tete->next = blocPrecedent;
		}
		//Si zoneToFree est après le premier bloque vide, 
		//tant que bloqueSuivant est avant zoneToFree, 
		//bloquePrecedent devient bloqueSuivant, qui lui-même devient son suivant.
		else if ((char*)blocPrecedent < (char*)zoneToFree) {
			blocSuivant = blocPrecedent->next;
			while (blocSuivant != NULL && (char*)blocSuivant < (char*)zoneToFree) {
				blocPrecedent = blocSuivant;
				blocSuivant = blocSuivant->next;
			}
			//On sort de la boucle, donc zoneToFree est avant bloqueSuivant 
			//et après bloquePrecedent.
			blocPrecedent->next = (fb*)zoneToFree;
			blocPrecedent->next->size = totalSize;
			blocPrecedent->next->next = blocSuivant;
			//On obtient donc : bloquePrecedent -> zoneToFree -> bloqueSuivant
		}
		else {
			return;
		}
		
		mem_free_fusion();
	}
}

//-------------------------------------------------------------
// Affiche l'entête et les zones libres de la mémoire suivies de leur taille.
// [[E:s][f:s]...]
// E : entête
// f : zone libre
// s : taille de la zone
// mem_show_free
//-------------------------------------------------------------
void mem_show_free() {
	printf("[[E:%ld]", sizeof(entete));
	fb* index = getHead()->tete;
	while (index != NULL) {
		printf("[f:%ld]", index->size);
		index = index->next;
	}
	printf("]\n");
}

//-------------------------------------------------------------
// Itérateur(parcours) sur le contenu de l'allocateur
// mem_show
//-------------------------------------------------------------
void mem_show(void (*print)(void *, size_t, int free)) {
	void* index = (char*)getHead() + sizeof(entete);
	fb* indexFree = getHead()->tete;
	int sizeLeftToProcess = get_memory_size() - sizeof(entete);
	debug("sizeLeftToProcess : %d\n", sizeLeftToProcess);
	while (sizeLeftToProcess > 0) {
		if ((fb*)index == indexFree) {
			print(indexFree, indexFree->size, 1);
			sizeLeftToProcess -= indexFree->size;
			debug("sizeLeftToProcess : %d\n", sizeLeftToProcess);
			index = (char*)index + indexFree->size;
			indexFree = indexFree->next;
		}
		else {
			print(index, ((bb*)index)->size, 0); 
			sizeLeftToProcess -= ((bb*)index)->size;
			debug("sizeLeftToProcess : %d\n", sizeLeftToProcess);
			index = (char*)index + ((bb*)index)->size;
		}
	}
}

//-------------------------------------------------------------
// mem_fit
//-------------------------------------------------------------
void mem_fit(mem_fit_function_t* mff) {
	getHead()->fc = mff;
}

//-------------------------------------------------------------
// Stratégies d'allocation
//-------------------------------------------------------------
struct fb* mem_first_fit(struct fb* head, size_t size) {
	//On parcours du début à la fin tant qu'on a pas un trou assez grand
	//On récupère la tête
	fb* toReturn = head;

	//Tant qu'il y a un suivant et que le trou n'est pas assez grand
	while(toReturn != NULL && toReturn->size < (size + sizeof(fb))) {
		toReturn = toReturn->next;
	}

	//Condition de sortie de boucle
	//toReturn = NULL : On n'a pas trouvé d'endroit libre
	//toReturn->size >= size + sizeof(bb) -> Taille désirée
	//Donc un return est soit null soit il correspond au bloc courant

	//Si on a trouvé un bloc correspondant hors du tableau on renvoit null
	return toReturn;
}
//-------------------------------------------------------------
struct fb* mem_best_fit(struct fb* head, size_t size) {
	//Meilleur trou possible
	fb* index = head;
	fb* result = head;

	while (index != NULL) {
		if (index->size == size + sizeof(fb)) {
			result = index;
			index = NULL;
		}
		else if (index->size > size + sizeof(fb) && index->size < result->size) {
			result = index;
			index = index->next;
		}
		else {
			index = index->next;
		}
	}

    return result;
}
//-------------------------------------------------------------
struct fb* mem_worst_fit(struct fb* head, size_t size) {
	//Pire trou possible
	fb* index = head;
	fb* result = head;

	while (index != NULL) {
		if (index->size > size + sizeof(fb) && index->size > result->size) {
			result = index;
			index = index->next;
		}
		else {
			index = index->next;
		}
	}

    return result;
}
