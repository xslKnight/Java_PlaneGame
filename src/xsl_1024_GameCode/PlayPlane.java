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
 * @author ��˫��
 * ��д����ķ��� ��
 * repaint --> update --> paint ����������Component �ж��壬���ã�������Ϸ����
 * repaint();�Ự���涯����Ĭ�����������ɫ����
 * update(); ��paint()������ ���ƻ�ͼ��
 * paint(); ִ���û��Զ���滭����
 */
@SuppressWarnings("serial")
public class PlayPlane extends Frame {
	//���ñ����ߴ�
	Image bufferImage = null;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	//��ȡ��ɫ�������
	Random color_rd = new Random();
	//����ö����Ϊһ������
	public static GameState state = GameState.start;
	//������Ϸ�������̱���
	public static boolean w=false,a=false,s=false,d=false,j=false,k=false;
	//������Ϸ����ʱ��
	public static double startTime = 0;
	public static double endTime = 0;
	public static boolean timeFlag = true;
	//������Ϸ����
	 //Ĭ�Ϲؿ�
	public static int customs = 1;
	 //����״̬
	private int bg_y;
	 //�ɻ�λ��
	public static int my_x=WIDTH/2-30;
	public static int my_y=600;
	 //����ͼƬ
	public static String[] bg_image={"bg2.jpg","bg3.jpg","bg4.jpg"};
	 //�ɻ�Ѫ��
	public static int host_hp = 5000;
	 //����ɱ�з���
	public static int count = 0;
	public static int host_score = 0;
	public static int boss_score = 0;
	//�����ӵ���
	public static int super_bullet;
	//�ӵ���С
	public static int bullet_height=15,bullet_width=5;
	//�����ӵ�ͼƬ�ͼ���
	public static ArrayList<Bullet> list_bullet =new ArrayList<Bullet>();
	public static String p_bullet = "bullet1.png";
	//��ŷɻ�����
	public static List<Enemy> list_enmey = new ArrayList<Enemy>();
	//��ű�ը����
	public static List<Boom> list_boom = new ArrayList<Boom>();
	//��ŵ��߼���
	public static List<Prop> list_prop = new ArrayList<Prop>();
	//����л�
	Random en_rd = new Random();
	
