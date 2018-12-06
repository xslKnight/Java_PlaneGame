package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import xsl_1024_GameCode.PlayPlane;

public class Boss {
	//属性+方法
	public int x,y;//坐标
	public int width=300,height=200;//大小
	public int speed;//速度
	public int power=500;//伤害
	public String[] image= {"boss001.jpg","boss002.png","boss003.gif"};//图片
	public int index;
	public boolean isGood;//敌我方判断
	public boolean isLive;//是否有效
	Random rd = new Random();//随机数生成
	private int left_right = 5*(int)Math.pow(-1, rd.nextInt(2));//随机左右移动
	//构造函数
	public Boss(int x, int y, int speed, String[] image, boolean isGood, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.image = image;
		this.isGood = isGood;
		this.isLive = isLive;
	}
	//绘制boss
	public void drawBoss(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(Bullet.class.
				getClassLoader().getResource("image/"+image[index]));
		//开始绘图
		g.drawImage(img, x, y, width,height, null);
		//调用移动方法
		bossMove();
	}
	//敌机移动方法
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
	//boss矩形框
	public Rectangle getBossRectangle() {
		return new Rectangle(x, y, width, height);
	}
	//boss攻击方法
	public void bossHit() {
		Rectangle hostRectangle = new Rectangle(PlayPlane.my_x, PlayPlane.my_y, 50, 44);
		for(int i=0;i<PlayPlane.list_enmey.size();i++) {
			Enemy e = PlayPlane.list_enmey.get(i);
			//判断主机与敌机是否相遇
			if(e.getEnemyRectangle().intersects(hostRectangle)==true) {
				//敌机亡，主机掉血
				e.isLive = false;
				PlayPlane.host_hp -= power;
				if(PlayPlane.host_hp<=0) {
					PlayPlane.state = GameState.fail;
				}
				//杀敌数量/得分加
				PlayPlane.count++;
				PlayPlane.host_score += 100;
				//移除敌机
				PlayPlane.list_enmey.remove(i);
				break;
			}
		}
	}
}
