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
import java.util.Calendar;

public class Question2{

    public static void main(String[] args){

        //input a task message
        String task = "労働";

        final String urlstr = "https://api.chatwork.com/v2/rooms/105172471/tasks";
        final String apiKey = "APIkey";

        HttpURLConnection con = null;
        try{

            URL url = new URL(urlstr);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("X-ChatWorkToken", apiKey);
            con.setDoOutput(true);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            Integer limit = Integer.valueOf(String.valueOf(cal.getTimeInMillis() / 1000));

            String taskEnc = URLEncoder.encode(task, "UTF-8");
            String parameter = "body=" + taskEnc
                               + "&limit=" + limit
                               + "&to_ids=2997282";
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(parameter);
            out.close();
            con.connect();

            try ( InputStream in = con.getInputStream();
                  InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
                  BufferedReader bufReader = new BufferedReader(inReader)) {
                String line = null;
                while((line = bufReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e ){
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