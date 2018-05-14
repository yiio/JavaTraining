package jp.co.goalist.markov;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Question1 {
    public static void main(String[] args) {
        Path text = Paths.get("C:/TechTraining/resources/A.txt");
        Map<String, Map<String, Integer>> countMap = new TreeMap<>();
        Map<String, Integer> sumMap = new TreeMap<>();

        // あたまわるい
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for(String x : str){
            Map<String, Integer> map = new TreeMap<>();
            for(String y : str){
                map.put(y, 0);
            }
            countMap.put(x, map);
        }

        for(String x : str) {
            sumMap.put(x, 0);
        }

        try (BufferedReader br = Files.newBufferedReader(text)) {
            String line = br.readLine();
            for (int i = 0; i < line.length()-1; i++) {
                String first = line.substring(i, i+1);
                String second = line.substring(i+1, i+2);
                countMap.get(first).replace(second, countMap.get(first).get(second) + 1);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String first : str) {
            for(String second : str) {
                int num = countMap.get(first).get(second);
                int sum = sumMap.get(first) + num;
                sumMap.put(first, sum);
            }
        }

        for(String first : str) {
            System.out.print(first + " > ");
            for(String second : str) {
                int sum = sumMap.get(first);
                double num = countMap.get(first).get(second);
                double ratio = num / sum * 100;
                BigDecimal ratio2 = new BigDecimal(String.valueOf(ratio));
                ratio2 = ratio2.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.print(second + ":" + ratio2 + "%, ");
            }
            System.out.println( );
        }
    }
}
