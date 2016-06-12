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
		txtrAsdasd.append("1. 파 일 분 석\n");
		txtrAsdasd.append("   -> 문장들로 이루어진 json데이터에서 문장의 감성을 판별해 긍정, 중립, 부정\n");
		txtrAsdasd.append("      문장의 비율을 보여주고, 분석기가 판별한 내용을 수정하고 적용 시킬 수 있다.\n");
		txtrAsdasd.append("   1) 분석할 파일(Filename필드)과 분석기준이 될 사전파일(Dictionary필드)\n      이름을 적는다.\n");
		txtrAsdasd.append("   -> 테스트파일 : test.json, data.json\n");
		txtrAsdasd.append("   -> 사      전 : dict.xls\n");
		txtrAsdasd.append("   2) 파일명을 적은 후 Analyze 버튼을 분석이 시작된다.\n");
		txtrAsdasd.append("   3) 분석이 성공적으로 끝나면 누르면 아래에 로그가 노출된다.\n");
		txtrAsdasd.append("      아래 로그엔 분석과정, 과정에서 생기는 파일이 생성된 경로 등을 확인할수 있다.\n");
		txtrAsdasd.append("   4) .text, .ma, .context버튼을 클릭하면 해당 파일을 바로 확인할 수 있다.\n");
		txtrAsdasd.append("   5) 통계를 확인하고 싶으면 Show stat, 상세정보확인은 Detail & Modify\n      버튼을 누른다.\n");
		txtrAsdasd.append("2. 문 장 분 석\n");
		txtrAsdasd.append("   -> 사용자가 분석해보고 싶은 문장(Sentence필드)을 입력 후 우측의 Analyze버튼을 누른다.\n");
		txtrAsdasd.append("      문장분석에선 입력한 문장의 결과를 바로 노출한다.\n");
		txtrAsdasd.append("      # 문장 분석 결과\n");
		txtrAsdasd.append("        Sentence : 분석한 문장\n");
		txtrAsdasd.append("        Value    : 분석한 문장의 극성값\n");
		txtrAsdasd.append("        Emotion  : 극성값을 기준으로 나눈 감성상태\n");
		txtrAsdasd.append("      그 이외에 다른 사용법은 파일분석과 같다.\n");		
		
		txtrAsdasd.setEditable(false);
		getContentPane().add(txtrAsdasd);
		setVisible(true);
		setSize(599,504);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
