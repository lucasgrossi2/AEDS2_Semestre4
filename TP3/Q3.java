import java.util.*;

public class Q3{

    public static void soma(int num, int soma){
        if(num > 0){
            soma = soma + num % 10;
            soma(num/10, soma);
        } else {
            System.out.println(soma);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!(input.equals("FIM"))){
            int fullNum = Integer.parseInt(input);
            soma(fullNum, 0);
            input = sc.nextLine();
        }

        sc.close();
    }
}
