package jp.co.goalist.array;

public class Question4 {

    public static void main(String[] args) {
        final String[][] goalistAll = {
                {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
                {"入倉", "盛次", "飯尾", "チナパ"},
                {"三井", "清水", "長田"}
            };
        for (String[] ary : goalistAll) {
            for (String name : ary) {
                System.out.println(name);
            }
        }
    }

}
