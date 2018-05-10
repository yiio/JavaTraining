package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question4 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int cnt = 0;
            String[] subjectAry = {};
            Map<String, Integer> subjectSumMap = new LinkedHashMap<String, Integer>(); // �Ȗږ�, �Ȗڂ��Ƃ̍��v�_
            
            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                
                // 1�s��
                if (cnt == 1) {
                    subjectAry = cols;
                    for (int i = 1; i < cols.length; i++) {
                        // �Ȗږ�, ���v�_�̏����l���Z�b�g
                        subjectSumMap.put(subjectAry[i], 0);
                    }
                    continue;
                }
                
                // 2�s�ڈȍ~
                for (int i =1; i < cols.length; i++) {
                    int sum = subjectSumMap.get(subjectAry[i]);
                    int score = Integer.valueOf(cols[i]);
                    sum += score;
                    subjectSumMap.put(subjectAry[i], sum);
                }
            }
            int number = cnt - 1; // �l��
            for (Map.Entry<String, Integer> entry : subjectSumMap.entrySet()) {
                double avg = (double)entry.getValue() / number;
                BigDecimal bdAvg = BigDecimal.valueOf(avg).setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.println(entry.getKey() + "�̕��ϓ_��" + bdAvg + "�_�ł��B");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
