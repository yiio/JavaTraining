package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question3 {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        
        try (BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            int cnt = 0;
            String[] subjectAry = {};
            Map<String, Integer> subjectScoreMap = new LinkedHashMap<String, Integer>(); // 科目名, 最高得点
            Map<String, String> subjectNameMap = new HashMap<String, String>(); // 科目名, 最高得点者名
            
            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                // 1行目
                if (cnt == 1) {
                    subjectAry = cols;
                    for (int i = 1; i < cols.length; i++) {
                        subjectScoreMap.put(subjectAry[i], 0);
                    }
                    continue;
                }
                // 2行目以降
                for (int i = 1; i < cols.length; i++) { // その行を科目ごとに処理
                    int max = subjectScoreMap.get(subjectAry[i]);
                    int score = Integer.valueOf(cols[i]);
                    if (max < score) {
                        // subjectAry[i]は科目名、cols[0]は最高得点者名
                        subjectScoreMap.put(subjectAry[i], score); // その時点での最高点として、科目：最高点をマップに格納
                        subjectNameMap.put(subjectAry[i], cols[0]); // その時点での最高得点者として、科目：最高得点者をマップに格納
                    }
                }
                
            }
            // 結果を出力
            for (Map.Entry<String, Integer> entry : subjectScoreMap.entrySet()) {
                String subject = entry.getKey(); // 科目名
                String name = subjectNameMap.get(entry.getKey()); // 最高得点者名
                int maxScore = entry.getValue();
                System.out.println(subject + "の最高得点者は、" + name + "さん、" + maxScore + "点です。");
            }
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}