package jp.co.goalist.analyze;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class Kadai3 {

    
    //ん〜、なんかきたない。
    public static void main(String[] args) {
        
        Path filePath = Paths.get("../../resources/testResult.csv");
        Map<String, Map<String, Integer>> seisekiMap = new HashMap<>();//name, <subject, point>
        
        List<String> subjectList = null;
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            subjectList = Arrays.asList(elemName).subList(1,elemName.length);
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem).subList(1, elem.length);//point list
                Map<String, Integer> map = new HashMap<>();//subject, point
                for(int x = 0; x < subjectList.size(); x++){
                    map.put(subjectList.get(x), Integer.parseInt(list.get(x)));
                }
                seisekiMap.put(elem[0], map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //最高得点者が複数人でも大丈夫
        for(String subject : subjectList){
            int max = 0;
            List<String> names = new ArrayList<>();
            System.out.print(subject + "の最高得点者は、");
            for(String name : seisekiMap.keySet()){
                int point = seisekiMap.get(name).get(subject);
                if(max < point){
                    names.clear();
                    names.add(name);
                    max = point;
                }else if(max == point){
                    names.add(name);
                }
            }
            
            for(int i = 0; i < names.size(); i++){
                if(i == 1){
                    System.out.print(names.get(i));
                }else{
                    System.out.print("と" + names.get(i));
                }
            }
            System.out.println("で、" + max + "です。");
            
        }
        
        
    }

}
