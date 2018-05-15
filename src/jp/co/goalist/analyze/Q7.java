package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Q7 {
    public static void main(String[] args) {

        Path itemFilePath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");// 商品リストのcsvファイルを取得
        Path salesFilePath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");// 売上リストのcsvファイルを取得

        Map<String, String> codeItemMap = new HashMap<String, String>();// (商品番号、商品名)のマップ
        Map<String, Integer> codePriceMap = new HashMap<String, Integer>();// (商品番号、売上単価)のマップ
        Map<String, Integer> codeAmountMap = new HashMap<String, Integer>();// (商品番号、売上量)のマップ
        int cnt = 0;// 何行目？

        // （商品番号、商品名）,(商品番号、売り上げ単価)（商品番号、売上量）のマップをつくる
        try (BufferedReader br = Files.newBufferedReader(itemFilePath)) {// 商品リストのファイル読み取り
            String itemList;
            while ((itemList = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// 行数を数える
                String[] item = itemList.split(",");// 取り出したカンマ含みの行をを配列に変換
                if (cnt == 1) { // １行目なら（ヘッダーなら）
                    continue;// スキップ
                }
                String code = item[0];// 商品コードを呼ぶ
                String itemName = item[1];// 商品名を呼ぶ
                int price = Integer.parseInt(item[2]);// 売り上げ単価を呼ぶ
                codeItemMap.put(code, itemName);// 格納
                codePriceMap.put(code, price);// 格納
                codeAmountMap.put(code, 0);// 初期化して格納

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // （商品番号、商品名）, (商品番号、売り上げ単価)、（商品番号、売上量）マップ作製終わり

        // （商品番号、売上量）のマップの売上量を足していく
        cnt =0;//カウントを0に戻す
        try (BufferedReader br = Files.newBufferedReader(salesFilePath)) {// 商品リストのファイル読み取り
            String salesList;
            while ((salesList = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// 行数を数える
                String[] sales = salesList.split(",");// 取り出したカンマ含みの行をを配列に変換
                if (cnt == 1) { // １行目なら（ヘッダーなら）
                    continue;// スキップ
                }
                String code = sales[1];// 商品コードを呼び出す
                int number = Integer.parseInt(sales[2]);// この会社の売上量を呼び出す
                int amount = codeAmountMap.get(code);// 今まで足し合わせた売上量を呼び出す
                amount = number + amount;// 足し合わせる
                codeAmountMap.put(code, amount);// 再格納
            }
        }
        // （商品番号、売上量）マップ売上量足し合わせおわり

        catch (IOException e) {
            e.printStackTrace();
        }

        // 結果を出力
        for (Map.Entry<String, String> entry : codeItemMap.entrySet()) {
            String code = entry.getKey();
            String item = entry.getValue();
            int price = codePriceMap.get(code);
            int amount = codeAmountMap.get(code);
            int sales = price * amount;
            System.out.println("「" + item + "」の売上高合計は、" + String.format("%,d", sales) + "円 です。");
        }
    }
}
