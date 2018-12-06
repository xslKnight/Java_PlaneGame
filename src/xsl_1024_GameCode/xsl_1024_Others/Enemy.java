package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import xsl_1024_GameCode.PlayPlane;

/**
 * 敌机类，生成敌机
 * @author 徐双翎
 *
 */
public class Enemy {
	//属性+方法
	public int x,y;//坐标
	public int width,height;//大小
	public int speed;//速度
	public int power;//伤害
	public String image;//图片
	public boolean isGood;//敌我方判断
	public boolean isLive;//是否有效
	public int hp;//血量
	//构造函数  有参
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
	
	//绘制敌机
	public void drawEnemy(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/enermy3.png"));
		//开始绘图
		g.drawImage(img, x, y, null);
		//调用移动方法
		enemyMove();
	}
	
	//敌机移动方法
	public void enemyMove() {
		if(isLive) {
			y += speed;
		}
		if(y>=PlayPlane.HEIGHT) {
			isLive = false;
		}
		hitHost();
	}
	
	//敌机矩形方法
	public Rectangle getEnemyRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	//敌机和主机相撞
	public void hitHost() {
		Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
		for(int i=0;i<PlayPlane.list_enmey.size();i++) {
			Enemy e = PlayPlane.list_enmey.get(i);
			//判断主机与敌机是否相遇
			if(e.getEnemyRectangle().intersects(hostRectangle)==true) {
				//敌机亡，主机掉血
				e.isLive = false;
				PlayPlane.host_hp -= 100;
				if(PlayPlane.host_hp<=0) {
					PlayPlane.state = GameState.fail;
				}
				//杀敌数量/得分加
				PlayPlane.count++;
				PlayPlane.host_score += 100;
				//判断总分大于一定分数 BOSS出现
				if(PlayPlane.host_score>100000) {
					
				}
				//移除敌机
				PlayPlane.list_enmey.remove(i);
				break;
			}
		}
	}
}
