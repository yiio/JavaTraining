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
import org.jsoup.select.Elements;

public class Question4 {

    public static void main(String[] args) {
        System.out.println("クロール＆スクレイプ処理 を開始しました。");

        makeArticlesCsv(2016,4); // 2016年4月の記事の一覧

        System.out.println("クロール＆スクレイプ処理 を終了しました。");
    }

    private static void makeArticlesCsv(int year, int month) {

        String[] number = null;
        //monthは1桁なら01という形に直す
        String rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" +String.format("%02d", month) + "/";
        String title = null;
        String url = null;
        String date = null;
        String output = null;
        List<String> outputList = new ArrayList<>();

        String content = null;

        try {


            Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求

            Element last = doc.select("a.paginate__last").get(0);

            String lastUrl = last.attr("href");
            number = lastUrl.split("="); //総ページ数を取得
            List<String> textList = null;


            int page = Integer.parseInt(number[1]);

            //2ページ目以降の記事を1ページずつ取得
            for(int y = 1; y <= page; y++) {

                String pageUrl = rootUrl + "?page=" + y;
                Document doc2 = Jsoup.connect(pageUrl).get();
                Elements el = doc2.select("div.thumb-s");


                for (int x = 0; x < el.size(); x++) {



                    for(int i = 1; i < el.get(x).children().size(); i++) {


                        Element thumb = el.get(x).child(i).child(0).child(1).child(0);


                        title = thumb.text();
                        url = "https://news.mynavi.jp" + thumb.attr("href");
                        if(el.get(x).child(i).child(0).child(2).children().size() >= 2) {
                            date = year + "/" +el.get(x).child(i).child(0).child(2).child(1).text();
                        }else {
                            date = year + "/" +el.get(x).child(i).child(0).child(2).child(0).text();
                        }

                        textList = new ArrayList<>();
                        Document art = Jsoup.connect(url).get();
                        Elements text = art.select("div.article-body > p");

                        if(text.isEmpty()) {

                            String str = "取得できませんでした";
                            content = str;

                        }else {


                            for (Element el2 : text) { //各ページの本文を取得


                                String str = el2.text().replace(",", "、");
                                textList.add(str);

                            }




                            Element main = doc2.select("main.main").get(0);

                            if (main.select("div.gtm").toString().isEmpty()) {


                              //2ページ目がない場合、何もしない


                            }else { //2ページ目以降がある場合

                                Element lastPage = doc2.select("a.paginate__last").get(0);

                                String[] lastPageNumber = ("https://news.mynavi.jp" + lastPage.attr("href")).split("/");
                                int pageNumber = Integer.parseInt(lastPageNumber[5]);


                                for (int j = 2; x < Integer.parseInt(lastPageNumber[5]); x++) { //記事の2ページ目以降の本文を取得

                                    Document doc3 = Jsoup.connect(url + j).get();
                                    Elements sentences = doc3.select("div.article-body > p");

                                    for (Element sentence : sentences) {

                                        String str = sentence.text().replace(",", "、");
                                        textList.add(str);

                                    }


                                }

                            }





                               content = String.join(" ", textList);

                           //System.out.println(content);


                        }



                        output = title  + "," + date +  "," + url + "," + content; //csvの1行を構成
                        outputList.add(output);

                    }


                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        System.out.println(e);
                    }



            }


            }






            Path answerPath = Paths.get("c:/TechTraining/resources/answerQ4.csv"); // 書き込み対象ファイルの場所を指定

            try {

                Files.deleteIfExists(answerPath); // 既に存在してたら削除
                Files.createFile(answerPath); // ファイル作成


                //結果の書き込み
                try(BufferedWriter bw = Files.newBufferedWriter(answerPath)) {

                    bw.write("件名,ニュース日時,URL,本文"); //ヘッダ行
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








        }catch (IOException e) {
            System.out.println(e);
        }


    }


}
