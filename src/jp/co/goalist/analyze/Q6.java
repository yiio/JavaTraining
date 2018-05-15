package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Q6 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");// ファイルの取得

        Map<Integer, String> sumMap = new TreeMap();// (合計点、名前)のツリーマップをつくる
        int cnt = 0;// 何行目？
        try (BufferedReader br = Files.newBufferedReader(filePath)) {// ファイル読み取り
            String test;
            while ((test = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// 行数を数える
                String[] testPoints = test.split(",");// 取り出したカンマ含みの行をを配列に変換
                if (cnt == 1) { // １行目なら（ヘッダーなら）
                    continue;// スキップ
                }
                String name = testPoints[0];// 名前を定義
                int sum = 0;// 合計を定義
                for (int i = 1; i < testPoints.length; i++) {// 名前を飛ばして以下を繰り返す
                    sum = sum + Integer.parseInt(testPoints[i]);// 合計点を出す
                }
                sumMap.put(-sum, name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 結果を出力
        int rank = 0;
        for (Map.Entry<Integer, String> entry : sumMap.entrySet()) {
            rank++;
            String name = entry.getValue();
            int sum = -(entry.getKey());
            System.out.println(rank + "位：" + name + "さん  " + sum + "点");
        }

    }
}
