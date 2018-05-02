package jp.co.goalist.array;

public class Question4 {
    public static void main(String[] args) {
        names2();
    }

    private static void names2() {
        String[][] goalistAll = { { "岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2" }, { "入倉", "盛次", "飯尾", "チナパ" },
                { "三井", "清水", "長田" } };

        goalistAll[0][5] = goalistAll[0][5].replaceAll("増田1", "増田亜");
        goalistAll[0][6] = goalistAll[0][6].replaceAll("増田2", "増田秀");

        for (int i = 0; i < goalistAll.length; i++)
            for (int j = 0; j < goalistAll[i].length; j++)

                System.out.println(goalistAll[i][j]);

    }
}
