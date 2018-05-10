package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question5 {

    public static void main(String[] args) {
        Path filePath = Paths.get("/Users/yiio/workspace/resources/testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // まずはヘッダ行の処理
            String line = br.readLine();
            String[] header = line.split(","); // ヘッダー行を配列として格納する
            // ヘッダ行以外の処理
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                printScore(header, cols);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 行ごとに点数を計算し、出力する
     * ネストが深くなり過ぎると見通しが悪いので、別メソッドに取り出してみた
     */
    private static void printScore(String[] header, String[] cols) {
        int sum = 0; // 全科目合計点
        int max = -1; // 最高点
        String maxSubject = ""; // 最高点の科目名
        int min = 101; // 最低点
        String minSubject = ""; // 最低点の科目名

        // 科目ごとの得点を見ていく
        for (int i = 1; i < cols.length; i++) {
            String subject = header[i]; // この科目名
            int point = Integer.valueOf(cols[i]); // この人のこの科目の点数
            // 合計
            sum += point;
            // 最高点の算出
            if (max < point) {
                max = point;
                maxSubject = subject;
            } else if (max == point) { // 最高点が複数ある場合、科目名に追加
                maxSubject = maxSubject + "と" + subject;
            }
            // 最低点の算出
            if (min > point) {
                min = point;
                minSubject = subject;
            } else if (min == point) {
                minSubject = minSubject + "と" + subject;
            }
        }
        // 平均点の算出
        double ave = (double) sum / (cols.length - 1);
        String aveStr = String.format("%.2f", ave);

        // 結果の出力
        String out = cols[0] + "さんの全科目合計点は" + sum + "点、全科目平均点は" + aveStr + "点、"
                + "最高点は" + maxSubject + "で" + max + "点、"
                + "最低点は" + minSubject + "で" + min + "点です。";
        System.out.println(out);
    }

}
