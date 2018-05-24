package crawler;

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

public class Question3 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("クロール＆スクレイプ処理を開始しました。");

        makeArticlesCsv(2018, 02);

        System.out.println("クロール＆スクレイプ処理を終了しました。");
    }

    static void makeArticlesCsv(int year, int month) throws InterruptedException, IOException {
        String rootUrl = "";
        if (month <= 9) {
            rootUrl = "http://news.mynavi.jp/list/headline/" + year + "/" + "0" + month + "/";
        }
        if (month >= 10) {
            rootUrl = "http://news.mynavi.jp/list/headline/" + year + "/" + month + "/";
        }
        Map<String, String> titleCategoryMap = new HashMap<String, String>(); // タイトル, カテゴリー
        Map<String, String> titleTimeMap = new HashMap<String, String>(); // タイトル, ニュース日時
        Map<String, String> titleUrlMap = new HashMap<String, String>(); // タイトル, URL

        String rootUrlPerPage = ""; // ページごとのURL

        for (int i = 1; i > 0; i++) {
            if (i == 1) { // 1ページ目以降のURL
                rootUrlPerPage = rootUrl;
            } else { // 2ページ目以降のURL
                rootUrlPerPage = rootUrl + "?page=" + i;
            }
            Document doc = Jsoup.connect(rootUrlPerPage).get();

            try { // 記事リストのページ数を超えたらforループを終了
                Element el = doc.select("body > div.wrapper > " + "div.container.container--top > "
                        + "div.body > main > section.box.box--top.box--line > div.thumb-s").get(0);

                for (Element child : el.children()) {
                    if (child.children().size() < 1) {
                        continue;
                    }
                    for (Element grandchild : child.children()) {
                        //
                        int cnt = 0;
                        String title = "";
                        String categoryName = "";
                        String prTime = "";
                        String perfectTime = "";
                        for (Element g_grandchild : grandchild.children()) {
                            cnt++;
                            if (cnt == 1) {
                                title = grandchild.child(1).text();
                                String url = grandchild.child(1).child(0).attr("href"); // urlのほとんどは不完全なURL
                                if (!url.substring(0, 4).equals("http")) { // urlが不完全なURLの場合
                                    String perfectUrl = "https://news.mynavi.jp" + url; // 完全なURLを作ってあげる
                                    titleUrlMap.put(title, perfectUrl);
                                } else { // urlが完全なURLの場合(urlがhttpから始まる場合)
                                    titleUrlMap.put(title, url);
                                }
                            }
                            if (cnt == 2) {
                                Elements thumbsTxt = grandchild.select("p");
                                // カテゴリー名の取得
                                Elements category = thumbsTxt.select("a");
                                categoryName = category.text();
                                titleCategoryMap.put(title, categoryName);
                                // ニュース日時の取得
                                Elements time = thumbsTxt.select("span");
                                prTime = time.text(); // これには西暦が入っていない&「PR 」という余計な文字が入っていることがある

                                if (prTime.contains("PR ")) { // 「PR 」が入っていればとる
                                    String newsTime = prTime.substring(3, prTime.length());
                                    perfectTime = year + "/" + newsTime;
                                } else {
                                    perfectTime = year + "/" + prTime;
                                }

                                // 完成したニュース日時をマップに格納
                                titleTimeMap.put(title, perfectTime);
                            }
                        }
                    }
                }
                // 1秒待機
                Thread.sleep(1000);
                // カウント
                System.out.println(i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        Path filePath = Paths.get("C:\\TechTraining\\resources\\crlAns3.csv");
        String contents = ""; // 書き込む内容
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            // ヘッダー行を作成
            contents = "件名,カテゴリ,ニュース日時,URL";
            bw.write(contents);
            bw.newLine();

            for (Map.Entry<String, String> entry : titleCategoryMap.entrySet()) {
                String title = entry.getKey();
                String category = titleCategoryMap.get(title);
                String newsTime = titleTimeMap.get(title);
                String url = titleUrlMap.get(title);
                // 記事タイトルの中にカンマが含まれている可能性があるので、タイトルをダブルクオーテーションで囲む
                contents = "\"" + title + "\"" + "," + "\"" + category + "\"" + ","
                        + "\"" + newsTime + "\"" + "," + "\"" + url + "\"";
                bw.write(contents);
                bw.newLine();
            }
        }

    }

}
