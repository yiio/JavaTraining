package jp.co.goalist.array;

public class kadai4 {

    public static void main(String[] args) {
        String[][] goalistAll = {
            {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
            {"入倉", "盛次", "飯尾", "チナパ"},
            {"三井", "清水", "長田"}
        };
        
        for(int x=0;x<goalistAll.length;x++){
            for(int y=0;y<goalistAll[x].length;y++){
                System.out.println(goalistAll[x][y]);
            }
        }
    }

}
