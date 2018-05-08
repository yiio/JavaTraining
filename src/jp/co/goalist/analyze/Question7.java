package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question7 {
    public static void main(String[] args) {
        Path filePath1 = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path filePath2 = Paths.get("C:\\TechTraining\\resources\\salesList.csv");

        HashMap<String, String> codeNameMap = new HashMap<String, String>();
        HashMap<String, Integer> codePriceMap = new HashMap<String, Integer>();

        int cnt = 0;
        int cnt2 = 0;

        // コードと商品名、コードと単価の対応表を作る
        try (BufferedReader br = Files.newBufferedReader(filePath1)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                codeNameMap.put(cols[0], cols[1]);
                codePriceMap.put(cols[0], Integer.parseInt(cols[2]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Integer> codeNumMap = new HashMap<String, Integer>();
        HashMap<String, Integer> codeSumMap = new HashMap<String, Integer>();

        // コードと数量の対応表を作る
        try (BufferedReader br = Files.newBufferedReader(filePath2)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt2++;
                if (cnt2 == 1) {
                    continue;
                }

                if (codeNumMap.containsKey(cols[1])) {
                    int sum = codeNumMap.get(cols[1]) + Integer.parseInt(cols[2]);
                    codeNumMap.put(cols[1], sum);
                } else {
                    int sum = Integer.parseInt(cols[2]);
                    codeNumMap.put(cols[1], sum);
                }
            }

            // 売上の計算
            for (Map.Entry<String, Integer> entry : codeNumMap.entrySet()) {
                String code = entry.getKey();
                if (codeSumMap.containsKey(code)) {
                    int sum = codeSumMap.get(code);
                    sum += codePriceMap.get(code) * entry.getValue();
                    codeSumMap.put(code, sum);
                } else {
                    int sum = codePriceMap.get(code) * entry.getValue();
                    codeSumMap.put(code, sum);
                }
            }

            // 売上高合計の書き出し
            for (Map.Entry<String, String> entry : codeNameMap.entrySet()) {
                String code = entry.getKey();
                String name = entry.getValue();
                int price = codeSumMap.get(code);
                System.out.println("「" + name + "」の売上高合計は、" + String.format("%, d", price) + "円です。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
