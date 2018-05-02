package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question2 {
    public static void main(String[] args) {

        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            Map<String, String> mathScore = new HashMap<String, String>();

            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                System.out.println(line); // 1行ずつ出力する

                String[] cols = line.split(",");

                mathScore.put(cols[0], cols[1]);

            }

            mathScore.remove("氏名");
            System.out.println(mathScore);

            int max = 0;

            for (String key : mathScore.keySet()) {
                int i = Integer.parseInt(mathScore.get(key));
                max = Math.max(max, i);
            }

            System.out.println("\n数学の最高得点は" + max + "です。");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
