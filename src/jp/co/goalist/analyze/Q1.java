package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;//ハッシュマップ使うよ！
import java.util.Map;//マップ使うよ！！

public class Q1 {
    public static void main(String args[]) {
        Map<String,Integer> prefCountMap=new HashMap<String,Integer>();
        
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            
           
            String pref;
            while ((pref = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
               if(prefCountMap.containsKey(pref)) {
                  int sum=prefCountMap.get(pref)+1;
                  prefCountMap.put(pref,sum);
               }
               else {
                  prefCountMap.put(pref,1); //
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Map.Entry<String, Integer> bar : prefCountMap.entrySet()) {
            System.out.println(bar.getKey()+"は、"+bar.getValue()+"件です。");
        }
    }

}
/*
 * マップに値を追加・取得する方法 map.put(key,value)/map.get(key) マップの繰り返し処理 for
 * (Map.Entry<String, Integer> bar : map.entrySet()) { bar.getKey(); キーを取得
 * bar.getValue(); 値を取得 }
 */