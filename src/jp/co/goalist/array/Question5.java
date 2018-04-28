package jp.co.goalist.array;

public class Question5 {

    public static void main(String[] args) {
        final String[][] arys = {
                {"さるが", "とりが", "いぬが"},
                {"山で", "川で", "鬼ヶ島で"},
                {"洗濯をした", "芝刈りをした", "鬼退治をした"}
            };
        for (String who : arys[0]) {
            for (String where : arys[1]) {
                for (String what : arys[2]) {
                    System.out.println(who + where + what);
                }
            }
        }
    }

}
