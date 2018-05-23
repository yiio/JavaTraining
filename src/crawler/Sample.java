package crawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Sample {

    public static void main(String[] args) throws IOException {
        String rootUrl = "http://news.mynavi.jp/";

        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("#js-tab0 > div.tab__list > ul").get(0);

        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }

            // �j���[�X�̃^�C�g��
            String title = child.text();

            // �j���[�X��URL
            String url = child.child(0).attr("href");
            url = rootUrl + url;
            
            System.out.println(title + "\n(" + url + ")\n");
        }
    }

}