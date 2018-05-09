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

        Path salesItem = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path salesList = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        Map<String,String> codeMap = new HashMap<>(); // 商品コード,商品名
        Map<String,Integer> tankaMap = new HashMap<>(); // 商品コード,単価
        Map<String,Integer> numMap = new HashMap<>(); // 商品コード,売上数
        Map<String,Integer> sumMap = new HashMap<>(); // 商品名,金額


        // salesItem.csvからMapへ要素の追加
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {
            String line;
            line = br.readLine(); // first line
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                codeMap.put( elem[0], elem[1]);
                tankaMap.put( elem[0], Integer.parseInt(elem[2]));
                numMap.put( elem[0], 0);
                sumMap.put( elem[1], 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // salesList.csvからnumMapへ要素の追加
        try (BufferedReader br = Files.newBufferedReader(salesList)) {
            String line;
            line = br.readLine();//first line
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String code = elem[1];
                int num = Integer.parseInt(elem[2]);
                numMap.put(code, numMap.get(code) + num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // sumMapに要素の追加
        for(String code : numMap.keySet()) {
            int price = tankaMap.get(code) * numMap.get(code);
            String name = codeMap.get(code);
            sumMap.put(name, sumMap.get(name) + price);
        }


        // おわおわり
        for(String item : sumMap.keySet()) {
            int sumPrice = sumMap.get(item);
            System.out.println("「" + item + "」の売上高合計は、" + String.format("%,d", sumPrice) + "円です。");
        }
    }
}
