package jp.co.goalist.array;

public class Question2 {

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            String line = "";
            for (int j = 1; j < 10; j++) {
                int quotient = i * j;
                line += String.valueOf(quotient) + " ";
            }
            System.out.println(line);
        }
    }

}
