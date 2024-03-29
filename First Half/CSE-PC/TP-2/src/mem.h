#if !defined(__MEM_H)
#define __MEM_H
#include <stddef.h>

/* -----------------------------------------------*/
/* Interface utilisateur de votre allocateur      */
/* -----------------------------------------------*/
void mem_init(void);
void* mem_alloc(size_t);
void mem_free(void*);
size_t mem_get_size(void *);

/* Itérateur sur la liste chaînée des zones libres */
void mem_show_free();

/* Itérateur sur le contenu de l'allocateur */
void mem_show(void (*print)(void *, size_t, int free));

#endif
