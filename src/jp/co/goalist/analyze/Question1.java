package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question1 {

    public static void main(String[] args) {
        Map<String,Integer> prefMap = new HashMap<>(); // Map初期化
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定

        try (BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                if(!prefMap.containsKey(line)) { // Mapに追加してない県は(県名,1)で追加
                    prefMap.put(line,1);
                }else {
                    prefMap.put( line, prefMap.get(line) + 1); // Mapにある県はvalueに1を足す
                }
            }
        }

        catch (IOException e) { // ファイルがなかった場合の例外処置
            e.printStackTrace();
        }

        for(String pref : prefMap.keySet()) {
            System.out.println( pref + "は、" + prefMap.get(pref) + "件です。");
        }
    }
}