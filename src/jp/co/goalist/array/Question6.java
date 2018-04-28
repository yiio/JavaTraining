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
        ArrayList<String> sentenceList = joinWords(arys);
        for (String sentence : sentenceList) {
            System.out.println(sentence);
        }
    }

    /**
     * 文字列の配列を再帰的に連結処理する
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

        // 次の配列があれば処理しておく
        ArrayList<String> sentenceList = joinWords(Arrays.copyOfRange(arys, 1, arys.length));
        for (String word : arys[0]) {
            for (String sentence : sentenceList) {
                res.add(word + sentence);
            }
        }
        return res;
    }
}
