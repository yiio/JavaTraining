package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Q4 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");

        String[] header = {};// 教科を格納
        Map<String, Integer> subjectSumMap = new HashMap<String, Integer>();// （教科、合計点）を格納
        try (BufferedReader br = Files.newBufferedReader(filePath)) {// ファイル読み取り

            String line;// １行目を格納するための変数
            int cnt = 0;// 何行目
            while ((line = br.readLine()) != null) {// 読み込んだものが空白でない限り以下を繰り返す
                cnt++;// cntを１行読み込むごとに一つずつ足す
                String[] cols = line.split(",");// １行をカンマで分割した配列をcolsと名付ける
                if (cnt == 1) {// ヘッダー行なら
                    header = cols;// ヘッダー行に格納
                    for (int i = 1; i < cols.length; i++) {
                        subjectSumMap.put(cols[i], 0);// 科目名と合計点の対応表を定義して格納
                    }
                    continue;// whileループの一番最初に戻る
                }
                // ヘッダ行以外の処理

                for (int i = 1; i < cols.length; i++) {// 科目ごとに点数を足し合わせる
                    String subject = header[i];// 教科名
                    int point = Integer.valueOf(cols[i]);// この人の点数
                    int sum = subjectSumMap.get(subject);// 教科名から今までの合計点を呼び出し
                    sum = sum + point;// 合計点を変更
                    subjectSumMap.put(subject, sum);// 再格納

                }

            }
            for (Map.Entry<String, Integer> entry : subjectSumMap.entrySet()) {//キーとバリューのセットを出力
                String subject = entry.getKey();//キー出力
                double sum = (double)entry.getValue();//double型で合計点出力
                double avePoint = sum / (cnt - 1);//平均点出力
                BigDecimal sum1 = new BigDecimal(String.valueOf(avePoint));//小数点以下の桁数を指定
                BigDecimal sum3 = sum1.setScale(2, RoundingMode.HALF_UP);//小数点以下を四捨五入
                System.out.println(subject + "の平均点は" + sum3 + "点です。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } // 例外処理
    }

}
