package jp.co.goalist.objectsiko;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import jp.co.goalist.objectsiko.SalesItem;
import jp.co.goalist.objectsiko.ItemMasta;
import jp.co.goalist.objectsiko.PriceReviseDay;

public class Kadai1 {

    
    public static void main(String[] args) {
        
        //構造の同じブロックが並んでいてまとめたいけど、まとめ方が分かりません。
        /*
        List<ItemMasta> itemMastas = new ArrayList<>();
        
        Path salesItemPath = Paths.get("../../resources/salesItem.csv");
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String line = br.readLine();//first line
            String[] elemNames = line.split(",");
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                //ItemMasta(code, name, price)
                ItemMasta item = new ItemMasta(elem[0], elem[1], elem[2]);
                itemMastas.add(item);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        
        //stream var.
        List<ItemMasta> itemMastas = null;
        
        Path salesItemPath = Paths.get("../../resources/salesItem.csv");
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String head = br.readLine();
            String[] elemNames = head.split(",");
            
            itemMastas = br.lines()
                            .map(line -> line.split(","))
                            .map(vals -> new ItemMasta(vals[0], vals[1], vals[2]))
                            .collect(Collectors.toList());
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        List<SalesItem> salesItems = new ArrayList<>();
        
        Path salesListPath = Paths.get("../../resources/salesList.csv");
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line = br.readLine();//first line
            String[] elemNames = line.split(",");
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                //SalesItem(day, code, count)
                SalesItem item = new SalesItem(elem[0], elem[1], elem[2]);
                salesItems.add(item);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //List<PriceReviseDay> revisionDays = new ArrayList<>();
        
        Path priceChagePath = Paths.get("../../resources/salesPrice.csv");
        try (BufferedReader br = Files.newBufferedReader(priceChagePath)) {
            
            String line = br.readLine();//first line
            String[] elemNames = line.split(",");
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                
                /*
                //PriceReviseDay(code, day, pirce)
                PriceReviseDay day = new PriceReviseDay(elem[0], elem[1], elem[2]);
                revisionDays.add(day);
                 */
                
                for(ItemMasta im : itemMastas){
                    if(im.getItemCode().equals(elem[0])){
                        im.revisionDays.put(elem[1], elem[2]);
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //inefficiency
        //これまたかなり頭が悪い
        Path filePath = Paths.get("../../resources/kadai1-object-answer.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            
            bw.write("\"販売日\",\"売上総額\"");
            bw.newLine();
            
            for(int month = 0; month < 2; month++){
                
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, 2017);
                int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                
                for (int j = 0; j < maxDay; j++) {
                    
                    cal.set(Calendar.DAY_OF_MONTH, j + 1);
                    String date = df.format(cal.getTime());
                    
                    /*
                    //There is room for improvement.
                    //ver. 1
                    //use PriceReviseDay class
                    for(PriceReviseDay prd : revisionDays){
                        if(date.equals(prd.getDate())){
                            for(ItemMasta im : itemMastas){
                                im.revisePrice(prd.getItemCode(), prd.getNewPrice());
                            }
                        }
                    }
                     */
                    
                    /*
                    //There is room for improvement.
                    //ver. 2
                    //give revision days into ItemMasta class
                    for(ItemMasta im : itemMastas){
                        if(im.revisionDays.containsKey(date)){
                            im.revisePrice(im.revisionDays.get(date));
                        }
                    }
                     */
                    
                    //ver. 3
                    //use stream
                    itemMastas.stream()
                              .filter(im -> im.revisionDays.containsKey(date))
                              .forEach(im -> im.revisePrice(im.revisionDays.get(date)));
                    
                    
                    //There is room for improvement.
                    int sum = 0;
                    for(SalesItem sa : salesItems){
                        if(date.equals(sa.getDate())){
                            for(ItemMasta im : itemMastas){
                                if(sa.getItemCode().equals(im.getItemCode())){
                                    sum += Integer.parseInt(im.getItemPrice()) * sa.getCount();
                                }
                            }
                        }
                    }
                    
                    NumberFormat nfNum = NumberFormat.getNumberInstance();
                    bw.write("\"" + date + "\",\"" + nfNum.format((long)sum) + "円\"");
                    bw.newLine();
                    
                }
            }
            
            bw.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    /*
    //最初の3ブロックをまとめたいけど、やり方が分からない。
    //interfaceを使えばできる？
    public static List<?> setEachInfo(String filename){
        List<?> list = new ArrayList<>();
        
        Path salesListPath = Paths.get(filename);
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line = br.readLine();//first line
            String[] elemNames = line.split(",");
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                <?> item = new <?>(elem[0], elem[1], elem[2]);
                list.add(item);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
     */

}
