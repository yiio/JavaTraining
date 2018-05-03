package jp.co.goalist.analyze;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class Kadai4 {

    
    public static void main(String[] args) {
        
        Path filePath = Paths.get("../../resources/testResult.csv");
        
        Map<String, Map<String, Integer>> seisekiMap = new HashMap<>();//name, <subject, point>
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
                
                Map<String, Integer> map = new HashMap<>();
                for(int x = 0; x < subjectList.size(); x++){
                    map.put(subjectList.get(x), Integer.parseInt(list.get(x)));
                }
                seisekiMap.put(elem[0],map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(int x = 0; x < subjectList.size(); x++){
            for(String name : seisekiMap.keySet()){
                sumList[x] += seisekiMap.get(name).get(subjectList.get(x));
            }
        }
        
        for(int x = 0; x < subjectList.size(); x++){
            System.out.println(subjectList.get(x) + "の平均点は、"
                               + String.format("%.2f", (double)sumList[x] / seisekiMap.size())
                               + "点です。");
        }
        
    }

}
