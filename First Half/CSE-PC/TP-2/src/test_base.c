#include "mem.h"
#include "common.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#define MAX_ALLOC (1<<10)
#define NB_TESTS 10

static void *allocs[MAX_ALLOC];

static int make_test() {
	int nb_alloc = 0;
	int i=0;
	// On remplit la mémoire de blocs de taille croissante
	debug("Issuing a sequence of size increasing mallocs, starting from 0\n");
	while ((i<MAX_ALLOC) && ((allocs[i] = mem_alloc(i)) != NULL)) {
		debug("Nouveau i : %d\n", i);
		i++;
	}
	i--;
	debug("Alloced up to %d bytes at %p\n", i, allocs[i]);
	nb_alloc = i;
	// On vide
	debug("Freeing all allocated memory\n");
	while (i>=0) {
		mem_free(allocs[i]);
		debug("Freed i : %d\n", i);
		i--;
	}
	return nb_alloc;
}

int main(int argc, char *argv[]) {
	int nb_alloc;
	int nb_make_test = 0;
	nb_alloc = 0;
	mem_init();
	fprintf(stderr, "Test réalisant des series d'allocations / désallocations en ordre LIFO\n"
			"Définir DEBUG à la compilation pour avoir une sortie un peu plus verbeuse."
 		"\n");
	nb_alloc = make_test();
	for (int i=0; i<NB_TESTS; i++) {
		// Teste si non idempotent !
		nb_make_test = make_test();
		debug("\n%d == %d\n", nb_make_test, nb_alloc);
		assert (nb_make_test == nb_alloc);
	}

	// TEST OK
    printf("TEST OK!!\n");
	return 0;
}
