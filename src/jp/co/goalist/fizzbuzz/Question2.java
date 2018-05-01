package jp.co.goalist.fizzbuzz;

public class Question2 {
    public static void main(String[] args) {
        for (int num = 1; num <= 30; num++) {
            String numStr = String.valueOf(num);
            if (numStr.contains("3")) {
                System.out.println(num + "です。アホ");

            } else if (num % 3 == 0) {
                System.out.println(num + "です。アホ");
            } else {
                System.out.println(num + "です。");
            }
        }

    }
}