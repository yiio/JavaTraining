package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ReturnJson {

    private String path;
    
    public ReturnJson(String filePath){
        path = filePath;
    }
    
    public String makeJson(){
        
        List<Map<String, String>> jsonList = new ArrayList<>();
        
        Path filePath = Paths.get(path);
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            
            String line;
            line = br.readLine();/* first line */
            String[] elemNames = line.split(",");
            List<String> elemNameList = new ArrayList<>();
            
            for(String elemName : elemNames){
                /* delete (xxxx) */
                elemNameList.add(elemName.replaceAll("\\(.*?\\)", ""));
                //elemNameList.add(elemName);
            }
            
            while ((line = br.readLine()) != null) {
                String[] elem = line.split(",");
                List<String> list = Arrays.asList(elem);
                Map<String, String> map = new HashMap<>();
                for(int i = 0; i < list.size(); i++){
                    map.put(elemNameList.get(i), list.get(i));
                }
                jsonList.add(map);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String json = convertMapListToJson(jsonList);
        
        return json;
        
    }
    
    public String convertMapListToJson(List<Map<String, String>> maplist){
        
        String json = "{\n";
        
        for(Map<String, String> map: maplist){
            json += "\t{\n";
            for(String key : map.keySet()){
                
                /* if number ->  xxxx  */
                /* else      -> "xxxx" */
                String value = map.get(key);
                if(isNumber(value)){
                    json += "\t\t\"" + key + "\":" + value + ", \n";
                }else{
                    json += "\t\t\"" + key + "\":\""+ value + "\", \n";
                }
                //json += "\t\t\"" + key + "\":\""+ map.get(key) + "\", \n";
                
            }
            json = json.substring(0, json.length()-3);
            json += " \n\t}, \n";
        }
        json = json.substring(0, json.length()-3);
        json += "\n}";
        
        return json;
        
    }
    
    
    public boolean isNumber(String str){
        
        String regex = "^[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
        
    }
    
}
