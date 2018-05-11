package jp.co.goalist.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Question1{

    public static void main(String[] args){

        //input a message you want to send
        String message = "Hello";
        final String urlstr = "https://api.chatwork.com/v2/rooms/105172471/messages";
        final String apiKey = "えーぴーあいきー";

        HttpURLConnection con = null;
        try{

            URL url = new URL(urlstr);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("X-ChatWorkToken", apiKey);
            con.setDoOutput(true);

            String messageEnc = URLEncoder.encode(message, "UTF-8");
            String parameter = "body=" + messageEnc;
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(parameter);
            out.close();

            con.connect();


            final InputStream in = con.getInputStream();
            final InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            final BufferedReader bufReader = new BufferedReader(inReader);

            String line = null;
            while((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }


        }catch (MalformedURLException e1) {
            e1.printStackTrace();
        }catch (ProtocolException e1) {
            e1.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }finally{
            if(con != null){
                con.disconnect();
            }
        }
    }
}