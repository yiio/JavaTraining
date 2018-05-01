package jp.co.goalist.array;

public class kadai2 {

    public static void main(String[] args) {
        int[] ary = {1, 2, 3, 4, 5,6,7,8,9};
        
        for(int x=0;x<ary.length;x++){
            for(int y=0;y<ary.length;y++){
                System.out.print(ary[x]*ary[y]+" ");
            }System.out.println();
        }
    }

}
