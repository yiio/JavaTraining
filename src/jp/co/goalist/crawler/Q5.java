package jp.co.goalist.crawler;

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
import org.jsoup.select.Elements;

public class Q5 {

    public static void main(String[] args) {
        try {
            System.out.println("クロール＆スクレイプ処理 を開始しました。");
            List<String> imgUrlList = getImgUrlList("business", "technology", "semiconductor", 2016, 12);
            // カテゴリ,サブカテゴリ,年,月指定してメソッド切り分け
            // <サムネイルのURL>リスト

            download(imgUrlList, "2016" , "12");

            System.out.println("クロール＆スクレイプ処理 を終了しました。");
        } catch (IOException e) {
            
            e.printStackTrace();
        }

    }

    private static List<String> getImgUrlList(String category, String mainCategory, String subCategory, int year,
            int month) throws IOException {
        List<String> imgUrlList = new ArrayList<String>();
        try {

            String rootUrl = "https://news.mynavi.jp/list/headline/" + category + "/" + mainCategory + "/" + subCategory
                    + "/" + year + "/" + month + "/";

            for (int i = 1; i > 0; i++) {// ページ数の数だけ
                String rootUrlpage = rootUrl + "?page=" + (i);

                Document doc = Jsoup.connect(rootUrlpage).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Element lastPageElem = doc.getElementsByClass("paginate__last").first();
                if (lastPageElem == null) {// 最後のページへのところにURLがなかったらbreak
                    break;

                }

                Elements elems = doc.getElementsByClass("thumb-s__img-wrap");
                // 新着記事のクラスを指定
                int cnt = 0;
                for (Element images : elems) {
                    cnt++;
                    if (cnt == 1) {
                        continue;
                    }

                    String imageUrl = "https://news.mynavi.jp" + images.child(0).attr("src");
                    imgUrlList.add(imageUrl);
                }

            }

        } catch (IOException e) {
            throw e;
        }

        return imgUrlList;
    }

    private static void download(List<String> imgUrlList, String year, String month) throws IOException {
        // ダウンロードする画像のURL

        // リストを回してダウンロード
        for (int i = 0; i < imgUrlList.size(); ++i) {
            String imgUrl = imgUrlList.get(i);
            String fileName = year + month + "-" +i + ".jpg"; 
            //20161229-siemens_mentor 

            // 画像のローカル保存先を用意する
            String dir = "c:\\TechTraining\\resources\\img";
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
