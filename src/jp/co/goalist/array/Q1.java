package jp.co.goalist.array;

public class Q1 {
    public static void main(String[] args) {

        int[] ary1 = { 1, 2, 3, 4, 5 };
        int[] ary2 = { 10, 20, 30, 40, 50 };

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {
                System.out.println(ary1[i] + "*" + ary2[j] + "=" + ary1[i] * ary2[j]);
            }
        }
    }
}
