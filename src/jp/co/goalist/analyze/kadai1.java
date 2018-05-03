package jp.co.goalist.analyze;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class Kadai1 {

    public static void main(String[] args) {
        
        Path filePath = Paths.get("../../resources/prefs.txt"); // 読み込み対象ファイルの場所を指定
        
        Map<String,Integer> countMap = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!countMap.containsKey(line)){
                    countMap.put(line.trim(), 1);
                }else{
                    countMap.replace(line, countMap.get(line) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(String pref : countMap.keySet()){
            System.out.println(pref + "は、" + countMap.get(pref) + "件です。");
        }
    }

}
