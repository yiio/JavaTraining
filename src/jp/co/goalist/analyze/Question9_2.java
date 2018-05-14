package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question9_2 {
    public static void main(String[] args) {

        Path salesPrice = Paths.get("c:/TechTraining/resources/salesPrice.csv");
        int lineCount = 0;
        List<String> codeList = new ArrayList<String>(); // 商品コードリスト
        List<String> dateList = new ArrayList<String>(); // 単価改定の日付リスト
        List<String> priceList = new ArrayList<String>(); // 改定後の単価リスト

        try (BufferedReader br = Files.newBufferedReader(salesPrice)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] elems = line.split(",");

                // 1行ごとに商品コード、日付、単価をそれぞれリストに追加
                codeList.add(elems[0]);
                dateList.add(elems[1]);
                priceList.add(elems[2]);

                lineCount++; // 行数カウント、for文で改定単価を適用するときのため
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        Map<String, Integer> priceMap = new HashMap<>(); // <商品コード,単価>
        Map<String, Integer> salesMap = new TreeMap<>(); // <日付,売上>
        Path salesItem = Paths.get("c:/TechTraining/resources/salesItem.csv");

        // 商品コードと改定前の単価をpriceMapに格納
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] elems = line.split(",");
                priceMap.put(elems[0], Integer.parseInt(elems[2]));

            }

        } catch (IOException e) {
            System.out.println(e);
        }

        Path salesList = Paths.get("c:/TechTraining/resources/salesList.csv");
        List<String> dateList2 = new ArrayList<String>(); // salesList.csvの日付リスト
        int lineCount2 = 0;

        Calendar firstDate = Calendar.getInstance();
        Calendar lastDate = Calendar.getInstance();

        try (BufferedReader br = Files.newBufferedReader(salesList)) {

            String line = br.readLine(); // 1行目

            while ((line = br.readLine()) != null) {

                // 1行ごとに配列に分解、日付をリストへ追加
                String[] elems = line.split(",");
                String date = elems[0];
                dateList2.add(date);

                // salesList.csvの日付がsalesPrice.csvの日付より後だった場合、改定後の単価を適用
                for (int x = 0; x < lineCount; x++) {

                    String code = codeList.get(x);

                    // 商品コードが一致、かつ改定の日付よりも後のとき、単価を上書き
                    if ((elems[1].equals(code)) && (date.compareTo(dateList.get(x)) >= 0)) {
                        priceMap.put(code, Integer.parseInt(priceList.get(x)));
                    }

                }

                // 日付ごとの売上を求める
                int price = priceMap.get(elems[1]);
                int number = Integer.parseInt(elems[2]);

                //日付がsalesMapに含まれていない場合、追加
                if (!salesMap.containsKey(elems[0])) {
                    salesMap.put(date, price * number);

                //含まれている場合、同じ日付のものに加算
                } else {
                    salesMap.put(date, salesMap.get(date) + (price * number));
                }

                lineCount2++; // 行数カウント、初日と最終日を求めるため
            }

            String[] date1 = dateList2.get(0).split("/"); // salesList.csvの初日の日付
            String[] date2 = dateList2.get(lineCount2 - 1).split("/"); // salesList.csvの最終日の日付

            // 初日をdate1の月の１日に設定
            firstDate.set(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1, 01);
            // 最終日date2の月の次月の１日に設定
            lastDate.set(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]), 01);

            // 初日と最終日の差分を求める
            long diff = lastDate.get(Calendar.DAY_OF_YEAR) - firstDate.get(Calendar.DAY_OF_YEAR); // 初日と最終日の差分
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 年/月/日の形に表示するよう設定

            // 差分の回数だけ日付に+1していき、salesMapのKeyに含まれない日付、売上0を追加
            for (int x = 0; x < diff; x++) {

                String now = sdf.format(firstDate.getTime());

                // 売上0の日付を追加していく
                if (!salesMap.containsKey(now)) {
                    salesMap.put(now, 0);

                }
                firstDate.add(Calendar.DATE, 1);// 日付を+1
            }

            System.out.println(salesMap);

        } catch (IOException e) {
            System.out.println(e);
        }

        Path filePath = Paths.get("c:/TechTraining/resources/answer.csv"); // 書き込み対象ファイルの場所を指定
        try {

            Files.deleteIfExists(filePath); // 既に存在してたら削除
            Files.createFile(filePath); // ファイル作成

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            bw.write("販売日" + "," + "売上総額"); // 1行目
            bw.newLine();

            // 1日ごとの売上をファイルに書き込む
            for (String sales : salesMap.keySet()) {
                bw.write(sales + "," + salesMap.get(sales) + "円");
                bw.newLine(); // 改行
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
