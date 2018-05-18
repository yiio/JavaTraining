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
import java.util.Calendar;

public class Question2 {
    public static void main(String[] args) {
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks";
        String api = "ひみつ";
        HttpURLConnection connection = null;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        long utc = cal.getTimeInMillis();
        String limit = String.valueOf(utc).substring(0, 10);

        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-ChatWorkToken", api);
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                String task = "body=美味しい水も必要。&limit=" + limit + "&to_ids=2997226";
                out.write(task);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            connection.connect();

            try (InputStream in = connection.getInputStream()) {
                InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
                BufferedReader bufReader = new BufferedReader(inReader);

                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
