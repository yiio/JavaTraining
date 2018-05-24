package jp.co.goalist.crawl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question4 {
    public static void main(String[] args) {

        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        try {
            makeArticlesCsv(2016, 12);
        } catch (IOException e1) {
            e1.printStackTrace();
        } // 2016年12月の記事の一覧

        System.out.println("クロール＆スクレイプ処理 を終了しました。");

    }

    private static void makeArticlesCsv(int year, int month) throws IOException {

        List<String> newsList = new ArrayList<>();

        // ニュースが載っているページ数の取得
        String url = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + month;
        Document docTop = Jsoup.connect(url).get();
        Element elPageNum = docTop.select("div.body > main.main  > section.box.box--top.box--line > div.paginate")
                .get(0);
        String pageLastLink = elPageNum.child(0).select("a.paginate__last").attr("href");
        String pageLastNum = pageLastLink.substring(pageLastLink.indexOf("?page=") + 6);

        // 明日はここから始める！！！！！！
        Connection con = Jsoup.connect(url);
        Response res = con.response();
        int status = res.statusCode();
        System.out.println(status);

        // カテゴリーの取得
        Element elCategory = docTop.select("div.wrapper > ul.breadcrumb").get(0);
        String category = elCategory.child(2).text();

        // 全部のニュースを取得する
        for (int i = 1; i < Integer.parseInt(pageLastNum) + 1; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/"
                    + month + "/?page=" + i;
            Document doc = Jsoup.connect(rootUrl).get();
            Element el = doc.select("div.body > main.main > section.box.box--top.box--line").get(0);

            for (Element child : el.children()) {
                if (child.children().size() < 1) {
                    continue;
                }

                for (Element child2 : child.children()) {
                    if (child2.children().size() < 1) {
                        continue;
                    }
                    if (child2.hasClass("thumb-s__update") || child2.hasClass("paginate__inner")) {// いらない要素を削除
                        continue;
                    }

                    // ニュースのタイトル
                    String title = child2.child(0).select("h3.thumb-s__tit").text();
                    // ニュースの日付
                    String date = child2.child(0).select("p.thumb-s__txt").select("span").text();
                    date = year + "/" + date;
                    String newDate = date.replace("/PR ", "/");// PRの文字を省く
                    // ニュースのリンク
                    String link = child2.child(0).select("h3.thumb-s__tit > a").attr("href");

                    // ニュースの本文を取得するためのあれこれ
                    String newsContents = "";
                    int pageNum = 1;
                    Document docNews = Jsoup.connect("https://news.mynavi.jp" + link).get();
                    // ニュースページが複数ある場合のページ数の取得
                    Element elNewsBody = docNews.select("main.main").get(0);
                    for (Element childMain : elNewsBody.children()) {
                        if (childMain.attr("class").equals("gtm")) {
                            Element elGetNewsPage = childMain.select("div.paginate").get(0);
                            String newsPage = elGetNewsPage.child(0).select("a.paginate__last").attr("href");
                            pageNum = Integer.parseInt(newsPage.substring(newsPage.length() - 2).replace("/", ""));
                        }
                    }

                    // ニュース本文の取得
                    for (int j = 1; j < pageNum + 1; j++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Document docNewsContents = Jsoup.connect("https://news.mynavi.jp" + link + j).get();
                        Element elNews = docNewsContents.select("main.main > article.article").get(0);
                        for (Element para : elNews.children().select("p")) {
                            newsContents += para.text();
                            newsContents.replace(",", "、");// 半角カンマを全角カンマに変換
                        }
                    }

                    System.out.println(newsContents);

                    // CSVに書き出す内容
                    String news = "\"" + title + "\",\"" + category + "\",\"" + newDate + "\",https://news.mynavi.jp"
                            + link + "\",\"" + newsContents + "\"";
                    newsList.add(news);
                }
            }
        }
        Collections.reverse(newsList);// ニュースを日付順にする

        // csvファイルの出力
        Path filePath = Paths.get("C:\\TechTraining\\resources\\newsList.csv");
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write("件名,カテゴリ,ニュース日時,URL,本文");
            bw.newLine();
            for (String news : newsList) {
                bw.write(news);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}