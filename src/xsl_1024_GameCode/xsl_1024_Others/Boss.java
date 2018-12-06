package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import xsl_1024_GameCode.PlayPlane;

public class Boss {
	//����+����
	public int x,y;//����
	public int width=300,height=200;//��С
	public int speed;//�ٶ�
	public int power=500;//�˺�
	public String[] image= {"boss001.jpg","boss002.png","boss003.gif"};//ͼƬ
	public int index;
	public boolean isGood;//���ҷ��ж�
	public boolean isLive;//�Ƿ���Ч
	Random rd = new Random();//���������
	private int left_right = 5*(int)Math.pow(-1, rd.nextInt(2));//��������ƶ�
	//���캯��
	public Boss(int x, int y, int speed, String[] image, boolean isGood, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.image = image;
		this.isGood = isGood;
		this.isLive = isLive;
	}
	//����boss
	public void drawBoss(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image[index]));
		//��ʼ��ͼ
		g.drawImage(img, x, y, width,height, null);
		//�����ƶ�����
		bossMove();
	}
	//�л��ƶ�����
	public void bossMove() {
		if(isLive) {
			x += left_right;
		}
		if(x>=PlayPlane.WIDTH-40){
			left_right = -5;
		}
		if(x<=0){
			left_right = 5;
		}
		bossHit();
	}
	//boss���ο�
	public Rectangle getBossRectangle() {
		return new Rectangle(x, y, width, height);
	}
	//boss��������
	public void bossHit() {
		Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
		for(int i=0;i<PlayPlane.list_enmey.size();i++) {
			Enemy e = PlayPlane.list_enmey.get(i);
			//�ж�������л��Ƿ�����
			if(e.getEnemyRectangle().intersects(hostRectangle)==true) {
				//�л�����������Ѫ
				e.isLive = false;
				PlayPlane.host_hp -= power;
				if(PlayPlane.host_hp<=0) {
					PlayPlane.state = GameState.fail;
				}
				//ɱ������/�÷ּ�
				PlayPlane.count++;
				PlayPlane.host_score += 100;
				//�Ƴ��л�
				PlayPlane.list_enmey.remove(i);
				break;
			}
		}
	}
}
