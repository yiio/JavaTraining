package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LastBasicTask {

    public static void main(String[] args) {
        kadai1();
        kadai2();
        kadai3();
        kadai4();

    }


    public static void kadai1 () {
        Path filePath=Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        try(BufferedReader br = Files.newBufferedReader(filePath)){

            String line=br.readLine();
            String[] header=line.split(",");
            int cnt=0;
            while((line=br.readLine())!=null) {

                cnt++;
            }
            System.out.println(cnt);
            //            String line=br.readLine();
//            //String[] header= line.split("2016/4/25");
//            int cnt=0;
//            while((line=br.readLine())!=null) {
//                int date =line.indexOf("2016/4/25");
//                if(date!=-1) {
//                    cnt++;
//                }
//            }
//            System.out.println(cnt);←答えは5154で正解

        } catch (IOException e) {
        // TODO 自動生成された catch ブロック
        e.printStackTrace();
        }
    }

    public static void kadai2 () {
        Path filePath=Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            String line=br.readLine();
            String[] header=line.split(",");
            int cnt=0;
            int number=0;
            while((line=br.readLine())!=null) {
                String[] cols=line.split(",");
                number=cols[8].indexOf("契約社員");
                if(number!=-1) {
                    cnt++;
                }
            }
            System.out.println(cnt);
//            String line=br.readLine();
//            int cnt=0;
//            while((line=br.readLine())!=null) {
//                int date =line.indexOf(",契約社員,");
//                if(date!=-1) {
//                    cnt++;
//                }
//            }
//            System.out.println(cnt);

        } catch (IOException e) {
        // TODO 自動生成された catch ブロック
        e.printStackTrace();
        }
    }

    public static void kadai3(){
        Path filePath=Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            String line=br.readLine();
            LinkedHashMap<String,Integer>map=new LinkedHashMap<>();
            String area;

            while((line=br.readLine())!=null) {
                String[] cols=line.split(",");
                area=cols[6];
                if (map.containsKey(area)) {
                    int count= map.get(area) + 1;//get(area)で値のこと
                    map.put(area,count);
                } else {
                    map.put(area, 0);
                }
            }
            for (Entry<String, Integer> entry : map.entrySet()) {
                System.out.println( entry.getKey()+"の掲載件数:"+entry.getValue()+"件");
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public static void kadai4(){
        Path filePath=Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            String line=br.readLine();
            String[] header=line.split(",");
            LinkedHashMap<String,Integer>jobMinMoneyMap= new LinkedHashMap<>();
            LinkedHashMap<String,Integer>jobHumanMap=new LinkedHashMap<>();
            String job;
            String minMoney;
            int min=0;
            int parsonCnt=1;
            while((line=br.readLine())!=null) {
                String[] cols=line.split(",");
                job=cols[4];
                minMoney=cols[11];
                if(minMoney.isEmpty()) {
                    continue;
                }else if(jobMinMoneyMap.containsKey(job)) {
                    min =Integer.parseInt(minMoney);
                    min+=jobMinMoneyMap.get(job);
                    jobMinMoneyMap.put(job,min);
                    parsonCnt=jobHumanMap.get(job)+1;
                    jobHumanMap.put(job,parsonCnt);
                }else {
                    min =Integer.parseInt(minMoney);
                    jobMinMoneyMap.put(job, min);
                    jobHumanMap.put(job, 0);
                }
            }

            for(Map.Entry<String,Integer> entry : jobMinMoneyMap.entrySet()) {
                String jobcat = entry.getKey();
                int count = jobHumanMap.get(jobcat);

                double db=(double)entry.getValue();
                //int i=jobHumanMap.get();
                double ave=db/count;//forを書かなくても、すでにカウントは上でputしている
                BigDecimal avebd=new BigDecimal(ave);
                BigDecimal roundedave =avebd.setScale(0,RoundingMode.HALF_UP);
                System.out.println(entry.getKey()+":\\"+roundedave);
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

//    public static void kadai5() throws IOException {
//        Path filePath=Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
//        try(BufferedReader br = Files.newBufferedReader(filePath)){
//            String line=br.readLine();
//            String[] header=line.split(",");
//        }
//    }
}
