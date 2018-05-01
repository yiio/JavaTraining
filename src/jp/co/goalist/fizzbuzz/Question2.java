package jp.co.goalist.fizzbuzz;

public class Question2 {
    public static void main(String[] args) {

        String aho = "アホか否か？";

        for (int i = 1; i < 31; i++) {
            String numStr = String.valueOf(i);
            if (i%3==0) {
                aho = "アホ";
            }else if (numStr.contains("3")) {
                aho ="アホ";
            }else {
                aho = "";
            }
            System.out.println(i + "です。" + aho);
        }

    }

}
