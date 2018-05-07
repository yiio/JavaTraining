package jp.co.goalist.objectsiko;

import java.util.Map;
import java.util.HashMap;

public class ItemMasta {
    
    private String itemCode;
    private String itemName;
    private String itemPrice;
    public Map<String, String> revisionDays;//date, price
    
    ItemMasta(String code, String name, String price){
        itemCode = code;
        itemName = name;
        itemPrice = price;
        revisionDays = new HashMap<>();
    }
    
    public String getItemCode(){
        return itemCode;
    }

    public String getItemName(){
        return itemName;
    }
    
    public String getItemPrice(){
        return itemPrice;
    }
    
    public void revisePrice(String code, String price){
        
        if(itemCode.equals(code)){
            itemPrice = price;
        }
        
        return;
    }
    
    public void revisePrice(String price){
        itemPrice = price;
        return;
    }
    
}
