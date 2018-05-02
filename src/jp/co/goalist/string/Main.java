package jp.co.goalist.string;

public class Main {

    public static void main(String args[]) {

        String str = "個と組織をGOALへ導くプロフェッショナルチーム";

        printStrLength(str);
            Question1(str);
            Question2(str);
            Question3(str);
            Question4(str);
    }

    private static void printStrLength(String str) {
        System.out.println("渡された文字は " + str.length() + " 文字です");

    }

    private static void Question1(String str) {
        System.out.println(str.charAt(2));
    }

    private static void Question2(String str) {
        System.out.println(str.substring(2, 6));
    }

    private static void Question3(String str) {
        System.out.println(str.charAt(0) + str.substring(str.length() - 1));
    }

    private static void Question4(String str) {
        System.out.println(str.substring(str.length() - 3));
    }
}