package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Question6 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int cnt = 0;
            Map<String, Integer> nameSumMap = new HashMap<String, Integer>();
            
            while((line = br.readLine()) != null) {
                cnt++;
                // 1�s�ڂ͔�΂�
                if (cnt == 1) {
                    continue;
                }
                // 2�s�ڈȍ~
                String[] cols = line.split(",");
                int sum = 0;
                String name = cols[0];
                for (int i = 1; i < cols.length;i++) {
                    int score = Integer.valueOf(cols[i]);
                    sum += score;
                    nameSumMap.put(name, sum);
                }
            }
            // �_�����ɕ��בւ�
            nameSumMap = nameSumMap.entrySet().stream()
                .sorted(Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            
            // ���ʂ��o��
            int rank = 0;
            for (Map.Entry<String, Integer> entry : nameSumMap.entrySet()) {
                rank++;
                System.out.println(rank + "��:" + entry.getKey() + "���� " + entry.getValue() + "�_");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}