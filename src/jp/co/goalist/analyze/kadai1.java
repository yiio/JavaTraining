package jp.co.goalist.analyze;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class kadai1 {

    public static void main(String[] args) {
        Path filePath = Paths.get("../../resources/prefs.txt"); // 読み込み対象ファイルの場所を指定
        
        Map<String,Integer> countMap = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                //System.out.println(line); // 1行ずつ出力する
                if(!countMap.containsKey(line.trim())){
                    countMap.put(line.trim(),1);
                }else{
                    countMap.replace(line.trim(),countMap.get(line.trim())+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(String pref : countMap.keySet()){
            System.out.println(pref+"は、"+countMap.get(pref)+"件です。");
        }
    }

}
