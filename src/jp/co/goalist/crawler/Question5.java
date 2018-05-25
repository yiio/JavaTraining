package jp.co.goalist.crawler;

import java.io.FileOutputStream;
import java.io.IOException;
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



public class Question5 {



    public static void main(String[] args) {

        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        try {

            Q5("entertainment", "entertainment", 2016, 11); //(カテゴリー、サブカテゴリー、年、月)

        }catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("クロール＆スクレイプ処理 を終了しました。");

    }





    private static void Q5(String category, String subCategory, int year, int month) throws IOException {

        List<String> imgList = new ArrayList<>();
        String rootUrl = "https://news.mynavi.jp/list/headline/" + category + "/" + subCategory + "/" + year + "/" + String.format("%02d", month) + "/";
        Document doc = Jsoup.connect(rootUrl).get();


        //総ページ数を取得
        Element last = doc.select("a.paginate__last").get(0);
        String[] lastUrl = last.attr("href").split("=");
        int page = Integer.parseInt(lastUrl[1]); //総ページ数を求める


        for (int x = 1; x <= page; x ++) { //1ページずつ取得

            String pageUrl = rootUrl + "?page=" + x;
            Document doc2 = Jsoup.connect(pageUrl).get();
            Elements el = doc2.select("div.thumb-s__thumb > div.thumb-s__img-wrap > img.thumb-s__img"); //div.thumb-sは日付ごと
            String elUrl = null;


            for(Element img : el) { //各記事の画像

                String src = img.attr("src");

                if(src.contains("http")) { //URLにhttps://news.mynavi.jpが含まれている場合

                    elUrl = src;

                }else {

                    elUrl = "https://news.mynavi.jp" + src;

                }

                if(elUrl.contains("/top")) {
                    elUrl = elUrl.substring(0, elUrl.lastIndexOf("/"));
                }

                imgList.add(elUrl);
                System.out.println(elUrl);

            }

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                System.out.println(e);
            }


        }





     // 画像のローカル保存先を用意する
        String dir = "c:\\TechTraining\\img";
        Path dirPath = Paths.get(dir);




        if (Files.notExists(dirPath)) { // ディレクトリが存在しなければ作成
            Files.createDirectories(dirPath);
        }


        for (String img : imgList) {  //画像を1つずつ保存

            String[] imgName = img.split("/");
            String fileName = subCategory + "_" + imgName[4] + ".jpg"; //ファイル名


            Path localFilePath = Paths.get(dir, fileName);
            Files.deleteIfExists(localFilePath);
            Files.createFile(localFilePath); // 保存先のファイル作成

            URL url = new URL(img); // URLクラスのインスタンスを生成

            try (java.io.InputStream is = url.openStream();
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
