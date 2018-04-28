package jp.co.goalist.array;

import java.util.HashMap;
import java.util.Map;

public class Question7 {

    public static void main(String[] args) {
        // 名前とチームの配列
        final String[][] teamArys = {
            {"山田", "Aチーム"},
            {"田中", "Aチーム"},
            {"鈴木", "Bチーム"},
            {"中村", "Bチーム"},
            {"伊藤", "Bチーム"}
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
        Map<String, String> teamMap = new HashMap<String, String>();
        for (String[] teamAry : teamArys) {
            // 名前とチームをそれぞれ取り出す
            String name = teamAry[0];
            String team = teamAry[1];
            // 対応表に追加する
            teamMap.put(name, team);
        }

        // Aチームの合計点を保持する変数
        int sumA = 0;
        // Bチームの合計点を保持する変数
        int sumB = 0;

        // 名前と得点の配列を１つずつ見ていく
        for (String[] pointAry : pointArys) {
            // 名前と得点をそれぞれ取り出す
            String name = pointAry[0];
            int point = Integer.parseInt(pointAry[1]); // 得点は数値に変換しておく
            // この得点がどのチームのものかを知りたいが、名前と得点の配列からだけではチーム名がわからないので、名前とチームの対応表からチーム名を調べる
            String team = teamMap.get(name);
            // チーム名に応じて合計する変数を選んで足す
            if ("Aチーム".equals(team)) {
                sumA = sumA + point;
            } else if ("Bチーム".equals(team)) {
                sumB = sumB + point;
            }
        }

        // 最終結果を出力
        System.out.println("Aチームの合計得点は、" + sumA + "点です。");
        System.out.println("Bチームの合計得点は、" + sumB + "点です。");
    }

}
