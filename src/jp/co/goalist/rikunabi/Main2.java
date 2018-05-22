package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Main2 {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        List<String[]> rikunaviList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
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
        System.out.println("都道府県ごとの求人掲載件数\n");
        for (Map.Entry<String, Integer> entry : areaMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + "件");
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

            numMap.put(shokushu, numMap.getOrDefault(shokushu, 0) + 1);// 掲載件数
            moneyMap.put(shokushu, moneyMap.getOrDefault(shokushu, 0) + kagenn);// 合計月給下限金額
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
        System.out.println("職種分類ごとの平均月給下限金額\n");
        for (Map.Entry<String, Integer> entry : heikinnMap.entrySet()) {
            System.out.println(entry.getKey() + " :" + String.format("%, d", entry.getValue()) + "円");
        }
    }

    static void kadai5(List<String[]> list) {
        System.out.println("\n----------課題5----------\n");
        Map<String, Integer> numMap = new HashMap<>();
        Map<Integer, Integer> rankMap = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer m, Integer n) {
                return ((Integer) m).compareTo(n) * -1;
            }
        });
        Map<Integer, List<String>> matomeMap = new TreeMap<>();// Map<順位, ＜企業リスト, 掲載件数＞>

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

        // 掲載件数の順位づけ (Map<掲載件数, 順位>)
        for (Map.Entry<String, Integer> entry : numMap.entrySet()) {
            int number = entry.getValue();
            rankMap.put(number, rankMap.getOrDefault(number, 0) + 1);
        }
        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            rank += entry.setValue(rank);
        }

        // Map<順位, ＜企業リスト, 掲載件数＞>の作成
        for (Map.Entry<String, Integer> entry : numMap.entrySet()) {
            String kigyou = entry.getKey();
            int kennsuu = entry.getValue();
            int junni = rankMap.get(kennsuu);
            if (matomeMap.containsKey(junni)) {
                List<String> kigyouKennsuu = matomeMap.get(junni);
                String kigyouList = kigyouKennsuu.get(0);
                kigyouList += "、" + kigyou;
                kigyouKennsuu.set(0, kigyouList);
            } else {
                List<String> kigyouKennsuu = new ArrayList<>();
                kigyouKennsuu.add(kigyou);
                kigyouKennsuu.add(String.valueOf(kennsuu));
                matomeMap.put(junni, kigyouKennsuu);
            }
        }

        // 課題の書き出し
        for (Map.Entry<Integer, List<String>> entry : matomeMap.entrySet()) {
            if (entry.getKey() > 10) {
                break;
            }
            System.out.println(entry.getKey() + "位 : " + entry.getValue().get(0) + " / " + entry.getValue().get(1) + "件");
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
        System.out.println("職種分類ごとの未経験者求人の割合\n");
        for (Entry<String, BigDecimal> entry : ratioMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + "%");
        }

    }

}
