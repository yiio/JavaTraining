package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class sample {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // �ǂݍ��ݑΏۃt�@�C���̏ꏊ���w��

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) { // �t�@�C����1�s���ǂݍ��݁A���ʂ�null�ȊO�Ȃ��while���[�v���̏������s��
                System.out.println(line); // 1�s���o�͂���
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    

}
