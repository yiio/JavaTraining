package jp.co.goalist.array;

public class Question2 {
    public static void main(String args[]) {
        Q2();
    }

    public static void Q2() {
        int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num.length; j++) {
                System.out.print(num[i] * num[j] + " ");
            }
            System.out.println(" ");
        }
    }
}