package jp.co.goalist.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Question7and8 {
    
    public static void main(String[] args) {
     // ���O�ƃ`�[���̔z��
        final String[][] teamArys = {
            {"�R�c", "A�`�[��"},
            {"�c��", "A�`�[��"},
            {"���", "B�`�[��"},
            {"����", "C�`�[��"},
            {"�ɓ�", "D�`�[��"}
        };

        // ���O�Ɠ��_�̔z��
        final String[][] pointArys = {
            {"�R�c", "1"},
            {"�R�c", "3"},
            {"�R�c", "1"},
            {"�ɓ�", "4"},
            {"�R�c", "2"},
            {"����", "1"},
            {"����", "1"},
            {"�R�c", "3"},
            {"���", "2"},
            {"�R�c", "4"},
            {"�ɓ�", "5"},
            {"�R�c", "2"},
            {"���", "3"},
            {"�R�c", "1"},
            {"�c��", "2"}
        };
        
        Map<String, String> teamMap = new HashMap<String, String>();
            {
                for (int i = 0; i < teamArys.length; i++) {
                    teamMap.put(teamArys[i][0], teamArys[i][1]);
                }
            }
        
        Map<String, Integer> pointMap = new HashMap<String, Integer>();
            {
                // pointMap�̏����l�̐ݒ�
                for (int i = 0; i < teamArys.length; i++) {
                    if (!pointMap.containsKey(teamArys[i][1])) {
                        pointMap.put(teamArys[i][1], 0);
                    }
                }
                for (int i = 0; i < pointArys.length; i++) {
                    // ���̓��_��������l���������Ă���`�[�������擾���L�[�Ƃ��Ēǉ��A���_��l�Ƃ��Ēǉ�
                    pointMap.put(teamMap.get(pointArys[i][0]), pointMap.get(teamMap.get(pointArys[i][0])) + Integer.parseInt(pointArys[i][1]));
                }
            }
        for (String teamName: pointMap.keySet()) {
            System.out.println(teamName + "�̍��v���_�́A" + pointMap.get(teamName) + "�_�ł��B");
        }
    }
}
