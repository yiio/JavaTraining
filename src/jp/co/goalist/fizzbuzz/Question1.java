package jp.co.goalist.fizzbuzz;

public class Question1 {

    public static void main(String[] args) {
        System.out.println("hello");

        for (int j = 1; j <= 30; j++) {
            switch (j % 3) {
            case 0:
                System.out.println(j + "です。アホ");
                break;
            default:
                System.out.println(j + "です。");
                break;
            }
        }

    }

}
