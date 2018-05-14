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

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String test;
            while ((test = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                String[] testPoints = test.split(",");// 取り出したカンマ含みの行をを配列に変換
                if (test.contains("数学")) { // 1行に「数学」が含まれていたら
                    continue;// スキップ
                } else if (mathBestPoint < Integer.parseInt(testPoints[1])) {// もしもtestPoints[1]がmathBestPointよりおおきければ

                    mathBestPoint = Integer.parseInt(testPoints[1]);// mathBestPointがtestPoint[1]を数字に直したものに置き換わる
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } // 例外処理

        System.out.println("数学の最高得点は、" + mathBestPoint + "点です。");
    }
}
