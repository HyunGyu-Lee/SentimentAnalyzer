package tweet;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class FHelpDlg extends JDialog{

	private static final long serialVersionUID = 1L;
	
	public FHelpDlg()
	{
		setTitle("\uB3C4\uC6C0\uB9D0");
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea txtrAsdasd = new JTextArea();
		txtrAsdasd.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtrAsdasd.append("1. �� �� �� ��\n");
		txtrAsdasd.append("   -> ������ �̷���� json�����Ϳ��� ������ ������ �Ǻ��� ����, �߸�, ����\n");
		txtrAsdasd.append("      ������ ������ �����ְ�, �м��Ⱑ �Ǻ��� ������ �����ϰ� ���� ��ų �� �ִ�.\n");
		txtrAsdasd.append("   1) �м��� ����(Filename�ʵ�)�� �м������� �� ��������(Dictionary�ʵ�)\n      �̸��� ���´�.\n");
		txtrAsdasd.append("   -> �׽�Ʈ���� : test.json, data.json\n");
		txtrAsdasd.append("   -> ��      �� : dict.xls\n");
		txtrAsdasd.append("   2) ���ϸ��� ���� �� Analyze ��ư�� �м��� ���۵ȴ�.\n");
		txtrAsdasd.append("   3) �м��� ���������� ������ ������ �Ʒ��� �αװ� ����ȴ�.\n");
		txtrAsdasd.append("      �Ʒ� �α׿� �м�����, �������� ����� ������ ������ ��� ���� Ȯ���Ҽ� �ִ�.\n");
		txtrAsdasd.append("   4) .text, .ma, .context��ư�� Ŭ���ϸ� �ش� ������ �ٷ� Ȯ���� �� �ִ�.\n");
		txtrAsdasd.append("   5) ��踦 Ȯ���ϰ� ������ Show stat, ������Ȯ���� Detail & Modify\n      ��ư�� ������.\n");
		txtrAsdasd.append("2. �� �� �� ��\n");
		txtrAsdasd.append("   -> ����ڰ� �м��غ��� ���� ����(Sentence�ʵ�)�� �Է� �� ������ Analyze��ư�� ������.\n");
		txtrAsdasd.append("      ����м����� �Է��� ������ ����� �ٷ� �����Ѵ�.\n");
		txtrAsdasd.append("      # ���� �м� ���\n");
		txtrAsdasd.append("        Sentence : �м��� ����\n");
		txtrAsdasd.append("        Value    : �м��� ������ �ؼ���\n");
		txtrAsdasd.append("        Emotion  : �ؼ����� �������� ���� ��������\n");
		txtrAsdasd.append("      �� �̿ܿ� �ٸ� ������ ���Ϻм��� ����.\n");		
		
		txtrAsdasd.setEditable(false);
		getContentPane().add(txtrAsdasd);
		setVisible(true);
		setSize(599,504);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
