package jp.co.goalist.fizzbuzz;

public class Question1 {

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            if (i % 3 == 0) { // 3の倍数の時（3で割り切れる時）
                System.out.println(i + "です。アホ");
            } else { // 3の倍数ではない時
                System.out.println(i + "です。");
            }
        }
    }

}
