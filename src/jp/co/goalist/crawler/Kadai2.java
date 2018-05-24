package jp.co.goalist.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Kadai2 {
    public static void main(String[] args) throws IOException {
        String rootUrl= "https://news.mynavi.jp/list/headline/ ";
        Document doc =Jsoup.connect(rootUrl).get();
        Element el = doc.select(" div.thumb-s").get(0);//dv.thumbsの0番目の要素？
        //dv.thumbsは一つしかないだけであって要素番号はある。
        String year =el.select("p").get(0).text();
        String newYear=year.substring(0,4);
        
        for(Element child : el.children()) {
            if(child.children().size()<1) {
                continue;
            }
            String title= child.select("a").get(0).text();
            String genre=child.select("a").get(1).text();
            String date=child.select("span").get(0).text();
            String url=child.child(0).child(1).child(0).attr("href");
            
            url="https://news.mynavi.jp"+url;
            System.out.println(title+","+genre+","+newYear+"/"+date+","+url);
        }
        
    }
}

//forの前に宣言すると初期化してないとなるが？
//url+=rootUrlとするとurl+rootUrlになることが分かった。逆にする。