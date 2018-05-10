package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question6 {

    public static void main(String[] args) {

        Path filePath = Paths.get("c:/TechTraining/resources/testResult.csv");

        int lineCount1 = -1;

        // ファイルを一度読み込んで行数を調べる
        try (BufferedReader br1 = Files.newBufferedReader(filePath)) {

            String line;
            while ((line = br1.readLine()) != null) {
                lineCount1++;
            }

        } catch (IOException e) {
            System.out.println(e);

        }

        int[] sums = new int[lineCount1];
        int lineCount2 = 0;
        Map<Integer, String> sumMap = new TreeMap<>();

        try (BufferedReader br2 = Files.newBufferedReader(filePath)) {

            // ２行目以降を点数リストに追加
            String line;
            line = br2.readLine();
            String[] subject = line.split(",");
            List<String> list = Arrays.asList(subject).subList(1, subject.length);
            Map<Integer, Integer> rankMap = new HashMap<>(); // <順位,点数>
            Map<Integer, String> nameMap = new HashMap<>(); // <順位, 名前>

            while ((line = br2.readLine()) != null) {

                String[] point = line.split(",");
                List<String> pointList1 = Arrays.asList(point).subList(1, point.length);

                // 各人の合計点数を計算
                for (int x = 0; x < list.size(); x++) {
                    sums[lineCount2] += Integer.parseInt(pointList1.get(x));
                }

                sumMap.put(sums[lineCount2], point[0]); // <合計点数,名前>
                lineCount2++;

            }

            // TreeMapによってValueで昇順にソートされた点数をリストへ追加
            List<Integer> pointList2 = new ArrayList<>(sumMap.keySet());

            // (人数)-(pointList内の要素番号)で順位を決定
            for (int x = 0; x < lineCount2; x++) {
                int result = pointList2.get(x);
                int rank = lineCount2 - x;
                rankMap.put(rank, result);
                nameMap.put(rank, sumMap.get(result));
            }

            // 結果を出力
            for (int x = 1; x <= lineCount2; x++) {
                System.out.println(x + "位: " + nameMap.get(x) + "さん　" + rankMap.get(x) + "点");
            }

        } catch (IOException e) {
            System.out.println(e);

        }
    }

}
