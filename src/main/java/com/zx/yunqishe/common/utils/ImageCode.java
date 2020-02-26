package com.zx.yunqishe.common.utils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

//验证字符集
public class ImageCode {
	
	// 字符数量  
    private static final int COUNT = 4;  
    // 干扰线数量  
    private static final int LINES = 4;  
    // 宽度  
    private static final int WIDTH = 90;  
    // 高度  
    private static final int HEIGHT = 40;  
    // 字体大小  
    private static final int SIZE = 30; 
    //字体大小偏移（振幅）
    private static final int A = 1;
    // 验证码字符集  
 	private static final String CODES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 	//字体名称
    private static String[] FAMILY = {Font.DIALOG,Font.DIALOG_INPUT,Font.MONOSPACED,Font.SANS_SERIF,Font.SERIF,"Microsoft Yahei UI","宋体", "仿宋", "黑体","Times New Roman"};
	//生成验证码图片
	public static Object[] getImageAndCode(){

		//1.在内存中得到一个图片缓冲区
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		//2.得到一个画笔
		Graphics2D g =  image.createGraphics();
		//3.设置画笔颜色
		g.setColor(Color.white);
		//4.绘制矩形图像图像的背景
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//5.画随机字符
		Random random = new Random();
		int x =WIDTH/COUNT/2;
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<COUNT;i++){
			//在字符集长度内生成一个随机整数用于取随机字符
			int n = random.nextInt(CODES.length());
			String s = CODES.charAt(n)+"";
			//得到一个随机角度，Graphics2D的方法
			int degree = random.nextInt(20)*(random.nextBoolean()?-1:1);
			//得到这个随机角度内旋转的字符区域
			g.rotate(degree*Math.PI/180, x, 2/3*HEIGHT);
			//画字符
			//设置画笔颜色
			g.setColor(getRandomColor(random));
			//设置字体大小和样式
			g.setFont(getRandomFont(random));
			g.drawString(s,x, HEIGHT*2/3);
			//角度回正
			g.rotate(-degree*Math.PI/180, x, 2/3*HEIGHT);
			//记录字符
			sb.append(s);
			x+=WIDTH/COUNT;
		}
		//6.画干扰线
		for(int i = 0;i<LINES;i++){
			int width = WIDTH/COUNT;
			int sx = random.nextInt(width);
			int sy = random.nextInt(HEIGHT);
			int ex = random.nextInt(width)+(width)*(COUNT-1);
			int ey = random.nextInt(HEIGHT);
			g.setColor(getRandomColor(random));
			//设置画笔粗细
			g.setStroke(new BasicStroke(1.2f));
			g.drawLine(sx, sy, ex, ey);
		};
		return new Object[]{image,sb.toString()};
	}
    
	private static Font getRandomFont(Random random) {
		int index = random.nextInt(FAMILY.length);
		String family = FAMILY[index];
		int style = random.nextInt(3);
		int size = (random.nextBoolean()?1:-1)*random.nextInt(A)+SIZE;
		Font font = new Font(family,style,size);
		return font;
	}

	private static Color getRandomColor(Random random) {
		int r = random.nextInt(200);
		int g = random.nextInt(200);
		int b = random.nextInt(200);
		Color color= new Color(r,g,b);
		return color;
	}
	
}
 

