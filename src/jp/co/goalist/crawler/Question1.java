package jp.co.goalist.crawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Question1 {

    public static void main (String[] args) {
        try {
            sample();
        }catch (IOException e) {
            System.out.println(e);
        }



    }

    public static void sample() throws IOException {
        String rootUrl = "http://news.mynavi.jp/top/headline/"; // 取得先URL
        String title = null;
        String url= null;
        String[] genres = null;
        String article = null;
        Map<String, List<String>> genreMap = new HashMap<>();
        List<String> genreList = null;

        Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求
        Element el = doc.select("div.thumb-s").get(0);


        for (int x = 1; x < el.children().size() - 1; x++) { //child(0)は日付なのでとばす


            if (el.child(x).children().size() < 1) {
                continue;
            }

            title = el.child(x).child(0).child(1).child(0).text();
            url ="https://news.mynavi.jp" +  el.child(x).child(0).child(1).child(0).attr("href");
            genres = el.child(x).child(0).child(2).text().split(" ");

            article = title +"\n(" + url + ")\n";

            //記事をジャンルごとのListに追加
            if(!genreMap.containsKey(genres[0])) {

                genreList = new ArrayList<>(); //そのジャンルがgenreMapに含まれていなければ、そのジャンルのリストを新しく作成

                genreList.add(article); //記事をListに追加
                genreMap.put(genres[0], genreList); //ジャンルと作ったリストをgenreMapに格納

            }else {
                genreMap.get(genres[0]).add(article); //すでにそのジャンルがgenreMapに含まれている場合、そのジャンルのListに記事を追加

            }


        }

        //ジャンルごとに記事を出力
        for(String genre : genreMap.keySet()) {

            System.out.println("■" + genre);

            for(int x = 0; x < genreMap.get(genre).size(); x++) {
                System.out.println(genreMap.get(genre).get(x));
            }


        }



    }

}
