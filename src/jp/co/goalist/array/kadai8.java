package jp.co.goalist.array;

import java.util.Map;
import java.util.HashMap;

public class Kadai8 {

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
        
        for(String[] nameTeam : teamArys){
            teamMap.put(nameTeam[0], nameTeam[1]);
            if(!pointMap.containsKey(nameTeam[1])){
                pointMap.put(nameTeam[1], 0);
            }
        }
        
        for(String[] namePoint : pointArys){
            pointMap.put(teamMap.get(namePoint[0]), pointMap.get(teamMap.get(namePoint[0])) + Integer.parseInt(namePoint[1]));
        }
        
        for(String team : pointMap.keySet()){
            System.out.println(team + "の合計得点は、" + pointMap.get(team) + "点です。");
        }
        
    }

}
