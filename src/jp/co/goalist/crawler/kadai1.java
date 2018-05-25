package jp.co.goalist.crawler;

import java.io.IOException;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Kadai1 {
    
    public static void main(String[] args){
        
        try{
            extractContents();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    public static void extractContents() throws IOException {
        
        String rootUrl = "https://news.mynavi.jp";
        String conUrl = "https://news.mynavi.jp/list/headline//";
        
        //connect and excute request method 'GET' to get responce body
        Document doc = Jsoup.connect(conUrl).get();
        
        Elements els = doc.select("div.thumb-s");
        Map<String, Map<String,  String>> groupByGenre = new HashMap<>();
        
        for(Element el : els){
        
            for (Element child : el.children()) {
                
                if (child.children().size() < 1) {
                    continue;
                }
                
                Elements grandsons = child.select("a");
                if(grandsons.size() != 2){
                    continue;
                }
                
                String title;
                String url;
                String genre;
                
                url = grandsons.get(0).attr("href");
                url = rootUrl + url;
                
                title = grandsons.get(0).text();
                genre = grandsons.get(1).text();
                
                if(!groupByGenre.containsKey(genre)){
                    Map<String, String> titleUrl = new HashMap<>();
                    titleUrl.put(title, url);
                    groupByGenre.put(genre, titleUrl);
                }else{
                    groupByGenre.get(genre).put(title, url);
                }
                
                for(String g : groupByGenre.keySet()){
                    System.out.println("\nGenre : " + g);
                    for(Entry<String, String> entry : groupByGenre.get(g).entrySet()){
                        System.out.println(entry.getKey());
                        System.out.println("( " + entry.getValue() + " )");
                    }
                }
                
            }
            
        }
        
        
    }

}
