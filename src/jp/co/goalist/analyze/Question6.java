package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Question6 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            // データを二次元配列に格納する
            String[][] scores = new String[6][5];
            int i = 0;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            // マップに名前と合計点をいれる
            Map<String, Integer> sumScore = new HashMap<String, Integer>();
            for (i = 1; i < scores.length; i++) {
                String name = scores[i][0];
                int scoreSum = 0;
                for (int j = 1; j < scores[i].length; j++) {
                    int score = Integer.parseInt(scores[i][j]);
                    scoreSum += score;// 全教科合計点
                    sumScore.put(name, scoreSum);
                }
            }

            // 合計点を降順に並べる
            List<Entry<String, Integer>> listScore = new ArrayList<Entry<String, Integer>>(sumScore.entrySet());

            Collections.sort(listScore, new Comparator<Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                    return obj2.getValue().compareTo(obj1.getValue());
                }
            });

            i = 1;
            for (Entry<String, Integer> entry : listScore) {
                System.out.println(i + "位：" + entry.getKey() + "さん  " + entry.getValue() + "点");
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
