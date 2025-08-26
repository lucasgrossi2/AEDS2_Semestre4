#include <stdlib.h>
#include <stdio.h>
#include <string.h>


int palindromo(char str[100], int dir, int esq){
    if(dir >= esq){
        if(str[dir] != str[esq]){
            return 0;
        } else {
            palindromo(str, dir-1, esq+1);
        }
    } else {
        return 1;
    }

    
}


int main(){

    char str[100];
    scanf("%s", str);
    while(strcmp(str, "FIM")){
        int i = 0;
        while(str[i] != '\0'){
            i++;
        }
        int tam = i;
        int bool = palindromo(str, tam-1, 0);
        if(bool == 1){
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
        scanf("%s", str);
    }



    return 0;
}