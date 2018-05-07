package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Question1 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt");
        
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            Map<String, Integer> prefecMap = new TreeMap<String, Integer>(); // �s���{�����F�o���
            
            while ((line = br.readLine()) != null) { 
                int count = 0;
                // ���̍s�̕�����i�s���{�����j���}�b�v�̃L�[�Ɋ܂܂�Ă���Βl���v���X�P
                if (prefecMap.containsKey(line)) {
                    count = prefecMap.get(line) + 1;
                    prefecMap.put(line, count);
                    
                // �܂܂�Ă��Ȃ���΁A�}�b�v�ɐV�����L�[��ǉ����l���v���X�P
                } else {
                    count += 1;
                    prefecMap.put(line, count);
                }
            } 
            // �������o��
            for (Map.Entry<String, Integer> entry : prefecMap.entrySet()) {
                String prefecture = entry.getKey();
                String count = String.valueOf(entry.getValue());
                System.out.println(prefecture + "�́A" + count + "���ł��B");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
