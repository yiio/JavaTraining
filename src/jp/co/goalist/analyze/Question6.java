package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Question6 {
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

            int sum = 0;
            Map<Integer,String> rankMap = new TreeMap<Integer,String>( new Comparator<Integer>() {
                public int compare(Integer m, Integer n){
                    return ((Integer)m).compareTo(n) * -1;
                }
            });

            // 合計を調べてマップに追加
            for (i = 1; i < scores.length; i++) {
                for (int j = 1; j < scores[0].length; j++) {
                    try {
                        sum += Integer.parseInt(scores[i][j]);
                    } catch (NumberFormatException e) {
                    }
                }
                rankMap.put (sum,scores[i][0]);
            }

            int x = 1;
            for(int score : rankMap.keySet()){
            System.out.println(x + "位：" + rankMap.get(score) + "さん" + score + "点");
            x ++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
