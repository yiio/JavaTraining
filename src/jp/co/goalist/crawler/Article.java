package jp.co.goalist.crawler;

import java.util.List;
import java.util.ArrayList;


public class Article {
    
    private String title;
    private String content;
    /* yyyy/mm/dd hh:mm */
    private String date;
    private String category;
    private String articleUrl;
    private String bmpFileName;
    private String bmpFilePath;
    
    public Article(){
        this.title = "";
        this.date = "";
        this.category = "";
        this.content = "";
        this.articleUrl = "";
        this.bmpFileName = "";
        this.bmpFilePath = "";
    }
    
    public String getTitle(){ return this.title; }
    public String getDate(){ return this.date; }
    public String getCategory(){ return this.category; }
    public String getContent(){ return this.content; }
    public String getArticleUrl(){ return this.articleUrl; }
    public String getBmpFileName(){ return this.bmpFileName; }
    public String getBmpFilePath(){ return this.bmpFilePath; }
    
    public Article setTitle(String title){
        this.title = title;
        return this;
    }
    
    public Article setCategory(String category){
        this.category = category;
        return this;
    }
    
    public Article setDate(String date){
        this.date = date;
        return this;
    }
    
    public Article setContent(String content){
        this.content = content;
        return this;
    }
    
    public Article setUrl(String url){
        this.articleUrl = url;
        return this;
    }
    
    public Article setBmpFileName(){
        if(this.articleUrl.length() == 0){
            System.out.println("You haven't set Article URL yet.");
            System.out.println("At first, you have to set it.");
            return this;
        }
        
        this.bmpFileName = this.articleUrl.split("/")[4] + ".bmp";
        return this;
        
    }
    
    public Article setBmpFilePath(){
        if(this.bmpFileName.length() == 0){
            System.out.println("You haven't set BMP file name yet.");
            System.out.println("At first, you have to set it.");
            return this;
        }
        
        this.bmpFilePath = "../../resources/bmp/" + this.bmpFileName;
        return this;
        
    }
    
    /* this method require that the 'day' form is yyyy/mm/dd  */
    public boolean matchDay(String day){
        
        String regex = "[12]{1}[0-9]{3}/[01]{1}[0-9]{1}/[0-3]{1}[0-9]{1}";
        
        if(!day.matches(regex)){
            System.out.println("isDay() : input doesn't follow required form. ");
            System.out.println("required form is yyyy/mm/dd, but input is " + day);
            return false;
        }
        
        if(!this.date.substring(0,10).equals(day)){
            return false;
        }
        
        return true;
    
    }
    
    public String toString(){
        String ret;
        
        ret = "title : " + this.title
                + ", date : " + this.date
                + ", category : " + this.category
                + ", content : " + this.content
                + ", URL : " + this.articleUrl
                + ", bmp file : " + this.bmpFileName;
        
        return ret;
        
    }

}
