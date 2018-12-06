package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import xsl_1024_GameCode.PlayPlane;

public class Bullet {
	//����+����
	public int x,y;//����
	public int width,height;//��С
	public int speed;//�ٶ�
	public int power;//�˺�
	public String image;//ͼƬ
	public boolean isGood;//���ҷ��ж�
	public boolean isLive;//�Ƿ���Ч
	//���캯��  �в�
	public Bullet(int x,int y,int width,int height,int speed, int power, String image, boolean isGood, boolean isLive) {
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
	}
	//�����ӵ�
	public void drawBullet(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image));
		//��ʼ��ͼ
		g.drawImage(img, x, y, width,height, null);
		//�����ƶ�����
		bulletMove();
	}
	
	//�ӵ��ƶ�����
	public void bulletMove() {
		if(isGood) {//����˫���ӵ��ƶ�����
			y -= speed;
		}else {
			y += speed;
		}
		//�ж��Ƿ���Ч
		if(y<=0 || y>=PlayPlane.HEIGHT+50){
			isLive = false;
		}else {
			isLive = true;
		}
		//�����ӵ����䷽��
		bulletShoot();
	}
	
	//�ӵ�����
	public Rectangle getBulletRectangle() {
		return new Rectangle(x, y, width, height);
	}
	//�ӵ����䷽�� 
	public void bulletShoot() {
			if(isGood) {
			//�ӵ���ײ����
			Rectangle gBullet = this.getBulletRectangle();
			for(int i=0;i<PlayPlane.list_bullet.size();i++){
				Bullet b = PlayPlane.list_bullet.get(i);
				if(b.isGood==false){
					if(b.getBulletRectangle().intersects(gBullet)){
						this.isLive = false;
						b.isLive  = false;
						PlayPlane.list_bullet.remove(i);
					}
				}
			}
			//�����ӵ����ел�
			Rectangle bRectangle = getBulletRectangle();
			for(int i=0;i<PlayPlane.list_enmey.size();i++){
				//��ȡ�л�
				Enemy e = PlayPlane.list_enmey.get(i);
				//�ж��Ƿ����
				if(e.getEnemyRectangle().intersects(bRectangle)){
					e.hp -= power;
					this.isLive = false;//�ӵ�ʧЧ
					//�жϵл��Ƿ�����
					if(e.hp<=0){
						// ���ñ�ըЧ��
						Boom boom = new Boom(x, y);
						PlayPlane.list_boom.add(boom);
						//�л����������
						PlayPlane.list_enmey.remove(i);
						PlayPlane.count +=1;
						PlayPlane.host_score +=100;
						break;
					}
				}
			}	
		}
		//�л��ӵ�������
		else{
			//�ҵ�����λ��
			Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
			for(int i=0;i<PlayPlane.list_bullet.size();i++) {
				Bullet e = PlayPlane.list_bullet.get(i);
				//�ж��ӵ�����������
				if(e.getBulletRectangle().intersects(hostRectangle)) {
					//�ӵ���ʧ ������Ѫ
					e.isLive = false;
					PlayPlane.host_hp -= 100;
					if(PlayPlane.host_hp<=0) {
						PlayPlane.state = GameState.fail;
					}
					//�Ƴ��ӵ�
					PlayPlane.list_bullet.remove(i);
					break;
				}
			}
		}
	}

	
	
}
