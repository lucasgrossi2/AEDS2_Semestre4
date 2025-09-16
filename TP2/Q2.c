#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int vogal(char str[1000]){
    int tam = strlen(str);
    
    for(int i = 0; i < tam; i++){
        char temp = tolower(str[i]);
        if(!(temp >= 'a' && temp <= 'z')){
            return 0; //achou char especial
        }
        if(temp != 'a' && temp != 'e' && temp != 'i' && temp != 'o' && temp != 'u'){
            return 0; //achou consoante
        }
        
    }
    return 1; //so vogais
}

int consoante(char str[1000]){
    int tam = strlen(str);
    
    for(int i = 0; i < tam; i++){
        char temp = tolower(str[i]);

        if(!(temp >= 'a' && temp <= 'z')){
            return 0; //achou char especial
        }
        if (temp != 'b' && temp != 'c' && temp != 'd' && temp != 'f' && temp != 'g' &&
            temp != 'h' && temp != 'j' && temp != 'k' && temp != 'l' && temp != 'm' &&
            temp != 'n' && temp != 'p' && temp != 'q' && temp != 'r' && temp != 's' &&
            temp != 't' && temp != 'v' && temp != 'w' && temp != 'x' && temp != 'y' &&
            temp != 'z') {
            return 0; //achou vogal
        }
        
    }
    return 1; //so consoante
}

int inteiro(char str[1000]){
    int tam = strlen(str);
    for(int i = 0; i < tam; i++){
        char temp = str[i];
        if(temp != '1' && temp != '2' && temp != '3' && temp != '4' && temp != '5' && temp != '6' && temp != '7' && temp != '8' && temp != '9' && temp != '0'){
            return 0; //achou letra
        }
    }
    return 1; //so numero
}

int real(char str[1000]){
    int num_virgs = 0;
    int tem_digito = 0;
    int tam = strlen(str);

    for(int i = 0; i < tam; i++){
        char temp = str[i];

        if (temp == '.' || temp == ',') {
            num_virgs++;
            if (num_virgs > 1) return 0;
        } else if (isdigit(temp)) {
            tem_digito = 1;
        } else {
            return 0; // caractere inválido
        }
    }

    if (!tem_digito) return 0; // não tem nenhum número

    return 1;
}


int main(){

    char str[1000];

    fgets(str, 1000, stdin);
    str[strcspn(str, "\n")] = 0;

    while(strcmp(str, "FIM") != 0){
        if(vogal(str) == 1){
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if(consoante(str) == 1){
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if(inteiro(str) == 1){
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if(real(str) == 1){
            printf("SIM");
        } else {
            printf("NAO");
        }

        printf("\n");

        fgets(str, 1000, stdin);
        str[strcspn(str, "\n")] = 0;
    }
    return 0;
}