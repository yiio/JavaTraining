package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Main {

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
                    int count= map.get(area) + 1;
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
            LinkedHashMap<String,Integer>map=new LinkedHashMap<>();
            int jobCnt=0;
            String job;
            while((line=br.readLine())!=null) {
                String[]cols=line.split(",");
                job=cols[11];

            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}
