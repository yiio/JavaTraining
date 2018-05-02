package jp.co.goalist.array;

public class Q2 {
    public static void main(String[] args) {

        int[] kuku = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(kuku[i] * kuku[j]);

            }
            System.out.println("");
        }

    }
}