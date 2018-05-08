package jp.co.goalist.fizzbuzz;

public class Question2 {

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            String iStr = String.valueOf(i);
            if (i % 3 == 0) {
                System.out.println(i + "です。アホ");
            } else if (iStr.contains("3")) {
                System.out.println(i + "です。アホ");
            } else {
                System.out.println(i + "です。");
            }
        }
    }
}