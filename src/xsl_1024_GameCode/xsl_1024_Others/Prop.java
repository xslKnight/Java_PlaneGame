package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import xsl_1024_GameCode.PlayPlane;

/**
 * ��Ϸ��������
 *
 */
public class Prop {
	//����+����
	public int x,y;//����
	public int width,height;//��С
	public int speed;//�ٶ�
	public String[] image={"ebu002.png","ebu003.png","yao.png","yao002.png","yaoyao.png","yaoyao002.png"};//ͼƬ
	public boolean isGood;//���ҷ��ж�
	public boolean isLive;//�Ƿ���Ч
	public int index;//���߻�ȡ�±꣨������
	public int addHp=500;//��Ѫ��
	public String bullet;//�任�ӵ�
	Random rd = new Random();//���������
	private int left_right = 5*(int)Math.pow(-1, rd.nextInt(2));//��������ƶ�
	//���캯��  �в�
	public Prop(int x, int y, int width, int height, int speed, boolean isGood, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.isGood = isGood;
		this.isLive = isLive;
		this.index = rd.nextInt(5);//�������0~6���±�
	}
	
	//���Ƶ���ͼƬ
	public void drawProp(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image[index]));
		//��ʼ��ͼ
		if(index == 2||index ==3 ){
			g.drawImage(img, x, y,20,20,null);
		}else{
			g.drawImage(img, x, y, 40,40,null);
		}
		//�����ƶ�����
		propMove();
	}
		
	//�����ƶ�����
	public void propMove() {
		if(isLive) {
			y += speed;
			x += left_right;
		}
		if(y>=PlayPlane.HEIGHT||y<=0) {
			isLive = false;
		}else{
			if(x>=PlayPlane.WIDTH-40){
				left_right = -5;
			}
			if(x<=0){
				left_right = 5;
			}
		}
		getProp();
	}
	
	//���߾��η���
	public Rectangle getPropRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	//������������ײ������ʰȡ���ߣ�
	public void getProp(){
		Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
		for(int i=0;i<PlayPlane.list_prop.size();i++){
			Prop p = PlayPlane.list_prop.get(i);
			if(p.getPropRectangle().intersects(hostRectangle)==true){
				p.isLive = false;
				switch (index) {
				case 0:
					PlayPlane.host_hp += addHp;
					break;
				case 1:
					PlayPlane.host_hp += 200;
					break;
				case 2:
					PlayPlane.p_bullet = "bullet001.png";
					PlayPlane.bullet_height = 20;
					PlayPlane.bullet_width = 20;
					PlayPlane.super_bullet += 100;
					break;
				case 3:
					PlayPlane.p_bullet = "bullet002.png";
					PlayPlane.bullet_height = 20;
					PlayPlane.bullet_width = 20;
					PlayPlane.super_bullet += 100;
					break;
				case 4:
					PlayPlane.host_hp = 10000;
					break;
				case 5:
					PlayPlane.host_score += 1000;
					break;
				default:
					break;
				}
				
				if(PlayPlane.host_hp>10000){
					PlayPlane.host_hp = 10000;
				}
				PlayPlane.list_prop.remove(i);
			}
		}
	}
}
