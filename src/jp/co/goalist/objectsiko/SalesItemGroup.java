package jp.co.goalist.objectsiko;

public class SalesItemGroup {
    
    private String groupCode;
    private String groupName;
    private int revenue;
    
    SalesItemGroup(String code, String name){
        groupCode = code;
        groupName = name;
        revenue = 0;
    }
    
    public String getGroupCode(){ return groupCode; }
    public String getGroupName(){ return groupName; }
    public int getRevenue(){ return revenue; }
    
    public void addRevenue(String sale){
        revenue += Integer.parseInt(sale);
    }
    
    public void addRevenue(int sale){
        revenue += sale;
    }
    
}
