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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Main {
    public static void main (String[] args) {
        //Q1();
        //Q2();
        //Q3();
        //Q4();
        //Q5();
        //Q6();

    }


    public static void Q1() {

        Path rukunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");
        long lineCount = 0;

        try(BufferedReader br = Files.newBufferedReader(rukunabi)){

            String line = br.readLine();

            while((line = br.readLine()) != null) {
                lineCount++;
            }

        }catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("全掲載件数は" + lineCount + "件です");

    }






    public static void Q2() {
        Path rukunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");
        int count = 0;

        try(BufferedReader br = Files.newBufferedReader(rukunabi)){

            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfStatus = headerList.indexOf("雇用区分");


            while((line = br.readLine()) != null) {
                String[] elems = line.split(",");
                if(elems[indexOfStatus].equals("契約社員")) {
                    count++;
                }

            }

        }catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("「契約社員」の掲載件数は" + count +"件です");

    }






    public static void Q3() {
        Path rukunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");
        Map<String, Integer> prefMap = new HashMap<>();
        String[] prefs = {"北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
                "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
                "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
                "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
                "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
                "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
                "熊本県","大分県","宮崎県","鹿児島県","沖縄県"
                };


        try(BufferedReader br = Files.newBufferedReader(rukunabi)){

            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfPref = headerList.indexOf("エリア都道府県");


            while((line = br.readLine()) != null) {

                String[] elems = line.split(",");
                String col = elems[indexOfPref];
                String[] div = col.split(":");
                String pref = div[1];

                if(!prefMap.containsKey(pref)) {
                    prefMap.put(pref, 1);
                }else {
                    prefMap.put(pref, prefMap.get(pref) + 1);
                }



            }

        for(String list : prefs) {

            if(!prefMap.containsKey(list)) {
                prefMap.put(list, 0);

            }
        }
        }catch (IOException e) {
            System.out.println(e);
        }


        for (Map.Entry<String, Integer> entry : prefMap.entrySet()) {
            System.out.println(entry.getKey() + "の掲載件数は" + entry.getValue() + "件です");
        }


    }





    public static void Q4() {
        Path rukunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");
        Map<String, Integer> numberMap = new HashMap<>();
        Map<String, Integer> wageMap = new HashMap<>();


        try(BufferedReader br = Files.newBufferedReader(rukunabi)){

            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfWage = headerList.indexOf("月給下限金額");
            int indexOfCategory = headerList.indexOf("職種分類");


            while((line = br.readLine()) != null) {

                String[] elems = line.split(",");
                String category = elems[indexOfCategory];


                if(elems[indexOfWage].isEmpty()) {

                    continue;

                }else if(!wageMap.containsKey(category)){
                    numberMap.put(category, 1);
                    wageMap.put(category, Integer.parseInt(elems[indexOfWage]));


                }else {
                    numberMap.put(category, numberMap.get(category) + 1);
                    wageMap.put(category, wageMap.get(category) + Integer.parseInt(elems[indexOfWage]));

                }


            }

        }catch (IOException e) {
            System.out.println(e);
        }


        for(String occupation : wageMap.keySet()) {
            System.out.println(occupation + "の月給下限金額の平均は" + String.format("%,d",wageMap.get(occupation) / numberMap.get(occupation)) + "円です");
        }

    }



    public static void Q5() {

        Path rukunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");

        Map<String,Integer> numberMap = new HashMap<>();



        try(BufferedReader br = Files.newBufferedReader(rukunabi)){

            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfCompany = headerList.indexOf("企業名");


            while((line = br.readLine()) != null) {
                String[] elems = line.split(",");
                String company = elems[indexOfCompany];

                if(!numberMap.containsKey(company)) {
                    numberMap.put(company, 1);

                }else {
                    numberMap.put(company, numberMap.get(company) + 1);
                }

            }

        }catch (IOException e) {
            System.out.println(e);
        }


        //numberMapをListに変換、valueでソート
        List<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(numberMap.entrySet());

        Collections.sort(list, new Comparator<Entry<String, Integer>>() {

            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2)
            {

                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        int rank = 1;
        int count = 0;



        //順位を算出

        for (int x = 0; x < list.size(); x++) {


            if (x >= 1) { //場合分けは、list.get(x-1)のため

                if(list.get(x).getValue().compareTo(list.get(x-1).getValue()) == 0) {

                    System.out.println (rank + "位:" + list.get(x).getKey() + "," + list.get(x).getValue() + "件");


                }else {

                    rank = count + 1;
                    System.out.println(count + 1 + "位:" + list.get(x).getKey() + "," + list.get(x).getValue() + "件");


                }

                count++;

            }else { //x=0のとき

                System.out.println(rank + "位:" + list.get(x).getKey() + "," + list.get(x).getValue() + "件");
                count++;
            }
        }




    }




    public static void Q6() {

        Path rikunabi = Paths.get("c:/TechTraining/resources/recruitNaviNext.csv");


        //都道府県ごと
        try(BufferedReader br = Files.newBufferedReader(rikunabi)){

            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfPlan = headerList.indexOf("広告プラン");
            int indexOfPref = headerList.indexOf("エリア都道府県");

            Map<String,Map<String, Integer>> prefMap = new TreeMap<>();  //<都道府県,Map<広告プラン,広告数>>
            Map<String, Integer> countMap = new HashMap<>(); //<広告プラン,広告数>


            while((line = br.readLine()) != null) {


                String[] elems = line.split(",");
                String plan = elems[indexOfPlan];
                String pref = elems[indexOfPref];


                if(!prefMap.containsKey(pref)) {
                   Map<String, Integer> planMap = new HashMap<>();
                   prefMap.put(pref, planMap);
                   prefMap.get(pref).put(plan, 1);

                }else if (prefMap.containsKey(pref)){


                    if(!(prefMap.get(pref).containsKey(plan))) {
                        prefMap.get(pref).put(plan, 1);


                    }else {
                        prefMap.get(pref).put(plan, prefMap.get(pref).get(plan) +1);
                    }
                }


                //都道府県ごとのカウント
                if(!countMap.containsKey(pref)) {
                    countMap.put(pref, 1);
                }else {
                    countMap.put(pref, countMap.get(pref) + 1);
                }


            }


           for(String prefs: prefMap.keySet()) {
               //System.out.println(prefs + ":" + prefMap.get(prefs));


               if(prefMap.get(prefs).containsKey("N5")) {
                   System.out.println(prefs + ":  " + (double)prefMap.get(prefs).get("N5") / countMap.get(prefs));

               }


               if((prefMap.get(prefs).containsKey("N5")) && (prefMap.get(prefs).containsKey("N4"))) {
                   System.out.println(prefs + ":  " + ((double)prefMap.get(prefs).get("N5") + (double)prefMap.get(prefs).get("N4")) / countMap.get(prefs));
               }


           }


        }catch (IOException e) {
            System.out.println(e);
        }






        //東京都の市区町村ごと
        Map<String, Map<String, Integer>> muniMap = new TreeMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        try (BufferedReader br2 = Files.newBufferedReader(rikunabi)){
            String line = br2.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfMunicipality = headerList.indexOf("エリア市区町村");
            int indexOfPref = headerList.indexOf("エリア都道府県");
            int indexOfPlan = headerList.indexOf("広告プラン");

            while((line = br2.readLine()) != null) {
                String[] elems = line.split(",");
                String muni = elems[indexOfMunicipality];
                String pref = elems[indexOfPref];
                String plan = elems[indexOfPlan];

                if (pref.contains("東京都")) {
                    if(!muniMap.containsKey(muni)) {
                        Map<String, Integer> planMap = new HashMap<>();
                        muniMap.put(muni, planMap);
                        muniMap.get(muni).put(plan, 1);

                    }else if (muniMap.containsKey(muni)){


                        if(!(muniMap.get(muni).containsKey(plan))) {
                            muniMap.get(muni).put(plan, 1);


                        }else {
                            muniMap.get(muni).put(plan, muniMap.get(muni).get(plan) +1);
                        }
                    }
                }else {
                    continue;
                }

                if(!countMap.containsKey(muni)) {
                    countMap.put(muni, 1);
                }else {
                    countMap.put(muni, countMap.get(muni) + 1);
                }


            }

            for(String munis: muniMap.keySet()) {
                System.out.println(munis + ":" + muniMap.get(munis));
            }

            for(String munis: muniMap.keySet()) {
                //System.out.println(prefs + ":" + prefMap.get(prefs));


                if(muniMap.get(munis).containsKey("N5")) {
                    //System.out.println(munis + ":  " + (double)muniMap.get(munis).get("N5") / countMap.get(munis));
                }

                if((muniMap.get(munis).containsKey("N5")) && (muniMap.get(munis).containsKey("N4"))) {
                    System.out.println(munis + ":  " + ((double)muniMap.get(munis).get("N5") + (double)muniMap.get(munis).get("N4")) / countMap.get(munis));
                }
            }


        }catch (IOException e) {
            System.out.println(e);
        }



    }

}



