package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Question5 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\\\TechTraining\\\\resources\\\\testResult.csv");

        // データの行列数を調べる
        int a = 0;
        int b = 0;
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] col = line.split(",");
                b = col.length;
                a++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // データを格納
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            String[][] scores = new String[a][b];
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            for (i = 1; i < scores.length; i++) {
                int sum = 0;
                double ave = 0;
                int max = 0;
                int min = 999;
                List<String> maxSubj = new ArrayList<>();
                List<String> minSubj = new ArrayList<>();

                // 合計
                for (int j = 1; j < scores[0].length; j++) {
                    try {
                        sum += Integer.parseInt(scores[i][j]);
                    } catch (NumberFormatException e) {
                    }
                }
                System.out.print(scores[i][0] + "さんの全科目合計は" + sum + "点、");

                // 平均
                ave = sum / (scores.length - 1);
                BigDecimal ave3 = new BigDecimal(String.valueOf(ave));
                ave3 = ave3.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.print("全科目平均点は" + ave3 + "点、");

                // 最高点
                for (int x = 1; x < scores[0].length; x++) {
                    try {
                        int point = Integer.parseInt(scores[i][x]);
                        if (max < point) {
                            maxSubj.clear();
                            maxSubj.add(scores[0][x]);
                            max = point;
                        }else if(max == point) {
                            maxSubj.add(scores[0][x]);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
                for(int z = 0; z < maxSubj.size(); z++){
                    if(z == 0){
                        System.out.print("最高点は" + maxSubj.get(z));
                    }else {
                        System.out.print("と" + maxSubj.get(z));
                    }
                }
                System.out.print("で" + max + "点、");

                // 最低点
                for (int x = 1; x < scores[0].length; x++) {
                    try {
                        int point = Integer.parseInt(scores[i][x]);
                        if (min > point) {
                            minSubj.clear();
                            minSubj.add(scores[0][x]);
                            min = point;
                        }else if(min == point) {
                            minSubj.add(scores[0][x]);
                        }
                    }catch (NumberFormatException e) {
                    }
                }
                for(int z = 0; z < minSubj.size(); z++){
                    if(z == 0){
                        System.out.print("最低点は" + minSubj.get(z));
                    }else {
                        System.out.print("と" + minSubj.get(z));
                    }
                }
                System.out.println("で" + min + "点です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
