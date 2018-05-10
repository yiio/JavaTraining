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
            Map<String, Integer> subjectScoreMap = new HashMap<String, Integer>(); // �Ȗږ�, �ō����_
            Map<String, String> subjectNameMap = new HashMap<String, String>(); // �Ȗږ�, �ō����_�Җ�
            
            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                // 1�s��
                if (cnt == 1) {
                    subjectAry = cols;
                    for (int i = 1; i < cols.length; i++) {
                        subjectScoreMap.put(subjectAry[i], 0);
                    }
                    continue;
                }
                for (int i = 1; i < cols.length; i++) { // ���̍s���Ȗڂ��Ƃɏ���
                    int max = subjectScoreMap.get(subjectAry[i]);
                    int score = Integer.valueOf(cols[i]);
                    if (max < score) {
                        subjectScoreMap.put(subjectAry[i], score); // ���̎��_�ł̍ō��_�Ƃ��āA�ȖځF�ō��_���}�b�v�Ɋi�[
                        subjectNameMap.put(subjectAry[i], cols[0]); // ���̎��_�ł̍ō����_�҂Ƃ��āA�ȖځF�ō����_�҂��}�b�v�Ɋi�[
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
