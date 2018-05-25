package jp.co.goalist.crawler;

import java.util.List;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.IOException;

import jp.co.goalist.crawler.Article;


public class DrawHTML {
    
    private final String filePath = "../../resources/kadai6.html";
    private List<Article> articles;
    String fullHtml;
    
    DrawHTML(List<Article> articles){
        this.articles = articles;
    }
    
    public void runDrawHTML(){
        setHTML();
        writeHTML(this.fullHtml);
        openHTML();
    
    }
    
    public void openHTML(){
        
        try{
            
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("open", this.filePath);
            Process process = pb.start();
    
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public void setHTML(){
        
        String firstPartHtml =
          "<!DOCTYPE html>"
        + "<html>"
        + "<head>"
        + "<meta charset=\"utf-8\" />"
        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"../JavaTraining/src/jp/co/goalist/crawler/kadai6.css\">"
        + "</head>"
        + "<body>";
        
        String latterHtml =
          "</body>"
        + "</html>";
        
        String template = "";
        template = template + "<h2>"+this.articles.get(0).getDate().substring(0,10)+"の記事(最大50件)</h2>";
        for(Article arc : this.articles){
            template =
            template
            + "<section class=\"item\">"
            + "<h3 class=\"title\">"
            + "<a href=\"" + arc.getArticleUrl() + "\" target=\"_blank\" >" + arc.getTitle() + "</a>"
            + "</h3>"
            + "<img src=\"." + arc.getBmpFilePath().substring(15) + "\" width=\"100\" height=\"100\" ><br>"
            + "<div class=\"tag\">"
            + "<span class=\"category\">" + arc.getCategory() + "</span><br>"
            + "<span class=\"date\">" + arc.getDate() + "</span>"
            + "</div>"
            + "</section>";
        }
        
        this.fullHtml = firstPartHtml + template + latterHtml;
        
        
    }
    
    public void writeHTML(String html){
        
        Path filePath = Paths.get(this.filePath);
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write(html);
        }catch (IOException e) {
            e.printStackTrace();
        }
    
    }

}
