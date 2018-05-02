package jp.co.goalist.string;

import java.io.*;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.InvalidParameterException;

public class Challenge {

    public static void main(String[] args) {
        
        String fileName = "jp/co/goalist/string/nengetsu.txt";
        String str;
        String nomalText;
        
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            System.out.println("元データ,変換後データ");
            while((str = br.readLine()) != null){
                nomalText = arrangement(str);
                System.out.println(str + "," + nomalText);
            }
            
            br.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
        
    }
    
    public static String arrangement(String line){
        
        String nomalText = Normalizer.normalize(line, Normalizer.Form.NFKC);
        nomalText = nomalText.replaceAll("元","1");
        nomalText = nomalText.replaceAll("年","/");
        nomalText = nomalText.replaceAll("月","/");
        nomalText = nomalText.replaceAll("日","/");
        nomalText = nomalText.replaceAll(" ","");
        nomalText = nomalText.replaceAll("S.","昭和");
        nomalText = nomalText.replaceAll("設立","");
        nomalText = nomalText.replaceAll("\\(.*?\\)","");
        String era = "(明治|大正|昭和|平成)([0-9]*?)/";
        
        Pattern kansuji = Pattern.compile("[零一二三四五六七八九十百千万億]+");
        Matcher kanjiMatch = kansuji.matcher(nomalText);
        while(kanjiMatch.find()){
            nomalText = nomalText.replaceAll(kanjiMatch.group(),String.valueOf(convertPositiveKanjiNumber(kanjiMatch.group())));
        }
        
        Pattern eraptn = Pattern.compile(era);
        Matcher eramatch = eraptn.matcher(nomalText);
        if(eramatch.find()){
            int eranum;
            if(nomalText.substring(0,2).equals("明治")){
                eranum = Integer.parseInt(eramatch.group(2))+1867;
                nomalText = nomalText.replaceAll("明治[0-9]{1,2}/",String.valueOf(eranum)+"/");
            }
            if(nomalText.substring(0,2).equals("大正")){
                eranum = Integer.parseInt(eramatch.group(2))+1911;
                nomalText = nomalText.replaceAll("大正[0-9]{1,2}/",String.valueOf(eranum)+"/");
            }
            if(nomalText.substring(0,2).equals("昭和")){
                eranum = Integer.parseInt(eramatch.group(2))+1925;
                nomalText = nomalText.replaceAll("昭和[0-9]{1,2}/",String.valueOf(eranum)+"/");
            }
            if(nomalText.substring(0,2).equals("平成")){
                eranum = Integer.parseInt(eramatch.group(2))+1988;
                nomalText = nomalText.replaceAll("平成[0-9]{1,2}/",String.valueOf(eranum)+"/");
            }
        }
        
        String dateFormat = "([0-9]{1})/";
        Pattern dateptn = Pattern.compile(dateFormat);
        Matcher datematch = dateptn.matcher(nomalText);
        while(datematch.find()){
            if(datematch.group(1).length()==1){
                nomalText = nomalText.replaceAll("/"+datematch.group(1)+"/","/0"+datematch.group(1)+"/");
            }
        }
        
        if(nomalText.charAt(nomalText.length()-1) == '/'){
            nomalText = nomalText.substring(0,nomalText.length()-1);
        }
        return nomalText;
        
    }
    
    
    
    //https://blogs.yahoo.co.jp/dk521123/36751924.html よりほぼコピー
    /**
     * 漢数字（正の整数）（をアラビア数字(int型)に変換する
     * @param targetValue 対象値（漢字・正の整数）
     * @return 正の整数（int型）
     */
    public static int convertPositiveKanjiNumber(String targetValue) {
        
        if (targetValue == null || "".equals(targetValue)) {
            throw new IllegalArgumentException("Empty");
        }
        
        if ("零".equals(targetValue)) {
            return 0;
        }
        
        int firstDegit = 1;
        int fourthDegit = 0;
        int total = 0;
        for (int i = 0; i < targetValue.length(); i++) {
            char kanjiNumber = targetValue.charAt(i);
            switch (kanjiNumber) {
                case '一':
                    firstDegit = 1;
                    break;
                case '二':
                    firstDegit = 2;
                    break;
                case '三':
                    firstDegit = 3;
                    break;
                case '四':
                    firstDegit = 4;
                    break;
                case '五':
                    firstDegit = 5;
                    break;
                case '六':
                    firstDegit = 6;
                    break;
                case '七':
                    firstDegit = 7;
                    break;
                case '八':
                    firstDegit = 8;
                    break;
                case '九':
                    firstDegit = 9;
                    break;
                case '十':
                    fourthDegit += (firstDegit != 0 ? firstDegit : 1) * 10;
                    firstDegit = 0;
                    break;
                case '百':
                    fourthDegit += (firstDegit != 0 ? firstDegit : 1) * 100;
                    firstDegit = 0;
                    break;
                case '千':
                    fourthDegit += (firstDegit != 0 ? firstDegit : 1) * 1_000;
                    firstDegit = 0;
                    break;
                case '万':
                    fourthDegit += firstDegit;
                    total += (fourthDegit != 0 ? fourthDegit : 1) * 10_000;
                    fourthDegit = 0;
                    firstDegit = 0;
                    break;
                case '億':
                    fourthDegit += firstDegit;
                    total += (fourthDegit != 0 ? fourthDegit : 1) * 100_000_000;
                    fourthDegit = 0;
                    firstDegit = 0;
                    break;
                default:
                    throw new InvalidParameterException("Found invaid parameter : " + kanjiNumber);
            }
        }
        return total + fourthDegit + firstDegit;
    }

}
