package jp.co.goalist.api;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Question2 {
    public static void main(String[] args) {
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks";
        HttpURLConnection  urlConn = null;
        String post = "お昼行ってきます";
        int limit = 1526914800;
        String apiToken = "キー";



        try {


        URL url = new URL(strUrl);

        String enc = URLEncoder.encode(post, "UTF-8");


        String massage = "body=" + enc +"&limit=" + limit + "&to_ids=2997278";
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("POST");
        urlConn.setDoOutput(true);
        urlConn.setRequestProperty("X-ChatWorkToken", apiToken);

        OutputStreamWriter output = new OutputStreamWriter(urlConn.getOutputStream());
        output.write(massage);
        output.close();

        urlConn.connect();

        int status = urlConn.getResponseCode();

        System.out.println("HTTP STATUS:" + status);




        }catch (IOException e) {
            System.out.println(e);
        }
    }
}