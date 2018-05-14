package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question8 {
    
    public static void main(String[] args) {
        Path itemPath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path listPath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        Path filePath = Paths.get("C:\\TechTraining\\JavaTraining\\answer1.csv");
        
        Map<String, String> codeDateMap = new HashMap<String,String>(); // 商品コード, 販売日
        Map<String, Integer> codePriceMap = new HashMap<String, Integer>(); // 商品コード, 単価
        Map<String, Integer> dateAmountMap = new LinkedHashMap<String, Integer>(); // 販売日, 日ごとの売上総額
        
        String code; // 商品コードの変数
        String date; // 販売日の変数
        int price; // 商品単価の変数
        int number; // 1件ごとの販売数量
        String contents = ""; // 新しいファイルに書き込む内容
        
        // salesItem.csvの処理
        try (BufferedReader itemBR = Files.newBufferedReader(itemPath)) {
            String line;
            int cnt = 0;
            
            while ((line = itemBR.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                // 2行目以降
                String[] cols = line.split(",");
                code = cols[0];
                price = Integer.valueOf(cols[2]);
                codePriceMap.put(code, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* dateAmountMapに、キーに日付を、値に0を初期値として格納
         * 1~2月の全日付を取得
         */ 
        for (int month = 0; month < 2; month++) {
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.MONTH, month);
            cl.set(Calendar.YEAR, 2017);
            int dom = cl.getActualMaximum(Calendar.DAY_OF_MONTH); // その月の日数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
            
            for (int date1 = 0; date1 < dom; date1++) {
                cl.set(Calendar.DATE, date1 + 1);
                String day = sdf.format(cl.getTime());
                
                // dateAmountMapに初期値として設定
                dateAmountMap.put(day, 0);
            }
        }
        
        // salesList.csvの処理
        try (BufferedReader listBR = Files.newBufferedReader(listPath)) {
            String line;
            int cnt = 0;
            
            while ((line = listBR.readLine()) != null) {
                cnt++;
                
                if (cnt == 1) {
                    continue;
                }
                
                // 2行目以降
                String[] cols = line.split(",");
                date = cols[0];
                code = cols[1];
                codeDateMap.put(code, date);
                number = Integer.valueOf(cols[2]);
                price = codePriceMap.get(code);
                
                // この塊のなかで、その日のその商品の総額を計算し、それまでの合計額と足し、dateAmountMapに格納しておく。
                int dayAmount = dateAmountMap.get(date) + number * price; // その日のその商品の総額
                
                dateAmountMap.put(date, dayAmount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // 新しいファイルを作成、書き込み
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)){
            // ヘッダー内容を書き込み・改行
            contents = "販売日,売上総額";
            bw.write(contents);
            bw.newLine();
            
            // 売上総額を計算
            for (Map.Entry<String, Integer> entry : dateAmountMap.entrySet()) {
                
            // 日ごとの売上総額を書き込み
                contents = entry.getKey() + "," + entry.getValue() + "円"; // その行の中身
                bw.write(contents);
                bw.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

}