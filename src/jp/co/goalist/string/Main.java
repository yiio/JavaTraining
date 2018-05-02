package jp.co.goalist.string;

public class Main {
    public static void main(String args[]) {
        String str = "個と組織をGOALへ導くプロフェッショナルチーム";
        third(str);
        thirdToSixth(str);
        firstAndLast(str);
        lastThree(str);
    }
    
    private static void third(String str) {
        String third = str.substring(2, 3);
        System.out.println(third);
    }
    private static void thirdToSixth(String str) {
        String thirdToSixth = str.substring(2, 6);
        System.out.println(thirdToSixth);
    }
    private static void firstAndLast(String str) {
        String first = str.substring(0, 1);
        String last = str.substring(str.length() - 1);
        System.out.println(first + last);
    }
    private static void lastThree(String str) {
        String lastThree = str.substring(str.length() - 3);
        System.out.println(lastThree);
    }
}