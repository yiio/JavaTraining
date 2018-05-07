package jp.co.goalist.objectsiko;

//This class name "SalesItem" does't conform to what is required. sorry.
//This is used in kadai 1 and 2.
public class SalesItem {
    
    private String itemCode;
    private String date;
    private int count;
    
    SalesItem(String day, String code, String number){
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
