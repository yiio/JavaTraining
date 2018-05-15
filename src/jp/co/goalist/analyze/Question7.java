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

public class Question7 {

    public static void main(String[] args) {
        // 商品マスタから、商品コードと商品名や単価などの情報との対応表を作る
        Map<String, Map<String, String>> codeItemMap = new HashMap<String, Map<String, String>>(); // key: 商品コード, value: (key: 要素名, value: 内容)
        Path itemFilePath = Paths.get("/Users/yiio/workspace/resources/salesItem.csv");
        try (BufferedReader br = Files.newBufferedReader(itemFilePath)) {
            String line = br.readLine();
            String[] header = line.split(",");
            while ((line = br.readLine()) != null) {
                Map<String, String> itemMap = new HashMap<String, String>(); // key: 要素名, value: 内容
                String[] cols = line.split(",");
                for (int i = 0; i < cols.length; i++) { // 各商品マスタの要素と内容を格納していく
                    String key = header[i];
                    String value = cols[i];
                    itemMap.put(key, value);
                }
                codeItemMap.put(itemMap.get("商品コード"), itemMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 売り上げデータから商品名と売り上げ額の対応表を作る
        Map<String, Integer> nameSumMap = new HashMap<String, Integer>();
        Path salesFilePath = Paths.get("/Users/yiio/workspace/resources/salesList.csv");
        try (BufferedReader br = Files.newBufferedReader(salesFilePath)) {

            // ヘッダ行の処理
            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfCode = headerList.indexOf("商品コード"); // 商品コードは毎行を配列にした時、何番目になるか

            // 2行目以降の処理
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");

                // 商品コードから、商品マスタのデータを取得
                String code = cols[indexOfCode];
                Map<String, String> item = codeItemMap.get(code);
                String name = item.get("商品名");
                int price = Integer.parseInt(item.get("単価"));

                // 商品名と売り上げ額の対応表を更新
                if (nameSumMap.containsKey(name)) {
                    int sum = nameSumMap.get(name) + price;
                    nameSumMap.put(name, sum);
                } else {
                    nameSumMap.put(name, price);
                }
            }

            // 結果を出力
            for (Map.Entry<String, Integer> entry : nameSumMap.entrySet()) {
                String name = entry.getKey();
                String sumStr = String.format("%,d", entry.getValue());
                System.out.println("「" + name + "」の売上高合計は、" + sumStr + "円です");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
