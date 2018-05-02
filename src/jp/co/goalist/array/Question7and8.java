package jp.co.goalist.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Question7and8 {
    
    public static void main(String[] args) {
     // 名前とチームの配列
        final String[][] teamArys = {
            {"山田", "Aチーム"},
            {"田中", "Aチーム"},
            {"鈴木", "Bチーム"},
            {"中村", "Cチーム"},
            {"伊藤", "Dチーム"}
        };

        // 名前と得点の配列
        final String[][] pointArys = {
            {"山田", "1"},
            {"山田", "3"},
            {"山田", "1"},
            {"伊藤", "4"},
            {"山田", "2"},
            {"中村", "1"},
            {"中村", "1"},
            {"山田", "3"},
            {"鈴木", "2"},
            {"山田", "4"},
            {"伊藤", "5"},
            {"山田", "2"},
            {"鈴木", "3"},
            {"山田", "1"},
            {"田中", "2"}
        };
        
        Map<String, String> teamMap = new HashMap<String, String>();
            {
                for (int i = 0; i < teamArys.length; i++) {
                    teamMap.put(teamArys[i][0], teamArys[i][1]);
                }
            }
        
        Map<String, Integer> pointMap = new HashMap<String, Integer>();
            {
                // pointMapの初期値の設定
                for (int i = 0; i < teamArys.length; i++) {
                    if (!pointMap.containsKey(teamArys[i][1])) {
                        pointMap.put(teamArys[i][1], 0);
                    }
                }
                for (int i = 0; i < pointArys.length; i++) {
                    // その得点を取った人が所属しているチーム名を取得しキーとして追加、得点を値として追加
                    pointMap.put(teamMap.get(pointArys[i][0]), pointMap.get(teamMap.get(pointArys[i][0])) + Integer.parseInt(pointArys[i][1]));
                }
            }
        for (String teamName: pointMap.keySet()) {
            System.out.println(teamName + "の合計得点は、" + pointMap.get(teamName) + "点です。");
        }
    }
}
