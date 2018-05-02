package jp.co.goalist.array;

import java.util.Arrays;

public class Question7 {
    
    public static void main(String[] args) {
     // 名前とチームの配列
        final String[][] teamArys = {
            {"山田", "Aチーム"},
            {"田中", "Aチーム"},
            {"鈴木", "Bチーム"},
            {"中村", "Bチーム"},
            {"伊藤", "Bチーム"}
        };

        // 名前と得点の配列
        final String[][] pointArys = {
            {"山田", "1"},
            {"山田", "3"},
            {"山田", "1"},
            {"伊藤", "4"},
            {"山田", "2"},
            {"中村", "1"},
            {"中村", "1"},
            {"山田", "3"},
            {"鈴木", "2"},
            {"山田", "4"},
            {"伊藤", "5"},
            {"山田", "2"},
            {"鈴木", "3"},
            {"山田", "1"},
            {"田中", "2"}
        };
        // Aチーム内のメンバーを検索（teamArys内）
        if (Arrays.asList(teamArys).contains("Aチーム")) {
            System.out.println("ww");
        }
        
        // それらのメンバーに該当する得点を検索・取得（pointArys内）
        
        
        // 得点（string)を(integer)に変換
        
        
        // Aチームの得点をすべて足す
        
        
        // Bチームも同様
        
        
        // 各チームの得点を知らせる文章を出力
        
        
    }

}
