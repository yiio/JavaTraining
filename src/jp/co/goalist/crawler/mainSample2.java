package jp.co.goalist.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class mainSample2{
    

    public static void main (String[]args) throws IOException {
        String rootUrl = "https://news.mynavi.jp/list/headline//"; // 取得先URL
    
        Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求
        Element el = doc.select("body > div.wrapper > div.container.container--top > div.body > main > section.box.box--top.box--line > div.thumb-s").get(0);
    
        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }
    
            //ニュースのタイトル
            String title = child.text();
    
            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;
    
            System.out.println(title + "\n(" + url + ")\n");
        }
    }
}