#include "mem.h"
#include "common.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#define MAX_ALLOC (1<<10)

static void *allocs[MAX_ALLOC];

//-------------------------------------------------------------
// Test allocation supérieure à la taille du tableau
// test_perso
//-------------------------------------------------------------
static void test_allocation_critique() {
	int i = 0;
	while (i < MAX_ALLOC && (allocs[0] = mem_alloc(get_memory_size() + i))) {
		assert(allocs[i] == NULL);
		i++;
	}
}

//-------------------------------------------------------------
// Test désallocation de l'en-tête
// Test désallocation hors du tableau
// test_perso
//-------------------------------------------------------------
static void test_desallocation_critique() {
	int i = 0;
	void *headAlloc;
	mem_free(get_memory_adr());
	headAlloc = mem_alloc(16);
	assert(headAlloc != get_memory_adr());
	mem_free(headAlloc);
	i = 0;
	while (i < MAX_ALLOC) {
		mem_free(get_memory_adr() + get_memory_size() + i);
		mem_free(get_memory_adr() - i - 1);
		i++;
	}
}

int main(int argc, char *argv[]) {
	mem_init();
	fprintf(stderr, "Test réalisant des series d'allocations / désallocations non-réfléchies\n"
			"Définir DEBUG à la compilation pour avoir une sortie un peu plus verbeuse."
 		"\n");
	test_allocation_critique();
	test_desallocation_critique();

	// TEST OK
    printf("TEST OK!!\n");
	return 0;
}
