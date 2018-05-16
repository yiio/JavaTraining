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
import java.util.Map;
import java.util.TreeMap;

public class Q9 {

    public static void main(String[] args) {

        Path itemFilePath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        // 商品リストのcsvファイルを取得
        Path salesFilePath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        // 売上リストのcsvファイルを取得
        Path priceFilePath = Paths.get("C:\\TechTraining\\resources\\salesPrice.csv");
        // 価格改定後の価格リストのcsvファイルを取得

        Map<String, Integer> priceMap = new HashMap<String, Integer>();
        // (code、price)のマップ
        Map<String, Integer> salesMap = new TreeMap<String, Integer>();
        // (販売日、売上量)のマップ

        int cnt = 0;// 何行目？

        // (日付、売上単価)のマップをつくる
        try (BufferedReader br = Files.newBufferedReader(itemFilePath)) {
            String itemList;
            while ((itemList = br.readLine()) != null) {
                // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// 行数を数える
                String[] item = itemList.split(",");// 取り出したカンマ含みの行をを配列に変換
                if (cnt == 1) { // １行目なら（ヘッダーなら）
                    continue;// スキップ
                }
                String code = item[0];// 商品コードを呼ぶ
                int price = Integer.parseInt(item[2]);// 売り上げ単価を呼ぶ
                priceMap.put(code, price);// 格納
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // (商品番号、売上単価)マップおわりストのファイル読み取り

        // 新しくファイルを書き出す

        Path answerPath = Paths.get("C:\\\\TechTraining\\\\resources\\\\question9.csv"); // 書き込み対象ファイルの場所を指定

        try (BufferedWriter bw = Files.newBufferedWriter(answerPath)) {// １行ごとに「書き込む」

            // １行目をぶちこむ
            bw.write("販売日" + "," + "売上総額");
            bw.newLine();

            /* カレンダーの設定をぶちこむ */
            Calendar cal = Calendar.getInstance();// カレンダーを取得

            for (int i = 0; i < 2; i++) { // ２月まで

                cal.set(Calendar.YEAR, 2017);// 年に2017をセット
                cal.set(Calendar.MONTH, i);// 月にiをセット
                int finalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// i月の最終日を取得
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");// フォーマットを決める

                // 1日ごとに見て行って、価格改定→（日付、売り上げ格納）
                for (int j = 1; j <= finalDay; j++) {
                    cal.set(Calendar.DAY_OF_MONTH, j);// 日にｊをセット
                    String date = dateFormat.format(cal.getTime());// 日付を例のフォーマットで取得

                    salesMap.put(date, 0);//セールスマップを初期化
                    
                    //日付を見て、今日の価格に改定
                    cnt=0;
                    try (BufferedReader br = Files.newBufferedReader(priceFilePath)) {
                        String priceList;
                        while ((priceList = br.readLine()) != null) {
                            // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                            cnt++;// 行数を数える
                            String[] prices = priceList.split(",");// 取り出したカンマ含みの行をを配列に変換
                            if (cnt == 1) { // １行目なら（ヘッダーなら）
                                continue;// スキップ
                            }
                            String code = prices[0];// 商品コードを呼ぶ
                            int price = Integer.parseInt(prices[2]);// 売り上げ単価を呼ぶ
                            if (date.equals(prices[1])) {
                                priceMap.put(code, price);// 格納
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //売上ファイル読み込み、日付が一致していたら売り上げを再格納
                    cnt = 0;
                    try (BufferedReader br = Files.newBufferedReader(salesFilePath)) {
                        String salesList;
                        while ((salesList = br.readLine()) != null) {
                            // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                            cnt++;// 行数を数える
                            String[]sales = salesList.split(",");// 取り出したカンマ含みの行をを配列に変換
                            if (cnt == 1) { // １行目なら（ヘッダーなら）
                                continue;// スキップ
                            }
                            String code = sales[1];// 商品コードを呼ぶ
                            int amount = Integer.parseInt(sales[2]);// 売り上げ量を呼ぶ
                            if (date.equals(sales[0])) {//もし日付がいっしょなら
                                int sum = priceMap.get(code)*amount;
                                
                                salesMap.put(date, sum);// 格納
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //(日付、売上）マップの再格納おわり
                    

                        int priceSum = salesMap.get(date);

                        bw.write(date + "," + String.format("%,d", priceSum) + "円");
                        bw.newLine();

                }
            }

            bw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        // 書き出し終わり

        // （おまけ）結果を出力

        try (BufferedReader br = Files.newBufferedReader(answerPath)) {
            // 出力したいファイル読み取り
            String answer;
            while ((answer = br.readLine()) != null) {

                System.out.println(answer);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
