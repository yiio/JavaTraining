package jp.co.goalist.fizzbuzz;

public class Question1 {

    public static void main(String[] args) {
        for(int i=1;i<=30;i++){
            System.out.print(i+"です。");
            System.out.println(i%3==0? "アホ" : "");
        }
    }
}
