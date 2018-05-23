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

public class Question3 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("�N���[�����X�N���C�v�������J�n���܂����B");

        makeArticlesCsv(2018, 02);

        System.out.println("�N���[�����X�N���C�v�������I�����܂����B");
    }

    static void makeArticlesCsv(int year, int month) throws InterruptedException, IOException {
        String rootUrl = "";
        if (month <= 9) {
            rootUrl = "http://news.mynavi.jp/list/headline/" + year + "/" + "0" + month + "/";
        }
        if (month >= 10) {
            rootUrl = "http://news.mynavi.jp/list/headline/" + year + "/" + month + "/";
        }
        Map<String, String> titleCategoryMap = new HashMap<String, String>(); // �^�C�g��, �J�e�S���[
        Map<String, String> titleTimeMap = new HashMap<String, String>(); // �^�C�g��, �j���[�X����
        Map<String, String> titleUrlMap = new HashMap<String, String>(); // �^�C�g��, URL

        String rootUrlPerPage = ""; // �y�[�W���Ƃ�URL

        for (int i = 1; i > 0; i++) {
            if (i == 1) { // 1�y�[�W�ڈȍ~��URL
                rootUrlPerPage = rootUrl;
            } else { // 2�y�[�W�ڈȍ~��URL
                rootUrlPerPage = rootUrl + "?page=" + i;
            }
            Document doc = Jsoup.connect(rootUrlPerPage).get();

            try { // �L�����X�g�̃y�[�W���𒴂�����for���[�v���I��
                Element el = doc.select("body > div.wrapper > " + "div.container.container--top > "
                        + "div.body > main > section.box.box--top.box--line > div.thumb-s").get(0);

                for (Element child : el.children()) {
                    if (child.children().size() < 1) {
                        continue;
                    }
                    for (Element grandchild : child.children()) {
                        //
                        int cnt = 0;
                        String title = "";
                        String categoryName = "";
                        String prTime = "";
                        String perfectTime = "";
                        for (Element g_grandchild : grandchild.children()) {
                            cnt++;
                            if (cnt == 1) {
                                title = grandchild.child(1).text();
                                String url = grandchild.child(1).child(0).attr("href"); // url�̂قƂ�ǂ͕s���S��URL
                                if (!url.substring(0, 4).equals("http")) { // url���s���S��URL�̏ꍇ
                                    String perfectUrl = "https://news.mynavi.jp" + url; // ���S��URL������Ă�����
                                    titleUrlMap.put(title, perfectUrl);
                                } else { // url�����S��URL�̏ꍇ(url��http����n�܂�ꍇ)
                                    titleUrlMap.put(title, url);
                                }
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
                                    perfectTime = year + "/" + newsTime;
                                } else {
                                    perfectTime = year + "/" + prTime;
                                }

                                // ���������j���[�X�������}�b�v�Ɋi�[
                                titleTimeMap.put(title, perfectTime);
                            }
                        }
                    }
                }
                // 1�b�ҋ@
                Thread.sleep(1000);
                // �J�E���g
                System.out.println(i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        Path filePath = Paths.get("C:\\TechTraining\\resources\\crlAns3.csv");
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
                // �L���^�C�g���̒��ɃJ���}���܂܂�Ă���\��������̂ŁA�^�C�g�����_�u���N�I�[�e�[�V�����ň͂�
                contents = "\"" + title + "\"" + "," + "\"" + category + "\"" + ","
                        + "\"" + newsTime + "\"" + "," + "\"" + url + "\"";
                bw.write(contents);
                bw.newLine();
            }
        }

    }

}
