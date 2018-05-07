package jp.co.goalist.api;

import jp.co.goalist.api.ApiKey;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class Kadai2{

    public static void main(String[] args){
        
        //input a task message
        String task = "";
        
        final String urlstr = "https://api.chatwork.com/v2/rooms/105172471/tasks";
        
        ApiKey ak = new ApiKey();
        final String apiKey = ak.getApiKey();
        
        HttpURLConnection con = null;
        try{
            
            URL url = new URL(urlstr);
            con = (HttpURLConnection) url.openConnection();
            
            con.setRequestMethod("POST");
            con.setRequestProperty("X-ChatWorkToken", apiKey);
            con.setDoOutput(true);
            
            String taskEnc = URLEncoder.encode(task, "UTF-8");
            String parameter = "body=" + task + "&to_ids=2997289";
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(parameter);
            out.close();
            
            con.connect();
            
            /*
            final InputStream in = con.getInputStream();
            final InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            final BufferedReader bufReader = new BufferedReader(inReader);
            
            String line = null;
            while((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }
             */
            
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