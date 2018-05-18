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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Question3_2 {
    public static void main(String[] args) {
        String strUrl = "https://api.chatwork.com/v2/rooms/105172471/tasks?";
        String api = "ひ・み・つ";
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        List<String> taskList = new ArrayList<>();

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
            String jsonData = null;
            String line = null;
            if (status == HttpURLConnection.HTTP_OK) {
                try (InputStream in = connection.getInputStream()) {
                    reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        jsonData = line;// .substring(1, line.length() - 1);
                    }

                    // タスク内容の取り出し
                    taskList = split(jsonData);
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

    public static List<String> split(String tasks) {
        JSONArray arr = new JSONArray(tasks);
        List<Object> list = new ArrayList<>();
        List<String> taskList = new ArrayList<>();
        for (Object str : arr) {
            list.add(str);
        }

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Map<String, Object> map = new HashMap<>();
            for (Object key : obj.keySet()) {
                map.put((String) key, obj.get((String) key));
            }

            Task task = new Task();
            task.contents(map);
            String taskContent = task.task_id + "," + task.name + "," + task.assigned_name + "," + task.message + ","
                    + task.date + "," + task.status;
            taskList.add(taskContent);
        }
        return taskList;
    }
}

// タスク内容を取り出すためのあれこれ
class Task {
    String task_id;
    String name;
    String assigned_name;
    String date;
    String message;
    String status;

    // 中身を入れる
    void contents(Map<String, Object> map) {
        JSONObject acc = (JSONObject) map.get("account");
        Map<String, Object> accMap = new HashMap<>();
        Map<String, Object> assAccMap = new HashMap<>();
        for (Object key : acc.keySet()) {
            accMap.put((String) key, acc.get((String) key));
        }
        this.name = (String) accMap.get("name");
        JSONObject assAcc = (JSONObject) map.get("assigned_by_account");
        for (Object key : assAcc.keySet()) {
            assAccMap.put((String) key, assAcc.get((String) key));
        }
        this.assigned_name = (String) assAccMap.get("name");

        this.task_id = String.valueOf(map.get("task_id"));
        this.date = String.valueOf(map.get("limit_time"));
        this.message = String.valueOf(map.get("body"));
        this.status = String.valueOf(map.get("status"));

        Date date = new Date(Long.parseLong(this.date) * 1000);
        SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
        this.date = String.valueOf(d.format(date));
    }
}