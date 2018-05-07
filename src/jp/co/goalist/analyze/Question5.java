package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question5 {

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

            for (i = 1; i < scores.length; i++) {
                int scoreSum = 0;
                int average = 0;
                int max = 0;
                int min = 100;
                String subjectMax = "";
                String subjectMin = "";
                String name = scores[i][0];

                for (int j = 1; j < scores[i].length; j++) {
                    int score = Integer.parseInt(scores[i][j]);
                    scoreSum += score;// 全教科合計点

                    // 最高点
                    if (max < score) {
                        subjectMax = scores[0][j];
                    } else if (max == score) {
                        subjectMax += "と" + scores[0][j];
                    }
                    max = Math.max(max, score);

                    // 最低点
                    if (min > score) {
                        subjectMin = scores[0][j];
                    } else if (min == score) {
                        subjectMin += "と" + scores[0][j];
                    }
                    min = Math.min(min, score);

                }
                average = scoreSum / (scores[i].length - 1);// 全教科平均点

                System.out.println(name + "さんの全科目合計点は" + scoreSum + "点、全科目平均点は" + average + "点、最高点は" + subjectMax + "で" + max + "点、最低点は" + subjectMin + "で" + min + "点です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
