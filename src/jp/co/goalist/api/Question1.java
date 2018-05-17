package jp.co.goalist.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Question1 {

    public static void main(String[] args) {
        String token = "";
        if (args.length == 1) { // 実行時の引数でトークンを渡すようにしてみた
            token = args[0];
        }

        String endPoint = "https://api.chatwork.com/v2";
        String roomId = "105172471";
        String method = "messages";
        String url = endPoint + "/rooms/" + roomId + "/" + method;
        String msg;
        try {
            msg = URLEncoder.encode("Hello!", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("しんでしまうとはなさけない");
            e.printStackTrace();
            return;
        };

        // apacheのライブラリを使う方法
        postWithApacheLib(token, url, msg);

        // javaのデフォルトhttpクライアントを使う方法
        postWithHttpURLCon(token, url, msg);
    }

    private static void postWithApacheLib(String token, String url, String msg) {
        HttpPost post = new HttpPost(url);
        // ドキュメントを見たところサンプルでcurl -dオプションを指定しているので、Content-typeはapplication/x-www-form-urlencodedに指定
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        // APIトークンもヘッダに追加
        post.setHeader("X-ChatWorkToken", token);

        // ボデーに投稿内容をセット
        StringEntity body = new StringEntity("body=" + msg, "UTF-8");
        post.setEntity(body);

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse response = httpclient.execute(post);) { // リクエスト実行
            // 返却ステータスをチェック
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                System.out.println("done!");
            } else {
                String responseData = EntityUtils.toString(response.getEntity());
                System.out.println(responseData);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void postWithHttpURLCon(String token, String urlStr, String msg) {
        HttpURLConnection con = null;
        try {
            // URLの作成
            URL url = new URL(urlStr);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection) url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            // ヘッダーの設定
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("X-ChatWorkToken", token);

            // ボデーの書き込み
            String body = "body=" + msg;
            try (OutputStream os = con.getOutputStream();
                    OutputStreamWriter out = new OutputStreamWriter(os);) {
                out.write(body);
            }

            // リクエスト実行 
            con.connect();
            // 返却ステータスをチェック
            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("done!");
            } else {
                try (InputStream in = con.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

}
