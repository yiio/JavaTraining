package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question1_2 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            // 課題1～マップを使ってみる～
            String line;

            // マップの作製

            Map<String, Integer> prefecture = new HashMap<String, Integer>();

            // 件数のカウント
            while ((line = br.readLine()) != null) {
                int i = 0;
                if (!prefecture.containsKey(line)) {
                    prefecture.put(line, i);
                }
                i = prefecture.get(line);
                i++;
                prefecture.put(line, i);
            }

            // 件数の書き出し
            for (String key : prefecture.keySet()) {
                System.out.println(key + "は、" + prefecture.get(key) + "件です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
