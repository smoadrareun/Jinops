package com.hebeu.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VerCode
 * @Author: Smoadrareun
 * @Description: TODO 生成图形验证码
 */
public class VerCode {
    public static String[] chars = new String[] {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
            "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
            "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    public static Map<String,String> getVerCode(Integer length){
        String verCode = "";
        Random rd = new Random();
        for (int i = 0; i < length; i++)
            verCode += IDUtil.chars[rd.nextInt(chars.length)];
        int width = 80;
        int height = 25;
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(rd.nextInt(100) + 155, rd.nextInt(100) + 155,
                    rd.nextInt(100) + 155));
            g.drawLine(rd.nextInt(width), rd.nextInt(height),
                    rd.nextInt(width), rd.nextInt(height));
        }
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, width - 1, height - 1);
        Font[] fonts = {new Font("隶书", Font.BOLD, 18),
                new Font("楷体", Font.BOLD, 18),
                new Font("宋体", Font.BOLD, 18),
                new Font("幼圆", Font.BOLD, 18)};
        for (int i = 0; i < length; i++) {
            g.setColor(new Color(rd.nextInt(150), rd.nextInt(150), rd
                    .nextInt(150)));
            g.setFont(fonts[rd.nextInt(fonts.length)]);
            g.drawString(verCode.charAt(i) + "", width / verCode.length() * i
                    + 2, 18);
        }
        g.dispose();
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "jpeg", bout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> map=new HashMap<>();
        map.put("verCode",MD5Util.getMD5Str(verCode.toLowerCase(Locale.ROOT)));
        map.put("verImage","data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(bout.toByteArray()));
        return map;
    }

}
