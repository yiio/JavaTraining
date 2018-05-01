package jp.co.goalist;
public class Main {
    public static void main(String args[]) {
        String str = "個と組織をGOALへ導くプロフェッショナルチーム";
        printStrLength(str);
    }

    private static void printStrLength(String str) {
        System.out.println("渡された文字は " + str.length() + " 文字です");
        
        String Q1 = str.substring(2,3);
        System.out.println(Q1); 
       
        String Q2 = str.substring(2,5);
        System.out.println(Q2);
        
        String Q31 = str .substring(0,1);
        String Q32 = str.substring(str.length()-1);
        System.out.println(Q31+Q32);
        
        String Q4 = str.substring(str.length()-3);
        System.out.println(Q4);
        
        
        
        
        
        
        
        
    }
}
