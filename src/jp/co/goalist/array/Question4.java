package jp.co.goalist.array;

public class Question4 {
    public static void main(String args[]) {
        Q4();
    }
    public static void Q4() {
        String[][] goalistAll = {
                {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田亜", "増田秀"},
                {"入倉", "盛次", "飯尾", "チナパ"},
                {"三井", "清水", "長田"}
            };
        System.out.println("----- for文 -----");
        for(int i = 0; i < goalistAll.length; i++) {
            for(int j = 0; j < goalistAll[i].length; j++) {
                System.out.println(goalistAll[i][j]);
            }
        }

        System.out.println(" ");
        System.out.println("----- 拡張for文 -----");
        for(String[] x : goalistAll ) {
            for(String name : x ) {
                System.out.println(name);
            }
        }
    }
}