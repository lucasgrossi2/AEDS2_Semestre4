import java.util.*;

public class SortingSanta {

    public static void ordenar(String[] str, int tam){
        
        for(int i = 0; i < tam - 1; i++){
            int indexMenor = i;
            for(int j = i + 1; j < tam; j++){
                if(str[j].compareTo(str[indexMenor]) < 0){
                    indexMenor = j;
                }
            }
            String temp = str[indexMenor];
            str[indexMenor] = str[i];
            str[i] = temp;
        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int numCriancas = sc.nextInt();
        int boas = 0;
        int malvadas = 0;
        String[] criancas = new String[numCriancas];

        for(int i = 0; i < numCriancas; i++){
            String comportamento = sc.next();
            char[] comp = comportamento.toCharArray();
            if(comp[0] == '+'){
                boas++;
            } else if(comp[0] == '-') {
                malvadas++;
            }
            criancas[i] = sc.next();
        }

        ordenar(criancas, numCriancas);

        for(int k = 0; k < numCriancas; k++){
            System.out.println(criancas[k]);
        }
        System.out.println("Se comportaram: " + boas + " | Nao se comportaram: " + malvadas);

        sc.close();
    }
}
