package jp.co.goalist.array;

public class Kadai4 {

    public static void main(String[] args) {
        String[][] goalistAll = {
            {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
            {"入倉", "盛次", "飯尾", "チナパ"},
            {"三井", "清水", "長田"}
        };
        
        for(String[] names : goalistAll){
            for(String name : names){
                System.out.println(name);
            }
        }
    }

}
