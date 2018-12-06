package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import xsl_1024_GameCode.PlayPlane;

/**
 * �л��࣬���ɵл�
 * @author ��˫��
 *
 */
public class Enemy {
	//����+����
	public int x,y;//����
	public int width,height;//��С
	public int speed;//�ٶ�
	public int power;//�˺�
	public String image;//ͼƬ
	public boolean isGood;//���ҷ��ж�
	public boolean isLive;//�Ƿ���Ч
	public int hp;//Ѫ��
	//���캯��  �в�
	public Enemy(int x, int y, int width, int height, int speed, int power, String image, boolean isGood,
			boolean isLive, int hp) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.power = power;
		this.image = image;
		this.isGood = isGood;
		this.isLive = isLive;
		this.hp = hp;
	}
	
	//���Ƶл�
	public void drawEnemy(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/enermy3.png"));
		//��ʼ��ͼ
		g.drawImage(img, x, y, null);
		//�����ƶ�����
		enemyMove();
	}
	
	//�л��ƶ�����
	public void enemyMove() {
		if(isLive) {
			y += speed;
		}
		if(y>=PlayPlane.HEIGHT) {
			isLive = false;
		}
		hitHost();
	}
	
	//�л����η���
	public Rectangle getEnemyRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	//�л���������ײ
	public void hitHost() {
		Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
		for(int i=0;i<PlayPlane.list_enmey.size();i++) {
			Enemy e = PlayPlane.list_enmey.get(i);
			//�ж�������л��Ƿ�����
			if(e.getEnemyRectangle().intersects(hostRectangle)==true) {
				//�л�����������Ѫ
				e.isLive = false;
				PlayPlane.host_hp -= 100;
				if(PlayPlane.host_hp<=0) {
					PlayPlane.state = GameState.fail;
				}
				//ɱ������/�÷ּ�
				PlayPlane.count++;
				PlayPlane.host_score += 100;
				//�ж��ִܷ���һ������ BOSS����
				if(PlayPlane.host_score>100000) {
					
				}
				//�Ƴ��л�
				PlayPlane.list_enmey.remove(i);
				break;
			}
		}
	}
}
