package jp.co.goalist.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public  class Question1 {
    public static void main(String[] args) throws IOException {
        String rootUrl = "https://news.mynavi.jp/";
        Document doc = Jsoup.connect(rootUrl).get();
        Element tab = doc.select("ul.tab-nav").get(0);
        for (Element child1 : tab.children()) {
            if (child1.children().size() < 1) {
                continue;
            }
            String category = child1.text();
            System.out.println("■" + category);
            String tag = child1.select("a").attr("href");
            Element el = doc.select(tag + "> div.tab__list > ul").get(0);
            for (Element child2 : el.children()) {
                if (child2.children().size() < 1) {
                    continue;
                }
                //ニュースのタイトル
                String title = child2.text();
                //ニュースのURL
                String url = child2.child(0).attr("href");

                System.out.println(title + "\n(" + url + ")\n");
            }
        }
    }




}
