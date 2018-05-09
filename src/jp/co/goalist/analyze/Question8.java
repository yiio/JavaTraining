package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Question8 {
    public static void main(String[] args) {

        Path salesItem = Paths.get("C:/TechTraining/resources/salesItem.csv");
        Path salesList = Paths.get("C:/TechTraining/resources/salesList.csv");
        Map<String,Integer> tankaMap = new HashMap<>(); // 商品コード,単価
        Map<String,Integer> dateMap = new TreeMap<>(); // 日付,金額


        // tankaMapへ要素の追加
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {
            String line;
            line = br.readLine(); // first line
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                tankaMap.put( elem[0], Integer.parseInt(elem[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // dateMapへ要素の追加
        try (BufferedReader br = Files.newBufferedReader(salesList)) {
            String line;
            line = br.readLine();//first line
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                String date = elem[0];
                int price = tankaMap.get(elem[1]) * Integer.parseInt(elem[2]);
                if(!dateMap.containsKey(date)) {
                    dateMap.put(date, price);
                } else {
                    dateMap.put(date, dateMap.get(date) + price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 書き込みます
        Path filePath = Paths.get("C:/TechTraining/resources/answer.txt");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write("販売日,売上総額");
            bw.newLine();


            for(int month = 0; month < 2; month++){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, 2017);
                int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");


                for (int i = 1; i < maxDay; i++) {
                    cal.set(Calendar.DAY_OF_MONTH, i );
                    String date = df.format(cal.getTime());

                    if(dateMap.containsKey(date)){
                        int sumPrice = dateMap.get(date);
                        String datePrice1 = date + ", " + sumPrice + "円" ;
                        bw.write(datePrice1);
                        System.out.println(datePrice1);
                    } else {
                        String datePrice2 = date + ", " + "0円" ;
                        bw.write(datePrice2);
                        System.out.println(datePrice2);
                    }
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
