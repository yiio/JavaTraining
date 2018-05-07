package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question3 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            // データを二次元配列に格納する
            String[][] scores = new String[6][5];
            int i = 0;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            // 最高得点者の特定
            int max = 0;
            String name = "";
            for (int j = 1; j < scores[0].length; j++) {
                for (i = 1; i < scores.length; i++) {
                    max = Math.max(max, Integer.parseInt(scores[i][j]));
                    if (max == (Integer.parseInt(scores[i][j]))) {
                        name = scores[i][0];
                    }
                }
                System.out.println(scores[0][j] + "の最高得点者は" + name + "さん、" + max + "点です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
