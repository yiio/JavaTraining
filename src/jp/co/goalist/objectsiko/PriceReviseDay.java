package jp.co.goalist.objectsiko;

public class PriceReviseDay {
    
    private String itemCode;
    private String date;
    private String newPrice;
    
    PriceReviseDay(String code, String day, String price){
        itemCode = code;
        date = day;
        newPrice = price;
    }
    
    public String getItemCode(){
        return itemCode;
    }

    public String getDate(){
        return date;
    }
    
    public String getNewPrice(){
        return newPrice;
    }
    
}
