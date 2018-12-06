package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import xsl_1024_GameCode.PlayPlane;

public class Bullet {
	//属性+方法
	public int x,y;//坐标
	public int width,height;//大小
	public int speed;//速度
	public int power;//伤害
	public String image;//图片
	public boolean isGood;//敌我方判断
	public boolean isLive;//是否有效
	//构造函数  有参
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
	//绘制子弹
	public void drawBullet(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image));
		//开始绘图
		g.drawImage(img, x, y, width,height, null);
		//调用移动方法
		bulletMove();
	}
	
	//子弹移动方法
	public void bulletMove() {
		if(isGood) {//敌我双方子弹移动方法
			y -= speed;
		}else {
			y += speed;
		}
		//判断是否有效
		if(y<=0 || y>=PlayPlane.HEIGHT+50){
			isLive = false;
		}else {
			isLive = true;
		}
		//调用子弹发射方法
		bulletShoot();
	}
	
	//子弹矩形
	public Rectangle getBulletRectangle() {
		return new Rectangle(x, y, width, height);
	}
	//子弹发射方法 
	public void bulletShoot() {
			if(isGood) {
			//子弹对撞方法
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
			//主机子弹打中敌机
			Rectangle bRectangle = getBulletRectangle();
			for(int i=0;i<PlayPlane.list_enmey.size();i++){
				//获取敌机
				Enemy e = PlayPlane.list_enmey.get(i);
				//判断是否打中
				if(e.getEnemyRectangle().intersects(bRectangle)){
					e.hp -= power;
					this.isLive = false;//子弹失效
					//判断敌机是否死亡
					if(e.hp<=0){
						// 设置爆炸效果
						Boom boom = new Boom(x, y);
						PlayPlane.list_boom.add(boom);
						//敌机死亡后操作
						PlayPlane.list_enmey.remove(i);
						PlayPlane.count +=1;
						PlayPlane.host_score +=100;
						break;
					}
				}
			}	
		}
		//敌机子弹打主机
		else{
			//找到主机位置
			Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
			for(int i=0;i<PlayPlane.list_bullet.size();i++) {
				Bullet e = PlayPlane.list_bullet.get(i);
				//判断子弹与主机相遇
				if(e.getBulletRectangle().intersects(hostRectangle)) {
					//子弹消失 主机掉血
					e.isLive = false;
					PlayPlane.host_hp -= 100;
					if(PlayPlane.host_hp<=0) {
						PlayPlane.state = GameState.fail;
					}
					//移除子弹
					PlayPlane.list_bullet.remove(i);
					break;
				}
			}
		}
	}

	
	
}
