package jp.co.goalist.array;

import java.util.Map;
import java.util.HashMap;

public class kadai8 {

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
        
        Map<String,String> teamMap = new HashMap<>();//name , team
        Map<String,Integer> pointMap = new HashMap<>();//team , point
        
        for(int x=0;x<teamArys.length;x++){
            teamMap.put(teamArys[x][0],teamArys[x][1]);
            if(!pointMap.containsKey(teamArys[x][1]))
                pointMap.put(teamArys[x][1],0);
        }
        
        for(int x=0;x<pointArys.length;x++){
            pointMap.put(teamMap.get(pointArys[x][0]), pointMap.get(teamMap.get(pointArys[x][0])) +  Integer.parseInt(pointArys[x][1]));
        }
        
        for(String team : pointMap.keySet()){
            System.out.println(team+"の合計得点は、"+pointMap.get(team)+"点です。");
        }
        
    }

}
