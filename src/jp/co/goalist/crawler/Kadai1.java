package jp.co.goalist.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Kadai1 {
    //throwで例外が投げられるとJVSが感知して処理してくれる？
    public static void main (String[]args) throws IOException {
        String rootUrl = "https://news.mynavi.jp/list/headline//";//クロール先のurl

        Document doc = Jsoup.connect(rootUrl).get();
        Element el=doc.select("body > div.wrapper > div.container.container--top > div.body > main > section.box.box--top.box--line > div.thumb-s").get(0);

        for(Element child : el.children()) {
            if(child.children().size()<1) {//ここでは何を？
                continue;
            }

            String title = child.text();//li要素内で平文の内容がタイトル文字列になっている
            //ニュースのurl
            String url = child.child(0).attr("href");//li要素の子要素のa要素のhrefというアトリビュート？
            url=rootUrl+url;//これがあることでURLが表示された。なぜ？

            System.out.println(title+ "\n("+ url +")\n");//\n(とは？
        }

    }
}
