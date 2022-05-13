package com.hebeu.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VerCode
 * @Author: Smoadrareun
 * @Description: TODO 验证码工具类
 */
public class VerUtil {

    private static final Random random = new Random();

    public static String[] chars = new String[] {
            "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m",
            "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
            "z", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
            "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    public static String getRandomNumber(int length) {
        DecimalFormat decimalFormat = new DecimalFormat("0"+"0".repeat(Math.max(0, length - 1)));
        String str=String.valueOf(Math.pow(10, Math.max(0, length - 1)));
        str=str.substring(0,str.indexOf("."));
        return decimalFormat.format(random.nextInt(Integer.parseInt(str)));
    }

    public static String getRandom(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars[random.nextInt(chars.length)]);
        return String.valueOf(stringBuilder);
    }

    public static String getCodeImage(String code){
        int width = 80;
        int height = 25;
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(random.nextInt(100) + 155, random.nextInt(100) + 155,
                    random.nextInt(100) + 155));
            g.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, width - 1, height - 1);
        Font[] fonts = {new Font("隶书", Font.BOLD, 18),
                new Font("楷体", Font.BOLD, 18),
                new Font("宋体", Font.BOLD, 18),
                new Font("幼圆", Font.BOLD, 18)};
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random
                    .nextInt(150)));
            g.setFont(fonts[random.nextInt(fonts.length)]);
            g.drawString(code.charAt(i) + "", width / code.length() * i
                    + 2, 18);
        }
        g.dispose();
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "jpeg", bout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64,"+CodeUtil.byteToBase64(bout.toByteArray());
    }

}
