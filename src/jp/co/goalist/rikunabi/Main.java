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

public class Main {
    public static void main(String[] args) {
        Path rikunabi = Paths.get("C:/TechTraining/resources/recruitNaviNext.csv");
        //Q1(rikunabi);
        //Q2(rikunabi);
        //Q3(rikunabi);
        //Q4(rikunabi);
        Q5(rikunabi);
        //Q6(rikunabi);
    }

    public static void Q1(Path rikunabi) {
        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            int i = 0;
            while ((line = br.readLine()) != null) {
                i++;
            }
            System.out.println("----------課題1----------");
            System.out.println("全掲載件数は " + String.format("%,d", i) + " 件です。\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Q2(Path rikunabi) {
        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                if( elem[8].equals("契約社員") ) {
                    i++;
                }
            }
            System.out.println("----------課題2----------");
            System.out.println("雇用区分が「契約社員」の掲載件数は " + i + " 件です。\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Q3(Path rikunabi) {
        String[] prefArry={"北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
                            "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
                            "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
                            "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
                            "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
                            "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
                            "熊本県","大分県","宮崎県","鹿児島県","沖縄県","その他・不明"
                };
        // 都道府県,件数
        Map<String,Integer> prefMap = new LinkedHashMap<>(); // 上の順で表示させたいのでLinkedHashMap使いました

        // mapに都道府県と初期件数として0を入れていく
        for(String pref : prefArry) {
            prefMap.put(pref, 0);
        }

        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String pref = elem[6].substring(3); // 13:東京都 ってなってるので最初の3文字を削る
                int countNum = prefMap.get(pref);
                prefMap.put(pref,countNum + 1); // mapのカウントを進める！
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----------課題3----------");
        System.out.println("47都道府県ごとの掲載件数は以下のとおりです。");
        for(String pref : prefMap.keySet()) {
            System.out.println(pref + " : " + prefMap.get(pref) + " 件");
        }
        System.out.println(" ");
    }

    public static void Q4(Path rikunabi) {
        Map<String,Integer> sumMap = new HashMap<>(); // 職種分類,合計金額
        Map<String,Integer> countMap = new HashMap<>(); // 職種分類,個数

        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String job = elem[4]; //職種分類

                try {
                    int price = Integer.parseInt(elem[11]); // 月給加減金額
                    if(!sumMap.containsKey(job)) {
                        sumMap.put(job, price);
                        countMap.put(job, 1);
                    } else {
                        sumMap.put(job, sumMap.get(job) + price);
                        countMap.put(job, countMap.get(job) + 1);
                    }
                } catch (NumberFormatException e) { // 月給加減金額に記載がない場合の処理
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----------課題4----------");
        System.out.println("職種分類ごとの月給下限金額の平均金額は以下のとおりです。");

        for(String job : sumMap.keySet()) {
            int sum = sumMap.get(job);
            int countNum = countMap.get(job);
            int ave = sum / countNum;
            System.out.println(job + " : " + String.format("%,d", ave) + " 円");
        }
        System.out.println(" ");
    }

    public static void Q5(Path rikunabi) {
        Map<String,Integer> countMap = new HashMap<>(); // 企業名,掲載件数
        Map<Integer, Integer> countRankMap = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer m, Integer n) {
                return ((Integer) m).compareTo(n) * -1;
            }
        });
        Map<String,Integer> rankMap = new HashMap<>(); // 企業名,掲載件数

        //countMapの作成
        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String name = elem[3];
                if(!countMap.containsKey(name)) {
                    countMap.put(name, 1);
                } else {
                    countMap.put(name, countMap.get(name) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // countrankMapの作成
        for(String name : countMap.keySet()) {
            int count = countMap.get(name);
            if(!countRankMap.containsKey(count)) {
                countRankMap.put(count, 0);
            }
        }
        int i = 0;
        for(int count : countRankMap.keySet()) {
            i++;
            countRankMap.put(count, i);
        }

        // rankMapの作成
        for(String name : countMap.keySet()) {
            int rank = countRankMap.get(countMap.get(name));
            rankMap.put(name, rank);
        }
        // 昇順に並び替える
        List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(rankMap.entrySet());
        Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj1.getValue().compareTo(obj2.getValue());
            }
        });

        System.out.println("----------課題5----------");
        System.out.println("掲載件数TOP10位までの企業ランキングは以下のとおりです。");

        int x =0,rank = 1,befNum = 0;
        for(Entry<String, Integer> entry : list_entries) {
            x++;
            String name = entry.getKey();
            int count = countMap.get(name);
            if(count<befNum) { // 掲載件数が減ったときだけ順位を落とす
                rank = x;
            }
            befNum = count;
            if (rank > 10) {
                break;
            }
            System.out.println(rank + "位(" + count + "件) : " + name );
        }
    }

    public static void Q6(Path rikunabi) {

        String[] jobArry = {"建設/土木/エネルギー", "飲食/フード", "運輸/物流/配送/警備/作業/調査",
                            "クリエイティブ（Web系）", "ITエンジニア/IT系専門職",
                            "クリエイティブ（Web以外）", "ファッション/アパレル/インテリア",
                             "映像/イベント/芸能/キャンペーン", "美容/エステ/ネイル",
                             "営業/事務/企画/管理", "専門職", "製造/工場/化学/食品",
                             "医療/医薬/福祉", "ホテル/旅館/ブライダル", "教育/語学/スポーツ",
                             "電気/電子/機械/自動車", "販売/接客/サービス", "公務員/団体職員"};

        Map<String,Integer> jobCountMap = new LinkedHashMap<>(); // 職種分類,件数

        for(String job : jobArry) {
            jobCountMap.put(job, 0);
        }

        Map<String, Map<String, Integer>> jobAdvMap = new TreeMap<>(); // < 業種, < 広告プラン , 件数 > >
        String[] advPlan = {"N1","N2","N3","N4","N5"};

        // jobAdvMapの準備
        for(String job : jobArry){
            Map<String, Integer> map = new TreeMap<>();
            for(String plan : advPlan){
                map.put(plan, 0);
            }
            jobAdvMap.put(job, map);
        }

        try (BufferedReader br = Files.newBufferedReader(rikunabi)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String job = elem[4];
                String plan = elem[13];
                int jobCountNum = jobCountMap.get(job);
                int planCountNum = jobAdvMap.get(job).get(plan);
                jobCountMap.put(job,jobCountNum + 1); // jobCountMapのカウントを進める！
                jobAdvMap.get(job).replace(plan, planCountNum + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----------課題6----------");
        for(String job : jobCountMap.keySet()) {
            int jobCountNum = jobCountMap.get(job);
            System.out.println(job);
            System.out.println("　掲載件数 : " + jobCountNum);
            for(String plan : jobAdvMap.get(job).keySet()) {
                int planCountNum = jobAdvMap.get(job).get(plan);
                System.out.println("　" + plan + " : " + planCountNum);
            }
        }
        System.out.println(" ");
    }
}
