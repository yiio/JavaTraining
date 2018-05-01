package jp.co.goalist.array;

public class Question1 {
    public static void main(String[] args) {
        int[] ary1 = { 1, 2, 3, 4, 5 };
        int[] ary2 = { 10, 20, 30, 40, 50 };

        for (int i = 0; i < ary1.length; i++) {
            for (int j = 0; j < ary2.length; j++) {
                System.out.println(ary1[i] * ary2[j]);
            }

        }

        int[] ary3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for (int k = 0; k < ary3.length; k++) {
            for (int l = 0; l < ary3.length; l++) {
                System.out.print(ary3[k] * ary3[l]);
                if (l == 8) {
                    System.out.println();
                }
            }

        }

    }

}
