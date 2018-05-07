package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Question4 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            String[][] scores = new String[6][5];
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            for (int j = 1; j < scores[0].length; j++) {
                double sum = 0;
                double ave = 0;
                for (i = 1; i < scores.length; i++) {
                    sum += Integer.parseInt(scores[i][j]);
                }
                ave = sum / (scores.length - 1);
                BigDecimal ave3 = new BigDecimal(String.valueOf(ave));
                ave3 = ave3.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.println(scores[0][j] + "の平均点は" + ave3 + "点です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}