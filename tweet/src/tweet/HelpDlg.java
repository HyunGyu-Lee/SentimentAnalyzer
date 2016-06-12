package tweet;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class HelpDlg extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public HelpDlg()
	{
		setTitle("\uB3C4\uC6C0\uB9D0");
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea txtrAsdasd = new JTextArea();
		txtrAsdasd.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtrAsdasd.append("1. 필요한 파일\n");
		txtrAsdasd.append(".text -> 문장 출력 \n");
		txtrAsdasd.append(".context -> 단어 출력\n");
		txtrAsdasd.append("2. 사용 방법\n");
		txtrAsdasd.append("   1) 데이터 불러오기\n");
		txtrAsdasd.append("      - 아래에 파일메뉴를 이용해 데이터를 불러온다. \n");
		txtrAsdasd.append("   2) 데이터 수정\n");
		txtrAsdasd.append("      - 수정할 데이터가 있다면 Inspect버튼을 눌러서 값을 수정한다.\n");
		txtrAsdasd.append("      - 중립 또는 잘못된 극성값을 가진 데이터는 콤보박스를 통해 변경할 수 있다.\n");
		txtrAsdasd.append("      - 데이터수정 대화상자에서는, 각 데이터의 극성값 변경, 서로에게\n");
		txtrAsdasd.append("        영향을 주는 데이터끼리 묶어 따로 극성을 줄 수 있다.\n");		
		txtrAsdasd.append("      - 수정한 후 변경사항을 확인하려면 Refresh버튼을 누른다.\n");
		txtrAsdasd.append("   3) 데이터 저장\n");
		txtrAsdasd.append("      - Backup버튼을 누르면 지금까지의 변경사항을 새로운 파일로 저장한다.\n");
		txtrAsdasd.append("      - 파일명은 기존 파일명 뒤에 _(언더바)가 붙는다.\n");
		txtrAsdasd.setEditable(false);
		getContentPane().add(txtrAsdasd);
		setVisible(true);
		setSize(599,374);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
