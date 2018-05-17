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
                                    + "\"status\":\"(.*?)\"\\}"; //group11:完了フラグ


    public static void main(String[] args) {

        String apiToken = ("キー");
        HttpURLConnection urlConn = null;


     //status=doneのタスクを取得
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks?" +"status=done";
        String data1 = null;


        try {

            URL url = new URL(strUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoOutput(false);  //入力用の接続なのでfalse
            urlConn.setRequestProperty("X-ChatWorkToken", apiToken);

            urlConn.connect();

            int status = urlConn.getResponseCode();


            if (status == HttpURLConnection.HTTP_OK) {  //通信成功

                InputStream input = urlConn.getInputStream(); //入力ストリームを取得
                InputStreamReader in = new InputStreamReader(input,"UTF-8");  //バイトストリームを文字ストリームに変換する
                BufferedReader br = new BufferedReader(in);

                String line = br.readLine();
                data1 = line;


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

        }catch (InterruptedException e){
            e.printStackTrace();
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

                String line = br.readLine();
                data2 = line;



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


        //タスク情報をファイルに書き込む
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            bw.write("タスクID,タスク担当者名,タスク登録者名,期日,メッセージ内容,完了フラグ"); //ヘッダ
            //System.out.println("タスクID,タスク担当者名,タスク登録者名,期日,メッセージ内容,完了フラグ");
            bw.newLine();

            String data = data1 + data2; //openのタスク+doneのタスク
            Pattern p = Pattern.compile(pattern); //正規表現パターンをコンパイル
            Matcher m = p.matcher(data); //タスク情報と正規表現パターンをマッチ


            while (m.find()) { //パターンが見つかる間

                Date limit = new Date(Long.parseLong(convert(m.group(10)))*1000); //UNIXTIMEを日付に変換
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //期日を表示する形式を設定

                String taskId = m.group(1); //タスクID
                String name1 = convert(m.group(3)); //タスク担当者名
                String name2 = convert(m.group(6)); //タスク登録者名
                String date = sdf.format(limit); //期日
                String text = convert(m.group(9)); //メッセージ内容
                String status = m.group(11); //完了フラグ


                //期限が設定されていない場合
                if(Long.parseLong(m.group(10)) == 0) {
                    bw.write(taskId + "," + name1 + "," + name2 + "," + "なし" + ","  + text + "," + status);
                    //System.out.println(taskId + "," + name1 + "," + name2 + "," + "なし" + ","  + text + "," + status);
                    bw.newLine();

                //期限が設定されている場合
                }else {
                    bw.write(taskId + "," + name1 + "," + name2 + "," + date + ","  + text + "," + status);
                    //System.out.println(taskId + "," + name1 + "," + name2 + "," + date + ","  + text + "," + status);
                    bw.newLine();
                }

            }






        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    //unicode→UTF-8
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

