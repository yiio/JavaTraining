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

public class Question3_2 {
    public static void main(String[] args) {
        Path filePath = Paths.get("c:/TechTraining/resources/testResult.csv");// 読み込み対象ファイルの場所を指定

        Map<Integer, String> nameMap = new HashMap<>(); // <点数,名前>
        Map<Integer, String> subMap = new HashMap<>(); // <点数,科目>

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            //１行目を科目リストに追加

            String line;
            line = br.readLine();
            String[] subject = line.split(",");
            List<String> list = Arrays.asList(subject).subList(1, subject.length); // 科目リスト
            int max[] = new int[list.size()];

            //２行目以降を点数リストに追加
            while ((line = br.readLine()) != null) {
                String[] point = line.split(",");

                List<String> pointList = Arrays.asList(point).subList(1, point.length); // 点数リスト

                // 最高得点の決定
                for (int x = 0; x < list.size(); x++) {
                    int data = Integer.parseInt(pointList.get(x));
                    subMap.put(data, list.get(x));
                    nameMap.put(data, point[0]);
                    if (max[x] < data) {
                        max[x] = data;
                    }

                }

            }
            //科目ごとの最高得点者・得点の出力
            for (int x = 0; x < list.size(); x++) {
                System.out.println(subMap.get(max[x]) + "の最高点者は" + nameMap.get(max[x]) + "さん、" + max[x] + "点です");
            }

        } catch (IOException e) {
            System.out.println(e);

        }
    }
}