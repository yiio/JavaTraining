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

        Map<String, Integer> prefMap = new HashMap<>();

        Path filePath = Paths.get("c:/TechTraining/resources/prefs.txt"); // 読み込み対象ファイルの場所を指定
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                System.out.println(line); // 1行ずつ出力する
                if (!prefMap.containsKey(line)) { //都道府県名を新たに追加
                    prefMap.put(line, 1);
                } else {
                    prefMap.put(line, prefMap.get(line) + 1); //件数を加算
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //それぞれの都道府県の件数を出力
        for (String pref : prefMap.keySet()) {
            System.out.println(pref + "は、" + prefMap.get(pref) + "件です");
        }
    }
}
