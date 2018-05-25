package jp.co.goalist.crawler;

import java.util.Arrays;
import java.util.Random;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import jp.co.goalist.crawler.Article;


public class ConvertData {
    
    private String fileName;
    private byte[] fileHeader = new byte[14];
    private byte[] infoHeader = new byte[40];
    private byte[] colorPallet = new byte[256*4];
    private byte[] mainImageData;
    private int imageWidth;
    private int imageHeight;
    private String content;
    private Article article;
    
    ConvertData(Article article){
        
        this.article = article;
        this.fileName = this.article.getBmpFilePath();
        
        /* initialize meta data */
        Arrays.fill(this.fileHeader,(byte)0);
        Arrays.fill(this.infoHeader,(byte)0);
        Arrays.fill(this.colorPallet,(byte)0);
        
        /* set file header */
        /**
         * (array index) -> discription
         * 0,1 -> BM
         * 2~5 -> file size
         * 6,7 -> reserved region 1 : 0
         * 8,9 -> reserver region 2 : 0
         * 10~13 -> the size from head to image data
         *       -> file header     14bytes
         *       -> info header     40bytes
         *       -> color pallet  1024bytes(256 colors * 4 bytes)
         *       => sum           1078bytes
         *                 ...(0x)00 00 04 36 
         *                     -> 36 04 00 00
         */
        this.fileHeader[0]  = 0x42;
        this.fileHeader[1]  = 0x4d;
        this.fileHeader[10] = 0x36;
        this.fileHeader[11] = 0x04;
        
        /* set informatin header */
        this.infoHeader[0]  = (byte)0x28;
        this.infoHeader[12] = (byte)0x01;
        this.infoHeader[14] = (byte)0x08;
        /* set dpi */
        this.infoHeader[24] = (byte)0x01;
        this.infoHeader[28] = (byte)0x01;
        
        /* set pallet data */
        byte[] base = new byte[6];
        base[0] = (byte)0x00;
        base[1] = (byte)0x33;
        base[2] = (byte)0x66;
        base[3] = (byte)0x99;
        base[4] = (byte)0xcc;
        base[5] = (byte)0xff;
        
        /* 0~215 : combination by base bytes one by one */
        int posi = 0;
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                for(int k=0;k<6;k++){
                    this.colorPallet[posi] = base[k];
                    this.colorPallet[posi+1] = base[j];
                    this.colorPallet[posi+2] = base[i];
                    this.colorPallet[posi+3] = (byte)0x00;
                    posi += 4;
                }
            }
        }
        
        /* 216~255 : combination by base bytes at random */
        /*
        Random rand = new Random();
        for(int i = 864; i < 1024; i++){
            if(i%4 == 3){
                continue;
            }
            colorPallet[i] = base[rand.nextInt(6)];
        }
         */
        
        /* 216~255 */
        for(int i = 864; i < 1024; i++){
            if(i%4 == 3){
                continue;
            }
            this.colorPallet[i] = this.colorPallet[i-864];
        }
    
    
    }
    
    /* contents of Article -> byte[] -> bitmap */
    public void runConvert(){
        this.content = this.article.getContent();
        setImageSize();
        setMainData();
        writeInBmp();
        //print();
    }
    
    private void setImageSize(){
        int length = this.content.getBytes().length;
        this.imageHeight = (int)Math.ceil(Math.sqrt(length));
        this.imageWidth = this.imageHeight;
        
        /* set width to information header */
        byte[] widthBytes = toBytesFrom(this.imageWidth);
        for(int i=0; i < 4 ; i++){
            this.infoHeader[4+i] = widthBytes[i];
        }
        
        /* set height to information header */
        byte[] heightBytes = toBytesFrom(this.imageHeight);
        for(int i=0; i < 4 ; i++){
            this.infoHeader[8+i] = heightBytes[i];
        }
        
    
    }
    
    private void setMainData(){
        
        int oneLineWidth = (int)Math.ceil((double)imageWidth/4) * 4;
        this.mainImageData = new byte[oneLineWidth * imageHeight];
        Arrays.fill(this.mainImageData, (byte)0);
        
        byte[] data = content.getBytes();
        
        int imgPosi = 0;
        int strPosi = 0;
        for(int i = 0; i < imageHeight; i++){
            for(int j = 0; j < oneLineWidth; j++){
                if(j < imageWidth && strPosi < data.length){
                    this.mainImageData[imgPosi] = data[strPosi];
                    strPosi++;
                }
                imgPosi++;
            }
        }
        
        int fullsize = this.fileHeader.length
                        + this.infoHeader.length
                        + this.colorPallet.length
                        + this.mainImageData.length;
        byte[] dataBytes = toBytesFrom(fullsize);
        for(int i=0; i < 4 ; i++){
            this.fileHeader[2+i] = dataBytes[i];
        }
        
        
    }
    
    private void writeInBmp(){
        
        int flen = this.fileHeader.length;
        int inlen = this.infoHeader.length;
        int collen = this.colorPallet.length;
        int datalen = this.mainImageData.length;
        
        byte[] outBytes = new byte[flen+inlen+collen+datalen];
        
        /* copy fileHeader to outBytes */
        System.arraycopy(this.fileHeader,0,outBytes,0,flen);
        System.arraycopy(this.infoHeader,0,outBytes,flen,inlen);
        System.arraycopy(this.colorPallet,0,outBytes,flen+inlen,collen);
        System.arraycopy(this.mainImageData,0,outBytes,flen+inlen+collen,datalen);
        
        try{
            OutputStream out = new FileOutputStream(this.fileName);
            BufferedImage image = ImageIO.read( new ByteArrayInputStream(outBytes));
            ImageIO.write(image, "bmp", out);
            
        }catch( Exception e ){
            e.printStackTrace();
        }
    
    }
    
    private byte[] toBytesFrom(int a) {
        byte[] bs = new byte[4];
        bs[3] = (byte) (0x000000ff & (a >>> 24));
        bs[2] = (byte) (0x000000ff & (a >>> 16));
        bs[1] = (byte) (0x000000ff & (a >>> 8));
        bs[0] = (byte) (0x000000ff & (a));
        return bs;
    }
    
    public void print(){
        
        byte[] bytes = this.content.getBytes();
        
        int posi = 0;
        System.out.println("input string data:" + this.content);
        for(byte a: bytes){
            System.out.print(String.format("%02X ", a));
            if(posi%4 == 3){
                System.out.println();
            }
            posi++;
        }
        System.out.println("\n");
        
        posi = 0;
        System.out.println("information header:");
        for(byte a : this.infoHeader){
            System.out.print(String.format("%02X ", a));
            if(posi%4 == 3){
                System.out.println();
            }
            posi++;
        }
        System.out.println();
        
        
        System.out.println("-------color data:");
        for(int i = 0; i < 1024; i++){
            if(i%4 == 0){
                System.out.print(Integer.toHexString(i/4) + " : ");
            }
            System.out.print(String.format("%02X ", this.colorPallet[i]));
            if(i%4 == 3){
                System.out.println();
            }
            
        }
        System.out.println();
        
        System.out.println("--main image data:");
        for(int i = 0; i < this.mainImageData.length; i++){
            if(i%4 == 0){
                System.out.print(i + " : ");
            }
            System.out.print(String.format("%02X ", this.mainImageData[i]));
            if(i%4 == 3){
                System.out.println();
            }
            
        }
        System.out.println();
    
    }
    
    

}
