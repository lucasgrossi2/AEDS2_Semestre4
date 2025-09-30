#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int somador(char num[100], int tam, int index, int soma){
    if(index < tam){
        int digito = num[index] - '0';
        soma = soma + digito;
        somador(num, tam, index + 1, soma);
    } else {
        return soma;
    }
}


int main(){

    char num[100];

    scanf("%s", num);

    while(strcmp(num, "FIM") != 0){
        int tam = strlen(num);
        printf("%d\n", somador(num, tam, 0, 0));
        scanf("%s", num);
    }

    return 0;
}