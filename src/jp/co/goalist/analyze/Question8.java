package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question8 {
    
    public static void main(String[] args) {
        Path itemPath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path listPath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        Path filePath = Paths.get("C:\\TechTraining\\JavaTraining\\answer1.csv");
        
        Map<String, String> codeDateMap = new HashMap<String,String>(); // ���i�R�[�h, �̔���
        Map<String, Integer> codePriceMap = new HashMap<String, Integer>(); // ���i�R�[�h, �P��
        Map<String, Integer> dateAmountMap = new LinkedHashMap<String, Integer>(); // �̔���, �����Ƃ̔��㑍�z
        
        String code; // ���i�R�[�h�̕ϐ�
        String date; // �̔����̕ϐ�
        int price; // ���i�P���̕ϐ�
        int number; // 1�����Ƃ̔̔�����
        String contents = ""; // �V�����t�@�C���ɏ������ޓ��e
        
        // salesItem.csv�̏���
        try (BufferedReader itemBR = Files.newBufferedReader(itemPath)) {
            String line;
            int cnt = 0;
            
            while ((line = itemBR.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                // 2�s�ڈȍ~
                String[] cols = line.split(",");
                code = cols[0];
                price = Integer.valueOf(cols[2]);
                codePriceMap.put(code, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* dateAmountMap�ɁA�L�[�ɓ��t���A�l��0�������l�Ƃ��Ċi�[
         * 1~2���̑S���t���擾
         */ 
        for (int month = 0; month < 2; month++) {
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.MONTH, month);
            cl.set(Calendar.YEAR, 2017);
            int dom = cl.getActualMaximum(Calendar.DAY_OF_MONTH); // ���̌��̓���
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
            
            for (int date1 = 0; date1 < dom; date1++) {
                cl.set(Calendar.DATE, date1 + 1);
                String day = sdf.format(cl.getTime());
                
                // dateAmountMap�ɏ����l�Ƃ��Đݒ�
                dateAmountMap.put(day, 0);
            }
        }
        
        // salesList.csv�̏���
        try (BufferedReader listBR = Files.newBufferedReader(listPath)) {
            String line;
            int cnt = 0;
            
            while ((line = listBR.readLine()) != null) {
                cnt++;
                
                if (cnt == 1) {
                    continue;
                }
                
                // 2�s�ڈȍ~
                String[] cols = line.split(",");
                date = cols[0];
                code = cols[1];
                codeDateMap.put(code, date);
                number = Integer.valueOf(cols[2]);
                price = codePriceMap.get(code);
                
                // ���̉�̂Ȃ��ŁA���̓��̂��̏��i�̑��z���v�Z���A����܂ł̍��v�z�Ƒ����AdateAmountMap�Ɋi�[���Ă����B
                int dayAmount = dateAmountMap.get(date) + number * price; // ���̓��̂��̏��i�̑��z
                
                dateAmountMap.put(date, dayAmount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // �V�����t�@�C�����쐬�A��������
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)){
            // �w�b�_�[���e���������݁E���s
            contents = "�̔���,���㑍�z";
            bw.write(contents);
            bw.newLine();
            
            // ���㑍�z���v�Z
            for (Map.Entry<String, Integer> entry : dateAmountMap.entrySet()) {
                
            // �����Ƃ̔��㑍�z����������
                contents = entry.getKey() + "," + entry.getValue() + "�~"; // ���̍s�̒��g
                bw.write(contents);
                bw.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

}