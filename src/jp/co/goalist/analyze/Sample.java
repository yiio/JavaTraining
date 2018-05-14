package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sample {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\prefs.txt"); // 読み込み対象ファイルの場所を指定
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) { // ファイルを1行ずつ読み込み、結果がnull以外ならばwhileループ内の処理を行う
                System.out.println(line); // 1行ずつ出力する
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
