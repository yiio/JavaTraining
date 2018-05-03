package jp.co.goalist.array;

public class Kadai1 {

    public static void main(String[] args) {
        int[] ary1 = {1, 2, 3, 4, 5};
        int[] ary2 = {10, 20, 30, 40, 50};
        
        for(int i : ary1){
            for(int j : ary2){
                System.out.println(i + " * " + j + " = " + i * j);
            }
        }
    }

}
