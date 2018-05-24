package jp.co.goalist.crawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question5 {
    public static void main(String[] args) throws IOException {
        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        makeImg(2017,8); // 2017年5月の記事の一覧

        System.out.println("クロール＆スクレイプ処理 を終了しました。");
    }

    public static void makeImg(int year, int mon) throws IOException {
        String month = null;
        if(mon < 10) {
            month = "0" + String.valueOf(mon);
        } else {
            month = String.valueOf(mon);
        }

        String rootUrl = "https://news.mynavi.jp";
        String subUrl = rootUrl + "/list/headline/entertainment/entertainment/geinou/" + year + "/" + month;

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

                    // イメージ画像のURL
                    String imgUrl =child2.select("img").get(0).attr("src");
                    String fileName = imgUrl.substring(imgUrl.lastIndexOf("article/") + 8 , 22 );
                    fileName = fileName + ".jpg";
                    imgUrl = rootUrl + imgUrl;
                    System.out.println(imgUrl);
                    System.out.println(fileName);

                    // 画像のローカル保存先を用意する
                    String dir = "C:/TechTraining/img";
                    Path dirPath = Paths.get(dir);
                    if (Files.notExists(dirPath)) { // ディレクトリが存在しなければ作成
                        Files.createDirectories(dirPath);
                    }
                    Path localFilePath = Paths.get(dir, fileName);
                    Files.deleteIfExists(localFilePath);
                    Files.createFile(localFilePath); // 保存先のファイル作成

                    URL url = new URL(imgUrl); // URLクラスのインスタンスを生成

                    try (InputStream is = url.openStream();
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
