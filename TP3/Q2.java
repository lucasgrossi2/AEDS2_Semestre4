import java.util.*;

public class Q2{

    public static void inversao(String input, char[] invertida, int i, int indexEnd, int tam){
        if(i < tam){
            invertida[i] = input.charAt(indexEnd);
            i++;
            indexEnd--;
            inversao(input, invertida, i, indexEnd, tam);
        } else {
            System.out.println(invertida);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while(!(input.equals("FIM"))){
            int tam = input.length();
            char[] invertida = new char[tam];
            inversao(input, invertida, 0, tam-1, tam);
            input = sc.nextLine();
        }

        sc.close();
    }

}