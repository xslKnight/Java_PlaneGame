package xsl_1024_GameCode.xsl_1024_Others;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class CloseWindow extends WindowAdapter{
	//��д�����windowClosing()����
	@Override
	public void windowClosing(WindowEvent e) {
		//������ʾ��Ϣ
		int result = JOptionPane.showConfirmDialog(null, "�Ƿ��˳���Ϸ?", "��ʾ",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result==0){
			//�˳�����
			System.exit(0);
		}
	}
}
