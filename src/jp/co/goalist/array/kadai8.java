package jp.co.goalist.array;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class kadai8 {
    private static final Object name = null;

    public static void main(String[] args) {
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
        Map<String,String>teamMap =new HashMap<String,String>();

        for(int i=0;i<teamArys.length;i++) {
            String[] teamAry =teamArys[i];
            String name=teamAry[0];
            String team=teamAry[1];
            teamMap.put(name, team);
            //System.out.println(name);
        }
        Map<String,Integer>pointMap = new TreeMap<String,Integer>();

        for(int j=0;j<pointArys.length;j++) {
            String[] pointAry=pointArys[j];
            int point =Integer.parseInt(pointAry[1]);
           
            String team = teamMap.get(name);

            if(pointMap.containsKey(team)) {
                int sum =pointMap.get(team)+point;
                pointMap.put(team, sum);
            }else {
                pointMap.put(team, point);
            }
        }
        for(Map.Entry<String,Integer>entry : pointMap.entrySet()) {
            String team = entry.getKey();
            String sum = String.valueOf(entry.getValue());
            System.out.println(team+"の合計得点は、"+ sum +"点です");

        }

            }
    }


