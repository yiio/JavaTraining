package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public  class Question2 {
    public static void main(String[] args)  throws IOException {
        Path answer = Paths.get("C:/TechTraining/resources/answer.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(answer)) {
            bw.write("件名,カテゴリ,ニュース日時,URL");
            bw.newLine();
            Overall(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Overall(BufferedWriter bw) throws IOException {
        String rootUrl = "http://news.mynavi.jp";
        String headlineUrl = rootUrl + "/top/headline";
        Document doc = Jsoup.connect(headlineUrl).get();
        Element el = doc.select("div.thumb-s").get(0);

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //カテゴリ
            String category = null;
            try {
                category = child.select("a").get(1).text();
            } catch (IndexOutOfBoundsException e) { // カテゴリーが無かった場合の処理
                category = " ";
            }

            //ニュースのURL
            String url = child.select("a").get(0).attr("href");
            String year = url.substring(9,13); // 記事のurlから年号を取得
            url = rootUrl + url;

            // 日時
            String date =year + "/" + child.select("span.thumb-s__date").get(0).text();

            String str = title + ","+ category + "," + date + "," + url  ;
            System.out.println( str );
            bw.write( str );
            bw.newLine();
        }
    }
}
