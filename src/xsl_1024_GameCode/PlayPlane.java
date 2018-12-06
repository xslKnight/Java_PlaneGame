package xsl_1024_GameCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import xsl_1024_GameCode.xsl_1024_Others.Boom;
import xsl_1024_GameCode.xsl_1024_Others.Bullet;
import xsl_1024_GameCode.xsl_1024_Others.CloseWindow;
import xsl_1024_GameCode.xsl_1024_Others.Enemy;
import xsl_1024_GameCode.xsl_1024_Others.GameSound;
import xsl_1024_GameCode.xsl_1024_Others.GameState;
import xsl_1024_GameCode.xsl_1024_Others.KeyDown;
import xsl_1024_GameCode.xsl_1024_Others.Prop;
/**
 * @author 徐双翎
 * 重写父类的方法 ：
 * repaint --> update --> paint 三个方法在Component 中定义，作用：构建游戏界面
 * repaint();会话界面动作，默认设置填充颜色功能
 * update(); 在paint()基础上 完善绘图；
 * paint(); 执行用户自定义绘画操作
 */
@SuppressWarnings("serial")
public class PlayPlane extends Frame {
	//设置背景尺寸
	Image bufferImage = null;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	//获取颜色随机编码
	Random color_rd = new Random();
	//定义枚举类为一个对象
	public static GameState state = GameState.start;
	//定义游戏操作键盘变量
	public static boolean w=false,a=false,s=false,d=false,j=false,k=false;
	//定义游戏操作时间
	public static double startTime = 0;
	public static double endTime = 0;
	public static boolean timeFlag = true;
	//定义游戏参数
	 //默认关卡
	public static int customs = 1;
	 //运行状态
	private int bg_y;
	 //飞机位置
	public static int my_x=WIDTH/2-30;
	public static int my_y=600;
	 //背景图片
	public static String[] bg_image={"bg2.jpg","bg3.jpg","bg4.jpg"};
	 //飞机血量
	public static int host_hp = 5000;
	 //定义杀敌分数
	public static int count = 0;
	public static int host_score = 0;
	public static int boss_score = 0;
	//超级子弹数
	public static int super_bullet;
	//子弹大小
	public static int bullet_height=15,bullet_width=5;
	//定义子弹图片和集合
	public static ArrayList<Bullet> list_bullet =new ArrayList<Bullet>();
	public static String p_bullet = "bullet1.png";
	//存放飞机集合
	public static List<Enemy> list_enmey = new ArrayList<Enemy>();
	//存放爆炸集合
	public static List<Boom> list_boom = new ArrayList<Boom>();
	//存放道具集合
	public static List<Prop> list_prop = new ArrayList<Prop>();
	//加入敌机
	Random en_rd = new Random();
	
	//定义一个内部类,执行一个线程
	class MyThread extends Thread{
		public void run(){
			while(true){
				try{
					//执行会话界面动作
					/**
					 * repaint()方法特性：
					 * If this component is a lightweight component, this method
					 * causes a call to this component's {@code paint} method
					 * as soon as possible.  Otherwise, this method causes a call to
					 * this component's {@code update} method as soon as possible.
					 */
					repaint();//开始绘制背景图片
					planeMove();//飞机移动方法
					Thread.sleep(80);
				}catch(Exception e){
					//TODO
					e.printStackTrace();
				}
			}
			
			
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(bufferImage == null){
			bufferImage = this.createImage(WIDTH, HEIGHT);
		}
		//Graphics 绘图类
		Graphics bg = bufferImage.getGraphics();
		paint(bg);
		g.drawImage(bufferImage, 0, 0, null);	
	}

	@Override
	public void paint(Graphics g) {
		//加入状态判断  调用枚举类
		switch (state) {
		case start:
			gameStart(g);
			break;
		case run:
			gameRun(g);
			break;
		case success:
			
			break;
		case fail:
			gameOver(g);
			break;
		default:
			break;
		}
	}
	
