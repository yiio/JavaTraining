package jp.co.goalist.crawler;

import java.util.List;
import java.util.ArrayList;

import jp.co.goalist.crawler.Article;
import jp.co.goalist.crawler.CrawlArticleList;
import jp.co.goalist.crawler.ConvertData;
import jp.co.goalist.crawler.DrawHTML;


public class Kadai6 {
    
    public static void main(String[] args){
        CrawlArticleList cal = new CrawlArticleList();
        List<Article> artList = cal.run();
        for(Article arc : artList){
            ConvertData cd = new ConvertData(arc);
            cd.runConvert();
        }
        
        DrawHTML dhtml = new DrawHTML(artList);
        dhtml.runDrawHTML();
        
    }
}