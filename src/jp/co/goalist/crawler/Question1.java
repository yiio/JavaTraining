package jp.co.goalist.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public  class Question1 {
    public static void main(String[] args)  throws IOException {
        Overall(args);
        Business(args);
        Digital(args);
        Kurashi(args);
        Entertainment(args);

    }

    // 総合カテゴリ
    public static void Overall(String[] args) throws IOException {
        System.out.println("■総合\n");
        String rootUrl = "http://news.mynavi.jp/top/headline";
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        //System.out.println(el.toString());

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            System.out.println(title + "\n(" + url + ")\n");
            //System.out.println(doc.html());
        }
    }


    // ビジネスカテゴリ
    public static void Business(String[] args) throws IOException {
        System.out.println("\n■ビジネス\n");
        String rootUrl = "https://news.mynavi.jp/list/headline/business/";
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        //System.out.println(el.toString());

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            System.out.println(title + "\n(" + url + ")\n");
            //System.out.println(doc.html());
        }
    }

    // デジタルカテゴリ
    public static void Digital(String[] args) throws IOException {
        System.out.println("\n■デジタル\n");
        String rootUrl = "https://news.mynavi.jp/list/headline/digital/";
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        //System.out.println(el.toString());

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            System.out.println(title + "\n(" + url + ")\n");
            //System.out.println(doc.html());
        }
    }

    // ライフカテゴリ
    public static void Kurashi(String[] args) throws IOException {
        System.out.println("\n■ライフ\n");
        String rootUrl = "https://news.mynavi.jp/list/headline/kurashi/";
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        //System.out.println(el.toString());

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            System.out.println(title + "\n(" + url + ")\n");
            //System.out.println(doc.html());
        }
    }

    // エンタメカテゴリ
    public static void Entertainment(String[] args) throws IOException {
        System.out.println("\n■エンタメ\n");
        String rootUrl = "https://news.mynavi.jp/list/headline/entertainment/";
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        //System.out.println(el.toString());

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            //ニュースのタイトル
            String title = child.select("a").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            System.out.println(title + "\n(" + url + ")\n");
            //System.out.println(doc.html());
        }
    }
}
