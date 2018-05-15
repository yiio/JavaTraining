package jp.co.goalist.objectsiko;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question1 {
    public static void main(String[] args) {
        Path filePath2 = Paths.get("C:\\TechTraining\\resources\\salesList.csv");
        Path filePath3 = Paths.get("C:\\TechTraining\\resources\\salesPrice.csv");

        Map<String, Integer> priceMap = new HashMap<>();
        Map<String, Integer> dateSumMap = new TreeMap<String, Integer>();

        // コードと単価の対応表を作る
        SalesItem2 sItem = new SalesItem2();
        sItem.filePath = Paths.get("C:\\TechTraining\\resources\\salesItem2.csv");
        try {
            sItem.makeList();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        // salesListのデータをListに格納
        List<String[]> salesList = new ArrayList<String[]>();
        try (BufferedReader br = Files.newBufferedReader(filePath2)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                salesList.add(cols);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // salesPriceのデータをListに格納
        List<String[]> salesPrice = new ArrayList<String[]>();
        try (BufferedReader br = Files.newBufferedReader(filePath3)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                salesPrice.add(cols);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1日ずつカレンダーを進めて日ごとの売り上げをまとめる
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat calendar = new SimpleDateFormat("yyyy/MM/dd");
        cal.set(2017, 0, 1);
        for (int i = 0; i < 60; i++) {
            String calDate = calendar.format(cal.getTime());

            // 商品の金額を書き換える
            for (String[] cols : salesPrice) {
                int price = Integer.parseInt(cols[2]);
                if (cols[1].equals(calDate)) {
                    priceMap.put(cols[0], price);
                }
            }

            // 日ごとの売り上げの計算
            for (String[] cols : salesList) {
                int sum1 = priceMap.get(cols[1]) * Integer.parseInt(cols[2]);
                if (calDate.equals(cols[0])) {
                    if (dateSumMap.containsKey(cols[0])) {
                        int sum2 = dateSumMap.get(cols[0]);
                        sum2 += sum1;
                        dateSumMap.put(cols[0], sum2);
                    } else {
                        dateSumMap.put(cols[0], sum1);
                    }
                }
            }

            if (!dateSumMap.containsKey(calDate)) {
                dateSumMap.put(calDate, 0);
            }

            cal.add(Calendar.DATE, 1);
        }

        // csvファイルの出力
        Path filePath = Paths.get("C:\\TechTraining\\resources\\answer2.csv");
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            String dateSum;
            for (Map.Entry<String, Integer> entry : dateSumMap.entrySet()) {
                dateSum = "\"" + entry.getKey() + "\"" + "," + "\"" + String.format("%, d", entry.getValue()) + "円\"";
                System.out.println(dateSum);
                bw.write(dateSum);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class SalesItem2 {
    Path filePath;
    Map<String, Integer> priceMap = new HashMap<>();

    public void makeList() throws IOException {
        BufferedReader br = Files.newBufferedReader(filePath);
        String line;
        int cnt = 0;
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(",");
            cnt++;
            if (cnt == 1) {
                continue;
            }
            priceMap.put(cols[1], Integer.parseInt(cols[3]));
        }
    }

}
