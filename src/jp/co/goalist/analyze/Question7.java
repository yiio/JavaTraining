package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question7 {
    public static void main(String[] args) {
        Path itemPath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path listPath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        
        Map<String, String> codeNameMap = new LinkedHashMap<String, String>(); // 商品コード, 商品名
        Map<String, Integer> codePriceMap = new HashMap<String, Integer>(); // 商品コード, 商品単価
        Map<String, Integer> codeSumMap = new HashMap<String, Integer>(); // 商品コード, 販売合計数
        
        String code; // 商品コードの変数
        String name; // 商品名の変数
        int price; // 商品単価の変数
        int number; // 1件ごとの販売数量
        int sum = 0; // 商品ごとの合計販売数
        
        // salesItem.csvを処理
        try (BufferedReader itemBR = Files.newBufferedReader(itemPath)) {
            String line;
            int cnt = 0;
            
            while ((line = itemBR.readLine()) != null) {
                cnt++;
                // 1行目は飛ばす
                if (cnt == 1) {
                    continue;
                }
                
                // 2行目以降
                String[] cols = line.split(",");
                code = cols[0];
                name = cols[1];
                price = Integer.valueOf(cols[2]);
                // codeNameMapへ「商品コード, 商品名」を、codePriceMapへ「商品コード, 値段」を格納
                codeNameMap.put(code, name);
                codePriceMap.put(code, price);
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // salesList.csvを処理
        try (BufferedReader listBR = Files.newBufferedReader(listPath)) {
            String line;
            int cnt = 0;
            
            while ((line = listBR.readLine()) != null) {
                cnt++;
                
                // 1行目は飛ばす
                if (cnt == 1) {
                    continue;
                }
                
                // 2行目以降
                String[] cols = line.split(",");
                code = cols[1];
                number = Integer.valueOf(cols[2]);
                sum += number;
                codeSumMap.put(code, sum);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 売上高合計を計算　→　3桁ごとにカンマで区切る　→　結果を出力
        for (Map.Entry<String, String> entry : codeNameMap.entrySet()) {
            code = entry.getKey(); // 商品コードを順番に取得
            name = entry.getValue(); // codeの商品コードに対応した商品名を取得
            price = codePriceMap.get(code); // codeの商品コードに対応した商品単価を取得
            sum = codeSumMap.get(code); // codeの商品コードに対応した販売合計数を取得
            
            // 商品ごとの売上高合計を計算
            int amount = price * sum;
            
            // 売上高を3桁ごとにカンマで区切る
            String viewAmount = String.format("%, d", amount);
            
            // 結果を出力
            System.out.println("「" + name + "」の売上高合計は、" + viewAmount + "円  です。");
        }
    }

}
