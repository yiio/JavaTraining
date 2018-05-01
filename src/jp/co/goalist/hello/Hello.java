package jp.co.goalist.hello;

public class Hello {

   	
	public static void main(String[] args) {
		String[] name = {"コウキ", "コショー", "サトウ"};
		String hello ="オッス！";
		
		
        System.out.println(hello);
        System.out.println("ぼくの名前は" + name[0] + "です。" + hello);
        System.out.println("前には" + name[1] + "が座ってます");
        System.out.println("隣には" + name[2] + "がいます");
    }

}
