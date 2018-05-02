package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question1 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            
            
            //課題1ここから
            String line;
            int tokyo = 0;
            int kanagawa = 0;
            int hyougo = 0;
            int oosaka = 0;
            int kyouto = 0;
            int tiba = 0;
            int saitama = 0;
            int nara = 0;
            
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                System.out.println(line); // 1行ずつ出力する
                
                
                switch (line) {
                case "東京都":
                    tokyo ++;
                    break;
                case "神奈川県":
                    kanagawa ++;
                    break;
                case "兵庫県":
                    hyougo ++;
                    break;
                case "大阪府":
                    oosaka ++;
                    break;
                case "京都府":
                    kyouto ++;
                    break;
                case "千葉県":
                    tiba ++;
                    break;
                case "埼玉県":
                    saitama ++;
                    break;
                case "奈良県":
                    nara ++;
                    break;
                    
                }
                
                
            }
            System.out.println("東京都は、" + tokyo + "件です。");
            System.out.println("神奈川県は、" + kanagawa + "件です。");
            System.out.println("兵庫県は、" + hyougo + "件です。");
            System.out.println("大阪府は、" + oosaka + "件です。");
            System.out.println("京都府は、" + kyouto + "件です。");
            System.out.println("千葉県は、" + tiba + "件です。");
            System.out.println("埼玉県は、" + saitama + "件です。");
            System.out.println("奈良県は、" + nara + "件です。");
            
            
            //課題1ここまで
            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
