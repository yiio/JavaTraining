package jp.co.goalist.objectsiko;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question2 {
    public static void main(String[] args) {
        Path filePath1 = Paths.get("C:\\TechTraining\\resources\\salesItemGroup.csv");
        Path filePath2 = Paths.get("C:\\TechTraining\\resources\\salesItem2.csv");
        Path filePath3 = Paths.get("C:\\TechTraining\\resources\\salesList.csv");

        Map<String, SalesItemGroup> itemGroupMap = new HashMap<>();
        Map<String, SalesItem2> itemMap = new HashMap<>();
        Map<String, Integer> salesMap = new HashMap<>();
        Map<String, Integer> sumMap = new HashMap<>();

        // 商品グループマスタ
        try (BufferedReader br = Files.newBufferedReader(filePath1)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                SalesItemGroup sig = new SalesItemGroup();
                sig.property = line.split(",");
                sig.write();
                itemGroupMap.put(sig.groupCode, sig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 商品マスタ
        try (BufferedReader br = Files.newBufferedReader(filePath2)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                SalesItem2 si = new SalesItem2();
                si.property = line.split(",");
                si.write();
                itemMap.put(si.code, si);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 販売実績
        try (BufferedReader br = Files.newBufferedReader(filePath3)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    continue;
                }
                Sales s = new Sales();
                s.property = line.split(",");
                s.write();
                if (salesMap.containsKey(s.code)) {
                    int number = salesMap.get(s.code) + s.number;
                    salesMap.put(s.code, number);
                } else {
                    salesMap.put(s.code, s.number);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 売上の計算
        int sum1;
        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            String code = entry.getKey();
            String groupCode = itemMap.get(code).groupCode;
            String groupName = itemGroupMap.get(groupCode).groupName;
            int money = entry.getValue() * itemMap.get(code).price;
            if (sumMap.containsKey(groupName)) {
                sum1 = sumMap.get(groupName) + money;
                sumMap.put(groupName, sum1);
            } else {
                sumMap.put(groupName, money);
            }
        }

        for (Map.Entry<String, Integer> entry : sumMap.entrySet()) {
            String groupName = entry.getKey();
            int sum = entry.getValue();
            System.out.println("「" + groupName + "」" + "の販売合計金額は、" + sum + "円です。");
        }
    }

}

class SalesItemGroup {
    String[] property;
    String groupCode;
    String groupName;

    void write() {
        this.groupCode = property[0];
        this.groupName = property[1];
    }

}

class SalesItem2 {
    String[] property;
    String groupCode;
    String code;
    String name;
    int price;

    void write() {
        this.groupCode = property[0];
        this.code = property[1];
        this.name = property[2];
        this.price = Integer.parseInt(property[3]);
    }

}

class Sales {
    String[] property;
    String date;
    String code;
    int number;
    String cliant;

    void write() {
        this.date = property[0];
        this.code = property[1];
        this.number = Integer.parseInt(property[2]);
        this.cliant = property[3];
    }

}