package jp.co.goalist.string;

public class Main {
    public static void main(String[] args) {
        letters1("arisamasuda");
        letters2("arisamasuda");
        letters3("arisamasuda");
        letters4("arisamasuda");

    }

    public static void letters1(String letters) {
        System.out.println(letters.charAt(2));
    }

    public static void letters2(String letters) {
        System.out.println(letters.substring(2, 6));
    }

    public static void letters3(String letters) {
        System.out.println(letters.charAt(0) + letters.substring(letters.length() - 1));
    }
    
    public static void letters4(String letters) {
        System.out.println(letters.substring(letters.length() - 3));
    }
}
