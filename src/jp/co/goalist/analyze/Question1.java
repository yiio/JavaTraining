package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Question1 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt");
        
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            Map<String, Integer> prefecMap = new TreeMap<String, Integer>(); // 都道府県名：登場回数
            
            while ((line = br.readLine()) != null) { 
                int count = 0;
                // その行の文字列（都道府県名）がマップのキーに含まれていれば値をプラス１
                if (prefecMap.containsKey(line)) {
                    count = prefecMap.get(line) + 1;
                    prefecMap.put(line, count);
                    
                // 含まれていなければ、マップに新しくキーを追加し値をプラス１
                } else {
                    count += 1;
                    prefecMap.put(line, count);
                }
            } 
            // 件数を出力
            for (Map.Entry<String, Integer> entry : prefecMap.entrySet()) {
                String prefecture = entry.getKey();
                String count = String.valueOf(entry.getValue());
                System.out.println(prefecture + "は、" + count + "件です。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
