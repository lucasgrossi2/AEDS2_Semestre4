import java.util.*;

class MyIO {
    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }
}

public class Q5{

    public static int comparar(int[] alfabeto1, int[] alfabeto2){
        for(int i = 0; i < 26; i++){
            if(alfabeto1[i] != alfabeto2[i]){
                return 0;
            }
        }
        return 1;
    }


    public static void encherArray(String str, int[] alfabeto) {
        int tam = str.length();
        for (int i = 0; i < tam; i++) {
            char c = Character.toLowerCase(str.charAt(i));
            if (c >= 'a' && c <= 'z') {
                alfabeto[c - 'a']++;
            }
        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String first = sc.next();

        while(!(first.equals("FIM"))){
            String dash = sc.next();
            String second = sc.next();

            int[] alfabeto1 = new int[26];
            int[] alfabeto2 = new int[26];
            
            encherArray(first, alfabeto1);
            encherArray(second, alfabeto2);

            if(comparar(alfabeto1, alfabeto2) == 1){
                MyIO.println("SIM");
            } else {
                MyIO.println("NÃO");
            }

            first = sc.next();
        }
        
    }

}