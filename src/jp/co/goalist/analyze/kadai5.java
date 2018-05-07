package jp.co.goalist.analyze;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class Kadai5 {

    
    public static void main(String[] args) {
        
        Path filePath = Paths.get("../../resources/testResult.csv");
        
        Map<String, Map<String, Integer>> seisekiMap = new HashMap<>();//<name , <subject , point>>
        List<String> subjectList = null;
        int[] sumList = null;
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            subjectList = Arrays.asList(elemName).subList(1, elemName.length);
            
            sumList = new int[subjectList.size()];
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem).subList(1, elemName.length);
                
                Map<String,Integer> map = new HashMap<>();
                for(int x = 0; x < subjectList.size(); x++){
                    map.put(subjectList.get(x), Integer.parseInt(list.get(x)));
                }
                
                seisekiMap.put(elem[0], map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(int x = 0; x < subjectList.size(); x++){
            for(String name : seisekiMap.keySet()){
                sumList[x] += seisekiMap.get(name).get(subjectList.get(x));
            }
        }
        
        for(String name : seisekiMap.keySet()){
            int sum = 0;
            int max = 0;
            List<String> maxList = new ArrayList<>();
            int min = 100;
            List<String> minList = new ArrayList<>();
            
            for(String subject : seisekiMap.get(name).keySet()){
                int point = seisekiMap.get(name).get(subject);
                sum += point;
                if(max < point){
                    maxList.clear();
                    max = point;
                    maxList.add(subject);
                }else if(max == point){
                    maxList.add(subject);
                }
                if(min > point){
                    minList.clear();
                    min = point;
                    minList.add(subject);
                }else if(min == point){
                    minList.add(subject);
                }
            }
            
            /*
            int maxTest = seisekiMap.get(name).keySet()
                                              .stream()
                                              .map(subject -> seisekiMap.get(name).get(subject))
                                              .mapToInt(point -> point)
                                              .sum();
            System.out.println("maxTest : " + maxTest);
             */
            
            System.out.print(name + "さんの全科目合計点は" + sum + "点、");
            System.out.print("全科目平均点は" + (sum / subjectList.size()) + "点、");
            
            System.out.print("最高点は");
            for(int x = 0; x < maxList.size(); x++){
                if(x == 0){
                    System.out.print(maxList.get(x));
                }else{
                    System.out.print("と" + maxList.get(x));
                }
            }
            System.out.print("で" + max + "点、");
            
            System.out.print("最低点は");
            for(int x = 0; x < minList.size(); x++){
                if(x == 0){
                    System.out.print(minList.get(x));
                }else{
                    System.out.print("と" + minList.get(x));
                }
            }
            System.out.println("で" + min + "点です。");
            
            
        
        }
        
        
        
    }

}
