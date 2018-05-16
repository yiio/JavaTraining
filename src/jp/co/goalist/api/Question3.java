package jp.co.goalist.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question3 {

    //取得したタスクの情報とマッチさせるための正規表現パターン
    public static String pattern = "\\{\"task_id\":([0-9]*?)," //group1:タスクID
                                    + "\"account\":\\{\"account_id\":([0-9]*?)," //group2:タスク担当者アカウントID
                                    + "\"name\":\"(.*?)\"," //group3:タスク担当者名
                                    + "\"avatar_image_url\":\"(https.*?)\"\\}," //group4:タスク担当者アバター画像URL
                                    + "\"assigned_by_account\":\\{\"account_id\":([0-9]*?)," //group5:タスク登録者アカウントID
                                    + "\"name\":\"(.*?)\"," //group6:タスク登録者名
                                    + "\"avatar_image_url\":\"(https.*?)\"\\}," //group7:タスク登録者アバター画像URL
                                    + "\"message_id\":\"([0-9]*?)\"," //group8:メッセージID
                                    + "\"body\":\"(.*?)\"," //group9:メッセージ内容
                                    + "\"limit_time\":([0-9]*?)," //group10:期限
                                    + "\"status\":\"(.*?)\"\\}"; //group11:状態（完了フラグ)


    public static void main(String[] args) {

        String apiToken = ("キー");
        HttpURLConnection urlConn = null;


     //status=doneのタスクを取得
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks?" +"status=done";
        String data = null;


        try {

            URL url = new URL(strUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoOutput(false);  //入力用のURL接続なので、falseに設定
            urlConn.setRequestProperty("X-ChatWorkToken", apiToken);

            urlConn.connect();

            int status = urlConn.getResponseCode();


            if (status == HttpURLConnection.HTTP_OK) {  //通信成功

                InputStream input = urlConn.getInputStream(); //入力ストリームを取得
                InputStreamReader in = new InputStreamReader(input,"UTF-8");  //バイトストリームをUTF-8の文字ストリームに変換(よくわかってません）
                BufferedReader br = new BufferedReader(in);

                String line;

                while((line=br.readLine()) != null) {


                    data = line;

                }

            }else{
                System.out.println(status);
            }


        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(urlConn != null){
                urlConn.disconnect();
            }
        }

        //3秒待つ
        try {
            Thread.sleep(3000);

            }catch (InterruptedException e1){
                e1.printStackTrace();
            }



     //status=openのタスクを取得
        String strUrl2 = "https://api.chatwork.com/v2/rooms/105172471/tasks?" +"status=open";
        String data2 = null;

        try {
            URL url = new URL(strUrl2);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoOutput(false);
            urlConn.setRequestProperty("X-ChatWorkToken", apiToken);

            urlConn.connect();

            int status = urlConn.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {

                InputStream input = urlConn.getInputStream();
                InputStreamReader in = new InputStreamReader(input,"UTF-8");
                BufferedReader br = new BufferedReader(in);

                String line;

                while((line=br.readLine()) != null) {


                    data2 = line;

                }

            }else{
                System.out.println(status);
            }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (ProtocolException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally{
                if(urlConn != null){
                    urlConn.disconnect();
                }
            }


        Path filePath = Paths.get("c:/TechTraining/resources/task.csv"); // 書き込み対象ファイルの場所を指定
        try {

            Files.deleteIfExists(filePath); // 既に存在してたら削除
            Files.createFile(filePath); // ファイル作成

        } catch (IOException e) {
            e.printStackTrace();
        }


        //ファイルに書き込み
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            bw.write("タスクID,タスク担当者名,タスク登録者名,期日,メッセージ内容,完了フラグ"); //ヘッダ
            //System.out.println("タスクID,タスク担当者名,タスク登録者名,期日,メッセージ内容,完了フラグ");
            bw.newLine();

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(data); //patternとマッチさせる文字列を、status=doneのタスク情報に設定

            //status=doneのタスク情報を書き込み
            while (m.find()) {

                Date limit = new Date((Long.parseLong(m.group(10))*1000)); //UNIXTIMEを日付に変換
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //期日を表示する形式を設定


                //期限が設定されていない場合
                if(Long.parseLong(m.group(10)) == 0) {
                    bw.write(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + "なし" + ","  + convert(m.group(9)) + "," + m.group(11));
                    //System.out.println(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + "なし" + ","  + convert(m.group(9)) + "," + m.group(11));
                    bw.newLine();

                //期限が設定されている場合
                }else {
                    bw.write(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + sdf.format(limit) + "," + convert(m.group(9)) + "," + m.group(11));
                    //System.out.println(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + sdf.format(limit) + "," + convert(m.group(9)));
                    bw.newLine();
                }

            }




            m = p.matcher(data2); //patternとマッチさせる文字列を、status=openのタスク情報に設定

            //status=openのタスク情報を書き込み
            while (m.find()) {

                Date limit = new Date((Long.parseLong(m.group(10))*1000)); //UNIXTIMEを日付に変換
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //期日を表示する形式を設定


                bw.write(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + sdf.format(limit) + "," + convert(m.group(9)) + "," + m.group(11));
                //System.out.println(m.group(1) + "," + convert(m.group(3)) + "," + convert(m.group(6)) + "," + sdf.format(limit) + "," + convert(m.group(9)));
                bw.newLine();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    //unicode→UTF-8へ変換
    private static String convert(String unicode){

        String ret = unicode;
        String regex = "u([0-9a-f]{4})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ret);

        while(m.find()){
            ret = ret.replaceAll("\\\\"+m.group(), String.valueOf((char)Integer.parseInt(m.group(1), 16)));
        }

        return ret;
    }



}

