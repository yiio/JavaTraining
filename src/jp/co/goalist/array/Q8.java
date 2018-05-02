package jp.co.goalist.array;

public class Q8 {
    public static void main(String[] args) {

        // 名前とチームの配列
        final String[][] teamArys = { { "山田", "Aチーム" }, { "田中", "Aチーム" }, { "鈴木", "Bチーム" }, { "中村", "Cチーム" },
                { "伊藤", "Dチーム" } };

        // 名前と得点の配列
        final String[][] pointArys = { { "山田", "1" }, { "山田", "3" }, { "山田", "1" }, { "伊藤", "4" }, { "山田", "2" },
                { "中村", "1" }, { "中村", "1" }, { "山田", "3" }, { "鈴木", "2" }, { "山田", "4" }, { "伊藤", "5" }, { "山田", "2" },
                { "鈴木", "3" }, { "山田", "1" }, { "田中", "2" } };

        int pointA = 0;
        int pointB = 0;
        int pointC = 0;
        int pointD = 0;

        for (int i = 0; i < pointArys.length; i++) {
            if (pointArys[i][0] == "山田" || pointArys[i][0] == "田中") {
                pointA += Integer.parseInt(pointArys[i][1]);
            } else if (pointArys[i][0] == "鈴木") {
                pointB += Integer.parseInt(pointArys[i][1]);
            } else if (pointArys[i][0] == "中村") {
                pointC += Integer.parseInt(pointArys[i][1]);
            } else {
                pointD += Integer.parseInt(pointArys[i][1]);
            }
        }

        // 点数を出す
        System.out.println("Aチームの合計得点は、" + pointA + "点です。");
        System.out.println("Bチームの合計得点は、" + pointB + "点です。");
        System.out.println("Cチームの合計得点は、" + pointC + "点です。");
        System.out.println("Dチームの合計得点は、" + pointD + "点です。");

    }
}
