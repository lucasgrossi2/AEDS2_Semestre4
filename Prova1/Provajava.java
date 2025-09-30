import java.util.*;

public class Provajava {

    static class Pais {
        String nome;
        int ouro;
        int prata;
        int bronze;

        public Pais(String nome, int ouro, int prata, int bronze) {
            this.nome = nome;
            this.ouro = ouro;
            this.prata = prata;
            this.bronze = bronze;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numPaises = sc.nextInt();
        Pais[] paises = new Pais[numPaises];

        for (int i = 0; i < numPaises; i++) {
            String inputName = sc.next();
            int inputOuro = sc.nextInt();
            int inputPrata = sc.nextInt();
            int inputBronze = sc.nextInt();
            paises[i] = new Pais(inputName, inputOuro, inputPrata, inputBronze);
        }

       
        for (int i = 0; i < numPaises; i++) {
            int maior = i;
            for (int j = i + 1; j < numPaises; j++) {
                if (paises[maior].ouro < paises[j].ouro) {
                    maior = j;
                } else if (paises[maior].ouro == paises[j].ouro) {
                    if (paises[maior].prata < paises[j].prata) {
                        maior = j;
                    } else if (paises[maior].prata == paises[j].prata) {
                        if (paises[maior].bronze < paises[j].bronze) {
                            maior = j;
                        } else if (paises[maior].bronze == paises[j].bronze &&
                                   paises[maior].nome.compareTo(paises[j].nome) > 0) {
                            maior = j;
                        }
                    }
                }
            }
          
            Pais temp = paises[i];
            paises[i] = paises[maior];
            paises[maior] = temp;
        }

    
        for (int k = 0; k < numPaises; k++) {
            System.out.println(paises[k].nome + " " +
                               paises[k].ouro + " " +
                               paises[k].prata + " " +
                               paises[k].bronze);
        }

        sc.close();
    }
}



