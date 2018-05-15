package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question9 {

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

        // 商品価格改定表から、価格改定日や単価などの情報を取り出す
        // 価格改定情報を取り出したリストから、商品コードごとに価格改定日や単価などの情報リストを持つマップを作る
        Map<String, List<Map<String, String>>> codePricesMap = new HashMap<String, List<Map<String, String>>>();
        Path priceFilePath = Paths.get("/Users/yiio/workspace/resources/salesPrice.csv");
        try (BufferedReader br = Files.newBufferedReader(priceFilePath)) {
            String line = br.readLine();
            String[] header = line.split(",");
            while ((line = br.readLine()) != null) {
                // 価格改定日や単価などの情報を取り出し、マップに格納
                Map<String, String> itemMap = new HashMap<String, String>(); // key: 要素名, value: 内容
                String[] cols = line.split(",");
                for (int i = 0; i < cols.length; i++) {
                    String key = header[i];
                    String value = cols[i];
                    itemMap.put(key, value);
                }

                // 商品コードごとに価格改定情報リストを持つマップを更新
                String code = itemMap.get("商品コード");
                List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
                if (codePricesMap.containsKey(code)) {
                    valueList = codePricesMap.get(code);
                }
                valueList.add(itemMap);
                codePricesMap.put(code, valueList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 売り上げデータから、販売日と売り上げ額の対応表を作る
        Map<String, Integer> dateSalesMap = new HashMap<String, Integer>();
        Path salesFilePath = Paths.get("/Users/yiio/workspace/resources/salesList.csv");
        try (BufferedReader br = Files.newBufferedReader(salesFilePath)) {

            // ヘッダ行の処理
            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfDate = headerList.indexOf("販売日");
            int indexOfCode = headerList.indexOf("商品コード");
            int indexOfCount = headerList.indexOf("数量");

            // 2行目以降の処理
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");

                // 商品コードから、商品マスタのデータを取得
                String dateStr = cols[indexOfDate];
                String code = cols[indexOfCode];
                Map<String, String> item = codeItemMap.get(code);
                // 単価が改定されていないかチェック
                int price = Integer.parseInt(item.get("単価"));
                List<Map<String, String>> priceList = codePricesMap.get(code); // この商品コードの価格改定情報リスト
                if (priceList.size() > 0) {
                    for (Map<String, String> map : priceList) {
                        String changedDateStr = map.get("価格改定日");
                        if (dateStr.compareTo(changedDateStr) >= 0) { // この日よりも改定日が前の場合、価格改定後
                            price = Integer.parseInt(map.get("単価")); // 単価を更新する
                        }
                    }
                }

                // この行の売上額を算出
                int count = Integer.parseInt(cols[indexOfCount]);
                int sales = price * count;

                // 販売日と売り上げ額の対応表を更新
                if (dateSalesMap.containsKey(dateStr)) {
                    int sum = dateSalesMap.get(dateStr) + sales;
                    dateSalesMap.put(dateStr, sum);
                } else {
                    dateSalesMap.put(dateStr, sales);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 結果を出力
        Path outFilePath = Paths.get("/Users/yiio/Downloads/answer.csv"); // 書き込み対象ファイルの場所を指定
        try {
            Files.deleteIfExists(outFilePath); // 既に存在してたら削除
            Files.createFile(outFilePath); // ファイル作成
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(outFilePath)) {
            // ヘッダ行を作成
            String header = ("\"販売日\",\"売上総額\"");
            bw.write(header);
            bw.newLine(); // 改行

            // 日付判定用のCalendarを用意する
            Calendar cal = Calendar.getInstance();
            cal.set(2017, Calendar.JANUARY, 1); // 2017/01/01をセット
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            // 2行目以降の書き込み
            // 2017/01/01からスタートして、1日分ずつ書き足していく
            while (cal.get(Calendar.MONTH) < Calendar.MARCH) { // calの月が3月より小さい間、ループする
                String thisDateStr = sdf.format(cal.getTime());
                int sales = 0; // この日の売上を入れるための変数
                if (dateSalesMap.containsKey(thisDateStr)) {
                    sales = dateSalesMap.get(thisDateStr); // この日の売上があれば呼び出す
                }
                String salesStr = String.format("%,d", sales);

                // CSVに書き出す
                bw.write("\"" + thisDateStr + "\",\"" + salesStr + "円\"");
                bw.newLine();

                // calに1日足す
                cal.add(Calendar.DATE, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
