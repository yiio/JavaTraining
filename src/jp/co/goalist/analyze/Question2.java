package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Question2 {

    public static void main(String[] args) {

       try {
        File f = new File("c:/TechTraining/resources/testResult.csv");// 読み込み対象ファイルの場所を指定
        BufferedReader br = new BufferedReader(new FileReader(f));

        String[][] data = new String[6][5];
        String line = br.readLine();
        for (int row = 0; line != null; row++) {
            data[row] = line.split(",");
            line = br.readLine();

          }
        int max = Integer.parseInt(data[1][1]);
        for(int i = 1; i < data.length; i++) {
            if (max < Integer.parseInt(data[i][1])) {
                max = Integer.parseInt(data[i][1]);
            }
        }
        System.out.println("数学の最高得点は" +max+ "点です");
          br.close();

       }catch (IOException e) {
           System.out.println(e);

       }

    }
}
