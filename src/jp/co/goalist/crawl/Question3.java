package jp.co.goalist.crawl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question3 {
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
        String url = "http://news.mynavi.jp/top/headline/" + year + "/" + month;
        Document docTop = Jsoup.connect(url).get();
        Element elPageNum = docTop.select(
                "div.container.container--top > div.body > main.main  > section.box.box--top.box--line > div.paginate")
                .get(0);
        String pageLastLink = elPageNum.child(0).child(8).attr("href");
        String pageLastNum = pageLastLink.substring(pageLastLink.indexOf("?page=") + 6);

        // 全部のニュースを取得する
        for (int i = 0; i < Integer.parseInt(pageLastNum); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String rootUrl = "http://news.mynavi.jp/list/headline/" + year + "/" + month + "/?page=" + i;
            System.out.println(rootUrl);
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
                date = year + "/" + date;
                String newDate = date.replace("/PR ", "/");
                System.out.println(newDate);
                // ニュースのリンク
                String link = child.child(0).child(1).child(0).attr("href");

                String news = "\"" + title + "\",\"" + genre + "\",\"" + newDate + "\",https://news.mynavi.jp" + link
                        + "\"";
                newsList.add(news);
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
}