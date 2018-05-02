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

        //HashMapクラスのオブジェクトを作成
        HashMap<String,String> nameTeamMap = new HashMap<>();
        HashMap<String,Integer> teamPointMap = new HashMap<>();

        //nameTeamMapに名前とチーム名を追加
        for (int i = 0; i < teamArys.length; i++) {
            nameTeamMap.put(teamArys[i][0], teamArys[i][1]);

            //teamAryにないチーム名が登場した場合、teamPointMapにチーム名と得点(0点)を追加
            if(!nameTeamMap.containsKey(teamArys[i][1])) {
                teamPointMap.put(teamArys[i][1],0);
            }

        }
        //teamPointMapに、チーム名とチームの得点を追加。チーム名は名前をキーとしてnameTeamMapにより取得、得点はnameTeamMapにより取得したチーム名をキーとして、teamPointMapにより取得されたチームの得点に、各人の点数を合算。
        for (int j = 0; j < pointArys.length; j++) {
            teamPointMap.put(nameTeamMap.get(pointArys[j][0]), teamPointMap.get(nameTeamMap.get(pointArys[j][0])) + Integer.parseInt(pointArys[j][1]));
        }
        //それぞれのチームの得点を出力
        for(String team : teamPointMap.keySet()){
            System.out.println(team + "の合計得点は" + teamPointMap.get(team) + "点です");

        }
    }
}