package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question7 {
    public static void main(String[] args) {
        Path itemPath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path listPath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        
        Map<String, String> codeNameMap = new LinkedHashMap<String, String>(); // ���i�R�[�h, ���i��
        Map<String, Integer> codePriceMap = new HashMap<String, Integer>(); // ���i�R�[�h, ���i�P��
        Map<String, Integer> codeSumMap = new HashMap<String, Integer>(); // ���i�R�[�h, �̔����v��
        
        String code; // ���i�R�[�h�̕ϐ�
        String name; // ���i���̕ϐ�
        int price; // ���i�P���̕ϐ�
        int number; // 1�����Ƃ̔̔�����
        int sum = 0; // ���i���Ƃ̍��v�̔���
        
        // salesItem.csv������
        try (BufferedReader itemBR = Files.newBufferedReader(itemPath)) {
            String line;
            int cnt = 0;
            
            while ((line = itemBR.readLine()) != null) {
                cnt++;
                // 1�s�ڂ͔�΂�
                if (cnt == 1) {
                    continue;
                }
                
                // 2�s�ڈȍ~
                String[] cols = line.split(",");
                code = cols[0];
                name = cols[1];
                price = Integer.valueOf(cols[2]);
                // codeNameMap�ցu���i�R�[�h, ���i���v���AcodePriceMap�ցu���i�R�[�h, �l�i�v���i�[
                codeNameMap.put(code, name);
                codePriceMap.put(code, price);
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // salesList.csv������
        try (BufferedReader listBR = Files.newBufferedReader(listPath)) {
            String line;
            int cnt = 0;
            
            while ((line = listBR.readLine()) != null) {
                cnt++;
                
                // 1�s�ڂ͔�΂�
                if (cnt == 1) {
                    continue;
                }
                
                // 2�s�ڈȍ~
                String[] cols = line.split(",");
                code = cols[1];
                number = Integer.valueOf(cols[2]);
                sum += number;
                codeSumMap.put(code, sum);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // ���㍂���v���v�Z�@���@3�����ƂɃJ���}�ŋ�؂�@���@���ʂ��o��
        for (Map.Entry<String, String> entry : codeNameMap.entrySet()) {
            code = entry.getKey(); // ���i�R�[�h�����ԂɎ擾
            name = entry.getValue(); // code�̏��i�R�[�h�ɑΉ��������i�����擾
            price = codePriceMap.get(code); // code�̏��i�R�[�h�ɑΉ��������i�P�����擾
            sum = codeSumMap.get(code); // code�̏��i�R�[�h�ɑΉ������̔����v�����擾
            
            // ���i���Ƃ̔��㍂���v���v�Z
            int amount = price * sum;
            
            // ���㍂��3�����ƂɃJ���}�ŋ�؂�
            String viewAmount = String.format("%, d", amount);
            
            // ���ʂ��o��
            System.out.println("�u" + name + "�v�̔��㍂���v�́A" + viewAmount + "�~  �ł��B");
        }
    }

}
