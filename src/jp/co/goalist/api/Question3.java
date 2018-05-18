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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question3 {
    public static void main(String[] args) {
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks?";
        String api = "ひみつ";
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        List<String> list = new ArrayList<>();

        // タスク一覧の取得
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-ChatWorkToken", api);
            connection.connect();

            int status = connection.getResponseCode();
            System.out.println("HTTPステータス:" + status);
            if (status == HttpURLConnection.HTTP_OK) {
                try (InputStream in = connection.getInputStream()) {
                    reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        String[] cols = (line.split("\"task_id\":"));
                        for (String task : cols) {
                            list.add(task);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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

        // タスク内容の取り出し
        String taskContents;
        List<String> taskList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            String task = list.get(i);
            Moji m = new Moji();
            m.split(task);
            Date date = new Date(Long.parseLong(m.date) * 1000);
            SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
            taskContents = m.task_id + "," + m.name + "," + m.assigned_name + "," + m.message + "," + d.format(date)
                    + "," + m.status;
            taskList.add(taskContents);
        }

        // csvファイルの出力
        Path filePath = Paths.get("C:\\TechTraining\\resources\\taskList.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            String header = "タスクID,タスク担当者名,タスク登録者名,メッセージ内容,期日,完了フラグ";
            bw.write(header);
            bw.newLine();
            for (String task : taskList) {
                bw.write(task);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// タスク内容を取り出すためのあれこれ
class Moji {
    String task_id;
    String name;
    String assigned_name;
    String date;
    String message;
    String status;

    // JSONデータから必要な情報を取り出す
    void split(String task) {
        this.task_id = task.substring(0, task.indexOf(","));
        task = task.substring(task.indexOf(this.task_id) + this.task_id.length());
        this.name = task.substring(task.indexOf("\\"), task.indexOf("\",\"avatar"));
        task = task.substring(task.indexOf("assigned_by_account"));
        this.assigned_name = task.substring(task.indexOf("\\"), task.indexOf("\",\"avatar"));
        task = task.substring(task.indexOf("body"));
        this.message = task.substring(task.indexOf("\\"), task.indexOf("\",\"limit_time\""));
        task = task.substring(task.indexOf(",\"limit_time\""));
        this.date = task.substring(task.indexOf(":") + 1, task.indexOf(",\"status\""));
        task = task.substring(task.indexOf(this.date) + this.date.length());
        this.status = task.substring(task.indexOf(":\"") + 2, task.indexOf("\"}"));

        // Unicodeを文字に変換する
        this.name = toText(this.name);
        this.assigned_name = toText(this.assigned_name);
        this.message = toText(this.message);
    }

    // 文字変換のためのメソッド
    String toText(String unicode) {
        String[] codeStrs = unicode.split("\\\\u");
        int[] codePoints = new int[codeStrs.length - 1];
        for (int i = 0; i < codePoints.length; i++) {
            codePoints[i] = Integer.parseInt(codeStrs[i + 1].trim(), 16);
        }
        unicode = new String(codePoints, 0, codePoints.length);
        return unicode;
    }
}
