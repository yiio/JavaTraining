package jp.co.goalist.array;

public class Question5 {
    
    public static void main(String[] args) {
        String[][] words = {
            {"さるが", "とりが", "いぬが"},
            {"山で", "川で", "鬼ヶ島で"},
            {"洗濯をした", "芝刈りをした", "鬼退治をした"}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    System.out.println(words[0][i] + words[1][j] + words[2][k]);
                }
            }
        }
    }
}
