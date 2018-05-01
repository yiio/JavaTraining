package jp.co.goalist.fizzbuzz;

public class Question2 {
    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            String numTri = String.valueOf(i);
            // 3を含む数字の場合
            if (numTri.contains("3")) {
                System.out.println(i + "です。アホ");
            // 3の倍数の場合
            } else if (i % 3 == 0) {
                System.out.println(i + "です。アホ");
            //上記以外の場合
            } else {
                System.out.println(i + "です。");
            }
        }
    }
}