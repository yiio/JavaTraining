package jp.co.goalist.analyze;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class kadai3 {

    
    //ん〜、なんかきたない。
    public static void main(String[] args) {
        Path filePath = Paths.get("../../resources/testResult.csv");
        Map<String,Map<String,Integer>> seisekiMap = new HashMap<>();//name , points
        List<String> subjectList=null;
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            subjectList = Arrays.asList(elemName).subList(1,elemName.length);
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem).subList(1,5);
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
            int max = 0;
            String nametemp = null;
            System.out.print(subjectList.get(x)+"の最高得点者は、");
            for(String name : seisekiMap.keySet()){
                if(max < seisekiMap.get(name).get(subjectList.get(x))){
                    max = seisekiMap.get(name).get(subjectList.get(x));
                    nametemp = name;
                }
            }
            System.out.println(nametemp+"で、"+seisekiMap.get(nametemp).get(subjectList.get(x))+"です。");
            
        }
        
        
    }

}
