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
		txtrAsdasd.append("1. �ʿ��� ����\n");
		txtrAsdasd.append(".text -> ���� ��� \n");
		txtrAsdasd.append(".context -> �ܾ� ���\n");
		txtrAsdasd.append("2. ��� ���\n");
		txtrAsdasd.append("   1) ������ �ҷ�����\n");
		txtrAsdasd.append("      - �Ʒ��� ���ϸ޴��� �̿��� �����͸� �ҷ��´�. \n");
		txtrAsdasd.append("   2) ������ ����\n");
		txtrAsdasd.append("      - ������ �����Ͱ� �ִٸ� Inspect��ư�� ������ ���� �����Ѵ�.\n");
		txtrAsdasd.append("      - �߸� �Ǵ� �߸��� �ؼ����� ���� �����ʹ� �޺��ڽ��� ���� ������ �� �ִ�.\n");
		txtrAsdasd.append("      - �����ͼ��� ��ȭ���ڿ�����, �� �������� �ؼ��� ����, ���ο���\n");
		txtrAsdasd.append("        ������ �ִ� �����ͳ��� ���� ���� �ؼ��� �� �� �ִ�.\n");		
		txtrAsdasd.append("      - ������ �� ��������� Ȯ���Ϸ��� Refresh��ư�� ������.\n");
		txtrAsdasd.append("   3) ������ ����\n");
		txtrAsdasd.append("      - Backup��ư�� ������ ���ݱ����� ��������� ���ο� ���Ϸ� �����Ѵ�.\n");
		txtrAsdasd.append("      - ���ϸ��� ���� ���ϸ� �ڿ� _(�����)�� �ٴ´�.\n");
		txtrAsdasd.setEditable(false);
		getContentPane().add(txtrAsdasd);
		setVisible(true);
		setSize(599,374);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
