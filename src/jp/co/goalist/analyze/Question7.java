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



        // ---------- salesList.csvの処理ゾーンここから ----------
        int a = 0;
        int b = 0;
        try (BufferedReader br = Files.newBufferedReader(salesList)) {
            String line;
            line = br.readLine();//first line
            while ((line = br.readLine()) != null) {
                String[] col = line.split(",");
                b = col.length;
                a++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] sList = new String[a][b];
        try (BufferedReader br = Files.newBufferedReader(salesList)) {
            String line;
            line = br.readLine();//first line
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                sList[i] = cols;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ---------- salesList.csvの処理ゾーンここまで ----------



        // ---------- salesItem.csvの処理ゾーンここから ----------
        int c = 0;
        int d = 0;
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {
            String line;
            line = br.readLine(); // first line
            while ((line = br.readLine()) != null) {
                String[] col = line.split(",");
                d = col.length;
                c++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] sItem = new String[c][d];
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {
            String line;
            line = br.readLine(); // first line
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                sItem[i] = cols;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ---------- salesItem.csvの処理ゾーンここまで ----------



        // Mapの初期化
        Map<String,String> codeMap = new HashMap<>(); // 商品コード,商品名
        Map<String,Integer> tankaMap = new HashMap<>(); // 商品コード,単価
        Map<String,Integer> sumMap = new HashMap<>(); // 商品名,金額



        // Mapに各要素追加
        for(int i = 0; i < sItem.length; i++) {
            codeMap.put( sItem[i][0], sItem[i][1]);
            tankaMap.put( sItem[i][0], Integer.parseInt(sItem[i][2]));
            if(!sumMap.containsKey(sItem[i][1])) {
                sumMap.put( sItem[i][1], 0);
            }
        }
        for(int i = 0; i < sList.length; i++) {
            String sItemCode = sList[i][1];
            int sPrice = tankaMap.get(sItemCode) * Integer.parseInt(sList[i][2]);
            sumMap.put(codeMap.get(sItemCode), sumMap.get(codeMap.get(sItemCode)) + sPrice);
        }



        // おわおわり
        for(String item : sumMap.keySet()) {
            int sumPrice = sumMap.get(item);
            System.out.println("「" + item + "」の売上高合計は、" + String.format("%,d", sumPrice) + "円です。");
        }
    }
}
