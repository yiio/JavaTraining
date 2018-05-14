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

public class Question8_2 {

    public static void main(String[] args) {

        Path filePath2 = Paths.get("c:/TechTraining/resources/salesItem.csv");
        Path filePath1 = Paths.get("c:/TechTraining/resources/salesList.csv");
        int lineCount = 0;

        String[] date1 = new String[3];
        String[] date2 = new String[3];
        List<String> dateList = new ArrayList<String>();
        List<String> dateList2 = null;
        Calendar firstDate = Calendar.getInstance();
        Calendar lastDate = Calendar.getInstance();
        Map<String,Integer> priceMap = new HashMap<>();
        Map<String,Integer> salesMap = new TreeMap<>();

        try(BufferedReader br = Files.newBufferedReader(filePath2)){

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] elems2 = line.split(",");
                priceMap.put(elems2[0], Integer.parseInt(elems2[2]));
            }

        }catch (IOException e) {
            System.out.println(e);
        }


        try(BufferedReader br1 = Files.newBufferedReader(filePath1)){

            String line = br1.readLine();
            while((line = br1.readLine())!=null) {
                String[] elems = line.split(",");

                dateList.add(elems[0]);

              //日付ごとの売り上げを求める
                if (!salesMap.containsKey(elems[0])) {
                    //salesMapに含まれていない日付は、新しく売上を追加
                    salesMap.put(elems[0], priceMap.get(elems[1]) * Integer.parseInt(elems[2]));
                }else {
                  //salesMapに含まれているものは、それまでの売上に加算
                    salesMap.put(elems[0], salesMap.get(elems[0]) + priceMap.get(elems[1])* Integer.parseInt(elems[2]));
                }

                lineCount++;
            }


           date1 = dateList.get(0).split("/");
           date2 = dateList.get(lineCount-1).split("/");
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

           dateList2 = new ArrayList<String>(salesMap.keySet());


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
            for(int x = 0; x < dateList2.size(); x++) {
               bw.write(dateList2.get(x) + "," + salesMap.get(dateList2.get(x)) + "円");
               bw.newLine(); // 改行
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}