package jp.co.goalist.api;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Question1 {
    public static void main(String[] args) {
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/messages";
        HttpURLConnection  urlConn = null;
        String post = "どうかな";
        String apiToken = "キー";



        try {


        URL url = new URL(strUrl);

        String enc = URLEncoder.encode(post, "UTF-8");
        String massage = "body=" + enc;
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
