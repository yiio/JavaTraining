package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question5 {

    public static void main(String[] args) {

        Path filePath = Paths.get("c:/TechTraining/resources/testResult.csv");

        Map<String, Integer> sumMap = new HashMap<>(); //<名前,合計点>
        Map<String, Integer> aveMap = new HashMap<>(); //<名前,平均点>
        Map<String, String> maxMap = new HashMap<>(); //<名前,最高点の科目>
        Map<String, String> minMap = new HashMap<>(); //<名前,最低点の科目>

        int lineCount1 = -1;

        //ファイルを一度読み込んで行数を調べる
        try (BufferedReader br2 = Files.newBufferedReader(filePath)) {

            String line;
            while ((line = br2.readLine()) != null) {
                lineCount1++;
            }
        } catch (IOException e) {
            System.out.println(e);

        }

        try (BufferedReader br1 = Files.newBufferedReader(filePath)) {

            //1行目を科目リストに追加
            String line;
            line = br1.readLine();
            String[] subject = line.split(",");
            List<String> list = Arrays.asList(subject).subList(1, subject.length); // 科目リスト

            //１度目の読み込みで調べた行数-1の要素数の配列を作成
            int[] sums = new int[lineCount1];
            int[] aves = new int[lineCount1];
            int[] maxes = new int[lineCount1];
            int[] mins = new int[lineCount1];

            int lineCount2 = 0;

            Map<String, Map<String, Integer>> minPointMap = new HashMap<>();
            Map<String, Map<String, Integer>> maxPointMap = new HashMap<>();

            while ((line = br1.readLine()) != null) {

                Map<String, Integer> maxSubMap = new HashMap<>(); //<最高点の科目,最高点>
                Map<String, Integer> minSubMap = new HashMap<>(); //<最低点の科目,最低点>

                //２行目以降を点数リストに追加
                String[] point = line.split(",");
                List<String> pointList = Arrays.asList(point).subList(1, point.length);
                mins[lineCount2] = Integer.parseInt(point[1]);

                for (int x = 0; x < pointList.size(); x++) {

                    int data = Integer.parseInt(pointList.get(x));

                    // 合計の算出
                    sums[lineCount2] += data;
                    sumMap.put(point[0], sums[lineCount2]);

                    // 平均の算出
                    aves[lineCount2] = sums[lineCount2] / pointList.size();
                    aveMap.put(point[0], aves[lineCount2]);

                    // 最高点の決定
                    if (maxes[lineCount2] < data) {
                        maxes[lineCount2] = data;
                        maxMap.put(point[0], list.get(x));

                    //最高点が複数ある場合
                    } else if (maxes[lineCount2] == data) {
                        maxMap.put(point[0], maxMap.get(point[0]) + "と" + list.get(x));
                    }



                    // 最低点の決定
                    if (x == 0) {      //まず最初の点数を最低点として設定
                        mins[lineCount2] = data;
                        minMap.put(point[0], list.get(x));

                    } else if (mins[lineCount2] > data) {
                        mins[lineCount2] = data;
                        minMap.put(point[0], list.get(x));

                    //最低点が複数ある場合
                    } else if (mins[lineCount2] == data) {
                        minMap.put(point[0], minMap.get(point[0]) + "と" + list.get(x));
                    }

                }

                maxSubMap.put(maxMap.get(point[0]), maxes[lineCount2]);
                maxPointMap.put(point[0], maxSubMap);

                minSubMap.put(minMap.get(point[0]), mins[lineCount2]);
                minPointMap.put(point[0], minSubMap);

                lineCount2++;

            }

            //結果の出力
            for (String name : maxMap.keySet()) {
                System.out.println(name + "さんの全科目合計得点は" + sumMap.get(name) + "点、最高点は" + maxMap.get(name) + "で"
                        + maxPointMap.get(name).get(maxMap.get(name)) + "点、" + "最低点は" + minMap.get(name) + "で"
                        + minPointMap.get(name).get(minMap.get(name)) + "点です。");
            }

        } catch (IOException e) {
            System.out.println(e);

        }
    }
}