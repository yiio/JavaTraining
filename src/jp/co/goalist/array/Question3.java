package jp.co.goalist.array;

public class Question3 {
    public static void main(String[] args) {
        names1();
    }
    public static void names1() {
        String[] newGraduates = {"岩崎", "古庄", "佐藤", "鈴木", "濱田", "増田1", "増田2"};
         for (int i = 0; i < newGraduates.length; i++) {
             System.out.println(newGraduates[i]);
         }
    }
}
