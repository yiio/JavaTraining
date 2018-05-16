package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class yomikomi03{
    public static void main(String[]args) {
        Path filePath =Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        try(BufferedReader br = Files.newBufferedReader(filePath)){
            //昔はnewが分かれてたけど、新型はくっついた。暗記するしか。

            String[]header = {};
            Map<String,Integer>kyouka =new HashMap<String,Integer>();
            Map<String,String>tensai =new HashMap<String,String>();

            //データを一個ずつ見ていく
            String line;
            int cnt = 0;
            //nullチェック
            while ((line=br.readLine())!=null) {
                cnt++;
                //splitでファイル内のデータを分立。colsにすべてのファイルデータが！あるだけ
                String[]cols = line.split(",");

                //ヘッダ行の処理(読み取ったファイルの一行目(頭=head))
                if(cnt ==1) {
                    //ここ大事！headerはcolsだけど一行目を覚えてられる！
                    header = cols;
                    for(int i =1;i<cols.length;i++) {
                        kyouka.put(cols[i],0);
                    }
                    continue;
                }

            //ヘッダ以外の処理
                String name =cols[0];//名前は0番目だから！
                for(int i =1;i<cols.length;i++) {//ヘッダ以外の欄だから2行目
                    String subject =header[i];
                    int point = Integer.valueOf(cols[i]);
                    int max = kyouka.get(subject);
                    if(max<point) {
                        kyouka.put(subject, point);
                        tensai.put(subject, name);

                    }
                }
            }
            for(Map.Entry<String, Integer> entry:kyouka.entrySet()) {
                String subject = entry.getKey();
                String pointStr = String.valueOf(entry.getValue());
                String name = tensai.get(subject);
                System.out.println(subject + "の最高得点者は"+ name + "さんで、"+pointStr +"です。");
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

    }
}