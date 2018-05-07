package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Question2 {
    public static void main(String[] args) {
        Map<String,Integer> scoreMap = new HashMap<>();
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");

        try (BufferedReader br = Files.newBufferedReader(filePath)){
            String line;
            line = br.readLine();//first line
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                scoreMap.put(elem[0],Integer.parseInt(elem[1]));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        int mathMax = 0;
        for(String name : scoreMap.keySet()){
            if(mathMax < scoreMap.get(name)) {
                mathMax = scoreMap.get(name);
            }
        }

        System.out.println("数学の最高得点は" + mathMax + "点です。");
    }
}