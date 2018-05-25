package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class Main {
    static Map<String, Integer> numberMap = new HashMap<String, Integer>(); // ƒL[‚Éu‘SŒfÚŒ”v‚ÆuŒ_–ñĞˆõv‚ğA’l‚É‚»‚ê‚ç‚ÌŒ”‚ğŠi”[
    static Map<String, Integer> prefNumberMap = new LinkedHashMap<String, Integer>(); // “s“¹•{Œ§, ŒfÚŒ”
    static Map<String, Integer> jobSalaryMap = new HashMap<String, Integer>(); // Eí, Œ‹‹‰ºŒÀ‹àŠz‚Ì‡Œv
    static Map<String, Integer> jobCountMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”
    static Map<String, Integer> nameCountMap = new HashMap<String, Integer>(); // Šé‹Æ–¼, Šé‹Æ‚²‚Æ‚ÌŒfÚŒ”
    
    // ‰Û‘è‚U—p
    static Map<String, Integer> tokyojobNumberMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”i“Œ‹“sj
    static Map<String, Integer> kanagawajobNumberMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”i_“ŞìŒ§j
    static Map<String, Integer> osakajobNumberMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”i‘åã•{j
    static Map<String, Integer> aichijobNumberMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”iˆ¤’mŒ§j
    static Map<String, Integer> saitamajobNumberMap = new HashMap<String, Integer>(); // Eí, Eí‚²‚Æ‚ÌŒfÚŒ”ié‹ÊŒ§j
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        
        int cnt = 0; // ‘SŒfÚŒ”
        int numOfContract = 0; // ŒÙ—p‹æ•ª‚ªŒ_–ñĞˆõ‚Å‚ ‚éˆÄŒ”
        
        // prefMap‚Ì‰Šú’l‚ğİ’è
        String[] prefAry = {"–kŠC“¹","ÂXŒ§","ŠâèŒ§","‹{éŒ§","H“cŒ§","RŒ`Œ§","•Ÿ“‡Œ§",
                "ˆïéŒ§","“È–ØŒ§","ŒQ”nŒ§","é‹ÊŒ§","ç—tŒ§","“Œ‹“s","_“ŞìŒ§",
                "VŠƒŒ§","•xRŒ§","ÎìŒ§","•ŸˆäŒ§","R—œŒ§","’·–ìŒ§","Šò•ŒŒ§",
                "Ã‰ªŒ§","ˆ¤’mŒ§","OdŒ§"," ‰êŒ§","‹“s•{","‘åã•{","•ºŒÉŒ§",
                "“Ş—ÇŒ§","˜a‰ÌRŒ§","’¹æŒ§","“‡ªŒ§","‰ªRŒ§","L“‡Œ§","RŒûŒ§",
                "“¿“‡Œ§","ìŒ§","ˆ¤•QŒ§","‚’mŒ§","•Ÿ‰ªŒ§","²‰êŒ§","’·èŒ§",
                "ŒF–{Œ§","‘å•ªŒ§","‹{èŒ§","­™“‡Œ§","‰«“êŒ§",
                };
        for (int i = 0; i < 47; i++) {
            prefNumberMap.put(prefAry[i], 0);
        }
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // ƒwƒbƒ_‚Ìˆ—
            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfEmp = headerList.indexOf("ŒÙ—p‹æ•ª");
            int indexOfPref = headerList.indexOf("ƒGƒŠƒA“s“¹•{Œ§");
            int indexOfJob = headerList.indexOf("Eí•ª—Ş");
            int indexOfSalary = headerList.indexOf("Œ‹‹‰ºŒÀ‹àŠz");
            int indexOfName = headerList.indexOf("Šé‹Æ–¼");
            
            
            // “à—e‚Ìˆ—i2s–ÚˆÈ~j
            while ((line = br.readLine()) != null) {
                cnt++; // ‚·‚×‚Ä‚ÌŒfÚŒ”‚ğƒJƒEƒ“ƒgi‰Û‘è‚Pj
                String[] cols = line.split(",");
                
                // uŒ_–ñĞˆõv‚ÌŒfÚŒ”‚ğƒJƒEƒ“ƒgi‰Û‘è‚Qj
                if (cols[indexOfEmp].equals("Œ_–ñĞˆõ")) {
                    numOfContract++;
                }
                
                // 47“s“¹•{Œ§‚²‚Æ‚ÌŒfÚŒ”‚ğƒJƒEƒ“ƒgi‰Û‘è‚Rj 
                String pref = cols[indexOfPref];
                // ƒGƒŠƒA“s“¹•{Œ§‚Ì•¶š—ñiu13:“Œ‹“sv‚È‚Çj‚ğ“s“¹•{Œ§–¼‚Ì‚İiu“Œ‹“sv‚È‚Çj‚É•ÏŠ·
                String[] prefCols = pref.split(":");
                String clearPref = prefCols[1];
                if (!prefNumberMap.containsKey(clearPref)) {
                    prefNumberMap.put(clearPref, 0);
                }
                int prefCnt = prefNumberMap.get(clearPref) + 1;
                prefNumberMap.put(clearPref, prefCnt);

                
                // Eí•ª—Ş‚²‚Æ‚ÌŒ‹‹‰ºŒÀ‹àŠz‚ğæ“¾‚µAjobSalaryMap‚Ì’l‚É‘«‚µ‚Ä‚¢‚­i‰Û‘è‚Sj
                if (StringUtils.isNumeric(cols[indexOfSalary])) { // Œ‹‹‰ºŒÀ‹àŠz‚ªŒfÚ‚³‚ê‚Ä‚¢‚È‚¢ƒf[ƒ^‚ÍœŠO
                    String job = cols[indexOfJob];
                    int salary = Integer.valueOf(cols[indexOfSalary]);
                    if (jobSalaryMap.containsKey(job)) {
                        int sumOfSalary = jobSalaryMap.get(job) + salary;
                        jobSalaryMap.put(job, sumOfSalary); // Eí•Ê‚ÌŒ‹‹‰ºŒÀ‹àŠz‚Ì‡Œv‚ğXV
                        int countOfJob = jobCountMap.get(job) + 1;
                        jobCountMap.put(job, countOfJob); // Eí•Ê‚ÌŒfÚŒ”‚ğXV
                    } else { // ‚»‚ÌEí‚Ì‰‰ñ“oê
                        jobSalaryMap.put(job, salary);
                        jobCountMap.put(job, 1);
                    }
                }
                
                // Šé‹Æ‚²‚Æ‚ÌŒfÚŒ”‚ğƒJƒEƒ“ƒgi‰Û‘è‚Tj
                String name = cols[indexOfName];
                if (nameCountMap.containsKey(name)) {
                    int countOfName = nameCountMap.get(name) + 1; 
                    nameCountMap.put(name, countOfName); // Šé‹Æ•Ê‚ÌŒfÚŒ”‚ğXV
                } else { // ‚»‚ÌŠé‹Æ‚Ì‰‰ñ“oê
                    nameCountMap.put(name, 1);
                }
                
                // lŒûãˆÊ‚T“s“¹•{Œ§‚ÌAEí‚²‚Æ‚ÌŒfÚŒ”‚ğæ“¾i‰Û‘è‚Uj
                // ŠÔ‚ª‚È‚¢‚Ì‚ÅƒSƒŠ‰Ÿ‚µ‚µ‚Ü‚·
                if (clearPref.equals("“Œ‹“s")) {
                    String job = cols[indexOfJob];
                    if (tokyojobNumberMap.containsKey(job)) {
                        int newNumber = tokyojobNumberMap.get(job) + 1;
                        tokyojobNumberMap.put(job, newNumber);
                    } else {
                        tokyojobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("_“ŞìŒ§")){
                    String job = cols[indexOfJob];
                    if (kanagawajobNumberMap.containsKey(job)) {
                        int newNumber = kanagawajobNumberMap.get(job) + 1;
                        kanagawajobNumberMap.put(job, newNumber);
                    } else {
                        kanagawajobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("‘åã•{")) {
                    String job = cols[indexOfJob];
                    if (osakajobNumberMap.containsKey(job)) {
                        int newNumber = osakajobNumberMap.get(job) + 1;
                        osakajobNumberMap.put(job, newNumber);
                    } else {
                        osakajobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("ˆ¤’mŒ§")) {
                    String job = cols[indexOfJob];
                    if (aichijobNumberMap.containsKey(job)) {
                        int newNumber = aichijobNumberMap.get(job) + 1;
                        aichijobNumberMap.put(job, newNumber);
                    } else {
                        aichijobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("é‹ÊŒ§")) {
                    String job = cols[indexOfJob];
                    if (saitamajobNumberMap.containsKey(job)) {
                        int newNumber = saitamajobNumberMap.get(job) + 1;
                        saitamajobNumberMap.put(job, newNumber);
                    } else {
                        saitamajobNumberMap.put(job, 1);
                    }
                }
            }
            numberMap.put("‘SŒfÚŒ”", cnt);
            numberMap.put("Œ_–ñĞˆõ", numOfContract);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // ‰Û‘è‚Ì“š‚¦‚ğ•\¦
        printQ1();
        printQ2();
        printQ3();
        printQ4();
        printQ5();
        printQ6();
    }

    // Še‰Û‘è‚Ìƒƒ\ƒbƒh‚ğì¬
    // Q1 ‘SŒfÚŒ”‚ğ•\¦‚·‚éƒƒ\ƒbƒh
    private static void printQ1() {
        System.out.println("‰Û‘è‚P");
        int allCnt = numberMap.get("‘SŒfÚŒ”");
        System.out.println("‘SŒfÚŒ”‚Í" + allCnt + "Œ‚Å‚·B");
        System.out.println("");
    }
    
    // Q2 uŒ_–ñĞˆõv‚ÌŒfÚŒ”‚ğ•\¦‚·‚éƒƒ\ƒbƒh
    private static void printQ2() {
        System.out.println("‰Û‘è‚Q");
        int numOfContract = numberMap.get("Œ_–ñĞˆõ");
        System.out.println("ŒÙ—p‹æ•ªuŒ_–ñĞˆõv‚ÌŒfÚŒ”‚Í" + numOfContract + "Œ‚Å‚·B");
        System.out.println("");
    }
    
    // Q3 prefMap‚©‚ç“s“¹•{Œ§‚²‚Æ‚ÌŒfÚŒ”‚ğæ“¾‚µA•\¦‚·‚éƒƒ\ƒbƒh
    private static void printQ3() {
        System.out.println("‰Û‘è‚R");
        System.out.println("“s“¹•{Œ§–¼ F ŒfÚŒ”");
        for (Map.Entry<String, Integer> entry : prefNumberMap.entrySet()) {
            String pref = entry.getKey();
            int count = entry.getValue();
            System.out.println(pref + " F " + count + "Œ");
        }
        System.out.println("");
    }
    
    // Q4 ‰ºŒÀ‹àŠz‚Ì‡Œv‚ğŒfÚŒ”‚ÅŠ„‚èAƒJƒ“ƒ}‹æØ‚è‚Å®”’l‚É‚µ‚Äo—Í‚·‚éƒƒ\ƒbƒh
    private static void printQ4() {
        System.out.println("‰Û‘è‚S");
        System.out.println("Eí F Œ‹‹‰ºŒÀ‹àŠz•½‹Ï");
        for (Map.Entry<String, Integer> entry : jobSalaryMap.entrySet()) {
            String jobName = entry.getKey(); // Eí–¼
            int divisor = jobCountMap.get(jobName); // Eí‚²‚Æ‚ÌŒfÚŒ”
            int sumSalary = entry.getValue(); // Eí‚²‚Æ‚ÌŒ‹‹‰ºŒÀ‹àŠz‚Ì‡Œv
            int avgSalary = sumSalary / divisor;
            String viewAvg = String.format("%, d", avgSalary);
            System.out.println(jobName + " F " + viewAvg + " ‰~");
        }
        System.out.println("");
    }
    
    // Q5 Šé‹Æ‚²‚Æ‚ÌŒ”‚ğ”äŠr‚µA10ˆÊ‚Ü‚Å‚ğo—Í
    private static void printQ5() {
        List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(nameCountMap.entrySet());
        
        Collections.sort(list_entries, new Comparator<Entry<String, Integer>>(){
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        
        // Œ‹‰Ê‚ğ•\¦
        System.out.println("‰Û‘è‚T");
        int rank = 0; // ‡ˆÊ
        int cntNum = 0; // •\¦‚·‚éŠé‹Æ”B10‚æ‚è‘å‚«‚­‚È‚Á‚½‚çI—¹
        int num1; // Šé‹Æ‚P‚ÌŒfÚ”
        int num2 = 0; // Šé‹Æ‚Q‚ÌŒfÚ”
        Map<Integer, Integer> tieMap = new HashMap<Integer, Integer>(); // ‡ˆÊ, “¯—¦‡ˆÊ‚ÌŠé‹Æ”
        // Šé‹Æ‚P‚ÆŠé‹Æ‚Q‚ÌŒfÚ”‚ğ”äŠr‚µ‚Ä‚¢‚­
        for(Entry<String, Integer> entry : list_entries) {
            cntNum++;
            num1 = entry.getValue();
            String name = entry.getKey();
            int number = entry.getValue();
            if (!tieMap.containsKey(rank)) {
                tieMap.put(rank, 1);
            }
            
            // Šé‹Æ‚P‚ÌŒfÚ”‚ÆŠé‹Æ‚Q‚ÌŒfÚ”‚ªˆá‚Á‚½‚çA‡ˆÊ‚ğ‚P‚Â‚¸‚ç‚·
            if (num2 != num1) { // ‡ˆÊ‚ª‰º‚ª‚éê‡
                if (cntNum >= 10) {
                    break;
                }
                // “¯—¦‡ˆÊ‚ÌŠé‹Æ”‚Ì•ª‚¾‚¯ƒ‰ƒ“ƒN‚ğ‰º‚°‚éi“¯—¦‡ˆÊ‚ÌŠé‹Æ‚ª‚È‚¯‚ê‚Î‚P‚¾‚¯‰º‚ª‚éj
                rank = rank + tieMap.get(rank);
                System.out.println(rank + "ˆÊ@" + name + " : " + number + "Œ");
            } else { // “¯—¦‡ˆÊ‚ª•À‚ñ‚Å‚¢‚éê‡;
                System.out.println(rank + "ˆÊ@" + name + " : " + number + "Œ");
                // “¯—¦‡ˆÊ‚ÌŠé‹Æ”‚ğXV
                tieMap.put(rank, tieMap.get(rank) + 1);
            }
            num2 = entry.getValue();
        }
        System.out.println("\r\n\r\n");
    }
    
    // Q6 lŒûãˆÊ5ˆÊ‚Ü‚Å‚Ì“s“¹•{Œ§‚Æ‘S‘‚ÅAEí•Ê‚Ì‹lŒfÚ”‚ÌŠ„‡‚ğZo
    private static void printQ6() {
        System.out.println("‰Û‘è‚U");
        System.out.println("“Œ‹“s‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(tokyojobNumberMap);
        
        System.out.println("_“ŞìŒ§‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(kanagawajobNumberMap);
        
        System.out.println("‘åã•{‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(osakajobNumberMap);
        
        System.out.println("ˆ¤’mŒ§‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(aichijobNumberMap);
        
        System.out.println("é‹ÊŒ§‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(saitamajobNumberMap);
        
        System.out.println("‘S‘‚ÌEí•Ê‹lŒfÚ”‚ÌŠ„‡");
        calcurateRatio(jobCountMap);
    }
    
    private static void calcurateRatio(Map<String, Integer> map) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sum +=  entry.getValue();
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            double avg = 100 * (double)entry.getValue() / sum;
            BigDecimal viewAvg = new BigDecimal(avg);
            viewAvg = viewAvg.setScale(1, BigDecimal.ROUND_HALF_UP);
            System.out.println(entry.getKey() + " : " + viewAvg + "%");
        }
        System.out.println("");
    }

}