package jp.co.goalist.array;

import java.util.HashMap;
import java.util.Map;

public class Question8 {
    public static void main(String args[]) {
        Q8();
    }

    public static void Q8() {
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

        // Mapの初期化
        Map<String,String> teamMap = new HashMap<>(); // 名前,チーム名
        Map<String,Integer> resultMap = new HashMap<>(); // チーム名,得点

        // teamMapに名前,チーム名を追加
        for(int i = 0; i < teamArys.length; i++){
            teamMap.put( teamArys[i][0], teamArys[i][1]);

        // pointMapに書いてないチーム名が出てき場合、pointMapにチーム名,得点(0点)を追加
            if(!resultMap.containsKey(teamArys[i][1])){
                resultMap.put(teamArys[i][1],0);
            }
        }

        // 名前に対応するチーム名、現在のチームの得点を取得。名前に対応した得点を加えてpointMapに上書き(?)
        for(int x=0;x<pointArys.length;x++){
            resultMap.put(teamMap.get(pointArys[x][0]), resultMap.get(teamMap.get(pointArys[x][0])) +  Integer.parseInt(pointArys[x][1]));
        }

        // 拡張for…すべての要素に対して順番に処理を行う  keySet…Mapに含まれるキーのセットを返す
        for(String team : resultMap.keySet()){
            System.out.println(team+"の合計得点は、"+resultMap.get(team)+"点です。");
        }
    }
}