package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Question3 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            // 配列にデータを入れていきます
            String[][] scores = new String[6][5];
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                scores[i] = cols;
                i++;
            }

            for (int x = 1; x < scores[0].length; x++) { // 教科の指定
                int max = 0;
                List<String> names = new ArrayList<>();
                System.out.print(scores[0][x] + "の最高得点者は");

                for (int y = 1; y < scores.length; y++) {
                    int point = Integer.parseInt(scores[y][x]); // 最高得点を調べましょう
                    if(max < point){
                        names.clear();
                        names.add(scores[y][0]); // 最高得点者はリストにブチ込みましょう
                        max = point;
                    }else if(max == point){
                        names.add(scores[y][0]); // 最高得点者が複数いる場合はリストにブチ込みましょう
                    }
                }

                for(int z = 0; z < names.size(); z++){
                    if(z == 0){ // 最高得点者がひとり
                        System.out.print(names.get(z));
                    }else { // 最高得点者が複数
                        System.out.print("と" + names.get(z));
                    }
                }
                System.out.println("で、" + max + "です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}