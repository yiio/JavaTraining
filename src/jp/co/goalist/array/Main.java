package jp.co.goalist.array;

public class Main {
    public static void main(String[] args) {
        one();
        two();
        three();
        four();
        five();
    }

        public static void one() {
            for(int i=1;i<=5;i++) {
                for(int j=10;j<=50;j+=10) {
                    System.out.println(i +"*"+j +"="+i*j);
            }
        }
        }
        public static void two() {
            for( int i=1; i<=9; i++ ) {
                for( int j=1; j<=9; j++ ) {
                     System.out.print(i*j + " ");
                 }
                      System.out.println();
                 }
        }
        public static void three() {
            String[] newGraduates= {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"};
            for(int i=0;i<newGraduates.length;i++) {
                System.out.println(newGraduates[i]);
            }
        }
        public static void four() {
            String[] []newGoalistAll = {
                    {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"},
                    {"入倉", "盛次", "飯尾", "チナパ"},
                    {"三井", "清水", "長田"}
                };
            for(int i=0;i<newGoalistAll.length;i++) {
                for(int j=0;j<newGoalistAll[i].length;j++) {
                System.out.println( newGoalistAll[i][j]);
                }
            }

        }
        public static void five() {
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
