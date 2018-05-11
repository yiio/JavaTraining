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
    public static void main(String[] args){

        final String jsonReg = "\\{\"task_id\":([0-9]*?),"//task_id : group 1
                                + "\"account\":\\{"
                                + "\"account_id\":([0-9]*?),"//account -> account_id : group 2
                                + "\"name\":\"(.*?)\","//account -> name : group 3
                                + "\"avatar_image_url\":\"(http.*?)\"\\},"//account -> avatar_image_url : group 4
                                + "\"assigned_by_account\":\\{"
                                + "\"account_id\":([0-9]*?),"//assigned_by_account -> account_id : group 5
                                + "\"name\":\"(.*?)\","//assigned_by_account -> name : group 6
                                + "\"avatar_image_url\":\"(http.*?)\"\\},"//assigned_by_account -> avatar_image_url : group 7
                                + "\"message_id\":\"([0-9]*?)\","//message_id : group 8
                                + "\"body\":\"(.*?)\","//body : group 9
                                + "\"limit_time\":([0-9]*?),"//limit_time : group 10
                                + "\"status\":\"(.*?)\"\\}";//status : group 11


        Path filePath  = Paths.get("C:/TechTraining/resources/Q3_API.csv");
        final String urlstr = "https://api.chatwork.com/v2/rooms/105172471/tasks";
        final String apiKey = "えーぴーあいきー";

        HttpURLConnection con = null;
        String response = null;
        try{

            URL url = new URL(urlstr);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("X-ChatWorkToken", apiKey);
            con.setDoOutput(true);

            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {

                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if(null == encoding){
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;

                while((line = bufReader.readLine()) != null) {
                    System.out.println(line);
                    response = line;
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                System.out.println(status);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1){
            e1.printStackTrace();
        } finally {
            if(con != null){
                con.disconnect();
            }
        }


        // ---------- ｶｷｺ ----------
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            bw.write("タスクID,タスク担当者名,タスク登録者名,期日,メッセージ内容,完了フラグ");
            bw.newLine();

            Pattern p = Pattern.compile(jsonReg);
            Matcher m = p.matcher(response);
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            while(m.find()){

                String taskId = m.group(1);
                String name = convertToOiginal(m.group(3));
                String registrant = convertToOiginal(m.group(6));
                String limit = m.group(10).equals("0")? "unlimited" : df.format(new Date((long)Integer.parseInt(m.group(10)) * 1000));
                String message = convertToOiginal(m.group(9));
                String status = m.group(11);

                String messageStr = ( taskId + ","
                        + name + ","
                        + registrant + ","
                        + limit + ","
                        + message + ","
                        + status + ",");

                bw.write( messageStr );
                bw.newLine();

                System.out.println( messageStr);
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String convertToOiginal(String unicode){

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
