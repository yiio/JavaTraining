package jp.co.goalist.array;

public class Question5 {
    
    public static void main(String[] args) {
        String[][] words = {
            {"‚³‚é‚ª", "‚Æ‚è‚ª", "‚¢‚Ê‚ª"},
            {"R‚Å", "ì‚Å", "‹Sƒ–“‡‚Å"},
            {"ô‘ó‚ğ‚µ‚½", "ÅŠ ‚è‚ğ‚µ‚½", "‹S‘Ş¡‚ğ‚µ‚½"}
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
