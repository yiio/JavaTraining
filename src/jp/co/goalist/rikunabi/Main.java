package jp.co.goalist.rikunabi;

import java.io.*;
import java.util.*;
import java.nio.file.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map.Entry;
import java.text.NumberFormat;

public class Main {
    
    
    public static final String[] prefs = {"北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
                                        "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
                                        "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
                                        "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
                                        "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
                                        "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
                                        "熊本県","大分県","宮崎県","鹿児島県","沖縄県","その他・不明"
    };
    
    //use this array for kadai 6 only
    public static final String[] occus = {"建設/土木/エネルギー", "飲食/フード", "運輸/物流/配送/警備/作業/調査",
                                            "クリエイティブ（Web系）", "ITエンジニア/IT系専門職",
                                            "クリエイティブ（Web以外）", "ファッション/アパレル/インテリア",
                                            "映像/イベント/芸能/キャンペーン", "美容/エステ/ネイル",
                                            "営業/事務/企画/管理", "専門職", "製造/工場/化学/食品",
                                            "医療/医薬/福祉", "ホテル/旅館/ブライダル", "教育/語学/スポーツ",
                                            "電気/電子/機械/自動車", "販売/接客/サービス", "公務員/団体職員"};
    
    public static void main(String[] args) {
        
        final String filePath = "../../resources/recruitNaviNext.csv";
        // check for kadai 1
        System.out.println(">----- kadai1 -----<");
        System.out.println("The number of publiched : "
                           + countPublishedIn(filePath));
        
        // for check kadai 2
        System.out.println(">----- kadai2 -----<");
        System.out.println("The number of publiched with contarct status: "
                           + countContratAtStatusIn(filePath));
        
        // for check kadai 3
        System.out.println(">----- kadai3 -----<");
        for(Entry<String, Integer> pair : countWithPrefIn(filePath).entrySet()){
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
        
        // for check kadai 4
        System.out.println(">----- kadai4 -----<");
        for(Entry<String, Integer> pair : makeTableSalaryWithEachOccupations(filePath).entrySet()){
            System.out.println(pair.getKey() + " : "
                               + NumberFormat.getNumberInstance().format(pair.getValue())
                               + "円");
        }
        
        //for check kadai 5
        int position = 1;
        System.out.println(">----- kadai5 -----<");
        for(Entry<Long, List<String>> pair : countWithCompNameIn(filePath, 10).entrySet()){
            String names = String.join(", ", pair.getValue());
            System.out.println(position + "位 : " + names
                             + " / 掲載件数 : " + pair.getKey() + "件");
            position += pair.getValue().size();
        }
        
        // for kadai 6
        countPrefAndOccu(filePath);
        
    }

    
    // for kadai 1
    public static long countPublishedIn(String filePath){
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            return br.lines()
                        .skip(1)
                        .count();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    
    // for kadai 2
    public static long countContratAtStatusIn(String filePath){
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            return br.lines()
                .skip(1)
                .map(line -> line.split(","))
                .filter(elems -> elems[8].equals("契約社員"))
                .count();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // for kadai 3
    public static Map<String, Integer> countWithPrefIn(String filePath){
        
        Map<String, Integer> prefcCount = new LinkedHashMap<>();
        for(String pref : prefs){
            prefcCount.put(pref, 0);
        }
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String line;
            br.readLine();
            
            while((line = br.readLine()) != null){
                String temp = line.split(",")[6];
                String pref = temp.substring(temp.indexOf(":") + 1);
                prefcCount.put(pref, prefcCount.get(pref) + 1);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return prefcCount;
    }
    
    // for kadai 4
    public static Map<String, Integer> makeTableSalaryWithEachOccupations(String filePath){
        
        Map<String, List<Integer>> averageMap = new HashMap<>();
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            String line;
            line = br.readLine();
            
            while((line = br.readLine()) != null){
                String[] elems = line.split(",");
                String occu = elems[4];
                String salary = elems[11];
                
                if(salary.isEmpty()){
                    continue;
                }else if(!averageMap.containsKey(occu)){
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.valueOf(salary));
                    averageMap.put(occu, list);
                }else{
                    averageMap.get(occu).add(Integer.valueOf(salary));
                }
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Map<String, Integer> retMap = new HashMap<>();
        
        for(String key : averageMap.keySet()){
            int average = (int) averageMap.get(key).stream()
                                                    .mapToInt(s->(int)s)
                                                    .average()
                                                    .orElse(0);
            retMap.put(key, average);
        }
        
        
        return retMap;
    }
    
    // for kadai 5
    public static Map<Long, List<String>> countWithCompNameIn(String filePath, int top){
        
        Map<String, Long> tempMap = new HashMap<>();
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            tempMap = br.lines()
                        .skip(1)
                        .map(line -> line.split(",")[3])
                        .collect(Collectors.groupingBy(s -> s,
                                                       Collectors.counting()));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>(tempMap.entrySet());
        Collections.sort(list, (r, l) -> -r.getValue().compareTo(l.getValue()));
        
        int count = 0;
        Map<Long, List<String>> retMap = new LinkedHashMap<>();
        for(Entry<String, Long> entry : list){
            
            if(!retMap.containsKey(entry.getValue())){
                if(count > top){
                    break;
                }
                List<String> l = new ArrayList<>();
                l.add(entry.getKey());
                retMap.put(entry.getValue(), l);
                count++;
            }else{
                retMap.get(entry.getValue()).add(entry.getKey());
                count++;
            }
            
        }
        
        return retMap;
    }
    
    // for kadai 6
    public static void countPrefAndOccu(String filePath){
        
        Map<String, Map<String, Integer>> retMap = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> probMap = new LinkedHashMap<>();
        for(String pref : prefs){
            Map<String, Integer> map = new LinkedHashMap<>();
            Map<String, Integer> pmap = new LinkedHashMap<>();
            for(String occu : occus){
                map.put(occu, 0);
                pmap.put(occu, 0);
            }
            retMap.put(pref, map);
            probMap.put(pref, pmap);
        }
        
        Path salesItemPath = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            br.readLine();
            
            String line;
            while((line = br.readLine()) != null){
                String[] elems = line.split(",");
                String occu = elems[4];
                String pref = elems[6].substring(elems[6].indexOf(":") + 1);
                
                retMap.get(pref).computeIfPresent(occu, (k, s) -> s + 1);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(String pref : retMap.keySet()){
            int count = retMap.get(pref).keySet().stream()
                                                .map(s -> retMap.get(pref).get(s))
                                                .mapToInt(s -> (int)s)
                                                .sum();
            if(count == 0){
                continue;
            }
            
            for(String occu : occus){
                probMap.get(pref).replace(occu, retMap.get(pref).get(occu) * 100 / count);
            }
        }
        
        System.out.print("      ");
        for(String occu : occus){
            System.out.printf("%.3s ", occu);
            //System.out.print(occu+",");
        }
        System.out.println();
        
        for(String pref : prefs){
            System.out.printf("%.3s : ", pref);
            //System.out.print(pref+",");
            for(String occu : occus){
                System.out.printf("%-5d ", probMap.get(pref).get(occu));
                //System.out.print(probMap.get(pref).get(occu)+",");
            }
            System.out.println();
        }
        
        return ;
    }
    
}
