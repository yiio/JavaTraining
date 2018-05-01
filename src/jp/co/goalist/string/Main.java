package jp.co.goalist.string;

public class Main {
    public static void main(String args[]) {
        String str = "個と組織をGOALへ導くプロフェッショナルチーム";
        printStrLength(str);
        Q1(str);
        Q2(str);
        Q3(str);
        Q4(str);
    }

    private static void printStrLength(String str) {
        System.out.println("渡された文字は " + str.length() + " 文字です");
    }

    private static void Q1(String str) {
        System.out.println("3文字目は「" + str.charAt(2) + "」です");
    }

    private static void Q2(String str) {
        System.out.println("3～6文字目は「" + str.substring(2, 5) + "」です");
    }

    private static void Q3(String str) {
        System.out.println("最初と最後の文字を組み合わせると「" + str.charAt(0) + str.substring(str.length() - 1) + "」となります");
    }

    private static void Q4(String str) {
        System.out.println("最後から3文字は「" + str.substring(str.length() - 3) + "」です");
    }

}