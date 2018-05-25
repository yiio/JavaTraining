package jp.co.goalist.crawler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Kadai3 {
    
    public static void main(String[] args){
        
        String[] date = input();
        
        try{
            
            extractArticlesList(date[0], date[1]);
        
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    

    // miss named yearInt. This type is String.
    public static void extractArticlesList(final String yearStr, final String monthStr) throws IOException {
        
        String rootUrl = "https://news.mynavi.jp";
        
        String conUrl = "https://news.mynavi.jp/list/headline/";
        conUrl = conUrl + yearStr + "/" + monthStr + "/";
        
        Document doc = Jsoup.connect(conUrl).get();
        
        String lastPageUrl = doc.select("div.paginate__inner > a.paginate__last").attr("href");
        int lastPage = Integer.valueOf(lastPageUrl.substring(lastPageUrl.indexOf("=") + 1));
        
        
        Path filePath = Paths.get("../../resources/kadai3scrape-test.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            
            bw.write("title,category,date,URL");
            bw.newLine();
            
            for(int page = 1; page <= lastPage; page++){
                
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                
                String eachConUrl = conUrl + "?page=" + page;
                
                Document eachPageDoc = Jsoup.connect(eachConUrl).get();
                Elements els = eachPageDoc.select("div.thumb-s");
                
                // el : each date contents
                for(Element el : els){
                    
                    //child : each content
                    for (Element child : el.children()) {
                        
                        String date = child.select("span.thumb-s__date").text();
                        date = yearStr + "/" + date;
                        
                        Elements grandsons = child.select("a");
                        
                        if(grandsons.size() != 2){
                            continue;
                        }
                        
                        String title;
                        String url;
                        String genre;
                        
                        url = rootUrl + grandsons.get(0).attr("href");
                        title = grandsons.get(0).text();
                        genre = grandsons.get(1).text();
                        
                        bw.write(title+","+genre+","+date+","+url);
                        bw.newLine();
                        
                        System.out.println(title+","+genre+","+date+","+url);
                        
                    }
                
                }
                
                
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static String[] input(){
        
        String[] date = new String[2];
        
        try(BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));){
            
            System.out.print("Input Year : ");
            date[0] = br.readLine();
            
            System.out.print("Input Month : ");
            String month  = br.readLine();
            
            if(month.length() == 1){
                month = "0" + month;
            }
            
            date[1] = month;
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return date;
    }

}
