package jp.co.goalist.crawler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.stream.Stream;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import jp.co.goalist.crawler.Article;

public class CrawlArticleList {
    
    private static final int MAX_ARTICLE_NUM = 50;
    
    private final String rootUrl = "https://news.mynavi.jp";
    
    /* date format : yyyy/mm/dd */
    public void showArcListWithDate(List<Article> article, String date){
        
        article.stream().filter(s -> s.matchDay(date))
                        .map(Article::toString)
                        .forEach(System.out::println);
    }
    
    public List<Article> run(){
    
        InputData id = new InputData();
        id.runInput();
        
        try{
            return extractArticlesList(id);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return null;
    }
    

    public List<Article> extractArticlesList(InputData inda) throws IOException {
        
        String baseUrl = "https://news.mynavi.jp/list/headline/";
        
        String conUrl = baseUrl + inda.getYear() + "/" + inda.getMonth() + "/";
        
        
        Document doc = Jsoup.connect(conUrl).get();
        
        String lastPageUrl = doc.select("a.paginate__last").attr("href");
        int lastPage = Integer.valueOf(lastPageUrl.substring(lastPageUrl.indexOf("=") + 1));
        
        
        List<Article> artList = new ArrayList<>();
        
        boolean flag = false;
        
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
                    
                    if( !date.substring(3, 5).equals(inda.getDay()) ){
                        if(flag == true){
                            break;
                        }
                        continue;
                    }
                    
                    date = inda.getYear() + "/" + date;
                    
                    Elements grandsons = child.select("a");
                    
                    if(grandsons.size() != 2){
                        continue;
                    }
                    
                    String url = rootUrl + grandsons.get(0).attr("href");
                    String title = grandsons.get(0).text();
                    String genre = grandsons.get(1).text();
                    Article article = new Article();
                    
                    if(!isStatusOk(url)){
                        continue;
                    }
                    
                    System.out.println(title+","+genre+","+date+","+url);
                    
                    String content = extractDetailArtcles(url);
                    
                    article = article.setTitle(title)
                                        .setContent(content)
                                        .setCategory(genre)
                                        .setDate(date)
                                        .setUrl(url)
                                        .setBmpFileName()
                                        .setBmpFilePath();
                    
                    artList.add(article);
                    
                    flag = true;
                    
                    
                    // set the max number of articles : 100
                    
                    if(artList.size() > MAX_ARTICLE_NUM){
                        return artList;
                    }
                    
                    
                }
                
            }
        }
        
        
        return artList;
        
        
    }

    
    private void sleepOneSec(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    private boolean isStatusOk(String url){
        
        sleepOneSec();
        
        try{
            
            Connection con = Jsoup.connect(url).ignoreHttpErrors(true);
            System.out.print("Connect -> " + url + "\n");
            Response res = null;
            int status;
            while(true){
                try{
                    res = con.execute();
                    status = res.statusCode​();
                    break;
                }catch (UnknownHostException e) {
                    System.out.println("retry......");
                    e.printStackTrace();
                    for(int i=0;i<10;i++){
                        sleepOneSec();
                    }
                    status = 404;
                    break;
                    
                }
            }
            
            //int status = res.statusCode​();
            
            if(status != 200){
                return false;
            }
            
            return true;
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    private String extractDetailArtcles(String url){
        
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
    
    private String extractDetailArticlecOf(String url){
        
        sleepOneSec();
        
        String content = null;
        
        try{
            
            Document detailDoc = Jsoup.connect(url).get();
            content = detailDoc.getElementsByClass("article-body").get(0).toString();
            content = content.replaceAll("\n", " ");
            content = content.replaceAll(",", "、");
            content = content.replaceAll("<.*?>", "");
            // one byte space delete.
            content = content.replaceAll(" ", "");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return content;
        
    }
    
}


class InputData{
    
    /**
     * I want to realize a function which an user can select how to search, 
     * by date or freeword.
     * But it's too troublesome, so I gave up.
     *
     */
    
    // private String searchWay;
    // private String freeword;
    private String year;
    private String month;
    private String day;
    
    InputData(){
    
    }
    
    // public String getSearchWay(){ return this.searchWay; }
    // public String getFreeword(){ return this.freeword; }
    public String getYear(){ return this.year; }
    public String getMonth(){ return this.month; }
    public String getDay(){ return this.day; }
    
    public InputData runInput(){
        inputDate();
        return this;
    }
    
    /* There is no scalability */
    /*
    private void decideSearchWay(){
        
        try(BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));){
            
            String flag;
            
            
            System.out.println("Select how to serach..... 1:freeword or 2:date.");
            System.out.print("Please input number : ");
            
            flag = br.readLine();
            
            if(!flag.matches("1|2")){
                System.out.println("Your input : " + flag + " doesn't match our format.");
                flag = "";
            }
            
            switch(flag){
                case "1":
                    this.searchWay = "freeword";
                    break;
                case "2":
                    this.searchWay = "date";
                    break;
                default :
                    this.searchWay = "I'm tired....";
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return ;
    }
    */
    /*
    private void inputData(){
        
        switch(this.searchWay){
            case "date":
                inputDate();
                break;
            case "freeword":
                inputFreeword();
                break;
            default:
                System.out.println("I want to thorw original exception..., but I don't know how to do it.");
        }
        
    }
     */
    
    private void inputDate(){
        
        try(BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));){
            
            System.out.print("Input Year : ");
            this.year = br.readLine();
            
            
            System.out.print("Input Month : ");
            String month  = br.readLine();
            if(month.length() == 1){
                month = "0" + month;
            }
            this.month = month;
            
            System.out.print("Input Day : ");
            String day  = br.readLine();
            if(day.length() == 1){
                day = "0" + day;
            }
            this.day = day;
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
    private void inputFreeword(){
        
        try(BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));){
            
            System.out.print("Input Freeword : ");
            this.freeword = br.readLine();
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
     */

}
