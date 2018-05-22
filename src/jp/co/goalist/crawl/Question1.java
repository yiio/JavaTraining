package jp.co.goalist.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question1 {
    public static void main(String[] args) {
        try {
            sample();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sample() throws IOException {
        Map<String, List<String>> categoryMap = new HashMap<>();

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
            String genre = child.child(0).child(2).child(0).text();

            // ニュースのリンク
            String link = child.child(0).child(1).child(0).attr("href");

            List<String> newsList = new ArrayList<>();
            if (categoryMap.containsKey(genre)) {
                categoryMap.get(genre).add(title + "\n(https://news.mynavi.jp" + link + ")");
            } else {
                newsList.add(title + "\n(https://news.mynavi.jp" + link + ")");
                categoryMap.put(genre, newsList);
            }
        }

        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            System.out.println("\n■" + entry.getKey());
            for (String news : entry.getValue()) {
                System.out.println(news);
            }
        }
    }
}
