package xsl_1024_GameCode.xsl_1024_Others;

import java.io.InputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class GameSound extends Thread{
	private String mp3Url;
	private boolean isloop;
	//生成构造函数
	public GameSound(String mp3Url, boolean isloop) {
		super();
		this.mp3Url = mp3Url;
		this.isloop = isloop;	
	}
	//重写父类run()方法
	@Override
	public void run() {
		super.run();
		try{
			do{
				InputStream bgMusic = GameSound.class.getClassLoader().getResourceAsStream("music/"+mp3Url);
				//播放，调用播放函数
				AdvancedPlayer player = new AdvancedPlayer(bgMusic);
				player.play();
			}while(isloop);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
