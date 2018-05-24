package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Question1 {

    public static void main(String[] args) throws IOException {
        String rootUrl = "http://news.mynavi.jp/top/headline/";
        
        Map<String, String> titleUrlMap = new HashMap<String, String>();
        Map<String, String> titleGenreMap = new HashMap<String, String>();
        ArrayList<String> genreList = new ArrayList<String>();
        
        
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("div.thumb-s").get(0);
        
        // child��thumb-s__item�Ȃ�
        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }
            
            // grandchild��thumb-s__link js-link�Ȃ�
            for (Element grandchild : child.children()) {
                
                // g_grandchild��thumb-s__thumb��thumb-s__tit�Ȃ�
                int cnt = 0;
                String title = "";
                String genreName = "";

                for(Element g_grandchild : grandchild.children()) {
                    cnt++;
                    if (cnt == 1) {
                        title = grandchild.child(1).text();
                        String url = grandchild.child(1).child(0).attr("href");
                        String perfectUrl = "https://news.mynavi.jp" + url;
                        titleUrlMap.put(title, perfectUrl);
                    }
                    if (cnt == 2) {
                        Elements a = grandchild.select("p");
                        Elements genre = a.select("a");
                        genreName = genre.text();
                        titleGenreMap.put(title, genreName);
                        if (!genreList.contains(genreName)) {
                            genreList.add(genreName);
                        }
                    }
                }
            }
        }
        
        int numberGenre = genreList.size();
        // �W���������ƂɃ^�C�g����URL���o�͂��Ă���
        for (int i = 0; i < numberGenre; i++) {
            System.out.println("��" + genreList.get(i));
            
            for (Map.Entry<String, String> entry : titleGenreMap.entrySet()) {
                if (entry.getValue().equals(genreList.get(i))) {
                    System.out.println(entry.getKey());
                    System.out.println("(" + titleUrlMap.get(entry.getKey()) + ")");
                }
            }
            System.out.println(""); // 1�s�󂯂�
        }
    }

}