	//游戏开始主界面
	private void gameStart(Graphics g) {
		//创建背景初始化游戏
		Toolkit tk = Toolkit.getDefaultToolkit();
		//获取图片资源
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/begin111.jpg"));
		//RGB颜色编码
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT,null);
		g.setColor(new Color(color_rd.nextInt(256),
							 color_rd.nextInt(256),
							 color_rd.nextInt(256)));
		//设置字体内容
		g.setFont(new Font("隶书",Font.BOLD,30));
		//绘制文字内容
		g.drawString("按下Enter开始游戏", 250, 650);
	}
	//游戏结束
	private void gameOver(Graphics g) {
		//创建背景初始化游戏
		Toolkit tk = Toolkit.getDefaultToolkit();
		//获取图片资源
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/GAMEOVER3.gif"));
		//RGB颜色编码
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT,null);
		g.setColor(new Color(color_rd.nextInt(256),
							 color_rd.nextInt(256),
							 color_rd.nextInt(256)));
		//设置字体内容
		g.setFont(new Font("隶书",Font.BOLD,30));
		//绘制文字内容
		g.drawString("菜鸡！输了吧！！！！",300, 600);
		g.drawString("菜鸡！再来一次？？？",300, 650);
	}

	//游戏运行时界面
	public void gameRun(Graphics g){
		//创建背景初始化游戏
		Toolkit tk = Toolkit.getDefaultToolkit();
		//获取图片资源
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/"+bg_image[customs>3 ? 1 : customs-1]));
		//开始绘图 绘制背景图片
		g.drawImage(bg, 0,bg_y, WIDTH,HEIGHT, null);
		//背景移动效果
		bg_y+=4;
		//重复绘制背景图片
		g.drawImage(bg,0,-HEIGHT+bg_y,WIDTH,HEIGHT,null);
		if(bg_y>=HEIGHT){//清除缓存，以使背景重复替代
			bg_y=0;
		}
		//定义飞机对象
		Image plane = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/player.png"));
		//绘制飞机图片
		g.drawImage(plane, my_x, my_y, null);
		
		//调用子弹类，发射子弹
		for(int i=0;i<list_bullet.size();i++) {
			Bullet b = list_bullet.get(i);
			//判断子弹
			if(b.isLive) {
				b.drawBullet(g);
			}else {
				list_bullet.remove(i);
			}
		}
		
		//敌机子弹
		for(int i=0;i<list_enmey.size();i++) {
			//取出敌机
			Enemy p = list_enmey.get(i);
			//判断是否死亡
			if(p.isLive) {
				p.drawEnemy(g);
			}else {
				list_enmey.remove(i);
			}
			//加入子弹
			if(en_rd.nextInt(5)==0) {
				Bullet b = new Bullet(p.x+7, p.y+6, 5,15, 50, 50, "bullet1.png", false, true);
				list_bullet.add(b);
			}
		}
		//将敌机加入到集合中
		if(en_rd.nextInt(5)==0) {
			Enemy p = new Enemy(en_rd.nextInt(WIDTH), 0, 30, 40, 20, 50
					, "enermy3.png", false, true, 500);
			list_enmey.add(p);
		}
		
		//将道具加入到集合中
		for(int i=0;i<list_prop.size();i++){
			Prop p = list_prop.get(i);
			if(p.isLive){
				p.drawProp(g);
			}else{
				list_prop.remove(i);
			}
		}
		if(en_rd.nextInt(30)==0){
			Prop p =new Prop(en_rd.nextInt(WIDTH), 0, 40, 40, 10, false, true);
			list_prop.add(p);
		}
		
		
		
		//加入爆炸效果图
		for (int i = 0; i < list_boom.size(); i++) {
			Boom b = list_boom.get(i);
			if(b.isLive){
				b.drawBoom(g);
			}else{
				list_boom.remove(i);
			}
		}
		
		//显示游戏基本信息
		g.setColor(Color.WHITE);
		g.setFont(new Font("楷体",Font.BOLD,20));
		g.drawString("关    卡:第"+customs+"关", 10, 50);
		g.drawString("血    量:"+host_hp, 10, 70);
		g.drawString("得    分:"+host_score, 10, 90);
		g.drawString("杀 敌 数:"+count, 10, 110);
		g.drawString("通关分数:"+10000, 10, 130);
		g.drawString("超级子弹:"+super_bullet, 10, 150);
		
		
		//显示时间
//		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		Calendar c = new GregorianCalendar();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		
		g.drawString(hour+":"+minute+":"+seconds, 800, 50);
	}
	
	//飞机移动方法
	public void planeMove(){
		if(w){
			if(my_y>0){
				my_y-=15;
			}
		}
		if(s){
			if(my_y<HEIGHT - 30){
				my_y+=15;
			}
		}
		if(a){
			if(my_x>-15){
				my_x-=15;
			}
		}
		if(d){
			if(my_x<WIDTH-15){
				my_x+=15;
			}
		}
		if(j){
			//子弹设置（位置，大小，形状，伤害等）
			Bullet b1 = new Bullet(my_x+15,my_y-20, bullet_width, bullet_height,
					30, 100, p_bullet, true, true);
			Bullet b2 = new Bullet(my_x+30,my_y-20, bullet_width, bullet_height,
					30, 100, p_bullet, true, true);
			//子弹加入集合
			list_bullet.add(b1);
			list_bullet.add(b2);
			//调用方法，加入发射声音  匿名调用 每次使用一次
			new GameSound("Beam.mp3", false).start();
		}
		
	}
	
	//构造方法
	public PlayPlane(){
		//窗体是否可见
		this.setVisible(true);
		//窗体标题设置
		this.setTitle("飞机大战游戏 2018-06-20");
		//窗体大小设置
		this.setSize(WIDTH, HEIGHT);
		//设置窗口是否可以拉伸
		this.setResizable(false);
		//设置窗口位置
		this.setLocationRelativeTo(null);
		//退出窗体,鼠标事件监听
		this.addWindowListener(new CloseWindow());
		//播放音乐
		GameSound sound = new GameSound("BGM_0001.mp3",true);
		//启动音乐播放线程
		sound.start();
		
		//加入开始界面背景图片
		MyThread myThread = new MyThread();
		myThread.start();
		
		//键盘事件监听
		this.addKeyListener(new KeyDown());
	}
	public static void main(String[] args){
		new PlayPlane();	
	}
}
