package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question4_2 {
    public static void main(String[] args) {

        Path filePath = Paths.get("c:/TechTraining/resources/testResult.csv");// 読み込み対象ファイルの場所を指定

        Map<String, Map<String, Integer>> resultMap = new HashMap<>(); // <名前,<科目,点数>>

        // 1行目を科目リストに追加
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            line = br.readLine();
            String[] subject = line.split(",");
            List<String> list = Arrays.asList(subject).subList(1, subject.length); // 科目リスト
            double[] sums = new double[list.size()];

            // 2行目以降を点数リストに追加、名前・科目・点数をマップに格納
            while ((line = br.readLine()) != null) {
                String[] point = line.split(",");
                Map<String, Integer> map = new HashMap<>(); // <科目,点数>
                List<String> pointList = Arrays.asList(point).subList(1, point.length); // 点数リスト
                for (int x = 0; x < list.size(); x++) {
                    map.put(list.get(x), Integer.parseInt(pointList.get(x)));

                }
                resultMap.put(point[0], map);

            }
            // 科目ごとの合計点を算出
            for (int x = 0; x < list.size(); x++) {
                for (String name : resultMap.keySet()) {
                    sums[x] += resultMap.get(name).get(list.get(x));
                }

            }

            // 平均を算出
            for (int x = 0; x < list.size(); x++) {
                double ave = sums[x] / resultMap.keySet().size();
                BigDecimal ave1 = new BigDecimal(ave);
                ave1 = ave1.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.println(list.get(x) + "の平均点は" + ave1 + "です");
            }

        } catch (IOException e) {
            System.out.println(e);

        }

    }
}
