package jp.co.goalist.array;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

        // 名前とチームの配列から、名前とチームの対応表を作る
        Map<String, String> nameTeamMap = new HashMap<String, String>();
        for (String[] teamAry : teamArys) {
            // 名前とチームをそれぞれ取り出す
            String name = teamAry[0];
            String team = teamAry[1];
            // 対応表に追加する
            nameTeamMap.put(name, team);
        }

        // 各チームの合計点を保持するマップを作る
        Map<String, Integer> teamPointMap = new TreeMap<String, Integer>(); // 出力時の順番をkeyを昇順にしたいので今回はTreeMapを使う

        // 名前と得点の配列を１つずつ見ていく
        for (String[] pointAry : pointArys) {
            // 名前と得点をそれぞれ取り出す
            String name = pointAry[0];
            int point = Integer.parseInt(pointAry[1]); // 得点は数値に変換しておく
            String team = nameTeamMap.get(name); // 名前とチームの対応表からチーム名を調べる
            // チーム名に応じて合計する変数を選んで足す
            if (teamPointMap.containsKey(team)) { // 各チームの合計点マップにすでにチームが登録されている場合
                int sum = teamPointMap.get(team) + point;
                teamPointMap.put(team, sum);
            } else {
                teamPointMap.put(team, point);
            }
        }

        // 最終結果を出力
        for (Map.Entry<String, Integer> entry : teamPointMap.entrySet()) {
            String team = entry.getKey();
            String sum = String.valueOf(entry.getValue());
            System.out.println(team + "の合計得点は、" + sum + "点です。");
        }
        System.out.println();
    }

}
