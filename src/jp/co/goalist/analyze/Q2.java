package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Q2 {
    public static void main(String[] args) {

        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // 読み込み対象ファイルの場所を指定

        int mathBestPoint = 0;
        int physicsBestPoint = 0;
        int chemistryBestPoint = 0;
        int englishBestPoint = 0;// 最高得点を定義

        String wanko = ("わんこ");

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う

                String[] points = line.split(",");// 取り出したカンマ含みの行をを配列に変換
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }//例外処理

        System.out.println("数学の最高得点は、" + "、" + mathBestPoint + "点です。");
        System.out.println("物理の最高得点は、" + "、" + physicsBestPoint + "点です。");
        System.out.println("化学の最高得点は、" + "、" + chemistryBestPoint + "点です。");
        System.out.println("英語の最高得点は、" + "、" + englishBestPoint + "点です。");
    }
}
