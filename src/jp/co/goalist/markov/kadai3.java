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


public class Kadai3 {
    
    public static final int loopCount = 10;
    public static final int AlphabetNum = 26;

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
        
        String[] bigram = new String[textA.length()-1];
        for(int i = 0; i < textA.length()-1; i++){
            bigram[i] = textA.substring(i,i+2);
        }
        
        Map<String, Integer> eachCountMap = new TreeMap<>();
        Map<String, Map<String, Integer>> countMap = new TreeMap<>();
        //initialize
        for(int i = 0; i < ('z' - 'a' + 1); i++){
            char a = 'a';
            Map<String, Integer> map = new TreeMap<>();
            for(int j = 0; j < ('z' - 'a' + 1); j++){
                map.put(String.valueOf((char)(a+j)), 0);
            }
            //eachCountMap.put(String.valueOf((char)(a+i)), 0);
            countMap.put(String.valueOf((char)(a+i)), map);
        }
        
        
        for(String bichar : bigram){
            String first = bichar.substring(0, 1);
            String second = bichar.substring(1, 2);
            //eachCountMap.replace(first,eachCountMap.get(first)+1);
            countMap.get(first).replace(second, countMap.get(first).get(second) + 1);
        
        }
        //eachCountMap.replace("n", eachCountMap.get("n")+ 1);
        
        double[] initCharDist = new double[26];
        /*
        /* initialize by probability distribution
        for(int i=0;i<26;i++){
            initCharDist[i] = (double)eachCountMap.get(String.valueOf((char)('a'+i)))/(bigram.length+1);
        }
         */
        
        /* initialize by the last char */
        for(int i=0;i<26;i++){
            initCharDist[i] = 0;
            if(i==('n'-'a')){
                initCharDist[i] = 1;
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
            
            for(String key2 : countMap.get(key1).keySet()){
                int count = countMap.get(key1).get(key2);
                double prob = (double)count / sum;
                tpm0[posix][posiy] = prob;
                posiy++;
                
            }
            posix++;
        }
        
        long startTime = System.currentTimeMillis();
        
        
        double[] ans = new double[26];
        
        ans = multiArrayAndMatrix(initCharDist, makeTransProbMatrixN(tpm0, 2));
        System.out.println("\na probability of 2nd char ");
        printArray(ans);
        System.out.println("highest prob char : "+extractHighestProbChar(ans));
        
        ans = multiArrayAndMatrix(initCharDist, makeTransProbMatrixN(tpm0, 999));
        System.out.println("\na probability of 999th char ");
        printArray(ans);
        System.out.println("highest prob char : "+extractHighestProbChar(ans));
        
        ans = multiArrayAndMatrix(initCharDist, makeTransProbMatrixN(tpm0, 1000));
        System.out.println("\na probability of 1000th char ");
        printArray(ans);
        System.out.println("highest prob char : "+extractHighestProbChar(ans));
        
        ans = multiArrayAndMatrix(initCharDist, makeTransProbMatrixN(tpm0, 1000000));
        System.out.println("\na probability of 1000000th char ");
        printArray(ans);
        System.out.println("highest prob char : "+extractHighestProbChar(ans));
        
        long endTime = System.currentTimeMillis();
        System.out.println("\nexcute time : "+(endTime-startTime)+"ms");
        
        
    }
    
    public static void printMatrix(double[][] matrix){
        for(double[] x : matrix){
            for(double y : x){
                System.out.printf("%.3f ",y);
            }
            System.out.println();
        }
    }
    
    public static void printArray(double[] a){
        for(int i = 0; i < 26 ; i++){
            System.out.print((char)('a'+i));
            System.out.printf(":%.2f",a[i]*100);
            System.out.print("% ");
        }
        System.out.println();
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

    public static double[] multiArrayAndMatrix(final double[] x,final double[][] y){
        double[] ret = new double[AlphabetNum];
        
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                ret[i] += x[j] * y[j][i];
            }
        }
        
        return ret;
    }
    
    public static double[][] makeTransProbMatrixN(double[][] matrix, int times){
        double[][] ret = new double[AlphabetNum][AlphabetNum];
        
        for(int i = 0; i < AlphabetNum; i++){
            for(int j = 0; j < AlphabetNum; j++){
                ret[i][j] = matrix[i][j];
            }
        }
        
        for(int i = 1; i < times; i++){
            ret = multiMatrix(ret, matrix);
        }
        
        return ret;
    }
    
    public static char extractHighestProbChar(double[] x){
        double max = 0;
        int posi =0;
        for(int i=0;i<26;i++){
            if(max < x[i]){
                max = x[i];
                posi = i;
            }
        }
        char ret = (char)('a'+posi);
        
        return ret;
    }
    
}
