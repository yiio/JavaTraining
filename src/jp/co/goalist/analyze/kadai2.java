package jp.co.goalist.analyze;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;

public class kadai2 {

    public static void main(String[] args) {
        Path filePath = Paths.get("../../resources/testResult.csv");
        Map<String,List<String>> seisekiMap = new HashMap<>();//name , points
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            line = br.readLine();//first line
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem).subList(1,5);
                seisekiMap.put(elem[0],list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int mathMax=0;
        for(String name : seisekiMap.keySet()){
            if(mathMax < Integer.parseInt(seisekiMap.get(name).get(0)))
                mathMax = Integer.parseInt(seisekiMap.get(name).get(0));
        }
        
        System.out.println("数学の最高得点は"+mathMax+"点です。");
        
    }

}
