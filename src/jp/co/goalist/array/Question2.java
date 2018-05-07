package jp.co.goalist.array;

public class Question2 {

    public static void main(String[] args) {
        // 例1
        final int[] kuku = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < kuku.length; i++) {
            String line = "";
            for (int j = 0; j < kuku.length; j++) {
                int quotient = kuku[i] * kuku[j];
                line += String.valueOf(quotient) + " ";
            }
            System.out.println(line);
        }

        // 例2 配列を使う必要がない
        for (int i = 1; i < 10; i++) {
            String line = "";
            for (int j = 1; j < 10; j++) {
                int quotient = i * j;
                line += String.valueOf(quotient) + " ";
            }
            System.out.println(line);
        }
    }

}
