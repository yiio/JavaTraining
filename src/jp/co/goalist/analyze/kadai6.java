package jp.co.goalist.analyze;

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class kadai6 {

    
    public static void main(String[] args) {
        
        Path filePath = Paths.get("../../resources/testResult.csv");
        
        Map<String,Map<String,Integer>> seisekiMap = new HashMap<>();//<name , <subject , point>>
        List<String> subjectList=null;
        int[] sumList = null;
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            subjectList = Arrays.asList(elemName).subList(1,elemName.length);
            
            sumList = new int[subjectList.size()];
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem).subList(1,elemName.length);
                
                Map<String,Integer> map = new HashMap<>();
                for(int x=0;x<subjectList.size();x++){
                    map.put(subjectList.get(x),Integer.parseInt(list.get(x)));
                }
                
                seisekiMap.put(elem[0],map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(int x=0;x<subjectList.size();x++){
            for(String name : seisekiMap.keySet()){
                sumList[x] += seisekiMap.get(name).get(subjectList.get(x));
            }
        }
        
        Map<Integer,List<String>> sumMap = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer m, Integer n){
                return ((Integer)m).compareTo(n) * -1;
            }
        });
        
        for(String name : seisekiMap.keySet()){
            int sum=0;
            for(String subject : seisekiMap.get(name).keySet()){
                sum += seisekiMap.get(name).get(subject);
            }
            
            if(!sumMap.containsKey(sum)){
                List<String> members = new ArrayList<>();
                members.add(name);
                sumMap.put(sum,members);
            }else{
                sumMap.get(sum).add(name);
            }
        }
        
        int position=1;
        for(Integer sum : sumMap.keySet()){
            System.out.print(position+"位：");
            for(String name : sumMap.get(sum)){
                System.out.print(name+"さん ");
            }
            System.out.println(sum+"点");
            position += sumMap.get(sum).size();
        }
        
        
        
    }

}
