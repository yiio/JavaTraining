package jp.co.goalist.array;

public class kadai6 {

    public static void main(String[] args) {
        String[][] words = {
            {"さるが", "とりが", "いぬが", "桃太郎が","田中マルクストゥーリオが"},
            {"山で", "川で", "鬼ヶ島で", "竜宮城で"},
            {"おじいさんと", "おばあさんと", "金太郎と", "浦島太郎と","実の息子と","最近別れた恋人と"},
            {"洗濯をした", "芝刈りをした", "鬼退治をした"}
        };
        
        
        //各行の要素数を値に持つリストを用意
        //ループが一つ進む毎に一つをカウントに加え、その値と上記のリストとによって表示する文字列を判別する
        int[] maxlist = new int[words.length];
        int[] positions = new int[words.length];
        int combNum = 1;
        for(int x=0;x<words.length;x++){
            maxlist[x] = words[x].length;
            positions[x] = 0;
            combNum *= words[x].length;
        }
        
        
        for(int x=0;x<combNum;x++){
            for(int y=0;y<words.length;y++){
                System.out.print(words[y][positions[y]]);
            }System.out.println();
            positions[words.length-1]++;
            checkAndArrangeArray(positions,maxlist);
            
        }
        
    }
    
    public static void checkAndArrangeArray(int[] target,int[] base){
        boolean flag;
        while(true){
            flag = true;
            for(int x=base.length-1;x>=0;x--){
                if(target[x]==base[x]){
                    target[x] = 0;
                    if(!(x==0))
                        target[x-1]++;
                    flag = false;
                }
            }
            if(flag)
                break;
        }
    
    
    }

}
