package jp.co.goalist.hello;

public class Q4 {
    public static void main(String[]args) {
        String[][] goalistAll = {
                {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
                {"入倉", "盛次", "飯尾", "チナパ"},
                {"三井", "清水", "長田"}
            };

        for (int i = 0; i < goalistAll.length; i++) {
            for (int j = 0; j < goalistAll[i].length; j++) {
                System.out.println(goalistAll[i][j]);
            }
        }
    }
}
