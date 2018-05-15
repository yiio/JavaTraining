package jp.co.goalist.markov;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Question2 {


    // あたまわるい
    final static String[] str = {"a", "b", "c", "d", "e", "f", "g",
                                   "h", "i", "j", "k", "l", "m", "n",
                                   "o", "p", "q", "r", "s", "t", "u",
                                   "v", "w", "x", "y", "z"};


    public static void main(String[] args) {
        int loopCount = 0;
        double matrix1[][] = new double[26][26];
        double matrix2[][] = new double[26][26];
        double temp[][] = new double[26][26];
        int firstFreq[] = {0, 0, 0, 0, 0, 0, 0,
                       0, 0, 0, 0, 0, 0, 0,
                       0, 0, 0, 1, 0, 0, 0, // 最後の文字がr
                       0, 0, 0, 0, 0 };

        Path text = Paths.get("C:/TechTraining/resources/A.txt");
        Map<String, Map<String, Integer>> countMap = new TreeMap<>(); // < fiirst ,< second , num > >
        Map<String, Integer> sumMap = new TreeMap<>(); // 確率を求めるために各アルファベットが出現した数をまとめるマップ

        // countMapに要素追加
        for(String x : str){
            Map<String, Integer> map = new TreeMap<>();
            for(String y : str){
                map.put(y, 0);
            }
            countMap.put(x, map);
        }

        // sumMapに要素追加
        for(String x : str) {
            sumMap.put(x, 0);
        }

        // textを読み込んで各組み合わせの出現数をcountMapに追加
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

        // 出現数の合計をsumMapに追加
        for(String first : str) {
            for(String second : str) {
                int num = countMap.get(first).get(second);
                int sum = sumMap.get(first) + num;
                sumMap.put(first, sum);
            }
        }

        // 出現確率を計算
        for(int i = 0; i < 26; i++) {
            String first = str[i];
            for(int j = 0; j < 26; j++) {
                String second = str[j];
                int sum = sumMap.get(first);
                double num = countMap.get(first).get(second);
                double ratio = num / sum;
                matrix1[i][j]=ratio;
                matrix2[i][j]=ratio;
                temp[i][j]=0;
            }
        }

        loopCount = 2;
        ans(temp,matrix1,matrix2,loopCount,firstFreq);

        loopCount = 999;
        ans(temp,matrix1,matrix2,loopCount,firstFreq);

        loopCount = 1000;
        ans(temp,matrix1,matrix2,loopCount,firstFreq);

        loopCount = 1000000;
        //ans(temp,matrix1,matrix2,loopCount,prob);
    }


    public static void ans(double[][] temp, double[][] matrix1, double[][] matrix2, int loopCount, int[] firstFreq ){

        // matorix1 -> かけるやつ
        // matorix2 -> かけられるやつ
        // temp -> 一時的に計算結果を保存しとくやつ

        // 行列の積をloopCount-1回数繰り返す
        for(int i = 0; i < loopCount - 1; i++) {
            for(int x = 0; x < 26; x++) {
                for(int y = 0; y < 26; y++) {
                    for(int z = 0; z < 26; z++) {
                        temp[x][y] += matrix2[x][z] * matrix1[z][y];
                    }
                }
            }
            // tempの中身をmatrix2に移してtempはリセット
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    matrix2[j][k] = temp[j][k];
                    temp[j][k]=0;
                }
            }
        }

        // 各アルファベットの出現確率を計算
        double freqArry[] = new double[26];
        for(int i = 0; i < 26; i++) {
            double freq = 0;
            for(int j = 0; j < 26; j++) {
                freq += firstFreq[j] * matrix2[j][i];
            }
            freqArry[i] = freq;
        }

        // 最も出現確率の高いアルファベットが何番目かを調べる
        double max = freqArry[0];
        int maxNum = 0;
        for (int i = 1; i < 26; i++) {
            double v = freqArry[i];
            if (v > max) {
                max = v;
                maxNum = i;
            }
        }
        System.out.println(loopCount + "番目の文字 : " + str[maxNum]);

/*
        // for check
        System.out.println("Transfer Frequency Matrix :");
        for(int i=0;i<26;i++) {
            for(int j=0;j<26;j++) {
                System.out.print(matrix2[i][j]+" ");
            }
            System.out.println();
        }
*/
    }
}