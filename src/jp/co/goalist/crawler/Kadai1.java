package jp.co.goalist.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Kadai1 {
    // throwで例外が投げられるとJVSが感知して処理してくれる？
    public static void main(String[] args) throws IOException {
        String rootUrl = "https://news.mynavi.jp/list/headline//";// クロール先のurl

        Document doc = Jsoup.connect(rootUrl).get();// そのURLのサイトのHTMLを取る！
        Element el = doc.select(
                "body > div.wrapper > div.container.container--top > div.body > main > section.box.box--top.box--line > div.thumb-s")
                .get(0);
        // cssセレクタとは？-A.bodyがセレクタで、どの部分に対して適用するかを決めるもの
        // HTML情報から指定のタグ要素の情報を検索する。
        Map<String, String> genreKijiMap = new HashMap<>();

        for (Element child : el.children()) {
            if (child.children().size() < 1) {// ここでは何を？→子要素が一つ(2つ？)以下のものは省く作業
                continue;
            }

            String title = child.select("a").get(0).text();// li(親)要素内で平文の内容がタイトル文字列になっている
            String genre = child.select("a").get(1).text();

            String url = child.child(0).child(1).child(0).attr("href");// li要素の子要素(.children)のa要素(attr)のhrefというアトリビュート(？)

            url = rootUrl + url;
            title+="\n("+url+")";
            if (genreKijiMap.containsKey(genre)) {

                String sumTitle = genreKijiMap.get(genre) + "\n" + title;
                
                genreKijiMap.put(genre, sumTitle);
            } else {
                genreKijiMap.put(genre, title);
            }
        }

        // 結果の出力
        for (Map.Entry<String, String> entry : genreKijiMap.entrySet()) {
            System.out.println(("■"+entry.getKey() )+ "\n" + entry.getValue() + "\n");// \n(とは？
        }
    }
}
// elementはHTML文章の要素を表す。
// スクレイピング手順
// 1,HTML情報をとる 2,指定タグ要素の情報を検索 3,テキスト、属性値を引き出す
// liはHTMLの親要素。子はul