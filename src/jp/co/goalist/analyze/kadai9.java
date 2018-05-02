package jp.co.goalist.analyze;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.text.NumberFormat;

public class kadai9 {

    
    public static void main(String[] args) {
        
        Path salesListPath = Paths.get("../../resources/salesList.csv");
        Path salesItemPath = Paths.get("../../resources/salesItem.csv");
        
        
        Map<String,String> codeItemName = new HashMap<>();
        Map<String,String> codePrice = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                
                codeItemName.put(elem[0],elem[1]);
                codePrice.put(elem[0],elem[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        Map<String,List<String>> dateItemCount = new TreeMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                if(!dateItemCount.containsKey(elem[0])){
                    List<String> list = new ArrayList<>();
                    //超絶頭が悪い
                    //date -> List:[Item][Item][Item][Item]...
                    for(int i=0;i<Integer.parseInt(elem[2]);i++){
                        list.add(elem[1]);
                    }
                    dateItemCount.put(elem[0],list);
                }else{
                    for(int i=0;i<Integer.parseInt(elem[2]);i++){
                        dateItemCount.get(elem[0]).add(elem[1]);
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //date ,<code , price>
        Map<String,Map<String,String>> revisionMap = new TreeMap<>();
        Path priceChagePath = Paths.get("../../resources/salesPrice.csv");
        Set<String> revisionDays = new TreeSet<>();
        
        try (BufferedReader br = Files.newBufferedReader(priceChagePath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                revisionDays.add(elem[1]);
                if(!revisionMap.containsKey(elem[1])){
                    Map<String,String> map = new HashMap<>();
                    map.put(elem[0],elem[2]);
                    revisionMap.put(elem[1],map);
                }else{
                    revisionMap.get(elem[1]).put(elem[0],elem[2]);
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //ひどすぎる。
        List<String> revisionDaysList = new ArrayList<>(revisionDays);
        
        int dayPosi = 0;
        Path filePath = Paths.get("../../resources/kadai2answer.csv");
        NumberFormat nfNum = NumberFormat.getNumberInstance();
        
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            
            bw.write("販売日,売上総額");
            bw.newLine();
            
            for(String date : dateItemCount.keySet()){
                
                if( ( dayPosi < revisionDaysList.size() )&&( date.compareTo(revisionDaysList.get(dayPosi)) >= 0 ) ){
                    
                    
                    for(String code : revisionMap.get(revisionDaysList.get(dayPosi)).keySet()){
                        codePrice.replace(code,revisionMap.get(revisionDaysList.get(dayPosi)).get(code));
                    }
                    dayPosi++;
                    
                
                }
                
                int sum=0;
                for(String code : dateItemCount.get(date)){
                    System.out.print(code+" : "+codePrice.get(code)+" ,");
                    sum += Integer.parseInt(codePrice.get(code));
                }
                bw.write("\""+date + "\",\"" + nfNum.format((long)sum) + "円\"");
                System.out.println("--> "+date + " , " + sum + "円");
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

}
