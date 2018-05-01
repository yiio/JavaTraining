package jp.co.goalist.array;

public class Question2 {
    public static void main(String args[]) {
        Q2();
    }

    public static void Q2() {
        int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num.length; j++)
            //ありがとう増田くん
            System.out.print(num[i] * num[j] + " "); //これだけだと改行できなくて詰む
            System.out.println(" "); //この文があることで改行が可能に。天才。
            //戒めのため、最初に書いたゴリラコードも残します→(num[i] * 1 + " " + num[i] * 2 + " " + num[i] * 3 + " " + num[i] * 4 + " " + num[i] * 5 + " " + num[i] * 6 + " " + num[i] * 7 + " " + num[i] * 8 + " " + num[i] * 9);
        }
    }
}