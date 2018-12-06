package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class CloseWindow extends WindowAdapter{
	//重写父类的windowClosing()方法
	@Override
	public void windowClosing(WindowEvent e) {
		//定义提示信息
		int result = JOptionPane.showConfirmDialog(null, "是否退出游戏?", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result==0){
			//退出窗体
			System.exit(0);
		}
	}
}
