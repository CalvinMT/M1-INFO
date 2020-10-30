#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int op1;
  int op2;
  float param;
  int bool;
} mystruct_t;

int main(){
    int a=5, b=10; 
    int arr_int[2];
    mystruct_t arr_mystruct[5];
    
    int* c = arr_int;
    *c = a;
    *(c+1) = b;
    
    mystruct_t ms = {1, 2, 0.10, 0};
    mystruct_t* s = arr_mystruct;
    for (int i = 0; i < 5; i++) {
       *(s+i) = ms;
       ms.op1+=1;
    }
    printf("Element 3 {%d, %d, %f, %d}\n", 
           (s+2)->op1, (s+2)->op2, (s+2)->param, (s+2)->bool);
    
    printf("int_arr[0]=%d\n", *c);
    printf("int_arr[1]=%d\n",*(c+1));
    *(int*)((char*)c+2)=2;
    printf("int_arr[0]=%d\n", *c);
    printf("int_arr[1]=%d\n",*(c+1));

    *(mystruct_t*)c=ms;
   printf("%d\n",((mystruct_t*)c)->op1);
    exit(0); 
}
