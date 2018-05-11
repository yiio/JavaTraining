package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Question5 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        
        // 点数を媒介して科目と項目を紐づける
        Map<String, Integer> subjectScoreMap = new HashMap<String, Integer>(); // 科目, 点数
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            String[] subjectAry = {}; // 科目名の配列の初期化
            
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                cnt ++;
                String[] cols = line.split(",");
                int sum = 0;
                double avg = 0;;
                int max = 0;
                String maxSubjects = ""; // 最高点の科目名
                int min = 100;
                String minSubjects = ""; // 最低点の科目名
                
                String subject;
                // 1行目
                if (cnt == 1) {
                    for (int i = 1; i < cols.length; i++) {
                        subjectAry = cols;
                        subject = subjectAry[i]; // 科目名を取得
                        subjectScoreMap.put(subject, 0); // 科目：得点マップの初期化
                    }
                    continue;
                }
                
                // 2行目以降
                for (int i = 1; i < cols.length; i++) { // その行のi列目を順に処理していく
                    // 各科目の得点を取得し、「科目名：得点」をマップに格納
                    int score = Integer.valueOf(cols[i]);
                    subject = subjectAry[i];
                    subjectScoreMap.put(subject, score);
                    
                    // 全科目合計点、平均点の計算
                    sum += score;
                    avg = sum / cols.length - 1;
                    
                    // 最高点・最低点とその科目名を求める
                    if (max < score) {
                        max = score;
                        maxSubjects = subject;
                    } else if (max == score) { // 最高点が複数出てきた場合
                        maxSubjects = maxSubjects + "と" + subject;
                    }
                    if (min > score) {
                        min = score;
                        minSubjects = subject;
                    } else if (min == score) {
                        minSubjects = minSubjects + "と" + subject;
                    }
                }
                
                // その行の人の名前を取得
                String name = cols[0];
                
                // 結果を出力
                System.out.println(name + "さんの全科目合計点は" + sum + "点、全科目平均点は" + avg + "点、最高点は" + maxSubjects
                        + "で" + max + "点、最低点は" + minSubjects + "で、" + min + "点です。");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
