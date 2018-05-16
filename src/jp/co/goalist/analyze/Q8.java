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

public class Q8 {

    public static void main(String[] args) {

        Path itemFilePath = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        // 商品リストのcsvファイルを取得
        Path salesFilePath = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        // 売上リストのcsvファイルを取得

        Map<String, Integer> codePriceMap = new HashMap<String, Integer>();
        // (商品番号、売上単価)のマップ
        Map<String, Integer> dateSalesMap = new TreeMap<String, Integer>();
        // (販売日、売上量)のマップ
        int cnt = 0;// 何行目？

        // (商品番号、売り上げ単価)のマップをつくる
        try (BufferedReader br = Files.newBufferedReader(itemFilePath)) {
            // 商品リストのファイル読み取り
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
                codePriceMap.put(code, price);// 格納
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // (商品番号、売上単価)マップおわり

        // (日付、売上高)のマップを作製
        cnt = 0;// カウントを0に戻す
        try (BufferedReader br = Files.newBufferedReader(salesFilePath)) {
            // 商品リストのファイル読み取り
            String dateSales;
            int sum = 0;
            while ((dateSales = br.readLine()) != null) {
                // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// 行数を数える
                String[] sales = dateSales.split(",");// 取り出したカンマ含みの行をを配列に変換

                if (cnt == 1) { // １行目なら（ヘッダーなら）
                    continue;// スキップ
                }
                String date = sales[0];// 日付を呼ぶ
                String code = sales[1];// 商品コードを呼ぶ
                int price = codePriceMap.get(code);// 商品コードから商品単価を呼び出し
                int amount = Integer.parseInt(sales[2]);// この行の売上量を呼ぶ
                int earning = price * amount;// この行の売り上げを計算

                if (dateSalesMap.containsKey(date)) {// もしこの日付が前にも出てきたなら
                    sum = sum + earning;// 足し合わせて
                    dateSalesMap.put(date, sum);// 再格納

                } else {// はじめてなら
                    dateSalesMap.put(date, earning);// 再格納
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // （日付、売上高）マップ作製終わり

        // 新しくファイルを書き出す

        Path answerPath = Paths.get("C:\\\\TechTraining\\\\resources\\\\question8.csv"); // 書き込み対象ファイルの場所を指定

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

                // 時間が回る
                for (int j = 1; j <= finalDay; j++) {
                    cal.set(Calendar.DAY_OF_MONTH, j);// 日にｊをセット
                    String date = dateFormat.format(cal.getTime());// 日付を例のフォーマットで取得

                    if (dateSalesMap.containsKey(date)) {

                        int priceSum = dateSalesMap.get(date);

                        bw.write(date + "," + priceSum + "円");
                        bw.newLine();

                    } else {
                        bw.write(date + "," + "0円");
                        bw.newLine();
                    }
                }
            }

            bw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        // 書き出し終わり

        // 結果を出力

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
