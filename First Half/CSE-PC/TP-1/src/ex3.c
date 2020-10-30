#include<stdio.h>
#include<stdlib.h>
int main() {
	int *pa = 2;
	pa = 34;
	int b = 4, *pb = &b;
	*pb = 5;
	int *pc;
	printf("pc is equal to %p\n", pc);
	printf("*pc is equal to %d\n", *pc); 
	pc = malloc(sizeof(int));
	*pc = -2;
        printf("pc is equal to %p\n", pc);
        printf("*pc is equal to %d\n", *pc);
	pa = pc;
	free(pa);
	printf("pc is equal to %p\n", pc);
	printf("*pc is equal to %d\n", *pc);
	*pc = -4;
	pc = -4;
	pa = malloc(sizeof(int));
        *pa = -2;
	pc = pa;
	printf("pc is equal to %p\n", pc);
        printf("*pc is equal to %d\n", *pc);
}
