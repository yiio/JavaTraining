package jp.co.goalist.array;

public class Question4 {
    
    public static void main(String[] args) {
        String[][] goalists = {
            {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
            {"入倉", "盛次", "飯尾", "チナパ"},
            {"三井", "清水", "長田"}
        };
        for (int i = 0; i < 3; i++) {
            int k = goalists[i].length;
            for (int j = 0; j < k; j++) {
                System.out.println(goalists[i][j]);
            }
        }
        
    }

}
