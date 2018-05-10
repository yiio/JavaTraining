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

public class Question4 {

    public static void main(String[] args) {
        Path filePath = Paths.get("/Users/yiio/workspace/resources/testResult.csv");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            // まずはヘッダ行の処理
            String line = br.readLine();
            String[] header = line.split(","); // ヘッダー行を配列として格納する

            // ヘッダ行以外の処理 科目ごとに合計点を出す
            Map<String, Integer> subjectSumMap = new HashMap<String, Integer>(); // 科目名と合計点の対応表
            int personCnt = 0; // 人数を保持しておく変数
            while ((line = br.readLine()) != null) {
                personCnt++;
                String[] cols = line.split(","); // 1行をカンマ区切りで配列に分割する
                // 科目ごとに合計点を足していく
                for (int i = 1; i < cols.length; i++) {
                    String subject = header[i]; // この科目名
                    int point = Integer.valueOf(cols[i]); // この人のこの科目の点数
                    if (subjectSumMap.containsKey(subject)) {
                        int sum = subjectSumMap.get(subject) + point;
                        subjectSumMap.put(subject, sum);
                    } else {
                        subjectSumMap.put(subject, point);
                    }
                }
            }

            // 科目ごとに平均点を出す
            for (Map.Entry<String, Integer> entry : subjectSumMap.entrySet()) {
                double sumDb = (double) entry.getValue(); // int型は整数しか持てない（整数に変換されてしまう）ので、実数を持てるdouble型を使って計算する
                double ave = sumDb / personCnt;
                BigDecimal aveBd = new BigDecimal(String.valueOf(ave)); // 小数点以下の値に誤差出さずに正確に計算するために、BigDecima型を使う
                BigDecimal roundedAve = aveBd.setScale(2, RoundingMode.HALF_UP); // 小数点第三位で四捨五入 setScale(2, BigDecimal.ROUND_HALF_UP)は非推奨
                System.out.println(entry.getKey() + "の平均点は、" + roundedAve + "点です"); // 実はString.format("%.2f", ave)でも同じことができる
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
