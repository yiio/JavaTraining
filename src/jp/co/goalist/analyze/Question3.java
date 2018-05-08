package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question3 {

    public static void main(String[] args) {

        Map<Integer, String> mMap = new HashMap<>();
        Map<Integer, String> pMap = new HashMap<>();
        Map<Integer, String> cMap = new HashMap<>();
        Map<Integer, String> eMap = new HashMap<>();

        Path filePath = Paths.get("c:/TechTraining/resources/testResult.csv");// 読み込み対象ファイルの場所を指定

        //csvの内容をを二次元配列に格納
        try {BufferedReader br = Files.newBufferedReader(filePath);

            String[][] data = new String[6][5];
            String line = br.readLine();
            for (int row = 0; line != null; row++) {
                data[row] = line.split(",");
                line = br.readLine();

            }

            //数学の最高得点者と得点を決定
            int mMax = Integer.parseInt(data[1][1]);
            for (int i = 1; i < data.length; i++) {
                int point1 = Integer.parseInt(data[i][1]);
                if (mMax < point1) {
                    mMax = point1;
                    mMap.put(point1, data[i][0]);
                    ;
                }
            }
            System.out.println("数学の最高得点者は" + mMap.get(mMax) + "さん、" + mMax + "点です。");

            //物理の最高得点者と得点を決定
            int pMax = Integer.parseInt(data[1][2]);
            for (int j = 1; j < data.length; j++) {
                int point2 = Integer.parseInt(data[j][2]);
                if (pMax < point2) {
                    pMax = point2;
                    pMap.put(point2, data[j][0]);
                }
            }
            System.out.println("物理の最高得点者は" + pMap.get(pMax) + "さん、" + pMax + "点です。");

            //化学の最高得点者と得点を決定
            int cMax = Integer.parseInt(data[1][3]);
            for (int k = 1; k < data.length; k++) {
                int point3 = Integer.parseInt(data[k][3]);
                if (cMax < point3) {
                    cMax = point3;
                    cMap.put(point3, data[k][0]);
                }
            }
            System.out.println("化学の最高得点者は" + cMap.get(cMax) + "さん、" + cMax + "点です。");

            //英語の最高得点者と得点を決定
            int eMax = Integer.parseInt(data[1][4]);
            for (int m = 1; m < data.length; m++) {
                int point4 = Integer.parseInt(data[m][4]);
                if (eMax < point4) {
                    eMax = point4;
                    eMap.put(point4, data[m][0]);
                }
            }
            System.out.println("英語の最高得点者は" + eMap.get(eMax) + "さん、" + eMax + "点です。");

            br.close();

        } catch (IOException e) {
            System.out.println(e);

        }

    }
}
