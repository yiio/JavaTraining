package jp.co.goalist.markov;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.*;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;


public class Kadai1 {

    public static void main(String[] args) {
        
        String textA = null;
        Path filePath = Paths.get("./jp/co/goalist/markov/A.txt");
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                textA = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //System.out.println(textA+"\nlength : "+textA.length());
        
        String[] bigram = new String[textA.length()-1];
        for(int i = 0; i<textA.length()-1; i++){
            bigram[i] = textA.substring(i,i+2);
        }
        
        
        Map<String, Map<String, Integer>> countMap = new TreeMap<>();
        
        //for initial(unnecessary)
        for(int i = 0; i < ('z' - 'a'); i++){
            char x = 'a';
            Map<String, Integer> map = new TreeMap<>();
            for(int j = 0; j < ('z' - 'a')+1; j++){
                map.put(String.valueOf((char)(x+j)), 0);
            }
            countMap.put(String.valueOf((char)(x+i)), map);
            
        }
        
        
        for(String bichar : bigram){
            String first = bichar.substring(0, 1);
            String second = bichar.substring(1, 2);
            
            if(!countMap.containsKey(first)){
                Map<String, Integer> map = new TreeMap<>();
                map.put(second, 1);
                countMap.put(first, map);
            }else{
                if(!countMap.get(first).containsKey(second)){//
                    countMap.get(first).put(second, 1);
                }else{
                    countMap.get(first).replace(second, countMap.get(first).get(second) + 1);
                }
            }
        }
        
        double[][] tpm0 = new double[26][26];
        int posix = 0;
        int posiy = 0;
        for(String key1 : countMap.keySet()){
            int sum = 0;
            int max = 0;
            posiy = 0;
            String maxStr = null;
            for(String key2 : countMap.get(key1).keySet()){
                int count = countMap.get(key1).get(key2);
                sum += count;
                if(max < count){
                    max = count;
                    maxStr = key2;
                }
                
            }
            System.out.print(key1 + " > ");
            //System.out.print(" sum : " + sum + " -> ")
            
            for(String key2 : countMap.get(key1).keySet()){
                int count = countMap.get(key1).get(key2);
                double prob = (double)count / sum;
                System.out.printf(key2 + ":%.2f ",(prob*100));
                //System.out.print(key2 + ":" + (prob * 100) + "%, ");
                
                tpm0[posix][posiy] = prob;
                posiy++;
                
            }
            System.out.println("\n"+key1 +" --> "+maxStr);
            posix++;
        }
        
        double[][] ret = new double[26][26];
        double[][] temp = new double[26][26];
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                temp[i][j] = 0;
                ret[i][j] = tpm0[i][j];
            }
        }
        
        /*
        System.out.println(">- tpm0 -<");
        printMatrix(tpm0);
        System.out.println(">- temp -<");
        printMatrix(temp);
         */
        
        
        System.out.println("0 timese matrix ----------<");
        printMatrix(ret);
        
        for(int loopCount = 0; loopCount < 10; loopCount++){
            
            ret = multiMatrix(tpm0, ret);
            
            System.out.println((loopCount+1) + " times matrix ----------<");
            
            printMatrix(ret);
        }
        
        
        
        
    }
    
    public static void printMatrix(double[][] matrix){
        for(double[] x : matrix){
            for(double y : x){
                System.out.printf("%.3f ",y);
            }
            System.out.println();
        }
    }
    
    public static double[][] multiMatrix(final double[][] x, final double[][] y){
        double[][] ret = new double[26][26];
        
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                for(int k=0;k<26;k++){
                    ret[i][j] += x[i][k]*y[k][j];
                }
            }
        }
        
        return ret;
    }

}
