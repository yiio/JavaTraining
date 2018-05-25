package jp.co.goalist.crawler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

public class Kadai5 {
    
    private static final String rootUrl = "https://news.mynavi.jp";
    private static final String dir = "/Users/kanaria/Desktop/Goalist/technique/second/TechTraining/resources/img";
    
    public static void main(String[] args){
        
        String[] date = input();
        
        try{
            
            List<String> list = getImagePathList(date[0], date[1], date[2]);
            for(String path : list){
                storeImageFrom(path);
            }
        
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    

    public static List<String> getImagePathList(final String yearStr,
                                                final String monthStr,
                                                final String category) throws IOException {
        
        List<String> imgPathList = new ArrayList<>();
        
        String baseUrl = "https://news.mynavi.jp/list/headline";
        String conUrl = baseUrl + "/" + category + "/" +yearStr + "/" + monthStr + "/";
        
        Document doc = Jsoup.connect(conUrl).get();
        
        String lastPageUrl = doc.select("a.paginate__last").attr("href");
        int lastPage = Integer.valueOf(lastPageUrl.substring(lastPageUrl.indexOf("=") + 1));
        
        for(int page = 1; page <= lastPage; page++){
            
            String eachConUrl = conUrl + "?page=" + page;
            
            sleepOneSec();
            Document eachPageDoc = Jsoup.connect(eachConUrl).get();
            
            Elements imgElems = eachPageDoc.select("img.thumb-s__img");
            
            for(Element imgElem : imgElems){
                String relPath = imgElem.attr("src");
                relPath = relPath.substring(0, relPath.length()-4);
                relPath = rootUrl + relPath;
                if(relPath.substring(relPath.length()-3).matches("jpg")){
                    imgPathList.add(relPath);
                }
                
            }
        }
        
        for(String path : imgPathList){
            System.out.println(path);
        }
        
        return imgPathList;
        
    }
    
    public static void storeImageFrom(String imgUrl) throws IOException {
        
        // URL : https://news.mynavi.jp/article/20170301-a123/index_images/index.jpg
        // forFileName 0:https:, 1: , 2:news.~, 3:article, 4:20170301~, 5:index~, 6:index.jpg
        String[] forFileName = imgUrl.split("/");
        String fileName = forFileName[4] + ".jpg"; // 20170407211804.jpg
        
        Path dirPath = Paths.get(dir);
        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        Path localFilePath = Paths.get(dir, fileName);
        Files.deleteIfExists(localFilePath);
        Files.createFile(localFilePath);
        
        sleepOneSec();
        URL url = new URL(imgUrl);
        URLConnection conn = url.openConnection();
        
        try (InputStream is = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(localFilePath.toFile(), false);) {
            
            int b;
            while ((b = is.read()) != -1) {
                fos.write(b);
            }
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
    
    private static String[] input(){
        
        String[] dateAndCategory = new String[3];
        
        try(BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));){
            
            System.out.print("Input Year : ");
            dateAndCategory[0] = br.readLine();
            
            System.out.print("Input Month : ");
            String month  = br.readLine();
            
            if(month.length() == 1){
                month = "0" + month;
            }
            
            dateAndCategory[1] = month;
            
            System.out.println("Select Category");
            System.out.print("business -> 1, digital -> 2, kurashi -> 3, ");
            System.out.println("entertainment -> 4, present -> 5");
            System.out.print("Select Number : ");
            String flag = br.readLine();
            
            switch(flag){
                    
                case "1":
                    dateAndCategory[2] = "business";
                    break;
                    
                case "2":
                    dateAndCategory[2] = "digital";
                    break;
                    
                case "3":
                    dateAndCategory[2] = "kurashi";
                    break;
                    
                case "4":
                    dateAndCategory[2] = "entertainment";
                    break;
                    
                case "5":
                    dateAndCategory[2] = "present";
                    break;
                    
                default:
                    System.out.println("You missed input!!");
                    dateAndCategory[2] = "business";
            }
            
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return dateAndCategory;
    }
}
