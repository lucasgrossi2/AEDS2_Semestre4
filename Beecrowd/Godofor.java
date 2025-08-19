import java.util.*;

class Deus {
    String nome;
    int poder;
    int abates;
    int mortes;

    public Deus(String nome, int poder, int abates, int mortes){
        this.nome = nome;
        this.poder = poder;
        this.abates = abates;
        this.mortes = mortes;
    }
}

public class Godofor {

    public static int comparar(Deus a, Deus b){
        if(a.poder > b.poder){
            return -1;
        } else if(b.poder > a.poder){
            return 1;
        } else {
            if(a.abates > b.abates){
                return -1;
            } else if(b.abates > a.abates){
                return 1;
            } else {
                if(a.mortes > b.mortes){
                    return -1;
                } else if(b.mortes > a.mortes){
                    return 1;
                } else {
                    return 0;
                }
            }
            
        }
    }
    
    public static void ordenar(Deus[] instancia){
        for(int i = 1; i < instancia.length; i++){
            Deus tmp = instancia[i];
            int j = i-1;
            while(j >= 0 && comparar(instancia[j], tmp) > 0){
                instancia[j+1] = instancia[j];
                j--;
            }
            instancia[j+1] = tmp;
        }
    }

    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        int num_deuses = sc.nextInt();

        Deus[] deuses = new Deus[num_deuses];

        for(int i = 0; i < num_deuses; i++){
            String nome = sc.next();
            int poder = sc.nextInt();
            int abates = sc.nextInt();
            int mortes = sc.nextInt();
            deuses[i] = new Deus(nome, poder, abates, mortes);
        }

        ordenar(deuses);
        System.out.println(deuses[0].nome);
        
    }
}

