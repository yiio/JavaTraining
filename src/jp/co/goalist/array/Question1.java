package jp.co.goalist.array;

public class Question1 {

    public static void main(String[] args) {
        final int[] ary1 = {1, 2, 3, 4, 5};
        final int[] ary2 = {10, 20, 30, 40, 50};

        // 例1
        for (int i = 0; i < ary1.length; i++) {
            int num1 = ary1[i];
            for (int j = 0; j < ary2.length; j++) {
                int num2 = ary2[j];
                int quotient = num1 * num2;
                System.out.println(num1 + " * " + num2 + " = " + quotient);
            }
        }

        // 例2
        for (int num1 : ary1) {
            for (int num2 : ary2) {
                int quotient = num1 * num2;
                System.out.println(num1 + " * " + num2 + " = " + quotient);
            }
        }
    }

}
