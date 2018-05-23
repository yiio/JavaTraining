package jp.co.goalist.crawler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Q1 {
    public static void main(String[] args) {

        try {
            String rootUrl = "http://news.mynavi.jp/top/headline/"; // クロール先のurl
            Document doc = Jsoup.connect(rootUrl).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
            Element tabs = doc.select(" div.tab > ul").get(0);

            // （タブ、クリック先のurl）のマップをつくる
            Map<String, String> tabUrlMap = new LinkedHashMap<String, String>();

            for (Element tabChild : tabs.children()) {
                String tabNakami = tabChild.text();
                String tabUrl = "https://news.mynavi.jp" + tabChild.child(0).attr("href");
                tabUrlMap.put(tabNakami, tabUrl);
            }

            // for文でクリックした内容のurlを取得→docとして取って回す→要素取り出し→うんぬんを繰り返す
            for (Map.Entry<String, String> entry : tabUrlMap.entrySet()) {
                String tab = entry.getKey();// タブ
                String url = entry.getValue();// URL

                String categoryUrl = url; // クロール先のurl
                Document categoryDoc = Jsoup.connect(categoryUrl).get(); // ページの内容を要求し、その内容をDocument型のdocとして取り回していく
                
             // cssセレクタである程度要素を切り取る
                Element nakami = categoryDoc.select("section.box.box--top.box--line > div.thumb-s").get(0);
                
                System.out.println("■"+tab);
                int cnt = 0;
                
                for (Element nakamiChild : nakami.children()) {
                        cnt++;
                        
                        if (cnt == 1) {
                            continue;
                        }

                        String title = nakamiChild.child(0).child(1).text();

                        String articleUrl = nakamiChild.child(0).child(1).child(0).attr("href"); // li要素の子要素のa要素のhrefというアトリビュートでリンク先のurlが指定されている

                        System.out.println(title + "\n(https://news.mynavi.jp" + articleUrl + ")\n");

                    }
                }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
