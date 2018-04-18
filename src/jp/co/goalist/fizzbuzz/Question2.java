package jp.co.goalist.fizzbuzz;

public class Question2 {

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            String iStr = String.valueOf(i); // int型をString型に変換する
            if (i % 3 == 0 || iStr.contains("3")) { // 3の倍数、または、文字にしたときに文字列の"3"を含む場合
                System.out.println(i + "です。アホ");
            } else {
                System.out.println(i + "です。");
            }
        }
    }

}
