package jp.co.goalist.markov;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Question2 {
    public static int loopCount = 0;
    public static double gyouretsu1[][] = new double[26][26];
    public static double gyouretsu2[][] = new double[26][26];
    public static double temp[][] = new double[26][26];
    public static int prob[] = {0, 0, 0, 0, 0, 0, 0,
                                         0, 0, 0, 0, 0, 0, 0,
                                         0, 0, 0, 1, 0, 0, 0,
                                         0, 0, 0, 0, 0 };
    public static String[] str = {"a", "b", "c", "d", "e", "f", "g",
                                    "h", "i", "j", "k", "l", "m", "n",
                                    "o", "p", "q", "r", "s", "t", "u",
                                    "v", "w", "x", "y", "z"};


    public static void main(String[] args) {
        Path text = Paths.get("C:/TechTraining/resources/A.txt");
        Map<String, Map<String, Integer>> countMap = new TreeMap<>();
        Map<String, Integer> sumMap = new TreeMap<>();

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


        for(int i = 0; i < 26; i++) {
            String first = str[i];
            for(int j = 0; j < 26; j++) {
                String second = str[j];
                int sum = sumMap.get(first);
                double num = countMap.get(first).get(second);
                double ratio = num / sum;
                gyouretsu1[i][j]=ratio;
                gyouretsu2[i][j]=ratio;
            }
        }

        loopCount = 2;
        printMatrix(temp);

        loopCount = 100;
        printMatrix(temp);

        loopCount = 1000;
        printMatrix(temp);

        loopCount = 10000;
        printMatrix(temp);
    }


    public static void printMatrix(double[][] temp){
        for (int j = 0; j < 26; j++) {
            for (int k = 0; k < 26; k++) {
                temp[j][k] = gyouretsu1[j][k];
            }
        }
        for(int i = 0; i < loopCount; i++) {
            for(int x = 0; x < 26; x++) {
                for(int y = 0; y < 26; y++) {
                    for(int z = 0; z < 26; z++) {
                        gyouretsu2[x][y] += gyouretsu1[x][z] * temp[z][y];
                    }
                }
            }
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    temp[j][k] = gyouretsu2[j][k];
                    gyouretsu2[j][k]=0;
                }
            }
        }
        double Q[] = new double[26];
        for(int i = 0; i < 26; i++) {
            double sum = 0;
            for(int j = 0; j < 26; j++) {
                sum += prob[j] * temp[j][i];
            }
            Q[i] = sum;
        }

        double max = Q[0];
        int maxNum = 0;
        for (int i = 1; i < 26; i++) {
            double v = Q[i];
            if (v > max) {
                max = v;
                maxNum = i;
            }
        }
        System.out.println(loopCount + "番目の文字 : " + str[maxNum]);

        /*
        // for check
        System.out.println("Transfer Probability Matrix :");
        for(int i=0;i<26;i++) {
            for(int j=0;j<26;j++) {
                System.out.print(temp[i][j]+" ");
            }
            System.out.println();
        }
        */
    }
}