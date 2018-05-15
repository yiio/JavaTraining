package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question6 {

    public static void main(String[] args) {
        Path filePath = Paths.get("/Users/yiio/workspace/resources/testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // 今回ヘッダ行は使う必要なし
            String line = br.readLine();

            // 合計点と名前の対応表を作る
            Map<Integer, String> sumNameMap = new HashMap<Integer, String>();
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                int sum = 0;
                for (int i = 1; i < cols.length; i++) {
                    sum += Integer.parseInt(cols[i]);
                }
                sumNameMap.put(sum, cols[0]);
            }

            // 合計点のリストを降順に並び替える
            List<Integer> sumList = new ArrayList<Integer>(sumNameMap.keySet());
            Collections.sort(sumList, Comparator.reverseOrder());

            // 並び替えた合計点順で結果を出力
            int rank = 0;
            for (int sum : sumList) {
                rank++;
                String name = sumNameMap.get(sum);
                System.out.println(String.valueOf(rank) + "位：" + name + "さん " + sum + "点");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
