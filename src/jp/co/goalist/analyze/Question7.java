package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question7 {
    public static void main(String[] args) {

        // salesitem.csvを読み込んで、商品コードと単価・商品名それぞれとの対応関係をマップに格納
        Path filePath1 = Paths.get("c:/TechTraining/resources/salesitem.csv");
        Map<String, Integer> priceMap = new HashMap<>(); // <商品コード,単価>
        Map<String, String> prodMap = new HashMap<>(); // <商品コード,商品名>
        String[] elems1 = null;
        int lineCount = 0;

        //
        try (BufferedReader br1 = Files.newBufferedReader(filePath1)) {

            String line = br1.readLine();

            while ((line = br1.readLine()) != null) {

                elems1 = line.split(",");
                priceMap.put(elems1[0], Integer.parseInt(elems1[2]));
                prodMap.put(elems1[0], elems1[1]);
                lineCount++;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        // salesList.csvを読み込んで、priceMapによって取得した単価に数量をかけて加算し、合計を計算
        Path filePath2 = Paths.get("c:/TechTraining/resources/salesList.csv");
        int[] sales = new int[lineCount];
        List<String> codeList = null;

        try (BufferedReader br2 = Files.newBufferedReader(filePath2)) {

            String line = br2.readLine();

            while ((line = br2.readLine()) != null) {

                String[] elems2 = line.split(",");
                codeList = new ArrayList<>(priceMap.keySet());

                for (int x = 0; x < codeList.size(); x++) {

                    // 商品コードが一致した場合、その商品ごとの売上高に加算
                    if (codeList.get(x).equals(elems2[1])) {
                        sales[x] += priceMap.get(codeList.get(x)) * Integer.parseInt(elems2[2]);
                    }
                }

            }

            // 結果を出力
            for (int x = 0; x < codeList.size(); x++) {
                System.out.println("「" + prodMap.get(codeList.get(x)) + "」" + "の売上高合計は、" + sales[x] + "円です。");
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
