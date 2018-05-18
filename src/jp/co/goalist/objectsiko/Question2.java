package jp.co.goalist.objectsiko;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class SalesItemGroup {
    String groupCode;
    String groupName;

    public SalesItemGroup(String code, String name) {
        groupCode = code;
        groupName = name;
    }
}


class SalesItem2 {
    String groupCode;
    String itemCode;
    int tanka;

    SalesItem2(String gCode, String iCode, String price) {
        groupCode = gCode;
        itemCode = iCode;
        tanka = Integer.parseInt(price);
    }
}


class Sales {
    String itemCode;
    int count;

    Sales(String code, String num) {
        itemCode = code;
        count = Integer.parseInt(num);
    }
}


public class Question2 {
    public static void main(String[] args) {
        Path salesItemGroup = Paths.get("C:/TechTraining/resources/SalesItemGroup.csv");
        Path salesItem2 = Paths.get("C:/TechTraining/resources/SalesItem2.csv");
        Path salesList2 = Paths.get("C:/TechTraining/resources/SalesList2.csv");
        Map<String,String> groupNameMap = new HashMap<>(); // グループコード,グループ名
        Map<String,Integer> groupSumMap = new TreeMap<>(); // グループコード,グループ合計
        Map<String,String> codeMap = new HashMap<>(); // グループコード,商品コード
        Map<String,Integer> tankaMap = new HashMap<>(); // 商品コード,単価

        // 商品グループマスタ
        try (BufferedReader br = Files.newBufferedReader(salesItemGroup)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String elem[] = line.split(",");
                SalesItemGroup group = new SalesItemGroup(elem[0],elem[1]);
                groupNameMap.put(group.groupCode, group.groupName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 商品マスタ
        try (BufferedReader br = Files.newBufferedReader(salesItem2)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String elem[] = line.split(",");
                SalesItem2 item = new SalesItem2(elem[0],elem[1],elem[3]);
                groupSumMap.put(item.groupCode, 0);
                codeMap.put(item.itemCode, item.groupCode);
                tankaMap.put(item.itemCode, item.tanka);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 販売実績
        try (BufferedReader br = Files.newBufferedReader(salesList2)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String elem[] = line.split(",");
                Sales propaty = new Sales(elem[1],elem[2]);
                String groupCode = codeMap.get(propaty.itemCode);
                int price = propaty.count * tankaMap.get(propaty.itemCode);
                groupSumMap.put(groupCode, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String groupCode : groupSumMap.keySet()) {
            int sumPrice = groupSumMap.get(groupCode);
            String groupName = groupNameMap.get(groupCode) ;
            System.out.println( "「" + groupName + "」の販売合計金額は、" + sumPrice + "円です。");
        }

    }
}
