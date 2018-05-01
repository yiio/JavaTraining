package jp.co.goalist.string;

public class Main {

    public static void main(String[] args) {
        String str = "個と組織をGOALへ導くプロフェッショナルチーム";
        System.out.println(str);
        System.out.println();

        printAnswer1(str);
        printAnswer2(str);
        printAnswer3(str);
        printAnswer4(str);
    }

    /**
     * 1. 渡された文字列の3文字目の文字を出力する
     */
    private static void printAnswer1(String str) {
        System.out.println("1. 渡された文字列の3文字目の文字を出力する");
        System.out.println(str.charAt(2));
        System.out.println();
    }

    /**
     * 2. 渡された文字列の3～6文字目の文字を出力する
     */
    private static void printAnswer2(String str) {
        System.out.println("2. 渡された文字列の3～6文字目の文字を出力する");
        System.out.println(str.substring(2, 6));
        System.out.println();
    }

    /**
     * 3. 渡された文字列の最初の文字と最後の文字を合わせて出力する
     */
    private static void printAnswer3(String str) {
        String first = str.substring(0, 1);
        String last = str.substring(str.length() - 1);
        System.out.println("3. 渡された文字列の最初の文字と最後の文字を合わせて出力する");
        System.out.println(first + last);
        System.out.println();
    }

    /**
     * 4. 渡された文字列の最後の3文字を出力する
     */
    private static void printAnswer4(String str) {
        System.out.println("4. 渡された文字列の最後の3文字を出力する");
        System.out.println(str.substring(str.length() - 3));
    }

}
