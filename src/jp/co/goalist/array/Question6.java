package jp.co.goalist.array;

import java.util.ArrayList;
import java.util.Arrays;

public class Question6 {

    public static void main(String[] args) {
        final String[][] arys = {
                {"さるが", "とりが", "いぬが", "桃太郎が"},
                {"山で", "川で", "鬼ヶ島で", "竜宮城で"},
                {"おじいさんと", "おばあさんと", "金太郎と", "浦島太郎と"},
                {"洗濯をした", "芝刈りをした", "鬼退治をした"}
            };

        // その1
        ArrayList<String> sentenceList = joinWords(arys);
        for (String sentence : sentenceList) {
            System.out.println(sentence);
        }

        // その2
        printCombination(0, arys, "");
    }

    /**
     * 文字列の配列を再帰的に連結して、文のリストを作っていく
     */
    private static ArrayList<String> joinWords(String[][] arys) {
        ArrayList<String> res = new ArrayList<String>();
        if (arys == null || arys.length == 0) {
            return res;
        }

        if (arys.length == 1) {
            for (String word : arys[0]) {
                res.add(word);
            }
            return res;
        }

        // 次の配列があれば連結しておく
        ArrayList<String> sentenceList = joinWords(Arrays.copyOfRange(arys, 1, arys.length));
        for (String word : arys[0]) {
            for (String sentence : sentenceList) {
                res.add(word + sentence);
            }
        }
        return res;
    }

    /**
     * かなり短い書き方、混乱する
     * @param i 深度
     * @param arys 最初の連想配列
     * @param outStr 連結済みの文字列
     */
    private static void printCombination(int i, String[][] arys, String outStr) {
        if (i == arys.length) { // 最深部まで到達したら文が完成する
            System.out.println(outStr);
            return;
        }
        for (int j = 0; j < arys[i].length; j++) {
            printCombination(i + 1, arys, outStr + arys[i][j]); // 次のグループの単語をくっつけて渡す
        }
    }
}
