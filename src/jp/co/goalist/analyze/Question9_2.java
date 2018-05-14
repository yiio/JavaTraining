package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question9_2 {
    public static void main(String[] args) {

        Path salesPrice = Paths.get("c:/TechTraining/resources/salesPrice.csv");
        int lineCount = 0;

        //1度読み込んで行数を調べる
        try (BufferedReader br = Files.newBufferedReader(salesPrice)){
            String line = br.readLine();

            while((line = br.readLine()) != null) {

                lineCount++;
            }
        }catch (IOException e) {
                System.out.println(e);
            }

        //二次元配列にsalesPrice.csvを格納
        String[][] revision = new String[lineCount][3];
        try (BufferedReader br1 = Files.newBufferedReader(salesPrice)){
            String line = br1.readLine();


            for (int x = 0; x < lineCount ; x++) {

                revision[x] = line.split(",");
                line = br1.readLine();

            }


        }catch (IOException e) {
            System.out.println(e);
        }


        Map<String,Integer> priceMap = new HashMap<>(); //<商品コード,単価>
        Map<String,Integer> salesMap = new TreeMap<>(); //<日付,売上>
        Path salesItem = Paths.get("c:/TechTraining/resources/salesItem.csv");

        //商品コードと改定前の単価をpriceMapに格納
        try(BufferedReader br = Files.newBufferedReader(salesItem)){

            String line =br.readLine();

            while((line = br.readLine()) != null) {

                String[] elems = line.split(",");
                priceMap.put(elems[0], Integer.parseInt(elems[2]));

            }


        }catch (IOException e) {
            System.out.println(e);
        }

        Path salesList = Paths.get("c:/TechTraining/resources/salesList.csv");
        List<String> dateList = new ArrayList<String>();
        int lineCount2 = 0;
        String[] date1 = new String[3]; //salesListの初日の日付
        String[] date2 = new String[3]; //salesList
        Calendar firstDate = Calendar.getInstance();
        Calendar lastDate = Calendar.getInstance();

        try(BufferedReader br = Files.newBufferedReader(salesList)){

            String line =br.readLine();


            while((line = br.readLine()) != null) {

                String[] elems = line.split(",");
                String date = elems[0];
                dateList.add(date);


                //salesList.csvの日付がsalesPrice.csvの日付より後だった場合、改定後の単価を適用
                for (int x = 0; x < lineCount; x++) {

                    if((elems[1].equals(revision[x][0])) && (date.compareTo(revision[x][1]) >= 0)) {
                        priceMap.put(revision[x][0], Integer.parseInt(revision[x][2]));
                    }

                }

                //日付ごとの売上を求める
                if(!salesMap.containsKey(elems[0])) {
                    salesMap.put(date, priceMap.get(elems[1]) * Integer.parseInt(elems[2]));
                }else {
                    salesMap.put(date, salesMap.get(date) + (priceMap.get(elems[1]) * Integer.parseInt(elems[2])));
                }

                lineCount2++;
            }



            date1 = dateList.get(0).split("/");
            date2 = dateList.get(lineCount2-1).split("/");

            //初日をその月の１日に設定
            firstDate.set(Integer.parseInt(date1[0]),Integer.parseInt(date1[1])-1,01);
            //最終日を次月の１日に設定
            lastDate.set(Integer.parseInt(date2[0]),Integer.parseInt(date2[1]),01);

          //初日と最終日の差分を求める
            long diff = lastDate.get(Calendar.DAY_OF_YEAR) - firstDate.get(Calendar.DAY_OF_YEAR);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            //差分の回数だけ日付に+1していき、salesMapのKeyに含まれない日付を追加、売上に0を追加
            for (int x = 0; x < diff; x++) {
                String now = sdf.format(firstDate.getTime());
                if (!salesMap.containsKey(now)) {
                    salesMap.put(now, 0);

                }
                firstDate.add(Calendar.DAY_OF_MONTH, +1);
            }

            System.out.println(salesMap);


        }catch (IOException e) {
            System.out.println(e);
        }

        Path filePath = Paths.get("c:/TechTraining/resources/answer.csv"); // 書き込み対象ファイルの場所を指定
        try {

            Files.deleteIfExists(filePath); // 既に存在してたら削除
            Files.createFile(filePath); // ファイル作成

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            bw.write("販売日" + "," + "売上総額");
            bw.newLine();

            //1日ごとの売上をファイルに書き込む
            for(String sales : salesMap.keySet()) {
               bw.write(sales + "," + salesMap.get(sales) + "円");
               bw.newLine(); // 改行
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
