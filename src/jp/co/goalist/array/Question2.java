package jp.co.goalist.array;

public class Question2 {
    public static void main(String[] args) {
        // 課題3
        String[] newGraduates = { "岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2" };

        for (int i = 0; i < newGraduates.length; i++) {
            System.out.println(newGraduates[i]);
        }

        // 課題4
        String[][] goalistAll = { { "岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2" }, { "入倉", "盛次", "飯尾", "チナパ" },
                { "三井", "清水", "長田" } };

        for (int j = 0; j < goalistAll.length; j++) {
            for (int k = 0; k < goalistAll[j].length; k++) {
                System.out.println(goalistAll[j][k]);
            }
        }

        //課題5
        word();

    }

    public static void word() {
        String[][] words = {
                { "さるが", "とりが", "いぬが" }, 
                { "山で", "川で", "鬼ヶ島で" }, 
                { "洗濯をした", "芝刈りをした", "鬼退治をした" }
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
