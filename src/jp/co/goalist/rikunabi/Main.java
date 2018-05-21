package jp.co.goalist.rikunabi;

import java.io.BufferedReader;
import java.io.IOException;
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
    static Map<String, Integer> prefMap = new LinkedHashMap<String, Integer>(); // �s���{��, �f�ڌ���
    static Map<String, Integer> jobSalaryMap = new HashMap<String, Integer>(); // �E��, �����������z�̍��v
    static Map<String, Integer> jobCountMap = new HashMap<String, Integer>(); // �E��, �E�킲�Ƃ̌f�ڌ���
    static Map<String, Integer> nameCountMap = new HashMap<String, Integer>(); // ��Ɩ�, ��Ƃ��Ƃ̌f�ڌ���


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
            prefMap.put(prefAry[i], 0);
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
                if (!prefMap.containsKey(clearPref)) {
                    prefMap.put(clearPref, 0);
                }
                int prefCnt = prefMap.get(clearPref) + 1;
                prefMap.put(clearPref, prefCnt);

                
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
        for (Map.Entry<String, Integer> entry : prefMap.entrySet()) {
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
        int rank = 0;
        int cnt = 0; // cnt��10���傫���Ȃ�����I��
        int num1;
        int num2 = 0;
        for(Entry<String, Integer> entry : list_entries) {
            cnt++;
            num1 = entry.getValue();
            if (num2 != num1) {
                rank++;
                if (cnt >= 11) {
                    break;
            }

            }
            String name = entry.getKey();
            int number = entry.getValue();
            System.out.println(rank + "�ʁ@" + name + " : " + number + "��");
            num2 = entry.getValue();
        }
    }

}
