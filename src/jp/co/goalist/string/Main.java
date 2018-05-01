package jp.co.goalist.string;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String str;
        /*
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("input : ");
            str = br.readLine();
            
        }catch(IOException e){
            System.out.println(e);
        }
        */
        str = "abcdefghai";
        System.out.print("c ? ");
        show3rd(str);
        System.out.print("cdef ? ");
        showFrom3rdTo6th(str);
        System.out.print("ai ? ");
        show1stAndLast(str);
        System.out.print("h ? ");
        showBehind3rd(str);
    }
    
    public static void show3rd(String str){
        System.out.println(str.charAt(2));
    }
    
    public static void showFrom3rdTo6th(String str){
        System.out.println(str.substring(2,6));
    }
    
    public static void show1stAndLast(String str){
        System.out.println(str.charAt(0)+""+str.charAt(str.length()-1));
    }
    
    public static void showBehind3rd(String str){
        System.out.println(str.charAt(str.length()-3));
    }

}
