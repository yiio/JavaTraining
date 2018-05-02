package jp.co.goalist.array;

public class Question1 {

    public static void main(String[] args) {
        array();
    }

    private static void array() {

        int[] ary1 = { 1, 2, 3, 4, 5 };
        int[] ary2 = { 10, 20, 30, 40, 50 };
        for (int i = 0; i < ary1.length; i++) {
            for (int j = 0; j < ary2.length; j++) {
                System.out.println(ary1[i] * ary2[j]);
            }
        }
    }
}
