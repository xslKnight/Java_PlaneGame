package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import xsl_1024_GameCode.PlayPlane;

public class KeyDown extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			//执行enter键的内容，改变状态
			if(PlayPlane.state==GameState.start){
				//初始化成功，开始加载游戏
				PlayPlane.state=GameState.run;
				//初始化游戏时间,获取当时时间（以毫秒为单位）
				PlayPlane.startTime = System.currentTimeMillis();
			}
			break;
		case KeyEvent.VK_W:
			PlayPlane.w=true;
			break;
		case KeyEvent.VK_A:
			PlayPlane.a=true;
			break;
		case KeyEvent.VK_S:
			PlayPlane.s=true;
			break;
		case KeyEvent.VK_D:
			PlayPlane.d=true;
			break;
		case KeyEvent.VK_J:
			PlayPlane.j=true;
			//超级子弹
			if(PlayPlane.j){
				if(PlayPlane.super_bullet!=0){
					PlayPlane.super_bullet -= 1;
				}else{
					PlayPlane.p_bullet="bullet1.png";
					PlayPlane.bullet_height = 15;
					PlayPlane.bullet_width = 5;
				}
			}
			break;
		case KeyEvent.VK_K:
//			PlayPlane.k=true;
			if(PlayPlane.super_bullet>0){
				//发送10组
				for (int i = 0; i < 15; i++) {
					Bullet b = new Bullet(5+70*i, PlayPlane.HEIGHT, 
							50, 30, 20, 500, "hero.GIF", true, true);
					PlayPlane.list_bullet.add(b);
				}
				PlayPlane.super_bullet-=1;
			}
			break;
		default:break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_W:PlayPlane.w=false;break;
		case KeyEvent.VK_A:PlayPlane.a=false;break;
		case KeyEvent.VK_S:PlayPlane.s=false;break;
		case KeyEvent.VK_D:PlayPlane.d=false;break;
		case KeyEvent.VK_J:PlayPlane.j=false;break;
		default:break;
		}
	}
	
}
