package jp.co.goalist.crawl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question2 {
    public static void main(String[] args) {
        List<String> newsList = new ArrayList<>();
        try {
            newsList = sample();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // csvファイルの出力
        Path filePath = Paths.get("C:\\TechTraining\\resources\\newsList.csv");
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write("件名,カテゴリ,ニュース日時,URL");
            bw.newLine();
            for (String news : newsList) {
                bw.write(news);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> sample() throws IOException {
        List<String> newsList = new ArrayList<>();

        String rootUrl = "http://news.mynavi.jp/top/headline/";

        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select(
                "div.container.container--top > div.body > main.main > section.box.box--top.box--line > div.thumb-s")
                .get(0);

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            // ニュースのタイトル
            String title = child.child(0).child(1).text();
            // ニュースのジャンル
            String genre = child.child(0).child(2).select("a").text();
            // ニュースの日付
            String date = child.child(0).child(2).select("span").text();
            // ニュースのリンク
            String link = child.child(0).child(1).child(0).attr("href");

            String news = "\"" + title + "\"" + ",\"" + genre + "\",\""  + link.substring(9, 13) + "/" + date+ "\",https://news.mynavi.jp" + link + "\"";
            newsList.add(news);
        }
        System.out.println(newsList);
        return newsList;
    }
}
