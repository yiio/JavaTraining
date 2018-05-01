package jp.co.goalist.array;

public class kadai5 {

    public static void main(String[] args) {
        String[][] words = {
            {"さるが", "とりが", "いぬが"},
            {"山で", "川で", "鬼ヶ島で"},
            {"洗濯をした", "芝刈りをした", "鬼退治をした"}
        };
        
        //ん〜.........ダメ......
        for(int x=0;x<words[0].length;x++){
            for(int y=0;y<words[1].length;y++){
                for(int z=0;z<words[2].length;z++)
                    System.out.println(words[0][x]+words[1][y]+words[2][z]);
            }
        }
        
    }

}
