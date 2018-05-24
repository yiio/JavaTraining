package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Question4 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("クロール＆スクレイプ処理を開始しました。");

        makeArticlesCsv(2018, 1);

        System.out.println("クロール＆スクレイプ処理を終了しました。");
    }

    static void makeArticlesCsv(int year, int month) throws InterruptedException, IOException {
        String rootUrl = "";
        if (month <= 9) { // monthが1桁の場合
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + "0"
                    + month + "/";
        }
        if (month >= 10) { // monthが2桁の場合
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + month
                    + "/";
        }
        // Map<String, String> titleCategoryMap = new HashMap<String, String>(); //
        // タイトル, カテゴリー とりあえず保留。余力があればカテゴリ名を自動で取得できるようにする
        Map<String, String> titleTimeMap = new HashMap<String, String>(); // タイトル, ニュース日時
        Map<String, String> titleUrlMap = new HashMap<String, String>(); // タイトル, URL

        String rootUrlPerPage = ""; // ページごとのURL

        for (int i = 1; i < 4; i++) {
            if (i == 1) { // 1ページ目以降のURL
                rootUrlPerPage = rootUrl;
            } else { // 2ページ目以降のURL
                rootUrlPerPage = rootUrl + "?page=" + i;
            }
            Document doc = Jsoup.connect(rootUrlPerPage).get();

            try { // 記事リストのページ数を超えたらforループを終了
                Elements els0 = doc.getElementsByClass("thumb-s__link js-link");
                for (Element el0 : els0) {
                    int cnt = 0;
                    String title = "";
                    String yearDateTime = "";
                    for (Element child : el0.children()) {
                        cnt++;
                        if (cnt == 1) { // タイトルとURLを取得
                            title = el0.child(1).text(); // タイトルを取得
                            // URLを取得
                            String url = el0.child(1).child(0).attr("href");
                            String perfectUrl = "https://news.mynavi.jp" + url;
                            titleUrlMap.put(title, perfectUrl);
                        }
                        if (cnt == 2) { // 日時を取得
                            String dateTime = el0.child(2).text();
                            yearDateTime = year + "/" + dateTime;
                            titleTimeMap.put(title, yearDateTime);
                        }
                    }
                }
                // 1秒待機
                Thread.sleep(1000);
                // 途中経過をカウントしてるだけです
                System.out.println(i);

            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        // 本文を格納するマップを作る
        Map<String, String> titleTextMap = new HashMap<String, String>();
        // 記事ごとに本文を取得
        for (Map.Entry<String, String> entry : titleUrlMap.entrySet()) {
            String title = entry.getKey();
            String textUrl = entry.getValue();
            Document doc = Jsoup.connect(textUrl).get();

            String articleBody = ""; // 本文を入れる変数
            Elements paragraphs = doc.getElementsByClass("article-body");
            for (Element paragraph : paragraphs) { // 本文の段落ごとに取得していき、articleBodyに足していく
                articleBody += paragraph.text();
            }
            // 半角カンマ→全角カンマ、改行→半角スペース　の処理を行う
            articleBody = articleBody.replaceAll(",", "，"); // カンマ置き換え
            articleBody = articleBody.replaceAll("\r\n", " "); // 改行を半角スペースへ置き換え
            titleTextMap.put(title, articleBody);
            Thread.sleep(1000);
        }

        Path filePath = Paths.get("C:\\TechTraining\\resources\\crlAns4.csv");
        String contents = ""; // 書き込む内容
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            // ヘッダー行を作成
            contents = "件名,カテゴリ,ニュース日時,URL,本文";
            bw.write(contents);
            bw.newLine();

            for (Map.Entry<String, String> entry : titleTimeMap.entrySet()) {
                String title = entry.getKey();
                String category = "半導体デバイス";
                String newsTime = entry.getValue();
                String url = titleUrlMap.get(title);
                String text = titleTextMap.get(title);
                if (text.equals("")) { // 本文を取得できなかった場合
                    text = "取得できませんでした";
                }
                // 記事タイトルの中にカンマが含まれている可能性があるので、タイトルをダブルクオーテーションで囲む
                contents = "\"" + title + "\"" + "," + "\"" + category + "\"" + "," + "\"" + newsTime + "\"" + ","
                        + "\"" + url + "\"" + "," + "\"" + text + "\"";
                bw.write(contents);
                bw.newLine();
            }

        }

    }

}
