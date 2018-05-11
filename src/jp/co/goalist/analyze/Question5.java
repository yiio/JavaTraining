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
        
        // �_����}��ĉȖڂƍ��ڂ�R�Â���
        Map<String, Integer> subjectScoreMap = new HashMap<String, Integer>(); // �Ȗ�, �_��
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            String[] subjectAry = {}; // �Ȗږ��̔z��̏�����
            
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                cnt ++;
                String[] cols = line.split(",");
                int sum = 0;
                double avg = 0;;
                int max = 0;
                String maxSubjects = ""; // �ō��_�̉Ȗږ�
                int min = 100;
                String minSubjects = ""; // �Œ�_�̉Ȗږ�
                
                String subject;
                // 1�s��
                if (cnt == 1) {
                    for (int i = 1; i < cols.length; i++) {
                        subjectAry = cols;
                        subject = subjectAry[i]; // �Ȗږ����擾
                        subjectScoreMap.put(subject, 0); // �ȖځF���_�}�b�v�̏�����
                    }
                    continue;
                }
                
                // 2�s�ڈȍ~
                for (int i = 1; i < cols.length; i++) { // ���̍s��i��ڂ����ɏ������Ă���
                    // �e�Ȗڂ̓��_���擾���A�u�Ȗږ��F���_�v���}�b�v�Ɋi�[
                    int score = Integer.valueOf(cols[i]);
                    subject = subjectAry[i];
                    subjectScoreMap.put(subject, score);
                    
                    // �S�Ȗڍ��v�_�A���ϓ_�̌v�Z
                    sum += score;
                    avg = sum / cols.length - 1;
                    
                    // �ō��_�E�Œ�_�Ƃ��̉Ȗږ������߂�
                    if (max < score) {
                        max = score;
                        maxSubjects = subject;
                    } else if (max == score) { // �ō��_�������o�Ă����ꍇ
                        maxSubjects = maxSubjects + "��" + subject;
                    }
                    if (min > score) {
                        min = score;
                        minSubjects = subject;
                    } else if (min == score) {
                        minSubjects = minSubjects + "��" + subject;
                    }
                }
                
                // ���̍s�̐l�̖��O���擾
                String name = cols[0];
                
                // ���ʂ��o��
                System.out.println(name + "����̑S�Ȗڍ��v�_��" + sum + "�_�A�S�Ȗڕ��ϓ_��" + avg + "�_�A�ō��_��" + maxSubjects
                        + "��" + max + "�_�A�Œ�_��" + minSubjects + "�ŁA" + min + "�_�ł��B");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
