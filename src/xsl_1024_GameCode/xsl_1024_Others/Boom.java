package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Boom {
	public int x,y;
	public int index;
	public String[] img = {"e1.gif","e2.gif","e3.gif","e4.gif","e5.gif"
			,"e6.gif","e7.gif","e8.gif","e9.gif","e10.gif","e11.gif"};
	public boolean isLive = true;
	//���캯��
	public Boom(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	//��ͼ
	public void drawBoom(Graphics g){
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Image e = tk.getImage(Boom.class.
				getClassLoader().getResource("image/"+img[index]));
		//��ʼ��ͼ
		g.drawImage(e, x, y, null);
		//��̬��ըЧ��
		index++;
		if(index >= img.length){
			this.isLive = false;
			//PlayPlane.list_boom.remove(index-1);//�Ѿ��Ƴ���һ��
		}
	}
}
