package jp.co.goalist.array;

public class Question2 {
    public static void main(String[] args) {
        multiplication();
    }

    public static void multiplication() {
        int[] numbers1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] numbers2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int i = 0; i < numbers1.length; i++) {
            for (int j = 0; j < numbers2.length; j++) {
                System.out.print(numbers1[i] * numbers2[j] + " ");

            }
            System.out.println();
        }

    }
}
