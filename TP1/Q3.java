import java.util.*;

public class Q3 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();

        while(!(str.equals("FIM"))){

            int tam = str.length();
            char[] result = str.toCharArray();

            for(int i = 0; i < tam; i++){
                if(result[i] != '\uFFFD'){
                    char temp = (char) (str.charAt(i) + 3);
                    result[i] = temp;
                }
            }

            System.out.println(result);
            str = sc.nextLine();
        }




        sc.close();
    }
}