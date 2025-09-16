#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>


int vogal(char str[1000], int tam, int i){
    
    if(i < tam){
        char temp = tolower(str[i]);
        if(!(temp >= 'a' && temp <= 'z')){
            return 0; //achou char especial
        }
        if(temp != 'a' && temp != 'e' && temp != 'i' && temp != 'o' && temp != 'u'){
            return 0; //achou consoante
        }
        vogal(str, tam, i + 1);
    } else {
        return 1;
    }
}



int main(){
    char str[1000];

    fgets(str, 1000, stdin);
    str[strcspn(str, "\n")] = 0;

    while(strcmp(str, "FIM") != 0){
        int tam = strlen(str);
    
        
        vogal(str, tam, 0);


    }

    return 0;
}