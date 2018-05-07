package jp.co.goalist.array;

public class kadai6 {
    public static void main (String[]args) {
        String[][] words = {
                {"さるが", "とりが", "いぬが", "桃太郎が"},
                {"山で", "川で", "鬼ヶ島で", "竜宮城で"},
                {"おじいさんと", "おばあさんと", "金太郎と", "浦島太郎と"},
                {"洗濯をした", "芝刈りをした", "鬼退治をした"}
            };
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                for(int k=0;k<4;k++) {
                    for(int l=0;l<3;l++) {
                        System.out.println(words[0][i]+words[1][j]+words[2][k]+words[3][l]);
                    }
                }
            }
        }
    }
}
