package jp.co.goalist.analyze;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class kyomikomi1 {

    public static void main(String[] args) {
        // 都道府県名と出現回数の対応表を作る
        Map<String, Integer> prefCountMap = new HashMap<String, Integer>(); // key 都道府県名 value 出現回数
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String pref;
            while ((pref = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                if (prefCountMap.containsKey(pref)) { // すでに出てきてる都道府県の場合
                    int sum = prefCountMap.get(pref) + 1; // カウントアップして
                    prefCountMap.put(pref, sum); // マップに再格納
                } else { // 初出の都道府県の場合
                    prefCountMap.put(pref, 1); // カウント1回でマップに格納
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 結果を出力
        for (Map.Entry<String, Integer> entry : prefCountMap.entrySet()) {
            System.out.println(entry.getKey() + "は、" + entry.getValue() + "件です");
        }
    }

}