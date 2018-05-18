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

public class Question1 {
    public static void main(String[] args) {
        String message = "リソースを閉じるとは！"; // 出力するメッセージ
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/messages";
        String api = "ひみつ";

        HttpURLConnection connection = null;

        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // URL接続を出力用として使用, POSTを可能にする
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-ChatWorkToken", api); // ヘッダを設定

            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                String messageEnc = URLEncoder.encode(message, "UTF-8");
                out.write("body=" + messageEnc); // データをPOSTする
            } catch (IOException e) {
                e.printStackTrace();
            }

            connection.connect();

            try (InputStream in = connection.getInputStream();
                    InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
                    BufferedReader bufReader = new BufferedReader(inReader);) {
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
