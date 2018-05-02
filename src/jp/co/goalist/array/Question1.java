package jp.co.goalist.array;

public class Question1 {
    public static void main(String[] args) {

        // 課題1
        int[] ary1 = { 1, 2, 3, 4, 5 };
        int[] ary2 = { 10, 20, 30, 40, 50 };

        for (int i = 0; i < ary1.length; i++) {
            for (int j = 0; j < ary2.length; j++) {
                System.out.println(ary1[i] * ary2[j]);
            }

        }

        // 課題2
        int[] ary3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for (int k = 0; k < ary3.length; k++) {
            for (int l = 0; l < ary3.length; l++) {
                System.out.print(ary3[k] * ary3[l]);
                if (l == 8) {
                    System.out.println();
                }
            }

        }

        caluculation();

    }

    // 課題2－2
    public static void caluculation() {
        int[] numAry = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        String number = "";

        for (int i = 0; i < numAry.length; i++) {
            for (int j = 0; j < numAry.length; j++) {
                number += String.valueOf(numAry[i] * numAry[j]);
            }

            System.out.println(number);

            number = "";
        }
    }

}
