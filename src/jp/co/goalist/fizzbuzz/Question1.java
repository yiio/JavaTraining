package jp.co.goalist.fizzbuzz;

public class Question1 {

    public static void main(String[] args) {
        for (int i = 1; i <= 30; i++) {
            if (i % 3 == 0) {
                System.out.println(i + "です。アホ");
            } else {
                System.out.println(i + "です。");
            }
        }
       　int i = 1;
        String numStr = String.valueOf(i);
        if (i.contains("3")) {
            System.out.println(numStr + "です。アホ");          
        }else if(i % 3 == 0) {
            System.out.println(i + "です。アホ");
            
        }else {
            System.out.println(i + "です。");
        }
    }
    
}
