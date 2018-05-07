package jp.co.goalist.objectsiko;

public class ItemMasta2 {
    
    private String groupCode;
    private String itemCode;
    private String itemName;
    private String itemPrice;
    
    ItemMasta2(String gcode, String icode, String name, String price){
        groupCode = gcode;
        itemCode = icode;
        itemName = name;
        itemPrice = price;
    }
    
    public String getGroupCode(){
        return groupCode;
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
