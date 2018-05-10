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
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        
        try (BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            int cnt = 0;
            String[] subjectAry = {};
            Map<String, Integer> subjectScoreMap = new HashMap<String, Integer>(); // 科目名, 最高得点
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
                for (int i = 1; i < cols.length; i++) { // その行を科目ごとに処理
                    int max = subjectScoreMap.get(subjectAry[i]);
                    int score = Integer.valueOf(cols[i]);
                    if (max < score) {
                        subjectScoreMap.put(subjectAry[i], score); // その時点での最高点として、科目：最高点をマップに格納
                        subjectNameMap.put(subjectAry[i], cols[0]); // その時点での最高得点者として、科目：最高得点者をマップに格納
                    }
                }
                
            }
            System.out.println(subjectScoreMap);
            System.out.println(subjectNameMap);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
