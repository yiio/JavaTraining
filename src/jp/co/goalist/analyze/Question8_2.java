package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question8_2 {
    public static void main(String[] args) {
        Path filePath1 = Paths.get("C:\\TechTraining\\resources\\salesItem.csv");
        Path filePath2 = Paths.get("C:\\TechTraining\\resources\\salesList.csv");

        HashMap<String, Integer> codePriceMap = new HashMap<String, Integer>();

        int cnt = 0;
        int cnt2 = 0;

        // コードと単価の対応表を作る
        try (BufferedReader br = Files.newBufferedReader(filePath1)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                codePriceMap.put(cols[0], Integer.parseInt(cols[2]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Integer> dateSumMap = new TreeMap<String, Integer>();

        // 日ごとの売上の対応表を作る
        try (BufferedReader br = Files.newBufferedReader(filePath2)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                cnt2++;
                if (cnt2 == 1) {
                    continue;
                }

                int sum1 = codePriceMap.get(cols[1]) * Integer.parseInt(cols[2]);
                if (dateSumMap.containsKey(cols[0])) {
                    int sum2 = dateSumMap.get(cols[0]);
                    sum2 += sum1;
                    dateSumMap.put(cols[0], sum2);
                } else {
                    dateSumMap.put(cols[0], sum1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // データに含まれる日付を月ごとにMap<Integer, List<>>に格納
        Map<String, List<Integer>> monthDay = new TreeMap<String, List<Integer>>();

        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();

        for (Map.Entry<String, Integer> entry1 : dateSumMap.entrySet()) {
            String date = entry1.getKey();
            int day = Integer.parseInt(date.substring(date.length() - 2));
            String month = date.substring(0, 8);
            if (month.contains("/01/")) {
                list1.add(day);
                monthDay.put(month, list1);
            } else if (month.contains("/02/")) {
                list2.add(day);
                monthDay.put(month, list2);
            }
        }

        // データにない日付を追加する
        for (Map.Entry<String, List<Integer>> entry2 : monthDay.entrySet()) {
            String date = entry2.getKey();
            int month = Integer.parseInt(date.substring(5, 7));
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, month - 1);
            int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            Format f = new DecimalFormat("00");
            for (int i = 1; i <= maxDays; i++) {
                if (!entry2.getValue().contains(i)) {
                    dateSumMap.put(date + f.format(i), 0);
                }
            }
        }

        // csvファイルの出力
        Path filePath = Paths.get("C:\\TechTraining\\resources\\answer.csv");
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            String dateSum;
            for (Map.Entry<String, Integer> entry : dateSumMap.entrySet()) {
                dateSum = entry.getKey() + "," + String.valueOf(entry.getValue()) + "円";
                System.out.println(dateSum);
                bw.write(dateSum);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