	//����һ���ڲ���,ִ��һ���߳�
	class MyThread extends Thread{
		public void run(){
			while(true){
				try{
					//ִ�лỰ���涯��
					/**
					 * repaint()�������ԣ�
					 * If this component is a lightweight component, this method
					 * causes a call to this component's {@code paint} method
					 * as soon as possible.  Otherwise, this method causes a call to
					 * this component's {@code update} method as soon as possible.
					 */
					repaint();//��ʼ���Ʊ���ͼƬ
					planeMove();//�ɻ��ƶ�����
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
		//Graphics ��ͼ��
		Graphics bg = bufferImage.getGraphics();
		paint(bg);
		g.drawImage(bufferImage, 0, 0, null);	
	}

	@Override
	public void paint(Graphics g) {
		//����״̬�ж�  ����ö����
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
	
	//��Ϸ��ʼ������
	private void gameStart(Graphics g) {
		//����������ʼ����Ϸ
		Toolkit tk = Toolkit.getDefaultToolkit();
		//��ȡͼƬ��Դ
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/begin111.jpg"));
		//RGB��ɫ����
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT,null);
		g.setColor(new Color(color_rd.nextInt(256),
							 color_rd.nextInt(256),
							 color_rd.nextInt(256)));
		//������������
		g.setFont(new Font("����",Font.BOLD,30));
		//������������
		g.drawString("����Enter��ʼ��Ϸ", 250, 650);
	}
	//��Ϸ����
	private void gameOver(Graphics g) {
		//����������ʼ����Ϸ
		Toolkit tk = Toolkit.getDefaultToolkit();
		//��ȡͼƬ��Դ
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/GAMEOVER3.gif"));
		//RGB��ɫ����
		g.drawImage(bg, 0, 0, WIDTH, HEIGHT,null);
		g.setColor(new Color(color_rd.nextInt(256),
							 color_rd.nextInt(256),
							 color_rd.nextInt(256)));
		//������������
		g.setFont(new Font("����",Font.BOLD,30));
		//������������
		g.drawString("�˼������˰ɣ�������",300, 600);
		g.drawString("�˼�������һ�Σ�����",300, 650);
	}

	//��Ϸ����ʱ����
	public void gameRun(Graphics g){
		//����������ʼ����Ϸ
		Toolkit tk = Toolkit.getDefaultToolkit();
		//��ȡͼƬ��Դ
		Image bg = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/"+bg_image[customs>3 ? 1 : customs-1]));
		//��ʼ��ͼ ���Ʊ���ͼƬ
		g.drawImage(bg, 0,bg_y, WIDTH,HEIGHT, null);
		//�����ƶ�Ч��
		bg_y+=4;
		//�ظ����Ʊ���ͼƬ
		g.drawImage(bg,0,-HEIGHT+bg_y,WIDTH,HEIGHT,null);
		if(bg_y>=HEIGHT){//������棬��ʹ�����ظ����
			bg_y=0;
		}
		//����ɻ�����
		Image plane = tk.getImage(PlayPlane.class.getClassLoader()
				.getResource("image/player.png"));
		//���Ʒɻ�ͼƬ
		g.drawImage(plane, my_x, my_y, null);
		
		//�����ӵ��࣬�����ӵ�
		for(int i=0;i<list_bullet.size();i++) {
			Bullet b = list_bullet.get(i);
			//�ж��ӵ�
			if(b.isLive) {
				b.drawBullet(g);
			}else {
				list_bullet.remove(i);
			}
		}
		
		//�л��ӵ�
		for(int i=0;i<list_enmey.size();i++) {
			//ȡ���л�
			Enemy p = list_enmey.get(i);
			//�ж��Ƿ�����
			if(p.isLive) {
				p.drawEnemy(g);
			}else {
				list_enmey.remove(i);
			}
			//�����ӵ�
			if(en_rd.nextInt(5)==0) {
				Bullet b = new Bullet(p.x+7, p.y+6, 5,15, 50, 50, "bullet1.png", false, true);
				list_bullet.add(b);
			}
		}
		//���л����뵽������
		if(en_rd.nextInt(5)==0) {
			Enemy p = new Enemy(en_rd.nextInt(WIDTH), 0, 30, 40, 20, 50
					, "enermy3.png", false, true, 500);
			list_enmey.add(p);
		}
		
		//�����߼��뵽������
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
		
		
		
		//���뱬ըЧ��ͼ
		for (int i = 0; i < list_boom.size(); i++) {
			Boom b = list_boom.get(i);
			if(b.isLive){
				b.drawBoom(g);
			}else{
				list_boom.remove(i);
			}
		}
		
		//��ʾ��Ϸ������Ϣ
		g.setColor(Color.WHITE);
		g.setFont(new Font("����",Font.BOLD,20));
		g.drawString("��    ��:��"+customs+"��", 10, 50);
		g.drawString("Ѫ    ��:"+host_hp, 10, 70);
		g.drawString("��    ��:"+host_score, 10, 90);
		g.drawString("ɱ �� ��:"+count, 10, 110);
		g.drawString("ͨ�ط���:"+10000, 10, 130);
		g.drawString("�����ӵ�:"+super_bullet, 10, 150);
		
		
		//��ʾʱ��
//		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		Calendar c = new GregorianCalendar();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		
		g.drawString(hour+":"+minute+":"+seconds, 800, 50);
	}
	
	//�ɻ��ƶ�����
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
			//�ӵ����ã�λ�ã���С����״���˺��ȣ�
			Bullet b1 = new Bullet(my_x+15,my_y-20, bullet_width, bullet_height,
					30, 100, p_bullet, true, true);
			Bullet b2 = new Bullet(my_x+30,my_y-20, bullet_width, bullet_height,
					30, 100, p_bullet, true, true);
			//�ӵ����뼯��
			list_bullet.add(b1);
			list_bullet.add(b2);
			//���÷��������뷢������  �������� ÿ��ʹ��һ��
			new GameSound("Beam.mp3", false).start();
		}
		
	}
	
	//���췽��
	public PlayPlane(){
		//�����Ƿ�ɼ�
		this.setVisible(true);
		//�����������
		this.setTitle("�ɻ���ս��Ϸ 2018-06-20");
		//�����С����
		this.setSize(WIDTH, HEIGHT);
		//���ô����Ƿ��������
		this.setResizable(false);
		//���ô���λ��
		this.setLocationRelativeTo(null);
		//�˳�����,����¼�����
		this.addWindowListener(new CloseWindow());
		//��������
		GameSound sound = new GameSound("BGM_0001.mp3",true);
		//�������ֲ����߳�
		sound.start();
		
		//���뿪ʼ���汳��ͼƬ
		MyThread myThread = new MyThread();
		myThread.start();
		
		//�����¼�����
		this.addKeyListener(new KeyDown());
	}
	public static void main(String[] args){
		new PlayPlane();	
	}
}
