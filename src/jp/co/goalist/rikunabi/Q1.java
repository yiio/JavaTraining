package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Q1 {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\\\recruitNaviNext.csv");

        try {

            // Q1
            int allNumber = makeAllNmuber(filePath);
            System.out.println("全掲載件数（ヘッダ除く）は" + allNumber + "件です");
            System.out.println();

            // Q2
            int contractEmployee = makeContractEmployee(filePath);
            System.out.println("雇用区分「契約社員」の件数は" + contractEmployee + "件です");
            System.out.println();

            // Q3
            Map<String, Integer> prefSumMap = makePrefSumMap(filePath);
            for (Map.Entry<String, Integer> entry : prefSumMap.entrySet()) {
                String pref = entry.getKey();
                int prefSum = entry.getValue();

                System.out.println(pref + "の件数は" + prefSum + "件です");
            }
            System.out.println();

            // Q4
            Map<String, Integer> jobAllowaceMap = makeJobAllowanceMap(filePath);
            for (Map.Entry<String, Integer> bar : jobAllowaceMap.entrySet()) {
                String job = bar.getKey();
                int allowance = bar.getValue();
                System.out.println(job + "の月給下限金額の平均金額は" + String.format("%,d", allowance) + "円です");
            }
            System.out.println();

            // Q5
            Map<String, Integer> cntMap = makeCntMap(filePath);// (掲載件数,社名)マップ
            Map<String, Integer> rankMap = makeRankMap(cntMap);// (社名、順位)マップ

            List<Entry<String, Integer>> rankList = new ArrayList<Entry<String, Integer>>(rankMap.entrySet());
            
            Collections.sort(rankList, new Comparator<Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                    // 4. 昇順
                    return obj1.getValue().compareTo(obj2.getValue());
                }
            });
            
            // 5. ループで要素順に値を取得する
            for(Entry<String, Integer> r : rankList) {
                String company = r.getKey();
                int rank = r.getValue();
                int companySum = cntMap.get(company);
                if (rank > 10) {
                    continue;
                }
                System.out.println(rank + "位：" + company + ":" + companySum + "件");

            }

            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Q1
    private static int makeAllNmuber(Path filePath) throws IOException {
        int allNumber = 0;

        // key: 商品コード, value: (key: ヘッダー情報, value: 商品情報)

        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                allNumber++;

            }
            allNumber = allNumber - 1;// ヘッダーを消す
        }

        catch (IOException e) {
            throw e;
        }
        return allNumber;
    }

    // Q2
    private static int makeContractEmployee(Path filePath) throws IOException {
        int contractEmployee = 0;

        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            String line = br.readLine();
            int cnt = 0;// 何行目？

            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                // ヘッダー処理
                if (cnt == 1) {
                    continue;
                } else if (cols[8].equals("契約社員")) {
                    contractEmployee++;

                }

            }

        } catch (IOException e) {
            throw e;
        }
        return contractEmployee;
    }
   
    // Q3
    private static Map<String, Integer> makePrefSumMap(Path filePath) throws IOException {
        Map<String, Integer> prefSumMap = new LinkedHashMap<String, Integer>();

        // 都道府県の配列
        String[] allPref = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都",
                "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府",
                "兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県",
                "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県" };

        // マップに（"都道府県",0）を格納
        for (int i = 0; i < allPref.length; i++) {
            prefSumMap.put(allPref[i], 0);
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            String line = br.readLine();
            int cnt = 0;

            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                String numberPref = cols[6];
                String pref = numberPref.substring(3);

                if (cnt == 1) {
                    continue;
                } else if (prefSumMap.containsKey(pref)) {
                    int sum = prefSumMap.get(pref) + 1;
                    prefSumMap.put(pref, sum);
                }

            }

        }

        catch (IOException e) {
            throw e;
        }
        return prefSumMap;
    }

    // Q4
    private static Map<String, Integer> makeJobAllowanceMap(Path filePath) throws IOException {
        Map<String, Integer> jobAllowanceMap = new LinkedHashMap<String, Integer>();
        Map<String, Integer> jobCountMap = new HashMap<String, Integer>();// 件数をカウントするマップ

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line = br.readLine();
            int cnt = 0;

            // ここではjobAllowanceMapの値は「すべての下限給与を足し合わせた額」
            while ((line = br.readLine()) != null) {
                cnt++;
                String[] cols = line.split(",");
                String job = cols[4];
                String allowance = cols[11];

                if (cnt == 1 || job.isEmpty() || allowance.isEmpty()) {
                    continue;
                } else if (jobAllowanceMap.containsKey(job)) {
                    // おなじ職種が出てきた場合の処理
                    int allAllowance = jobAllowanceMap.get(job) + Integer.parseInt(allowance);
                    jobAllowanceMap.put(job, allAllowance);
                    int sum = jobCountMap.get(job) + 1;
                    jobCountMap.put(job, sum);

                } else {
                    // 初めて出てきた職種の処理
                    jobAllowanceMap.put(job, Integer.parseInt(allowance));
                    jobCountMap.put(job, 1);
                }

            }

        }

        catch (IOException e) {
            throw e;
        }

        // 給与を平均額にして再格納
        for (Map.Entry<String, Integer> entry : jobAllowanceMap.entrySet()) {
            String job = entry.getKey();
            int allowanceSum = entry.getValue();
            int jobCount = jobCountMap.get(job);
            jobAllowanceMap.put(job, allowanceSum / jobCount);

        }

        return jobAllowanceMap;
    }

    // Q5
    private static Map<String, Integer> makeCntMap(Path filePath) throws IOException {
        Map<String, Integer> cntMap = new HashMap<String, Integer>();// (会社名、件数)マップ
        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            String line = br.readLine();

            // （会社名、件数）を格納
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                String company = cols[3];
                if (cntMap.containsKey(company)) {
                    int sum = cntMap.get(company) + 1;
                    cntMap.put(company, sum);
                } else {
                    cntMap.put(company, 1);
                }
            }
        } catch (IOException e) {
            throw e;
        }

        return cntMap;
    }

    private static Map<String, Integer> makeRankMap(Map<String, Integer> cntMap) {
        Map<String, Integer> rankMap = new TreeMap<String, Integer>();

        // ほかの会社と比較して、件数が少なかったら順位が増えていく
        for (Map.Entry<String, Integer> companies : cntMap.entrySet()) {
            int cnt = 0;// 順位に足す数をカウント
            String company = companies.getKey();
            int sum = companies.getValue();
            rankMap.put(company, 1);
            for (Map.Entry<String, Integer> eachRank : cntMap.entrySet()) {
                int comparedSum = eachRank.getValue();// 他社の会社の件数
                if (comparedSum > sum) {
                    cnt++;
                }
                
            }
            int rankSum = 1 + cnt;
            rankMap.put(company, rankSum);
        }

        return rankMap;
    }
}
