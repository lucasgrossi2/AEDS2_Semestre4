import java.util.*;

public class Q1 {

    public static boolean palindromo(String str){
        int tam = str.length();
        boolean check = true;
        for(int i = 0; i <= tam/2; i++){
            if(str.charAt(i) != str.charAt(tam-1-i)){
                check = false;
                break;
            }
        }
        return check;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while(!(str.equals("FIM"))){
            if(palindromo(str)){
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            str = sc.nextLine();
        }

        sc.close();
    }
}

