package crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Question2 {

    public static void main(String[] args) throws IOException {
        String rootUrl = "http://news.mynavi.jp/top/headline/";
        
        Map<String, String> titleCategoryMap = new HashMap<String, String>(); // �^�C�g��, �J�e�S���[
        Map<String, String> titleTimeMap = new HashMap<String, String>(); // �^�C�g��, �j���[�X����
        Map<String, String> titleUrlMap = new HashMap<String, String>(); // �^�C�g��, URL
        
        Document doc = Jsoup.connect(rootUrl).get();
        Element el = doc.select("body > div.wrapper > "
                + "div.container.container--top > div.body > main > "
                + "section.box.box--top.box--line > div.thumb-s").get(0);
        
        // 
        for (Element child : el.children()) {
            if (child.children().size() < 1) {
                continue;
            }
            
            // 
            for (Element grandchild : child.children()) {
                
                // 
                int cnt = 0;
                String title = "";
                String categoryName = "";
                String prTime = "";
                String perfectTime = "";
                String year = ""; // ����
                for (Element g_grandchild : grandchild.children()) {
                    cnt++;
                    if (cnt == 1) {
                        title =grandchild.child(1).text();
                        String url = grandchild.child(1).child(0).attr("href");
                        String perfectUrl = "https://news.mynavi.jp" + url;
                        titleUrlMap.put(title, perfectUrl);
                        
                    }
                    if (cnt == 2) {
                        Elements thumbsTxt = grandchild.select("p");
                        // �J�e�S���[���̎擾
                        Elements category = thumbsTxt.select("a");
                        categoryName = category.text();
                        titleCategoryMap.put(title, categoryName);
                        // �j���[�X�����̎擾
                        Elements time = thumbsTxt.select("span");
                        prTime = time.text(); // ����ɂ͐�������Ă��Ȃ�&�uPR �v�Ƃ����]�v�ȕ����������Ă��邱�Ƃ�����
                        
                        if (prTime.contains("PR ")) { // �uPR �v�������Ă���΂Ƃ�
                            String newsTime = prTime.substring(3, prTime.length());
                            perfectTime = 2018 + "/" + newsTime;
                        } else {
                            perfectTime = 2018 + "/" + prTime;
                        }
                        
                        // ���������j���[�X�������}�b�v�Ɋi�[
                        titleTimeMap.put(title, perfectTime);
                    }
                }
            }
        }
        
        // �}�b�v�̃`�F�b�N
        System.out.println(titleCategoryMap);
        System.out.println(titleTimeMap);
        System.out.println(titleUrlMap);
        
        Path filePath = Paths.get("C:\\TechTraining\\resources\\crlAns2.csv");
        String contents = ""; // �������ޓ��e
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            // �w�b�_�[�s���쐬
            contents = "����,�J�e�S��,�j���[�X����,URL";
            bw.write(contents);
            bw.newLine();
            
            for (Map.Entry<String, String> entry : titleCategoryMap.entrySet()) {
                String title = entry.getKey();
                String category = titleCategoryMap.get(title);
                String newsTime = titleTimeMap.get(title);
                String url = titleUrlMap.get(title);
                contents = title + "," + category + "," + newsTime + "," + url;
                bw.write(contents);;
                bw.newLine();
            }
        }
    }

}
