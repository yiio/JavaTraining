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

            String[][] scores = new String[6][5];
            int i = 0;

            //データを二次元配列に格納する
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            // 最高得点者の特定
            int max = 0;
            String name = "";
            for (i = 1; i < scores.length - 1; i++) {
                max = Integer.parseInt(scores[i][1]);
                max = Math.max(max, i);
                if (max == (Integer.parseInt(scores[i][1]))) {
                    name = scores[i][0];
                }
            }
            System.out.println("数学の最高得点者は" + name + "さん、" + max + "点です。");

            for (i = 1; i < scores.length - 1; i++) {
                max = Integer.parseInt(scores[i][2]);
                max = Math.max(max, i);
                if (max == (Integer.parseInt(scores[i][2]))) {
                    name = scores[i][0];
                }
            }
            System.out.println("物理の最高得点者は" + name + "さん、" + max + "点です。");

            for (i = 1; i < scores.length - 1; i++) {
                max = Integer.parseInt(scores[i][3]);
                max = Math.max(max, i);
                if (max == (Integer.parseInt(scores[i][3]))) {
                    name = scores[i][0];
                }
            }
            System.out.println("化学の最高得点者は" + name + "さん、" + max + "点です。");

            for (i = 1; i < scores.length - 1; i++) {
                max = Integer.parseInt(scores[i][4]);
                max = Math.max(max, i);
                if (max == (Integer.parseInt(scores[i][4]))) {
                    name = scores[i][0];
                }
            }
            System.out.println("英語の最高得点者は" + name + "さん、" + max + "点です。");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
