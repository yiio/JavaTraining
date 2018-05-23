package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Q2 {

    public static void main(String[] args) {

        try {
            String rootUrl = "http://news.mynavi.jp/top/headline/"; // クロール先のurl
            Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
            Element articles = doc.select("section.box.box--top.box--line > div.thumb-s").get(0);

            // (件名、＜いろんな情報＞)のマップを作製
            Map<String, List<String>> articleMap = new LinkedHashMap<String, List<String>>();
            int cnt = 0;
            for (Element articleChild : articles.children()) {
                cnt++;
                List<String> infoList = new ArrayList<String>();
                if (cnt == 1) {
                    infoList.add("件名");
                    infoList.add("カテゴリ");
                    infoList.add("ニュース日時");
                    infoList.add("URL");
                    articleMap.put("件名", infoList);
                    continue;
                }

                String title = articleChild.child(0).child(1).text();
                String category = articleChild.child(0).child(2).child(0).text();
                String date = articleChild.child(0).child(2).child(1).text();
                String articleUrl = articleChild.child(0).child(1).child(0).attr("href");

                infoList.add(title);
                infoList.add(category);
                infoList.add(date);
                infoList.add(articleUrl);
                articleMap.put(title, infoList);
            }

            Path answerPath = Paths.get("C:\\TechTraining\\resources\\crawl_2.csv"); // 書き込み対象ファイルの場所を指定

            try (BufferedWriter bw = Files.newBufferedWriter(answerPath)) {// １行ごとに「書き込む」

                for (Map.Entry<String, List<String>> entry : articleMap.entrySet()) {
                   
                    List<String> newsInfo = entry.getValue();// もろもろ情報
                    
                    for ( int i = 0; i<newsInfo.size(); ++i ) {
                        bw.write(newsInfo.get(i));
                        bw.write(",");
                        
                    }
                    bw.newLine();
                }
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
