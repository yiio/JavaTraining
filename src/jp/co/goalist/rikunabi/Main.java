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
    static Map<String, Integer> numberMap = new HashMap<String, Integer>(); // �L�[�Ɂu�S�f�ڌ����v�Ɓu�_��Ј��v���A�l�ɂ����̌������i�[
    static Map<String, Integer> prefNumberMap = new LinkedHashMap<String, Integer>(); // �s���{��, �f�ڌ���
    static Map<String, Integer> jobSalaryMap = new HashMap<String, Integer>(); // �E��, �����������z�̍��v
    static Map<String, Integer> jobCountMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ���
    static Map<String, Integer> nameCountMap = new HashMap<String, Integer>(); // ��Ɩ�, ��Ƃ��Ƃ̌f�ڌ���
    
    // �ۑ�U�p
    static Map<String, Integer> tokyojobNumberMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ����i�����s�j
    static Map<String, Integer> kanagawajobNumberMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ����i�_�ސ쌧�j
    static Map<String, Integer> osakajobNumberMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ����i���{�j
    static Map<String, Integer> aichijobNumberMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ����i���m���j
    static Map<String, Integer> saitamajobNumberMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ����i��ʌ��j
    
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\TechTraining\\resources\\recruitNaviNext.csv");
        
        int cnt = 0; // �S�f�ڌ���
        int numOfContract = 0; // �ٗp�敪���_��Ј��ł���Č���
        
        // prefMap�̏����l��ݒ�
        String[] prefAry = {"�k�C��","�X��","��茧","�{�錧","�H�c��","�R�`��","������",
                "��錧","�Ȗ،�","�Q�n��","��ʌ�","��t��","�����s","�_�ސ쌧",
                "�V����","�x�R��","�ΐ쌧","���䌧","�R����","���쌧","�򕌌�",
                "�É���","���m��","�O�d��","���ꌧ","���s�{","���{","���Ɍ�",
                "�ޗǌ�","�a�̎R��","���挧","������","���R��","�L����","�R����",
                "������","���쌧","���Q��","���m��","������","���ꌧ","���茧",
                "�F�{��","�啪��","�{�茧","��������","���ꌧ",
                };
        for (int i = 0; i < 47; i++) {
            prefNumberMap.put(prefAry[i], 0);
        }
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // �w�b�_�̏���
            String line = br.readLine();
            String[] header = line.split(",");
            List<String> headerList = Arrays.asList(header);
            int indexOfEmp = headerList.indexOf("�ٗp�敪");
            int indexOfPref = headerList.indexOf("�G���A�s���{��");
            int indexOfJob = headerList.indexOf("�E�핪��");
            int indexOfSalary = headerList.indexOf("�����������z");
            int indexOfName = headerList.indexOf("��Ɩ�");
            
            
            // ���e�̏����i2�s�ڈȍ~�j
            while ((line = br.readLine()) != null) {
                cnt++; // ���ׂĂ̌f�ڌ������J�E���g�i�ۑ�P�j
                String[] cols = line.split(",");
                
                // �u�_��Ј��v�̌f�ڌ������J�E���g�i�ۑ�Q�j
                if (cols[indexOfEmp].equals("�_��Ј�")) {
                    numOfContract++;
                }
                
                // 47�s���{�����Ƃ̌f�ڌ������J�E���g�i�ۑ�R�j 
                String pref = cols[indexOfPref];
                // �G���A�s���{���̕�����i�u13:�����s�v�Ȃǁj��s���{�����̂݁i�u�����s�v�Ȃǁj�ɕϊ�
                String[] prefCols = pref.split(":");
                String clearPref = prefCols[1];
                if (!prefNumberMap.containsKey(clearPref)) {
                    prefNumberMap.put(clearPref, 0);
                }
                int prefCnt = prefNumberMap.get(clearPref) + 1;
                prefNumberMap.put(clearPref, prefCnt);

                
                // �E�핪�ނ��Ƃ̌����������z���擾���AjobSalaryMap�̒l�ɑ����Ă����i�ۑ�S�j
                if (StringUtils.isNumeric(cols[indexOfSalary])) { // �����������z���f�ڂ���Ă��Ȃ��f�[�^�͏��O
                    String job = cols[indexOfJob];
                    int salary = Integer.valueOf(cols[indexOfSalary]);
                    if (jobSalaryMap.containsKey(job)) {
                        int sumOfSalary = jobSalaryMap.get(job) + salary;
                        jobSalaryMap.put(job, sumOfSalary); // �E��ʂ̌����������z�̍��v���X�V
                        int countOfJob = jobCountMap.get(job) + 1;
                        jobCountMap.put(job, countOfJob); // �E��ʂ̌f�ڌ������X�V
                    } else { // ���̐E��̏���o�ꎞ
                        jobSalaryMap.put(job, salary);
                        jobCountMap.put(job, 1);
                    }
                }
                
                // ��Ƃ��Ƃ̌f�ڌ������J�E���g�i�ۑ�T�j
                String name = cols[indexOfName];
                if (nameCountMap.containsKey(name)) {
                    int countOfName = nameCountMap.get(name) + 1; 
                    nameCountMap.put(name, countOfName); // ��ƕʂ̌f�ڌ������X�V
                } else { // ���̊�Ƃ̏���o�ꎞ
                    nameCountMap.put(name, 1);
                }
                
                // �l����ʂT�s���{���́A�E�킲�Ƃ̌f�ڌ������擾�i�ۑ�U�j
                // ���Ԃ��Ȃ��̂ŃS���������܂�
                if (clearPref.equals("�����s")) {
                    String job = cols[indexOfJob];
                    if (tokyojobNumberMap.containsKey(job)) {
                        int newNumber = tokyojobNumberMap.get(job) + 1;
                        tokyojobNumberMap.put(job, newNumber);
                    } else {
                        tokyojobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("�_�ސ쌧")){
                    String job = cols[indexOfJob];
                    if (kanagawajobNumberMap.containsKey(job)) {
                        int newNumber = kanagawajobNumberMap.get(job) + 1;
                        kanagawajobNumberMap.put(job, newNumber);
                    } else {
                        kanagawajobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("���{")) {
                    String job = cols[indexOfJob];
                    if (osakajobNumberMap.containsKey(job)) {
                        int newNumber = osakajobNumberMap.get(job) + 1;
                        osakajobNumberMap.put(job, newNumber);
                    } else {
                        osakajobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("���m��")) {
                    String job = cols[indexOfJob];
                    if (aichijobNumberMap.containsKey(job)) {
                        int newNumber = aichijobNumberMap.get(job) + 1;
                        aichijobNumberMap.put(job, newNumber);
                    } else {
                        aichijobNumberMap.put(job, 1);
                    }
                } else if (clearPref.equals("��ʌ�")) {
                    String job = cols[indexOfJob];
                    if (saitamajobNumberMap.containsKey(job)) {
                        int newNumber = saitamajobNumberMap.get(job) + 1;
                        saitamajobNumberMap.put(job, newNumber);
                    } else {
                        saitamajobNumberMap.put(job, 1);
                    }
                }
            }
            numberMap.put("�S�f�ڌ���", cnt);
            numberMap.put("�_��Ј�", numOfContract);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // �ۑ�̓�����\��
        printQ1();
        printQ2();
        printQ3();
        printQ4();
        printQ5();
        printQ6();
    }

    // �e�ۑ�̃��\�b�h���쐬
    // Q1 �S�f�ڌ�����\�����郁�\�b�h
    private static void printQ1() {
        System.out.println("�ۑ�P");
        int allCnt = numberMap.get("�S�f�ڌ���");
        System.out.println("�S�f�ڌ�����" + allCnt + "���ł��B");
        System.out.println("");
    }
    
    // Q2 �u�_��Ј��v�̌f�ڌ�����\�����郁�\�b�h
    private static void printQ2() {
        System.out.println("�ۑ�Q");
        int numOfContract = numberMap.get("�_��Ј�");
        System.out.println("�ٗp�敪�u�_��Ј��v�̌f�ڌ�����" + numOfContract + "���ł��B");
        System.out.println("");
    }
    
    // Q3 prefMap����s���{�����Ƃ̌f�ڌ������擾���A�\�����郁�\�b�h
    private static void printQ3() {
        System.out.println("�ۑ�R");
        System.out.println("�s���{���� �F �f�ڌ���");
        for (Map.Entry<String, Integer> entry : prefNumberMap.entrySet()) {
            String pref = entry.getKey();
            int count = entry.getValue();
            System.out.println(pref + " �F " + count + "��");
        }
        System.out.println("");
    }
    
    // Q4 �������z�̍��v���f�ڌ����Ŋ���A�J���}��؂�Ő����l�ɂ��ďo�͂��郁�\�b�h
    private static void printQ4() {
        System.out.println("�ۑ�S");
        System.out.println("�E�� �F �����������z����");
        for (Map.Entry<String, Integer> entry : jobSalaryMap.entrySet()) {
            String jobName = entry.getKey(); // �E�햼
            int divisor = jobCountMap.get(jobName); // �E�킲�Ƃ̌f�ڌ���
            int sumSalary = entry.getValue(); // �E�킲�Ƃ̌����������z�̍��v
            int avgSalary = sumSalary / divisor;
            String viewAvg = String.format("%, d", avgSalary);
            System.out.println(jobName + " �F " + viewAvg + " �~");
        }
        System.out.println("");
    }
    
    // Q5 ��Ƃ��Ƃ̌������r���A10�ʂ܂ł��o��
    private static void printQ5() {
        List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(nameCountMap.entrySet());
        
        Collections.sort(list_entries, new Comparator<Entry<String, Integer>>(){
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        
        // ���ʂ�\��
        System.out.println("�ۑ�T");
        int rank = 0; // ����
        int cntNum = 0; // �\�������Ɛ��B10���傫���Ȃ�����I��
        int num1; // ��ƂP�̌f�ڐ�
        int num2 = 0; // ��ƂQ�̌f�ڐ�
        Map<Integer, Integer> tieMap = new HashMap<Integer, Integer>(); // ����, �������ʂ̊�Ɛ�
        // ��ƂP�Ɗ�ƂQ�̌f�ڐ����r���Ă���
        for(Entry<String, Integer> entry : list_entries) {
            cntNum++;
            num1 = entry.getValue();
            String name = entry.getKey();
            int number = entry.getValue();
            if (!tieMap.containsKey(rank)) {
                tieMap.put(rank, 1);
            }
            
            // ��ƂP�̌f�ڐ��Ɗ�ƂQ�̌f�ڐ����������A���ʂ��P���炷
            if (num2 != num1) { // ���ʂ�������ꍇ
                if (cntNum >= 10) {
                    break;
                }
                // �������ʂ̊�Ɛ��̕����������N��������i�������ʂ̊�Ƃ��Ȃ���΂P����������j
                rank = rank + tieMap.get(rank);
                System.out.println(rank + "�ʁ@" + name + " : " + number + "��");
            } else { // �������ʂ�����ł���ꍇ;
                System.out.println(rank + "�ʁ@" + name + " : " + number + "��");
                // �������ʂ̊�Ɛ����X�V
                tieMap.put(rank, tieMap.get(rank) + 1);
            }
            num2 = entry.getValue();
        }
        System.out.println("\r\n\r\n");
    }
    
    // Q6 �l�����5�ʂ܂ł̓s���{���ƑS���ŁA�E��ʂ̋��l�f�ڐ��̊������Z�o
    private static void printQ6() {
        System.out.println("�ۑ�U");
        System.out.println("�����s�̐E��ʋ��l�f�ڐ��̊���");
        calcurateRatio(tokyojobNumberMap);
        
        System.out.println("�_�ސ쌧�̐E��ʋ��l�f�ڐ��̊���");
        calcurateRatio(kanagawajobNumberMap);
        
        System.out.println("���{�̐E��ʋ��l�f�ڐ��̊���");
        calcurateRatio(osakajobNumberMap);
        
        System.out.println("���m���̐E��ʋ��l�f�ڐ��̊���");
        calcurateRatio(aichijobNumberMap);
        
        System.out.println("��ʌ��̐E��ʋ��l�f�ڐ��̊���");
        calcurateRatio(saitamajobNumberMap);
        
        System.out.println("�S���̐E��ʋ��l�f�ڐ��̊���");
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