package jp.co.goalist.analyze;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

public class kadai8 {

    
    public static void main(String[] args) {
        
        Path salesListPath = Paths.get("../../resources/salesList.csv");
        Path salesItemPath = Paths.get("../../resources/salesItem.csv");
        
        Map<String,String> codeItemName = new HashMap<>();
        Map<String,String> codePrice = new HashMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesItemPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                
                codeItemName.put(elem[0],elem[1]);
                codePrice.put(elem[0],elem[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        Map<String,List<String>> dateItemCount = new TreeMap<>();
        
        try (BufferedReader br = Files.newBufferedReader(salesListPath)) {
            
            String line;
            line = br.readLine();//first line
            String[] elemName = line.split(",");
            
            while ((line = br.readLine()) != null) {
                
                String[] elem = line.split(",");
                if(!dateItemCount.containsKey(elem[0])){
                    List<String> list = new ArrayList<>();
                    //超絶頭が悪い
                    //date -> List:[Item][Item][Item][Item]...
                    for(int i=0;i<Integer.parseInt(elem[2]);i++){
                        list.add(elem[1]);
                    }
                    dateItemCount.put(elem[0],list);
                }else{
                    for(int i=0;i<Integer.parseInt(elem[2]);i++){
                        dateItemCount.get(elem[0]).add(elem[1]);
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //check
        for(String date : dateItemCount.keySet()){
            int sum=0;
            
            for(String code : dateItemCount.get(date)){
                sum += Integer.parseInt(codePrice.get(code));
            }
            System.out.println(date+" , "+sum+"円");
        }
        
        
        Path filePath = Paths.get("../../resources/kadai1answer.csv"); // 書き込み対象ファイルの場所を指定
        //Files.deleteIfExists(filePath); // 既に存在してたら削除
        //Files.createFile(filePath); // ファイル作成
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            bw.write("販売日,売上総額");
            bw.newLine();
            
            for(int i=0;i<2;i++){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, i);
                cal.set(Calendar.YEAR, 2017);
                int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                for (int j = 0; j < maxDay; j++) {
                    cal.set(Calendar.DAY_OF_MONTH, j + 1);
                    String date = df.format(cal.getTime());
                    if(dateItemCount.containsKey(date)){
                        int sum = 0;
                        for(String code : dateItemCount.get(date)){
                            sum += Integer.parseInt(codePrice.get(code));
                        }
                        bw.write("\""+date+"\",\""+sum+"円\"");
                        
                    }else{
                        bw.write("\""+date+"\",\"0円\"");
                    }
                    bw.newLine();
                }
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

}
