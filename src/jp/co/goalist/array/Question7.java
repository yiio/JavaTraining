package jp.co.goalist.array;

public class Question7 {
    public static void main(String[] args) {
        match();
    }
    private static void match () {
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
        int aTeam =0;
        int bTeam =0;
        for (int i = 0; i < pointArys.length; i++) {
            if ((pointArys[i][0] == "山田")||(pointArys[i][0] == "田中")) {
                aTeam += Integer.parseInt(pointArys[i][1]);

            }else {
                bTeam += Integer.parseInt(pointArys[i][1]);
            }
        }
        System.out.println("Aチームの合計得点は" + aTeam + "点です");
        System.out.println("Bチームの合計得点は" + bTeam + "点です");
    }
}
