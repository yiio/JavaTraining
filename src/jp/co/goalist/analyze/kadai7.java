package jp.co.goalist.analyze;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.text.NumberFormat;

public class Kadai7 {

    
    public static void main(String[] args) {
        
        Path salesListPath = Paths.get("../../resources/salesList.csv");
        Path salesItemPath = Paths.get("../../resources/salesItem.csv");
        
        Map<String, String> codeItemName = new HashMap<>();
        Map<String, String> codePrice = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                
                codeItemName.put(elem[0], elem[1]);
                codePrice.put(elem[0], elem[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        Map<String, Integer> codeCount = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                if(!codeCount.containsKey(elem[1])){
                    codeCount.put(elem[1], Integer.parseInt(elem[2]));
                }else{
                    codeCount.replace(elem[1], codeCount.get(elem[1]) + Integer.parseInt(elem[2]));
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        NumberFormat nfNum = NumberFormat.getNumberInstance();
        for(String code : codeCount.keySet()){
            System.out.println("「" + codeItemName.get(code) + "」の売上高合計は、"
                               + nfNum.format((long)codeCount.get(code) * Integer.parseInt(codePrice.get(code)))
                               + "円 です。");
        
        }
        
        
    }

}
