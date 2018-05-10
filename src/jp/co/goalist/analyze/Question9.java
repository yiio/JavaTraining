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

public class Question9 {
    public static void main(String[] args) {

        Path salesItem = Paths.get("C:/TechTraining/resources/salesItem.csv");
        Path salesList = Paths.get("C:/TechTraining/resources/salesList.csv");
        Path salesPrice = Paths.get("C:/TechTraining/resources/salesPrice.csv");
        Path answer = Paths.get("C:/TechTraining/resources/answer.csv");
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


        //日付を1日ずつ進めていく
        Calendar cal = Calendar.getInstance();
        for(int month = 0; month < 2; month++){
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, 2017);
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            for (int i = 1; i < maxDay; i++) {
                cal.set(Calendar.DAY_OF_MONTH, i );
                String date = df.format(cal.getTime());
                dateMap.put(date, 0);


                // salesPriceを1行ずつ読み、記載されてる日付がdateと一致する場合は価格を改定
                try (BufferedReader br = Files.newBufferedReader(salesPrice)) {
                    String line;
                    line = br.readLine(); // first line
                    while ((line = br.readLine()) != null) {
                        String[] elem = line.split(",");
                        if( date.equals(elem[1]) ) {
                            tankaMap.put( elem[0], Integer.parseInt(elem[2]));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // salesListを1行ずつ読み、記載されてる日付がdateと一致する場合はdateMapの金額に加算
                // 一致しない場合は<日付,0>を追加
                try (BufferedReader br = Files.newBufferedReader(salesList)) {
                    String line;
                    line = br.readLine(); // first line
                    while ((line = br.readLine()) != null) {
                        String[] elem = line.split(",");
                        if( date.equals(elem[0])) {
                            int price = tankaMap.get(elem[1]) * Integer.parseInt(elem[2]);
                            if(!dateMap.containsKey(date)) {
                                dateMap.put(date, price);
                            } else {
                                dateMap.put(date, dateMap.get(date) + price);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // ｶｷｺ
        try (BufferedWriter bw = Files.newBufferedWriter(answer)) {
            bw.write("販売日,売上総額");
            bw.newLine();
            for(String date : dateMap.keySet()) {
                int sumPrice = dateMap.get(date);
                String datePrice = date + "," + sumPrice + "円" ;
                System.out.println(datePrice);
                bw.write(datePrice);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
