package com.bzn.fundamental.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 随机生成图片工具类
 * 
 * @author：fengli
 * @since：2016年10月18日 下午6:47:55
 * @version:
 */
public class RandomImgCodeUtils {

	/**
	 * 生成验证码图片
	 * 
	 * @param randomCode
	 * @param os
	 * @throws IOException
	 */
	public static void createImg(String randomCode, OutputStream os) throws IOException {
		Font imgFont = new Font("Times New Roman", Font.BOLD, 20);
		char[] vs = randomCode.toCharArray();
		int width = 100, height = 30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(imgFont);

		g.setColor(getRandColor(160, 200));

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < 70; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		for (int i = 0; i < vs.length; i++) {
			String rand = String.valueOf(vs[i]);
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
					20 + random.nextInt(110)));
			g.drawString(rand, 20 * i + 18, 22);

		}

		g.dispose();
		ImageIO.write(image, "JPEG", os);
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static void main(String[] args) {
		try {
			FileOutputStream os = new FileOutputStream("c:/randomCode.JPEG");
			createImg("1423", os);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
