package jp.co.goalist.analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

public class Question2 {
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\testResult.csv");
        
        try (BufferedReader br = Files.newBufferedReader(filePath)){
            
            String line;
            Map<String, Integer> mathScores = new HashMap<String, Integer>(); // 名前, 数学の点数
            
            while ((line = br.readLine()) != null) {
                // 各行の文字列を配列に分解
                String[] scoreAry = line.split(","); // 得点部分がString型になっていることに注意
                
                // 配列の得点部分(数学)の値が数値かどうかチェック
                if (StringUtils.isNumeric(scoreAry[1])) { 
                    
                    // 数値ならば、数学の得点をIntegerへ変換
                    int mathScore = Integer.parseInt(scoreAry[1]);
                    
                    // 生徒名を取得し、変数nameへ代入
                    String name = scoreAry[0];
                    
                    // マップmathScoresへ、キー（生徒名）と値（数学の得点）をセットでぶち込む
                    mathScores.put(name, mathScore);
                }
            
            }
            int maxScore = 0;
            
            // 数学の最高点(maxScore)を求める
            for (Map.Entry<String, Integer> mathEntry : mathScores.entrySet()) {
                maxScore = Math.max(maxScore, mathEntry.getValue());
            }
            // 数学の最点を出力
            System.out.println("数学の最高得点は" + maxScore + "点です。");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
