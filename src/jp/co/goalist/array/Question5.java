package jp.co.goalist.array;

public class Question5 {
    public static void main(String args[]) {
        Q5();
    }

    public static void Q5() {
        String[][] words = { { "さるが", "とりが", "いぬが" }, { "山で", "川で", "鬼ヶ島で" }, { "洗濯をした", "芝刈りをした", "鬼退治をした" } };
        for (int i = 0; i < words[0].length; i++) {
            for (int j = 0; j < words[1].length; j++) {
                for (int k = 0; k < words[2].length; k++) {
                    System.out.println(words[0][i] + words[1][j] + words[2][k]);
                }
            }
        }
    }
}
