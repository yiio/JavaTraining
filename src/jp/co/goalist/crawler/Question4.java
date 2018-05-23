package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question4 {
    public static void main(String[] args) {
        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        makeArticlesCsv(2017,5); // 2017年5月の記事の一覧

        System.out.println("クロール＆スクレイプ処理 を終了しました。");
    }
    private static void makeArticlesCsv(int year, int mon) {
        String month = null;
        if(mon < 10) {
            month = "0" + String.valueOf(mon);
        }else {
            month =String.valueOf(mon);
        }

        Path answer = Paths.get("C:/TechTraining/resources/answer.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(answer)) {
            bw.write("件名,カテゴリ,ニュース日時,URL,本文");
            bw.newLine();

            String rootUrl = "http://news.mynavi.jp";
            String subUrl = rootUrl + "/list/headline/business/technology/semiconductor/" + year + "/" + month + "/";

            // 最後のページを調べます
            String lastPageNum = null;
            Document docTop = Jsoup.connect(subUrl).get();
            try {
                Element elPageNum = docTop.select("a.paginate__last").get(0);
                String lastPageLink = elPageNum.attr("href");
                lastPageNum = lastPageLink.substring(lastPageLink.indexOf("?page=") + 6);
            } catch (IndexOutOfBoundsException e) {
                lastPageNum = "1";
            }

            for (int i = 1; i < Integer.parseInt(lastPageNum) +1; i++) {
                String headlineUrl = subUrl  + "/?page=" + i;
                Document doc = Jsoup.connect(headlineUrl).get();
                Element el = doc.select("section").get(0);

                for (Element child1 : el.children()) {
                    for(Element child2 : child1.select("div.thumb-s")) {
                        if (child2.children().size() < 1) {
                            continue;
                        }
                        //ニュースのタイトル
                        String title = child2.select("a").get(0).text();

                        //カテゴリ
                        String category = null;
                        try {
                            category = child2.select("a").get(1).text();
                        } catch (IndexOutOfBoundsException e) {
                            category = " ";
                        }

                        // 日時
                        String date =year + "/"+child2.select("span.thumb-s__date").get(0).text();

                        //ニュースのURL
                        String url = child2.select("a").get(0).attr("href");
                        url = rootUrl + url;

                        // 本文
                        Document article = Jsoup.connect(url).get();
                        el = article.select("div.article-body").get(0);
                        String text = el.text();

                        String str = title + ","+ category + "," + date + "," + url + "," + text ;
                        System.out.println(str);
                        bw.write(str);
                        bw.newLine();
                    }
                } try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
