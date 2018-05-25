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
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

public class Kadai4 {
    
    public static void main(String[] args){
        
        String[] date = input();
        
        try{
            
            extractArticlesList(date[0], date[1]);
        
        }catch(IOException e){
            
            e.printStackTrace();
        }
        
    }
    

    public static void extractArticlesList(final String yearStr, final String monthStr) throws IOException {
        
        String rootUrl = "https://news.mynavi.jp";
        String baseUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/";
        String conUrl = baseUrl + yearStr + "/" + monthStr + "/";
        
        Document doc = Jsoup.connect(conUrl).get();
        
        String lastPageUrl = doc.select("div.paginate__inner > a.paginate__last").attr("href");
        int lastPage = Integer.valueOf(lastPageUrl.substring(lastPageUrl.indexOf("=") + 1));
        
        
        Path filePath = Paths.get("../../resources/kadai4scrape-test.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            
            bw.write("\"Title\",\"Category\",\"Date\",\"URL\",\"Content\"");
            bw.newLine();
            
            for(int page = 1; page <= lastPage; page++){
                
                sleepOneSec();
                
                String eachConUrl = conUrl + "?page=" + page;
                
                Document eachPageDoc = Jsoup.connect(eachConUrl).get();
                Elements els = eachPageDoc.select("div.thumb-s");
                
                
                // el : each date contents
                for(Element el : els ){
                    
                    //child : each content
                    for (Element child : el.select("section.thumb-s__item")) {
                        
                        String date = child.getElementsByClass("thumb-s__date").get(0).text();
                        date = yearStr + "/" + date;
                        
                        Element hrefElem = child.select("a").get(0);
                        
                        
                        String title;
                        String url;
                        String genre = "テクノロジー";
                        String content;
                        
                        title = hrefElem.text();
                        
                        url = hrefElem.attr("href");
                        url = rootUrl + url;
                        
                        if(!isStatusOk(url)){
                            content = "取得できませんでした";
                        }else{
                            content = extractDetailArtcles(url);
                        }
                        
                        bw.write("\""+title+"\",\""+genre+"\",\""+date+"\",\""+url+"\",\""+content+"\"");
                        bw.newLine();
                        
                        System.out.println("\n"+title+","+genre+","+date+","+url+",\n\n"+content);
                        
                    }
                
                }
                
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    
    private static void sleepOneSec(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    private static boolean isStatusOk(String url){
        
        sleepOneSec();
        
        try{
            
            Connection con = Jsoup.connect(url).ignoreHttpErrors(true);
            Response res = con.execute();
            int status = res.statusCode​();
            
            if(status != 200){
                return false;
            }
            
            return true;
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    private static String extractDetailArtcles(String url){
        
        sleepOneSec();
        
        String content = null;
        
        try{
        
            Document detailDoc = Jsoup.connect(url).get();
            
            String otherPageUrl = detailDoc.getElementsByClass("paginate__last").attr("href");
            
            content = extractDetailArticlecOf(url);
            
            if(!(otherPageUrl == null) && !(otherPageUrl.length() == 0)){
                String[] parts = otherPageUrl.split("/");
                for(int i = 2; i <= Integer.valueOf(parts[3]); i++){
                    String otherUrl = url + String.valueOf(i);
                    content += extractDetailArticlecOf(otherUrl);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return content;
    }
    
    private static String extractDetailArticlecOf(String url){
        
        sleepOneSec();
        
        String content = null;
        
        try{
            
            Document detailDoc = Jsoup.connect(url).get();
            content = detailDoc.getElementsByClass("article-body").get(0).toString();
            content = content.replaceAll("\n", " ");
            content = content.replaceAll(",", "、");
            content = content.replaceAll("<.*?>", "");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return content;
        
    }
    
    private static String[] input(){
        
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
