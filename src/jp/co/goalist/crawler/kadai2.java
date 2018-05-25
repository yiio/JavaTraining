package jp.co.goalist.crawler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Kadai2 {
    
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
        
        Path filePath = Paths.get("../../resources/kadai2scrape.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            
            Elements els = doc.select("div.thumb-s");
            
            for(Element el : els){
                
                String year = el.select("p.thumb-s__update").text().substring(0,4);
                
                Map<String, Map<String,  String>> groupByGenre = new HashMap<>();
                
                bw.write("\"title\",\"category\",\"date\",\"URL\"");
                bw.newLine();
                System.out.println("title,category,date,URL");
                
                for (Element child : el.children()) {
                    
                    if (child.children().size() < 1) {
                        continue;
                    }
                    
                    String date = child.select("span.thumb-s__date").text();
                    date = year + "/" + date;
                    
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
                    
                    bw.write("\"" + title + "\",\"" + genre + "\",\"" + date + "\",\"" + url + "\"");
                    bw.newLine();
                    System.out.println(title+","+genre+","+date+","+url);
                    
                }
            
            }
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
