package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

public class Question2 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        
        try (BufferedReader br = Files.newBufferedReader(filePath)){
            
            String line;
            Map<String, Integer> mathScores = new HashMap<String, Integer>(); // ���O, ���w�̓_��
            
            while ((line = br.readLine()) != null) {
                // �e�s�̕������z��ɕ���
                String[] scoreAry = line.split(","); // ���_������String�^�ɂȂ��Ă��邱�Ƃɒ���
                
                // �z��̓��_����(���w)�̒l�����l���ǂ����`�F�b�N
                if (StringUtils.isNumeric(scoreAry[1])) { 
                    
                    // ���l�Ȃ�΁A���w�̓��_��Integer�֕ϊ�
                    int mathScore = Integer.parseInt(scoreAry[1]);
                    
                    // ���k�����擾���A�ϐ�name�֑��
                    String name = scoreAry[0];
                    
                    // �}�b�vmathScores�ցA�L�[�i���k���j�ƒl�i���w�̓��_�j���Z�b�g�łԂ�����
                    mathScores.put(name, mathScore);
                }
            
            }
            int maxScore = 0;
            
            // ���w�̍ō��_(maxScore)�����߂�
            for (Map.Entry<String, Integer> mathEntry : mathScores.entrySet()) {
                maxScore = Math.max(maxScore, mathEntry.getValue());
            }
            // ���w�̍œ_���o��
            System.out.println("���w�̍ō����_��" + maxScore + "�_�ł��B");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
