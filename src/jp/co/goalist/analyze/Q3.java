package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Q3 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv"); // ファイル読み込み

        String[] header = {};// ヘッダーをつくる

        Map<String, String> subjectNameMap = new HashMap<String, String>();// (教科、最高得点者の名前)のマップ
        Map<String, Integer> subjectMaxMap = new HashMap<String, Integer>();// (教科、最高点数)のマップ

        try (BufferedReader br = Files.newBufferedReader(filePath)) {// ファイルを１行ずつ読み込む
            String test;// 読み込んだものをtestと定義
            int cnt = 0;// 何行目

            while ((test = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                cnt++;// cntは数える
                String[] testPoints = test.split(",");// 取り出したカンマ含みの行をを配列に変換

                if (cnt == 1) { // ヘッダー行なら
                    for (int i = 1; i < testPoints.length; i++) {// 以下を繰り返す
                        header = testPoints;// 教科をヘッダーに格納
                        subjectMaxMap.put(testPoints[i], 0);// （教科、最高点）を格納
                    }
                    continue;// スキップ
                }
                // ヘッダー行の処理終わり

                String name = testPoints[0];// 氏名を定義
                for (int j = 1; j < testPoints.length; j++) {
                    String subject = header[j];// 教科を定義
                    int point = Integer.parseInt(testPoints[j]);// この人の点数

                    if (subjectMaxMap.get(subject) < point) {// 比較して大きかったら
                        subjectMaxMap.put(subject, point);// （教科、最高得点）マップに再格納
                        subjectNameMap.put(subject, name);// (教科、最高得点者の名前)マップに再格納
                    }

                }
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        } // 例外処理
        for (Map.Entry<String, Integer> entry : subjectMaxMap.entrySet()) {

            String subject = entry.getKey();
            String name = subjectNameMap.get(subject);
            int maxPoint = entry.getValue();
            System.out.println(subject + "の最高得点者は、" + name + "さん、" + maxPoint + "点です。");
        }
    }
}
