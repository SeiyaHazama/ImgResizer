/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.aitclab;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

/**
 *
 * @author K15103(Seiya Hazama)
 */
public class ImgResizer {

    /**
     * 画像をリサイズして出力します。
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String url = args[0];
        double size = Double.parseDouble(args[1]);
        String ext = getExtension(url);
        String filename = "resize_" + getDate() + "." + ext;
        try {
            System.out.println("Image file reading...");
            BufferedImage src_img = ImageIO.read(new File(url));
            File os = new File(filename);
            System.out.println("Image resizing...");
            ImageIO.write(convertImage(src_img, size), ext, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Resized image is exported.\nFile name is \"" + filename + "\"");
    }
    
    /**
     * ファイル名から拡張子を取得します。
     * @param url
     * @return String
     */
    private static String getExtension(String url) {
        int size = url.length();
        int start = url.indexOf(".");
        return url.substring(start + 1, size);
    }
    
    /**
     * 画像をアフィン変換でリサイズします。
     * @param src_img
     * @return BufferedImage
     */
    private static BufferedImage convertImage(BufferedImage src_img, double size) {
        int width = (int) (src_img.getWidth() * size);
        int height = (int) (src_img.getHeight() * size);
        BufferedImage cnv_img = new BufferedImage(width, height, src_img.getType());
        AffineTransform transform = AffineTransform.getScaleInstance(size, size);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
        op.filter(src_img, cnv_img);
        return cnv_img;
    }
    
    /**
     * ファイル名に使用する現在日時と時刻を取得します。
     * @return String
     */
    private static String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.now();
        String date_str = dtf.format(ldt);
        return date_str;
    }

}
