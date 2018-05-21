package jp.co.goalist.rikunabi;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class sample {
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        add.list()
        Map<Integer, Integer> rankMap = makeRankMap(list, false, true);

    list.sort((a, b) -> b - a);
    for (int score : list) {
        System.out.printf("rank:%d, score:%d\n", rankMap.get(score), score);
    }
    }
    
    public static Map<Integer, Integer> makeRankMap(Collection<Integer> scores, 
            boolean ascending, 
            boolean rankWithoutSkipping) 
{
TreeMap<Integer, Integer> map = new TreeMap<>(ascending ? null : Collections.reverseOrder());
for (int score : scores) {
map.put(score, map.getOrDefault(score, 0) + 1);
}

if (rankWithoutSkipping) {
int rank = 1;
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
entry.setValue(rank++);
}
return map;
}

int rank = 1;
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
rank += entry.setValue(rank);
}
return map;
}
}
