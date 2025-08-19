import java.util.*;

public class sorvete {
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);

        int comprimento = sc.nextInt();
        int sorveteiros = sc.nextInt();

        int[] intervalos = new int[sorveteiros*2];

        for(int i = 0; i < sorveteiros*2; i++){
            intervalos[i] = sc.nextInt();
        }




        sc.close();
    }

}