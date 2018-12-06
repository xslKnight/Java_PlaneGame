package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import xsl_1024_GameCode.PlayPlane;

/**
 * 游戏道具生成
 *
 */
public class Prop {
	//属性+方法
	public int x,y;//坐标
	public int width,height;//大小
	public int speed;//速度
	public String[] image={"ebu002.png","ebu003.png","yao.png","yao002.png","yaoyao.png","yaoyao002.png"};//图片
	public boolean isGood;//敌我方判断
	public boolean isLive;//是否有效
	public int index;//道具获取下标（索引）
	public int addHp=500;//加血量
	public String bullet;//变换子弹
	Random rd = new Random();//随机数生成
	private int left_right = 5*(int)Math.pow(-1, rd.nextInt(2));//随机左右移动
	//构造函数  有参
	public Prop(int x, int y, int width, int height, int speed, boolean isGood, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.isGood = isGood;
		this.isLive = isLive;
		this.index = rd.nextInt(5);//随机生成0~6的下标
	}
	
	//绘制道具图片
	public void drawProp(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image[index]));
		//开始绘图
		if(index == 2||index ==3 ){
			g.drawImage(img, x, y,20,20,null);
		}else{
			g.drawImage(img, x, y, 40,40,null);
		}
		//调用移动方法
		propMove();
	}
		
	//道具移动方法
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
	
	//道具矩形方法
	public Rectangle getPropRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	//道具与主机碰撞（主机拾取道具）
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
