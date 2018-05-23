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
            Map<String, Integer> jobAllowanceMap = makeJobAllowanceMap(filePath);
            for (Map.Entry<String, Integer> bar : jobAllowanceMap.entrySet()) {
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
            for (Entry<String, Integer> r : rankList) {
                String company = r.getKey();
                int rank = r.getValue();
                int companySum = cntMap.get(company);
                if (rank >= 10) {
                    continue;
                }
                System.out.println(rank + "位：" + company + ":" + companySum + "件");

            }

            System.out.println();

            // Q6 自由課題1 広告プランの割合

            Map<String, Integer> planMap = makePlanMap(filePath);// (広告プラン,企業数)マップ
            Map<String, String> priceMap = new HashMap<String, String>();// (広告プラン、お値段)マップ

            // プランと値段の配列からマップをつくる
            String[][] planPrices = { { "N1", "90000" }, { "N2", "140000" }, { "N3", "220000" }, { "N4", "400000" },
                    { "N5", "720000" } };
            for (int j = 0; j < planPrices.length; j++) {
                priceMap.put(planPrices[j][0], planPrices[j][1]);
            }

            for (Map.Entry<String, Integer> planSum : planMap.entrySet()) {
                String plan = planSum.getKey();
                int sum = planSum.getValue();
                int price = Integer.parseInt(priceMap.get(plan));

                System.out.println(price + "円のプラン(" + plan + ")を選んでいる企業は" + sum + "％" + "(" + sum + "社)");
                System.out.println();
            }

            // Q6 自由課題2 業種別比較
            Map<String, Integer> jobPlanMap = makeJobPlanMap(filePath);// ((プラン,職種)、件数）リスト

            Map<String, Integer> jobCountMap = new HashMap<String, Integer>();// 件数をカウントするマップ

            try (BufferedReader br = Files.newBufferedReader(filePath)) {
                String line = br.readLine();
                int cnt = 0;

                while ((line = br.readLine()) != null) {
                    cnt++;
                    String[] cols = line.split(",");
                    String job = cols[4];
                    if (cnt == 1 || job.isEmpty()) {
                        continue;
                    } else if (jobCountMap.containsKey(job)) {
                        // おなじ職種が出てきた場合の処理
                        int sum = jobCountMap.get(job) + 1;
                        jobCountMap.put(job, sum);

                    } else {
                        // 初めて出てきた職種の処理
                        jobCountMap.put(job, 1);
                    }

                }

            } catch (IOException e) {
                throw e;
            }

            // 業種の数だけ回す

            for (Map.Entry<String, Integer> jobPlan : jobPlanMap.entrySet()) {
                String planOfJob = jobPlan.getKey();
                String plan = planOfJob.substring(0, planOfJob.indexOf(":"));
                String job = planOfJob.substring(planOfJob.indexOf(":") + 1);
                // うしろと前で職種とプラン分ける
                if (planOfJob.contains("広告プラン")) {
                    continue;
                }

                int price = Integer.parseInt(priceMap.get(plan));
                int jobPlanSum = jobPlan.getValue();
                int jobAllSum = jobCountMap.get(job);
                System.out.println("・" + price + "円のプランの件数は," + job + "で" + jobPlanSum + "件（"
                        + jobPlanSum * 100 / jobAllSum + "％)");

            }

            System.out.println();

            Map<String, Integer> nomalMap = new TreeMap<String, Integer>();
            Map<String, Integer> highMap = new TreeMap<String, Integer>();
            int nomalCnt = 0;
            int highCnt = 0;
            String all = "全件数";
            for (Map.Entry<String, Integer> jobPlan : jobPlanMap.entrySet()) {
                String planOfJob = jobPlan.getKey();
                String plan = planOfJob.substring(0, planOfJob.indexOf(":"));
                String job = planOfJob.substring(planOfJob.indexOf(":") + 1);
                // うしろと前で職種とプラン分ける
                if (planOfJob.contains("広告プラン")) {
                    continue;
                }

                int jobPlanSum = jobPlan.getValue();

                if (plan.contains("N1") || plan.contains("N2") || plan.contains("N3")) {

                    nomalCnt++;

                    if (nomalMap.containsKey(job)) {
                        int sum = nomalMap.get(job) + jobPlanSum;

                        nomalMap.put(job, sum);
                    } else {
                        nomalMap.put(job, jobPlanSum);
                    }

                } else {
                    highCnt++;

                    if (highMap.containsKey(job)) {
                        int sum = highMap.get(job) + jobPlanSum;
                        highMap.put(job, sum);
                    } else {
                        highMap.put(job, jobPlanSum);
                    }

                }

            }

            for (Map.Entry<String, Integer> nomalPlan : nomalMap.entrySet()) {
                String job = nomalPlan.getKey();
                int sum = nomalPlan.getValue();
                if (job.contains("広告プラン")) {
                    continue;
                }

                System.out.println("・通常プランにおいて、" + job + "の件数は" + sum + "件");

            }
            System.out.println();

            for (Map.Entry<String, Integer> highPlan : highMap.entrySet()) {
                String job = highPlan.getKey();
                int sum = highPlan.getValue();
                // うしろと前で職種とプラン分ける
                if (job.contains("広告プラン")) {
                    continue;
                }

                System.out.println("・高価格プランにおいて、" + job + "の件数は" + sum + "件");

            }
            System.out.println();

            System.out.println();

            // Q6自由課題3 給与下限平均額ランキング

            Map<String, Integer> allowanceRankMap = makeAllowanceRankMap(jobAllowanceMap);

            List<Entry<String, Integer>> allowanceRankList = new ArrayList<Entry<String, Integer>>(
                    allowanceRankMap.entrySet());
            Collections.sort(allowanceRankList, new Comparator<Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> ob1, Entry<String, Integer> ob2) {
                    // 4. 昇順
                    return ob1.getValue().compareTo(ob2.getValue());
                }
            });

            for (Entry<String, Integer> jobRanking : allowanceRankList) {
                String job = jobRanking.getKey();
                int jobRank = jobRanking.getValue();
                int allowance = jobAllowanceMap.get(job);
                System.out.println(jobRank + "位：" + job + "：月給下限金額の平均金額" + String.format("%,d", allowance) + "円");
            }
            System.out.println();

            Map<String, Integer> jobHighPlanRankMap = makeJobHighPlanRankMap(highMap);

            List<Entry<String, Integer>> highPlanRankList = new ArrayList<Entry<String, Integer>>(
                    jobHighPlanRankMap.entrySet());
            Collections.sort(highPlanRankList, new Comparator<Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> obe1, Entry<String, Integer> obe2) {
                    // 4. 昇順
                    return obe1.getValue().compareTo(obe2.getValue());
                }
            });

            for (Entry<String, Integer> highRanking : highPlanRankList) {
                String job = highRanking.getKey();
                int highRank = highRanking.getValue();
                int sum = highMap.get(job);
                System.out.println(highRank + "位：" + job + "、件数：" + sum + "件");
            }
            System.out.println();

            Map<String, Integer> jobNomalPlanRankMap = makeJobNomalPlanRankMap(nomalMap);

            List<Entry<String, Integer>> nomalPlanRankList = new ArrayList<Entry<String, Integer>>(
                    jobNomalPlanRankMap.entrySet());
            Collections.sort(nomalPlanRankList, new Comparator<Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> obe1, Entry<String, Integer> obe2) {
                    // 4. 昇順
                    return obe1.getValue().compareTo(obe2.getValue());
                }
            });

            for (Entry<String, Integer> nomalRanking : nomalPlanRankList) {
                String job = nomalRanking.getKey();
                int nomalRank = nomalRanking.getValue();
                int sum = nomalMap.get(job);
                System.out.println(nomalRank + "位：" + job + "、件数：" + sum + "件");
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

    // Q6
    private static Map<String, Integer> makePlanMap(Path filePath) throws IOException {
        Map<String, Integer> planMap = new TreeMap<String, Integer>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] cols = line.split(",");
                String plan = cols[13];
                if (planMap.containsKey(plan)) {
                    int sum = planMap.get(plan) + 1;
                    planMap.put(plan, sum);
                } else {
                    planMap.put(plan, 1);
                }

            }

        }

        catch (IOException e) {
            throw e;
        }
        return planMap;

    }

    private static Map<String, Integer> makeJobPlanMap(Path filePath) throws IOException {
        Map<String, Integer> jobPlanMap = new TreeMap<String, Integer>();
        // ((職種-プラン),件数)

        try (BufferedReader br = Files.newBufferedReader(filePath)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.contains("取得日")) {
                    continue;
                }

                String[] cols = line.split(",");
                String job = cols[4];
                String plan = cols[13];
                String key = plan + ":" + job;
                if (jobPlanMap.containsKey(key)) {
                    int sum = jobPlanMap.get(key) + 1;
                    jobPlanMap.put(key, sum);
                } else {
                    // {職種分類 広告プラン}を格納
                    jobPlanMap.put(key, 1);
                }
            }

        } catch (IOException e) {
            throw e;
        }

        return jobPlanMap;
    }

    private static Map<String, Integer> makeAllowanceRankMap(Map<String, Integer> jobAllowanceMap) {

        Map<String, Integer> allowanceRankMap = new HashMap<String, Integer>();
        // ほかの会社と比較して、件数が少なかったら順位が増えていく
        for (Map.Entry<String, Integer> jobs : jobAllowanceMap.entrySet()) {
            int cnt = 0;// 順位に足す数をカウント
            String job = jobs.getKey();
            int allowance = jobs.getValue();
            allowanceRankMap.put(job, 1);
            for (Map.Entry<String, Integer> eachRank : jobAllowanceMap.entrySet()) {
                int comparedSum = eachRank.getValue();// 他社の会社の件数
                if (comparedSum > allowance) {
                    cnt++;
                }

            }
            int rankSum = 1 + cnt;
            allowanceRankMap.put(job, rankSum);
        }

        return allowanceRankMap;
    }

    private static Map<String, Integer> makeJobHighPlanRankMap(Map<String, Integer> highMap) {
        Map<String, Integer> jobHighPlanRankMap = new HashMap<String, Integer>();

        for (Map.Entry<String, Integer> jobs : highMap.entrySet()) {
            int cnt = 0;// 順位に足す数をカウント
            String job = jobs.getKey();
            int sum = jobs.getValue();
            jobHighPlanRankMap.put(job, 1);
            for (Map.Entry<String, Integer> eachRank : highMap.entrySet()) {
                int comparedSum = eachRank.getValue();// 他社の会社の件数
                if (comparedSum > sum) {
                    cnt++;
                }

            }
            int rankSum = 1 + cnt;
            jobHighPlanRankMap.put(job, rankSum);
        }

        return jobHighPlanRankMap;

    }

    private static Map<String, Integer> makeJobNomalPlanRankMap(Map<String, Integer> nomalMap) {
        Map<String, Integer> jobNomalPlanRankMap = new HashMap<String, Integer>();

        for (Map.Entry<String, Integer> jobs : nomalMap.entrySet()) {
            int cnt = 0;// 順位に足す数をカウント
            String job = jobs.getKey();
            int sum = jobs.getValue();
            jobNomalPlanRankMap.put(job, 1);
            for (Map.Entry<String, Integer> eachRank : nomalMap.entrySet()) {
                int comparedSum = eachRank.getValue();// 他社の会社の件数
                if (comparedSum > sum) {
                    cnt++;
                }

            }
            int rankSum = 1 + cnt;
            jobNomalPlanRankMap.put(job, rankSum);
        }

        return jobNomalPlanRankMap;

    }
}
