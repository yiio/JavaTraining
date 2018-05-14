
package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class analyz_Q2 {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:/TechTraining/resources/testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line; // 1行分の文字列を格納する変数
            int cnt = 0; // 何行目
            int max = 0; // 最高点
            while ((line = br.readLine()) != null) {
                cnt++;
                if (cnt == 1) { // ヘッダ行はスキップ
                    continue;
                }
                // ヘッダ行以外の処理
                String[] cols = line.split(","); // 1行をカンマ区切りで配列に分割する
                int mathPoint = Integer.valueOf(cols[1]); // 数学の点数は配列の1番目にある
                if (max < mathPoint) { // 今までの最高点と比べて大きければ、この点数が最高点ということになる
                    max = mathPoint;
                }
            }
            // 結果を出力
            System.out.println("数学の最高得点は" + max + "点です");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
