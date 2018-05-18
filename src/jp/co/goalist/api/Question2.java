package jp.co.goalist.api;

import java.io.IOException;
import java.io.OutputStream;
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
        String message = null;


        try {


            URL url = new URL(strUrl);

            String enc = URLEncoder.encode(post, "UTF-8"); //UTF-8を使用して文字列をエンコード


            message = "body=" + enc +"&limit=" + limit + "&to_ids=2997278";
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true); //出力用の接続なのでtrue
            urlConn.setRequestProperty("X-ChatWorkToken", apiToken);



            try(OutputStream out = urlConn.getOutputStream();
                    OutputStreamWriter output = new OutputStreamWriter(out)) { //文字ストリームをバイトストリームに変換

                output.write(message); //書き込み
                output.close();

                urlConn.connect();

                int status = urlConn.getResponseCode();

                System.out.println("HTTP STATUS:" + status);

            }


        }catch (IOException e) {
            System.out.println(e);
        }finally {
            if(urlConn != null) {
                urlConn.disconnect();

            }
        }

    }
}