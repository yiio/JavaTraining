package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class yomikomi02 {
    public static void main(String[] args) {
       Path filePath =Paths.get("C:\\TechTraining\\resources\\testResult.csv");
       try(BufferedReader br = Files.newBufferedReader(filePath)){
           String line;
           int cnt =0;
           int max =0;
           while((line = br.readLine()) !=null){
               cnt++;
               if(cnt ==1) {
                   continue;

               }
               String[] cols = line.split(",");
               int mathPoint = Integer.valueOf(cols[1]);
               if(max<mathPoint) {
                   max =mathPoint;

               }
           }
           System.out.println("数学の最高点数は"+ max +"点です");
       }catch (IOException e) {
        // TODO 自動生成された catch ブロック
        e.printStackTrace();
         }



    }
}
