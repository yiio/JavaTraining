package jp.co.goalist.array;


import java.util.HashMap;


public class Question8 {
    public static void main(String[] args) {
        // 名前とチームの配列
        final String[][] teamArys = {
            {"山田", "Aチーム"},
            {"田中", "Aチーム"},
            {"鈴木", "Bチーム"},
            {"中村", "Cチーム"},
            {"伊藤", "Dチーム"}
        };


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
    HashMap<String,String> ntMap = new HashMap<>();
    HashMap<String,Integer> tpMap = new HashMap<>();

    for (int i = 0; i < teamArys.length; i++) {
        ntMap.put(teamArys[i][0], teamArys[i][1]);
        if(!ntMap.containsKey(teamArys[i][1])) {
            tpMap.put(teamArys[i][1],0);
        }

    }
    for (int j = 0; j < pointArys.length; j++) {
        tpMap.put(ntMap.get(pointArys[j][0]), tpMap.get(ntMap.get(pointArys[j][0])) + Integer.parseInt(pointArys[j][1]));
    }
    for(String team : tpMap.keySet()){
        System.out.println(team + "の合計得点は" + tpMap.get(team) + "点です");

    }
   }
}