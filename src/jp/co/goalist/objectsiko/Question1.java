package jp.co.goalist.objectsiko;

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

public class Question1 {
    public static void main(String[] args) {
        Path salesItem = Paths.get("C:/TechTraining/resources/salesItem.csv");
        Path salesList = Paths.get("C:/TechTraining/resources/salesList.csv");
        Path ans = Paths.get("C:/TechTraining/resources/answer.csv");
        Map<String,Integer> tankaMap = new HashMap<>(); // 商品コード,単価
        Map<String,Integer> dateMap = new TreeMap<>(); // 日付,金額

        // tankaMapへの要素追加
        try (BufferedReader br = Files.newBufferedReader(salesItem)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                SalesItem item = new SalesItem();
                item.property= line.split(",");
                item.inItem();
                tankaMap.put( item.code, item.tanka );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // dateMapへ要素の追加
        try (BufferedReader br = Files.newBufferedReader(salesList)) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                SalesItem item = new SalesItem();
                item.property= line.split(",");
                item.inSalesDate();
                int price = tankaMap.get(item.code) * item.num;
                if(!dateMap.containsKey(item.date)) {
                    dateMap.put(item.date, price);
                } else {
                    dateMap.put(item.date, dateMap.get(item.date) + price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 書き込みます
        try (BufferedWriter bw = Files.newBufferedWriter(ans)) {
            bw.write("販売日,売上総額");
            bw.newLine();

            Calendar cal = Calendar.getInstance();
            for(int month = 0; month < 2; month++){
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

class SalesItem {
    String[] property;
    String code;
    String name;
    int tanka;
    String date;
    int num;

    void inItem() {
        this.code = property[0];
        this.name = property[1];
        this.tanka = Integer.parseInt(property[2]);
    }

    void inSalesDate() {
        this.date = property[0];
        this.code = property[1];
        this.num = Integer.parseInt(property[2]);
    }
}
