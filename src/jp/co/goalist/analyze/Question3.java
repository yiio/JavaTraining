package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question3 {

    public static void main(String[] args) {
        Path filePath = Paths.get("/Users/yiio/workspace/resources/testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            Map<String, Integer> subjectPointMap = new HashMap<String, Integer>(); // 科目名と最高点の対応表
            Map<String, String> subjectNameMap = new HashMap<String, String>(); // 科目名と最高得点者の対応表
            String line; // 1行分の文字列を格納する変数

            // まずはヘッダ行の処理
            line = br.readLine(); // 1行目を読み込む
            String[] header = line.split(","); // ヘッダー行をカンマ区切りで配列に分割し、配列として格納する
            for (int i = 1; i < header.length; i++) {
                subjectPointMap.put(header[i], 0); // 科目名と最高点の対応表を初期化しておく
            }

            // ヘッダ行以外の処理
            while ((line = br.readLine()) != null) { // whileループしつつ2行目以降を読み込む
                String[] cols = line.split(","); // 1行をカンマ区切りで配列に分割する
                String name = cols[0]; // 名前は0番目
                for (int i = 1; i < cols.length; i++) { // 科目ごとに最高点を比較していく
                    String subject = header[i]; // この科目名
                    int point = Integer.valueOf(cols[i]); // この人のこの科目の点数
                    int max = subjectPointMap.get(subject); // この科目の最高点
                    if (max < point) {
                        subjectPointMap.put(subject, point); // 今までの最高点と比べて大きければ、この点数が最高点ということになる
                        subjectNameMap.put(subject, name); // この人を最高得点者とする
                    }
                }
            }

            // 結果を出力
            for (Map.Entry<String, Integer> entry : subjectPointMap.entrySet()) {
                String subject = entry.getKey();
                String pointStr = String.valueOf(entry.getValue());
                String name = subjectNameMap.get(subject);
                System.out.println(subject + "の最高得点者は" + name + "さんで、" + pointStr + "点です");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
