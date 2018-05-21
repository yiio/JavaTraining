package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class Main {
    static Map<String, Integer> numberMap = new HashMap<String, Integer>(); // キーに「全掲載件数」と「契約社員」を、値にそれらの件数を格納
    static Map<String, Integer> prefMap = new LinkedHashMap<String, Integer>(); // 都道府県, 掲載件数
    static Map<String, Integer> jobSalaryMap = new HashMap<String, Integer>(); // 職種, 月給下限金額の合計
    static Map<String, Integer> jobCountMap = new HashMap<String, Integer>(); // 職種, 職種ごとの掲載件数
    static Map<String, Integer> nameCountMap = new HashMap<String, Integer>(); // 企業名, 企業ごとの掲載件数


    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        
        
        int cnt = 0; // 全掲載件数
        int numOfContract = 0; // 雇用区分が契約社員である案件数
        
        // prefMapの初期値を設定
        String[] prefAry = {"北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
                "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
                "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
                "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
                "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
                "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
                "熊本県","大分県","宮崎県","鹿児島県","沖縄県",
                };
        for (int i = 0; i < 47; i++) {
            prefMap.put(prefAry[i], 0);
        }
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // ヘッダの処理
            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfEmp = headerList.indexOf("雇用区分");
            int indexOfPref = headerList.indexOf("エリア都道府県");
            int indexOfJob = headerList.indexOf("職種分類");
            int indexOfSalary = headerList.indexOf("月給下限金額");
            int indexOfName = headerList.indexOf("企業名");
            
            
            // 内容の処理（2行目以降）
            while ((line = br.readLine()) != null) {
                cnt++; // すべての掲載件数をカウント（課題１）
                String[] cols = line.split(",");
                
                // 「契約社員」の掲載件数をカウント（課題２）
                if (cols[indexOfEmp].equals("契約社員")) {
                    numOfContract++;
                }
                
                // 47都道府県ごとの掲載件数をカウント（課題３） 
                String pref = cols[indexOfPref];
                // エリア都道府県の文字列（「13:東京都」など）を都道府県名のみ（「東京都」など）に変換
                String[] prefCols = pref.split(":");
                String clearPref = prefCols[1];
                if (!prefMap.containsKey(clearPref)) {
                    prefMap.put(clearPref, 0);
                }
                int prefCnt = prefMap.get(clearPref) + 1;
                prefMap.put(clearPref, prefCnt);

                
                // 職種分類ごとの月給下限金額を取得し、jobSalaryMapの値に足していく（課題４）
                if (StringUtils.isNumeric(cols[indexOfSalary])) { // 月給下限金額が掲載されていないデータは除外
                    String job = cols[indexOfJob];
                    int salary = Integer.valueOf(cols[indexOfSalary]);
                    if (jobSalaryMap.containsKey(job)) {
                        int sumOfSalary = jobSalaryMap.get(job) + salary;
                        jobSalaryMap.put(job, sumOfSalary); // 職種別の月給下限金額の合計を更新
                        int countOfJob = jobCountMap.get(job) + 1;
                        jobCountMap.put(job, countOfJob); // 職種別の掲載件数を更新
                    } else { // その職種の初回登場時
                        jobSalaryMap.put(job, salary);
                        jobCountMap.put(job, 1);
                    }
                }
                
                // 企業ごとの掲載件数をカウント（課題５）
                String name = cols[indexOfName];
                if (nameCountMap.containsKey(name)) {
                    int countOfName = nameCountMap.get(name) + 1; 
                    nameCountMap.put(name, countOfName); // 企業別の掲載件数を更新
                } else { // その企業の初回登場時
                    nameCountMap.put(name, 1);
                }
            }
            numberMap.put("全掲載件数", cnt);
            numberMap.put("契約社員", numOfContract);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 課題の答えを表示
        printQ1();
        printQ2();
        printQ3();
        printQ4();
        printQ5();
        
    }

    // 各課題のメソッドを作成
    // Q1 全掲載件数を表示するメソッド
    private static void printQ1() {
        System.out.println("課題１");
        int allCnt = numberMap.get("全掲載件数");
        System.out.println("全掲載件数は" + allCnt + "件です。");
        System.out.println("");
    }
    
    // Q2 「契約社員」の掲載件数を表示するメソッド
    private static void printQ2() {
        System.out.println("課題２");
        int numOfContract = numberMap.get("契約社員");
        System.out.println("雇用区分「契約社員」の掲載件数は" + numOfContract + "件です。");
        System.out.println("");
    }
    
    // Q3 prefMapから都道府県ごとの掲載件数を取得し、表示するメソッド
    private static void printQ3() {
        System.out.println("課題３");
        System.out.println("都道府県名 ： 掲載件数");
        for (Map.Entry<String, Integer> entry : prefMap.entrySet()) {
            String pref = entry.getKey();
            int count = entry.getValue();
            System.out.println(pref + " ： " + count + "件");
        }
        System.out.println("");
    }
    
    // Q4 下限金額の合計を掲載件数で割り、カンマ区切りで整数値にして出力するメソッド
    private static void printQ4() {
        System.out.println("課題４");
        System.out.println("職種 ： 月給下限金額平均");
        for (Map.Entry<String, Integer> entry : jobSalaryMap.entrySet()) {
            String jobName = entry.getKey(); // 職種名
            int divisor = jobCountMap.get(jobName); // 職種ごとの掲載件数
            int sumSalary = entry.getValue(); // 職種ごとの月給下限金額の合計
            int avgSalary = sumSalary / divisor;
            String viewAvg = String.format("%, d", avgSalary);
            System.out.println(jobName + " ： " + viewAvg + " 円");
        }
        System.out.println("");
    }
    
    // Q5 企業ごとの件数を比較し、10位までを出力
    private static void printQ5() {
        List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(nameCountMap.entrySet());
        
        Collections.sort(list_entries, new Comparator<Entry<String, Integer>>(){
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        
        // 結果を表示
        System.out.println("課題５");
        int rank = 0;
        int cnt = 0; // cntが10より大きくなったら終了
        int num1;
        int num2 = 0;
        for(Entry<String, Integer> entry : list_entries) {
            cnt++;
            num1 = entry.getValue();
            if (num2 != num1) {
                rank++;
                if (cnt >= 11) {
                    break;
            }

            }
            String name = entry.getKey();
            int number = entry.getValue();
            System.out.println(rank + "位　" + name + " : " + number + "件");
            num2 = entry.getValue();
        }
    }

}
