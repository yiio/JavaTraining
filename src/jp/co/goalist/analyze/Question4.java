package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question4 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            // データを二次元配列に格納する
            String line;
            String[][] scores = new String[6][5];
            int i = 0;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            // 平均点の計算
            double sum = 0;
            double average = 0;
            for (int j = 1; j < scores[0].length; j++) {
                for (i = 1; i < scores.length; i++) {
                    double score = Double.parseDouble(scores[i][j]);
                    sum += score;
                }
                average = sum / (scores.length - 1);
                BigDecimal bdAverage = new BigDecimal(String.valueOf(average));
                BigDecimal average3 = bdAverage.setScale(2, RoundingMode.HALF_UP);
                System.out.println(scores[0][j] + "の平均点は" + average3 + "点です。");
                sum = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
