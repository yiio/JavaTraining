package jp.co.goalist.objectsiko;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import jp.co.goalist.objectsiko.Sales;
import jp.co.goalist.objectsiko.ItemMasta2;
import jp.co.goalist.objectsiko.SalesItemGroup;

public class Kadai2 {

    
    public static void main(String[] args) {
        
        
        List<SalesItemGroup> itemGroups = null;
        
        Path salesItemGroupPath = Paths.get("../../resources/salesItemGroup.csv");
        try (BufferedReader br = Files.newBufferedReader(salesItemGroupPath)) {
            
            String head = br.readLine();
            String[] elemNames = head.split(",");
            
            itemGroups = br.lines()
                           .map(line -> line.split(","))
                           .map(vals -> new SalesItemGroup(vals[0], vals[1]))
                           .collect(Collectors.toList());
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        List<ItemMasta2> itemMastas = null;
        
        Path salesItemPath = Paths.get("../../resources/salesItem2.csv");
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String head = br.readLine();
            String[] elemNames = head.split(",");
            
            itemMastas = br.lines()
                            .map(line -> line.split(","))
                            .map(vals -> new ItemMasta2(vals[0], vals[1], vals[2], vals[3]))
                            .collect(Collectors.toList());
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        List<Sales> sales = new ArrayList<>();
        
        Path salesListPath = Paths.get("../../resources/salesList2.csv");
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line = br.readLine();//first line
            String[] elemNames = line.split(",");
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                //SalesItem(day, code, count)
                Sales sale = new Sales(elem[0], elem[1], elem[2]);
                sales.add(sale);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //super inefficiency
        //頭が悪い
        //わざわざクラスを作成するメリットとは...
        Map<String, Integer> groupRevenueMap = new HashMap<>();
        for(Sales sale : sales){
            int price = 0;
            for(ItemMasta2 im : itemMastas){
                if(im.getItemCode().equals(sale.getItemCode())){
                    price = Integer.parseInt(im.getItemPrice());
                }
            }
            
            String gc = sale.getItemCode().substring(0,1);
            if(!groupRevenueMap.containsKey(gc)){
                groupRevenueMap.put(gc, price * sale.getCount());
            }else{
                groupRevenueMap.put(gc, price * sale.getCount()+groupRevenueMap.get(gc));
            }
        }
        
        for(String gc : groupRevenueMap.keySet()){
            Optional<String> groupName = itemGroups.stream()
                                                   .filter(ig -> ig.getGroupCode().equals(gc))
                                                   .map(ig -> ig.getGroupName())
                                                   .findFirst();
            
            NumberFormat nfNum = NumberFormat.getNumberInstance();
            System.out.println("「" + groupName.orElse("ねぇよ") + "」の販売合計金額は、" + nfNum.format((long)groupRevenueMap.get(gc))
                               + "円です。");
            
        }
        
        
        
        
        
        
    }

}
