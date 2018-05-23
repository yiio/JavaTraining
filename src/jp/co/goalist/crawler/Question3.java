package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question3 {
    public static void main(String[] args) {
        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        makeArticlesCsv(2016, 12); // 2016年12月の記事の一覧

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
            bw.write("件名,カテゴリ,ニュース日時,URL");
            bw.newLine();

            String rootUrl = "http://news.mynavi.jp";
            String subUrl = rootUrl + "/list/headline/" + year + "/" + month; // 指定した年月のページ

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
                try {
                    String headlineUrl = subUrl  + "/?page=" + i; // 何ページ目かを指定
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
                        } catch (IndexOutOfBoundsException e) {
                            category = " ";
                        }

                        // 日時
                        String date =year + "/"+child.select("span.thumb-s__date").get(0).text();

                        //ニュースのURL
                        String url = child.select("a").get(0).attr("href");
                        url = rootUrl + url;

                        String str = title + ","+ category + "," + date + "," + url  ;
                        System.out.println(str);
                        bw.write(str);
                        bw.newLine();
                    } try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    };
                } catch(IndexOutOfBoundsException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
