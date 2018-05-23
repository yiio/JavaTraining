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

public class Question2 {
    public static void main(String[] args) {

        try {
            sample();
        }catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void sample() throws IOException {


        String rootUrl = "http://news.mynavi.jp/top/headline/"; // クロール先のurl

        Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求
        Element el = doc.select("div.thumb-s").get(0);

        String title = null;
        String url= null;
        String[] genres = null;
        String date = null;
        String output = null;
        List<String> outputList = new ArrayList<>();

        for (int x = 1; x < el.children().size() - 1; x++) {


            if (el.child(x).children().size() < 1) {
                continue;
            }

            title = el.child(x).child(0).child(1).child(0).text();
            url = "https://news.mynavi.jp" + el.child(x).child(0).child(1).child(0).attr("href");
            genres = el.child(x).child(0).child(2).text().split(" ");


            date = el.child(x).child(0).child(2).child(1).text();
            output = title + "," + genres[0] + "," +date +  "," + url; //csvの1行を構成

            outputList.add(output);


        }


        Path answerPath = Paths.get("c:/TechTraining/resources/answerQ1.csv"); // 書き込み対象ファイルの場所を指定

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
