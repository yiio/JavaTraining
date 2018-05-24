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
import org.jsoup.select.Elements;

public class Q4 {
    public static void main(String[] args) {
        try {
            System.out.println("クロール＆スクレイプ処理 を開始しました。");
            makeArticlesCsv(2016, 12); // 2016年12月の記事を調べる

            System.out.println("クロール＆スクレイプ処理 を終了しました。");
        } catch (IOException e) {
            System.out.println("取得できませんでした");

        }

    }

    private static void makeArticlesCsv(int year, int month) throws IOException {

        try {
            String rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/"
                    + month + "/"; // クロール先のurl

            // (URL、＜件名、日時、URL＞)のマップ
            Map<String, List<String>> articleMap = new LinkedHashMap<String, List<String>>();

            for (int i = 1; i>0;i++) {//ページ数の数だけ
                String rootUrlpage = rootUrl +"?page="+(i);
            try {
                Document doc = Jsoup.connect(rootUrlpage).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
                Element lastPageElem = doc.getElementsByClass("paginate__last").first();
                // String lastPage = lastPageElem.attr("href");
                if (lastPageElem == null) {// 最後のページへのところにURLがなかったらbreak
                    break;

                }

                Elements elems = doc.getElementsByClass("thumb-s__item");
                // (URL、＜いろんな情報(件名、日時、URL)＞)のマップつくる
                int cnt = 0;
                for (Element articleChild : elems) {
                    cnt++;
                    List<String> infoList = new ArrayList<String>();
                    if (cnt == 1) {
                        infoList.add("件名");
                        infoList.add("カテゴリ");
                        infoList.add("ニュース日時");
                        infoList.add("URL");
                        articleMap.put("URL", infoList);
                        continue;
                    }

                    String title = articleChild.child(0).child(1).text();
                    String category = "テクノロジー";
                    String date = articleChild.child(0).child(2).text();
                    String articleUrl = "https://news.mynavi.jp" + articleChild.child(0).child(1).child(0).attr("href");

                    infoList.add(title);
                    infoList.add(category);
                    infoList.add(date);
                    infoList.add(articleUrl);
                    articleMap.put(articleUrl, infoList);


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                throw e;
            }
            }





            // (URL, 本文)マップ
            Map<String, String> honbunMap = new LinkedHashMap<String, String>();


            // リストを回しながら各URLにアクセスして(URL, ,本文)マップつくる

            int cnt = 0;
            for (Map.Entry<String, List<String>> entry : articleMap.entrySet()) {
                cnt++;
                String url = entry.getKey();

                if (cnt == 1) {
                    String honbun = "本文";
                    honbunMap.put(url, honbun);
                    continue;
                }

                try {
                    Document nakamiDoc = Jsoup.connect(url).get();
                    Elements honbunElem = nakamiDoc.select("body > div.wrapper > div.container > div.body > main > article[class= article]");

                    String genbun = honbunElem.text();
                    
                    String lineSepa = System.getProperty("line.separator");

                    String henkou = genbun.replaceAll(lineSepa , " ");
                    String honbun = henkou.replaceAll(",", "，");

                    honbunMap.put(url, honbun);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    honbunMap.put(url, "取得できませんでした");
                }
            }

            // カキコ
            Path answerPath = Paths.get("C:\\TechTraining\\resources\\crawl_4.csv"); // 書き込み対象ファイルの場所を指定

            try (BufferedWriter br = Files.newBufferedWriter(answerPath)) {// １行ごとに「書き込む」

                for (Map.Entry<String, List<String>> entry : articleMap.entrySet()) {

                    List<String> newsInfo = entry.getValue();// もろもろ情報
                    String title = newsInfo.get(0);
                    String date = newsInfo.get(1);
                    String category = newsInfo.get(2);
                    String url = entry.getKey();
                    String honbun = honbunMap.get(url);

                    // 件名,カテゴリ,ニュース日時,URL,本文
                    br.write(title + "," + category + "," + date + "," + url + "," + honbun);
                    br.newLine();
                }
                br.close();

            } catch (IOException e) {
                throw e;
            }

        } catch (IOException e) {
           throw e;
        }
    }


}
