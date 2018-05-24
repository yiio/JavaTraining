package jp.co.goalist.crawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Question5 {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("�N���[�����X�N���C�v�������J�n���܂����B");

        dlArticleImages(2018, 02);

        System.out.println("�N���[�����X�N���C�v�������I�����܂����B");
    }

    static void dlArticleImages(int year, int month) throws InterruptedException, IOException {
        String rootUrl = "";
        if (month <= 9) { // month��1���̏ꍇ
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + "0"
                    + month + "/";
        }
        if (month >= 10) { // month��2���̏ꍇ
            rootUrl = "https://news.mynavi.jp/list/headline/business/technology/semiconductor/" + year + "/" + month
                    + "/";
        }
        List<String> imgUrlList = new ArrayList<String>();

        String rootUrlPerPage = ""; // �y�[�W���Ƃ�URL
        for (int i = 1; i < 4; i++) {
            if (i == 1) { // 1�y�[�W�ڂ�URL
                rootUrlPerPage = rootUrl;
            } else { // 2�y�[�W�ڈȍ~��URL
                rootUrlPerPage = rootUrl + "?page=" + i;
            }
            Document doc = Jsoup.connect(rootUrlPerPage).get();
            Thread.sleep(1000);

            try {
                Elements els = doc.getElementsByClass("thumb-s__img");
                for (Element el : els) {
                    String perfectUrl = "https://news.mynavi.jp" + el.attr("src");
                    imgUrlList.add(perfectUrl);
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        System.out.println(imgUrlList);
        // �摜��ۑ�
        String imgUrl = "";
        for (int i = 0; i < imgUrlList.size(); i++) {
            imgUrl = imgUrlList.get(i);
            String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1) + i + ".jpg";
            // �摜�̕ۑ����p��
            String dir = "C:\\TechTraining\\img";
            Path dirPath = Paths.get(dir);
            if (Files.notExists(dirPath)) { // �f�B���N�g�������݂��Ȃ���΍쐬
                Files.createDirectories(dirPath);
            }
            Path localFilePath = Paths.get(dir, fileName);
            Files.deleteIfExists(localFilePath);
            Files.createFile(localFilePath); // �ۑ���̃t�@�C���쐬
            URL url = new URL(imgUrl); // URL�N���X�̃C���X�^���X�𐶐�
            try (InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(localFilePath.toFile(), false);) {
                // �擾�����摜�f�[�^���t�@�C���ɏ����o��
                int b;
                System.out.println(i);
                while ((b = is.read()) != -1) {
                    fos.write(b);
                }
            }
        }
    }

}
