package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Q5 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        String[] header = {};// ヘッダーをつくる
        Map<String, String> subjectMaxMap = new HashMap<String, String>();// (名前、最高点の教科)
        Map<String, String> subjectMinMap = new HashMap<String, String>();// (名前、最低点の教科)
        Map<String, Integer> pointMaxMap = new HashMap<String, Integer>();// （名前と最高点数）
        Map<String, Integer> pointMinMap = new HashMap<String, Integer>();// (名前と最低点数)
        Map<String, Integer> sumMap = new HashMap<String, Integer>();// （名前と合計点）
        int cnt1 = 0;

        try (BufferedReader br = Files.newBufferedReader(filePath)) {// ファイル読み取り
            String line;// １行目を格納するための変数
            int cnt = 0;// 何行目
            while ((line = br.readLine()) != null) {// 読み込んだものが空白でない限り以下を繰り返す
                cnt++;// cntを１行読み込むごとに一つずつ足す
                String[] cols = line.split(",");// １行をカンマで分割
                if (cnt == 1) {// ヘッダー行なら
                    header = cols;// ヘッダー行に格納
                    cnt1 = (cols.length - 1);
                    continue;// whileループの一番最初に戻る
                }
                String name = cols[0];// 名前は0番目
                int sum = 0;// 合計点を定義
                int max = 0;// 最高点を定義
                int min = 100;// 最低点を定義
                for (int j = 1; j < cols.length; j++) {// 科目ごとに点数を比較する
                    sum = sum + Integer.parseInt(cols[j]);// 合計点を出す

                    String subject = header[j];// 教科を定義
                    int point = Integer.parseInt(cols[j]);// この人の点数

                    if (max < point) { // 現在の最大値よりも大きい値が出たら
                        max = point; // 変数maxに値を入れ替える
                        pointMaxMap.put(name, max);// (名前と最高点数を再格納)
                        subjectMaxMap.put(name, subject);// (名前と最高点の教科を再格納)
                    }
                    if (min > point) { // 現在の最小値よりも小さい値が出たら
                        min = point; // 変数minに値を入れ替える
                        pointMinMap.put(name, min);// 名前と最低点数を再格納
                        subjectMinMap.put(name, subject);// (名前と最低点の教科を再格納)
                    }
                    sumMap.put(name, sum);// 名前と合計点を再格納

                } // 結果を出力

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : subjectMaxMap.entrySet()) {
            String name1 = entry.getKey();
            int sum = sumMap.get(name1);
            int ave = sum / cnt1;
            String maxSub = entry.getValue();
            int max = pointMaxMap.get(name1);
            String minSub = subjectMinMap.get(name1);
            int min = pointMinMap.get(name1);

            System.out.println(name1 + "の全科目合計点は" + sum + "点、全科目平均点は" + ave + "点、最高点は" + maxSub + "で" + max + "点、最低点は"
                    + minSub + "で、" + min + "点です。");
            // }
        }
    }
}
