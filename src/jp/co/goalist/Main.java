package jp.co.goalist;

public class Main {
    public static void main(String[] args) {
        String str = "個と組織をGOALへ導くプロフェッショナルチーム";


        printStrLength(str);
        goal(str);
        ko(str);
        sosiki(str);

    }

    public static void printStrLength(String str) {

        System.out.println("渡された文字は"+str.charAt(2)+"文字です");

    }
    public static void goal(String str) {
        System.out.println("渡された文字は"+str.substring(2,6)+"文字です");
    }
    public static void ko(String str) {
        int size =str.length();
        int cut_length=1;
        System.out.println("渡された文字は"+str.charAt(0)+str.substring(size-cut_length)+"文字です");
    }
    public static void sosiki(String str) {
        int right=str.length();
        int cut =3;
        System.out.println("渡された文字は"+str.substring(right-cut)+"文字です");
    }
    
    








}


