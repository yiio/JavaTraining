package jp.co.goalist.crawler;

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

public class Question4 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("�N���[�����X�N���C�v�������J�n���܂����B");

        makeArticlesCsv(2018, 1);

        System.out.println("�N���[�����X�N���C�v�������I�����܂����B");
    }

    static void makeArticlesCsv(int year, int month) throws InterruptedException, IOException {
        String rootUrl = "";
        if (month <= 9) { // month��1���̏ꍇ
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + "0"
                    + month + "/";
        }
        if (month >= 10) { // month��2���̏ꍇ
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + month
                    + "/";
        }
        // Map<String, String> titleCategoryMap = new HashMap<String, String>(); //
        // �^�C�g��, �J�e�S���[ �Ƃ肠�����ۗ��B�]�͂�����΃J�e�S�����������Ŏ擾�ł���悤�ɂ���
        Map<String, String> titleTimeMap = new HashMap<String, String>(); // �^�C�g��, �j���[�X����
        Map<String, String> titleUrlMap = new HashMap<String, String>(); // �^�C�g��, URL

        String rootUrlPerPage = ""; // �y�[�W���Ƃ�URL

        for (int i = 1; i < 4; i++) {
            if (i == 1) { // 1�y�[�W�ڈȍ~��URL
                rootUrlPerPage = rootUrl;
            } else { // 2�y�[�W�ڈȍ~��URL
                rootUrlPerPage = rootUrl + "?page=" + i;
            }
            Document doc = Jsoup.connect(rootUrlPerPage).get();

            try { // �L�����X�g�̃y�[�W���𒴂�����for���[�v���I��
                Elements els0 = doc.getElementsByClass("thumb-s__link js-link");
                for (Element el0 : els0) {
                    int cnt = 0;
                    String title = "";
                    String yearDateTime = "";
                    for (Element child : el0.children()) {
                        cnt++;
                        if (cnt == 1) { // �^�C�g����URL���擾
                            title = el0.child(1).text(); // �^�C�g�����擾
                            // URL���擾
                            String url = el0.child(1).child(0).attr("href");
                            String perfectUrl = "https://news.mynavi.jp" + url;
                            titleUrlMap.put(title, perfectUrl);
                        }
                        if (cnt == 2) { // �������擾
                            String dateTime = el0.child(2).text();
                            yearDateTime = year + "/" + dateTime;
                            titleTimeMap.put(title, yearDateTime);
                        }
                    }
                }
                // 1�b�ҋ@
                Thread.sleep(1000);
                // �r���o�߂��J�E���g���Ă邾���ł�
                System.out.println(i);

            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        // �{�����i�[����}�b�v�����
        Map<String, String> titleTextMap = new HashMap<String, String>();
        // �L�����Ƃɖ{�����擾
        for (Map.Entry<String, String> entry : titleUrlMap.entrySet()) {
            String title = entry.getKey();
            String textUrl = entry.getValue();
            Document doc = Jsoup.connect(textUrl).get();

            String articleBody = ""; // �{��������ϐ�
            Elements paragraphs = doc.getElementsByClass("article-body");
            for (Element paragraph : paragraphs) { // �{���̒i�����ƂɎ擾���Ă����AarticleBody�ɑ����Ă���
                articleBody += paragraph.text();
            }
            // ���p�J���}���S�p�J���}�A���s�����p�X�y�[�X�@�̏������s��
            articleBody = articleBody.replaceAll(",", "�C"); // �J���}�u������
            articleBody = articleBody.replaceAll("\r\n", " "); // ���s�𔼊p�X�y�[�X�֒u������
            titleTextMap.put(title, articleBody);
            Thread.sleep(1000);
        }

        Path filePath = Paths.get("C:\\TechTraining\\resources\\crlAns4.csv");
        String contents = ""; // �������ޓ��e
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            // �w�b�_�[�s���쐬
            contents = "����,�J�e�S��,�j���[�X����,URL,�{��";
            bw.write(contents);
            bw.newLine();

            for (Map.Entry<String, String> entry : titleTimeMap.entrySet()) {
                String title = entry.getKey();
                String category = "�����̃f�o�C�X";
                String newsTime = entry.getValue();
                String url = titleUrlMap.get(title);
                String text = titleTextMap.get(title);
                if (text.equals("")) { // �{�����擾�ł��Ȃ������ꍇ
                    text = "�擾�ł��܂���ł���";
                }
                // �L���^�C�g���̒��ɃJ���}���܂܂�Ă���\��������̂ŁA�^�C�g�����_�u���N�I�[�e�[�V�����ň͂�
                contents = "\"" + title + "\"" + "," + "\"" + category + "\"" + "," + "\"" + newsTime + "\"" + ","
                        + "\"" + url + "\"" + "," + "\"" + text + "\"";
                bw.write(contents);
                bw.newLine();
            }

        }

    }

}
