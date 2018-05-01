package jp.co.goalist.fizzbuzz;

public class Question1 {
    public static void main(String[] args) {

        String aho = "アホか否か？";

        for (int i = 1; i < 31; i++) {

            if (i%3==0) {
                aho = "アホ";
            }else {
                aho = "";
            }
            System.out.println(i + "です。" + aho);
        }

        }
}
