package jp.co.goalist.analyze;

import java.io.BufferedReader;//BufferedReader classとは
//テキストを１行ずつ読み込むお！！
import java.io.IOException;//例外が出てきたときに対応！
import java.nio.file.Files;//ファイルのパスを取得するよ！
import java.nio.file.Path;//Pathオブジェクトを取得するよ！
import java.nio.file.Paths;//Pathオブジェクトを取得するよ！
import java.util.HashMap;//ハッシュマップ使うよ！
import java.util.Map;//マップ使うよ！！
public class analyze_Q1shakyo {//手で写経したほう
    public static void main(String[] args) {

        Map<String, Integer> prefCountMap = new HashMap<String, Integer>();
        /*
         * ハッシュマップのつくりかた prefCountMapという名前の<String,Integer>を中に格納するマップをつくるよ。
         * それはハッシュマップ型だよ。
         */
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt");
        /* filePathという名前のPathを()内の場所から取得するよ */
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            /* filePathという名前のファイルを１行１行読み取っていくよ */
            String pref;// 1行１それぞれをprefと名付けたよ
            while ((pref = br.readLine()) != null) {
                // pref=ファイルを1行ずつ読み込み、結果がnull以外ならwhile内の処理をおこなう
                if (prefCountMap.containsKey(pref)) {// もしもprefがprefContMapのキーに含まれていたら
                    int sum = prefCountMap.get(pref) + 1;// sumを定義してvalueに足す
                    prefCountMap.put(pref, sum);// 再格納
                } else {
                    prefCountMap.put(pref, 1);// 新しく作る
                }
            }
        } catch (IOException e) {
            e.printStackTrace();// 例外があったら処理します
        }

        for (Map.Entry<String, Integer> entry : prefCountMap.entrySet()) {
            // entrySet()=マップに含まれるキーと値のSetの数まで繰り返す
            System.out.println(entry.getKey() + "は、" + entry.getValue() + "件です。");
            // キーはバリュー件です。
        }
    }

}
