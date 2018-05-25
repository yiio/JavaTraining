package jp.co.goalist.crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question3 {

    public static void main(String[] args) {
        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        makeArticlesCsv(2016, 12); // 2016年12月の記事の一覧

        System.out.println("クロール＆スクレイプ処理 を終了しました。");
    }

    private static void makeArticlesCsv(int year, int month) {

        
        String[] number = null;
        String rootUrl = "https://news.mynavi.jp/list/headline/" + year + "/" + month + "/";
        String title = null;
        String url = null;
        String[] genres = null;
        String date = null;
        String output = null;
        List<String> outputList = new ArrayList<>();


        try {


            Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求

            Element last = doc.select("a.paginate__last").get(0);

            String lastUrl = last.attr("href");
            number = lastUrl.split("="); //総ページ数を取得

            

            //1ページ目の記事を取得
            Element first = doc.select("div.thumb-s").get(0);
            for (int x = 1; x < first.children().size() - 1; x++) {


                if (first.child(x).children().size() < 1) {
                    continue;
                }

                title = first.child(x).child(0).child(1).child(0).text();
                url = "https://news.mynavi.jp" + first.child(x).child(0).child(1).child(0).attr("href");
                genres = first.child(x).child(0).child(2).text().split(" ");


                date = year + "/" + first.child(x).child(0).child(2).child(1).text();
                output = title + "," + genres[0] + "," +date +  "," + url; //csvの1行を構成

                outputList.add(output);

                
            }


            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                System.out.println(e);
            }


            int page = Integer.parseInt(number[1]);

            //2ページ目以降の記事を1ページずつ取得
            for(int x = 2; x <= page; x++) {

                String pageUrl = rootUrl + "?page=" + x;
                Document doc2 = Jsoup.connect(pageUrl).get();
                Element el = doc2.select("div.thumb-s").get(0);

                for (int i = 1; i < el.children().size(); i++){

                    if (el.child(i).children().size() < 1) {
                        continue;
                    }

                    title = el.child(i).child(0).child(1).child(0).text();
                    url = "https://news.mynavi.jp" + el.child(i).child(0).child(1).child(0).attr("href");
                    genres = el.child(i).child(0).child(2).text().split(" ");


                    date = year + "/" + el.child(i).child(0).child(2).child(1).text();
                    output = title + "," + genres[0] + "," +date +  "," + url; //csvの1行を構成

                    outputList.add(output);


                }

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    System.out.println(e);
                }

            }


        }catch (IOException e) {
            System.out.println(e);
        }


        Path answerPath = Paths.get("c:/TechTraining/resources/answerQ3.csv"); // 書き込み対象ファイルの場所を指定

        try {

            Files.deleteIfExists(answerPath); // 既に存在してたら削除
            Files.createFile(answerPath); // ファイル作成


            //結果の書き込み
            try(BufferedWriter bw = Files.newBufferedWriter(answerPath)) {

                bw.write("件名,カテゴリ,ニュース日時,URL"); //ヘッダ行
                bw.newLine();

                //1行ごとに結果を書き込む
                for(String line : outputList) {
                    bw.write(line);
                    //System.out.println(line);
                    bw.newLine();

                }

            }

        } catch (IOException e) {
            System.out.println(e);
        }



    }



}
