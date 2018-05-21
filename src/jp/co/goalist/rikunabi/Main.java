package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        List<String[]> rikunaviList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                rikunaviList.add(cols);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        kadai1(rikunaviList);
        kadai2(rikunaviList);
        kadai3(rikunaviList);
        kadai4(rikunaviList);
        kadai5(rikunaviList);
        kadai6(rikunaviList);

    }

    static void kadai1(List<String[]> list) {
        System.out.println("----------課題1----------\n");
        System.out.println("「リクナビNEXT」の全掲載件数は" + (list.size() - 1) + "件です。");
    }

    static void kadai2(List<String[]> list) {
        System.out.println("\n----------課題2----------\n");
        int keiyaku = 0;
        for (String[] kyuujinn : list) {
            if (kyuujinn[8].contains("契約社員")) {
                keiyaku++;
            }
        }
        System.out.println("「リクナビNEXT」の雇用区分が契約社員の掲載件数は" + keiyaku + "件です。");
    }

    static void kadai3(List<String[]> list) {
        System.out.println("\n----------課題3----------\n");
        String[] nihonn = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都",
                "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府",
                "兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県",
                "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県", "その他・不明" };

        Map<String, Integer> areaMap = new HashMap<>();

        // 都道府県のMap作成
        for (String tiiki : nihonn) {
            areaMap.put(tiiki, 0);
        }
        int cnt = 0;

        // 掲載件数の集計
        for (String[] kyuujinn : list) {
            cnt++;
            if (cnt == 1) {
                continue;
            }
            String tiiki = kyuujinn[6].substring(3);
            int number = areaMap.get(tiiki);
            number++;
            areaMap.put(tiiki, number);
        }

        // 課題の書き出し
        for (Map.Entry<String, Integer> entry : areaMap.entrySet()) {
            System.out.println(entry.getKey() + "の掲載件数は、" + entry.getValue() + "件です。");
        }
    }

    static void kadai4(List<String[]> list) {
        System.out.println("\n----------課題4----------\n");
        Map<String, Integer> numMap = new HashMap<>();// 職種ごとの掲載件数
        Map<String, Integer> moneyMap = new HashMap<>();// 職種ごとの月給下限金額を足していく
        Map<String, Integer> heikinnMap = new HashMap<>();// 職種ごとの平均月給下限金額
        int cnt = 0;

        // 職種ごとの掲載件数、合計月給下限金額を集計する
        for (String[] kyuujinn : list) {
            cnt++;
            if (cnt == 1) {
                continue;
            }
            String shokushu = kyuujinn[4];
            String kagennStr = kyuujinn[11];

            if (kagennStr.equals("")) { // 月給下限金額のデータがないときは除外
                continue;
            }

            int kagenn = Integer.parseInt(kagennStr);

            if (numMap.containsKey(shokushu)) {
                int number = numMap.get(shokushu);
                int money = moneyMap.get(shokushu);
                number++;
                money += kagenn;
                numMap.put(shokushu, number);
                moneyMap.put(shokushu, money);
            } else {
                numMap.put(shokushu, 1);
                moneyMap.put(shokushu, kagenn);
            }
        }

        // 平均月給下限金額の計算
        for (Map.Entry<String, Integer> entry : moneyMap.entrySet()) {
            String shokushu = entry.getKey();
            int money = entry.getValue();
            int number = numMap.get(shokushu);
            int heikinn = money / number;
            heikinnMap.put(shokushu, heikinn);
        }

        // 課題の書き出し
        for (Map.Entry<String, Integer> entry : heikinnMap.entrySet()) {
            System.out.println(entry.getKey() + "の平均月給下限金額は、" + String.format("%, d", entry.getValue()) + "円です。");
        }
    }

    static void kadai5(List<String[]> list) {
        System.out.println("\n----------課題5----------\n");
        Map<String, Integer> numMap = new TreeMap<>();
        Map<Integer, Integer> rankMap = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer m, Integer n) {
                return ((Integer) m).compareTo(n) * -1;
            }
        });

        // 企業ごとの掲載件数の集計
        for (String[] kyuujinn : list) {
            String kigyou = kyuujinn[3];
            if (numMap.containsKey(kigyou)) {
                int number = numMap.get(kigyou);
                number++;
                numMap.put(kigyou, number);
            } else {
                numMap.put(kigyou, 1);
            }
        }

        // 掲載件数の順位づけ
        for (Map.Entry<String, Integer> entry : numMap.entrySet()) {
            int number = entry.getValue();
            rankMap.put(number, rankMap.getOrDefault(number, 0) + 1);
        }

        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            rank += entry.setValue(rank);
        }
        System.out.println(rankMap);

        // 比較関数Comparatorを使用してMap.Entryの値を比較する（降順）
        List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(numMap.entrySet());
        Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
            // compareを使用して値を比較する
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                // 降順
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        // 書き出し
        for (Entry<String, Integer> entry : list_entries) {
            if (rankMap.get(entry.getValue()) >= 10) { // 10位より下は出力しない
                break;
            }
            System.out.println("掲載件数第" + rankMap.get(entry.getValue()) + "位は、" + entry.getKey() + "で、"
                    + entry.getValue() + "件です。");

        }
    }

    static void kadai6(List<String[]> list) {
        System.out.println("\n----------課題6----------\n");
        Map<String, Double> numMap = new HashMap<>();
        Map<String, Double> mikeikenMap = new HashMap<>();
        Map<String, BigDecimal> ratioMap = new HashMap<>();

        // 職種分類ごとの全求人数と未経験求人数の集計
        int cnt = 0;
        for (String[] kyuujinn : list) {
            cnt++;
            if (cnt == 1) {
                continue;
            }
            String shokushu = kyuujinn[4];
            String kisaiShokushu = kyuujinn[5];
            numMap.put(shokushu, numMap.getOrDefault(shokushu, 0.0) + 1);
            if (kisaiShokushu.contains("未経験")) {
                mikeikenMap.put(shokushu, mikeikenMap.getOrDefault(shokushu, 0.0) + 1);
            }
        }

        // 職種分類ごとの未経験求人数の割合
        for (Entry<String, Double> entry : numMap.entrySet()) {
            String shokushu = entry.getKey();
            if (mikeikenMap.containsKey(shokushu)) {
                Double ratio = (double) ((mikeikenMap.get(shokushu) / entry.getValue()) * 100);
                BigDecimal bdAverage = new BigDecimal(String.valueOf(ratio));
                BigDecimal ratio2 = bdAverage.setScale(2, RoundingMode.HALF_UP);
                ratioMap.put(shokushu, ratio2);
            }
        }

        // 書き出し
        System.out.println("職種分類ごとの未経験者応募の割合\n");
        for (Entry<String, BigDecimal> entry : ratioMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

}
