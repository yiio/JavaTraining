package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

        // <合計点数,名前>のTreeMapを作成、降順にソート
        Map<Integer, String> sumMap = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer m, Integer n) {
                return ((Integer) m).compareTo(n) * -1;
            }
        });

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

                sumMap.put(sums[lineCount2], point[0]);
                lineCount2++;

            }

            // TreeMapによってValueで降順にソートされた点数をリストへ追加
            List<Integer> pointList2 = new ArrayList<>(sumMap.keySet());

            // 順位を決定、出力
            for (int x = 0; x < lineCount2; x++) {
                int rank = x + 1;
                int result = pointList2.get(x);
                rankMap.put(rank, result);
                nameMap.put(rank, sumMap.get(result));
                System.out.println(rank + "位: " + nameMap.get(rank) + "さん　" + rankMap.get(rank) + "点");
            }

        } catch (IOException e) {
            System.out.println(e);

        }
    }

}
