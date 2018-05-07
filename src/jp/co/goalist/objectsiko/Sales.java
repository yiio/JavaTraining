package jp.co.goalist.objectsiko;

public class Sales {
    
    private String itemCode;
    private String date;
    private int count;
    
    Sales(String day, String code, String number){
        date = day;
        itemCode = code;
        count = Integer.parseInt(number);
    }
    
    public String getDate(){
        return date;
    }
    
    public String getItemCode(){
        return itemCode;
    }
    
    public int getCount(){
        return count;
    }
    
}
