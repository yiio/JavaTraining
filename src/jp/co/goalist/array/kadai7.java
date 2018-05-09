package jp.co.goalist.array;

import java.util.HashMap;
import java.util.Map;

public class kadai7 {

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
        Map<String,String>teamMap =new HashMap<String,String>();

        for(String[] teamAry : teamArys) {
            String name = teamAry[0];
            String team = teamAry[1];
            teamMap.put(name,team);
            //System.out.println(name);
        }
        System.out.println();

        int sumA = 0;
        int sumB = 0;

        for(int j=0;j<pointArys.length;j++) {
            String[] pointAry=pointArys[j];
            String name = pointAry[0];
            int point = Integer.parseInt(pointAry[1]);
            String team = teamMap.get(name);
            if("Aチーム".equals(team)) {
                sumA = sumA + point;


            }else if("Bチーム".equals(team)) {
                sumB =sumB+point;

            }
        }
        System.out.println("Aチームの合計得点は、"+sumA+"点です");
        System.out.println("Bチームの合計得点は、"+sumB+"点です");

        /*for(String[] pointAry : pointArys) {
            String name = pointAry[0];
            int point = Integer.parseInt(pointAry[1]);
            String team = teamMap.get(name);

        }*/

    }
}

