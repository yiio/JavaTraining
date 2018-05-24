package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Q3 {
    public static void main(String[] args) {
        try {
            System.out.println("クロール＆スクレイプ処理 を開始しました。");

            makeArticlesCsv(2016, 12); // 2016年12月の記事の一覧

            System.out.println("クロール＆スクレイプ処理 を終了しました。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeArticlesCsv(int year, int month) throws IOException {

        try {
            String rootUrl = "https://news.mynavi.jp/list/headline/" + year + "/" + month + "/"; // クロール先のurl
            Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
            Element el = doc.select(" section.box.box--top.box--line > div.thumb-s").get(0);

            // // (件名、＜いろんな情報＞)のマップを作製
            Map<String, List<String>> articleMap = new LinkedHashMap<String, List<String>>();

            int cnt = 0;
            for (Element articleChild : el.children()) {
                cnt++;
                List<String> infoList = new ArrayList<String>();
                if (cnt == 1) {
                    infoList.add("件名");
                    infoList.add("カテゴリ");
                    infoList.add("ニュース日時");
                    infoList.add("URL");
                    articleMap.put("件名", infoList);
                    continue;
                }

                String title = articleChild.child(0).child(1).text();
                String category = articleChild.child(0).child(2).child(0).text();
                String date = articleChild.child(0).child(2).child(1).text();
                String articleUrl = "https://news.mynavi.jp" + articleChild.child(0).child(1).child(0).attr("href");

                infoList.add(title);
                infoList.add(category);
                infoList.add(date);
                infoList.add(articleUrl);
                articleMap.put(title, infoList);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }

            Path answerPath = Paths.get("C:\\TechTraining\\resources\\crawl_3.csv"); // 書き込み対象ファイルの場所を指定

            try (BufferedWriter bw = Files.newBufferedWriter(answerPath)) {// １行ごとに「書き込む」

                for (Map.Entry<String, List<String>> entry : articleMap.entrySet()) {

                    List<String> newsInfo = entry.getValue();// もろもろ情報
                    String title = newsInfo.get(0);
                    String category = newsInfo.get(1);
                    String date = newsInfo.get(2);
                    String url = newsInfo.get(3);
                    bw.write(title + "," + category + "," + date + "," + url);
                    bw.newLine();
                }
                bw.close();

            } catch (IOException e) {
                throw e;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
