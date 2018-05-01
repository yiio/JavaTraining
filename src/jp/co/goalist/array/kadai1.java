package jp.co.goalist.array;

public class kadai1 {

    public static void main(String[] args) {
        int[] ary1 = {1, 2, 3, 4, 5};
        int[] ary2 = {10, 20, 30, 40, 50};
        
        for(int x=0;x<ary1.length;x++){
            for(int y=0;y<ary2.length;y++){
                System.out.println(ary1[x]+"*"+ary2[y]+"="+ary1[x]*ary2[y]);
            }
        }
    }

}
