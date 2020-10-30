#include <stdio.h>
#include <stdlib.h>
int main(){
    int a; 
    a=5;

    int* c;
    c = &a;
    // Le cast est nécessaire puisque %p attend void*
    // sans le cast, le compilateur génère un avertissement (warning)
    printf("L'adresse de a: %p\n", (void*)c);

    int valeur_mystere = *c; 
    printf("Valeur mystère: %d\n", valeur_mystere);
    exit(0); 
}
