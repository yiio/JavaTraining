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
            Business(bw);
            Digital(bw);
            Kurashi(bw);
            Entertainment(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 総合カテゴリ
    public static void Overall(BufferedWriter bw) throws IOException {
        System.out.println("■総合");
        String rootUrl = "http://news.mynavi.jp/top/headline";
        Document doc = Jsoup.connect(rootUrl).get();
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
                } catch (IndexOutOfBoundsException e) {
                    category = " ";
                }

                // 日時
                String date ="2018/"+child.select("span.thumb-s__date").get(0).text();

                //ニュースのURL
                String url = child.child(0).attr("href");
                url = rootUrl + url;

                String str = title + ","+ category + "," + date + "," + url  ;
                System.out.println( str );
                bw.write( str );
                bw.newLine();
            }

    }

    // ビジネスカテゴリ
    public static void Business(BufferedWriter bw) throws IOException {
        System.out.println("■ビジネス");
        String rootUrl = "http://news.mynavi.jp/top/headline/business/";
        Document doc = Jsoup.connect(rootUrl).get();
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
            } catch (IndexOutOfBoundsException e) {
                category = " ";
            }

            // 日時
            String date ="2018/"+child.select("span.thumb-s__date").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            String str = title + ","+ category + "," + date + "," + url  ;
            System.out.println( str );
            bw.write( str );
            bw.newLine();
        }
    }

    // デジタルカテゴリ
    public static void Digital(BufferedWriter bw) throws IOException {
        System.out.println("■デジタル");
        String rootUrl = "https://news.mynavi.jp/list/headline/digital/";
        Document doc = Jsoup.connect(rootUrl).get();
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
            } catch (IndexOutOfBoundsException e) {
                category = " ";
            }

            // 日時
            String date ="2018/"+child.select("span.thumb-s__date").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            String str = title + ","+ category + "," + date + "," + url  ;
            System.out.println( str );
            bw.write( str );
            bw.newLine();
        }
    }

    // ライフカテゴリ
    public static void Kurashi(BufferedWriter bw) throws IOException {
        System.out.println("■ライフ");
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

            //カテゴリ
            String category = null;
            try {
                category = child.select("a").get(1).text();
            } catch (IndexOutOfBoundsException e) {
                category = " ";
            }

            // 日時
            String date ="2018/"+child.select("span.thumb-s__date").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            String str = title + ","+ category + "," + date + "," + url  ;
            System.out.println( str );
            bw.write( str );
            bw.newLine();
        }
    }

    // エンタメカテゴリ
    public static void Entertainment(BufferedWriter bw) throws IOException {
        System.out.println("■エンタメ");
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

            //カテゴリ
            String category = null;
            try {
                category = child.select("a").get(1).text();
            } catch (IndexOutOfBoundsException e) {
                category = " ";
            }

            // 日時
            String date ="2018/"+child.select("span.thumb-s__date").get(0).text();

            //ニュースのURL
            String url = child.child(0).attr("href");
            url = rootUrl + url;

            String str = title + ","+ category + "," + date + "," + url  ;
            System.out.println( str );
            bw.write( str );
            bw.newLine();
        }
    }
}
