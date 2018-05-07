package jp.co.goalist.array;

public class Question2 {
    public static void main(String args[]) {
        System.out.println("----- for文 -----");
        Q2();
        System.out.println(" ");
        System.out.println("----- 拡張for文 -----");
        Q2ex();
    }

    public static void Q2() {
        for (int i = 1; i < 10 ; i++) {
            for (int j = 1; j < 10 ; j++) {
                System.out.print(i * j + " ");
            }System.out.println(" ");
        }
    }

    public static void Q2ex() {
        int[] ary = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int num1 : ary) {
            for (int num2 : ary ) {
                int quotient = num1 * num2;
                System.out.print(quotient + " ");
            }System.out.println(" ");
        }
    }
}