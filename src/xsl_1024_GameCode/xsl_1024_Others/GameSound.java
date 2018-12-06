package xsl_1024_GameCode.xsl_1024_Others;

import java.io.InputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class GameSound extends Thread{
	private String mp3Url;
	private boolean isloop;
	//���ɹ��캯��
	public GameSound(String mp3Url, boolean isloop) {
		super();
		this.mp3Url = mp3Url;
		this.isloop = isloop;	
	}
	//��д����run()����
	@Override
	public void run() {
		super.run();
		try{
			do{
				InputStream bgMusic = GameSound.class.getClassLoader().getResourceAsStream("music/"+mp3Url);
				//���ţ����ò��ź���
				AdvancedPlayer player = new AdvancedPlayer(bgMusic);
				player.play();
			}while(isloop);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
