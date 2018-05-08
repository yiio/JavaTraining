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
        }catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            // 多元配列にデータを格納
            int i = 0;
            String[][] scores = new String[a][b];
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
                    try {
                        int point = Integer.parseInt(scores[y][x]);
                        if(max < point){
                            names.clear();
                            names.add(scores[y][0]); // 最高得点者はリストにブチ込みます
                            max = point;
                        }else if(max == point){
                            names.add(scores[y][0]); // 最高得点者が複数いる場合は、そいつもリストにブチ込みましょう
                        }
                    }catch (NumberFormatException e) {
                    } // ここで何も処理しないのってまずいですかね…？
                }

                for(int z = 0; z < names.size(); z++){ // リストに入ってる人数で場合分け
                    if(z == 0){
                        System.out.print(names.get(z));
                    }else {
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