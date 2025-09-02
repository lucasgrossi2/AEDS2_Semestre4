import java.util.*;

public class Q3{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();

        while(!(str.equals("FIM"))){
            char[] input = str.toCharArray();
            int tam = str.length();
            char[] output = new char[tam];

            int j = 0;

            for(int i = tam-1; i >= 0; i--){
                output[j] = input[i];
                j++;
            }

            System.out.println(output);

            str = sc.nextLine();
        }

        sc.close();
    }
}