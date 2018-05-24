package jp.co.goalist.crawl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question5 {
    public static void main(String[] args) {

        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        try {
            makeArticlesCsv("kurashi", "life", 2016, 12);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("クロール＆スクレイプ処理 を終了しました。");

    }

    private static void makeArticlesCsv(String category, String subCategory, int year, int month) throws IOException {

        List<String> newsList = new ArrayList<>();

        // ニュースが載っているページ数の取得
        String url = "https://news.mynavi.jp/list/headline/" + category + "/" + subCategory + "/" + year + "/" + month;
        Document docTop = Jsoup.connect(url).get();
        Element elPageNum = docTop.select("div.body > main.main  > section.box.box--top.box--line > div.paginate")
                .get(0);
        String pageLastLink = elPageNum.child(0).select("a.paginate__last").attr("href");
        String pageLastNum = pageLastLink.substring(pageLastLink.indexOf("?page=") + 6);

        // カテゴリーの取得
        Element elCategory = docTop.select("div.wrapper > ul.breadcrumb").get(0);
        String categoryJ = elCategory.child(2).text();

        // 全部のニュースを取得する
        for (int i = 1; i < Integer.parseInt(pageLastNum) + 1; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String rootUrl = "https://news.mynavi.jp/list/headline/" + category + "/" + subCategory + "/" + year + "/"
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

                    // 画像を取り出す
                    String imgUrl = "https://news.mynavi.jp" + child2.select("img").attr("src");
                    String fileName = "";
                    if (child2.select("img").attr("alt").equals("No image")) { // 画像がないときは取得しない
                        continue;
                    } else if (child2.select("img").attr("alt").equals("Top")) {
                        fileName = imgUrl.substring(imgUrl.indexOf("article/") + 8, imgUrl.indexOf("/index_")) + ".jpg";
                        if (fileName.contains("/")) {
                            fileName = imgUrl.substring(imgUrl.indexOf("kikaku/") + 7, imgUrl.indexOf("/index_"))
                                    + ".jpg";
                        }

                    } else if (child2.select("img").attr("alt").equals("index.iapp")) {
                        fileName = imgUrl.substring(imgUrl.indexOf("file_name/") + 10, imgUrl.indexOf("/index."))
                                + ".jpg";
                    } else {
                        continue;
                    }

                    System.out.println(fileName);

                    String dir = "C:\\TechTraining\\img";
                    Path dirPath = Paths.get(dir);
                    if (Files.notExists(dirPath)) { // ディレクトリが存在しなければ作成
                        Files.createDirectories(dirPath);
                    }
                    Path localFilePath = Paths.get(dir, fileName);
                    Files.deleteIfExists(localFilePath);
                    Files.createFile(localFilePath); // 保存先のファイル作成

                    URL urlImage = new URL(imgUrl); // URLクラスのインスタンスを生成

                    try (InputStream is = urlImage.openStream();
                            FileOutputStream fos = new FileOutputStream(localFilePath.toFile(), false);) {
                        // 取得した画像データをファイルに書き出す
                        int b;
                        while ((b = is.read()) != -1) {
                            fos.write(b);
                        }
                    }
                }
            }
        }
    }
}