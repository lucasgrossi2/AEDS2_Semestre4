import java.util.*;

public class Q4{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        Random gerador = new Random();
        gerador.setSeed(4);

        

        while(!(str.equals("FIM"))){

            char primeira = (char) ('a' + (Math.abs(gerador.nextInt() % 26)));
            char segunda = (char) ('a' + (Math.abs(gerador.nextInt() % 26)));

            int tam = str.length();
            char[] result = str.toCharArray();
            for(int i = 0; i < tam; i++){
                if(result[i] == primeira){
                    result[i] = segunda;
                }
            }

            System.out.println(result);

            str = sc.nextLine();
        }

        







        sc.close();
    }


}